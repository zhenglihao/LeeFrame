package com.example.lee.leeframe;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.example.lee.leeframe.base.BaseActivity;
import com.example.lee.leeframe.base.SimpleActivity;
import com.example.lee.leeframe.databinding.ActivityHomeBinding;
import com.example.lee.leeframe.ui.adapter.MyFragmentPagerAdapter;
import com.example.lee.leeframe.ui.fragment.EmptyFragment;
import com.example.lee.leeframe.ui.fragment.PersonalCenterFragment;

public class HomeActivity extends BaseActivity<ActivityHomeBinding> {


    @Override
    protected ActivityHomeBinding createDataBinding(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_home);
    }

    @Override
    protected void initView() {

        Fragment[] fragments = new Fragment[5];
        fragments[0] = EmptyFragment.newInstance();
        fragments[1] = EmptyFragment.newInstance();
        fragments[2] = EmptyFragment.newInstance();
        fragments[3] = EmptyFragment.newInstance();
        fragments[4] = new PersonalCenterFragment();

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        mBinding.vpFragments.setOffscreenPageLimit(4);
        mBinding.vpFragments.setAdapter(adapter);
        mBinding.bottomBar.setViewPager(mBinding.vpFragments);

        mBinding.bottomBar.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem item, int i, int i1) {


            }
        });

    }

    @Override
    protected void initEventAndData() {

    }


    @Override
    public void showError(String msg) {

    }

}
