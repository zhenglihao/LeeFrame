package com.example.lee.leeframe.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.lee.leeframe.AuthResult;
import com.example.lee.leeframe.PayResult;
import com.example.lee.leeframe.bean.WXProjectBean;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


import java.util.Map;

/**
 * @Author Lee
 * @Time 2018/7/12
 * @Theme 支付工具类（微信 和 支付宝）
 */

public class PayUtil {

    private static final PayUtil instance = new PayUtil();

    private PayUtil() {
        super();
    }

    public static PayUtil getInstance() {
        return instance;
    }

    // 默认
    public static final int PLACE_DEFAULT = 0x200;
    // 充值
    public static final int PLACE_RECHARGE = 0x201;
    // 购买商品
    public static final int PLACE_CART = 0x202;

    // 标记在哪个地方使用支付
    public int place = PLACE_DEFAULT;

    // 支付宝的Handler
    public static Handler mHandler;

    // 微信支付
    private IWXAPI api;

    /**
     * 确认微信支付
     */
    public void CheckWXPay(Activity activity, WXProjectBean result, int place) {
        this.place = place;
        // 判断当前微信版本是否支持微信支付
        api = WXAPIFactory.createWXAPI(activity, result.getAppid());
        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        if (isPaySupported) {
            //            MyApplication.getInstance().weChatPayFlag = MyApplication.SHOP;
            weChatPay(result);
        } else {
            ToastUtils.showShortToast(activity, "当前版本不支持微信支付，请升级微信后重试");
        }
    }

    /**
     * 微信支付
     */
    private void weChatPay(WXProjectBean result) {
        PayReq req = new PayReq();
        req.appId = result.getAppid();
        req.prepayId = result.getPrepayid();
        req.nonceStr = result.getNoncestr();
        req.timeStamp = result.getTimestamp();
        req.packageValue = result.getPackages();
        req.sign = result.getSign();
        req.partnerId = result.getPartnerid();

        boolean flag = api.sendReq(req);
    }

    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_AUTH_FLAG = 2;

    class AliPayHandler extends Handler {

        private Activity activity;

        public AliPayHandler(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    // 同步返回需要验证的信息
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(activity, "支付成功", Toast.LENGTH_SHORT).show();

                        // todo

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(activity, "支付失败", Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                case SDK_AUTH_FLAG: {

                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        ToastUtils.showShortToast(activity, "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()));
                    } else {
                        // 其他状态值则为授权失败
                        ToastUtils.showShortToast(activity, "授权失败" + String.format("authCode:%s"));
                    }
                    break;
                }
                default:
                    break;
            }
        }
    }


    /**
     * 支付宝支付
     */

    public void payV2(final Activity activity, final String resultInfo) {
        if (null == mHandler) {
            mHandler = new AliPayHandler(activity);
        }
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask aliPay = new PayTask(activity);
                Map<String, String> result = aliPay.payV2(resultInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
