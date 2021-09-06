package com.daqsoft.busquery.buscommon;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.daqsoft.busquery.R;
import com.daqsoft.busquery.bean.NearbyEntity;
import com.library.flowlayout.FlowLayoutManager;

import java.util.List;

/**
 * 附近公交站布局
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-8-1.10:36
 * @since JDK 1.8
 */

public class NearbyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<NearbyEntity> mDtas;

    public NearbyAdapter(Context context, List<NearbyEntity> mDtas) {
        this.context = context;
        this.mDtas = mDtas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NearbyHolder(View.inflate(context, R.layout.iem_busnearby, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final NearbyHolder productHolder = (NearbyHolder) holder;
        NearbyEntity bean = mDtas.get(position);
        final FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        productHolder.mBusStation.setText(bean.getBusStation());
        productHolder.mTvdestence.setText(bean.getDestence());
        productHolder.mRv.setLayoutManager(flowLayoutManager);
        productHolder.mRv.setAdapter(new FlowAdapter(mDtas.get(position).getmBusbean()));
    }

    @Override
    public int getItemCount() {
        return mDtas.size();
    }

    class NearbyHolder extends RecyclerView.ViewHolder {
        // 站台
        private TextView mBusStation;
        // 距离
        private TextView mTvdestence;
        // 适配器
        private RecyclerView mRv;

        public NearbyHolder(View itemView) {
            super(itemView);
            mBusStation = (TextView) itemView.findViewById(R.id.tv_busstation);
            mTvdestence = (TextView) itemView.findViewById(R.id.tvdistence);
            mRv = (RecyclerView) itemView.findViewById(R.id.mRv);
        }
    }

    private int dp2px(float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context
                .getResources().getDisplayMetrics());
    }

    class FlowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<NearbyEntity.BusBean> list;

        public FlowAdapter(List<NearbyEntity.BusBean> list) {
            this.list = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(View.inflate(context, R.layout.flow_item, null));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            TextView textView = ((MyHolder) holder).text;

            final NearbyEntity.BusBean des = list.get(position);
            textView.setBackground(context.getResources().getDrawable(R.drawable
                    .bg_common_solid_gray));
            textView.setText(des.getBusName());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {

            private TextView text;

            public MyHolder(View itemView) {
                super(itemView);
                text = (TextView) itemView.findViewById(R.id.flow_text);
            }
        }
    }
}
