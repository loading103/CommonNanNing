package com.daqsoft.commonnanning.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.ParamsCommon;
import com.daqsoft.commonnanning.ui.destination.RegionEntity;
import com.daqsoft.commonnanning.ui.mine.interact.entity.SceneryType;
import com.daqsoft.commonnanning.ui.service.guide.bean.GuideTypeEntity;
import com.example.tomasyb.baselib.util.ObjectUtils;

import java.util.List;

import io.agora.yview.dialog.BaseDialog;

/**
 * 公共弹框
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/4/12 0012
 * @since JDK 1.8
 */
public class CommonWindow {
    /**
     * 名称
     */
    private static String name = "";
    /**
     * key
     */
    private static String key = "";

    /**
     * 公共弹框
     *
     * @param view     控件
     * @param datas    数据源
     * @param select   选中数据
     * @param type     类型
     * @param callBack
     */
    public static void CommomPopupWindow(Activity activity, View view, List<?> datas, String
            select, int type,
                                         final WindowCallBack callBack) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        BaseDialog dialog = new BaseDialog(activity);
        dialog.setContentView(R.layout.filter_common_window);
        dialog.layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        dialog.gravity(Gravity.TOP);
        dialog.offset(0, (int) (view.getY()) + view.getHeight());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (ObjectUtils.isNotEmpty(callBack)) {
                    callBack.windowDismiss();
                }
            }
        });
        dialog.show();
        RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.rg_filter_window);
        if (ObjectUtils.isNotEmpty(datas) && ObjectUtils.isNotEmpty(radioGroup)) {
            radioGroup.removeAllViews();
            int size = datas.size();
            for (int i = 0; i < size; i++) {
                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(LinearLayout
                        .LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.rightMargin = 15;
                RadioButton rb = (RadioButton) inflater.inflate(R.layout.item_filter, null);
                if (datas.get(i) instanceof RegionEntity) {
                    RegionEntity region = (RegionEntity) datas.get(i);
                    name = region.getName();
                    key = region.getRegion();
                } else if (datas.get(i) instanceof SceneryType) {
                    SceneryType sceneryType = (SceneryType) datas.get(i);
                    name = sceneryType.getName();
                    key = sceneryType.getSkey();
                }  else if (datas.get(i) instanceof GuideTypeEntity) {
                    GuideTypeEntity guideTypeEntity = (GuideTypeEntity) datas.get(i);
                    String s = guideTypeEntity.toString();
                    name = guideTypeEntity.getName();
                    key = guideTypeEntity.getCode();
                } else {
                    return;
                }

                if (ObjectUtils.isNotEmpty(name)) {
                    rb.setText(name);
                    rb.setTag(key);
                }
                if (type == 1) {
                    rb.setGravity(Gravity.LEFT);
                } else if (type == 2) {
                    rb.setGravity(Gravity.CENTER);
                }
                rb.setChecked(rb.getTag().toString().equals(select));
                rb.setId(ParamsCommon.ID + i);
                radioGroup.addView(rb, params);
            }
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkId) {
                    RadioButton rb = (RadioButton) group.findViewById(checkId);
                    if (ObjectUtils.isNotEmpty(callBack)) {
                        callBack.windowCallBack(rb.getText().toString().trim(), rb.getTag() + "");
                        dialog.dismiss();
                    }
                }
            });

        }
    }

    /**
     * 公共弹框的回调接口
     */
    public interface WindowCallBack {
        /**
         * 公共弹框的弹出回调方法
         *
         * @param value1 name值
         * @param value2 key值
         */
        void windowCallBack(String value1, String value2);

        /**
         * 弹框消失进行回调
         */
        void windowDismiss();
    }

}
