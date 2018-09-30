package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="sys_role_button")
public class RoleButton implements Serializable{

	  private Integer RoleId;
	  private Integer ButtonId;

	  public void setRoleId(Integer RoleId) {
		this.RoleId = RoleId;
	  }
	  public Integer getRoleId() {
		return RoleId;
	  }
	  public void setButtonId(Integer ButtonId) {
		this.ButtonId = ButtonId;
	  }
	  public Integer getButtonId() {
		return ButtonId;
	  }
}
