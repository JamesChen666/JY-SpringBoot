package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name = "student_sign_log")
public class SignLog implements Serializable {

    private Integer Id;
    private Integer StudentId;
    private String GraduationWhereAboutCode;
    private String HighGradeTypeCode;
    private String CorporationName;
    private String CorpNatureCdoe;
    private String CorpIndustryCode;
    private String UnderDepartmentCode;
    private String CorpOrginCode;
    private String CorpOrganizationCode;
    private String JobTypeCode;
    private String CorpContactor;
    private String ContactorPostion;
    private String CorpContactPhone;
    private String CorpContactEmail;
    private String CorpContactFax;
    private String CorpAddress;
    private String CorpPostCode;
    private String ArchiveCorp;
    private String ArchiveContactor;
    private String ArchiveContactPhone;
    private String ForwardCorpName;
    private String ForwardCorpPostCode;
    private String WaitingTypeCode;
    private String Remark;
    private Date CreateDate;
    private Integer UserId;

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

    public void setGraduationWhereAboutCode(String GraduationWhereAboutCode) {
        this.GraduationWhereAboutCode = GraduationWhereAboutCode;
    }

    public String getGraduationWhereAboutCode() {
        return GraduationWhereAboutCode;
    }

    public void setHighGradeTypeCode(String HighGradeTypeCode) {
        this.HighGradeTypeCode = HighGradeTypeCode;
    }

    public String getHighGradeTypeCode() {
        return HighGradeTypeCode;
    }

    public void setCorporationName(String CorporationName) {
        this.CorporationName = CorporationName;
    }

    public String getCorporationName() {
        return CorporationName;
    }

    public void setCorpNatureCdoe(String CorpNatureCdoe) {
        this.CorpNatureCdoe = CorpNatureCdoe;
    }

    public String getCorpNatureCdoe() {
        return CorpNatureCdoe;
    }

    public void setCorpIndustryCode(String CorpIndustryCode) {
        this.CorpIndustryCode = CorpIndustryCode;
    }

    public String getCorpIndustryCode() {
        return CorpIndustryCode;
    }

    public void setUnderDepartmentCode(String UnderDepartmentCode) {
        this.UnderDepartmentCode = UnderDepartmentCode;
    }

    public String getUnderDepartmentCode() {
        return UnderDepartmentCode;
    }

    public void setCorpOrginCode(String CorpOrginCode) {
        this.CorpOrginCode = CorpOrginCode;
    }

    public String getCorpOrginCode() {
        return CorpOrginCode;
    }

    public void setCorpOrganizationCode(String CorpOrganizationCode) {
        this.CorpOrganizationCode = CorpOrganizationCode;
    }

    public String getCorpOrganizationCode() {
        return CorpOrganizationCode;
    }

    public void setJobTypeCode(String JobTypeCode) {
        this.JobTypeCode = JobTypeCode;
    }

    public String getJobTypeCode() {
        return JobTypeCode;
    }

    public void setCorpContactor(String CorpContactor) {
        this.CorpContactor = CorpContactor;
    }

    public String getCorpContactor() {
        return CorpContactor;
    }

    public void setContactorPostion(String ContactorPostion) {
        this.ContactorPostion = ContactorPostion;
    }

    public String getContactorPostion() {
        return ContactorPostion;
    }

    public void setCorpContactPhone(String CorpContactPhone) {
        this.CorpContactPhone = CorpContactPhone;
    }

    public String getCorpContactPhone() {
        return CorpContactPhone;
    }

    public void setCorpContactEmail(String CorpContactEmail) {
        this.CorpContactEmail = CorpContactEmail;
    }

    public String getCorpContactEmail() {
        return CorpContactEmail;
    }

    public void setCorpContactFax(String CorpContactFax) {
        this.CorpContactFax = CorpContactFax;
    }

    public String getCorpContactFax() {
        return CorpContactFax;
    }

    public void setCorpAddress(String CorpAddress) {
        this.CorpAddress = CorpAddress;
    }

    public String getCorpAddress() {
        return CorpAddress;
    }

    public void setCorpPostCode(String CorpPostCode) {
        this.CorpPostCode = CorpPostCode;
    }

    public String getCorpPostCode() {
        return CorpPostCode;
    }

    public void setArchiveCorp(String ArchiveCorp) {
        this.ArchiveCorp = ArchiveCorp;
    }

    public String getArchiveCorp() {
        return ArchiveCorp;
    }

    public void setArchiveContactor(String ArchiveContactor) {
        this.ArchiveContactor = ArchiveContactor;
    }

    public String getArchiveContactor() {
        return ArchiveContactor;
    }

    public void setArchiveContactPhone(String ArchiveContactPhone) {
        this.ArchiveContactPhone = ArchiveContactPhone;
    }

    public String getArchiveContactPhone() {
        return ArchiveContactPhone;
    }

    public void setForwardCorpName(String ForwardCorpName) {
        this.ForwardCorpName = ForwardCorpName;
    }

    public String getForwardCorpName() {
        return ForwardCorpName;
    }

    public void setForwardCorpPostCode(String ForwardCorpPostCode) {
        this.ForwardCorpPostCode = ForwardCorpPostCode;
    }

    public String getForwardCorpPostCode() {
        return ForwardCorpPostCode;
    }

    public String getWaitingTypeCode() {
        return WaitingTypeCode;
    }

    public void setWaitingTypeCode(String waitingTypeCode) {
        WaitingTypeCode = waitingTypeCode;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getRemark() {
        return Remark;
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
        return "SignLog{" +
                "Id='" + Id + '\'' +
                "StudentId='" + StudentId + '\'' +
                "GraduationWhereAboutCode='" + GraduationWhereAboutCode + '\'' +
                "HighGradeTypeCode='" + HighGradeTypeCode + '\'' +
                "CorporationName='" + CorporationName + '\'' +
                "CorpNatureCdoe='" + CorpNatureCdoe + '\'' +
                "CorpIndustryCode='" + CorpIndustryCode + '\'' +
                "UnderDepartmentCode='" + UnderDepartmentCode + '\'' +
                "CorpOrginCode='" + CorpOrginCode + '\'' +
                "CorpOrganizationCode='" + CorpOrganizationCode + '\'' +
                "JobTypeCode='" + JobTypeCode + '\'' +
                "CorpContactor='" + CorpContactor + '\'' +
                "ContactorPostion='" + ContactorPostion + '\'' +
                "CorpContactPhone='" + CorpContactPhone + '\'' +
                "CorpContactEmail='" + CorpContactEmail + '\'' +
                "CorpContactFax='" + CorpContactFax + '\'' +
                "CorpAddress='" + CorpAddress + '\'' +
                "CorpPostCode='" + CorpPostCode + '\'' +
                "ArchiveCorp='" + ArchiveCorp + '\'' +
                "ArchiveContactor='" + ArchiveContactor + '\'' +
                "ArchiveContactPhone='" + ArchiveContactPhone + '\'' +
                "ForwardCorpName='" + ForwardCorpName + '\'' +
                "ForwardCorpPostCode='" + ForwardCorpPostCode + '\'' +
                "WaitingTypeCode='" + WaitingTypeCode + '\'' +
                "Remark='" + Remark + '\'' +
                "CreateDate='" + CreateDate + '\'' +
                "UserId='" + UserId + '\'' +
                '}';
    }
}
