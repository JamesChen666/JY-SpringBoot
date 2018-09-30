package com.boot.util.excel.converter.UserConverter;

import com.boot.util.DictionaryType;
import com.boot.util.excel.converter.DefaultConvertible;

public class UserTypeConvert extends DefaultConvertible {
    @Override
    public Object execRead(String object) {
        //如果是导入
        if (object.equals(DictionaryType.管理员.getName())) {
            return DictionaryType.管理员.getValue();
        } else if (object.equals(DictionaryType.教师.getName())) {
            return DictionaryType.教师.getValue();
        } else if (object.equals(DictionaryType.学生.getName())) {
            return DictionaryType.学生.getValue();
        } else if (object.equals(DictionaryType.单位.getName())){
            return DictionaryType.单位.getValue();
        }else {
            return object;
        }
    }

    @Override
    public Object execWrite(Object object) {
        Integer integer = Integer.valueOf(object.toString());
        //如果是导入
        if (integer==DictionaryType.管理员.getValue()) {
            return DictionaryType.管理员.getName();
        } else if (integer==DictionaryType.教师.getValue()) {
            return DictionaryType.教师.getName();
        } else if (integer==DictionaryType.学生.getValue()) {
            return DictionaryType.学生.getName();
        } else if (integer==DictionaryType.单位.getValue()){
            return DictionaryType.单位.getName();
        }else {
            return object;
        }
    }
}
