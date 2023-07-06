package com.marvin.springframework.context.annotation;

import java.lang.annotation.*;

/**
 * @TODO: Bean的注解
 * @author: dengbin
 * @create: 2023-07-05 22:22
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    String value() default "";
}
