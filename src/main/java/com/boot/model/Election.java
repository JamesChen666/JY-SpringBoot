package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name="School_Election")
public class Election implements Serializable{

	  private Integer Id;
	  private String Title;
	  private String Address;
	  private Date RunDate;
	  private Date SignEndDate;
	  private String Description;
	  private Integer UserId;
	  private Date CreateDate;
	  private Boolean IsEnabled;

	  public void setId(Integer Id) {
		this.Id = Id;
	  }
	  public Integer getId() {
		return Id;
	  }
	  public void setTitle(String Title) {
		this.Title = Title;
	  }
	  public String getTitle() {
		return Title;
	  }
	  public void setAddress(String Address) {
		this.Address = Address;
	  }
	  public String getAddress() {
		return Address;
	  }
	  public void setRunDate(Date RunDate) {
		this.RunDate = RunDate;
	  }
	  public Date getRunDate() {
		return RunDate;
	  }
	  public void setSignEndDate(Date SignEndDate) {
		this.SignEndDate = SignEndDate;
	  }
	  public Date getSignEndDate() {
		return SignEndDate;
	  }
	  public void setDescription(String Description) {
		this.Description = Description;
	  }
	  public String getDescription() {
		return Description;
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
	  public void setIsEnabled(Boolean IsEnabled) {
		this.IsEnabled = IsEnabled;
	  }
	  public Boolean getIsEnabled() {
		return IsEnabled;
	  }
      @Override
      public String toString() {
         return "Election{" +
                "Id='" + Id + '\'' +
                "Title='" + Title + '\'' +
                "Address='" + Address + '\'' +
                "RunDate='" + RunDate + '\'' +
                "SignEndDate='" + SignEndDate + '\'' +
                "Description='" + Description + '\'' +
                "UserId='" + UserId + '\'' +
                "CreateDate='" + CreateDate + '\'' +
                "IsEnabled='" + IsEnabled + '\'' +
                '}';
      }
}
