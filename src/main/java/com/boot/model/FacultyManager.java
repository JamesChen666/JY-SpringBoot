package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="school_faculty_manager")
public class FacultyManager implements Serializable{

	  private String FacultyCode;
	  private String JobNumber;

	  public void setFacultyCode(String FacultyCode) {
		this.FacultyCode = FacultyCode;
	  }
	  public String getFacultyCode() {
		return FacultyCode;
	  }
	  public void setJobNumber(String JobNumber) {
		this.JobNumber = JobNumber;
	  }
	  public String getJobNumber() {
		return JobNumber;
	  }
}
