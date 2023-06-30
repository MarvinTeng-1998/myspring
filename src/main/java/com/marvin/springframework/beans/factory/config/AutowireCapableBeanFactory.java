package com.marvin.springframework.beans.factory.config;

import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.factory.BeanFactory;

/**
 * @TODO:一个可以操作BeanFactory中的Bean类，在Bean实例化之前可以处理注入相关的属性。
 * @author: dengbin
 * @create: 2023-06-30 15:16
 **/
public interface AutowireCapableBeanFactory extends BeanFactory {
    /*
     * @Description: TODO 执行BeanPostProcessor的实例化前的修改Bean的方法
     * @Author: dengbin
     * @Date: 30/6/23 15:17
     * @param existingBean:
     * @param beanName:
     * @return: java.lang.Object
     **/
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    /*
     * @Description: TODO 执行BeanPostProcessor的实例化后的修改Bean的方法
     * @Author: dengbin
     * @Date: 30/6/23 15:17
     * @param existingBean:
     * @param beanName:
     * @return: java.lang.Object
     **/
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;

}
