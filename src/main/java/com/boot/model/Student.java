package com.boot.model;

import com.boot.util.excel.annotation.ExcelField;
import com.boot.util.excel.converter.UserConverter.NationConvert;
import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name = "school_student")
public class Student implements Serializable {

    private Integer Id;
    @ExcelField(title = "学号",order = 1)
    private String StudentNumber;
    @ExcelField(title = "姓名",order = 2)
    private String RealName;
    @ExcelField(title = "考生号",order = 3)
    private String ExamineNumber;
    @ExcelField(title = "身份证号",order = 4)
    private String IdCard;
    @ExcelField(title = "性别",order = 5)
    private Integer Sex;
    @ExcelField(title = "民族",order = 6,writeConverter = NationConvert.class)
    private String NationCode;
    @ExcelField(title = "政治面貌",order = 7)
    private String PoliticalCode;
    @ExcelField(title = "学历",order = 8)
    private String LevelCode;
    @ExcelField(title = "生源地",order = 9)
    private String OriginCode;
    @ExcelField(title = "培养方式",order = 10)
    private String CultureCode;
    @ExcelField(title = "主修外语语种",order = 11)
    private String MajorLanguageCode;
    @ExcelField(title = "入学时间",order = 12)
    private Integer EnromDate;
    @ExcelField(title = "毕业时间",order = 13)
    private Integer GraduationDate;
    @ExcelField(title = "生日",order = 14)
    private Date Birthday;
    @ExcelField(title = "师范生类别",order = 15)
    private String NormalCode;
    @ExcelField(title = "困难生类别",order = 16)
    private String DifficultCode;
    @ExcelField(title = "低保家庭困难生类别",order = 17)
    private String SubsistenceCode;
    @ExcelField(title = "定向或委派单位",order = 18)
    private String DireOrDeleUnit;
    @ExcelField(title = "班级",order = 19)
    private String ClassNumber;
    @ExcelField(title = "专业",order = 20)
    private String SpecialtyCode;
    @ExcelField(title = "专业方向",order = 21)
    private String SpecialtyDir;
    @ExcelField(title = "城乡生源",order = 22)
    private String UrbanOrRuralCode;
    @ExcelField(title = "入学前档案所在单位",order = 23)
    private String BeforeArchives;
    @ExcelField(title = "档案是否转入学校",order = 24)
    private Boolean IsArchiveIntoSchool;
    @ExcelField(title = "入学前户口所在地派出所",order = 25)
    private String BeforeResidentPolice;
    @ExcelField(title = "户口是否转入学校",order = 26)
    private Boolean IsResidentIntoSchool;
    @ExcelField(title = "电话",order = 27)
    private String Telephone;
    @ExcelField(title = "邮箱",order = 28)
    private String Email;
    @ExcelField(title = "QQ号码",order = 29)
    private String QqNumber;
    @ExcelField(title = "家庭地址",order = 30)
    private String FamilyAddress;
    @ExcelField(title = "家庭电话",order = 31)
    private String FamilyPhone;
    @ExcelField(title = "家庭邮编",order = 32)
    private String FamilyZipCode;
    private Boolean IsEnabled;
    @ExcelField(title = "备注",order = 1)
    private String Remarks;

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public Integer getId() {
        return Id;
    }

    public void setStudentNumber(String StudentNumber) {
        this.StudentNumber = StudentNumber;
    }

    public String getStudentNumber() {
        return StudentNumber;
    }

    public void setRealName(String RealName) {
        this.RealName = RealName;
    }

    public String getRealName() {
        return RealName;
    }

    public void setExamineNumber(String ExamineNumber) {
        this.ExamineNumber = ExamineNumber;
    }

    public String getExamineNumber() {
        return ExamineNumber;
    }

    public void setIdCard(String IdCard) {
        this.IdCard = IdCard;
    }

    public String getIdCard() {
        return IdCard;
    }

    public void setSex(Integer Sex) {
        this.Sex = Sex;
    }

    public Integer getSex() {
        return Sex;
    }

    public void setNationCode(String NationCode) {
        this.NationCode = NationCode;
    }

    public String getNationCode() {
        return NationCode;
    }

    public void setPoliticalCode(String PoliticalCode) {
        this.PoliticalCode = PoliticalCode;
    }

    public String getPoliticalCode() {
        return PoliticalCode;
    }

    public void setLevelCode(String LevelCode) {
        this.LevelCode = LevelCode;
    }

    public String getLevelCode() {
        return LevelCode;
    }

    public void setOriginCode(String OriginCode) {
        this.OriginCode = OriginCode;
    }

    public String getOriginCode() {
        return OriginCode;
    }

    public void setCultureCode(String CultureCode) {
        this.CultureCode = CultureCode;
    }

    public String getCultureCode() {
        return CultureCode;
    }

    public void setMajorLanguageCode(String MajorLanguageCode) {
        this.MajorLanguageCode = MajorLanguageCode;
    }

    public String getMajorLanguageCode() {
        return MajorLanguageCode;
    }

    public void setEnromDate(Integer EnromDate) {
        this.EnromDate = EnromDate;
    }

    public Integer getEnromDate() {
        return EnromDate;
    }

    public void setGraduationDate(Integer GraduationDate) {
        this.GraduationDate = GraduationDate;
    }

    public Integer getGraduationDate() {
        return GraduationDate;
    }

    public void setBirthday(Date Birthday) {
        this.Birthday = Birthday;
    }

    public Date getBirthday() {
        return Birthday;
    }

    public void setNormalCode(String NormalCode) {
        this.NormalCode = NormalCode;
    }

    public String getNormalCode() {
        return NormalCode;
    }

