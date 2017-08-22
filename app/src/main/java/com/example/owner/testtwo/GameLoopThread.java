package com.example.owner.testtwo;

import android.graphics.Canvas;

/**
 * Created by Owner on 7/28/2017.
 */

public class GameLoopThread extends Thread {

    private GameView view;
    private boolean running = false;


    public GameLoopThread(GameView inView){
        this.view = inView;
    }

    public void setRunning(boolean run){
        running = run;
    }


    @Override
    public void run() {
        while (running){
            Canvas canvas = null;
            try {
                canvas = view.getHolder().lockCanvas();
                synchronized (view.getHolder()){
                    view.draw(canvas);
                }
            }finally {
                if(canvas != null){
                    view.getHolder().unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
