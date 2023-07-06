package com.marvin.aop;

import com.marvin.springframework.context.annotation.Component;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Random;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-04 18:37
 **/
@Component("userService")
@ToString
@Getter
@Setter
public class AopUserServiceImpl implements AopUserService{
    private String token;
    @Override
    public String queryUserInfo() {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "小傅哥，100001，深圳";
    }

    @Override
    public String register(String userName) {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "注册用户：" + userName + " success！";
    }


}
