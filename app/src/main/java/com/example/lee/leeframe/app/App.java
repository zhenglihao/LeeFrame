package com.example.lee.leeframe.app;

import android.app.ActivityManager;
import android.content.Context;

import com.example.lee.leeframe.BuildConfig;
import com.example.lee.leeframe.api.YuApi;
import com.example.lee.leeframe.api.http.RetrofitServiceManager;
import com.example.lee.leeframe.utils.LogUtil;
import com.example.lee.leeframe.widget.GlideImageLoader;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * @Author Lee
 * @Time 2018/7/12
 * @Theme 整个APP入口
 */


public class App extends android.app.Application {

    private static App instance;

    private static final int MEMORY_SIZE = 5 * 1024 * 1024;
    private static final int DISK_SIZE = 20 * 1024 * 1024;

    private static Context mContext;
    public YuApi mApi;


    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
        instance = this;

        // 1 初始化SP
        AppSp.init(this);

        // 2 创建网络接口
        initApi();

        // 开启debug模式
        LogUtil.init(BuildConfig.DEBUG);

        // 有盟（分享，三方登录）
        initUmeng();

        // 微信支付
        initWechatPay();

        // 初始化 图片选择器
        initImagePicker();

        // 初始化 环信
        initEM();

    }

    private void initEM() {

        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        // 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
        options.setAutoTransferMessageAttachments(true);
        // 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
        options.setAutoDownloadThumbnail(true);
        //初始化
        EMClient.getInstance().init(mContext, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }

    private void initWechatPay() {
        // 1 注册APPID
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(mContext, Constants.WX_APP_ID, true);
        // 2. 将该app注册到微信
        msgApi.registerApp(Constants.WX_APP_ID);
    }

    private void initUmeng() {

        UMShareAPI.get(this);

        UMConfigure.setLogEnabled(true);  // 打印日志

        UMConfigure.init(this, Constants.UM_APPKEY,
                "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");

        // 在程序入口配置正确的ID号和秘钥（换成项目的id号和secrect秘钥）
        PlatformConfig.setWeixin(Constants.WX_APP_ID,
                Constants.WX_APP_SECRECT);  // 微信分享的ID号和秘钥需要用对应应用包申请随影配置信息

        // QQ：
        PlatformConfig.setQQZone(Constants.QQ_APP_ID, Constants.QQ_APP_KEY);
        // 微博：(参数3：回调地址)
        PlatformConfig.setSinaWeibo(Constants.SINA_APP_ID, Constants.SINA_APP_KEY,
                Constants.SINA_CALLBACK);

    }

    private void initApi() {
        mApi = RetrofitServiceManager.getInstance().create(YuApi.class);
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(false);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }



    /**
     * 获取APP单例模式
     *
     * @return
     */
    public static synchronized App getInstance() {
        return instance;
    }

    /**
     * 获取全局上下文环境
     *
     * @return
     */
    public static Context getContext() {
        return mContext;
    }


    /**
     * @return
     */
    public YuApi getApi() {
        return mApi;
    }


}
