package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import com.boot.controller.system.BaseController;
import com.boot.model.Changesign;
import com.boot.model.ChangesignLog;
import com.boot.model.Sign;
import com.boot.util.AjaxResult;
import com.boot.util.ShiroUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author chenjiang
 */
@Controller
@RequestMapping("/changesign")
public class ChangesignController extends BaseController{

    private static final String BASE_PATH = "changesign";
    private static final String LIST = "changesign.list";

    @RequestMapping("/")
	public String index() {
		return BASE_PATH+"/changesign_list";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery(LIST,httpServletRequest);
        return map;
    }

    @RequestMapping("/add")
    public String add() {
        return BASE_PATH + "/changesign_add";
    }

    @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        modelMap.put("id", httpServletRequest.getParameter("id"));
        return BASE_PATH + "/changesign_edit";
    }

    @RequestMapping("/view/{id}")
    public String view(@PathVariable Integer id,ModelMap modelMap) {
        Map map = sqlManager.selectSingle("changesign.findOne",
                Dict.create().set("Id", id), Map.class);
        modelMap.put("changesign", map);
        Map map1 = sqlManager.selectSingle("sign.findOneSign",
                Dict.create().set("StudentId", map.get("StudentId")), Map.class);
        modelMap.put("sign", map1);
        Changesign single = sqlManager.single(Changesign.class, id);
        Map student = sqlManager.selectSingle("student.findSimpleStudentMessage",
                Dict.create().set("Id", single.getStudentId()), Map.class);
        modelMap.put("student", student);
        return BASE_PATH + "/changesign_view";
    }

    @ResponseBody
    @RequestMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id) {
        Changesign Changesign = sqlManager.single(Changesign.class,id);
        return Changesign;
    }

    @ResponseBody
    @RequestMapping("/studentEdit/{id}")
    public Object studentEdit(@PathVariable Integer id) {
        Changesign changesign = sqlManager.query(Changesign.class)
                .andEq("StudentId", id).single();
        return changesign;
    }

    @ResponseBody
    @RequestMapping("/studentSign/{id}")
    public Object studentSign(@PathVariable Integer id) {
        Map changesign = sqlManager.selectSingle("changesign.findByStudentId",
                Dict.create().set("StudentId", id), Map.class);
        return changesign;
    }

    @ResponseBody
    @RequestMapping("/save")
    public AjaxResult save(HttpServletRequest request) {
        Changesign model = mapping(Changesign.class, request);
        ChangesignLog  changesignLog = mapping(ChangesignLog.class, request);
        int result=0;
        if (model.getId() == null) {
            model.setCreateDate(new Date());
            model.setGraduationWhereAboutCode("10");
            model.setStatus(0);
            result += sqlManager.insert(model);
        } else {
            Changesign single = sqlManager.single(Changesign.class, model.getId());
            model.setCreateDate(single.getCreateDate());
            model.setGraduationWhereAboutCode("10");
            model.setStatus(0);
            result += sqlManager.updateById(model);
        }
        changesignLog.setCreateDate(new Date());
        changesignLog.setGraduationWhereAboutCode("10");
        result +=sqlManager.insert(changesignLog);
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
            deleteById += sqlManager.deleteById(Changesign.class, id);
        }
        if (deleteById <= 0) {
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/export")
    public AjaxResult exportExcel(HttpServletRequest httpServletRequest) {
        String ids = httpServletRequest.getParameter("ids");
        List<Changesign> mapList;
        if (ids == null || ids.isEmpty()) {
            mapList = sqlManager.all(Changesign.class);
        } else {
            mapList = selectByIds(Changesign.class, ids);
        }
        try {
            exportExcel("Changesign", mapList,Changesign.class);
        } catch (Exception e) {
            e.getStackTrace();
            return fail(FAIL);
        }
        return success(SUCCESS);
    }
    @ResponseBody
    @RequestMapping("/audit")
    public AjaxResult audit(HttpServletRequest httpServletRequest) {
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        int update = 0;
        for (String s : parameterMap.keySet()) {
            String id = httpServletRequest.getParameter(s);
            Changesign model = sqlManager.single(Changesign.class, id);
            //修改签约
            Sign sign = sqlManager.query(Sign.class)
                    .andEq("StudentId", model.getStudentId()).single();
            sign.setArchiveContactor(model.getArchiveContactor());
            sign.setArchiveContactPhone(model.getArchiveContactPhone());
            sign.setArchiveCorp(model.getArchiveCorp());
            sign.setCorpAddress(model.getCorpAddress());
            sign.setCorpContactEmail(model.getCorpContactEmail());
            sign.setCorpContactFax(model.getCorpContactFax());
            sign.setCorpContactor(model.getCorpContactor());
            sign.setCorpContactPhone(model.getCorpContactPhone());
            sign.setCorpIndustryCode(model.getCorpIndustryCode());
            sign.setCorpNatureCdoe(model.getCorpNatureCdoe());
            sign.setCorpOrginCode(model.getCorpOrginCode());
            sign.setCorpOrganizationCode(model.getCorpOrganizationCode());
            sign.setCorporationName(model.getCorporationName());
            sign.setCorpPostCode(model.getCorpPostCode());
            sign.setContactorPostion(model.getContactorPostion());
            sign.setForwardCorpName(model.getForwardCorpName());
            sign.setForwardCorpPostCode(model.getForwardCorpPostCode());
            sign.setGraduationWhereAboutCode(model.getGraduationWhereAboutCode());
            sign.setHighGradeTypeCode(model.getHighGradeTypeCode());
            sign.setJobTypeCode(model.getJobTypeCode());
            sign.setRemark(model.getRemark());
            sign.setStudentId(model.getStudentId());
            sign.setUnderDepartmentCode(model.getUnderDepartmentCode());
            sign.setIsLock(true);
            update += sqlManager.updateById(sign);
            //新增改签日志
            ChangesignLog changesignlog = new ChangesignLog();
            changesignlog.setCreateDate(new Date());
            changesignlog.setApprovaler(ShiroUtils.getInstence().getUser().getId());
            changesignlog.setTypeCode(model.getTypeCode());
            changesignlog.setReason(model.getReason());
            changesignlog.setArchiveContactor(model.getArchiveContactor());
            changesignlog.setArchiveContactPhone(model.getArchiveContactPhone());
            changesignlog.setArchiveCorp(model.getArchiveCorp());
            changesignlog.setCorpAddress(model.getCorpAddress());
            changesignlog.setCorpContactEmail(model.getCorpContactEmail());
            changesignlog.setCorpContactFax(model.getCorpContactFax());
            changesignlog.setCorpContactor(model.getCorpContactor());
            changesignlog.setCorpContactPhone(model.getCorpContactPhone());
            changesignlog.setCorpIndustryCode(model.getCorpIndustryCode());
            changesignlog.setCorpNatureCdoe(model.getCorpNatureCdoe());
            changesignlog.setCorpOrginCode(model.getCorpOrginCode());
            changesignlog.setCorpOrganizationCode(model.getCorpOrganizationCode());
            changesignlog.setCorporationName(model.getCorporationName());
            changesignlog.setCorpPostCode(model.getCorpPostCode());
            changesignlog.setContactorPostion(model.getContactorPostion());
            changesignlog.setForwardCorpName(model.getForwardCorpName());
            changesignlog.setForwardCorpPostCode(model.getForwardCorpPostCode());
            changesignlog.setGraduationWhereAboutCode(model.getGraduationWhereAboutCode());
            changesignlog.setHighGradeTypeCode(model.getHighGradeTypeCode());
            changesignlog.setJobTypeCode(model.getJobTypeCode());
            changesignlog.setRemark(model.getRemark());
            changesignlog.setStudentId(model.getStudentId());
            changesignlog.setUnderDepartmentCode(model.getUnderDepartmentCode());
            update += sqlManager.insert(changesignlog);
            //删除改签信息
            update +=sqlManager.deleteById(Changesign.class, id);
        }
        if (update <= 0) {
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

}
