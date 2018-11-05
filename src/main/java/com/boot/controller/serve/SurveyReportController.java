package com.boot.controller.serve;

import com.boot.controller.system.BaseController;
import com.boot.model.Student;
import com.boot.model.survey.*;
import com.boot.model.survey.enums.QuType;
import com.boot.service.survey.*;
import com.boot.util.AjaxResult;
import com.boot.util.CookieUtils;
import com.boot.util.HtmlUtil;
import com.boot.util.excel.XLSExportUtil;
import com.mysql.cj.util.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 问卷统计
 * @author mrren
 */
@Controller
@RequestMapping("/survey_report")
public class SurveyReportController extends BaseController{

    private static final String BASE_PATH = "survey";
    private static final String LIST = "survey.list";

    @Autowired
    SurveyDirectoryService surveyDirectoryService;

    @Autowired
    QuestionService questionService;

    @Autowired
    SurveyStyleService surveyStyleService;

    @Autowired
    SurveyDetailService surveyDetailService;

    @Autowired
    SurveyAnswerService surveyAnswerService;

    @Autowired
    SurveyStatsService surveyStatsService;

    @RequestMapping("/surveyreport")
    public String defaultReport(Model model,HttpServletRequest  request) throws Exception {
        // 得到频数分析数据
        //User user = accountManager.getCurUser();
        //if(user!=null){
           // directory=directoryManager.getSurveyByUser(surveyId, user.getId());
        Object surveyId=request.getParameter("id");
        if (surveyId == null || "".equals(surveyId)) {
            throw  new Exception("问卷id为空");
        }
        SurveyDirectory directory = surveyDirectoryService.getSurveyById(Long.valueOf(surveyId.toString()));
        SurveyStats surveyStats = new SurveyStats();
            if(directory!=null){
                List<Question> questions = surveyStatsService.findFrequency(directory);
                surveyStats.setQuestions(questions);
            }
            model.addAttribute("surveyStats", surveyStats);
        model.addAttribute("directory", directory);
        //}
        return BASE_PATH + "/survey_report";
    }
    @ResponseBody
    @RequestMapping(value="/chartData")
    public void chartData(HttpServletRequest  request, HttpServletResponse response) throws Exception {
        //取柱状图数据
        //User user = accountManager.getCurUser();
        //if(user!=null){
        Object questionId=request.getParameter("quId");
        Question question=new Question();
        question.setId(Long.valueOf(questionId.toString()));
        surveyStatsService.questionDateCross(question);
        response.getWriter().write(question.getStatJson());
        //}
    }

    @RequestMapping("/fillblank_answerList_html")
    public String fillblank_answerList_html(Model model, HttpServletRequest request) {
        model.addAttribute("quId",request.getParameter("quId"));
        model.addAttribute("surveyId",request.getParameter("surveyId"));
        return BASE_PATH + "/fb_answer_list";
    }

    @ResponseBody
    @RequestMapping("/fillblank_answerList")
    public Object fillblank_answerList(HttpServletRequest request) {
        Map params = new HashMap();
        params.put("quId",request.getParameter("quId"));
        params.put("surveyId",request.getParameter("surveyId"));
        String realName = request.getParameter("realName");
        if (!StringUtils.isNullOrEmpty(realName)) {
            params.put("realName",realName);
        }
        String userName = request.getParameter("userName");
        if (!StringUtils.isNullOrEmpty(userName)) {
            params.put("userName",userName);
        }
        Map map = pageQueryCondition("svanswer.fbAnswerList", request, params);
        return map;
    }

    @RequestMapping("/multifillblank_answerList_html")
    public String multifillblank_answerList_html(Model model,HttpServletRequest request) {
        model.addAttribute("quItemId",request.getParameter("quItemId"));
        model.addAttribute("surveyId",request.getParameter("surveyId"));
        return BASE_PATH + "/mfb_answer_list";
    }

    @ResponseBody
    @RequestMapping("/multifillblank_answerList")
    public Object multifillblank_answerList(HttpServletRequest request) {
        Map params = new HashMap();
        params.put("quItemId",request.getParameter("quItemId"));
        params.put("surveyId",request.getParameter("surveyId"));
        String realName = request.getParameter("realName");
        if (!StringUtils.isNullOrEmpty(realName)) {
            params.put("realName",realName);
        }
        String userName = request.getParameter("userName");
        if (!StringUtils.isNullOrEmpty(userName)) {
            params.put("userName",userName);
        }
        Map map = pageQueryCondition("svanswer.mfbAnswerList", request, params);
        return map;
    }

