package com.example.lee.leeframe.bean;

/**
 * @Author Lee
 * @Time 2018/7/12
 * @Theme
 */

public class LoginBean {

    /**
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjgzLCJhcHBJZCI6MywiZXhwIjoxNTMwNjI2NDAyLCJ1c2VybmFtZSI6IjEzNDE2MjM3MTI1In0.FI_u7Hp5gHwcuUve6CcKEEE-vZ2LRjS2OMM4U6hgoE0
     * id : 83
     * authType : null
     * avatarImg : /upload/006/2018/06/b4b8e84d10024c769b33aa5de0ff7479.png
     * nickName : 我是83号
     * phoneNumber : 13416237125
     * userAppPermission : {"userId":83,"cricleReadPerm":true,"criclePublishPerm":true,"cricleCommentPerm":true}
     * auditStatus : null
     */

    private String token;
    private int id;
    private Object authType;
    private String avatarImg;
    private String nickName;
    private String phoneNumber;
    private UserAppPermissionBean userAppPermission;
    private Object auditStatus;
    private String imAccountName;
    private String imPassword;

    public String getImAccountName() {
        return imAccountName;
    }

    public void setImAccountName(String imAccountName) {
        this.imAccountName = imAccountName;
    }

    public String getImPassword() {
        return imPassword;
    }

    public void setImPassword(String imPassword) {
        this.imPassword = imPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getAuthType() {
        return authType;
    }

    public void setAuthType(Object authType) {
        this.authType = authType;
    }

    public String getAvatarImg() {
        return avatarImg;
    }

    public void setAvatarImg(String avatarImg) {
        this.avatarImg = avatarImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserAppPermissionBean getUserAppPermission() {
        return userAppPermission;
    }

    public void setUserAppPermission(UserAppPermissionBean userAppPermission) {
        this.userAppPermission = userAppPermission;
    }

    public Object getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Object auditStatus) {
        this.auditStatus = auditStatus;
    }

    public static class UserAppPermissionBean {
        /**
         * userId : 83
         * cricleReadPerm : true
         * criclePublishPerm : true
         * cricleCommentPerm : true
         */

        private int userId;
        private boolean cricleReadPerm;
        private boolean criclePublishPerm;
        private boolean cricleCommentPerm;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public boolean isCricleReadPerm() {
            return cricleReadPerm;
        }

        public void setCricleReadPerm(boolean cricleReadPerm) {
            this.cricleReadPerm = cricleReadPerm;
        }

        public boolean isCriclePublishPerm() {
            return criclePublishPerm;
        }

        public void setCriclePublishPerm(boolean criclePublishPerm) {
            this.criclePublishPerm = criclePublishPerm;
        }

        public boolean isCricleCommentPerm() {
            return cricleCommentPerm;
        }

        public void setCricleCommentPerm(boolean cricleCommentPerm) {
            this.cricleCommentPerm = cricleCommentPerm;
        }
    }
}
