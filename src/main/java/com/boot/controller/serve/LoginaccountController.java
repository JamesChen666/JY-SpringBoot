package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import com.boot.controller.system.BaseController;
import com.boot.model.Loginaccount;
import com.boot.model.Role;
import com.boot.model.Student;
import com.boot.util.AjaxResult;
import com.boot.util.Md5Util;
import com.boot.util.ShiroUtils;
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
@RequestMapping("/loginaccount")
public class LoginaccountController extends BaseController {

    private static final String BASE_PATH = "loginaccount";
    private static final String LIST = "loginaccount.list";

    @RequestMapping("/")
    public String index() {
        return BASE_PATH + "/loginaccount_list";
    }

    @ResponseBody
    @RequestMapping("/list")
    public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST, httpServletRequest);
        return map;
    }
    @ResponseBody
    @RequestMapping("/roleList")
    public Object roleList() {
        List<Role> list = sqlManager.all(Role.class);
        return list;
    }
    @ResponseBody
    @RequestMapping("/disableOrEnable")
    public AjaxResult disableOrEnable(HttpServletRequest httpServletRequest) {
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        int updateById = 0;
        for (String s : parameterMap.keySet()) {
            if (!s.equals("flag")) {
                String id = httpServletRequest.getParameter(s);
                String flag = httpServletRequest.getParameter("flag");
                Loginaccount single = sqlManager.single(Loginaccount.class, id);
                Student student = sqlManager.query(Student.class)
                        .andEq("StudentNumber", single.getUserName()).single();
                if (ObjectUtil.isNotNull(student)){
                    if (flag.equals("true")) {
                        student.setIsEnabled(true);
                    } else {
                        student.setIsEnabled(false);
                    }
                }
                if (flag.equals("true")) {
                    single.setIsEnabled(true);
                } else {
                    single.setIsEnabled(false);
                }
                updateById += sqlManager.updateById(single);
                if(student != null){
                    updateById += sqlManager.updateById(student);
                }
            }

        }
        if (updateById <= 0) {
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/reset")
    public Object reset(HttpServletRequest httpServletRequest) {
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        int updateById = 0;
        for (String s : parameterMap.keySet()) {
            String id = httpServletRequest.getParameter(s);
            Loginaccount single = sqlManager.single(Loginaccount.class, id);
            String salt = Md5Util.getInstance().getSalt();
            String md5 = Md5Util.getInstance().MD5("123456", salt);
            single.setPassWord(md5);
            single.setSalt(salt);
            updateById += sqlManager.updateById(single);
        }
        if (updateById <= 0) {
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/export")
    public AjaxResult exportExcel(HttpServletRequest httpServletRequest) {
        String ids = httpServletRequest.getParameter("ids");
        List<Loginaccount> mapList;
        if (ids == null||ids.isEmpty()) {
            mapList = sqlManager.all(Loginaccount.class);
        }else {
            mapList = selectByIds(Loginaccount.class,ids);
        }
        try {
            exportExcel("用户信息",mapList,Loginaccount.class);
        }catch (Exception e){
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

    @RequestMapping("/userCole")
    public String userCole(HttpServletRequest request) {
        String id = request.getParameter("id");
        Map map = sqlManager.selectSingle("loginaccount.findOne",Dict.create().set("id",id),Map.class);
        request.setAttribute("user",map);
        return BASE_PATH + "/setuser_role";
    }

    @ResponseBody
    @RequestMapping("/editPassword")
    public AjaxResult editPassword(HttpServletRequest httpServletRequest) {
        Loginaccount user = ShiroUtils.getInstence().getUser();
        String password = httpServletRequest.getParameter("password");
        String salt = Md5Util.getInstance().getSalt();
        String md5 = Md5Util.getInstance().MD5(password, salt);
        user.setPassWord(md5);
        user.setSalt(salt);
        int updateById = sqlManager.updateById(user);
        if (updateById<=0){
            return fail(FAIL);
        }
        return success(SUCCESS);
    }
}
