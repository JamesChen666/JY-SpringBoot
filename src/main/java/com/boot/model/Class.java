package com.boot.model;

import com.boot.util.excel.annotation.ExcelField;
import com.boot.util.excel.converter.UserConverter.SpecialtyConvert;
import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;

/**
 * @author chenjiang
 */
@Table(name = "school_class")
public class Class implements Serializable {

    private Integer Id;
    @ExcelField(title = "所属专业", order = 3,readConverter = SpecialtyConvert.class,writeConverter = SpecialtyConvert.class )
    private String SpecialtyCode;
    @ExcelField(title = "班级编号", order = 2)
    private String ClassNo;
    @ExcelField(title = "班级名称", order = 1)
    private String ClassName;
    @ExcelField(title = "年级", order = 4)
    private String Grade;
    @ExcelField(title = "毕业年份", order = 5)
    private String GraduationYear;
    @ExcelField(title = "备注", order = 6)
    private String Remark;

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public Integer getId() {
        return Id;
    }

    public void setSpecialtyCode(String SpecialtyCode) {
        this.SpecialtyCode = SpecialtyCode;
    }

    public String getSpecialtyCode() {
        return SpecialtyCode;
    }

    public void setClassNo(String ClassNo) {
        this.ClassNo = ClassNo;
    }

    public String getClassNo() {
        return ClassNo;
    }

    public void setClassName(String ClassName) {
        this.ClassName = ClassName;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setGrade(String Grade) {
        this.Grade = Grade;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGraduationYear(String GraduationYear) {
        this.GraduationYear = GraduationYear;
    }

    public String getGraduationYear() {
        return GraduationYear;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getRemark() {
        return Remark;
    }

    @Override
    public String toString() {
        return "Class{" +
                "Id='" + Id + '\'' +
                "SpecialtyCode='" + SpecialtyCode + '\'' +
                "ClassNo='" + ClassNo + '\'' +
                "ClassName='" + ClassName + '\'' +
                "Grade='" + Grade + '\'' +
                "GraduationYear='" + GraduationYear + '\'' +
                "Remark='" + Remark + '\'' +
                '}';
    }
}
