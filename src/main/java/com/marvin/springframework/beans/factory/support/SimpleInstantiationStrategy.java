package com.marvin.springframework.beans.factory.support;

import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @TODO: 使用JavaJDk的方式来对Bean进行实例化
 * @author: dengbin
 * @create: 2023-06-28 15:26
 **/
public class SimpleInstantiationStrategy implements InstantiationStrategy {
    /*
     * @Description: TODO 使用Java的Jdk来对Bean对象进行实例化
     * @Author: dengbin
     * @Date: 28/6/23 15:26
     * @param beanDefinition:
     * @param beanName:
     * @param constructor:
     * @param args:
     * @return: java.lang.Object
     **/
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor<Object> constructor, Object[] args) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        try {
            if (null != constructor) {
                return beanClass.getDeclaredConstructor(constructor.getParameterTypes()).newInstance(args);
            } else {
                return beanClass.getDeclaredConstructor().newInstance();
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new BeansException("Failed to instantiate [" + beanClass.getName() + "]", e);
        }
    }
}
