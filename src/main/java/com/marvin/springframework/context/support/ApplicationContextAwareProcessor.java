package com.marvin.springframework.context.support;

import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.factory.config.BeanPostProcessor;
import com.marvin.springframework.context.ApplicationContext;
import com.marvin.springframework.context.ApplicationContextAware;

/**
 * @TODO: 一个关于ApplicationContext的感知处理器
 *        由于ApplicationContext的获取并能直接在创建Bean的时候就拿到，所以需要在refresh操作时，把ApplicationContext的写到一个包装的BeanProcessor中去。
 * @author: dengbin
 * @create: 2023-07-03 16:34
 **/
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicatioNContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext){
        this.applicatioNContext = applicationContext;
    }
    /*
     * @Description: TODO 感知对象实例化前的处理方式
     * @Author: dengbin
     * @Date: 3/7/23 16:35
     * @param bean:
     * @param beanName:
     * @return: java.lang.Object
     **/
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof ApplicationContextAware){
            ((ApplicationContextAware) bean).setApplicationContext(applicatioNContext);
        }
        return bean;
    }

    /*
     * @Description: TODO 感知对象实例化后的处理方式
     * @Author: dengbin
     * @Date: 3/7/23 16:35
     * @param bean:
     * @param beanName:
     * @return: java.lang.Object
     **/
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
