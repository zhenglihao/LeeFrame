package com.example.lee.leeframe.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lee.leeframe.R;
import com.example.lee.leeframe.app.App;

/**
 * @Author Lee
 * @Time 2018/7/12
 * @Theme UI工具类（dp px sp 转换 ）
 */

public class UiUtil {

    /**
     * 获取指定dp
     *
     * @param context
     * @param value   dp值
     * @return
     */
    public static float getDp(Context context, int value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }

    /**
     * 获取指定sp
     *
     * @param context
     * @param value   sp值
     * @return
     */
    public static float getSp(Context context, int value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, context.getResources().getDisplayMetrics());
    }

    //dp转px
    public static int getPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //px转dp
    public static int px2dip(int pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    //dp转px
    public static float dip2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (dipValue * scale + 0.5f);
    }


    public static String[] getStringArray(Context context, int stringArray) {
        return context.getResources().getStringArray(stringArray);
    }


    /**
     * 找到界面中的button和imagebutton设置点击事件
     *
     * @param view
     * @param listener
     */
    public static void findButtonAndSetOnClickListener(View view, View.OnClickListener listener) {
        if (view instanceof ViewGroup) {
            ViewGroup rootView = (ViewGroup) view;
            int childCount = rootView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = rootView.getChildAt(i);
                if (childView instanceof Button || childView instanceof ImageButton) {
                    childView.setOnClickListener(listener);
                } else {
                    findButtonAndSetOnClickListener(childView, listener);
                }

            }
        }
    }


    /**
     * 根据id获取颜色值
     *
     * @param color
     * @return
     */
    public static int getColor(int color) {
        return App.getContext().getResources().getColor(color);
    }

    /**
     * 根据id获取颜图片
     *
     * @param drawable
     * @return
     */
    public static Drawable getDrawable(int drawable) {
        return App.getContext().getResources().getDrawable(drawable);
    }

    /**
     * 移除EditText 焦点
     *
     * @param context
     * @param et
     */
    public static void removeEtViewFocus(Context context, EditText et) {
        InputMethodManager mInputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        et.setFocusable(false);//设置输入框不可聚焦，即失去焦点和光标
        if (mInputMethodManager.isActive()) {
            mInputMethodManager.hideSoftInputFromWindow(et.getWindowToken(), 0);// 隐藏输入法
        }
    }



}
