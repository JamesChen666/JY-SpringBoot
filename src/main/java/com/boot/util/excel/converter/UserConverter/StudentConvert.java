package com.boot.util.excel.converter.UserConverter;

import com.boot.model.Student;
import com.boot.util.excel.converter.DefaultConvertible;

public class StudentConvert extends DefaultConvertible {
    @Override
    public Object execRead(String object) {
        Student Name = sqlManager.query(Student.class)
                .andEq("StudentNumber", object).single();
        return Name.getId();
    }

    @Override
    public Object execWrite(Object object) {
        Student Name = sqlManager.query(Student.class)
                .andEq("Id", object).single();
        return Name.getStudentNumber();
    }
}
