package com.boot.model;

import com.boot.util.excel.annotation.ExcelField;
import com.boot.util.excel.converter.UserConverter.AreaConvert;
import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;

/**
 * @author chenjiang
 */
@Table(name = "base_area")
public class Area implements Serializable {

    private Integer Id;
    @ExcelField(title = "地区代码", order =2)
    private String AreaCode;
    @ExcelField(title = "地区名称", order =1)
    private String AreaName;
    @ExcelField(title = "上级地区", order =3,readConverter = AreaConvert.class,writeConverter = AreaConvert.class)
    private Integer ParentId;
    @ExcelField(title = "排序", order =4)
    private Integer Sort;

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public Integer getId() {
        return Id;
    }

    public void setAreaCode(String AreaCode) {
        this.AreaCode = AreaCode;
    }

    public String getAreaCode() {
        return AreaCode;
    }

    public void setAreaName(String AreaName) {
        this.AreaName = AreaName;
    }

    public String getAreaName() {
        return AreaName;
    }

    public void setParentId(Integer ParentId) {
        this.ParentId = ParentId;
    }

    public Integer getParentId() {
        return ParentId;
    }

    public void setSort(Integer Sort) {
        this.Sort = Sort;
    }

    public Integer getSort() {
        return Sort;
    }

    @Override
    public String toString() {
        return "Area{" +
                "Id='" + Id + '\'' +
                "AreaCode='" + AreaCode + '\'' +
                "AreaName='" + AreaName + '\'' +
                "ParentId='" + ParentId + '\'' +
                "Sort='" + Sort + '\'' +
                '}';
    }
}
