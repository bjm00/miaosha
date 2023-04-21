package com.miaosha.enums;

/**
 * 1pc，2android，3ios
 */
public enum OrderChannel {
    pc(1, "pc"),
    android(2, "android"),
    ios(3, "ios");

    private int code;
    private String type;


    OrderChannel(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
