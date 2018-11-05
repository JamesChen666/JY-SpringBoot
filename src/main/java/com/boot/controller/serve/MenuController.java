package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import com.boot.controller.system.BaseController;
import com.boot.model.Menu;
import com.boot.model.TreeModel;
import com.boot.system.SqlIntercepter;
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
@RequestMapping("/menu")
public class MenuController extends BaseController{

    private static final String BASE_PATH = "menu";
    private static final String LIST = "menu.list";

    @RequestMapping("/")
    public String index() {
        return BASE_PATH+"/menu_list";
    }

    @ResponseBody
    @RequestMapping("/list")
    public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST, httpServletRequest);
        return map;
    }
    @ResponseBody
    @RequestMapping("/combotreeList")
    public Object combotreeList() {
        List<Map> map = sqlManager.select(LIST, Map.class);
        List<Map> combotree = combotree(map);
        return combotree;
    }

    @ResponseBody
    @RequestMapping("/treeList")
    public Object treeList() {
        List<TreeModel> map = sqlManager.select("menu.treeList", TreeModel.class);
        List<TreeModel> combotree = buildTree(map,"0");
        return combotree;
    }

    @ResponseBody
    @RequestMapping("/updateTreeList")
    public Object updateTreeList(HttpServletRequest request) {
        List<TreeModel> map = sqlManager.select("menu.treeList", TreeModel.class);
        //查询出
        List<TreeModel> combotree = buildTree(map,"0");
        return combotree;
    }

    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/menu_add";
    }

    @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        return BASE_PATH + "/menu_edit";
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        Menu Menu = sqlManager.single(Menu.class,id);
        return Menu;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        Menu model = mapping(Menu.class, request);
        int result;
        if (model.getId() == null) {
            Menu MenuName = sqlManager.query(Menu.class)
                    .andEq("MenuName", model.getMenuName())
                    .single();
            if (ObjectUtil.isNotNull(MenuName)){
                return error("菜单名称重复!");
            }
            Menu MenuCode = sqlManager.query(Menu.class)
                    .andEq("MenuCode", model.getMenuCode())
                    .single();
            if (ObjectUtil.isNotNull(MenuCode)){
                return error("菜单编码重复!");
            }
            model.setIsEnabled(true);
            if (model.getParentId()==null){
                model.setParentId(0);
            }
            result = sqlManager.insert(model);
        } else {
            Menu MenuName = sqlManager.query(Menu.class)
                    .andEq("MenuName", model.getMenuName())
                    .andNotEq("Id", model.getId())
                    .single();
            if (ObjectUtil.isNotNull(MenuName)){
                return error("菜单名称重复!");
            }
            Menu MenuCode = sqlManager.query(Menu.class)
                    .andEq("MenuCode", model.getMenuCode())
                    .andNotEq("Id", model.getId())
                    .single();
            if (ObjectUtil.isNotNull(MenuCode)){
                return error("菜单编码重复!");
            }
            Menu single = sqlManager.single(Menu.class, model.getId());
            model.setIsEnabled(single.getIsEnabled());
            if (model.getParentId()==null){
                model.setParentId(0);
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
            int children = sqlManager.query(Menu.class).andEq("ParentId", id).select().size();
            if (children>0){
                return error("菜单有子菜单，请先删除所有子菜单");
            }
            deleteById += sqlManager.deleteById(Menu.class, id);
            deleteById += sqlManager.update("roleMenu.deleteByMenuId",
                    Dict.create().set("MenuId", id));
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
                Menu single = sqlManager.single(Menu.class, id);
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
        int err = 0;
        for (String s : multiFileMap.keySet()) {
            MultipartFile file = request.getFile(s);
            List<Menu> Menus = importExcel(file, Menu.class);
            for (Menu menu : Menus) {
                err++;
                Menu menuName = sqlManager.query(Menu.class)
                        .andEq("MenuName", menu.getMenuName()).single();
                if (ObjectUtil.isNotNull(menuName)){
                    return error("第"+err+"行"+menu.getMenuName()+"重复");
                }
                Menu MenuCode = sqlManager.query(Menu.class)
                        .andEq("MenuCode", menu.getMenuCode()).single();
                if (ObjectUtil.isNotNull(MenuCode)){
                    return error("第"+err+"行"+menu.getMenuCode()+"重复");
                }
            }
            for (Menu menu : Menus) {
                menu.setIsEnabled(true);
                insert += sqlManager.insert(menu);
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
        List<Menu> mapList;
        if (ids == null||ids.isEmpty()) {
            mapList = sqlManager.all(Menu.class);
        }else {
            mapList = selectByIds(Menu.class, ids);
        }
        try {
            exportExcel("菜单信息", mapList,Menu.class);
        }catch (Exception e){
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/parentList")
    public Object parentList(){
        List<Map> list = appendToList(LIST,SqlIntercepter.create().set("WHERE sm.ParentId = 0") );
        return list;
    }


    public  List<TreeModel> updateBuildTree(List<TreeModel> list,String pId){
        List<TreeModel> menus=new ArrayList<TreeModel>();
        //查询出该角色的权限
        for (TreeModel menu : list) {
            String menuId = menu.getId();
            String ParentId = menu.getPid();
            if("0".equals(menu.getPid())){
                menu.setState("closed");
            }
            if (pId.equals(ParentId)) {
                List<TreeModel> menuLists = buildTree(list, menuId);
                menu.setChildren(menuLists);
                menus.add(menu);
            }
            if(menuId.equals("but_1")){
                menu.setChecked(true);
            }
        }
        return menus;
    }

}
