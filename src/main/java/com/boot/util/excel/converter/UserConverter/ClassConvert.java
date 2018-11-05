package com.boot.util.excel.converter.UserConverter;

import com.boot.model.Class;
import com.boot.util.excel.converter.DefaultConvertible;

public class ClassConvert extends DefaultConvertible {
    @Override
    public Object execRead(String object) {
        Class Name = sqlManager.query(Class.class)
                .andEq("ClassName", object).single();
        return Name.getClassNo();
    }

    @Override
    public Object execWrite(Object object) {
        Class Name = sqlManager.query(Class.class)
                .andEq("ClassNo", object).single();
        return Name.getClassName();
    }
}
