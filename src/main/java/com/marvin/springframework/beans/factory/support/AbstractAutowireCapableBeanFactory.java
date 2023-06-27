package com.marvin.springframework.beans.factory.support;

import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.factory.config.BeanDefinition;

/**
 * @TODO: 抽象方法，主要实现createBean的方法。
 * @author: dengbin
 * @create: 2023-06-27 23:17
 **/
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    /*
     * @Description: TODO 对createBean的实现，目前只考虑单例情况，将新创建的bean注入到singleton容器中。
     * @Author: dengbin
     * @Date: 27/6/23 23:20
     * @param beanName:
     * @param beanDefinition:
     * @return: java.lang.Object
     **/
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) {
        Object bean = null;
        try {
           bean = beanDefinition.getBeanClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BeansException("Instantiation of bean failed",e);
        }
        addSingleton(beanName,bean);
        return bean;
    }
}
