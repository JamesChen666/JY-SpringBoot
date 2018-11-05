package com.boot.controller.system;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.boot.model.TreeModel;
import com.boot.system.SqlIntercepter;
import com.boot.util.AjaxResult;
import com.boot.util.AsianFontProvider;
import com.boot.util.excel.ExcelUtils;
import com.boot.util.excel.exceptions.Excel4JException;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.annotatoin.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.sql.Time;
import java.util.*;

/**
 * @author chenjiang
 */
public class BaseController extends AjaxResult {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    protected net.sf.ehcache.CacheManager CacheManager;
    @Autowired
    protected SQLManager sqlManager;

    /**
     * @param tClass             实体类
     * @param httpServletRequest 当前请求
     * @param <T>
     * @return
     */
    public <T> T mapping(Class<T> tClass, HttpServletRequest httpServletRequest) {
        T t = null;
        try {
            t = tClass.newInstance();
            ServletRequestDataBinder ServletRequestDataBinder = new ServletRequestDataBinder(t);
            ServletRequestDataBinder.bind(httpServletRequest);
            Field[] declaredFields = t.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                String simpleName = declaredField.getType().getSimpleName();
                if (simpleName.equals("Date")) {
                    String parameter1 = httpServletRequest.getParameter(declaredField.getName());
                    String parameter2 = httpServletRequest.getParameter(StrUtil.lowerFirst(declaredField.getName()));
                    String parameter = StrUtil.isNotEmpty(parameter1) ? parameter1 : parameter2;
                    if (parameter != null && !parameter.equals("")) {
                        DateTime parse = DateUtil.parse(parameter);
                        declaredField.setAccessible(true);
                        declaredField.set(t, parse);
                    }
                }
                if (simpleName.equals("Time")) {
                    String parameter1 = httpServletRequest.getParameter(declaredField.getName());
                    String parameter2 = httpServletRequest.getParameter(StrUtil.lowerFirst(declaredField.getName()));
                    String parameter = StrUtil.isNotEmpty(parameter1) ? parameter1 : parameter2;
                    if (parameter != null && !parameter.equals("")) {
                        DateTime parse = DateUtil.parse(parameter);
                        Time Time = new Time(parse.getTime());
                        declaredField.setAccessible(true);
                        declaredField.set(t, Time);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * @param sqlId          SQL文件SQLId
     * @param sqlIntercepter 在SQL末尾追后加入SQL语句
     * @return
     */
    public List<Map> appendToList(String sqlId, SqlIntercepter sqlIntercepter) {
        String script = sqlManager.getScript(sqlId).getSql();
        String append = sqlIntercepter.getAppendSql();
        StringBuffer stringBuffer = new StringBuffer();
        String toString = stringBuffer.append(script).append(" ").append(append).toString();
        return sqlManager.execute(toString, Map.class, Dict.create());
    }


    /**
     * @param sqlId          SQL文件SQLId
     * @param sqlIntercepter 在SQL末尾追后加入SQL语句
     * @param params         参数集合
     * @return
     */
    public List<Map> appendToList(String sqlId, SqlIntercepter sqlIntercepter, Map params) {
        String script = sqlManager.getScript(sqlId).getSql();
        String append = sqlIntercepter.getAppendSql();
        StringBuffer stringBuffer = new StringBuffer();
        String toString = stringBuffer.append(script).append(" ").append(append).toString();
        return sqlManager.execute(toString, Map.class, params);
    }

    /**
     * @param tClass 实体类
     * @param ids    字符串 --> id1,id2,id3 ...
     * @param <T>
     * @return
     */
    public <T> List<T> selectByIds(Class<T> tClass, String ids) {
        String table = tClass.getAnnotation(Table.class).name();
        String sql = "SELECT * FROM  " + table + " WHERE FIND_IN_SET(Id,#{ids})";
        List<T> list = sqlManager.execute(sql, tClass, Dict.create().set("ids", ids));
        return list;
    }

    /**
     * @param file              文件
     * @param tClass            实体类
     * @param @ExcelField(title = "姓名",writeConverter = MyWriteConvert.class,readConverter = MyReadConvert.class)
     * @return
     */
    public <T> List<T> importExcel(MultipartFile file, Class<T> tClass) {
        File desktopDir = FileSystemView.getFileSystemView()
                .getHomeDirectory();
        String desktopPath = desktopDir.getPath();
        File File = new File(desktopPath + "\\temp.xls");
        try {
            file.transferTo(File);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<T> list = null;
        try {
            list = ExcelUtils.getInstance().readExcel2Objects(File.getPath(), tClass, 0, 0);
        } catch (Excel4JException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        File.delete();
        return list;
    }


    /**
     * @param fileName 文件名称
     * @param data     数据
     * @param tClass   实体类
     * @return
     */
    public <T> void exportExcel(String fileName, List<T> data, Class<T> tClass) {
        FileSystemView fsv = FileSystemView.getFileSystemView();
        File com = fsv.getHomeDirectory();
        String filePath = com.getPath() +"\\"+fileName + ".xls";
        try {
            ExcelUtils.getInstance()
                    .exportObjects2Excel(data, tClass, true, filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param fileName 文件名称
     * @param data     数据
     * @return
     */
    public AjaxResult simpleExport(String fileName, List<Map> data) {
        File desktopDir = FileSystemView.getFileSystemView()
                .getHomeDirectory();
        String desktopPath = desktopDir.getAbsolutePath();
        String filePath = desktopPath + "/" + fileName + ".xlsx";
        for (int i = 1; i < 1024; i++) {
            File File = new File(filePath);
            if (File.exists()) {
                filePath = desktopPath + "/" + fileName + i + ".xlsx";
            }
        }
        Map map = data.get(0);
        List<String> header = new ArrayList<>();
        List<List<String>> dataList = new ArrayList<>();
        Set set = map.keySet();
        for (Object o : set) {
            header.add(o.toString());
        }
        for (Map map1 : data) {
            List<String> list = new ArrayList<>();
            for (Object o : map1.keySet()) {
                if (map1.get(o) == null || map1.get(o) == "") {
                    list.add("无数据");
                } else {
                    list.add(map1.get(o).toString());
                }
            }
            dataList.add(list);
        }
        try {
            ExcelUtils.getInstance().exportObjects2Excel(dataList, header, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success(SUCCESS);
    }

    /**
     * @param maps List<Map>数据
     * @return
     */
    public List<Map> combotree(List<Map> maps) {
        for (Map map : maps) {
            List<Map> maps2 = new ArrayList<>();
            for (Map map1 : maps) {
                if (map1.get("ParentId").equals(map.get("Id"))) {
                    maps2.add(map1);
                }
            }
            if (CollUtil.isNotEmpty(maps2)) {
                map.put("children", maps2);
            }
        }
        List<Map> mapList = new ArrayList<>();
        for (Map map : maps) {
            if (map.get("ParentId").equals("0") || map.get("ParentId").toString().equals("0")) {
                mapList.add(map);
            }
        }
        return mapList;
    }


    public  List<TreeModel> buildTree(List<TreeModel> list,String pId){
        List<TreeModel> menus=new ArrayList<TreeModel>();
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
        }
        return menus;
    }





    /**
     * 获取树状数据（组装tree所用  异步树）
     *
     * @param currentId 当前节点id
     * @param currentType 当前节点类型 province=省  city=市 county=区县
     *
     * @return 省、市、区县
     */
    public List<Map> treeData(String currentId, String currentType)
    {
        List<Map> list = new LinkedList<>();

        Map<String, String> attr = new HashMap<String, String>();

        // 如果当前节点类型为空 则是第一次加载 即需要加载学校json
        if(StringUtils.isEmpty(currentType))
        {
            // 查询所有的省级
            list = sqlManager.select("area.queryProvince",Map.class);
            for (Map<String, Object> school : list)
            {
                school.put("type", "province");
                //school.put("attributes", attr);
                //注意此时省级节点要关闭，方能触发onBeforeExpand事件
                school.put("state", "closed");
            }
        }
        // 如果当前节点类型为学校 则异步加载年级json
        else if(StringUtils.equals("province", currentType))
        {
            // 根据省级id查询市级
            list = sqlManager.select("area.queryCity",Map.class,Dict.create().set("ParentId",currentId));
            if(!CollectionUtils.isEmpty(list))
            {
                for (Map<String, Object> grade : list)
                {
                    grade.put("type", "city");
                    //grade.put("attributes", attr);
                    //注意此时市级节点要关闭，方能触发onBeforeExpand事件
                    grade.put("state", "closed");
                }
            }
        }
        // 如果当前节点类型为市级 则异步加载县级json
        else if(StringUtils.equals("city", currentType))
        {
            // 根据市级id查询县级
            list = sqlManager.select("area.queryCounty",Map.class,Dict.create().set("ParentId",currentId));
            if(!CollectionUtils.isEmpty(list))
            {
                for (Map<String, Object> classs : list)
                {
                    classs.put("type", "county");
                    //classs.put("attributes", attr);
                    classs.put("state", "open");
                }
            }
        }
        return list;
    }



    /**
     * @param sqlId              SQL ID
     * @param httpServletRequest 必须参数：page当前页,rows总条数,sidx排序列,sord排正序、倒序
     * @return
     */
    public Map pageQuery(String sqlId, HttpServletRequest httpServletRequest) {
        String page = httpServletRequest.getParameter("page");
        String rows = httpServletRequest.getParameter("rows");
        String sort = httpServletRequest.getParameter("sidx");
        String order = httpServletRequest.getParameter("sord");
        String sortName = httpServletRequest.getParameter("sort");
        String sortOrder = httpServletRequest.getParameter("order");
        if (sort == null && sortName != null) {
            sort = sortName;
        }
        if (order == null && sortOrder != null) {
            order = sortOrder;
        }
        StringBuffer seach = new StringBuffer();
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        for (String paramName : parameterMap.keySet()) {
            if (paramName.startsWith("s_")) {
                if (!StrUtil.hasEmpty(httpServletRequest.getParameter(paramName))) {
                    if (paramName.endsWith("_et")) {
                        seach.append(paramName.substring(2, paramName.length() - 3))
                                .append(" = ")
                                .append(" ' ")
                                .append(httpServletRequest.getParameter(paramName))
                                .append(" ' ")
                                .append(" AND ");
                    } else if (paramName.endsWith("_rt")) {
                        seach.append(paramName.substring(2, paramName.length() - 3))
                                .append(" >= ")
                                .append(" '")
                                .append(httpServletRequest.getParameter(paramName))
                                .append("' ")
                                .append(" AND ");
                    } else if (paramName.endsWith("_lt")) {
                        seach.append(paramName.substring(2, paramName.length() - 3))
                                .append(" <= ")
                                .append(" '")
                                .append(httpServletRequest.getParameter(paramName))
                                .append("' ")
                                .append(" AND ");
                    } else if (NumberUtil.isNumber(httpServletRequest.getParameter(paramName))) {
                        seach.append(paramName.substring(2))
                                .append(" = ")
                                .append(httpServletRequest.getParameter(paramName))
                                .append(" AND ");
                    } else {
                        seach.append(paramName.substring(2))
                                .append(" LIKE ")
                                .append("'%")
                                .append(httpServletRequest.getParameter(paramName))
                                .append("%'")
                                .append(" AND ");
                    }
                }
            }
        }
        for (String s : parameterMap.keySet()) {
            if (s.startsWith("sm_")) {
                if (!StrUtil.hasEmpty(httpServletRequest.getParameter(s))) {
                    String[] split = s.substring(3).split("-");
                    for (String str : split) {
                        seach.append(str)
                                .append(" LIKE ")
                                .append("'%")
                                .append(httpServletRequest.getParameter(s))
                                .append("%'")
                                .append(" OR ");
                    }
                }
            }
        }
        String seachSql = "";
        if (seach.length() > 0) {
            if (seach.toString().endsWith("AND ")) {
                seachSql = " WHERE " + seach.substring(0, seach.length() - 4);
            } else if (seach.toString().contains("AND")) {
                int and = seach.toString().lastIndexOf("AND");
                seachSql = " WHERE " + seach.toString().substring(0, and) + "AND ("
                        + seach.toString().substring(and + 3, seach.length() - 3) + ")";
            } else {
                seachSql = " WHERE " + seach.toString().substring(0, seach.length() - 3);
            }
        }
        Integer integerPage = Integer.valueOf(page);
        Integer integerRows = Integer.valueOf(rows);
        String sql = sqlManager.getScript(sqlId).getSql();
        if (sql.contains("GROUP BY") || sql.contains("group by")) {
            seachSql = seachSql.replace("WHERE", "HAVING");
        }
        Map map = new HashMap();
        Integer totalCount = appendToList(sqlId, SqlIntercepter.create().set(seachSql)).size();
        map.put("records", totalCount);
        Double doubleRows = Double.valueOf(rows);
        Integer totalPage = (int) Math.ceil(totalCount / doubleRows);
        map.put("total", totalPage);
        map.put("page",integerPage);
        if (!sql.contains("ORDER")&&!sql.contains("order")){
            sort = "ORDER BY " + sort;
        }else {
            sort = ","+sort;
        }
        if (totalCount < integerRows * integerPage) {
            List<Map> mapList = appendToList(sqlId,
                    SqlIntercepter.create().set(seachSql + sort + " " + order))
                    .subList(integerRows * (integerPage - 1), totalCount);
            map.put("rows", mapList);
        } else {
            List<Map> mapList = appendToList(sqlId,
                    SqlIntercepter.create().set(seachSql + sort + " " + order))
                    .subList(integerRows * (integerPage - 1), integerRows * integerPage);
            map.put("rows", mapList);
        }
        return map;
    }


    /**
     * @param sqlId              SQL ID
     * @param httpServletRequest 必须参数：page当前页,rows总条数,sidx排序列,sord排正序、倒序
     * @return
     */
    public Map pageQueryCondition(String sqlId, HttpServletRequest httpServletRequest, Map myMap) {
        String page = httpServletRequest.getParameter("page");
        String rows = httpServletRequest.getParameter("rows");
        String sort = httpServletRequest.getParameter("sidx");
        String order = httpServletRequest.getParameter("sord");
        Integer integerPage = Integer.valueOf(page);
        Integer integerRows = Integer.valueOf(rows);
        Map map = new HashMap();
        Integer totalCount = sqlManager.select(sqlId, Map.class, myMap).size();
        map.put("records", totalCount);
        Double doubleRows = Double.valueOf(rows);
        Integer totalPage = (int) Math.ceil(totalCount / doubleRows);
        map.put("total", totalPage);
        String sql = sqlManager.getScript(sqlId).getSql();
        if (!sql.contains("ORDER")&&!sql.contains("order")){
            sort = "ORDER BY " + sort;
        }else {
            sort = ","+sort;
        }
        if (totalCount < integerRows * integerPage) {
            List<Map> mapList = appendToList(sqlId,
                    SqlIntercepter.create().set(sort + " " + order), myMap)
                    .subList(integerRows * (integerPage - 1), totalCount);
            map.put("rows", mapList);
        } else {
            List<Map> mapList = appendToList(sqlId,
                    SqlIntercepter.create().set(sort + " " + order), myMap)
                    .subList(integerRows * (integerPage - 1), integerRows * integerPage);
            map.put("rows", mapList);
        }
        return map;
    }

    public void printPdf(String htmlString, String pdfPath) {
        try {
            Document document = new Document(PageSize.A3);
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            document.open();
           /* document.addAuthor("pdf作者");
            document.addCreator("pdf创建者");
            document.addSubject("pdf主题");
            document.addCreationDate();
            document.addTitle("pdf标题,可在html中指定title");*/
            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            InputStream inputStream = null;
            worker.parseXHtml(pdfWriter, document,
                    new ByteArrayInputStream(htmlString.getBytes("UTF-8")),
                    inputStream, Charset.forName("UTF-8"), new AsianFontProvider());
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
