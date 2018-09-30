package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name="Election_CorpPart")
public class CorpPart implements Serializable{

	  private Integer Id;
	  private Integer CorpId;
	  private Integer ElectionId;
	  private Date ApplyDate;
	  private String RecuitFiles;
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
	  public void setCorpId(Integer CorpId) {
		this.CorpId = CorpId;
	  }
	  public Integer getCorpId() {
		return CorpId;
	  }
	  public void setElectionId(Integer ElectionId) {
		this.ElectionId = ElectionId;
	  }
	  public Integer getElectionId() {
		return ElectionId;
	  }
	  public void setApplyDate(Date ApplyDate) {
		this.ApplyDate = ApplyDate;
	  }
	  public Date getApplyDate() {
		return ApplyDate;
	  }
	  public void setRecuitFiles(String RecuitFiles) {
		this.RecuitFiles = RecuitFiles;
	  }
	  public String getRecuitFiles() {
		return RecuitFiles;
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
         return "CorpPart{" +
                "Id='" + Id + '\'' +
                "CorpId='" + CorpId + '\'' +
                "ElectionId='" + ElectionId + '\'' +
                "ApplyDate='" + ApplyDate + '\'' +
                "RecuitFiles='" + RecuitFiles + '\'' +
                "Status='" + Status + '\'' +
                "ExamineUserId='" + ExamineUserId + '\'' +
                "ExamineDate='" + ExamineDate + '\'' +
                "ExamineRemark='" + ExamineRemark + '\'' +
                '}';
      }
}
