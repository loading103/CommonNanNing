package com.daqsoft.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daqsoft.bean.MapConfigBean;
import com.daqsoft.guide.R;
import com.daqsoft.utils.Config;

/**
 * 导游导览底部类型适配器
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-4-18
 * @since JDK 1.8
 */

public class GuideConfigAdapter extends RecyclerBaseAdapter<MapConfigBean, GuideConfigAdapter
        .Myholder> {
    /**
     * 文本样式
     */
    private Typeface typeface;
    /**
     * 类型点击监听
     */
    private OnTypeclickListener listener;


    public void setSelectData(MapConfigBean selectData) {
        this.selectData = selectData;
    }

    /**
     * 地图配置
     */
    private MapConfigBean selectData;
    /**
     * TTF文本前段
     */
    private static final String TAG = "&#x";

    /**
     * 设置监听
     *
     * @param listener
     */
    public void setListener(OnTypeclickListener listener) {
        this.listener = listener;
    }

    public GuideConfigAdapter(Context context) {
        super(context);
        // 获取本地的字体图标文件
        typeface = Typeface.createFromAsset(context.getAssets(), "iconfont.ttf");
    }

    @Override
    public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = this.inflater.inflate(R.layout.guide_adapter_config, parent, false);
        return new Myholder(v);
    }

    @Override
    public void onBindViewHolder(final Myholder holder, int position) {
        final MapConfigBean bean = this.list.get(position);
        if (Config.CITY_NAME.equals("乌鲁木齐") && "厕所".equals(bean.getName())) {
            holder.rbtn_type.setText("旅游厕所");
        } else {
            holder.rbtn_type.setText(bean.getName());
        }
        // 获取字体图标的颜色
        String value = bean.getIconFont() + ";";
        holder.rbtn_type_img.setText(Html.fromHtml(value));
        // 是否有选中状态
        if (bean.isIscheck()) {
            if (!TextUtils.isEmpty(bean.getColor())) {
                holder.rbtn_type_img.setTextColor(Color.parseColor("#" + bean.getColor()));
                holder.rbtn_type.setTextColor(Color.parseColor("#" + bean.getColor()));
            }
        } else {
            holder.rbtn_type_img.setTextColor(Color.parseColor("#b4bfbe"));
            holder.rbtn_type.setTextColor(Color.parseColor("#333333"));
        }
        // 用本地的字体文件的对象渲染字体图标
        holder.rbtn_type.setTypeface(typeface);
        holder.rbtn_type_img.setTypeface(typeface);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectData != null) {
                    selectData.setIscheck(false);
                }
                selectData = bean;
                bean.setIscheck(true);
                notifyDataSetChanged();
                if (listener != null) {
                    listener.onCLick(bean);
                }
            }
        });
    }


    public MapConfigBean getSelect() {
        return selectData;
    }

    public class Myholder extends RecyclerView.ViewHolder {
        TextView rbtn_type;
        TextView rbtn_type_img;

        public Myholder(View itemView) {
            super(itemView);
            rbtn_type = (TextView) itemView.findViewById(R.id.rbtn_type);
            rbtn_type_img = (TextView) itemView.findViewById(R.id.rbtn_type_img);
        }
    }

    /**
     * 监听
     */
    public interface OnTypeclickListener {
        void onCLick(MapConfigBean bean);
    }
}
