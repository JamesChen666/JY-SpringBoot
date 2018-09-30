package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="Portal_Nav")
public class Nav implements Serializable{

	  private Integer Id;
	  private String Title;
	  private Integer TypeId;
	  private String Url;
	  private String Content;
	  private Integer RedirectTypeId;
	  private Integer ParentId;
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
	  public void setTypeId(Integer TypeId) {
		this.TypeId = TypeId;
	  }
	  public Integer getTypeId() {
		return TypeId;
	  }
	  public void setUrl(String Url) {
		this.Url = Url;
	  }
	  public String getUrl() {
		return Url;
	  }
	  public void setContent(String Content) {
		this.Content = Content;
	  }
	  public String getContent() {
		return Content;
	  }
	  public void setRedirectTypeId(Integer RedirectTypeId) {
		this.RedirectTypeId = RedirectTypeId;
	  }
	  public Integer getRedirectTypeId() {
		return RedirectTypeId;
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
	  public void setIsEnabled(Boolean IsEnabled) {
		this.IsEnabled = IsEnabled;
	  }
	  public Boolean getIsEnabled() {
		return IsEnabled;
	  }
      @Override
      public String toString() {
         return "Nav{" +
                "Id='" + Id + '\'' +
                "Title='" + Title + '\'' +
                "TypeId='" + TypeId + '\'' +
                "Url='" + Url + '\'' +
                "Content='" + Content + '\'' +
                "RedirectTypeId='" + RedirectTypeId + '\'' +
                "ParentId='" + ParentId + '\'' +
                "Sort='" + Sort + '\'' +
                "IsEnabled='" + IsEnabled + '\'' +
                '}';
      }
}
