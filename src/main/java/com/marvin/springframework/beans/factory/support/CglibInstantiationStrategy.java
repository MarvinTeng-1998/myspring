package com.marvin.springframework.beans.factory.support;

import com.marvin.springframework.beans.factory.config.BeanDefinition;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;

/**
 * @TODO: 使用Cglib的方式来对Bean进行有参实例化。
 * @author: dengbin
 * @create: 2023-06-28 15:30
 **/
public class CglibInstantiationStrategy implements InstantiationStrategy{

    /*
     * @Description: TODO 使用Cglib的方式对Bean进行有参实例化
     * @Author: dengbin
     * @Date: 28/6/23 15:31
     * @param beanDefinition:
     * @param beanName:
     * @param constructor:
     * @param args:
     * @return: java.lang.Object
     **/
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor<Object> constructor, Object[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getBeanClass());
        enhancer.setCallback(new NoOp() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });
        if(null == constructor) {
            return enhancer.create();
        }
        return enhancer.create(constructor.getParameterTypes(),args);
    }
}
