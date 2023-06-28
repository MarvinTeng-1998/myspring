package com.marvin.springframework.beans;

/**
 * @TODO: 这是一个Bean对象属性的类 后续每读到一行xml或者是每读到一个注解则填充一个PropertyValue类。
 * @author: dengbin
 * @create: 2023-06-28 17:08
 **/
public class PropertyValue {
    private final String name;
    private final Object value;

    public PropertyValue(String name,Object value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
