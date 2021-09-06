package com.example.tomasyb.baselib.widget;

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

import com.example.tomasyb.baselib.R;

import butterknife.ButterKnife;

/**
 * 头部视图
 *
 * @author MouJunFeng
 * @time 2018-3-21.
 */

public class HeadView extends FrameLayout implements View.OnClickListener {
    ImageView img_back;
    View view_status;
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

    private void init() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.view_head, null);
        img_back = v.findViewById(R.id.binding_phone_back);
        txt_title = v.findViewById(R.id.binding_phone_title);
        view_status = v.findViewById(R.id.view_status);
        txt_menu = v.findViewById(R.id.txt_menu);
        img_back.setOnClickListener(this);
        txt_menu.setOnClickListener(this);
        this.addView(v, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.MATCH_PARENT));
        ButterKnife.bind(this, v);
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
     * 设置图标
     *
     * @param icon
     */
    public void setMenuBackGround(int icon) {
        txt_menu.setBackground(getResources().getDrawable(icon));
    }

    /**
     * 设置左边按钮是否显示
     *
     * @param show
     */
    public void setBackHidden(boolean show) {

        img_back.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void setMemuTextColor(int color) {
        txt_menu.setTextColor(color);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.txt_menu) {
            if (menuListener != null) {
                menuListener.onClickMenu(v);
            }
        } else if (v.getId() == R.id.binding_phone_back) {
            if (backListener != null) {
                backListener.onBack(v);
                return;
            }
            ((Activity) getContext()).finish();
        }

    }

    public interface OnMenuListener {
        void onClickMenu(View v);
    }

    public interface OnBackListener {
        void onBack(View v);
    }
}
