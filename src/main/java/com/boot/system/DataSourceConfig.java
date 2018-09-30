package com.boot.system;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
/**
 * @author chenjiang
 */
@Configuration
public class DataSourceConfig {

 @Bean(name="datasource")
  public DataSource datasource(Environment env) {
	DruidDataSourceBuilder builder= new DruidDataSourceBuilder();
	DruidDataSource build = builder.build(env, "");
	build.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
	build.setUrl(env.getProperty("spring.datasource.url"));
	build.setUsername(env.getProperty("spring.datasource.username"));
	build.setPassword(env.getProperty("spring.datasource.password"));
    return build;
  }
}
