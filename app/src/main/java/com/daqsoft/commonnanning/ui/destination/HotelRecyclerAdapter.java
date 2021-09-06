package com.daqsoft.commonnanning.ui.destination;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.ui.entity.HotelEntity;
import com.example.tomasyb.baselib.base.glide.GlideApp;
import com.example.tomasyb.baselib.widget.img.RoundImageView;

import java.util.List;

/**
 * 景区景点的列表适配器
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018/7/23 0023
 * @since JDK 1.8
 */
public class HotelRecyclerAdapter extends RecyclerView.Adapter<HotelRecyclerAdapter
        .HotelViewHolder> {

    private Activity activity;
    private List<HotelEntity.DatasBean> hotelList;

    public HotelRecyclerAdapter(Activity mActicity, List<HotelEntity.DatasBean> data) {
        this.activity = mActicity;
        this.hotelList = data;
    }


    @Override
    public HotelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_hotel_destination, null);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HotelViewHolder holder, final int position) {
        holder.name.setText(hotelList.get(position).getName());
        GlideApp.with(activity).load(hotelList.get(position).getPicture())
                .placeholder(R.mipmap.comimg_fail_240_180)
                .error(R.mipmap.comimg_fail_240_180)
                .into(holder.img);
        if (hotelList.get(position).getCommentLevel().equals("0")){
            holder.tvScore.setVisibility(View.INVISIBLE);
        }else {
            holder.tvScore.setVisibility(View.VISIBLE);
            holder.tvScore.setText(hotelList.get(position).getCommentLevel() + "分");
        }
        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build(Constant.ACTIVITY_HOTEL_DETAIL)
                        .withString("mId", hotelList.get(position).getId()).navigation();
            }
        });
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public class HotelViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView name;
        private TextView tvScore;
        private LinearLayout llItem;

        public HotelViewHolder(View itemView) {
            super(itemView);
            img = (RoundImageView) itemView.findViewById(R.id.iv_item_img_des);
            name = (TextView) itemView.findViewById(R.id.tv_item_name_des);
            tvScore = (TextView) itemView.findViewById(R.id.tv_item_score_des);
            llItem = (LinearLayout) itemView.findViewById(R.id.ll_item);
        }
    }
}
