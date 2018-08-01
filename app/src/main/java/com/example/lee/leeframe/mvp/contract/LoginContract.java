package com.example.lee.leeframe.mvp.contract;

import com.example.lee.leeframe.api.http.BaseResponse;
import com.example.lee.leeframe.base.BaseView;
import com.example.lee.leeframe.bean.LoginBean;
import com.example.lee.leeframe.bean.requestbean.ThirdLoginRequestBean;

import rx.Observable;

/**
 * Created by hjx on 2018/04/25.
 * You can make it better
 */

public class LoginContract {

    public interface View extends BaseView {

        void onLoginSuccess(LoginBean bean);
        void onThirdLoginLoaded(LoginBean bean);
        void onRegisterIMSuccess();
    }

    public interface Presenter {

        void registerImUsers();
        void login(String username, String password);
        void thirdLogin(String nickName, String openId, ThirdLoginRequestBean requestBean);
    }

    public interface Model {

        Observable<BaseResponse<LoginBean>> login(String username, String password);
        Observable<BaseResponse<LoginBean>> thirdLogin(ThirdLoginRequestBean requestBean);
        boolean saveMember(LoginBean loginBean);
        Observable<BaseResponse<Object>> registerImUsers();
    }

}
