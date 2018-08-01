package com.example.lee.leeframe.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.lee.leeframe.bean.LoginBean;
import com.google.gson.Gson;

/**
 * @Author Lee
 * @Time 2018/7/12
 * @Theme SP 保存信息
 */

public class AppSp {

    private static final String USER_INFO = "userinfo";

    /**
     * USER_INFO 键
     */
    private static String MEMBER_BEAN = "member_bean";
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String ID = "id";
    public static final String NICKNAME = "nickName";
    public static final String AUTH_TYPE = "authType";
    public static final String HEADER_IMAGE = "headerImage";
    public static final String AUDIT_STATUS = "auditStatus";

    /**
     * USER_INFO 值
     */
    private static SharedPreferences mUserSp;
    private static String accessToken;
    private static int id = -1;
    private static int authType = -1;
    private static int auditType = -1;
    private static String nickName;
    private static String headerImage;
    private static String phoneNumber;
    private static String avatarImg;

    // 初始化SP （APP调用）
    public static void init(Context context) {
        mUserSp = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor editUser() {
        return mUserSp.edit();
    }

    public static SharedPreferences getUserSp() {
        return mUserSp;
    }

    // 保存个人信息
    public static boolean saveMember(LoginBean loginBean) {
        id = loginBean.getId();
        accessToken = loginBean.getToken();
        if (loginBean.getAuthType() != null) {
            authType = (int) loginBean.getAuthType();
        }
        if (loginBean.getAuditStatus() != null) {
            auditType = (int) loginBean.getAuditStatus();
        }
        nickName = loginBean.getNickName();
        headerImage = loginBean.getAvatarImg();
        phoneNumber = loginBean.getPhoneNumber();
        avatarImg = loginBean.getAvatarImg();
        saveAvatarImg(loginBean.getAvatarImg());
        return editUser().putString(MEMBER_BEAN, new Gson().toJson(loginBean))
                .putInt(ID, id)
                .putString(ACCESS_TOKEN, accessToken)
                .putString(NICKNAME, nickName)
                .putString(HEADER_IMAGE, headerImage)
                .putInt(AUTH_TYPE, authType)
                .putInt(AUDIT_STATUS, auditType)
                .commit();
    }

    // 清除个人信息
    public static void clearUser() {

        editUser().putString(MEMBER_BEAN, null); // 清空Bean
        SharedPreferences.Editor editor = getUserSp().edit();
        editor.putString(ACCESS_TOKEN, "");
        editor.clear();
        editor.commit();
    }


    // 法1：获取AccessToken, 为空则直接跳转登录界面 (使用：需要传递token接口)
    public static String getAccessToken() {
        if (TextUtils.isEmpty(accessToken)) {
            accessToken = mUserSp.getString(ACCESS_TOKEN, "");
        }
        if (TextUtils.isEmpty(accessToken)) {
            AppManager.getAppManager().reLogin();
            return "";
        }
        return accessToken;
    }

    // 法2：获取AccessToken的值 （ 使用: 游客模式 ）
    public static String getAccessToken02() {
        if (TextUtils.isEmpty(accessToken)) {
            accessToken = mUserSp.getString(ACCESS_TOKEN, "");
        }
        return accessToken;
    }

    /**
     * 是否已登录
     *
     * @return
     */
    public static boolean isLogined() {
        if (TextUtils.isEmpty(getAccessToken())) {
            return false;
        }
        return true;
    }

    public static LoginBean getMember() {
        return new Gson().fromJson(mUserSp.getString(MEMBER_BEAN, null), LoginBean.class);
    }

    public static int getMemberID() {
        if (id == -1) {
            id = mUserSp.getInt(ID, -1);
        }
        return id;
    }

    public static String getHeaderImage() {
        return mUserSp.getString(HEADER_IMAGE, "");
    }


    public static int getMemberAuthType() {
        if (authType == -1) {
            authType = mUserSp.getInt(AUTH_TYPE, -1);
        }
        return authType;
    }

    /**
     * 是否已身份认证
     *
     * @return
     */
    public static boolean isAudit() {
        if (auditType == -1) {
            auditType = mUserSp.getInt(AUDIT_STATUS, -1);
        }
        return auditType == 1;
    }


    // 自定义保存SP
    public static boolean saveAvatarImg(String avatarImg) {
        return mUserSp.edit().putString("AvatarImg", avatarImg).commit();
    }

    public static String getAvatarImg() {
        return mUserSp.getString("AvatarImg", "");
    }


    public static boolean saveLoginCount(String count) {
        return mUserSp.edit().putString("LoginCount", count).commit();
    }

    public static boolean savePassword(String password) {
        return mUserSp.edit().putString("PassWord", password).commit();
    }

    public static boolean saveIMLoginCount(String count) {
        return mUserSp.edit().putString("IMLoginCount", count).commit();
    }

    public static boolean saveIMPassword(String password) {
        return mUserSp.edit().putString("IMPassWord", password).commit();
    }

    public static String getIMLoginCount() {
        return mUserSp.getString("IMLoginCount", "");
    }

    public static String getIMPassword() {
        return mUserSp.getString("IMPassWord", "");
    }


}
