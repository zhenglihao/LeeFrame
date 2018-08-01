package com.example.lee.leeframe.api.http;

/**
 * 异常处理类，将异常包装成一个 Fault ,抛给上层统一处理
 * Created by zhouwei on 16/11/17.
 */

public class Fault extends RuntimeException {

    private String errorCode;

    public Fault(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
