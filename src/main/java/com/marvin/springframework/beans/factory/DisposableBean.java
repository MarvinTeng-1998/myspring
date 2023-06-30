package com.marvin.springframework.beans.factory;

/**
 * @TODO: Bean销毁之前的操作
 * @author: dengbin
 * @create: 2023-06-30 21:54
 **/
public interface DisposableBean {

    /*
     * @Description: TODO Bean销毁前的操作
     * @Author: dengbin
     * @Date: 30/6/23 21:54

     * @return: void
     **/
    void destroy() throws Exception;
}
