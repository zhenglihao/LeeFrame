package com.example.lee.leeframe.widget;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.lee.leeframe.R;

/**
 * @Author Lee
 * @Time 2018/7/13
 * @Theme 可以浏览大图的ImageView
 */

public class TransFromImageView extends android.support.v7.widget.AppCompatImageView {
    public enum Status {
        STATE_NORMAL,
        STATE_IN,
        STATE_OUT
    }

    private boolean transformStart;
    Status mStatus = Status.STATE_NORMAL;
    private Transform animTransform;
    private Transform startTransform;
    private Transform endTransform;
    private Paint mPaint;
    private int mBgColor = 0xFF000000;
    private Matrix matrix;

    //开始进入时上一个activity传过来范围
    private Rect startBounds;
    private Bitmap mBitmap;

    //存储 进入 和 退出 时的参数
    private class Transform implements Cloneable {
        float left, top, width, height;
        int alpha;
        float scale;

        @Override
        public Transform clone() {
            Transform obj = null;
            try {
                obj = (Transform) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return obj;
        }

        @Override
        public String toString() {
            return "Transform{" +
                    "left=" + left +
                    ", top=" + top +
                    ", width=" + width +
                    ", height=" + height +
                    ", alpha=" + alpha +
                    ", scale=" + scale +
                    '}';
        }
    }

    public TransFromImageView(Context context) {
        super(context);
    }

    public TransFromImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

        //初始化画笔，方便画背景
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mBgColor);

