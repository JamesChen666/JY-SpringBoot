package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import com.boot.controller.system.BaseController;
import com.boot.model.Menu;
import com.boot.model.Role;
import com.boot.model.RoleMenu;
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author chenjiang
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    private static final String BASE_PATH = "role";
    private static final String LIST = "role.list";

    @RequestMapping("/")
    public String index() {
        return BASE_PATH + "/role_list";
    }

    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/role_add";
    }

    @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        return BASE_PATH + "/role_edit";
    }

    @ResponseBody
    @RequestMapping("/list")
    public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST,httpServletRequest);
        return map;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        Role model = mapping(Role.class, request);
        int result;
        if (model.getId() == null) {
            Role RoleName = sqlManager.query(Role.class)
                    .andEq("RoleName", model.getRoleName()).single();
            if (ObjectUtil.isNotNull(RoleName)){
                return error("角色名称重复");
            }
            result = sqlManager.insert(model);
        } else {
            Role RoleName = sqlManager.query(Role.class)
                    .andEq("RoleName", model.getRoleName())
                    .andNotEq("Id", model.getId())
                    .single();
            if (ObjectUtil.isNotNull(RoleName)){
                return error("角色名称重复");
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
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        Role Role = sqlManager.single(Role.class, id);
        return Role;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public AjaxResult delete(HttpServletRequest httpServletRequest) {
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        int deleteById = 0;
        for (String s : parameterMap.keySet()) {
            String id = httpServletRequest.getParameter(s);
            deleteById += sqlManager.deleteById(Role.class, id);
            deleteById += sqlManager.update("roleMenu.deleteByRoleId",
                    Dict.create().set("RoleId", id));
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
        for (String s : multiFileMap.keySet()) {
            MultipartFile file = request.getFile(s);
            List<Role> roles = importExcel(file, Role.class);
            for (Role role : roles) {
                insert += sqlManager.insert(role);
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
            mapList = sqlManager.select("role.list",Map.class);
        }else {
            mapList = appendToList("role.list",
                    SqlIntercepter.create().set("WHERE FIND_IN_SET(Id,#{ids})"),
                    Dict.create().set("ids", ids));
        }
        try {
            simpleExport("角色信息", mapList );
        }catch (Exception e){
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }
    @ResponseBody
    @RequestMapping("/permission")
    public Object permission(HttpServletRequest httpServletRequest) {
        String values = null;
        String id = httpServletRequest.getParameter("id[]");
        Role single = sqlManager.single(Role.class, id);
        List<RoleMenu> roleMenus = sqlManager.select("roleMenu.findByRoleId",
                RoleMenu.class, Dict.create().set("RoleId", single.getId()));
        StringBuffer StringBuffer = new StringBuffer();
        for (RoleMenu roleMenu : roleMenus) {
            StringBuffer.append(roleMenu.getMenuId()).append(",");
        }
        if (StringBuffer.length() > 1) {
            values = StringBuffer.substring(0, StringBuffer.length() - 1);
        }
        String ids = sqlManager.selectSingle("role.findPermissionDataIds",
                Dict.create().set("Ids", values), String.class);
        return ids;
    }

    @ResponseBody
    @RequestMapping("/setPermission")
    public AjaxResult setPermission(HttpServletRequest httpServletRequest) {
        String data = httpServletRequest.getParameter("data");
        String[] split = data.split(",");
        Set<String> menuIds = new LinkedHashSet<>();
        for (String s : split) {
            Menu menu = sqlManager.single(Menu.class, s);
            if (ObjectUtil.isNotNull(menu)) {
                menuIds.add(s);
            }
        }
        List<String> ids = sqlManager.select("role.findPrentId",
                String.class,Dict.create().set("Ids", data));
        menuIds.addAll(ids);
        int insert = 0;
        String id = httpServletRequest.getParameter("id[]");
        Role single = sqlManager.single(Role.class, id);
        try {
            sqlManager.update("roleMenu.deleteByRoleId",
                    Dict.create().set("RoleId", single.getId()));
            for (String menuId : menuIds) {
                if (!menuId.equals("0")){
                    RoleMenu roleMenu = new RoleMenu();
                    roleMenu.setMenuId(Integer.valueOf(menuId));
                    roleMenu.setRoleId(single.getId());
                    insert += sqlManager.insert(roleMenu);
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }
}
