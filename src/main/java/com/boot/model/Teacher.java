package com.boot.model;

import com.boot.util.excel.annotation.ExcelField;
import com.boot.util.excel.converter.UserConverter.FacultyConvert;
import com.boot.util.excel.converter.UserConverter.SexConvert;
import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;

/**
 * @author chenjiang
 */
@Table(name = "school_teacher")
public class Teacher implements Serializable {

    private Integer Id;
    @ExcelField(title = "姓名", order =1)
    private String RealName;
    @ExcelField(title = "工号", order =2)
    private String JobNumber;
    @ExcelField(title = "性别", order =3,writeConverter = SexConvert.class,readConverter = SexConvert.class)
    private Boolean Sex;
    @ExcelField(title = "年龄", order =4)
    private Integer Age;
    @ExcelField(title = "联系方式", order =5)
    private String Contact;
    @ExcelField(title = "所属院系", order =6,writeConverter = FacultyConvert.class,readConverter = FacultyConvert.class)
    private String FacultyCode;

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public Integer getId() {
        return Id;
    }

    public void setRealName(String RealName) {
        this.RealName = RealName;
    }

    public String getRealName() {
        return RealName;
    }

    public void setJobNumber(String JobNumber) {
        this.JobNumber = JobNumber;
    }

    public String getJobNumber() {
        return JobNumber;
    }

    public void setSex(Boolean Sex) {
        this.Sex = Sex;
    }

    public Boolean getSex() {
        return Sex;
    }

    public void setAge(Integer Age) {
        this.Age = Age;
    }

    public Integer getAge() {
        return Age;
    }

    public void setContact(String Contact) {
        this.Contact = Contact;
    }

    public String getContact() {
        return Contact;
    }

    public void setFacultyCode(String FacultyCode) {
        this.FacultyCode = FacultyCode;
    }

    public String getFacultyCode() {
        return FacultyCode;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "Id='" + Id + '\'' +
                "RealName='" + RealName + '\'' +
                "JobNumber='" + JobNumber + '\'' +
                "Sex='" + Sex + '\'' +
                "Age='" + Age + '\'' +
                "Contact='" + Contact + '\'' +
                "FacultyCode='" + FacultyCode + '\'' +
                '}';
    }
}
