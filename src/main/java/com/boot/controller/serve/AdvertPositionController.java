package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import com.boot.controller.system.BaseController;
import com.boot.model.AdvertPosition;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenjiang
 */
@Controller
@RequestMapping("/advertPosition")
public class AdvertPositionController extends BaseController{

    private static final String BASE_PATH = "advertPosition";
    private static final String LIST = "advertPosition.list";

    @RequestMapping("/")
	public String index() {
		return BASE_PATH+"/advertPosition_list";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST,httpServletRequest);
        return map;
    }

    @ResponseBody
    @RequestMapping("/advertPositionList")
    public Object advertPositionList(HttpServletRequest httpServletRequest) {
        List<Map>  list = sqlManager.select(LIST,Map.class);
        return list;
    }

    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/advertPosition_add";
    }

     @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        return BASE_PATH + "/advertPosition_edit";
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        AdvertPosition AdvertPosition = sqlManager.single(AdvertPosition.class,id);
        return AdvertPosition;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        AdvertPosition advertposition = mapping(AdvertPosition.class,request);
        if(advertposition == null){
            return fail("没有接收到参数，请重新提交");
        }
        if(PublicClass.isNull(advertposition.getTitle())){
            return fail("标题不能为空");
        }
        if(PublicClass.isNull(advertposition.getDefaultCover())){
            return fail("默认封面不能为空");
        }
        if(PublicClass.isNull(advertposition.getId()+"")){
            //新增
            return sqlManager.insert(AdvertPosition.class,advertposition)>0?success(SUCCESS):fail(FAIL);
        }else {
            //修改
            return sqlManager.updateById(advertposition)>0?success(SUCCESS):fail(FAIL);
        }
    }

    @ResponseBody
    @RequestMapping("/delete")
    public AjaxResult delete(HttpServletRequest httpServletRequest) {
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        int deleteById = 0;
        for (String s : parameterMap.keySet()) {
            String id = httpServletRequest.getParameter(s);
            deleteById += sqlManager.deleteById(AdvertPosition.class, id);
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
                AdvertPosition single = sqlManager.single(AdvertPosition.class, id);
                /*if (flag.equals("true")){
                    single.setIsEnabled(true);
                }else {
                    single.setIsEnabled(false);
                }*/
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
            List<AdvertPosition> AdvertPositions = importExcel(file, AdvertPosition.class);
            for (AdvertPosition advertPosition : AdvertPositions) {
                insert += sqlManager.insert(advertPosition);
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
            mapList = sqlManager.select("advertPosition.list",Map.class);
        }else {
            mapList = appendToList("advertPosition.list",
                    SqlIntercepter.create().set("WHERE FIND_IN_SET(Id,#{ids})"),
                    Dict.create().set("ids", ids));
        }
        try {
            simpleExport("AdvertPosition", mapList );
        }catch (Exception e){
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }


    @ResponseBody
    @RequestMapping("/queryPosition")
    public Object queryPosition(HttpServletRequest request) {
        List<AdvertPosition> list =  new ArrayList<>();
        if(!PublicClass.isNull(request.getParameter("PositionId"))){
            Map map = new HashMap();
            map.put("id",request.getParameter("PositionId"));
            list = sqlManager.select("advertPosition.findOne", AdvertPosition.class,map);
        }
        return list;
    }
}
