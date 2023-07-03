package com.marvin.bean;

import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.factory.*;
import com.marvin.springframework.context.ApplicationContext;
import com.marvin.springframework.context.ApplicationContextAware;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-06-27 22:21
 **/
@Data
@Getter
@Setter
@ToString
public class UserService implements InitializingBean, DisposableBean , BeanNameAware, BeanClassLoaderAware, ApplicationContextAware, BeanFactoryAware {
    private String uId;
    private String company;
    private String location;
    private UserDao userDao;
    private ApplicationContext applicationContext;
    private BeanFactory beanFactory;

    public void queryInfo() {
        System.out.println("查询用户信息" + userDao.queryUsername(uId));
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("执行DisposableBean的销毁函数");
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        System.out.println("执行在属性注入后的Bean初始化函数");

    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("ClassLoader:" + classLoader);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("可以修改的BeanName的" + name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
       this.applicationContext = applicationContext;
    }
}
