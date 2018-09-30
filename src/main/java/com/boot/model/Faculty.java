package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="school_faculty")
public class Faculty implements Serializable{

	  private Integer Id;
	  private String FacultyCode;
	  private String FacultyName;
	  private String CampusCode;
	  private Integer TypeID;
	  private String Remark;
	  private Integer Sort;

	  public void setId(Integer Id) {
		this.Id = Id;
	  }
	  public Integer getId() {
		return Id;
	  }
	  public void setFacultyCode(String FacultyCode) {
		this.FacultyCode = FacultyCode;
	  }
	  public String getFacultyCode() {
		return FacultyCode;
	  }
	  public void setFacultyName(String FacultyName) {
		this.FacultyName = FacultyName;
	  }
	  public String getFacultyName() {
		return FacultyName;
	  }
	  public void setCampusCode(String CampusCode) {
		this.CampusCode = CampusCode;
	  }
	  public String getCampusCode() {
		return CampusCode;
	  }
	  public void setTypeID(Integer TypeID) {
		this.TypeID = TypeID;
	  }
	  public Integer getTypeID() {
		return TypeID;
	  }
	  public void setRemark(String Remark) {
		this.Remark = Remark;
	  }
	  public String getRemark() {
		return Remark;
	  }
	  public void setSort(Integer Sort) {
		this.Sort = Sort;
	  }
	  public Integer getSort() {
		return Sort;
	  }
}
