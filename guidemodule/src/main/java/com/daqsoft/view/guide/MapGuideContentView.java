package com.daqsoft.view.guide;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daqsoft.bean.MapConfigureBean;
import com.daqsoft.bean.MarketBean;
import com.daqsoft.guide.R;
import com.daqsoft.guide.SpotDetailActivity;
import com.daqsoft.http.DefaultProgressCallBack;
import com.daqsoft.utils.Constant;
import com.daqsoft.utils.EncryptUtils;
import com.daqsoft.utils.FileUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * 导游导览顶部风景详情视图
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-3-14
 * @since JDK 1.8
 */

public class MapGuideContentView extends FrameLayout {
    LinearLayout layoutContent;
    SimpleDraweeView v_guideImg;
    ImageView img_tag;
    ImageView imgPlay;
    ProgressBar progressBar;
    TextView txtTitle;
    TextView txtContent;
    TextView txtDownLoad;
    FrameLayout layoutDownload;
    ProgressBar barDownload;
    ImageView imgDownLoadPlay;
    TextView txtDownOver;
    LinearLayout layoutGuideDetiail;
    TextView txtAddress;
    TextView txtTime;
    LinearLayout llContent;
    TextView tvLevel;

    /**
     * 获得map的数据
     */
    MapConfigureBean bean;
    /**
     * 回调监听
     */
    private OnclickMenuListener listener;
    /**
     * 取消http请求对象
     */
    private Callback.Cancelable cancelable;
    /**
     * 离线语音包下载地址
     */
    private static String AUDIO_PATH = "";
    /**
     * databinding对象
     */
    private com.daqsoft.view.guide.MapGuideContentViewDataBind bind;

    public MapGuideContentView(@NonNull Context context) {
        super(context);
        init();
    }

