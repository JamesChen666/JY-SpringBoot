package com.boot.model;

import com.boot.util.excel.annotation.ExcelField;
import com.boot.util.excel.converter.UserConverter.DateConvert;
import com.boot.util.excel.converter.UserConverter.StudentConvert;
import com.boot.util.excel.converter.UserConverter.WhetherConvert;
import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name = "student_feedback")
public class Feedback implements Serializable {

    private Integer Id;
    @ExcelField(title = "学号", order = 1, writeConverter = StudentConvert.class, readConverter = StudentConvert.class)
    private Integer StudentId;
    @ExcelField(title = "标题", order = 2)
    private String Title;
    @ExcelField(title = "内容", order = 3)
    private String Content;
    @ExcelField(title = "联系电话", order = 4)
    private String ContactPhone;
    @ExcelField(title = "创建时间", order = 5, readConverter = DateConvert.class, writeConverter = DateConvert.class)
    private Date CreateDate;
    @ExcelField(title = "是否回复", order = 6, readConverter = WhetherConvert.class, writeConverter = WhetherConvert.class)
    private Boolean IsReply;
    @ExcelField(title = "回复内容", order = 7)
    private String ReplyContent;
    @ExcelField(title = "回复时间", order = 8, readConverter = DateConvert.class, writeConverter = DateConvert.class)
    private Date ReplyDate;

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public Integer getId() {
        return Id;
    }

    public void setStudentId(Integer StudentId) {
        this.StudentId = StudentId;
    }

    public Integer getStudentId() {
        return StudentId;
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
        return "Feedback{" +
                "Id='" + Id + '\'' +
                "StudentId='" + StudentId + '\'' +
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
