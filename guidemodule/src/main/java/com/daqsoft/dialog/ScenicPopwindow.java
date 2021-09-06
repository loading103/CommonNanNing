package com.daqsoft.dialog;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.daqsoft.guide.R;


/**
 * 景区的market显示的popwindow
 * @author MouJunFeng
 * @time 2018-3-14
 * @since JDK 1.8
 * @version 1.0.0
 */

public class ScenicPopwindow extends PopupWindow {

    public ScenicPopwindow(Context context) {
        super(context);
        init(context);
    }

    /**
     * 初始化
     * @param context
     */
    public void init(Context context){
        View v = LayoutInflater.from(context).inflate(R.layout.guide_dialog_scenic,null);
        this.setContentView(v);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高;
        this.setFocusable(true);// 设置弹出窗口可
    }
}
