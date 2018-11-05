package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name="Recruit_StudentReport")
public class StudentReport implements Serializable{

	  private Integer Id;
	  private Integer SpecialId;
	  private Integer StudentId;
	  private Date EnrollDate;
	  private Date SignDate;

	  public void setId(Integer Id) {
		this.Id = Id;
	  }
	  public Integer getId() {
		return Id;
	  }
	  public void setSpecialId(Integer SpecialId) {
		this.SpecialId = SpecialId;
	  }
	  public Integer getSpecialId() {
		return SpecialId;
	  }
	  public void setStudentId(Integer StudentId) {
		this.StudentId = StudentId;
	  }
	  public Integer getStudentId() {
		return StudentId;
	  }
	  public void setEnrollDate(Date EnrollDate) {
		this.EnrollDate = EnrollDate;
	  }
	  public Date getEnrollDate() {
		return EnrollDate;
	  }
	  public void setSignDate(Date SignDate) {
		this.SignDate = SignDate;
	  }
	  public Date getSignDate() {
		return SignDate;
	  }
      @Override
      public String toString() {
         return "StudentReport{" +
                "Id='" + Id + '\'' +
                "SpecialId='" + SpecialId + '\'' +
                "StudentId='" + StudentId + '\'' +
                "EnrollDate='" + EnrollDate + '\'' +
                "SignDate='" + SignDate + '\'' +
                '}';
      }
}
