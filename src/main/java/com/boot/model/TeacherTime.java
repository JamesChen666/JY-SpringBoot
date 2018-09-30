package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name="consult_teacher_time")
public class TeacherTime implements Serializable{

	  private Integer Id;
	  private String JobNumber;
	  private Date ClassDate;
	  private Time StartHour;
	  private Time EndHour;

	  public void setId(Integer Id) {
		this.Id = Id;
	  }
	  public Integer getId() {
		return Id;
	  }
	  public void setJobNumber(String JobNumber) {
		this.JobNumber = JobNumber;
	  }
	  public String getJobNumber() {
		return JobNumber;
	  }
	  public void setClassDate(Date ClassDate) {
		this.ClassDate = ClassDate;
	  }
	  public Date getClassDate() {
		return ClassDate;
	  }
	  public void setStartHour(Time StartHour) {
		this.StartHour = StartHour;
	  }
	  public Time getStartHour() {
		return StartHour;
	  }
	  public void setEndHour(Time EndHour) {
		this.EndHour = EndHour;
	  }
	  public Time getEndHour() {
		return EndHour;
	  }
      @Override
      public String toString() {
         return "TeacherTime{" +
                "Id='" + Id + '\'' +
                "JobNumber='" + JobNumber + '\'' +
                "ClassDate='" + ClassDate + '\'' +
                "StartHour='" + StartHour + '\'' +
                "EndHour='" + EndHour + '\'' +
                '}';
      }
}
