package com.miaosha.resultcode;

public class Result<T> {
    private int code;
    private String msg;
    private T data;

    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    public Result(CodeMsg codeMsg) {
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    public static <T> Result<T> error(CodeMsg codeMsg) {
        if (null == codeMsg) {
            return null;
        }
        return new Result<T>(codeMsg);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
