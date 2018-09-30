package com.boot.controller.serve;

import com.boot.controller.system.BaseController;
import com.boot.model.Dispatch;
import com.boot.model.DispatchLog;
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
@RequestMapping("/dispatch")
public class DispatchController extends BaseController{

    private static final String BASE_PATH = "dispatch";
    private static final String LIST = "dispatch.list";

    @RequestMapping("/")
	public String index() {
		return BASE_PATH+"/dispatch_list";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST,httpServletRequest);
        return map;
    }

    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/dispatch_add";
    }

     @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        return BASE_PATH + "/dispatch_edit";
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        Dispatch Dispatch = sqlManager.single(Dispatch.class,id);
        return Dispatch;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        Dispatch model = mapping(Dispatch.class, request);
        int result=0;
        if (model.getId() == null) {
            result += sqlManager.insert(model);
            DispatchLog dispatchLog = mapping(DispatchLog.class, request);
            dispatchLog.setUserId(ShiroUtils.getInstence().getUser().getId());
            dispatchLog.setCreateDate(new Date());
            result +=sqlManager.insert(dispatchLog);
        } else {
            result = sqlManager.updateById(model);
            DispatchLog dispatchLog = mapping(DispatchLog.class, request);
            dispatchLog.setUserId(ShiroUtils.getInstence().getUser().getId());
            dispatchLog.setCreateDate(new Date());
            result +=sqlManager.insert(dispatchLog);

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
            deleteById += sqlManager.deleteById(Dispatch.class, id);
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
            List<Dispatch> Dispatchs = importExcel(file, Dispatch.class);
            for (Dispatch dispatch : Dispatchs) {
                insert += sqlManager.insert(dispatch);
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
        List<Dispatch> mapList;
        if (ids == null || ids.isEmpty()) {
            mapList = sqlManager.all(Dispatch.class);
        } else {
            mapList = selectByIds(Dispatch.class, ids);
        }
        try {
            exportExcel("Dispatch", mapList,Dispatch.class);
        } catch (Exception e) {
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

}
