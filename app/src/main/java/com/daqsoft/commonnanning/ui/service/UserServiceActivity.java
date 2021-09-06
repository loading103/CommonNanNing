package com.daqsoft.commonnanning.ui.service;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.base.IApplication;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.HtmlConstant;
import com.daqsoft.commonnanning.common.SPCommon;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.widget.HeadView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 用户协议
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019/3/19.
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_USER_SERVICE)
public class UserServiceActivity extends BaseActivity {

    @BindView(R.id.headView)
    HeadView headView;
    @BindView(R.id.tv_service)
    TextView mTvService;
    @Autowired(name = "HTMLURL")
    String mHtmlUrl;
    private int mPageType = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_service;
    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        // 根据文本下标，指定单个部分文字变色
        mPageType = getIntent().getIntExtra("mPageType",0);
        mTvService.setText(StringDesignUtil.getSpannableStringBuilder("点击“确认”即表示你同意《“乐游南宁”用户授权协议》", getResources().getColor(R.color.main_default), 12, 26));
        headView.setBackListener(new HeadView.OnBackListener() {
            @Override
            public void onBack(View v) {
                finish();
            }
        });
        headView.setTitle("服务协议");
    }


    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @OnClick({R.id.headView, R.id.tv_check_service,R.id.ll_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_service:
                ARouter.getInstance().build(Constant.ACTIVITY_BASEWEB).withString
                        ("HTMLTITLE", "用户授权协议").withString("HTMLURL", HtmlConstant.HTML_SERVICE)
                        .navigation();
                break;
            case R.id.headView:
                break;
            case R.id.tv_check_service:
                if (mPageType==1){
                    ARouter.getInstance().build(Constant.ACTIVITY_ROUTE).navigation();
                }else {
                    ARouter.getInstance().build(Constant.ACTIVITY_BASEWEB).withString
                            ("HTMLTITLE", "--").withString("HTMLURL", mHtmlUrl)
                            .navigation();
                }
                SPUtils.getInstance().put(SPCommon.SERVICE,true);
                finish();
                break;
        }
    }
}
