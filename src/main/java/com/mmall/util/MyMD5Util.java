package com.mmall.util;

import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;

public class MyMD5Util {

    private static final String slat = "adfafwqreqw4154265465%^&$%#$%#$%&*(";

    public static String md5DigestAsHex(String str){
        try {
            byte[] bytes = (str+slat).getBytes("UTF-8");
            return  DigestUtils.md5DigestAsHex(bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
