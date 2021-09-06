package com.daqsoft.guide;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.daqsoft.bean.MarketBean;
import com.daqsoft.service.MediaPlayerService;
import com.daqsoft.utils.Config;
import com.daqsoft.utils.Constant;
import com.daqsoft.utils.ShowToast;
import com.daqsoft.utils.TimerUtils;
import com.daqsoft.utils.Utils;
import com.daqsoft.view.HeadView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 景点详细信息
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-3-14
 * @since JDK 1.8
 */

public class SpotDetailActivity extends Activity {
    /**
     * 头部试图
     */
    HeadView headView;
    TextView txtCuttentTime;
    TextView txtTotalTime;
    ImageView imgPlay;
    SeekBar progressBar;
    LinearLayout llSoptDetail;
    private WebView webView;

    /**
     * 播放音频服务绑定对象
     */

    private MediaplayerServiceConn serviceConn;
    /**
     * 播放音频服务
     */
    private MediaPlayerService playerService;
    /**
     * http请求对象
     */
    private MarketBean bean;
    /**
     * 服务是否在运行
     */
    private boolean audioServicePlay = false;
    /**
     * 通过反射找到的CommonShare的方法
     */
    private Method setShareWindow;
    /**
     * 语音讲解的时间控件
     */
    private LinearLayout llPlayTime;
    private TextView mTvName;
    private TextView mTvNameVoice;
    private LinearLayout llVoice;
    private SimpleDraweeView ivImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_activity_spot_detial);
        headView = (HeadView) findViewById(R.id.headView);
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.getSettings().setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(webView.getSettings()
                    .MIXED_CONTENT_ALWAYS_ALLOW);  //注意安卓5.0以上的权限
        }
        llSoptDetail = (LinearLayout) findViewById(R.id.guide_spot_detail_ll);
        mTvNameVoice = (TextView) findViewById(R.id.tv_details_title_voice);
        bean = (MarketBean) getIntent().getSerializableExtra(Constant.IntentKey.BEAN);
        mTvName = (TextView) findViewById(R.id.tv_details_title);
        headView.setMenuTextDrawableLeft(R.mipmap.share_icon);
        ivImg = (SimpleDraweeView) findViewById(R.id.iv_img);

        headView.setMenuHidden(false);
        headView.setMenuText("");
        mTvName.setText(Utils.isnotNull(bean.getName()) ? bean.getName() : "未知");
        mTvNameVoice.setText(bean.getName() + "语音导览");

