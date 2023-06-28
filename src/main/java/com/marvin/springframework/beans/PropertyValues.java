package com.marvin.springframework.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-06-28 17:11
 **/
public class PropertyValues {
    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    /*
     * @Description: TODO 添加一个PropertyValue
     * @Author: dengbin
     * @Date: 28/6/23 17:12
     * @param propertyValue:
     * @return: void
     **/
    public void addPropertyValue(PropertyValue propertyValue) {
        propertyValueList.add(propertyValue);
    }

    /*
     * @Description: TODO 返回一个PropertyValue的数组
     * @Author: dengbin
     * @Date: 28/6/23 17:12

     * @return: com.marvin.springframework.beans.PropertyValue[]
     **/
    public PropertyValue[] getPropertyValues() {
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    /*
     * @Description: TODO 根据PropertyValue的名字获取值
     * @Author: dengbin
     * @Date: 28/6/23 17:14
     * @param name:
     * @return: com.marvin.springframework.beans.PropertyValue
     **/
    public PropertyValue getProperty(String name) {
        for (PropertyValue propertyValue : propertyValueList) {
            if (propertyValue.getName().equals(name)) {
                return propertyValue;
            }
        }
        return null;
    }
}
