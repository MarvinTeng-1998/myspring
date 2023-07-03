package com.marvin.springframework;

import cn.hutool.core.io.IoUtil;
import com.marvin.bean.UserService;
import com.marvin.processor.MyBeanFactoryPostProcessor;
import com.marvin.processor.MyBeanPostProcessor;
import com.marvin.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.marvin.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.marvin.springframework.context.support.ClassPathXmlApplicationContext;
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

    @Test
    public void test_BeanFactoryPostProcessorAndBeanPostProcessor(){
        // 1.初始化BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2.读取配置文件
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");
        // 3.BeanDefinition 加载完成&Bean实例化之前，修改BeanDefinition的属性值
        MyBeanFactoryPostProcessor beanFactoryPostProcessor = new MyBeanFactoryPostProcessor();
        beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        // 4.实例化之后，修改Bean属性信息
        MyBeanPostProcessor beanPostProcessor = new MyBeanPostProcessor();
        beanFactory.addBeanPostProcessor(beanPostProcessor);

        // 5.获取Bean对象 调用方法
        UserService userService = beanFactory.getBean("userService", UserService.class);
        userService.queryInfo();
        System.out.println(userService.toString());

    }

    @Test
    public void test_xml(){
        // 1.初始化BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();
        UserService userService = applicationContext.getBean("userService", UserService.class);
        System.out.println(userService.toString());
    }

    @Test
    public void test_xmlabc(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();

        UserService userService = applicationContext.getBean("userService",UserService.class);
        userService.queryInfo();
        System.out.println(userService.getBeanFactory());
        System.out.println(userService.getApplicationContext());
    }
}
