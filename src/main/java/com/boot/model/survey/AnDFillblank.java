package com.boot.model.survey;


import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;

/**
 * 答卷  多行填空题保存表
 *
 */
@Table(name = "sv_an_dfillblank")
public class AnDFillblank implements Serializable {

    private static final long serialVersionUID = -3192171093623449314L;
    private Long id;
    //所属问卷ID
    private Long belongId;
    //对应的答卷信息表
    private Long belongAnswerId;
    //题目 ID
    private Long quId;
    //对应的填空项ID
    private Long quItemId;
    //答案
    private String answer;

    private Integer visibility = 1;

    public AnDFillblank() {

    }

    public AnDFillblank(Long surveyId, Long surveyAnswerId, Long quId,
                        Long quItemId, String answerValue) {
        this.belongId = surveyId;
        this.belongAnswerId = surveyAnswerId;
        this.quId = quId;
        this.quItemId = quItemId;
        this.answer = answerValue;
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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }


}
