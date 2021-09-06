package com.daqsoft.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.daqsoft.guide.R;

/**
 * 音频播放时候的特效
 *
 * @author MouJunFeng
 * @time 2018-3-14
 * @since JDK 1.8
 * @version 1.0.0
 */

public class MediaPlayView extends FrameLayout {

    LinearLayout ll_layout;
    public MediaPlayView(@NonNull Context context) {
        super(context);
        init();
    }

    public MediaPlayView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MediaPlayView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    void init() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.guide_view_media_palyer, null);
         ll_layout = (LinearLayout) v.findViewById(R.id.ll_layout);
        this.addView(v);
    }
    private Handler handler = new Handler() {
        @Override
        public void  handleMessage(Message msg) {
            super.handleMessage(msg);
            getAnimationSet(msg.what);
        }
    };

    /**
     * 设置动画
     * @param i
     */
    private void getAnimationSet(int i) {
        ScaleAnimation animation = new ScaleAnimation(1.0f, 1.0f, 1.0f, 0.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(800);
        animation.setRepeatCount(Integer.MAX_VALUE);
        animation.setRepeatMode(Animation.REVERSE);
        View view = ll_layout.getChildAt(i);
        view.startAnimation(animation);
    }

    /**
     * 开始
     */
    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (ll_layout != null) {
                    for (int i = 0; i < ll_layout.getChildCount(); i++) {
                        synchronized (this){
                            handler.sendEmptyMessage(i);
                        }
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    /**
     * 停止
     */
    public void stop() {
        LinearLayout ll_layout = (LinearLayout) findViewById(R.id.ll_layout);
        if (ll_layout != null) {
            for (int i = 0; i < ll_layout.getChildCount(); i++) {
                View v = ll_layout.getChildAt(i);
                v.getAnimation().cancel();
            }
        }
    }

}
