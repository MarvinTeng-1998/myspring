package com.marvin.springframework.beans.factory.support;

import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * @TODO: 抽象方法，主要实现createBean的方法。
 * @author: dengbin
 * @create: 2023-06-27 23:17
 **/
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
    // 创建策略，关于实例化Bean对象的策略：JDK动态代理还是Cglib动态代理
    private final InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    /*
     * @Description: TODO 对createBean的实现，目前只考虑单例情况，将新创建的bean注入到singleton容器中。
     * @Author: dengbin
     * @Date: 27/6/23 23:20
     * @param beanName:
     * @param beanDefinition:
     * @return: java.lang.Object
     **/
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) {
        Object bean = null;
        try {
           bean = beanDefinition.getBeanClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BeansException("Instantiation of bean failed",e);
        }
        addSingleton(beanName,bean);
        return bean;
    }

    /*
     * @Description: TODO 创建Bean的方法，当Bean有参数需要入参时。
     * @Author: dengbin
     * @Date: 28/6/23 15:20
     * @param beanName:
     * @param beanDefinition:
     * @param args:  Bean的入参对象
     * @return: java.lang.Object
     **/
    @Override
    protected Object createBean(String beanName,BeanDefinition beanDefinition,Object[] args){
        Object bean = null;
        try {
            bean = createBeanInstance(beanDefinition,beanName,args);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed",e);
        }
        addSingleton(beanName,bean);
        return bean;
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition,String beanName,Object[] args){
        Constructor constructor = null;
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        for(Constructor<?> ctor : declaredConstructors){
            if(null != args && ctor.getParameterTypes().length == args.length){
                constructor = ctor;
                break;
            }
        }
        return instantiationStrategy.instantiate(beanDefinition,beanName,constructor,args);
    }
}
