package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import com.boot.controller.system.BaseController;
import com.boot.model.Sign;
import com.boot.model.SignLog;
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
@RequestMapping("/sign")
public class SignController extends BaseController{

    private static final String BASE_PATH = "sign";
    private static final String LIST = "sign.list";

    @RequestMapping("/")
	public String index() {
		return BASE_PATH+"/sign_list";
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
        return BASE_PATH + "/sign_edit";
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id,ModelMap modelMap) {
        Map map = sqlManager.selectSingle("sign.findOne", Dict.create().set("Id", id),
                Map.class);
        return map;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        Sign model = mapping(Sign.class, request);
        //查询出是否存在签约信息
        Sign studentId = sqlManager.query(Sign.class)
                .andEq("StudentId", model.getStudentId()).single();
        int result=0;
        if (ObjectUtil.isNull(studentId)) {
            //不存在签约信息新增
            model.setIsLock(true);
            model.setStudentId(model.getId());
            model.setId(null);
            result += sqlManager.insert(model);
            //新增签约日志
            SignLog signLog = mapping(SignLog.class, request);
            signLog.setCreateDate(new Date());
            signLog.setUserId(ShiroUtils.getInstence().getUser().getId());
            result += sqlManager.insert(signLog);
        } else {
            //更新签约信息
            model.setIsLock(studentId.getIsLock());
            model.setId(studentId.getId());
            model.setStudentId(model.getId());
            result = sqlManager.updateById(model);
            //新增签约日志
            SignLog signLog = mapping(SignLog.class, request);
            signLog.setCreateDate(new Date());
            signLog.setUserId(ShiroUtils.getInstence().getUser().getId());
            result += sqlManager.insert(signLog);
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
            deleteById += sqlManager.deleteById(Sign.class, id);
        }
        if (deleteById <= 0) {
            return fail(FAIL);
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
                Sign single = sqlManager.query(Sign.class)
                        .andEq("StudentId", id).single();
                if (ObjectUtil.isNull(single)){
                    return  error("未签约学生不能锁定或解锁");
                }
                if (flag.equals("true")) {
                    single.setIsLock(true);
                } else {
                    single.setIsLock(false);
                }
                updateById += sqlManager.updateById(single);
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
            List<Sign> Signs = importExcel(file, Sign.class);
            for (Sign sign : Signs) {
                insert += sqlManager.insert(sign);
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
        List<Sign> mapList;
        if (ids == null || ids.isEmpty()) {
            mapList = sqlManager.all(Sign.class);
        } else {
            mapList = selectByIds(Sign.class, ids);
        }
        try {
            exportExcel("Sign", mapList,Sign.class);
        } catch (Exception e) {
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

}
