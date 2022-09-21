package com.miaosha.utils;


import org.apache.commons.codec.digest.DigestUtils;

public class Md5Util {

    private static String salt = "1a2b3c4d";

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    public static String inputMd5FormPass(String src) {
        String str = "" + salt.charAt(0) + salt.charAt(1) + src + salt.charAt(4) + salt.charAt(5);
        return md5(str);
    }

    public static String FormPassMd5DbPass(String src, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(1) + src + salt.charAt(4) + salt.charAt(5);
        return md5(str);
    }

    public static String inputMd5DbPass(String src, String saltDb) {
        String dbPass = FormPassMd5DbPass(inputMd5FormPass(src), saltDb);
        return dbPass;
    }
}
