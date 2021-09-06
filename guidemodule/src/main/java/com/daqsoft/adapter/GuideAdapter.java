package com.daqsoft.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.daqsoft.guide.R;


/**
 * 查询适配器
 * @author MouJunFeng
 * @time 2018-3-20.
 * @since JDK 1.8
 * @version 1.0.0
 */

public class GuideAdapter extends RecyclerBaseAdapter<String, GuideAdapter.MyHolder> {
    public GuideAdapter(Context context) {
        super(context);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = this.inflater.inflate(R.layout.guide_adapter_guide, parent,false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int viewType) {

    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public MyHolder(View itemView) {
            super(itemView);
        }
    }
}
