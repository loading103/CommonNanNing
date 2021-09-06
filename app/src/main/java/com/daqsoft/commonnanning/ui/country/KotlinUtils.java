package com.daqsoft.commonnanning.ui.country;

import android.app.Activity;
import android.view.View;

import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.ui.entity.IndexBanner;
import com.daqsoft.commonnanning.ui.holder.NetWorkImageHolderView;

import java.util.List;

import io.agora.yview.banner.ConvenientBanner;
import io.agora.yview.banner.holder.CBViewHolderCreator;
import io.agora.yview.banner.listener.OnItemClickListener;

/**
 * Kotlin工具类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/5/18 0018
 * @since JDK 1.8
 */
public class KotlinUtils {

    /**
     * @param list
     */
    public static void initBanner(List<IndexBanner> list, ConvenientBanner mBanner, Activity
            activity, boolean canScroll) {
        mBanner.setPages(new CBViewHolderCreator() {
            @Override
            public NetWorkImageHolderView createHolder(View itemView) {
                return new NetWorkImageHolderView(itemView, activity);
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_banner_img;
            }
        }, list)
                // 设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.bga_banner_point_disabled, R.drawable
                        .bga_banner_point_enabled,}).setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        }).setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        //      .setOnPageChangeListener(this)
        // 设置翻页监听
        //        convenientBanner.setManualPageable(false);
        //  设置不能手动影响
        if (list.size() == 1) {
            mBanner.setCanLoop(false);
            mBanner.setPointViewVisible(false);
            canScroll = false;
            mBanner.stopTurning();
        } else {
            canScroll = true;
        }
    }
}
