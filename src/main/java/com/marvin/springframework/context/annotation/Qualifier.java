package com.marvin.springframework.context.annotation;

import javax.xml.bind.Element;
import java.lang.annotation.*;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-06 15:46
 **/
@Target({ElementType.TYPE,ElementType.FIELD, ElementType.PARAMETER,ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Qualifier {
    String value() default "";
}
