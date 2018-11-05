package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import com.boot.controller.system.BaseController;
import com.boot.model.Dictionary;
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
@RequestMapping("/dictionary")
public class DictionaryController extends BaseController{

    private static final String BASE_PATH = "dictionary";
    private static final String LIST = "dictionary.list";

    @RequestMapping("/")
    public String index() {
        return BASE_PATH+"/dictionary_list";
    }

    @ResponseBody
    @RequestMapping("/list")
    public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST, httpServletRequest);
        return map;
    }

    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/dictionary_add";
    }

    @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        return BASE_PATH + "/dictionary_edit";
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        Dictionary Dictionary = sqlManager.single(Dictionary.class,id);
        return Dictionary;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        Dictionary model = mapping(Dictionary.class, request);
        int result;
        if (model.getId() == null) {
            Dictionary typeCode = sqlManager.query(Dictionary.class)
                    .andEq("TypeCode", model.getTypeCode())
                    .andEq("MemberValue", model.getMemberValue())
                    .single();
            if (ObjectUtil.isNotNull(typeCode)){
                return error("字典已存在");
            }
            result = sqlManager.insert(model);
        } else {
            Dictionary typeCode = sqlManager.query(Dictionary.class)
                    .andEq("TypeCode", model.getTypeCode())
                    .andEq("MemberValue", model.getMemberValue())
                    .andNotEq("Id", model.getId())
                    .single();
            if (ObjectUtil.isNotNull(typeCode)){
                return error("字典已存在");
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
    @RequestMapping("/delete")
    public AjaxResult delete(HttpServletRequest httpServletRequest) {
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        int deleteById = 0;
        for (String s : parameterMap.keySet()) {
            String id = httpServletRequest.getParameter(s);
            deleteById += sqlManager.deleteById(Dictionary.class, id);
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
        int err=0;
        for (String s : multiFileMap.keySet()) {
            MultipartFile file = request.getFile(s);
            List<Dictionary> Dictionarys = importExcel(file, Dictionary.class);
            for (Dictionary dictionary : Dictionarys) {
                err++;
                Dictionary typeCode = sqlManager.query(Dictionary.class)
                        .andEq("TypeCode", dictionary.getTypeCode()).single();
                if (ObjectUtil.isNotNull(typeCode)){
                    return error("第"+err+"行"+typeCode.getTypeCode()+"重复");
                }
            }
            for (Dictionary dictionary : Dictionarys) {
                insert += sqlManager.insert(dictionary);
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
        List<Dictionary> mapList;
        if (ids == null||ids.isEmpty()) {
            mapList = sqlManager.all(Dictionary.class);
        }else {
            mapList = selectByIds(Dictionary.class,ids);
        }
        try {
            exportExcel("字典信息", mapList ,Dictionary.class);
        }catch (Exception e){
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/dictionaryCode/{code}")
    public Object dictionaryCode(@PathVariable String code) {
        List<Dictionary> typeCode = sqlManager.select("dictionary.findDictionaryByTypeCode",
                Dictionary.class, Dict.create().set("TypeCode", code));
        return typeCode;
    }

}
