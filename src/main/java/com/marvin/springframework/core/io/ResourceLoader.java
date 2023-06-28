package com.marvin.springframework.core.io;

/**
 * @TODO: 用来设置classpath的前缀
 * @author: dengbin
 * @create: 2023-06-28 18:24
 **/
public interface ResourceLoader {
    public static final String CLASSPATH_PREFIX = "classpath:";

    /*
     * @Description: TODO 根据资源的类型不同， 可以直接把这些Resource来放到这个资源处理器下来获取Resource
     * @Author: dengbin
     * @Date: 28/6/23 18:25
     * @param location:
     * @return: com.marvin.springframework.core.io.Resource
     **/
    Resource getResource(String location);
}
