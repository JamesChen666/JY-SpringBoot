package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="Portal_Banner")
public class Banner implements Serializable{

	  private Integer Id;
	  private String ImgUrl;
	  private String RedirectUrl;
	  private Integer Sort;
	  private Boolean IsEnabled;

	  public void setId(Integer Id) {
		this.Id = Id;
	  }
	  public Integer getId() {
		return Id;
	  }
	  public void setImgUrl(String ImgUrl) {
		this.ImgUrl = ImgUrl;
	  }
	  public String getImgUrl() {
		return ImgUrl;
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
         return "Banner{" +
                "Id='" + Id + '\'' +
                "ImgUrl='" + ImgUrl + '\'' +
                "RedirectUrl='" + RedirectUrl + '\'' +
                "Sort='" + Sort + '\'' +
                "IsEnabled='" + IsEnabled + '\'' +
                '}';
      }
}
