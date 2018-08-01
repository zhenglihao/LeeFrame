package com.example.lee.leeframe.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Toast;

import com.example.lee.leeframe.HomeActivity;
import com.example.lee.leeframe.R;
import com.example.lee.leeframe.app.AppSp;
import com.example.lee.leeframe.base.BaseActivity;
import com.example.lee.leeframe.bean.LoginBean;
import com.example.lee.leeframe.bean.requestbean.ThirdLoginRequestBean;
import com.example.lee.leeframe.databinding.ActivityLoginBinding;
import com.example.lee.leeframe.mvp.contract.LoginContract;
import com.example.lee.leeframe.mvp.presenter.LoginPresenter;
import com.example.lee.leeframe.utils.ToastUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;


public class LoginActivity extends BaseActivity<ActivityLoginBinding> implements
        LoginContract.View, View.OnClickListener {

    private boolean isPwVisitable;
    private LoginPresenter mPresenter;
    private UMShareAPI mShareAPI;

    @Override
    protected ActivityLoginBinding createDataBinding(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_login);
    }

    @Override
    protected void initView() {

        // UM(第三方登录)设置每次登录拉取确认界面
        initThirdLogin();

    }

    /**
     * UM(第三方登录)设置每次登录拉取确认界面
     */
    private void initThirdLogin() {

        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true); // 需要认证登录
        mShareAPI = UMShareAPI.get(LoginActivity.this);
        mShareAPI.setShareConfig(config);
    }


    @Override
    protected void initEventAndData() {
        mBinding.setOnClickListener(this);
        mPresenter = new LoginPresenter(this);

        if (AppSp.getMember() != null) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }

    }

    @Override
    public void showError(String msg) {
        if (mLoadingDialog.isShowing()) {
            mLoadingDialog.dissmiss();
        }
        ToastUtils.showShortToast(mContext, msg);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            //注册
            case R.id.tv_action_right:
                // todo
                break;
            //密码可视
            case R.id.iv_visit_pw:
                if (isPwVisitable) {
                    isPwVisitable = false;
                    // 隐藏
                    mBinding.etPw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mBinding.ivVisitPw.setImageResource(R.mipmap.icon_register_eye);
                } else {
                    isPwVisitable = true;
                    // 显示
                    mBinding.etPw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mBinding.ivVisitPw.setImageResource(R.mipmap.icon_register_eyes);
                }
                break;
            case R.id.tv_login:
                String username = mBinding.etAccount.getText().toString();
                String password = mBinding.etPw.getText().toString();
                mLoadingDialog.showUnCalcel();
                mPresenter.login(username, password);
                break;
            //游客登录：
            case R.id.tv_visitor:
                // todo
                break;
            //忘记密码：
            case R.id.tv_forget_pw:
                // todo
                break;
            // 三方登录：
            case R.id.iv_wechat_login:

                mShareAPI.getPlatformInfo(LoginActivity.this,
                        SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
            default:
                break;
        }
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            //微信
            // uid （6.2以前用unionid）用户id
            // accessToken （6.2以前用access_token）
            // RefreshToken（6.2以前用refresh_token）
            // expiration （6.2以前用expires_in）过期时间

            Toast.makeText(mContext, "授权成功", Toast.LENGTH_SHORT).show();

            String openId = data.get("openid");  // 不同于uid（用户id）
            String accessToken = data.get("accessToken");
            String expiration = data.get("expiration");  // 过期时间
            String nickName = data.get("name");

            // 顾问端是2，客户端是3
            int appID = 3;
            mPresenter.thirdLogin(nickName, openId,
                    new ThirdLoginRequestBean(openId, accessToken, appID, expiration));
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            Toast.makeText(mContext, "授权失败：" + t.getMessage(), Toast.LENGTH_SHORT).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(mContext, "取消授权", Toast.LENGTH_SHORT).show();
        }
    };


    /**
     * @param intentClass
     */
    private void jumpIntent(Class intentClass) {
        startActivity(new Intent(this, intentClass));
    }

    @Override
    public void onLoginSuccess(LoginBean bean) {
        if (mLoadingDialog != null) {
            mLoadingDialog.dissmiss();
        }
        if (!TextUtils.isEmpty(bean.getImAccountName())) {
            AppSp.saveIMLoginCount(bean.getImAccountName());
            AppSp.saveIMPassword(bean.getImPassword());
        } else {
            mPresenter.registerImUsers();
        }
        String username = mBinding.etAccount.getText().toString();
        String password = mBinding.etPw.getText().toString();
        AppSp.saveLoginCount(username);
        AppSp.savePassword(password);
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public void onThirdLoginLoaded(LoginBean bean) {

        // 已经三方授权且 绑定
        AppSp.saveMember(bean);
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onRegisterIMSuccess() {

    }

    @Override
    protected void onDestroy() {
        mPresenter.unSubscribe(); // 界面销毁时 取消订阅事件
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}
