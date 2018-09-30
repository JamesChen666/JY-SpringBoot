package com.boot.controller.system;

import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.boot.util.AjaxResult;
import com.boot.util.JavaAttribute;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenjiang
 */
@Controller
@RequestMapping("/templates")
public class TemplatesController extends AjaxResult {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    DruidDataSource DruidDataSource;

    @RequestMapping("/")
    public String templates(){
        return "/freemarker/generateModel";
    }

    /**
     * 通过数据库生成所有的模板
     *
     * @param HttpServletRequest 传入参数 packageName包名 dataBaseName数据库名称
     * @return
     */
    @ResponseBody
    @RequestMapping("/dataBase")
    public AjaxResult dataBase(HttpServletRequest HttpServletRequest) {
        String packageName = HttpServletRequest.getParameter("packageName");
        String dataBase = HttpServletRequest.getParameter("dataBaseName");
        DruidPooledConnection DruidPooledConnection;
        List<String> tableList = new ArrayList();
        List<String> classList = new ArrayList();
        try {
            DruidPooledConnection = DruidDataSource.getConnection();
            DatabaseMetaData metaData = DruidPooledConnection.getMetaData();
            String[] tb = {"TABLE"};
            ResultSet tables = metaData.getTables(dataBase, null, null, tb);
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                if (tableName.split("_").length > 0) {
                    String className = createClassName(tableName);
                    classList.add(className);
                }
                tableList.add(tableName);

            }
            for (int i = 0; i <= tableList.size(); i++) {
                java.util.List<JavaAttribute> JavaAttributes = new ArrayList<>();
                PreparedStatement preparedStatement = DruidPooledConnection.prepareStatement("select * from " + tableList.get(i) + "");
                ResultSet resultSet = preparedStatement.executeQuery();
                ResultSetMetaData ResultSetMetaData = resultSet.getMetaData();
                int columnCount = ResultSetMetaData.getColumnCount();
                for (int j = 1; j <= columnCount; j++) {
                    String columnTypeName = ResultSetMetaData.getColumnTypeName(j);
                    String javaType = dbTypeToJavaType(columnTypeName);
                    JavaAttributes.add(new JavaAttribute(javaType, ResultSetMetaData.getColumnName(j)));
                }
                creatTemplates(packageName, tableList.get(i), classList.get(i), JavaAttributes);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return error("错误");
        }
        return success("操作成功");
    }

    /**
     * 通过数据库表生成对应的模板
     *
     * @param HttpServletRequest 传入参数 packageName 包名 tableName表名
     * @return
     */
    @ResponseBody
    @RequestMapping("/table")
    public AjaxResult start(HttpServletRequest HttpServletRequest) {
        String packageName = HttpServletRequest.getParameter("packageName");
        String tableName = HttpServletRequest.getParameter("tableName");
        try {
            DruidPooledConnection DruidPooledConnection = DruidDataSource.getConnection();
            java.util.List<JavaAttribute> JavaAttributes = new ArrayList<>();
            PreparedStatement preparedStatement = DruidPooledConnection.prepareStatement("select * from " + tableName);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData ResultSetMetaData = resultSet.getMetaData();
            int columnCount = ResultSetMetaData.getColumnCount();
            for (int j = 1; j <= columnCount; j++) {
                String columnTypeName = ResultSetMetaData.getColumnTypeName(j);
                String javaType = dbTypeToJavaType(columnTypeName);
                JavaAttributes.add(new JavaAttribute(javaType, ResultSetMetaData.getColumnName(j)));
            }
            String className =createClassName(tableName);
            creatTemplates(packageName, tableName, className, JavaAttributes);
        } catch (SQLException e) {
            e.printStackTrace();
            return error("错误");
        }
        return success("操作成功");
    }


