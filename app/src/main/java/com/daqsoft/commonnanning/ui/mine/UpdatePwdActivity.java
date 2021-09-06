package com.daqsoft.commonnanning.ui.mine;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.net.common.DefaultObserver;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 修改密码
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018.05.07
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_UPDATE_PWD)
public class UpdatePwdActivity extends BaseActivity {


    @BindView(R.id.headView)
    HeadView headView;
    @BindView(R.id.update_pwd_old)
    EditText updatePwdOld;
    @BindView(R.id.iv_clear1)
    ImageView ivClear1;
    @BindView(R.id.mine_login_pwd_close1)
    ImageView mineLoginPwdClose1;
    @BindView(R.id.update_pwd_new)
    EditText updatePwdNew;
    @BindView(R.id.iv_clear2)
    ImageView ivClear2;
    @BindView(R.id.mine_login_pwd_close2)
    ImageView mineLoginPwdClose2;
    @BindView(R.id.update_pwd_renew)
    EditText updatePwdRenew;
    @BindView(R.id.iv_clear3)
    ImageView ivClear3;
    @BindView(R.id.mine_login_pwd_close3)
    ImageView mineLoginPwdClose3;
    @BindView(R.id.update_pwd_save)
    TextView updatePwdSave;
    @BindView(R.id.tv_password_forget)
    TextView tvPasswordForget;
    @BindView(R.id.activity_update_pwd)
    LinearLayout activityUpdatePwd;
    /**
     * 原始密码
     */
    private String oldPwd = "";
    /**
     * 新密码
     */
    private String newPwd = "";
    /**
     * 确认新密码
     */
    private String renewPwd = "";


    @Override
    public int getLayoutId() {
        return R.layout.activity_update_pwd;
    }

    public void initView() {
        headView.setTitle("修改密码");
        updatePwdOld.addTextChangedListener(textWatcher);
        updatePwdNew.addTextChangedListener(textWatcher);
        updatePwdRenew.addTextChangedListener(textWatcher);
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }


    @OnClick({R.id.update_pwd_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.update_pwd_save:
                oldPwd = updatePwdOld.getText().toString().trim();
                newPwd = updatePwdNew.getText().toString().trim();
                renewPwd = updatePwdRenew.getText().toString().trim();
                if (!ObjectUtils.isNotEmpty(oldPwd)) {
                    ToastUtils.showShortCenter("请输入原始密码");
                    return;
                }
                if (!ObjectUtils.isNotEmpty(newPwd)) {
                    ToastUtils.showShortCenter("请设置新密码");
                    return;
                }
                if (!ObjectUtils.isNotEmpty(renewPwd)) {
                    ToastUtils.showShortCenter("请再次输入新密码");
                    return;
                }
                if (oldPwd.length() < 6) {
                    ToastUtils.showShortCenter("请输入不小于6位的原始密码");
                    return;
                }
                if (newPwd.length() < 6) {
                    ToastUtils.showShortCenter("请设置不小于6位的新密码");
                    return;
                }
                if (renewPwd.length() < 6) {
                    ToastUtils.showShortCenter("请再次输入不小于6位的新密码");
                    return;
                }
                if (!newPwd.equals(renewPwd)) {
                    ToastUtils.showShortCenter("两次输入密码不一致，请重新输入");
                    updatePwdRenew.setText("");
                    return;
                }

                LoadingDialog.showDialogForLoading(this, "数据保存中~", true);
                RetrofitHelper.getApiService().updatePwd(oldPwd, newPwd)
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
                                    ToastUtils.showShortCenter(response.getMessage());
                                    if (response.getCode() == 0) {
                                        SPUtils.getInstance().put(SPCommon.TOKEN, "");
                                        SPUtils.getInstance().put(SPCommon.PWD, "");
                                        ARouter.getInstance().build(Constant.ACTIVITY_LOGIN).navigation();
                                        finish();
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
                break;
        }
    }


    /**
     * 输入框的监听事件
     */
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (ObjectUtils.isNotEmpty(updatePwdOld.getText().toString().trim())) {
                ivClear1.setVisibility(View.VISIBLE);
            } else {
                ivClear1.setVisibility(View.GONE);
            }
            if (ObjectUtils.isNotEmpty(updatePwdNew.getText().toString().trim())) {
                ivClear2.setVisibility(View.VISIBLE);
            } else {
                ivClear2.setVisibility(View.GONE);
            }
            if (ObjectUtils.isNotEmpty(updatePwdRenew.getText().toString().trim())) {
                ivClear3.setVisibility(View.VISIBLE);
            } else {
                ivClear3.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @OnClick({R.id.iv_clear1, R.id.iv_clear2, R.id.iv_clear3})
    public void textClear(View view) {
        switch (view.getId()) {
            case R.id.iv_clear1:
                updatePwdOld.setText("");
                break;
            case R.id.iv_clear2:
                updatePwdNew.setText("");
                break;
            case R.id.iv_clear3:
                updatePwdRenew.setText("");
                break;
        }
    }

    @OnClick({R.id.mine_login_pwd_close1, R.id.mine_login_pwd_close2, R.id.mine_login_pwd_close3})
    public void visible(View view) {
        switch (view.getId()) {
            case R.id.mine_login_pwd_close1:
                // 密码可见与不可见
                if (mineLoginPwdClose1.isSelected()) {
                    mineLoginPwdClose1.setSelected(!mineLoginPwdClose1.isSelected());
                    // 设置EditText文本为隐藏的
                    updatePwdOld.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    mineLoginPwdClose1.setSelected(!mineLoginPwdClose1.isSelected());
                    // 设置EditText文本为可见的
                    updatePwdOld.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                break;
            case R.id.mine_login_pwd_close2:
                // 密码可见与不可见
                if (mineLoginPwdClose2.isSelected()) {
                    mineLoginPwdClose2.setSelected(!mineLoginPwdClose2.isSelected());
                    // 设置EditText文本为隐藏的
                    updatePwdNew.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    mineLoginPwdClose2.setSelected(!mineLoginPwdClose2.isSelected());
                    // 设置EditText文本为可见的
                    updatePwdNew.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                break;
            case R.id.mine_login_pwd_close3:
                // 密码可见与不可见
                if (mineLoginPwdClose3.isSelected()) {
                    mineLoginPwdClose3.setSelected(!mineLoginPwdClose3.isSelected());
                    // 设置EditText文本为隐藏的
                    updatePwdRenew.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    mineLoginPwdClose3.setSelected(!mineLoginPwdClose3.isSelected());
                    // 设置EditText文本为可见的
                    updatePwdRenew.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                break;

        }
    }

    @OnClick(R.id.tv_password_forget)
    public void forgetPwd() {
        Intent intent = new Intent(UpdatePwdActivity.this, RegisterActivity.class);
        intent.putExtra("type", 1);
        startActivity(intent);
    }
}
