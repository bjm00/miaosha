package com.miaosha.redis;

public class MiaoshaUserKey extends BasePrefix {

    private static final int expireSeconds = 3600 * 24 * 2;

    public MiaoshaUserKey(String prefix) {
        super(expireSeconds, prefix);
    }
    public static MiaoshaUserKey token = new MiaoshaUserKey("token");
}
