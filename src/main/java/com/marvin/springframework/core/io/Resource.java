package com.marvin.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @TODO: 拿到文件的inputStream
 * @author: dengbin
 * @create: 2023-06-28 18:17
 **/
public interface Resource {
    InputStream getInputStream() throws IOException;
}
