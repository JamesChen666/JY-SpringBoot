package com.boot.util.excel.converter.UserConverter;

import com.boot.model.Campus;
import com.boot.util.excel.converter.DefaultConvertible;

public class CampusConvert extends DefaultConvertible {
    @Override
    public Object execRead(String object) {
        Campus campusName = sqlManager.query(Campus.class)
                .andEq("CampusName", object).single();
        return campusName.getCampusCode();
    }

    @Override
    public Object execWrite(Object object) {
        Campus campusName = sqlManager.query(Campus.class)
                .andEq("CampusCode", object).single();
        return campusName.getCampusName();
    }
}
