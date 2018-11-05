package com.boot.controller.serve;

import com.boot.controller.system.BaseController;
import com.boot.model.Confirm;
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
@RequestMapping("/confirm")
public class ConfirmController extends BaseController{

    private static final String BASE_PATH = "confirm";
    private static final String LIST = "confirm.list";

    @RequestMapping("/")
    public String index() {
        return BASE_PATH+"/confirm_list";
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
        List<Confirm> mapList;
        if (ids == null||ids.isEmpty()) {
            mapList = sqlManager.all(Confirm.class);
        }else {
            mapList = selectByIds(Confirm.class,ids);
        }
        try {
            exportExcel("学生信息确认", mapList ,Confirm.class);
        }catch (Exception e){
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

}
