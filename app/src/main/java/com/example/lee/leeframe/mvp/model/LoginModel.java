package com.example.lee.leeframe.mvp.model;



import com.example.lee.leeframe.api.http.BaseResponse;
import com.example.lee.leeframe.api.http.ObjectLoader;
import com.example.lee.leeframe.app.App;
import com.example.lee.leeframe.app.AppSp;
import com.example.lee.leeframe.bean.LoginBean;
import com.example.lee.leeframe.bean.requestbean.ThirdLoginRequestBean;
import com.example.lee.leeframe.mvp.contract.LoginContract;

import rx.Observable;

/**
 * Created by hjx on 2018/04/25.
 * You can make it better
 */

public class LoginModel extends ObjectLoader implements LoginContract.Model {

    @Override
    public Observable<BaseResponse<LoginBean>> login(String username, String password) {
        return observe(App.getInstance().getApi().login(username, password));
    }

    @Override
    public Observable<BaseResponse<LoginBean>> thirdLogin(ThirdLoginRequestBean requestBean) {
        return observe(App.getInstance().getApi().thirdLogin(requestBean));
    }

    @Override
    public boolean saveMember(LoginBean loginBean) {
        boolean commit = AppSp.saveMember(loginBean);
        return commit;
    }

    @Override
    public Observable<BaseResponse<Object>> registerImUsers() {
        return observe(App.getInstance().getApi().registerImUsers(AppSp.getAccessToken()));
    }

}
