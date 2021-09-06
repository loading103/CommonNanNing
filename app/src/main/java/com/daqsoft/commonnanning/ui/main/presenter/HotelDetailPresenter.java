package com.daqsoft.commonnanning.ui.main.presenter;

import com.daqsoft.commonnanning.common.SourceType;
import com.daqsoft.commonnanning.hotel.HotelTip;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.FoundNearEy;
import com.daqsoft.commonnanning.ui.entity.HotelDetail;
import com.daqsoft.commonnanning.ui.main.contract.HotelDetailContact;
import com.daqsoft.commonnanning.ui.mine.interact.entity.CommentBean;
import com.daqsoft.utils.Utils;
import com.example.tomasyb.baselib.base.mvp.BasePresenter;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 酒店详情P层
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-3-28.9:24
 * @since JDK 1.8
 */

public class HotelDetailPresenter extends BasePresenter<HotelDetailContact.view> implements
        HotelDetailContact.presenter {

    public HotelDetailPresenter(HotelDetailContact.view view) {
        super(view);
    }

    @Override
    public void getHotelDetail(String id) {
        RetrofitHelper.getApiService().getHotelDetail(id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<HotelDetail>>() {
                    @Override
                    public void accept(BaseResponse<HotelDetail> bean) throws Exception {
                        if (bean.getCode() == 0) {
                            view.setBaseData(bean.getData());
                        } else {
                            view.setBaseData(null);
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.setBaseData(null);
                    }
                });
    }

    @Override
    public void getListTip(String id) {
        RetrofitHelper.getApiService().getHotelListTip(id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<HotelTip>>() {
                    @Override
                    public void accept(BaseResponse<HotelTip> bean) throws Exception {
                        if (bean.getCode()==0&& ObjectUtils.isNotEmpty(bean.getDatas().get(0).getSummary())){
                            view.setHotelTip(bean.getDatas().get(0).getSummary());
                        }else {
                            view.setHotelTip("");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.setHotelTip("");
                    }
                });
    }

    @Override
    public void getAroundMap(String latitude, String longitude, String sourceType, String
            distance) {
        RetrofitHelper.getApiService().getAroundMap("1", "5", latitude, longitude, sourceType,
                distance)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<FoundNearEy>>() {
                    @Override
                    public void accept(BaseResponse<FoundNearEy> bean) throws Exception {
                        view.addMapMarker(bean.getDatas());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.addMapMarker(null);
                    }
                });
    }

    @Override
    public void getCommonInfoData(String mRid, String soucetype) {
        RetrofitHelper.getApiService().getCommentInfo(mRid, soucetype, "1", "3")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<CommentBean>>() {
                    @Override
                    public void accept(BaseResponse<CommentBean> line) throws Exception {
                        view.setCommonInfoData(line.getDatas(), line.getPage().getTotal() + "");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.setCommonInfoData(null, "0");
                    }
                });
    }

    @Override
    public void saveThumb(String content, String reId, String title) {
        if (Utils.isnotNull(content)) {
            if (content.length() > 50) {
                content = content.substring(0, 50) + "...";
            }
        }
        RetrofitHelper.getApiService().saveThumb(reId, content,
                title, SourceType.RESOURCE_HOTEL)
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
                        if (bean.getCode() == 0) {
                            view.setTumbleStyle(true);
                            ToastUtils.showShort("点赞成功");
                        }  else if (bean.getCode()==2){
                            ToastUtils.showShort("请先登录");
                            view.goToLoginActivity();
                        }else {
                            ToastUtils.showShort(bean.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showShort("请求错误，请稍后重试!");
                    }
                });
    }

    @Override
    public void delThumb(String reId) {
        RetrofitHelper.getApiService().deleteThumb(reId)
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
                        if (bean.getCode() == 0) {
                            view.setTumbleStyle(false);
                            ToastUtils.showShort("取消点赞成功");
                        } else if (bean.getCode()==2){
                            ToastUtils.showShort("请先登录");
                            view.goToLoginActivity();
                        }else {
                            ToastUtils.showShort(bean.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showShort("请求错误，请稍后重试!");
                    }
                });
    }

    @Override
    public void saveCollection(String strategyId, String title, String content) {
        RetrofitHelper.getApiService().saveCollection(strategyId, SourceType.RESOURCE_HOTEL,
                content, title)
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
                            view.setChangStyle(true);
                        } else if (bean.getCode()==2){
                            ToastUtils.showShort("请先登录");
                            view.goToLoginActivity();
                        }else {
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

    @Override
    public void delCollection(String reId) {
        RetrofitHelper.getApiService().delCollection(reId, SourceType.RESOURCE_HOTEL)
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
                        if (bean.getCode() == 0) {
                            view.setChangStyle(false);
                            ToastUtils.showShort("取消收藏成功");
                        }  else if (bean.getCode()==2){
                            ToastUtils.showShort("请先登录");
                            view.goToLoginActivity();
                        }else {
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
}
