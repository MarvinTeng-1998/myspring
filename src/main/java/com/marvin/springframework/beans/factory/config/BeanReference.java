package com.marvin.springframework.beans.factory.config;

/**
 * @TODO:关于Bean的引用，也就是Bean中的引用类型的属性而不是基本数据类型！
 * @author: dengbin
 * @create: 2023-06-28 17:21
 **/
public class BeanReference {
    private String beanName;

    public String getBeanName() {
        return beanName;
    }

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }
}
