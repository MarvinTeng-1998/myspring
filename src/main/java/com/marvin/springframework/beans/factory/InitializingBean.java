package com.marvin.springframework.beans.factory;

/**
 * @TODO: 实例化Bean并填充属性后的调用接口
 * @author: dengbin
 * @create: 2023-06-30 21:52
 **/
public interface InitializingBean {

    /*
     * @Description: TODO Bean填充属性后调用
     * @Author: dengbin
     * @Date: 30/6/23 21:53

     * @return: void
     **/
    void afterPropertiesSet() throws Exception;
}
