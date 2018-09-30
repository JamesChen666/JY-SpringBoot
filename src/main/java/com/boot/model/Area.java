package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="base_area")
public class Area implements Serializable{

	  private Integer Id;
	  private String AreaCode;
	  private String AreaName;
	  private Integer ParentId;
	  private Integer Sort;

	  public void setId(Integer Id) {
		this.Id = Id;
	  }
	  public Integer getId() {
		return Id;
	  }
	  public void setAreaCode(String AreaCode) {
		this.AreaCode = AreaCode;
	  }
	  public String getAreaCode() {
		return AreaCode;
	  }
	  public void setAreaName(String AreaName) {
		this.AreaName = AreaName;
	  }
	  public String getAreaName() {
		return AreaName;
	  }
	  public void setParentId(Integer ParentId) {
		this.ParentId = ParentId;
	  }
	  public Integer getParentId() {
		return ParentId;
	  }
	  public void setSort(Integer Sort) {
		this.Sort = Sort;
	  }
	  public Integer getSort() {
		return Sort;
	  }
      @Override
      public String toString() {
         return "Area{" +
                "Id='" + Id + '\'' +
                "AreaCode='" + AreaCode + '\'' +
                "AreaName='" + AreaName + '\'' +
                "ParentId='" + ParentId + '\'' +
                "Sort='" + Sort + '\'' +
                '}';
      }
}
