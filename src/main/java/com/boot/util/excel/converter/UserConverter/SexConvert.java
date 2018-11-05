package com.boot.util.excel.converter.UserConverter;

import com.boot.util.excel.converter.DefaultConvertible;

public class SexConvert extends DefaultConvertible {
    @Override
    public Object execRead(String object) {
       if (object.equals("男")){
           return 1;
       }else {
           return 0;
       }
    }

    @Override
    public Object execWrite(Object object) {
        if (object.toString().equals("0")||Integer.valueOf(object.toString())==0){
            return "男";
        }else {
            return "女";
        }
    }
}
