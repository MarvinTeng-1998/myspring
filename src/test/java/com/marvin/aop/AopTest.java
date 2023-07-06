package com.marvin.aop;

import com.marvin.bean.UserService;
import com.marvin.springframework.aop.AdvisedSupport;
import com.marvin.springframework.aop.TargetSource;
import com.marvin.springframework.aop.aspectj.AspectJExpressionPointcut;
import com.marvin.springframework.aop.framework.Cglib2AopProxy;
import com.marvin.springframework.aop.framework.JdkDynamicAopProxy;
import com.marvin.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-04 17:27
 **/
public class AopTest {
    @Test
    public void testProxy() throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* com.marvin.bean.UserService.*(..))");
        Class<UserService> clazz = UserService.class;
        Method method = clazz.getDeclaredMethod("queryInfo");

        System.out.println(pointcut.matches(clazz));
        System.out.println(pointcut.matches(method, clazz));
    }

    @Test
    public void test_Dynamic(){
        AopUserService userService = new AopUserServiceImpl();

        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(userService));
        advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* com.marvin.aop.AopUserService.*(..))"));

        // 使用JDK方式来进行反射调用
        AopUserService proxy_jdk = (AopUserService) new JdkDynamicAopProxy(advisedSupport).getProxy();

        System.out.println("测试结果" + proxy_jdk.queryUserInfo());

        // 使用Cglib方式来进行反射调用
        AopUserService proxy_cglib = (AopUserService) new Cglib2AopProxy(advisedSupport).getProxy();
        System.out.println("测试结果" + proxy_cglib.queryUserInfo());

    }

    @Test
    public void test_AOP(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring4.xml");
        AopUserService userService = applicationContext.getBean("AopUserServiceImpl",AopUserService.class);
        System.out.println("测试结果" + userService.queryUserInfo());
    }

    @Test
    public void test_property(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring5.xml");
        AopUserService userService = applicationContext.getBean("userService", AopUserService.class);
        System.out.println("测试结果" + userService);
    }

    @Test
    public void test_scan(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-scan.xml");
        AopUserService userService = applicationContext.getBean("userService",AopUserService.class);
        System.out.println(userService.queryUserInfo());
    }

    @Test
    public void test_injection(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring6.xml");
        AopUserService userService = applicationContext.getBean("userService", AopUserService.class);
        System.out.println("测试结果" + userService.queryUserInfo());
    }
}
