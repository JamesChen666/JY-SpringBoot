package com.boot.service;

import java.util.List;


public interface BaseService  {

    Object selectById(Class<?> c, Integer id);

    List all(Class<?> c);
}
