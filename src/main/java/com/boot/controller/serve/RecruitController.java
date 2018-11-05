package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import com.boot.controller.system.BaseController;
import com.boot.model.Corp;
import com.boot.model.Position;
import com.boot.model.Recruit;
import com.boot.util.AjaxResult;
import com.boot.util.ShiroUtils;
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
import java.util.*;

/**
 * @author chenjiang
 */
@Controller
@RequestMapping("/recruit")
public class RecruitController extends BaseController{

    private static final String BASE_PATH = "recruit";
    private static final String LIST = "recruit.list";

    @RequestMapping("/")
	public String index() {
		return BASE_PATH+"/recruit_list";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST,httpServletRequest);
        return map;
    }

	@ResponseBody
	@RequestMapping("/stuList")
	public Object stuList(HttpServletRequest httpServletRequest) {
        List<Map> mapList = sqlManager.select("recruit.stuList", Map.class);
        return mapList;
    }

    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/recruit_add";
    }

     @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        return BASE_PATH + "/recruit_edit";
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        /*Recruit Specia = sqlManager.single(Recruit.class,id);
        return Specia;*/ //GROUP_CONCAT(sp.PositionId SEPARATOR ',') as PositionId
       Map speciaMap = sqlManager.selectSingle("recruit.findOne",Dict.create().set("id",id),Map.class);
       return speciaMap;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        Recruit model = mapping(Recruit.class, request);
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
            //查询出该
            Recruit specia = sqlManager.single(Recruit.class,model.getId());
            if(model.getStatus() == null){
                model.setStatus(specia.getStatus());
                model.setExamineUserId(specia.getExamineUserId());
                model.setExamineDate(specia.getExamineDate());
                model.setExamineRemark(specia.getExamineRemark());
            }else{
                model.setExamineDate(new Date());
                model.setExamineUserId(ShiroUtils.getInstence().getUser().getId());
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
    @RequestMapping("/saveRecruit")
    public AjaxResult saveRecruit(HttpServletRequest request) {
        Recruit model = mapping(Recruit.class, request);
        //查询出单位id
        Corp corp = sqlManager.selectSingle("corp.findCorpName",Dict.create().set("corpName",ShiroUtils.getInstence().getUser().getUserName()),Corp.class);
        if(corp != null){
            model.setCorpId(corp.getId());
        }
        int result =0;
        if (model.getId() == null) {
            if(model.getStatus() == null || model.getStatus() == 0){
                model.setStatus(0);
            }else{
                model.setExamineDate(new Date());
                model.setExamineUserId(ShiroUtils.getInstence().getUser().getId());
            }
            result = sqlManager.insert(model);
        }
        if (result > 0) {
            //查询出刚刚添加的招聘信息
            Recruit recruit = sqlManager.selectSingle("recruit.lastOne",new HashMap<>(),Recruit.class);
            //添加职位
            String positionIds = request.getParameter("positionId");
            JSONArray array = JSONArray.fromObject(positionIds);
            List list =(ArrayList)JSONArray.toCollection(array, String.class);
            for (int i = 0; i <list.size() ; i++) {
                Position position = new Position();
                position.setSpecialId(recruit.getId());
                position.setPositionId(Integer.parseInt(list.get(i)+""));
                result = sqlManager.insert(position);
            }
            if(result >0){
                return success(SUCCESS);
            }else{
                //删除新添加的信息
                sqlManager.deleteById(Recruit.class, recruit.getId());
                return fail(FAIL);
            }
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
            deleteById += sqlManager.deleteById(Recruit.class, id);
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
                Recruit single = sqlManager.single(Recruit.class, id);
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
                Recruit single = sqlManager.single(Recruit.class, id);
                //审核通过
                if (flag.equals("true")){
                    single.setStatus(1);
                    //查询出使用的场地
                   Map map = sqlManager.selectSingle("recruit.queryPlace",Dict.create().set("id",id),Map.class);
                    if(map!=null && "2".equals(map.get("TypeId")+"")){
                        //修改场地为不可用
                        sqlManager.update("place.updatePlace",Dict.create().set("id",map.get("Id")));
                    }
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
            List<Recruit> Specias = importExcel(file, Recruit.class);
            for (Recruit specia : Specias) {
                insert += sqlManager.insert(specia);
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
        List<Recruit> mapList;
        if (ids == null || ids.isEmpty()) {
            mapList = sqlManager.all(Recruit.class);
        } else {
            mapList = selectByIds(Recruit.class, ids);
        }
        try {
            exportExcel("Recruit", mapList,Recruit.class);
        } catch (Exception e) {
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

}
