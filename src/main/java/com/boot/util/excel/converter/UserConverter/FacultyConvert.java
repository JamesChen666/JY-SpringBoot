package com.boot.util.excel.converter.UserConverter;

import com.boot.model.Faculty;
import com.boot.util.excel.converter.DefaultConvertible;

public class FacultyConvert extends DefaultConvertible {
    @Override
    public Object execRead(String object) {
        Faculty facultyName = sqlManager.query(Faculty.class)
                .andEq("FacultyName", object).single();
        return facultyName.getFacultyCode();
    }

    @Override
    public Object execWrite(Object object) {
        Faculty facultyName = sqlManager.query(Faculty.class)
                .andEq("FacultyCode", object).single();
        return facultyName.getFacultyName();
    }
}
