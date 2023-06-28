package com.marvin.springframework.core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-06-28 18:21
 **/
public class FileSystemResource implements Resource{
    private final File file;
    private final String path;

    public FileSystemResource(File file) {
        this.file = file;
        this.path = file.getPath();
    }

    public FileSystemResource(String path) {
        this.path = path;
        this.file = new File(path);
    }

    /*
     * @Description: TODO 根据文件地址或者文件本身获取到XML配置文件，并拿到InputStream
     * @Author: dengbin
     * @Date: 28/6/23 18:23

     * @return: java.io.InputStream
     **/
    @Override
    public InputStream getInputStream() throws IOException {
        return Files.newInputStream(this.file.toPath());
    }
}
