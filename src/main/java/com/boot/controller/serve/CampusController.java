package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import com.boot.controller.system.BaseController;
import com.boot.model.Campus;
import com.boot.model.CampusManager;
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
@RequestMapping("/campus")
public class CampusController extends BaseController {

    private static final String BASE_PATH = "campus";
    private static final String LIST = "campus.list";

    @RequestMapping("/")
    public String index() {
        return BASE_PATH + "/campus_list";
    }

    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/campus_add";
    }

    @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        return BASE_PATH + "/campus_edit";
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
        List<Map> mapList = sqlManager.select(LIST, Map.class);
        return mapList;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        Campus model = mapping(Campus.class, request);
        int result;
        if (model.getId() == null) {
            Campus campusName = sqlManager.query(Campus.class)
                    .andEq("CampusName", model.getCampusName()).single();
            if (ObjectUtil.isNotNull(campusName)){
                return error("校区名称重复");
            }
            Campus campusCode = sqlManager.query(Campus.class)
                    .andEq("CampusCode", model.getCampusCode()).single();
            if (ObjectUtil.isNotNull(campusCode)){
                return error("校区代码重复");
            }
            result = sqlManager.insert(model);
        } else {
            Campus campusName = sqlManager.query(Campus.class)
                    .andEq("CampusName", model.getCampusName())
                    .andNotEq("Id", model.getId())
                    .single();
            if (ObjectUtil.isNotNull(campusName)){
                return error("校区名称重复");
            }
            Campus campusCode = sqlManager.query(Campus.class)
                    .andEq("CampusCode", model.getCampusCode())
                    .andNotEq("Id", model.getId())
                    .single();
            if (ObjectUtil.isNotNull(campusCode)){
                return error("校区代码重复");
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
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        Campus Campus = sqlManager.single(Campus.class, id);
        return Campus;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public AjaxResult delete(HttpServletRequest httpServletRequest) {
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        int deleteById = 0;
        for (String s : parameterMap.keySet()) {
            String id = httpServletRequest.getParameter(s);
            deleteById += sqlManager.deleteById(Campus.class, id);
            deleteById += sqlManager.update("campusManager.deleteByCampus",
                    Dict.create().set("Id", id));
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
            List<Campus> campuses = importExcel(file, Campus.class);
            for (Campus campus : campuses) {
                insert += sqlManager.insert(campus);
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
        List<Map> mapList;
        if (ids == null || ids.isEmpty()) {
            mapList = sqlManager.select("campus.list", Map.class);
        } else {
            mapList = appendToList("campus.list",
                    SqlIntercepter.create().set("WHERE FIND_IN_SET(Id,#{ids})"),
                    Dict.create().set("ids", ids));
        }
        try {
            simpleExport("校区信息", mapList);
        } catch (Exception e) {
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
        Campus single = sqlManager.single(Campus.class, id);
        List<CampusManager> campusManagers = sqlManager.select("campusManager.findByCampusCode",
                CampusManager.class, Dict.create().set("CampusCode", single.getCampusCode()));
        StringBuffer StringBuffer = new StringBuffer();
        for (CampusManager campusManager : campusManagers) {
            StringBuffer.append(campusManager.getJobNumber()).append(",");
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
        Campus single = sqlManager.single(Campus.class, id);
        try {
            sqlManager.update("campusManager.deleteByCampusCode",
                    Dict.create().set("CampusCode", single.getCampusCode()));
            for (String jobNumber : jobNumbers) {
                CampusManager CampusManager = new CampusManager();
                CampusManager.setCampusCode(single.getCampusCode());
                CampusManager.setJobNumber(jobNumber);
                insert += sqlManager.insert(CampusManager);
            }
        } catch (Exception e) {
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }
}
