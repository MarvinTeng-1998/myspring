package com.marvin.springframework.beans.factory.support;

import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.factory.config.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-06-27 23:24
 **/
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry{
    // 用来存放BeanDefinition的容器
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    /*
     * @Description: TODO 获取BeanDefinition
     * @Author: dengbin
     * @Date: 27/6/23 23:27
     * @param beanName:
     * @return: com.marvin.springframework.beans.factory.config.BeanDefinition
     **/
    @Override
    protected BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if(beanDefinition == null) {
            throw new BeansException("No bean named '" + beanName + "' is defined");
        }
        return beanDefinition;
    }

    /*
     * @Description: TODO 将beanDefinition注册到beanDefinitionMap中。
     * @Author: dengbin
     * @Date: 27/6/23 23:27
     * @param beanName:
     * @param beanDefinition:
     * @return: void
     **/
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName,beanDefinition);
    }
}
