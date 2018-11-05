package com.boot;

import com.boot.system.ToHtmlServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableCaching
@EnableAspectJAutoProxy
@ServletComponentScan
public class Application extends SpringBootServletInitializer{
	//启动应用
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	//配置打包部署启动应用
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

	@Bean
	public ServletRegistrationBean toHtml(){
		return new ServletRegistrationBean(new ToHtmlServlet(),"/toHtml");
	}

}
