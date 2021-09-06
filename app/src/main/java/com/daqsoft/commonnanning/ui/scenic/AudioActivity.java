package com.daqsoft.commonnanning.ui.scenic;

import android.media.MediaPlayer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.utils.GlideUtils;
import com.daqsoft.commonnanning.utils.Utils;
import com.daqsoft.view.AlwaysMarqueeTextView;
import com.example.tomasyb.baselib.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * haungx 2018/1/4
 * 语音播放
 */
public class AudioActivity extends BaseActivity implements MediaPlayer.OnCompletionListener {

    @BindView(R.id.include_title_ib_left)
    ImageView includeTitleIbLeft;
    @BindView(R.id.include_title_ib_left2)
    ImageButton includeTitleIbLeft2;
    @BindView(R.id.include_title_tv)
    AlwaysMarqueeTextView includeTitleTv;
    @BindView(R.id.ib_collection)
    ImageButton ibCollection;
    @BindView(R.id.include_title_ib_right)
    ImageButton includeTitleIbRight;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.include_title_tv_right)
    TextView includeTitleTvRight;
    @BindView(R.id.include_title_va_right)
    ViewAnimator includeTitleVaRight;
    @BindView(R.id.iv_audio)
    ImageView ivAudio;
    @BindView(R.id.tv_audio_name)
    TextView tvAudioName;
    @BindView(R.id.tv_audio_time)
    TextView tvAudioTime;
    @BindView(R.id.bar_audio_progress)
    SeekBar barAudioProgress;
    @BindView(R.id.tv_audio_total)
    TextView tvAudioTotal;
    @BindView(R.id.iv_audio_play)
    ImageView ivAudioPlay;
    @BindView(R.id.activity_audio)
    LinearLayout activityAudio;

    private String name = "";
    private String img = "";
    private String audio = "";

    private Player player;
    private boolean isPlay = false;
    private Animation an;


    @Override
    public int getLayoutId() {
        return R.layout.activity_audio;
    }

    /**
     * 初始化
     */
    public void initView() {
        includeTitleTv.setText("语音导览");
        name = getIntent().getStringExtra("name");
        audio = getIntent().getStringExtra("audio");
        audio = Utils.strToUrl(audio);
        img = getIntent().getStringExtra("img");
        GlideUtils.loadCircleImage(
                this,
                ivAudio,
                img,
                R.mipmap.banner_default
        );
        tvAudioName.setText(name + "语音介绍");
        initAnimotion();
    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.include_title_ib_left, R.id.iv_audio_play})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.include_title_ib_left:
                finish();
                break;
            case R.id.iv_audio_play:
                if (null == player) {
                    player = new Player(barAudioProgress, tvAudioTime, tvAudioTotal);
                    ivAudio.startAnimation(an);
                    barAudioProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            //path = Utils.getImageUrl(resouce);
                            player.playUrl(audio);
                        }
                    }).start();
                    ivAudioPlay.setImageResource(R.mipmap.tourism_products_play_button);
                } else {
                    if (null != player && player.mediaPlayer.isPlaying()) {
                        player.pause();
                        ivAudio.clearAnimation();
                        ivAudioPlay.setImageResource(R.mipmap.tourism_products_suspended_button);
                    } else {
                        ivAudio.startAnimation(an);
                        player.play();
                        ivAudioPlay.setImageResource(R.mipmap.tourism_products_play_button);
                    }
                }

                break;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
    }


    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
        int progress;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            this.progress = progress * player.mediaPlayer.getDuration() / seekBar.getMax();
            //			tvMusicCurrent.setText(getTimeFromInt(progress));
            //tvMusicTime.setText(getTimeFromInt(player.mediaPlayer.getDuration()));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            player.mediaPlayer.seekTo(progress);
        }

    }

    public void initAnimotion() {
        an = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation
                .RELATIVE_TO_SELF, 0.5f);
        // 不停顿
        an.setInterpolator(new LinearInterpolator());
        // 重复次数
        an.setRepeatCount(-1);
        // 停在最后
        an.setFillAfter(true);
        an.setDuration(4000);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.mediaPlayer.release();
        player.mediaPlayer = null;
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (player != null) {
//            if (player.mediaPlayer.isPlaying()) {
//                player.pause();
//                ivAudioPlay.setImageResource(R.mipmap.tourism_products_suspended_button);
//            }
//        }
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (player != null) {
//            if (!player.mediaPlayer.isPlaying()) {
//                player.play();
//                ivAudioPlay.setImageResource(R.mipmap.tourism_products_play_button);
//            }
//        }
//    }
}
