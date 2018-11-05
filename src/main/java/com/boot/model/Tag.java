package com.boot.model;

import com.boot.util.excel.annotation.ExcelField;
import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;

/**
 * @author chenjiang
 */
@Table(name = "school_tag")
public class Tag implements Serializable {

    private Integer Id;
    @ExcelField(title = "标签名称",order =1 )
    private String Title;
    @ExcelField(title = "排序",order = 2)
    private Integer Sort;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Integer getSort() {
        return Sort;
    }

    public void setSort(Integer sort) {
        Sort = sort;
    }
}
