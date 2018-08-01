package com.example.lee.leeframe.api.http;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 将一些重复的操作提出来，放到父类以免Loader 里每个接口都有重复代码
 * Created by zhouwei on 16/11/10.
 */

public class ObjectLoader {
    /**
     * @param observable
     * @param <T>
     * @return
     */
    protected <T> Observable<T> observe(Observable<T> observable) {
         // 在子线程做订阅 和 取消订阅 的操作
         // 在主线程在 处理订阅事件
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
