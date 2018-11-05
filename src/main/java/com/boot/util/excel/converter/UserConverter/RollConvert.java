package com.boot.util.excel.converter.UserConverter;

import com.boot.util.DictionaryType;
import com.boot.util.excel.converter.DefaultConvertible;

public class RollConvert extends DefaultConvertible {
    @Override
    public Object execRead(String object) {
        //如果是导入
        if (object.equals(DictionaryType.升学.getName())) {
            return DictionaryType.升学.getValue();
        }else {
            return DictionaryType.复学.getValue();
        }
    }

    @Override
    public Object execWrite(Object object) {
        Integer integer = Integer.valueOf(object.toString());
        //如果是导入
        if (integer==DictionaryType.升学.getValue()) {
            return DictionaryType.升学.getName();
        }else {
            return DictionaryType.复学.getName();
        }
    }
}
