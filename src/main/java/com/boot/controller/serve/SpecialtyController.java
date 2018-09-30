package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import com.boot.controller.system.BaseController;
import com.boot.model.Specialty;
import com.boot.model.SpecialtyManager;
import com.boot.model.Teacher;
import com.boot.system.SqlIntercepter;
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
@RequestMapping("/specialty")
public class SpecialtyController extends BaseController{

    private static final String BASE_PATH = "specialty";
    private static final String LIST = "specialty.list";

    @RequestMapping("/")
    public String index() {
        return BASE_PATH+"/specialty_list";
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
        List<Map> list = sqlManager.select(LIST, Map.class);
        return list;
    }
    @ResponseBody
    @RequestMapping("/combotreeList")
    public Object combotreeList() {
        List<Map> list = sqlManager.select("specialty.combotreeList", Map.class);
        List<Map> combotree = combotree(list);
        return combotree;
    }


    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/specialty_add";
    }

    @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        return BASE_PATH + "/specialty_edit";
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        Specialty Specialty = sqlManager.single(Specialty.class,id);
        return Specialty;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        Specialty model = mapping(Specialty.class, request);
        int result;
        if (model.getId() == null) {
            Specialty specialtyName = sqlManager.query(Specialty.class)
                    .andEq("SpecialtyName", model.getSpecialtyName())
                    .single();
            if (ObjectUtil.isNotNull(specialtyName)){
                return error("专业名称重复");
            }
            Specialty specialtyCode = sqlManager.query(Specialty.class)
                    .andEq("SpecialtyCode", model.getSpecialtyCode())
                    .single();
            if (ObjectUtil.isNotNull(specialtyCode)){
                return error("专业代码重复");
            }
            Specialty gbCode = sqlManager.query(Specialty.class)
                    .andEq("GbCode", model.getGbCode())
                    .single();
            if (ObjectUtil.isNotNull(gbCode)){
                return error("专业国标码重复");
            }
            result = sqlManager.insert(model);
        } else {
            Specialty specialtyName = sqlManager.query(Specialty.class)
                    .andEq("SpecialtyName", model.getSpecialtyName())
                    .andNotEq("Id", model.getId())
                    .single();
            if (ObjectUtil.isNotNull(specialtyName)){
                return error("专业名称重复");
            }
            Specialty specialtyCode = sqlManager.query(Specialty.class)
                    .andEq("SpecialtyCode", model.getSpecialtyCode())
                    .andNotEq("Id", model.getId())
                    .single();
            if (ObjectUtil.isNotNull(specialtyCode)){
                return error("专业代码重复");
            }
            Specialty gbCode = sqlManager.query(Specialty.class)
                    .andEq("GbCode", model.getGbCode())
                    .andNotEq("Id", model.getId())
                    .single();
            if (ObjectUtil.isNotNull(gbCode)){
                return error("专业国标码重复");
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
            deleteById += sqlManager.deleteById(Specialty.class, id);
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
            List<Specialty> Specialtys = importExcel(file, Specialty.class);
            for (Specialty specialty : Specialtys) {
                insert += sqlManager.insert(specialty);
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
        List<Map> mapList;
        if (ids == null||ids.isEmpty()) {
            mapList = sqlManager.select("specialty.list",Map.class);
        }else {
            mapList = appendToList("specialty.list",
                    SqlIntercepter.create().set("WHERE FIND_IN_SET(Id,#{ids})"),
                    Dict.create().set("ids", ids));
        }
        try {
            simpleExport("Specialty", mapList );
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
        Specialty single = sqlManager.single(Specialty.class, id);
        List<SpecialtyManager> specialtyManagers = sqlManager.select("specialtyManager.findBySpecialtyCode",
                SpecialtyManager.class, Dict.create().set("SpecialtyCode", single.getSpecialtyCode()));
        StringBuffer StringBuffer = new StringBuffer();
        for (SpecialtyManager specialtyManager : specialtyManagers) {
            StringBuffer.append(specialtyManager.getJobNumber()).append(",");
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
        Specialty single = sqlManager.single(Specialty.class, id);
        try {
            sqlManager.update("specialtyManager.deleteBySpecialtyCode",
                    Dict.create().set("SpecialtyCode", single.getSpecialtyCode()));
            for (String jobNumber : jobNumbers) {
                SpecialtyManager SpecialtyManager = new SpecialtyManager();
                SpecialtyManager.setSpecialtyCode(single.getSpecialtyCode());
                SpecialtyManager.setJobNumber(jobNumber);
                insert += sqlManager.insert(SpecialtyManager);
            }
        } catch (Exception e) {
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

}