    public MapGuideContentView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MapGuideContentView(@NonNull Context context, @Nullable AttributeSet attrs, int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        bind = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.guide_view_mapguide_content, null, false);
        View v = bind.getRoot();
        layoutContent = (LinearLayout) v.findViewById(R.id.layout_content);
        v_guideImg = (SimpleDraweeView) v.findViewById(R.id.v_guideImg);
        tvLevel = (TextView) v.findViewById(R.id.txt_level);
        img_tag = (ImageView) v.findViewById(R.id.img_tag);
        img_tag.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick_tag(v);
            }
        });
        imgPlay = (ImageView) v.findViewById(R.id.img_play);
        imgPlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick_play(v);
            }
        });
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        txtTitle = (TextView) v.findViewById(R.id.txt_title);
        txtContent = (TextView) v.findViewById(R.id.txt_content);
        txtDownLoad = (TextView) v.findViewById(R.id.txt_downLoad);
        txtDownLoad.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick_downLoad(v);
            }
        });
        layoutDownload = (FrameLayout) v.findViewById(R.id.layout_download);
        layoutDownload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick_layoutDownload(v);
            }
        });
        barDownload = (ProgressBar) v.findViewById(R.id.bar_download);
        imgDownLoadPlay = (ImageView) v.findViewById(R.id.img_downLoadPlay);
        txtDownOver = (TextView) v.findViewById(R.id.txt_downOver);
        layoutGuideDetiail = (LinearLayout) v.findViewById(R.id.layout_guide_detial);
        txtAddress = (TextView) v.findViewById(R.id.txt_address);
        txtTime = (TextView) v.findViewById(R.id.txt_time);
        llContent = (LinearLayout) v.findViewById(R.id.ll_content);
        llContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final MarketBean marketBean = new MarketBean();
                marketBean.setAudioPath(bean.getSceneryAudioPath());
                marketBean.setCover(bean.getCover());
                marketBean.setName(bean.getName());
                marketBean.setSummary(bean.getSummary());
                Intent intent = new Intent(getContext(), SpotDetailActivity.class);
                intent.putExtra(Constant.IntentKey.BEAN, marketBean);
                getContext().startActivity(intent);
            }
        });
        this.addView(bind.getRoot());
    }

    /**
     * 通过MapConfigureBean对象初始化界面
     *
     * @param bean
     */
    public void setInitData(MapConfigureBean bean) {
        this.bean = bean;
        bind.setConfigBean(bean);
        //        v_guideImg.setImageURI(bean.getCover());
        String tempAudioPath = bean.getSceneryAudioPath();
        // 语音不为空
        if (!TextUtils.isEmpty(tempAudioPath)) {
            //            String name = tempAudioPath.substring(tempAudioPath.lastIndexOf("/") + 1);
            // 获取文件要加密的部分
            String fileName = tempAudioPath.substring(0, tempAudioPath.lastIndexOf("."));
            // 获取文件后缀名
            String suffix = tempAudioPath.substring(tempAudioPath.lastIndexOf(".") + 1);
            String ciphertext = EncryptUtils.encryptMD5ToString(fileName) + "." + suffix;
            AUDIO_PATH = getContext().getCacheDir().getAbsolutePath() + File.separator +
                    "audio" + File.separator + ciphertext;
            File file = new File(AUDIO_PATH);
            if (file.exists()) {
                bean.setOffLineAudioPath(AUDIO_PATH);
                txtDownLoad.setVisibility(View.GONE);
                layoutDownload.setVisibility(View.GONE);
                txtDownOver.setText("离线语音包已下载（" + FileUtil.FormetFileSize((int) file.length()) +
                        "）");
                txtDownOver.setVisibility(View.VISIBLE);
            }
        } else {
            // 否则隐藏语音离线下载按钮和播放按钮
            txtDownLoad.setVisibility(View.GONE);
            imgPlay.setVisibility(View.GONE);
        }
    }

    /**
     * 点击头部层级显示影藏
     *
     * @param v
     */
    public void onclick_tag(View v) {
        img_tag.setTag("show".equals(img_tag.getTag()) ? "hidden" : "show");
        layoutContent.setVisibility("show".equals(img_tag.getTag()) ? View.VISIBLE : View.GONE);
        img_tag.setImageResource("show".equals(img_tag.getTag()) ?
                R.mipmap.guide_down_highlighted : R.mipmap.guide_down_normal);
    }

    /**
     * 点击头部层级播放按钮
     *
     * @param v
     */
    public void onclick_play(View v) {
        imgPlay.setTag("play".equals(imgPlay.getTag()) ? "stop" : "play");
        imgPlay.setBackgroundResource(imgPlay.getTag().toString().equals("play") ?
                R.mipmap.guide_introduction_pause : R.mipmap.guide_introduction_play);
        progressBar.setVisibility("play".equals(imgPlay.getTag()) ? View.VISIBLE : View.GONE);
        layoutGuideDetiail.setVisibility(GONE);
        if (this.listener != null) {
            this.listener.onclick(v);
        }
    }

    /**
     * 点击下载按钮
     *
     * @param v
     */
    public void onclick_downLoad(View v) {
        txtDownLoad.setVisibility(View.GONE);
        layoutDownload.setVisibility(View.VISIBLE);
    }

    /**
     * 点击下载按钮
     *
     * @param v
     */
    public void onclick_layoutDownload(View v) {
        //设置tag标记是否是播放
        layoutDownload.setTag("play".equals(layoutDownload.getTag()) ? "stop" : "play");
        imgDownLoadPlay.setImageResource("play".equals(layoutDownload.getTag()) ?
                R.mipmap.guide_introduction_play : R.mipmap
                .guide_introduction_pause);
        if ("play".equals(layoutDownload.getTag())) {
            if (bean == null) {
                return;
            }
            download(bean.getSceneryAudioPath(), AUDIO_PATH);
        } else {
            cancelable.cancel();
        }

    }

    /**
     * 设置音频播放进度
     *
     * @param total
     * @param progress
     */
    public void setAudioProgress(int total, int progress) {
        progressBar.setMax(total);
        progressBar.setProgress(progress);
    }

    /**
     * 设置点击事件的回调
     *
     * @param listener
     */
    public void setListener(OnclickMenuListener listener) {
        this.listener = listener;
    }

    /**
     * 点击查看全部的回调
     */
    public interface OnclickMenuListener {
        void onclick(View v);
    }

    /**
     * 下载文件
     */
    public void download(String url, String path) {
        RequestParams requestParams = new RequestParams(url);
        if (!TextUtils.isEmpty(path)) {
            requestParams.setSaveFilePath(path);
        }
        cancelable = x.http().get(requestParams, new DefaultProgressCallBack<File>() {
            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                //                Log.i("获得文件大小--->" + total + "\t当前下载进度--->" + current);
                barDownload.setMax((int) total);
                barDownload.setProgress((int) current);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("tag", "文件下载失败");
            }

            @Override
            public void onSuccess(File result) {
                txtDownLoad.setVisibility(View.GONE);
                layoutDownload.setVisibility(View.VISIBLE);
                txtDownOver.setText("离线语音包已下载（" + FileUtil.FormetFileSize((int) result.length())
                        + "）");
            }

            @Override
            public void onFinished() {
            }
        });

    }
}
