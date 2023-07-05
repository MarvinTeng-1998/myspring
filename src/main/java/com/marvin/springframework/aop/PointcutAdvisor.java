package com.marvin.springframework.aop;

/**
 * @TODO: Advisor：切面。承担了Pointcut和Advice的组合，Pointcut用于获取JointPoint，而Advice决定于JoinPoint执行什么操作。
 * @author: dengbin
 * @create: 2023-07-05 16:01
 **/
public interface PointcutAdvisor extends Advisor{


    /*
     * @Description: TODO 返回Pointcut
     * @Author: dengbin
     * @Date: 5/7/23 16:02

     * @return: com.marvin.springframework.aop.Pointcut
     **/
    Pointcut getPointcut();


}
