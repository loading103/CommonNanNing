package com.daqsoft.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.daqsoft.guide.R;


/**
 * @author MouJunFeng
 * @time 2018-3-20
 * @since JDK 1.8
 * @version 1.0.0
 */

public class GuidequeryAdapter extends RecyclerBaseAdapter<String, GuidequeryAdapter.MyHolder> {

    public GuidequeryAdapter(Context context) {
        super(context);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = this.inflater.inflate(R.layout.guide_adapter_guidequery, parent,false);
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
