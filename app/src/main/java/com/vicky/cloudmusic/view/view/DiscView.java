package com.vicky.cloudmusic.view.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ThemedSpinnerAdapter;

import com.vicky.cloudmusic.R;
import com.vicky.cloudmusic.cache.CacheManager;
import com.vicky.cloudmusic.callback.ITouchCallback;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by vicky on 2017/9/1.
 */
public class DiscView extends View implements ValueAnimator.AnimatorUpdateListener {

    private static final long Roatation_Offest_Time = 50;
    private static final float Rotation_increase = 0.5f;
    private static final float Needle_Rotation_start = 0.0f;
    private static final float Needle_Rotation_send= -25.0f;

    private Bitmap needleBitmap;
    private Bitmap discBitmap;
    private Bitmap baseBitmap;

    private Point needlePoint = new Point();
    private Point discPoint = new Point();
    private Point basePoint = new Point();

    //中心点
    private Point needleCenterPoint = new Point();
    private Point discCenterPoint = new Point();
    private Point baseCenterPoint = new Point();

    //最终的矩阵
    private Matrix needleMatrix = new Matrix();
    private Matrix discMatrix = new Matrix();
    private Matrix baseMatrix = new Matrix();

    private float needleScale;
    private float discScale;
    private float baseScale;

    private float nedleScaleWidth;
    private float nedleScaleHeight;
    private float discScaleWidth;
    private float discScaleHeight;
    private float baseScaleWidth;
    private float baseScaleHeight;

    private float needleRotation = 0.f;
    private float discRatation = 0.0f;

    private ValueAnimator needlePlayAnimator;
    private ValueAnimator needlePauseAnimator;

    private boolean isPlaying = false;

    private ITouchCallback iTouchCallback;

    private Handler handler = new Handler();
    private Runnable runnable = new  Runnable(){
        @Override
        public void run() {
            if (isPlaying){
                discRatation += Rotation_increase;
                if (discRatation >= 360)
                    discRatation = 0;
                invalidate();
                handler.postDelayed(runnable,Roatation_Offest_Time);
            }
        }
    };

    public void setTouchCallback(ITouchCallback callback){
        this.iTouchCallback = callback;
    }

    public DiscView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init(){

        needleBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.play_needle);
        discBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.play_disc);
        baseBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.placeholder_disk_play_program);

        needlePlayAnimator = ValueAnimator.ofFloat(Needle_Rotation_send, Needle_Rotation_start);
        needlePlayAnimator.setDuration(300);
        needlePlayAnimator.addUpdateListener(this);
        needlePauseAnimator = ValueAnimator.ofFloat(Needle_Rotation_start, Needle_Rotation_send);
        needlePauseAnimator.setDuration(300);
        needlePauseAnimator.addUpdateListener(this);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        calLayout();
    }

    private void calLayout(){
        //计算把手
        needleScale = (float)getHeight() / 3 / (float)needleBitmap.getHeight();
        nedleScaleWidth = (float)needleBitmap.getWidth()*needleScale;
        nedleScaleHeight = (float)needleBitmap.getHeight()*needleScale;
        needlePoint.x = (int)((float)getWidth() / 2 - nedleScaleWidth / 6);
        needlePoint.y = -(int)(nedleScaleHeight/ 8);
        needleCenterPoint.x = (int)(nedleScaleWidth / 6);
        needleCenterPoint.y = (int)(nedleScaleHeight/ 8);


        //计算 唱片
        discScale = (float)getHeight() / 1.5f / (float)discBitmap.getHeight();
        discScaleWidth = (float)discBitmap.getWidth()*discScale;
        discScaleHeight = (float)discBitmap.getHeight()*discScale;
        discCenterPoint.x =(int)discScaleWidth / 2;
        discCenterPoint.y =(int)discScaleHeight / 2;
        discPoint.x = (int)(((float)getWidth() - discScaleWidth) / 2) ;
        discPoint.y = (int)(nedleScaleHeight / 2);

        //计算底片
        calBaseLasyout();
    }

    private void calBaseLasyout(){

        float tempScale = (float)discScaleHeight * 0.69f / (float)baseBitmap.getHeight();
        baseScale = (float)discScaleWidth * 0.69f / (float)baseBitmap.getWidth();
        baseScale = Math.min(baseScale,tempScale);

        baseScaleWidth = (float)baseBitmap.getWidth()*baseScale;
        baseScaleHeight = (float)baseBitmap.getHeight()*baseScale;
        baseCenterPoint.x =(int)baseScaleWidth / 2;
        baseCenterPoint.y =(int)baseScaleHeight / 2;
        basePoint.x= (int)((float)discPoint.x + (float)discScaleWidth / 2 - baseScaleWidth / 2);
        basePoint.y = (int)((float)discPoint.y + (float)discScaleHeight / 2 - baseScaleHeight / 2 );

    }

    @Override
    protected void onDraw(Canvas canvas) {

        //底片
        baseMatrix.setScale(baseScale,baseScale);
        baseMatrix.postRotate(discRatation,baseCenterPoint.x,baseCenterPoint.y);
        baseMatrix.postTranslate(basePoint.x,basePoint.y);
        canvas.drawBitmap(baseBitmap,baseMatrix,null);

        //唱片
        discMatrix.setScale(discScale,discScale );
        discMatrix.postRotate(discRatation,discCenterPoint.x,discCenterPoint.y);
        discMatrix.postTranslate(discPoint.x,discPoint.y);
        canvas.drawBitmap(discBitmap,discMatrix,null);

        //把手
        needleMatrix.setScale(needleScale,needleScale);
        needleMatrix.postRotate(needleRotation,needleCenterPoint.x,needleCenterPoint.y);
        needleMatrix.postTranslate(needlePoint.x,needlePoint.y);
        canvas.drawBitmap(needleBitmap,needleMatrix,null);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (iTouchCallback != null)
                    iTouchCallback.onTouchOnce();
                break;
        }
        return true;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        needleRotation =( float) valueAnimator.getAnimatedValue();
        invalidate();
    }

    public void setBitmap(Bitmap bitmap){
        if (bitmap == null)
            return;
        baseBitmap = bitmap;
        calBaseLasyout();
        invalidate();
    }

    public void start(){
        if (isPlaying)
            return;
        handler.postDelayed(runnable,Roatation_Offest_Time);
        needlePlayAnimator.start();
        isPlaying = true;
    }

    public void pause(){
        if (!isPlaying)
            return;
        handler.removeCallbacks(runnable);
        needlePauseAnimator.start();
        isPlaying = false;
    }

    public void stop(){

    }

}
