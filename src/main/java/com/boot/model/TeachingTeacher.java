package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="consult_teaching_teacher")
public class TeachingTeacher implements Serializable{

		private Integer Id;
	  private String JobNumber;
	  private String ConsultDir;
	  private String Description;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public void setJobNumber(String JobNumber) {
		this.JobNumber = JobNumber;
	  }
	  public String getJobNumber() {
		return JobNumber;
	  }
	  public void setConsultDir(String ConsultDir) {
		this.ConsultDir = ConsultDir;
	  }
	  public String getConsultDir() {
		return ConsultDir;
	  }
	  public void setDescription(String Description) {
		this.Description = Description;
	  }
	  public String getDescription() {
		return Description;
	  }
      @Override
      public String toString() {
         return "TeachingTeacher{" +
                "Id='" + Id + '\'' +
                "JobNumber='" + JobNumber + '\'' +
                "ConsultDir='" + ConsultDir + '\'' +
                "Description='" + Description + '\'' +
                '}';
      }
}
