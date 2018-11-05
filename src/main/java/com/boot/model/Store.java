package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name="Student_Store")
public class Store implements Serializable{

	  private Integer Id;
	  private Integer StudentId;
	  private Integer CorpId;
	  private Integer PositionId;
	  private Date CreateDate;

	  public void setId(Integer Id) {
		this.Id = Id;
	  }
	  public Integer getId() {
		return Id;
	  }
	  public void setStudentId(Integer StudentId) {
		this.StudentId = StudentId;
	  }
	  public Integer getStudentId() {
		return StudentId;
	  }
	  public void setCorpId(Integer CorpId) {
		this.CorpId = CorpId;
	  }
	  public Integer getCorpId() {
		return CorpId;
	  }
	  public void setPositionId(Integer PositionId) {
		this.PositionId = PositionId;
	  }
	  public Integer getPositionId() {
		return PositionId;
	  }
	  public void setCreateDate(Date CreateDate) {
		this.CreateDate = CreateDate;
	  }
	  public Date getCreateDate() {
		return CreateDate;
	  }
      @Override
      public String toString() {
         return "Store{" +
                "Id='" + Id + '\'' +
                "StudentId='" + StudentId + '\'' +
                "CorpId='" + CorpId + '\'' +
                "PositionId='" + PositionId + '\'' +
                "CreateDate='" + CreateDate + '\'' +
                '}';
      }
}
