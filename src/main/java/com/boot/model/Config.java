package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="sys_config")
public class Config implements Serializable{

	  private Integer Id;
	  private String ParameterKey;
	  private String ParameterValue;
	  private String Remarks;

	  public void setId(Integer Id) {
		this.Id = Id;
	  }
	  public Integer getId() {
		return Id;
	  }
	  public void setParameterKey(String ParameterKey) {
		this.ParameterKey = ParameterKey;
	  }
	  public String getParameterKey() {
		return ParameterKey;
	  }
	  public void setParameterValue(String ParameterValue) {
		this.ParameterValue = ParameterValue;
	  }
	  public String getParameterValue() {
		return ParameterValue;
	  }
	  public void setRemarks(String Remarks) {
		this.Remarks = Remarks;
	  }
	  public String getRemarks() {
		return Remarks;
	  }
      @Override
      public String toString() {
         return "Config{" +
                "Id='" + Id + '\'' +
                "ParameterKey='" + ParameterKey + '\'' +
                "ParameterValue='" + ParameterValue + '\'' +
                "Remarks='" + Remarks + '\'' +
                '}';
      }
}
