package com.boot.controller.weixin;

import com.boot.controller.system.BaseController;
import com.boot.model.Student;
import com.boot.util.AjaxResult;
import com.boot.util.DictionaryType;
import com.boot.util.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
@RequestMapping("/weixin")
public class WxLoginController extends BaseController {

    public static final String BASE_PATH = "/weixin";

    @GetMapping("/login")
    public String login(HttpServletResponse httpServletResponse) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            try {
                Integer userTypeId = ShiroUtils.getInstence().getUser().getUserTypeId();
                if (Integer.valueOf(DictionaryType.学生.getValue().toString()) == userTypeId) {
                    httpServletResponse.sendRedirect("/weixin/student");
                } else if (Integer.valueOf(DictionaryType.单位.getValue().toString()) == userTypeId) {
                    httpServletResponse.sendRedirect("/weixin/company");
                } else {
                    httpServletResponse.sendRedirect("/weixin/system");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return BASE_PATH + "/login";
    }

    @ResponseBody
    @PostMapping("/login")
    public AjaxResult login(HttpServletRequest httpServletRequest) {
        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");
        UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray());
        token.setRememberMe(true);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            return error("账号不存在");
        } catch (IncorrectCredentialsException e) {
            return error("密码错误");
        } catch (UnsupportedTokenException e) {
            return error("身份认证异常");
        } catch (LockedAccountException e) {
            return error("账号锁定");
        } catch (DisabledAccountException e) {
            return error("用户禁用");
        } catch (AccountException e) {
            return error("账号异常");
        } catch (UnauthorizedException e) {
            return error("权限异常");
        } catch (UnauthenticatedException e) {
            return error("授权异常");
        }
        return success("登陆成功");
    }

    @RequestMapping("/loginOut")
    public String loginOut() {
        SecurityUtils.getSubject().logout();
        return "/weixin/login";
    }

    @RequestMapping("/system")
    public String system(HttpServletResponse httpServletResponse) {
        return BASE_PATH + "/t-main";
    }
    @RequestMapping("/company")
    public String company(HttpServletResponse httpServletResponse) {
        return BASE_PATH + "/c-main";
    }
    @RequestMapping("/student")
    public String student(ModelMap modelMap) {
        Student student = sqlManager.query(Student.class)
                .andEq("StudentNumber",
                        ShiroUtils.getInstence().getUser().getUserName()).single();
        modelMap.put("student", student);
        return BASE_PATH + "/x-main";
    }
}
