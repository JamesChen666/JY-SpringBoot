package com.boot.service.impl;

import com.boot.service.BaseService;
import org.beetl.sql.core.SQLManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BaseServiceImpl implements BaseService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    SQLManager SQLManager;

    @Override
    public Object selectById(Class<?> entity, Integer id) {
        Object single =SQLManager.single(entity, id);
        return single;
    }

    @Override
    public List all(Class<?> c) {
        List<?> all = SQLManager.all(c);
        return all;
    }
}
