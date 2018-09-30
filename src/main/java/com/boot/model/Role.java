package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="sys_role")
public class Role implements Serializable{

	  private Integer Id;
	  private String RoleName;
	  private Integer Sort;

	  public void setId(Integer Id) {
		this.Id = Id;
	  }
	  public Integer getId() {
		return Id;
	  }
	  public void setRoleName(String RoleName) {
		this.RoleName = RoleName;
	  }
	  public String getRoleName() {
		return RoleName;
	  }
	  public void setSort(Integer Sort) {
		this.Sort = Sort;
	  }
	  public Integer getSort() {
		return Sort;
	  }

	@Override
	public String toString() {
		return "Role{" +
				"Id='" + Id + '\'' +
				"RoleName='" + RoleName + '\'' +
				"Sort='" + Sort + '\'' +
				'}';
	}
}
