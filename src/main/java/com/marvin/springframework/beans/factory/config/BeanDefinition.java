package com.marvin.springframework.beans.factory.config;

import com.marvin.springframework.beans.PropertyValues;

/**
 * @TODO: Bean的定义, 用来定义Bean的实例化信息
 * @author: dengbin
 * @create: 2023-06-27 18:44
 **/
public class BeanDefinition {

    private final Class<?> beanClass;

    // Bean对象需要填充的属性集合
    private final PropertyValues propertyValues;

    // 初始化Bean的方法
    private String initMethodName;

    // 销毁Bean的方法名字
    private String destroyMethodName;

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }

    /*
     * @Description: TODO 创建一个BeanDefinition对象
     * @Author: dengbin
     * @Date: 27/6/23 23:15
     * @param beanClass: 这个是Bean的类
     * @return: null
     **/
    public BeanDefinition(Class<?> beanClass) {
        this.beanClass = beanClass;
        this.propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class<?> beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues == null ? new PropertyValues() : propertyValues;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }
}
