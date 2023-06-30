package com.marvin.springframework.beans.factory;

import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.marvin.springframework.beans.factory.config.BeanDefinition;
import com.marvin.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * @TODO: 一个可以配置调整BeanFactory中BeanDefinition的类
 * @author: dengbin
 * @create: 2023-06-30 15:10
 **/
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {
    /*
     * @Description: TODO 获取BeanDefinition
     * @Author: dengbin
     * @Date: 30/6/23 15:28
     * @param beanName:
     * @return: com.marvin.springframework.beans.factory.config.BeanDefinition
     **/
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    void preInstantiateSingletons() throws BeansException;


}
