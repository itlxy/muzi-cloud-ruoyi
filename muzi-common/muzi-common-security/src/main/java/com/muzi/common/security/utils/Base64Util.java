package com.muzi.common.security.utils;

import org.apache.commons.codec.binary.Base64;

/**
 * Base64
 *
 * @Author: muzi
 * @DateTime: 2022/8/7 22:40
 **/
public class Base64Util{

    /**
     * Decoding to binary
     * @param base64 base64
     * @return byte
     * @throws Exception Exception
     */
    public static byte[] decode(String base64) throws Exception {
        return Base64.decodeBase64(base64);
    }

    /**
     * Binary encoding as a string
     * @param bytes byte
     * @return String
     * @throws Exception Exception
     */
    public static String encode(byte[] bytes) throws Exception {
        return new String(Base64.encodeBase64(bytes));
    }
}
