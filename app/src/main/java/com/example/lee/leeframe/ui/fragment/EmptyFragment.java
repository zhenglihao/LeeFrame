package com.example.lee.leeframe.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lee.leeframe.R;
import com.example.lee.leeframe.base.LazyLoadFragment;


/**
 * @Author Lee
 * @Time 2018/7/12
 * @Theme 空Fragment( 开发可用)
 */
public class EmptyFragment extends LazyLoadFragment {

    public static EmptyFragment newInstance(){
        return new EmptyFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_empty, container, false);
        return view;
    }

    @Override
    protected void doLoad() {

    }
}
