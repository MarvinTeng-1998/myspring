package com.marvin.springframework.core.io;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ClassUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @TODO: 这个是读取classpath地址下的XML文件
 * @author: dengbin
 * @create: 2023-06-28 18:17
 **/
public class ClassPathResource implements Resource{
    // 文件地址
    private final String path;

    // 类加载器，调用classLoader.getResourceAsStream()方法
    private ClassLoader classLoader;

    public ClassPathResource(String path) {
        this.path = path;
        this.classLoader = ClassUtil.getContextClassLoader();
    }

    public ClassPathResource(String path, ClassLoader classLoader) {
        Assert.notNull(path,"path must not be null");
        this.path = path;
        this.classLoader = classLoader;
    }

    /*
     * @Description: TODO 拿到classpath文件的输入流
     * @Author: dengbin
     * @Date: 28/6/23 18:20

     * @return: java.io.InputStream
     **/
    @Override
    public InputStream getInputStream() throws IOException {
        InputStream is = classLoader.getResourceAsStream(path);
        if(is == null){
            throw new FileNotFoundException(this.path + "cannot be opened beacuase it is not exist!");
        }
        return is;
    }
}
