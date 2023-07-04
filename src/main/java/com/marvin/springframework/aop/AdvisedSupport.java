package com.marvin.springframework.aop;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * @TODO: 包装切面通知信息
 * 主要是用来把代理、拦截、匹配的各项属性包装到一个类中，方便在Proxy实现类进行使用。
 * @author: dengbin
 * @create: 2023-07-04 17:58
 **/
@Getter
@Setter
@Data
public class AdvisedSupport {

    // 被代理的目标对象
    private TargetSource targetSource;

    // 方法拦截器，由用户自己实现MethodInterceptor#invoke方法，做具体的处理
    private MethodInterceptor methodInterceptor;

    // 方法匹配器(检查目标方法是否符合通知条件)，是AspectJExpressionPointcut提供服务
    private MethodMatcher methodMatcher;

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }
}
