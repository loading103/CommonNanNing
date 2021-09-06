package com.daqsoft.commonnanning.base;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.daqsoft.android.ProjectConfig;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.http.HttpApiService;
import com.daqsoft.commonnanning.ui.destination.RegionEntity;
import com.daqsoft.commonnanning.ui.entity.LocationEntity;
import com.daqsoft.utils.Utils;
import com.daqsoft.voice.IMAudioManager;
import com.example.tomasyb.baselib.base.api.ApiConstants;
import com.example.tomasyb.baselib.base.app.BaseApplication;
import com.example.tomasyb.baselib.util.SPUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.umeng.commonsdk.UMConfigure;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * IApplication程序入口
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/3/20 0020
 * @since JDK 1.8
 */
public class IApplication extends BaseApplication {
    /**
     * 是否第一次进入身边游，需要检测到相应位置
     */
    public boolean isCheckLocationMap = false;

    public boolean isCheckLocationMap() {
        return isCheckLocationMap;
    }

    public void setCheckLocationMap(boolean checkLocationMap) {
        isCheckLocationMap = checkLocationMap;
    }

    private boolean isDebug = true;
    private String mLat;
    private String mLon;


    public String getmLat() {
        return mLat;
    }

    public void setmLat(String mLat) {
        this.mLat = mLat;
    }

    public String getmLon() {
        return mLon;
    }

    public void setmLon(String mLon) {
        this.mLon = mLon;
    }

    /**
     * 省份数据
     */
    public static ArrayList<String> options1Items = new ArrayList<>();
    /**
     * 城市数据
     */
    public static ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    /**
     * 地区数据
     */
    public static ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    /**
     * 地区集合(全国数据
     */
    public static ArrayList<LocationEntity> locationList = new ArrayList<>();

    /**
     * 图片缓存的对象apply
     */
    public static RequestOptions requestOptions = null;
    public static IApplication application = null;

    public  Boolean isAgreePrivate ; //是否统一隐私协议1是同意 0是未同意
    @Override
    public void onCreate() {
        super.onCreate();
        initRouter();

        isAgreePrivate = SPUtils.getInstance().getBoolean(ApiConstants.IS_FIRST_AGREE,false);

        if (application == null) {
            application = this;
        }

        try {
            // 初始化xUtils
            x.Ext.init(this);
            // 设置为debug模式
            x.Ext.setDebug(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        requestOptions = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        // 公交查询需要的url根地址
        setBaseUrl(ProjectConfig.BASE_URL);
        setREQUESTMAP(HttpApiService.REQUESTMAP);


        // 因为西藏上线要先同意隐私协议才能初始化权限

        if( ProjectConfig.CITY_NAME.equals("西藏")){
            if(isAgreePrivate){
                initNeedPrivateSDK();
            }else {
                //西藏旅游
                UMConfigure.preInit(this, "5d3579e84ca35787cc000e6e", "zgxzlv");
            }
        }else {
            initNeedPrivateSDK();
        }
    }



    /**
     * 需要同意隐私协议才能初始化，不然会非法获取mac地址，导致不能上架
     */
    public void initNeedPrivateSDK() {
        // 这个是科大讯飞智能机器人需要初始化的
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=" + ProjectConfig.XF_APPID);
        IMAudioManager.instance().init(this);
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);

    }
    /**
     * 需要同意隐私协议才能初始化，不然会非法获取mac地址，导致不能上架
     */
    public Boolean getIsAgreePrivate() {
        return  SPUtils.getInstance().getBoolean(ApiConstants.IS_FIRST_AGREE,false);

    }
    /**
     * 站点地址集合
     */
    public List<RegionEntity> regionList = new ArrayList<>();
    /**
     * 站点地区
     */
    public ArrayList<String> siteRegionList = new ArrayList<>();

    /**
     * 初始化路由跳转
     */
    private void initRouter() {
        if (isDebug) {
            // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();
            // 打印日志
            ARouter.openDebug();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);
        HttpApiService.REQUESTMAP.put("siteCode", ProjectConfig.SITECODE);
        HttpApiService.REQUESTMAP.put("lang", ProjectConfig.LANG);
        HttpApiService.REQUESTMAP.put("token", SPUtils.getInstance().getString(SPCommon.TOKEN));
        // 导游导览需要初始化的
        ImagePipelineConfig frescoConfig =
                ImagePipelineConfig.newBuilder(this).setDownsampleEnabled(true).build();
        Fresco.initialize(this, frescoConfig);
    }

    /**
     * 当前运行的activity的Context上下文
     */
    private static IApplication mInstance;

    public static IApplication getInstance() {
        if (!Utils.isnotNull(mInstance)) {
            synchronized (IApplication.class) {
                if (!Utils.isnotNull(mInstance)) {
                    mInstance = new IApplication();
                }
            }
        }
        return mInstance;
    }
}
