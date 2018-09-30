package com.boot.util.excel.converter.UserConverter;

import com.boot.util.excel.converter.DefaultConvertible;

public class WhetherConvert extends DefaultConvertible {

    @Override
    public Object execRead(String object) {
        return object.equals("是");
    }

    @Override
    public Object execWrite(Object object) {
        String toString = object.toString();
        return toString.equals("true") ? "是" : "否";
    }
}
