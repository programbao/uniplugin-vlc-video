package com.programbao.vlc_video;

// Source code is unavailable, and was generated by the Fernflower decompiler.

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.SurfaceView;

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

public class VlcVideo extends WXComponent<SurfaceView> {

    private LibVLC mLibVLC;
    private MediaPlayer mMediaPlayer;

    private SurfaceView surfaceView;
    private IVLCVout vlcVout;

    public VlcVideo(WXSDKInstance instance, WXVContainer parent, BasicComponentData basicComponentData) {
        super(instance, parent, basicComponentData);
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

                mMediaPlayer.play();
            }
        });
    }

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
    void callbackEvent(String paramString, Map paramMap)
    {
        int i = getEvents().size();
        int j = 0;
        int m;
        for (int k = 0; ; k++)
        {
            m = j;
            if (k >= i)
                break;
            if (!((String)getEvents().get(k)).equals(paramString))
                continue;
            m = 1;
            break;
        }
        if (m != 0)
        {
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
            case 267:
                Map<String, Object> timeChangedMap = new HashMap<>();
                long currentMilliseconds = paramEvent.getTimeChanged(); // 这是VLC返回的时长值

                timeChangedMap.put("time", Long.valueOf(currentMilliseconds));
                timeChangedMap.put("currentTimeFormat", getVideoFomatTime(currentMilliseconds));
                callbackEvent("onTimeChanged", timeChangedMap);
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
            case 273:
                Map<String, Object> lengthChangedMap = new HashMap<>();
                long milliseconds = paramEvent.getLengthChanged(); // 这是VLC返回的时长值

                lengthChangedMap.put("time", Long.valueOf(milliseconds));
                lengthChangedMap.put("totalTimeFormat", getVideoFomatTime(milliseconds));
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