package com.programbao.vlc_video;

// Source code is unavailable, and was generated by the Fernflower decompiler.

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

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

public class VlcVideo extends WXComponent<SurfaceView>  {
    private String TAG = "VlcVideo";
    private LibVLC mLibVLC;
    private MediaPlayer mMediaPlayer;

    private SurfaceView surfaceView;
    private IVLCVout vlcVout;
    Activity activity; // 在初始化时传入

    public VlcVideo(WXSDKInstance instance, WXVContainer parent, BasicComponentData basicComponentData) {
        super(instance, parent, basicComponentData);
        activity = (Activity) instance.getContext();
    }

    @Override
    protected SurfaceView initComponentHostView(Context context) {
        initPlay(context);
        return surfaceView;
    }

    public void initPlay(Context context) {
        Object localObject = new ArrayList();
        ((ArrayList)localObject).add("-vvv");
        ((ArrayList)localObject).add("--no-drop-late-frames");
        ((ArrayList)localObject).add("--no-skip-frames");
        ((ArrayList)localObject).add("--rtsp-tcp");
        ((ArrayList)localObject).add("--avcodec-hw=any");
        ((ArrayList)localObject).add("--live-caching=0");
        ((ArrayList)localObject).add(":fullscreen");
        mLibVLC = new LibVLC(context, (ArrayList<String>) localObject);
        mMediaPlayer = new MediaPlayer(mLibVLC);
        surfaceView = new SurfaceView(context);
        vlcVout = mMediaPlayer.getVLCVout();

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
                // 创建一个定时器,延迟3秒执行
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
////                        initVLC();
//                        changeToLandscape();
//                    }
//                }, 5000); // 3秒delay

//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
////                        initVLC();
////                        changeToLandscape();
////                        toggleFullscreen(true);
//
//                        // 创建全屏容器
////                        fullscreenContainer = new FrameLayout(getContext());
////                        fullscreenContainer.setLayoutParams(new FrameLayout.LayoutParams(
////                                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
//
//                        // 添加播放器视图到全屏容器
////                        View videoView = createVideoView(); // 创建播放器视图，这部分代码需要根据你的实际情况编写
////                        vlcContainerView.removeView(surfaceView);
//
//                        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
//
//                        new Handler().postDelayed(new Runnable() {
//                              @Override
//                              public void run() {
//                                  activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                                          WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                                  ViewGroup localViewGroup = (ViewGroup) activity.getWindow().getDecorView();
//                                  ((ViewGroup)getParent().getHostView()).removeView(getHostView());
//                                  FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-1, -1);
//                                  localViewGroup.addView(getHostView(), localLayoutParams);
//                                  surfaceView.post(new Runnable() {
//                                      @Override
//                                      public void run() {
//                                          new Handler().postDelayed(new Runnable() {
//                                              @Override
//                                              public void run() {
//                                                  // 在子线程中准备播放
//                                                  int width = surfaceView.getWidth();
//                                                  int height = surfaceView.getHeight();
//                                                  vlcVout.setVideoSurface(surfaceView.getHolder().getSurface(), surfaceView.getHolder());
//                                                  vlcVout.setWindowSize(width, height);
//                                                  vlcVout.attachViews();
//                                                  // setBringToFront();
//                                                  surfaceView.requestLayout();
//                                              }
//                                          }, 60000);
//                                      }
//                                  });
//                              }
//                        }, 200);
//
////                        videoView.setLayoutParams(new ViewGroup.LayoutParams(
////                                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
////                        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
////                        int screenWidth = displayMetrics.widthPixels;
////                        int screenHeight = displayMetrics.heightPixels;
////                        System.out.println("screenWidth:" + screenWidth + " screenHeight:" + screenHeight);
////                        // 设置SurfaceView的宽度和高度
//////                        surfaceView.getLayoutParams().width = screenWidth;
//////                        surfaceView.getLayoutParams().height = screenHeight;
////                        vlcVout.setWindowSize(screenWidth, screenHeight);
////                        surfaceView.requestLayout();
////                        fullscreenContainer.addView(videoView);
////
////                        // 将全屏容器添加到Activity的内容视图中
////                        activity.getWindow().addContentView(fullscreenContainer, fullscreenContainer.getLayoutParams());
//
//
//                    }
//                }, 3000); // 3秒delay

//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//////                        vlcContainerView.addView(surfaceView);
////                        ViewGroup localViewGroup = (ViewGroup)getParent().getHostView();
////                        View localView = getHostView();
////                        localViewGroup.addView(localView, new FrameLayout.LayoutParams(-1, -1));
////                        surfaceView.post(new Runnable() {
////                            @Override
////                            public void run() {
////                                // 在子线程中准备播放
////                                int width = surfaceView.getWidth();
////                                int height = surfaceView.getHeight();
//////                                vlcVout.setVideoSurface(surfaceView.getHolder().getSurface(), surfaceView.getHolder());
////                                vlcVout.setWindowSize(width, height);
//////                                vlcVout.attachViews();
////                                System.out.println("2222width:" + width + " 22222height:" + height);
//////                                setBringToFront();
////                                surfaceView.requestLayout();
////                            }
////                        });
//                    }
//                }, 6000);

                mMediaPlayer.play();
                //设置沉浸式观影模式体验
//                activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                //永远不息屏
                activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        });
    }
    public void initTextureView() {
        surfaceView.post(new Runnable()
        {
            public void run()
            {
                int i = surfaceView.getWidth();
                int j = surfaceView.getHeight();
                vlcVout.setWindowSize(i, j);
            }
        });
    }

