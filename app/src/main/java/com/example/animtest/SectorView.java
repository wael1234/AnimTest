package com.example.animtest;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.DecelerateInterpolator;

/**
 * Description:
 * Copyright  : Copyright (c) 2018
 * Company    : 普华
 * Author     : hjk
 * Date       : 2019/1/28 17:30
 */
public class SectorView extends SurfaceView implements SurfaceHolder.Callback {
    private Paint mPaint;
    private int mLineHeight = 5;
    private ValueAnimator mSectorController;//画圆的控制器
    private AnimatorSet animatorSet;

    private int DEFAULT_SECTOR_DURATION = 1000;//画圆的时间
    public static final int STATE_SECTOR = 3;//向上状态

    public static final int DEFAULT_LINE_COLOR = Color.parseColor("#ffffff");

    float mSectorDistance;
    private boolean isAnimationShowing = false;
    private int state;

    public SectorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public SectorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }






    private void initView(Context context, AttributeSet attrs) {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(DEFAULT_LINE_COLOR);
        mPaint.setStrokeWidth(mLineHeight);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);


        getHolder().addCallback(this);

        initController();

    }

    private void initController() {

        mSectorController = ValueAnimator.ofFloat(0, 1);
        mSectorController.setDuration(DEFAULT_SECTOR_DURATION);
//        mSectorController.setInterpolator(new DecelerateInterpolator());
        mSectorController.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSectorDistance = (float)animation.getAnimatedValue();
                postInvalidate();
//                invalidate();
            }
        });
        mSectorController.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                state = STATE_SECTOR;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimationShowing = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        animatorSet = new AnimatorSet();
        animatorSet.play(mSectorController);//.before(mUpController);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimationShowing = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    /**
     * 启动动画,外部调用
     */
    public void startAnimations() {
        if (isAnimationShowing) {
            return;
        }
        if (animatorSet.isRunning()) {
            animatorSet.end();
            animatorSet.cancel();
        }
//        isBounced = false;
//        isBallFreeUp = false;
//        ismUpControllerDied = false;

        animatorSet.start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        Log.i("111111","onDraw:"+mWavyDistance);
//        mPaint.setColor(DEFAULT_SECTOR_DURATION);


        float x = getWidth()/2;
        float y = getHeight()/2;
        RectF oval = new RectF( x-100,y-100,x+100,y+100);
        canvas.drawArc(oval,270,360*mSectorDistance,false,mPaint);


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = holder.lockCanvas();//锁定整个SurfaceView对象,获取该Surface上的Canvas.
        draw(canvas);
        holder.unlockCanvasAndPost(canvas);//释放画布，提交修改
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }
}
