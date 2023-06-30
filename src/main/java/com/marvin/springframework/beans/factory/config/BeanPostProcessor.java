package com.marvin.springframework.beans.factory.config;

import com.marvin.springframework.beans.BeansException;

/**
 * @TODO: 用来修改实例化Bean对象的拓展接口
 * @author: dengbin
 * @create: 2023-06-30 15:23
 **/
public interface BeanPostProcessor {
    /*
     * @Description: TODO 在Bean实例化之前的处理
     * @Author: dengbin
     * @Date: 30/6/23 15:24
     * @param bean:
     * @param beanName:
     * @return: java.lang.Object
     **/
    Object postProcessBeforeInitialization(Object bean,String beanName) throws BeansException;

    /*
     * @Description: TODO 在Bean实例化之后的处理
     * @Author: dengbin
     * @Date: 30/6/23 15:25
     * @param bean:
     * @param beanName:
     * @return: java.lang.Object
     **/
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
