package com.marvin.springframework.util;

/**
 * @TODO: 用来解析字符串的一个接口
 * @author: dengbin
 * @create: 2023-07-06 15:32
 **/
public interface StringValueResolver {

    String resolveStringValue(String strVal);
}
