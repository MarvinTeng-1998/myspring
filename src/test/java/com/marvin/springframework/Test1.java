package com.marvin.springframework;

import com.marvin.bean.UserDao;
import com.marvin.bean.UserService;
import com.marvin.springframework.beans.PropertyValue;
import com.marvin.springframework.beans.PropertyValues;
import com.marvin.springframework.beans.factory.BeanFactory;
import com.marvin.springframework.beans.factory.config.BeanDefinition;
import com.marvin.springframework.beans.factory.config.BeanReference;
import com.marvin.springframework.beans.factory.support.DefaultListableBeanFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @TODO: 对BeanFactory做一个简单测试, 通过注册一个beanDefinition来对Bean的注入。
 * 过程也就是：
 * 1.首先new一个对象
 * 2.然后注入到BeanDefinition中
 * 3.然后将BeanDefinition来注入到Map容器中。
 * @author: dengbin
 * @create: 2023-06-27 22:21
 **/
@Slf4j
public class Test1 {
    @Test
    public void test1() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));
        PropertyValues propertyValues = new PropertyValues();

        propertyValues.addPropertyValue(new PropertyValue("uId","1001"));
        propertyValues.addPropertyValue(new PropertyValue("userDao",new BeanReference("userDao")));
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class,propertyValues);
        beanFactory.registerBeanDefinition("userService",beanDefinition);

        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryInfo();

    }
}
