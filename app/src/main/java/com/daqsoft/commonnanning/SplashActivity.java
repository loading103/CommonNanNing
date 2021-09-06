package com.daqsoft.commonnanning;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;

import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.location.AMapLocation;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.helps_gdmap.CompleteFuncData;
import com.daqsoft.commonnanning.helps_gdmap.HelpMaps;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.rx.permission.RxPermissions;
import com.example.tomasyb.baselib.util.LogUtils;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.util.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 引导页
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-6-4.15:50
 * @since JDK 1.8
 */
public class SplashActivity extends BaseActivity {
    @BindView(R.id.button)
    Button mBtnTime;

    /**
     * 任务
     */
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.sendEmptyMessage(0);
        }
    };
    /**
     * 设定倒计时时长单位s
     */
    private int time = 3;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 设置倒计时
                case 0:
                    time--;
                    if (time > 0) {
                        if (ObjectUtils.isNotEmpty(mBtnTime)) {
                            mBtnTime.setText("跳过(" + time + ")");
                            handler.postDelayed(runnable, 1000);
                        }
                    } else {
                        ARouter.getInstance().build(Constant.ACTIVITY_MAIN).navigation();
                        finish();
                    }
                    break;
                // 跳转到主界面
                case 1:
                    ARouter.getInstance().build(Constant.ACTIVITY_MAIN).navigation();
                    finish();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void doBeforeSetContentView() {
        super.doBeforeSetContentView();
        StatusBarUtil.setTranslucentForImageView(this, null);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        mBtnTime.setText("跳过(" + time + ")");
        handler.postDelayed(runnable, 1000);
    }


//
//    @SuppressLint("CheckResult")
//    private void requestPermissions() {
//        RxPermissions rxPermission = new RxPermissions(this);
//        rxPermission.request(Manifest.permission.CALL_PHONE,
//                Manifest.permission.READ_PHONE_STATE).subscribe(new Consumer<Boolean>() {
//            @Override
//            public void accept(Boolean aBoolean) throws Exception {
//                // 成功
//                if (aBoolean) {
//                    setLocation();
//                    mBtnTime.setText("跳过(" + time + ")");
//                    handler.postDelayed(runnable, 1000);
//                    LogUtils.e("获取权限成功");
//                } else {
//                    LogUtils.e("获取权限失败");
//                }
//            }
//        });
//    }
//
//    private void setLocation() {
//        HelpMaps.startLocation(new CompleteFuncData() {
//            @Override
//            public void success(AMapLocation location) {
//                try {
//                    String latitude = String.valueOf(location.getLatitude());
//                    String longitude = String.valueOf(location.getLongitude());
//                    SPUtils.getInstance().put(SPCommon.LATITUDE, latitude);
//                    SPUtils.getInstance().put(SPCommon.LONGITUDE, longitude);
//                    SPUtils.getInstance().put(SPCommon.CITY_NAME, location.getCity());
//                    SPUtils.getInstance().put(SPCommon.CITY_ADDRESS, location.getAddress());
//                    HelpMaps.stopLocation();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @OnClick(R.id.button)
    public void onViewClicked() {
        handler.removeCallbacks(runnable);
        handler.sendEmptyMessage(1);
        handler.removeCallbacks(runnable);
    }
}
