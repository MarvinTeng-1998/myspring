package com.marvin.springframework;

import com.marvin.bean.UserService;
import com.marvin.springframework.beans.factory.BeanFactory;
import com.marvin.springframework.beans.factory.config.BeanDefinition;
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
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService",beanDefinition);
        UserService userService1 = (UserService) beanFactory.getBean("userService","zhangsan");
        userService1.queryInfo();
        System.out.println(userService1);

        UserService userService2 = (UserService) beanFactory.getBean("userService");
        userService2.queryInfo();
        System.out.println(userService2);

        System.out.println("两个对象是否相等：" + (userService1 == userService2) );
    }
}
