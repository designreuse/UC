package com.yealink.uc.platform.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.BitSet;

import com.google.common.base.Charsets;

import org.apache.commons.codec.net.URLCodec;

public final class EncodingUtil {

    // refer to org.apache.commons.codec.net.URLCodec.WWW_FORM_URL, not including space, url path requires space be encoded as %20
    private static final BitSet URL_PATH = new BitSet(256);

    static {
        for (int i = 'a'; i <= 'z'; i++) {
            URL_PATH.set(i);
        }
        for (int i = 'A'; i <= 'Z'; i++) {
            URL_PATH.set(i);
        }
        for (int i = '0'; i <= '9'; i++) {
            URL_PATH.set(i);
        }
        URL_PATH.set('-');
        URL_PATH.set('_');
        URL_PATH.set('.');
        URL_PATH.set('*');
    }

    // url encoding is for queryString, url path encoding is for url path, the difference is queryString uses + for space, url path uses %20 for space
    // This is a ~&test will change to This%20is%20a%20%7E%26test
    public static String urlPath(String text) {
        return new String(URLCodec.encodeUrl(URL_PATH, text.getBytes(Charsets.UTF_8)), Charsets.UTF_8);
    }

    public static String url(String text) {
        try {
            return URLEncoder.encode(text, Charsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }
}
