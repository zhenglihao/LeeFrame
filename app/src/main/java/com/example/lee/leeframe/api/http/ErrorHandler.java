package com.example.lee.leeframe.api.http;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.lee.leeframe.app.AppManager;
import com.example.lee.leeframe.base.BaseView;
import com.example.lee.leeframe.utils.LogUtil;

/**
 * @Author Lee
 * @Time 2018/7/12
 * @Theme 抛异常错误处理类
 */

public class ErrorHandler {

    private static final String LOGIN_INVALID_CODE = "401";

    public static void e(Throwable error, BaseView view) {
        if (error instanceof Fault) {
            view.showError(error.getMessage());
            Fault fault = (Fault) error;
            if (LOGIN_INVALID_CODE.equals(fault.getErrorCode())) {
                AppManager.getAppManager().reLogin();
            }

        } else if (view instanceof Activity && !isNetworkConnected(((Activity) view))
                || view instanceof Fragment && !isNetworkConnected(((Fragment) view).getActivity())) {
            view.showError("请检查网络连接");

        } else if (error.getMessage() != null && error.getMessage().contains("BEGIN_OBJECT")) {

            view.showError(error.getMessage());
            // view.showError("json解析出错，请后台不要在失败时返回对象，返回个空字符串行不行？");

        } else {
            view.showError("网络错误");
            LogUtil.e("错误", error);
        }
    }

    /**
     * 判断是否有网络连接
     *
     * @param context
     * @return
     */
    private static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

}
