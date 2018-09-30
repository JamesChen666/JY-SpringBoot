package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name="Portal_Advert")
public class Advert implements Serializable{

	  private Integer Id;
	  private Integer PositionId;
	  private String Cover;
	  private String RedirectUrl;
	  private Boolean IsEnabled;
	  private Integer UserId;
	  private Date CreateDate;

	  public void setId(Integer Id) {
		this.Id = Id;
	  }
	  public Integer getId() {
		return Id;
	  }
	  public void setPositionId(Integer PositionId) {
		this.PositionId = PositionId;
	  }
	  public Integer getPositionId() {
		return PositionId;
	  }
	  public void setCover(String Cover) {
		this.Cover = Cover;
	  }
	  public String getCover() {
		return Cover;
	  }
	  public void setRedirectUrl(String RedirectUrl) {
		this.RedirectUrl = RedirectUrl;
	  }
	  public String getRedirectUrl() {
		return RedirectUrl;
	  }
	  public void setIsEnabled(Boolean IsEnabled) {
		this.IsEnabled = IsEnabled;
	  }
	  public Boolean getIsEnabled() {
		return IsEnabled;
	  }
	  public void setUserId(Integer UserId) {
		this.UserId = UserId;
	  }
	  public Integer getUserId() {
		return UserId;
	  }
	  public void setCreateDate(Date CreateDate) {
		this.CreateDate = CreateDate;
	  }
	  public Date getCreateDate() {
		return CreateDate;
	  }
      @Override
      public String toString() {
         return "Advert{" +
                "Id='" + Id + '\'' +
                "PositionId='" + PositionId + '\'' +
                "Cover='" + Cover + '\'' +
                "RedirectUrl='" + RedirectUrl + '\'' +
                "IsEnabled='" + IsEnabled + '\'' +
                "UserId='" + UserId + '\'' +
                "CreateDate='" + CreateDate + '\'' +
                '}';
      }
}
