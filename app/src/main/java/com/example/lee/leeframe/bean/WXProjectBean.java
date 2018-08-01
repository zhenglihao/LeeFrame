package com.example.lee.leeframe.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @Author Lee
 * @Time 2018/6/1
 * @Theme
 */

public class WXProjectBean {

    /**
     * package : Sign=WXPay
     * appid : wx68cf259bb4afae2d
     * sign : 636866A230A8338E3F3101A9C200EB66
     * partnerid : 1507681561
     * prepayid : wx20204133941625cde0d17e580503661671
     * noncestr : 1529498493999
     * timestamp : 1529498493
     */

    @SerializedName("package")
    private String packages;
    private String appid;
    private String sign;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private String timestamp;

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
