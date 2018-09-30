package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="Election_RunPlace")
public class RunPlace implements Serializable{

	  private Integer Id;
	  private Integer ElectionId;
	  private Integer PlaceId;

	  public void setId(Integer Id) {
		this.Id = Id;
	  }
	  public Integer getId() {
		return Id;
	  }
	  public void setElectionId(Integer ElectionId) {
		this.ElectionId = ElectionId;
	  }
	  public Integer getElectionId() {
		return ElectionId;
	  }
	  public void setPlaceId(Integer PlaceId) {
		this.PlaceId = PlaceId;
	  }
	  public Integer getPlaceId() {
		return PlaceId;
	  }
      @Override
      public String toString() {
         return "RunPlace{" +
                "Id='" + Id + '\'' +
                "ElectionId='" + ElectionId + '\'' +
                "PlaceId='" + PlaceId + '\'' +
                '}';
      }
}
