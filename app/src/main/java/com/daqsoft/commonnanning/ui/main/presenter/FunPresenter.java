package com.daqsoft.commonnanning.ui.main.presenter;

import com.daqsoft.commonnanning.common.ParamsCommon;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.AdvertEntity;
import com.daqsoft.commonnanning.ui.entity.IndexActivity;
import com.daqsoft.commonnanning.ui.entity.IndexBanner;
import com.daqsoft.commonnanning.ui.entity.IndexScenic;
import com.daqsoft.commonnanning.ui.entity.MyStrategyListBean;
import com.daqsoft.commonnanning.ui.entity.PanoramaListBean;
import com.daqsoft.commonnanning.ui.main.contract.FunContact;
import com.example.tomasyb.baselib.base.mvp.BasePresenter;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 好玩P层
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-3-23.11:23
 * @since JDK 1.8
 */

public class FunPresenter extends BasePresenter<FunContact.view> implements FunContact.presenter{

    public FunPresenter(FunContact.view view) {
        super(view);
    }

    @Override
    public void getPanoramaList() {
        RetrofitHelper.getApiService().getPanoramaList("1","3")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<PanoramaListBean>>() {
                    @Override
                    public void accept(BaseResponse<PanoramaListBean>
                                               bean) throws Exception {
                        view.setPanorama(bean.getDatas());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.setPanorama(null);
                    }
                });
    }

    @Override
    public void getBannerData() {
        RetrofitHelper.getApiService().getIndexBannar(ParamsCommon.FOUND_BANNER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .subscribe(new Consumer<BaseResponse<AdvertEntity>>() {
                    @Override
                    public void accept(BaseResponse<AdvertEntity> bean) throws Exception {
                        List<IndexBanner> mlist = new ArrayList<>();
                        mlist.clear();
                        if (bean.getCode()==0&&bean.getDatas().size()>0){
                            for (int i = 0; i < bean.getDatas().size(); i++) {
                                IndexBanner beans  = new IndexBanner();
                                beans.setId(bean.getDatas().get(i).getId());
                                beans.setImg(bean.getDatas().get(i).getPics().get(0));
                                mlist.add(beans);
                            }
                            view.initBanner(mlist);
                        }else {
                            IndexBanner beans  = new IndexBanner();
                            beans.setId("");
                            beans.setImg("");
                            mlist.add(beans);
                            view.initBanner(mlist);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        List<IndexBanner> mlist = new ArrayList<>();
                        IndexBanner beans  = new IndexBanner();
                        beans.setId("");
                        beans.setImg("");
                        mlist.add(beans);
                        view.initBanner(mlist);
                    }
                });
    }

    @Override
    public void getLineData() {
        RetrofitHelper.getApiService().getLineList("1","2","1","")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<MyStrategyListBean>>() {
                    @Override
                    public void accept(BaseResponse<MyStrategyListBean> line) throws Exception {
                        view.setLineData(line.getDatas());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.setLineData(null);
                    }
                });
    }

    @Override
    public void getActivityList(String name) {
        RetrofitHelper.getApiService().getActivityList("1","2",
                name, ParamsCommon.ACTIVITY_CHANELCODE)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<IndexActivity>>() {
                    @Override
                    public void accept(BaseResponse<IndexActivity> bean) throws Exception {
                        view.refreshActivity(bean.getDatas());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.refreshActivity(null);
                    }
                });
    }

    @Override
    public void getBestScenic() {
        RetrofitHelper.getApiService().getIndexScenic("1", "5", "1","recommendedPriority")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .subscribe(new Consumer<BaseResponse<IndexScenic>>() {
                    @Override
                    public void accept(BaseResponse<IndexScenic> bean) throws Exception {
                        if (bean.getCode()==0&&bean.getDatas().size()>0){
                            view.setBestScenicData(bean.getDatas());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
}
