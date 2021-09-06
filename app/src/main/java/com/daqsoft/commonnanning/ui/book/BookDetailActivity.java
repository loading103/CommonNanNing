package com.daqsoft.commonnanning.ui.book;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.ComUtils;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.helps_gdmap.MapNaviUtils;
import com.daqsoft.commonnanning.utils.GlideUtils;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.PhoneUtils;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.web.ProgressWebView;
import com.example.tomasyb.baselib.widget.HeadView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 图书馆详情
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-5-31.14:04
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_LIBRARY_DETAIL)
public class BookDetailActivity extends BaseActivity {
    @Autowired(name = "headimg")
    String headimg;
    @Autowired(name = "name")
    String name;
    @Autowired(name = "address")
    String address;
    @Autowired(name = "intaoce")
    String intaoce;
    @Autowired(name = "lat")
    String lat;
    @Autowired(name = "lon")
    String lon;
    @Autowired(name = "phone")
    String phone;
    @Autowired(name = "opentime")
    String opentime;
    @BindView(R.id.img_top)
    ImageView imgTop;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.head)
    HeadView mHead;
    @BindView(R.id.webView)
    ProgressWebView webView;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_phone)
    TextView tv_phone;

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_detail;
    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        mHead.setTitle("详情");
        mHead.setBackListener(new HeadView.OnBackListener() {
            @Override
            public void onBack(View v) {
                webView.loadUrl("about:blank");
                finish();
            }
        });
        GlideUtils.loadImage(mContext, imgTop, headimg, R.mipmap.common_ba_banner);
        tvTitle.setText(name);
        tvAddress.setText(address);
        webView.loadData(ComUtils.getNewContent(intaoce),
                ComUtils.WEBVIEW_TYPE, null);
        if (ObjectUtils.isNotEmpty(opentime)){
            tv_time.setVisibility(View.VISIBLE);
            tv_time.setText("开馆时间 : "+opentime);
        }else {
            tv_time.setVisibility(View.GONE);
        }
        if (ObjectUtils.isNotEmpty(phone)){
            tv_phone.setVisibility(View.VISIBLE);
            tv_phone.setText("电话 : "+phone);
        }else {
            tv_phone.setVisibility(View.GONE);
        }
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }





    @OnClick({R.id.tv_time, R.id.tv_phone,R.id.tv_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_address:
                // 导航
                if (ObjectUtils.isNotEmpty(lat)
                        && ObjectUtils.isNotEmpty(lon)) {
                    MapNaviUtils.isMapNaviUtils((Activity) mContext,
                            SPUtils.getInstance().getString(SPCommon.LATITUDE),
                            SPUtils.getInstance().getString(SPCommon.LONGITUDE),
                            SPUtils.getInstance().getString(SPCommon.CITY_ADDRESS),
                            lat,
                            lon,
                            address);
                } else {
                    ToastUtils.showShortCenter(getResources().getString(R.string.no_map_navi));
                }
                break;
            case R.id.tv_time:
                break;
            case R.id.tv_phone:
                if (ActivityCompat.checkSelfPermission(mContext,
                        Manifest
                                .permission.CALL_PHONE) !=
                        PackageManager
                                .PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    return;
                }
                if (ObjectUtils.isNotEmpty(phone)){
                    PhoneUtils.call(phone);
                }else {
                    ToastUtils.showShort("暂无电话!");
                }
                break;
        }
    }
}
