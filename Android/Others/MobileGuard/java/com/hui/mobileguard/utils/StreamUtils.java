package com.hui.mobileguard.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by HUI on 2016/4/29.
 */
public class StreamUtils {

    public static String getStringFromStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while((len=is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        return baos.toString();
    }
}
