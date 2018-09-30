package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="base_dispatchunit")
public class Dispatchunit implements Serializable{

	  private Integer Id;
	  private String ProviderName;
	  private String AreaCode;
	  private String Address;
	  private String Contactor;
	  private String ContactPhone;
	  private String ZipCode;
	  private Boolean IsNormal;
	  private String FileReceivUnit;
	  private String FileReceivAddress;
	  private Integer Sort;
	  private Boolean IsEnabled;
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
