package com.boot.util.excel.converter.UserConverter;

import cn.hutool.core.util.StrUtil;
import com.boot.model.Menu;
import com.boot.util.excel.converter.DefaultConvertible;

public class MenuConvert extends DefaultConvertible {

    @Override
    public Object execWrite(Object object) {
        if (object.toString().equals("0")){
            return null;
        }else {
            Menu menu = sqlManager.single(Menu.class, object);
            return menu.getMenuName();
        }
    }

    @Override
    public Object execRead(String object) {
        if (StrUtil.hasEmpty(object)){
            return 0;
        }else {
            Menu menu = sqlManager.query(Menu.class)
                    .andEq("MenuName", object).single();
            return menu.getId();
        }
    }
}
