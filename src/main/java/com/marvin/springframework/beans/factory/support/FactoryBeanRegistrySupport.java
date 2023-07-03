package com.marvin.springframework.beans.factory.support;

import com.marvin.springframework.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-03 18:12
 **/
public class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    /*
     * @Description: TODO 一个BeanFactory的缓存，key是BeanFactory的名字，value是BeanFactory实际需要返回的对象。
     * @Author: dengbin
     * @Date: 3/7/23 18:32
     * @param null:
     * @return: null
     **/
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    /*
     * @Description: TODO 从BeanFactory缓存中获取到这个getObject的Bean
     * @Author: dengbin
     * @Date: 3/7/23 18:33
     * @param beanName:
     * @return: java.lang.Object
     **/
    protected Object getCacheObjectForFactoryBean(String beanName) {
        Object object = this.factoryBeanObjectCache.get(beanName);

        return (object != null ? object : null);
    }

    /*
     * @Description: TODO 如果factory是singleton的话，那则需要去缓存中找到这个getObject的对象，如果不是则每次都调用getObject方法
     * @Author: dengbin
     * @Date: 3/7/23 18:33
     * @param factory:
     * @param beanName:
     * @return: java.lang.Object
     **/
    protected Object getObjectFromFactoryBean(FactoryBean factory, String beanName) {
        if (factory.isSingleton()) {
            Object object = this.factoryBeanObjectCache.get(beanName);
            if (object == null) {
                object = doGetObjectFromFactoryBean(factory, beanName);
                this.factoryBeanObjectCache.put(beanName, (object != null ? object : null));
            }
            return (object != null ? object : null);
        } else {
            return doGetObjectFromFactoryBean(factory, beanName);
        }
    }


    /*
     * @Description: TODO 调用getObject方法
     * @Author: dengbin
     * @Date: 3/7/23 18:34
     * @param factory:
     * @param beanName:
     * @return: java.lang.Object
     **/
    private Object doGetObjectFromFactoryBean(final FactoryBean factory, final String beanName) {
        return factory.getObject();
    }
}
