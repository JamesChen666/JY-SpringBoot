package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import com.boot.controller.system.BaseController;
import com.boot.model.Config;
import com.boot.util.AjaxResult;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.PanelUI;
import java.util.*;

import net.sf.json.JSONArray;

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
        Object cs = request.getParameter("cs");
        JSONArray array = JSONArray.fromObject(cs);
        List list =(ArrayList)JSONArray.toCollection(array, HashMap.class);
        boolean isOk = false;
           for (int i = 0; i < list.size(); i++) {
               Map map = (Map) list.get(i);
               Config model = new Config();
               model.setId(Integer.parseInt(map.get("Id")+""));
               model.setParameterValue(map.get("ParameterValue")+"");
               model.setParameterKey(map.get("ParameterKey")+"");
                if(model.getParameterKey() == null || "".equals(model.getParameterKey())){
                    Config config = sqlManager.single(Config.class,model.getId());
                    model.setParameterKey(config.getParameterKey());
                }
                if(model.getParameterValue() == null || "".equals(model.getParameterValue())){
                    Config config = sqlManager.single(Config.class,model.getId());
                    model.setParameterValue(config.getParameterValue());
                }
                isOk = sqlManager.updateById(model) > 0;
            }
        return isOk?success(SUCCESS):fail(FAIL);
    }


    /**
     * 查询出当前毕业年份
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryGraduationYear")
    public Object queryGraduationYear() {
       Map map = sqlManager.selectSingle("config.findKey",Dict.create().set("key","当前毕业年份"),Map.class);
        return map;
    }
}
