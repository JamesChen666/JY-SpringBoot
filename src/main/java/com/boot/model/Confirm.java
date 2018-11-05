package com.boot.model;

import com.boot.util.excel.annotation.ExcelField;
import com.boot.util.excel.converter.UserConverter.DateConvert;
import com.boot.util.excel.converter.UserConverter.StudentConvert;
import com.boot.util.excel.converter.UserConverter.UserConvert;
import com.boot.util.excel.converter.UserConverter.WhetherConvert;
import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name = "student_confirm")
public class Confirm implements Serializable {

    private Integer Id;
    @ExcelField(title = "学号",order = 1,readConverter = StudentConvert.class,writeConverter = StudentConvert.class)
    private Integer StudentId;
    @ExcelField(title = "是否确认",order = 2,readConverter = WhetherConvert.class,writeConverter = WhetherConvert.class)
    private Boolean IsConfirm;
    @ExcelField(title = "确认时间",order =3,readConverter = DateConvert.class,writeConverter = DateConvert.class)
    private Date ConfirmDate;
    @ExcelField(title = "确认人",order = 4,readConverter = UserConvert.class,writeConverter = UserConvert.class)
    private Integer UserId;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public void setStudentId(Integer StudentId) {
        this.StudentId = StudentId;
    }

    public Integer getStudentId() {
        return StudentId;
    }

    public void setIsConfirm(Boolean IsConfirm) {
        this.IsConfirm = IsConfirm;
    }

    public Boolean getIsConfirm() {
        return IsConfirm;
    }

    public void setConfirmDate(Date ConfirmDate) {
        this.ConfirmDate = ConfirmDate;
    }

    public Date getConfirmDate() {
        return ConfirmDate;
    }

    public void setUserId(Integer UserId) {
        this.UserId = UserId;
    }

    public Integer getUserId() {
        return UserId;
    }

    @Override
    public String toString() {
        return "Confirm{" +
                "Id='" + Id + '\'' +
                "StudentId='" + StudentId + '\'' +
                "IsConfirm='" + IsConfirm + '\'' +
                "ConfirmDate='" + ConfirmDate + '\'' +
                "UserId='" + UserId + '\'' +
                '}';
    }
}
