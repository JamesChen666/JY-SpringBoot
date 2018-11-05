package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;

/**
 * @author chenjiang
 */
@Table(name = "sys_menu_button")
public class MenuButton implements Serializable {

    private Integer Id;
    private Integer MenuId;
    private String Type;
    private String BtnCode;
    private String BtnName;
    private String ButtonCode;
    private String ButtonName;
    private String Title;
    private String IconCls;
    private String ListId;
    private Boolean IsEnabled;
    private Integer Sort;
    private String Href;
    private String Save;
    private Double Height;
    private String Confirm;
    private String Args;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getMenuId() {
        return MenuId;
    }

    public void setMenuId(Integer menuId) {
        MenuId = menuId;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getBtnCode() {
        return BtnCode;
    }

    public void setBtnCode(String btnCode) {
        BtnCode = btnCode;
    }

    public String getBtnName() {
        return BtnName;
    }

    public void setBtnName(String btnName) {
        BtnName = btnName;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getIconCls() {
        return IconCls;
    }

    public void setIconCls(String iconCls) {
        IconCls = iconCls;
    }

    public String getListId() {
        return ListId;
    }

    public void setListId(String listId) {
        ListId = listId;
    }

    public Boolean getIsEnabled() {
        return IsEnabled;
    }

    public void setIsEnabled(Boolean IsEnabled) {
        this.IsEnabled = IsEnabled;
    }

    public Integer getSort() {
        return Sort;
    }

    public void setSort(Integer sort) {
        Sort = sort;
    }

    public String getHref() {
        return Href;
    }

    public void setHref(String href) {
        Href = href;
    }

    public String getSave() {
        return Save;
    }

    public void setSave(String save) {
        Save = save;
    }

    public Double getHeight() {
        return Height;
    }

    public void setHeight(Double height) {
        Height = height;
    }

    public String getConfirm() {
        return Confirm;
    }

    public void setConfirm(String confirm) {
        Confirm = confirm;
    }

    public String getArgs() {
        return Args;
    }

    public void setArgs(String args) {
        Args = args;
    }

    public String getButtonCode() {
        return ButtonCode;
    }

    public void setButtonCode(String buttonCode) {
        ButtonCode = buttonCode;
    }

    public String getButtonName() {
        return ButtonName;
    }

    public void setButtonName(String buttonName) {
        ButtonName = buttonName;
    }


    @Override
    public String toString() {
        return "MenuButton{" +
                "Id='" + Id + '\'' +
                "MenuId='" + MenuId + '\'' +
                "Type='" + Type + '\'' +
                "BtnCode='" + BtnCode + '\'' +
                "BtnName='" + BtnName + '\'' +
                "title='" + Title + '\'' +
                "iconCls='" + IconCls + '\'' +
                "listId='" + ListId + '\'' +
                "IsEnabled='" + IsEnabled + '\'' +
                "Sort='" + Sort + '\'' +
                "href='" + Href + '\'' +
                "save='" + Save + '\'' +
                "height='" + Height + '\'' +
                "confirm='" + Confirm + '\'' +
                "args='" + Args + '\'' +
                '}';
    }
}
