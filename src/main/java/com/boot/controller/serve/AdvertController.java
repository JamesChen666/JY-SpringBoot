package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import com.boot.controller.system.BaseController;
import com.boot.model.Advert;
import com.boot.system.SqlIntercepter;
import com.boot.util.AjaxResult;
import com.boot.util.PublicClass;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenjiang
 */
@Controller
@RequestMapping("/advert")
public class AdvertController extends BaseController{

    private static final String BASE_PATH = "advert";
    private static final String LIST = "advert.list";

    @RequestMapping("/")
	public String index() {
		return BASE_PATH+"/advert_list";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST,httpServletRequest);
        return map;
    }

    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/advert_add";
    }

     @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        return BASE_PATH + "/advert_edit";
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        Advert Advert = sqlManager.single(Advert.class,id);
        return Advert;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        Advert advert = mapping(Advert.class,request);
        if(advert == null){
            return fail("没有接收到参数，请重新提交");
        }
        //必填字段验证
        if(PublicClass.isNull(advert.getPositionId()+"")){
            return fail("广告位置不能为空");
        }
        if(PublicClass.isNull(advert.getCover())){
            return fail("广告封面不能为空");
        }
        if(PublicClass.isNull(advert.getRedirectUrl())){
            return  fail("跳转链接不能为空");
        }
        advert.setUserId(ShiroUtils.getInstence().getUser().getId());
        advert.setCreateDate(new Date());
        if(PublicClass.isNull(advert.getId()+"")){
            if(PublicClass.isNull(advert.getIsEnabled()+"")){
                advert.setIsEnabled(false);
            }
            //新增
            return sqlManager.insert(Advert.class,advert)>0? success(SUCCESS):fail(FAIL);
        }else {
           Advert advert1 = sqlManager.single(Advert.class,advert.getId());
            if(PublicClass.isNull(advert.getIsEnabled()+"")){
                advert.setIsEnabled(advert1.getIsEnabled());
            }
            //修改
            return sqlManager.updateById(advert)>0?success(SUCCESS):fail(FAIL);
        }
    }

    @ResponseBody
    @RequestMapping("/delete")
    public AjaxResult delete(HttpServletRequest httpServletRequest) {
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        int deleteById = 0;
        for (String s : parameterMap.keySet()) {
            String id = httpServletRequest.getParameter(s);
            deleteById += sqlManager.deleteById(Advert.class, id);
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
                Advert single = sqlManager.single(Advert.class, id);
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
            List<Advert> Adverts = importExcel(file, Advert.class);
            for (Advert advert : Adverts) {
                insert += sqlManager.insert(advert);
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
            mapList = sqlManager.select("advert.list",Map.class);
        }else {
            mapList = appendToList("advert.list",
             SqlIntercepter.create().set("WHERE FIND_IN_SET(Id,#{ids})"),
                    Dict.create().set("ids", ids));
        }
        try {
            simpleExport("Advert", mapList );
        }catch (Exception e){
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }


}
