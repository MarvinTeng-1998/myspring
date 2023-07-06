package com.marvin.springframework.beans.factory.config;

import com.marvin.springframework.beans.factory.HierarchicalBeanFactory;
import com.marvin.springframework.util.StringValueResolver;

/**
 * @TODO:这是一个用来设置类的属性的类，同时还可以添加Bean实例化后的处理器。
 * @author: dengbin
 * @create: 2023-06-30 15:21
 **/
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry{
    // 标识这是一个单例类
    String SCOPE_SINGLETON = "singleton";
    // 标识这是一个原型类
    String SCOPE_PROTOTYPE = "prototype";

    /*
     * @Description: TODO 将Bean实例化的PostProcessor添加在这里
     * @Author: dengbin
     * @Date: 30/6/23 15:25
     * @param beanPostProcessor: 包含两个处理器，一个是实例化前的处理器，一个是实例化之后的处理器
     * @return: void
     **/
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    void addEmbeddedValueResolver(StringValueResolver valueResolver);

    String resolveEmbeddedValue(String value);
}
