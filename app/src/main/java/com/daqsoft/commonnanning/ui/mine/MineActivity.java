package com.daqsoft.commonnanning.ui.mine;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bumptech.glide.request.RequestOptions;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.base.IApplication;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.common.TakePhoto;
import com.daqsoft.commonnanning.http.HttpApiService;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.LocationEntity;
import com.daqsoft.commonnanning.ui.entity.UpImgEntity;
import com.daqsoft.commonnanning.ui.entity.UserInfoEntity;
import com.daqsoft.commonnanning.utils.DateUtil;
import com.example.tomasyb.baselib.base.glide.GlideApp;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.net.common.DefaultObserver;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
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
@Route(path = Constant.ACTIVITY_MINE)
public class MineActivity extends BaseActivity {


    @BindView(R.id.headView)
    HeadView headView;
    @BindView(R.id.mine_img)
    ImageView mineImg;
    @BindView(R.id.mine_base_info)
    LinearLayout mineBaseInfo;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.ll_nick_name)
    LinearLayout llNickName;
    @BindView(R.id.tv_real_name)
    TextView tvRealName;
    @BindView(R.id.ll_real_name)
    LinearLayout llRealName;
    @BindView(R.id.tv_phone_num)
    TextView tvPhoneNum;
    @BindView(R.id.ll_phone_num)
    LinearLayout llPhoneNum;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.ll_birthday)
    LinearLayout llBirthday;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.ll_gender)
    LinearLayout llGender;
    @BindView(R.id.tv_local_city)
    TextView tvLocalCity;
    @BindView(R.id.ll_local_city)
    LinearLayout llLocalCity;
    @BindView(R.id.tv_usual_city)
    TextView tvUsualCity;
    @BindView(R.id.ll_usual_city)
    LinearLayout llUsualCity;
    @BindView(R.id.mine_edit_pwd)
    TextView mineEditPwd;
    @BindView(R.id.activity_mine)
    LinearLayout activityMine;
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
     * ????????????
     */
    /**
     * ??????????????????
     */
    private String provRegion = "";
    /**
     * ??????????????????
     */
    private String cityRegion = "";
    /**
     * ??????????????????
     */
    private String distRegion = "";
    /**
     * ???????????????????????????
     */
    private int provPosition = 0, cityPosition = 0, distPosition = 0;
    /**
     * ???????????????
     */
    /**
     * ??????????????????
     */
    private String provRegionAlways = "";
    /**
     * ??????????????????
     */
    private String cityRegionAlways = "";
    /**
     * ??????????????????
     */
    private String distRegionAlways = "";
    /**
     * ??????????????????????????????
     */
    private int provPositionAlways = 0, cityPositionAlways = 0, distPositionAlways = 0;
    /**
     * ????????????
     */
    private String[] birthdays = null;
    /**
     * ???????????????
     */
    TimePickerView pvTime;
    /**
     * ????????????
     */
    private List<String> sexList = new ArrayList<>();
    /**
     * ?????????
     */
    private String locationcity = "";
    /**
     * ???????????????uri
     */
    private Uri imgUri = null;
    /**
     * ?????????
     */
    private String startcity = "";
    /**
     * ???????????????
     */
    private String[] sexS = null;

    /**
     * ???????????????
     */
    OptionsPickerView pvOptions;
    /**
     * ?????????????????????
     */
    private BaseDialog mPhotoDialog;
    private TextView mTvPhotoBook;
    private TextView mTvTakePhoto;
    private TextView mTvCancle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine;
    }


    /**
     * ?????????
     */
    @Override
    public void initView() {
        headView.setTitle("????????????");
        TakePhoto.initPhotoError();
        sexS = getResources().getStringArray(R.array.sex_list);
        for (String i : sexS) {
            sexList.add(i);
        }
        getUserInfo();
        initPhoto();
    }


    @Override
    public IBasePresenter initPresenter() {
        return null;
    }


    /**
     * ????????????????????????
     */
    public void getUserInfo() {
        LoadingDialog.showDialogForLoading(this);
        RetrofitHelper.getApiService().getUserInfo().subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver<UserInfoEntity>() {
            @Override
            public void onSuccess(BaseResponse<UserInfoEntity> response) {
                if (ObjectUtils.isNotEmpty(response)) {
                    if (response.getCode() == 0) {
                        UserInfoEntity user = response.getData();
                        img = user.getHead();
                        nickName = user.getNickname();
                        name = user.getName();
                        birthday = user.getBirthday();
                        sex = user.getGender();
                        tvPhoneNum.setText(user.getPhone());
                        tvNickname.setText(nickName);
                        GlideApp.with(MineActivity.this).load(img).apply(RequestOptions.bitmapTransform(new CropCircleTransformation())).error(R.mipmap.my_avatar_default).placeholder(R.mipmap.my_avatar_default).into(mineImg);
                        SPUtils.getInstance().put(SPCommon.QQ_OPEN_ID, user.getQqid());
                        SPUtils.getInstance().put(SPCommon.WX_UNION_ID, user.getUnionid());
                        SPUtils.getInstance().put(SPCommon.NAME, user.getNickname());
                        SPUtils.getInstance().put(SPCommon.HEAD_IMG, user.getHead());
                        SPUtils.getInstance().put(SPCommon.REAL_NAME, user.getName());
                        tvRealName.setText(name);
                        if (ObjectUtils.isNotEmpty(user.getBirthday())) {
                            tvBirthday.setText(user.getBirthday());
                        }
                        if (user.getGender() == 0) {
                            // ???
                            tvGender.setText("???");
                        } else if (user.getGender() == 1) {
                            // ???
                            tvGender.setText("???");
                        } else {
                            tvGender.setText("??????");
                        }
                        // ????????????
                        UserInfoEntity.CityJson startCityJson = user.getStartCityJson();
                        if (ObjectUtils.isNotEmpty(startCityJson)) {
                            String city =
                                    startCityJson.getProvRegionName() + startCityJson.getCityRegionName() + startCityJson.getDistRegionName();
                            if (ObjectUtils.isNotEmpty(city)) {
                                tvUsualCity.setText(city);
                            } else {
                                tvUsualCity.setText("?????????");
                            }

                            provRegionAlways = startCityJson.getProvRegion();
                            cityRegionAlways = startCityJson.getCityRegion();
                            distRegionAlways = startCityJson.getDistRegion();
                        }
                        // ????????????
                        UserInfoEntity.CityJson locationCityJson = user.getLocationCityJson();
                        if (ObjectUtils.isNotEmpty(locationCityJson)) {
                            String city =
                                    locationCityJson.getProvRegionName() + locationCityJson.getCityRegionName() + locationCityJson.getDistRegionName();
                            if (ObjectUtils.isNotEmpty(city)) {
                                tvLocalCity.setText(city);
                            } else {
                                tvLocalCity.setText("?????????");
                            }
                            provRegion = locationCityJson.getProvRegion();
                            cityRegion = locationCityJson.getCityRegion();
                            distRegion = locationCityJson.getDistRegion();
                        }
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


    @OnClick({R.id.ll_local_city, R.id.ll_usual_city, R.id.mine_edit_pwd, R.id.mine_base_info,
            R.id.mine_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_local_city:
                //  ????????????
                if (ObjectUtils.isNotEmpty(provRegion)) {
                    for (int i = 0; i < IApplication.locationList.size(); i++) {
                        LocationEntity locationEntity = IApplication.locationList.get(i);
                        if (locationEntity.getRegion().equals(provRegion)) {
                            provPosition = i;
                            if (ObjectUtils.isNotEmpty(cityRegion)) {
                                for (int j = 0; j < locationEntity.getSub().size(); j++) {
                                    LocationEntity.SubBeanX subBeanX =
                                            locationEntity.getSub().get(j);
                                    if (subBeanX.getRegion().equals(cityRegion)) {
                                        cityPosition = j;
                                        if (ObjectUtils.isNotEmpty(distRegion)) {
                                            for (int m = 0; m < subBeanX.getSub().size(); m++) {
                                                LocationEntity.SubBeanX.SubBean subBean =
                                                        subBeanX.getSub().get(m);
                                                if (distRegion.equals(subBean.getRegion())) {
                                                    distPosition = m;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                // ???????????????
                pvOptions = new OptionsPickerView.Builder(this,
                        new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        provPosition = options1;
                        cityPosition = options2;
                        distPosition = options3;
                        if (options1 == 0) {
                            tvLocalCity.setText("");
                            // ?????????
                            locationcity = "";
                        } else {
                            if (options2 == 0) {
                                tvLocalCity.setText(IApplication.options1Items.get(options1));
                                locationcity = IApplication.locationList.get(options1).getRegion();
                                updateUserInfo();
                            } else {
                                if (options3 == 0) {
                                    tvLocalCity.setText(IApplication.options1Items.get(options1) + "/" + IApplication.options2Items.get(options1).get(options2));
                                    locationcity =
                                            IApplication.locationList.get(options1).getSub().get(options2).getRegion();
                                    updateUserInfo();
                                } else {
                                    tvLocalCity.setText(IApplication.options1Items.get(options1) + "/" + IApplication.options2Items.get(options1).get(options2) + "/" + IApplication.options3Items.get(options1).get(options2).get(options3));
                                    locationcity =
                                            IApplication.locationList.get(options1).getSub().get(options2).getSub().get(options3).getRegion();
                                    updateUserInfo();
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
                pvOptions.setPicker(IApplication.options1Items, IApplication.options2Items,
                        IApplication.options3Items);
                pvOptions.show();
                break;
            case R.id.ll_usual_city:
                // ???????????????
                if (ObjectUtils.isNotEmpty(provRegionAlways)) {
                    for (int i = 0; i < IApplication.locationList.size(); i++) {
                        LocationEntity locationEntity = IApplication.locationList.get(i);
                        if (locationEntity.getRegion().equals(provRegionAlways)) {
                            provPositionAlways = i;
                            if (ObjectUtils.isNotEmpty(cityRegionAlways)) {
                                for (int j = 0; j < locationEntity.getSub().size(); j++) {
                                    LocationEntity.SubBeanX subBeanX =
                                            locationEntity.getSub().get(j);
                                    if (subBeanX.getRegion().equals(cityRegionAlways)) {
                                        cityPositionAlways = j;
                                        if (ObjectUtils.isNotEmpty(distRegionAlways)) {
                                            for (int m = 0; m < subBeanX.getSub().size(); m++) {
                                                LocationEntity.SubBeanX.SubBean subBean =
                                                        subBeanX.getSub().get(m);
                                                if (distRegionAlways.equals(subBean.getRegion())) {
                                                    distPositionAlways = m;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                pvOptions = new OptionsPickerView.Builder(this,
                        new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        provPositionAlways = options1;
                        cityPositionAlways = options2;
                        distPositionAlways = options3;
                        if (options1 == 0) {
                            tvUsualCity.setText("");
                            // ?????????
                            startcity = "";
                        } else {
                            if (options2 == 0) {
                                tvUsualCity.setText(IApplication.options1Items.get(options1));
                                startcity = IApplication.locationList.get(options1).getRegion();
                                updateUserInfo();
                            } else {
                                if (options3 == 0) {
                                    tvUsualCity.setText(IApplication.options1Items.get(options1) + "/" + IApplication.options2Items.get(options1).get(options2));
                                    startcity =
                                            IApplication.locationList.get(options1).getSub().get(options2).getRegion();
                                    updateUserInfo();
                                } else {
                                    tvUsualCity.setText(IApplication.options1Items.get(options1) + "/" + IApplication.options2Items.get(options1).get(options2) + "/" + IApplication.options3Items.get(options1).get(options2).get(options3));
                                    startcity = IApplication.locationList.get(options1).
                                            getSub().get(options2).getSub().get(options3).getRegion();
                                    updateUserInfo();
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
                }).isDialog(false).setSelectOptions(provPositionAlways, cityPositionAlways,
                        distPositionAlways).build();
                pvOptions.setPicker(IApplication.options1Items, IApplication.options2Items,
                        IApplication.options3Items);
                pvOptions.show();
                break;
            case R.id.mine_edit_pwd:
                // ????????????
                ARouter.getInstance().build(Constant.ACTIVITY_UPDATE_PWD).navigation();
                break;
            case R.id.mine_base_info:
                // ????????????
                mPhotoDialog.show();
                break;


            case R.id.mine_exit:
                // ????????????
                final BaseDialog dialog = new BaseDialog(this);
                dialog.setContentView(R.layout.z_dialog_tip);
                dialog.setCancelable(true);
                dialog.show();
                TextView tvTitle = dialog.findViewById(R.id.z_tip_tv_dialog_title);
                tvTitle.setText("????????????");
                TextView tvContent = dialog.findViewById(R.id.z_tip_tv_dialog_description);
                tvContent.setText("???????????????????????????");
                dialog.findViewById(R.id.z_tip_btn_dialog_sure).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SPUtils.getInstance().put(SPCommon.TOKEN, "");
                        SPUtils.getInstance().put(SPCommon.NAME, "");
                        SPUtils.getInstance().put(SPCommon.ID, "");
                        SPUtils.getInstance().put(SPCommon.ACCOUNT, "");
                        SPUtils.getInstance().put(SPCommon.HEAD_IMG, "");
                        SPUtils.getInstance().put(SPCommon.PWD, "");
                        SPUtils.getInstance().put(SPCommon.UC_ID, "");
                        SPUtils.getInstance().put(SPCommon.UC_TOKEN, "");
                        HttpApiService.REQUESTMAP.put("token", "");
                        ARouter.getInstance().build(Constant.ACTIVITY_LOGIN).navigation();
                        finish();
                        dialog.dismiss();
                    }
                });
                dialog.findViewById(R.id.z_tip_btn_dialog_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                break;
            default:
                break;
        }
    }


    /**
     * ????????????
     */
    @OnClick(R.id.tv_nickname)
    public void nickNameEdit() {
        Intent intent = new Intent(MineActivity.this, ChangeNameActivity.class);
        intent.putExtra("name", tvNickname.getText().toString());
        intent.putExtra("type", 0);
        startActivityForResult(intent, 2);
    }

    /**
     * ??????
     */
    @OnClick(R.id.ll_birthday)
    public void birthdayChoose() {
        // ???????????????
        Calendar selectedDate = Calendar.getInstance();
        // ?????????????????? ??????????????????????????????
        if (ObjectUtils.isNotEmpty(birthday)) {
            birthdays = birthday.split("-");
        }
        if (birthdays != null && birthdays.length == 3) {
            selectedDate.set(Integer.parseInt(birthdays[0]), Integer.parseInt(birthdays[1]) - 1,
                    Integer.parseInt(birthdays[2]));
        }
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                // ??????????????????
                birthday = DateUtil.convertDateToStringOnlyYMD(date);
                tvBirthday.setText(birthday);
                updateUserInfo();
            }
        }).setDate(selectedDate).setLayoutRes(R.layout.pickerview_custom_time,
                new CustomListener() {

            @Override
            public void customLayout(View v) {
                TextView tvSubmit = (TextView) v.findViewById(R.id.choose_date_sure);
                TextView tvCancel = (TextView) v.findViewById(R.id.choose_date_cancel);
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
        }).setContentSize(18).setType(new boolean[]{true, true, true, false, false, false}).setLabel("???", "???", "???", "???", "???", "???").setLineSpacingMultiplier(1.2f).setTextXOffset(0, 0, 0, 40, 0, -40)
                // ???????????????????????????????????????????????????????????????
                .setOutSideCancelable(false)
                // ?????????????????????????????????label?????????false?????????item???????????????label???
                .isCenterLabel(false).setDividerColor(0xFF24AD9D).build();
        pvTime.show();
    }

    @OnClick(R.id.ll_gender)
    public void genderChoose() {

        // ???????????????
        pvOptions = new OptionsPickerView.Builder(this,
                new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tvGender.setText(sexList.get(options1));
                sex = options1;
                updateUserInfo();
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
     * ??????????????????????????????
     */
    private String mImagePath;
    /**
     * ????????????????????????
     */
    public final static int SELECT_IMAGE_RESULT_CODE = 200;

    /**
     * ??????
     */
    private void initPhoto() {
        mPhotoDialog = new BaseDialog(this);
        mPhotoDialog.contentView(R.layout.dialog_photo).gravity(Gravity.BOTTOM).layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)).animType(BaseDialog.AnimInType.BOTTOM).canceledOnTouchOutside(true);
        mTvPhotoBook = mPhotoDialog.findViewById(R.id.tv_photobook);
        mTvTakePhoto = mPhotoDialog.findViewById(R.id.tv_takephoto);
        mTvCancle = mPhotoDialog.findViewById(R.id.tv_cancle);
        mTvPhotoBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ????????????
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
                mPhotoDialog.dismiss();
            }
        });
        mTvTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    imgUri = TakePhoto.takePhoto(MineActivity.this, 0);
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
        if (requestCode == 0 && resultCode == RESULT_OK) {
            if (ObjectUtils.isNotEmpty(imgUri)) {
                if (android.os.Build.VERSION.SDK_INT < 24) {
                    uploadImg(imgUri.getPath());
                } else {
                    String[] filePathColumns = {MediaStore.Images.Media.DATA};
                    Cursor c = getContentResolver().query(imgUri, filePathColumns, null, null,
                            null);
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
        } else if (requestCode == 2 && resultCode == 2) {
            tvNickname.setText(data.getStringExtra("name"));
            SPUtils.getInstance().put(SPCommon.NAME, data.getStringExtra("name"));
        } else if (requestCode == 3 && resultCode == 3) {
            tvRealName.setText(data.getStringExtra("name"));
        } else if (requestCode == 4 && resultCode == 4) {
            tvPhoneNum.setText(data.getStringExtra("phone"));
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
        RetrofitHelper.getHttpApiServiceFileUpload().upImg(parts).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<UpImgEntity>() {
            @Override
            public void accept(UpImgEntity upImgEntity) throws Exception {
                LoadingDialog.cancelDialogForLoading();
                if (ObjectUtils.isNotEmpty(upImgEntity.getFileUrl())) {
                    img = upImgEntity.getFileUrl();
                    GlideApp.with(MineActivity.this).load(img).apply(RequestOptions.bitmapTransform(new CropCircleTransformation())).error(R.mipmap.my_avatar_default).placeholder(R.mipmap.my_avatar_default).into(mineImg);
                    updateUserInfo();
                    ToastUtils.showShortCenter("??????????????????");
                } else {
                    ToastUtils.showShortCenter("??????????????????");
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                LoadingDialog.cancelDialogForLoading();
            }
        });
    }

    @OnClick({R.id.ll_nick_name, R.id.ll_real_name})
    public void changeName(View view) {
        switch (view.getId()) {
            case R.id.ll_nick_name:
                ARouter.getInstance().build(Constant.ACTIVITY_CHANGE_NAME).withInt("type", 0).withString("name", tvNickname.getText().toString()).navigation(this, 2);
                break;
            case R.id.ll_real_name:
                ARouter.getInstance().build(Constant.ACTIVITY_CHANGE_NAME).withInt("type", 1).withString("name", tvRealName.getText().toString()).navigation(this, 3);
                break;
        }
    }


    @OnClick(R.id.ll_phone_num)
    public void phoneBind() {
        ARouter.getInstance().build(Constant.ACTIVITY_BIND_PHONE).withInt("pageType", 1).navigation(this, 4);
    }

    /**
     * ????????????
     */
    public void updateUserInfo() {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", SPUtils.getInstance().getString(SPCommon.TOKEN));
        if (ObjectUtils.isNotEmpty(img)) {
            map.put("head", img);
        }
        if (ObjectUtils.isNotEmpty(locationcity)) {
            map.put("locationcity", locationcity);
        }
        if (ObjectUtils.isNotEmpty("name")) {
            map.put("name", name);
        }
        if (ObjectUtils.isNotEmpty(sex + "")) {
            map.put("gender", sex + "");
        }
        if (ObjectUtils.isNotEmpty(startcity)) {
            map.put("startcity", startcity);
        }
        if (ObjectUtils.isNotEmpty(birthday)) {
            map.put("birthday", birthday);
        }
        if (ObjectUtils.isNotEmpty(nickName)) {
            map.put("nickname", nickName);
        }
        LoadingDialog.showDialogForLoading(this);
        RetrofitHelper.getApiService().updateUserInfo(map).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).subscribe(new DefaultObserver() {
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
}