//        if(ProjectConfig.CITY_NAME.equals("西藏")) {
//            head.setMenuHidden(false);
//        }
        headView.setMenuListener(new HeadView.OnMenuListener() {
            @Override
            public void onClickMenu(View v) {
              /*  Intent intent = new Intent();
                intent.putExtra("htmlUrl", Config.HTMLURL + "guide-desc.html?id=" + bean.getId()
                + "&platform=Android");
                intent.putExtra("isShare", true);
                intent.setClassName(SpotDetailActivity.this, "com.daqsoft.android.base
                .WebActivity");
                startActivity(intent);*/
                String shareClassName = "com.daqsoft.android.common.CommonShare";
                try {
                    Class shareClass = Class.forName(shareClassName);
                    // 反射出该Class类中的show()方法
                    setShareWindow = shareClass.getDeclaredMethod("setShareWindow", View.class,
                            String.class, String.class, String.class, String.class, String.class,
                            String.class);
                    // 取消访问私有方法的合法性检查
                    setShareWindow.setAccessible(true);
                    // 调用show()方法
                    List<Object> list = new ArrayList<>();
                    list.add(llSoptDetail);
                    list.add(bean.getName());
                    list.add(bean.getIntroduce());
                    list.add(bean.getCover());
                    list.add(Config.HTMLURL + Constant.GUIDE_DESC_HTML + bean.getId());
                    list.add("");
                    list.add(bean.getId() + "");
                    //  CommonShare.setShareWindow(rl,shareTitle,shareContent,shareImg,shareUrl,
                    // sourceType,reId);
                    setShareWindow.invoke(shareClass.newInstance(), list.toArray());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("反射异常", e.toString());
                }
            }


        });
        txtCuttentTime = (TextView) findViewById(R.id.txt_cuttentTime);
        txtTotalTime = (TextView) findViewById(R.id.txt_totalTime);
        imgPlay = (ImageView) findViewById(R.id.img_play);
        llVoice = (LinearLayout) findViewById(R.id.ll_voice);
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick_playAudio(v);
            }
        });
        llPlayTime = (LinearLayout) findViewById(R.id.ll_play_time);
        progressBar = (SeekBar) findViewById(R.id.progressBar);
        if (bean != null) {
            headView.setTitle("导游导览详情");
            String content = "";
            if (!Utils.isnotNull(Utils.deleteHtmlImg(bean.getIntroduce()))) {
                content = Utils.deleteHtmlImg(bean.getSummary());
            } else {
                content = bean.getIntroduce();
            }
            if (null == content || content.equals("")) {

            } else {
                webView.loadDataWithBaseURL(null, getNewContent(content), "text/html", "UTF-8",
                        null);
            }
            if (Utils.isnotNull(bean.getCover())) {
                ivImg.setImageURI(bean.getCover());
                ivImg.setVisibility(View.VISIBLE);
            } else {
                ivImg.setVisibility(View.GONE);
            }
        }
        findViewById(R.id.btn_intent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName(SpotDetailActivity.this, "com.daqsoft.android.ui.found" + ""
                        + ".FoundNearNewActivity");
                startActivity(intent);
            }
        });
        if (!Utils.isnotNull(bean.getAudioPath())) {
            progressBar.setVisibility(View.GONE);
            imgPlay.setVisibility(View.GONE);
            llPlayTime.setVisibility(View.GONE);
            llVoice.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
            imgPlay.setVisibility(View.VISIBLE);
            llPlayTime.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 让WebView的内容宽度，自适应手机屏幕的宽度
     *
     * @param htmltext
     * @return
     */
    public String getNewContent(String htmltext) {
        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
        }
        return doc.toString();
    }

    /**
     * 点击按钮事件
     *
     * @param v
     */
    public void onclick_playAudio(View v) {
        if (bean == null || !Utils.isnotNull(bean.getAudioPath())) {
            ShowToast.showText(this, "暂无音频!");
            return;
        }
        imgPlay.setTag("play".equals(imgPlay.getTag()) ? "stop" : "play");
        imgPlay.setImageResource("play".equals(imgPlay.getTag()) ? R.mipmap.dydldetail_icon_zt :
                R.mipmap.dydldetail_icon_bf);
        if ("play".equals(imgPlay.getTag())) {
            if (serviceConn != null && playerService != null) {
                playerService.resume();
            }
            if (serviceConn == null && !TextUtils.isEmpty(bean.getAudioPath())) {
                if (!audioServicePlay) {
                    serviceConn = new MediaplayerServiceConn(bean.getAudioPath());
                    Intent intent = new Intent(this, MediaPlayerService.class);
                    audioServicePlay = bindService(intent, serviceConn, BIND_AUTO_CREATE);
                }

            }
        } else {
            if (serviceConn != null && playerService != null) {
                playerService.pause();
            }
        }
    }

    /**
     * 播放音频的服务
     */
    private class MediaplayerServiceConn implements ServiceConnection {
        private String url;

        public MediaplayerServiceConn(String url) {
            this.url = url;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            playerService = ((MediaPlayerService.MyBinder) service).getMediaPlayerService();
            if (playerService.isPlaying()) {
                return;
            }
            playerService.play(url);
            playerService.setListener(new MediaPlayerService.OnAudioListener() {
                @Override
                public void onstart(MediaPlayer mp) {

                }

                @Override
                public void currentSeek(int current, int total) {
                    progressBar.setMax(total);
                    progressBar.setProgress(current);
                    txtCuttentTime.setText(TimerUtils.formatDuring(current));
                    txtTotalTime.setText(TimerUtils.formatDuring(total));
                }

                @Override
                public void onfinish(MediaPlayer mp) {
                    imgPlay.setImageResource(R.mipmap.dydldetail_icon_bf);
                    imgPlay.setTag("stop");
                    progressBar.setProgress(0);
                    txtCuttentTime.setText("00:00");
                    if (serviceConn != null && audioServicePlay) {
                        unbindService(serviceConn);
                        audioServicePlay = false;
                        playerService = null;
                        audioServicePlay = false;
                        serviceConn = null;
                    }

                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            playerService = null;
            playerService = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
       /* if (audioServicePlay){
            if (playerService!=null){
                if (playerService.isPlaying()){
                    playerService.pause();
                }
            }
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (audioServicePlay) {
            if (playerService != null) {
                playerService.resume();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceConn != null && audioServicePlay) {
            this.unbindService(serviceConn);
            if (playerService.isPlaying()) {
                playerService.stop();
            }
            serviceConn = null;
            playerService = null;
            audioServicePlay = false;
        }
    }
}
