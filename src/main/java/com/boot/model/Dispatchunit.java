package com.boot.model;

import com.boot.util.excel.annotation.ExcelField;
import com.boot.util.excel.converter.UserConverter.WhetherConvert;
import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;

/**
 * @author chenjiang
 */
@Table(name = "base_dispatchunit")
public class Dispatchunit implements Serializable {

    private Integer Id;
    @ExcelField(title = "单位名称", order =1)
    private String ProviderName;
    @ExcelField(title = "地区代码", order =2)
    private String AreaCode;
    @ExcelField(title = "地址", order =3)
    private String Address;
    @ExcelField(title = "单位联系人", order =4)
    private String Contactor;
    @ExcelField(title = "单位联系人电话", order =5)
    private String ContactPhone;
    @ExcelField(title = "邮编", order =6)
    private String ZipCode;
    @ExcelField(title = "是否师范单位", order =7,readConverter = WhetherConvert.class,writeConverter = WhetherConvert.class)
    private Boolean IsNormal;
    @ExcelField(title = "档案接收单位", order =8)
    private String FileReceivUnit;
    @ExcelField(title = "档案接收地址", order =9)
    private String FileReceivAddress;
    @ExcelField(title = "排序", order =11)
    private Integer Sort;
    private Boolean IsEnabled;
    @ExcelField(title = "备注", order =10)
    private String Remark;

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public Integer getId() {
        return Id;
    }

    public void setProviderName(String ProviderName) {
        this.ProviderName = ProviderName;
    }

    public String getProviderName() {
        return ProviderName;
    }

    public void setAreaCode(String AreaCode) {
        this.AreaCode = AreaCode;
    }

    public String getAreaCode() {
        return AreaCode;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getAddress() {
        return Address;
    }

    public void setContactor(String Contactor) {
        this.Contactor = Contactor;
    }

    public String getContactor() {
        return Contactor;
    }

    public void setContactPhone(String ContactPhone) {
        this.ContactPhone = ContactPhone;
    }

    public String getContactPhone() {
        return ContactPhone;
    }

    public void setZipCode(String ZipCode) {
        this.ZipCode = ZipCode;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setIsNormal(Boolean IsNormal) {
        this.IsNormal = IsNormal;
    }

    public Boolean getIsNormal() {
        return IsNormal;
    }

    public void setFileReceivUnit(String FileReceivUnit) {
        this.FileReceivUnit = FileReceivUnit;
    }

    public String getFileReceivUnit() {
        return FileReceivUnit;
    }

    public void setFileReceivAddress(String FileReceivAddress) {
        this.FileReceivAddress = FileReceivAddress;
    }

    public String getFileReceivAddress() {
        return FileReceivAddress;
    }

    public void setSort(Integer Sort) {
        this.Sort = Sort;
    }

    public Integer getSort() {
        return Sort;
    }

    public void setIsEnabled(Boolean IsEnabled) {
        this.IsEnabled = IsEnabled;
    }

    public Boolean getIsEnabled() {
        return IsEnabled;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getRemark() {
        return Remark;
    }

    @Override
    public String toString() {
        return "Dispatchunit{" +
                "Id='" + Id + '\'' +
                "ProviderName='" + ProviderName + '\'' +
                "AreaCode='" + AreaCode + '\'' +
                "Address='" + Address + '\'' +
                "Contactor='" + Contactor + '\'' +
                "ContactPhone='" + ContactPhone + '\'' +
                "ZipCode='" + ZipCode + '\'' +
                "IsNormal='" + IsNormal + '\'' +
                "FileReceivUnit='" + FileReceivUnit + '\'' +
                "FileReceivAddress='" + FileReceivAddress + '\'' +
                "Sort='" + Sort + '\'' +
                "IsEnabled='" + IsEnabled + '\'' +
                "Remark='" + Remark + '\'' +
                '}';
    }
}
