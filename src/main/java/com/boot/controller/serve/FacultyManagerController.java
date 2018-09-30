package com.boot.controller.serve;

import com.boot.controller.system.BaseController;
import com.boot.model.FacultyManager;
import com.boot.util.AjaxResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * @author chenjiang
 */
@Controller
@RequestMapping("/facultyManager")
public class FacultyManagerController extends BaseController{

    private static final String BASE_PATH = "facultyManager";
    private static final String LIST = "facultyManager.list";

    @RequestMapping("/")
	public String index() {
		return BASE_PATH+"/list";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list() {
    List<FacultyManager> list = sqlManager.select(LIST, FacultyManager.class);
    return list;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        return null;
    }

    @ResponseBody
    @RequestMapping("/edit")
    public Object edit(HttpServletRequest request) {
    FacultyManager FacultyManager = sqlManager.single(FacultyManager.class, request.getParameter("id"));
    return FacultyManager;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public AjaxResult delete(HttpServletRequest request) {
    int deleteById = sqlManager.deleteById(FacultyManager.class, request.getParameter("id"));
    if (deleteById<=0) {
    return fail("删除失败");
    }
    return success("删除成功");
    }

}
