package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="Election_Corp_Position")
public class CorpPosition implements Serializable{

	  private Integer Id;
	  private Integer ElectionCorpId;
	  private Integer PositionId;

	  public void setId(Integer Id) {
		this.Id = Id;
	  }
	  public Integer getId() {
		return Id;
	  }
	  public void setElectionCorpId(Integer ElectionCorpId) {
		this.ElectionCorpId = ElectionCorpId;
	  }
	  public Integer getElectionCorpId() {
		return ElectionCorpId;
	  }
	  public void setPositionId(Integer PositionId) {
		this.PositionId = PositionId;
	  }
	  public Integer getPositionId() {
		return PositionId;
	  }
      @Override
      public String toString() {
         return "CorpPosition{" +
                "Id='" + Id + '\'' +
                "ElectionCorpId='" + ElectionCorpId + '\'' +
                "PositionId='" + PositionId + '\'' +
                '}';
      }
}
