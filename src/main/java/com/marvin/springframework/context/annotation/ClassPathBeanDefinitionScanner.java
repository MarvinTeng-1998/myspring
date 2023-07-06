package com.marvin.springframework.context.annotation;

import cn.hutool.core.util.StrUtil;
import com.marvin.springframework.beans.factory.config.BeanDefinition;
import com.marvin.springframework.beans.factory.support.BeanDefinitionRegistry;

import java.util.Set;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-06 14:33
 **/
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {
    private BeanDefinitionRegistry beanDefinitionRegistry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition beanDefinition : candidates) {
                String beanScope = resolveBeanScope(beanDefinition);
                if (StrUtil.isNotEmpty(beanScope)) {
                    beanDefinition.setScope(beanScope);
                }
                beanDefinitionRegistry.registerBeanDefinition(determineBeanName(beanDefinition), beanDefinition);
            }
        }
        // beanDefinitionRegistry.registerBeanDefinition("");
    }

    /*
     * @Description: TODO 根据BeanClass上的Scope来确定Bean的Scope，是单例还是原型
     * @Author: dengbin
     * @Date: 6/7/23 14:38
     * @param beanDefinition:
     * @return: java.lang.String
     **/
    private String resolveBeanScope(BeanDefinition beanDefinition){
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if(null != scope) {
            return scope.value();
        }
        return StrUtil.EMPTY;
    }

    /*
     * @Description: TODO 根据BeanDefinition中的class上是否含有对应的Annotation来确定是否需要被扫描成Component
     * @Author: dengbin
     * @Date: 6/7/23 14:38
     * @param beanDefinition:
     * @return: java.lang.String
     **/
    private String determineBeanName(BeanDefinition beanDefinition){
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();
        if(StrUtil.isEmpty(value)){
            value = StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return value;
    }
}
