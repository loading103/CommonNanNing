package com.daqsoft.commonnanning.ui.mine;

import android.content.Intent;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
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

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 改变昵称，改变姓名Activity
 *
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2018-8-27
 * @since JDK 1.8.0_171
 */
@Route(path = Constant.ACTIVITY_CHANGE_NAME)
public class ChangeNameActivity extends BaseActivity {
    @BindView(R.id.headView)
    HeadView headView;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.tv_ok)
    TextView tvOk;
    private String name;
    private int type;


    @Override
    public int getLayoutId() {
        return R.layout.activity_change_name;
    }

    @Override
    public void initView() {
        etName.setHint(name);
        try {
            name = getIntent().getExtras().getString("name");
            type = getIntent().getExtras().getInt("type");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (type == 0) {
            headView.setTitle("设置昵称");
        } else if (type == 1) {
            headView.setTitle("设置名称");
        }
        InputFilter filter=new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int i, int i1, Spanned spanned,
                                       int i2, int i3) {
                String speChat="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\ud83e\udc00-\ud83e\udfff]|[\u2100-\u32ff]|[\u0030-\u007f][\u20d0-\u20ff]|[\u0080-\u00ff]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if(matcher.find())return "";
                else return null;
            }
        };
        etName.setFilters(new InputFilter[]{filter,new InputFilter.LengthFilter(10)});
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    @OnClick(R.id.iv_clear)
    public void clear() {
        etName.setText("");
    }

    @OnClick(R.id.tv_ok)
    public void ok() {
        String s = etName.getText().toString().trim();
        if (ObjectUtils.isNotEmpty(s)) {
            updateUserInfo(s);
        }

    }


    /**
     * 更新数据
     */
    public void updateUserInfo(final String s) {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", SPUtils.getInstance().getString(SPCommon.TOKEN));
        if (type == 0) {
            map.put("nickname", s);
        } else if (type == 1) {
            map.put("name", s);
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
                                ToastUtils.showShortCenter("保存成功");
                                Intent intent = new Intent();
                                intent.putExtra("name", s);
                                if (type == 0) {
                                    setResult(2, intent);
                                } else if (type == 1) {
                                    setResult(3, intent);
                                }
                                finish();
                            } else
                                ToastUtils.showShortCenter(response.getMessage());
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

}
