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
import com.daqsoft.commonnanning.ui.entity.ScenicEntity;
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
public class SpotRecyclerAdapter extends RecyclerView.Adapter<SpotRecyclerAdapter.SpotViewHolder> {

    private Activity activity;
    private List<ScenicEntity.DatasBean> scenicList;

    public SpotRecyclerAdapter(Activity mActicity, List<ScenicEntity.DatasBean> data) {
        this.activity = mActicity;
        this.scenicList = data;
    }


    @Override
    public SpotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_scenic_spot_destination,
                null);
        return new SpotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SpotViewHolder holder, final int position) {
        holder.name.setText(scenicList.get(position).getName());
        GlideApp.with(activity).load(scenicList.get(position).getPicture())
                .placeholder(R.mipmap.comimg_fail_240_180)
                .error(R.mipmap.comimg_fail_240_180)
                .into(holder.img);
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ARouter.getInstance().build(Constant.ACTIVITY_SCENIC_DETAIL)
                            .withString("mId", scenicList.get(position).getId())
                            .navigation();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return scenicList.size();
    }

    public class SpotViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView name;
        private LinearLayout ll;

        public SpotViewHolder(View itemView) {
            super(itemView);
            img = (RoundImageView) itemView.findViewById(R.id.iv_item_img);
            name = (TextView) itemView.findViewById(R.id.tv_item_name);
            ll = (LinearLayout) itemView.findViewById(R.id.ll_item);
        }
    }
}
