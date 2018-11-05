package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import com.boot.controller.system.BaseController;
import com.boot.model.*;
import com.boot.util.AjaxResult;
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
    @RequestMapping("/roleList")
    public Object roleList(HttpServletRequest httpServletRequest) {
       List<Map> list = sqlManager.select(LIST,Map.class);
        return list;
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
            setPermissions(request);
            return success(SUCCESS);
        } else {
            return fail(FAIL);
        }
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        Map map = new HashMap();
        String value = "";
        String values = "";
        Role single = sqlManager.single(Role.class, id);
        List<RoleMenu> roleMenus = sqlManager.select("roleMenu.findByRoleId",
                RoleMenu.class, Dict.create().set("RoleId", single.getId()));
        StringBuffer StringBuffer = new StringBuffer();
        for (RoleMenu roleMenu : roleMenus) {
            StringBuffer.append(roleMenu.getMenuId()).append(",");
        }
        if (StringBuffer.length() > 1) {
            value = StringBuffer.substring(0, StringBuffer.length() - 1);
        }
        String menuIds = sqlManager.selectSingle("role.findPermissionDataIds",
                Dict.create().set("Ids", value), String.class);

        List<RoleButton> roleButtons = sqlManager.select("roleButton.findByRoleId",
                RoleButton.class, Dict.create().set("RoleId", single.getId()));
        StringBuffer StringBuffers = new StringBuffer();
        for (RoleButton roleButton : roleButtons) {
            StringBuffers.append(roleButton.getButtonId()).append(",");
        }
        if (StringBuffers.length() > 1) {
            values = StringBuffers.substring(0, StringBuffers.length() - 1);
        }
        String buttonIds = sqlManager.selectSingle("role.findButtonDataIds",
                Dict.create().set("Ids", values), String.class);
        map.put("Id",single.getId());
        map.put("RoleName",single.getRoleName());
        map.put("Sort",single.getSort());
        map.put("menuIds",menuIds);
        map.put("buttonIds",buttonIds);
        return map;
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
        int err = 0;
        int insert = 0;
        for (String s : multiFileMap.keySet()) {
            MultipartFile file = request.getFile(s);
            List<Role> roles = importExcel(file, Role.class);
            for (Role role : roles) {
                Role roleName = sqlManager.query(Role.class)
                        .andEq("RoleName", role.getRoleName()).single();
                err++;
                if (ObjectUtil.isNotNull(roleName)){
                    return error("第"+err+"行"+roleName.getRoleName()+"已存在");
                }
            }
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
        List<Role> mapList;
        if (ids == null||ids.isEmpty()) {
            mapList = sqlManager.all(Role.class);
        }else {
            mapList = selectByIds(Role.class,ids);
        }
        try {
            exportExcel("角色信息", mapList,Role.class );
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


    @ResponseBody
    @RequestMapping("/setPermissions")
    public AjaxResult setPermissions(HttpServletRequest httpServletRequest) {
        String data = httpServletRequest.getParameter("data");
        JSONArray array = JSONArray.fromObject(data);
        List list =(ArrayList)JSONArray.toCollection(array, String.class);
        Set<String> menuIds = new LinkedHashSet<>();
        Set<String> buttonIds = new LinkedHashSet<>();
        for (int i = 0; i < list.size(); i++) {
            if(i == 0){
                buttonIds.addAll((ArrayList)list.get(i));
            }
            if(i==1){
                menuIds.addAll((ArrayList)list.get(i));
            }
        }
        int insert = 0;
        String id = httpServletRequest.getParameter("Id");
        Role single = sqlManager.single(Role.class, id);
        //添加菜单
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
        //添加按钮
        try {
            sqlManager.update("roleButton.deleteByRoleId",
                    Dict.create().set("RoleId", single.getId()));
            for (String buttonId : buttonIds) {
                if (!buttonId.equals("0")){
                    RoleButton roleButton = new RoleButton();
                    roleButton.setRoleId(single.getId());
                    roleButton.setButtonId(Integer.parseInt(buttonId));
                    insert += sqlManager.insert(roleButton);
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }
}
