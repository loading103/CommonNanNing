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
 * ????????????
 *
 * @author ??????
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
     * ??????
     */
    private String img = "";
    /**
     * ??????
     */
    private String nickName = "";
    /**
     * ????????????
     */
    private String name = "";
    /**
     * ??????
     */
    private String birthday = "";
    /**
     * ??????
     */
    private int sex = 0;
    /**
     * ???????????????
     */
    TimePickerView pvTime;
    /**
     * ???????????????
     */
    OptionsPickerView pvOptions;
    /**
     * ??????list
     */
    private List<String> sexList = new ArrayList<>();
    /**
     * ????????????
     */
    private String[] sexS = null;
    /**
     * ??????
     */
    private String[] birthdays = null;
    /**
     * ???????????????uri
     */
    private Uri imgUri = null;
    /**
     * ????????????
     */
    private String imgPath = "";


    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_info;
    }

    /**
     * ?????????
     */
    @Override
    public void initView() {
        TakePhoto.initPhotoError();
        headView.setTitle("????????????");
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
        mineInfoSex.setText(sex == 0 ? "???" : sex == 1 ? "???" : "??????");
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
                // ??????
                // ???????????????
                Calendar selectedDate = Calendar.getInstance();
                // ?????????????????? ??????????????????????????????
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
                        // ??????????????????
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
                        .setLabel("???", "???", "???", "???", "???", "???")
                        .setLineSpacingMultiplier(1.2f)
                        .setTextXOffset(0, 0, 0, 40, 0, -40)
                        // ???????????????????????????????????????????????????????????????
                        .setOutSideCancelable(false)
                        // ?????????????????????????????????label?????????false?????????item???????????????label???
                        .isCenterLabel(false)
                        .setDividerColor(0xFF24AD9D)
                        .build();
                pvTime.show();
                break;
            case R.id.mine_info_ll_sex:
                // ???????????????
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
                // ????????????
                showInputDialog(this, 0, "????????????", nickName, 6);
                break;
            case R.id.mine_info_name:
                // ??????????????????
                showInputDialog(this, 1, "??????????????????", name, 6);
                break;
            case R.id.mine_info_save:
                updateUserInfo();
                break;
            default:
                break;
        }
    }


    /**
     * ????????????
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
                                ToastUtils.showShortCenter("????????????");
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
     * ??????????????????????????????
     *
     * @param activity
     * @param type     0:???????????????1????????????
     * @param title    ??????
     * @param input    ???????????????
     */
    public void showInputDialog(Activity activity, final int type, String title, String input,
                                final int length) {
        // ????????????
        final BaseDialog dialog = new BaseDialog(activity);
        dialog.contentView(R.layout.include_input_dialog)
                .canceledOnTouchOutside(true).show();
        TextView tvTitle = dialog.findViewById(R.id.input_dialog_title);
        tvTitle.setText(title);
        final EditText etName = dialog.findViewById(R.id.input_dialog_content);
        // ??????????????????
        etName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
        if (ObjectUtils.isNotEmpty(input)) {
            etName.setText(input);
        }
        // ??????
        dialog.findViewById(R.id.input_dialog_cancel).setOnClickListener(new View.OnClickListener
                () {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        // ??????
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
                    ToastUtils.showShortCenter("?????????????????????");
                }
            }
        });
    }

    /**
     * ??????????????????????????????popupwindow
     */
    public void takePhoto() {
        ChoosePictureWindow.takePhoto(this, activityMineInfo,
                new ChoosePictureWindow.DataCallBack() {
                    @Override
                    public void dataCallBack(int type) {
                        // ??????
                        if (type == 2) {
                            try {
                                imgUri = TakePhoto.takePhoto(MineInfoActivity.this, 0);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else if (type == 1) {
                            // ????????????
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
        // ??????????????????
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
     * ????????????
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
                            ToastUtils.showShortCenter("??????????????????");
                        } else {
                            ToastUtils.showShortCenter("??????????????????");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showShortCenter("??????????????????");
                        LoadingDialog.cancelDialogForLoading();
                    }
                });
    }


}
