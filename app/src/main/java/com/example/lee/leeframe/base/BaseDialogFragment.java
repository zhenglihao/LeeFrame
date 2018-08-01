package com.example.lee.leeframe.base;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.lee.leeframe.utils.AnimUitl;
import com.example.lee.leeframe.utils.UiUtil;

/**
 * Created by lpb on 0014 12-14.
 * You can make it better
 */

public abstract class BaseDialogFragment extends DialogFragment implements View.OnClickListener {


    private View mRootView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onStart() {
        super.onStart();

        Window window = getDialog().getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams params = window.getAttributes();
        initLocationAndSize(window, params);
        window.setAttributes(params);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (mRootView == null) {
            mRootView = inflater.inflate(getViewRes(), container, false);
            UiUtil.findButtonAndSetOnClickListener(mRootView, this);
        }
        return mRootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AnimUitl.slideToUp(view);
        initView(mRootView);
        initData();
        initListener();

    }


    @Override
    public void onClick(View v) {
        onClick(v, v.getId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    protected abstract void initLocationAndSize(Window window, WindowManager.LayoutParams params);

    protected abstract int getViewRes();

    protected abstract void initView(View view);

    protected abstract void initData();

    protected abstract void initListener();

    protected abstract void onClick(View v, int id);
}
