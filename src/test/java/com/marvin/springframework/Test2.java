package com.marvin.springframework;

import com.marvin.event.CustomEvent;
import com.marvin.springframework.context.support.ClassPathXmlApplicationContext;
import com.marvin.testNew.IUserService;
import org.junit.Test;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-03 21:06
 **/
public class Test2 {
    // @Test
    // public void test_prototype(){
    //     ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
    //     applicationContext.registerShutdownHook();
    //
    //     IUserService iUserService01 = applicationContext.getBean("iUserService", IUserService.class);
    //     IUserService iUserService02 = applicationContext.getBean("iUserService", IUserService.class);
    //
    //     System.out.println(iUserService01);
    //     System.out.println(iUserService02);
    //
    //     System.out.println("两个对象是否相等：" + String.valueOf( iUserService01 == iUserService02));
    //
    // }

    @Test
    public void test_event(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring3.xml");
        applicationContext.registerShutdownHook();

        applicationContext.publishEvent(new CustomEvent(applicationContext, 12334535123L,"发布一个新事件！"));
    }
}
