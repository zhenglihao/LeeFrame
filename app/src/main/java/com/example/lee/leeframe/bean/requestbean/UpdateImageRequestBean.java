package com.example.lee.leeframe.bean.requestbean;

/**
 * Created by tomGuo on 2018/5/11.
 */

public class UpdateImageRequestBean {

    private String fileId;
    private String bizType;

    public UpdateImageRequestBean(String fileId, String bizType) {
        this.fileId = fileId;
        this.bizType = bizType;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }
}
