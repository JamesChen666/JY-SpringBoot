package com.boot.controller.serve;

import com.boot.controller.system.BaseController;
import com.boot.model.Position;
import com.boot.model.Postion;
import com.boot.model.Specia;
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
@RequestMapping("/specia")
public class SpeciaController extends BaseController{

    private static final String BASE_PATH = "specia";
    private static final String LIST = "specia.list";

    @RequestMapping("/")
	public String index() {
		return BASE_PATH+"/specia_list";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST,httpServletRequest);
        return map;
    }

    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/specia_add";
    }

     @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        return BASE_PATH + "/specia_edit";
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        /*Specia Specia = sqlManager.single(Specia.class,id);
        return Specia;*/ //GROUP_CONCAT(sp.PositionId SEPARATOR ',') as PositionId
        Map map = new HashMap();
        map.put("id",id);
       Map speciaMap = sqlManager.selectSingle("specia.findOne",map,Map.class);
       return speciaMap;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        Specia model = mapping(Specia.class, request);
        String positionId = request.getParameter("positionId");
        int result;
        if (model.getId() == null) {
            model.setStatus(3);
            result = sqlManager.insert(model);
            if(result > 0 && positionId != null){
               //添加到专场招聘职位表
                String[] strings = positionId.split(",");
                List<Specia> list = sqlManager.select("position.lastOne",Specia.class);
                for (int i = 0; i < strings.length; i++) {
                    Position position = new Position();
                    position.setPositionId(Integer.parseInt(strings[i]));
                    position.setSpecialId(list.get(0).getId());
                    sqlManager.insert(position);
                }
            }
        } else {
            Specia specia = sqlManager.single(Specia.class,model.getId());
            model.setStatus(specia.getStatus());
            model.setExamineUserId(specia.getExamineUserId());
            model.setExamineDate(specia.getExamineDate());
            model.setExamineRemark(specia.getExamineRemark());
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
            deleteById += sqlManager.deleteById(Specia.class, id);
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
                Specia single = sqlManager.single(Specia.class, id);
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
                Specia single = sqlManager.single(Specia.class, id);
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
            List<Specia> Specias = importExcel(file, Specia.class);
            for (Specia specia : Specias) {
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
        List<Specia> mapList;
        if (ids == null || ids.isEmpty()) {
            mapList = sqlManager.all(Specia.class);
        } else {
            mapList = selectByIds(Specia.class, ids);
        }
        try {
            exportExcel("Specia", mapList,Specia.class);
        } catch (Exception e) {
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

}
