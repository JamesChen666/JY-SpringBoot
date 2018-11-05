package com.boot.util.excel.converter.UserConverter;

import com.boot.model.Dictionary;
import com.boot.util.excel.converter.DefaultConvertible;

public class SubsistenceConvert extends DefaultConvertible {
    @Override
    public Object execRead(String object) {
        Dictionary Name = sqlManager.query(Dictionary.class)
                .andEq("TypeCode","CXJMDBJTLXDM")
                .andEq("DisplayText", object)
                .single();
        return Name.getMemberValue();
    }

    @Override
    public Object execWrite(Object object) {
        Dictionary  Name = sqlManager.query(Dictionary.class)
                .andEq("TypeCode","CXJMDBJTLXDM")
                .andEq("MemberValue", object).single();
        return Name.getDisplayText();
    }
}
