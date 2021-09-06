package com.daqsoft.commonnanning.ui.holder;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.ui.entity.IndexBanner;
import com.daqsoft.commonnanning.utils.GlideUtils;

import io.agora.yview.banner.holder.Holder;

/**
 * Created by yanb on 15/8/4.
 * 网络图片加载例子
 */
public class NetWorkImageHolderView extends Holder<IndexBanner> {
    private ImageView imageView;
    private Activity activity;

    public NetWorkImageHolderView(View itemView, Activity context) {
        super(itemView);
        activity = context;
    }

    @Override
    protected void initView(View itemView) {
        imageView =itemView.findViewById(R.id.ivPost);
    }

    @Override
    public void updateUI(IndexBanner data) {
        GlideUtils.loadImage(activity,imageView,data.getImg(),R.mipmap.common_img_fail_h300);
    }
}
