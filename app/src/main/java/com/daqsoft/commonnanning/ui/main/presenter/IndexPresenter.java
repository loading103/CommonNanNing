package com.daqsoft.commonnanning.ui.main.presenter;

import com.daqsoft.android.ProjectConfig;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.ParamsCommon;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.AdvertEntity;
import com.daqsoft.commonnanning.ui.entity.GuideBean;
import com.daqsoft.commonnanning.ui.entity.IndexActivity;
import com.daqsoft.commonnanning.ui.entity.IndexBanner;
import com.daqsoft.commonnanning.ui.entity.IndexMenu;
import com.daqsoft.commonnanning.ui.entity.NewsListEntity;
import com.daqsoft.commonnanning.ui.main.contract.IndexContact;
import com.example.tomasyb.baselib.base.mvp.BasePresenter;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * IndexPresenter
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-9-5.12:35
 * @since JDK 1.8
 */

public class IndexPresenter extends BasePresenter<IndexContact.view> implements IndexContact
        .presenter {

    public IndexPresenter(IndexContact.view view) {
        super(view);
    }


    @Override
    public void getBannerData() {
        RetrofitHelper.getApiService().getIndexBannar(ParamsCommon.INDEXTOP_BANNER).subscribeOn
                (Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).subscribe(new Consumer<BaseResponse<AdvertEntity>>() {
            @Override
            public void accept(BaseResponse<AdvertEntity> bean) throws Exception {
                List<IndexBanner> mlist = new ArrayList<>();
                mlist.clear();
                if (bean.getCode() == 0 && bean.getDatas().size() > 0) {
                    for (int i = 0; i < bean.getDatas().size(); i++) {
                        IndexBanner beans = new IndexBanner();
                        beans.setId(bean.getDatas().get(i).getId());
                        beans.setImg(bean.getDatas().get(i).getPics().get(0));
                        mlist.add(beans);
                    }
                    view.initBanner(mlist);
                } else {
                    IndexBanner beans = new IndexBanner();
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
                IndexBanner beans = new IndexBanner();
                beans.setId("");
                beans.setImg("");
                mlist.add(beans);
                view.initBanner(mlist);
            }
        });
    }

    @Override
    public void setMenuData() {

        Integer[] img = {R.mipmap.home_entrance_spot, R.mipmap.home_entrance_hotel, R.mipmap
                .home_entrance_food, R.mipmap.home_entrance_trave_agency, R.mipmap
                .home_through_train, R.mipmap.home_entrance_panoramic};
        String[] name = {"景区", "酒店", "美食", "旅行社", "行程", "全景"};
        if (ProjectConfig.SITECODE.equals("cehmhyhwgw")) {
            name = new String[]{"概况", "酒店", "美食", "行程", "AR互动", "报警"};
            img = new Integer[]{R.mipmap.home_entrance_spot, R.mipmap.home_entrance_hotel, R
                    .mipmap.home_entrance_food, R.mipmap.home_entrance_trave_agency, R.mipmap
                    .home_entrance_ar, R.mipmap.home_entrance_help};
        }
        List<IndexMenu> mDatas = new ArrayList<>();
        for (int i = 0; i < name.length; i++) {
            IndexMenu bean = new IndexMenu();
            bean.setmImg(img[i]);
            bean.setName(name[i]);
            mDatas.add(bean);
        }
        view.initRv(mDatas);
    }

    @Override
    public void getNowData() {
        RetrofitHelper.getApiService().getNewsList(ParamsCommon.SERVICE_LYZX, "", "", "3", "1")
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).subscribe(new Consumer<BaseResponse<NewsListEntity>>() {
            @Override
            public void accept(BaseResponse<NewsListEntity> bean) throws Exception {
                if (bean.getDatas().size() > 0) {
                    List<String> data = new ArrayList<>();
                    for (int i = 0; i < bean.getDatas().size(); i++) {
                        data.add(bean.getDatas().get(i).getTitle());
                    }
                    view.setNowData(data, bean.getDatas());
                } else {
                    view.setNoDataNotify();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                view.setNoDataNotify();
            }
        });
    }

    @Override
    public void getMapGuideList() {
        RetrofitHelper.getApiService().getMapGuideListNear(SPUtils.getInstance().getString
                (SPCommon.LATITUDE), SPUtils.getInstance().getString(SPCommon.LONGITUDE), "",
                "1", "1").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).subscribe(new Consumer<BaseResponse<GuideBean>>() {
            @Override
            public void accept(BaseResponse<GuideBean> bean) throws Exception {
                if (bean.getCode() == 0) {
                    view.setMapData(bean.getDatas());
                } else {
                    view.setMapData(null);
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                view.setMapData(null);
            }
        });
    }

    @Override
    public void getPlayList() {
        RetrofitHelper.getApiService().getIndexBannar(ParamsCommon.INDEX_PLAY_BANNER).subscribeOn
                (Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).subscribe(new Consumer<BaseResponse<AdvertEntity>>() {
            @Override
            public void accept(BaseResponse<AdvertEntity> bean) throws Exception {
                List<AdvertEntity> mlist = new ArrayList<>();
                mlist.clear();
                if (bean.getCode() == 0 && bean.getDatas().size() > 0) {
                    mlist.addAll(bean.getDatas());
                    view.setPlayList(mlist);
                } else {
                    view.setPlayList(null);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                view.setPlayList(null);
            }
        });
    }


    @Override
    public void getActivityList(String name) {
        RetrofitHelper.getApiService().getActivityList("1", "2", name, ParamsCommon
                .ACTIVITY_CHANELCODE).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                .mainThread()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).subscribe(new Consumer<BaseResponse<IndexActivity>>() {
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


}
