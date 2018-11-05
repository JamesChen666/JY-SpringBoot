package com.boot.controller.serve;

import com.boot.controller.system.BaseController;
import com.boot.model.survey.QuCheckbox;
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
 * 多选
 * @author mrren
 */
@Controller
@RequestMapping("/survey_qu_checkbox")
public class SurveyQuCheckboxController extends BaseController{

    private static final String BASE_PATH = "survey";
    private static final String LIST = "survey.list";

    @Autowired
    SurveyDirectoryService surveyDirectoryService;

    @Autowired
    QuestionService questionService;

    @Autowired
    QuCheckboxService quCheckboxService;

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
        String hv=request.getParameter("hv");
        String randOrder=request.getParameter("randOrder");
        String cellCount=request.getParameter("cellCount");
        String contactsAttr=request.getParameter("contactsAttr");
        String contactsField=request.getParameter("contactsField");
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
        entity.setQuType(QuType.CHECKBOX);
        isRequired=(isRequired==null || "".equals(isRequired))?"0":isRequired;
        hv=(hv==null || "".equals(hv))?"0":hv;
        randOrder=(randOrder==null || "".equals(randOrder))?"0":randOrder;
        cellCount=(cellCount==null || "".equals(cellCount))?"0":cellCount;
        contactsAttr=(contactsAttr==null || "".equals(contactsAttr))?"0":contactsAttr;
        entity.setContactsAttr(Integer.parseInt(contactsAttr));
        entity.setContactsField(contactsField);
        entity.setIsRequired(Integer.parseInt(isRequired));
        entity.setHv(Integer.parseInt(hv));
        entity.setRandOrder(Integer.parseInt(randOrder));
        entity.setCellCount(Integer.parseInt(cellCount));
        Map<String, Object> optionNameMap=WebUtils.getParametersStartingWith(request, "optionValue_");
        List<QuCheckbox> quCheckboxs=new ArrayList<QuCheckbox>();
        for (String key : optionNameMap.keySet()) {
            String isNote=request.getParameter("isNote_"+key);
            String checkType=request.getParameter("checkType_"+key);
            String isRequiredFill=request.getParameter("isRequiredFill_"+key);

            Object optionName=optionNameMap.get(key);
            String optionNameValue=(optionName!=null)?optionName.toString():"";
            QuCheckbox quCheckbox=new QuCheckbox();
//			quRadio.setOptionTitle(key);
            optionNameValue=URLDecoder.decode(optionNameValue,"utf-8");
            quCheckbox.setOptionName(optionNameValue);
            quCheckbox.setOrderById(Integer.parseInt(key));
            isNote=(isNote==null || "".equals(isNote))?"0":isNote;
            checkType=(checkType==null || "".equals(checkType))?"NO":checkType;
            isRequiredFill=(isRequiredFill==null || "".equals(isRequiredFill))?"0":isRequiredFill;
            quCheckbox.setIsNote(Integer.parseInt(isNote));
            quCheckbox.setCheckType(CheckType.valueOf(checkType));
            quCheckbox.setIsRequiredFill(Integer.parseInt(isRequiredFill));
            quCheckbox.setQuId(entity.getId());
            quCheckboxs.add(quCheckbox);
            quCheckboxService.save(quCheckbox);

        }
        entity.setQuCheckboxs(quCheckboxs);
        Map<String, Object> quLogicIdMap=WebUtils.getParametersStartingWith(request, "quLogicId_");
        List<QuestionLogic> quLogics=new ArrayList<QuestionLogic>();
        for (String key : quLogicIdMap.keySet()) {
            Object cgQuItemId=request.getParameter("cgQuItemId_"+key);
            String skQuId=request.getParameter("skQuId_"+key);
            String visibility=request.getParameter("visibility_"+key);
            String logicType=request.getParameter("logicType_"+key);
            Object quLogicId=quLogicIdMap.get(key);
            Long quLogicIdValue=(quLogicId!=null)?Long.valueOf(quLogicId.toString()):-1l;
            QuestionLogic quLogic=new QuestionLogic();
            quLogic.setCgQuItemId(Long.valueOf(cgQuItemId.toString()));
            quLogic.setSkQuId(skQuId);
            quLogic.setVisibility(Integer.parseInt(visibility));
            quLogic.setTitle(key);
            quLogic.setLogicType(logicType);
            quLogics.add(quLogic);
            questionLogicService.save(quLogic);
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
        List<QuCheckbox> quCheckboxs=entity.getQuCheckboxs();
        for (QuCheckbox quCheckbox : quCheckboxs) {
            strBuf.append("{id:'").append(quCheckbox.getId());
            strBuf.append("',title:'").append(quCheckbox.getOrderById()).append("'},");
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
            quCheckboxService.delete(Long.valueOf(quItemId.toString()));
            return success("删除成功");
        }catch(Exception e){
            e.printStackTrace();
            return fail("删除失败");
        }
    }
}
