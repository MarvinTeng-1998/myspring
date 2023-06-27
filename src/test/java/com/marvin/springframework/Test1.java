package com.marvin.springframework;

import com.marvin.bean.UserService;
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
public class Test1 {
    @Test
    public void test1() {
        BeanFactory beanFactory = new BeanFactory();
        BeanDefinition beanDefinition = new BeanDefinition(new UserService());
        beanFactory.registerBeanDefinition("userService", beanDefinition);
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryInfo();
    }
}
