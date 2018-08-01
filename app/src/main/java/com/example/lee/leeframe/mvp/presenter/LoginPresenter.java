package com.example.lee.leeframe.mvp.presenter;

import android.content.Intent;
import android.util.Log;

import com.example.lee.leeframe.api.http.ErrorHandler;
import com.example.lee.leeframe.api.http.Fault;
import com.example.lee.leeframe.api.http.PayLoad;
import com.example.lee.leeframe.app.App;
import com.example.lee.leeframe.base.BasePresenter;
import com.example.lee.leeframe.bean.LoginBean;
import com.example.lee.leeframe.bean.requestbean.ThirdLoginRequestBean;
import com.example.lee.leeframe.mvp.contract.LoginContract;
import com.example.lee.leeframe.mvp.model.LoginModel;
import com.example.lee.leeframe.ui.activity.RegisterActivity;

import rx.functions.Action1;


public class LoginPresenter extends BasePresenter implements LoginContract.Presenter {

    private LoginModel mModel;
    private LoginContract.View mView;

    public LoginPresenter(LoginContract.View view) {
        mView = view;
        mModel = new LoginModel();
    }

    @Override
    public void registerImUsers() {
        // 添加 订阅事件
        addSubscription(mModel.registerImUsers()
                .map(new PayLoad<Object>())
                .subscribe(
                        new Action1<Object>() {
                            @Override
                            public void call(Object bean) {
                                mView.onRegisterIMSuccess();
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                ErrorHandler.e(throwable, mView);

                            }
                        }
                ));
    }

    @Override
    public void login(String username, String password) {
        addSubscription(mModel.login(username, password)
                .map(new PayLoad<LoginBean>())
                .subscribe(
                        new Action1<LoginBean>() {
                            @Override
                            public void call(LoginBean bean) {
                                if (mModel.saveMember(bean)) {
                                    mView.onLoginSuccess(bean);
                                    Log.i("token", bean.getToken());
                                }
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                ErrorHandler.e(throwable, mView);

                            }
                        }
                ));
    }

    @Override
    public void thirdLogin(final String nickName, final String openId, ThirdLoginRequestBean requestBean) {

        addSubscription(mModel.thirdLogin(requestBean)
                .map(new PayLoad<LoginBean>())
                .subscribe(
                        new Action1<LoginBean>() {
                            @Override
                            public void call(LoginBean bean) {
                                mView.onThirdLoginLoaded(bean);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Fault fault = (Fault) throwable;
                                if (fault.getErrorCode().equals("500")) {
                                    // 跳转到注册界面
                                    Intent intent = new Intent(App.getContext(), RegisterActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("nickName", nickName);
                                    intent.putExtra("openId", openId);
                                    App.getContext().startActivity(intent);
                                }
                            }
                        }
                ));
    }

    // 三方登录

}
