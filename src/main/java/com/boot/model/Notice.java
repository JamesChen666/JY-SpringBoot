package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name="Portal_Notice")
public class Notice implements Serializable{

	  private Integer Id;
	  private String TypeCode;
	  private String Title;
	  private String SubTitle;
	  private String Cover;
	  private String VideoUrl;
	  private Boolean IsTop;
	  private String Content;
	  private Integer ClickCount;
	  private String Author;
	  private String Resource;
	  private Date CreateDate;
	  private Integer UserId;

	  public void setId(Integer Id) {
		this.Id = Id;
	  }
	  public Integer getId() {
		return Id;
	  }
	  public void setTypeCode(String TypeCode) {
		this.TypeCode = TypeCode;
	  }
	  public String getTypeCode() {
		return TypeCode;
	  }
	  public void setTitle(String Title) {
		this.Title = Title;
	  }
	  public String getTitle() {
		return Title;
	  }
	  public void setSubTitle(String SubTitle) {
		this.SubTitle = SubTitle;
	  }
	  public String getSubTitle() {
		return SubTitle;
	  }
	  public void setCover(String Cover) {
		this.Cover = Cover;
	  }
	  public String getCover() {
		return Cover;
	  }
	  public void setVideoUrl(String VideoUrl) {
		this.VideoUrl = VideoUrl;
	  }
	  public String getVideoUrl() {
		return VideoUrl;
	  }
	  public void setIsTop(Boolean IsTop) {
		this.IsTop = IsTop;
	  }
	  public Boolean getIsTop() {
		return IsTop;
	  }
	  public void setContent(String Content) {
		this.Content = Content;
	  }
	  public String getContent() {
		return Content;
	  }
	  public void setClickCount(Integer ClickCount) {
		this.ClickCount = ClickCount;
	  }
	  public Integer getClickCount() {
		return ClickCount;
	  }
	  public void setAuthor(String Author) {
		this.Author = Author;
	  }
	  public String getAuthor() {
		return Author;
	  }
	  public void setResource(String Resource) {
		this.Resource = Resource;
	  }
	  public String getResource() {
		return Resource;
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
      @Override
      public String toString() {
         return "Notice{" +
                "Id='" + Id + '\'' +
                "TypeCode='" + TypeCode + '\'' +
                "Title='" + Title + '\'' +
                "SubTitle='" + SubTitle + '\'' +
                "Cover='" + Cover + '\'' +
                "VideoUrl='" + VideoUrl + '\'' +
                "IsTop='" + IsTop + '\'' +
                "Content='" + Content + '\'' +
                "ClickCount='" + ClickCount + '\'' +
                "Author='" + Author + '\'' +
                "Resource='" + Resource + '\'' +
                "CreateDate='" + CreateDate + '\'' +
                "UserId='" + UserId + '\'' +
                '}';
      }
}
