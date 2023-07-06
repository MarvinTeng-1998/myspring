package com.marvin.springframework.context.annotation;

import java.lang.annotation.*;

/**
 * @TODO: 用于配置作用域的自定义注解，方便通过配置Bean对象注解的时候，拿到Bean对象的作用域。不过一般使用默认的singleton。
 * @author: dengbin
 * @create: 2023-07-05 22:19
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {

    /*
     * @Description: TODO 处理Bean的范围，是singleton还是prototype
     * @Author: dengbin
     * @Date: 5/7/23 22:21

     * @return: java.lang.String
     **/
    String value() default "singleton";
}
