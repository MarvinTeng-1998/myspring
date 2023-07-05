package com.marvin.springframework.aop;

import org.aopalliance.aop.Advice;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-05 15:58
 **/
public interface Advisor {

    /*
     * @Description: TODO 返回这个切面的advice部分，这个advice可能是拦截器、前置通知、返回通知、异常通知、后置通知、环绕通知等。
     * @Author: dengbin
     * @Date: 5/7/23 15:59

     * @return: org.aopalliance.aop.Advice
     **/
    Advice getAdvice();
}
