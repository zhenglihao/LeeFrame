package com.example.lee.leeframe.bean.requestbean;

/**
 * @Author Lee
 * @Time 2018/6/26
 * @Theme
 */

public class ThirdLoginRequestBean {

    /**
     * openid :
     * accessToken : xxx
     * appid : 2
     * expiresIn : 7200
     */

    private String openid;
    private String accessToken;
    private int appid;
    private String expiresIn;

    // 构造函数：提供post传递的实体bean
    public ThirdLoginRequestBean(String openid, String accessToken, int appid, String expiresIn) {
        this.openid = openid;
        this.accessToken = accessToken;
        this.appid = appid;
        this.expiresIn = expiresIn;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }
}
