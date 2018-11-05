package com.boot.util.excel.converter.UserConverter;

import com.boot.model.Dictionary;
import com.boot.util.excel.converter.DefaultConvertible;

public class MajorLanguageConvert extends DefaultConvertible {
    @Override
    public Object execRead(String object) {
        Dictionary Name = sqlManager.query(Dictionary.class)
                .andEq("TypeCode","ZXWYYZDM")
                .andEq("DisplayText", object)
                .single();
        return Name.getMemberValue();
    }

    @Override
    public Object execWrite(Object object) {
        Dictionary  Name = sqlManager.query(Dictionary.class)
                .andEq("TypeCode","ZXWYYZDM")
                .andEq("MemberValue", object).single();
        return Name.getDisplayText();
    }
}
