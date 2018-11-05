package com.boot.service.survey.impl;

import com.boot.model.survey.*;
import com.boot.model.survey.enums.QuType;
import com.boot.service.impl.BaseServiceImpl;
import com.boot.service.survey.*;
import com.boot.util.HtmlUtil;
import com.boot.util.excel.XLSExportUtil;
import org.beetl.sql.core.annotatoin.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SurveyAnswerServiceImpl  extends BaseServiceImpl implements SurveyAnswerService {

    @Autowired
    SurveyDirectoryService surveyDirectoryService;

    @Autowired
    SurveyAnswerService surveyAnswerService;

    @Autowired
    SurveyStatsService surveyStatsService;

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
    public void saveAnswer(SurveyAnswer surveyAnswer,
                     Map<String, Map<String, Object>> quMaps) {


        Date curDate=new Date();

        //保存答案信息
        Long surveyId=surveyAnswer.getSurveyId();
//		Survey survey=(Survey) session.get(Survey.class, surveyId);
        SurveyDirectory survey=surveyDirectoryService.getSurveyById(surveyId);
//		System.out.println("survey:"+survey);
        Integer answerNum = survey.getAnswerNum();
        if(answerNum==null){
            answerNum=0;
        }
        survey.setAnswerNum(answerNum+1);
        surveyDirectoryService.saveOrUpdate(survey);
        int surveyQuAnItemNum=survey.getAnItemLeastNum();//可以回答的最少项目数

        surveyAnswer.setBgAnDate(curDate);
        surveyAnswer.setEndAnDate(new Date());

        //计算答卷用时
        long time=surveyAnswer.getEndAnDate().getTime()-surveyAnswer.getBgAnDate().getTime();
        surveyAnswer.setTotalTime(Float.parseFloat(time/(60*60)+""));
        SQLManager.insert(SurveyAnswer.class,surveyAnswer, true);

        int anCount=0;
        //保存答案
        //单选题
        Map<String,Object> radioMaps=quMaps.get("radioMaps");
        anCount+=saveAnRadioMaps(surveyAnswer, radioMaps);
        //多选题
        Map<String,Object> checkboxMaps=quMaps.get("checkboxMaps");
        anCount+=saveAnCheckboxMaps(surveyAnswer,checkboxMaps);
        //填空题
        Map<String,Object> fillblankMaps=quMaps.get("fillblankMaps");
        anCount+=saveAnFillMaps(surveyAnswer, fillblankMaps);
        //多项填空题
        Map<String,Object> multifillblankMaps=quMaps.get("multifillblankMaps");
        anCount+=saveAnMultiFillMaps(surveyAnswer, multifillblankMaps);
        //问答题
       /* Map<String,Object> answerMaps=quMaps.get("answerMaps");
        anCount+=saveAnAnswerMaps(surveyAnswer, answerMaps,session);*/
        //复合单选题
        Map<String,Object> compRadioMaps=quMaps.get("compRadioMaps");
        anCount+=saveCompAnRadioMaps(surveyAnswer, compRadioMaps);
        //复合多选题
        Map<String,Object> compCheckboxMaps=quMaps.get("compCheckboxMaps");
        anCount+=saveCompAnCheckboxMaps(surveyAnswer, compCheckboxMaps);

        //保存anCount
        surveyAnswer.setCompleteItemNum(anCount);
        int isComplete=0;
        if(anCount>=surveyQuAnItemNum){
            isComplete=1;//表示回完
        }
        surveyAnswer.setIsComplete(isComplete);
        int isEffective=0;
        if(anCount>0){
            isEffective=1;//暂时只要答过一题就表示回答有效
        }
        surveyAnswer.setIsEffective(isEffective);
        SQLManager.updateById(surveyAnswer);

        //更新统计状态
        SurveyStats surveyStats=surveyStatsService.findBySurvey(surveyId);
        if(surveyStats!=null){
            int isNewData = surveyStats.getIsNewData();
            if(isNewData==1){
                surveyStats.setIsNewData(0);
                surveyStatsService.saveOrUpdate(surveyStats);
            }
        }else{
            surveyStats=new SurveyStats();
            surveyStats.setSurveyId(surveyId);
            surveyStatsService.saveOrUpdate(surveyStats);
        }

        SQLManager.updateById(surveyAnswer);
    }

    @Override
    public long getCountByIp(Long surveyId, String ipAddr) {
        Map map = new HashMap();
        map.put("surveyId",surveyId);
        map.put("ipAddr",ipAddr);
        return SQLManager.intValue("svanswer.countByIp", map);
    }

    /**
     * 保存单选题答案

     * @param radioMaps

     */
    private int saveAnRadioMaps(SurveyAnswer surveyAnswer,
                                Map<String, Object> radioMaps) {
        Long surveyId=surveyAnswer.getSurveyId();
        Long surveyAnswerId=surveyAnswer.getId();
        int answerQuCount=0;
        if (radioMaps!=null)
            for (String key : radioMaps.keySet()) {
                answerQuCount++;
                Long quId=Long.valueOf(key);
                Long quItemId=((AnRadio)radioMaps.get(key)).getQuItemId();
                AnRadio anRadio=new AnRadio(surveyId,surveyAnswerId,quId,quItemId);
                SQLManager.insert(AnRadio.class, anRadio, true);
            }
        return answerQuCount;
    }


    /**
     * 保存多选题答案

     * @param checkboxMaps

     */
    private int saveAnCheckboxMaps(SurveyAnswer surveyAnswer,
                                   Map<String, Object> checkboxMaps) {
        Long surveyId=surveyAnswer.getSurveyId();
        Long surveyAnswerId=surveyAnswer.getId();

        int answerQuCount=0;
        if (checkboxMaps!=null)
            for (String key : checkboxMaps.keySet()) {
                Long quId=Long.valueOf(key);
                Map<String, Object> map=(Map<String, Object>) checkboxMaps.get(key);
                for (String keyMap : map.keySet()) {
                    answerQuCount++;
                    Long quItemId=Long.valueOf(map.get(keyMap).toString());
                    AnCheckbox anCheckbox=new AnCheckbox(surveyId,surveyAnswerId,quId,quItemId);
                    SQLManager.insert(AnCheckbox.class, anCheckbox, true);
                }
            }
        return answerQuCount;
    }

    /**
     * 保存单项填空题答案

     * @param fillMaps

     */
    private int saveAnFillMaps(SurveyAnswer surveyAnswer,
                               Map<String, Object> fillMaps) {
        Long surveyId=surveyAnswer.getSurveyId();
        Long surveyAnswerId=surveyAnswer.getId();
        int answerQuCount=0;
        for (String key : fillMaps.keySet()) {
            answerQuCount++;
            Long quId=Long.valueOf(key);
            String answerValue=fillMaps.get(key).toString();
            AnFillblank anFillblank=new AnFillblank(surveyId,surveyAnswerId,quId,answerValue);
            SQLManager.insert(AnFillblank.class, anFillblank,true);
        }
        return answerQuCount;
    }

    /**
     * 保存多项填空题答案

     * @param dfillMaps

     */
    private int saveAnMultiFillMaps(SurveyAnswer surveyAnswer,
                                    Map<String, Object> dfillMaps) {
        Long surveyId=surveyAnswer.getSurveyId();
        Long surveyAnswerId=surveyAnswer.getId();

        int answerQuCount=0;
        for (String key : dfillMaps.keySet()) {
            Long quId=Long.valueOf(key);
            Map<String, Object> map=(Map<String, Object>) dfillMaps.get(key);
            if(map!=null && map.size()>0){
                for (String keyMap : map.keySet()) {
                    answerQuCount++;
                    //answerValue+=keyMap+Question.splitTag1+map.get(keyMap)+Question.splitTag;
                    Long quItemId=Long.valueOf(keyMap);
                    String answerValue=map.get(keyMap).toString();
                    AnDFillblank anDFillblank=new AnDFillblank(surveyId,surveyAnswerId,quId,quItemId,answerValue);
                    SQLManager.insert(AnDFillblank.class, anDFillblank, true);
                }
            }
        }
        return answerQuCount;
    }

    /**
     * 复合单选题
     * @param surveyAnswer
     * @param compRadioMaps

     */
    private int saveCompAnRadioMaps(SurveyAnswer surveyAnswer,
                                    Map<String, Object> compRadioMaps) {
        Long surveyId=surveyAnswer.getSurveyId();
        Long surveyAnswerId=surveyAnswer.getId();
        int answerQuCount=0;
        for (String key : compRadioMaps.keySet()) {
            answerQuCount++;
            Long quId=Long.valueOf(key);
            //String quItemId=compRadioMaps.get(key).toString();
            AnRadio tempAnRadio=(AnRadio) compRadioMaps.get(key);
            Long quItemId=tempAnRadio.getQuItemId();
            String othertext=tempAnRadio.getOtherText();
            AnRadio anRadio=new AnRadio(surveyId,surveyAnswerId,quId,quItemId);
            anRadio.setOtherText(othertext);
            SQLManager.insert(AnRadio.class, anRadio, true);
        }
        return answerQuCount;
    }
    /**
     * 保存复合多选题答案
     */
    private int saveCompAnCheckboxMaps(SurveyAnswer surveyAnswer,
                                       Map<String, Object> compCheckboxMaps) {
        Long surveyId=surveyAnswer.getSurveyId();
        Long surveyAnswerId=surveyAnswer.getId();
        int answerQuCount=0;
        for (String key : compCheckboxMaps.keySet()) {
            Long quId=Long.valueOf(key);
            Map<String, Object> map=(Map<String, Object>) compCheckboxMaps.get(key);
            for (String keyMap : map.keySet()) {
                answerQuCount++;
//				String quItemId=map.get(keyMap).toString();
                AnCheckbox tempAnCheckbox=(AnCheckbox) map.get(keyMap);
                Long quItemId=tempAnCheckbox.getQuItemId();
                String otherText=tempAnCheckbox.getOtherText();
                AnCheckbox anCheckbox=new AnCheckbox(surveyId,surveyAnswerId,quId,quItemId);
                anCheckbox.setOtherText(otherText);
                SQLManager.insert(AnCheckbox.class, anCheckbox, true);
            }
        }
        return answerQuCount;
    }



    @Override
    public List<SurveyAnswer> getAnswerBySurveyId(Long surveyId) {
        Map map = new HashMap();
        map.put("surveyId", surveyId);
        return SQLManager.select("svanswer.getAnswerBySurveyId", SurveyAnswer.class, map);
    }

    @Override
    public long getAnswerCountByUser(Long surveyId, Long userId) {
        Map map = new HashMap();
        map.put("surveyId",surveyId);
        map.put("userId",userId);
        return SQLManager.intValue("svanswer.countByUserId", map);
    }

    /**
     * 取问卷值方式
     *
     * @param surveyAnswerId
     * @param question
     * @return
     */
    @Override
    public int getquestionAnswer(Long surveyAnswerId, Question question) {
        int score = 0;
        Long quId = question.getId();
        // 查询每一题的答案,如果是主观题，则判断是否答对
        QuType quType = question.getQuType();

        //重置导出
        question.setAnCheckboxs(new ArrayList<>());
        question.setAnDFillblanks(new ArrayList<>());
        question.setAnFillblank(new AnFillblank());
        question.setAnRadio(new AnRadio());

        if (quType == QuType.RADIO) {// 单选题答案
            // 复合
            AnRadio anRadio = quRadioService.findAnswer(surveyAnswerId, quId);
            if (anRadio != null) {
                question.setAnRadio(anRadio);
            }
        } else if (quType == QuType.CHECKBOX) {// 多选题答案
            // 复合
            List<AnCheckbox> anCheckboxs = quCheckboxService.findAnswer(
                    surveyAnswerId, quId);
            if (anCheckboxs != null) {
                question.setAnCheckboxs(anCheckboxs);
            }
        } else if (quType == QuType.FILLBLANK) {// 单项填空题答案
            AnFillblank anFillblank = quFillblankService.findAnswer(
                    surveyAnswerId, quId);
            if (anFillblank != null) {
                question.setAnFillblank(anFillblank);
            }

        } else if (quType == QuType.MULTIFILLBLANK) {// 多项填空题答案
            List<AnDFillblank> anDFillblanks = quMultiFillblankService.findAnswer(
                    surveyAnswerId, quId);
            if (anDFillblanks != null) {
                question.setAnDFillblanks(anDFillblanks);
            }
        }
        return score;
    }

}
