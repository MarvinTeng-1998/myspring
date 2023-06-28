package com.marvin.springframework.beans.factory.support;

import com.marvin.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * @TODO: 一个用来决定Bean实例化策略的接口：Java的Default实例化方式或者是Cglib
 * @author: dengbin
 * @create: 2023-06-28 15:23
 **/
public interface InstantiationStrategy {

    /*
     * @Description: TODO 一个实例化Bean对象的方法
     * @Author: dengbin
     * @Date: 28/6/23 15:25
     * @param beanDefinition: Bean的定义方式
     * @param beanName: Bean的名字
     * @param constructor: Bean的带参数构造器
     * @param args: Bean实例化需要的参数
     * @return: java.lang.Object
     **/
    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor<Object> constructor,Object[] args);

}
