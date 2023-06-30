package com.marvin.processor;

import com.marvin.bean.UserService;
import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-06-30 16:53
 **/
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if("userService".equals(beanName)){
            UserService userService = (UserService) bean;
            userService.setLocation("改为：北京");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
