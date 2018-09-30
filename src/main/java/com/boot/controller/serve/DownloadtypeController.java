package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import com.boot.controller.system.BaseController;
import com.boot.model.Downloadtype;
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
@RequestMapping("/downloadType")
public class DownloadtypeController extends BaseController{

    private static final String BASE_PATH = "downloadType";
    private static final String LIST = "downloadType.list";

    @RequestMapping("/")
	public String index() {
		return BASE_PATH+"/downloadType_list";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST,httpServletRequest);
        return map;
    }

    @ResponseBody
    @RequestMapping("/downloadTypeList")
    public Object downloadTypeList(HttpServletRequest httpServletRequest) {
      List<Map> list = sqlManager.select(LIST,Map.class);
      return list;
    }

    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/downloadType_add";
    }

     @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        return BASE_PATH + "/downloadType_edit";
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        Downloadtype DownloadType = sqlManager.single(Downloadtype.class,id);
        return DownloadType;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        Downloadtype model = mapping(Downloadtype.class, request);
        if(model != null){
            if(PublicClass.isNull(model.getId()+"")){
                model.setIsEnabled(false);
                //新增
                return sqlManager.insert(model)>0?success(SUCCESS):fail(FAIL);
            }else{
                Downloadtype downloadtype = sqlManager.single(Downloadtype.class,model.getId());
                model.setIsEnabled(downloadtype.getIsEnabled());
                //修改
                return sqlManager.updateById(model)>0?success(SUCCESS):fail(FAIL);
            }
        }else{
            return fail("没有接收到参数，请重新提交");
        }
    }

    @ResponseBody
    @RequestMapping("/delete")
    public AjaxResult delete(HttpServletRequest httpServletRequest) {
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        int deleteById = 0;
        for (String s : parameterMap.keySet()) {
            String id = httpServletRequest.getParameter(s);
            deleteById += sqlManager.deleteById(Downloadtype.class, id);
        }
        if (deleteById <= 0) {
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/queryCode")
    public Object queryCode(HttpServletRequest request) {
        Map map = new HashMap();
        String typeCode = request.getParameter("TypeCode");
        if(!PublicClass.isNull(typeCode)){
            map.put("typeCode",typeCode);
            return sqlManager.select("downloadtype.queryCode",Map.class,map);
        }
        return null;
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
                Downloadtype single = sqlManager.single(Downloadtype.class, id);
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
            List<Downloadtype> DownloadTypes = importExcel(file, Downloadtype.class);
            for (Downloadtype downloadType : DownloadTypes) {
                insert += sqlManager.insert(downloadType);
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
            mapList = sqlManager.select("downloadType.list",Map.class);
        }else {
            mapList = appendToList("downloadType.list",
                    SqlIntercepter.create().set("WHERE FIND_IN_SET(Id,#{ids})"),
                    Dict.create().set("ids", ids));
        }
        try {
            simpleExport("DownloadType", mapList );
        }catch (Exception e){
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

}
