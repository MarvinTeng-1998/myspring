package com.marvin.bean;

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
public class UserService {
    private String uId;
    private String company;
    private String location;
    private UserDao userDao;

    public void queryInfo() {
        System.out.println("查询用户信息" + userDao.queryUsername(uId));
    }
}
