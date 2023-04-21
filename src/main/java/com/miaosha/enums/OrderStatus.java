package com.miaosha.enums;


/**
 * 订单状态，0新建未支付，1已支付，2已发货，3已收货，4已退款，5已完成
 */
public enum OrderStatus {
    init(0, "新建未支付"),
    success(1, "已支付"),
    send(2, "已发货"),
    take(3, "已收货"),
    refund(4, "已退款"),
    done(5, "已完成")
    ;

    private int code;
    private String type;

    OrderStatus(int code, String type) {
        this.code=code;
        this.type= type;
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
