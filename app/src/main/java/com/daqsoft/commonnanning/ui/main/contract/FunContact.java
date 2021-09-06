package com.daqsoft.commonnanning.ui.main.contract;

import com.daqsoft.commonnanning.ui.entity.IndexActivity;
import com.daqsoft.commonnanning.ui.entity.IndexBanner;
import com.daqsoft.commonnanning.ui.entity.IndexScenic;
import com.daqsoft.commonnanning.ui.entity.MyStrategyListBean;
import com.daqsoft.commonnanning.ui.entity.PanoramaListBean;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.mvp.IBaseView;

import java.util.List;

/**
 * 功能
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-3-23.11:22
 * @since JDK 1.8
 */

public interface FunContact {
    /**
     * view
     */
    interface view extends IBaseView {
        /**
         * 获取720
         *
         * @param list
         */
        void setPanorama(List<PanoramaListBean> list);

        /**
         * 好玩数据
         *
         * @param list
         */
        void initBanner(List<IndexBanner> list);

        /**
         * 线路列表
         *
         * @param list
         */
        void setLineData(List<MyStrategyListBean> list);

        /**
         * 节庆活动
         *
         * @param list
         */
        void refreshActivity(List<IndexActivity> list);

        /**
         * 设置推荐景区数据
         */
        void setBestScenicData(List<IndexScenic> mlist);
    }

    /**
     * 数据请求类
     */
    interface presenter extends IBasePresenter {
        /**
         * 获取720
         */
        void getPanoramaList();

        /**
         * 获取banner数据
         */
        void getBannerData();

        /**
         * 获取线路数据
         */
        void getLineData();

        /**
         * 获取节庆活动数据
         *
         * @param name
         */
        void getActivityList(String name);

        /**
         * 获取推荐景点
         */
        void getBestScenic();
    }
}
