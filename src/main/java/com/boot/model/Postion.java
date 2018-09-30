package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name="Corp_Postion")
public class Postion implements Serializable{

	  private Integer Id;
	  private Integer CorpId;
	  private String Title;
	  private String WorkAddress;
	  private String IndustryCode;
	  private String TypeCode;
	  private String 面向专业;
	  private String LevelCode;
	  private String PositionTypeCode;
	  private String RecuitTypeCode;
	  private Integer PeopleCount;
	  private Integer WorkYearLimit;
	  private Integer Salary;
	  private String Contactor;
	  private String ContactPhone;
	  private String Functions;
	  private String Requirement;
	  private String RecuitFiles;
	  private Boolean IsPortal;
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
	  public void setTitle(String Title) {
		this.Title = Title;
	  }
	  public String getTitle() {
		return Title;
	  }
	  public void setWorkAddress(String WorkAddress) {
		this.WorkAddress = WorkAddress;
	  }
	  public String getWorkAddress() {
		return WorkAddress;
	  }
	  public void setIndustryCode(String IndustryCode) {
		this.IndustryCode = IndustryCode;
	  }
	  public String getIndustryCode() {
		return IndustryCode;
	  }
	  public void setTypeCode(String TypeCode) {
		this.TypeCode = TypeCode;
	  }
	  public String getTypeCode() {
		return TypeCode;
	  }
	  public void set面向专业(String 面向专业) {
		this.面向专业 = 面向专业;
	  }
	  public String get面向专业() {
		return 面向专业;
	  }
	  public void setLevelCode(String LevelCode) {
		this.LevelCode = LevelCode;
	  }
	  public String getLevelCode() {
		return LevelCode;
	  }
	  public void setPositionTypeCode(String PositionTypeCode) {
		this.PositionTypeCode = PositionTypeCode;
	  }
	  public String getPositionTypeCode() {
		return PositionTypeCode;
	  }
	  public void setRecuitTypeCode(String RecuitTypeCode) {
		this.RecuitTypeCode = RecuitTypeCode;
	  }
	  public String getRecuitTypeCode() {
		return RecuitTypeCode;
	  }
	  public void setPeopleCount(Integer PeopleCount) {
		this.PeopleCount = PeopleCount;
	  }
	  public Integer getPeopleCount() {
		return PeopleCount;
	  }
	  public void setWorkYearLimit(Integer WorkYearLimit) {
		this.WorkYearLimit = WorkYearLimit;
	  }
	  public Integer getWorkYearLimit() {
		return WorkYearLimit;
	  }
	  public void setSalary(Integer Salary) {
		this.Salary = Salary;
	  }
	  public Integer getSalary() {
		return Salary;
	  }
	  public void setContactor(String Contactor) {
		this.Contactor = Contactor;
	  }
	  public String getContactor() {
		return Contactor;
	  }
	  public void setContactPhone(String ContactPhone) {
		this.ContactPhone = ContactPhone;
	  }
	  public String getContactPhone() {
		return ContactPhone;
	  }
	  public void setFunctions(String Functions) {
		this.Functions = Functions;
	  }
	  public String getFunctions() {
		return Functions;
	  }
	  public void setRequirement(String Requirement) {
		this.Requirement = Requirement;
	  }
	  public String getRequirement() {
		return Requirement;
	  }
	  public void setRecuitFiles(String RecuitFiles) {
		this.RecuitFiles = RecuitFiles;
	  }
	  public String getRecuitFiles() {
		return RecuitFiles;
	  }
	  public void setIsPortal(Boolean IsPortal) {
		this.IsPortal = IsPortal;
	  }
	  public Boolean getIsPortal() {
		return IsPortal;
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
         return "Postion{" +
                "Id='" + Id + '\'' +
                "CorpId='" + CorpId + '\'' +
                "Title='" + Title + '\'' +
                "WorkAddress='" + WorkAddress + '\'' +
                "IndustryCode='" + IndustryCode + '\'' +
                "TypeCode='" + TypeCode + '\'' +
                "面向专业='" + 面向专业 + '\'' +
                "LevelCode='" + LevelCode + '\'' +
                "PositionTypeCode='" + PositionTypeCode + '\'' +
                "RecuitTypeCode='" + RecuitTypeCode + '\'' +
                "PeopleCount='" + PeopleCount + '\'' +
                "WorkYearLimit='" + WorkYearLimit + '\'' +
                "Salary='" + Salary + '\'' +
                "Contactor='" + Contactor + '\'' +
                "ContactPhone='" + ContactPhone + '\'' +
                "Functions='" + Functions + '\'' +
                "Requirement='" + Requirement + '\'' +
                "RecuitFiles='" + RecuitFiles + '\'' +
                "IsPortal='" + IsPortal + '\'' +
                "Status='" + Status + '\'' +
                "ExamineUserId='" + ExamineUserId + '\'' +
                "ExamineDate='" + ExamineDate + '\'' +
                "ExamineRemark='" + ExamineRemark + '\'' +
                '}';
      }
}
