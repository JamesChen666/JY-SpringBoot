package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import com.boot.controller.system.BaseController;
import com.boot.model.Friendlink;
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
@RequestMapping("/friendLink")
public class FriendlinkController extends BaseController{

    private static final String BASE_PATH = "friendLink";
    private static final String LIST = "friendLink.list";

    @RequestMapping("/")
	public String index() {
		return BASE_PATH+"/friendLink_list";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST,httpServletRequest);
        return map;
    }

    /*@ResponseBody
    @RequestMapping("/list")
    public Object list(HttpServletRequest request) {
        String code = request.getParameter("typeCode");
        List<Map> list = null;
        if(PublicClass.isNull(code)){
            list = sqlManager.select(LIST, Map.class);
        }else{
            Map map = new HashMap();
            map.put("typeCode",code);
            list = sqlManager.select("friendlink.queryCodeList", Map.class,map);
        }
        return list;
    }*/

    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/friendLink_add";
    }

     @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        return BASE_PATH + "/friendLink_edit";
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        Friendlink FriendLink = sqlManager.single(Friendlink.class,id);
        return FriendLink;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        Friendlink friendlink = mapping(Friendlink.class,request);
        if(friendlink == null){
            return error("没有接收到参数，请重新提交");
        }
        //必填字段验证
        if(PublicClass.isNull(friendlink.getTitle())){
            return fail("标题不能为空");
        }
        if(PublicClass.isNull(friendlink.getTypeCode())){
            return fail("链接类型代码不能为空");
        }
        if(PublicClass.isNull(friendlink.getRedirectUrl())){
            return fail("跳转地址不能为空");
        }
        if(PublicClass.isNull(friendlink.getSort()+"")){
            return fail("请选择排序");
        }

        if(PublicClass.isNull(friendlink.getId()+"")){
            if(PublicClass.isNull(friendlink.getIsEnabled()+"")){
                friendlink.setIsEnabled(false);
            }
            //新增
            boolean isOk = sqlManager.insert(Friendlink.class,friendlink) > 0;
            return isOk?success(SUCCESS):fail(FAIL);
        }else {
            Friendlink friendlink1 = sqlManager.single(Friendlink.class,friendlink.getId());
            friendlink.setIsEnabled(friendlink1.getIsEnabled());
            //修改
            boolean isOk = sqlManager.updateById(friendlink) > 0;
            return isOk?success(SUCCESS):fail(FAIL);
        }
    }

    @ResponseBody
    @RequestMapping("/delete")
    public AjaxResult delete(HttpServletRequest httpServletRequest) {
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        int deleteById = 0;
        for (String s : parameterMap.keySet()) {
            String id = httpServletRequest.getParameter(s);
            deleteById += sqlManager.deleteById(Friendlink.class, id);
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
                Friendlink single = sqlManager.single(Friendlink.class, id);
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
            List<Friendlink> FriendLinks = importExcel(file, Friendlink.class);
            for (Friendlink friendLink : FriendLinks) {
                insert += sqlManager.insert(friendLink);
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
            mapList = sqlManager.select("friendLink.list",Map.class);
        }else {
            mapList = appendToList("friendLink.list",
                    SqlIntercepter.create().set("WHERE FIND_IN_SET(Id,#{ids})"),
                    Dict.create().set("ids", ids));
        }
        try {
            simpleExport("FriendLink", mapList );
        }catch (Exception e){
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

}
