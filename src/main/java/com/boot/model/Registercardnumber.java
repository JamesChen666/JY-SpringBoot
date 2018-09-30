package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name="student_registercardnumber")
public class Registercardnumber implements Serializable{

	  private Integer Id;
	  private Integer StudentId;
	  private String Number;
	  private Date AllotDate;
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
      @Override
      public String toString() {
         return "Registercardnumber{" +
                "Id='" + Id + '\'' +
                "StudentId='" + StudentId + '\'' +
                "Number='" + Number + '\'' +
                "AllotDate='" + AllotDate + '\'' +
                "UserId='" + UserId + '\'' +
                '}';
      }
}