        //对图片进行操作
        matrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (getDrawable() == null) {
            return;
        }
        if (mStatus == Status.STATE_OUT || mStatus == Status.STATE_IN) {
            if (startTransform == null || endTransform == null || animTransform == null) {
                initTransform();
            }

            if (animTransform == null) {
                super.onDraw(canvas);
                return;
            }
            //初始化transform执行

            mPaint.setAlpha(animTransform.alpha);
            //画背景  类似canvas.drawColor(Color.BLUE)这样的用法
            canvas.drawPaint(mPaint);

            //对画布和图像进行操作
            int saveCount = canvas.getSaveCount();
            matrix.setScale(animTransform.scale, animTransform.scale);
            float translateX = -(mBitmap.getWidth() * animTransform.scale - animTransform.width) / 2;
            float translateY = -(mBitmap.getHeight() * animTransform.scale - animTransform.height) / 2;
            matrix.postTranslate(translateX, translateY);

            canvas.translate(animTransform.left, animTransform.top);
            canvas.clipRect(0, 0, animTransform.width, animTransform.height);
            canvas.concat(matrix);
            getDrawable().draw(canvas);
            canvas.restoreToCount(saveCount);

            if (transformStart) {
                startTransform();
            }


        } else {
            mPaint.setAlpha(255);
            canvas.drawPaint(mPaint);
            super.onDraw(canvas);
        }
    }

    private void startTransform() {
        transformStart = false;
        if (animTransform == null) {
            return;
        }

        ValueAnimator animator = new ValueAnimator();
        animator.setDuration(300);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        if (mStatus == Status.STATE_IN) {
            PropertyValuesHolder scaleHolder = PropertyValuesHolder.ofFloat("animScale", startTransform.scale, endTransform.scale);
            PropertyValuesHolder alphaHolder = PropertyValuesHolder.ofInt("animAlpha", startTransform.alpha, endTransform.alpha);
            PropertyValuesHolder leftHolder = PropertyValuesHolder.ofFloat("animLeft", startTransform.left, endTransform.left);
            PropertyValuesHolder topHolder = PropertyValuesHolder.ofFloat("animTop", startTransform.top, endTransform.top);
            PropertyValuesHolder widthHolder = PropertyValuesHolder.ofFloat("animWidth", startTransform.width, endTransform.width);
            PropertyValuesHolder heightHolder = PropertyValuesHolder.ofFloat("animHeight", startTransform.height, endTransform.height);
            animator.setValues(scaleHolder, alphaHolder, leftHolder, topHolder, widthHolder, heightHolder);
        } else if (mStatus == Status.STATE_OUT) {
            PropertyValuesHolder scaleHolder = PropertyValuesHolder.ofFloat("animScale", endTransform.scale, startTransform.scale);
            PropertyValuesHolder alphaHolder = PropertyValuesHolder.ofInt("animAlpha", endTransform.alpha, startTransform.alpha);
            PropertyValuesHolder leftHolder = PropertyValuesHolder.ofFloat("animLeft", endTransform.left, startTransform.left);
            PropertyValuesHolder topHolder = PropertyValuesHolder.ofFloat("animTop", endTransform.top, startTransform.top);
            PropertyValuesHolder widthHolder = PropertyValuesHolder.ofFloat("animWidth", endTransform.width, startTransform.width);
            PropertyValuesHolder heightHolder = PropertyValuesHolder.ofFloat("animHeight", endTransform.height, startTransform.height);
            animator.setValues(scaleHolder, alphaHolder, leftHolder, topHolder, widthHolder, heightHolder);
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animTransform.alpha = (Integer) animation.getAnimatedValue("animAlpha");
                animTransform.scale = (float) animation.getAnimatedValue("animScale");
                animTransform.left = (float) animation.getAnimatedValue("animLeft");
                animTransform.top = (float) animation.getAnimatedValue("animTop");
                animTransform.width = (float) animation.getAnimatedValue("animWidth");
                animTransform.height = (float) animation.getAnimatedValue("animHeight");
                invalidate();
            }
        });

        animator.start();

    }

    private void initTransform() {

        if (getDrawable() == null) {
            return;
        }
        if (startTransform != null && endTransform != null && animTransform != null) {
            return;
        }
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        if (mBitmap == null) {
            mBitmap = ((BitmapDrawable) getDrawable()).getBitmap();
        }

        //因为实在onGlobalLayout中之后调用的invalidate();图片已经加载完毕了
        if (mBitmap == null) {
            mBitmap = ((BitmapDrawable) getDrawable()).getBitmap();
        }

        int bitmapWidth = mBitmap.getWidth();
        int bitmapHeight = mBitmap.getHeight();
          /*
          刚开时进入时，前一个activity 中centerCrop的图片的位置，拷贝到现在页面的位置，再进行图片的变化
         */

        // 第一步----根据前一个activity图片的位置，完美复制到当前页面

        startTransform = new Transform();
        //刚开始进去的时候，背景色为0
        startTransform.alpha = 0;
        //图片顶部距离顶部的距离（除去状态栏）
        startTransform.top = startBounds.top - getStatusBarHeight(getContext());
        startTransform.left = startBounds.left;
        startTransform.width = startBounds.width();
        startTransform.height = startBounds.height();


        //第二部----计算图片在新页面显示后的位置

        //计算显示后的  长和宽的比例
        float startScaleX = ((float) startBounds.width()) / ((float) bitmapWidth);
        float startScaleY = ((float) startBounds.height()) / ((float) bitmapHeight);
        //这是执行放大的操作，比例大的那边完整放大，比例小的根据比例大的scale放大
        //这个比例放大是放大到图片真实尺寸的大小，因为是   startBounds.width() / bitmapWidth  哪个比例大哪个先达到图片真实的储存，所以取比例大的
        startTransform.scale = startScaleX > startScaleX ? startScaleX : startScaleY;

        //以上是进入时开始，参数的配置


        /*
        * 结束终点时：参数的配置
        */
        endTransform = new Transform();
        //因为设置的改view 的长宽是match-parent，图片会自动按比例放大，直到一边达到getWidth()或者getHeight()
        endTransform.alpha = 255;
        //得出是长、宽哪个边达到屏幕的极限,
        float endScaleX = (float) getWidth() / bitmapWidth;
        float endScaleY = (float) getHeight() / bitmapHeight;
        //这个比例放大是真实图片尺寸放大到屏幕的尺寸的大小，以为是 getWidth() / bitmapWidth  哪个比例小哪个首先达到屏幕的尺寸，所以取小的
        endTransform.scale = endScaleX > endScaleY ? endScaleY : endScaleX;

        //这样的 startTransform.scale--->endTransform.scale 就可以达到屏幕的过渡了
        int endBitmapWidth = (int) (endTransform.scale * bitmapWidth);
        int endBitmapHeight = (int) (endTransform.scale * bitmapHeight);

        //图片在中间显示
        endTransform.left = (getWidth() - endBitmapWidth) / 2;
        endTransform.top = (getHeight() - endBitmapHeight) / 2;
        endTransform.width = endBitmapWidth;
        endTransform.height = endBitmapHeight;

        //以上初始化参数完成

        Log.i("TransFromImageView", "initTransform:startTransform： " + startTransform.toString() + "\n\nendTransform:" + endTransform.toString());
        //判断是进入还是推出
        if (mStatus == Status.STATE_IN) {
            animTransform = startTransform.clone();
        }
        if (mStatus == Status.STATE_OUT) {
            animTransform = endTransform.clone();

        }
    }

    public void transformIn(Rect thunmbRect) {
        this.startBounds = thunmbRect;
        transformStart = true;
        mStatus = Status.STATE_IN;
        invalidate();
    }

    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        java.lang.reflect.Field field = null;
        int x = 0;
        int statusBarHeight = context.getResources().getDimensionPixelSize(R.dimen.top_bar_size);
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
            return statusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }


}