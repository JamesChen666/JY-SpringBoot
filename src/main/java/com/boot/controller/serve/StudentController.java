package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.boot.controller.system.BaseController;
import com.boot.model.*;
import com.boot.util.AjaxResult;
import com.boot.util.DictionaryType;
import com.boot.util.Md5Util;
import com.boot.util.ShiroUtils;
import org.beetl.sql.core.db.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenjiang
 */
@Controller
@RequestMapping("/student")
public class StudentController extends BaseController {

    private static final String BASE_PATH = "student";
    private static final String LIST = "student.list";

    @RequestMapping("/")
    public String index() {
        return BASE_PATH + "/student_list";
    }

    @ResponseBody
    @RequestMapping("/list")
    public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST, httpServletRequest);
        return map;
    }

    @ResponseBody
    @RequestMapping("/gridList")
    public Object gridList() {
        List<Map> select = sqlManager.select("student.gridList", Map.class);
        Map gridData = new LinkedHashMap();
        gridData.put("total", select.size());
        gridData.put("rows", select);
        return gridData;
    }

    @ResponseBody
    @RequestMapping("/specialty")
    public Object specialty(HttpServletRequest httpServletRequest) {
      Map map = sqlManager.selectSingle("student.classToCFS"
                ,Dict.create().set("ClassNo", httpServletRequest.getParameter("classNo"))
                ,Map.class);
        return map;
    }

    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/student_add";
    }

    @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        Student student = sqlManager.single(Student.class, httpServletRequest.getParameter("id"));
        Map map = sqlManager.selectSingle("student.classToCFS",
                Dict.create().set("ClassNo", student.getClassNumber()), Map.class);
        modelMap.put("school", map);
        return BASE_PATH + "/student_edit";
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        Student Student = sqlManager.single(Student.class, id);
        return Student;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        Student model = mapping(Student.class, request);
        int result=0;
        int insert = 0;
        if (model.getId() == null) {
            Student studentNumber = sqlManager.query(Student.class)
                    .andEq("StudentNumber", model.getStudentNumber())
                    .single();
            if (ObjectUtil.isNotNull(studentNumber)) {
                return error("学号不能重复");
            }
            boolean validCard = IdcardUtil.isValidCard(model.getIdCard());
            if (!validCard) {
                return error("身份证号输入格式错误,请输入正确的身份证号码");
            }
            sqlManager.query(Specialty.class).andEq("", "");
            model.setIsEnabled(true);
            result += sqlManager.insert(model);
            //增加用户
            Loginaccount loginaccount = new Loginaccount();
            loginaccount.setIsEnabled(true);
            loginaccount.setRealName(model.getRealName());
            loginaccount.setUserName(model.getStudentNumber());
            String salt = Md5Util.getInstance().getSalt();
            String md5 = Md5Util.getInstance().MD5(model.getStudentNumber(), salt);
            loginaccount.setPassWord(md5);
            loginaccount.setSalt(salt);
            loginaccount.setCreateDate(new Date());
            loginaccount.setUserTypeId(Integer.valueOf(DictionaryType.学生.getValue().toString()));
            KeyHolder keyHolder = new KeyHolder();
            result += sqlManager.insert(Loginaccount.class, loginaccount, keyHolder);
            LoginaccountRole loginaccountRole=new LoginaccountRole();
            loginaccountRole.setRoleId(Integer.valueOf(DictionaryType.学生.getValue().toString()));
            loginaccountRole.setUserId(Integer.valueOf(keyHolder.getKey().toString()));
            result += sqlManager.insert(loginaccountRole);
        } else {
            Student studentNumber = sqlManager.query(Student.class)
                    .andEq("StudentNumber", model.getStudentNumber())
                    .andNotEq("Id", model.getId())
                    .single();
            if (ObjectUtil.isNotNull(studentNumber)) {
                return error("学号不能重复");
            }
            boolean validCard = IdcardUtil.isValidCard(model.getIdCard());
            if (!validCard) {
                return error("身份证号输入格式错误,请输入正确的身份证号码");
            }
            Student single = sqlManager.single(Student.class, model.getId());
            model.setIsEnabled(single.getIsEnabled());
            result += sqlManager.updateById(model);
            //如果姓名修改修改姓名
            Loginaccount username = sqlManager.query(Loginaccount.class)
                    .andEq("Username", model.getStudentNumber()).single();
            if (!model.getRealName().equals(username.getRealName())) {
                username.setRealName(model.getRealName());
                result += sqlManager.updateById(username);
            }
        }
        if (result <= 0) {
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/delete")
    public AjaxResult delete(HttpServletRequest httpServletRequest) {
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        int deleteById = 0;
        int deleteObject = 0;
        for (String s : parameterMap.keySet()) {
            String id = httpServletRequest.getParameter(s);
            Student student = sqlManager.single(Student.class, id);
            Loginaccount loginaccount = sqlManager.query(Loginaccount.class)
                    .andEq("UserName", student.getStudentNumber()).single();
            deleteById += sqlManager.deleteById(Student.class, id);
            deleteObject += sqlManager.deleteObject(loginaccount);
        }
        if (deleteById <= 0) {
            return fail(FAIL);
        } else if (deleteObject <= 0) {
            return fail("删除用户错误");
        }
        return success(SUCCESS);
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
                Student student = sqlManager.single(Student.class, id);
                Loginaccount loginaccount = sqlManager.query(Loginaccount.class)
                        .andEq("UserName", student.getStudentNumber()).single();
                if (flag.equals("true")) {
                    student.setIsEnabled(true);
                    loginaccount.setIsEnabled(true);
                } else {
                    student.setIsEnabled(false);
                    loginaccount.setIsEnabled(false);
                }
                updateById += sqlManager.updateById(student);
                updateById += sqlManager.updateById(loginaccount);
            }

        }
        if (updateById <= 0) {
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/import")
    public AjaxResult importExcel(MultipartHttpServletRequest request) {
        MultiValueMap<String, MultipartFile> multiFileMap = request.getMultiFileMap();
        int insert = 0;
        for (String s : multiFileMap.keySet()) {
            MultipartFile file = request.getFile(s);
            List<Student> Students = importExcel(file, Student.class);
            for (Student student : Students) {
                insert += sqlManager.insert(student);
            }
        }
        if (insert <= 0) {
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/export")
    public AjaxResult exportExcel(HttpServletRequest httpServletRequest) {
        String ids = httpServletRequest.getParameter("ids");
        List<Student> studentList ;
        if (StrUtil.hasEmpty(ids)){
            studentList = sqlManager.all(Student.class);
        }else {
            studentList = selectByIds(Student.class, ids);
        }
        try {
            exportExcel("学生信息", studentList,Student.class);
        } catch (Exception e) {
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

    @RequestMapping("/sign")
    public String sign(ModelMap modelMap){
        Student studentNumber = sqlManager.query(Student.class)
                .andEq("StudentNumber", ShiroUtils.getInstence().getUser().getUserName()).single();
        Map student = sqlManager.selectSingle("student.findSimpleStudentMessage",
                Dict.create().set("Id", studentNumber.getId()), Map.class);
        modelMap.put("student", student);
        return BASE_PATH+"/student_sign";
    }

    @ResponseBody
    @RequestMapping("/sign/{id}")
    public Object sign(@PathVariable Integer id){
        Map sign = sqlManager.selectSingle("student.findSign",
                Dict.create().set("Id", id),
                Map.class);
        return sign;
    }

    @ResponseBody
    @RequestMapping("/saveSign")
    public AjaxResult saveSign(HttpServletRequest httpServletRequest){
        Sign model = mapping(Sign.class, httpServletRequest);
        SignLog signLog = mapping(SignLog.class, httpServletRequest);
        model.setIsLock(false);
        model.setGraduationWhereAboutCode("10");
        signLog.setGraduationWhereAboutCode("10");
        signLog.setCreateDate(new Date());
        signLog.setUserId(ShiroUtils.getInstence().getUser().getId());
        Sign studentId = sqlManager.query(Sign.class)
                .andEq("StudentId", model.getStudentId()).single();
        int insert=0;
        if (ObjectUtil.isNotNull(studentId)){
            if (studentId.getIsLock()){
                return fail("已锁定,不允许修改");
            }else {
                model.setId(studentId.getId());
                insert += sqlManager.updateById(model);

            }
        }else {
             insert += sqlManager.insert(model);
        }
         insert += sqlManager.insert(signLog);
        if (insert<=0){
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

    @RequestMapping("/work")
    public String work(ModelMap modelMap){
        Student studentNumber = sqlManager.query(Student.class)
                .andEq("StudentNumber", ShiroUtils.getInstence().getUser().getUserName()).single();
        Map student = sqlManager.selectSingle("student.findSimpleStudentMessage",
                Dict.create().set("Id", studentNumber.getId()), Map.class);
        modelMap.put("student", student);
        return BASE_PATH+"/student_work";
    }

    @ResponseBody
    @RequestMapping("/work/{id}")
    public Object work(@PathVariable Integer id){
        Map sign = sqlManager.selectSingle("student.findWork",
                Dict.create().set("Id", id),
                Map.class);
        return sign;
    }

    @ResponseBody
    @RequestMapping("/saveWork")
    public AjaxResult saveWork(HttpServletRequest httpServletRequest){
        Sign model = mapping(Sign.class, httpServletRequest);
        SignLog signLog = mapping(SignLog.class, httpServletRequest);
        model.setIsLock(false);
        signLog.setCreateDate(new Date());
        signLog.setUserId(ShiroUtils.getInstence().getUser().getId());
        Sign studentId = sqlManager.query(Sign.class)
                .andEq("StudentId", model.getStudentId()).single();
        int insert=0;
        if (ObjectUtil.isNotNull(studentId)){
          return error("就业协议已签约,不能再提交工作证明");
        }else {
             insert += sqlManager.insert(model);
        }
         insert += sqlManager.insert(signLog);
        if (insert<=0){
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/graduationWhereAboutCode")
    public Object graduationWhereAboutCode() {
        List<Dictionary> dictionaries = sqlManager.query(Dictionary.class)
                .andEq("TypeCode", "JYBYQXDM")
                .andNotEq("MemberValue", 10)
                .select();
        return dictionaries;
    }

    @RequestMapping("/viewSign/{type}")
    public String viewSign(@PathVariable Integer type, ModelMap modelMap){
        Student studentNumber = sqlManager.query(Student.class)
                .andEq("StudentNumber", ShiroUtils.getInstence().getUser().getUserName())
                .single();
        Map student = sqlManager.selectSingle("student.findSimpleStudentMessage",
                Dict.create().set("Id", studentNumber.getId()), Map.class);
        modelMap.put("student", student);
        List<Map> mapList = sqlManager.select("student.findSignView", Map.class,
                Dict.create().set("StudentId", studentNumber.getId()));

        Map sign = null;
        Map work = null;
        for (Map map : mapList) {
            if (map.get("GraduationWhereAboutCode").toString().equals("签就业协议形式就业")){
                sign=map;
            }else {
                work =map;
            }
        }
        if (type==1){
            modelMap.put("sign", sign);
            return BASE_PATH+"/student_signview";
        }else {
            modelMap.put("sign", work);
            return BASE_PATH+"/student_workview";
        }
    }

}
