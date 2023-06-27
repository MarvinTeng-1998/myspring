package com.marvin.springframework;

/**
 * @TODO: Bean的定义,用来定义Bean的实例化信息
 * @author: dengbin
 * @create: 2023-06-27 18:44
 **/
public class BeanDefinition {

    private final Object bean;

    public BeanDefinition(Object bean){
        this.bean = bean;
    }

    public Object getBean(){
        return bean;
    }


}
