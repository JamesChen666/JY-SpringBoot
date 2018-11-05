package com.boot.model;

import com.boot.util.excel.annotation.ExcelField;
import com.boot.util.excel.converter.UserConverter.DateConvert;
import com.boot.util.excel.converter.UserConverter.UserTypeConvert;
import com.boot.util.excel.converter.UserConverter.WhetherConvert;
import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name = "sys_loginaccount")
public class Loginaccount implements Serializable {

    private Integer Id;
    @ExcelField(title = "姓名",order = 1)
    private String RealName;
    @ExcelField(title = "用户名",order = 2)
    private String UserName;
    private String PassWord;
    @ExcelField(title = "用户类型",order = 3,readConverter = UserTypeConvert.class,writeConverter = UserTypeConvert.class)
    private Integer UserTypeId;
    @ExcelField(title = "是否启用",order = 5,writeConverter = WhetherConvert.class)
    private Boolean IsEnabled;
    private String Avatar;
    @ExcelField(title = "创建时间",order = 4,writeConverter = DateConvert.class)
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
