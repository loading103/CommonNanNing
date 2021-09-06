package com.daqsoft.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Administrator on 2016/7/6 0006.
 */
public class CenterDrawableEdittext extends EditText {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CenterDrawableEdittext(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CenterDrawableEdittext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CenterDrawableEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CenterDrawableEdittext(Context context) {
        super(context);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        CenterDrawableHelper.preDrawEdit(this, canvas);
        super.onDraw(canvas);
    }

}
