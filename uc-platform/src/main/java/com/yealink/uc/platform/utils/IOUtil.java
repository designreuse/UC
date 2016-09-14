package com.yealink.uc.platform.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ChNan
 */
public class IOUtil {
    private static final Logger logger = LoggerFactory.getLogger(IOUtil.class);
    private static final int BUFFER_SIZE = 4096;

    public static void close(OutputStream stream) {
        try {
            if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static byte[] bytes(InputStream stream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byte[] buf = new byte[BUFFER_SIZE];
        int len;
        try {
            while (true) {
                len = stream.read(buf);
                if (len < 0) break;
                byteArrayOutputStream.write(buf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }


    public static byte[] bytes(String resourceFile) {
        InputStream stream = null;
        stream = ResourceUtil.class.getClassLoader().getResourceAsStream(resourceFile);
        if (stream == null) {
            throw new IllegalArgumentException("can not load resource, path=" + resourceFile);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byte[] buf = new byte[BUFFER_SIZE];
        int len;
        try {
            while (true) {
                len = stream.read(buf);
                if (len < 0) break;
                byteArrayOutputStream.write(buf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }
}
