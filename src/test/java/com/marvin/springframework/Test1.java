package com.marvin.springframework;

import cn.hutool.core.io.IoUtil;
import com.marvin.bean.UserDao;
import com.marvin.bean.UserService;
import com.marvin.springframework.beans.PropertyValue;
import com.marvin.springframework.beans.PropertyValues;
import com.marvin.springframework.beans.factory.BeanFactory;
import com.marvin.springframework.beans.factory.config.BeanDefinition;
import com.marvin.springframework.beans.factory.config.BeanReference;
import com.marvin.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.marvin.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.marvin.springframework.core.io.DefaultResourceLoader;
import com.marvin.springframework.core.io.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

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
    private DefaultResourceLoader resourceLoader;

    @Before
    public void init(){
        resourceLoader = new DefaultResourceLoader();
    }

    @Test
    public void test1() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:important.properties");
        InputStream is = resource.getInputStream();
        String content = IoUtil.readUtf8(is);
        System.out.println(content);
    }

    @Test
    public void test2(){
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");
        // 这里是getBean的问题，导致出现问题。UserService.class
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryInfo();
    }
}
