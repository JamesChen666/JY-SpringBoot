package com.boot.util.excel.converter.UserConverter;

import com.boot.model.Specialty;
import com.boot.util.excel.converter.DefaultConvertible;

public class SpecialtyConvert extends DefaultConvertible {
    @Override
    public Object execRead(String object) {
        Specialty  Name = sqlManager.query(Specialty.class)
                .andEq("SpecialtyName", object).single();
        return Name.getSpecialtyCode();
    }

    @Override
    public Object execWrite(Object object) {
        Specialty    Name = sqlManager.query(Specialty.class)
                .andEq("SpecialtyCode", object).single();
        return Name.getSpecialtyName();
    }
}
