package com.boot.model;

import com.boot.util.excel.annotation.ExcelField;
import com.boot.util.excel.converter.UserConverter.FacultyConvert;
import com.boot.util.excel.converter.UserConverter.LevelConvert;
import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;

/**
 * @author chenjiang
 */
@Table(name = "school_specialty")
public class Specialty implements Serializable {

    private Integer Id;
    @ExcelField(title = "专业代码", order =2)
    private String SpecialtyCode;
    @ExcelField(title = "专业名称", order =1)
    private String SpecialtyName;
    @ExcelField(title = "院系", order =3,writeConverter = FacultyConvert.class,readConverter = FacultyConvert.class)
    private String FacultyCode;
    @ExcelField(title = "专业国标码", order =4)
    private String GbCode;
    @ExcelField(title = "专业方向", order =5)
    private String SpecialtyDirection;
    @ExcelField(title = "培养层次", order =6,writeConverter = LevelConvert.class,readConverter = LevelConvert.class )
    private String LevelCode;
    @ExcelField(title = "学制", order =7)
    private Integer SchoolLength;
    @ExcelField(title = "备注", order =8)
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

    public void setSpecialtyName(String SpecialtyName) {
        this.SpecialtyName = SpecialtyName;
    }

    public String getSpecialtyName() {
        return SpecialtyName;
    }

    public void setFacultyCode(String FacultyCode) {
        this.FacultyCode = FacultyCode;
    }

    public String getFacultyCode() {
        return FacultyCode;
    }

    public void setGbCode(String GbCode) {
        this.GbCode = GbCode;
    }

    public String getGbCode() {
        return GbCode;
    }

    public void setSpecialtyDirection(String SpecialtyDirection) {
        this.SpecialtyDirection = SpecialtyDirection;
    }

    public String getSpecialtyDirection() {
        return SpecialtyDirection;
    }

    public void setLevelCode(String LevelCode) {
        this.LevelCode = LevelCode;
    }

    public String getLevelCode() {
        return LevelCode;
    }

    public void setSchoolLength(Integer SchoolLength) {
        this.SchoolLength = SchoolLength;
    }

    public Integer getSchoolLength() {
        return SchoolLength;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getRemark() {
        return Remark;
    }

    @Override
    public String toString() {
        return "Specialty{" +
                "Id='" + Id + '\'' +
                "SpecialtyCode='" + SpecialtyCode + '\'' +
                "SpecialtyName='" + SpecialtyName + '\'' +
                "FacultyCode='" + FacultyCode + '\'' +
                "GbCode='" + GbCode + '\'' +
                "SpecialtyDirection='" + SpecialtyDirection + '\'' +
                "LevelCode='" + LevelCode + '\'' +
                "SchoolLength='" + SchoolLength + '\'' +
                "Remark='" + Remark + '\'' +
                '}';
    }
}
