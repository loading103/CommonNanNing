package com.daqsoft.commonnanning.food;

import android.annotation.SuppressLint;

import com.daqsoft.commonnanning.common.ParamsCommon;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.AdvertEntity;
import com.daqsoft.commonnanning.ui.entity.IndexBanner;
import com.example.tomasyb.baselib.base.mvp.BasePresenter;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * -----------------------------------------------------------------
 *
 * @version 1.0.1
 * @Description 美食p层
 * @Author 董彧傑
 * @CreateDate 2019-3-21 8:46
 * @since jdk1.8.0_201
 * -----------------------------------------------------------------
 */
public class FoodPresenter extends BasePresenter<FoodContact.view> implements FoodContact
        .presenter {


    public FoodPresenter(FoodContact.view view) {
        super(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getBannerData() {
        RetrofitHelper.getApiService().getIndexBannar(ParamsCommon.FOOD_BANNER).subscribeOn
                (Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe
                (disposable -> addDisposable(disposable)).subscribe(new Consumer<BaseResponse<AdvertEntity>>() {
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
}
