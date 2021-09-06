package com.daqsoft.commonnanning.http;

import android.annotation.SuppressLint;
import android.app.Activity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.base.IApplication;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.ui.destination.RegionEntity;
import com.example.tomasyb.baselib.base.AppManager;
import com.example.tomasyb.baselib.base.net.common.DefaultObserver;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.util.LogUtils;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.util.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 功能统一的网络请求方法
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-4-2.18:28
 * @since JDK 1.8
 */

public class CommonRequest {
    public interface OnStateCallBack {
        void result(int value);
    }

    /**
     * 检查token是否失效
     */
    @SuppressLint("CheckResult")
    public static void checkedToken(final OnStateCallBack listener) {
        RetrofitHelper.getApiService().checkToken().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BaseResponse>() {
            @Override
            public void accept(BaseResponse baseResponse) throws Exception {
                LogUtils.e(baseResponse.toString());
                if (baseResponse.getCode() == 0) {
                    listener.result(0);
                } else {
                    listener.result(1);
                    if (ObjectUtils.isNotEmpty(SPUtils.getInstance().getString(SPCommon.TOKEN))) {
                        ToastUtils.showShort("登录已失效，请重新登录!");
                    }
                    HttpApiService.REQUESTMAP.put("token", "");
                    SPUtils.getInstance().put(SPCommon.TOKEN, "");
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                listener.result(1);
            }
        });
    }

    /**
     * 判断url地址是否包含？，从而确定该已什么来进行拼接
     */
    public static final String CHECK_URL = "?";

    /**
     * 跳转小电商网页方法
     *
     * @param activity 上下文
     * @param url      地址
     * @param title    标题
     */
    public static void goToShoppingHtml(final Activity activity, final String url,
                                        final String title) {
        if (ObjectUtils.isNotEmpty(SPUtils.getInstance().getString(SPCommon.TOKEN))) {
            if (ObjectUtils.isNotEmpty(SPUtils.getInstance().getString(SPCommon.UC_ID)) && ObjectUtils.isNotEmpty(SPUtils.getInstance().getString(SPCommon.UC_TOKEN))) {


                CommonRequest.checkedToken(new CommonRequest.OnStateCallBack() {
                    @Override
                    public void result(int value) {
                        // 0未失效
                        if (0 == value) {
                            String htmlUrl = url;
                            String params =
                                    ("token=" + SPUtils.getInstance().getString(SPCommon.UC_TOKEN) + "&unid=" + SPUtils.getInstance().getString(SPCommon.UC_ID) + "&linkFrom=app").trim();
                            if (url.contains(CHECK_URL)) {
                                htmlUrl = url + "&" + params;
                            } else {
                                htmlUrl = url + "?" + params;
                            }
                            ARouter.getInstance().build(Constant.ACTIVITY_BASEWEB).withString(
                                    "HTMLTITLE", title).withString("HTMLURL", htmlUrl).navigation(activity, 1);
                            LogUtils.e("--------------------------->" + htmlUrl);
                        } else {
                            ARouter.getInstance().build(Constant.ACTIVITY_LOGIN).navigation(activity, 1);
                        }
                    }
                });
            } else {
                ToastUtils.showShortCenter("小电商信息获取错误!");
            }
        } else {
            ToastUtils.showShortCenter("请先登录");
            ARouter.getInstance().build(Constant.ACTIVITY_LOGIN).navigation();
        }
    }

    /**
     * 获取站点的地区信息
     */
    public static void getSiteRegions() {
        RetrofitHelper.getApiService().getSiteRegions().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver<RegionEntity>() {
            @Override
            public void onSuccess(BaseResponse<RegionEntity> response) {
                if (response.getCode() == 0) {
                    RegionEntity region = new RegionEntity();
                    region.setName(AppManager.getAppManager().currentActivity().getResources().getString(R.string.filter_name));
                    region.setRegion("");
                    IApplication.getInstance().regionList.add(region);
                    IApplication.getInstance().siteRegionList.add(region.getName());
                    for (RegionEntity regionEntity : response.getDatas()) {
                        IApplication.getInstance().siteRegionList.add(regionEntity.getName());
                        IApplication.getInstance().regionList.add(regionEntity);
                    }
                }
            }
        });
    }
}
