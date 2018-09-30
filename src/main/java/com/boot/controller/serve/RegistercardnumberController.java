package com.boot.controller.serve;

import com.boot.controller.system.BaseController;
import com.boot.model.Registercardnumber;
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
@RequestMapping("/registercardnumber")
public class RegistercardnumberController extends BaseController{

    private static final String BASE_PATH = "registercardnumber";
    private static final String LIST = "registercardnumber.list";

    @RequestMapping("/")
	public String index() {
		return BASE_PATH+"/registercardnumber_list";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST,httpServletRequest);
        return map;
    }

    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/registercardnumber_add";
    }

     @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        return BASE_PATH + "/registercardnumber_edit";
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        Registercardnumber Registercardnumber = sqlManager.single(Registercardnumber.class,id);
        return Registercardnumber;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        Registercardnumber model = mapping(Registercardnumber.class, request);
        int result;
        if (model.getId() == null) {
            model.setAllotDate(new Date());
            model.setUserId(ShiroUtils.getInstence().getUser().getId());
            result = sqlManager.insert(model);
        } else {
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
            deleteById += sqlManager.deleteById(Registercardnumber.class, id);
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
            List<Registercardnumber> Registercardnumbers = importExcel(file, Registercardnumber.class);
            for (Registercardnumber registercardnumber : Registercardnumbers) {
                insert += sqlManager.insert(registercardnumber);
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
        List<Registercardnumber> mapList;
        if (ids == null || ids.isEmpty()) {
            mapList = sqlManager.all(Registercardnumber.class);
        } else {
            mapList = selectByIds(Registercardnumber.class, ids);
        }
        try {
            exportExcel("Registercardnumber", mapList,Registercardnumber.class);
        } catch (Exception e) {
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

}
