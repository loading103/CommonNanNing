package com.daqsoft.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView.Adapter 的基类
 * E 为集合数据类型泛型
 * T 为自定义RecycleViewHolder的子类
 * @author MouJunFeng
 * @time 2018-3-14
 * @since JDK 1.8
 * @version 1.0.0
 */

public abstract class RecyclerBaseAdapter<E, T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    protected LayoutInflater inflater;
    /**
     * 上下文
     */
    protected Context context;
    /**
     * 数据集
     */
    protected List<E> list;

    public RecyclerBaseAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = new ArrayList<E>();
    }

    @Override
    public abstract T onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(T holder, int position);

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<E> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    /**
     * 添加数据
     * @param e
     */
    public void add(E e) {
        this.list.add(e);
        this.notifyDataSetChanged();
    }

    /**
     * 添加数据
     * @param e 数据源
     * @param index 索引位置
     */
    public void add(E e, int index) {
        this.list.add(index, e);
        this.notifyItemChanged(index);
    }

    /**
     * 修改索引位置数据
     * @param e 数据源
     * @param index 索引值
     */
    public void set(E e, int index) {
        if (index < 0 || index >= this.list.size()) {
            return;
        }
        this.list.set(index, e);
        this.notifyItemChanged(index);
    }

    /**
     * 获得索引位置的值
     * @param position 索引
     * @return 数据
     */
    public E get(int position) {
        if (position < 0 || position >= this.list.size()) {
            return null;
        }
        return this.list.get(position);
    }

    /**
     * 添加数据
     * @param eList 数据源
     */
    public void addAll(List<E> eList) {
        this.list.addAll(eList);
        this.notifyDataSetChanged();
    }

    /**
     * 删除数据
     * @param index 索引
     */
    public void delete(int index) {
        this.list.remove(index);
        this.notifyDataSetChanged();
    }

    /**
     * 获得数据集
     * @return
     */
    public List<E> getList() {
        return list;
    }

    /**
     * 清除数据集
     */
    public void clear() {
        this.list.clear();
        this.notifyDataSetChanged();
    }
}
