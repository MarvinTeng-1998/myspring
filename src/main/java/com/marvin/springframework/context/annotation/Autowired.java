package com.marvin.springframework.context.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-06 15:45
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD,ElementType.METHOD})
public @interface Autowired {
}
