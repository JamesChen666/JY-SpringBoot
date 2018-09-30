package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import com.boot.controller.system.BaseController;
import com.boot.model.Nav;
import com.boot.system.SqlIntercepter;
import com.boot.util.AjaxResult;
import com.boot.util.PublicClass;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenjiang
 */
@Controller
@RequestMapping("/nav")
public class NavController extends BaseController{

    private static final String BASE_PATH = "nav";
    private static final String LIST = "nav.list";

    @RequestMapping("/")
	public String index() {
		return BASE_PATH+"/nav_list";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST,httpServletRequest);
        return map;
    }

    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/nav_add";
    }

     @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        return BASE_PATH + "/nav_edit";
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        Nav Nav = sqlManager.single(Nav.class,id);
        return Nav;
    }

    @ResponseBody
    @RequestMapping("/queryParent")
    public Object queryParent(HttpServletRequest request) {
        List<Map> list = sqlManager.select("nav.navList",Map.class);
        return list;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        Nav nav = mapping(Nav.class,request);
        //必填字段验证
        if(nav == null){
            return fail("没有接收到参数，请重新提交");
        }
        if(PublicClass.isNull(nav.getTitle())){
            return fail("栏目名称不能为空");
        }
        if(PublicClass.isNull(nav.getTypeId()+"")){
            return fail("栏目类别不能为空");
        }
        if(PublicClass.isNull(nav.getParentId()+"")){
            return fail("父节点不能为空");
        }
        if(PublicClass.isNull(nav.getRedirectTypeId()+"")){
            return fail("打开方式不能为空");
        }
        if(PublicClass.isNull(nav.getSort()+"")){
            return fail("排序不能为空");
        }
        if(PublicClass.isNull(nav.getId()+"")){
            if(PublicClass.isNull(nav.getIsEnabled()+"")){
                nav.setIsEnabled(false);
            }
            //新增
            return sqlManager.insert(Nav.class,nav)>0?success(SUCCESS):fail(FAIL);
        }else {
            Nav nav1 =  sqlManager.single(Nav.class,nav.getId());
            if(PublicClass.isNull(nav.getIsEnabled()+"")){
                nav.setIsEnabled(nav1.getIsEnabled());
            }
            //修改
            return sqlManager.updateById(nav)>0?success(SUCCESS):fail(FAIL);
        }
    }

    @ResponseBody
    @RequestMapping("/delete")
    public AjaxResult delete(HttpServletRequest httpServletRequest) {
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        int deleteById = 0;
        for (String s : parameterMap.keySet()) {
            String id = httpServletRequest.getParameter(s);
            deleteById += sqlManager.deleteById(Nav.class, id);
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
                Nav single = sqlManager.single(Nav.class, id);
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
        for (String s : multiFileMap.keySet()) {
            MultipartFile file = request.getFile(s);
            List<Nav> Navs = importExcel(file, Nav.class);
            for (Nav nav : Navs) {
                insert += sqlManager.insert(nav);
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
            mapList = sqlManager.select("nav.list",Map.class);
        }else {
            mapList = appendToList("nav.list",
                    SqlIntercepter.create().set("WHERE FIND_IN_SET(Id,#{ids})"),
                    Dict.create().set("ids", ids));
        }
        try {
            simpleExport("Nav", mapList );
        }catch (Exception e){
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

}
