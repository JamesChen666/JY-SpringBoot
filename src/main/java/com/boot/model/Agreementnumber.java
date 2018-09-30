package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name="student_agreementnumber")
public class Agreementnumber implements Serializable{

	  private Integer Id;
	  private Integer StudentId;
	  private String Number;
	  private Date AllotDate;
	  private Integer UserId;
	  private Boolean IsEnabled;
	  private String InvalidReason;

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
	  public void setNumber(String Number) {
		this.Number = Number;
	  }
	  public String getNumber() {
		return Number;
	  }
	  public void setAllotDate(Date AllotDate) {
		this.AllotDate = AllotDate;
	  }
	  public Date getAllotDate() {
		return AllotDate;
	  }
	  public void setUserId(Integer UserId) {
		this.UserId = UserId;
	  }
	  public Integer getUserId() {
		return UserId;
	  }
	  public void setIsEnabled(Boolean IsEnabled) {
		this.IsEnabled = IsEnabled;
	  }
	  public Boolean getIsEnabled() {
		return IsEnabled;
	  }
	  public void setInvalidReason(String InvalidReason) {
		this.InvalidReason = InvalidReason;
	  }
	  public String getInvalidReason() {
		return InvalidReason;
	  }
      @Override
      public String toString() {
         return "Agreementnumber{" +
                "Id='" + Id + '\'' +
                "StudentId='" + StudentId + '\'' +
                "Number='" + Number + '\'' +
                "AllotDate='" + AllotDate + '\'' +
                "UserId='" + UserId + '\'' +
                "IsEnabled='" + IsEnabled + '\'' +
                "InvalidReason='" + InvalidReason + '\'' +
                '}';
      }
}
