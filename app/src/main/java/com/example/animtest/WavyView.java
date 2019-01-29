package com.example.animtest;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.LinearInterpolator;

/**
 * Description:
 * Copyright  : Copyright (c) 2018
 * Company    : 普华
 * Author     : hjk
 * Date       : 2019/1/28 17:30
 */
public class WavyView extends SurfaceView implements SurfaceHolder.Callback {
    private Paint mPaint;
    private Paint mPaint2;
    private int mLineHeight = 5;
    private ValueAnimator mWavyController;//画圆的控制器
    private AnimatorSet animatorSet;
    public static final int DEFAULT_LINE_WIDTH = 50;
    private int DEFAULT_SECTOR_DURATION = 150;//画圆的时间
    public static final int STATE_SECTOR = 3;//向上状态
    int waveHeight = 5;

    public static final int DEFAULT_LINE_COLOR = Color.parseColor("#ffffff");
    Path mPath;

    private int mLineWidth;
    float mWavyDistance;
    private boolean isAnimationShowing = false;
    private int state;

    public WavyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public WavyView(Context context, AttributeSet attrs, int defStyleAttr) {
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

        mPaint2 = new Paint();
        mPaint2.setAntiAlias(true);
        mPaint2.setColor(Color.BLACK);
        mPaint2.setStrokeWidth(mLineHeight);
        mPaint2.setStrokeCap(Paint.Cap.ROUND);


        mPath = new Path();

        mLineWidth = DEFAULT_LINE_WIDTH;

        getHolder().addCallback(this);

        initController();

    }


    public void stopAnim() {
    }

    private void initController() {

        mWavyController = ValueAnimator.ofFloat(0, 1);
        mWavyController.setDuration(DEFAULT_SECTOR_DURATION);
        mWavyController.setInterpolator(new LinearInterpolator());
        mWavyController.setRepeatCount(-1);
//        mWavyController.setInterpolator(new DecelerateInterpolator());
        mWavyController.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mWavyDistance = (float) animation.getAnimatedValue();
                postInvalidate();
//                invalidate();
            }
        });
        mWavyController.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                state = STATE_SECTOR;
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

        animatorSet = new AnimatorSet();
        animatorSet.play(mWavyController);//.before(mUpController);
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
        mPath.reset();

        float x = getWidth() / 2 - mLineWidth  ;
        float y = getHeight() / 2;
        float xOff = mLineWidth / 10;


        float mDistance = mWavyDistance;
        if (mWavyDistance > 1) {
            mDistance = 1 - (mWavyDistance - 1);
        }
        x=x+mDistance*mLineWidth/2;
        mPath.moveTo(x, y);

        mPath.quadTo(x + xOff, y + waveHeight, x + xOff * 2, y);
        mPath.quadTo(x + xOff * 3, y - waveHeight, x + xOff * 4, y);
        mPath.quadTo(x + xOff * 5, y + waveHeight, x + xOff * 6, y);
        mPath.quadTo(x + xOff * 7, y - waveHeight, x + xOff * 8, y);
        mPath.quadTo(x + xOff * 9, y + waveHeight, x + xOff * 10, y);
        mPath.quadTo(x + xOff * 11, y - waveHeight, x + xOff * 12, y);
        mPath.quadTo(x + xOff * 13, y + waveHeight, x + xOff * 14, y);

        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(mPath, mPaint);
        Rect rLeft = new Rect( (int)(getWidth() / 2 - mLineWidth*2), (int)(y- waveHeight), (int)(getWidth() / 2 - mLineWidth/2), (int)(y+ waveHeight));
        Rect rRight = new Rect( (int)(getWidth() / 2 + mLineWidth/2), (int)(y- waveHeight), (int)(getWidth() / 2 + mLineWidth*2), (int)(y+ waveHeight));
//        canvas.drawOval(rectFLeft,mPaint2);
//        canvas.drawOval(rectRight,mPaint2);

        canvas.drawRect(rLeft,mPaint2);
        canvas.drawRect(rRight,mPaint2);

    }

    //    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
////        Log.i("111111","onDraw:"+mWavyDistance);
////        mPaint.setColor(DEFAULT_SECTOR_DURATION);
//        mPath.reset();
//        float x = getWidth() / 2- mLineWidth / 2;
//        float xOff =  mLineWidth / 8;
//        mPath.moveTo(x, getHeight() / 2);
////        mPath.quadTo(getWidth() / 2 - mLineWidth / 2,getHeight() / 2,getWidth() / 2 + mLineWidth / 2,getHeight() / 2);
//        float mDistance = mWavyDistance;
//        if(mWavyDistance>1){
//            mDistance = 1-(mWavyDistance-1);
//        }
//
////        mPath.quadTo(getWidth()/2- mLineWidth / 4 , getHeight() / 2+(mDistance*10-5), getWidth()/2, getHeight() / 2);
////        mPath.quadTo(getWidth()/2+ mLineWidth / 4 , getHeight() / 2+((1-mDistance)*10)-5, getWidth()/2+mLineWidth/2, getHeight() / 2);
//
//        mPath.quadTo(x+xOff,getHeight() / 2+(mDistance* waveHeight *2- waveHeight),x+xOff*2,getHeight() / 2);
//        mPath.quadTo(x+xOff*3, getHeight() / 2+((1-mDistance)* waveHeight *2)- waveHeight,x+xOff*4,getHeight() / 2);
//        mPath.quadTo(x+xOff*5, getHeight() / 2+(mDistance* waveHeight *2- waveHeight),x+xOff*6,getHeight() / 2);
//        mPath.quadTo(x+xOff*7, getHeight() / 2+((1-mDistance)* waveHeight *2)- waveHeight,x+xOff*8,getHeight() / 2);
//
//        mPaint.setStyle(Paint.Style.STROKE);
//        canvas.drawPath(mPath, mPaint);
//
//
//    }

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
