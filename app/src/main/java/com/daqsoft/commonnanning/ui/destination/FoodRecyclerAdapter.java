package com.daqsoft.commonnanning.ui.destination;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.ui.entity.FoodEntity;
import com.example.tomasyb.baselib.base.glide.GlideApp;
import com.example.tomasyb.baselib.widget.img.RoundImageView;

import java.util.List;

/**
 * 目的地的美食的列表适配器
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018/7/23 0023
 * @since JDK 1.8
 */
public class FoodRecyclerAdapter extends RecyclerView.Adapter<FoodRecyclerAdapter.FoodViewHolder> {

    private Activity activity;
    private List<FoodEntity> foodList;

    public FoodRecyclerAdapter(Activity mActicity, List<FoodEntity> data) {
        this.activity = mActicity;
        this.foodList = data;
    }


    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_scenic_spot_destination,
                null);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FoodViewHolder holder, final int position) {
        holder.name.setText(foodList.get(position).getName());
        GlideApp.with(activity).load(foodList.get(position).getCoverOneToOne())
                .placeholder(R.mipmap.common_ba_banner)
                .error(R.mipmap.common_ba_banner)
                .into(holder.img);
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build(Constant.ACTIVITY_FOOD_DETIAL)
                        .withString("ID", foodList.get(position).getId() + "")
                        .navigation();
                ;
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {

        private RoundImageView img;
        private TextView name;
        private LinearLayout ll;

        public FoodViewHolder(View itemView) {
            super(itemView);
            img = (RoundImageView) itemView.findViewById(R.id.iv_item_img);
            name = (TextView) itemView.findViewById(R.id.tv_item_name);
            ll = (LinearLayout) itemView.findViewById(R.id.ll_item);
        }
    }
}
