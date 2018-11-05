package com.boot.model.survey;

import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * 题目逻辑设置
 *
 */

@Table(name = "sv_question_logic")
public class QuestionLogic implements Serializable {
    private static final long serialVersionUID = 7429037455062407230L;
    private Long id;
    //cgQuId,cgQuItem,skQuId,skType
    //回答选择的题ID
    private Long ckQuId;
    //回答选择题的选项ID  （0任意选项）
    private Long cgQuItemId;
    //要跳转的题  (end1提前结束-计入结果  end2提前结束-不计结果)
    private String skQuId;
    //逻辑类型  (1=跳转,2显示)
    private String logicType = "1";
    //评分题 ge大于，le小于
    private String geLe;
    private Integer scoreNum;
    //创建时间
    private Date createDate = new Date();
    //是否显示  1显示 0不显示
    private Integer visibility = 1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCkQuId() {
        return ckQuId;
    }

    public void setCkQuId(Long ckQuId) {
        this.ckQuId = ckQuId;
    }

    public Long getCgQuItemId() {
        return cgQuItemId;
    }

    public void setCgQuItemId(Long cgQuItemId) {
        this.cgQuItemId = cgQuItemId;
    }

    public String getSkQuId() {
        return skQuId;
    }

    public void setSkQuId(String skQuId) {
        this.skQuId = skQuId;
    }

    public String getLogicType() {
        return logicType;
    }

    public void setLogicType(String logicType) {
        this.logicType = logicType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public String getGeLe() {
        return geLe;
    }

    public void setGeLe(String geLe) {
        this.geLe = geLe;
    }

    public Integer getScoreNum() {
        return scoreNum;
    }

    public void setScoreNum(Integer scoreNum) {
        this.scoreNum = scoreNum;
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
