package com.boot.model;

import com.boot.util.excel.annotation.ExcelField;
import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;

/**
 * @author chenjiang
 */
@Table(name = "base_dictionary")
public class Dictionary implements Serializable {

    private Integer Id;
    @ExcelField(title = "类型代码",order = 1)
    private String TypeCode;
    @ExcelField(title = "字段代码",order = 2)
    private String MemberValue;
    @ExcelField(title = "字典值",order = 3)
    private String DisplayText;
    @ExcelField(title = "排序",order = 4)
    private Integer Sort;

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public Integer getId() {
        return Id;
    }

    public void setTypeCode(String TypeCode) {
        this.TypeCode = TypeCode;
    }

    public String getTypeCode() {
        return TypeCode;
    }

    public String getMemberValue() {
        return MemberValue;
    }

    public void setMemberValue(String memberValue) {
        MemberValue = memberValue;
    }

    public String getDisplayText() {
        return DisplayText;
    }

    public void setDisplayText(String displayText) {
        DisplayText = displayText;
    }

    public void setSort(Integer Sort) {
        this.Sort = Sort;
    }

    public Integer getSort() {
        return Sort;
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "Id='" + Id + '\'' +
                "TypeCode='" + TypeCode + '\'' +
                "MemberCode='" + MemberValue + '\'' +
                "DisplayValue='" + DisplayText + '\'' +
                "Sort='" + Sort + '\'' +
                '}';
    }
}
