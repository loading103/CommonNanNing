package com.daqsoft.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 语音播放
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-3-14
 * @since JDK 1.8
 */

public class MediaPlayerService extends Service {
    /**
     * 音频播放
     */
    private MediaPlayer mediaPlayer;
    /**
     * 设置定时器
     */
    private Timer timer;
    /**
     * 定时器执行的任务类
     */
    private TimerTask task;
    /**
     * 播放回调
     */
    private OnAudioListener listener;
    /**
     * 服务绑定
     */
    private MyBinder binder = new MyBinder();
    /**
     * UI更新
     */
    private MyHandler<MediaPlayerService> handler = new MyHandler<MediaPlayerService>(this);

    /**
     * 声明一个静态的Handler内部类，并持有外部类的弱引用
     */
    private static class MyHandler<T> extends Handler {

        private final WeakReference<T> obj;

        private MyHandler(Service obj) {
            this.obj = new WeakReference<T>((T) obj);
        }

        @Override
        public void handleMessage(Message msg) {
            MediaPlayerService service = (MediaPlayerService) obj.get();
            if (service == null) {
                return;
            }
            if (service.listener != null && service.mediaPlayer != null) {
                int current = service.mediaPlayer.getCurrentPosition();
                int total = service.mediaPlayer.getDuration();
                service.listener.currentSeek(current, total);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    /**
     * 播放音频
     *
     * @param uri 地址
     */
    public void play(String uri) {
        String s = uri.replaceAll(" ", "%20");
        if (TextUtils.isEmpty(s)) {
            return;
        }
        try {
            if (mediaPlayer == null) {
                mediaPlayer = new MediaPlayer();
            }
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.setDataSource(s);
            }
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
            mediaPlayer.prepareAsync();
            // 流媒体加载完成
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    if (listener != null) {
                        listener.onstart(mp);
                    }
                    // 计时器
                    task = new TimerTask() {
                        @Override
                        public void run() {
                            System.out.print("定时器循环获得音频的进度");
                            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                                handler.sendEmptyMessage(0);

                            }
                        }
                    };
                    timer = new Timer();
                    timer.schedule(task, 0, 1000);
                }
            });
            // 流媒体播放结束
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                        if (timer != null) {
                            timer.cancel();
                        }
                        if (listener != null) {
                            listener.onfinish(mp);
                        }
                    }
                }
            });

            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Log.e("加载错误", "what:" + what + "\n" + "extra:" + extra);
                    return false;
                }
            });
            // 流媒体缓存进度
            mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    Log.e("加载错误", "sss");
                }
            });

            // 通过seek设置播放位置时回调
            mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(MediaPlayer mp) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置播放的进度
     *
     * @param seek
     */
    public void setSeek(int seek) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(seek);
        }
    }

    /**
     * 续播
     */
    public void resume() {
        if (!mediaPlayer.isPlaying()) {
            if (mediaPlayer != null) {
                this.mediaPlayer.start();
            }
        }
    }

    /**
     * 暂停播放
     */
    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            this.mediaPlayer.pause();
        }
    }

    /**
     * 停止播放
     */
    public void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            this.mediaPlayer.stop();
            mediaPlayer.reset();
            this.mediaPlayer.release();
            timer.cancel();
            mediaPlayer = null;
        }
    }

    /**
     * 是否在播放
     *
     * @return
     */
    public boolean isPlaying() {
        if (mediaPlayer == null) {
            return false;
        }
        return mediaPlayer.isPlaying();
    }

    /**
     * 设置当前进度的监听
     *
     * @param listener
     */
    public void setListener(OnAudioListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
            mediaPlayer = null;
            if (timer != null) {
                timer.cancel();
            }
        }
    }

    /**
     * 音频播放的回调
     */
    public interface OnAudioListener {
        void onstart(MediaPlayer mp);

        void currentSeek(int current, int total);

        void onfinish(MediaPlayer mp);
    }

    /**
     * 绑定服务对象
     */
    public class MyBinder extends Binder {
        public MediaPlayerService getMediaPlayerService() {
            return MediaPlayerService.this;
        }
    }
}
