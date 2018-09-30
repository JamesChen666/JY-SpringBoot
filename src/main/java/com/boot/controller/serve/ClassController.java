package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import com.boot.controller.system.BaseController;
import com.boot.model.Class;
import com.boot.model.ClassInstructor;
import com.boot.model.Teacher;
import com.boot.util.AjaxResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author chenjiang
 */
@Controller
@RequestMapping("/class")
public class ClassController extends BaseController{

    private static final String BASE_PATH = "class";
    private static final String LIST = "class.list";

    @RequestMapping("/")
    public String index() {
        return BASE_PATH+"/class_list";
    }

    @ResponseBody
    @RequestMapping("/list")
    public Object list(HttpServletRequest httpServletRequest) {
        Map map= pageQuery(LIST, httpServletRequest);
        return map;
    }
    @ResponseBody
    @RequestMapping("/combotreeList")
    public Object combotreeList() {
        List<Map> list = sqlManager.select("class.combotreeList", Map.class);
        List<Map> combotree = combotree(list);
        return combotree;
    }

    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/class_add";
    }

    @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        return BASE_PATH + "/class_edit";
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        Class Class = sqlManager.single(Class.class,id);
        return Class;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        Class model = mapping(Class.class, request);
        int result;
        if (model.getId() == null) {
            Class className = sqlManager.query(Class.class)
                    .andEq("ClassName", model.getClassName())
                    .single();
            if (ObjectUtil.isNotNull(className)){
                return error("班级名称重复");
            }
            Class classNo = sqlManager.query(Class.class)
                    .andEq("ClassNo", model.getClassNo())
                    .single();
            if (ObjectUtil.isNotNull(classNo)){
                return error("班级编号重复");
            }
            result = sqlManager.insert(model);
        } else {
            Class className = sqlManager.query(Class.class)
                    .andEq("ClassName", model.getClassName())
                    .andNotEq("Id", model.getId())
                    .single();
            if (ObjectUtil.isNotNull(className)){
                return error("班级名称重复");
            }
            Class classNo = sqlManager.query(Class.class)
                    .andEq("ClassNo", model.getClassNo())
                    .andNotEq("Id", model.getId())
                    .single();
            if (ObjectUtil.isNotNull(classNo)){
                return error("班级编号重复");
            }
            result = sqlManager.updateById(model);
        }
        if (result > 0) {
            return success(SUCCESS);
        } else {
            return fail(FAIL);
        }
    }

    @ResponseBody
    @RequestMapping("/delete")
    public AjaxResult delete(HttpServletRequest httpServletRequest) {
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        int deleteById = 0;
        for (String s : parameterMap.keySet()) {
            String id = httpServletRequest.getParameter(s);
            deleteById += sqlManager.deleteById(Class.class, id);
        }
        if (deleteById <= 0) {
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
            List<Class> Classes = importExcel(file, Class.class);
            for (Class cls : Classes) {
                insert += sqlManager.insert(cls);
            }
        }
        if (insert<=0){
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/export")
    public AjaxResult exportExcel(HttpServletRequest httpServletRequest) {
        String ids = httpServletRequest.getParameter("ids");
        List<Class> mapList;
        if (ids == null||ids.isEmpty()) {
            mapList = sqlManager.all(Class.class);
        }else {
            mapList = selectByIds(Class.class, ids);
        }
        try {
            exportExcel("班级管理", mapList, Class.class);
        }catch (Exception e){
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/manager")
    public Object manager(HttpServletRequest httpServletRequest) {
        String values = null;
        String id = httpServletRequest.getParameter("id[]");
        Class single = sqlManager.single(Class.class, id);
        List<ClassInstructor> classManagers = sqlManager.select("classinstructor.findByClassNo",
                ClassInstructor.class, Dict.create().set("ClassNo", single.getClassNo()));
        StringBuffer StringBuffer = new StringBuffer();
        for (ClassInstructor classManager : classManagers) {
            StringBuffer.append(classManager.getJobNumber()).append(",");
        }
        if (StringBuffer.length() > 1) {
            values = StringBuffer.substring(0, StringBuffer.length() - 1);
        }
        return values;
    }

    @ResponseBody
    @RequestMapping("/setManager")
    public AjaxResult setManager(HttpServletRequest httpServletRequest) {
        String manager = httpServletRequest.getParameter("manager");
        String[] split = manager.split(",");
        List<String> jobNumbers = new ArrayList();
        for (String s : split) {
            Teacher teacher = sqlManager.selectSingle("teacher.findByJobNumber",
                    Dict.create().set("JobNumber", s), Teacher.class);
            if (ObjectUtil.isNotNull(teacher)) {
                jobNumbers.add(s);
            }
        }
        int insert = 0;
        String id = httpServletRequest.getParameter("id[]");
        Class single = sqlManager.single(Class.class, id);
        try {
            sqlManager.update("classinstructor.deleteByClassNo",
                    Dict.create().set("ClassNo", single.getClassNo()));
            for (String jobNumber : jobNumbers) {
                ClassInstructor classInstructor = new ClassInstructor();
                classInstructor.setClassNo(single.getClassNo());
                classInstructor.setJobNumber(jobNumber);
                insert += sqlManager.insert(classInstructor);
            }
        } catch (Exception e) {
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

}