//    @JSMethod
//    public void setFullScreen () {
//        // 获取屏幕宽度和高度
//        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
//        int screenWidth = displayMetrics.widthPixels;
//        int screenHeight = displayMetrics.heightPixels;
//        System.out.println("screenWidth:" + screenWidth + " screenHeight:" + screenHeight);
//        // 设置SurfaceView的宽度和高度
//        surfaceView.getLayoutParams().width = screenWidth;
//        surfaceView.getLayoutParams().height = screenHeight;
//        vlcVout.setWindowSize(screenHeight, screenWidth);
////        surfaceView.requestLayout();
////        vlcVout.setAspectRatio(screenWidth + ":" + screenHeight);
//        mMediaPlayer.setAspectRatio(screenWidth + ":" + screenHeight);
//    }

    /* 切换横屏 */
//    @JSMethod(uiThread = true)
//    public void changeToLandscape() {
//        Log.d(TAG, "changeToLandscape");
//        JSONObject result = new JSONObject();
//        if (getInstance().getContext() instanceof Activity) {
//            Activity context = (Activity) getInstance().getContext();
//            PolyvScreenUtils.setLandscape(context);
//            //初始为横屏时，状态栏需要隐藏
//            PolyvScreenUtils.hideStatusBar(context);
//            setFullScreen();
////            result.put("orientation", "portrait");
//        } else {
//            Log.e(TAG, "can not get Activity context");
//            result.put("errMsg", "can not get Activity context");
//        }
////        if (callback != null) {
////            callback.invoke(result);
////        }
//    }

    /* 切换竖屏 */
