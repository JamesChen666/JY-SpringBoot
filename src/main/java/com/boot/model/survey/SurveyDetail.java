package com.boot.model.survey;


import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * 具体的一次调查
 */
@Table(name = "sv_survey_detail")
public class SurveyDetail implements Serializable {
    private static final long serialVersionUID = 2794574370530483062L;
    private Long id;
    //所对应的surveyDirectory的ID
    private Long dirId;
    //问卷有效性 限制 --------- 1不限制， 2使用Cookie技术， 3使用来源IP检测
    // 4 每台电脑或手机只能答一次
    private Integer effective = 1;
    //有效性间隔时间
    private Integer effectiveTime = 5;
    //每个IP只能答一次 1是 0否
    private Integer effectiveIp = 0;

    //防刷新  1启用 0不启用
    private Integer refresh = 1;
    private Integer refreshNum = 3;

    //调查规则  --------  1公开, 2私有, 3令牌
    //3 表示启用访问密码
    private Integer rule = 1;
    private String ruleCode = "令牌";

    //结束方式  ---------- 1手动结束   2依据结束时间  3依据收到的份数
    private Integer endType = 1;

    //开始时间
    private Date begTime = new Date();
    //结束时间
    private Date endTime = new Date();
    //收到的份数
    private Integer endNum = 1000;
    //问卷说明
    private String surveyNote;

    //是否依据收到的份数结束
    private Integer ynEndNum = 0;
    private Integer ynEndTime = 0;

    //问卷
    //问卷下面有多少题目数  ---
    private Integer surveyQuNum = 0;
    //可以回答的最少选项数目
    private Integer anItemLeastNum = 0;
    //可以回答的最多选项数目
    private Integer anItemMostNum = 0;

    //只有邮件邀请唯一链接的受访者可回答  1启用 0不启用
    private Integer mailOnly = 0;

    //显示分享
    private Integer showShareSurvey = 1;
    private Integer showAnswerDa = 0;

    //面向群体（1：学生，2：单位）
    private String toGroup;

    //是否强制 0:是，1：否
    private Integer isForce;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDirId() {
        return dirId;
    }

    public void setDirId(Long dirId) {
        this.dirId = dirId;
    }

    public String getSurveyNote() {
        return surveyNote;
    }

    public void setSurveyNote(String surveyNote) {
        this.surveyNote = surveyNote;
    }

    public Integer getEffective() {
        return effective;
    }

    public void setEffective(Integer effective) {
        this.effective = effective;
    }

    public Integer getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Integer effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Integer getRefresh() {
        return refresh;
    }

    public void setRefresh(Integer refresh) {
        this.refresh = refresh;
    }

    public Integer getRefreshNum() {
        return refreshNum;
    }

    public void setRefreshNum(Integer refreshNum) {
        this.refreshNum = refreshNum;
    }

    public Integer getRule() {
        return rule;
    }

    public void setRule(Integer rule) {
        this.rule = rule;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public Integer getEndType() {
        return endType;
    }

    public void setEndType(Integer endType) {
        this.endType = endType;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getEndNum() {
        return endNum;
    }

    public void setEndNum(Integer endNum) {
        this.endNum = endNum;
    }

    public Integer getAnItemLeastNum() {
        return anItemLeastNum;
    }

    public void setAnItemLeastNum(Integer anItemLeastNum) {
        this.anItemLeastNum = anItemLeastNum;
    }

    public Integer getAnItemMostNum() {
        return anItemMostNum;
    }

    public void setAnItemMostNum(Integer anItemMostNum) {
        this.anItemMostNum = anItemMostNum;
    }

    public Integer getSurveyQuNum() {
        return surveyQuNum;
    }

    public void setSurveyQuNum(Integer surveyQuNum) {
        this.surveyQuNum = surveyQuNum;
    }

    public Integer getMailOnly() {
        return mailOnly;
    }

    public void setMailOnly(Integer mailOnly) {
        this.mailOnly = mailOnly;
    }

    public Integer getEffectiveIp() {
        return effectiveIp;
    }

    public void setEffectiveIp(Integer effectiveIp) {
        this.effectiveIp = effectiveIp;
    }

    public Integer getYnEndNum() {
        return ynEndNum;
    }

    public void setYnEndNum(Integer ynEndNum) {
        this.ynEndNum = ynEndNum;
    }

    public Integer getYnEndTime() {
        return ynEndTime;
    }

    public void setYnEndTime(Integer ynEndTime) {
        this.ynEndTime = ynEndTime;
    }

    public Integer getShowShareSurvey() {
        return showShareSurvey;
    }

    public void setShowShareSurvey(Integer showShareSurvey) {
        this.showShareSurvey = showShareSurvey;
    }

    public Integer getShowAnswerDa() {
        return showAnswerDa;
    }

    public void setShowAnswerDa(Integer showAnswerDa) {
        this.showAnswerDa = showAnswerDa;
    }

    public Date getBegTime() {
        return begTime;
    }

    public void setBegTime(Date begTime) {
        this.begTime = begTime;
    }

    public String getToGroup() {
        return toGroup;
    }

    public void setToGroup(String toGroup) {
        this.toGroup = toGroup;
    }

    public Integer getIsForce() {
        return isForce;
    }

    public void setIsForce(Integer isForce) {
        this.isForce = isForce;
    }
}
