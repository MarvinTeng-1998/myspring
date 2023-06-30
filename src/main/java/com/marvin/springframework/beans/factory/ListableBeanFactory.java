package com.marvin.springframework.beans.factory;

import com.marvin.springframework.beans.BeansException;

import java.util.Map;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-06-30 15:12
 **/
public interface ListableBeanFactory extends BeanFactory{

    /*
     * @Description: TODO 根据类型返回Bean的承载容器，例如：BeanPostProcessor,BeanFactoryPostProcessor。可能多个，所以会放在Map集合中作为Bean
     * @Author: dengbin
     * @Date: 30/6/23 15:13
     * @param type:
     * @return: java.util.Map<java.lang.String,T>
     **/
    <T> Map<String,T> getBeansOfType(Class<T> type) throws BeansException;

    /*
     * @Description: TODO 返回注册表中所有的Bean的名称
     * @Author: dengbin
     * @Date: 30/6/23 15:13

     * @return: java.lang.String[]
     **/
    String[] getBeanDefinitionNames();
}
