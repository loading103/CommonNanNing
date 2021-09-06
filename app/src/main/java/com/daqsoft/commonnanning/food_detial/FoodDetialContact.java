package com.daqsoft.commonnanning.food_detial;

import com.daqsoft.commonnanning.ui.entity.CommentEntity;
import com.daqsoft.commonnanning.ui.entity.FoodDetialEntity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.mvp.IBaseView;

import java.util.List;

/**
 * -----------------------------------------------------------------
 *
 * @version 1.0.1
 * @Description 美食详情contact
 * @Author 董彧傑
 * @CreateDate 2019-3-28 10:21
 * @since jdk1.8.0_201
 * -----------------------------------------------------------------
 */
public interface FoodDetialContact {
    /**
     * view接口
     */
    interface view extends IBaseView {

        /**
         * 显示加载状态
         */
        void showProgress();
        /**
         * 隐藏加载状态
         */
        void hideProgress();

        /**
         * 跳转登录
         */
        void goToLoginActivity();

        /**
         * 刷新数据完成,传入数据
         * @param bean
         */
        void onDataRefresh(FoodDetialEntity.DataBean bean);
        void loadCommentSuccess(List<CommentEntity.DatasBean> bean);
    }

    /**
     * mode接口
     */
    interface presenter extends IBasePresenter {

        /**获取数据
         * @param id
         */
        void getData(String id);

        void getComment(String id);

    }
}
