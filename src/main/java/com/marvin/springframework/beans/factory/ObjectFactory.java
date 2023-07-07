package com.marvin.springframework.beans.factory;

import com.marvin.springframework.beans.BeansException;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-07 14:56
 **/
public interface ObjectFactory<T> {
    T getObjects() throws BeansException;
}
