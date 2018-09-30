package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="sys_loginaccount_role")
public class LoginaccountRole implements Serializable{

	  private Integer UserId;
	  private Integer RoleId;

	  public void setUserId(Integer UserId) {
		this.UserId = UserId;
	  }
	  public Integer getUserId() {
		return UserId;
	  }
	  public void setRoleId(Integer RoleId) {
		this.RoleId = RoleId;
	  }
	  public Integer getRoleId() {
		return RoleId;
	  }
}
