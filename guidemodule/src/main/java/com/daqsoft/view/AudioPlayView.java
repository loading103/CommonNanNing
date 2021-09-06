package com.daqsoft.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.daqsoft.guide.R;

/**
 * 音频播放时候的特效
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-3-14
 * @since JDK 1.8
 */

public class AudioPlayView extends View {
    /**
     * 定义一共多少个
     */

    private int count = 5;
    /**
     * 定义每个的宽度
     */
    private int tagWidth = 5;
    /**
     * 定义最大的高度
     */
    private int tagMaxHeight = 50;

    /**
     * 定义每一个的颜色
     */
    private int tagColor = Color.WHITE;

    /**
     * 定义每个之间的间距
     */
    private int space = 5;
    /**
     * 定义最小的高度
     */
    private int tagMinHeight = 5;

    /**
     * 定义一个画笔
     */
    private Paint paint;
    /**
     * view宽度
     */
    private int viewWidth;
    /**
     * view高度
     */
    private int viewHeight;
    /**
     * 绘制开始的位置
     */
    private int startX;
    /**
     * 绘制开始的位置
     */
    private int starty;
    /**
     * 间隔
     */
    private int spaceHeight;
    /**
     * 是否停止
     */
    private boolean isStop = false;
    /**
     * 线程是否开始
     */
    private boolean trheadStart = false;

    private int current = 0;
    private RectF f = new RectF();


    public AudioPlayView(Context context) {
        super(context);
        init();
    }

    public AudioPlayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AudioPlayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AudioPlayView);
        count = typedArray.getInteger(R.styleable.AudioPlayView_count, 5);
        tagWidth = typedArray.getInteger(R.styleable.AudioPlayView_tagWidth, 5);
        tagMaxHeight = typedArray.getInteger(R.styleable.AudioPlayView_tagMaxHeight, 50);
        tagColor = typedArray.getColor(R.styleable.AudioPlayView_tagColor, 0xFFFFFF);
        space = typedArray.getInteger(R.styleable.AudioPlayView_space, 5);
        tagMinHeight = typedArray.getInteger(R.styleable.AudioPlayView_tagMinHeight, 10);
        typedArray.recycle();
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        paint = new Paint();
        paint.setColor(tagColor);
        // 消除锯齿
        paint.setAntiAlias(true);
        // 设置实心
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(tagWidth);
        start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //        if (current < 5) {
        //            for (int j = 0; j <= current; j++) {
        //                f.left = startX + (j * space) + (j * tagWidth);
        //                f.right = f.left + tagWidth;
        //                //spaceHeight 是递减的值 tempHeight是每个因该减少的高度
        //                int tempHeight = spaceHeight * (current - (current - j));
        //                f.top = tempHeight / 2;
        //                f.bottom = tagMaxHeight - tempHeight / 2;
        //                drawRect(canvas, f);
        //            }
        //
        //            //绘制从小到大
        //            for (int k = current + 1; k < count; k++) {
        //                f.left = startX + (k * space) + (k * tagWidth);
        //                f.right = f.left + tagWidth;
        //                //spaceHeight 是递减的值 tempHeight是每个因该减少的高度
        //                int tempHeight = spaceHeight * (count - k);
        //                f.top = tempHeight / 2;
        //                f.bottom = tagMaxHeight - tempHeight / 2;
        //                drawRect(canvas, f);
        //            }
        //        } else {
        //            //绘制从小到大
        //            int current = this.current % 5;
        //            for (int j = 0; j <= current; j++) {
        //                f.left = startX + (j * space) + (j * tagWidth);
        //                f.right = f.left + tagWidth;
        //                //spaceHeight 是递减的值 tempHeight是每个因该减少的高度
        //                int tempHeight = spaceHeight * (current - j);
        //                f.top = tempHeight / 2;
        //                f.bottom = tagMaxHeight - tempHeight / 2;
        //                drawRect(canvas, f);
        //            }
        //            //绘制从大到小
        //            for (int k = current + 1; k < count; k++) {
        //                f.left = startX + (k * space) + (k * tagWidth);
        //                f.right = f.left + tagWidth;
        //                //spaceHeight 是递减的值 tempHeight是每个因该减少的高度
        //                int tempHeight = spaceHeight * (k - current);
        //                f.top = tempHeight / 2;
        //                f.bottom = tagMaxHeight - tempHeight / 2;
        //                drawRect(canvas, f);
        //            }
        //        }
        //绘制从小到大
        int current = this.current % 5;
        for (int j = 0; j <= current; j++) {
            f.left = startX + (j * space) + (j * tagWidth);
            f.right = f.left + tagWidth;
            //spaceHeight 是递减的值 tempHeight是每个因该减少的高度
            int tempHeight = spaceHeight * (current - j);
            f.top = tempHeight / 2;
            f.bottom = tagMaxHeight - tempHeight / 2;
            drawRect(canvas, f);
        }
        //绘制从大到小
        for (int k = current + 1; k < count; k++) {
            f.left = startX + (k * space) + (k * tagWidth);
            f.right = f.left + tagWidth;
            //spaceHeight 是递减的值 tempHeight是每个因该减少的高度
            int tempHeight = spaceHeight * (k - current);
            f.top = tempHeight / 2;
            f.bottom = tagMaxHeight - tempHeight / 2;
            drawRect(canvas, f);
        }

    }

    /**
     * 绘制
     *
     * @param canvas
     * @param f
     */
    private void drawRect(Canvas canvas, RectF f) {
        canvas.drawRect(f, paint);
    }

    /**
     * 开始
     */
    public void start() {
        isStop = false;
        if (trheadStart) {
            return;
        }
        new Thread() {
            public void run() {
                trheadStart = true;
                while (!isStop) {
                    postInvalidate();
                    try {
                        current++;
                        if (current >= 5) {
                            current = 0;
                        }
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    /**
     * 停止
     */
    public void stop() {
        current = 0;
        isStop = true;
        trheadStart = false;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        viewWidth = getPaddingLeft() + getPaddingRight() + tagWidth * count + space * (count - 1);
        viewHeight = tagMaxHeight + getPaddingTop() + getPaddingBottom();
        startX = getPaddingLeft();
        starty = getPaddingTop();
        spaceHeight = (tagMaxHeight - tagMinHeight) / 5;
        setMeasuredDimension(viewWidth, viewHeight);
    }
}
