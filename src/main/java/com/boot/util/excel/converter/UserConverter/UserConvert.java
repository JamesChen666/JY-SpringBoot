package com.boot.util.excel.converter.UserConverter;

import com.boot.model.Loginaccount;
import com.boot.util.excel.converter.DefaultConvertible;

public class UserConvert extends DefaultConvertible {
    @Override
    public Object execRead(String object) {
        Loginaccount realName = sqlManager.query(Loginaccount.class)
                .andEq("RealName", object).single();
        return realName.getId();
    }

    @Override
    public Object execWrite(Object object) {
        Loginaccount realName = sqlManager.query(Loginaccount.class)
                .andEq("Id", object).single();
        return realName.getRealName();
    }
}
