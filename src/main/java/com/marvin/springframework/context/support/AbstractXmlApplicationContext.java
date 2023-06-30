package com.marvin.springframework.context.support;

import com.marvin.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.marvin.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @TODO: 这个上下文主要是用来加载BeanDefinition的
 * @author: dengbin
 * @create: 2023-06-30 16:03
 **/
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext{

    /*
     * @Description: TODO 从配置文件中读取beanDefinition的内容，然后传入到beanFactory中的BeanDefinition容器中
     * @Author: dengbin
     * @Date: 30/6/23 16:09
     * @param beanFactory:
     * @return: void
     **/
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory,this);
        String[] configLocations = getConfigLocations();
        if(null != configLocations){
            beanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    protected abstract String[] getConfigLocations();
}
