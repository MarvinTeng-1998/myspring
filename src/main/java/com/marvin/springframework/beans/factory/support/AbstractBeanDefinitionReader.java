package com.marvin.springframework.beans.factory.support;

import com.marvin.springframework.core.io.DefaultResourceLoader;
import com.marvin.springframework.core.io.ResourceLoader;

/**
 * @TODO: 这里主要实例化了registry和resourceLoader这两个类，一个将来将读取到的Bean内容注册到BeanDefinition中，一个将来用来读取XML文件
 * @author: dengbin
 * @create: 2023-06-28 18:32
 **/
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader{

    private final BeanDefinitionRegistry registry;

    private ResourceLoader resourceLoader;

    /*
     * @Description: TODO 将BeanDefinitionRegistry和ResourceLoader加载到这个BeanDefinitionReader中，默认使用DefaultBeanDefinitionReader
     * @Author: dengbin
     * @Date: 28/6/23 18:34
     * @param registry:
     * @return: null
     **/
    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry){
        this(registry,new DefaultResourceLoader());
    }

    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader){
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }


}
