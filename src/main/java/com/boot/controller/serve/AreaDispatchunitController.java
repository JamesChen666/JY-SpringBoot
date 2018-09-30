package com.boot.controller.serve;

import com.boot.controller.system.BaseController;
import com.boot.model.AreaDispatchunit;
import com.boot.util.AjaxResult;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/areaDispatchunit")
public class AreaDispatchunitController extends BaseController{

    private static final String BASE_PATH = "areaDispatchunit";
    private static final String LIST = "areaDispatchunit.list";

    @RequestMapping("/")
	public String index() {
		return BASE_PATH+"/list";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list() {
    List<Map> list = sqlManager.select(LIST, Map.class);
    return list;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        AreaDispatchunit model = mapping(AreaDispatchunit.class, request);
        int result;
        if (model.getDispatchUnitId() == null) {
            result = sqlManager.insert(model);
        } else {
            result = sqlManager.updateById(model);
        }
        if (result > 0) {
            return success(SUCCESS);
        } else {
            return fail(FAIL);
        }
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
    AreaDispatchunit AreaDispatchunit = sqlManager.single(AreaDispatchunit.class,id);
    return AreaDispatchunit;
    }

    @ResponseBody
    @RequestMapping("/delete/{id}")
    public AjaxResult delete(@PathVariable Integer id) {
    int deleteById = sqlManager.deleteById(AreaDispatchunit.class,id);
    if (deleteById<=0) {
    return fail("删除失败");
    }
    return success("删除成功");
    }

}
