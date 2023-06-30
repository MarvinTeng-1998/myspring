package com.marvin.springframework.beans.factory.support;

import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.core.io.Resource;
import com.marvin.springframework.core.io.ResourceLoader;

/**
 * @TODO: BeanDefinition的读取接口
 * @author: dengbin
 * @create: 2023-06-28 18:29
 **/
public interface BeanDefinitionReader {
    /*
     * @Description: TODO 获取所有的BeanDefinitionRegistry,实际上就是获取BeanDefinition容器并且能够给BeanDefinition注册到容器中
     * @Author: dengbin
     * @Date: 28/6/23 18:29

     * @return: com.marvin.springframework.beans.factory.support.BeanDefinitionRegistry
     **/
    BeanDefinitionRegistry getRegistry();

    /*
     * @Description: TODO 获取资源文件加载器
     * @Author: dengbin
     * @Date: 28/6/23 18:30

     * @return: com.marvin.springframework.core.io.ResourceLoader
     **/
    ResourceLoader getResourceLoader();

    /*
     * @Description: TODO 根据Resource类型加载BeanDefinition
     * @Author: dengbin
     * @Date: 28/6/23 18:31
     * @param resource:
     * @return: void
     **/
    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(Resource... resources) throws BeansException;

    void loadBeanDefinitions(String location) throws BeansException;

    void loadBeanDefinitions(String[] locations) throws BeansException;

}
