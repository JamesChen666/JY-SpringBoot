package com.boot.model.survey;

import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;

/**
 * 答卷 单选题保存表
 *
 */
@Table(name = "sv_an_radio")
public class AnRadio implements Serializable {

    private static final long serialVersionUID = -7381796091395924092L;
    private Long id;
    // 所属问卷ID
    private Long belongId;
    // 对应的答卷信息表
    private Long belongAnswerId;
    // 题目 ID
    private Long quId;
    //结果选项ID
    private Long quItemId;
    //复合题的其它项
    private String otherText;

    private Integer visibility = 1;

    public AnRadio() {

    }

    public AnRadio(Long surveyId, Long surveyAnswerId, Long quId,
                   Long quItemId) {
        this.belongId = surveyId;
        this.belongAnswerId = surveyAnswerId;
        this.quId = quId;
        this.quItemId = quItemId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBelongId() {
        return belongId;
    }

    public void setBelongId(Long belongId) {
        this.belongId = belongId;
    }

    public Long getBelongAnswerId() {
        return belongAnswerId;
    }

    public void setBelongAnswerId(Long belongAnswerId) {
        this.belongAnswerId = belongAnswerId;
    }

    public Long getQuId() {
        return quId;
    }

    public void setQuId(Long quId) {
        this.quId = quId;
    }

    public Long getQuItemId() {
        return quItemId;
    }

    public void setQuItemId(Long quItemId) {
        this.quItemId = quItemId;
    }

    public String getOtherText() {
        return otherText;
    }

    public void setOtherText(String otherText) {
        this.otherText = otherText;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }
}
