package com.daqsoft.commonnanning.ui.destination;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 通用的适配器
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2017/11/16.
 * @since JDK 1.8
 */
public abstract class GridAdapter<T> extends BaseAdapter {
    protected Activity mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private int layoutId;
    private static int mPosition = 0;
    private String type;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public GridAdapter(Activity context, List<T> datas, int layoutId) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        if (null == mDatas) {
            return 0;
        }
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        if (null == mDatas) {
            return null;
        }
        if (mDatas.size() == 0) {
            return null;
        }
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridViewHolder holder = GridViewHolder.get(mContext, convertView, parent,
                layoutId, position);
        setPosition(position);
        convert(holder, getItem(position));
        return holder.getConvertView();
    }

    public static void setPosition(int position) {
        mPosition = position;
    }

    public static int getPosition() {
        return mPosition;
    }

    public abstract void convert(GridViewHolder holder, T t);


}
