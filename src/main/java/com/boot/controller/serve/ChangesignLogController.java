package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import com.boot.controller.system.BaseController;
import com.boot.model.ChangesignLog;
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
@RequestMapping("/changesignLog")
public class ChangesignLogController extends BaseController{

    private static final String BASE_PATH = "changesignLog";
    private static final String LIST = "changesignLog.list";

    @RequestMapping("/")
	public String index() {
		return BASE_PATH+"/changesignLog_list";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST,httpServletRequest);
        return map;
    }

    @RequestMapping("/view/{id}")
    public String view(@PathVariable Integer id, ModelMap modelMap) {
        Map map = sqlManager.selectSingle("changesignlog.findOne",
                Dict.create().set("Id", id), Map.class);
        modelMap.put("changesignlog", map);
        SignLog single = sqlManager.single(SignLog.class, id);
        Map student = sqlManager.selectSingle("student.findSimpleStudentMessage",
                Dict.create().set("Id", single.getStudentId()), Map.class);
        modelMap.put("student", student);
        return BASE_PATH + "/changesignlog_view";
    }

    @ResponseBody
    @RequestMapping("/export")
    public AjaxResult exportExcel(HttpServletRequest httpServletRequest) {
        String ids = httpServletRequest.getParameter("ids");
        List<ChangesignLog> mapList;
        if (ids == null || ids.isEmpty()) {
            mapList = sqlManager.all(ChangesignLog.class);
        } else {
            mapList = selectByIds(ChangesignLog.class, ids);
        }
        try {
            exportExcel("ChangesignLog", mapList,ChangesignLog.class);
        } catch (Exception e) {
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

}
