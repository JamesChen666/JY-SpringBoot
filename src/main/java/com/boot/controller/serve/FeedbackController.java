package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.boot.controller.system.BaseController;
import com.boot.model.Feedback;
import com.boot.system.SqlIntercepter;
import com.boot.util.AjaxResult;
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
        List<Map> mapList;
        if (ids == null||ids.isEmpty()) {
            mapList = sqlManager.select("feedback.list",Map.class);
        }else {
            mapList = appendToList("feedback.list",
                    SqlIntercepter.create().set("WHERE FIND_IN_SET(Id,#{ids})"),
                    Dict.create().set("ids", ids));
        }
        try {
            simpleExport("Feedback", mapList );
        }catch (Exception e){
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

}
