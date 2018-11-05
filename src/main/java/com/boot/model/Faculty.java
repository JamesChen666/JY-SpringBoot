package com.boot.model;

import com.boot.util.excel.annotation.ExcelField;
import com.boot.util.excel.converter.UserConverter.CampusConvert;
import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="school_faculty")
public class Faculty implements Serializable{

	  private Integer Id;
	  @ExcelField(title = "院系代码",order = 2)
	  private String FacultyCode;
	  @ExcelField(title = "院系名称",order =1 )
	  private String FacultyName;
	  @ExcelField(title = "所属校区",order = 3,readConverter = CampusConvert.class,writeConverter = CampusConvert.class)
	  private String CampusCode;
	  @ExcelField(title = "类型",order = 4)
	  private Integer TypeID;
	  @ExcelField(title = "标准",order =5 )
	  private String Remark;
	  @ExcelField(title = "排序",order = 6)
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
