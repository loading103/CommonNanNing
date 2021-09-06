package com.daqsoft.commonnanning.food_detial;

import android.annotation.SuppressLint;

import com.daqsoft.commonnanning.common.SourceType;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.CommentEntity;
import com.daqsoft.commonnanning.ui.entity.FoodDetialEntity;
import com.example.tomasyb.baselib.base.mvp.BasePresenter;
import com.example.tomasyb.baselib.util.LogUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * -----------------------------------------------------------------
 *
 * @version 1.0.1
 * @Description ${todo}
 * @Author 董彧傑
 * @CreateDate 2019-3-28 13:37
 * @since jdk1.8.0_201
 * -----------------------------------------------------------------
 */
public class FoodDetialPresenter extends BasePresenter<FoodDetialContact.view> implements FoodDetialContact.presenter {
    public FoodDetialPresenter(FoodDetialContact.view view) {
        super(view);
    }


    @Override
    public void getData(String id) {
        loadData(id);
        getComment(id);

    }

    @SuppressLint("CheckResult")
    @Override
    public void getComment(String id) {
        //view.showProgress();
        RetrofitHelper.getApiService().getComment("" + id, SourceType.FOOD_SOURCE_TYPE, "1", "10")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .subscribe(new Consumer<CommentEntity>() {
                    @Override
                    public void accept(CommentEntity bean) throws Exception {
                        LogUtils.e("getComment success :  ");
                        //view.hideProgress();
                        view.loadCommentSuccess(bean.getDatas());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.e("load page fail: "+throwable.getMessage());
                        //view.hideProgress();
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void loadData(String id) {
        //view.showProgress();
        RetrofitHelper.getApiService().getFoodDetial("", "" + id, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .subscribe(new Consumer<FoodDetialEntity>() {
                    @Override
                    public void accept(FoodDetialEntity bean) throws Exception {
                        LogUtils.e("load page success :  ");
                        //view.hideProgress();
                        view.onDataRefresh(bean.getData());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.e("load page fail: "+throwable.getMessage());
                        //view.hideProgress();
                    }
                });
    }

}
