package com.programbao.vlc_video;

// Source code is unavailable, and was generated by the Fernflower decompiler.

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;

//import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;
import org.videolan.libvlc.interfaces.IVLCVout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;

//import javax.naming.Context;

public class VlcVideoV2 extends WXComponent<RelativeLayout> {
    private String TAG = "VlcVideo";
    private LibVLC mLibVLC;
    private MediaPlayer mMediaPlayer;

    private SurfaceView surfaceView;

    private RelativeLayout vlcContainerView;

    RelativeLayout mRootView;

    /*顶部控件*/
    private LinearLayout mControlTopLayout;
    /*底部控件*/
    private RelativeLayout mControlBottomLayout;
    /*右边控件*/
    private LinearLayout mControlRightLayout;
    /*中间倍速选择控件*/
    private LinearLayout mControlMiddleSpeedLayout;
    /*倍速控件*/
    private TextView mSpeedBtn;
    /*倍速选择组控件*/
    private RadioGroup radioGroupView;

    /*顶部返回控件*/
    private ImageView mTopBack;
    /*标题*/
    private TextView mTopTitle;
    /*时间文本*/
    private TextView vlcDuration;
    /*进度条*/
    private SeekBar vlcSeekbar;

    public String mTitle = "我是标题";

    private ImageView mPlayBtn;


    private IVLCVout vlcVout;
    Activity activity; // 在初始化时传入

    private boolean isFullscreen = false; // 标记是否处于全屏模式
    private boolean isPlay = false; // 标记是否处于播放
    private boolean isShowMiddleSelectSpeed = false; // 标记是否处于倍速控件选择
    ImageView fullControl;
    private OrientationEventListener orientationEventListener;  // 横竖屏变化事件监听

    public VlcVideoV2(WXSDKInstance instance, WXVContainer parent, BasicComponentData basicComponentData) {
        super(instance, parent, basicComponentData);
        activity = (Activity) instance.getContext();
    }

    @Override
    protected RelativeLayout initComponentHostView(Context context) {
        initPlay(context);
        // 添加返回键事件监听器
//        vlcContainerView.setFocusableInTouchMode(true);
//        vlcContainerView.requestFocus();
//        vlcContainerView.setOnKeyListener(this);
        return mRootView;
    }


