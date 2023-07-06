package com.marvin.dao;

import com.marvin.dao.UserService;
import com.marvin.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-06 16:26
 **/
public class DaoTest {
    @Test
    public void test_scan(){
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:spring-annotation.xml");
        UserService userService = classPathXmlApplicationContext.getBean("userService", UserService.class);
        System.out.println("测试结果" + userService.queryUserInfo());
        System.out.println(userService.getToken());

    }
}
