package com.programbao.vlc_video;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class VlcVideoView extends FrameLayout {

    RelativeLayout mRootView;
    private SurfaceView mVideoView;

    private RelativeLayout vlcContainerView;

    private LinearLayout mControlTopLayout;
    private RelativeLayout mControlBottomLayout;

    private TextView mTopTitle;
    private ImageView mTopBack;


    private ProgressBar mProgressbar;

//    全屏控件
    private ImageView fullControll;

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

        mRootView = this.findViewById(R.id.programbao_vod_view);
        mVideoView = this.findViewById(R.id.programbao_video_view);
        vlcContainerView = this.findViewById(R.id.vlc_container);
//        mProgressbar = this.findViewById(R.id.polyv_loading_progress);
        fullControll = this.findViewById(R.id.iv_bottom_video_full);

        mControlTopLayout = findViewById(R.id.layout_control_top);
        mTopTitle = findViewById(R.id.tv_top_title);
        mTopBack = findViewById(R.id.iv_back);

        mControlBottomLayout = findViewById(R.id.layout_control_bottom);
//        mVideoView.setPlayerBufferingIndicator(mProgressbar);

    }

    public LinearLayout getmControlTopLayout() { return mControlTopLayout; };
    public RelativeLayout getmControlBottomLayout() { return mControlBottomLayout; };

    public SurfaceView getVideoView() {
        return mVideoView;
    }
    public RelativeLayout getVlcContainerView() {
        return vlcContainerView;
    }

    public RelativeLayout getmRootView() {return mRootView;}

    public ImageView getFullControll() {return fullControll;};
//    public PolyvMarqueeView getMarqueeView() {
//        return mMarqueeView;
//    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}
