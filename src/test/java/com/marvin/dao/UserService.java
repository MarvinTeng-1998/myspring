package com.marvin.dao;

import com.marvin.springframework.context.annotation.Autowired;
import com.marvin.springframework.context.annotation.Component;
import com.marvin.springframework.context.annotation.Qualifier;
import com.marvin.springframework.context.annotation.Value;
import lombok.Data;

import java.util.Random;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-06 16:21
 **/
@Component("userService")
@Data
public class UserService {
    @Value("${token}")
    private String token;

    @Autowired
    @Qualifier("userDao")
    private UserDao userDao;

    public String queryUserInfo(){
        try{
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userDao.queryName("10001");
    }
}
