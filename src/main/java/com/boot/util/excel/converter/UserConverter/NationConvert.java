package com.boot.util.excel.converter.UserConverter;

import com.boot.util.excel.converter.DefaultConvertible;

public class NationConvert extends DefaultConvertible {

    @Override
    public Object execWrite(Object object) {
        return "123";
    }

    @Override
    public Object execRead(String object) {
        throw new UnsupportedOperationException();
    }
}
