package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="school_teacher")
public class Teacher implements Serializable{

	  private Integer Id;
	  private String RealName;
	  private String JobNumber;
	  private Boolean Sex;
	  private Integer Age;
	  private String Contact;
	  private String FacultyCode;

	  public void setId(Integer Id) {
		this.Id = Id;
	  }
	  public Integer getId() {
		return Id;
	  }
	  public void setRealName(String RealName) {
		this.RealName = RealName;
	  }
	  public String getRealName() {
		return RealName;
	  }
	  public void setJobNumber(String JobNumber) {
		this.JobNumber = JobNumber;
	  }
	  public String getJobNumber() {
		return JobNumber;
	  }
	  public void setSex(Boolean Sex) {
		this.Sex = Sex;
	  }
	  public Boolean getSex() {
		return Sex;
	  }
	  public void setAge(Integer Age) {
		this.Age = Age;
	  }
	  public Integer getAge() {
		return Age;
	  }
	  public void setContact(String Contact) {
		this.Contact = Contact;
	  }
	  public String getContact() {
		return Contact;
	  }
	  public void setFacultyCode(String FacultyCode) {
		this.FacultyCode = FacultyCode;
	  }
	  public String getFacultyCode() {
		return FacultyCode;
	  }
      @Override
      public String toString() {
         return "Teacher{" +
                "Id='" + Id + '\'' +
                "RealName='" + RealName + '\'' +
                "JobNumber='" + JobNumber + '\'' +
                "Sex='" + Sex + '\'' +
                "Age='" + Age + '\'' +
                "Contact='" + Contact + '\'' +
                "FacultyCode='" + FacultyCode + '\'' +
                '}';
      }
}
