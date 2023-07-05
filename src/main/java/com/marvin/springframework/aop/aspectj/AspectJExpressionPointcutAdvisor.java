package com.marvin.springframework.aop.aspectj;

import com.marvin.springframework.aop.Pointcut;
import com.marvin.springframework.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

/**
 * @TODO: AspectJExpressionPointcutAdvisor实现了PointcutAdvisor接口，把切面pointcut、拦截方法advice和具体的拦截表达式包装在了一起。
 *        这样就可以在xml的配置中定义一个pointcutAdvisor切面拦截器了。
 * @author: dengbin
 * @create: 2023-07-05 16:02
 **/
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {
    // 切面
    private AspectJExpressionPointcut pointcut;

    // 具体的方法增强
    private Advice advice;

    // 表达式
    private String expression;

    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public Pointcut getPointcut() {
        if(null == pointcut){
            pointcut = new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }
}
