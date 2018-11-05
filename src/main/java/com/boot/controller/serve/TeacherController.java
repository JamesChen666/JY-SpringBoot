package com.boot.controller.serve;

import cn.hutool.core.util.ObjectUtil;
import com.boot.controller.system.BaseController;
import com.boot.model.Faculty;
import com.boot.model.Loginaccount;
import com.boot.model.LoginaccountRole;
import com.boot.model.Teacher;
import com.boot.util.AjaxResult;
import com.boot.util.DictionaryType;
import com.boot.util.Md5Util;
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
import java.util.List;
import java.util.Map;

/**
 * @author chenjiang
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController extends BaseController {

    private static final String BASE_PATH = "teacher";
    private static final String LIST = "teacher.list";

    @RequestMapping("/")
    public String index() {
        return BASE_PATH + "/teacher_list";
    }

    @ResponseBody
    @RequestMapping("/list")
    public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST, httpServletRequest);
        return map;
    }

    @ResponseBody
    @RequestMapping("/combotreeList")
    public Object combotreeList() {
        List<Map> mapList = sqlManager.select("teacher.combotree", Map.class);
        List<Map> combotree = combotree(mapList);
        return combotree;
    }

    @ResponseBody
    @RequestMapping("/teachingTeacher")
    public Object teachingTeacher() {
        List<Map> mapList = sqlManager.select("teacher.teachingTeacher", Map.class);
        List<Map> combotree = combotree(mapList);
        return combotree;
    }

    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/teacher_add";
    }

    @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        return BASE_PATH + "/teacher_edit";
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        Teacher Teacher = sqlManager.single(Teacher.class, id);
        return Teacher;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        Teacher model = mapping(Teacher.class, request);
        int result = 0;
        int insert = 0;
        if (model.getId() == null) {
            Teacher teacher = sqlManager.query(Teacher.class)
                    .andEq("JobNumber", model.getJobNumber()).single();
            if (ObjectUtil.isNotNull(teacher)){
                return error("教师工号重复");
            }
            Faculty facultyCode = sqlManager.query(Faculty.class)
                    .andEq("FacultyCode", model.getFacultyCode()).single();
            if (ObjectUtil.isNull(facultyCode)){
                return error("请输入正确的部门");
            }
            result += sqlManager.insert(model);
            //增加用户
            Loginaccount loginaccount = new Loginaccount();
            loginaccount.setIsEnabled(true);
            loginaccount.setRealName(model.getRealName());
            loginaccount.setUserName(model.getJobNumber());
            String salt = Md5Util.getInstance().getSalt();
            String md5 = Md5Util.getInstance().MD5(model.getJobNumber(), salt);
            loginaccount.setPassWord(md5);
            loginaccount.setSalt(salt);
            loginaccount.setCreateDate(new Date());
            loginaccount.setUserTypeId(Integer.valueOf(DictionaryType.教师.getValue().toString()));
            KeyHolder keyHolder = new KeyHolder();
            result += sqlManager.insert(Loginaccount.class,loginaccount,keyHolder);
            LoginaccountRole loginaccountRole=new LoginaccountRole();
            loginaccountRole.setRoleId(Integer.valueOf(DictionaryType.教师.getValue().toString()));
            loginaccountRole.setUserId(Integer.valueOf(keyHolder.getKey().toString()));
            result += sqlManager.insert(loginaccountRole);
        } else {
            Teacher teacher = sqlManager.query(Teacher.class)
                    .andEq("JobNumber", model.getJobNumber())
                    .andNotEq("Id", model.getId())
                    .single();
            if (ObjectUtil.isNotNull(teacher)){
                return error("教师工号重复");
            }
            Faculty facultyCode = sqlManager.query(Faculty.class)
                    .andEq("FacultyCode", model.getFacultyCode())
                    .single();
            if (ObjectUtil.isNull(facultyCode)){
                return error("请输入正确的部门");
            }
            result = sqlManager.updateById(model);
            //如果姓名修改修改姓名
            Loginaccount username = sqlManager.query(Loginaccount.class)
                    .andEq("Username", model.getJobNumber()).single();
            if (!model.getRealName().equals(username.getRealName())) {
                username.setRealName(model.getRealName());
                insert += sqlManager.updateById(username);
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
            Teacher teacher = sqlManager.single(Teacher.class, id);
            Loginaccount loginaccount = sqlManager.query(Loginaccount.class)
                    .andEq("UserName", teacher.getJobNumber()).single();
            deleteById += sqlManager.deleteById(Teacher.class, id);
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
    @RequestMapping("/import")
    public AjaxResult importExcel(MultipartHttpServletRequest request) {
        MultiValueMap<String, MultipartFile> multiFileMap = request.getMultiFileMap();
        int insert = 0;
        int err = 0;
        for (String s : multiFileMap.keySet()) {
            MultipartFile file = request.getFile(s);
            List<Teacher> Teachers = importExcel(file, Teacher.class);
            for (Teacher teacher : Teachers) {
                err++;
                Teacher jobNumber = sqlManager.query(Teacher.class)
                        .andEq("JobNumber", teacher.getJobNumber()).single();
                if (ObjectUtil.isNotNull(jobNumber)){
                    return error("第"+err+"行"+jobNumber.getJobNumber()+"重复");
                }
            }
            for (Teacher teacher : Teachers) {
                insert += sqlManager.insert(teacher);
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
        List<Teacher> mapList;
        if (ids == null || ids.isEmpty()) {
            mapList = sqlManager.all(Teacher.class);
        } else {
            mapList = selectByIds(Teacher.class, ids);
        }
        try {
            exportExcel("教师信息", mapList, Teacher.class);
        } catch (Exception e) {
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }


}
