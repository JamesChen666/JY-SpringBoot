package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name="Corp_BlackList")
public class BlackList implements Serializable{

	  private Integer Id;
	  private Integer CorpId;
	  private Date CreateDate;
	  private Integer UserId;
	  private String Reason;

	  public void setId(Integer Id) {
		this.Id = Id;
	  }
	  public Integer getId() {
		return Id;
	  }
	  public void setCorpId(Integer CorpId) {
		this.CorpId = CorpId;
	  }
	  public Integer getCorpId() {
		return CorpId;
	  }
	  public void setCreateDate(Date CreateDate) {
		this.CreateDate = CreateDate;
	  }
	  public Date getCreateDate() {
		return CreateDate;
	  }
	  public void setUserId(Integer UserId) {
		this.UserId = UserId;
	  }
	  public Integer getUserId() {
		return UserId;
	  }
	  public void setReason(String Reason) {
		this.Reason = Reason;
	  }
	  public String getReason() {
		return Reason;
	  }
      @Override
      public String toString() {
         return "BlackList{" +
                "Id='" + Id + '\'' +
                "CorpId='" + CorpId + '\'' +
                "CreateDate='" + CreateDate + '\'' +
                "UserId='" + UserId + '\'' +
                "Reason='" + Reason + '\'' +
                '}';
      }
}
