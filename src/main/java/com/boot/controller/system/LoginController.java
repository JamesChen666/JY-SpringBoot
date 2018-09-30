package com.boot.controller.system;

import cn.hutool.core.lang.Dict;
import com.boot.model.Menu;
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
import java.util.List;

/**
 * @author chenjiang
 */
@Controller
public class LoginController extends BaseController {

    @RequestMapping("/")
    public String home() {
        return "/home";
    }

    @GetMapping("/login")
    public String login(HttpServletResponse httpServletResponse) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()){
            try {
                Integer userTypeId = ShiroUtils.getInstence().getUser().getUserTypeId();
                if (Integer.valueOf(DictionaryType.学生.getValue().toString())==userTypeId){
                    httpServletResponse.sendRedirect("student");
                }else if (Integer.valueOf(DictionaryType.单位.getValue().toString())==userTypeId){
                    httpServletResponse.sendRedirect("unit");
                }else {
                    httpServletResponse.sendRedirect("index");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "/login";
    }

    /**
     * 注册
     * @return 跳转到注册页面
     */
    @RequestMapping("/register")
    public String register() {
        return "/register";
    }

    @ResponseBody
    @PostMapping("/login")
    public AjaxResult login(HttpServletRequest httpServletRequest) {
        String verifyCode = (String) httpServletRequest.getSession().getAttribute("verifyCode");
        String parameter = httpServletRequest.getParameter("verifyCode");
        if (!verifyCode.equals(parameter)) {
            return error("验证码错误应输入" + verifyCode);
        }
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


    @RequestMapping("/index")
    public String index(ModelMap ModelMap) {
        List<Menu> menuList = sqlManager.select("shiro.findUserMenu", Menu.class,
                Dict.create().set("UserName", ShiroUtils.getInstence().getUser().getUserName()));
        ModelMap.put("menus", menuList);
        ModelMap.put("user", ShiroUtils.getInstence().getUser().getRealName());
        ModelMap.put("role", ShiroUtils.getInstence().getRole().getRoleName());
        return "/Index";
    }

    @RequestMapping("/student")
    public String student(ModelMap ModelMap) {
        ModelMap.put("user", ShiroUtils.getInstence().getUser().getRealName());
        return "/student";
    }

    @RequestMapping("/loginOut")
    public String loginOut() {
        SecurityUtils.getSubject().logout();
        return "/login";
    }
}
