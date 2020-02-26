package com.liao.edu.service.utils.encryption;

import org.springframework.util.DigestUtils;

/**
 * @author liao
 * @since 2020/2/25 20:44
 */
public class MD5Encry {
    public static String encodeMd5(String s) {
        return DigestUtils.md5DigestAsHex(s.getBytes());
    }

    public static boolean check(String s, String md5Str) {
        return DigestUtils.md5DigestAsHex(s.getBytes()).equals(md5Str);
    }
}
