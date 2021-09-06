package com.daqsoft.commonnanning.ui.adapter;

import android.webkit.WebView;
import android.widget.ImageView;

import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.ComUtils;
import com.daqsoft.commonnanning.ui.entity.StraveXqEy;
import com.example.tomasyb.baselib.adapter.BaseMultiItemQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.glide.GlideApp;

import java.util.List;

/**
 * 游记攻略详情是匹配器
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-10-13.14:19
 * @since JDK 1.8
 */

public class MultipleTraveAdapter extends BaseMultiItemQuickAdapter<StraveXqEy,BaseViewHolder> {

    public MultipleTraveAdapter(List<StraveXqEy> data) {
        super(data);
        addItemType(StraveXqEy.CONTENTSTITLE, R.layout.item_trave_title);
        addItemType(StraveXqEy.IMG, R.layout.item_trave_img);
        addItemType(StraveXqEy.TRAVETITLE, R.layout.item_trave_title);
        addItemType(StraveXqEy.CONTENTS, R.layout.item_trave_title);
        addItemType(StraveXqEy.WEBCONTENT, R.layout.item_trave_web);
    }

    @Override
    protected void convert(BaseViewHolder holder, StraveXqEy item) {
        switch (holder.getItemViewType()) {
            case StraveXqEy.CONTENTSTITLE:
                holder.setText(R.id.tv_title_details, item
                        .getContent());
                break;
            case StraveXqEy.IMG:
                ImageView img = (ImageView)holder.getView(R.id.iv_details);
                GlideApp.with(mContext).load(item.getContent())
                        .placeholder(R.mipmap.my_avatar_default)
                        .error(R.mipmap.my_avatar_default)
                        .into(img);
                break;
            case StraveXqEy.TRAVETITLE:
                holder.setText(R.id.tv_title_details, item
                        .getContent());
                holder.setVisible(R.id.tv_title_details,false);
                break;
            case StraveXqEy.CONTENTS:
                holder.setText(R.id.tv_title_details, item
                        .getContent());
                break;
            case StraveXqEy.WEBCONTENT:
                WebView web = (WebView) holder.getView(R.id.item_web);
                web.loadData(ComUtils.getNewContent(item.getContent()), ComUtils.WEBVIEW_TYPE, null);
                break;
        }
    }
}
