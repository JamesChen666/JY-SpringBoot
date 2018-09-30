package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="school_class")
public class Class implements Serializable{

	  private Integer Id;
	  private String SpecialtyCode;
	  private String ClassNo;
	  private String ClassName;
	  private String Grade;
	  private String GraduationYear;
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
	  public void setClassNo(String ClassNo) {
		this.ClassNo = ClassNo;
	  }
	  public String getClassNo() {
		return ClassNo;
	  }
	  public void setClassName(String ClassName) {
		this.ClassName = ClassName;
	  }
	  public String getClassName() {
		return ClassName;
	  }
	  public void setGrade(String Grade) {
		this.Grade = Grade;
	  }
	  public String getGrade() {
		return Grade;
	  }
	  public void setGraduationYear(String GraduationYear) {
		this.GraduationYear = GraduationYear;
	  }
	  public String getGraduationYear() {
		return GraduationYear;
	  }
	  public void setRemark(String Remark) {
		this.Remark = Remark;
	  }
	  public String getRemark() {
		return Remark;
	  }
      @Override
      public String toString() {
         return "Class{" +
                "Id='" + Id + '\'' +
                "SpecialtyCode='" + SpecialtyCode + '\'' +
                "ClassNo='" + ClassNo + '\'' +
                "ClassName='" + ClassName + '\'' +
                "Grade='" + Grade + '\'' +
                "GraduationYear='" + GraduationYear + '\'' +
                "Remark='" + Remark + '\'' +
                '}';
      }
}
