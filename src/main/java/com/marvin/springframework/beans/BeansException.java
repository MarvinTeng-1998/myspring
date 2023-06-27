package com.marvin.springframework.beans;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-06-27 22:42
 **/
public class BeansException extends RuntimeException{

    private static final long serialVersionUID = 5138225684463988535L;

    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
