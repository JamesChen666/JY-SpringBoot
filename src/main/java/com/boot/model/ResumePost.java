package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name="Student_Resume_Post")
public class ResumePost implements Serializable{

	  private Integer StudentId;
	  private Integer PostionId;
	  private Date CreateDate;

	  public void setStudentId(Integer StudentId) {
		this.StudentId = StudentId;
	  }
	  public Integer getStudentId() {
		return StudentId;
	  }
	  public void setPostionId(Integer PostionId) {
		this.PostionId = PostionId;
	  }
	  public Integer getPostionId() {
		return PostionId;
	  }
	  public void setCreateDate(Date CreateDate) {
		this.CreateDate = CreateDate;
	  }
	  public Date getCreateDate() {
		return CreateDate;
	  }
      @Override
      public String toString() {
         return "ResumePost{" +
                "StudentId='" + StudentId + '\'' +
                "PostionId='" + PostionId + '\'' +
                "CreateDate='" + CreateDate + '\'' +
                '}';
      }
}
