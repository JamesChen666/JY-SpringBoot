package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="school_class_instructor")
public class ClassInstructor implements Serializable{

	  private String ClassNo;
	  private String JobNumber;

	  public void setClassNo(String ClassNo) {
		this.ClassNo = ClassNo;
	  }
	  public String getClassNo() {
		return ClassNo;
	  }
	  public void setJobNumber(String JobNumber) {
		this.JobNumber = JobNumber;
	  }
	  public String getJobNumber() {
		return JobNumber;
	  }
}
