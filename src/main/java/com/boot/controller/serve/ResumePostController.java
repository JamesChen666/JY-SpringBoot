package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import com.boot.controller.system.BaseController;
import com.boot.model.ResumePost;
import com.boot.model.Student;
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
import java.util.List;
import java.util.Map;

/**
 * @author chenjiang
 */
@Controller
@RequestMapping("/resumePost")
public class ResumePostController extends BaseController{

    private static final String BASE_PATH = "resumePost";
    private static final String LIST = "resumePost.list";

    @RequestMapping("/")
	public String index() {
		return BASE_PATH+"/resumePost_list";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST,httpServletRequest);
        return map;
    }

	@ResponseBody
	@RequestMapping("/ytjl")
	public Object ytjl(HttpServletRequest httpServletRequest) {
        Student studentNumber = sqlManager.query(Student.class)
                .andEq("StudentNumber",
                    ShiroUtils.getInstence().getUser().getUserName())
                .single();
        List<Map> mapList = sqlManager.select("resumePost.ytjl", Map.class,
                Dict.create().set("StudentId",studentNumber.getId()));
        return mapList;
    }

    @ResponseBody
    @RequestMapping("/deliveryList")
    public Object deliveryList(HttpServletRequest httpServletRequest) {
        Map map = pageQuery("resumePost.deliveryList",httpServletRequest);
        return map;
    }

    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/resumePost_add";
    }

     @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        return BASE_PATH + "/resumePost_edit";
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        ResumePost ResumePost = sqlManager.single(ResumePost.class,id);
        return ResumePost;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        try {
            ResumePost model = mapping(ResumePost.class, request);
            Student student = sqlManager.selectSingle("student.findStudent",Dict.create().set("userName",
                    ShiroUtils.getInstence().getUser().getUserName()),Student.class);
            model.setStudentId(student.getId());
            model.setCreateDate(new Date());
            //判断是否已投递
            Map map = sqlManager.selectSingle("resumePost.isDelivery",Dict.create().set("studentId",model.getStudentId()).set("postionId",model.getPostionId()),Map.class);
            if(map != null && map.size()>0){
                return fail("已经投递，请勿重复投递");
            }
            int result;
            result = sqlManager.insert(model);
            if (result > 0) {
                return success(SUCCESS);
            } else {
                return fail(FAIL);
            }
        }catch (Exception e){
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
            deleteById += sqlManager.deleteById(ResumePost.class, id);
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
                ResumePost single = sqlManager.single(ResumePost.class, id);
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

    @ResponseBody
    @RequestMapping("/import")
    public AjaxResult importExcel(MultipartHttpServletRequest request) {
        MultiValueMap<String, MultipartFile> multiFileMap = request.getMultiFileMap();
        int insert = 0;
        for (String s : multiFileMap.keySet()) {
            MultipartFile file = request.getFile(s);
            List<ResumePost> ResumePosts = importExcel(file, ResumePost.class);
            for (ResumePost resumePost : ResumePosts) {
                insert += sqlManager.insert(resumePost);
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
        List<ResumePost> mapList;
        if (ids == null || ids.isEmpty()) {
            mapList = sqlManager.all(ResumePost.class);
        } else {
            mapList = selectByIds(ResumePost.class, ids);
        }
        try {
            exportExcel("ResumePost", mapList,ResumePost.class);
        } catch (Exception e) {
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

}
