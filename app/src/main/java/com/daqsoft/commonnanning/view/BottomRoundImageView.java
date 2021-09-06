package com.daqsoft.commonnanning.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * 功能
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-5-18.17:41
 * @since JDK 1.8
 */

public class BottomRoundImageView extends AppCompatImageView {
    private static final String TAG = BottomRoundImageView.class.getSimpleName();
    protected Context mContext;
    private static final Xfermode sXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    private Bitmap mMaskBitmap;
    private Paint mPaint;
    private WeakReference<Bitmap> mWeakBitmap;
    /**
     * 圆角半径
     */
    private int radius;

    private int mWidth;
    private int mHeight;
    private Path path;
    private RectF rectF;

    public BottomRoundImageView(Context context) {
        super(context);
        sharedConstructor(context);
    }

    public BottomRoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        sharedConstructor(context);
    }

    public BottomRoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        sharedConstructor(context);
    }

    private void sharedConstructor(Context context) {
        mContext = context;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        //        radius = SizeUtils.dp2px(6);
    }

    @Override
    public void invalidate() {
        mWeakBitmap = null;
        if (mMaskBitmap != null) {
            mMaskBitmap.recycle();
        }
        super.invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        if (!isInEditMode()) {
            int i = canvas.saveLayer(0.0f, 0.0f, mWidth, mHeight, null, Canvas.ALL_SAVE_FLAG);
            try {
                Bitmap bitmap = mWeakBitmap != null ? mWeakBitmap.get() : null;
                if (bitmap == null || bitmap.isRecycled()) {
                    Drawable drawable = getDrawable();
                    if (drawable != null) {
                        bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
                        Canvas bitmapCanvas = new Canvas(bitmap);
                        drawable.setBounds(0, 0, mWidth, mHeight);
                        drawable.draw(bitmapCanvas);
                        if (mMaskBitmap == null || mMaskBitmap.isRecycled()) {
                            mMaskBitmap = getBitmap();
                        }
                        mPaint.reset();
                        mPaint.setFilterBitmap(false);
                        mPaint.setXfermode(sXfermode);
                        bitmapCanvas.drawBitmap(mMaskBitmap, 0.0f, 0.0f, mPaint);

                        mWeakBitmap = new WeakReference<>(bitmap);
                    }
                }


                if (bitmap != null) {
                    mPaint.setXfermode(null);
                    canvas.drawBitmap(bitmap, 0.0f, 0.0f, mPaint);
                    return;
                }
            } catch (Exception e) {
                System.gc();

                Log.e(TAG, String.format("Failed to draw, Id :: %s. Error occurred :: %s", getId
                        (), e.toString()));
            } finally {
                canvas.restoreToCount(i);
            }
        } else {
            super.onDraw(canvas);
        }
    }

    public Bitmap getBitmap(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        path = new Path();
        path.moveTo(0, 0);
        path.lineTo(0, height * 2 / 3);
        path.quadTo(width / 2, height, width, height * 2 / 3);
        path.lineTo(width, 0);
        path.close();

        rectF = new RectF();
        rectF.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight()
                - getPaddingBottom());
        // 这里要加上半径的高度，不然最后底部两个角会截取不干净
        rectF.set(0, 0, getWidth(), getHeight() + radius);
        path.addRoundRect(rectF, radius, radius, Path.Direction.CW);
        path.setFillType(Path.FillType.INVERSE_EVEN_ODD);
        canvas.drawPath(path, paint);
        return bitmap;
    }


    public Bitmap getBitmap() {
        return getBitmap(mWidth, mHeight);
    }

}
