package com.boot.controller.system;

import cn.hutool.core.lang.Dict;
import com.boot.model.Corp;
import com.boot.model.Loginaccount;
import com.boot.model.Menu;
import com.boot.system.SqlIntercepter;
import com.boot.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author chenjiang
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    JavaMailSender jms;
    @Autowired
    MailConfig mailConfig;

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
                    httpServletResponse.sendRedirect("company");
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

    /**
     * 单位注册
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @PostMapping("/corpRegister")
    public AjaxResult corpRegister(HttpServletRequest httpServletRequest) {
        Corp model = mapping(Corp.class, httpServletRequest);
        String verifyCode = (String) httpServletRequest.getSession().getAttribute("verifyCode");
        String parameter = httpServletRequest.getParameter("verifyCode");
        if (!verifyCode.equals(parameter)) {
            return error("验证码错误应输入" + verifyCode);
        }
        //判断该单位是否已存在
        Corp corp = sqlManager.selectSingle("corp.findCorpName",Dict.create().set("corpName",model.getCorpName()),Corp.class);
        if(corp != null){
           return fail("该单位已存在");
        }
        //添加单位登录信息
        Loginaccount loginaccount = mapping(Loginaccount.class, httpServletRequest);
        loginaccount.setRealName(model.getContactor());
        loginaccount.setIsEnabled(false);
        loginaccount.setUserTypeId(4);
        loginaccount.setCreateDate(new Date());
        loginaccount.setUserName(model.getCorpName());
        //String salt = Md5Util.getInstance().getSalt();
        //生成6位随机数密码
        String passWord = random();
        String md5 = Md5Util.getInstance().MD5(passWord, passWord);
        loginaccount.setPassWord(md5);
        loginaccount.setSalt(passWord);
        boolean isOk = sqlManager.insert(loginaccount)>0;
        if(isOk){
            Loginaccount login = sqlManager.selectSingle("loginaccount.findByUserName",Dict.create().
                    set("UserName",loginaccount.getUserName()),Loginaccount.class);
            if(model.getStatus() == null || model.getStatus() == 0){
                model.setStatus(0);
            }else{
                model.setExamineDate(new Date());
                model.setExamineUserId(ShiroUtils.getInstence().getUser().getId());
            }
            model.setUserId(login.getId());
            isOk = sqlManager.insert(model)>0;
        }
        if(isOk){
            try {
                //注册成功发送邮件
                MailSending mailSending = new MailSending();
                mailSending.send(mailConfig,jms,model.getEmail(),"四川师范大学就业系统注册通知",
                        "您好，您注册的单位信息已提交，请等待管理员审批！您的登录名称为单位名称，登录密码为："+passWord);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return  isOk? success("信息提交成功，请等待管理员审核"):fail("单位注册失败，请重新提交");
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
        //查询出学生信息
        SqlIntercepter sqlIntercepter = new SqlIntercepter();
        sqlIntercepter.setAppendSql("WHERE StudentNumber = #{userName}");
        List<Map> userName = appendToList("student.list", sqlIntercepter,
                Dict.create().set("userName", ShiroUtils.getInstence().getUser().getUserName()));
        //查询出当前毕业年份
        /*String yea = sqlManager.selectSingle("config.findKey",Dict.create().set("key","当前毕业年份"),String.class);
        ModelMap.put("GraduationYear",yea);*/
        ModelMap.put("student",userName.get(0));
        return "/studentEnd/stu-center";
    }

    @RequestMapping("/company")
    public String company(ModelMap modelMap) {
        //查询出单位信息
        Corp corp = sqlManager.selectSingle("corp.findCorpName",Dict.create().set("corpName",
                ShiroUtils.getInstence().getUser().getUserName()),Corp.class);
        modelMap.put("corp",corp);
        //判断是否需要完善资料,1:不需要，2：需要
        modelMap.put("type",corp.getOrganizetionCode()!=null? "1":"2");
        //查询出已发布有效职位数
        Integer postionCount = sqlManager.selectSingle("postion.postionCount",Dict.create().set("corpId",corp.getId()),Integer.class);
        //查询出单位被收藏数
        Integer storeCount = sqlManager.selectSingle("store.storeCount",Dict.create().set("corpId",corp.getId()),Integer.class);
        //查询出简历投递数
        Integer resumeCount = sqlManager.selectSingle("resumePost.resumeCount",Dict.create().set("corpId",corp.getId()),Integer.class);
        //查询出来校次数
        Integer toSchoolCount = sqlManager.selectSingle("recruit.toSchoolCount",Dict.create().set("corpId",corp.getId()),Integer.class);
        //查询出自行联系职位数
        Integer contactCount = sqlManager.selectSingle("recruit.contactCount",Dict.create().set("corpId",corp.getId()),Integer.class);
        modelMap.put("postionCount",postionCount);
        modelMap.put("storeCount",storeCount);
        modelMap.put("resumeCount",resumeCount);
        modelMap.put("toSchoolCount",toSchoolCount);
        modelMap.put("contactCount",contactCount);
        return "/company/com-center";
    }

    @RequestMapping("/loginOut")
    public String loginOut() {
        SecurityUtils.getSubject().logout();
        return "/login";
    }

    public String random(){
        StringBuffer flag = new StringBuffer();
            String sources = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; // 加上一些字母，就可以生成pc站的验证码了
            Random rand = new Random();
            for (int j = 0; j < 6; j++){
                flag.append(sources.charAt(rand.nextInt(61)) + "");
            }
        return flag.toString();
    }
}
