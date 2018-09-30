package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="Portal_AdvertPosition")
public class AdvertPosition implements Serializable{

	  private Integer Id;
	  private String Title;
	  private String Description;
	  private String DefaultCover;

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
	  public void setDescription(String Description) {
		this.Description = Description;
	  }
	  public String getDescription() {
		return Description;
	  }
	  public void setDefaultCover(String DefaultCover) {
		this.DefaultCover = DefaultCover;
	  }
	  public String getDefaultCover() {
		return DefaultCover;
	  }
      @Override
      public String toString() {
         return "AdvertPosition{" +
                "Id='" + Id + '\'' +
                "Title='" + Title + '\'' +
                "Description='" + Description + '\'' +
                "DefaultCover='" + DefaultCover + '\'' +
                '}';
      }
}
