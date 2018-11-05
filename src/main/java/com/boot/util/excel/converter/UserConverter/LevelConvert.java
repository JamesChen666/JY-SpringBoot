package com.boot.util.excel.converter.UserConverter;

import com.boot.model.Dictionary;
import com.boot.util.excel.converter.DefaultConvertible;

public class LevelConvert extends DefaultConvertible {
    @Override
    public Object execRead(String object) {
        Dictionary  Name = sqlManager.query(Dictionary.class)
                .andEq("TypeCode","PYCCDM")
                .andEq("DisplayText", object)
                .single();
        return Name.getMemberValue();
    }

    @Override
    public Object execWrite(Object object) {
        Dictionary  Name = sqlManager.query(Dictionary.class)
                .andEq("TypeCode","PYCCDM")
                .andEq("MemberValue", object).single();
        return Name.getDisplayText();
    }
}
