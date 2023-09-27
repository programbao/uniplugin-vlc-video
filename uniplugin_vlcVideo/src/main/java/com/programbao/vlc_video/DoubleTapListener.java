package com.programbao.vlc_video;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class DoubleTapListener implements View.OnTouchListener {

    private GestureDetector gestureDetector;
    private View.OnClickListener singleClickListener;
    private View.OnClickListener doubleClickListener;

    public DoubleTapListener(Context context, View.OnClickListener singleClickListener, View.OnClickListener doubleClickListener) {
        this.singleClickListener = singleClickListener;
        this.doubleClickListener = doubleClickListener;
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            // 单击事件
            if (singleClickListener != null) {
                singleClickListener.onClick(null);
            }
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            // 双击事件
            if (doubleClickListener != null) {
                doubleClickListener.onClick(null);
            }
            return true;
        }
    }
}
