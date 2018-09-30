package com.boot.util;

import cn.hutool.core.lang.Dict;

import java.util.Map;

public enum DictionaryType {
    管理员("管理员", 1),
    教师("教师", 2),
    学生("学生", 3),
    单位("单位", 4),
    升学("升学", 1),
    复学("复学", 2),
    学院("学院", 1),
    系部("系部", 2);

    String name;
    Object value;

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public Map getMap() {
        Dict dict = Dict.create()
                .set("name", name)
                .set("value", value);
        return dict;
    }

    DictionaryType(String name, Object value) {
        this.name = name;
        this.value = value;
    }
}
