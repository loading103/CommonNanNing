package com.daqsoft.commonnanning.ui.main.contract;

import android.content.Context;

import com.daqsoft.commonnanning.ui.country.entity.CountryBean;
import com.daqsoft.commonnanning.ui.entity.GuideBean;
import com.daqsoft.commonnanning.ui.entity.IndexActivity;
import com.daqsoft.commonnanning.ui.entity.IndexBanner;
import com.daqsoft.commonnanning.ui.entity.IndexMenu;
import com.daqsoft.commonnanning.ui.entity.MyStrategyListBean;
import com.daqsoft.commonnanning.ui.entity.PanoramaListBean;
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
public interface IndexNewContact {
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
         * 设置导游导览数据
         *
         * @param list
         */
        void setMapData(List<GuideBean> list);

        /**
         * 线路列表
         *
         * @param list
         */
        void setLineData(List<MyStrategyListBean> list);

        /**
         * 设置乡村旅游数据
         *
         * @param list
         */
        void setRuRalData(List<CountryBean> list);

        /**
         * 设置农家乐
         *
         * @param list
         */
        void setFamerData(List<CountryBean> list);

        /**
         * 设置节庆活动数据
         *
         * @param list 数据集合
         */
        void setActivityData(List<IndexActivity> list);

    }

    /**
     * P层，网络请求数据交互层
     */
    interface presenter extends IBasePresenter {
        /**
         * 获取线路数据
         */
        void getLineData();

        /**
         * 获取720
         */
        void getPanoramaList();

        /**
         * 获取banner数据
         */
        void getBannerData();

        /**
         * 设置菜单数据
         */
        void setMenuData(Context context);

        /**
         * 获取导游导览列表数据
         */
        void getMapGuideList();

        /**
         * 获取乡村旅游数据
         */
        void getRuralData();

        /**
         * 农家乐
         */
        void getFamerData();

        /**
         * 获取节庆活动
         */
        void getActivityData();
    }
}
