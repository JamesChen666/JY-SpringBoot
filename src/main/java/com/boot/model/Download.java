package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name="Portal_Download")
public class Download implements Serializable{

	  private Integer Id;
	  private String Title;
	  private String Url;
	  private Integer DownCount;
	  private Date CreateDate;
	  private Integer UserId;
	  private String TypeCode;

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
	  public void setUrl(String Url) {
		this.Url = Url;
	  }
	  public String getUrl() {
		return Url;
	  }
	  public void setDownCount(Integer DownCount) {
		this.DownCount = DownCount;
	  }
	  public Integer getDownCount() {
		return DownCount;
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
	  public void setTypeCode(String TypeCode) {
		this.TypeCode = TypeCode;
	  }
	  public String getTypeCode() {
		return TypeCode;
	  }
      @Override
      public String toString() {
         return "Download{" +
                "Id='" + Id + '\'' +
                "Title='" + Title + '\'' +
                "Url='" + Url + '\'' +
                "DownCount='" + DownCount + '\'' +
                "CreateDate='" + CreateDate + '\'' +
                "UserId='" + UserId + '\'' +
                "TypeCode='" + TypeCode + '\'' +
                '}';
      }
}
