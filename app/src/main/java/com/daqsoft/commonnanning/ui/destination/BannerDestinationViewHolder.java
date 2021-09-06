package com.daqsoft.commonnanning.ui.destination;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.example.tomasyb.baselib.base.glide.GlideApp;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import org.xutils.common.util.LogUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 目的地banner页面的ViewHolder适配器
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018.05.07
 * @since JDK 1.8
 */

public class BannerDestinationViewHolder implements MZViewHolder<DestinationInfoEntity> {
    private ImageView img;
    private TextView name, content, num;
    private LinearLayout llItem, llNum;

    @Override
    public View createView(Context context) {
        // 页面布局
        View view = LayoutInflater.from(context).inflate(R.layout.item_destination_banner, null);
        img = (ImageView) view.findViewById(R.id.iv_item_destination_banner);
        name = (TextView) view.findViewById(R.id.tv_item_destination_name);
        content = (TextView) view.findViewById(R.id.tv_item_destination_summary);
        num = (TextView) view.findViewById(R.id.tv_item_destination_num);
        llNum = (LinearLayout) view.findViewById(R.id.ll_item_destination_num);
        llItem = (LinearLayout) view.findViewById(R.id.ll_item_destination_banner);
        return view;
    }

    @Override
    public void onBind(final Context context, int i, final DestinationInfoEntity destination) {

        // 数据绑定
        GlideApp.with(context).load(destination.getCoverTwoToThree())
                .placeholder(R.mipmap.common_img_fail_h158)
                .error(R.mipmap.common_img_fail_h158)
                .into(img);
        name.setText(destination.getRegionName());

        String content1 = deleteHtmlImg(destination.getIntroduction());
        if (ObjectUtils.isNotEmpty(content1)) {
            content.setText(content1);
            content.setVisibility(View.VISIBLE);
        } else {
            content.setVisibility(View.GONE);
        }
        num.setText(destination.getViewCount() + "");
        llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.e("点击banner");
                ARouter.getInstance().build(Constant.ACTIVITY_DESTINATION_DETAILS)
                        .withString("name", destination.getRegionName())
                        .withString("region", destination.getRegion())
                        .navigation();
            }
        });

    }

    /**
     * 去除带html标签的内容中，去掉<img>图片标签
     *
     * @param content 内容
     * @return
     */
    public static String deleteHtmlImg(String content) {
        // 匹配img标签的正则表达式
        String regxpForImgTag = "<img\\s[^>]+/>";
        Pattern pattern = Pattern.compile(regxpForImgTag);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            String temp = matcher.group();
            content = content.replace(temp, "");
        }
        return Html.fromHtml(content).toString().trim();
    }
}
