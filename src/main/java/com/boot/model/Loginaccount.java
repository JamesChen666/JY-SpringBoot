package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name = "sys_loginaccount")
public class Loginaccount implements Serializable {

    private Integer Id;
    private String RealName;
    private String UserName;
    private String PassWord;
    private Integer UserTypeId;
    private Boolean IsEnabled;
    private String Avatar;
    private Date CreateDate;
    private String Salt;

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public Integer getId() {
        return Id;
    }

    public void setRealName(String RealName) {
        this.RealName = RealName;
    }

    public String getRealName() {
        return RealName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setPassWord(String PassWord) {
        this.PassWord = PassWord;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setUserTypeId(Integer UserTypeId) {
        this.UserTypeId = UserTypeId;
    }

    public Integer getUserTypeId() {
        return UserTypeId;
    }

    public void setIsEnabled(Boolean IsEnabled) {
        this.IsEnabled = IsEnabled;
    }

    public Boolean getIsEnabled() {
        return IsEnabled;
    }

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setCreateDate(Date CreateDate) {
        this.CreateDate = CreateDate;
    }

    public Date getCreateDate() {
        return CreateDate;
    }

    public String getSalt() {
        return Salt;
    }

    public void setSalt(String Salt) {
        this.Salt = Salt;
    }

    @Override
    public String toString() {
         return "Loginaccount{" +
                "Id='" + Id + '\'' +
                "RealName='" + RealName + '\'' +
                ", UserName='" + UserName + '\'' +
                ", PassWord='" + PassWord + '\'' +
                ", UserTypeId='" + UserTypeId + '\'' +
                ", CreateDate='" + CreateDate + '\'' +
                '}';
    }
}
