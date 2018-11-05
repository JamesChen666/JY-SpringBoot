package com.boot.util.excel.converter.UserConverter;

import com.boot.model.Area;
import com.boot.util.excel.converter.DefaultConvertible;

public class AreaConvert extends DefaultConvertible {

    @Override
    public Object execRead(String object) {
        if (object.equals("0")||Integer.valueOf(object)==0||object==null){
            return 0;
        }else {
            Area areaName = sqlManager.query(Area.class)
                    .andEq("AreaName", object).single();
            return areaName.getId();
        }
    }
    @Override
    public Object execWrite(Object object) {
        if (object.equals("0")||Integer.valueOf(object.toString())==0||object==null){
            return "无";
        }else {
            Area areaName = sqlManager.single(Area.class, object);
            return areaName.getAreaName();
        }
    }

}
