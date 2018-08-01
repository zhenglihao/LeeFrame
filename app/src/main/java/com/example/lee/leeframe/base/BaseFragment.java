package com.example.lee.leeframe.base;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lee.leeframe.widget.dialog.LoadingDialog;

/**
 * @Author Lee
 * @Time 2018/7/12
 * @Theme Fragment基础公共类
 * @description Android应用开发过程中，ViewPager同时加载多个fragment，以实现多tab页面快速切换,
 * 但是fragment初始化时若加载的内容较多，就可能导致整个应用启动速度缓慢，影响用户体验。
 * 为了提高用户体验，我们会使用一些懒加载方案，实现加载延迟。这时我们会用到getUserVisibleHint()
 * 与setUserVisibleHint()这两个方法。
 */

public abstract class BaseFragment<B extends ViewDataBinding> extends Fragment implements BaseView {

    protected B mBinding;
    protected View mView;
    protected Activity mActivity;
    protected Context mContext;
    protected boolean isInited = false;
    protected LoadingDialog mLoadingDialog;

    private boolean mNeedLoad = true; // 是否需要加载
    private boolean mViewCreated;  // View 是否被创建

    /**
     * 当fragment被用户可见时，setUserVisibleHint()会调用且传入true值，
     * 当fragment不被用户可见时，setUserVisibleHint()则得到false值。
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        loadIfNeed();
    }

    private void loadIfNeed() {
        if (getUserVisibleHint() && mNeedLoad && mViewCreated) {
            mLoadingDialog = new LoadingDialog(getActivity());
            initView();
            initEventAndData();
            mNeedLoad = false;
        }
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initEventAndData();

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return mView = mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewCreated = true;
        //        由于setUserVisibleHint优于onCreate调用，所以当onCreate调用完毕setUserVisibleHint就不会触发，使用这个方法可能会有一些问题，因为这个方法是在onCreate方法之后运行的，一进来有时候可能看不到第一页的数据，这个时候
        //        你可以在onCreate 或者是onCreateView里面进行判断
        //        if (getUserVisibleHint()) {
        //            //加载数据相当于Fragment的onPause
        //        }
        loadIfNeed();
    }

    protected void setToolBar(Toolbar toolbar) {
        setHasOptionsMenu(true);
        toolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity = null;
    }

}
