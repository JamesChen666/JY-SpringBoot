package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="school_campus")
public class Campus implements Serializable{

	  private Integer Id;
	  private String CampusName;
	  private String CampusCode;
	  private String Address;
	  private Integer Sort;

	  public void setId(Integer Id) {
		this.Id = Id;
	  }
	  public Integer getId() {
		return Id;
	  }
	  public void setCampusName(String CampusName) {
		this.CampusName = CampusName;
	  }
	  public String getCampusName() {
		return CampusName;
	  }
	  public void setCampusCode(String CampusCode) {
		this.CampusCode = CampusCode;
	  }
	  public String getCampusCode() {
		return CampusCode;
	  }
	  public void setAddress(String Address) {
		this.Address = Address;
	  }
	  public String getAddress() {
		return Address;
	  }
	  public void setSort(Integer Sort) {
		this.Sort = Sort;
	  }
	  public Integer getSort() {
		return Sort;
	  }
}
