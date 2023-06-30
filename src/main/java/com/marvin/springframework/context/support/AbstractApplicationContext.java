package com.marvin.springframework.context.support;

import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.factory.BeanFactory;
import com.marvin.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.marvin.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.marvin.springframework.beans.factory.config.BeanPostProcessor;
import com.marvin.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.marvin.springframework.context.ConfigurableApplicationContext;
import com.marvin.springframework.core.io.DefaultResourceLoader;

import java.util.Map;

/**
 * @TODO: 这是一个ApplicationContext的抽象类，实现了refresh这个方法，定义了上下文刷新的流程。
 * @author: dengbin
 * @create: 2023-06-30 15:37
 **/
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
    @Override
    public void refresh() throws BeansException {
        // 1. 创建BeanFactory，并且加载BeanDefinition
        refreshBeanFactory();

        // 2. 获取BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 3. 在实例化Bean之前，执行BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        // 4. BeanPostProcess 需要提前其他Bean实例化操作之前进行注册操作
        registerBeanPostProcessor(beanFactory);

        // 5. 提前实例化单例Bean对象
        beanFactory.preInstantiateSingletons();

    }

    protected abstract void refreshBeanFactory() throws BeansException;

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    /*
     * @Description: TODO 在修改BeanDefinition的工厂中对BeanDefinition的修改处理器逐一执行。
     * @Author: dengbin
     * @Date: 30/6/23 15:44
     * @param beanFactory:
     * @return: void
     **/
    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory){
        // 获取Bean容器中的Map，这个Map存放着所有BeanFactoryPostProcessor
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for(BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()){
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    /*
     * @Description: TODO 注册所有的BeanPostProcessor
     * @Author: dengbin
     * @Date: 30/6/23 15:45
     * @param beanFactory:
     * @return: void
     **/
    private void registerBeanPostProcessor(ConfigurableListableBeanFactory beanFactory){

        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for(BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()){
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }

    }

    @Override
    public Object getBean(String beanName) {
        return getBeanFactory().getBean(beanName);
    }

    @Override
    public Object getBean(String beanName, Object... args) {
        return getBeanFactory().getBean(beanName,args);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(beanName, requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }
}
