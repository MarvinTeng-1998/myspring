package com.marvin.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.PropertyValue;
import com.marvin.springframework.beans.PropertyValues;
import com.marvin.springframework.beans.factory.config.BeanDefinition;
import com.marvin.springframework.beans.factory.config.BeanReference;

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
           applyPropertyValues(beanName,bean,beanDefinition);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BeansException("Instantiation of bean failed",e);
        }
        addSingleton(beanName,bean);
        return bean;
    }

    /*
     * @Description: TODO 给创建好的Bean注入PropertyValue
     * @Author: dengbin
     * @Date: 28/6/23 17:17
     * @param beanName:
     * @param bean:
     * @param beanDefinition:
     * @return: void
     **/
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition){
        try {
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            for(PropertyValue propertyValue : propertyValues.getPropertyValues()){
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();

                // 这里表示如果这个属性是BeanReference 也就是是一个引用数据类型的话，我们需要去从Spring容器中找到这个引用数据类型。然后注入进去。
                if(value instanceof BeanReference){
                    value = getBean(((BeanReference) value).getBeanName());
                }
                BeanUtil.setFieldValue(bean,name,value);
            }
        } catch (BeansException e) {
            throw new BeansException("Error setting property values: " + beanName);
        }
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
