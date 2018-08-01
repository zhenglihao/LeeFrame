package com.example.lee.leeframe.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.lee.leeframe.app.App;
import com.example.lee.leeframe.app.Constants;
import com.example.lee.leeframe.utils.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * @Author Lee
 * @Time 2018/7/12
 * @Theme 微信支付的回调
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID, true);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        Log.e("", "onPayFinish, errCode = " + resp.errCode);
        int code = resp.errCode;
        switch (code) {
            //支付成功后的界面
            case 0:
                // todo
                break;
            case -1:
                ToastUtils.showShortToast(App.getContext(), "签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、您的微信账号异常等");
                finish();
                break;
            //用户取消支付后的界面
            case -2:
                ToastUtils.showShortToast(App.getContext(), "取消支付");
                finish();
                break;
            default:
                ToastUtils.showShortToast(App.getContext(), "支付失败");
                finish();
                break;
        }
        finish();
    }


}