package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.boot.controller.system.BaseController;
import com.boot.model.Tag;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/tools")
public class ToolsController extends BaseController {

    @RequestMapping("/SetManager")
    public String SetManager(HttpServletRequest request){
        //代码
        String code = request.getParameter("objectCode");
        //类型
        String type = request.getParameter("type");
        request.setAttribute("type",type);
        if("campus".equals(type)){
            //查询出该校区的管理员
            List<Map> list = sqlManager.select("campusManager.queryCampusManager",Map.class,Dict.create().set("campusCode",code));
            request.setAttribute("list",JSON.toJSONString(list));
        }else if("faculty".equals(type)){
            //查询出该院系的管理员
            List<Map> list = sqlManager.select("facultyManager.queryFacultyManager",Map.class,Dict.create().set("facultyCode",code));
            request.setAttribute("list",JSON.toJSONString(list));
        }else if("specialty".equals(type)){
            //查询出该专业的管理员
            List<Map> list = sqlManager.select("specialtyManager.querySpecialtyManager",Map.class,Dict.create().set("specialtyCode",code));
            request.setAttribute("list",JSON.toJSONString(list));
        }else if("class".equals(type)){
            //查询出该班级的管理员
            List<Map> list = sqlManager.select("classinstructor.queryClassInstructor",Map.class,Dict.create().set("classNo",code));
            request.setAttribute("list",JSON.toJSONString(list));
        }

        return "/tools/setmanager";
    }
}
