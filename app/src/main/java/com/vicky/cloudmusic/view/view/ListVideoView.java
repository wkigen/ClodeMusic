package com.vicky.cloudmusic.view.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.vicky.cloudmusic.Constant;
import com.vicky.cloudmusic.R;
import com.volokh.danylo.video_player_manager.ui.MediaPlayerWrapper;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;

/**
 * Created by vicky on 2017/9/13.
 */
public class ListVideoView extends RelativeLayout implements MediaPlayerWrapper.MainThreadMediaPlayerListener{

    ImageView backgroundImageView;
    ImageView playStatusImageView;

    public ListVideoView(Context context) {
        super(context);
        init();
    }

    public ListVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){

        setBackgroundColor(getContext().getResources().getColor(R.color.black));

        backgroundImageView= new ImageView(getContext());
        playStatusImageView = new ImageView(getContext());

        backgroundImageView.setScaleType(ImageView.ScaleType.FIT_XY);

        playStatusImageView.setImageResource(R.drawable.lock_btn_play);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);

        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp2.addRule(RelativeLayout.CENTER_IN_PARENT);

        addView(backgroundImageView, lp);
        addView(playStatusImageView, lp2);

    }

    public ImageView getBackgroundImageView(){
        return backgroundImageView;
    }

    public void setBackground(Bitmap bitmap){
        backgroundImageView.setImageBitmap(bitmap);
    }

    public void setStatus(int status){
        switch (status){
            case Constant.Status_Stop:
                backgroundImageView.setVisibility(VISIBLE);
                playStatusImageView.setVisibility(VISIBLE);
                playStatusImageView.setImageResource(R.drawable.lock_btn_play);
                break;
            case Constant.Status_Resume:
                backgroundImageView.setVisibility(GONE);
                playStatusImageView.setVisibility(GONE);
                break;
            case Constant.Status_Play:
                backgroundImageView.setVisibility(GONE);
                playStatusImageView.setVisibility(GONE);
                break;
            case Constant.Status_Pause:
                backgroundImageView.setVisibility(VISIBLE);
                playStatusImageView.setVisibility(VISIBLE);
                playStatusImageView.setImageResource(R.drawable.lock_btn_play);
                break;
            case Constant.Status_Prepare:
                backgroundImageView.setVisibility(GONE);
                playStatusImageView.setVisibility(GONE);
                break;
        }
    }


    @Override
    public void onVideoSizeChangedMainThread(int width, int height) {

    }

    @Override
    public void onVideoPreparedMainThread() {

    }

    @Override
    public void onVideoCompletionMainThread() {
        setStatus(Constant.Status_Stop);
    }

    @Override
    public void onErrorMainThread(int what, int extra) {
        setStatus(Constant.Status_Stop);
    }

    @Override
    public void onBufferingUpdateMainThread(int percent) {
    }

    @Override
    public void onVideoStoppedMainThread() {
        setStatus(Constant.Status_Stop);
    }

    @Override
    public void onVideoStartedMainThread(){
        setStatus(Constant.Status_Play);
    }

    @Override
    public void onVideoPausedMainThread(){
        setStatus(Constant.Status_Pause);
    }

}
