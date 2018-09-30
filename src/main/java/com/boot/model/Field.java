package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="student_field")
public class Field implements Serializable{

	  private Integer Id;
	  private String FieldCode;
	  private String CnTitle;
	  private String EnTitle;
	  private Boolean IsAllotUpdate;
	  private Boolean IsNeedAudit;
	  private Boolean IsEnabled;
	  private Integer Sort;

	  public void setId(Integer Id) {
		this.Id = Id;
	  }
	  public Integer getId() {
		return Id;
	  }
	  public void setFieldCode(String FieldCode) {
		this.FieldCode = FieldCode;
	  }
	  public String getFieldCode() {
		return FieldCode;
	  }
	  public void setCnTitle(String CnTitle) {
		this.CnTitle = CnTitle;
	  }
	  public String getCnTitle() {
		return CnTitle;
	  }
	  public void setEnTitle(String EnTitle) {
		this.EnTitle = EnTitle;
	  }
	  public String getEnTitle() {
		return EnTitle;
	  }
	  public void setIsAllotUpdate(Boolean IsAllotUpdate) {
		this.IsAllotUpdate = IsAllotUpdate;
	  }
	  public Boolean getIsAllotUpdate() {
		return IsAllotUpdate;
	  }
	  public void setIsNeedAudit(Boolean IsNeedAudit) {
		this.IsNeedAudit = IsNeedAudit;
	  }
	  public Boolean getIsNeedAudit() {
		return IsNeedAudit;
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
         return "Field{" +
                "Id='" + Id + '\'' +
                "FieldCode='" + FieldCode + '\'' +
                "CnTitle='" + CnTitle + '\'' +
                "EnTitle='" + EnTitle + '\'' +
                "IsAllotUpdate='" + IsAllotUpdate + '\'' +
                "IsNeedAudit='" + IsNeedAudit + '\'' +
                "IsEnabled='" + IsEnabled + '\'' +
                "Sort='" + Sort + '\'' +
                '}';
      }
}
