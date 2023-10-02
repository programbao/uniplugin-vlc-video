package com.programbao.vlc_video;

import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;

import org.videolan.libvlc.MediaPlayer;

public class OnDoubleClickListener implements View.OnTouchListener {

    private static final int DOUBLE_CLICK_DELAY = 200; // 双击的时间间隔，单位毫秒
    private static final int SINGLE_CLICK_DELAY = 300; // 单击的时间间隔，单位毫秒

    private int clickCount = 0; // 点击次数
    private long lastClickTime = 0; // 上次点击时间
    private boolean waitingForSecondClick = false; // 是否等待第二次点击
    private float touchStartX = 0;
    private long startPosition = 0;
    private DoubleClickCallback mCallback;

    private MediaPlayer mMediaPlayer;

    private long startTime;

    public interface DoubleClickCallback {
        void onDoubleClick();

        void onClick();

//        void onTouch();

        void onTouch(View v, MotionEvent event, float touchStartX, long startPosition);
    }

    public OnDoubleClickListener(MediaPlayer mMediaPlayer, DoubleClickCallback callback) {
        super();
        this.mCallback = callback;
        this.mMediaPlayer = mMediaPlayer;
    }

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable singleClickRunnable = new Runnable() {
        @Override
        public void run() {
            if (clickCount == 1) {
                mCallback.onClick();
            }
            clickCount = 0;
            waitingForSecondClick = false;
        }
    };

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (clickCount == 0) {
                    long currentTime = System.currentTimeMillis();
//                    System.out.println("currentTime--" + currentTime + "  lastClickTime--" + lastClickTime + "currentTime - lastClickTime " + (currentTime - lastClickTime) );
//                    if (lastClickTime <= 0) {
//                        lastClickTime = currentTime;
//                    }
//                    if (currentTime - lastClickTime <= SINGLE_CLICK_DELAY) {
//                        // 在一定时间内连续点击了，等待第二次点击
//                        waitingForSecondClick = true;
//                    } else {
////                        handler.postDelayed(singleClickRunnable, SINGLE_CLICK_DELAY);
//                    }
                    waitingForSecondClick = true;
                    // 单击
                    handler.postDelayed(singleClickRunnable, SINGLE_CLICK_DELAY);
                    lastClickTime = currentTime;
                    startTime = System.currentTimeMillis();
                } else if (clickCount == 1) {
                    long currentTime = System.currentTimeMillis();
                    if (waitingForSecondClick && (currentTime - lastClickTime <= DOUBLE_CLICK_DELAY)) {
                        // 连续点击了两次，且时间间隔在规定范围内，认为是双击
                        mCallback.onDoubleClick();
//                        handler.removeCallbacks(singleClickRunnable);
                        clickCount = 0;
                        waitingForSecondClick = false;
                    }
                }
                clickCount++;
                /*初始触摸相关的字段*/
                touchStartX = event.getX();
                startPosition = mMediaPlayer.getTime();
                break;
            case MotionEvent.ACTION_MOVE:
                // 在触摸移动事件中处理滑动事件
                long time = System.currentTimeMillis() - startTime;
                /*滑动持续500毫米才执行*/
                if (mCallback != null && time >= 200) {
                    mCallback.onTouch(v, event, touchStartX, startPosition);
                }
                break;
            case MotionEvent.ACTION_UP:
                // 在触摸事件结束时可以执行一些操作
                startTime = 0;
                break;
        }
        return true;
    }
}
