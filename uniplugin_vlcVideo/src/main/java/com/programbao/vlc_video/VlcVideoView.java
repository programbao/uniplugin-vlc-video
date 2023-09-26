package com.programbao.vlc_video;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


public class VlcVideoView extends FrameLayout {

    RelativeLayout mRootView;
    private SurfaceView mVideoView;

    private FrameLayout vlcContainerView;
    private ProgressBar mProgressbar;


    public VlcVideoView(Context context) {
        super(context);
        initView();
    }

    public VlcVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public VlcVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.programbo_vlc_layout, this);

        mRootView = this.findViewById(R.id.polyv_vod_view);
        mVideoView = this.findViewById(R.id.polyv_video_view);
        vlcContainerView = this.findViewById(R.id.vlc_container);
//        mProgressbar = this.findViewById(R.id.polyv_loading_progress);

//        mVideoView.setPlayerBufferingIndicator(mProgressbar);

    }

    public SurfaceView getVideoView() {
        return mVideoView;
    }
    public FrameLayout getVlcContainerView() {
        return vlcContainerView;
    }
//    public PolyvMarqueeView getMarqueeView() {
//        return mMarqueeView;
//    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}
