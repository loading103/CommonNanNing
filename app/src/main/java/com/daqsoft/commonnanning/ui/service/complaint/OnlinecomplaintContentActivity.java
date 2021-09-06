package com.daqsoft.commonnanning.ui.service.complaint;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.base.IApplication;
import com.daqsoft.commonnanning.common.Constant;
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
import com.example.tomasyb.baselib.util.PhoneUtils;
import com.example.tomasyb.baselib.util.RegexUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.LoadingDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import io.agora.yview.dialog.BaseDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * 在线投诉 （输入投诉内容界面）
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-3-23
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_COMPLAINT)
public class OnlinecomplaintContentActivity extends BaseActivity {
    @BindView(R.id.headView)
    HeadView headView;
    @BindView(R.id.edt_name)
    EditText edt_name;
    @BindView(R.id.txt_sex)
    TextView txt_sex;
    @BindView(R.id.edt_to_comlpaint)
    EditText edt_to_comlpaint;
    @BindView(R.id.txt_phone)
    EditText txt_phone;
    @BindView(R.id.edt_title)
    EditText edt_title;
    @BindView(R.id.txt_title_count)
    TextView txt_title_count;
    @BindView(R.id.edt_content)
    EditText edt_content;
    @BindView(R.id.txt_content_count)
    TextView txt_content_count;
    @BindView(R.id.chk_open)
    CheckBox chk_open;
    @BindView(R.id.chk_agree)
    CheckBox chk_agree;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerViewWriteComment;
    @BindView(R.id.txt_address)
    TextView txtAddress;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.complaint_phone)
    TextView complaintPhone;
    @BindView(R.id.fl_complaint)
    LinearLayout flComplaint;

    /**
     * 性别的list集合
     */
    private List<String> sexList = new ArrayList<>();
    /**
     * 性别选择器
     */
    OptionsPickerView pvOptions;
    /**
     * 性别
     */
    private int sex = 0;

    /**
     * 地区编码
     */
    private String region = "";
    /**
     * 省市区选择的序号
     */
    private int provPosition = 0, cityPosition = 0, distPosition = 0;

    /**
     * 图片
     */
    Uri imgUri;

    /**
     * 选择照片
     */
    int ONE = 1;

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

    @Override
    public int getLayoutId() {
        return R.layout.activity_onlinecomplaint_content;
    }

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

    @Override
    public void initView() {
        headView.setTitle("在线投诉");
        matchEditText();
        setImageAdapter();
        headView.setBackListener(new HeadView.OnBackListener() {
            @Override
            public void onBack(View v) {
                dialog.show();
            }
        });
        chk_open.setChecked(true);
        chk_agree.setChecked(true);
        edt_title.addTextChangedListener(new MyTextWatcher(txt_title_count, 40, edt_title));
        edt_content.addTextChangedListener(new MyTextWatcher(txt_content_count, 500, edt_content));
        initPhoto();
        init();
        initDialog();
    }

    /**
     * 匹配输入
     */
    private void matchEditText() {
        InputFilter filter = new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int i, int i1, Spanned spanned, int
                    i2, int i3) {
                String speChat = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\ud83e" +
                        "" + "" + "\udc00-\ud83e\udfff]|[\u2100-\u32ff]|[\u0030-\u007f][\u20d0" +
                        "-\u20ff" + "]|[\u0080-\u00ff]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.find()) return "";
                else return null;
            }
        };
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
        edt_title.setFilters(new InputFilter[]{filter2, new InputFilter.LengthFilter(40)});
        edt_name.setFilters(new InputFilter[]{filter2, new InputFilter.LengthFilter(6)});
        edt_to_comlpaint.setFilters(new InputFilter[]{filter2, new InputFilter.LengthFilter(10)});
        edt_content.setFilters(new InputFilter[]{filter2, new InputFilter.LengthFilter(500)});
        setNotLine(edt_title);
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
    public IBasePresenter initPresenter() {
        return null;
    }

    public void init() {
        sexList.clear();
        String[] sexS = getResources().getStringArray(R.array.sex_list);
        for (String i : sexS) {
            sexList.add(i);
        }
        TakePhoto.initPhotoError();
    }

    /**
     * 这个是头像选择
     */
    private BaseDialog mPhotoDialog;
    private TextView mTvPhotoBook;
    private TextView mTvTakePhoto;
    private TextView mTvCancle;

    /**
     * 拍照
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
                // 调用相册
                // 选择照片

                mPhotoDialog.dismiss();
            }
        });
        mTvTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    imgUri = TakePhoto.takePhoto(OnlinecomplaintContentActivity.this, ONE);
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


    /**
     * 拨打投诉电话
     */
    @SuppressLint("MissingPermission")
    @OnClick(R.id.complaint_phone)
    public void onViewClicked() {
        PhoneUtils.call("12301");
    }


    /**
     * 输入框的监听事件
     */
    public class MyTextWatcher implements TextWatcher {
        public TextView countView;
        public int count;
        private EditText editText;

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
            countView.setText(len + " / " + MyTextWatcher.this.count);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    @OnClick(R.id.txt_sex)
    public void onclick_sex(View v) {
        KeyboardUtils.hideSoftInput(this);
        // 条件选择器
        pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView
                .OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                txt_sex.setText(sexList.get(options1));
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
    }

    /**
     * 发生地选择
     *
     * @param view
     */
    @OnClick(R.id.txt_address)
    public void onclick_address_choose(View view) {
        KeyboardUtils.hideSoftInput(this);
        // 条件选择器
        pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView
                .OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                provPosition = options1;
                cityPosition = options2;
                distPosition = options3;
                if (options1 == 0) {
                    txtAddress.setText("");
                    // 未选择
                    region = "";
                } else {
                    if (options2 == 0) {
                        txtAddress.setText(IApplication.options1Items.get(options1));
                        region = IApplication.locationList.get(options1).getRegion();
                    } else {
                        if (options3 == 0) {
                            txtAddress.setText(IApplication.options1Items.get(options1) + "/" +
                                    IApplication.options2Items.get(options1).get(options2));
                            region = IApplication.locationList.get(options1).getSub().get
                                    (options2).getRegion();
                        } else {
                            txtAddress.setText(IApplication.options1Items.get(options1) + "/" +
                                    IApplication.options2Items.get(options1).get(options2) + "/"
                                    + IApplication.options3Items.get(options1).get(options2).get
                                    (options3));
                            region = IApplication.locationList.get(options1).getSub().get
                                    (options2).getSub().get(options3).getRegion();
                        }
                    }
                }
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
        }).isDialog(false).setSelectOptions(provPosition, cityPosition, distPosition).build();
        pvOptions.setPicker(IApplication.options1Items, IApplication.options2Items, IApplication
                .options3Items);
        pvOptions.show();
    }

    /**
     * 提交投诉
     *
     * @param v
     */
    private String strPhone = "";
    private String strName = "";

    @OnClick(R.id.btn_next)
    public void onclick_next(View v) {
        strName = edt_name.getText().toString().trim();
        String content = edt_content.getText().toString().trim();
        String title = edt_title.getText().toString().trim();
        String comlpaint = edt_to_comlpaint.getText().toString().trim();
        strPhone = txt_phone.getText().toString().trim();

        if (ObjectUtils.isNotEmpty(comlpaint) && ObjectUtils.isNotEmpty(title) && ObjectUtils
                .isNotEmpty(content) && chk_agree.isChecked() && RegexUtils.isMobileExact
                (strPhone)) {
            LoadingDialog.showDialogForLoading(this);
            if (mImgList.size() > 0) {
                FileUpload.uploadFile(this, mImgList, new FileUpload.UploadFileBack() {
                    @Override
                    public void result(String value) {
                        if (!getString(R.string.value_1).equals(value)) {
                            LogUtils.e("请求-->" + value);
                            addComplaint(comlpaint, title, content, value);
                        } else {
                            ToastUtils.showShortCenter("图片上传失败");
                        }
                    }

                    @Override
                    public void resultList(List<String> value) {
                    }
                });
            } else {
                addComplaint(comlpaint, title, content, "");
            }
        } else if (!ObjectUtils.isNotEmpty(comlpaint)) {
            ToastUtils.showShortCenter("被投诉方不能为空!");
        } else if (!ObjectUtils.isNotEmpty(title)) {
            ToastUtils.showShortCenter("标题不能为空!");
        } else if (!ObjectUtils.isNotEmpty(content)) {
            ToastUtils.showShortCenter("投诉事由不能为空!");
        } else if (!chk_agree.isChecked()) {
            ToastUtils.showShortCenter("请先同意协议内容!");
        } else if (!RegexUtils.isMobileExact(strPhone)) {
            ToastUtils.showShortCenter("请输入正确的手机号!");
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dialog.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 增加投诉
     */
    public void addComplaint(String complaint, String title, String content, String image) {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", strName);
        map.put("sex", sex + "");
        map.put("phone", strPhone);
        map.put("unit", complaint);
        map.put("title", title);
        map.put("content", content);
        map.put("region", region);
        map.put("open", (chk_open.isChecked() ? 1 : 0) + "");
        if (ObjectUtils.isNotEmpty(image)) {
            map.put("credentials", image);
        }
        RetrofitHelper.getApiService().addComplaint(map).subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver() {
            @Override
            public void onSuccess(BaseResponse response) {
                if (ObjectUtils.isNotEmpty(response)) {
                    if (response.getCode() == 0) {
                        ToastUtils.showShortCenter("投诉成功");
                        finish();
                    } else {
                        ToastUtils.showShortCenter(response.getMessage());
                    }
                } else {
                    ToastUtils.showShortCenter("投诉失败，请稍后再试");
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                LoadingDialog.cancelDialogForLoading();
            }
        });
    }

    private BaseDialog dialog;

    /**
     * 退出
     */
    private void initDialog() {
        dialog = new BaseDialog(this);
        dialog.contentView(R.layout.dialog_ensure_layout).canceledOnTouchOutside(true);
        TextView mTitle = dialog.findViewById(R.id.tv_title);
        mTitle.setText("您确定要离开此页面吗?");
        dialog.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

}
