package com.daqsoft.commonnanning.ui.destination;

import android.annotation.SuppressLint;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.ComUtils;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.example.tomasyb.baselib.base.glide.GlideApp;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.web.WebUtils;
import com.example.tomasyb.baselib.widget.HeadView;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 地区概况页面
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018/7/23 0023
 * @since JDK 1.8
 */
public class CountySummaryActivity extends BaseActivity {
    @BindView(R.id.head_county_summary)
    HeadView headView;
    @BindView(R.id.iv_county_img)
    ImageView ivImg;
    @BindView(R.id.tv_county_name)
    TextView tvName;
    @BindView(R.id.item_web)
    WebView mWeb;
    /**
     * 当前地区的图片
     */
    private String img;
    /**
     * 当前地区的名字
     */
    private String name;
    /**
     * 当前地区概况的介绍内容
     */
    private String content;

    DestinationInfoEntity info;
    /**
     * 地区编码
     */
    String region = "";


    @Override
    public int getLayoutId() {
        return R.layout.activity_county_summary;
    }

    @Override
    public void initView() {
        initData();
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    /**
     * 数据初始化并赋值
     */
    public void initData() {
        headView.setTitle("概况");
        region = getIntent().getStringExtra("region");
        getBaseInfoByRegion(region);
    }

    /**
     * 根据地区编码获取目的地基础信息
     */
    @SuppressLint("CheckResult")
    public void getBaseInfoByRegion(String region) {
        RetrofitHelper.getApiService()
                .getDestinationBaseInfo(region)
                .doOnSubscribe(disposable -> {
                    addDisposable(disposable);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getCode() == 0
                            && ObjectUtils.isNotEmpty(bean.getData())) {
                        info = bean.getData();
                        name = info.getRegionName();
                        content = info.getContent();
                        img = info.getCoverTwoToThree();
                        GlideApp.with(CountySummaryActivity.this)
                                .load(img)
                                .placeholder(R.mipmap.common_ba_banner)
                                .error(R.mipmap.common_ba_banner)
                                .into(ivImg);
                        tvName.setText(name);
                        if (ObjectUtils.isNotEmpty(content)) {
                            mWeb.loadData(WebUtils.getNewContent(content), ComUtils.WEBVIEW_TYPE,
                                    null);
                        }
                    }
                });
    }
}
