package com.daqsoft.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daqsoft.guide.R;
import com.daqsoft.http.requestapi.CompleteFuncData;
import com.daqsoft.utils.ShowToast;

/**
 * 弹出提示框
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-3-14
 * @since JDK 1.8
 */
public class ShowDialog {

/*    public static void showRobotInput(Context context, InputInterface iterface) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(false);
        dialog.setCustomTitle(null);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context
        .LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) layoutInflater.inflate(R.layout.dialog_robot_input,
        null);
        dialog.setView(layout);
        dialog.show();
    }*/

    /**
     * 显示加载框，提示语为“请稍后”
     *
     * @param context
     * @return
     */
    public static AlertDialog.Builder getDialog(Context context) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(false);
        dialog.setTitle(context.getString(R.string.please_wait_text));
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);
        dialog.setView(layoutInflater.inflate(R.layout.guide_dialog_loading, null));
        return dialog;
    }

    /**
     * 显示加载框，可自定义提示语
     *
     * @param context 上下文
     * @param content 提示语
     * @return
     */

    public static AlertDialog.Builder getDialog(Context context, String content) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(true);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) layoutInflater.inflate(R.layout
                .guide_dialog_loading, null);
        ((TextView) layout.findViewById(R.id.dialog_content)).setText(content);
        dialog.setView(layout);
        return dialog;
    }


    /**
     * 显示加载框，可自定义提示语
     *
     * @param context 上下文
     * @param content 提示语
     * @return
     */
    public static android.support.v7.app.AlertDialog.Builder getV7Dialog(Context context, String
            content) {
        final android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app
                .AlertDialog.Builder(context);
        dialog.setCancelable(true);
        dialog.setTitle(context.getString(R.string.please_wait_text));
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) layoutInflater.inflate(R.layout
                .guide_dialog_loading, null);
        ((TextView) layout.findViewById(R.id.dialog_content)).setText(content);
        dialog.setView(layout);
        return dialog;
    }

  /*  public static void showWaitDialog(Context context) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(true);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context
        .LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) layoutInflater.inflate(R.layout.dialog_wait, null);
        dialog.setView(layout);
        dialog.show();
    }*/

    /**
     * 弹出带 确定和取消的提示框
     * @param title 提示标题，可以为空（默认 温馨提示）
     * @param content 提示内容，不能为空
     * @param function 回调确定
     */


    /**
     * 程序的提示框
     */
    public static void showDialog(Context context, String title, String text, final
    CompleteFuncData function) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.z_dialog_tip);
        window.setGravity(Gravity.CENTER);
        TextView tvTitle = (TextView) window.findViewById(R.id.z_tip_tv_dialog_title);
        tvTitle.setText("温馨提示");
        if (TextUtils.isEmpty(title)) tvTitle.setText(title);
        TextView tvDescription = (TextView) window.findViewById(R.id.z_tip_tv_dialog_description);
        Button btnCancel = (Button) window.findViewById(R.id.z_tip_btn_dialog_cancel);
        // dialog
        Button btnSure = (Button) window.findViewById(R.id.z_tip_btn_dialog_sure);
        tvDescription.setText(text);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                function.success("0");
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

    /* *//**
     * 隐藏Dialog
     *//*
    public static void hideLoadingDialog() {
        if (null != RequestData.alertDialog) {
            RequestData.alertDialog.dismiss();
        }
    }*/


    /**
     * 弹出带有输入框的
     *
     * @param activity
     * int length :内容的长度
     */
    private static AlertDialog dialog;

    public static void showInputDialog(final Activity activity, String title, String name, final
    int length, final CallBack callBack) {
        dialog = new AlertDialog.Builder(activity).create();
        dialog.setCancelable(false);
        dialog.setCustomTitle(null);
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) layoutInflater.inflate(R.layout
                .guide_include_input_dialog, null);
        dialog.setView(layout);
        dialog.show();
        TextView tvTitle = (TextView) layout.findViewById(R.id.input_dialog_title);
        tvTitle.setText(TextUtils.isEmpty(title) ? title : "修改");
        final EditText etContent = (EditText) layout.findViewById(R.id.input_dialog_content);
        etContent.setText(name);
        TextView tvCancel = (TextView) layout.findViewById(R.id.input_dialog_cancel);
        TextView tvSure = (TextView) layout.findViewById(R.id.input_dialog_sure);
        tvSure.setOnClickListener(new View.OnClickListener() {//确定
            @Override
            public void onClick(View view) {
                String content = etContent.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    if (length != 0) {
                        if (content.length() > length) {
                            ShowToast.showText(activity, "请输入小于或等于" + length + "位的值");
                            return;
                        }
                    }
                    if (callBack != null) {
                        callBack.callBack(content);
                    }
                    dialog.dismiss();
                } else {
                    ShowToast.showText(activity, "请输入相关信息");
                }
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {//取消
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public interface CallBack {
        void callBack(String name);
    }

}
