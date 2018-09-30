package com.boot.controller.serve;

import com.boot.controller.system.BaseController;
import com.boot.model.Config;
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
@RequestMapping("/config")
public class ConfigController extends BaseController{

    private static final String BASE_PATH = "config";
    private static final String LIST = "config.list";

    @RequestMapping("/")
    public String index() {
        return BASE_PATH+"/system_config";
    }

    @ResponseBody
    @RequestMapping("/list")
    public Object list() {
        List<Map> mapList = sqlManager.select(LIST, Map.class);
        return mapList;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        Config model = mapping(Config.class, request);
        int result = sqlManager.insert(model);
        if (result > 0) {
            return success(SUCCESS);
        } else {
            return fail(FAIL);
        }
    }
}
