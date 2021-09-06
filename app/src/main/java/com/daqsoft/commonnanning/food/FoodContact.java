package com.daqsoft.commonnanning.food;

import com.daqsoft.commonnanning.ui.entity.IndexBanner;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.mvp.IBaseView;

import java.util.List;

/**
 * -----------------------------------------------------------------
 *
 * @version 1.0.1
 * @Description:美食接口
 * @Author 董彧傑
 * @CreateDate 2019-3-21 8:46
 * @since jdk1.8.0_201
 * -----------------------------------------------------------------
 */
public interface FoodContact {
    /**
     * view接口
     */
    interface view extends IBaseView {
        void showProgress();
        void hideProgress();
        void initBanner(List<IndexBanner> list);

    }

    /**
     * mode接口
     */
    interface presenter extends IBasePresenter {
        /**
         * 获取banner数据
         */
        void getBannerData();

    }
}