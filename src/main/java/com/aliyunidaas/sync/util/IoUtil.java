package com.aliyunidaas.sync.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * IO工具类
 *
 * @author hatterjiang
 */
public class IoUtil {

    public static byte[] readAll(InputStream inputStream) throws IOException {
        final byte[] buffer = new byte[1024 * 8];
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int len; ((len = inputStream.read(buffer)) != -1); ) {
            baos.write(buffer, 0, len);
        }
        return baos.toByteArray();
    }
}
