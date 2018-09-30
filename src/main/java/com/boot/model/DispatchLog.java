package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name="student_dispatch_log")
public class DispatchLog implements Serializable{

	  private Integer Id;
	  private Integer StudentId;
	  private String IssueTypeCode;
	  private String CorporationName;
	  private String OriginCode;
	  private String Remarks;
	  private Date CreateDate;
	  private Integer UserId;

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
	  public void setIssueTypeCode(String IssueTypeCode) {
		this.IssueTypeCode = IssueTypeCode;
	  }
	  public String getIssueTypeCode() {
		return IssueTypeCode;
	  }
	  public void setCorporationName(String CorporationName) {
		this.CorporationName = CorporationName;
	  }
	  public String getCorporationName() {
		return CorporationName;
	  }
	  public void setOriginCode(String OriginCode) {
		this.OriginCode = OriginCode;
	  }
	  public String getOriginCode() {
		return OriginCode;
	  }
	  public void setRemarks(String Remarks) {
		this.Remarks = Remarks;
	  }
	  public String getRemarks() {
		return Remarks;
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
      @Override
      public String toString() {
         return "DispatchLog{" +
                "Id='" + Id + '\'' +
                "StudentId='" + StudentId + '\'' +
                "IssueTypeCode='" + IssueTypeCode + '\'' +
                "CorporationName='" + CorporationName + '\'' +
                "OriginCode='" + OriginCode + '\'' +
                "Remarks='" + Remarks + '\'' +
                "CreateDate='" + CreateDate + '\'' +
                "UserId='" + UserId + '\'' +
                '}';
      }
}
