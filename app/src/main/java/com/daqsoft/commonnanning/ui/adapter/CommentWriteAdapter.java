package com.daqsoft.commonnanning.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.daqsoft.commonnanning.R;
import com.example.tomasyb.baselib.base.glide.GlideApp;

import java.util.ArrayList;
import java.util.List;

/**
 * 写评论的适配器
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-7-26.17:52
 * @since JDK 1.8
 */

public class CommentWriteAdapter extends RecyclerView.Adapter<CommentWriteAdapter.ViewHolder> {
    /**
     * LayoutInflater
     */
    private LayoutInflater mInflater;

    /**
     * Context
     */
    private Context context;
    private List<String> mImgList = new ArrayList<>();
    private int selectMax = 5;
    /**
     * 点击添加图片跳转
     */
    private onAddPicClickListener mOnAddPicClickListener;
    /**
     * 添加图片按钮
     */
    public static final int TYPE_ADD = 1;
    /**
     * 展示图片
     */
    public static final int TYPE_PICTURE = 2;

    public CommentWriteAdapter(Context context_, onAddPicClickListener mOnAddPicClickListener) {
        this.context = context_;
        mInflater = LayoutInflater.from(context_);
        this.mOnAddPicClickListener = mOnAddPicClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.gv_filter_image,
                parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_ADD) {
            holder.mImg.setImageResource(R.mipmap.complaint_publish_addpic_normal);
            holder.mImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 添加图片
                    mOnAddPicClickListener.onAddPicClick();
                }
            });
            holder.ll_del.setVisibility(View.INVISIBLE);
        } else {
            holder.ll_del.setVisibility(View.VISIBLE);
            // 点击删除
            holder.ll_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = holder.getAdapterPosition();
                    // 这里有时会返回-1造成数据下标越界,具体可参考getAdapterPosition()源码，
                    // 通过源码分析应该是bindViewHolder()暂未绘制完成导致，知道原因的也可联系我~感谢
                    if (index != RecyclerView.NO_POSITION) {
                        mImgList.remove(index);
                        notifyItemRemoved(index);
                        notifyItemRangeChanged(index, mImgList.size());
                    }
                }
            });
            GlideApp.with(context)
                    .load(mImgList.get(position))
                    .placeholder(R.mipmap.banner_default)
                    .error(R.mipmap.banner_default)
                    .into(holder.mImg);
        }
    }


    @Override
    public int getItemCount() {
        if (mImgList.size() < selectMax) {
            return mImgList.size() + 1;
        } else {
            return mImgList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImg;
        LinearLayout ll_del;

        public ViewHolder(View view) {
            super(view);
            mImg = (ImageView) view.findViewById(R.id.fiv);
            ll_del = (LinearLayout) view.findViewById(R.id.ll_del);
        }
    }

    public void setSelectMax(int selectMax) {
        this.selectMax = selectMax;
    }

    public void setList(List<String> list) {
        this.mImgList = list;
    }

    private boolean isShowAddItem(int position) {
        int size = mImgList.size() == 0 ? 0 : mImgList.size();
        return position == size;
    }

    public List<String> getlist() {
        return mImgList;
    }

    public interface onAddPicClickListener {
        void onAddPicClick();
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowAddItem(position)) {
            return TYPE_ADD;
        } else {
            return TYPE_PICTURE;
        }
    }
}
