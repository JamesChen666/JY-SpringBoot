package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.boot.controller.system.BaseController;
import com.boot.model.Examine;
import com.boot.model.Field;
import com.boot.util.AjaxResult;
import com.boot.util.ShiroUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * @author chenjiang
 */
@Controller
@RequestMapping("/examine")
public class ExamineController extends BaseController {

    private static final String BASE_PATH = "examine";
    private static final String LIST = "examine.list";

    @RequestMapping("/")
    public String index() {
        return BASE_PATH + "/examine_list";
    }

    @ResponseBody
    @RequestMapping("/list")
    public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST, httpServletRequest);
        return map;
    }
    @ResponseBody
    @RequestMapping("/view/{id}")
    public Object view(@PathVariable Integer id) {
        Map map = sqlManager.selectSingle("examine.findOne",
                Dict.create().set("Id", id), Map.class);
        return map;
    }

    @ResponseBody
    @RequestMapping("/studentList")
    public Object studentList(HttpServletRequest httpServletRequest) {
        List<Map> mapList = sqlManager.select("examine.studentList", Map.class,
                Dict.create().set("StudentNumber",
                        ShiroUtils.getInstence().getUser().getUserName()));
        return mapList;
    }

    @ResponseBody
    @RequestMapping("/export")
    public AjaxResult exportExcel(HttpServletRequest httpServletRequest) {
        String ids = httpServletRequest.getParameter("ids");
        List<Examine> mapList;
        if (ids == null || ids.isEmpty()) {
            mapList = sqlManager.select("examine.list", Examine.class);
        } else {
            mapList = selectByIds(Examine.class, ids);
        }
        try {
            exportExcel("学生字段修改信息", mapList, Examine.class);
        } catch (Exception e) {
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/audit")
    public AjaxResult audit(HttpServletRequest httpServletRequest) {
        String ids = httpServletRequest.getParameter("ids");
        Examine single = sqlManager.single(Examine.class, ids);
        if (single.getStatus() != 0) {
            return error("已审核数据不能再次审核!");
        }
        String status = httpServletRequest.getParameter("status");
        String rowDatas = httpServletRequest.getParameter("data");
        single.setStatus(Integer.valueOf(status));
        single.setApprovalUserId(ShiroUtils.getInstence().getUser().getId());
        single.setApprovalDate(new Date());
        single.setOpinion(rowDatas);
        int update = sqlManager.updateById(single);
        if (update <= 0) {
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest httpServletRequest) {
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
        Map student = sqlManager.selectSingle("student.findByStudentNumber",
                Dict.create().set("StudentNumber",
                        ShiroUtils.getInstence().getUser().getUserName()),Map.class);
        int insert = 0;
        while (parameterNames.hasMoreElements()) {
            String s = parameterNames.nextElement();
            String name = StrUtil.upperFirst(s);
            String value = httpServletRequest.getParameter(s);
            if (!student.get(name).toString().equals(value)){
                Examine examine = new Examine();
                examine.setStudentId(Integer.valueOf(student.get("Id").toString()));
                Integer fieldId = sqlManager.query(Field.class)
                        .andEq("FieldCode", name).single().getId();
                examine.setFieldId(fieldId);
                examine.setStatus(0);
                examine.setCreateDate(new Date());
                examine.setBeforeText(name);
                examine.setBeforeValue(student.get(name).toString());
                examine.setAfterText(name);
                examine.setAfterValue(value);
                examine.setUserId(ShiroUtils.getInstence().getUser().getId());
                insert += sqlManager.insert(examine);
            }
        }
        if (insert <= 0) {
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

}
