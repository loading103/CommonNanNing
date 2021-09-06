package com.daqsoft.commonnanning.utils;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daqsoft.commonnanning.R;
import com.example.tomasyb.baselib.util.ObjectUtils;

import io.agora.yview.dialog.BaseDialog;

/**
 * 各种提示操作弹框
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/3/22 0022
 * @since JDK 1.8
 */
public class ShowDialog {
    /**
     * 弹出带 确定和取消的提示框
     *
     * @param title    提示标题，可以为空（默认 温馨提示）
     * @param text     提示内容，不能为空
     * @param function 回调确定
     */
    public static void showDialog(Context context, String title, String text, final
    CompleteFuncData function) {
        final BaseDialog dialog = new BaseDialog(context);
        dialog.setContentView(R.layout.z_dialog_tip);
        dialog.setCancelable(true);
        dialog.show();
        TextView tvTitle = (TextView) dialog.findViewById(R.id.z_tip_tv_dialog_title);
        tvTitle.setText("温馨提示");
        if (ObjectUtils.isNotEmpty(title)) {
            tvTitle.setText(title);
        }
        TextView tvDescription = (TextView) dialog.findViewById(R.id.z_tip_tv_dialog_description);
        Button btnCancel = (Button) dialog.findViewById(R.id.z_tip_btn_dialog_cancel);
        // dialog
        Button btnSure = (Button) dialog.findViewById(R.id.z_tip_btn_dialog_sure);
        // executive
        tvDescription.setText(text);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 点击取消什么事也不做，只是关闭窗口
                dialog.dismiss();
            }
        });
        btnSure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                function.success("1");
                dialog.dismiss();
            }
        });
    }


    /**
     * 弹出机器人录音的的提示框
     */
    public static BaseDialog showVoiceDialog(Context context) {
        final BaseDialog dialog = new BaseDialog(context);
        dialog.setContentView(R.layout.dialog_voice);
        LinearLayout ll = (LinearLayout) dialog.findViewById(R.id.ll_dialog_voice);
        ll.getBackground().setAlpha(125);
        dialog.setCancelable(true);
        return dialog;
    }


    /**
     * 操作成功后进行回调
     */
    public interface CompleteFuncData {
        /**
         * 请求完成后回调
         */
        void success(String result);

    }
}
