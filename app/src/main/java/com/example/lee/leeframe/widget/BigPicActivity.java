package com.example.lee.leeframe.widget;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.lee.leeframe.R;
import com.example.lee.leeframe.base.BaseActivity;
import com.example.lee.leeframe.databinding.ActivityBigPicBinding;
import com.example.lee.leeframe.utils.LogUtil;

import uk.co.senab.photoview.PhotoView;


/**
 * author:Bandele
 * date:2018/7/13 0013$
 * describe:
 */
public class BigPicActivity extends BaseActivity<ActivityBigPicBinding> {

    @Override
    protected ActivityBigPicBinding createDataBinding(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_big_pic);
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        LogUtil.d("url" + intent.getStringExtra("url"));
        Uri uri = Uri.parse(intent.getStringExtra("url"));

        RequestOptions options = new RequestOptions();
        options.centerCrop().placeholder(R.drawable.icon_moren).error(R.drawable.icon_moren);
        Glide.with(this)
                .load(intent.getStringExtra("url"))
                .apply(options)
                .into((PhotoView) findViewById(R.id.photoView));

       /* GlideApp.with(this).load(intent.getStringExtra("url")).error(R.mipmap.icon_moren).
                into((PhotoView) findViewById(R.id.photoView));*/
        mBinding.photoView.setImageURI(uri);
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void showError(String msg) {

    }
}
