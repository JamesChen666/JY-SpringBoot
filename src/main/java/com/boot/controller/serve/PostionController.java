package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import com.boot.controller.system.BaseController;
import com.boot.model.Corp;
import com.boot.model.Postion;
import com.boot.model.PostionSpecialty;
import com.boot.util.AjaxResult;
import com.boot.util.ShiroUtils;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author chenjiang
 */
@Controller
@RequestMapping("/postion")
public class PostionController extends BaseController{

    private static final String BASE_PATH = "postion";
    private static final String LIST = "postion.list";

    @RequestMapping("/")
	public String index() {
		return BASE_PATH+"/postion_list";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST,httpServletRequest);
        return map;
    }


    @ResponseBody
    @RequestMapping("/postionList")
    public Object postionList(HttpServletRequest httpServletRequest) {
        List<Map> list = sqlManager.select(LIST,Map.class);
        return list;
    }

    /**
     * 某个单位的职位列表
     * @param httpServletRequest
     * @return
     */
    @ResponseBody
    @RequestMapping("/cropPostionList")
    public Object cropPostionList(HttpServletRequest httpServletRequest) {
        /*List<Map> list = new ArrayList<>();
        if(httpServletRequest.getParameter("title") !=null){
            list = sqlManager.select("postion.findCorpPostionName",Map.class,Dict.create().
                    set("corpId",httpServletRequest.getParameter("id")).set("title",httpServletRequest.getParameter("title")));
        }else{
            list = sqlManager.select("",Map.class,Dict.create().set("corpId",httpServletRequest.getParameter("id")));
        }*/
        Map map = pageQuery("postion.findCorpPostionList",httpServletRequest);
        return map;
    }

    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/postion_add";
    }

     @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        return BASE_PATH + "/postion_edit";
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        Map map = sqlManager.selectSingle("postion.findOne",Dict.create().set("id",id),Map.class);
        //Postion Postion = sqlManager.single(Postion.class,id);
        return map;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        List<String> list = new ArrayList<>();
        Postion model = mapping(Postion.class, request);
        String specialtyCodes = request.getParameter("specialtyCodes");
        if(specialtyCodes != null){
            JSONArray array = JSONArray.fromObject(specialtyCodes);
            list =(ArrayList)JSONArray.toCollection(array, String.class);
        }else {
            if(request.getParameter("SpecialtyCode")!=null){
                list.add(request.getParameter("SpecialtyCode"));
            }
        }
        int result;
        if (model.getId() == null) {
            if(model.getStatus() == null || model.getStatus() == 0){
                model.setStatus(0);
            }else{
                model.setExamineDate(new Date());
                model.setExamineUserId(ShiroUtils.getInstence().getUser().getId());
            }
            result = sqlManager.insert(model);
        } else {
            Postion postion = sqlManager.single(Postion.class,model.getId());
            if(model.getStatus() == null){
                model.setStatus(postion.getStatus());
                model.setExamineRemark(postion.getExamineRemark());
                model.setExamineDate(postion.getExamineDate());
                model.setExamineUserId(postion.getExamineUserId());
            }else{
                model.setExamineDate(new Date());
                model.setExamineUserId(ShiroUtils.getInstence().getUser().getId());
            }
            result = sqlManager.updateById(model);
        }
        if (result > 0) {
            if(model.getId() ==null){
                //查询出新添加的数据
                Postion postion = sqlManager.selectSingle("postion.lastOne","",Postion.class);
                model.setId(postion.getId());
            }
            //删除之前数据
            sqlManager.update("postionSpecialty.deleteSpecialty",Dict.create().set("PositionId",model.getId()));
            //添加新数据
            for (int i = 0; i < list.size(); i++) {
                PostionSpecialty postionSpecialty = new PostionSpecialty();
                postionSpecialty.setPositionId(model.getId());
                postionSpecialty.setSpecialtyCode(list.get(i));
                sqlManager.insert(postionSpecialty);
            }
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
            deleteById += sqlManager.deleteById(Postion.class, id);
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
                Postion single = sqlManager.single(Postion.class, id);
               /* if (flag.equals("true")){
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
                Postion single = sqlManager.single(Postion.class, id);
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
            List<Postion> Postions = importExcel(file, Postion.class);
            for (Postion postion : Postions) {
                insert += sqlManager.insert(postion);
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
        List<Postion> mapList;
        if (ids == null || ids.isEmpty()) {
            mapList = sqlManager.all(Postion.class);
        } else {
            mapList = selectByIds(Postion.class, ids);
        }
        try {
            exportExcel("Postion", mapList,Postion.class);
        } catch (Exception e) {
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

}
