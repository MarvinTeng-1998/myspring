package com.marvin.springframework.beans.factory.config;

import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.PropertyValue;
import com.marvin.springframework.beans.PropertyValues;

/**
 * @TODO: 专门用来在Bean对象执行init-method前执行这个方法。
 * @author: dengbin
 * @create: 2023-07-05 16:14
 **/
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
    /*
     * @Description: TODO
     * @Author: dengbin
     * @Date: 5/7/23 16:15
     * @param bean:
     * @param beanName:
     * @return: java.lang.Object
     **/
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException;

    PropertyValues postProcessPropertyValues(PropertyValues propertyValues, Object bean, String beanName) throws BeansException;

    default Object getEarlyBeanFactory(Object bean, String beanName){return bean;};
}