    /**
     * 模板生成方法
     * @param packageName 包名
     * @param tableName 表名
     * @param className 类名
     * @param JavaAttributes 属性名称集合
     */
    private void creatTemplates(@NotNull String packageName, @NotNull String tableName, String className, List<JavaAttribute> JavaAttributes) {
        Configuration Configuration = new Configuration(freemarker.template.Configuration.VERSION_2_3_23);
        String javaBeanTemplatesPath = System.getProperty("user.dir") + "\\src\\main\\resources\\freemarkerTemplates\\javaBeanTemplates.ftl";
        String controllerTemplatesPath = System.getProperty("user.dir") + "\\src\\main\\resources\\freemarkerTemplates\\controllerTemplates.ftl";
        String beetlSqlTemplatesPath = System.getProperty("user.dir") + "\\src\\main\\resources\\freemarkerTemplates\\beetlSqlTemplates.ftl";
        String listHtmlTemplatesPath = System.getProperty("user.dir") + "\\src\\main\\resources\\freemarkerTemplates\\listHtmlTemplates.ftl";
        String addHtmlTemplatesPath = System.getProperty("user.dir") + "\\src\\main\\resources\\freemarkerTemplates\\addHtmlTemplates.ftl";
        String editHtmlTemplatesPath = System.getProperty("user.dir") + "\\src\\main\\resources\\freemarkerTemplates\\editHtmlTemplates.ftl";
        File javaClassFile = new File(javaBeanTemplatesPath);
        File controllerFile = new File(controllerTemplatesPath);
        File MdFile = new File(beetlSqlTemplatesPath);
        File listHtmlFile = new File(listHtmlTemplatesPath);
        File addHtmlFile = new File(addHtmlTemplatesPath);
        File editHtmlFile = new File(editHtmlTemplatesPath);
        try {
            Reader javaClassReader = new FileReader(javaClassFile);
            Reader controllerReader = new FileReader(controllerFile);
            Reader MdReader = new FileReader(MdFile);
            Reader listHtmlFileReader = new FileReader(listHtmlFile);
            Reader addHtmlFileReader= new FileReader(addHtmlFile);
            Reader editHtmlFileReader = new FileReader(editHtmlFile);
            Template javaClassTemplate = new Template("javaBeanTemplates.ftl", javaClassReader, Configuration);
            Template controllerTemplate = new Template("controllerTemplates.ftl", controllerReader, Configuration);
            Template MdTemplate = new Template("beetlSqlTemplates.ftl", MdReader, Configuration);
            Template listHtmlTemplate = new Template("listHtmlTemplates.ftl", listHtmlFileReader, Configuration);
            Template addHtmlTemplate = new Template("addHtmlTemplates.ftl", addHtmlFileReader, Configuration);
            Template editHtmlTemplate = new Template("editHtmlTemplates.ftl", editHtmlFileReader, Configuration);
            Map<String, Object> map = new HashMap<>();
            map.put("packageName", packageName);
            String javaClassName = StrUtil.upperFirst(className);
            map.put("className", javaClassName);
            map.put("attrs", JavaAttributes);
            map.put("tableName", tableName);
            String replace = packageName.replace(".", "\\");
            String modelPackagePath = System.getProperty("user.dir") + "\\src\\main\\java\\" + replace + "\\model";
            java.io.File modelPackageFlie = new File(modelPackagePath);
            if (!modelPackageFlie.exists()) {
                modelPackageFlie.mkdirs();
            }
            String javaClassPath = System.getProperty("user.dir") + "\\src\\main\\java\\" + replace + "\\model\\" + javaClassName + ".java";
            OutputStream javaClassOutputStream = new FileOutputStream(javaClassPath);
            Writer javaClassWriter = new OutputStreamWriter(javaClassOutputStream);
            javaClassTemplate.process(map, javaClassWriter);
            javaClassOutputStream.close();
            javaClassWriter.close();

            String controllerPackagePath = System.getProperty("user.dir") + "\\src\\main\\java\\" + replace + "\\controller";
            java.io.File controllerPackageFlie = new File(controllerPackagePath);
            if (!controllerPackageFlie.exists()) {
                controllerPackageFlie.mkdirs();
            }
            String controllerName = javaClassName.concat("Controller");
            String controllerPath = System.getProperty("user.dir") + "\\src\\main\\java\\" + replace + "\\controller\\" + controllerName + ".java";
            OutputStream controllerOutputStream = new FileOutputStream(controllerPath);
            Writer controllerWriter = new OutputStreamWriter(controllerOutputStream);
            controllerTemplate.process(map, controllerWriter);
            controllerOutputStream.close();
            controllerWriter.close();

            String mdPackagePath = System.getProperty("user.dir") + "\\src\\main\\resources\\beetlsql";
            java.io.File mdPackageFlie = new File(mdPackagePath);
            if (!mdPackageFlie.exists()) {
                controllerPackageFlie.mkdirs();
            }

            String sqlFileName = StrUtil.lowerFirst(className);
            String mdPath = System.getProperty("user.dir") + "\\src\\main\\resources\\beetlsql\\" + sqlFileName + ".sql";
            OutputStream mdOutputStream = new FileOutputStream(mdPath);
            Writer mdWriter = new OutputStreamWriter(mdOutputStream);
            MdTemplate.process(map, mdWriter);
            mdOutputStream.close();
            mdWriter.close();

            String lowerFirst = StrUtil.lowerFirst(javaClassName);
            String htmlPackagePath = System.getProperty("user.dir") + "\\src\\main\\webapp\\WEB-INF\\views\\"+lowerFirst;
            java.io.File htmlPackageFile = new File(htmlPackagePath);
            if (!htmlPackageFile.exists()) {
                htmlPackageFile.mkdirs();
            }

            String listHtmlName = StrUtil.lowerFirst(className).concat("_list");
            String addHtmlName = StrUtil.lowerFirst(className).concat("_add");
            String editHtmlName = StrUtil.lowerFirst(className).concat("_edit");
            String listHtmlPath = System.getProperty("user.dir") + "\\src\\main\\webapp\\WEB-INF\\views\\" + lowerFirst + "\\"+listHtmlName+".html";
            String addHtmlPath = System.getProperty("user.dir") + "\\src\\main\\webapp\\WEB-INF\\views\\" + lowerFirst + "\\"+addHtmlName+".html";
            String editHtmlPath = System.getProperty("user.dir") + "\\src\\main\\webapp\\WEB-INF\\views\\" + lowerFirst + "\\"+editHtmlName+".html";
            OutputStream listHtmlOutputStream = new FileOutputStream(listHtmlPath);
            Writer listHtmlWriter = new OutputStreamWriter(listHtmlOutputStream);
            listHtmlTemplate.process(map, listHtmlWriter);
            listHtmlOutputStream.close();
            listHtmlWriter.close();
            OutputStream addHtmlOutputStream = new FileOutputStream(addHtmlPath);
            Writer addHtmlWriter = new OutputStreamWriter(addHtmlOutputStream);
            addHtmlTemplate.process(map, addHtmlWriter);
            addHtmlOutputStream.close();
            addHtmlWriter.close();
            OutputStream editHtmlOutputStream = new FileOutputStream(editHtmlPath);
            Writer editHtmlWriter = new OutputStreamWriter(editHtmlOutputStream);
            editHtmlTemplate.process(map, editHtmlWriter);
            editHtmlOutputStream.close();
            editHtmlWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据库字段对应实体类字段
     * @param dbType 数据库字段类型
     * @return
     */
    private String dbTypeToJavaType(@NotNull String dbType) {
        if (dbType == "VARCHAR") {
            return "String";
        } else if (dbType == "CHAR") {
            return "String";
        } else if (dbType == "TEXT") {
            return "String";
        } else if (dbType == "LONGTEXT") {
            return "String";
        } else if (dbType == "INTEGER") {
            return "Integer";
        } else if (dbType == "TINYINT") {
            return "Integer";
        } else if (dbType == "SMALLINT") {
            return "Integer";
        } else if (dbType == "MEDIUMINT") {
            return "Integer";
        } else if (dbType == "BIGINT") {
            return "Integer";
        } else if (dbType == "BIT") {
            return "Boolean";
        } else if (dbType == "INT") {
            return "Integer";
        } else if (dbType == "FLOAT") {
            return "Float";
        } else if (dbType == "DOUBLE") {
            return "Double";
        } else if (dbType == "DECIMAL") {
            return "BigDecimal";
        } else if (dbType == "DATE") {
            return "Date";
        } else if (dbType == "TIME") {
            return "Time";
        } else if (dbType == "DATETIME") {
            return "Date";
        } else if (dbType == "YEAR") {
            return "Date";
        } else if (dbType == "TIMESTAMP") {
            return "Timestamp";
        } else if (dbType == "BOOLEAN") {
            return "Integer";
        } else {
            return "String";
        }
    }

    private  String createClassName(String str){
        int i = str.indexOf("_");
        String[] substring = str.substring(str.indexOf("_")+1).split("_");
        StringBuffer StringBuffer = new StringBuffer();
        for (String s : substring) {
            String concat = s.substring(0, 1).toUpperCase().concat(s.substring(1));
            StringBuffer.append(concat);
        }
        return StringBuffer.toString();
    }
}
