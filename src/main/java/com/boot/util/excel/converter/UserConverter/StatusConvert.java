package com.boot.util.excel.converter.UserConverter;

import com.boot.util.excel.converter.DefaultConvertible;

public class StatusConvert extends DefaultConvertible {

    @Override
    public Object execWrite(Object object) {
        if (object.toString().equals("0") || Integer.valueOf(object.toString()) == 0) {
            return "待审核";
        } else if (object.toString().equals("1") || Integer.valueOf(object.toString()) == 1) {
            return "已审核";
        } else {
            return "审核拒绝";
        }
    }
}
