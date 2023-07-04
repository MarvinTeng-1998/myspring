package com.marvin.springframework.aop.framework;

/**
 * @TODO: 用于获取代理类，因为具体实现代理的方式可以用JDK方式和Cglib方式，所以定义接口更加方便管理实现类
 * @author: dengbin
 * @create: 2023-07-04 18:05
 **/
public interface AopProxy {
    Object getProxy();
}
