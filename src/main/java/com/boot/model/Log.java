package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name = "sys_log")
public class Log implements Serializable {

    private Integer Id;
    private Integer UserId;
    private String Module;
    private String Description;
    private Date CreateDate;

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public Integer getId() {
        return Id;
    }

    public void setUserId(Integer UserId) {
        this.UserId = UserId;
    }

    public Integer getUserId() {
        return UserId;
    }

    public String getModule() {
        return Module;
    }

    public void setModule(String module) {
        Module = module;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getDescription() {
        return Description;
    }

    public void setCreateDate(Date CreateDate) {
        this.CreateDate = CreateDate;
    }

    public Date getCreateDate() {
        return CreateDate;
    }

    @Override
    public String toString() {
        return "Log{" +
                "Id='" + Id + '\'' +
                "UserId='" + UserId + '\'' +
                "Module='" + Module + '\'' +
                "Description='" + Description + '\'' +
                "CreateDate='" + CreateDate + '\'' +
                '}';
    }
}
