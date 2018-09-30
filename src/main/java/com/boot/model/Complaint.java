package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name="student_complaint")
public class Complaint implements Serializable{

	  private Integer Id;
	  private Integer StudentId;
	  private Integer CompanyId;
	  private Integer JobId;
	  private String TypeCode;
	  private String Reason;
	  private Date CreateDate;
	  private Integer Status;
	  private Date AuditDate;
	  private Integer AuditUserId;
	  private String AuditReamrks;

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
	  public void setCompanyId(Integer CompanyId) {
		this.CompanyId = CompanyId;
	  }
	  public Integer getCompanyId() {
		return CompanyId;
	  }
	  public void setJobId(Integer JobId) {
		this.JobId = JobId;
	  }
	  public Integer getJobId() {
		return JobId;
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
	  public void setStatus(Integer Status) {
		this.Status = Status;
	  }
	  public Integer getStatus() {
		return Status;
	  }
	  public void setAuditDate(Date AuditDate) {
		this.AuditDate = AuditDate;
	  }
	  public Date getAuditDate() {
		return AuditDate;
	  }
	  public void setAuditUserId(Integer AuditUserId) {
		this.AuditUserId = AuditUserId;
	  }
	  public Integer getAuditUserId() {
		return AuditUserId;
	  }
	  public void setAuditReamrks(String AuditReamrks) {
		this.AuditReamrks = AuditReamrks;
	  }
	  public String getAuditReamrks() {
		return AuditReamrks;
	  }
      @Override
      public String toString() {
         return "Complaint{" +
                "Id='" + Id + '\'' +
                "StudentId='" + StudentId + '\'' +
                "CompanyId='" + CompanyId + '\'' +
                "JobId='" + JobId + '\'' +
                "TypeCode='" + TypeCode + '\'' +
                "Reason='" + Reason + '\'' +
                "CreateDate='" + CreateDate + '\'' +
                "Status='" + Status + '\'' +
                "AuditDate='" + AuditDate + '\'' +
                "AuditUserId='" + AuditUserId + '\'' +
                "AuditReamrks='" + AuditReamrks + '\'' +
                '}';
      }
}
