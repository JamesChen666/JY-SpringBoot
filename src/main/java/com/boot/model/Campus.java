package com.boot.model;

import com.boot.util.excel.annotation.ExcelField;
import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;

/**
 * @author chenjiang
 */
@Table(name = "school_campus")
public class Campus implements Serializable {

    private Integer Id;
    @ExcelField(title = "校区名称", order =1)
    private String CampusName;
    @ExcelField(title = "校区代码", order =2)
    private String CampusCode;
    @ExcelField(title = "地址", order =3)
    private String Address;
    @ExcelField(title = "排序", order =4)
    private Integer Sort;

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public Integer getId() {
        return Id;
    }

    public void setCampusName(String CampusName) {
        this.CampusName = CampusName;
    }

    public String getCampusName() {
        return CampusName;
    }

    public void setCampusCode(String CampusCode) {
        this.CampusCode = CampusCode;
    }

    public String getCampusCode() {
        return CampusCode;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getAddress() {
        return Address;
    }

    public void setSort(Integer Sort) {
        this.Sort = Sort;
    }

    public Integer getSort() {
        return Sort;
    }
}
