package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name="Corp_Leave")
public class Leave implements Serializable{

	  private Integer Id;
	  private Integer CorpId;
	  private String Title;
	  private String Content;
	  private String ContactPhone;
	  private Date CreateDate;
	  private Boolean IsReply;
	  private String ReplyContent;
	  private Date ReplyDate;

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
	  public void setTitle(String Title) {
		this.Title = Title;
	  }
	  public String getTitle() {
		return Title;
	  }
	  public void setContent(String Content) {
		this.Content = Content;
	  }
	  public String getContent() {
		return Content;
	  }
	  public void setContactPhone(String ContactPhone) {
		this.ContactPhone = ContactPhone;
	  }
	  public String getContactPhone() {
		return ContactPhone;
	  }
	  public void setCreateDate(Date CreateDate) {
		this.CreateDate = CreateDate;
	  }
	  public Date getCreateDate() {
		return CreateDate;
	  }
	  public void setIsReply(Boolean IsReply) {
		this.IsReply = IsReply;
	  }
	  public Boolean getIsReply() {
		return IsReply;
	  }
	  public void setReplyContent(String ReplyContent) {
		this.ReplyContent = ReplyContent;
	  }
	  public String getReplyContent() {
		return ReplyContent;
	  }
	  public void setReplyDate(Date ReplyDate) {
		this.ReplyDate = ReplyDate;
	  }
	  public Date getReplyDate() {
		return ReplyDate;
	  }
      @Override
      public String toString() {
         return "Leave{" +
                "Id='" + Id + '\'' +
                "CorpId='" + CorpId + '\'' +
                "Title='" + Title + '\'' +
                "Content='" + Content + '\'' +
                "ContactPhone='" + ContactPhone + '\'' +
                "CreateDate='" + CreateDate + '\'' +
                "IsReply='" + IsReply + '\'' +
                "ReplyContent='" + ReplyContent + '\'' +
                "ReplyDate='" + ReplyDate + '\'' +
                '}';
      }
}
