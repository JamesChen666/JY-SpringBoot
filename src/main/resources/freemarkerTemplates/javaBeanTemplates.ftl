package ${packageName}.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
/**
 * @author chenjiang
 */
@Table(name="${tableName}")
public class ${className} implements Serializable{

<#-- 循环类型及属性 -->
<#list attrs as attr>
	  private ${attr.type} ${attr.name};
</#list>

<#-- 循环生成set get方法 -->
<#list attrs as attr>
	  public void set${attr.name?cap_first}(${attr.type} ${attr.name}) {
		this.${attr.name} = ${attr.name};
	  }
	  public ${attr.type} get${attr.name?cap_first}() {
		return ${attr.name};
	  }
</#list>
      @Override
      public String toString() {
         return "${className}{" +
            <#list attrs as attr>
                "${attr.name}='" + ${attr.name} + '\'' +
            </#list>
                '}';
      }
}
