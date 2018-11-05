package com.boot.util.excel.converter.UserConverter;

import com.boot.model.Field;
import com.boot.util.excel.converter.DefaultConvertible;

public class FieldConvert extends DefaultConvertible {
    @Override
    public Object execRead(String object) {
        Field Name = sqlManager.query(Field.class)
                .andEq("FieldCode", object).single();
        return Name.getId();
    }

    @Override
    public Object execWrite(Object object) {
        Field Name = sqlManager.query(Field.class)
                .andEq("Id", object).single();
        return Name.getFieldCode();
    }
}