package com.marvin.bean;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-06-27 22:21
 **/
public class UserService {
    private String username;

    // public UserService() {
    // }

    public UserService(String username) {
        this.username = username;
    }

    public void queryInfo() {
        System.out.println("查询用户信息" + username);
    }

    @Override
    public String toString() {
        return "UserService{" +
                "username='" + username + '\'' +
                '}';
    }
}
