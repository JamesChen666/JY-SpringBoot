package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import com.boot.controller.system.BaseController;
import com.boot.model.Dispatchunit;
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
@RequestMapping("/dispatchunit")
public class DispatchunitController extends BaseController{

    private static final String BASE_PATH = "dispatchunit";
    private static final String LIST = "dispatchunit.list";

    @RequestMapping("/")
    public String index() {
        return BASE_PATH+"/dispatchunit_list";
    }

    @ResponseBody
    @RequestMapping("/list")
    public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST, httpServletRequest);
        return map;
    }

    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/dispatchunit_add";
    }

    @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        return BASE_PATH + "/dispatchunit_edit";
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        Dispatchunit Dispatchunit = sqlManager.single(Dispatchunit.class,id);
        return Dispatchunit;
    }

    @RequestMapping("/view/{id}")
    public String view(@PathVariable Integer id,ModelMap modelMap) {
        Map map = sqlManager.selectSingle("dispatchunit.findOne",
                Dict.create().set("Id", id), Map.class);
        modelMap.put("dispatchunit", map);
        return BASE_PATH + "/dispatchunit_view";
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        Dispatchunit model = mapping(Dispatchunit.class, request);
        int result;
        if (model.getId() == null) {
            Dispatchunit providerName = sqlManager.query(Dispatchunit.class)
                    .andEq("ProviderName", model.getProviderName()).single();
            if (ObjectUtil.isNotNull(providerName)) {
                return error("单位已存在");
            }
            model.setIsEnabled(true);
            result = sqlManager.insert(model);
        } else {
            Dispatchunit providerName = sqlManager.query(Dispatchunit.class)
                    .andEq("ProviderName", model.getProviderName())
                    .andNotEq("Id", model.getId())
                    .single();
            if (ObjectUtil.isNotNull(providerName)) {
                return error("单位已存在");
            }
            Dispatchunit dispatchunit = sqlManager.single(Dispatchunit.class, model.getId());
            model.setIsEnabled(dispatchunit.getIsEnabled());
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
            deleteById += sqlManager.deleteById(Dispatchunit.class, id);
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
            if (!s.equals("flag")){
                String id = httpServletRequest.getParameter(s);
                String flag = httpServletRequest.getParameter("flag");
                Dispatchunit single = sqlManager.single(Dispatchunit.class, id);
                if (flag.equals("true")){
                    single.setIsEnabled(true);
                }else {
                    single.setIsEnabled(false);
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
        int err = 0;
        for (String s : multiFileMap.keySet()) {
            MultipartFile file = request.getFile(s);
            List<Dispatchunit> Dispatchunits = importExcel(file, Dispatchunit.class);
            for (Dispatchunit dispatchunit : Dispatchunits) {
                err++;
                Dispatchunit providerName = sqlManager.query(Dispatchunit.class)
                        .andEq("ProviderName", dispatchunit.getProviderName()).single();
                if (ObjectUtil.isNotNull(providerName)){
                    return error("第"+err+"行"+providerName.getProviderName()+"已存在");
                }
            }
            for (Dispatchunit dispatchunit : Dispatchunits) {
                dispatchunit.setIsEnabled(true);
                insert += sqlManager.insert(dispatchunit);
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
        List<Dispatchunit> mapList;
        if (ids == null||ids.isEmpty()) {
            mapList = sqlManager.all(Dispatchunit.class);
        }else {
            mapList = selectByIds(Dispatchunit.class,ids);
        }
        try {
            exportExcel("派遣单位信息", mapList,Dispatchunit.class );
        }catch (Exception e){
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

}
