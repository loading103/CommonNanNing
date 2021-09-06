package com.daqsoft.commonnanning.ui.mine;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.common.TakePhoto;
import com.daqsoft.commonnanning.common.URLConstant;
import com.daqsoft.commonnanning.http.FileUpload;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.adapter.CommentWriteAdapter;
import com.daqsoft.commonnanning.utils.annotation.ViewToast;
import com.daqsoft.commonnanning.view.FullyGridLayoutManager;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.net.common.DefaultObserver;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.util.LogUtils;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.RegexUtils;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.agora.yview.dialog.BaseDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * 意见反馈
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-3-27
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_OPINION)
public class OpinionActivity extends BaseActivity {
    @BindView(R.id.headView)
    HeadView headView;
    @ViewToast(id = R.id.edt_content, order = 0, emptyToast = "请输入内容")
    @BindView(R.id.edt_content)
    EditText edt_content;

    @BindView(R.id.txt_count)
    TextView txt_count;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @ViewToast(id = R.id.edt_phone, order = 1, emptyToast = "请输入手机号", regex = URLConstant
            .PHONE_MATCHER, regexToast = "手机号码格式不正确，请重新输入")
    @BindView(R.id.edt_phone)
    EditText edt_phone;
    @BindView(R.id.rv_tag)
    RecyclerView rvTag;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.ll_opinion)
    FrameLayout llOpinion;


    private int type = 1;
    private BaseQuickAdapter<String, BaseViewHolder> tagAdapter;

    /**
     * 只能输入中文的判断
     *
     * @param c
     * @return
     */
    private boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock
                .CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock
                .CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock
                .GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 匹配输入
     */
    private void matchEditText() {
        edt_content.addTextChangedListener(new MyTextWatcher(txt_count, 500, edt_content));
        /**
         * EditText只能输入中文
         */
        InputFilter filter2 = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int
                    dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!isChinese(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };
        edt_content.setFilters(new InputFilter[]{filter2, new InputFilter.LengthFilter(500)});
        setNotLine(edt_content);
    }

    /**
     * 设置多行显示
     */
    private void setNotLine(EditText editText) {
        editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editText.setGravity(Gravity.TOP);
        //改变默认的单行模式
        editText.setSingleLine(false);
        //水平滚动设置为False
        editText.setHorizontallyScrolling(false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_opinion;
    }

    @Override
    public void initView() {
        headView.setTitle("意见征集");
        edt_phone.setText(SPUtils.getInstance().getString(SPCommon.PHONE));
        setImageAdapter();
        matchEditText();
        rvTag.setLayoutManager(new GridLayoutManager(this, 4));
        mTvName.setText(ObjectUtils.isNotEmpty(SPUtils.getInstance().getString(SPCommon.NAME)) ?
                SPUtils.getInstance().getString(SPCommon.NAME) : SPUtils.getInstance().getString
                (SPCommon.ACCOUNT));
        ArrayList<String> strings = new ArrayList<>();
        strings.add("服务建议");
        strings.add("体验问题");
        strings.add("功能建议");
        strings.add("其他");
        tagAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_tag5, strings) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_classification, item);
                if (helper.getAdapterPosition() + 1 == type) {
                    helper.itemView.setSelected(true);
                } else {
                    helper.itemView.setSelected(false);
                }
            }
        };

        tagAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                type = position + 1;
                tagAdapter.notifyDataSetChanged();
            }
        });
        rvTag.setAdapter(tagAdapter);
        TakePhoto.initPhotoError();
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> imgList = data.getStringArrayListExtra
                        (MultiImageSelectorActivity.EXTRA_RESULT);
                mImgList.addAll(imgList);
                mAdapter.setList(mImgList);
                mAdapter.notifyDataSetChanged();
            }
        }
    }


    public class MyTextWatcher implements TextWatcher {
        public TextView countView;
        private EditText editText;
        public int count;

        public MyTextWatcher(TextView countView, int count, EditText editText) {
            this.countView = countView;
            this.count = count;
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int len = 0;
            String value = editText.getText().toString();
            if (!TextUtils.isEmpty(value)) {
                len = value.length();
            }

            countView.setText(len + "/500");
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    /**
     * 图片选择的适配器
     */
    CommentWriteAdapter mAdapter;
    /**
     * 图片选择数据集合
     */
    ArrayList<String> mImgList = new ArrayList<>();

    public void setImageAdapter() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 4, GridLayoutManager
                .VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        mAdapter = new CommentWriteAdapter(this, new CommentWriteAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                choicePicture(5 - mImgList.size(), 1);
            }
        });
        mAdapter.setList(mImgList);
        recyclerView.setAdapter(mAdapter);
    }

    private void choicePicture(int num, int model) {
        Intent intent = new Intent(this, MultiImageSelectorActivity.class);
        // 是否显示调用相机拍照
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大图片选择数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, num);
        // 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者 多选/MultiImageSelectorActivity
        // .MODE_MULTI)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, model);
        // 默认选择
      /*  if (imgList != null && imgList.size() > 0) {
            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, imgList);
        }*/
        startActivityForResult(intent, 200);

    }

    @OnClick(R.id.btn_submit)
    public void onclick_submit(View v) {
        if (type <= 0) {
            ToastUtils.showShortCenter("请选择意见类型");
            return;
        }
        if (mImgList.size() == 0) {
            ToastUtils.showShortCenter("请添加图片");
            return;
        }
        String content = edt_content.getText().toString();
        String phone = edt_phone.getText().toString();
        if (ObjectUtils.isNotEmpty(content) && ObjectUtils.isNotEmpty(phone) && RegexUtils
                .isMobileExact(phone)) {
            if (mImgList.size() > 0) {
                LoadingDialog.showDialogForLoading(this);
                FileUpload.uploadFile(this, mImgList, new FileUpload.UploadFileBack() {
                    @Override
                    public void result(String value) {
                        if (!getString(R.string.value_1).equals(value)) {
                            LogUtils.e("请求-->" + value);
                            addOpinion(phone, content, type + "", value);
                        } else {
                            LoadingDialog.cancelDialogForLoading();
                            ToastUtils.showShortCenter("图片上传失败");
                        }
                    }

                    @Override
                    public void resultList(List<String> value) {
                    }
                });
            }
        } else if (ObjectUtils.isEmpty(content)) {
            ToastUtils.showShort("请填写意见内容!");
        } else if (ObjectUtils.isEmpty(phone)) {
            ToastUtils.showShort("请填写手机号码!");
        } else if (!RegexUtils.isMobileExact(phone)) {
            ToastUtils.showShort("请填写正确的手机号码!");
        }
    }


    /**
     * 增加投诉
     */
    public void addOpinion(String phone, String content, String type, String images) {
        RetrofitHelper.getApiService().addOpinion(phone, content, type, images).subscribeOn
                (Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver() {
            @Override
            public void onSuccess(BaseResponse response) {
                if (ObjectUtils.isNotEmpty(response)) {
                    if (response.getCode() == 0) {
                        ToastUtils.showShortCenter("意见反馈成功");
                        finish();
                    } else {
                        ToastUtils.showShortCenter(response.getMessage());
                    }
                } else {
                    ToastUtils.showShortCenter("意见反馈失败，请稍后再试");
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
     * 这个是头像选择
     */
    private BaseDialog mPhotoDialog;
    private TextView mTvPhotoBook;
    private TextView mTvTakePhoto;
    private TextView mTvCancle;
}
