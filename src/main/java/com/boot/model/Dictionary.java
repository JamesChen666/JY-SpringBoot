package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;

/**
 * @author chenjiang
 */
@Table(name = "base_dictionary")
public class Dictionary implements Serializable {

    private Integer Id;
    private String TypeCode;
    private String MemberValue;
    private String DisplayText;
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
