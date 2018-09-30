package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import com.boot.controller.system.BaseController;
import com.boot.model.SignLog;
import com.boot.util.AjaxResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author chenjiang
 */
@Controller
@RequestMapping("/signLog")
public class SignLogController extends BaseController{

    private static final String BASE_PATH = "signLog";
    private static final String LIST = "signLog.list";

    @RequestMapping("/")
	public String index() {
		return BASE_PATH+"/signLog_list";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST,httpServletRequest);
        return map;
    }

    @RequestMapping("/view/{id}")
    public String view(@PathVariable Integer id, ModelMap modelMap) {
        Map map = sqlManager.selectSingle("signlog.findOne",
                Dict.create().set("Id", id), Map.class);
        modelMap.put("signlog", map);
        SignLog single = sqlManager.single(SignLog.class, id);
        Map student = sqlManager.selectSingle("student.findSimpleStudentMessage",
                Dict.create().set("Id", single.getStudentId()), Map.class);
        modelMap.put("student", student);
        return BASE_PATH + "/signlog_view";
    }

    @ResponseBody
    @RequestMapping("/export")
    public AjaxResult exportExcel(HttpServletRequest httpServletRequest) {
        String ids = httpServletRequest.getParameter("ids");
        List<SignLog> mapList;
        if (ids == null || ids.isEmpty()) {
            mapList = sqlManager.all(SignLog.class);
        } else {
            mapList = selectByIds(SignLog.class, ids);
        }
        try {
            exportExcel("SignLog", mapList,SignLog.class);
        } catch (Exception e) {
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

}
