package com.daqsoft.commonnanning.ui.main.presenter;

import com.daqsoft.commonnanning.common.SourceType;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.FoundNearEy;
import com.daqsoft.commonnanning.ui.entity.GuideDetail;
import com.daqsoft.commonnanning.ui.entity.ScenicChild;
import com.daqsoft.commonnanning.ui.entity.ScenicDetail;
import com.daqsoft.commonnanning.ui.entity.ScenicVideo;
import com.daqsoft.commonnanning.ui.main.contract.ScenicDetailContact;
import com.daqsoft.commonnanning.ui.mine.interact.entity.CommentBean;
import com.daqsoft.utils.Utils;
import com.example.tomasyb.baselib.base.mvp.BasePresenter;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.util.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 景区详情P层
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-3-28.9:24
 * @since JDK 1.8
 */

public class ScenicDetailPresenter extends BasePresenter<ScenicDetailContact.view> implements
        ScenicDetailContact.presenter {

    public ScenicDetailPresenter(ScenicDetailContact.view view) {
        super(view);
    }

    @Override
    public void getDetail(String sceneryId) {
        RetrofitHelper.getApiService().getScenicDetail(sceneryId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<ScenicDetail>>() {
                    @Override
                    public void accept(BaseResponse<ScenicDetail> bean) throws Exception {
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
    public void getMapGuideDetail(String id) {
        RetrofitHelper.getApiService().getMapGuideDetail(id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<GuideDetail>>() {
                    @Override
                    public void accept(BaseResponse<GuideDetail> guideDetailBaseResponse) throws
                            Exception {
                        view.setMapData(guideDetailBaseResponse.getData());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.setMapData(null);
                    }
                });
    }

    @Override
    public void getChildScenic(String id) {
        RetrofitHelper.getApiService().getScenicChild("1", "4", id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<ScenicChild>>() {
                    @Override
                    public void accept(BaseResponse<ScenicChild> bean) throws Exception {
                        if (bean.getCode() == 0) {
                            view.setChildScenic(bean.getDatas());
                        }else {
                            view.setChildScenic(null);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.setChildScenic(null);
                    }
                });
    }

    @Override
    public void getScenicVideo(String id) {
        RetrofitHelper.getApiService().getScenicVideo("1", "4", id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<ScenicVideo>>() {
                    @Override
                    public void accept(BaseResponse<ScenicVideo> bean) throws Exception {
                        if (bean.getCode() == 0) {
                            view.setScenicVideo(bean.getDatas());
                        }else {
                            view.setScenicVideo(null);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.setScenicVideo(null);
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
    public void getCommonInfoData(String mRid,String soucetype) {
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
                        view.setCommonInfoData(line.getDatas(),line.getPage().getTotal()+"");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.setCommonInfoData(null,"0");
                    }
                });
    }

    @Override
    public void saveThumb(String content,String reId,String title) {
        if (Utils.isnotNull(content)) {
            if (content.length() > 50) {
                content = content.substring(0, 50) + "...";
            }
        }
        RetrofitHelper.getApiService().saveThumb(reId, content,
                title, SourceType.RESOURCE_SCENIC)
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
    public void saveCollection(String strategyId,String title,String content) {
        RetrofitHelper.getApiService().saveCollection(strategyId, SourceType.RESOURCE_SCENIC,content,title)
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

    @Override
    public void delCollection(String reId) {
        RetrofitHelper.getApiService().delCollection(reId,SourceType.RESOURCE_SCENIC)
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
                            ToastUtils.showShort(bean.getMessage());
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
