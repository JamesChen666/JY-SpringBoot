package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="Election_Election_Field")
public class ElectionField implements Serializable{

	  private Integer Id;
	  private String Title;
	  private String Address;
	  private String LatitudeAndLongitude;
	  private String Files;

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
	  public void setAddress(String Address) {
		this.Address = Address;
	  }
	  public String getAddress() {
		return Address;
	  }
	  public void setLatitudeAndLongitude(String LatitudeAndLongitude) {
		this.LatitudeAndLongitude = LatitudeAndLongitude;
	  }
	  public String getLatitudeAndLongitude() {
		return LatitudeAndLongitude;
	  }
	  public void setFiles(String Files) {
		this.Files = Files;
	  }
	  public String getFiles() {
		return Files;
	  }
      @Override
      public String toString() {
         return "ElectionField{" +
                "Id='" + Id + '\'' +
                "Title='" + Title + '\'' +
                "Address='" + Address + '\'' +
                "LatitudeAndLongitude='" + LatitudeAndLongitude + '\'' +
                "Files='" + Files + '\'' +
                '}';
      }
}
