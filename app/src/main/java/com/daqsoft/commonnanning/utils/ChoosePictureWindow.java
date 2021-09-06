package com.daqsoft.commonnanning.utils;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.daqsoft.commonnanning.R;
import com.example.tomasyb.baselib.util.ObjectUtils;

/**
 * 选择照片的弹框
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018/10/25 0025
 * @since JDK 1.8
 */
public class ChoosePictureWindow {

    /**
     * 弹框popupWindow
     */
    static PopupWindow window = null;

    /**
     * 弹出相册和拍照的选择popupWindow
     */
    public static void takePhoto(final Activity activity, View v, final DataCallBack dataCallBack) {
        if (window == null) {
            window = new PopupWindow(activity);
        }
        View view = LayoutInflater.from(activity).inflate(R.layout.include_take_photo, null);
        LinearLayout llTakePhoto = (LinearLayout) view.findViewById(R.id.take_photo_ll);
        llTakePhoto.getBackground().setAlpha(100);
        window.setContentView(view);
        window.setTouchable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        window.setBackgroundDrawable(dw);
        window.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        final WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        // 当弹出Popupwindow时，背景变半透明
        params.alpha = 0.7f;
        params.horizontalMargin = 20f;
        activity.getWindow().setAttributes(params);
        // 设置Popupwindow显示位置（从底部弹出）
        window.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_VERTICAL, 0, 60);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                activity.getWindow().setAttributes(params);
            }
        });
        // 拍照
        TextView takePhoto = (TextView) view.findViewById(R.id.take_photo_take);
        // 相册
        TextView choosePhoto = (TextView) view.findViewById(R.id.take_photo_choose);
        TextView cancel = (TextView) view.findViewById(R.id.take_photo_cancel);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ObjectUtils.isNotEmpty(dataCallBack)) {
                    dataCallBack.dataCallBack(2);
                }
                window.dismiss();
            }
        });
        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 调用相册
                if (ObjectUtils.isNotEmpty(dataCallBack)) {
                    dataCallBack.dataCallBack(1);
                }
                window.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window.dismiss();
            }
        });
    }


    /**
     * 数据回调
     */
    public interface DataCallBack {
        /**
         * 回调
         *
         * @param type
         */
        void dataCallBack(int type);
    }

}
