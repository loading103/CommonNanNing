package com.daqsoft.commonnanning.hotel;

import com.daqsoft.commonnanning.ui.entity.HotelEntity;
import com.daqsoft.commonnanning.ui.entity.IndexBanner;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.mvp.IBaseView;

import java.util.HashMap;
import java.util.List;


/**
 * -----------------------------------------------------------------
 *
 * @version 1.0.1
 * @Description 酒店接口
 * @Author 董彧傑
 * @CreateDate 2019-3-20 15:56
 * @since jdk1.8.0_201
 * -----------------------------------------------------------------
 */
public interface HotelContact {
    /**
     * view接口
     */
    interface view extends IBaseView {

        void showProgress();

        void hideProgress();

        void initBanner(List<IndexBanner> list);

        void onDataRefresh(HotelEntity list);

    }

    /**
     * mode接口
     */
    interface presenter extends IBasePresenter {

        /**
         * 获取banner数据
         */
        void getBannerData();

        /**
         * 获取数据
         */
        void getData(HashMap<String, String> map);
    }
}
