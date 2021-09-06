package com.daqsoft.commonnanning.ui.mine;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bumptech.glide.request.RequestOptions;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.common.TakePhoto;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.UpImgEntity;
import com.daqsoft.commonnanning.utils.ChoosePictureWindow;
import com.daqsoft.commonnanning.utils.DateUtil;
import com.example.tomasyb.baselib.base.glide.GlideApp;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.net.common.DefaultObserver;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.util.LogUtils;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.util.Utils;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.LoadingDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.agora.yview.dialog.BaseDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 基本资料
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018.05.07
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_MINE_INFO)
public class MineInfoActivity extends BaseActivity {

    @BindView(R.id.headView)
    HeadView headView;
    @BindView(R.id.mine_info_img)
    ImageView mineInfoImg;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.mine_info_ll_head)
    LinearLayout mineInfoLlHead;
    @BindView(R.id.mine_info_nick_name)
    TextView mineInfoNickName;
    @BindView(R.id.mine_info_ll_nick_name)
    LinearLayout mineInfoLlNickName;
    @BindView(R.id.mine_info_name)
    TextView mineInfoName;
    @BindView(R.id.mine_info_ll_name)
    LinearLayout mineInfoLlName;
    @BindView(R.id.mine_info_birthday)
    TextView mineInfoBirthday;
    @BindView(R.id.mine_info_ll_birthday)
    LinearLayout mineInfoLlBirthday;
    @BindView(R.id.mine_info_sex)
    TextView mineInfoSex;
    @BindView(R.id.mine_info_ll_sex)
    LinearLayout mineInfoLlSex;
    @BindView(R.id.mine_info_save)
    TextView mineInfoSave;
    @BindView(R.id.activity_mine_info)
    LinearLayout activityMineInfo;
    /**
     * 头像
     */
    private String img = "";
    /**
     * 昵称
     */
    private String nickName = "";
    /**
     * 真实姓名
     */
    private String name = "";
    /**
     * 生日
     */
    private String birthday = "";
    /**
     * 性别
     */
    private int sex = 0;
    /**
     * 时间筛选框
     */
    TimePickerView pvTime;
    /**
     * 条件选择器
     */
    OptionsPickerView pvOptions;
    /**
     * 性别list
     */
    private List<String> sexList = new ArrayList<>();
    /**
     * 性别数组
     */
    private String[] sexS = null;
    /**
     * 生日
     */
    private String[] birthdays = null;
    /**
     * 拍照图片的uri
     */
    private Uri imgUri = null;
    /**
     * 图片地址
     */
    private String imgPath = "";


    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_info;
    }

    /**
     * 初始化
     */
    @Override
    public void initView() {
        TakePhoto.initPhotoError();
        headView.setTitle("基本资料");
        Intent intent = getIntent();
        img = intent.getStringExtra("img");
        name = intent.getStringExtra("name");
        nickName = intent.getStringExtra("nickname");
        birthday = intent.getStringExtra("birthday");
        sex = intent.getIntExtra("sex", 0);
        GlideApp.with(this)
                .load(img)
                .apply(RequestOptions.bitmapTransform(new CropCircleTransformation()))
                .error(R.mipmap.my_avatar_default)
                .placeholder(R.mipmap.my_avatar_default)
                .into(mineInfoImg);
        mineInfoNickName.setText(Utils.getSixStr(nickName));
        mineInfoName.setText(Utils.getSixStr(name));
        mineInfoBirthday.setText(birthday);
        mineInfoSex.setText(sex == 0 ? "男" : sex == 1 ? "女" : "保密");
        sexS = getResources().getStringArray(R.array.sex_list);
        for (String i : sexS) {
            sexList.add(i);
        }
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    @OnClick({R.id.mine_info_ll_head, R.id.mine_info_ll_birthday, R
            .id.mine_info_ll_sex, R.id.mine_info_save,
            R.id.mine_info_nick_name, R.id.mine_info_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_info_ll_head:
                takePhoto();
                break;
            case R.id.mine_info_ll_birthday:
                // 生日
                // 时间选择器
                Calendar selectedDate = Calendar.getInstance();
                // 正确设置方式 原因：注意事项有说明
                if (ObjectUtils.isNotEmpty(birthday)) {
                    birthdays = birthday.split("-");
                }
                if (birthdays != null && birthdays.length == 3) {
                    selectedDate.set(Integer.parseInt(birthdays[0]), Integer.parseInt
                            (birthdays[1]) - 1, Integer.parseInt(birthdays[2]));
                }
                pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener
                        () {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        // 选中事件回调
                        birthday = DateUtil.convertDateToStringOnlyYMD(date);
                        mineInfoBirthday.setText(birthday);
                    }
                })
                        .setDate(selectedDate)
                        .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                            @Override
                            public void customLayout(View v) {
                                TextView tvSubmit = (TextView) v.findViewById(R.id
                                        .choose_date_sure);
                                TextView tvCancel = (TextView) v.findViewById(R.id
                                        .choose_date_cancel);
                                tvSubmit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        pvTime.returnData();
                                        pvTime.dismiss();
                                    }
                                });
                                tvCancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        pvTime.dismiss();
                                    }
                                });
                            }
                        })
                        .setContentSize(18)
                        .setType(new boolean[]{true, true, true, false, false, false})
                        .setLabel("年", "月", "日", "时", "分", "秒")
                        .setLineSpacingMultiplier(1.2f)
                        .setTextXOffset(0, 0, 0, 40, 0, -40)
                        // 点击屏幕，点在控件外部范围时，是否取消显示
                        .setOutSideCancelable(false)
                        // 是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .isCenterLabel(false)
                        .setDividerColor(0xFF24AD9D)
                        .build();
                pvTime.show();
                break;
            case R.id.mine_info_ll_sex:
                // 条件选择器
                pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView
                        .OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        LogUtils.e(options1 + "--" + options2 + "--" + options3);
                        mineInfoSex.setText(sexList.get(options1));
                        sex = options1;
                    }
                })
                        .setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
                            @Override
                            public void customLayout(View v) {
                                TextView tvSubmit = (TextView) v.findViewById(R.id
                                        .choose_date_sure);
                                TextView tvCancel = (TextView) v.findViewById(R.id
                                        .choose_date_cancel);
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
                        })
                        .isDialog(false)
                        .setSelectOptions(sex)
                        .build();
                pvOptions.setPicker(sexList);
                pvOptions.show();
                break;
            case R.id.mine_info_nick_name:
                // 修改昵称
                showInputDialog(this, 0, "修改昵称", nickName, 6);
                break;
            case R.id.mine_info_name:
                // 修改真实姓名
                showInputDialog(this, 1, "修改真实姓名", name, 6);
                break;
            case R.id.mine_info_save:
                updateUserInfo();
                break;
            default:
                break;
        }
    }


    /**
     * 更新数据
     */
    public void updateUserInfo() {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", SPUtils.getInstance().getString(SPCommon.TOKEN));
        if (ObjectUtils.isNotEmpty(imgPath)) {
            map.put("head", imgPath);
        }
        if (ObjectUtils.isNotEmpty("name")) {
            map.put("name", name);
        }
        if (ObjectUtils.isNotEmpty(sex + "")) {
            map.put("gender", sex + "");
        }
        if (ObjectUtils.isNotEmpty(birthday)) {
            map.put("birthday", birthday);
        }
        if (ObjectUtils.isNotEmpty(nickName)) {
            map.put("nickname", nickName);
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


    /**
     * 弹出修改框，修改昵称
     *
     * @param activity
     * @param type     0:修改昵称，1修改姓名
     * @param title    标题
     * @param input    输入默认值
     */
    public void showInputDialog(Activity activity, final int type, String title, String input,
                                final int length) {
        // 修改昵称
        final BaseDialog dialog = new BaseDialog(activity);
        dialog.contentView(R.layout.include_input_dialog)
                .canceledOnTouchOutside(true).show();
        TextView tvTitle = dialog.findViewById(R.id.input_dialog_title);
        tvTitle.setText(title);
        final EditText etName = dialog.findViewById(R.id.input_dialog_content);
        // 设定最大长度
        etName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
        if (ObjectUtils.isNotEmpty(input)) {
            etName.setText(input);
        }
        // 取消
        dialog.findViewById(R.id.input_dialog_cancel).setOnClickListener(new View.OnClickListener
                () {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        // 确定
        dialog.findViewById(R.id.input_dialog_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ObjectUtils.isNotEmpty(etName.getText().toString().trim())) {
                    if (type == 0) {
                        nickName = etName.getText().toString();
                        mineInfoNickName.setText(nickName);
                    } else if (type == 1) {
                        name = etName.getText().toString();
                        mineInfoName.setText(name);
                    }
                } else {
                    ToastUtils.showShortCenter("请输入相关信息");
                }
            }
        });
    }

    /**
     * 弹出相册和拍照的选择popupwindow
     */
    public void takePhoto() {
        ChoosePictureWindow.takePhoto(this, activityMineInfo,
                new ChoosePictureWindow.DataCallBack() {
                    @Override
                    public void dataCallBack(int type) {
                        // 拍照
                        if (type == 2) {
                            try {
                                imgUri = TakePhoto.takePhoto(MineInfoActivity.this, 0);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else if (type == 1) {
                            // 选择相册
                            Intent intent = new Intent(Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, 1);
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拍照结果返回
        if (requestCode == 0 && resultCode == RESULT_OK) {
            if (ObjectUtils.isNotEmpty(imgUri)) {
                if (android.os.Build.VERSION.SDK_INT < 24) {
                    uploadImg(imgUri.getPath());
                } else {
                    String[] filePathColumns = {MediaStore.Images.Media.DATA};
                    Cursor c = getContentResolver().query(imgUri, filePathColumns,
                            null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePathColumns[0]);
                    String imagePath = c.getString(columnIndex);
                    uploadImg(imagePath);
                }
            }
        } else if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            uploadImg(imagePath);
        }
    }

    /**
     * 图片上传
     */
    public void uploadImg(String image) {
        LoadingDialog.showDialogForLoading(this);
        File file = new File(image);
        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Builder builder =
                new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("path",
                        this.getResources().getString(R.string.app_name)).addFormDataPart("key",
                        Constant.OSS_KEY).addFormDataPart("Filedata", file.getName(), fileBody);
        List<MultipartBody.Part> parts = builder.build().parts();
        RetrofitHelper.getHttpApiServiceFileUpload()
                .upImg(parts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UpImgEntity>() {
                    @Override
                    public void accept(UpImgEntity upImgEntity) throws Exception {
                        LoadingDialog.cancelDialogForLoading();
                        if (ObjectUtils.isNotEmpty(upImgEntity.getFileUrl())) {
                            imgPath = upImgEntity.getFileUrl();
                            GlideApp.with(MineInfoActivity.this).load(imgPath)
                                    .apply(RequestOptions.bitmapTransform(new
                                            CropCircleTransformation()))
                                    .error(R.mipmap.my_avatar_default)
                                    .placeholder(R.mipmap.my_avatar_default)
                                    .into(mineInfoImg);
                            ToastUtils.showShortCenter("文件上传成功");
                        } else {
                            ToastUtils.showShortCenter("文件上传失败");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showShortCenter("文件上传失败");
                        LoadingDialog.cancelDialogForLoading();
                    }
                });
    }


}
