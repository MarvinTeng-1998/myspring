package com.marvin.springframework.beans.factory.config;

import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.factory.ConfigurableListableBeanFactory;

/**
 * @TODO: 一个在Bean实例化之前对BeanDefinition进行调整的接口，用户如果需要在Bean定义之后，可以继承这个接口来调整BeanDefinition。
 * @author: dengbin
 * @create: 2023-06-30 15:08
 **/
public interface BeanFactoryPostProcessor {

    /*
     * @Description: TODO 在所有的BeanDefinition加载完成之后，实例化Bean之前，提供修改BeanDefinition属性的机制。
     *                也就是说，这个接口是在BeanDefinition加载完成后，实例化Bean对象之前，对BeanDefinition修改。
     * @Author: dengbin
     * @Date: 30/6/23 15:26
     * @param configurableListableBeanFactory:
     * @return: void
     **/
    void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException;
}
