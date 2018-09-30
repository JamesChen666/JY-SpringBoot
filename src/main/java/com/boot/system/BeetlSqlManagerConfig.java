package com.boot.system;

import org.beetl.sql.core.*;
import org.beetl.sql.core.db.DBStyle;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.ext.DebugInterceptor;
import org.beetl.sql.ext.SimpleCacheInterceptor;
import org.beetl.sql.ext.spring4.BeetlSqlDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;
/**
 * @author chenjiang
 */
@Configuration
@Lazy
public class BeetlSqlManagerConfig {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    BeetlSqlDataSource beetlSqlDataSource;

    @Primary
    @Bean("SQLManager")
    public SQLManager sqlManager() {
        DBStyle mysql = new MySqlStyle();
        // sql语句放在classpagth的/sql 目录下
        SQLLoader loader = new ClasspathLoader("/beetlsql");
        // 数据库命名跟java命名一样，所以采用DefaultNameConversion，还有一个是UnderlinedNameConversion，下划线风格的
        DefaultNameConversion nc = new  DefaultNameConversion();
        List<String> arrayList = new ArrayList<>();
        String[] beetlSqlCache = Constant.BEETLSQLCACHE;
        for (String cache : beetlSqlCache) {
            arrayList.add(cache);
        }
        SimpleCacheInterceptor cache =new SimpleCacheInterceptor(arrayList);
        Interceptor[] inters = new Interceptor[]{ new DebugInterceptor(),cache};
        SQLManager sql = new SQLManager(mysql,loader, beetlSqlDataSource,nc, inters);
        return sql;
    }


}
