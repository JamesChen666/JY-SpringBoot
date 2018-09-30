package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="Election_Industry")
public class Industry implements Serializable{

	  private Integer Id;
	  private Integer ElectionId;
	  private String IndustryCode;

	  public void setId(Integer Id) {
		this.Id = Id;
	  }
	  public Integer getId() {
		return Id;
	  }
	  public void setElectionId(Integer ElectionId) {
		this.ElectionId = ElectionId;
	  }
	  public Integer getElectionId() {
		return ElectionId;
	  }
	  public void setIndustryCode(String IndustryCode) {
		this.IndustryCode = IndustryCode;
	  }
	  public String getIndustryCode() {
		return IndustryCode;
	  }
      @Override
      public String toString() {
         return "Industry{" +
                "Id='" + Id + '\'' +
                "ElectionId='" + ElectionId + '\'' +
                "IndustryCode='" + IndustryCode + '\'' +
                '}';
      }
}
