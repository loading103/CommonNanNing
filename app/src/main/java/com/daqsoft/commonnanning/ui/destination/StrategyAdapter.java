package com.daqsoft.commonnanning.ui.destination;

import android.app.Activity;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.ui.entity.MyStrategyListBean;
import com.example.tomasyb.baselib.base.glide.GlideApp;
import com.example.tomasyb.baselib.rvadapter.CommonAdapter;
import com.example.tomasyb.baselib.rvadapter.base.ViewHolder;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.widget.img.RoundImageView;

import java.util.List;

/**
 * 黔东南第二个首页面中游记列表适配器
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018.05.07
 * @since JDK 1.8
 */
public class StrategyAdapter extends CommonAdapter<MyStrategyListBean> {
    public StrategyAdapter(Activity context, int layoutId, List<MyStrategyListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, final MyStrategyListBean resource, int position) {
        GlideApp.with(mContext).load(resource.getCoverTwoToOne()).
                error(R.mipmap.common_ba_banner).placeholder(R.mipmap.common_ba_banner)
                .into((RoundImageView) holder.getView(R.id.item_strategy_index_img));
        // 是否推荐 1：否 0 ：是
        holder.setVisible(R.id.item_strategy_index_recommend,
                resource.getRecommend() == 0 ? true : false);
        holder.getView(R.id.item_strategy_index_recommend).getBackground().setAlpha(150);
        holder.setVisible(R.id.item_strategy_index_region,
                ObjectUtils.isNotEmpty(resource.getRegionName()));
        holder.getView(R.id.item_strategy_index_region).getBackground().setAlpha(150);
        holder.setText(R.id.item_strategy_index_region, resource.getRegionName());
        holder.setText(R.id.item_strategy_index_name, resource.getTitle());
        holder.setText(R.id.item_strategy_index_time, resource.getUpdateTime());
        holder.setText(R.id.item_strategy_index_collect, ObjectUtils.isNotEmpty(resource
                .getCollection())
                ? resource.getCollection() + "" : "0");
        holder.setText(R.id.item_strategy_index_like, ObjectUtils.isNotEmpty(resource
                .getGivepoint()) ?
                resource.getGivepoint() + "" : "0");
        holder.setText(R.id.item_strategy_index_comment, ObjectUtils.isNotEmpty(resource
                .getComment()) ?
                resource.getComment() + "" : "0");
        holder.setOnClickListener(R.id.item_scenic_two_ll, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build(Constant.ACTIVITY_LINEDETAIL)
                        .withString("mId", resource.getId() + "")
                        .navigation();
            }
        });
    }
}