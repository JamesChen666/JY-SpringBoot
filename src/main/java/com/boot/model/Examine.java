package com.boot.model;

import com.boot.util.excel.annotation.ExcelField;
import com.boot.util.excel.converter.UserConverter.*;
import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name = "student_examine")
public class Examine implements Serializable {

    private Integer Id;
    @ExcelField(title = "学号", order = 1, writeConverter = StudentConvert.class, readConverter = StudentConvert.class)
    private Integer StudentId;
    @ExcelField(title = "字段", order = 2, writeConverter = FieldConvert.class, readConverter = FieldConvert.class)
    private Integer FieldId;
    @ExcelField(title = "修改前字段", order = 3)
    private String BeforeValue;
    @ExcelField(title = "修改前字段值", order = 4)
    private String BeforeText;
    @ExcelField(title = "修改后字段", order = 5)
    private String AfterValue;
    @ExcelField(title = "修改后字段值", order = 6)
    private String AfterText;
    @ExcelField(title = "审核状态", order = 7,writeConverter = StatusConvert.class)
    private Integer Status;
    @ExcelField(title = "修改人", order = 8,writeConverter = UserConvert.class, readConverter = UserConvert.class)
    private Integer UserId;
    @ExcelField(title = "修改时间", order = 9,writeConverter = DateConvert.class,readConverter = DateConvert.class)
    private Date CreateDate;
    @ExcelField(title = "审核人", order = 10,writeConverter = UserConvert.class, readConverter = UserConvert.class)
    private Integer ApprovalUserId;
    @ExcelField(title = "审核时间", order = 11,writeConverter = DateConvert.class,readConverter = DateConvert.class)
    private Date ApprovalDate;
    @ExcelField(title = "审核意见", order = 12)
    private String Opinion;

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

    public void setFieldId(Integer FieldId) {
        this.FieldId = FieldId;
    }

    public Integer getFieldId() {
        return FieldId;
    }

    public void setBeforeValue(String BeforeValue) {
        this.BeforeValue = BeforeValue;
    }

    public String getBeforeValue() {
        return BeforeValue;
    }

    public void setBeforeText(String BeforeText) {
        this.BeforeText = BeforeText;
    }

    public String getBeforeText() {
        return BeforeText;
    }

    public void setAfterValue(String AfterValue) {
        this.AfterValue = AfterValue;
    }

    public String getAfterValue() {
        return AfterValue;
    }

    public void setAfterText(String AfterText) {
        this.AfterText = AfterText;
    }

    public String getAfterText() {
        return AfterText;
    }

    public void setStatus(Integer Status) {
        this.Status = Status;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setUserId(Integer UserId) {
        this.UserId = UserId;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setCreateDate(Date CreateDate) {
        this.CreateDate = CreateDate;
    }

    public Date getCreateDate() {
        return CreateDate;
    }

    public Integer getApprovalUserId() {
        return ApprovalUserId;
    }

    public void setApprovalUserId(Integer approvalUserId) {
        ApprovalUserId = approvalUserId;
    }

    public Date getApprovalDate() {
        return ApprovalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        ApprovalDate = approvalDate;
    }

    public String getOpinion() {
        return Opinion;
    }

    public void setOpinion(String opinion) {
        Opinion = opinion;
    }

    @Override
    public String toString() {
        return "Examine{" +
                "Id='" + Id + '\'' +
                "StudentId='" + StudentId + '\'' +
                "FieldId='" + FieldId + '\'' +
                "BeforeValue='" + BeforeValue + '\'' +
                "BeforeText='" + BeforeText + '\'' +
                "AfterValue='" + AfterValue + '\'' +
                "AfterText='" + AfterText + '\'' +
                "Status='" + Status + '\'' +
                "UserId='" + UserId + '\'' +
                "CreateDate='" + CreateDate + '\'' +
                "ApprovalUserId='" + ApprovalUserId + '\'' +
                "ApprovalDate='" + ApprovalDate + '\'' +
                "Opinion='" + Opinion + '\'' +
                '}';
    }
}
