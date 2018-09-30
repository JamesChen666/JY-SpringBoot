package com.boot.util.excel.converter.UserConverter;

import cn.hutool.core.date.DateUtil;
import com.boot.util.excel.converter.DefaultConvertible;
import com.boot.util.excel.utils.DateUtils;

import java.util.Date;

public class DateConvert extends DefaultConvertible {

    @Override
    public Object execWrite(Object object) {
        Date date = (Date) object;
        return DateUtils.date2Str(date, DateUtils.DATE_FORMAT_SEC);
    }

    @Override
    public Object execRead(String object) {
       return DateUtil.parse(object);
    }
}
