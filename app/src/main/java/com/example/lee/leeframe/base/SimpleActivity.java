package com.example.lee.leeframe.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.lee.leeframe.R;
import com.example.lee.leeframe.app.AppManager;


/**
 * 无MVP的activity基类，简单activity直接继承该类
 */

public abstract class SimpleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(getResId());
        initSavedInstanceState(savedInstanceState);
        initView();
        initEventAndData();
    }

    protected void initSavedInstanceState(Bundle savedInstanceState) {

    }

    protected void setToolBar(Toolbar toolbar) {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
    }

    @LayoutRes
    protected abstract int getResId();

    protected abstract void initView();

    protected abstract void initEventAndData();
}
