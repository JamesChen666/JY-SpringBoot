package com.boot.service.survey.impl;

import com.alibaba.fastjson.JSONArray;
import com.boot.model.survey.Question;
import com.boot.model.survey.SurveyDirectory;
import com.boot.model.survey.SurveyReport;
import com.boot.model.survey.SurveyStats;
import com.boot.model.survey.enums.QuType;
import com.boot.service.impl.BaseServiceImpl;
import com.boot.service.survey.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class SurveyStatsServiceImpl extends BaseServiceImpl implements SurveyStatsService {

    @Autowired
    QuestionService questionService;

    @Autowired
    QuRadioService quRadioService;

    @Autowired
    QuCheckboxService quCheckboxService;

    @Autowired
    QuFillblankService quFillblankService;

    @Autowired
    QuMultiFillblankService quMultiFillblankService;

    @Override
    public SurveyStats findBySurvey(Long surveyId) {
        return null;
    }

    @Override
    public void saveOrUpdate(SurveyStats surveyStats) {
        if (surveyStats.getId() == null || surveyStats.getId() <=0l) {
            SQLManager.insert(SurveyStats.class, surveyStats, true);
        } else {
            SQLManager.updateById(surveyStats);
        }
    }

    @Override
    public List<Question> findFrequency(SurveyDirectory survey) {
        List<Question> questions = questionService.findDetailsBySurveyId(survey.getId(),
                "2");
        for (Question question : questions) {
            QuType quType = question.getQuType();

            if (QuType.RADIO == quType) {// 单选
                // 复合单选
                quRadioService.findGroupStats(question);
            } else if (QuType.CHECKBOX == quType
                    || QuType.COMPCHECKBOX == quType) {// 多选 复合多选
                quCheckboxService.findGroupStats(question);
            } else if (QuType.FILLBLANK == quType) {// 填空题
                quFillblankService.findGroupStats(question);
            } else if (QuType.MULTIFILLBLANK == quType) {// 组合填空
                quMultiFillblankService.findGroupStats(question);
            }
        }

        return questions;
    }

    @Override
    public void questionReports(Question question) {
        List<SurveyReport> reports = findDataChart(question);
        if(reports!=null){
            for (SurveyReport surveyReport : reports) {
                // 去掉optionName中的html
                String optionName = surveyReport.getOptionName();
                if(optionName==null){
                    optionName="";
                }
                optionName = removeTagFromText(optionName);
                if(optionName.length()>15){
                    optionName=optionName.substring(0, 15)+"...";
                }
                surveyReport.setOptionName(optionName);
            }
            String statJson = JSONArray.toJSONString(reports);
            question.setStatJson(statJson);
        }
    }

    @Override
    public List<SurveyReport> findDataChart(Question question) {
        if (question != null ) {
            QuType quType = question.getQuType();
             if (QuType.RADIO == quType ) {// 单选题
                return quRadioService.findStatsDataChart(question);
            } else if (QuType.CHECKBOX == quType ) {
                return quCheckboxService.findStatsDataChart(question);
            } else if (QuType.FILLBLANK == quType){
                quFillblankService.findGroupStats(question);
            } else if (QuType.MULTIFILLBLANK == quType){
                quMultiFillblankService.findGroupStats(question);
            }
        }
        return null;
    }


    public void questionDateCross(Question question) {
        List<SurveyReport> crosses = findDataChart(question);
        if(crosses!=null){
            for (SurveyReport dataCross : crosses) {
                // 去掉optionName中的html
                String optionName = dataCross.getOptionName();
                if(optionName==null){
                    optionName="";
                }
                optionName = removeTagFromText(optionName);
                if(optionName.length()>15){
                    optionName=optionName.substring(0, 15)+"...";
                }
                dataCross.setOptionName(optionName);
            }
            String statJson = JSONArray.toJSONString(crosses);

            question.setStatJson(statJson);
        }
    }


    public String removeTagFromText(String htmlStr) {
        if (htmlStr == null || "".equals(htmlStr))
            return "";
        String textStr = "";
        java.util.regex.Pattern pattern;
        java.util.regex.Matcher matcher;

        try {
            String regEx_remark = "<!--.+?-->";
            // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            // }
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";

            // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            // }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";

            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            String regEx_html1 = "<[^>]+";
            htmlStr = htmlStr.replaceAll("\n", "");
            htmlStr = htmlStr.replaceAll("\t", "");
            htmlStr = htmlStr.replaceAll("\r", "");

            pattern = Pattern.compile(regEx_remark);// 过滤注释标签
            matcher = pattern.matcher(htmlStr);
            htmlStr = matcher.replaceAll("");

            pattern = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(htmlStr);
            htmlStr = matcher.replaceAll(""); // 过滤script标签

            pattern = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(htmlStr);
            htmlStr = matcher.replaceAll(""); // 过滤style标签

            pattern = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(htmlStr);
            htmlStr = matcher.replaceAll(""); // 过滤html标签

            pattern = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(htmlStr);
            htmlStr = matcher.replaceAll(""); // 过滤html标签

            htmlStr = htmlStr.replaceAll("\n[\\s| ]*\r", "");
            htmlStr = htmlStr.replaceAll("<(.*)>(.*)<\\/(.*)>|<(.*)\\/>", "");

            textStr = htmlStr.trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textStr;// 返回文本字符串
    }
}
