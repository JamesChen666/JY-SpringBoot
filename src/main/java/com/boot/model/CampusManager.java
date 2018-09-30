package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="school_campus_manager")
public class CampusManager implements Serializable{

	  private String CampusCode;
	  private String JobNumber;

	  public void setCampusCode(String CampusCode) {
		this.CampusCode = CampusCode;
	  }
	  public String getCampusCode() {
		return CampusCode;
	  }
	  public void setJobNumber(String JobNumber) {
		this.JobNumber = JobNumber;
	  }
	  public String getJobNumber() {
		return JobNumber;
	  }
}
