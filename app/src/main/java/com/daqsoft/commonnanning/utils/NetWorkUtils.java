package com.daqsoft.commonnanning.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.SourceType;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.utils.Utils;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.util.LogUtils;
import com.example.tomasyb.baselib.util.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * -----------------------------------------------------------------
 *
 * @version 1.0.1
 * @Description 公共网络访问集合
 * @Author 董彧傑
 * @CreateDate 2019-3-29 11:35
 * @since jdk1.8.0_201
 * -----------------------------------------------------------------
 */
public class NetWorkUtils {
    /**
     * 取消点赞
     */
    @SuppressLint("CheckResult")
    public static void deleteThumb(String reId, final View view, final Activity activity) {
        RetrofitHelper.getApiService().deleteThumb("" + reId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (0 == bean.getCode()) {
                            ToastUtils.showShort("取消点赞");
                            view.setSelected(false);
                        } else if (bean.getCode() == 2) {
                            goLoginActivity(activity);
                        } else {
                            ToastUtils.showShort(bean.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showShort("操作失败，请稍后重试");
                    }
                });
    }

    /**
     * 保存点赞的数据
     */
    @SuppressLint("CheckResult")
    public static void saveThumb(String reId, String sourceType, String name, String content, final View view, final Activity activity) {
        if (Utils.isnotNull(content)) {
            if (content.length() > 50) {
                content = content.substring(0, 50) + "...";
            }
        }
        RetrofitHelper.getApiService().saveThumb(reId + "", content, name, sourceType)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        LogUtils.e("点赞成功");
                        if (bean.getCode() == 0) {
                            ToastUtils.showShort("点赞成功");
                            view.setSelected(true);
                        } else if (bean.getCode() == 2) {
                            goLoginActivity(activity);
                        } else {
                            ToastUtils.showShort(bean.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showShort("操作失败，请稍后重试");
                    }
                });
    }

    /**
     * 取消收藏
     */
    @SuppressLint("CheckResult")
    public static void delCollection(String reId, final View view, final Activity activity) {
        RetrofitHelper.getApiService().delCollection("" + reId, SourceType.STRATEGY_TYPE)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (0 == bean.getCode()) {
                            ToastUtils.showShort("取消收藏");
                            view.setSelected(false);
                        } else if (bean.getCode() == 2) {
                            goLoginActivity(activity);
                        } else {
                            ToastUtils.showShort(bean.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showShort("操作失败，请稍后重试");
                    }
                });
    }

    /**
     * 保存收藏
     */
    @SuppressLint("CheckResult")
    public static void saveCollection(String reId, String sourceType, String name, String content, final View view, final Activity activity) {
        RetrofitHelper.getApiService().saveCollection("" + reId, sourceType, content, name)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (0 == bean.getCode()) {
                            ToastUtils.showShort("收藏成功");
                            view.setSelected(true);
                        } else if (bean.getCode() == 2) {
                            goLoginActivity(activity);
                        } else {
                            ToastUtils.showShort(bean.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showShort("操作失败，请稍后重试");
                    }
                });
    }

    /**
     * 将Disposable添加
     *
     * @param subscription
     */

    private static CompositeDisposable mCompositeDisposable;

    public static void addDisposable(Disposable subscription) {
        // csb 如果解绑了的话添加 sb 需要新的实例否则绑定时无效的
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    public static void goLoginActivity(Activity activity) {
        ToastUtils.showShortCenter("请先登录");
        ARouter.getInstance().build(Constant.ACTIVITY_LOGIN).navigation(activity, 1);
    }
}
