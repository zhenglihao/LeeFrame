package com.example.lee.leeframe.base;

/**
 * @Author Lee
 * @Time 2018/7/12
 * @Theme  网络请求结果BaseBean
 */


public class BaseBean<T> {

    private String message;
    private int status;
    private T result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
