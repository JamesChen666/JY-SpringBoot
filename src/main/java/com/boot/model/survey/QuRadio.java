package com.boot.model.survey;


import com.boot.model.survey.enums.CheckType;
import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;

/**
 * 单选题选项
 *
 */

@Table(name = "sv_qu_radio")
public class QuRadio implements Serializable {

    private static final long serialVersionUID = -6120754565012063194L;
    private Long id;
    //所属题
    private Long quId;
    //选项标题
    private String optionTitle;
    //选项内容
    private String optionName;
    //是否带说明  0否  1是
    private Integer isNote = 0;
    //说明的验证方式
    private CheckType checkType;
    //说明内容是否必填
    private Integer isRequiredFill = 0;
    //排序ID
    private Integer orderById;
    //是否显示  0不显示
    private Integer visibility = 1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuId() {
        return quId;
    }

    public void setQuId(Long quId) {
        this.quId = quId;
    }

    public String getOptionTitle() {
        return optionTitle;
    }

    public void setOptionTitle(String optionTitle) {
        this.optionTitle = optionTitle;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public Integer getOrderById() {
        return orderById;
    }

    public void setOrderById(Integer orderById) {
        this.orderById = orderById;
    }

    public Integer getIsNote() {
        return isNote;
    }

    public void setIsNote(Integer isNote) {
        this.isNote = isNote;
    }

    public CheckType getCheckType() {
        return checkType;
    }

    public void setCheckType(CheckType checkType) {
        this.checkType = checkType;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Integer getIsRequiredFill() {
        return isRequiredFill;
    }

    public void setIsRequiredFill(Integer isRequiredFill) {
        this.isRequiredFill = isRequiredFill;
    }

    private Integer anCount = 0;

    public Integer getAnCount() {
        return anCount;
    }

    public void setAnCount(Integer anCount) {
        this.anCount = anCount;
    }

}
