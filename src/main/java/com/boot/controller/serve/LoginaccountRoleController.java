package com.boot.controller.serve;

import com.boot.controller.system.BaseController;
import com.boot.model.LoginaccountRole;
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
@RequestMapping("/loginaccountRole")
public class LoginaccountRoleController extends BaseController{

    private static final String BASE_PATH = "loginaccountRole";
    private static final String LIST = "loginaccountRole.list";

    @RequestMapping("/")
	public String index() {
		return BASE_PATH+"/list";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list() {
    List<LoginaccountRole> list = sqlManager.select(LIST, LoginaccountRole.class);
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
    LoginaccountRole LoginaccountRole = sqlManager.single(LoginaccountRole.class, request.getParameter("id"));
    return LoginaccountRole;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public AjaxResult delete(HttpServletRequest request) {
    int deleteById = sqlManager.deleteById(LoginaccountRole.class, request.getParameter("id"));
    if (deleteById<=0) {
    return fail("删除失败");
    }
    return success("删除成功");
    }

}
