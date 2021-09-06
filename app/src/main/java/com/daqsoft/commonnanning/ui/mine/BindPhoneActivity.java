package com.daqsoft.commonnanning.ui.mine;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.URLConstant;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.utils.Utils;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.net.common.DefaultObserver;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.RegexUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.LoadingDialog;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 绑定手机号Activity
 *
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2018-8-27
 * @since JDK 1.8.0_171
 */
@Route(path = Constant.ACTIVITY_BIND_PHONE)
public class BindPhoneActivity extends BaseActivity {
    @BindView(R.id.headView)
    HeadView headView;
    @BindView(R.id.mine_register_phone)
    EditText mineRegisterPhone;
    @BindView(R.id.mine_register_phone_ll)
    LinearLayout mineRegisterPhoneLl;
    @BindView(R.id.mine_register_test)
    EditText mineRegisterTest;
    @BindView(R.id.mine_register_send_message)
    TextView mineRegisterSendMessage;
    @BindView(R.id.mine_register_test_ll)
    LinearLayout mineRegisterTestLl;
    @BindView(R.id.mine_register_next)
    TextView mineRegisterNext;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @Autowired(name = "pageType")
    int pageType;

    /**
     * 是否发送
     */
    private boolean isSend = true;
    /**
     * 电话号码
     */
    private String phone = "";

    /**
     * 短信的类型
     */
    private String sendType = "";
    /**
     * 0 注册，1 找回密码,2绑定手机号
     */
    private int type = 2;


    @Override
    public int getLayoutId() {
        return R.layout.activity_binding_phone2;
    }

    public void initView() {
        ARouter.getInstance().inject(this);
        if (pageType==1){
            mineRegisterTestLl.setVisibility(View.GONE);
        }else {
            mineRegisterTestLl.setVisibility(View.VISIBLE);
        }
        headView.setTitle("绑定手机号");
        sendType = type == 0 ? URLConstant.REGISTER_TYPE : type == 1 ?
                URLConstant.FIND_PASSWORD_TYPE : URLConstant.BIND_ACCOUNT_TYPE;
        mineRegisterPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    ivClear.setVisibility(View.VISIBLE);
                } else {
                    ivClear.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    @OnClick(R.id.iv_clear)
    public void clear() {
        mineRegisterPhone.setText("");
    }

    @OnClick(R.id.mine_register_next)
    public void ok() {
        updateUserInfo();
    }

    /**
     * 发送验证码
     */
    @OnClick(R.id.mine_register_send_message)
    public void message() {
        // 发送验证码
        if (("获取验证码".equals(mineRegisterSendMessage.getText().toString().trim())
                || "重新发送".equals(mineRegisterSendMessage.getText().toString().trim()))
                && isSend) {
            // 电话
            phone = mineRegisterPhone.getText().toString().trim();
            if (!checkPhone(phone)) {
                return;
            }
            LoadingDialog.showDialogForLoading(this, "正在获取验证码", true);
            RetrofitHelper.getApiService()
                    .getSendMsg(phone, sendType)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            addDisposable(disposable);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DefaultObserver() {
                        @Override
                        public void onSuccess(BaseResponse response) {
                            if (ObjectUtils.isNotEmpty(response)) {
                                if (response.getCode() == 0) {
                                    ToastUtils.showShortCenter("验证码发送成功,请查看短信");
                                    time = 120;
                                    handler.sendEmptyMessage(1);
                                } else {
                                    ToastUtils.showShortCenter(response.getMessage());
                                }
                            } else {
                                ToastUtils.showShortCenter("验证码发送失败");
                            }
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                            isSend = true;
                            LoadingDialog.cancelDialogForLoading();
                        }
                    });

        }
    }

    /**
     * 验证手机号码是否为空以及是否正确
     *
     * @param phone
     */
    public boolean checkPhone(String phone) {
        boolean isPhone = true;
        if (!ObjectUtils.isNotEmpty(phone)) {
            ToastUtils.showShortCenter("请输入手机号码！");
            return false;
        }
        if (!Utils.isMobile(phone)) {
            ToastUtils.showShortCenter("请输入正确的手机号码！");
            return false;
        }

        return isPhone;
    }

    /**
     * 发送验证码后的倒计时
     */
    private int time = 120;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (time == 0) {
                    handler.removeMessages(1);
                    mineRegisterSendMessage.setText("重新发送");
                    isSend = true;
                } else {
                    mineRegisterSendMessage.setText("    " + time + "s" + "    ");
                    time--;
                    handler.sendEmptyMessageDelayed(1, 1000);
                }
            }
        }
    };

    /**
     * 更新数据
     */
    public void updateUserInfo() {
        if (RegexUtils.isMobileExact(mineRegisterPhone.getText().toString().trim())){
            HashMap<String, String> map = new HashMap<>();
            if (ObjectUtils.isNotEmpty(mineRegisterPhone.getText().toString().trim())) {
                map.put("phone", mineRegisterPhone.getText().toString().trim());
            }
            LoadingDialog.showDialogForLoading(this);
            RetrofitHelper.getApiService().updateUserInfo(map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            addDisposable(disposable);
                        }
                    })
                    .subscribe(new DefaultObserver() {
                        @Override
                        public void onSuccess(BaseResponse response) {
                            if (ObjectUtils.isNotEmpty(response)) {
                                if (response.getCode() == 0) {
                                    if (pageType==1){
                                        Intent intent = new Intent();
                                        intent.putExtra("phone", mineRegisterPhone.getText().toString().trim());
                                        setResult(4, intent);
                                        finish();
                                    }
                                    ToastUtils.showShortCenter("绑定成功");
                                } else {
                                    ToastUtils.showShortCenter(response.getMessage());
                                }
                            } else {
                                ToastUtils.showShortCenter("操作异常");
                            }
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                            LoadingDialog.cancelDialogForLoading();
                        }
                    });
        }else {
            ToastUtils.showShort("请输入正确的手机号!");
        }
    }
}
