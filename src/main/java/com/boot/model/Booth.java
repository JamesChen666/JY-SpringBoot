package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="Election_Booth")
public class Booth implements Serializable{

	  private Integer Id;
	  private Integer PlaceId;
	  private String BoothNumber;
	  private String Remark;

	  public void setId(Integer Id) {
		this.Id = Id;
	  }
	  public Integer getId() {
		return Id;
	  }
	  public void setPlaceId(Integer PlaceId) {
		this.PlaceId = PlaceId;
	  }
	  public Integer getPlaceId() {
		return PlaceId;
	  }
	  public void setBoothNumber(String BoothNumber) {
		this.BoothNumber = BoothNumber;
	  }
	  public String getBoothNumber() {
		return BoothNumber;
	  }
	  public void setRemark(String Remark) {
		this.Remark = Remark;
	  }
	  public String getRemark() {
		return Remark;
	  }
      @Override
      public String toString() {
         return "Booth{" +
                "Id='" + Id + '\'' +
                "PlaceId='" + PlaceId + '\'' +
                "BoothNumber='" + BoothNumber + '\'' +
                "Remark='" + Remark + '\'' +
                '}';
      }
}
