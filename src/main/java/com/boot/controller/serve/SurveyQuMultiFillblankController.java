package com.boot.controller.serve;

import com.boot.controller.system.BaseController;
import com.boot.model.survey.QuMultiFillblank;
import com.boot.model.survey.QuRadio;
import com.boot.model.survey.Question;
import com.boot.model.survey.QuestionLogic;
import com.boot.model.survey.enums.CheckType;
import com.boot.model.survey.enums.QuType;
import com.boot.service.survey.*;
import com.boot.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 多行填空
 * @author mrren
 */
@Controller
@RequestMapping("/survey_qu_multifillblank")
public class SurveyQuMultiFillblankController extends BaseController{

    private static final String BASE_PATH = "survey";
    private static final String LIST = "survey.list";

    @Autowired
    SurveyDirectoryService surveyDirectoryService;

    @Autowired
    QuestionService questionService;

    @Autowired
    QuMultiFillblankService quMultiFillblankService;

    @Autowired
    QuestionLogicService questionLogicService;

    @Autowired
    SurveyStyleService surveyStyleService;

    @Autowired
    SurveyDetailService surveyDetailService;



    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request, HttpServletResponse response) {
        try{
            Question entity=ajaxBuildSaveOption(request);
            questionService.update( entity);

            String resultJson=buildResultJson(entity);
            return success("保存成功",resultJson);
        }catch (Exception e) {
            e.getStackTrace();
            return fail("保存失败");
        }
    }

    private Question ajaxBuildSaveOption(HttpServletRequest request) throws UnsupportedEncodingException {
        Object quId=request.getParameter("quId");
        Object belongId=request.getParameter("belongId");
        String quTitle=request.getParameter("quTitle");
        String orderById=request.getParameter("orderById");
        String tag=request.getParameter("tag");
        String isRequired=request.getParameter("isRequired");
        //hv 1水平显示 2垂直显示
        String hv=request.getParameter("hv");
        //randOrder 选项随机排列
        String randOrder=request.getParameter("randOrder");
        String cellCount=request.getParameter("cellCount");
        String paramInt01=request.getParameter("paramInt01");//最小分
        String paramInt02=request.getParameter("paramInt02");//最大分

        if (belongId == null || "".equals(belongId)) {
            return null;
        }
        Question entity= new Question();
        if(quId != null && !"".equals(quId)){
            entity=questionService.findOneById(Long.valueOf(quId.toString()));
        } else {
            questionService.save(entity);
        }
        entity.setBelongId(Long.valueOf(belongId.toString()));
        if(quTitle!=null){
            quTitle=URLDecoder.decode(quTitle,"utf-8");
            entity.setQuTitle(quTitle);
        }
        entity.setOrderById(Integer.parseInt(orderById));
        entity.setTag(Integer.parseInt(tag));
        entity.setQuType(QuType.MULTIFILLBLANK);
        //参数
        isRequired=(isRequired==null || "".equals(isRequired))?"0":isRequired;
        hv=(hv==null || "".equals(hv))?"0":hv;
        randOrder=(randOrder==null || "".equals(randOrder))?"0":randOrder;
        cellCount=(cellCount==null || "".equals(cellCount))?"0":cellCount;
        paramInt01=(paramInt01==null || "".equals(paramInt01))?"0":paramInt01;
        entity.setIsRequired(Integer.parseInt(isRequired));
        entity.setHv(Integer.parseInt(hv));
        entity.setRandOrder(Integer.parseInt(randOrder));
        entity.setCellCount(Integer.parseInt(cellCount));
        entity.setParamInt01(Integer.parseInt(paramInt01));
        entity.setParamInt02(10);
        Map<String, Object> optionNameMap=WebUtils.getParametersStartingWith(request, "optionValue_");
        List<QuMultiFillblank> quMFillblanks=new ArrayList<QuMultiFillblank>();
        for (String key : optionNameMap.keySet()) {
            //String optionId=request.getParameter("optionId_"+key);
            Object optionName=optionNameMap.get(key);
            String optionNameValue=(optionName!=null)?optionName.toString():"";
            QuMultiFillblank quMultiFillblank=new QuMultiFillblank();
            optionNameValue=URLDecoder.decode(optionNameValue,"utf-8");
            quMultiFillblank.setOptionName(optionNameValue);
            quMultiFillblank.setOrderById(Integer.parseInt(key));
            quMultiFillblank.setQuId(entity.getId());

            quMFillblanks.add(quMultiFillblank);
            quMultiFillblankService.save(quMultiFillblank);
        }
        entity.setQuMultiFillblanks(quMFillblanks);

        //逻辑选项设置
        Map<String, Object> quLogicIdMap=WebUtils.getParametersStartingWith(request, "quLogicId_");
        List<QuestionLogic> quLogics=new ArrayList<QuestionLogic>();
        for (String key : quLogicIdMap.keySet()) {
            Object cgQuItemId=request.getParameter("cgQuItemId_"+key);
            String skQuId=request.getParameter("skQuId_"+key);
            String visibility=request.getParameter("visibility_"+key);
            String logicType=request.getParameter("logicType_"+key);
            Object quLogicId=quLogicIdMap.get(key);
            String quLogicIdValue=(quLogicId!=null)?quLogicId.toString():null;

            QuestionLogic quLogic=new QuestionLogic();
            quLogic.setCgQuItemId(Long.valueOf(cgQuItemId.toString()));
            quLogic.setSkQuId(skQuId);
            quLogic.setVisibility(Integer.parseInt(visibility));
            quLogic.setTitle(key);
            quLogic.setLogicType(logicType);
            quLogics.add(quLogic);
        }
        entity.setQuestionLogics(quLogics);

        return entity;
    }

    public static String buildResultJson(Question entity){
        StringBuffer strBuf=new StringBuffer();
        strBuf.append("{id:'").append(entity.getId());
        strBuf.append("',orderById:");
        strBuf.append(entity.getOrderById());
        strBuf.append(",quItems:[");
        List<QuMultiFillblank> quMultiFillblanks=entity.getQuMultiFillblanks();
        for (QuMultiFillblank quMultiFillblank : quMultiFillblanks) {
            strBuf.append("{id:'").append(quMultiFillblank.getId());
            strBuf.append("',title:'").append(quMultiFillblank.getOrderById()).append("'},");
        }
        int strLen=strBuf.length();
        if(strBuf.lastIndexOf(",")==(strLen-1)){
            strBuf.replace(strLen-1, strLen, "");
        }
        strBuf.append("]");

        strBuf.append(",quLogics:[");
        List<QuestionLogic> questionLogics=entity.getQuestionLogics();
        if(questionLogics!=null){
            for (QuestionLogic questionLogic : questionLogics) {
                strBuf.append("{id:'").append(questionLogic.getId());
                strBuf.append("',title:'").append(questionLogic.getTitle()).append("'},");
            }
        }
        strLen=strBuf.length();
        if(strBuf.lastIndexOf(",")==(strLen-1)){
            strBuf.replace(strLen-1, strLen, "");
        }
        strBuf.append("]}");
        return strBuf.toString();
    }


    /**
     * 删除选项
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/delete")
    public AjaxResult delete(HttpServletRequest request) {
        try{
            Object quItemId=request.getParameter("quItemId");
            if (quItemId == null) {
                return fail("id不能为空");
            }
            quMultiFillblankService.delete(Long.valueOf(quItemId.toString()));
            return success("删除成功");
        }catch(Exception e){
            e.printStackTrace();
            return fail("删除失败");
        }
    }


}
