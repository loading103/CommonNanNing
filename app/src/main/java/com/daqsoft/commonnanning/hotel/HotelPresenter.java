package com.daqsoft.commonnanning.hotel;

import android.annotation.SuppressLint;

import com.daqsoft.commonnanning.common.ParamsCommon;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.AdvertEntity;
import com.daqsoft.commonnanning.ui.entity.HotelEntity;
import com.daqsoft.commonnanning.ui.entity.IndexBanner;
import com.example.tomasyb.baselib.base.mvp.BasePresenter;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.util.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * -----------------------------------------------------------------
 *
 * @version 1.0.1
 * @Description 酒店p层
 * @Author 董彧傑
 * @CreateDate 2019-3-20 15:54
 * @since jdk1.8.0_201
 * -----------------------------------------------------------------
 */
public class HotelPresenter extends BasePresenter<HotelContact.view> implements HotelContact
        .presenter {

    public HotelPresenter(HotelContact.view view) {
        super(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getBannerData() {
        RetrofitHelper.getApiService().getIndexBannar(ParamsCommon.HOTEL_BANNER).subscribeOn
                (Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BaseResponse<AdvertEntity>>() {
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
                if (null != view) {
                    view.initBanner(mlist);
                }
            }
        });
    }

    @Override
    public void getData(HashMap<String, String> map) {
        loadData(map);
    }


    @SuppressLint("CheckResult")
    public void loadData(HashMap<String, String> map) {
        RetrofitHelper.getApiService()
                .getHotelList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .subscribe(new Consumer<HotelEntity>() {
                    @Override
                    public void accept(HotelEntity bean) throws Exception {
                        LogUtils.e("load page success :  ");
                        view.onDataRefresh(bean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.e("load page fail: " + throwable.getMessage());
                        view.onDataRefresh(null);
                    }
                });
    }

}
