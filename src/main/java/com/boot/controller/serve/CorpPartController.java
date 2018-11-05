package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import com.boot.controller.system.BaseController;
import com.boot.model.*;
import com.boot.util.AjaxResult;
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
import java.util.*;

/**
 * @author chenjiang
 */
@Controller
@RequestMapping("/corpPart")
public class CorpPartController extends BaseController{

    private static final String BASE_PATH = "corpPart";
    private static final String LIST = "corpPart.list";

    @RequestMapping("/")
	public String index() {
		return BASE_PATH+"/corpPart_list";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST,httpServletRequest);
        return map;
    }

    @ResponseBody
    @RequestMapping("/corpPartList")
    public Object corpPartList(HttpServletRequest httpServletRequest) {
        List<Map> list = sqlManager.select(LIST,Map.class);
        return list;
    }

    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/corpPart_add";
    }

     @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        return BASE_PATH + "/corpPart_edit";
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        /*CorpPart CorpPart = sqlManager.single(CorpPart.class,id);
        return CorpPart;*/
        Map map = sqlManager.selectSingle("corpPart.queryBoothIdandPositionId",Dict.create().set("id",id),Map.class);
        return map;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        CorpPart model = mapping(CorpPart.class, request);
        int result;
        if (model.getId() == null) {
            if(model.getStatus() == null || model.getStatus() == 0){
                model.setStatus(0);
            }else{
                model.setExamineDate(new Date());
                model.setExamineUserId(ShiroUtils.getInstence().getUser().getId());
            }
            result = sqlManager.insert(model);
            if(result>0){
                //查询出刚刚添加的数据
                CorpPart corpPart = sqlManager.selectSingle("corpPart.lastOne",new HashMap<>(),CorpPart.class);
                //添加职位关联
                addCorpPosition(request,corpPart.getId());
                //添加展位关联
                addCorpBooth(request,corpPart.getId());
            }
        } else {
            //查询出该条双选会参与单位对应的展位与职位
           Map map = sqlManager.selectSingle("corpPart.queryBoothIdandPositionId",Dict.create().set("id",model.getId()),Map.class);
            //对比展位与职位是否有修改
            if(map!= null && map.get("boothId") !=null && !map.get("boothId").equals(request.getParameter("BoothId"))){
                String[] ecbIds = (map.get("ecbId")+"").split(",");
                //删除展位关联
                for (int i = 0; i <ecbIds.length ; i++) {
                    sqlManager.deleteById(CorpBooth.class,Integer.parseInt(ecbIds[i]));
                }
                addCorpBooth(request,model.getId());
            }
            if(map!= null && map.get("positionId")!=null && !map.get("positionId").equals(request.getParameter("PositionId"))){
                String[] ecpIds = (map.get("ecpId")+"").split(",");
                //删除职位关联
                for (int i = 0; i <ecpIds.length ; i++) {
                    sqlManager.deleteById(CorpPosition.class,Integer.parseInt(ecpIds[i]));
                }
                addCorpPosition(request,model.getId());
            }
            CorpPart corpPart = sqlManager.single(CorpPart.class,model.getId());
            if(model.getStatus() == null){
                model.setExamineRemark(corpPart.getExamineRemark());
                model.setExamineDate(corpPart.getExamineDate());
                model.setExamineUserId(corpPart.getExamineUserId());
                model.setStatus(corpPart.getStatus());
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
    @RequestMapping("/delete")
    public AjaxResult delete(HttpServletRequest httpServletRequest) {
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        int deleteById = 0;
        for (String s : parameterMap.keySet()) {
            String id = httpServletRequest.getParameter(s);
            deleteById += sqlManager.deleteById(CorpPart.class, id);
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
                CorpPart single = sqlManager.single(CorpPart.class, id);
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
                CorpPart single = sqlManager.single(CorpPart.class, id);
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
            List<CorpPart> CorpParts = importExcel(file, CorpPart.class);
            for (CorpPart corpPart : CorpParts) {
                insert += sqlManager.insert(corpPart);
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
        List<CorpPart> mapList;
        if (ids == null || ids.isEmpty()) {
            mapList = sqlManager.all(CorpPart.class);
        } else {
            mapList = selectByIds(CorpPart.class, ids);
        }
        try {
            exportExcel("CorpPart", mapList,CorpPart.class);
        } catch (Exception e) {
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

    /**
     * 添加展位关联
     * @param request
     * @param id
     */
    public void addCorpBooth(HttpServletRequest request,Integer id){
        //添加展位关联
        CorpBooth corpBooth = new CorpBooth();
        String[] boothIds = request.getParameter("BoothId").split(",");
        for (int i = 0; i < boothIds.length; i++) {
            corpBooth.setElectionCorpId(id);
            corpBooth.setBoothId(Integer.parseInt(boothIds[i]));
            sqlManager.insert(corpBooth);
        }
    }

    /**
     * 添加职位关联
     * @param request
     * @param id
     */
    public void addCorpPosition(HttpServletRequest request,Integer id){
        //添加职位
        CorpPosition corpPosition = new CorpPosition();
        String[] positionIds = request.getParameter("PositionId").split(",");
        for (int i = 0; i < positionIds.length; i++) {
            corpPosition.setElectionCorpId(id);
            corpPosition.setPositionId(Integer.parseInt(positionIds[i]));
            sqlManager.insert(corpPosition);
        }
    }

    @ResponseBody
    @RequestMapping("/applyElection")
    public Object applyElection(HttpServletRequest request){
        CorpPart model = mapping(CorpPart.class, request);
        int result;
        model.setStatus(0);
        model.setApplyDate(new Date());
        //判断是否已申请
        Map map = sqlManager.selectSingle("corpPart.isExist",Dict.create().set("corpId",model.getCorpId()).set("electionId",model.getElectionId()),Map.class);
        if(map != null && map.size()>0){
            return fail("已申请过，请勿重复申请");
        }
        result = sqlManager.insert(model);
        if(result>0){
            return success(SUCCESS);
        }else{
            return fail(FAIL);
        }
    }

}
