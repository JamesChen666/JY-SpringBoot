package com.boot.controller.serve;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.boot.controller.system.BaseController;
import com.boot.model.Notice;
import com.boot.util.PublicClass;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主页
 */
@Controller
@RequestMapping("/home")
public class HomeController extends BaseController {


    @RequestMapping("/")
    public String index(HttpServletRequest request) {
        String code = request.getParameter("code");

        if("1".equals(code)){
            //查询出参数
            /*Map map1 = new HashMap();
            map1.put("typeCode","dm001");
            Map map = pageQueryCondition("notice.typeCodeList",request,map1);
            request.setAttribute("list",JSON.toJSONString(map));*/
            request.setAttribute("name","通知公告列表");
            request.setAttribute("code","1");
            return "homePage/news-list-two";
        }else if("2".equals(code)){
            //查询出参数
           /* Map map = new HashMap();
            map.put("typeCode","dm002");
            List<Map> list = sqlManager.select("notice.typeCodeList",Map.class,map);
            request.setAttribute("list",JSON.toJSONString(list));*/
            request.setAttribute("name","自行联系列表");
            request.setAttribute("code","2");
            return "homePage/news-list";
        }else if("3".equals(code)){
            //查询出参数
           /* Map map = new HashMap();
            map.put("typeCode","dm003");
            List<Map> list = sqlManager.select("notice.typeCodeList",Map.class,map);
            request.setAttribute("list",JSON.toJSONString(list));*/
            request.setAttribute("name","历年来校单位列表");
            request.setAttribute("code","3");
            return "homePage/news-list";
        }else if("4".equals(code)){
            //查询出参数
           /* Map map = new HashMap();
            map.put("typeCode","xxzl");
            List<Map> list = sqlManager.select("download.typeCodeList",Map.class,map);
            request.setAttribute("list",JSON.toJSONString(list));*/
            request.setAttribute("name","信息专栏列表");
            request.setAttribute("code","4");
            return "homePage/news-list-two";
        }else if("5".equals(code)){
            //查询出参数
            /*Map map = new HashMap();
            map.put("typeCode","xgxz");
            List<Map> list = sqlManager.select("download.typeCodeList",Map.class,map);
            request.setAttribute("list",JSON.toJSONString(list));*/
            request.setAttribute("name","相关下载列表");
            request.setAttribute("code","5");
            return "homePage/news-list-two";
        }else if("6".equals(code)){
            //查询出参数
           /* Map map1 = new HashMap();
            map1.put("typeCode","dm006");
            Map map = pageQueryCondition("notice.typeCodeList",request,map1);
            request.setAttribute("list",JSON.toJSONString(map));*/
            request.setAttribute("name","就业指导栏目列表");
            request.setAttribute("code","6");
            return "homePage/news-list-two";
        }
        return "/list";
    }


    @ResponseBody
    @RequestMapping("/homeNoticeList")
    public Object homeList(HttpServletRequest request) {
        Map map1 = new HashMap();
        String code = request.getParameter("code");
        String code1 = "1".equals(code)?"dm001":"2".equals(code)?"dm002":"3".equals(code)?"dm003":"6".equals(code)?"dm006":code;
        map1.put("typeCode",code1);
        Map map = pageQueryCondition("notice.typeCodeList",request,map1);
        return map;
    }


    @ResponseBody
    @RequestMapping("/homeDownloadList")
    public Object homeDownloadList(HttpServletRequest request) {
        Map map1 = new HashMap();
        String code = request.getParameter("code");
        String code1 = "5".equals(code)?"xxzl":"xgxz";
        map1.put("typeCode",code1);
        Map map = pageQueryCondition("download.typeCodeList",request,map1);
        return map;
    }



    @ResponseBody
    @RequestMapping("/noticeList")
    public Object noticeList(HttpServletRequest request) {
        String code = request.getParameter("TypeCode");
        List<Map> list;
        if(PublicClass.isNull(code)){
            list = sqlManager.select("notice.list",Map.class);
        }else{
            Map map = new HashMap();
            map.put("typeCode",code);
            list = sqlManager.select("notice.typeCodeList",Map.class,map);
        }
        return list;
    }


    @ResponseBody
    @RequestMapping("/downloadList")
    public Object downloadList(HttpServletRequest request) {
        String code = request.getParameter("TypeCode");
        List<Map> list = null;
        if(PublicClass.isNull(code)){
            list = sqlManager.select("download.list", Map.class);
        }else{
            Map map = new HashMap();
            map.put("typeCode",code);
            list = sqlManager.select("download.typeCodeList", Map.class,map);
        }
        return list;
    }


    @ResponseBody
    @RequestMapping("/linktypeList")
    public Object linkTypeList() {
        List<Map> list = sqlManager.select("friendlinktype.list", Map.class);
        return list;
    }


