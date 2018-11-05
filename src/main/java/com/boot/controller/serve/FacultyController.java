package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import com.boot.controller.system.BaseController;
import com.boot.model.Faculty;
import com.boot.model.FacultyManager;
import com.boot.model.Teacher;
import com.boot.util.AjaxResult;
import com.boot.util.DictionaryType;
import net.sf.json.JSONArray;
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
@RequestMapping("/faculty")
public class FacultyController extends BaseController {

    private static final String BASE_PATH = "faculty";
    private static final String LIST = "faculty.list";

    @RequestMapping("/")
    public String index() {
        return BASE_PATH + "/faculty_list";
    }

    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/faculty_add";
    }

    @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        return BASE_PATH + "/faculty_edit";
    }

    @ResponseBody
    @RequestMapping("/facultyType")
    public Object facultyType() {
        List<Map> mapList = new ArrayList<>();
        mapList.add(DictionaryType.学院.getMap());
        mapList.add(DictionaryType.系部.getMap());
        return mapList;
    }

    @ResponseBody
    @RequestMapping("/list")
    public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST, httpServletRequest);
        return map;
    }

    /**
     * 通过校区代码查询出院系
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @RequestMapping("/findFacultyList")
    public Object findFacultyList(HttpServletRequest httpServletRequest) {
       List<Map> mapList = sqlManager.select("faculty.findFaculty",Map.class,Dict.create().set("CampusCode",httpServletRequest.getParameter("CampusCode")));
       return mapList;
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
        Faculty model = mapping(Faculty.class, request);
        int result;
        if (model.getId() == null) {
            Faculty facultyName = sqlManager.query(Faculty.class)
                    .andEq("FacultyName", model.getFacultyName())
                    .single();
            if (ObjectUtil.isNotNull(facultyName)){
                return error("院系名称重复");
            }
            Faculty facultyCode = sqlManager.query(Faculty.class)
                    .andEq("FacultyCode", model.getFacultyCode())
                    .single();
            if (ObjectUtil.isNotNull(facultyCode)){
                return error("院系代码重复");
            }
            result = sqlManager.insert(model);
        } else {
            Faculty facultyName = sqlManager.query(Faculty.class)
                    .andEq("FacultyName", model.getFacultyName())
                    .andNotEq("Id", model.getId())
                    .single();
            if (ObjectUtil.isNotNull(facultyName)){
                return error("院系名称重复");
            }
            Faculty facultyCode = sqlManager.query(Faculty.class)
                    .andEq("FacultyCode", model.getFacultyCode())
                    .andNotEq("Id", model.getId())
                    .single();
            if (ObjectUtil.isNotNull(facultyCode)){
                return error("院系代码重复");
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
        Faculty Faculty = sqlManager.single(Faculty.class, id);
        return Faculty;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public AjaxResult delete(HttpServletRequest httpServletRequest) {
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        int deleteById = 0;
        for (String s : parameterMap.keySet()) {
            String id = httpServletRequest.getParameter(s);
            deleteById += sqlManager.deleteById(Faculty.class, id);
            deleteById += sqlManager.update("roleMenu.deleteByRoleId",
                    Dict.create().set("RoleId", id));
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
        int err = 0;
        for (String s : multiFileMap.keySet()) {
            MultipartFile file = request.getFile(s);
            List<Faculty> facultys = importExcel(file, Faculty.class);
            for (Faculty faculty : facultys) {
                err++;
                Faculty single = sqlManager.query(Faculty.class)
                        .andEq("FacultyName", faculty.getFacultyName()).single();
                if (ObjectUtil.isNotNull(single)){
                    return error("第"+err+"行"+single.getFacultyName()+"重复");
                }
                Faculty facultyCode = sqlManager.query(Faculty.class)
                        .andEq("FacultyCode", faculty.getFacultyCode()).single();
                if (ObjectUtil.isNotNull(facultyCode)){
                    return error("第"+err+"行"+facultyCode.getFacultyCode()+"重复");
                }
            }
            for (Faculty faculty : facultys) {
                insert += sqlManager.insert(faculty);
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
        List<Faculty> mapList;
        if (ids == null || ids.isEmpty()) {
            mapList = sqlManager.all(Faculty.class);
        } else {
            mapList = selectByIds(Faculty.class,ids);
        }
        try {
            exportExcel("院系信息", mapList,Faculty.class);
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
        Faculty single = sqlManager.single(Faculty.class, id);
        List<FacultyManager> facultyManagers = sqlManager.select("facultyManager.findByFacultyCode",
                FacultyManager.class, Dict.create().set("FacultyCode", single.getFacultyCode()));
        StringBuffer StringBuffer = new StringBuffer();
        for (FacultyManager facultyManager : facultyManagers) {
            StringBuffer.append(facultyManager.getJobNumber()).append(",");
        }
        if (StringBuffer.length() > 1) {
            values = StringBuffer.substring(0, StringBuffer.length() - 1);
        }
        return values;
    }

    @ResponseBody
    @RequestMapping("/setManager")
    public AjaxResult setManager(HttpServletRequest httpServletRequest) {
        String code = httpServletRequest.getParameter("objectCode");
        String jobNumberss = httpServletRequest.getParameter("jobNumber");
        JSONArray array = JSONArray.fromObject(jobNumberss);
        List<String> list =(ArrayList)JSONArray.toCollection(array, String.class);
        /*String manager = httpServletRequest.getParameter("manager");
        String[] split = manager.split(",");*/
        List<String> jobNumbers = new ArrayList();
        for (String s : list) {
            Teacher teacher = sqlManager.selectSingle("teacher.findByJobNumber",
                    Dict.create().set("JobNumber", s), Teacher.class);
            if (ObjectUtil.isNotNull(teacher)) {
                jobNumbers.add(s);
            }
        }
        int insert = 0;
        /*String id = httpServletRequest.getParameter("id[]");
        Faculty single = sqlManager.single(Faculty.class, id);*/
        try {
            sqlManager.update("facultyManager.deleteByFacultyCode",
                    Dict.create().set("FacultyCode", code/*single.getFacultyCode()*/));
            for (String jobNumber : jobNumbers) {
                FacultyManager FacultyManager = new FacultyManager();
                FacultyManager.setFacultyCode(code/*single.getFacultyCode()*/);
                FacultyManager.setJobNumber(jobNumber);
                insert += sqlManager.insert(FacultyManager);
            }
        } catch (Exception e) {
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }
}
