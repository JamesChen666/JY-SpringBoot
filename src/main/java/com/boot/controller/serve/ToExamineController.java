package com.boot.controller.serve;

import cn.hutool.core.lang.Dict;
import com.boot.controller.system.BaseController;
import com.boot.model.Corp;
import com.boot.model.CorpPart;
import com.boot.model.Postion;
import com.boot.model.Recruit;
import com.boot.util.AjaxResult;
import com.boot.util.ShiroUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/toExamine")
public class ToExamineController extends BaseController {

    private static final String BASE_PATH = "toExamine";
    private static final String LIST = "toExamine.list";

    @RequestMapping("/")
    public String index() {
        return BASE_PATH+"/toExamine_list";
    }

    @ResponseBody
    @RequestMapping("/list")
    public Object list(HttpServletRequest httpServletRequest) {
        Map map = pageQuery("toExamine.newList",httpServletRequest);
        return map;

    }

    @RequestMapping("/edit")
    public String edit(HttpServletRequest httpServletRequest, ModelMap modelMap) {
        String id  = httpServletRequest.getParameter("id");
        modelMap.put("id", id);
        //查询出单位信息
        Map corp = sqlManager.selectSingle("corp.findOne",
                Dict.create().set("id", id), Map.class);
        //查询出职位审核信息
        List<Map>  postion = sqlManager.select("corp.findPostion",Map.class,Dict.create().set("corpId",id));
        //查询出专场招聘审核信息
        //查询出双选会审核信息
        modelMap.put("corp", corp);
        modelMap.put("postion",postion!=null?postion.get(0):null);
        return BASE_PATH + "/sign_edit";
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
                if(id != null && id.contains("School_Corp")){
                    String[] strings = id.split("-");
                    String flag = httpServletRequest.getParameter("flag");
                    String opinion = httpServletRequest.getParameter("opinion");
                    Corp single = sqlManager.single(Corp.class, strings[0]);
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
                if(id != null && id.contains("Corp_Postion")){
                    String[] strings = id.split("-");
                    String flag = httpServletRequest.getParameter("flag");
                    String opinion = httpServletRequest.getParameter("opinion");
                    Postion single = sqlManager.single(Postion.class, strings[0]);
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
                if(id != null && id.contains("School_Specia")){
                    String[] strings = id.split("-");
                    String flag = httpServletRequest.getParameter("flag");
                    String opinion = httpServletRequest.getParameter("opinion");
                    Recruit single = sqlManager.single(Recruit.class, strings[0]);
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
                if(id != null && id.contains("Election_CorpPart")){
                    String[] strings = id.split("-");
                    String flag = httpServletRequest.getParameter("flag");
                    String opinion = httpServletRequest.getParameter("opinion");
                    CorpPart single = sqlManager.single(CorpPart.class, strings[0]);
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
        }
        if (updateById <= 0) {
            return fail(FAIL);
        }
        return success(SUCCESS);
    }

}