    @ResponseBody
    @RequestMapping(value="/exportXLS")
    public void exportXLS(HttpServletRequest  request, HttpServletResponse response) throws Exception{
        Object surveyId=request.getParameter("surveyId");
        if (surveyId == null || "".equals(surveyId)) {
            throw  new Exception("问卷id为空");
        }
        try{
            String savePath = request.getSession().getServletContext().getRealPath("/");
                SurveyDirectory survey=surveyDirectoryService.getSurveyById(Long.valueOf(surveyId.toString()) );
                if(survey!=null){
                    exportXLS(Long.valueOf(surveyId.toString()),savePath, response);
                }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportXLS(Long surveyId, String savePath, HttpServletResponse response) {
        String basepath = surveyId + "";
        String urlPath = "/file/" + basepath + "/";// 下载所用的地址
        String path = urlPath.replace("/", File.separator);// 文件系统路径
        savePath = savePath + path;
        File file = new File(savePath);
        if (!file.exists())
            file.mkdirs();
        SurveyDirectory surveyDirectory = surveyDirectoryService.getSurveyById(surveyId);
        String fileName = surveyDirectory.getSurveyName() + "的统计.xlsx";

        XLSExportUtil exportUtil = new XLSExportUtil(fileName, savePath);
        try {
            List<SurveyAnswer> answers = surveyAnswerService.getAnswerBySurveyId(surveyId);
            List<Question> questions = questionService.findDetailsBySurveyId(surveyId,"2");
            exportXLSTitle(exportUtil, questions);
            int answerListSize = answers.size();
            for (int j = 0; j < answerListSize; j++) {
                SurveyAnswer surveyAnswer = answers.get(j);
                Long surveyAnswerId = surveyAnswer.getId();
                exportUtil.createRow(j+1);
                exportXLSRow(exportUtil, surveyAnswerId, questions, surveyAnswer);
                System.out.println(j+1+"/"+answerListSize);
            }
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                exportUtil.getWorkbook().write(os);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes(), "iso-8859-1"));
            ServletOutputStream out = response.getOutputStream();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                bis = new BufferedInputStream(is);
                bos = new BufferedOutputStream(out);
                byte[] buff = new byte[2048];
                int bytesRead;
                // Simple read/write loop.
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
            } catch (final IOException e) {
                throw e;
            } finally {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
                response.flushBuffer();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exportXLSRow(XLSExportUtil exportUtil,Long surveyAnswerId, List<Question> questions,SurveyAnswer surveyAnswer) {
        int cellIndex = 0;
        for (Question question : questions) {
            surveyAnswerService.getquestionAnswer(surveyAnswerId, question);
            QuType quType = question.getQuType();
            String quName = question.getQuName();
            String titleName = quType.getCnName();
            if (quType == QuType.RADIO) {// 单选题
                Long quItemId = question.getAnRadio().getQuItemId();
                List<QuRadio> quRadios=question.getQuRadios();

                boolean isNote = false;
                for (QuRadio quRadio : quRadios) {
                    String answerOptionName="0";
                    String answerOtherText="";
                    Long quRadioId=quRadio.getId();
                    if(quRadioId ==quItemId){
                        answerOptionName="1";
                        if(quRadio.getIsNote()==1){
                            answerOtherText = question.getAnRadio().getOtherText();
                            isNote = true;
                        }
                    }
                    exportUtil.setCell(cellIndex++, answerOptionName);

                    answerOtherText= HtmlUtil.removeTagFromText(answerOtherText);
                    answerOtherText = answerOtherText.replace("&nbsp;"," ");
                    /*if(isNote)*/ exportUtil.setCell(cellIndex++, answerOtherText);

                }
            } else if (quType == QuType.CHECKBOX) {// 多选题
                List<AnCheckbox> anCheckboxs=question.getAnCheckboxs();
                List<QuCheckbox> checkboxs = question.getQuCheckboxs();
                for (QuCheckbox quCheckbox : checkboxs) {
                    Long quCkId=quCheckbox.getId();
                    String answerOptionName="0";
                    String answerOtherText="";
                    boolean isNote = false;
                    for (AnCheckbox anCheckbox : anCheckboxs) {
                        Long anQuItemId=anCheckbox.getQuItemId();
                        if(quCkId == anQuItemId){
                            answerOptionName="1";
                            if(quCheckbox.getIsNote() == 1){
                                answerOtherText = anCheckbox.getOtherText();
                                isNote = true;
                            }
                            break;
                        }
                    }

                    exportUtil.setCell(cellIndex++, answerOptionName);
                    answerOtherText=HtmlUtil.removeTagFromText(answerOtherText);
                    answerOtherText = answerOtherText.replace("&nbsp;"," ");
                    /*if(isNote)*/ exportUtil.setCell(cellIndex++, answerOtherText);
                }
            } else if (quType == QuType.FILLBLANK) {// 填空题
                AnFillblank anFillblank=question.getAnFillblank();
                exportUtil.setCell(cellIndex++, anFillblank.getAnswer());
            }   else if (quType == QuType.MULTIFILLBLANK) {// 组合填空题
                List<QuMultiFillblank> quMultiFillblanks = question.getQuMultiFillblanks();
                List<AnDFillblank> anDFillblanks=question.getAnDFillblanks();
                for (QuMultiFillblank quMultiFillblank : quMultiFillblanks) {
                    Long quMultiFillbankId=quMultiFillblank.getId();
                    String answerOptionName="";
                    for (AnDFillblank anDFillblank : anDFillblanks) {
                        if(quMultiFillbankId == anDFillblank.getQuItemId()){
                            answerOptionName=anDFillblank.getAnswer();
                            break;
                        }
                    }
                    answerOptionName = answerOptionName.replace("&nbsp;"," ");
                    exportUtil.setCell(cellIndex++, answerOptionName);
                }
            }
        }
        exportUtil.setCell(cellIndex++,  new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(surveyAnswer.getEndAnDate()));
    }

    private void exportXLSTitle(XLSExportUtil exportUtil,
                                List<Question> questions) {
        exportUtil.createRow(0);
        int cellIndex = 0;


        int quNum=0;
        for (Question question : questions) {
            quNum++;
            QuType quType = question.getQuType();
            String quName = question.getQuTitle();
            quName=HtmlUtil.removeTagFromText(quName);
            String titleName =quNum +"、" + quName + "[" + quType.getCnName() + "]";
            if (quType == QuType.RADIO) {// 单选题
                List<QuRadio> quRadios=question.getQuRadios();
                boolean isNote = false;
                for (QuRadio quRadio : quRadios) {
                    String optionName = quRadio.getOptionName();
                    optionName=HtmlUtil.removeTagFromText(optionName);
                    exportUtil.setCell(cellIndex++, titleName + "－" + optionName );
                    /*if(isNote)*/ exportUtil.setCell(cellIndex++, titleName + "－" + optionName  + "-选项说明");
                }
            } else if (quType == QuType.CHECKBOX) {// 多选题
                List<QuCheckbox> checkboxs = question.getQuCheckboxs();
                for (QuCheckbox quCheckbox : checkboxs) {
                    String optionName = quCheckbox.getOptionName();
                    optionName=HtmlUtil.removeTagFromText(optionName);
                    exportUtil.setCell(cellIndex++,titleName + "－" + optionName );
                    /*if(quCheckbox.getIsNote()==1){*/
                    exportUtil.setCell(cellIndex++, titleName+ "－" + optionName  + "－选项说明");

                }
            } else if (quType == QuType.FILLBLANK) {// 填空题
                exportUtil.setCell(cellIndex++, titleName);
            } else if (quType == QuType.MULTIFILLBLANK) {// 组合填空题
                List<QuMultiFillblank> quMultiFillblanks = question
                        .getQuMultiFillblanks();
                for (QuMultiFillblank quMultiFillblank : quMultiFillblanks) {
                    String optionName = quMultiFillblank.getOptionName();
                    optionName=HtmlUtil.removeTagFromText(optionName);
                    exportUtil.setCell(cellIndex++, titleName + "－"
                            + optionName);
                }
            }
        }

        exportUtil.setCell(cellIndex++,  "回答时间");

    }

}
