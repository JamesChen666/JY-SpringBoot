package com.boot.controller.serve;

import cn.hutool.core.util.ObjectUtil;
import com.boot.controller.system.BaseController;
import com.boot.model.Tag;
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
@RequestMapping("/tag")
public class TagController extends BaseController {

    private static final String BASE_PATH = "tag";
    private static final String LIST = "tag.list";

    @RequestMapping("/")
    public String index() {
        return BASE_PATH + "/tag_list";
    }

    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/tag_add";
    }

    @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        return BASE_PATH + "/tag_edit";
    }

    @ResponseBody
    @RequestMapping("/list")
    public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST, httpServletRequest);
        return map;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        Tag model = mapping(Tag.class, request);
        int result;
        if (model.getId() == null) {
            Tag title = sqlManager.query(Tag.class)
                    .andEq("Title", model.getTitle())
                    .single();
            if (ObjectUtil.isNotNull(title)) {
                return error("标签名称重复");
            }
            result = sqlManager.insert(model);
        } else {
            Tag title = sqlManager.query(Tag.class)
                    .andEq("Title", model.getTitle())
                    .andNotEq("Id", model.getId())
                    .single();
            if (ObjectUtil.isNotNull(title)) {
                return error("标签名称重复");
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
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        Tag Tag = sqlManager.single(Tag.class, id);
        return Tag;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public AjaxResult delete(HttpServletRequest httpServletRequest) {
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        int deleteById = 0;
        for (String s : parameterMap.keySet()) {
            String id = httpServletRequest.getParameter(s);
            deleteById += sqlManager.deleteById(Tag.class, id);
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
        int err = 0;
        for (String s : multiFileMap.keySet()) {
            MultipartFile file = request.getFile(s);
            List<Tag> tags = importExcel(file, Tag.class);
            for (Tag tag : tags) {
                err++;
                Tag title = sqlManager.query(Tag.class)
                        .andEq("Title", tag.getTitle()).single();
                if (ObjectUtil.isNotNull(title)) {
                    return error("第" + err + "行"+title.getTitle()+"重复");
                }

            }
            for (Tag tag : tags) {
                insert += sqlManager.insert(tag);
            }
        }
        if (insert <= 0) {
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/export")
    public AjaxResult exportExcel(HttpServletRequest httpServletRequest) {
        String ids = httpServletRequest.getParameter("ids");
        List<Tag> mapList;
        if (ids == null || ids.isEmpty()) {
            mapList = sqlManager.all(Tag.class);
        } else {
            mapList = selectByIds(Tag.class,ids);
        }
        try {
            exportExcel("标签信息", mapList,Tag.class);
        } catch (Exception e) {
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/queryTagList")
    public Object queryTagList(HttpServletRequest request){
        List<Map> list = sqlManager.select(LIST,Map.class);
        return list;
    }

}
