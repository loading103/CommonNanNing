package com.daqsoft.commonnanning.ui.mine.online;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.common.TakePhoto;
import com.daqsoft.commonnanning.http.FileUpload;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.adapter.CommentWriteAdapter;
import com.daqsoft.commonnanning.view.FullyGridLayoutManager;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.net.common.DefaultObserver;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.util.KeyboardUtils;
import com.example.tomasyb.baselib.util.LogUtils;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.LoadingDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
 * ??????????????????
 *
 * @author ??????
 * @version 1.0.0
 * @date 2018.05.07
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_ONLINE_MESSAGE)
public class OnLineMessageActivity extends BaseActivity {


    @BindView(R.id.online_message_title)
    HeadView onlineMessageTitle;
    @BindView(R.id.et_online_message_name)
    EditText etOnlineMessageName;
    @BindView(R.id.et_online_message_sex)
    TextView etOnlineMessageSex;
    @BindView(R.id.et_online_message_code)
    EditText etOnlineMessageCode;
    @BindView(R.id.et_online_message_phone)
    EditText etOnlineMessagePhone;
    @BindView(R.id.et_online_message_email)
    EditText etOnlineMessageEmail;
    @BindView(R.id.et_online_message_address)
    EditText etOnlineMessageAddress;
    @BindView(R.id.tv_title_num)
    TextView tvTitleNum;
    @BindView(R.id.et_online_message_title)
    EditText etOnlineMessageTitle;
    @BindView(R.id.on_line_message_num)
    TextView onLineMessageNum;
    @BindView(R.id.et_online_message_content)
    EditText etOnlineMessageContent;
    @BindView(R.id.gridView_on_line_picture)
    RecyclerView recyclerViewWriteComment;
    @BindView(R.id.on_line_message_save)
    TextView onLineMessageSave;
    @BindView(R.id.iv_on_line_message_list)
    ImageView ivOnLineMessageList;
    @BindView(R.id.activity_on_line_message)
    LinearLayout activityOnLineMessage;
    /**
     * ???????????????
     */
    private OptionsPickerView pvOptions;
    /**
     * ??????????????????
     */
    private List<String> sexList = new ArrayList<>();
    private String[] sexS = null;
    /**
     * ?????????????????????
     */
    private int sex = 0;
    /**
     * ??????
     */
    private String title = "";
    /**
     * ??????
     */
    private String name = "";
    /**
     * ??????
     */
    private String phone = "";
    /**
     * ??????
     */
    private String email = "";
    /**
     * ??????
     */
    private String zipCode = "";
    /**
     * ??????
     */
    private String content = "";
    /**
     * ??????
     */
    private String adddress = "";
    /**
     * ????????????
     */
    private String credentials = "";
    /**
     * ?????????????????????
     */
    private Uri imgUri = null;

    /**
     * ????????????
     */
    private boolean checkSave = true;

