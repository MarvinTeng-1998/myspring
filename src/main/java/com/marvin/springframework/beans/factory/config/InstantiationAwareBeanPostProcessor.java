package com.marvin.springframework.beans.factory.config;

import com.marvin.springframework.beans.BeansException;

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
    public Object postProcessBeforeInitialization(Class<?> beanClass, String beanName) throws BeansException;
}