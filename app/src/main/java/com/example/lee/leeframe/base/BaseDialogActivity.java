package com.example.lee.leeframe.base;

import android.app.Activity;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.lee.leeframe.app.AppManager;

/**
 * Created by hjx on 2018/04/08.
 * You can make it better
 */

public abstract class BaseDialogActivity<B extends ViewDataBinding> extends
        CheckPermissionsActivity implements BaseView {

    protected B mBinding;
    protected Activity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mBinding = createDataBinding(savedInstanceState);

        //添加到activity栈
        AppManager.getAppManager().addActivity(this);
        initMap(savedInstanceState);
        initWindow();

        initView();
        initEventAndData();
    }

    private void initWindow() {
        //窗口对齐屏幕宽度
        Window win = this.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.TOP;//设置对话框置顶显示
        win.setAttributes(lp);
    }

    protected void initMap(Bundle state) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        AppManager.getAppManager().finishActivity(this);
    }


    protected abstract B createDataBinding(Bundle savedInstanceState);

    protected abstract void initView();

    protected abstract void initEventAndData();
}
