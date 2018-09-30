package com.boot.util;
/**
 * @author chenjiang
 */
public class JavaAttribute {

    private  Object type;
    private  String name;

    public JavaAttribute(Object type, String name) {
        this.type = type;
        this.name = name;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
