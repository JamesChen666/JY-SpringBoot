package com.boot.controller.serve;

import com.boot.controller.system.BaseController;
import com.boot.model.TeacherTime;
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
import java.util.List;
import java.util.Map;

/**
 * @author chenjiang
 */
@Controller
@RequestMapping("/teacherTime")
public class TeacherTimeController extends BaseController{

    private static final String BASE_PATH = "teacherTime";
    private static final String LIST = "teacherTime.list";

    @RequestMapping("/")
	public String index() {
		return BASE_PATH+"/teacherTime_list";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST,httpServletRequest);
        return map;
    }

    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/teacherTime_add";
    }

     @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        return BASE_PATH + "/teacherTime_edit";
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        TeacherTime TeacherTime = sqlManager.single(TeacherTime.class,id);
        return TeacherTime;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        TeacherTime model = mapping(TeacherTime.class, request);
        int result;
        if (model.getId() == null) {
            result = sqlManager.insert(model);
        } else {
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
            deleteById += sqlManager.deleteById(TeacherTime.class, id);
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
            List<TeacherTime> TeacherTimes = importExcel(file, TeacherTime.class);
            for (TeacherTime teacherTime : TeacherTimes) {
                insert += sqlManager.insert(teacherTime);
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
        List<TeacherTime> mapList;
        if (ids == null || ids.isEmpty()) {
            mapList = sqlManager.all(TeacherTime.class);
        } else {
            mapList = selectByIds(TeacherTime.class, ids);
        }
        try {
            exportExcel("TeacherTime", mapList,TeacherTime.class);
        } catch (Exception e) {
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

}