package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="student_student_tag")
public class StudentTag implements Serializable{

	  private Integer StudentId;
	  private Integer TagId;

	  public void setStudentId(Integer StudentId) {
		this.StudentId = StudentId;
	  }
	  public Integer getStudentId() {
		return StudentId;
	  }
	  public void setTagId(Integer TagId) {
		this.TagId = TagId;
	  }
	  public Integer getTagId() {
		return TagId;
	  }
      @Override
      public String toString() {
         return "StudentTag{" +
                "StudentId='" + StudentId + '\'' +
                "TagId='" + TagId + '\'' +
                '}';
      }
}
