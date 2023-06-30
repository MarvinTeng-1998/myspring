package com.marvin.springframework.context.support;

import com.marvin.springframework.beans.BeansException;
import com.marvin.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.marvin.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * @TODO: 这个BeanFactory中存放着一个默认进行容器注册、BeanDefinition注册类。也就是DefaultListableBeanFactory这个类
 *          它主要是用来处理这些事情！！！
 * @author: dengbin
 * @create: 2023-06-30 15:47
 **/
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext{
    // 所有ApplicationContext中默认都用的是DefaultListableBeanFactory来对里面的方法进行实现和调用。
    private DefaultListableBeanFactory beanFactory;

    /*
     * @Description: TODO 这个方法主要是用来刷新这个DefaultListableBeanFactory
     * @Author: dengbin
     * @Date: 30/6/23 16:00
     
     * @return: void
     **/
    @Override
    protected void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    /*
     * @Description: TODO 这个方法会实例化DefaultListableBeanFactory
     * @Author: dengbin
     * @Date: 30/6/23 16:02

     * @return: com.marvin.springframework.beans.factory.support.DefaultListableBeanFactory
     **/
    private DefaultListableBeanFactory createBeanFactory(){
        return new DefaultListableBeanFactory();
    }

    /*
     * @Description: TODO 这个方法主要是对这个DefaultListableBeanFactory来加载里面的BeanDefinitions
     * @Author: dengbin
     * @Date: 30/6/23 16:02
     * @param beanFactory:
     * @return: void
     **/
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }
}