    @ResponseBody
    @RequestMapping("/linkList")
    public Object linkList(HttpServletRequest request) {
        String code = request.getParameter("typeCode");
        List<Map> list = null;
        if(PublicClass.isNull(code)){
            list = sqlManager.select("friendlink.list", Map.class);
        }else{
            Map map = new HashMap();
            map.put("typeCode",code);
            list = sqlManager.select("friendlink.queryCodeList", Map.class,map);
        }
        return list;
    }


    @RequestMapping("/details")
    public String details(HttpServletRequest request) {
        Map map = new HashMap();
       // List<Map> list = null;
        //公告表id
       /* String id = request.getParameter("id");
        if(!PublicClass.isNull(id)){
            map.put("id",id);
            //通过公告表id查询出公告信息
            list = sqlManager.select("notice.findOne",Map.class,map);
        }*/
        //request.setAttribute("list",JSON.toJSONString(list));
        request.setAttribute("code",request.getParameter("code"));
        if("1".equals(request.getParameter("code"))){
            request.setAttribute("lbName","通知公告列表");
            request.setAttribute("name","通知公告详情");
        }else if("6".equals(request.getParameter("code"))){
            request.setAttribute("lbName","就业指导栏目列表");
            request.setAttribute("name","就业指导栏目详情");
        }
        return "/homePage/news-pro";
    }


    @ResponseBody
    @RequestMapping("/queryDetails")
    public Object queryDetails(HttpServletRequest request) {
        List<Map> list = null;
        Map map = new HashMap();
        //公告表id
        String id = request.getParameter("id");
        if(!PublicClass.isNull(id)){
            map.put("id",id);
            //通过公告表id查询出公告信息
            list = sqlManager.select("notice.findOne",Map.class,map);
        }
        return list;
    }

    @ResponseBody
    @RequestMapping("/navList")
    public Object navList(HttpServletRequest httpServletRequest) {
        Map map = pageQuery("nav.navList",httpServletRequest);
        //取出数据
        if(map != null && map.get("rows") != null){
            List<Map> list= (List<Map>) map.get("rows");
            for (Map maps : list) {
                //循环查询下级参数
              List<Map> mapList = sqlManager.select("nav.nodeList",Map.class,maps);
                  maps.put("mapList",mapList);
            }
        }
        return map;
    }

    /**
     * 查询首页广告位信息
     */
    @ResponseBody
    @RequestMapping("/advertList")
    public Object advertList() {
        //查询出广告位信息
        List<Map> list1 = sqlManager.select("advertPosition.list", Map.class);
        for(Map map : list1){
            //查询出该广告位的广告信息
            List<Map> list = sqlManager.select("advert.queryPositionIdList", Map.class,map);
            map.put("list",list);
        }
        return list1;
    }


    /**
     * 视频列表
     * @param request
     * @return
     */
    @RequestMapping("/videoList")
    public String videoList(HttpServletRequest request) {
        String code = request.getParameter("code");
        request.setAttribute("name","视频列表");
        request.setAttribute("code",code);
        /*Map map = new HashMap();
        map.put("typeCode",code);
        List<Map> list = sqlManager.select("notice.typeCodeList",Map.class,map);
        request.setAttribute("list",JSON.toJSONString(list));*/
        return "homePage/news-list";
    }

    /**
     * 视频详情
     * @param request
     * @return
     */
    @RequestMapping("/videoDetails")
    public String videoDetails(HttpServletRequest request) {
        String id = request.getParameter("id");
        if(!PublicClass.isNull(id)){
            //查询出该视频详情   findOne
            Notice notice = sqlManager.single(Notice.class,id);
            request.setAttribute("notice",JSON.toJSONString(notice));
        }
        return "homePage/video";
    }

    /**
     * 单位招聘列表
     * @param request
     * @return
     */
    @RequestMapping("/comList")
    public String comList(HttpServletRequest request) {
        String id = request.getParameter("id");
        if(!PublicClass.isNull(id)){
            //查询出该单位的招聘列表
           /* Notice notice = sqlManager.single(Notice.class,id);
            request.setAttribute("notice",JSON.toJSONString(notice));*/
        }
        return "homePage/com-pro";
    }


    /**
     * 单位招聘详情
     * @param request
     * @return
     */
    @RequestMapping("/comDetails")
    public String comDetails(HttpServletRequest request) {
        String id = request.getParameter("id");
        if(!PublicClass.isNull(id)){
            //查询出该单位的招聘列表
           /* Notice notice = sqlManager.single(Notice.class,id);
            request.setAttribute("notice",JSON.toJSONString(notice));*/
        }
        return "homePage/com-list";
    }
}
