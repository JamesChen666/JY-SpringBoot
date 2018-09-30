package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name="student_emptyregistercard")
public class Emptyregistercard implements Serializable{

	  private Integer Id;
	  private Integer TypeId;
	  private String RegisterCardNumber;
	  private String SerialNumber;
	  private Integer StudentId;
	  private Date CreateDate;
	  private Integer UserId;
	  private String Remark;

	  public void setId(Integer Id) {
		this.Id = Id;
	  }
	  public Integer getId() {
		return Id;
	  }
	  public void setTypeId(Integer TypeId) {
		this.TypeId = TypeId;
	  }
	  public Integer getTypeId() {
		return TypeId;
	  }
	  public void setRegisterCardNumber(String RegisterCardNumber) {
		this.RegisterCardNumber = RegisterCardNumber;
	  }
	  public String getRegisterCardNumber() {
		return RegisterCardNumber;
	  }
	  public void setSerialNumber(String SerialNumber) {
		this.SerialNumber = SerialNumber;
	  }
	  public String getSerialNumber() {
		return SerialNumber;
	  }
	  public void setStudentId(Integer StudentId) {
		this.StudentId = StudentId;
	  }
	  public Integer getStudentId() {
		return StudentId;
	  }
	  public void setCreateDate(Date CreateDate) {
		this.CreateDate = CreateDate;
	  }
	  public Date getCreateDate() {
		return CreateDate;
	  }
	  public void setUserId(Integer UserId) {
		this.UserId = UserId;
	  }
	  public Integer getUserId() {
		return UserId;
	  }
	  public void setRemark(String Remark) {
		this.Remark = Remark;
	  }
	  public String getRemark() {
		return Remark;
	  }
      @Override
      public String toString() {
         return "Emptyregistercard{" +
                "Id='" + Id + '\'' +
                "TypeId='" + TypeId + '\'' +
                "RegisterCardNumber='" + RegisterCardNumber + '\'' +
                "SerialNumber='" + SerialNumber + '\'' +
                "StudentId='" + StudentId + '\'' +
                "CreateDate='" + CreateDate + '\'' +
                "UserId='" + UserId + '\'' +
                "Remark='" + Remark + '\'' +
                '}';
      }
}
