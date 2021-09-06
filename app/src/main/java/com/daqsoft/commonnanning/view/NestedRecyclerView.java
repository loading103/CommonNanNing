package com.daqsoft.commonnanning.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.library.flowlayout.FlowLayoutManager;

/**
 * 主要是为了适配rv嵌套rv的案例，如果大家有什么好的方案请提出来，谢谢了!!!
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-8-1.10:36
 * @since JDK 1.8
 */
public class NestedRecyclerView extends RecyclerView {
    public NestedRecyclerView(Context context) {
        super(context);
    }

    public NestedRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        FlowLayoutManager layoutManager = (FlowLayoutManager) getLayoutManager();
        int widthMode = MeasureSpec.getMode(widthSpec);
        int measureWidth = MeasureSpec.getSize(widthSpec);
        int heightMode = MeasureSpec.getMode(heightSpec);
        int measureHeight = MeasureSpec.getSize(heightSpec);
        int width, height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = measureWidth;
        } else {
            // 以实际屏宽为标准
            width = getContext().getResources().getDisplayMetrics().widthPixels;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = measureHeight;
        } else {
            height = layoutManager.getTotalHeight() + getPaddingTop() + getPaddingBottom();
        }
        setMeasuredDimension(width, height);

    }
}
