package com.daqsoft.commonnanning.ui.mine;

import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.android.ProjectConfig;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.base.BaseWebActivity;
import com.daqsoft.commonnanning.base.IApplication;
import com.daqsoft.commonnanning.common.AESEncryptUtil;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.common.SocialUtil;
import com.daqsoft.commonnanning.http.HttpApiService;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.BindPhoneEntity;
import com.daqsoft.commonnanning.ui.entity.EventPost;
import com.daqsoft.commonnanning.ui.entity.UserEntity;
import com.example.tomasyb.baselib.base.api.ApiConstants;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.net.common.DefaultObserver;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.util.LogUtils;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import cloudbae.loginlibrary.bean.AuthResult;
import cloudbae.loginlibrary.util.AuthUtil;
import io.agora.yshare.SocialHelper;
import io.agora.yshare.callback.SocialLoginCallback;
import io.agora.yshare.entities.ThirdInfoEntity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.daqsoft.android.ProjectConfig.ANANING_TYPE;
import static com.daqsoft.commonnanning.ui.mine.RegisterActivity.isLetterDigit;
import com.daqsoft.event.UndateFinishEvent;
import org.greenrobot.eventbus.EventBus;
/**
 * 登录页面
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018.05.07
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_LOGIN)
public class LoginActivity extends BaseActivity implements SocialLoginCallback {

    @BindView(R.id.mine_login_register)
    TextView mineLoginRegister;
    @BindView(R.id.mine_login_user)
    EditText mineLoginUser;
    @BindView(R.id.mine_login_user_ll)
    LinearLayout mineLoginUserLl;
    @BindView(R.id.mine_login_pwd)
    EditText mineLoginPwd;
    @BindView(R.id.mine_login_pwd_close)
    ImageView mineLoginPwdClose;
    @BindView(R.id.mine_login_pwd_ll)
    LinearLayout mineLoginPwdLl;
    @BindView(R.id.mine_login_find_pwd)
    TextView mineLoginFindPwd;
    @BindView(R.id.mine_login_login)
    TextView mineLoginLogin;
    @BindView(R.id.mine_login_qq)
    ImageView mineLoginQq;
    @BindView(R.id.mine_login_wechat)
    ImageView mineLoginWechat;
    @BindView(R.id.headView)
    HeadView headView;
    @BindView(R.id.tv_login_title)
    TextView mTvTitle;
    @BindView(R.id.ll_login)
    LinearLayout ll_qqlogin;
    @BindView(R.id.login_agreement)
    LinearLayout loginAgreement;
    @BindView(R.id.mine_login_ai)
    ImageView mImageAi;
    @BindView(R.id.check)
    CheckBox checkbox;
    @BindView(R.id.registerAgreement)
    TextView registerAgreement;
    @BindView(R.id.mine_register_accept)
    TextView mineRegisterAccept;
    @BindView(R.id.ll_protocol)
    LinearLayout llProtocol;
    /**
     * 账号
     */
    private String account = "";
    /**
     * 密码
     */
    private String password = "";

    /**
     * 三方登录
     */
    private SocialHelper socialHelper;
    /**
     * 类型，1表示微信，2表示QQ
     */
    String type = "";
    /**
     * subway
     */
    private static final String CLIENT_ID = "TourNanning";

    @Override
    public int getLayoutId() {
        return R.layout.mine_login;
    }

    /**
     * 初始化
     */
    @Override
    public void initView() {

        AuthUtil.obtain().install(this, CLIENT_ID, ANANING_TYPE);
        if (ProjectConfig.CITY_NAME.equals("西藏")) {
            mineLoginQq.setVisibility(View.GONE);
            llProtocol.setVisibility(View.VISIBLE);
            boolean  isAgreePrivate = SPUtils.getInstance().getBoolean(ApiConstants.IS_FIRST_AGREE,false);
//            checkbox.setChecked(isAgreePrivate);
            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        boolean isAgreePrivate = SPUtils.getInstance().getBoolean(ApiConstants.IS_FIRST_AGREE, false);
                        if(!isAgreePrivate && ProjectConfig.CITY_NAME.equals("西藏")){
                            SPUtils.getInstance().put(ApiConstants.IS_FIRST_AGREE, true);
                            IApplication.application.initNeedPrivateSDK();
//                            EventBus.getDefault().post(new UndateFinishEvent(true));
                        }
                    }
                }
            });
        }else {
            mineLoginQq.setVisibility(View.VISIBLE);
            llProtocol.setVisibility(View.GONE);
        }

        if (ProjectConfig.CITY_NAME.equals("南宁")) {
            mImageAi.setVisibility(View.VISIBLE);
        } else {
            mImageAi.setVisibility(View.GONE);
        }
        headView.setTitle("登录");
        EventBus.getDefault().register(this);
        headView.setBackListener(new HeadView.OnBackListener() {
            @Override
            public void onBack(View v) {
                // 未登陆则点击返回退出了
                setResult(2);
                finish();
            }
        });
        mTvTitle.setText("欢迎来到" + ProjectConfig.CITY_NAME);
        socialHelper = SocialUtil.INSTANCE.socialHelper;
        if (ObjectUtils.isNotEmpty(ProjectConfig.WECHAT_APPID)) {
            ll_qqlogin.setVisibility(View.VISIBLE);
        } else {
            ll_qqlogin.setVisibility(View.GONE);
        }
    }

    /**
     * 用处：qq登录和分享回调，以及微博登录回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && socialHelper != null) {
            socialHelper.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == 4 && resultCode == 3) {
            finish();
        }
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    @OnClick({R.id.mine_login_register, R.id.mine_login_user, R.id.mine_login_pwd,
            R.id.mine_login_pwd_close, R.id.mine_login_find_pwd, R.id.mine_login_login,
            R.id.mine_login_qq, R.id.mine_login_wechat, R.id.login_agreement, R.id.mine_login_ai,R.id.mine_register_accept,R.id.registerAgreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_login_ai:
                AuthUtil.obtain().authLogin(this, new AuthUtil.CallBack() {
                    @Override
                    public void onResultCallBack(AuthResult ret) {
                        if (ret.result_code == AuthResult.RESULT_CODE_SUCCEED) {
                            LogUtils.e("你获取的云宝宝-->" + ret.auth_code);
                            LoadingDialog.showDialogForLoading(LoginActivity.this);
                            RetrofitHelper.getApiService().getYbbToken(ret.auth_code).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BaseResponse<UserEntity>>() {
                                @Override
                                public void accept(BaseResponse<UserEntity> response) throws Exception {
                                    LoadingDialog.cancelDialogForLoading();
                                    if (ObjectUtils.isNotEmpty(response)) {
                                        if (response.getCode() == 0 && ObjectUtils.isNotEmpty(response.getData())) {
                                            UserEntity user = response.getData();
                                            SPUtils.getInstance().put(SPCommon.TOKEN,
                                                    user.getToken());
                                            SPUtils.getInstance().put(SPCommon.NAME,
                                                    user.getName());
                                            SPUtils.getInstance().put(SPCommon.ID, user.getId());
                                            SPUtils.getInstance().put(SPCommon.ACCOUNT,
                                                    user.getAccount());
                                            SPUtils.getInstance().put(SPCommon.HEAD_IMG,
                                                    user.getHead());
                                            SPUtils.getInstance().put(SPCommon.PWD,
                                                    user.getAccount());
                                            SPUtils.getInstance().put(SPCommon.UC_ID,
                                                    user.getUcId());
                                            SPUtils.getInstance().put(SPCommon.UC_TOKEN,
                                                    user.getUcToken());
                                            HttpApiService.REQUESTMAP.put("token",
                                                    SPUtils.getInstance().getString(SPCommon.TOKEN));
                                            ToastUtils.showShortCenter("登录成功");
                                            setResult(3);
                                            finish();
                                        } else {
                                            ToastUtils.showShortCenter(response.getMessage());
                                        }
                                    } else {
                                        ToastUtils.showShortCenter("操作异常");
                                    }
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    LoadingDialog.cancelDialogForLoading();
                                    ToastUtils.showShortCenter("操作异常");
                                }
                            });
                        } else if (ret.result_code == AuthResult.RESULT_CODE_FAIL) { // 失败
                            ToastUtils.showShortCenter("登录失败!");
                        } else if (ret.result_code == AuthResult.RESULT_CODE_CANCEL) { // 取消
                            // 爱南宁1.6.0版本才支持这个操作
                            ToastUtils.showShortCenter("登录取消!");
                        }
                    }
                });
                break;
            case R.id.mine_login_register:
                // 注册
                ARouter.getInstance().build(Constant.ACTIVITY_REGISTER).withInt("type", 0).navigation();
                break;
            case R.id.mine_login_user:
                break;
            case R.id.mine_login_pwd:
                break;
            case R.id.mine_login_pwd_close:
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
                break;
            case R.id.mine_login_find_pwd:
                // 找回密码
                ARouter.getInstance().build(Constant.ACTIVITY_REGISTER).withInt("type", 1).navigation();
                break;
            case R.id.mine_login_login:
                // 登录

                if(!checkbox.isChecked() ){
                    ToastUtils.showShortCenter("请阅读并勾选相关协议");
                    return;
                }
                account = mineLoginUser.getText().toString().trim();
                password = mineLoginPwd.getText().toString().trim();
                login(account, password);
                break;
            case R.id.mine_login_qq:
                // QQ登录
                if(!checkbox.isChecked() ){
                    ToastUtils.showShortCenter("请阅读并勾选相关协议");
                    return;
                }
                socialHelper.loginQQ(LoginActivity.this, this);
                break;
            case R.id.mine_login_wechat:
                // 微信登录
                if(!checkbox.isChecked() ){
                    ToastUtils.showShortCenter("请阅读并勾选相关协议");
                    return;
                }
                socialHelper.loginWX(LoginActivity.this, this);
                break;
            case R.id.login_agreement:
                // 用户注册协议
                if (ProjectConfig.CITY_NAME.equals("西藏")) {
                    Intent intent = new Intent(LoginActivity.this, BaseWebActivity.class);
                    intent.putExtra("HTMLURL", ProjectConfig.USER_PATH);
                    startActivity(intent);
                }else{
                    ARouter.getInstance().build(Constant.ACTIVITY_CONTENT_WEB).withInt("pageType", 1).navigation();

                }
                break;
            case R.id.registerAgreement:
                // 隐私协议
                if(ProjectConfig.CITY_NAME.equals("西藏")) {
                    Intent intent =new Intent(LoginActivity.this, BaseWebActivity.class);
                    intent.putExtra("HTMLURL", ProjectConfig.PRIVATER_PATH);
                    startActivity(intent);
                }
                break;
            case R.id.mine_register_accept:
                // 用户注册协议
                if (ProjectConfig.CITY_NAME.equals("西藏")) {
                    Intent intent = new Intent(LoginActivity.this, BaseWebActivity.class);
                    intent.putExtra("HTMLURL", ProjectConfig.USER_PATH);
                    startActivity(intent);
                }else{
                    ARouter.getInstance().build(Constant.ACTIVITY_CONTENT_WEB).withInt("pageType", 1).navigation();
                }
                break;

            default:
                break;
        }
    }

    /**
     * 登录
     *
     * @param account  账号
     * @param password 密码
     */
    public void login(String account, final String password) {
        if (!ObjectUtils.isNotEmpty(account)) {
            ToastUtils.showLongCenter("请输入账号！");
            return;
        }
        if (!ObjectUtils.isNotEmpty(password)) {
            ToastUtils.showLongCenter("请输入密码！");
            return;
        }
        if (isLetterDigit(password)) {
            if (mineLoginPwd.getText().toString().length() < 8) {
                ToastUtils.showShortCenter("请输入长度不小于8位的密码！");
                return;
            }
        } else {
            ToastUtils.showShortCenter("请检查是否包含数字和字母!");
        }
        LoadingDialog.showDialogForLoading(this, "登录中~", false);
        try {
            String psd = AESEncryptUtil.Encrypt(password);
            RetrofitHelper.getApiService().login("1", account, psd).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer<Disposable>() {
                @Override
                public void accept(Disposable disposable) throws Exception {
                    addDisposable(disposable);
                }
            }).subscribe(new Consumer<BaseResponse<UserEntity>>() {
                @Override
                public void accept(BaseResponse<UserEntity> response) throws Exception {
                    LoadingDialog.cancelDialogForLoading();
                    if (response.getCode() == 0) {
                        UserEntity user = response.getData();
                        SPUtils.getInstance().put(SPCommon.TOKEN, user.getToken());
                        SPUtils.getInstance().put(SPCommon.NAME, user.getName());
                        SPUtils.getInstance().put(SPCommon.ID, user.getId());
                        SPUtils.getInstance().put(SPCommon.ACCOUNT, user.getAccount());
                        SPUtils.getInstance().put(SPCommon.HEAD_IMG, user.getHead());
                        SPUtils.getInstance().put(SPCommon.PWD, user.getAccount());
                        SPUtils.getInstance().put(SPCommon.UC_ID, user.getUcId());
                        SPUtils.getInstance().put(SPCommon.UC_TOKEN, user.getUcToken());
                        HttpApiService.REQUESTMAP.put("token",
                                SPUtils.getInstance().getString(SPCommon.TOKEN));
                        ToastUtils.showShortCenter("登录成功");
                        setResult(3);
                        finish();
                    } else {
                        ToastUtils.showShortCenter(response.getMessage());
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    LoadingDialog.cancelDialogForLoading();
                    ToastUtils.showLong("请求失败!");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventPost msg) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (null != socialHelper) {
            socialHelper.clear();
        }
        AuthUtil.obtain().uninstall();
        LogUtils.e("------------------》执行消失方法");
    }

    /**
     * 第三方登录成功回调
     *
     * @param info
     */
    @Override
    public void loginSuccess(ThirdInfoEntity info) {
        if (ObjectUtils.isNotEmpty(info.getOpenId())) {
            // qq分享如果选择留在qq，通过home键退出，再进入app则不会有回调
            try {
                LoadingDialog.showDialogForLoading(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // QQ 登录成功的回调
            if (ThirdInfoEntity.PLATFORM_QQ.equals(info.getPlatform())) {
                type = "2";
                SPUtils.getInstance().put(SPCommon.QQ_OPEN_ID, info.getOpenId());
                SPUtils.getInstance().put(SPCommon.WX_UNION_ID, info.getOpenId());
                SPUtils.getInstance().put(SPCommon.HEAD_IMG, info.getQqInfo().getFigureurl_2());
            } else if (ThirdInfoEntity.PLATFORM_WX.equals(info.getPlatform())) {
                // 微信登录成功的回调
                type = "1";
                SPUtils.getInstance().put(SPCommon.WX_UNION_ID, info.getUnionId());
                SPUtils.getInstance().put(SPCommon.WX_OPEN_ID, info.getWxInfo().getOpenid());
                SPUtils.getInstance().put(SPCommon.HEAD_IMG, info.getWxInfo().getHeadimgurl());
            }
            SPUtils.getInstance().put(SPCommon.NAME, info.getNickname());
            bindingPhone();
        }
    }

    /**
     * 第三方登录错误回调
     *
     * @param msg
     */
    @Override
    public void socialError(String msg) {
        LogUtils.e(msg);
        ToastUtils.showShortCenter("登录取消!");
    }

    /**
     * 判断是否已经绑定账号，否则则绑定账号
     */
    public void bindingPhone() {
        RetrofitHelper.getApiService().bindingInfo(SPUtils.getInstance().getString(SPCommon.WX_UNION_ID), type).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).subscribe(new DefaultObserver<BindPhoneEntity>() {
            @Override
            public void onSuccess(BaseResponse<BindPhoneEntity> response) {
                LoadingDialog.cancelDialogForLoading();
                if (ObjectUtils.isNotEmpty(response) && ObjectUtils.isNotEmpty(response.getData()) && response.getCode() == 0) {
                    // 已经绑定
                    if (response.getData().getStatus() == 0) {
                        SPUtils.getInstance().put(SPCommon.TOKEN, response.getData().getToken());
                        SPUtils.getInstance().put(SPCommon.UC_ID, response.getData().getUcId());
                        SPUtils.getInstance().put(SPCommon.UC_TOKEN,
                                response.getData().getUcToken());
                        HttpApiService.REQUESTMAP.put("token",
                                SPUtils.getInstance().getString(SPCommon.TOKEN));
                        setResult(3);
                        finish();
                    } else {
                        // 未绑定，则跳转绑定页面
                        ARouter.getInstance().build(Constant.ACTIVITY_REGISTER).withInt("type",
                                2).withString("wechat", type).navigation(LoginActivity.this, 4);
                    }
                }
            }
        });
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            setResult(2);
            finish();
        }
        return super.dispatchKeyEvent(event);
    }
}
