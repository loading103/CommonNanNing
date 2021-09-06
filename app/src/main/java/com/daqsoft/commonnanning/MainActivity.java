package com.daqsoft.commonnanning;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocation;
import com.daqsoft.android.ProjectConfig;
import com.daqsoft.commonnanning.base.IApplication;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.helps_gdmap.CompleteFuncData;
import com.daqsoft.commonnanning.helps_gdmap.HelpMaps;
import com.daqsoft.commonnanning.http.CommonRequest;
import com.daqsoft.commonnanning.http.LocationService;
import com.daqsoft.commonnanning.ui.destination.DestinationFragment;
import com.daqsoft.commonnanning.ui.entity.TabEntity;
import com.daqsoft.commonnanning.ui.find.FindFragment;
import com.daqsoft.commonnanning.ui.main.BoleServiceFragment;
import com.daqsoft.commonnanning.ui.main.FunFragment;
import com.daqsoft.commonnanning.ui.main.MainBoleFragment;
import com.daqsoft.commonnanning.ui.main.MainFragment;
import com.daqsoft.commonnanning.ui.main.MainNewFragment;
import com.daqsoft.commonnanning.ui.main.MainXlgLNewFragment;
import com.daqsoft.commonnanning.ui.main.MeFragment;
import com.daqsoft.commonnanning.ui.main.ServerFragment;
import com.daqsoft.commonnanning.ui.main.ShopFragment;
import com.daqsoft.commonnanning.version.VersionCheck;
import com.daqsoft.commonnanning.view.PrivacyStatementDialog;
import com.daqsoft.event.UndateFinishEvent;
import com.example.tomasyb.baselib.base.AppManager;
import com.example.tomasyb.baselib.base.api.ApiConstants;
import com.example.tomasyb.baselib.base.app.BaseApplication;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.rx.permission.RxPermissions;
import com.example.tomasyb.baselib.util.LogUtils;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.util.StatusBarUtil;
import com.example.tomasyb.baselib.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.agora.yview.tablayout.CommonTabLayout;
import io.agora.yview.tablayout.listener.CustomTabEntity;
import io.reactivex.functions.Consumer;

