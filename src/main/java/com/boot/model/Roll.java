package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name="student_roll")
public class Roll implements Serializable{

	  private Integer Id;
	  private Integer StudentId;
	  private String TypeCode;
	  private String Reason;
	  private Date CreateDate;
	  private Integer UserId;
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
