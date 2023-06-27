package com.marvin.springframework.beans.factory.support;

import com.marvin.springframework.beans.factory.config.BeanDefinition;

/**
 * @TODO: 接口：抽象BeanDefinition的注册
 * @author: dengbin
 * @create: 2023-06-27 23:22
 **/
public interface BeanDefinitionRegistry {
    /*
     * @Description: TODO 用来注册beanDefinition到容器中。
     * @Author: dengbin
     * @Date: 27/6/23 23:23
     * @param beanName:
     * @param beanDefinition:
     * @return: void
     **/
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
