package com.boot.model;

import com.boot.util.excel.annotation.ExcelField;
import com.boot.util.excel.converter.UserConverter.DateConvert;
import com.boot.util.excel.converter.UserConverter.RollConvert;
import com.boot.util.excel.converter.UserConverter.StudentConvert;
import com.boot.util.excel.converter.UserConverter.UserConvert;
import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name = "student_roll")
public class Roll implements Serializable {

    private Integer Id;
    @ExcelField(title = "学号", order = 1, readConverter = StudentConvert.class, writeConverter = StudentConvert.class)
    private Integer StudentId;
    @ExcelField(title = "异动类型",order = 2,readConverter = RollConvert.class,writeConverter =RollConvert.class )
    private String TypeCode;
    @ExcelField(title = "异动原因",order = 3)
    private String Reason;
    @ExcelField(title = "异动时间",order = 4,readConverter = DateConvert.class,writeConverter = DateConvert.class)
    private Date CreateDate;
    @ExcelField(title = "操作人",order = 5,readConverter = UserConvert.class,writeConverter = UserConvert.class)
    private Integer UserId;
    @ExcelField(title = "备注",order = 6)
    private String Remarks;

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public Integer getId() {
        return Id;
    }

    public void setStudentId(Integer StudentId) {
        this.StudentId = StudentId;
    }

    public Integer getStudentId() {
        return StudentId;
    }

    public void setTypeCode(String TypeCode) {
        this.TypeCode = TypeCode;
    }

    public String getTypeCode() {
        return TypeCode;
    }

    public void setReason(String Reason) {
        this.Reason = Reason;
    }

    public String getReason() {
        return Reason;
    }

    public void setCreateDate(Date CreateDate) {
        this.CreateDate = CreateDate;
    }

    public Date getCreateDate() {
        return CreateDate;
    }

    public void setUserId(Integer UserId) {
        this.UserId = UserId;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setRemarks(String Remarks) {
        this.Remarks = Remarks;
    }

    public String getRemarks() {
        return Remarks;
    }

    @Override
    public String toString() {
        return "Roll{" +
                "Id='" + Id + '\'' +
                "StudentId='" + StudentId + '\'' +
                "TypeCode='" + TypeCode + '\'' +
                "Reason='" + Reason + '\'' +
                "CreateDate='" + CreateDate + '\'' +
                "UserId='" + UserId + '\'' +
                "Remarks='" + Remarks + '\'' +
                '}';
    }
}
