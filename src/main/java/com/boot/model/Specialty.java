package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="school_specialty")
public class Specialty implements Serializable{

	  private Integer Id;
	  private String SpecialtyCode;
	  private String SpecialtyName;
	  private String FacultyCode;
	  private String GbCode;
	  private String SpecialtyDirection;
	  private String LevelCode;
	  private Integer SchoolLength;
	  private String Remark;

	  public void setId(Integer Id) {
		this.Id = Id;
	  }
	  public Integer getId() {
		return Id;
	  }
	  public void setSpecialtyCode(String SpecialtyCode) {
		this.SpecialtyCode = SpecialtyCode;
	  }
	  public String getSpecialtyCode() {
		return SpecialtyCode;
	  }
	  public void setSpecialtyName(String SpecialtyName) {
		this.SpecialtyName = SpecialtyName;
	  }
	  public String getSpecialtyName() {
		return SpecialtyName;
	  }
	  public void setFacultyCode(String FacultyCode) {
		this.FacultyCode = FacultyCode;
	  }
	  public String getFacultyCode() {
		return FacultyCode;
	  }
	  public void setGbCode(String GbCode) {
		this.GbCode = GbCode;
	  }
	  public String getGbCode() {
		return GbCode;
	  }
	  public void setSpecialtyDirection(String SpecialtyDirection) {
		this.SpecialtyDirection = SpecialtyDirection;
	  }
	  public String getSpecialtyDirection() {
		return SpecialtyDirection;
	  }
	  public void setLevelCode(String LevelCode) {
		this.LevelCode = LevelCode;
	  }
	  public String getLevelCode() {
		return LevelCode;
	  }
	  public void setSchoolLength(Integer SchoolLength) {
		this.SchoolLength = SchoolLength;
	  }
	  public Integer getSchoolLength() {
		return SchoolLength;
	  }
	  public void setRemark(String Remark) {
		this.Remark = Remark;
	  }
	  public String getRemark() {
		return Remark;
	  }
      @Override
      public String toString() {
         return "Specialty{" +
                "Id='" + Id + '\'' +
                "SpecialtyCode='" + SpecialtyCode + '\'' +
                "SpecialtyName='" + SpecialtyName + '\'' +
                "FacultyCode='" + FacultyCode + '\'' +
                "GbCode='" + GbCode + '\'' +
                "SpecialtyDirection='" + SpecialtyDirection + '\'' +
                "LevelCode='" + LevelCode + '\'' +
                "SchoolLength='" + SchoolLength + '\'' +
                "Remark='" + Remark + '\'' +
                '}';
      }
}
