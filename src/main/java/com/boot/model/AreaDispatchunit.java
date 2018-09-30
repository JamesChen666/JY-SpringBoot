package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="base_area_dispatchunit")
public class AreaDispatchunit implements Serializable{

	  private String AreaCode;
	  private Integer DispatchUnitId;
	  private Boolean IsNormal;

	  public void setAreaCode(String AreaCode) {
		this.AreaCode = AreaCode;
	  }
	  public String getAreaCode() {
		return AreaCode;
	  }
	  public void setDispatchUnitId(Integer DispatchUnitId) {
		this.DispatchUnitId = DispatchUnitId;
	  }
	  public Integer getDispatchUnitId() {
		return DispatchUnitId;
	  }
	  public void setIsNormal(Boolean IsNormal) {
		this.IsNormal = IsNormal;
	  }
	  public Boolean getIsNormal() {
		return IsNormal;
	  }
}
