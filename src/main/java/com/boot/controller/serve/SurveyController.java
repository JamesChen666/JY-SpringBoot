package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import com.boot.controller.system.BaseController;
import com.boot.model.Loginaccount;
import com.boot.model.survey.*;
import com.boot.model.survey.enums.QuType;
import com.boot.service.survey.*;
import com.boot.util.AjaxResult;
import com.boot.util.RandomUtils;
import com.boot.util.ShiroUtils;
import org.apache.commons.lang3.StringUtils;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 问卷列表和设计
 * @author mrren
 */
@Controller
@RequestMapping("/survey")
public class SurveyController extends BaseController{

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

    @RequestMapping("")
    public String index() {
        return BASE_PATH+"/survey_list";
    }

    @ResponseBody
    @RequestMapping("/list")
    public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST, httpServletRequest);
        return map;
    }

    @ResponseBody
    @RequestMapping("/questionList")
    public Object questionList(){
        List<Map> mapList = sqlManager.select("survey.questionList", Map.class);
        return mapList;
    }

    @ResponseBody
    @RequestMapping("/searchList")
    public Object searchList(HttpServletRequest httpServletRequest){
        List<Map> mapList = sqlManager.select("survey.searchList", Map.class,
                Dict.create().set("search", httpServletRequest.getParameter("content")));
        return mapList;
    }

    @ResponseBody
    @RequestMapping("/detail/{id}")
    public Object detail(@PathVariable Integer id){
        Map mapList = sqlManager.selectSingle("survey.findQuestion",
                Dict.create().set("Id", id),Map.class);
        return mapList;
    }

    @RequestMapping("/design")
    public String design(Model model, HttpServletRequest request) {
        Object surveyIdObj = request.getParameter("id");
        Long surveyId = 0l;
        if (surveyIdObj == null) {
            return BASE_PATH+"/survey_design";
        } else {
            surveyId = Long.valueOf(surveyIdObj.toString());
        }
        SurveyDirectory surveyDirectory = surveyDirectoryService.getSurveyById(surveyId);
        if (surveyDirectory!=null) {
            surveyDetailService.getSurveyDetail(surveyId, surveyDirectory);
            List<Question> questions =  questionService.findDetailsBySurveyId(surveyId, "2");
            surveyDirectory.setQuestions(questions);
            surveyDirectory.setSurveyQuNum(questions.size());
            //surveyDirectoryService.save(surveyDirectory);
            model.addAttribute("survey", surveyDirectory);
            SurveyStyle surveyStyle=surveyStyleService.getBySurveyId(surveyId);
            model.addAttribute("surveyStyle", surveyStyle);

            model.addAttribute("prevHost", null);
        }
        return BASE_PATH+"/survey_design";
    }


    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult ajaxSave( HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object surveyId=request.getParameter("surveyId");
        String svyName=request.getParameter("svyName");
        String svyNote=request.getParameter("svyNote");
        //属性
        String toGroup=request.getParameter("toGroup");
        String isForce=request.getParameter("isForce");
        String begTime=request.getParameter("begTime");
        String endTime=request.getParameter("endTime");

        SurveyDirectory survey= new SurveyDirectory();
        if (surveyId != null && !"".equals(surveyId)) {
            survey=surveyDirectoryService.getSurveyById(Long.valueOf(surveyId.toString()));
            surveyDetailService.getSurveyDetail(Long.valueOf(surveyId.toString()),survey);
        }

        SurveyDetail surveyDetail= survey.getSurveyDetail();
        if (surveyDetail == null) {
            surveyDetail= new SurveyDetail();
        }
       /* Loginaccount loginaccount = ShiroUtils.getInstence().getUser();
        Integer userId=loginaccount.getId();
        if(userId.equals(survey.getUserId())){*/
            //survey.setUserId(Long.valueOf(userId));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if( svyNote!=null){
                svyNote= URLDecoder.decode(svyNote,"utf-8");
                surveyDetail.setSurveyNote(svyNote);
            }
            if(svyName!=null && !"".equals(svyName)){
                svyName=URLDecoder.decode(svyName,"utf-8");
                survey.setSurveyName(svyName);
            }

            //保存属性
            if(StringUtils.isNotEmpty(toGroup)){
                surveyDetail.setToGroup(toGroup);
            }
            if(StringUtils.isNotEmpty(isForce)){
                surveyDetail.setIsForce(Integer.valueOf(isForce.toString()));
            }
            if(StringUtils.isNotEmpty(begTime)){
                surveyDetail.setBegTime(sdf.parse(begTime));
            }
            if(StringUtils.isNotEmpty(endTime)){
                surveyDetail.setEndTime(sdf.parse(endTime));
            }
            surveyDirectoryService.saveOrUpdate(survey);
            surveyDetail.setDirId(survey.getId());
            surveyDetailService.saveOrUpdate(surveyDetail);

        //}
        return success("保存成功",survey);
    }

    @ResponseBody
    @RequestMapping("/delete")
    public AjaxResult delete(HttpServletRequest httpServletRequest) {
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        int deleteById = 0;
        for (String s : parameterMap.keySet()) {
            String id = httpServletRequest.getParameter(s);
            List<Question> questions = sqlManager.query(Question.class)
                    .andEq("belong_id", id).select();
            for (Question question : questions) {
                deleteById += sqlManager.update("survey.deleteQuestion",
                        Dict.create().set("qid", question.getId()));
            }
            deleteById += sqlManager.update("survey.deleteDirectory",
                    Dict.create().set("id", id));

        }
        if (deleteById <= 0) {
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

    /**
     * 创建静态问卷html(后台调用)
     * @return
     */
    @RequestMapping("/create_html")
    public String createSurveyHtml(Model model, HttpServletRequest request){
        Object surveyId=request.getParameter("surveyId");
        if (surveyId == null || "".equals(surveyId)) {
            return error("问卷id为空，发布失败").getMsg();
        }
        SurveyDirectory survey=surveyDirectoryService.getSurveyById(Long.valueOf(surveyId.toString()));
        if (survey == null) {
            return error("问卷id为空，发布失败").getMsg();
        }
        surveyDetailService.getSurveyDetail(Long.valueOf(surveyId.toString()), survey);
        survey.setQuestions(questionService.findDetailsBySurveyId(Long.valueOf(surveyId.toString()), "2"));
        model.addAttribute("survey", survey);
        SurveyStyle surveyStyle=surveyStyleService.getBySurveyId(Long.valueOf(surveyId.toString()));
        model.addAttribute("surveyStyle", surveyStyle);
        model.addAttribute("prevHost", null);
        return BASE_PATH+"/survey_answer";
    }

    /**
     * 发布问卷
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/publish")
    public AjaxResult publish(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        int deleteById = 0;
        for (String s : parameterMap.keySet()) {
            String surveyId = request.getParameter(s);
            if (surveyId != null && !"".equals(surveyId)) {
                try {
                    publishSurvey(request, Long.valueOf(surveyId.toString()));
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return success(SUCCESS);
    }

    /**
     * 取消发布问卷
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/cancel")
    public AjaxResult cancel(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        int update = 0;
        for (String s : parameterMap.keySet()) {
            String surveyId = request.getParameter(s);
            if (surveyId != null && !"".equals(surveyId)) {
                try {
                     update += sqlManager.update("survey.cancelQuestion",
                            Dict.create().set("id", surveyId));
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        if (update<=0){
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

    public void publishSurvey(HttpServletRequest request, Long surveyId) throws Exception {
        SurveyDirectory survey=surveyDirectoryService.getSurveyById(surveyId);
        if (survey == null) {
             throw new Exception("问卷为空");
        }
        Date createDate=survey.getCreateDate();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd/");
        try{
            String url="/survey/create_html?surveyId="+surveyId;
            String filePath="WEB-INF/views/survey/wjHtml/"+dateFormat.format(createDate);
            String fileName=surveyId+".html";
            postJspToHtml(request, url, filePath, fileName);
            survey.setHtmlPath("/"+filePath.substring(filePath.indexOf("wjHtml")) + fileName.substring(0, fileName.length()-5));

            List<Question> questions=questionService.findDetailsBySurveyId(surveyId, "2");
            survey.setSurveyQuNum(questions.size());
            survey.setSurveyState(1);
            survey.setSid(RandomUtils.randomStr(6, 12));
            surveyDirectoryService.saveOrUpdate(survey);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void postJspToHtml( HttpServletRequest request, String postUrl, String filePath,String fileName) throws Exception{
        String reqTarget = request.getScheme()+"://"+request.getServerName()+(request.getServerPort()==80?"":":"+request.getServerPort())+request.getContextPath();
        reqTarget =reqTarget+"/toHtml";
        //?url="+postUrl+"&filePath="+filePath+"&fileName="+fileName;
        Map<String, String> map=new HashMap<String, String>();
        map.put("url", postUrl);
        map.put("filePath", filePath);
        map.put("fileName", fileName);
        Connection connection = Jsoup.connect(reqTarget);
        connection.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31");
        connection.data(map);
        Document doc=connection.timeout(8000).get();
    }

    @RequestMapping("/toAnswer")
    public String toAnswer(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String sid=request.getParameter("sid");
        if (sid == null || "".equals(sid)) {
            throw new Exception("问卷sid为空");
        }
        SurveyDirectory survey=surveyDirectoryService.getSurveyBySid(sid);
        surveyDetailService.getSurveyDetail(survey.getId(), survey);
        if (survey == null) {
            throw new Exception("问卷为空");
        } else {
            String filterStatus = filterStatus(survey);
            if(filterStatus!=null){
                throw new Exception(filterStatus);
            }
            String htmlPath = survey.getHtmlPath();
            return BASE_PATH+htmlPath;
        }
    }


    private String filterStatus(SurveyDirectory directory){
        SurveyDetail surveyDetail = directory.getSurveyDetail();
        int rule = surveyDetail.getRule();
        Integer ynEndNum = surveyDetail.getYnEndNum();
        Integer endNum = surveyDetail.getEndNum();
        Integer ynEndTime = surveyDetail.getYnEndTime();
        Date endTime = surveyDetail.getEndTime();
        Integer anserNum = directory.getAnswerNum();

        if (directory.getSurveyQuNum() <= 0
                || directory.getSurveyState() != 1 ||
                (anserNum!=null && ynEndNum==1 && anserNum > endNum ) ||
                (endTime!=null && ynEndTime==1 && endTime.getTime() < (new Date().getTime())) ){
            return "目前该问卷已暂停收集，请稍后再试";
        }
        if (2 == rule) {
            return "rule2";
        }
        return null;
    }

    /**
     * 提交问卷答案
     * @param request
     * @return
     */
    @RequestMapping("/saveAnswer")
    public String saveAnswer(Model model, HttpServletRequest request, HttpServletResponse response) {
        Object surveyId=request.getParameter("surveyId");
        if (surveyId == null || "".equals(surveyId)) {
            model.addAttribute("msg", "问卷id为空");
            return BASE_PATH + "/survey_message";
        }
        try {
            //String ipAddr = ipService.getIp(request);
            //long ipNum = surveyAnswerService.getCountByIp(Long.valueOf(surveyId.toString()), ipAddr);
            Loginaccount user = ShiroUtils.getInstence().getUser();
            SurveyAnswer entity = new SurveyAnswer();
            if (user != null) {
                entity.setUserId(Long.valueOf(user.getId()));
            }
            long count = surveyAnswerService.getAnswerCountByUser(Long.valueOf(surveyId.toString()), Long.valueOf(user.getId()));
            if (count > 1) {
                model.addAttribute("msg", "您已经参与了该问卷调查！");
                return BASE_PATH + "/survey_message";
            }
            SurveyDirectory directory = surveyDirectoryService.getSurveyById(Long.valueOf(surveyId.toString()));
            SurveyDetail surveyDetail = surveyDetailService.getSurveyDetail(Long.valueOf(surveyId.toString()), directory);
            int refreshNum = surveyDetail.getRefreshNum();

            /*Cookie cookie = CookieUtils.getCookie(request, surveyId);
            Integer effectiveIp = surveyDetail.getEffectiveIp();
            Integer effective = surveyDetail.getEffective();
            if ((effective != null && effective > 1 && cookie != null) || (effectiveIp != null && effectiveIp == 1 && ipNum > 0)) {
                return RELOAD_ANSER_ERROR;
            }*/
            /*if (ipNum >= refreshNum) {
                String code = request.getParameter("jcaptchaInput");
                if (!imageCaptchaService.validateResponseForID(request.getSession().getId(), code)) {
                    return ANSWER_CODE_ERROR;
                }
            }*/
            Map<String, Map<String, Object>> quMaps = buildSaveSurveyMap(request);
            /*String addr = ipService.getCountry(ipAddr);
            String city = ipService.getCurCityByCountry(addr);
            entity.setIpAddr(ipAddr);
            entity.setAddr(addr);
            entity.setCity(city);*/
            entity.setSurveyId(Long.valueOf(surveyId.toString()));
            entity.setDataSource(0);
            surveyAnswerService.saveAnswer(entity, quMaps);
            int effe = surveyDetail.getEffectiveTime();
            //CookieUtils.addCookie(response, surveyId+"", (ipNum+ 1) + "", effe * 60, "/");
            // exambatchManager.savePaperAnswer(entity,quMaps);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msg", "保存失败，请稍后再试");
            return BASE_PATH + "/survey_message";
        }
        model.addAttribute("msg", "保存成功");
        return BASE_PATH + "/survey_message";
    }

    public Map<String, Map<String, Object>> buildSaveSurveyMap(HttpServletRequest request) {
        Map<String, Map<String, Object>> quMaps = new HashMap<String, Map<String, Object>>();
        Map<String, Object> radioMaps = WebUtils.getParametersStartingWith(
                request, "qu_"+QuType.RADIO + "_");//单选
        Map<String, Object> checkboxMaps = WebUtils.getParametersStartingWith(
                request, "qu_"+QuType.CHECKBOX + "_");//多选
        Map<String, Object> fillblankMaps = WebUtils.getParametersStartingWith(
                request, "qu_" + QuType.FILLBLANK + "_");//填空
        quMaps.put("fillblankMaps", fillblankMaps);
        Map<String, Object> dfillblankMaps = WebUtils
                .getParametersStartingWith(request, "qu_"
                        + QuType.MULTIFILLBLANK + "_");//多项填空
        for (String key : dfillblankMaps.keySet()) {
            String dfillValue = dfillblankMaps.get(key).toString();
            Map<String, Object> map = WebUtils.getParametersStartingWith(
                    request, dfillValue);
            dfillblankMaps.put(key, map);
        }
        quMaps.put("multifillblankMaps", dfillblankMaps);
        Map<String, Object> answerMaps = WebUtils.getParametersStartingWith(
                request, "qu_" + QuType.ANSWER + "_");//多行填空
        quMaps.put("answerMaps", answerMaps);
        Map<String, Object> compRadioMaps = WebUtils.getParametersStartingWith(
                request, "qu_" + QuType.COMPRADIO + "_");//复合单选
        for (String key : compRadioMaps.keySet()) {
            Long enId = Long.valueOf(key);
            Long quItemId = Long.valueOf(compRadioMaps.get(key).toString());
            String otherText = request.getParameter("text_qu_"
                    + QuType.COMPRADIO + "_" + enId + "_" + quItemId);
            AnRadio anRadio = new AnRadio();
            anRadio.setQuId(enId);
            anRadio.setQuItemId(quItemId);
            anRadio.setOtherText(otherText);
            compRadioMaps.put(key, anRadio);
        }
        quMaps.put("compRadioMaps", compRadioMaps);
        for (String key:radioMaps.keySet()) {
            Long enId = Long.valueOf(key);
            Long quItemId = Long.valueOf(radioMaps.get(key).toString());
            String otherText = request.getParameter("text_qu_"
                    + QuType.RADIO + "_" + enId + "_" + quItemId);
            AnRadio anRadio = new AnRadio();
            anRadio.setQuId(enId);
            anRadio.setQuItemId(quItemId);
            anRadio.setOtherText(otherText);
            radioMaps.put(key, anRadio);
        }
        quMaps.put("compRadioMaps", radioMaps);
        Map<String, Object> compCheckboxMaps = WebUtils
                .getParametersStartingWith(request, "qu_" + QuType.COMPCHECKBOX
                        + "_");//复合多选
        for (String key : compCheckboxMaps.keySet()) {
            String dfillValue = compCheckboxMaps.get(key).toString();
            Map<String, Object> map = WebUtils.getParametersStartingWith(
                    request, "tag_" + dfillValue);
            for (String key2 : map.keySet()) {
                Long quItemId = Long.valueOf(map.get(key2).toString());
                String otherText = request.getParameter("text_"
                        + dfillValue + quItemId);
                AnCheckbox anCheckbox = new AnCheckbox();
                anCheckbox.setQuItemId(quItemId);
                anCheckbox.setOtherText(otherText);
                map.put(key2, anCheckbox);
            }
            compCheckboxMaps.put(key, map);
        }
        quMaps.put("compCheckboxMaps", compCheckboxMaps);
        for (String key : checkboxMaps.keySet()) {
            String dfillValue = checkboxMaps.get(key).toString();
            Map<String, Object> map = WebUtils.getParametersStartingWith(
                    request, dfillValue);
            for (String key2 : map.keySet()) {
                Long quItemId = Long.valueOf(map.get(key2).toString());
                String otherText = request.getParameter("text_"
                        + dfillValue + quItemId);
                AnCheckbox anCheckbox = new AnCheckbox();
                anCheckbox.setQuItemId(quItemId);
                anCheckbox.setOtherText(otherText);
                map.put(key2, anCheckbox);
            }
            checkboxMaps.put(key, map);
        }
        quMaps.put("compCheckboxMaps", checkboxMaps);
        return quMaps;
    }

}