//    @JSMethod(uiThread = true)
//    public void changeToPortrait(JSONObject options, JSCallback callback) {
//        Log.d(TAG, "changeToPortrait");
//        JSONObject result = new JSONObject();
//        if (getInstance().getContext() instanceof Activity) {
//            Activity context = (Activity) getInstance().getContext();
//            PolyvScreenUtils.setPortrait(context);
//            PolyvScreenUtils.reSetStatusBar(context);
////            result.put("orientation", "portrait");
//        } else {
//            Log.e(TAG, "can not get Activity context");
//            result.put("errMsg", "can not get Activity context");
//        }
//        if (callback != null) {
//            callback.invoke(result);
//        }
//    }


    // 暂停
    @JSMethod
    public void pause(JSONObject options, JSCallback callback) {
        log(mMediaPlayer.toString());
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
        }
    }
    // 播放
    @UniJSMethod(uiThread=false)
    public void toPlay() {
        MediaPlayer localMediaPlayer = this.mMediaPlayer;
        if (localMediaPlayer != null)
            localMediaPlayer.play();
    }

    // 终止
    @UniJSMethod(uiThread=false)
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

    @Override
    public void onActivityResume() {
        super.onActivityResume();
    }

    @Override
    public void onActivityPause() {
        super.onActivityPause();
    }
    @Override
    public void onActivityDestroy() {
        super.onActivityDestroy();
        mMediaPlayer.release();
        mLibVLC.release();
    }
    void callbackEvent(String paramString, Map paramMap) {
        int size = getEvents().size();
        int startNum = 0;
        int v;
        for (int i = 0; ; i++) {
            v = startNum;
            if (i >= size)
                break;
            if (!((String) getEvents().get(i)).equals(paramString))
                continue;
            v = 1;
            break;
        }
        if (v != 0) {
            HashMap localHashMap = new HashMap();
            if (paramMap != null)
                localHashMap.put("detail", paramMap);
            fireEvent(paramString, localHashMap);
        }
    }

    private void log(String paramString)
    {
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
    public void videoPlayerEvent(MediaPlayer.Event paramEvent) {
        int eventType = paramEvent.type;

        switch (eventType) {
            case MediaPlayer.Event.MediaChanged:
                log("MediaChanged");
                break;
            case MediaPlayer.Event.Opening:
                callbackEvent("onOpening", null);
                break;
            case MediaPlayer.Event.Stopped:
                callbackEvent("onPlayStopped", null);
                break;
            case MediaPlayer.Event.Playing:
                callbackEvent("onPlaying", null);
                break;
            case MediaPlayer.Event.Paused:
                callbackEvent("onPlayPaused", null);
                break;
            case MediaPlayer.Event.EncounteredError:
                callbackEvent("onPlayError", null);
                break;
            case MediaPlayer.Event.TimeChanged:
                Map<String, Object> timeChangedMap = new HashMap<>();
                long currentMilliseconds = paramEvent.getTimeChanged(); // 这是VLC返回的时长值

                timeChangedMap.put("time", Long.valueOf(currentMilliseconds));
                timeChangedMap.put("currentTimeFormat", getVideoFomatTime(currentMilliseconds));
                callbackEvent("onTimeChanged", timeChangedMap);
                break;
            case MediaPlayer.Event.PositionChanged:
                Map<String, Object> positionChangedMap = new HashMap<>();
                positionChangedMap.put("position", Float.valueOf(paramEvent.getPositionChanged()));
                callbackEvent("onPositionChange", positionChangedMap);
                break;
            case MediaPlayer.Event.SeekableChanged:
                log("SeekableChanged: " + paramEvent.getSeekable());
                break;
            case MediaPlayer.Event.PausableChanged:
                log("PausableChanged: " + paramEvent.getPausable());
                break;
            case MediaPlayer.Event.ESAdded:
                callbackEvent("ESAdded", null);
                break;
            case MediaPlayer.Event.ESDeleted:
                callbackEvent("ESDeleted", null);
                break;
            case MediaPlayer.Event.ESSelected:
                callbackEvent("ESSelected", null);
                break;
            case MediaPlayer.Event.LengthChanged:
                Map<String, Object> lengthChangedMap = new HashMap<>();
                long milliseconds = paramEvent.getLengthChanged(); // 这是VLC返回的时长值

                lengthChangedMap.put("time", Long.valueOf(milliseconds));
                lengthChangedMap.put("totalTimeFormat", getVideoFomatTime(milliseconds));
                callbackEvent("onTotalTime", lengthChangedMap);
                break;
            case MediaPlayer.Event.EndReached:
                mMediaPlayer.stop();
                callbackEvent("onPlayEnd", null);
                break;
            case MediaPlayer.Event.RecordChanged:
                log("RecordChanged: " + paramEvent.getRecordPath());
                break;
            case MediaPlayer.Event.Vout:
                log("Vout: " + paramEvent.getVoutCount());
                break;
            default:
                // Handle other event types or ignore them
                break;
        }
    }


}