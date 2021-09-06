package com.daqsoft.commonnanning.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.Button;

import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.example.tomasyb.baselib.base.net.common.DefaultObserver;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.ToastUtils;

import java.lang.ref.WeakReference;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@SuppressLint("AppCompatCustomView")
public class AuthenButton extends Button {
    /**
     * 是否开始的标记
     */
    private boolean isStart = false;
    /**
     * 倒计时时候 背景颜色
     */
    private int startBgColor;
    /**
     * 正常模式下 背景颜色
     */
    private int nomaloBgColor;
    /**
     * 计时总数
     */
    private int total = 0;
    private Object obj = new Object();
    private boolean isSuccess = false;
    private String phone;
    private MyHandler handler = new MyHandler(this);

    public class MyHandler extends Handler {
        private WeakReference<AuthenButton> obj;

        public MyHandler(AuthenButton obj) {
            this.obj = new WeakReference<AuthenButton>(obj);
        }

        @Override
        public void handleMessage(Message msg) {
            if (obj.get() != null) {
                obj.get().setText("(" + total + ")秒重新获取");
                if (total <= 0) {
                    obj.get().setEnabled(true);
                    obj.get().setClickable(true);
                    obj.get().setBackgroundColor(obj.get().nomaloBgColor);
                    obj.get().isStart = false;
                    obj.get().isSuccess = false;
                    obj.get().total = 0;
                    obj.get().setText("获取验证码");
                }
            }

        }
    }

    public AuthenButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AuthenButton);
        startBgColor = array.getColor(R.styleable.AuthenButton_startBgColor, 0xffffff);
        nomaloBgColor = array.getColor(R.styleable.AuthenButton_startBgColor, 0xffffff);
        array.recycle();
    }

    /**
     * 为true的时候代码数据请求成功
     *
     * @return
     */
    public boolean isSuccess() {
        return isSuccess;
    }

    public void start(String phone, String type) {
        if (this.isStart) {
            return;
        }
        this.setText("获取中...");
        this.isStart = true;
        // 获取验证过程中，按钮不可触发onClickListener事件
        String mobile = phone;
        RetrofitHelper.getApiService()
                .getSendMsg(phone, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        if (ObjectUtils.isNotEmpty(response)) {
                            if (response.getCode() == 0) {
                                AuthenButton.this.setBackgroundColor(startBgColor);
                                isSuccess = true;
                                start(60);
                            } else {
                                handler.sendEmptyMessage(0);
                            }
                        } else {
                            ToastUtils.showShortCenter(response.getMessage());
                            handler.sendEmptyMessage(0);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        handler.sendEmptyMessage(0);
                    }
                });
    }

    /**
     * 开始
     */
    private void start(int second) {
        this.setEnabled(false);
        this.setClickable(false);
        this.total = second;
        this.setText("(" + total + ")秒重新获取");
        this.isStart = true;
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public synchronized void stop() {
        total = 0;
        isSuccess = false;
    }

    /**
     * 计时器
     */
    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
            synchronized (obj) {
                while (total > 0) {
                    total--;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(total);
                }
            }
        }
    };
}
