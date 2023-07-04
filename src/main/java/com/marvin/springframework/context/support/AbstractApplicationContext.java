package com.marvin.springframework.context.support;

import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.factory.BeanFactory;
import com.marvin.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.marvin.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.marvin.springframework.beans.factory.config.BeanPostProcessor;
import com.marvin.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.marvin.springframework.context.ApplicationEvent;
import com.marvin.springframework.context.ApplicationListener;
import com.marvin.springframework.context.ConfigurableApplicationContext;
import com.marvin.springframework.context.event.ApplicationEventMulticaster;
import com.marvin.springframework.context.event.ContextClosedEvent;
import com.marvin.springframework.context.event.ContextRefreshEvent;
import com.marvin.springframework.context.event.SimpleApplicationEventMulticaster;
import com.marvin.springframework.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

/**
 * @TODO: 这是一个ApplicationContext的抽象类，实现了refresh这个方法，定义了上下文刷新的流程。
 * @author: dengbin
 * @create: 2023-06-30 15:37
 **/
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
    // 定义广播器的名字为常量
    public static final String APPLICACATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;
    @Override
    public void refresh() throws BeansException {
        // 1. 创建BeanFactory，并且加载BeanDefinition
        refreshBeanFactory();

        // 2. 获取BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 3. 添加ApplicationContextAwareProcessor ，让继承自ApplicationContextAware的Bean对象都能感知到所属的ApplicationContext
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        // 4. 在实例化Bean之前，执行BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        // 5. BeanPostProcess 需要提前其他Bean实例化操作之前进行注册操作
        registerBeanPostProcessor(beanFactory);



        // 6. 初始化事件发布者
        initApplicationEventMulticaster();

        // 7. 注册事件监听器
        registerListeners();

        // 8. 提前实例化单例Bean对象
        beanFactory.preInstantiateSingletons();

        // 9.发布容器刷新完成事件
        finishRefresh();
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

    /*
     * @Description: TODO 这是一个关闭的时候的钩子函数，会去新创建一个线程去销毁所有的singletons资源
     * @Author: dengbin
     * @Date: 30/6/23 23:33

     * @return: void
     **/
    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        publishEvent(new ContextClosedEvent(this));
        getBeanFactory().destroySingletons();
    }

    /*
     * @Description: TODO 初始化事件广播器，提前注册这个容器
     * @Author: dengbin
     * @Date: 4/7/23 15:24

     * @return: void
     **/
    private void initApplicationEventMulticaster(){
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        // 创建一个事件广播器Bean，交给Spring容器来管理。
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        // 将事件广播器给注册到Singleton容器中
        beanFactory.addSingleton(APPLICACATION_EVENT_MULTICASTER_BEAN_NAME,applicationEventMulticaster);
    }

    /*
     * @Description: TODO 将所有的事件监听器注册到容器中
     * @Author: dengbin
     * @Date: 4/7/23 15:26

     * @return: void
     **/
    private void registerListeners(){
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for(ApplicationListener listener : applicationListeners){
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    private void finishRefresh(){
        publishEvent(new ContextRefreshEvent(this));
    }
    @Override
    public void publishEvent(ApplicationEvent event){
        applicationEventMulticaster.multicastEvent(event);
    }
}
