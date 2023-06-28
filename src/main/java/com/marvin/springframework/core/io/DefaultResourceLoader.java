package com.marvin.springframework.core.io;

import cn.hutool.core.lang.Assert;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-06-28 18:26
 **/
public class DefaultResourceLoader implements ResourceLoader{

    /*
     * @Description: TODO 简单工厂模式，根据传入的Location值来确定创建什么样的Resource实例
     * @Author: dengbin
     * @Date: 28/6/23 18:27
     * @param location: 传入的地址值，可能是classpath开头的，也可能是文件地址开头的。
     * @return: com.marvin.springframework.core.io.Resource
     **/
    @Override
    public Resource getResource(String location) {
        Assert.notNull(location,"Location must not be null");;
        if(location.startsWith(CLASSPATH_PREFIX)){
            return new ClassPathResource(location.substring(CLASSPATH_PREFIX.length()));
        }else{
            return new FileSystemResource(location);
        }
    }
}
