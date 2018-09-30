package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="Specia_Position")
public class Position implements Serializable{

	  private Integer id;
	  private Integer SpecialId;
	  private Integer PositionId;

	  public void setId(Integer id) {
		this.id = id;
	}
	  public Integer getId() {
		return id;
	}
	  public void setSpecialId(Integer SpecialId) {
		this.SpecialId = SpecialId;
	  }
	  public Integer getSpecialId() {
		return SpecialId;
	  }
	  public void setPositionId(Integer PositionId) {
		this.PositionId = PositionId;
	  }
	  public Integer getPositionId() {
		return PositionId;
	  }
      @Override
      public String toString() {
         return "Position{" +
				 "id='" + id + '\'' +
                "SpecialId='" + SpecialId + '\'' +
                "PositionId='" + PositionId + '\'' +
                '}';
      }
}
