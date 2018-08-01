package com.example.lee.leeframe.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lee.leeframe.R;

/**
 * @Author Lee
 * @Time 2018/7/12
 * @Theme 自定义 加载对话框 (可以设置 加载不可取消 判断是否显示加载框，设置加载文本)
 */

public class LoadingDialog {

    private Dialog mDialog;
    private Window window;
    private ProgressBar mProgressBar;

    public LoadingDialog(Context context, String message) {
        initView(context, message);
    }

    public LoadingDialog(Context context) {
        initView(context, null);
    }

    private void initView(Context context, String message) {
        mDialog = new Dialog(context, R.style.CustomDialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        window = mDialog.getWindow();
        window.setContentView(R.layout.dialog_text_loading);
        TextView tvMessage = window.findViewById(R.id.message);
        if (message != null) {
            tvMessage.setVisibility(View.VISIBLE);
            tvMessage.setText(message);
        }
        mProgressBar = window.findViewById(R.id.pb_loadingbar);
    }

    public void show() {
        mDialog.setCancelable(true);
        mDialog.show();
    }

    public void showUnCalcel() {
        mDialog.setCancelable(false);
        mDialog.show();
    }

    public void setCancelable(boolean cancelable) {
        mDialog.setCancelable(cancelable);
    }

    public boolean isShowing() {
        if (mDialog.isShowing()) {
            return true;
        } else {
            return false;
        }
    }

    public void dissmiss() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public void setMessage(String msg) {
        window = mDialog.getWindow();
        TextView tvMessage = window.findViewById(R.id.message);
        tvMessage.setVisibility(View.VISIBLE);
        tvMessage.setText(msg);
    }

}
