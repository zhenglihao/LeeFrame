package com.example.lee.leeframe.bean;

/**
 * @Author Lee
 * @Time 2018/7/12
 * @Theme 上传头像Bean(应该根据具体后台调整)
 */

public class UploadPhotoBean {

    private String fileUrl;
    private String fileId;
    private String fileUUId;

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileUUId() {
        return fileUUId;
    }

    public void setFileUUId(String fileUUId) {
        this.fileUUId = fileUUId;
    }
}
