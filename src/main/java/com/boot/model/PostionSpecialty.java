package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="corp_postionSpecialty")
public class PostionSpecialty implements Serializable{

	  private Integer PositionId;
	  private String SpecialtyCode;

	  public void setPositionId(Integer PositionId) {
		this.PositionId = PositionId;
	  }
	  public Integer getPositionId() {
		return PositionId;
	  }
	  public void setSpecialtyCode(String SpecialtyCode) {
		this.SpecialtyCode = SpecialtyCode;
	  }
	  public String getSpecialtyCode() {
		return SpecialtyCode;
	  }
      @Override
      public String toString() {
         return "PostionSpecialty{" +
                "PositionId='" + PositionId + '\'' +
                "SpecialtyCode='" + SpecialtyCode + '\'' +
                '}';
      }
}
