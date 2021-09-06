package com.daqsoft.commonnanning.ui.scenic;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Player/* extends Service*/ implements OnBufferingUpdateListener,
        OnCompletionListener, OnPreparedListener {

    public MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private String musicUrl = "";
    private TextView tvMusicCurrent;
    private TextView tvMusicTotal;
    private Timer mTimer = new Timer();

    public Player() {
    }

    //	@Override
    //	public void onCreate() {
    //		super.onCreate();
    //		try {
    //			mediaPlayer = new MediaPlayer();
    //			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// ����ý��������
    //			mediaPlayer.setOnBufferingUpdateListener(this);
    //			mediaPlayer.setOnPreparedListener(this);
    //			mediaPlayer.setOnCompletionListener(this);
    //			new Thread(new Runnable() {
    //
    //				@Override
    //				public void run() {
    //					playUrl(musicUrl);
    //				}
    //			}).start();
    //		} catch (Exception e) {
    //			e.printStackTrace();
    //		}
    //		mTimer.schedule(timerTask, 0, 1000);
    //	}

    public Player(SeekBar seekBar, TextView tvMusicCurrent, TextView tvMusicTotal/*, String url*/) {
        super();
        this.seekBar = seekBar;
        this.tvMusicCurrent = tvMusicCurrent;
        this.tvMusicTotal = tvMusicTotal;
        //		this.musicUrl = url;
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);//
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mTimer.schedule(timerTask, 0, 1000);
    }

    TimerTask timerTask = new TimerTask() {

        @Override
        public void run() {
            if (mediaPlayer == null)
                return;
            if (mediaPlayer.isPlaying() && seekBar.isPressed() == false) {
                handler.sendEmptyMessage(0);
            }
        }
    };

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int position = mediaPlayer.getCurrentPosition();
            int duration = mediaPlayer.getDuration();
            Log.e("音乐时长为：", getTimeFromInt(duration));
            tvMusicCurrent.setText(getTimeFromInt(position));
            tvMusicTotal.setText(getTimeFromInt(duration));
            if (duration > 0) {
                long pos = seekBar.getMax() * position / duration;
                seekBar.setProgress((int) pos);

            }


        }

        ;
    };

    /**
     * 获取音乐时长
     */
    public String getTimeFromInt(int time) {
        if (time <= 0) {
            return "0:00";
        }
        int secondnd = (time / 1000) / 60;
        int million = (time / 1000) % 60;
        String f = String.valueOf(secondnd);
        String m = million >= 10 ? String.valueOf(million) : "0"
                + String.valueOf(million);
        return f + ":" + m;
    }

    public void play() {
        mediaPlayer.start();
    }

    /**
     * @param url url
     */
    public void playUrl(String url) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void resume() {
        if (null != mediaPlayer) {
            mediaPlayer.release();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        Log.e("mediaPlayer", "onPrepared");
    }

    /**
     * 当Audio播放完的时候触发该动作
     */
    @Override
    public void onCompletion(MediaPlayer player) {
        // TODO Auto-generated method stub
        //		stopSelf();// 结束了，则结束Service
        mediaPlayer.start();
        seekBar.setSecondaryProgress(0);
        tvMusicCurrent.setText("0:00");
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        seekBar.setSecondaryProgress(percent);
        try {
            int currentProgress = seekBar.getMax()
                    * mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration();
            Log.e(currentProgress + "% play", percent + " buffer");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------------------------------------------
    //	private final IBinder binder = new AudioBinder();
    //
    //	@Override
    //	public IBinder onBind(Intent intent) {
    //		// TODO Auto-generated method stub
    //		return null;
    //	}
    //
    //	// 为了和Activity交互，我们需要定义一个Binder对象
    //	public class AudioBinder extends Binder {
    //
    //		// 返回Service对象
    //		public Player getService() {
    //			return Player.this;
    //		}
    //		// 返回Service对象
    //		public Player getService(SeekBar seekBar,TextView tvPersent,String url) {
    ////			return Player.this;
    //			return new Player(seekBar, tvPersent, url);
    //		}
    //	}
    //
    //	// 后退播放进度
    //	public void haveFun() {
    //		if (mediaPlayer.isPlaying() && mediaPlayer.getCurrentPosition() > 2500) {
    //			mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 2500);
    //		}
    //	}
    //
    //	/**
    //	 * 该方法在SDK2.0才开始有的，替代原来的onStart方法
    //	 */
    //	public int onStartCommand(Intent intent, int flags, int startId) {
    //		if (!mediaPlayer.isPlaying()) {
    //			mediaPlayer.start();
    //		}
    //		return START_STICKY;
    //	}
    //
    //	public void onDestroy() {
    //		// super.onDestroy();
    //		if (mediaPlayer.isPlaying()) {
    //			mediaPlayer.stop();
    //		}
    //		mediaPlayer.release();
    //	}
}