    /**
     * ????????????
     */
    int TWO = 2;
    /**
     * ??????
     */
    int ONE = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_on_line_message;
    }

    /**
     * ?????????
     */
    @Override
    public void initView() {
        etOnlineMessagePhone.setText(SPUtils.getInstance().getString(SPCommon.PHONE));
        if (ObjectUtils.isNotEmpty(SPUtils.getInstance().getString(SPCommon.REAL_NAME))) {
            etOnlineMessageName.setText(SPUtils.getInstance().getString(SPCommon.REAL_NAME));
        }
        matchEditText();
        setImageAdapter();
        onlineMessageTitle.setTitle("????????????");
        sexS = getResources().getStringArray(R.array.sex_list);
        etOnlineMessageContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onLineMessageNum.setText(charSequence.length() + "/200");
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() > 200) {
                    editable.delete(200, editable.length());
                }

            }
        });

        etOnlineMessageTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvTitleNum.setText(s.length() + "/40");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        sexList.clear();
        for (String i : sexS) {
            sexList.add(i);
        }
        TakePhoto.initPhotoError();
        initPhoto();
    }

    /**
     * ?????????????????????
     */
    private BaseDialog mPhotoDialog;
    private TextView mTvPhotoBook;
    private TextView mTvTakePhoto;
    private TextView mTvCancle;

    /**
     * ??????
     */
    private void initPhoto() {
        mPhotoDialog = new BaseDialog(this);
        mPhotoDialog.contentView(R.layout.dialog_photo).gravity(Gravity.BOTTOM).layoutParams(new
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.WRAP_CONTENT)).animType(BaseDialog.AnimInType.BOTTOM)
                .canceledOnTouchOutside(true);
        mTvPhotoBook = mPhotoDialog.findViewById(R.id.tv_photobook);
        mTvTakePhoto = mPhotoDialog.findViewById(R.id.tv_takephoto);
        mTvCancle = mPhotoDialog.findViewById(R.id.tv_cancle);
        mTvPhotoBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ????????????
                mPhotoDialog.dismiss();
            }
        });
        mTvTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    imgUri = TakePhoto.takePhoto(OnLineMessageActivity.this, ONE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mPhotoDialog.dismiss();
            }
        });
        mTvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPhotoDialog.dismiss();
            }
        });
    }

    /**
     * ????????????
     */
    private void matchEditText() {

        /**
         * EditText??????????????????
         */
        InputFilter filter2 = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int
                    dstart, int dend) {
                /*for (int i = start; i < end; i++) {
                    if (!isChinese(source.charAt(i))) {
                        return "";
                    }
                }*/
                return null;
            }
        };

        etOnlineMessageName.setFilters(new InputFilter[]{filter2, new InputFilter.LengthFilter
                (10)});
        etOnlineMessageAddress.setFilters(new InputFilter[]{filter2, new InputFilter.LengthFilter
                (20)});
        etOnlineMessageTitle.setFilters(new InputFilter[]{filter2, new InputFilter.LengthFilter
                (40)});
        etOnlineMessageContent.setFilters(new InputFilter[]{filter2, new InputFilter.LengthFilter
                (200)});
        setNotLine(etOnlineMessageTitle);
        setNotLine(etOnlineMessageContent);
    }

    /**
     * ??????????????????
     */
    private void setNotLine(EditText editText) {
        editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editText.setGravity(Gravity.TOP);
        //???????????????????????????
        editText.setSingleLine(false);
        //?????????????????????False
        editText.setHorizontallyScrolling(false);
    }

    /**
     * ???????????????????????????
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

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    @OnClick({R.id.et_online_message_sex, R.id.iv_on_line_message_list, R.id.on_line_message_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_online_message_sex:
                // ????????????
                KeyboardUtils.hideSoftInput(this);
                // ???????????????
                pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView
                        .OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        etOnlineMessageSex.setText(sexList.get(options1));
                        etOnlineMessageSex.setTextColor(getResources().getColor(R.color.black));
                        sex = options1;
                    }
                }).setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView tvSubmit = (TextView) v.findViewById(R.id.choose_date_sure);
                        TextView tvCancel = (TextView) v.findViewById(R.id.choose_date_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvOptions.returnData();
                                pvOptions.dismiss();
                            }
                        });

                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvOptions.dismiss();
                            }
                        });


                    }
                }).isDialog(false).setSelectOptions(sex).build();
                pvOptions.setPicker(sexList);
                pvOptions.show();
                break;
            case R.id.iv_on_line_message_list:
                // ????????????
                ARouter.getInstance().build(Constant.ACTIVITY_ONLINE_MESSAGE_LIST).navigation();
                break;
            case R.id.on_line_message_save:
                // ????????????
                if (checkSave) {
                    saveMsg();
                }
                break;
            default:
                break;
        }
    }

    /**
     * ????????????????????????
     */
    CommentWriteAdapter mAdapter;
    /**
     * ????????????????????????
     */
    ArrayList<String> mImgList = new ArrayList<>();

    public void setImageAdapter() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 4, GridLayoutManager
                .VERTICAL, false);
        recyclerViewWriteComment.setLayoutManager(manager);
        mAdapter = new CommentWriteAdapter(this, new CommentWriteAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                choicePicture(5 - mImgList.size(), 1);
            }
        });
        mAdapter.setList(mImgList);
        recyclerViewWriteComment.setAdapter(mAdapter);
    }

    private void choicePicture(int num, int model) {
        Intent intent = new Intent(this, MultiImageSelectorActivity.class);
        // ??????????????????????????????
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // ????????????????????????
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, num);
        // ???????????? (?????? ??????/MultiImageSelectorActivity.MODE_SINGLE ?????? ??????/MultiImageSelectorActivity
        // .MODE_MULTI)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, model);
        // ????????????
      /*  if (imgList != null && imgList.size() > 0) {
            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, imgList);
        }*/
        startActivityForResult(intent, 200);

    }

    /**
     * ????????????
     */
    public void saveMsg() {
        title = etOnlineMessageTitle.getText().toString().trim();
        name = etOnlineMessageName.getText().toString().trim();
        phone = etOnlineMessagePhone.getText().toString().trim();
        email = etOnlineMessageEmail.getText().toString().trim();
        zipCode = etOnlineMessageCode.getText().toString().trim();
        content = etOnlineMessageContent.getText().toString().trim();
        adddress = etOnlineMessageAddress.getText().toString().trim();
        if (!ObjectUtils.isNotEmpty(name)) {
            ToastUtils.showShortCenter("?????????????????????");
            return;
        }
        if (!ObjectUtils.isNotEmpty(phone)) {
            ToastUtils.showShortCenter("???????????????????????????");
            return;
        }
        if (!ObjectUtils.isNotEmpty(title)) {
            ToastUtils.showShortCenter("?????????????????????");
            return;
        }
        if (!ObjectUtils.isNotEmpty(content)) {
            ToastUtils.showShortCenter("?????????????????????");
            return;
        }
        if (phone.length() != 11) {
            ToastUtils.showShortCenter("??????????????????????????????");
            return;
        }
        LoadingDialog.showDialogForLoading(this, "???????????????~", true);
        if (mImgList.size() > 0) {
            FileUpload.uploadFile(this, mImgList, new FileUpload.UploadFileBack() {
                @Override
                public void result(String value) {
                    if (!getString(R.string.value_1).equals(value)) {
                        LogUtils.e("??????-->" + value);
                        save(value);
                    } else {
                        ToastUtils.showShortCenter("??????????????????");
                    }
                }

                @Override
                public void resultList(List<String> value) {
                }
            });
        } else {
            save("");
        }
        checkSave = false;
    }

    /**
     * ??????
     */
    public void save(String images) {
        HashMap<String, String> map = new HashMap<>();
        map.put("title", title);
        map.put("name", name);
        map.put("phone", phone);
        map.put("content", content);
        if (ObjectUtils.isNotEmpty(email)) {
            map.put("email", email);
        }
        if (ObjectUtils.isNotEmpty(zipCode)) {
            map.put("zipCode", zipCode);
        }
        if (ObjectUtils.isNotEmpty(adddress)) {
            map.put("address", adddress);
        }
        if (ObjectUtils.isNotEmpty(images)) {
            map.put("credentials", images);
        }
        if (ObjectUtils.isNotEmpty(sex + "")) {
            map.put("sex", sex + "");
        }
        RetrofitHelper.getApiService().saveMsg(map).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver() {
            @Override
            public void onSuccess(BaseResponse response) {
                if (ObjectUtils.isNotEmpty(response)) {
                    if (response.getCode() == 0) {
                        ToastUtils.showShortCenter("????????????");
                        etOnlineMessageAddress.setText("");
                        etOnlineMessageName.setText("");
                        etOnlineMessageCode.setText("");
                        etOnlineMessageContent.setText("");
                        etOnlineMessageEmail.setText("");
                        etOnlineMessagePhone.setText("");
                        etOnlineMessageTitle.setText("");
                        etOnlineMessageSex.setText("???");
                        credentials = "";
                        title = "";
                        name = "";
                        phone = "";
                        email = "";
                        zipCode = "";
                        content = "";
                        adddress = "";
                        sex = 0;
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


}
