package com.daqsoft.commonnanning.ui.service.complaint;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.utils.Utils;
import com.daqsoft.commonnanning.view.AuthenButton;
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
import io.reactivex.schedulers.Schedulers;

/**
 * 在线投诉查询页面
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-3-22
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_COMPLAINT_QUERY)
public class OnlineComplaintActivity extends BaseActivity {
    @BindView(R.id.headView)
    HeadView headView;
    @BindView(R.id.ll_code)
    LinearLayout ll_code;
    @BindView(R.id.ll_phone)
    LinearLayout ll_phone;
    @BindView(R.id.ll_authenCode)
    LinearLayout ll_authenCode;
    @BindView(R.id.edt_code)
    EditText edt_code;
    @BindView(R.id.edt_phone)
    EditText edt_phone;
    @BindView(R.id.edt_authen_code)
    EditText edt_authen_code;
    @BindView(R.id.btn_authen)
    AuthenButton btn_authen;


    @Override
    public int getLayoutId() {
        return R.layout.activity_online_complaint;
    }

    @Override
    public void initView() {
        headView.setTitle("在线投诉");
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    @OnClick(R.id.rbtn_code)
    public void onclick_code(View v) {
        ll_phone.setVisibility(View.GONE);
        ll_code.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.rbtn_phone)
    public void onclick_phone(View v) {
        ll_phone.setVisibility(View.VISIBLE);
        ll_code.setVisibility(View.GONE);
        ll_authenCode.setVisibility(ObjectUtils.isNotEmpty(
                SPUtils.getInstance().getString(SPCommon.TOKEN)) ? View.GONE : View.VISIBLE);
    }

    @OnClick(R.id.btn_code_select)
    public void onclick_formCodeSelect(View v) {
        if (TextUtils.isEmpty(edt_code.getText().toString())) {
            Toast.makeText(this, "请输入查询编码", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, ComplaintQueryResultActivity.class);
        intent.putExtra("content", edt_code.getText().toString());
        intent.putExtra("type", "code");
        startActivity(intent);
    }

    @OnClick(R.id.btn_authen)
    public void onclick_authen(View v) {
        if (TextUtils.isEmpty(edt_phone.getText().toString())) {
            Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        String phone = edt_phone.getText().toString();
        if (!Utils.isMobile(phone)) {
            Toast.makeText(this, "手机号码格式不正确，请重新输入", Toast.LENGTH_SHORT).show();
            return;
        }
        btn_authen.start(edt_phone.getText().toString(), "_complaintValidate");
    }

    @OnClick(R.id.btn_phone_select)
    public void onclick_fromPhoneSelect(View v) {
        if (TextUtils.isEmpty(edt_phone.getText().toString())) {
            Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ll_authenCode.getVisibility() == View.GONE) {
            Intent intent = new Intent(OnlineComplaintActivity.this, ComplaintQueryResultActivity.class);
            intent.putExtra("content", edt_phone.getText().toString());
            intent.putExtra("type", "phone");
            startActivity(intent);
            return;
        }
        String phone = edt_phone.getText().toString();
        if (!Utils.isMobile(phone)) {
            Toast.makeText(this, "手机号码格式不正确，请重新输入", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(edt_authen_code.getText().toString())) {
            Toast.makeText(this, "请输入手机验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        LoadingDialog.showDialogForLoading(this, "正在验证", true);
        RetrofitHelper.getApiService()
                .checkMsg(phone, "_complaintValidate", edt_authen_code.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver() {

                    @Override
                    public void onSuccess(BaseResponse response) {
                        if (ObjectUtils.isNotEmpty(response)) {
                            if (response.getCode() == 0) {
                                Intent intent = new Intent(OnlineComplaintActivity.this,
                                        ComplaintQueryResultActivity.class);
                                intent.putExtra("content", edt_phone.getText().toString());
                                intent.putExtra("type", "phone");
                                startActivity(intent);
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
    }

    @Override
    protected void onStop() {
        super.onStop();
        btn_authen.stop();
    }
}
