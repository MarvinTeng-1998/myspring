package com.marvin.processor;

import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.PropertyValue;
import com.marvin.springframework.beans.PropertyValues;
import com.marvin.springframework.beans.factory.BeanFactory;
import com.marvin.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.marvin.springframework.beans.factory.config.BeanDefinition;
import com.marvin.springframework.beans.factory.config.BeanFactoryPostProcessor;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-06-30 16:52
 **/
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition("userService");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("company","改成：字节跳动"));
    }
}
