package com.boot.controller.serve;

import com.boot.controller.system.BaseController;
import com.boot.service.survey.QuestionService;
import com.boot.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 问题
 * @author mrren
 */
@Controller
@RequestMapping("/survey_question")
public class SurveyQuestionController extends BaseController{

    @Autowired
    QuestionService questionService;
    /**
     * 删除选项
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping(value = "/delete")
    public AjaxResult delete(HttpServletRequest request) {
        try{
            Object delQuId=request.getParameter("quId");
            if (delQuId == null || "".equals(delQuId)) {
                return error("问题id为空");
            } else {
                questionService.delete(Long.valueOf(delQuId.toString()));
                return success("删除成功");
            }
        }catch (Exception e) {
            return success("删除失败");
        }
    }
}
