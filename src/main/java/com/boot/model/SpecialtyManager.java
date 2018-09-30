package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="school_specialty_manager")
public class SpecialtyManager implements Serializable{

	  private String SpecialtyCode;
	  private String JobNumber;

	  public void setSpecialtyCode(String SpecialtyCode) {
		this.SpecialtyCode = SpecialtyCode;
	  }
	  public String getSpecialtyCode() {
		return SpecialtyCode;
	  }
	  public void setJobNumber(String JobNumber) {
		this.JobNumber = JobNumber;
	  }
	  public String getJobNumber() {
		return JobNumber;
	  }
}
