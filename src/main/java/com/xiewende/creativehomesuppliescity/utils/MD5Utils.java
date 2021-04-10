package com.xiewende.creativehomesuppliescity.utils;

import org.springframework.util.DigestUtils;

/**
 * @create 2019-12-20  19:37
 */
public class MD5Utils {
    public static String getMD5passwprd(String password){
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }
}
