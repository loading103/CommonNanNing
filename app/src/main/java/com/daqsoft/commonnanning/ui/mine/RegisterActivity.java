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
 * 用户注册页面
 *
 * @author 黄熙
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
     * 电话号码
     */
    private String phone = "";
    /**
     * 验证码
     */
    private String sendMsg = "";
    /**
     * 是否发送
     */
    private boolean isSend = true;
    /**
     * 0 注册，1 找回密码,2  绑定手机号
     */
    @Autowired(name = "type")
    int type;
    /**
     * 标题
     */
    private String title = "";
    /**
     * 短信的类型
     */
    private String sendType = "";

    /**
     * 判断是微信第三方登录还是QQ第三方登录
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
     * 初始化
     */
    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        title = type == 0 ? "手机号注册" : type == 1 ? "找回密码" : "绑定手机号";
        if (type == 2) {
            wechat = getIntent().getStringExtra("wechat");
        }
        registerTitle.setText(title);
        sendType = type == 0 ? URLConstant.REGISTER_TYPE : type == 1 ?
                URLConstant.FIND_PASSWORD_TYPE : URLConstant.BIND_ACCOUNT_TYPE;
        if (type == 0) {
            // 手机号注册
            llProtocol.setVisibility(View.VISIBLE);
            tvAccountLogin.setVisibility(View.VISIBLE);
            mineRegisterNext.setText("注册");
            tvAccountLogin.setText("账号密码登录");
        } else if (type == 1) {
            mineRegisterNext.setText("确定");
            llProtocol.setVisibility(View.INVISIBLE);
            tvAccountLogin.setVisibility(View.GONE);
        } else if (type == 2) {
            llProtocol.setVisibility(View.INVISIBLE);
            mineRegisterNext.setText("确认绑定");
            tvAccountLogin.setVisibility(View.GONE);
        }
        if(!ProjectConfig.CITY_NAME.equals("西藏")) {
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
                // 发送验证码
                if (("获取验证码".equals(mineRegisterSendMessage.getText().toString().trim()) ||
                        "重新发送".equals(mineRegisterSendMessage.getText().toString().trim())) && isSend) {
                    // 电话
                    if (type == 2) {
                        // 绑定手机号，判断账号是否存在
                        accountExist();
                    }
                    LoadingDialog.showDialogForLoading(this, "正在获取验证码", true);
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
                                ToastUtils.showShortCenter("验证码发送成功,请查看短信");
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
                            ToastUtils.showShortCenter("请求失败!");
                        }
                    });

                }
                break;
            case R.id.registerAgreement:
                // 隐私协议
                if(ProjectConfig.CITY_NAME.equals("西藏")) {
                    Intent intent =new Intent(RegisterActivity.this, BaseWebActivity.class);
                    intent.putExtra("HTMLURL", ProjectConfig.PRIVATER_PATH);
                    startActivity(intent);
                }
                break;
            case R.id.mine_register_accept:
                // 用户注册协议
                if (ProjectConfig.CITY_NAME.equals("西藏")) {
                    Intent intent =new Intent(RegisterActivity.this, BaseWebActivity.class);
                    intent.putExtra("HTMLURL", ProjectConfig.USER_PATH);
                    startActivity(intent);
                }else{
                    ARouter.getInstance().build(Constant.ACTIVITY_CONTENT_WEB).withInt("pageType", 1).navigation();

                }
                break;
            case R.id.mine_register_next:
                if(!checkbox.isChecked() && type == 0 ){
                    ToastUtils.showShortCenter("请阅读并勾选相关协议");
                    return;
                }
                boolean isAgreePrivate = SPUtils.getInstance().getBoolean(ApiConstants.IS_FIRST_AGREE, false);
                if(!isAgreePrivate && ProjectConfig.CITY_NAME.equals("西藏")){
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
                    ToastUtils.showShortCenter("请输入验证码");
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
                        ToastUtils.showShortCenter("请求错误!");
                    }
                });
                break;
            default:
                break;
        }

    }

    /**
     * 判断账号是否已经存在或者注册
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
                        // 已经存在
                        mineLoginPwdLl.setVisibility(View.GONE);
                        viewLine.setVisibility(View.GONE);
                    } else if (response.getData().getExist() == 1) {
                        // 不存在
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
     * 找回密码
     */
    public void findPwd() {
        LoadingDialog.showDialogForLoading(RegisterActivity
                .this, "密码设置中~", true);
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
                        ToastUtils.showShortCenter("修改密码成功");
                        finish();
                    } else {
                        ToastUtils.showShortCenter(response.getMessage());
                    }
                } else {
                    ToastUtils.showShortCenter("修改密码错误");
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
     * 注册
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
                    ToastUtils.showShortCenter("注册失败");
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
     * 微信登录后绑定账号
     */
    public void bindAccount() {
        String password = mineLoginPwd.getText().toString().trim();
        if (mineLoginPwdLl.getVisibility() == View.VISIBLE && !ObjectUtils.isNotEmpty(password)) {
            ToastUtils.showShort("请输入密码");
        } else {
            LoadingDialog.showDialogForLoading(this, "帐号绑定中~", true);
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
                        ToastUtils.showShortCenter("账号绑定失败");
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
//        if (!RegexUtils.isMobileExact(phone)) {
//            ToastUtils.showShortCenter("请输入正确的手机号码！");
//            return false;
//        }
        if (phone.length()!=11) {
            ToastUtils.showShortCenter("请输入正确的手机号码！");
            return false;
        }
        return isPhone;
    }

    /**
     * 必须包含数字、中文、字母
     * www.yoodb.com
     *
     * @param str
     * @return
     */
    public static boolean isLetterDigit(String str) {
        boolean isDigit = false;
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
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
     * 发送验证码后的倒计时
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
                    mineRegisterSendMessage.setText("重新发送");
                    isSend = true;
                } else {
                    mineRegisterSendMessage.setText("    " + time + "s" + "    ");
                    time--;
                    handler.sendEmptyMessageDelayed(1, 1000);
                }
            } else if (msg.what == 2) {
                // 绑定第三方账号
                if (type == 2) {
                    bindAccount();
                } else if (type == 0) {
                    // 注册
                    String texpasd = mineLoginPwd.getText().toString();
                    if (isLetterDigit(texpasd)) {
                        if (mineLoginPwd.getText().toString().length() < 8) {
                            ToastUtils.showShortCenter("请输入长度不小于8位的密码！");
                            return;
                        }
                        register();
                    } else {
                        ToastUtils.showShortCenter("请检查是否包含数字和字母!");
                    }
                } else if (type == 1) {
                    // 找回密码
                    String texpasd = mineLoginPwd.getText().toString();
                    if (isLetterDigit(texpasd)) {
                        if (mineLoginPwd.getText().toString().length() < 8) {
                            ToastUtils.showShortCenter("请输入长度不小于8位的密码！");
                            return;
                        }
                        findPwd();
                    } else {
                        ToastUtils.showShortCenter("请检查是否包含数字和字母!");
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
     * 密码显示隐藏的状态改变
     */
    @OnClick(R.id.mine_login_pwd_close)
    public void pwdVisible() {
        // 密码可见与不可见
        if (mineLoginPwdClose.isSelected()) {
            mineLoginPwdClose.setSelected(!mineLoginPwdClose.isSelected());
            //设置EditText文本为隐藏的
            mineLoginPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            mineLoginPwdClose.setSelected(!mineLoginPwdClose.isSelected());
            //设置EditText文本为可见的
            mineLoginPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
    }

    @OnClick(R.id.tv_account_login)
    public void accountLoginClicked(View view) {
        if (tvAccountLogin.getText().toString().equals("账号密码登录")) {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if (tvAccountLogin.getText().toString().equals("修改密码")) {
            ARouter.getInstance().build(Constant.ACTIVITY_UPDATE_PWD).navigation();
            finish();
        }
    }

}