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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenjiang
 */
@Controller
@RequestMapping("/election")
public class ElectionController extends BaseController{

    private static final String BASE_PATH = "election";
    private static final String LIST = "election.list";

    @RequestMapping("/")
	public String index() {
		return BASE_PATH+"/election_list";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST,httpServletRequest);
        return map;
    }


    @ResponseBody
    @RequestMapping("/electionList")
    public Object electionList() {
        List<Map> list = sqlManager.select(LIST,Map.class);
        return list;
    }

    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/election_add";
    }

     @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        return BASE_PATH + "/election_edit";
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        /*Election Election = sqlManager.single(Election.class,id);
        return Election;*/
        Map map = sqlManager.selectSingle("election.findOne",Dict.create().set("id",id),Map.class);
        return map;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        Election model = mapping(Election.class, request);
        int result;
        model.setUserId(ShiroUtils.getInstence().getUser().getId());
        model.setCreateDate(new Date());
        if (model.getId() == null) {
            model.setIsEnabled(false);
            result = sqlManager.insert(model);
            if(result >0){
                //查询出刚刚添加的数据
               Election election = sqlManager.selectSingle("election.lastOne",new HashMap<>(),Election.class);
                //添加面向行业
               Industry industry = new Industry();
                String[] industryIds = request.getParameter("IndustryCode").split(",");
                for (int i = 0; i < industryIds.length; i++) {
                    industry.setElectionId(election.getId());
                    industry.setIndustryCode(industryIds[i]);
                    sqlManager.insert(industry);
                }
                //添加场地
                RunPlace runPlace = new RunPlace();
                String[] placeIds = request.getParameter("PlaceId").split(",");
                for (int i = 0; i < industryIds.length; i++) {
                    runPlace.setElectionId(election.getId());
                    runPlace.setPlaceId(Integer.parseInt(placeIds[i]));
                    sqlManager.insert(runPlace);
                }
                //添加职位
                CorpPosition corpPosition = new CorpPosition();
                String[] positionIds = request.getParameter("PositionId").split(",");
                for (int i = 0; i < industryIds.length; i++) {
                    corpPosition.setElectionCorpId(election.getId());
                    corpPosition.setPositionId(Integer.parseInt(positionIds[i]));
                    sqlManager.insert(corpPosition);
                }
                //添加展位
                CorpBooth corpBooth = new CorpBooth();
                String[] boothIds = request.getParameter("BoothId").split(",");
                for (int i = 0; i < industryIds.length; i++) {
                    corpBooth.setElectionCorpId(election.getId());
                    corpBooth.setBoothId(Integer.parseInt(boothIds[i]));
                    sqlManager.insert(corpBooth);
                }
            }
        } else {
            Election election = sqlManager.single(Election.class,model.getId());
            model.setIsEnabled(election.getIsEnabled());
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
            deleteById += sqlManager.deleteById(Election.class, id);
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
                Election single = sqlManager.single(Election.class, id);
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
            List<Election> Elections = importExcel(file, Election.class);
            for (Election election : Elections) {
                insert += sqlManager.insert(election);
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
        List<Election> mapList;
        if (ids == null || ids.isEmpty()) {
            mapList = sqlManager.all(Election.class);
        } else {
            mapList = selectByIds(Election.class, ids);
        }
        try {
            exportExcel("Election", mapList,Election.class);
        } catch (Exception e) {
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

}
