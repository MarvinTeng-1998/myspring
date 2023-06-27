package com.marvin.springframework.beans.factory.config;

/**
 * @TODO: Bean的定义,用来定义Bean的实例化信息
 * @author: dengbin
 * @create: 2023-06-27 18:44
 **/
public class BeanDefinition {

    private final Class<?> beanClass;

    /*
     * @Description: TODO 创建一个BeanDefinition对象
     * @Author: dengbin
     * @Date: 27/6/23 23:15
     * @param beanClass: 这个是Bean的类
     * @return: null
     **/
    public BeanDefinition(Class<?> beanClass){
        this.beanClass = beanClass;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }
}
