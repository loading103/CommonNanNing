package com.daqsoft.commonnanning.helps_gdmap;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.tomasyb.baselib.base.app.BaseApplication;
import com.example.tomasyb.baselib.util.LogUtils;


/**
 * 地图服务类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018-6-4.15:50
 * @since JDK 1.8
 */
public class HelpMaps {

    /**
     * 声明mlocationClient对象
     */
    public static AMapLocationClient mlocationClient;
    /**
     * 声明mLocationOption对象
     */
    public static AMapLocationClientOption mLocationOption = null;

    /**
     * 开始定位
     */
    public static void startLocation(final CompleteFuncData callBack) {
        mlocationClient = new AMapLocationClient(BaseApplication.getAppContext());
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        if (callBack != null) {
                            callBack.success(aMapLocation);
                        }
                        mlocationClient.stopLocation();
                    } else {
                        // 显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        LogUtils.e("location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                        if (callBack != null) {
                            callBack.success(null);
                        }
                    }
                }
            }
        });
        // 设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        // 设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        // 启动定位
        mlocationClient.startLocation();
    }

    /**
     * 停止定位
     */
    public static void stopLocation() {
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient = null;
        }
    }


}
