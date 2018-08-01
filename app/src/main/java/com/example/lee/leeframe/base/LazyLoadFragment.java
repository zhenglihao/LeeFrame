package com.example.lee.leeframe.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * @Author Lee
 * @Time 2018/7/12
 * @Theme 懒加载Frafment
 */

public abstract class LazyLoadFragment extends Fragment {

    private boolean mNeedLoad = true;
    private boolean mViewCreated;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        mViewCreated = true;
        loadIfNeed();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);
        loadIfNeed();
    }

    private void loadIfNeed(){
        if(getUserVisibleHint() && mNeedLoad && mViewCreated){
            doLoad();
            mNeedLoad = false;
        }
    }

    protected abstract void doLoad();

}
