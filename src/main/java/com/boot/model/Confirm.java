package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name = "student_confirm")
public class Confirm implements Serializable {

    private Integer Id;
    private Integer StudentId;
    private Boolean IsConfirm;
    private Date ConfirmDate;
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
