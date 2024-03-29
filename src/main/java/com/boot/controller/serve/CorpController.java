package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import com.boot.controller.system.BaseController;
import com.boot.model.Corp;
import com.boot.model.Loginaccount;
import com.boot.util.AjaxResult;
import com.boot.util.Md5Util;
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
@RequestMapping("/corp")
public class CorpController extends BaseController{

    private static final String BASE_PATH = "corp";
    private static final String LIST = "corp.list";

    @RequestMapping("/")
	public String index() {
		return BASE_PATH+"/corp_list";
	}

	@ResponseBody
    @RequestMapping("/list")
    public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST,httpServletRequest);
        return map;
    }

    @ResponseBody
    @RequestMapping("/corpList")
    public Object corpList(HttpServletRequest httpServletRequest) {
        List<Map> list = sqlManager.select(LIST,Map.class);
        //Map map = pageQuery(LIST,httpServletRequest);
        return list;
    }

    @ResponseBody
    @RequestMapping("/zxlx")
    public Object zxlx(HttpServletRequest httpServletRequest) {
        List<Map> list = sqlManager.select("corp.findZxlx",Map.class);
        return list;
    }

    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/corp_add";
    }

     @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        return BASE_PATH + "/corp_edit";
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        /*Corp Corp = sqlManager.single(Corp.class,id);
        return Corp;*/
        Map map = new HashMap();
        map.put("id",id);
       Map corpMap = sqlManager.selectSingle("corp.findOne",map,Map.class);
       return corpMap;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        Corp model = mapping(Corp.class, request);
        Loginaccount loginaccount = mapping(Loginaccount.class, request);
        loginaccount.setRealName(model.getContactor());
        int result;
        if (model.getId() == null) {
            Map loginMap = sqlManager.selectSingle("loginaccount.findByUserName",Dict.create().
                    set("UserName",loginaccount.getUserName()),Map.class);
            if(loginMap != null && loginMap.size()>0){
                  return fail("登录账户已存在");
            }else {
                //添加单位登录信息
                loginaccount.setIsEnabled(false);
                loginaccount.setUserTypeId(4);
                loginaccount.setCreateDate(new Date());
                String salt = Md5Util.getInstance().getSalt();
                String md5 = Md5Util.getInstance().MD5(loginaccount.getPassWord(), salt);
                loginaccount.setPassWord(md5);
                loginaccount.setSalt(salt);
                boolean isOk = sqlManager.insert(loginaccount)>0;
                if(isOk){
                    Loginaccount login = sqlManager.selectSingle("loginaccount.findByUserName",Dict.create().
                            set("UserName",loginaccount.getUserName()),Loginaccount.class);
                    if(model.getStatus() == null || model.getStatus() == 0){
                        model.setStatus(0);
                    }else{
                        model.setExamineDate(new Date());
                        model.setExamineUserId(ShiroUtils.getInstence().getUser().getId());
                    }
                    model.setUserId(login.getId());
                    result = sqlManager.insert(model);
                }else{
                    return fail("单位登录信息添加失败");
                }
            }
        } else {
            Corp corp = sqlManager.single(Corp.class,model.getId());
            if(model.getStatus() == null){
                model.setStatus(corp.getStatus());
                model.setExamineRemark(corp.getExamineRemark());
                model.setExamineDate(corp.getExamineDate());
                model.setExamineUserId(corp.getExamineUserId());
            }else{
                model.setExamineDate(new Date());
                model.setExamineUserId(ShiroUtils.getInstence().getUser().getId());
            }
            model.setUserId(corp.getUserId());
            result = sqlManager.updateById(model);
            if(result > 0){
                String loginId = request.getParameter("loginId");
                if(loginId != null && !"".equals(loginId) && !"null".equals(loginId)){
                    Loginaccount loginaccount1 = sqlManager.selectSingle("loginaccount.findIsRepeat",Dict.create().
                            set("UserName",loginaccount.getUserName()).set("id",loginId),Loginaccount.class);
                    if(loginaccount1 != null){
                        return fail("登录账户已存在");
                    }else{
                        //修改
                        Loginaccount login = sqlManager.single(Loginaccount.class,loginId);
                        login.setRealName(loginaccount.getRealName());
                        login.setUserName(loginaccount.getUserName());
                        if(!login.getPassWord().equals(loginaccount.getPassWord())){
                            String salt = Md5Util.getInstance().getSalt();
                            String md5 = Md5Util.getInstance().MD5(loginaccount.getPassWord(), salt);
                            login.setPassWord(md5);
                            login.setSalt(salt);
                        }
                        boolean isOk = sqlManager.updateById(login)>0;
                        return isOk? success(SUCCESS) : fail("单位登录信息更新失败");
                    }
                }
            }
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
            deleteById += sqlManager.deleteById(Corp.class, id);
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
                Corp single = sqlManager.single(Corp.class, id);
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


    /**
     * 审核
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @RequestMapping("/toExamine")
    public AjaxResult toExamine(HttpServletRequest httpServletRequest) {
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        int updateById = 0;
        for (String s : parameterMap.keySet()) {
            if (!s.equals("flag") && !s.equals("opinion")){
                String id = httpServletRequest.getParameter(s);
                String flag = httpServletRequest.getParameter("flag");
                String opinion = httpServletRequest.getParameter("opinion");
                Corp single = sqlManager.single(Corp.class, id);
                //审核通过
                if (flag.equals("true")){
                    single.setStatus(1);
                }else {
                    //审核不通过
                    single.setStatus(2);
                }
                single.setExamineRemark(opinion);
                single.setExamineDate(new Date());
                single.setExamineUserId(ShiroUtils.getInstence().getUser().getId());
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
            List<Corp> Corps = importExcel(file, Corp.class);
            for (Corp corp : Corps) {
                insert += sqlManager.insert(corp);
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
        List<Corp> mapList;
        if (ids == null || ids.isEmpty()) {
            mapList = sqlManager.all(Corp.class);
        } else {
            mapList = selectByIds(Corp.class, ids);
        }
        try {
            exportExcel("Corp", mapList,Corp.class);
        } catch (Exception e) {
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

}