/**
 * 主页
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-6-4.15:50
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_MAIN)
public class MainActivity extends BaseActivity {
    @BindView(R.id.tab_bottom)
    CommonTabLayout botBar;

    private Fragment mIndexFragment;
    private Fragment mFunFragment;
    private Fragment mShopFragment;
    private Fragment mServiceFragment;
    private Fragment mMeFragment;

    private FragmentManager manager;
    private FragmentTransaction transaction;
    private List<Fragment> fragments;
    int fragment_index = 0;
    /**
     * 当前显示位置
     */
    int currPosition = 0;
    /**
     * 标题
     */
    private String[] mTitles = null;
    private Fragment mFindFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        StatusBarUtil.setLightMode(this);
        manager = getSupportFragmentManager();
        mTitles = getResources().getStringArray(R.array.main_page_index);
        fragments = new ArrayList<>();
        if (savedInstanceState != null) {
            fragment_index = savedInstanceState.getInt("index");
            // 南宁
            if ("南宁".equals(ProjectConfig.CITY_NAME) || "江西".equals(ProjectConfig.CITY_NAME)) {
                mIndexFragment = manager.findFragmentByTag("home1");
                if (!"江西".equals(ProjectConfig.CITY_NAME)) {
                    mFindFragment = manager.findFragmentByTag("home2");
                }
                mServiceFragment = manager.findFragmentByTag("home3");
                mMeFragment = manager.findFragmentByTag("home4");
                fragments.add(mIndexFragment);
                fragments.add(mFindFragment);
                fragments.add(mServiceFragment);
                fragments.add(mMeFragment);
            } else if ("博乐".equals(ProjectConfig.CITY_NAME)) {
                mIndexFragment = manager.findFragmentByTag("home1");
                mFindFragment = manager.findFragmentByTag("home2");
                mServiceFragment = manager.findFragmentByTag("home3");
                mMeFragment = manager.findFragmentByTag("home4");
                fragments.add(mIndexFragment);
                fragments.add(mFindFragment);
                fragments.add(mServiceFragment);
                fragments.add(mMeFragment);
            } else if ("锡林郭勒盟".equals(ProjectConfig.CITY_NAME)) {
                mIndexFragment = manager.findFragmentByTag("home1");
                mFunFragment = manager.findFragmentByTag("home5");
                mFindFragment = manager.findFragmentByTag("home2");
                mServiceFragment = manager.findFragmentByTag("home3");
                mMeFragment = manager.findFragmentByTag("home4");
                fragments.add(mIndexFragment);
                fragments.add(mFindFragment);
                fragments.add(mServiceFragment);
                fragments.add(mMeFragment);
            } else {
                // 通用版 （铜川）
                mIndexFragment = manager.findFragmentByTag("home1");
                mFunFragment = manager.findFragmentByTag("home2");
                mShopFragment = manager.findFragmentByTag("home3");
                mServiceFragment = manager.findFragmentByTag("home4");
                mMeFragment = manager.findFragmentByTag("home5");
                fragments.add(mIndexFragment);
                fragments.add(mFunFragment);
                fragments.add(mShopFragment);
                fragments.add(mServiceFragment);
                fragments.add(mMeFragment);
            }

        } else {
            if ("南宁".equals(ProjectConfig.CITY_NAME) || "江西".equals(ProjectConfig.CITY_NAME)) {
                fragmentInitNanNing();
            } else if ("博乐".equals(ProjectConfig.CITY_NAME)) {
                fragmentInitBole();
            } else if ("锡林郭勒盟".equals(ProjectConfig.CITY_NAME)) {
                fragmentInitXLGLM();
            }else if ("西藏".equals(ProjectConfig.CITY_NAME)) {
                fragmentInitXiZang();
            } else {
                fragmentInit();
            }
        }
        initBottomBar();
        CommonRequest.getSiteRegions();
    }

    /**
     * 铜川版本
     */
    private void fragmentInit() {
        transaction = manager.beginTransaction();
        mIndexFragment = new MainFragment();
        mFunFragment = new FunFragment();
        mShopFragment = new ShopFragment();
        mServiceFragment = new ServerFragment();
        mMeFragment = new MeFragment();

        fragments.add(mIndexFragment);
        fragments.add(mFunFragment);
        fragments.add(mShopFragment);
        fragments.add(mServiceFragment);
        fragments.add(mMeFragment);

        transaction.add(R.id.fl_tab_container, mIndexFragment, "home1");
        transaction.add(R.id.fl_tab_container, mFunFragment, "home2");
        transaction.add(R.id.fl_tab_container, mShopFragment, "home3");
        transaction.add(R.id.fl_tab_container, mServiceFragment, "home4");
        transaction.add(R.id.fl_tab_container, mMeFragment, "home5");
        transaction.commitAllowingStateLoss();
    }

    /**
     * 初始化博乐界面
     */
    private void fragmentInitBole() {
        transaction = manager.beginTransaction();
        mIndexFragment = new MainBoleFragment();
        mFindFragment = new FindFragment();
        mServiceFragment = new BoleServiceFragment();
        mMeFragment = new MeFragment();

        fragments.add(mIndexFragment);
        fragments.add(mFindFragment);
        fragments.add(mServiceFragment);
        fragments.add(mMeFragment);

        transaction.add(R.id.fl_tab_container, mIndexFragment, "home1");
        transaction.add(R.id.fl_tab_container, mFindFragment, "home2");
        transaction.add(R.id.fl_tab_container, mServiceFragment, "home3");
        transaction.add(R.id.fl_tab_container, mMeFragment, "home4");
        transaction.commitAllowingStateLoss();
    }


    /**
     * 初始化锡林郭勒盟界面
     */
    private void fragmentInitXLGLM() {
        transaction = manager.beginTransaction();
        mIndexFragment = new MainXlgLNewFragment();
        fragments.add(mIndexFragment);
        transaction.add(R.id.fl_tab_container, mIndexFragment, "home1");
        mFunFragment = new DestinationFragment();
        fragments.add(mFunFragment);
        transaction.add(R.id.fl_tab_container, mFunFragment, "home5");
        mFindFragment = new FindFragment();
        fragments.add(mFindFragment);
        transaction.add(R.id.fl_tab_container, mFindFragment, "home2");
        mServiceFragment = new ServerFragment();
        mMeFragment = new MeFragment();
        fragments.add(mServiceFragment);
        transaction.add(R.id.fl_tab_container, mServiceFragment, "home3");
        fragments.add(mMeFragment);
        transaction.add(R.id.fl_tab_container, mMeFragment, "home4");
        transaction.commitAllowingStateLoss();
    }

    /**
     * 南宁版本
     */
    private void fragmentInitNanNing() {
        transaction = manager.beginTransaction();
        mIndexFragment = new MainNewFragment();
        fragments.add(mIndexFragment);
        transaction.add(R.id.fl_tab_container, mIndexFragment, "home1");
        if (!"江西".equals(ProjectConfig.CITY_NAME)) {
            mFindFragment = new FindFragment();
            fragments.add(mFindFragment);
            transaction.add(R.id.fl_tab_container, mFindFragment, "home2");
        }
        mServiceFragment = new ServerFragment();
        mMeFragment = new MeFragment();
        fragments.add(mServiceFragment);
        transaction.add(R.id.fl_tab_container, mServiceFragment, "home3");
        fragments.add(mMeFragment);
        transaction.add(R.id.fl_tab_container, mMeFragment, "home4");
        transaction.commitAllowingStateLoss();
    }


    /**
     *中国西藏旅游版本
     */
    private void fragmentInitXiZang() {
        transaction = manager.beginTransaction();
        mIndexFragment = new MainNewFragment();
        fragments.add(mIndexFragment);
        transaction.add(R.id.fl_tab_container, mIndexFragment, "home1");
//        mFunFragment = new DestinationFragment();
//        fragments.add(mFunFragment);
//        transaction.add(R.id.fl_tab_container, mFunFragment, "home5");
        mFindFragment = new FindFragment();
        fragments.add(mFindFragment);
        transaction.add(R.id.fl_tab_container, mFindFragment, "home2");
        mServiceFragment = new ServerFragment();
        mMeFragment = new MeFragment();
        fragments.add(mServiceFragment);
        transaction.add(R.id.fl_tab_container, mServiceFragment, "home3");
        fragments.add(mMeFragment);
        transaction.add(R.id.fl_tab_container, mMeFragment, "home4");
        transaction.commitAllowingStateLoss();
    }

    /**
     * 选择碎片
     */
    private void switchFragment(int index) {
        transaction = manager.beginTransaction();
        for (int i = 0; i < mTitles.length; i++) {
            Fragment fragment = fragments.get(i);
            if (i == index) {
                transaction.show(fragment);
                continue;
            }
            transaction.hide(fragment);
        }
        transaction.commit();
    }

    @Override
    public void doBeforeSetContentView() {
        super.doBeforeSetContentView();
        /**
         * 在fragment设置全屏
         */
        //StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, null);
    }

    /**
     * 初始化底部
     */
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private void initBottomBar() {
        switchTo(0);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], ProjectConfig.mIconSelectIds[i],
                    ProjectConfig.mIconUnselectIds[i]));
        }
        botBar.setTabData(mTabEntities);
        botBar.setOnTabSelectListener(new io.agora.yview.tablayout.listener.OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switchTo(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    private void switchTo(int tab) {
        fragment_index = tab;
        switchFragment(fragment_index);
        currPosition = fragment_index;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // 保存当前页面
        outState.putInt("index", fragment_index);
        super.onSaveInstanceState(outState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        // 获取权限
        showPrivacyStatement();
    }




    /**
     * 显示隐私用户协议(因为西藏上架，为了上架要先同意隐私协议)
     */
    private PrivacyStatementDialog privacyStatementDialog;
    private void showPrivacyStatement() {
        boolean isFirst = SPUtils.getInstance().getBoolean(ApiConstants.IS_FIRST_AGREE, false);
        if (!isFirst && ProjectConfig.CITY_NAME.equals("西藏")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (privacyStatementDialog == null) {
                        privacyStatementDialog = new PrivacyStatementDialog(MainActivity.this);
                        privacyStatementDialog.show();
                    }
                }
            }, 2000);
        }else {

            SPUtils.getInstance().put(ApiConstants.IS_FIRST_AGREE, true);
            requestPermissions();
            VersionCheck.checkUpdatePermission(this);
            // 获取全国地址
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LocationService.getLocation();
                }
            }).start();
        }
    }



    /**
     * 获取权限
     */
    @SuppressLint("CheckResult")
    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(this);
        rxPermission.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA,
                Manifest.permission.READ_CALENDAR, Manifest.permission.CALL_PHONE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.READ_PHONE_STATE, Manifest.permission.RECORD_AUDIO).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                // 成功
                if (aBoolean) {
                    setLocation();
                    LogUtils.e("获取权限成功");
                } else {
                    LogUtils.e("获取权限失败");
                }
            }
        });
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void setLocation() {
        HelpMaps.startLocation(new CompleteFuncData() {
            @Override
            public void success(AMapLocation location) {
                try {
                    String latitude = String.valueOf(location.getLatitude());
                    String longitude = String.valueOf(location.getLongitude());
                    SPUtils.getInstance().put(SPCommon.LATITUDE, latitude);
                    SPUtils.getInstance().put(SPCommon.LONGITUDE, longitude);
                    SPUtils.getInstance().put(SPCommon.CITY_NAME, location.getCity());
                    SPUtils.getInstance().put(SPCommon.CITY_ADDRESS, location.getAddress());
                    HelpMaps.stopLocation();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1001) {
            // 版本检测
            VersionCheck.checkUpdate(this);
        }
    }

    /**
     * 监听返回键，连续点击两次退出系统
     *
     * @param event
     * @return
     */
    private long exitTime = 0L;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                if (System.currentTimeMillis() - exitTime > 2000) {
                    ToastUtils.showShort("再按一次退出");
                    exitTime = System.currentTimeMillis();
                } else {
                    this.finish();
                    AppManager.getAppManager().AppExit(this, true);
                }
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finished(UndateFinishEvent event) {
        if(event.getSuccess()){
            boolean isAgreePrivate = SPUtils.getInstance().getBoolean(ApiConstants.IS_FIRST_AGREE, false);
            if(!isAgreePrivate) {
                SPUtils.getInstance().put(ApiConstants.IS_FIRST_AGREE, true);
                IApplication.application.initNeedPrivateSDK();
                requestPermissions();
                VersionCheck.checkUpdatePermission(this);
                // 获取全国地址
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LocationService.getLocation();
                    }
                }).start();
            }
        }else {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    finish();
//                }
//            },400);

//            IApplication.application.initNeedPrivateSDK();
        }
    }


}
