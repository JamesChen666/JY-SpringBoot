package com.boot.util;

public class PublicClass {

    /**
     * 验证参数是否为空
     * @param value ：参数值
     * @return true:为空，false:不为空
     */
    public  static boolean isNull(String value){
        if(value == null || "".equals(value.trim()) || "null".equals(value.trim())){
            return true;
        }
        return false;
    }
}
