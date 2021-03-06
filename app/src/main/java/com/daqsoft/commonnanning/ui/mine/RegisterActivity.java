package com.daqsoft.commonnanning.ui.mine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.daqsoft.commonnanning.base.IApplication;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.android.ProjectConfig;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.base.BaseWebActivity;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.common.URLConstant;
import com.daqsoft.commonnanning.http.HttpApiService;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.BindPhoneEntity;
import com.daqsoft.commonnanning.ui.entity.UserEntity;
import com.example.tomasyb.baselib.base.api.ApiConstants;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.net.common.DefaultObserver;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.RegexUtils;
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
import com.daqsoft.event.UndateFinishEvent;
import org.greenrobot.eventbus.EventBus;
/**
 * ??????????????????
 *
 * @author ??????
 * @version 1.0.0
 * @date 2018.05.07
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_REGISTER)
public class RegisterActivity extends BaseActivity {
    @BindView(R.id.mine_register_phone)
    EditText mineRegisterPhone;
    @BindView(R.id.mine_register_send_message)
    TextView mineRegisterSendMessage;
    @BindView(R.id.mine_register_phone_ll)
    LinearLayout mineRegisterPhoneLl;
    @BindView(R.id.mine_register_test)
    EditText mineRegisterTest;
    @BindView(R.id.mine_register_test_ll)
    LinearLayout mineRegisterTestLl;
    @BindView(R.id.mine_register_next)
    TextView mineRegisterNext;
    @BindView(R.id.mine_register_accept)
    TextView mineRegisterAccept;
    @BindView(R.id.register_title)
    TextView registerTitle;
    @BindView(R.id.headView)
    HeadView headView;
    @BindView(R.id.mine_login_pwd)
    EditText mineLoginPwd;
    @BindView(R.id.mine_login_pwd_close)
    ImageView mineLoginPwdClose;
    @BindView(R.id.mine_login_pwd_ll)
    LinearLayout mineLoginPwdLl;
    @BindView(R.id.ll_protocol)
    LinearLayout llProtocol;
    @BindView(R.id.view)
    View viewLine;
    @BindView(R.id.tv_account_login)
    TextView tvAccountLogin;
    @BindView(R.id.check)
    CheckBox checkbox;
    @BindView(R.id.registerAgreement)
    TextView registerAgreement;


    /**
     * ????????????
     */
    private String phone = "";
    /**
     * ?????????
     */
    private String sendMsg = "";
    /**
     * ????????????
     */
    private boolean isSend = true;
    /**
     * 0 ?????????1 ????????????,2  ???????????????
     */
    @Autowired(name = "type")
    int type;
    /**
     * ??????
     */
    private String title = "";
    /**
     * ???????????????
     */
    private String sendType = "";

    /**
     * ????????????????????????????????????QQ???????????????
     */
    @Autowired(name = "wechat")
    String wechat = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    /**
     * ?????????
     */
    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        title = type == 0 ? "???????????????" : type == 1 ? "????????????" : "???????????????";
        if (type == 2) {
            wechat = getIntent().getStringExtra("wechat");
        }
        registerTitle.setText(title);
        sendType = type == 0 ? URLConstant.REGISTER_TYPE : type == 1 ?
                URLConstant.FIND_PASSWORD_TYPE : URLConstant.BIND_ACCOUNT_TYPE;
        if (type == 0) {
            // ???????????????
            llProtocol.setVisibility(View.VISIBLE);
            tvAccountLogin.setVisibility(View.VISIBLE);
            mineRegisterNext.setText("??????");
            tvAccountLogin.setText("??????????????????");
        } else if (type == 1) {
            mineRegisterNext.setText("??????");
            llProtocol.setVisibility(View.INVISIBLE);
            tvAccountLogin.setVisibility(View.GONE);
        } else if (type == 2) {
            llProtocol.setVisibility(View.INVISIBLE);
            mineRegisterNext.setText("????????????");
            tvAccountLogin.setVisibility(View.GONE);
        }
        if(!ProjectConfig.CITY_NAME.equals("??????")) {
            registerAgreement.setVisibility(View.GONE);
        }else {
            registerAgreement.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    @OnClick({R.id.mine_register_send_message, R.id.mine_register_accept, R.id.mine_register_next,R.id.registerAgreement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_register_send_message:
                phone = mineRegisterPhone.getText().toString().trim();
                if (!checkPhone(phone)) {
                    return;
                }
                if (type == 0 || type == 1) {
                    mineLoginPwdLl.setVisibility(View.VISIBLE);
                    viewLine.setVisibility(View.VISIBLE);
                }
                // ???????????????
                if (("???????????????".equals(mineRegisterSendMessage.getText().toString().trim()) ||
                        "????????????".equals(mineRegisterSendMessage.getText().toString().trim())) && isSend) {
                    // ??????
                    if (type == 2) {
                        // ??????????????????????????????????????????
                        accountExist();
                    }
                    LoadingDialog.showDialogForLoading(this, "?????????????????????", true);
                    RetrofitHelper.getApiService().getSendMsg(phone, sendType).subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            addDisposable(disposable);
                        }
                    }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BaseResponse>() {
                        @Override
                        public void accept(BaseResponse response) throws Exception {
                            LoadingDialog.cancelDialogForLoading();
                            if (response.getCode() == 0) {
                                ToastUtils.showShortCenter("?????????????????????,???????????????");
                                time = 120;
                                handler.sendEmptyMessage(1);
                            } else {
                                ToastUtils.showShortCenter(response.getMessage());
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            LoadingDialog.cancelDialogForLoading();
                            ToastUtils.showShortCenter("????????????!");
                        }
                    });

                }
                break;
            case R.id.registerAgreement:
                // ????????????
                if(ProjectConfig.CITY_NAME.equals("??????")) {
                    Intent intent =new Intent(RegisterActivity.this, BaseWebActivity.class);
                    intent.putExtra("HTMLURL", ProjectConfig.PRIVATER_PATH);
                    startActivity(intent);
                }
                break;
            case R.id.mine_register_accept:
                // ??????????????????
                if (ProjectConfig.CITY_NAME.equals("??????")) {
                    Intent intent =new Intent(RegisterActivity.this, BaseWebActivity.class);
                    intent.putExtra("HTMLURL", ProjectConfig.USER_PATH);
                    startActivity(intent);
                }else{
                    ARouter.getInstance().build(Constant.ACTIVITY_CONTENT_WEB).withInt("pageType", 1).navigation();

                }
                break;
            case R.id.mine_register_next:
                if(!checkbox.isChecked() && type == 0 ){
                    ToastUtils.showShortCenter("??????????????????????????????");
                    return;
                }
                boolean isAgreePrivate = SPUtils.getInstance().getBoolean(ApiConstants.IS_FIRST_AGREE, false);
                if(!isAgreePrivate && ProjectConfig.CITY_NAME.equals("??????")){
                    SPUtils.getInstance().put(ApiConstants.IS_FIRST_AGREE, true);
                    IApplication.application.initNeedPrivateSDK();
//                    EventBus.getDefault().post(new UndateFinishEvent(true));
                }
                phone = mineRegisterPhone.getText().toString().trim();
                sendMsg = mineRegisterTest.getText().toString().trim();
                if (!checkPhone(phone)) {
                    return;
                }
                if (!ObjectUtils.isNotEmpty(sendMsg)) {
                    ToastUtils.showShortCenter("??????????????????");
                    return;
                }
                RetrofitHelper.getApiService().checkMsg(phone, sendType, sendMsg).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                }).subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse response) throws Exception {
                        if (response.getCode() == 0) {
                            handler.sendEmptyMessage(2);
                        } else {
                            ToastUtils.showShortCenter(response.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showShortCenter("????????????!");
                    }
                });
                break;
            default:
                break;
        }

    }

    /**
     * ??????????????????????????????????????????
     */
    public void accountExist() {
        RetrofitHelper.getApiService().accountExist(phone).subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver<BindPhoneEntity>() {
            @Override
            public void onSuccess(BaseResponse<BindPhoneEntity> response) {
                if (ObjectUtils.isNotEmpty(response) && ObjectUtils.isNotEmpty(response.getData()) && response.getCode() == 0) {
                    if (response.getData().getExist() == 0) {
                        // ????????????
                        mineLoginPwdLl.setVisibility(View.GONE);
                        viewLine.setVisibility(View.GONE);
                    } else if (response.getData().getExist() == 1) {
                        // ?????????
                        mineLoginPwdLl.setVisibility(View.VISIBLE);
                        viewLine.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }


    /**
     * ????????????
     */
    public void findPwd() {
        LoadingDialog.showDialogForLoading(RegisterActivity
                .this, "???????????????~", true);
        RetrofitHelper.getApiService().findPwd(phone, mineLoginPwd.getText().toString(),
                mineLoginPwd.getText().toString(), sendMsg).subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver() {
            @Override
            public void onSuccess(BaseResponse response) {
                if (ObjectUtils.isNotEmpty(response)) {
                    if (response.getCode() == 0) {
                        ToastUtils.showShortCenter("??????????????????");
                        finish();
                    } else {
                        ToastUtils.showShortCenter(response.getMessage());
                    }
                } else {
                    ToastUtils.showShortCenter("??????????????????");
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                LoadingDialog.cancelDialogForLoading();
            }
        });
    }

    /**
     * ??????
     */
    public void register() {
        LoadingDialog.showDialogForLoading(this);
        RetrofitHelper.getApiService().register(phone, mineLoginPwd.getText().toString(), sendMsg
                , "1").subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver() {
            @Override
            public void onSuccess(BaseResponse response) {
                if (ObjectUtils.isNotEmpty(response)) {
                    if (response.getCode() == 0) {
                        ToastUtils.showCustomShort(R.layout.toast_register);
                        finish();
                    } else {
                        ToastUtils.showShortCenter(response.getMessage());
                    }
                } else {
                    ToastUtils.showShortCenter("????????????");
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                LoadingDialog.cancelDialogForLoading();
            }
        });
    }

    /**
     * ???????????????????????????
     */
    public void bindAccount() {
        String password = mineLoginPwd.getText().toString().trim();
        if (mineLoginPwdLl.getVisibility() == View.VISIBLE && !ObjectUtils.isNotEmpty(password)) {
            ToastUtils.showShort("???????????????");
        } else {
            LoadingDialog.showDialogForLoading(this, "???????????????~", true);
            RetrofitHelper.getApiService().bindingAccount(password,
                    SPUtils.getInstance().getString(SPCommon.WX_UNION_ID), phone, wechat).subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() {

                @Override
                public void accept(Disposable disposable) throws Exception {
                    addDisposable(disposable);
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver<UserEntity>() {
                @Override
                public void onSuccess(BaseResponse<UserEntity> response) {
                    if (ObjectUtils.isNotEmpty(response)) {
                        if (ObjectUtils.isNotEmpty(response.getData()) && ObjectUtils.isNotEmpty(response.getCode())) {
                            UserEntity user = response.getData();
                            SPUtils.getInstance().put(SPCommon.TOKEN, user.getToken());
                            SPUtils.getInstance().put(SPCommon.ACCOUNT, user.getAccount());
                            SPUtils.getInstance().put(SPCommon.ID, user.getId());
                            SPUtils.getInstance().put(SPCommon.UC_ID, user.getUcId());
                            SPUtils.getInstance().put(SPCommon.UC_TOKEN, user.getUcToken());
                            HttpApiService.REQUESTMAP.put("token",
                                    SPUtils.getInstance().getString(SPCommon.TOKEN));
                            setResult(3);
                            finish();
                        } else {
                            ToastUtils.showShortCenter(response.getMessage());
                        }
                    } else {
                        ToastUtils.showShortCenter("??????????????????");
                    }
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    LoadingDialog.cancelDialogForLoading();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 0) {
            finish();
        }
    }

    /**
     * ????????????????????????????????????????????????
     *
     * @param phone
     */
    public boolean checkPhone(String phone) {
        boolean isPhone = true;
        if (!ObjectUtils.isNotEmpty(phone)) {
            ToastUtils.showShortCenter("????????????????????????");
            return false;
        }
//        if (!RegexUtils.isMobileExact(phone)) {
//            ToastUtils.showShortCenter("?????????????????????????????????");
//            return false;
//        }
        if (phone.length()!=11) {
            ToastUtils.showShortCenter("?????????????????????????????????");
            return false;
        }
        return isPhone;
    }

    /**
     * ????????????????????????????????????
     * www.yoodb.com
     *
     * @param str
     * @return
     */
    public static boolean isLetterDigit(String str) {
        boolean isDigit = false;
        boolean isLetter = false;//????????????boolean????????????????????????????????????
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                isDigit = true;
            }
            if (Character.isLetter(str.charAt(i))) {
                isLetter = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isDigit && isLetter && str.matches(regex);
        return isRight;
    }

    /**
     * ??????????????????????????????
     */
    private int time = 120;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (time == 0) {
                    handler.removeMessages(1);
                    mineRegisterSendMessage.setText("????????????");
                    isSend = true;
                } else {
                    mineRegisterSendMessage.setText("    " + time + "s" + "    ");
                    time--;
                    handler.sendEmptyMessageDelayed(1, 1000);
                }
            } else if (msg.what == 2) {
                // ?????????????????????
                if (type == 2) {
                    bindAccount();
                } else if (type == 0) {
                    // ??????
                    String texpasd = mineLoginPwd.getText().toString();
                    if (isLetterDigit(texpasd)) {
                        if (mineLoginPwd.getText().toString().length() < 8) {
                            ToastUtils.showShortCenter("????????????????????????8???????????????");
                            return;
                        }
                        register();
                    } else {
                        ToastUtils.showShortCenter("????????????????????????????????????!");
                    }
                } else if (type == 1) {
                    // ????????????
                    String texpasd = mineLoginPwd.getText().toString();
                    if (isLetterDigit(texpasd)) {
                        if (mineLoginPwd.getText().toString().length() < 8) {
                            ToastUtils.showShortCenter("????????????????????????8???????????????");
                            return;
                        }
                        findPwd();
                    } else {
                        ToastUtils.showShortCenter("????????????????????????????????????!");
                    }
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(1);
    }

    /**
     * ?????????????????????????????????
     */
    @OnClick(R.id.mine_login_pwd_close)
    public void pwdVisible() {
        // ????????????????????????
        if (mineLoginPwdClose.isSelected()) {
            mineLoginPwdClose.setSelected(!mineLoginPwdClose.isSelected());
            //??????EditText??????????????????
            mineLoginPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            mineLoginPwdClose.setSelected(!mineLoginPwdClose.isSelected());
            //??????EditText??????????????????
            mineLoginPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
    }

    @OnClick(R.id.tv_account_login)
    public void accountLoginClicked(View view) {
        if (tvAccountLogin.getText().toString().equals("??????????????????")) {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if (tvAccountLogin.getText().toString().equals("????????????")) {
            ARouter.getInstance().build(Constant.ACTIVITY_UPDATE_PWD).navigation();
            finish();
        }
    }

}