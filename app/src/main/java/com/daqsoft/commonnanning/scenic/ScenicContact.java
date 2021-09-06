package com.daqsoft.commonnanning.scenic;

import com.daqsoft.commonnanning.ui.entity.IndexBanner;
import com.daqsoft.commonnanning.ui.entity.ScenicEntity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.mvp.IBaseView;

import java.util.HashMap;
import java.util.List;

/**
 * -----------------------------------------------------------------
 *
 * @version 1.0.1
 * @Description 景区接口
 * @Author 董彧傑
 * @CreateDate 2019-3-23 9:25
 * @since jdk1.8.0_201
 * -----------------------------------------------------------------
 */
public interface ScenicContact {
    /**
     * view接口
     */
    interface view extends IBaseView {

        void showProgress();

        void hideProgress();

        void initBanner(List<IndexBanner> list);

        void onDataRefresh(ScenicEntity list);

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
         *
         * @param map 筛选条件
         */
        void getData(HashMap<String, String> map);
    }
}
