package com.daqsoft.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.daqsoft.guide.R;

/**
 * 头部视图
 *
 * @author MouJunFeng
 * @time 2018-3-14
 * @since JDK 1.8
 * @version 1.0.0
 */

public class HeadView extends FrameLayout {
    ImageView img_back;
    AlwaysMarqueeTextView txt_title;
    TextView txt_menu;

    //点击右边的监听
    private OnMenuListener menuListener;
    private OnBackListener backListener;

    public void setBackListener(OnBackListener backListener) {
        this.backListener = backListener;
    }

    public void setMenuListener(OnMenuListener menuListener) {
        this.menuListener = menuListener;
    }

    public HeadView(@NonNull Context context) {
        super(context);
        init();
    }

    public HeadView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.guide_view_head, null);
        img_back = (ImageView) v.findViewById(R.id.binding_phone_back);
        img_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (backListener != null) {
                    backListener.onBack(v);
                    return;
                }
                ((Activity) getContext()).finish();
            }
        });
        txt_title = (AlwaysMarqueeTextView) v.findViewById(R.id.guide_view_binding_phone_title);
        txt_menu = (TextView) v.findViewById(R.id.txt_menu);
        txt_menu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuListener != null) {
                    menuListener.onClickMenu(v);
                }
            }
        });
        this.addView(v, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        txt_title.setText(title);
    }

    /**
     * 设置文字
     *
     * @param value
     */
    public void setMenuText(String value) {
        txt_menu.setText(value);
    }

    public void setMenuTextDrawableLeft(int r) {
//        txt_menu.setCompoundDrawablesRelativeWithIntrinsicBounds(r,0,0,0);
        txt_menu.setCompoundDrawablesWithIntrinsicBounds(r, 0, 0, 0);
    }

    /**
     * 设置右边按钮是否显示
     *
     * @param show
     */
    public void setMenuHidden(boolean show) {

        txt_menu.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置颜色
     * @param color
     */
    public void setMemuTextColor(int color) {
        txt_menu.setTextColor(color);
    }

    public interface OnMenuListener {
        void onClickMenu(View v);
    }

    public interface OnBackListener {
        void onBack(View v);
    }
}
