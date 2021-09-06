package com.daqsoft.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daqsoft.bean.WalkDetailBean;
import com.daqsoft.guide.R;
import com.daqsoft.view.mapview.MyMapView;


/**
 * 线路具体的浏览点展示适配器
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-3-20.
 * @since JDK 1.8
 */

public class GuideLineAdapter extends RecyclerBaseAdapter<WalkDetailBean, GuideLineAdapter
        .MyHolder> {


    private MyMapView myMapView;

    private Listener listener;

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public MyMapView getMyMapView() {
        return myMapView;
    }

    public void setMyMapView(MyMapView myMapView) {
        this.myMapView = myMapView;
    }

    public GuideLineAdapter(Context context) {
        super(context);
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = this.inflater.inflate(R.layout.item_line_content_list, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        WalkDetailBean walkDetailBean = this.list.get(position);
        holder.tvNum.setText((position + 1) + "");
        holder.tvName.setText(walkDetailBean.getName());
        if (position == (this.list.size() - 1)) {
            holder.tvLine.setVisibility(View.GONE);
        } else {
            holder.tvLine.setVisibility(View.VISIBLE);
        }
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setSelected(false);
                }
                list.get(position).setSelected(true);
                notifyDataSetChanged();
                listener.setListener(position);
            }
        });
        if (walkDetailBean.isSelected()) {
            holder.tvNum.setSelected(true);
        } else {
            holder.tvNum.setSelected(false);
        }


    }

    public interface Listener {
        void setListener(int position);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        /**
         * 序号
         */
        private TextView tvNum;
        /**
         * 名字
         */
        private TextView tvName;
        /**
         * 线
         */
        private TextView tvLine;

        private LinearLayout ll;

        public MyHolder(View itemView) {
            super(itemView);
            tvNum = (TextView) itemView.findViewById(R.id.tv_item_number);
            tvName = (TextView) itemView.findViewById(R.id.tv_item_name);
            tvLine = (TextView) itemView.findViewById(R.id.tv_item_line);
            ll = (LinearLayout) itemView.findViewById(R.id.item_line);
        }
    }
}
