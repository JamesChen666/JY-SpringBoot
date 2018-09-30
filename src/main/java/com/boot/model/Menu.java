package com.boot.model;

import com.boot.util.excel.annotation.ExcelField;
import com.boot.util.excel.converter.UserConverter.MenuConvert;
import com.boot.util.excel.converter.UserConverter.WhetherConvert;
import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="sys_menu")
public class Menu implements Serializable{

	  private Integer Id;
	  @ExcelField(title = "上级菜单",order = 2,writeConverter = MenuConvert.class,readConverter = MenuConvert.class)
	  private Integer ParentId;
	  @ExcelField(title = "菜单名称",order = 1)
	  private String MenuName;
	  @ExcelField(title = "菜单代码",order = 3)
	  private String MenuCode;
	  @ExcelField(title = "菜单地址",order = 4)
	  private String Url;
	  @ExcelField(title = "是否启用",order = 5,writeConverter = WhetherConvert.class,readConverter = WhetherConvert.class)
	  private Boolean IsEnabled;
	  @ExcelField(title = "排序",order = 6)
	  private Integer Sort;

	  public void setId(Integer Id) {
		this.Id = Id;
	  }
	  public Integer getId() {
		return Id;
	  }
	  public void setParentId(Integer ParentId) {
		this.ParentId = ParentId;
	  }
	  public Integer getParentId() {
		return ParentId;
	  }
	  public void setMenuName(String MenuName) {
		this.MenuName = MenuName;
	  }
	  public String getMenuName() {
		return MenuName;
	  }
	  public void setMenuCode(String MenuCode) {
		this.MenuCode = MenuCode;
	  }
	  public String getMenuCode() {
		return MenuCode;
	  }
	  public void setUrl(String Url) {
		this.Url = Url;
	  }
	  public String getUrl() {
		return Url;
	  }
	  public void setIsEnabled(Boolean IsEnabled) {
		this.IsEnabled = IsEnabled;
	  }
	  public Boolean getIsEnabled() {
		return IsEnabled;
	  }
	  public void setSort(Integer Sort) {
		this.Sort = Sort;
	  }
	  public Integer getSort() {
		return Sort;
	  }
      @Override
      public String toString() {
         return "Menu{" +
                "Id='" + Id + '\'' +
                "ParentId='" + ParentId + '\'' +
                "MenuName='" + MenuName + '\'' +
                "MenuCode='" + MenuCode + '\'' +
                "Url='" + Url + '\'' +
                "IsEnabled='" + IsEnabled + '\'' +
                "Sort='" + Sort + '\'' +
                '}';
      }
}
