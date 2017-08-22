package com.example.owner.testtwo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.BitmapCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Owner on 7/28/2017.
 */

public class GameView extends SurfaceView {

    private Bitmap bmp;
    private Bitmap bmp1;
    private Bitmap bmp2;
    private Bitmap bmp3;
    private Bitmap bmp4;
    private Bitmap bmp5;
    private Bitmap bmp6;
    private Bitmap bmp7;

    private GameCard gameCard;



    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread = null;
    private  int x = 400;

    public GameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        holder = getHolder();

        bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
        bmp1 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
        bmp2 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
        bmp3 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
        bmp4 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
        bmp5 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
        bmp6 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
        bmp7 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);

        gameCard = new GameCard(context);

        gameLoopThread = new GameLoopThread(this);
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                gameLoopThread.setRunning(true);
                gameLoopThread.start();

            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while(retry){
                    try{
                        gameLoopThread.join();
                        retry = false;
                    }catch (InterruptedException e){
                        //Add Log
                    }
                }

            }
        });
    }

//    public GameView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
//
//    public GameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }



    @Override
    public SurfaceHolder getHolder() {
        return super.getHolder();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean gatherTransparentRegion(Region region) {
        return super.gatherTransparentRegion(region);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(getResources().getColor(R.color.colorTeal));

        canvas.drawBitmap(bmp, x, 10, null);
        canvas.drawBitmap(bmp1, x, 600, null);
        canvas.drawBitmap(bmp2, x+300, 10, null);
        canvas.drawBitmap(bmp3, x+300, 600, null);
        canvas.drawBitmap(bmp4, x+600, 10, null);
        canvas.drawBitmap(bmp5, x+600, 600, null);
        canvas.drawBitmap(bmp6, x+900, 10, null);
        canvas.drawBitmap(bmp7, x+900, 600, null);




//        if(bmp != null) {
//            if (x < canvas.getWidth() - bmp.getWidth()) {
//                x++;
//            }
//        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    @Override
    public void setZOrderMediaOverlay(boolean isMediaOverlay) {
        super.setZOrderMediaOverlay(isMediaOverlay);
    }

    @Override
    public void setZOrderOnTop(boolean onTop) {
        super.setZOrderOnTop(onTop);
    }

    @Override
    public void setSecure(boolean isSecure) {
        super.setSecure(isSecure);
    }


//    public boolean onTouchEvent(MotionEvent event){
//        int eventAction = event.getAction();
//
//        int x =(int)event.getX();
//        int y =(int)event.getY();
//
//        switch(eventAction){
//            case MotionEvent.ACTION_DOWN:
//                bmp.setPixel(x , y , Color.BLACK);
//                break;
//            case MotionEvent.ACTION_UP:
//                bmp.setPixel(x , y , Color.CYAN);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                bmp.setPixel(x , y , Color.WHITE);
//                break;
//        }
//        invalidate();
//        return true;
//    }
}
