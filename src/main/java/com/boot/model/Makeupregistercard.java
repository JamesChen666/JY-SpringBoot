package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name="student_makeupregistercard")
public class Makeupregistercard implements Serializable{

	  private Integer Id;
	  private Integer StudentId;
	  private String TypeCode;
	  private Date CreateDate;
	  private Integer UserId;
	  private Integer Status;
	  private Integer ExamineUserId;
	  private Date ExamineDate;
	  private String ExamineRemark;

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
	  public void setStatus(Integer Status) {
		this.Status = Status;
	  }
	  public Integer getStatus() {
		return Status;
	  }
	  public void setExamineUserId(Integer ExamineUserId) {
		this.ExamineUserId = ExamineUserId;
	  }
	  public Integer getExamineUserId() {
		return ExamineUserId;
	  }
	  public void setExamineDate(Date ExamineDate) {
		this.ExamineDate = ExamineDate;
	  }
	  public Date getExamineDate() {
		return ExamineDate;
	  }
	  public void setExamineRemark(String ExamineRemark) {
		this.ExamineRemark = ExamineRemark;
	  }
	  public String getExamineRemark() {
		return ExamineRemark;
	  }
      @Override
      public String toString() {
         return "Makeupregistercard{" +
                "Id='" + Id + '\'' +
                "StudentId='" + StudentId + '\'' +
                "TypeCode='" + TypeCode + '\'' +
                "CreateDate='" + CreateDate + '\'' +
                "UserId='" + UserId + '\'' +
                "Status='" + Status + '\'' +
                "ExamineUserId='" + ExamineUserId + '\'' +
                "ExamineDate='" + ExamineDate + '\'' +
                "ExamineRemark='" + ExamineRemark + '\'' +
                '}';
      }
}
