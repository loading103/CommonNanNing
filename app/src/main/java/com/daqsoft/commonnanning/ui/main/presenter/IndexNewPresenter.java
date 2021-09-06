package com.daqsoft.commonnanning.ui.main.presenter;

import android.content.Context;

import com.daqsoft.android.ProjectConfig;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.ParamsCommon;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.IndexActivity;
import com.daqsoft.commonnanning.ui.entity.IndexBanner;
import com.daqsoft.commonnanning.ui.entity.IndexMenu;
import com.daqsoft.commonnanning.ui.entity.PanoramaListBean;
import com.daqsoft.commonnanning.ui.main.contract.IndexNewContact;
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

public class IndexNewPresenter extends BasePresenter<IndexNewContact.view> implements
        IndexNewContact.presenter {
    @Override
    public void getLineData() {
        RetrofitHelper.getApiService()
                .getLineList("1", "15", "1", "")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> addDisposable(disposable))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(line ->
                                view.setLineData(line.getDatas()),
                        throwable ->
                                view.setLineData(null)
                );
    }

    public IndexNewPresenter(IndexNewContact.view view) {
        super(view);
    }


    @Override
    public void getBannerData() {
        RetrofitHelper.getApiService()
                .getIndexBannar(ParamsCommon.INDEXTOP_BANNER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> addDisposable(disposable))
                .subscribe(bean -> {
                    List<IndexBanner> mlist = new ArrayList<>();
                    mlist.clear();
                    if (bean.getCode() == 0 && bean.getDatas().size() > 0) {
                        for (int i = 0; i < bean.getDatas().size(); i++) {
                            IndexBanner beans = new IndexBanner();
                            beans.setId(bean.getDatas().get(i).getId());
                            beans.setImg(bean.getDatas().get(i).getPics().get(0));
                            beans.setUrl(bean.getDatas().get(i).getUrl());
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
                }, throwable -> {
                    List<IndexBanner> mlist = new ArrayList<>();
                    IndexBanner beans = new IndexBanner();
                    beans.setId("");
                    beans.setImg("");
                    mlist.add(beans);
                    view.initBanner(mlist);
                });
    }

    @Override
    public void setMenuData(Context context) {

        Integer[] img = ProjectConfig.INDEX_MENU_ICON;
        String[] name = context.getResources().getStringArray(R.array.index_menu_name);
        String[] types = context.getResources().getStringArray(R.array.index_menu_type);
        List<IndexMenu> mDatas = new ArrayList<>();
        for (int i = 0; i < name.length; i++) {
            IndexMenu bean = new IndexMenu();
            bean.setmImg(img[i]);
            bean.setName(name[i]);
            bean.setType(types[i]);
            mDatas.add(bean);
        }
        view.initRv(mDatas);
    }

    @Override
    public void getPanoramaList() {
        RetrofitHelper.getApiService().getPanoramaList("1", "6").subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BaseResponse<PanoramaListBean>>() {
            @Override
            public void accept(BaseResponse<PanoramaListBean> bean) throws Exception {
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
    public void getMapGuideList() {
        RetrofitHelper.getApiService()
                .getMapGuideListNear(
                        SPUtils.getInstance().getString(SPCommon.LATITUDE),
                        SPUtils.getInstance().getString(SPCommon.LONGITUDE),
                        "",
                        "1",
                        "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> addDisposable(disposable))
                .subscribe(bean -> {
                            if (bean.getCode() == 0) {
                                view.setMapData(bean.getDatas());
                            } else {
                                view.setMapData(null);
                            }

                        }, throwable ->
                                view.setMapData(null)
                );
    }

    @Override
    public void getRuralData() {
        RetrofitHelper.getApiService()
                .getCountryList(
                        15,
                        "",
                        1,
                        "",
                        972,
                        "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> addDisposable(disposable))
                .subscribe(bean -> {
                            if (bean.getCode() == 0 && bean.getDatas().size() > 0) {
                                view.setRuRalData(bean.getDatas());
                            } else {
                                view.setRuRalData(null);
                            }
                        }, throwable ->
                                view.setRuRalData(null)
                );
    }

    @Override
    public void getFamerData() {
        RetrofitHelper.getApiService()
                .getCountryList(
                        15,
                        "",
                        1,
                        "",
                        971,
                        "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> addDisposable(disposable))
                .subscribe(bean -> {
                    if (bean.getCode() == 0 && bean.getDatas().size() > 0) {
                        view.setFamerData(bean.getDatas());
                    } else {
                        view.setFamerData(null);
                    }
                }, throwable -> view.setFamerData(null));
    }

    /**
     * 获取节庆活动的数据
     */
    @Override
    public void getActivityData() {
        RetrofitHelper.getApiService()
                .getActivityList(
                        "1",
                        "2",
                        "",
                        ParamsCommon.ACTIVITY_CHANELCODE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> addDisposable(disposable))
                .subscribe(bean -> {
                    if (bean.getCode() == 0 && bean.getDatas().size() > 0) {
                        view.setActivityData(bean.getDatas());
                    } else {
                        view.setActivityData(null);
                    }
                }, throwable -> view.setActivityData(null));
    }


}
