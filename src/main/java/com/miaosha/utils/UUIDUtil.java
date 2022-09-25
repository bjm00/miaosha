package com.miaosha.utils;

import java.util.UUID;

public class UUIDUtil {
    private static final UUID uuidString = UUID.randomUUID();

    public static String genRandomString(){
        return uuidString.toString().replace("-","");
    }

}
