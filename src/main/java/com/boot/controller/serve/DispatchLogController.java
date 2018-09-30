package com.boot.controller.serve;

import com.boot.controller.system.BaseController;
import com.boot.model.DispatchLog;
import com.boot.util.AjaxResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author chenjiang
 */
@Controller
@RequestMapping("/dispatchLog")
public class DispatchLogController extends BaseController{

    private static final String BASE_PATH = "dispatchLog";
    private static final String LIST = "dispatchLog.list";

    @RequestMapping("/")
	public String index() {
		return BASE_PATH+"/dispatchLog_list";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST,httpServletRequest);
        return map;
    }

    @ResponseBody
    @RequestMapping("/export")
    public AjaxResult exportExcel(HttpServletRequest httpServletRequest) {
        String ids = httpServletRequest.getParameter("ids");
        List<DispatchLog> mapList;
        if (ids == null || ids.isEmpty()) {
            mapList = sqlManager.all(DispatchLog.class);
        } else {
            mapList = selectByIds(DispatchLog.class, ids);
        }
        try {
            exportExcel("DispatchLog", mapList,DispatchLog.class);
        } catch (Exception e) {
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

}
