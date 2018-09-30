package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="portal_friendlinktype")
public class Friendlinktype implements Serializable{

	  private Integer Id;
	  private String TypeName;
	  private String TypeCode;
	  private Integer Sort;
	  private Boolean IsEnabled;

	  public void setId(Integer Id) {
		this.Id = Id;
	  }
	  public Integer getId() {
		return Id;
	  }
	  public void setTypeName(String TypeName) {
		this.TypeName = TypeName;
	  }
	  public String getTypeName() {
		return TypeName;
	  }
	  public void setTypeCode(String TypeCode) {
		this.TypeCode = TypeCode;
	  }
	  public String getTypeCode() {
		return TypeCode;
	  }
	  public void setSort(Integer Sort) {
		this.Sort = Sort;
	  }
	  public Integer getSort() {
		return Sort;
	  }
	  public void setIsEnabled(Boolean IsEnabled) {
		this.IsEnabled = IsEnabled;
	  }
	  public Boolean getIsEnabled() {
		return IsEnabled;
	  }
      @Override
      public String toString() {
         return "Friendlinktype{" +
                "Id='" + Id + '\'' +
                "TypeName='" + TypeName + '\'' +
                "TypeCode='" + TypeCode + '\'' +
                "Sort='" + Sort + '\'' +
                "IsEnabled='" + IsEnabled + '\'' +
                '}';
      }
}
