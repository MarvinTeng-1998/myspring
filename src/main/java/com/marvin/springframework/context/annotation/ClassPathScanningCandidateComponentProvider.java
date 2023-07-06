package com.marvin.springframework.context.annotation;

import cn.hutool.core.util.ClassUtil;
import com.marvin.springframework.beans.factory.config.BeanDefinition;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @TODO: 处理对象扫描装配
 * @author: dengbin
 * @create: 2023-07-06 14:29
 **/
public class ClassPathScanningCandidateComponentProvider {

    /*
     * @Description: TODO 扫描某个包下的类，然后获取到所有的component并且注册到BeanDefinition，形成一个Set容器
     * @Author: dengbin
     * @Date: 6/7/23 14:32
     * @param basePackage:
     * @return: java.util.Set<com.marvin.springframework.beans.factory.config.BeanDefinition>
     **/
    public Set<BeanDefinition> findCandidateComponents(String basePackage){
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for(Class<?> clazz : classes){
            candidates.add(new BeanDefinition(clazz));
        }
        return candidates;
    }
}
