package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="Portal_FriendLink")
public class Friendlink implements Serializable{

	  private Integer Id;
	  private String Title;
	  private String TypeCode;
	  private String RedirectUrl;
	  private Integer Sort;
	  private Boolean IsEnabled;

	  public void setId(Integer Id) {
		this.Id = Id;
	  }
	  public Integer getId() {
		return Id;
	  }
	  public void setTitle(String Title) {
		this.Title = Title;
	  }
	  public String getTitle() {
		return Title;
	  }
	  public void setTypeCode(String TypeCode) {
		this.TypeCode = TypeCode;
	  }
	  public String getTypeCode() {
		return TypeCode;
	  }
	  public void setRedirectUrl(String RedirectUrl) {
		this.RedirectUrl = RedirectUrl;
	  }
	  public String getRedirectUrl() {
		return RedirectUrl;
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
         return "FriendLink{" +
                "Id='" + Id + '\'' +
                "Title='" + Title + '\'' +
                "TypeCode='" + TypeCode + '\'' +
                "RedirectUrl='" + RedirectUrl + '\'' +
                "Sort='" + Sort + '\'' +
                "IsEnabled='" + IsEnabled + '\'' +
                '}';
      }
}
