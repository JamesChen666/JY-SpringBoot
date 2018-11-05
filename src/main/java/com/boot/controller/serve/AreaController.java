package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import com.boot.controller.system.BaseController;
import com.boot.model.Area;
import com.boot.model.AreaDispatchunit;
import com.boot.util.AjaxResult;
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
import java.util.List;
import java.util.Map;

/**
 * @author chenjiang
 */
@Controller
@RequestMapping("/area")
public class AreaController extends BaseController{

    private static final String BASE_PATH = "area";
    private static final String LIST = "area.list";

    @RequestMapping("/")
    public String index() {
        return BASE_PATH+"/area_list";
    }

    @ResponseBody
    @RequestMapping("/list")
    public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST, httpServletRequest);
        return map;
    }

    @ResponseBody
    @RequestMapping("/parentList")
    public Object parentList(HttpServletRequest httpServletRequest) {
        List<Area> areaList = sqlManager.all(Area.class);
        return areaList;
    }

    @ResponseBody
    @RequestMapping("/combotreeList")
    public Object combotreeList() {
        List<Map> list = sqlManager.select(LIST, Map.class);
        List<Map> combotree = combotree(list);
        return combotree;
    }

    @ResponseBody
    @RequestMapping("/combotreeLists")
    public Object combotreeLists(HttpServletRequest request) {
        List<Map> combotree = treeData(request.getParameter("id"),request.getParameter("type"));
        return combotree;
    }

    @ResponseBody
    @RequestMapping("/findChildren")
    public Object findChildren(HttpServletRequest request) {
        Area single = sqlManager.query(Area.class)
                .andEq("AreaCode", request.getParameter("AreaCode"))
                .single();
        List<Area> areaList = sqlManager.query(Area.class)
                .andEq("ParentId", single.getId())
                .select();
        return areaList;
    }

    @ResponseBody
    @RequestMapping("/findSame")
    public Object findSame(HttpServletRequest request) {
        Area single = sqlManager.query(Area.class)
                .andEq("AreaCode",
                    request.getParameter("AreaCode"))
                .single();
        List<Area> areaList = sqlManager.query(Area.class)
                .andEq("ParentId", single.getParentId()).select();
        return areaList;
    }


    @ResponseBody
    @RequestMapping("/combotreeEcho")
    public Object combotreeEcho(HttpServletRequest request) {
        List list = new ArrayList();
       String code = sqlManager.selectSingle("area.queryCode",Dict.create().set("AreaCode",request.getParameter("areaCode")),String.class);
           if(code != null){
             String[] codes =  code.split(",");
               for (int i = 0; i <codes.length ; i++) {
                   list.add(codes[i]);
               }
           }
        return list;
    }

    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/area_add";
    }

    @RequestMapping("/relation")
    public String relation(HttpServletRequest request) {
        //行政区划表Id
        String id = request.getParameter("id");
        //查询出行政区划表信息
        List<Map> list = sqlManager.select("area.find",Map.class,Dict.create().set("id",id));
        if(list !=null && list.size()>0){
            request.setAttribute("id",list.get(0).get("Id"));
            request.setAttribute("AreaCode",list.get(0).get("AreaCode"));
            request.setAttribute("AreaName",list.get(0).get("AreaName"));
            for (Map map : list) {
                if("true".equals(map.get("IsNormal")+"")){
                    request.setAttribute("DispatchUnitId",map.get("DispatchUnitId"));
                    request.setAttribute("NormalCorpName",map.get("ProviderName"));
                }else {
                    request.setAttribute("noDispatchUnitId",map.get("DispatchUnitId"));
                    request.setAttribute("NonNormalCorpName",map.get("ProviderName"));
                }
            }
        }
        return BASE_PATH + "/area_relation";
    }

    @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        return BASE_PATH + "/area_edit";
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        Area Area = sqlManager.single(Area.class,id);
        return Area;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        Area model = mapping(Area.class, request);
        int result;
        if (model.getId() == null) {
            result = sqlManager.insert(model);
        } else {
            result = sqlManager.updateById(model);
        }
        if (result > 0) {
            return success(SUCCESS);
        } else {
            return fail(FAIL);
        }
    }


    /**
     * 关联派遣单位
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/relationCorp")
    public AjaxResult relationCorp(HttpServletRequest request) {
        AreaDispatchunit model = mapping(AreaDispatchunit.class, request);
        int result = 0;
        //删除关联单位
        sqlManager.update("areaDispatchunit.deleteRelation",Dict.create().set("areaCode",model.getAreaCode()));
        result = sqlManager.insert(model);
        if (result > 0) {
            //非师范生派遣单位id
            String normalDispatchUnitId = request.getParameter("normalDispatchUnitId");
            model.setDispatchUnitId(Integer.parseInt(normalDispatchUnitId));
            model.setIsNormal(false);
            result = sqlManager.insert(model);
            if(result > 0){
                return success(SUCCESS);
            }else{
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
            deleteById += sqlManager.deleteById(Area.class, id);
        }
        if (deleteById <= 0) {
            return fail(FAIL);
        }
        return success(SUCCESS);
    }


    @ResponseBody
    @RequestMapping("/import")
    public AjaxResult importExcel(MultipartHttpServletRequest request) {
        MultiValueMap<String, MultipartFile> multiFileMap = request.getMultiFileMap();
        int insert = 0;
        int err = 0;
        for (String s : multiFileMap.keySet()) {
            MultipartFile file = request.getFile(s);
            List<Area> Areas = importExcel(file, Area.class);
            for (Area area : Areas) {
                err++;
                Area areaCode = sqlManager.query(Area.class)
                        .andEq("AreaCode", area.getAreaCode()).single();
                if (ObjectUtil.isNotNull(areaCode)){
                    return error("第"+err+"行"+area.getAreaCode()+"重复");
                }
                Area AreaName = sqlManager.query(Area.class)
                        .andEq("AreaName", area.getAreaName()).single();
                if (ObjectUtil.isNotNull(AreaName)){
                    return error("第"+err+"行"+area.getAreaName()+"重复");
                }
            }
            for (Area area : Areas) {
                insert += sqlManager.insert(area);
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
        List<Area> mapList;
        if (ids == null||ids.isEmpty()) {
            mapList = sqlManager.all(Area.class);
        }else {
            mapList = selectByIds(Area.class,ids);
        }
        try {
            exportExcel("行政区划", mapList,Area.class );
        }catch (Exception e){
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

}
