package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="Election_Corp_Booth")
public class CorpBooth implements Serializable{

	  private Integer Id;
	  private Integer ElectionCorpId;
	  private Integer BoothId;

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
	  public void setBoothId(Integer BoothId) {
		this.BoothId = BoothId;
	  }
	  public Integer getBoothId() {
		return BoothId;
	  }
      @Override
      public String toString() {
         return "CorpBooth{" +
                "Id='" + Id + '\'' +
                "ElectionCorpId='" + ElectionCorpId + '\'' +
                "BoothId='" + BoothId + '\'' +
                '}';
      }
}
