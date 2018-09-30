package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import com.boot.controller.system.BaseController;
import com.boot.model.Noticetype;
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
@RequestMapping("/noticeType")
public class NoticetypeController extends BaseController{

    private static final String BASE_PATH = "noticeType";
    private static final String LIST = "noticeType.list";

    @RequestMapping("/")
	public String index() {
		return BASE_PATH+"/noticeType_list";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST,httpServletRequest);
        return map;
    }

    @ResponseBody
    @RequestMapping("/noticeTypeList")
    public Object noticeTypeList(HttpServletRequest httpServletRequest) {
      List<Map> list = sqlManager.select(LIST,Map.class);
      return list;
    }

    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/noticeType_add";
    }

     @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        return BASE_PATH + "/noticeType_edit";
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        Noticetype NoticeType = sqlManager.single(Noticetype.class,id);
        return NoticeType;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        Noticetype noticetype = mapping(Noticetype.class,request);
        if(noticetype == null){
            return fail("没有接收到参数，请重新提交");
        }
        //必填参数验证
        if(PublicClass.isNull(noticetype.getTypeCode())){
            return fail("公告类型不能为空");
        }
        if(PublicClass.isNull(noticetype.getTypeName())){
            return fail("公告名称不能为空");
        }
        if(PublicClass.isNull(noticetype.getSort()+"")){
            return fail("请选择排序");
        }

        if(PublicClass.isNull(noticetype.getId()+"")){
            if(PublicClass.isNull(noticetype.getIsEnabled()+"")){
                noticetype.setIsEnabled(false);
            }
            //新增
            boolean isOk =  sqlManager.insert(Noticetype.class,noticetype) > 0;
            return isOk?success(SUCCESS) : fail(FAIL);
        }else{
            Noticetype noticetype1 = sqlManager.single(Noticetype.class,noticetype.getId());
            noticetype.setIsEnabled(noticetype1.getIsEnabled());
            //修改
            boolean isOk = sqlManager.updateById(noticetype) > 0;
            return  isOk ? success(SUCCESS):fail(FAIL);
        }
    }

    @ResponseBody
    @RequestMapping("/delete")
    public AjaxResult delete(HttpServletRequest httpServletRequest) {
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        int deleteById = 0;
        for (String s : parameterMap.keySet()) {
            String id = httpServletRequest.getParameter(s);
            deleteById += sqlManager.deleteById(Noticetype.class, id);
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
                Noticetype single = sqlManager.single(Noticetype.class, id);
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
            List<Noticetype> NoticeTypes = importExcel(file, Noticetype.class);
            for (Noticetype noticeType : NoticeTypes) {
                insert += sqlManager.insert(noticeType);
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
            mapList = sqlManager.select("noticeType.list",Map.class);
        }else {
            mapList = appendToList("noticeType.list",
                    SqlIntercepter.create().set("WHERE FIND_IN_SET(Id,#{ids})"),
                    Dict.create().set("ids", ids));
        }
        try {
            simpleExport("NoticeType", mapList );
        }catch (Exception e){
            e.getStackTrace();
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
            return sqlManager.select("noticetype.queryCode",Map.class,map);
        }
        return null;
    }

}
