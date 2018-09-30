package com.boot.controller.serve;

import com.boot.controller.system.BaseController;
import com.boot.model.Agreementnumber;
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
@RequestMapping("/agreementnumber")
public class AgreementnumberController extends BaseController{

    private static final String BASE_PATH = "agreementnumber";
    private static final String LIST = "agreementnumber.list";

    @RequestMapping("/")
	public String index() {
		return BASE_PATH+"/agreementnumber_list";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST,httpServletRequest);
        return map;
    }

    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/agreementnumber_add";
    }

     @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        return BASE_PATH + "/agreementnumber_edit";
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        Agreementnumber Agreementnumber = sqlManager.single(Agreementnumber.class,id);
        return Agreementnumber;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        Agreementnumber model = mapping(Agreementnumber.class, request);
        int result;
        if (model.getId() == null) {
            model.setIsEnabled(true);
            model.setAllotDate(new Date());
            model.setUserId(ShiroUtils.getInstence().getUser().getId());
            result = sqlManager.insert(model);
        } else {
            Agreementnumber single = sqlManager.single(Agreementnumber.class, model.getId());
            model.setIsEnabled(single.getIsEnabled());
            model.setAllotDate(new Date());
            model.setUserId(ShiroUtils.getInstence().getUser().getId());
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
            deleteById += sqlManager.deleteById(Agreementnumber.class, id);
        }
        if (deleteById <= 0) {
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/Obsolete")
    public AjaxResult disableOrEnable(HttpServletRequest httpServletRequest) {
        String id = httpServletRequest.getParameter("id");
        String text = httpServletRequest.getParameter("text");
        Agreementnumber single = sqlManager.single(Agreementnumber.class, id);
        single.setInvalidReason(text);
        single.setIsEnabled(false);
        int  updateById = sqlManager.updateById(single);
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
            List<Agreementnumber> Agreementnumbers = importExcel(file, Agreementnumber.class);
            for (Agreementnumber agreementnumber : Agreementnumbers) {
                insert += sqlManager.insert(agreementnumber);
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
        List<Agreementnumber> mapList;
        if (ids == null || ids.isEmpty()) {
            mapList = sqlManager.all(Agreementnumber.class);
        } else {
            mapList = selectByIds(Agreementnumber.class, ids);
        }
        try {
            exportExcel("Agreementnumber", mapList,Agreementnumber.class);
        } catch (Exception e) {
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

}
