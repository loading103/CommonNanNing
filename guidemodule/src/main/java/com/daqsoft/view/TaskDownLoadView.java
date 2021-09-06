package com.daqsoft.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.daqsoft.guide.R;
import com.daqsoft.http.DefaultProgressCallBack;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * 圆形的下载进度条
 * @author MouJunFeng
 * @time 2018-3-14
 * @since JDK 1.8
 * @version 1.0.0
 */

public class TaskDownLoadView extends View {
    /**
     * 设置开始状态的时候 圆圈的颜色
     */
    private int startCricleColor;
    /**
     * 设置进度源泉的颜色
     */
    private int progressCricleColor;

    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 当前进度
     */
    private int progress;
    /**
     * 圆环中心点
     */
    private int centre;
    /**
     * 当前的状态图片
     */
    private Bitmap bitmap;
    /**
     * 开始状态图片
     */
    private int srcStart;
    /**
     * 暂停状态图片
     */
    private int srcStorp;
    /**
     * 0开始状态；1暂停；2完成
     */
    private int state;
    /**
     * 设置画笔圆圈的宽度
     */
    private int mCircleWidth;
    /**
     * 设置圆圈到状态图片的间距
     */
    private int padding_circle;
    /**
     * 图片宽度
     */
    private int bitmapWidth = 0;
    /**
     * 控件总大小
     */
    private int totalWidth = 0;
    /**
     * 回调接口
     */
    private StateProgressListner listner;
    /**
     * 请求取消
     */
    private Callback.Cancelable cancelable;
    /**
     * 是否正在下载
     */
    private boolean isDowning = false;


    public TaskDownLoadView(Context context) {
        super(context);
    }

    public TaskDownLoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.taskDownLoadView);
        startCricleColor = typedArray.getColor(R.styleable.taskDownLoadView_startCricleColor, 0xEEEEEE);
        progressCricleColor = typedArray.getColor(R.styleable.taskDownLoadView_progressCricleColor, 0x00D0AE);
        srcStart = typedArray.getResourceId(R.styleable.taskDownLoadView_srcStart, 0);
        srcStorp = typedArray.getResourceId(R.styleable.taskDownLoadView_srcStorp, 0);
        mCircleWidth = typedArray.getInteger(R.styleable.taskDownLoadView_circleWidth, 0);
        padding_circle = typedArray.getInteger(R.styleable.taskDownLoadView_padding_circle, 0);
        typedArray.recycle();
        bitmap = BitmapFactory.decodeResource(getResources(), srcStart);
        bitmapWidth = bitmap.getWidth();
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
        progress = 0;
        state = 0;
    }

    public TaskDownLoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置圆圈的底色
     *
     * @param color
     */
    public void setFirstColor(int color) {
        this.startCricleColor = color;
    }

    /**
     * 设置进度颜色
     *
     * @param progressColor
     */
    public void setProgressColor(int progressColor) {
        this.progressCricleColor = progressColor;
    }

    /**
     * 设置开始状态图片
     *
     * @param bitmap
     */
    public void setStartBitmap(int bitmap) {
        this.srcStart = bitmap;
    }

    /**
     * 设置暂停状态图片
     *
     * @param bitmap
     */
    public void setPauseBitmap(int bitmap) {
        this.srcStorp = bitmap;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCirle(canvas);
        drawBitmap(canvas);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_UP) {
////            判断当前状态
//            if (state == 0) {
//                onPause();
//            } else if (state == 1) {
//                onStart();
//            }
//        }
//        return true;
//    }

    /**
     * 圆弧进度
     *
     * @param canvas
     */
    private void drawCirle(Canvas canvas) {
        centre = totalWidth / 2; // 获取圆心的x , y坐标
        int radius = totalWidth / 2;// 半径
        mPaint.setStrokeWidth(mCircleWidth); // 设置圆环的宽度
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
        // 用于定义的圆弧的形状和大小的界限
        RectF oval = new RectF(0 + mCircleWidth / 2, 0 + mCircleWidth / 2, totalWidth - mCircleWidth / 2, totalWidth - mCircleWidth / 2);
        mPaint.setColor(startCricleColor); // 设置圆环的颜色
        canvas.drawCircle(centre, centre, radius - mCircleWidth / 2, mPaint); // 画出圆环
        mPaint.setColor(progressCricleColor); // 设置圆环的颜色
        mPaint.setStrokeCap(Paint.Cap.ROUND);//两边带圆弧
        canvas.drawArc(oval, -90, progress, false, mPaint); // 根据进度画圆弧
    }


    /**
     * 画出当前的状态图标
     *
     * @param canvas
     */
    private void drawBitmap(Canvas canvas) {
        if (bitmap != null) bitmap.recycle();
        bitmap = BitmapFactory.decodeResource(getResources(), !isDowning ? srcStart : srcStorp);
        if (bitmap != null) {
            int with = bitmap.getWidth();
            int height = bitmap.getHeight();
            canvas.drawBitmap(bitmap, centre - with / 2, centre - height / 2, mPaint);
        }
    }

    /**
     * 暂停
     */
    public void onPause() {
        isDowning = false;
        state = 1;
        if (cancelable != null) {
            cancelable.cancel();
        }
        if (listner != null) {
            listner.onPause();
        }
        invalidate();
    }

    /**
     * 开始
     */
    public void onStart(String url, String path) {
        isDowning = true;
        if (TextUtils.isEmpty(url)) {
            return;
        }
        state = 0;
        download(url, path);
        invalidate();
        if (listner != null) {
            listner.onstart();
        }
    }

    /**
     * 返回是否正在下载
     */
    public boolean isDownLoad() {
        return isDowning;
    }

    /**
     * 更新进度条
     *
     * @param progress
     */
    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    /**
     * 文件下载
     *
     * @param url  下载地址
     * @param path 保存地址
     */
    private void download(String url, String path) {
        RequestParams requestParams = new RequestParams(url);
        requestParams.setAutoResume(true);
        if (!TextUtils.isEmpty(path)) {
            requestParams.setSaveFilePath(path);
        }
        cancelable = x.http().get(requestParams, new DefaultProgressCallBack<File>() {
            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
//                Log.i("获得文件大小--->" + total + "\t当前下载进度--->" + current);
                //计算出百分比
                float percent = (float) current / total;
                setProgress((int) (percent * 360));
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (listner != null) {
                    listner.onError();
                }
            }

            @Override
            public void onSuccess(File result) {
                progress = 0;
                state = 0;
                isDowning = false;
                if (listner != null) {
                    listner.onfinish();
                }
                invalidate();
            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        totalWidth = bitmapWidth + mCircleWidth * 2 + padding_circle * 2;
        setMeasuredDimension(totalWidth, totalWidth);
    }

    public void setListner(StateProgressListner listner) {
        this.listner = listner;
    }

    /**
     * 销毁处理
     */
    public void onDestory() {
        if (bitmap != null) bitmap.recycle();
    }

    /**
     * 自定义监听器
     */
    public interface StateProgressListner {
        /**
         * 开始
         */
        void onstart();

        /**
         * 暂停
         */
        void onPause();

        /**
         * 结束
         */
        void onfinish();
        /**
         * 出错
         */
        void onError();
    }
}
