package com.daqsoft.commonnanning.ui.main.contract;

import com.daqsoft.commonnanning.ui.entity.FoundNearEy;
import com.daqsoft.commonnanning.ui.entity.HotelDetail;
import com.daqsoft.commonnanning.ui.mine.interact.entity.CommentBean;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.mvp.IBaseView;

import java.util.List;

/**
 * 酒店详情
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-3-28.9:25
 * @since JDK 1.8
 */

public interface HotelDetailContact {
    /**
     * View层
     */
    interface view extends IBaseView{
        /**
         * 设置基本数据
         * @param bean
         */
        void setBaseData(HotelDetail bean);
        /**
         * 添加地图点位
         */
        void addMapMarker(List<FoundNearEy> list);
        /**
         * 设置底部评论数据
         * @param list 数据list
         * @param allpeople 有多少人评价
         */
        void setCommonInfoData(List<CommentBean> list, String allpeople);

        /**
         * 设置点赞的状态
         * @param isSelected
         */
        void setTumbleStyle(boolean isSelected);
        /**
         * 设置收藏的状态
         * @param isSelected
         */
        void setChangStyle(boolean isSelected);
        /**
         * 跳转到登录
         */
        void goToLoginActivity();
        void setHotelTip(String tip);
    }

    /**
     * P层
     */
    interface presenter extends IBasePresenter{
        /**
         * 获取酒店基础数据
         * @param id
         */
        void getHotelDetail(String id);

        /**
         * 获取酒店小贴士
         * @param id
         */
        void getListTip(String id);
        /**
         * 获取身边资源地图
         */
        void getAroundMap(String latitude,String longitude,String sourceType,String distance);
        /**
         * 获取评论数据
         */
        void getCommonInfoData(String mRid,String soucetype);

        /**
         * 保存点赞
         */
        void saveThumb(String content,String reId,String title);

        /**
         * 删除点赞
         */
        void delThumb(String reId);

        /**
         * 保存收藏
         */
        void saveCollection(String strategyId,String title,String content);
        /**
         * 删除收藏
         */
        void delCollection(String reId);
    }
}
