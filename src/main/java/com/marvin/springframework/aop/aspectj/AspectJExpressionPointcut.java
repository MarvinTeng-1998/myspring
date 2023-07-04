package com.marvin.springframework.aop.aspectj;

import com.marvin.springframework.aop.ClassFilter;
import com.marvin.springframework.aop.MethodMatcher;
import com.marvin.springframework.aop.Pointcut;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-04 17:44
 **/
public class AspectJExpressionPointcut implements Pointcut, ClassFilter, MethodMatcher {

    private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES = new HashSet<>();

    static{
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
    }

    // 解析表达式后的结果
    private final PointcutExpression pointcutExpression;

    public AspectJExpressionPointcut(String expression){
        PointcutParser pointcutParser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(SUPPORTED_PRIMITIVES,this.getClass().getClassLoader());
        pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }

    /*
     * @Description: TODO 用来确定切入点是否能匹配到这个类
     * @Author: dengbin
     * @Date: 4/7/23 17:50
     * @param clazz:
     * @return: boolean
     **/
    @Override
    public boolean matches(Class<?> clazz) {
        // 返回一个切入点是否匹配到这个类文件
        return pointcutExpression.couldMatchJoinPointsInType(clazz);
    }

    /*
     * @Description: TODO 用来确定切入点是否可以标识method和targetClass
     * @Author: dengbin
     * @Date: 4/7/23 17:50
     * @param method:
     * @param targetClass:
     * @return: boolean
     **/
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
    }

    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
