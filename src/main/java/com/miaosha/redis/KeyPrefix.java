package com.miaosha.redis;

public interface KeyPrefix {

    public int expirePrefix();

    public String getPrefix();

}
