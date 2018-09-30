package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import com.boot.controller.system.BaseController;
import com.boot.model.Examine;
import com.boot.system.SqlIntercepter;
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
@RequestMapping("/examine")
public class ExamineController extends BaseController {

    private static final String BASE_PATH = "examine";
    private static final String LIST = "examine.list";

    @RequestMapping("/")
    public String index() {
        return BASE_PATH + "/examine_list";
    }

    @ResponseBody
    @RequestMapping("/list")
    public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST, httpServletRequest);
        return map;
    }

    @ResponseBody
    @RequestMapping("/export")
    public AjaxResult exportExcel(HttpServletRequest httpServletRequest) {
        String ids = httpServletRequest.getParameter("ids");
        List<Map> mapList;
        if (ids == null || ids.isEmpty()) {
            mapList = sqlManager.select("examine.list", Map.class);
        } else {
            mapList = appendToList("examine.list",
                    SqlIntercepter.create().set("WHERE FIND_IN_SET(Id,#{ids})"),
                    Dict.create().set("ids", ids));
        }
        try {
            simpleExport("Examine", mapList);
        } catch (Exception e) {
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/audit")
    public AjaxResult audit(HttpServletRequest httpServletRequest) {
        String ids = httpServletRequest.getParameter("ids");
        Examine single = sqlManager.single(Examine.class, ids);
        if (single.getStatus() != 0) {
            return error("已审核数据不能再次审核!");
        }
        String status = httpServletRequest.getParameter("status");
        String rowDatas = httpServletRequest.getParameter("data");
        single.setStatus(Integer.valueOf(status));
        single.setApprovalUserId(ShiroUtils.getInstence().getUser().getId());
        single.setApprovalDate(new Date());
        single.setOpinion(rowDatas);
        int update = sqlManager.updateById(single);
        if (update <= 0) {
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

}
