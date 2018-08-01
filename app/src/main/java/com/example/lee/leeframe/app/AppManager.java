package com.example.lee.leeframe.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import com.example.lee.leeframe.ui.activity.LoginActivity;

import java.util.LinkedList;

/**
 * @Author Lee
 * @Time 2018/7/12
 * @Theme Activity基础公共类
 */

public class AppManager {

    /**
     * 队列 （对头：删除； 队尾： 插入）
     */
    private static LinkedList<Activity> activityStack;
    // volatile： java 并发编程
    private volatile static AppManager instance;

    private AppManager() {

    }

    static {
        activityStack = new LinkedList();
    }

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            synchronized (AppManager.class) {
                if (instance == null) {
                    instance = new AppManager();
                }
            }

        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new LinkedList<>();
        }
        activityStack.push(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        try {
            Activity activity = activityStack.getLast();
            return activity;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前Activity的前一个Activity
     */
    public Activity preActivity() {
        int index = activityStack.size() - 2;
        if (index < 0) {
            return null;
        }
        Activity activity = activityStack.get(index);
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.getLast();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 移除指定的Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        try {
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    finishActivity(activity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();

    }

    /**
     * 返回到指定的activity
     *
     * @param cls
     */
    public void returnToActivity(Class<?> cls) {
        while (activityStack.size() != 0) {
            if (activityStack.peek().getClass() == cls) {
                break;
            } else {
                finishActivity(activityStack.peek());
            }
        }
    }


    /**
     * 是否已经打开指定的activity
     *
     * @param cls
     * @return
     */
    public boolean isOpenActivity(Class<?> cls) {
        if (activityStack != null) {
            for (int i = 0, size = activityStack.size(); i < size; i++) {
                if (cls == activityStack.peek().getClass()) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 跳转指定Activity并结束其他所有Activity
     */
    public void goToAndFinishAll(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
        finishAllActivity();
    }


    long preMillis = -1L;

    /**
     * 跳转到登录Activity
     */
    public void reLogin() {
        Activity act = null;
        if (activityStack.size() < 1 || System.currentTimeMillis() - preMillis < 1000) {
            return;
        }
        preMillis = System.currentTimeMillis();

        do {
            act = activityStack.pop();
        } while (act == null && activityStack.size() > 0);

        if (act == null) {
            return;
        }

        finishAllActivity();

        act.startActivity(new Intent(act, LoginActivity.class));
        act.finish();
    }

    //    /**
    //     * 重新登录
    //     */
    //    public void reLogin(Activity aAct) {
    //        for (int i = 0, size = activityStack.size(); i < size; i++) {
    //            if (null != activityStack.get(i)) {
    //                if (!aAct.equals(activityStack.get(i))) {
    //                    activityStack.get(i).finish();
    //                }
    //            }
    //        }
    //        activityStack.clear();
    //        activityStack.add(aAct);
    //        aAct.startActivity(new Intent(aAct, LoginActivity.class));
    //        aAct.finish();
    //    }


    /**
     * 退出应用程序
     *
     * @param context      上下文
     * @param isBackground 是否开开启后台运行
     */
    public void AppExit(Context context, Boolean isBackground) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
        } catch (Exception e) {

        } finally {
            // 注意，如果您有后台程序运行，请不要支持此句子
            if (!isBackground) {
                System.exit(0);
            }
        }
    }
}