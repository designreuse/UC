package com.yealink.uc.platform.utils;

import java.io.InputStream;
import java.nio.charset.Charset;

public final class ResourceUtil {
    private static byte[] bytes;

    private static void read(String resourcePath) {
        InputStream stream = null;
        stream = ResourceUtil.class.getClassLoader().getResourceAsStream(resourcePath);
        if (stream == null) {
            throw new IllegalArgumentException("can not load resource, path=" + resourcePath);
        }
        bytes = IOUtil.bytes(stream);
    }

    public static String getTextContent(String resourcePath) {
        read(resourcePath);
        return new String(bytes, Charset.defaultCharset());
    }

    public byte[] getBytes() {
        return bytes;
    }
}
