package com.example.owner.testtwo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import java.io.IOException;

/**
 * Created by Owner on 6/23/2017.
 */

public class MovieActivity extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceView surfaceView = null;
    MediaPlayer mp = null;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)public MovieActivity(Context context, AttributeSet attributeSet, int defStyleAttr, int defStyleRes){
        super(context, attributeSet, defStyleAttr, defStyleRes);
        init();
    }

    public MovieActivity(Context context, AttributeSet attributeSet, int defStyleAttr){
        super(context, attributeSet, defStyleAttr);
        init();
    }

    public MovieActivity(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        init();
    }

    public MovieActivity(Context context){
        super(context);
        init();
    }

    private void init() {
        mp = new MediaPlayer();
        getHolder().addCallback(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        //TODO Actual RawFile HERE
//        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.mipmap.ic_launcher);
        try {
                mp.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ViewGroup.LayoutParams lp = surfaceView.getLayoutParams();
            lp.width = getWidth();
            lp.height = getHeight();
            setLayoutParams(lp);
            mp.setDisplay(surfaceHolder);
            mp.setLooping(true);
            mp.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mp.stop();
    }
}