    public void initPlay(Context context) {
        Object localObject = new ArrayList();
        ((ArrayList) localObject).add("-vvv");
        ((ArrayList) localObject).add("--no-drop-late-frames");
        ((ArrayList) localObject).add("--no-skip-frames");
        ((ArrayList) localObject).add("--rtsp-tcp");
        ((ArrayList) localObject).add("--avcodec-hw=any");
        ((ArrayList) localObject).add("--live-caching=0");
        mLibVLC = new LibVLC(context, (ArrayList<String>) localObject);
        mMediaPlayer = new MediaPlayer(mLibVLC);
        VlcVideoView vlcVideoView = new VlcVideoView(context);

        /*获取布局元素*/
        surfaceView = vlcVideoView.getVideoView();
        vlcContainerView = vlcVideoView.getVlcContainerView();
        mRootView = vlcVideoView.getmRootView();
        mControlTopLayout = vlcVideoView.getmControlTopLayout();
        mControlBottomLayout = vlcVideoView.getmControlBottomLayout();
        mControlRightLayout = vlcVideoView.getmControlRightLayout();
        mControlMiddleSpeedLayout = vlcVideoView.getmControlMiddleSpeedLayout();
        mTopBack = vlcVideoView.getmTopBack();
        mTopTitle = vlcVideoView.getmTopTitle();
        mPlayBtn = vlcVideoView.getmPlayBtn();
        vlcDuration = vlcVideoView.getVlcDuration();
        vlcSeekbar = vlcVideoView.getVlcSeekbar();
        fullControl = vlcVideoView.getFullControll();
        mSpeedBtn = vlcVideoView.getmSpeedBtn();
        radioGroupView = vlcVideoView.getRadioGroupView();

        vlcVout = mMediaPlayer.getVLCVout();


        // 设置各种控件状态
        /*默认隐藏*/
        mTopBack.setVisibility(GONE);
        mControlMiddleSpeedLayout.setVisibility(GONE);
        mRootView.setOnTouchListener(new OnDoubleClickListener(mMediaPlayer, new OnDoubleClickListener.DoubleClickCallback() {
            @Override
            public void onDoubleClick() {
                handlePlay();//处理双击事件
            }

            @Override
            public void onClick() {
                // 先移除之前发送的
                mRootView.removeCallbacks(mShowControllerRunnable);
                mRootView.removeCallbacks(mHideControllerRunnable);
                if (mControllerShow) {
                    // 隐藏控制面板
                    mRootView.post(mHideControllerRunnable);
                } else {
                    // 显示控制面板
                    mRootView.post(mShowControllerRunnable);
                    mRootView.postDelayed(mHideControllerRunnable, 3000);
                }
            }

            @Override
            public void onTouch(View v, MotionEvent event, float touchStartX, long startPosition) {
//                System.out.println("mMediaPlayer.getTime() --" + mMediaPlayer.getTime() + "mMediaPlayer.getLength()--"+mMediaPlayer.getLength());
//                touchStartX = event.getX();
                System.out.println("event.getAction() --- " + event.getAction());
                float touchEndX = event.getX();
                float deltaX = touchEndX - touchStartX;
                // 根据滑动的距离来调整时间
                long newPosition = startPosition + (long) (deltaX * mMediaPlayer.getLength() / v.getWidth()) / 10;
                if (newPosition < 0) {
                    newPosition = 0;
                }
                if (newPosition > mMediaPlayer.getLength()) {
                    newPosition = mMediaPlayer.getLength();
                }
                // 更新进度条位置
                vlcSeekbar.setProgress((int) (newPosition * 100 / mMediaPlayer.getLength()));
                // 更新电影对象的位置
                mMediaPlayer.setTime(newPosition);
            }
        }));



        /*播放控件点击*/
        mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePlay();
            }
        });

        /*全屏控件点击事件*/
        fullControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在按钮点击时执行的代码
                // 例如：处理按钮点击事件
                handleFullScreenStatus();
            }
        });
        /*顶部返回控件点击事件*/
        mTopBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFullscreen) exitFullScreen(null, null);
            }
        });
        /*进度条控件改变事件*/
        vlcSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.v("NEW POS", "pos is ----  : " + i);
                if (i != 0)
                    mMediaPlayer.setPosition(((float) i / 100.0f));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        /*倍速控件点击*/
        mSpeedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*显示中间倍速选择控件*/
                if (isShowMiddleSelectSpeed) {
                    mControlMiddleSpeedLayout.setVisibility(GONE);
                    isShowMiddleSelectSpeed = false;
                } else {
                    mControlMiddleSpeedLayout.setVisibility(VISIBLE);
                    isShowMiddleSelectSpeed = true;
                }
            }
        });
        radioGroupView.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                System.out.println("checkedIdcheckedId ---" + checkedId);
                // 在这里处理选中的RadioButton变化
                if (checkedId == R.id.radioButton1) {// 选中了0.5x，执行相应的操作，例如更改播放速度
//                        changePlaybackSpeed(0.5f);
                } else if (checkedId == R.id.radioButton2) {// 选中了1.0x，执行相应的操作
//                        changePlaybackSpeed(1.0f);
                } else if (checkedId == R.id.radioButton3) {// 选中了1.25x，执行相应的操作
//                        changePlaybackSpeed(1.25f);
                } else if (checkedId == R.id.radioButton4) {// 选中了1.5x，执行相应的操作
//                        changePlaybackSpeed(1.5f);
                } else if (checkedId == R.id.radioButton5) {// 选中了2.0x，执行相应的操作
//                        changePlaybackSpeed(2.0f);
                }
            }
        });


        mMediaPlayer.setEventListener(new MediaPlayer.EventListener() {
            @Override
            public void onEvent(MediaPlayer.Event event) {
                // 处理播放事件
                videoPlayerEvent(event);
            }
        });


    }

    @WXComponentProp(name = "play")
    public void play(String path) {
        mMediaPlayer.stop();
        vlcVout.detachViews();
        surfaceView.post(new Runnable() {
            @Override
            public void run() {
                // 在子线程中准备播放
                int width = surfaceView.getWidth();
                int height = surfaceView.getHeight();
                System.out.println("width:" + width + " height:" + height);
                vlcVout.setVideoSurface(surfaceView.getHolder().getSurface(), surfaceView.getHolder());
                vlcVout.setWindowSize(width, height);
                vlcVout.attachViews();
                try {
                    Media media = new Media(mLibVLC, Uri.parse(path));
                    media.setHWDecoderEnabled(true, false);
                    media.addOption(":network-caching=100");
                    media.addOption(":clock-jitter=0");
                    media.addOption(":clock-synchro=0");
                    media.addOption(":fullscreen");
                    mMediaPlayer.setAspectRatio(width + ":" + height);
                    mMediaPlayer.setMedia(media);
                    media.release();
                } catch (Exception var4) {
                    throw new RuntimeException("Invalid asset folder");
                }
//                System.out.println("vlcContainerView.getHeight: " + vlcContainerView.getHeight());
//                // 创建一个定时器,延迟3秒执行
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        enterFullScreen();
//                    }
//                }, 3000); // 3秒delay
////
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        exitFullScreen();
//                    }
//                }, 10000);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        vlcContainerView.postDelayed(mHideControllerRunnable, 3000);
                    }
                }, 3000); // 3秒delay
                mMediaPlayer.play();
                isPlay = true;
                /*屏幕常亮*/
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                /*关闭常亮*/
                // activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        });
    }

    public void handlePlay() {
        if (isPlay) {
            pause(null, null);
            mPlayBtn.setImageResource(R.drawable.player_play);
        } else {
            toPlay();
            mPlayBtn.setImageResource(R.drawable.player_pause);
        }
    };

    /*处理是否进入或退出全屏*/
    public void handleFullScreenStatus() {
        if (isFullscreen) {
            fullControl.setImageResource(R.drawable.nur_ic_fangda);
            exitFullScreen(null, null);
        } else {
            enterFullScreen(null, null);
            fullControl.setImageResource(R.drawable.nur_ic_fangxiao);
        }
    }


    private boolean mControllerShow = true;
    /**
     * 显示控制面板
     */
    private final Runnable mShowControllerRunnable = () -> {
        if (!mControllerShow) {
            showController();
        }
    };

    /**
     * 隐藏控制面板
     */
    private final Runnable mHideControllerRunnable = () -> {
        if (mControllerShow) {
            hideController();
        }
    };

    /**
     * 显示面板
     */
    public void showController() {
        if (mControllerShow) {
            return;
        }
        mControllerShow = true;
        ObjectAnimator.ofFloat(mControlTopLayout, "translationY", -mControlTopLayout.getHeight(), 0).start();
        ObjectAnimator.ofFloat(mControlBottomLayout, "translationY", mControlBottomLayout.getHeight(), 0).start();
        ObjectAnimator.ofFloat(mControlRightLayout, "translationX", mControlRightLayout.getWidth() + mControlRightLayout.getResources().getDimension(R.dimen.padding_5), 0).start();

        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(200);
        animator.addUpdateListener(animation -> {
            float alpha = (float) animation.getAnimatedValue();
//            mControlLeftLayout.setAlpha(alpha);

            if ((int) alpha != 1) {
                return;
            }
//            if (mControlLeftLayout.getVisibility() == INVISIBLE) {
//                mControlLeftLayout.setVisibility(VISIBLE);
//            }
        });
        animator.start();
    }


    /**
     * 隐藏面板
     */
    public void hideController() {
        if (!mControllerShow) {
            return;
        }
        mControllerShow = false;
        ObjectAnimator.ofFloat(mControlTopLayout, "translationY", 0, -mControlTopLayout.getHeight()).start();
        ObjectAnimator.ofFloat(mControlBottomLayout, "translationY", 0, mControlBottomLayout.getHeight()).start();
        ObjectAnimator.ofFloat(mControlRightLayout, "translationX", 0, mControlRightLayout.getWidth() + mControlRightLayout.getResources().getDimension(R.dimen.padding_5)).start();
        ValueAnimator animator = ValueAnimator.ofFloat(1f, 0f);
        animator.setDuration(200);
        animator.addUpdateListener(animation -> {
            float alpha = (float) animation.getAnimatedValue();
//            mControlLeftLayout.setAlpha(alpha);
            if (alpha != 0f) {
                return;
            }

//            if (mControlLeftLayout.getVisibility() == VISIBLE) {
//                mControlLeftLayout.setVisibility(INVISIBLE);
//            }
        });
        animator.start();
    }


    /*进入全屏*/
    @JSMethod
    public void enterFullScreen(JSONObject options, JSCallback callback) {
        if (isFullscreen) return;
        isFullscreen = true;
        /*剥离视图*/
        this.vlcVout.detachViews();
        // 横屏
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        hideNavigationBar();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*进入全屏*/
                activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                ViewGroup localViewGroup = (ViewGroup) activity.getWindow().getDecorView();
                ((ViewGroup) getParent().getHostView()).removeView(getHostView());
                FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-1, -1);
                localViewGroup.addView(getHostView(), localLayoutParams);
                adjustSurfaceView();
                if (callback != null) {
                    callback.invoke(isFullscreen);
                }
                /*显示顶部返回控件*/
                mTopBack.setVisibility(VISIBLE);
            }
        }, 500);

    }

    /*退出全屏*/
    @JSMethod
    public void exitFullScreen(JSONObject options, JSCallback callback) {
        if (!isFullscreen) return;
        isFullscreen = false;
        // 竖屏
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        showNavigationBar();
        // 退出全屏
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                ((ViewGroup) activity.getWindow().getDecorView()).removeView(getHostView());
                ViewGroup localViewGroup = (ViewGroup) getParent().getHostView();
                View localView = getHostView();
                localViewGroup.addView(localView, new FrameLayout.LayoutParams(-1, -1));

                adjustSurfaceView();
                if (callback != null) {
                    callback.invoke(isFullscreen);
                }
            }
        }, 300);
    }

    // 隐藏导航栏
    public void hideNavigationBar() {
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    // 显示导航栏
    public void showNavigationBar() {
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    //    调整播放器视图
    public void adjustSurfaceView() {
        surfaceView.post(new Runnable() {
            @Override
            public void run() {
                // 在子线程中准备播放
                int width = surfaceView.getWidth();
                int height = surfaceView.getHeight();
                vlcVout.setVideoSurface(surfaceView.getHolder().getSurface(), surfaceView.getHolder());
                vlcVout.setWindowSize(width, height);
                vlcVout.attachViews();
                surfaceView.requestLayout();
                System.out.println("width ----" + width + " height ---" + height);
//                mMediaPlayer.setAspectRatio(width + ":" + height);
            }
        });
    }

    @JSMethod
    public void setPlayerTitle(String title) {
        this.mTitle = title;
        mTopTitle.setText(mTitle + "");
    }

    // 暂停
    @JSMethod
    public void pause(JSONObject options, JSCallback callback) {
        isPlay = false;
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
            isPlay = false;
        }
    }

    // 播放
    @UniJSMethod(uiThread = false)
    public void toPlay() {
        MediaPlayer localMediaPlayer = this.mMediaPlayer;
        if (localMediaPlayer != null) {
            localMediaPlayer.play();
            isPlay = true;
        }

    }

    // 终止
    @UniJSMethod(uiThread = false)
    public void stop() {
        mMediaPlayer.stop();
    }

    // 设置播放速率
    @JSMethod
    public void setRate(JSONObject paramJSONObject) {
        float rate = paramJSONObject.getFloatValue("rate");
        this.mMediaPlayer.setRate(rate);
    }


    // 生命周期方法
    @Override
    public void onActivityStart() {
        super.onActivityStart();
    }

    // 其他生命周期方法
    @Override
    public void onActivityStop() {
        super.onActivityStop();
        this.mMediaPlayer.stop();
        this.vlcVout.detachViews();
    }

    //    进入应用
    @Override
    public void onActivityResume() {
        super.onActivityResume();
        this.mMediaPlayer.play();
        adjustSurfaceView();
    }

    //    应用在后台
    @Override
    public void onActivityPause() {
        super.onActivityPause();
        this.mMediaPlayer.pause();
        this.vlcVout.detachViews();
    }

    @Override
    public void onActivityDestroy() {
        super.onActivityDestroy();
        mMediaPlayer.release();
        mLibVLC.release();
        // 在销毁时禁用方向监听器
        orientationEventListener.disable();
    }

    void callbackEvent(String paramString, Map paramMap) {
        int i = getEvents().size();
        int j = 0;
        int m;
        for (int k = 0; ; k++) {
            m = j;
            if (k >= i)
                break;
            if (!((String) getEvents().get(k)).equals(paramString))
                continue;
            m = 1;
            break;
        }
        if (m != 0) {
            HashMap localHashMap = new HashMap();
            if (paramMap != null)
                localHashMap.put("detail", paramMap);
            fireEvent(paramString, localHashMap);
        }
    }

    private void log(String paramString) {
        Log.e("VlcPlugin", paramString);
    }

    private String getVideoFomatTime(long milliseconds) {
//        long milliseconds = paramEvent.getLengthChanged(); // 这是VLC返回的时长值
        // 将毫秒转换为秒
        long seconds = milliseconds / 1000;

        // 计算小时、分钟和秒
        int hours = (int) (seconds / 3600);
        int minutes = (int) ((seconds % 3600) / 60);
        int remainingSeconds = (int) (seconds % 60);

        // 构建时间字符串
        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }

    private String totalTimeFormat;

    public void videoPlayerEvent(MediaPlayer.Event paramEvent) {
        int eventType = paramEvent.type;

        switch (eventType) {
            case 256:
                log("MediaChanged");
                break;
            case 258:
                callbackEvent("onOpening", null);
                break;
            case 262:
                callbackEvent("onPlayStopped", null);
                break;
            case 260:
                callbackEvent("onPlaying", null);
                break;
            case 261:
                callbackEvent("onPlayPaused", null);
                break;
            case 266:
                callbackEvent("onPlayError", null);
                break;
            case 267: // 播放中
                Map<String, Object> timeChangedMap = new HashMap<>();
                long currentMilliseconds = paramEvent.getTimeChanged(); // 这是VLC返回的时长值
                String currentTimeFormat = getVideoFomatTime(currentMilliseconds);
                timeChangedMap.put("time", Long.valueOf(currentMilliseconds));
                timeChangedMap.put("currentTimeFormat", currentTimeFormat);
                callbackEvent("onTimeChanged", timeChangedMap);
                vlcDuration.setText(currentTimeFormat + " / " + totalTimeFormat);
                vlcSeekbar.setProgress((int) (mMediaPlayer.getPosition() * 100));
                break;
            case 268:
                Map<String, Object> positionChangedMap = new HashMap<>();
                positionChangedMap.put("position", Float.valueOf(paramEvent.getPositionChanged()));
                callbackEvent("onPositionChange", positionChangedMap);
                break;
            case 269:
                log("SeekableChanged: " + paramEvent.getSeekable());
                break;
            case 270:
                log("PausableChanged: " + paramEvent.getPausable());
                break;
            case 276:
                callbackEvent("ESAdded", null);
                break;
            case 277:
                callbackEvent("ESDeleted", null);
                break;
            case 278:
                callbackEvent("ESSelected", null);
                break;
            case 273: // 获取视频总时长
                Map<String, Object> lengthChangedMap = new HashMap<>();
                long milliseconds = paramEvent.getLengthChanged(); // 这是VLC返回的时长值
                totalTimeFormat = getVideoFomatTime(milliseconds);
                lengthChangedMap.put("time", Long.valueOf(milliseconds));
                lengthChangedMap.put("totalTimeFormat", totalTimeFormat);
                callbackEvent("onTotalTime", lengthChangedMap);
                break;
            case 286:
                log("RecordChanged: " + paramEvent.getRecordPath());
                break;
            case 274:
                log("Vout: " + paramEvent.getVoutCount());
                break;
            default:
                // Handle other event types or ignore them
                break;
        }
    }


}