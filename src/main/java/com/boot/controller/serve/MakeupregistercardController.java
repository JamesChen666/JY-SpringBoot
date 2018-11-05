package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import com.boot.controller.system.BaseController;
import com.boot.model.Makeupregistercard;
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
@RequestMapping("/makeupregistercard")
public class MakeupregistercardController extends BaseController{

    private static final String BASE_PATH = "makeupregistercard";
    private static final String LIST = "makeupregistercard.list";

    @RequestMapping("/")
	public String index() {
		return BASE_PATH+"/makeupregistercard_list";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST,httpServletRequest);
        return map;
    }

    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/makeupregistercard_add";
    }

     @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        return BASE_PATH + "/makeupregistercard_edit";
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        Makeupregistercard Makeupregistercard = sqlManager.single(Makeupregistercard.class,id);
        return Makeupregistercard;
    }

    @ResponseBody
    @RequestMapping("/studentList/{id}")
    public Object studentList(@PathVariable Integer id) {
        List<Map> makeupregistercards
                = sqlManager.select("makeupregistercard.findByStudentId" ,Map.class,
                Dict.create().set("StudentId", id));
        return makeupregistercards;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        Makeupregistercard model = mapping(Makeupregistercard.class, request);
        int result;
        if (model.getId() == null) {
            model.setCreateDate(new Date());
            model.setStatus(0);
            model.setUserId(ShiroUtils.getInstence().getUser().getId());
            result = sqlManager.insert(model);
        } else {
            Makeupregistercard single = sqlManager.single(Makeupregistercard.class, model.getId());
            model.setCreateDate(single.getCreateDate());
            model.setUserId(single.getUserId());
            model.setStatus(single.getStatus());
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
            deleteById += sqlManager.deleteById(Makeupregistercard.class, id);
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
            List<Makeupregistercard> Makeupregistercards = importExcel(file, Makeupregistercard.class);
            for (Makeupregistercard makeupregistercard : Makeupregistercards) {
                insert += sqlManager.insert(makeupregistercard);
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
        List<Makeupregistercard> mapList;
        if (ids == null || ids.isEmpty()) {
            mapList = sqlManager.all(Makeupregistercard.class);
        } else {
            mapList = selectByIds(Makeupregistercard.class, ids);
        }
        try {
            exportExcel("Makeupregistercard", mapList,Makeupregistercard.class);
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
        Makeupregistercard single = sqlManager.single(Makeupregistercard.class, ids);
        if (single.getStatus() != 0) {
            return error("已审核数据不能再次审核!");
        }
        String status = httpServletRequest.getParameter("status");
        String rowDatas = httpServletRequest.getParameter("data");
        single.setStatus(Integer.valueOf(status));
        single.setExamineUserId(ShiroUtils.getInstence().getUser().getId());
        single.setExamineDate(new Date());
        single.setExamineRemark(rowDatas);
        int update = sqlManager.updateById(single);
        if (update <= 0) {
            return fail(FAIL);
        }
        return success(SUCCESS);
    }
}
