package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="Portal_NoticeType")
public class Noticetype implements Serializable{

	  private Integer Id;
	  private String TypeCode;
	  private String TypeName;
	  private Integer Sort;
	  private Boolean IsEnabled;

	  public void setId(Integer Id) {
		this.Id = Id;
	  }
	  public Integer getId() {
		return Id;
	  }
	  public void setTypeCode(String TypeCode) {
		this.TypeCode = TypeCode;
	  }
	  public String getTypeCode() {
		return TypeCode;
	  }
	  public void setTypeName(String TypeName) {
		this.TypeName = TypeName;
	  }
	  public String getTypeName() {
		return TypeName;
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
         return "NoticeType{" +
                "Id='" + Id + '\'' +
                "TypeCode='" + TypeCode + '\'' +
                "TypeName='" + TypeName + '\'' +
                "Sort='" + Sort + '\'' +
                "IsEnabled='" + IsEnabled + '\'' +
                '}';
      }
}
