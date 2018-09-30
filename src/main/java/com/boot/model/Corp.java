package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name="School_Corp")
public class Corp implements Serializable{

	  private Integer Id;
	  private Integer UserId;
	  private String CorpName;
	  private String EconomicTypeCode;
	  private String NatureCdoe;
	  private String IndustryCode;
	  private String ScaleCode;
	  private String CapitalCode;
	  private Date EstablishmentDate;
	  private String OrganizetionCode;
	  private String Contactor;
	  private String ContactPhone;
	  private String FixPhone;
	  private String Email;
	  private String PostCode;
	  private String Fax;
	  private String OrginCode;
	  private String Website;
	  private String QQ;
	  private String BusinessImage;
	  private String Logo;
	  private String Address;
	  private String Description;
	  private Integer Status;
	  private Integer ExamineUserId;
	  private Date ExamineDate;
	  private String ExamineRemark;

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
	  public void setCorpName(String CorpName) {
		this.CorpName = CorpName;
	  }
	  public String getCorpName() {
		return CorpName;
	  }
	  public void setEconomicTypeCode(String EconomicTypeCode) {
		this.EconomicTypeCode = EconomicTypeCode;
	  }
	  public String getEconomicTypeCode() {
		return EconomicTypeCode;
	  }
	  public void setNatureCdoe(String NatureCdoe) {
		this.NatureCdoe = NatureCdoe;
	  }
	  public String getNatureCdoe() {
		return NatureCdoe;
	  }
	  public void setIndustryCode(String IndustryCode) {
		this.IndustryCode = IndustryCode;
	  }
	  public String getIndustryCode() {
		return IndustryCode;
	  }
	  public void setScaleCode(String ScaleCode) {
		this.ScaleCode = ScaleCode;
	  }
	  public String getScaleCode() {
		return ScaleCode;
	  }
	  public void setCapitalCode(String CapitalCode) {
		this.CapitalCode = CapitalCode;
	  }
	  public String getCapitalCode() {
		return CapitalCode;
	  }
	  public void setEstablishmentDate(Date EstablishmentDate) {
		this.EstablishmentDate = EstablishmentDate;
	  }
	  public Date getEstablishmentDate() {
		return EstablishmentDate;
	  }
	  public void setOrganizetionCode(String OrganizetionCode) {
		this.OrganizetionCode = OrganizetionCode;
	  }
	  public String getOrganizetionCode() {
		return OrganizetionCode;
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
	  public void setFixPhone(String FixPhone) {
		this.FixPhone = FixPhone;
	  }
	  public String getFixPhone() {
		return FixPhone;
	  }
	  public void setEmail(String Email) {
		this.Email = Email;
	  }
	  public String getEmail() {
		return Email;
	  }
	  public void setPostCode(String PostCode) {
		this.PostCode = PostCode;
	  }
	  public String getPostCode() {
		return PostCode;
	  }
	  public void setFax(String Fax) {
		this.Fax = Fax;
	  }
	  public String getFax() {
		return Fax;
	  }
	  public void setOrginCode(String OrginCode) {
		this.OrginCode = OrginCode;
	  }
	  public String getOrginCode() {
		return OrginCode;
	  }
	  public void setWebsite(String Website) {
		this.Website = Website;
	  }
	  public String getWebsite() {
		return Website;
	  }
	  public void setQQ(String QQ) {
		this.QQ = QQ;
	  }
	  public String getQQ() {
		return QQ;
	  }
	  public void setBusinessImage(String BusinessImage) {
		this.BusinessImage = BusinessImage;
	  }
	  public String getBusinessImage() {
		return BusinessImage;
	  }
	  public void setLogo(String Logo) {
		this.Logo = Logo;
	  }
	  public String getLogo() {
		return Logo;
	  }
	  public void setAddress(String Address) {
		this.Address = Address;
	  }
	  public String getAddress() {
		return Address;
	  }
	  public void setDescription(String Description) {
		this.Description = Description;
	  }
	  public String getDescription() {
		return Description;
	  }
	  public void setStatus(Integer Status) {
		this.Status = Status;
	  }
	  public Integer getStatus() {
		return Status;
	  }
	  public void setExamineUserId(Integer ExamineUserId) {
		this.ExamineUserId = ExamineUserId;
	  }
	  public Integer getExamineUserId() {
		return ExamineUserId;
	  }
	  public void setExamineDate(Date ExamineDate) {
		this.ExamineDate = ExamineDate;
	  }
	  public Date getExamineDate() {
		return ExamineDate;
	  }
	  public void setExamineRemark(String ExamineRemark) {
		this.ExamineRemark = ExamineRemark;
	  }
	  public String getExamineRemark() {
		return ExamineRemark;
	  }
      @Override
      public String toString() {
         return "Corp{" +
                "Id='" + Id + '\'' +
                "UserId='" + UserId + '\'' +
                "CorpName='" + CorpName + '\'' +
                "EconomicTypeCode='" + EconomicTypeCode + '\'' +
                "NatureCdoe='" + NatureCdoe + '\'' +
                "IndustryCode='" + IndustryCode + '\'' +
                "ScaleCode='" + ScaleCode + '\'' +
                "CapitalCode='" + CapitalCode + '\'' +
                "EstablishmentDate='" + EstablishmentDate + '\'' +
                "OrganizetionCode='" + OrganizetionCode + '\'' +
                "Contactor='" + Contactor + '\'' +
                "ContactPhone='" + ContactPhone + '\'' +
                "FixPhone='" + FixPhone + '\'' +
                "Email='" + Email + '\'' +
                "PostCode='" + PostCode + '\'' +
                "Fax='" + Fax + '\'' +
                "OrginCode='" + OrginCode + '\'' +
                "Website='" + Website + '\'' +
                "QQ='" + QQ + '\'' +
                "BusinessImage='" + BusinessImage + '\'' +
                "Logo='" + Logo + '\'' +
                "Address='" + Address + '\'' +
                "Description='" + Description + '\'' +
                "Status='" + Status + '\'' +
                "ExamineUserId='" + ExamineUserId + '\'' +
                "ExamineDate='" + ExamineDate + '\'' +
                "ExamineRemark='" + ExamineRemark + '\'' +
                '}';
      }
}
