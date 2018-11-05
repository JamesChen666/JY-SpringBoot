package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.boot.controller.system.BaseController;
import com.boot.model.Feedback;
import com.boot.model.Student;
import com.boot.util.AjaxResult;
import com.boot.util.ShiroUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author chenjiang
 */
@Controller
@RequestMapping("/feedback")
public class FeedbackController extends BaseController{

    private static final String BASE_PATH = "feedback";
    private static final String LIST = "feedback.list";

    @RequestMapping("/")
    public String index() {
        return BASE_PATH+"/feedback_list";
    }

    @ResponseBody
    @RequestMapping("/list")
    public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST,httpServletRequest);
        return map;
    }

    @ResponseBody
    @RequestMapping("/myList")
    public Object myList() {
        List<Map> mapList = sqlManager.select("feedback.myList", Map.class,
                Dict.create().set("StudentNumber",
                        ShiroUtils.getInstence().getUser().getUserName()));
        return mapList;
    }
    @ResponseBody
    @RequestMapping("/searchList")
    public Object searchList(HttpServletRequest httpServletRequest) {
        List<Map> mapList = sqlManager.select("feedback.searchList", Map.class,
                Dict.create().set("StudentNumber", ShiroUtils.getInstence().getUser().getUserName())
                                .set("search", httpServletRequest.getParameter("content")));
        return mapList;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest httpServletRequest) {
        String title = httpServletRequest.getParameter("Title");
        String content = httpServletRequest.getParameter("Content");
        String contact = httpServletRequest.getParameter("Contact");
        if (StrUtil.isEmpty(title)||StrUtil.isEmpty(content)){
            return error("留言标题和内容不能为空");
        }
        String userName = ShiroUtils.getInstence().getUser().getUserName();
        Student student = sqlManager.query(Student.class)
                .andEq("StudentNumber", userName).single();
        Feedback feedback = new Feedback();
        feedback.setStudentId(student.getId());
        feedback.setTitle(title);
        feedback.setContent(content);
        feedback.setCreateDate(new Date());
        feedback.setContactPhone(contact);
        feedback.setIsReply(false);
        int insert = sqlManager.insert(feedback);
        if (insert<=0){
            return fail("留言失败");
        }
        return success("留言成功");
    }

    @ResponseBody
    @RequestMapping("/reply")
    public Object reply(HttpServletRequest httpServletRequest) {
        String ids = httpServletRequest.getParameter("ids");
        String rowDatas = httpServletRequest.getParameter("data");
        int insert=0;
        Feedback single = sqlManager.single(Feedback.class, ids);
        if (StrUtil.isNotEmpty(single.getReplyContent())){
            single.setReplyContent(rowDatas);
            single.setReplyDate(new Date());
            single.setIsReply(true);
             insert += sqlManager.insert(single);
        }
        single.setReplyContent(rowDatas);
        single.setReplyDate(new Date());
        sqlManager.updateById(single);
        single.setIsReply(true);
        insert +=sqlManager.updateById(single);
        if (insert<=0){
            return fail("回复失败");
        }
        return success(SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/export")
    public AjaxResult exportExcel(HttpServletRequest httpServletRequest) {
        String ids = httpServletRequest.getParameter("ids");
        List<Feedback> mapList;
        if (ids == null||ids.isEmpty()) {
            mapList = sqlManager.all(Feedback.class);
        }else {
            mapList = selectByIds(Feedback.class,ids);
        }
        try {
            exportExcel("学生留言信息", mapList ,Feedback.class);
        }catch (Exception e){
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

}
