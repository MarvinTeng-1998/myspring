package com.marvin.bean;

import com.marvin.springframework.beans.factory.DisposableBean;
import com.marvin.springframework.beans.factory.InitializingBean;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-06-27 22:21
 **/
@Data
@Getter
@Setter
@ToString
public class UserService implements InitializingBean, DisposableBean {
    private String uId;
    private String company;
    private String location;
    private UserDao userDao;

    public void queryInfo() {
        System.out.println("查询用户信息" + userDao.queryUsername(uId));
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("执行DisposableBean的销毁函数");
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        System.out.println("执行在属性注入后的Bean初始化函数");

    }
}
