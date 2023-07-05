package com.marvin.springframework.aop.framework;

import com.marvin.springframework.aop.AdvisedSupport;

/**
 * @TODO: 代理工厂：根据不同的创建需求来确定是JDK代理还是Cglib代理。
 * @author: dengbin
 * @create: 2023-07-05 16:09
 **/
public class ProxyFactory {
    // 包装切面通知信息
    private AdvisedSupport advisedSupport;

    public ProxyFactory(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    private AopProxy createAopProxy(){
        if(advisedSupport.isProxyTargetClass()){
            return new Cglib2AopProxy(advisedSupport);
        }
        return new JdkDynamicAopProxy(advisedSupport);
    }
}
