package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="sys_role_menu")
public class RoleMenu implements Serializable{

	  private Integer RoleId;
	  private Integer MenuId;

	  public void setRoleId(Integer RoleId) {
		this.RoleId = RoleId;
	  }
	  public Integer getRoleId() {
		return RoleId;
	  }
	  public void setMenuId(Integer MenuId) {
		this.MenuId = MenuId;
	  }
	  public Integer getMenuId() {
		return MenuId;
	  }
}
