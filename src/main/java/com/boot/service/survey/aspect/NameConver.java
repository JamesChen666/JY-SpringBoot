package com.boot.service.survey.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.UnderlinedNameConversion;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 改变sqlmanager默认命名风格为驼峰风格
 */
@Aspect
@Component
public class NameConver {
    protected static org.slf4j.Logger logger = LoggerFactory.getLogger(NameConver.class);

    @Autowired
    SQLManager SQLManager;

    @Before("execution(* com.boot.service.survey.*.*(..))")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        //logger.info("进入方法 " );
        SQLManager.setNc(new UnderlinedNameConversion());
    }

    @After( "execution(* com.boot.service.survey.*.*(..))")
    public void doAfterReturning() throws Throwable {
        //logger.info("离开方法 " );
        SQLManager.setNc(new UnderlinedNameConversion());
    }


}
