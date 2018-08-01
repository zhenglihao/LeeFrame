package com.example.lee.leeframe.utils;

import android.databinding.ObservableBoolean;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;

/**
 * Created by George on 2017/7/24.
 */

public final class AnimatorUtil {

    private AnimatorUtil() {
    }

    /**
     * 向下滑
     *
     * @param view
     */
    public static void doSlideDown(View view) {
        doSlideDown(view, 500);
    }

    /**
     * 向上滑
     *
     * @param view
     */
    public static void doSlideUp(View view) {
        doSlideUp(view, 500);
    }


    public static void doSlideDown(View view, int duration) {
        view.setVisibility(View.VISIBLE);
        Animation slideDown = setLayoutAnim_slidedown(duration);
        view.startAnimation(slideDown);
    }


    public static void doSlideUp(final View view, int duration) {
        Animation slideUp = setLayoutAnim_slideup(duration);
        view.startAnimation(slideUp);
        slideUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    private static Animation setLayoutAnim_slidedown(int duration) {
        AnimationSet set = new AnimationSet(true);

        Animation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        animation.setDuration(duration);
        set.addAnimation(animation);

        LayoutAnimationController controller = new LayoutAnimationController(
                set, 0.25f);

        return animation;
    }

    private static Animation setLayoutAnim_slideup(int duration) {
        AnimationSet set = new AnimationSet(true);

        Animation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f);
        animation.setDuration(duration);
        set.addAnimation(animation);

        LayoutAnimationController controller = new LayoutAnimationController(
                set, 0.25f);

        return animation;
    }

    public static void toggleSlide(View view, ObservableBoolean observableBoolean) {
        boolean toggle;
        observableBoolean.set(toggle = !observableBoolean.get());
        if (toggle) {
            AnimatorUtil.doSlideDown(view, 300);
        } else {
            AnimatorUtil.doSlideUp(view, 300);
        }
    }

}
