package com.boot.system;

public class SqlIntercepter {

    private String appendSql;

    public SqlIntercepter() {
    }

    public static  SqlIntercepter create() {
        return new SqlIntercepter();
    }

    public static SqlIntercepter set(String appendSql){
        SqlIntercepter sqlIntercepter = create();
        sqlIntercepter.setAppendSql(appendSql);
        return sqlIntercepter;
    }

    public String getAppendSql() {
        return appendSql;
    }

    public void setAppendSql(String appendSql) {
        this.appendSql = appendSql;
    }
}
