package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name = "student_examine")
public class Examine implements Serializable {

    private Integer Id;
    private Integer StudentId;
    private Integer FieldId;
    private String BeforeValue;
    private String BeforeText;
    private String AfterValue;
    private String AfterText;
    private Integer Status;
    private Integer UserId;
    private Date CreateDate;
    private Integer ApprovalUserId;
    private Date ApprovalDate;
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
