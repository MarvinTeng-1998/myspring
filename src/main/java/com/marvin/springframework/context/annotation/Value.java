package com.marvin.springframework.context.annotation;

import java.lang.annotation.*;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-06 15:47
 **/
@Target({ElementType.PARAMETER,ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Value {
    String value();
}
