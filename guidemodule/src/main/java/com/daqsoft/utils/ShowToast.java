package com.daqsoft.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 弹出提示语
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-3-14
 * @since JDK 1.8
 */

public class ShowToast {

    private static Toast toast = null;

    /**
     * 弹出提示语
     * 字符串
     */
    public static void showText(Context context, String msg) {
        if (null == toast) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
