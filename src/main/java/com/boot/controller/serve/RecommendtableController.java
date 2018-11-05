package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import com.boot.controller.system.BaseController;
import com.boot.model.Recommendtable;
import com.boot.system.SqlIntercepter;
import com.boot.util.AjaxResult;
import com.boot.util.ShiroUtils;
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
@RequestMapping("/recommendtable")
public class RecommendtableController extends BaseController{

    private static final String BASE_PATH = "recommendtable";
    private static final String LIST = "recommendtable.list";

    @RequestMapping("/")
    public String index() {
        return BASE_PATH+"/recommendtable_list";
    }

    @ResponseBody
    @RequestMapping("/list")
    public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST,httpServletRequest);
        return map;
    }

    @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        Map student = sqlManager.selectSingle("student.findSimpleStudentMessage",
                Dict.create().set("Id", httpServletRequest.getParameter("id")), Map.class);
        modelMap.put("student", student);
        return BASE_PATH + "/recommendtable_edit";
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        Map map = sqlManager.selectSingle("recommendtable.findOne",
                Dict.create().set("Id", id), Map.class);
        return map;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        Recommendtable model = mapping(Recommendtable.class, request);
        Recommendtable studentId = sqlManager.query(Recommendtable.class)
                .andEq("StudentId", model.getId()).single();
        int result;
        if (ObjectUtil.isNull(studentId)) {
            model.setStudentId(model.getId());
            model.setId(null);
            model.setCreateDate(new Date());
            model.setUserId(ShiroUtils.getInstence().getUser().getId());
            result = sqlManager.insert(model);
        } else {
            model.setId(studentId.getId());
            model.setStudentId(model.getId());
            Recommendtable single = sqlManager.single(Recommendtable.class, model.getId());
            model.setUserId(single.getUserId());
            model.setCreateDate(single.getCreateDate());
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
            Recommendtable single = sqlManager.query(Recommendtable.class)
                    .andEq("StudentNumber", s).single();
            deleteById += sqlManager.deleteById(Recommendtable.class, single.getId());
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
            List<Recommendtable> Recommendtables = importExcel(file, Recommendtable.class);
            for (Recommendtable recommendtable : Recommendtables) {
                insert += sqlManager.insert(recommendtable);
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
            mapList = sqlManager.select("recommendtable.list",Map.class);
        }else {
            mapList = appendToList("recommendtable.list",
                    SqlIntercepter.create().set("WHERE FIND_IN_SET(Id,#{ids})"),
                    Dict.create().set("ids", ids));
        }
        try {
            simpleExport("Recommendtable", mapList );
        }catch (Exception e){
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

}
