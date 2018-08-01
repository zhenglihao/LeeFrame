package com.example.lee.leeframe.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import com.example.lee.leeframe.R;
import com.example.lee.leeframe.utils.StatusBarCompat;
import com.example.lee.leeframe.widget.dialog.LoadingDialog;

import com.example.lee.leeframe.app.AppManager;

/**
 * @Author Lee
 * @Time 2018/7/12
 * @Theme Activity基础公共类
 */

public abstract class BaseActivity<B extends ViewDataBinding> extends CheckPermissionsActivity
        implements BaseView {

    protected B mBinding;
    protected Activity mContext;
    protected LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBeforeSetcontentView();
        mContext = this;
        mBinding = createDataBinding(savedInstanceState);
        mLoadingDialog = new LoadingDialog(this);
        //添加到activity栈
        AppManager.getAppManager().addActivity(this);
        initView();
        initEventAndData();
    }

    /**
     * 设置沉浸式菜单，无标题，屏幕方向等
     */
    protected void doBeforeSetcontentView() {
        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        SetStatusBarColor();
    }


    protected void setToolBar(Toolbar toolbar) {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor() {
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this,
                R.color.colorPrimaryDark));
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor(int color) {
        StatusBarCompat.setStatusBarColor(this, color);
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void SetTranslanteBar() {
        StatusBarCompat.translucentStatusBar(this, false);
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
