package com.marvin.springframework.beans.factory.support;

import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.factory.BeanFactory;
import com.marvin.springframework.beans.factory.config.BeanDefinition;
import com.marvin.springframework.beans.factory.config.SingletonBeanRegistry;
import com.marvin.springframework.beans.factory.support.DefaultSingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @TODO: Bean对象的工厂，可以存放BeanDefinition到Map中以及获取。
 * @author: dengbin
 * @create: 2023-06-27 18:45
 **/
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    /*
     * @Description: TODO 用来获取Bean对象的方法，这里用来获取Bean实例。
     * @Author: dengbin
     * @Date: 27/6/23 18:50
     * @param beanName: Bean的名字
     * @return: java.lang.Object
     **/
    @Override
    public Object getBean(String beanName) throws BeansException {
        Object singleton = getSingleton(beanName);
        if (singleton != null) {
            return singleton;
        }

        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        return createBean(beanName, beanDefinition);
    }

    /*
     * @Description: TODO 对具有参数的Bean进行构造
     * @Author: dengbin
     * @Date: 28/6/23 15:18
     * @param beanName:
     * @param args:
     * @return: java.lang.Object
     **/
    @Override
    public Object getBean(String beanName, Object... args) {
        Object singleton = getSingleton(beanName);
        if(singleton != null){
            return singleton;
        }
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        return createBean(beanName,beanDefinition,args);
    }

    /*
     * @Description: TODO 主要是获取BeanDefinition，也就是说我们把BeanDefinition的容器放到抽象工厂的子类下去了，使用了模版模式。
     * @Author: dengbin
     * @Date: 27/6/23 23:05
     * @param beanName: Bean在容器中的名字
     * @return: com.marvin.springframework.beans.factory.config.BeanDefinition
     **/
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /*
     * @Description: TODO 创建一个Bean，当我们发现我们Singleton容器中没有Bean时，调用这个方法去创建一个Bean
     * @Author: dengbin
     * @Date: 27/6/23 23:06
     * @param beanName: Bean在容器中的名字
     * @param beanDefinition: Bean的定义
     * @return: java.lang.Object
     **/
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition);


    /*
     * @Description: TODO 使用Bean的带参构造器去实例化一个Bean对象 的抽象方法。具体由AbstractAutowireBeanFactory实现。
     * @Author: dengbin
     * @Date: 28/6/23 15:22
     * @param beanName:
     * @param beanDefinition:
     * @param args:
     * @return: java.lang.Object
     **/
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition,Object[] args);

}
