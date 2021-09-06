package com.daqsoft.commonnanning.ui.main.contract;

import com.daqsoft.commonnanning.ui.entity.AdvertEntity;
import com.daqsoft.commonnanning.ui.entity.GuideBean;
import com.daqsoft.commonnanning.ui.entity.IndexActivity;
import com.daqsoft.commonnanning.ui.entity.IndexBanner;
import com.daqsoft.commonnanning.ui.entity.IndexMenu;
import com.daqsoft.commonnanning.ui.entity.NewsListEntity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.mvp.IBaseView;

import java.util.List;

/**
 * 首页
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-9-5.11:46
 * @since JDK 1.8
 */
public interface IndexContact {
    /**
     * view
     */
    interface view extends IBaseView {
        /**
         * 刷新初始化
         */
        void initRefresh();

        /**
         * 初始化banner
         *
         * @param list
         */
        void initBanner(List<IndexBanner> list);

        /**
         * 初始化菜单
         *
         * @param mDatas
         */
        void initRv(List<IndexMenu> mDatas);

        /**
         * 设置通知数据
         *
         * @param mDatas
         */
        void setNowData(List<String> mDatas, List<NewsListEntity> noticeList);

        /**
         * 设置公告无数据的情况
         */
        void setNoDataNotify();

        /**
         * 显示进度条
         */
        void showProgress();

        /**
         * 隐藏进度条
         */
        void hideProgress();

        /**
         * 节庆活动
         *
         * @param list
         */
        void refreshActivity(List<IndexActivity> list);

        /**
         * 设置导游导览数据
         *
         * @param list
         */
        void setMapData(List<GuideBean> list);

        /**
         * 玩转南宁
         *
         * @param list
         */
        void setPlayList(List<AdvertEntity> list);
    }

    /**
     * P层，网络请求数据交互层
     */
    interface presenter extends IBasePresenter {
        /**
         * 获取banner数据
         */
        void getBannerData();

        /**
         * 设置菜单数据
         */
        void setMenuData();

        /**
         * 获取通知数据
         */
        void getNowData();

        /**
         * 获取节庆活动数据
         *
         * @param name
         */
        void getActivityList(String name);

        /**
         * 获取导游导览列表数据
         */
        void getMapGuideList();

        /**
         * 玩转南宁
         */
        void getPlayList();
    }
}
