package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name = "student_recommendtable")
public class Recommendtable implements Serializable {

    private Integer Id;
    private Integer StudentId;
    private String HopeNationCode;
    private String HopeIndustryCode;
    private BigDecimal HopeSalary;
    private String HopeLocation;
    private String PositionRemark;
    private String RewardRemark;
    private String PracticeRemark;
    private String SelfRecommendation;
    private String FacultyRecommendation;
    private Date CreateDate;
    private Integer UserId;
    private Date RecommendDate;
    private String RecommendUserId;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public void setStudentId(Integer StudentId) {
        this.StudentId = StudentId;
    }

    public Integer getStudentId() {
        return StudentId;
    }

    public void setHopeNationCode(String HopeNationCode) {
        this.HopeNationCode = HopeNationCode;
    }

    public String getHopeNationCode() {
        return HopeNationCode;
    }

    public void setHopeIndustryCode(String HopeIndustryCode) {
        this.HopeIndustryCode = HopeIndustryCode;
    }

    public String getHopeIndustryCode() {
        return HopeIndustryCode;
    }

    public void setHopeSalary(BigDecimal HopeSalary) {
        this.HopeSalary = HopeSalary;
    }

    public BigDecimal getHopeSalary() {
        return HopeSalary;
    }

    public void setHopeLocation(String HopeLocation) {
        this.HopeLocation = HopeLocation;
    }

    public String getHopeLocation() {
        return HopeLocation;
    }

    public String getPositionRemark() {
        return PositionRemark;
    }

    public void setPositionRemark(String positionRemark) {
        PositionRemark = positionRemark;
    }

    public String getRewardRemark() {
        return RewardRemark;
    }

    public void setRewardRemark(String rewardRemark) {
        RewardRemark = rewardRemark;
    }

    public String getPracticeRemark() {
        return PracticeRemark;
    }

    public void setPracticeRemark(String practiceRemark) {
        PracticeRemark = practiceRemark;
    }

    public void setSelfRecommendation(String SelfRecommendation) {
        this.SelfRecommendation = SelfRecommendation;
    }

    public String getSelfRecommendation() {
        return SelfRecommendation;
    }

    public void setFacultyRecommendation(String FacultyRecommendation) {
        this.FacultyRecommendation = FacultyRecommendation;
    }

    public String getFacultyRecommendation() {
        return FacultyRecommendation;
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

    public void setRecommendDate(Date RecommendDate) {
        this.RecommendDate = RecommendDate;
    }

    public Date getRecommendDate() {
        return RecommendDate;
    }

    public void setRecommendUserId(String RecommendUserId) {
        this.RecommendUserId = RecommendUserId;
    }

    public String getRecommendUserId() {
        return RecommendUserId;
    }

    @Override
    public String toString() {
        return "Recommendtable{" +
                "Id='" + Id + '\'' +
                "StudentId='" + StudentId + '\'' +
                "HopeNationCode='" + HopeNationCode + '\'' +
                "HopeIndustryCode='" + HopeIndustryCode + '\'' +
                "HopeSalary='" + HopeSalary + '\'' +
                "HopeLocation='" + HopeLocation + '\'' +
                "SelfRecommendation='" + SelfRecommendation + '\'' +
                "FacultyRecommendation='" + FacultyRecommendation + '\'' +
                "CreateDate='" + CreateDate + '\'' +
                "UserId='" + UserId + '\'' +
                "RecommendDate='" + RecommendDate + '\'' +
                "RecommendUserId='" + RecommendUserId + '\'' +
                '}';
    }
}