    public void setDifficultCode(String DifficultCode) {
        this.DifficultCode = DifficultCode;
    }

    public String getDifficultCode() {
        return DifficultCode;
    }

    public void setSubsistenceCode(String SubsistenceCode) {
        this.SubsistenceCode = SubsistenceCode;
    }

    public String getSubsistenceCode() {
        return SubsistenceCode;
    }

    public void setDireOrDeleUnit(String DireOrDeleUnit) {
        this.DireOrDeleUnit = DireOrDeleUnit;
    }

    public String getDireOrDeleUnit() {
        return DireOrDeleUnit;
    }

    public String getClassNumber() {
        return ClassNumber;
    }

    public void setClassNumber(String classNumber) {
        ClassNumber = classNumber;
    }

    public void setSpecialtyCode(String SpecialtyCode) {
        this.SpecialtyCode = SpecialtyCode;
    }

    public String getSpecialtyCode() {
        return SpecialtyCode;
    }

    public void setSpecialtyDir(String SpecialtyDir) {
        this.SpecialtyDir = SpecialtyDir;
    }

    public String getSpecialtyDir() {
        return SpecialtyDir;
    }

    public void setUrbanOrRuralCode(String UrbanOrRuralCode) {
        this.UrbanOrRuralCode = UrbanOrRuralCode;
    }

    public String getUrbanOrRuralCode() {
        return UrbanOrRuralCode;
    }

    public void setBeforeArchives(String BeforeArchives) {
        this.BeforeArchives = BeforeArchives;
    }

    public String getBeforeArchives() {
        return BeforeArchives;
    }

    public void setIsArchiveIntoSchool(Boolean IsArchiveIntoSchool) {
        this.IsArchiveIntoSchool = IsArchiveIntoSchool;
    }

    public Boolean getIsArchiveIntoSchool() {
        return IsArchiveIntoSchool;
    }

    public void setBeforeResidentPolice(String BeforeResidentPolice) {
        this.BeforeResidentPolice = BeforeResidentPolice;
    }

    public String getBeforeResidentPolice() {
        return BeforeResidentPolice;
    }

    public void setIsResidentIntoSchool(Boolean IsResidentIntoSchool) {
        this.IsResidentIntoSchool = IsResidentIntoSchool;
    }

    public Boolean getIsResidentIntoSchool() {
        return IsResidentIntoSchool;
    }

    public void setTelephone(String Telephone) {
        this.Telephone = Telephone;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getEmail() {
        return Email;
    }

    public String getQqNumber() {
        return QqNumber;
    }

    public void setQqNumber(String qqNumber) {
        QqNumber = qqNumber;
    }

    public void setFamilyAddress(String FamilyAddress) {
        this.FamilyAddress = FamilyAddress;
    }

    public String getFamilyAddress() {
        return FamilyAddress;
    }

    public void setFamilyPhone(String FamilyPhone) {
        this.FamilyPhone = FamilyPhone;
    }

    public String getFamilyPhone() {
        return FamilyPhone;
    }

    public String getFamilyZipCode() {
        return FamilyZipCode;
    }

    public void setFamilyZipCode(String familyZipCode) {
        FamilyZipCode = familyZipCode;
    }

    public void setIsEnabled(Boolean IsEnabled) {
        this.IsEnabled = IsEnabled;
    }

    public Boolean getIsEnabled() {
        return IsEnabled;
    }

    public void setRemarks(String Remarks) {
        this.Remarks = Remarks;
    }

    public String getRemarks() {
        return Remarks;
    }

    @Override
    public String toString() {
        return "Student{" +
                "Id='" + Id + '\'' +
                "StudentNumber='" + StudentNumber + '\'' +
                "RealName='" + RealName + '\'' +
                "ExamineNumber='" + ExamineNumber + '\'' +
                "IdCard='" + IdCard + '\'' +
                "Sex='" + Sex + '\'' +
                "NationCode='" + NationCode + '\'' +
                "PoliticalCode='" + PoliticalCode + '\'' +
                "LevelCode='" + LevelCode + '\'' +
                "OriginCode='" + OriginCode + '\'' +
                "CultureCode='" + CultureCode + '\'' +
                "MajorLanguageCode='" + MajorLanguageCode + '\'' +
                "EnromDate='" + EnromDate + '\'' +
                "GraduationDate='" + GraduationDate + '\'' +
                "Birthday='" + Birthday + '\'' +
                "NormalCode='" + NormalCode + '\'' +
                "DifficultCode='" + DifficultCode + '\'' +
                "SubsistenceCode='" + SubsistenceCode + '\'' +
                "DireOrDeleUnit='" + DireOrDeleUnit + '\'' +
                "ClassNumber='" + ClassNumber + '\'' +
                "SpecialtyCode='" + SpecialtyCode + '\'' +
                "SpecialtyDir='" + SpecialtyDir + '\'' +
                "UrbanOrRuralCode='" + UrbanOrRuralCode + '\'' +
                "BeforeArchives='" + BeforeArchives + '\'' +
                "IsArchiveIntoSchool='" + IsArchiveIntoSchool + '\'' +
                "BeforeResidentPolice='" + BeforeResidentPolice + '\'' +
                "IsResidentIntoSchool='" + IsResidentIntoSchool + '\'' +
                "Telphone='" + Telephone + '\'' +
                "Email='" + Email + '\'' +
                "QqNumber='" + QqNumber + '\'' +
                "FamilyAddress='" + FamilyAddress + '\'' +
                "FamilyPhone='" + FamilyPhone + '\'' +
                "FamillZipCode='" + FamilyZipCode + '\'' +
                "IsEnabled='" + IsEnabled + '\'' +
                "Remarks='" + Remarks + '\'' +
                '}';
    }
}
