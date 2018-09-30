package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name="Specia_Place")
public class Place implements Serializable{

	  private Integer Id;
	  private String Title;
	  private String Address;
	  private Integer TypeId;
	  private String Contactor;
	  private String ContactPhone;
	  private Integer Capacity;
	  private Boolean IsEnabled;
	  private Date StartDate;
	  private Date EndDate;
	  private String StartHour;
	  private String EndHour;

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
	  public void setTypeId(Integer TypeId) {
		this.TypeId = TypeId;
	  }
	  public Integer getTypeId() {
		return TypeId;
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
	  public void setCapacity(Integer Capacity) {
		this.Capacity = Capacity;
	  }
	  public Integer getCapacity() {
		return Capacity;
	  }
	  public void setIsEnabled(Boolean IsEnabled) {
		this.IsEnabled = IsEnabled;
	  }
	  public Boolean getIsEnabled() {
		return IsEnabled;
	  }
	  public void setStartDate(Date StartDate) {
		this.StartDate = StartDate;
	  }
	  public Date getStartDate() {
		return StartDate;
	  }
	  public void setEndDate(Date EndDate) {
		this.EndDate = EndDate;
	  }
	  public Date getEndDate() {
		return EndDate;
	  }
	  public void setStartHour(String StartHour) {
		this.StartHour = StartHour;
	  }
	  public String getStartHour() {
		return StartHour;
	  }
	  public void setEndHour(String EndHour) {
		this.EndHour = EndHour;
	  }
	  public String getEndHour() {
		return EndHour;
	  }
      @Override
      public String toString() {
         return "Place{" +
                "Id='" + Id + '\'' +
                "Title='" + Title + '\'' +
                "Address='" + Address + '\'' +
                "TypeId='" + TypeId + '\'' +
                "Contactor='" + Contactor + '\'' +
                "ContactPhone='" + ContactPhone + '\'' +
                "Capacity='" + Capacity + '\'' +
                "IsEnabled='" + IsEnabled + '\'' +
                "StartDate='" + StartDate + '\'' +
                "EndDate='" + EndDate + '\'' +
                "StartHour='" + StartHour + '\'' +
                "EndHour='" + EndHour + '\'' +
                '}';
      }
}
