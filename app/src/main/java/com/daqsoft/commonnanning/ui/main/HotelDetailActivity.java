package com.daqsoft.commonnanning.ui.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.daqsoft.android.ProjectConfig;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.AESEncryptUtil;
import com.daqsoft.commonnanning.common.ComUtils;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.common.SocialUtil;
import com.daqsoft.commonnanning.common.SourceConstant;
import com.daqsoft.commonnanning.common.SourceType;
import com.daqsoft.commonnanning.helps_gdmap.CompleteFuncData;
import com.daqsoft.commonnanning.helps_gdmap.HelpMaps;
import com.daqsoft.commonnanning.helps_gdmap.MapUtils;
import com.daqsoft.commonnanning.hotel.HotelActivity;
import com.daqsoft.commonnanning.http.CommonRequest;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.FoundNearEy;
import com.daqsoft.commonnanning.ui.entity.HotelDetail;
import com.daqsoft.commonnanning.ui.entity.KeyValue;
import com.daqsoft.commonnanning.ui.entity.UserInfoEntity;
import com.daqsoft.commonnanning.ui.main.contract.HotelDetailContact;
import com.daqsoft.commonnanning.ui.main.presenter.HotelDetailPresenter;
import com.daqsoft.commonnanning.ui.mine.interact.entity.CommentBean;
import com.daqsoft.commonnanning.ui.service.FoundNearNewActivity;
import com.daqsoft.commonnanning.utils.MyLayoutManager;
import com.daqsoft.commonnanning.view.CenterDrawableTextView;
import com.daqsoft.commonnanning.view.ExpandTextView;
import com.daqsoft.commonnanning.view.FlowLayout;
import com.daqsoft.utils.ShowToast;
import com.daqsoft.utils.Utils;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.glide.GlideApp;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.refresh.SmartRefreshLayout;
import com.example.tomasyb.baselib.refresh.api.RefreshLayout;
import com.example.tomasyb.baselib.refresh.listener.OnRefreshListener;
import com.example.tomasyb.baselib.util.ImageUtils;
import com.example.tomasyb.baselib.util.LogUtils;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.agora.yshare.SocialHelper;
import io.agora.yshare.callback.SocialShareCallback;
import io.agora.yshare.entities.QQShareEntity;
import io.agora.yshare.entities.ShareEntity;
import io.agora.yshare.entities.WXShareEntity;
import io.agora.yview.dialog.BaseDialog;
import io.agora.yview.img.CircleImageView;
import io.agora.yview.photoview.PicturePreviewActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 酒店详情
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-6-4.15:50
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_HOTEL_DETAIL)

public class HotelDetailActivity extends BaseActivity<HotelDetailContact.presenter> implements HotelDetailContact.view {
    @BindView(R.id.head)
    HeadView head;
    @BindView(R.id.img_top)
    ImageView imgTop;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @Autowired(name = "mId")
    String strategyId;
    @BindView(R.id.va)
    ViewAnimator mVa;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_distance)
    TextView tv_distance;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.extv)
    ExpandTextView extv;
    @BindView(R.id.img_sceven)
    ImageView imgSceven;
    @BindView(R.id.rv_tool)
    RecyclerView mToolRv;
    @BindView(R.id.extv_tie)
    ExpandTextView extvXiao;
    @BindView(R.id.ll_tip)
    LinearLayout ll_tip;
    @BindView(R.id.commoninfo_rv)
    RecyclerView mCommonInfoRv;
    @BindView(R.id.tv_record_star)
    CenterDrawableTextView tvRecordStar;
    @BindView(R.id.tv_record_comment)
    CenterDrawableTextView tvRecordComment;
    @BindView(R.id.tv_chang)
    CenterDrawableTextView tvChang;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout mSmartRefresh;
    @BindView(R.id.rv_tag)
    RecyclerView mTagRv;
    @BindView(R.id.tv_img_show)
    TextView mTvImgList;
    @BindView(R.id.tv_comment_write)
    TextView mTvOrding;
    @BindView(R.id.tv_hotel_order)
    TextView tvOrder;
    @BindView(R.id.tv_source)
    TextView tv_source;
    @BindView(R.id.ratingbar)
    RatingBar ratingBar;
    @BindView(R.id.lable_container)
    FlowLayout flowLayout;
    /**
     * 景区简介
     */
    private String submin = "";
    /**
     * 电话
     */
    private String phone = "";
    /**
     * 是否可以查看图片集合
     */
    private ArrayList<String> imgList = new ArrayList<>();
    /**
     * 酒店名字
     */
    private String name = "";
    /**
     * 全景
     */
    private String panorama = "";
    /**
     * 存小图标的集合
     */
    private List<KeyValue> mToolALLList = new ArrayList<>();
    /**
     * 所有数据
     */
    private List<KeyValue> mToolList = new ArrayList<>();
    /**
     * 只有6个数据
     */
    private List<KeyValue> mToolChildList = new ArrayList<>();
    /**
     * 便利设施适配器
     */
    private BaseQuickAdapter<KeyValue, BaseViewHolder> mToolAdapter;
    /**
     * 便利设施一开始箭头向下
     */
    private boolean isDown = true;
    /**
     * 景区Tag适配器
     */
    private BaseQuickAdapter<String, BaseViewHolder> mTagAdapter;
    /**
     * 地图相关
     */
    private MapView mapView;
    private AMap aMap;
    private UiSettings mUiSettings;
    /**
     * 评论的适配器
     */
    private BaseQuickAdapter<CommentBean, BaseViewHolder> mCommonInfoAdapter;
    /**
     * 当前经纬度
     */
    private String currentLat = "";
    private String currentLon = "";
    private String currentAddress = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        initMap();
    }

    /**
     * 初始化地图
     */
    private void initMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            mUiSettings = aMap.getUiSettings();
            // 是否显示缩放按钮
            mUiSettings.setZoomControlsEnabled(false);
            aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    //ComUtils.goToNearMap(currentLat,currentLon,name,currentAddress,
                    // HotelDetailActivity.this);
                }
            });
            getMySelfLocation();

        }
    }

    /**
     * 获取当前用户位置
     */
    public void getMySelfLocation() {
        HelpMaps.startLocation(new CompleteFuncData() {
            @Override
            public void success(AMapLocation location) {
                if (ObjectUtils.isNotEmpty(location)) {
                    try {
                        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
                        HelpMaps.stopLocation();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (mapView != null) {
                mapView.onDestroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化评论适配器
     */
    private void initCommonInfoAdapter() {
        mCommonInfoRv.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mCommonInfoAdapter =
                new BaseQuickAdapter<CommentBean, BaseViewHolder>(R.layout.item_comment, null) {
            @Override
            protected void convert(BaseViewHolder helper, final CommentBean bean) {
                GlideApp.with(mContext).load(bean.getHeadPath()).placeholder(R.mipmap.my_avatar_default).error(R.mipmap.my_avatar_default).into((CircleImageView) helper.getView(R.id.iv_head_portrait));
                helper.setText(R.id.tv_comment_user_name, bean.getName());
                helper.setText(R.id.tv_comment, bean.getComment());
                helper.setText(R.id.tv_comment_time, bean.getTime());
                // 设置评论星级
                RatingBar ratingbar = helper.getView(R.id.ratingbar);
                ratingbar.setRating(bean.getStar());

            }
        };
        mCommonInfoRv.setAdapter(mCommonInfoAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_hotel_detail;
    }

    @Override
    public void initView() {
        socialHelper = SocialUtil.INSTANCE.socialHelper;
        ARouter.getInstance().inject(this);
        head.setTitle("酒店详情");
        initTagAdapter();
        initCommonInfoAdapter();
        head.setBackListener(new HeadView.OnBackListener() {
            @Override
            public void onBack(View v) {
                finish();
            }
        });
        initTool();
        mSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.getHotelDetail(strategyId);
                presenter.getListTip(strategyId);
            }
        });
        mSmartRefresh.autoRefresh();
        head.setMenuBackGround(R.mipmap.share);
        if (ObjectUtils.isNotEmpty(ProjectConfig.QQ_APPID) && ObjectUtils.isNotEmpty(ProjectConfig.WECHAT_APPID)) {
            head.setMenuHidden(true);
        } else {
            head.setMenuHidden(false);
        }
        if(ProjectConfig.CITY_NAME.equals("西藏")) {
            head.setMenuHidden(false);
        }
        head.setMenuListener(new HeadView.OnMenuListener() {
            @Override
            public void onClickMenu(View v) {
                if (ObjectUtils.isNotEmpty(sharePicture) && ObjectUtils.isNotEmpty(name) && ObjectUtils.isNotEmpty(submin) && ObjectUtils.isNotEmpty(strategyId)) {
                    mShareDialog.show();
                } else {
                    ToastUtils.showShort("数据不全无法分享!");
                }
            }
        });
        ininShareDialog();
    }

    /**
     * 分享弹框
     */
    private BaseDialog mShareDialog;
    /**
     * 三方登录
     */
    private SocialHelper socialHelper;
    /**
     * 分享的图片地址本地
     */
    private String picturePath = "";
    /**
     * 分享的图片
     */
    private String sharePicture = "";


    /**
     * 初始化弹框
     */
    private void ininShareDialog() {
        mShareDialog = new BaseDialog(this);
        mShareDialog.contentView(R.layout.dialog_bottom_share).layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)).gravity(Gravity.BOTTOM).animType(BaseDialog.AnimInType.BOTTOM).canceledOnTouchOutside(true);
        mShareDialog.findViewById(R.id.img_qq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShareDialog.dismiss();
                ShareEntity appInfo = QQShareEntity.createImageTextInfo(name,
                        ProjectConfig.SHARE_HOTEL_URL + strategyId, sharePicture, submin,
                        ProjectConfig.CITY_NAME);
                LogUtils.e("你分享的地址---》" + ProjectConfig.SHARE_BASE_URL + strategyId);
                // QQ
                socialHelper.shareQQ(HotelDetailActivity.this, appInfo, new SocialShareCallback() {
                    @Override
                    public void shareSuccess(int type) {

                    }

                    @Override
                    public void socialError(String msg) {

                    }
                });
            }
        });
        mShareDialog.findViewById(R.id.img_qqzone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShareDialog.dismiss();
                ArrayList<String> imglist = new ArrayList<>();
                imglist.add(sharePicture);
                ShareEntity appInfo = QQShareEntity.createImageTextInfoToQZone(name,
                        ProjectConfig.SHARE_HOTEL_URL + strategyId, imglist, submin,
                        ProjectConfig.CITY_NAME);

                // QQ空间
                socialHelper.shareQQ(HotelDetailActivity.this, appInfo, new SocialShareCallback() {
                    @Override
                    public void shareSuccess(int type) {

                    }

                    @Override
                    public void socialError(String msg) {

                    }
                });
            }
        });
        mShareDialog.findViewById(R.id.img_weix).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShareDialog.dismiss();
                ShareEntity webPageInfo = WXShareEntity.createWebPageInfo(false,
                        ProjectConfig.SHARE_HOTEL_URL + strategyId, picturePath, name, submin);

                socialHelper.shareWX(HotelDetailActivity.this, webPageInfo,
                        new SocialShareCallback() {
                    @Override
                    public void shareSuccess(int type) {

                    }

                    @Override
                    public void socialError(String msg) {

                    }
                });
            }
        });
        mShareDialog.findViewById(R.id.img_weixzone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShareDialog.dismiss();
                ShareEntity webPageInfo = WXShareEntity.createWebPageInfo(true,
                        ProjectConfig.SHARE_HOTEL_URL + strategyId, picturePath, name, submin);

                socialHelper.shareWX(HotelDetailActivity.this, webPageInfo,
                        new SocialShareCallback() {
                    @Override
                    public void shareSuccess(int type) {

                    }

                    @Override
                    public void socialError(String msg) {

                    }
                });
            }
        });
    }

    /**
     * 初始化TAG适配器
     */
    private void initTagAdapter() {
        /*mTagRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));*/
        mTagRv.setLayoutManager(new MyLayoutManager());
        mTagAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_tag_orange, null) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_tag, item);
            }
        };
        mTagRv.setAdapter(mTagAdapter);
    }

    /**
     * 初始化便利设置
     */
    private void initTool() {
        for (int i = 0; i < SourceConstant.code.length; i++) {
            KeyValue bean = new KeyValue();
            bean.setCode(SourceConstant.code[i]);
            bean.setImg(SourceConstant.iconPic[i]);
            mToolALLList.add(bean);
        }
        mToolRv.setLayoutManager(new GridLayoutManager(this, 3));
        mToolAdapter = new BaseQuickAdapter<KeyValue, BaseViewHolder>(R.layout.item_hotel_tool,
                null) {
            @Override
            protected void convert(BaseViewHolder helper, KeyValue item) {
                helper.setText(R.id.tv_name, item.getName());
                helper.setImageResource(R.id.img_tag, item.getImg());
            }
        };
        // 添加头布局
        View head = getLayoutInflater().inflate(R.layout.include_item_head_text_only,
                (ViewGroup) mToolRv.getParent(), false);
        TextView mTv = (TextView) head.findViewById(R.id.tv_tag);
        mTv.setText("便利设施");
        View footer = getLayoutInflater().inflate(R.layout.include_item_footer_img_down,
                (ViewGroup) mToolRv.getParent(), false);
        final ImageView mImgDown = (ImageView) footer.findViewById(R.id.img_down);
        mImgDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isDown) {
                    mImgDown.setImageResource(R.mipmap.complaint_details_arrow_up_normal);
                    isDown = false;
                    mToolAdapter.setNewData(mToolList);
                } else {
                    mImgDown.setImageResource(R.mipmap.complaint_details_arrow_down_normal);
                    isDown = true;
                    mToolAdapter.setNewData(mToolChildList);
                }
            }
        });
        mToolAdapter.addHeaderView(head);
        mToolAdapter.addFooterView(footer);
        mToolRv.setAdapter(mToolAdapter);
    }

    @Override
    public HotelDetailContact.presenter initPresenter() {
        return new HotelDetailPresenter(this);
    }

    @Override
    public void setBaseData(HotelDetail bean) {
        flowLayout.removeAllViews();
        mSmartRefresh.finishRefresh();
        if (ObjectUtils.isNotEmpty(bean)) {
            mVa.setDisplayedChild(0);
            // 评分
            try {
                if (ObjectUtils.isNotEmpty(bean.getCommentLevel())) {
                    if (bean.getCommentLevel().equals("0.0") || bean.getCommentLevel().equals("0")) {
                        tv_source.setVisibility(View.GONE);
                    } else {
                        tv_source.setVisibility(View.VISIBLE);
                        tv_source.setText("评分:" + bean.getCommentLevel());
                    }
                } else {
                    tv_source.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                tv_source.setVisibility(View.GONE);
                e.printStackTrace();
            }
            // 星级等级
            String level = HotelActivity.hotelLevelFormat(bean.getLevel());
            if (ObjectUtils.isNotEmpty(level)) {
                bean.getTag().add(0, level);
            }

            // 在线预定
            if (ObjectUtils.isNotEmpty(bean.getCheap())) {
                orDingPath = bean.getCheap();
                tvOrder.setVisibility(View.VISIBLE);
                mTvOrding.setVisibility(View.VISIBLE);
                mTvOrding.setBackgroundColor(getResources().getColor(R.color.main_default));
                LinearLayout tvOrder =
                        (LinearLayout) getLayoutInflater().inflate(R.layout.include_order, null);
                flowLayout.addView(tvOrder);
                flowLayout.invalidate();
            } else {
                mTvOrding.setVisibility(View.GONE);
                tvOrder.setVisibility(View.GONE);
            }
            if (bean.getImgList().size() > 0) {
                mTvImgList.setVisibility(View.VISIBLE);
                for (int i = 0; i < bean.getImgList().size(); i++) {
                    imgList.add(bean.getImgList().get(i).getImgPath());
                }
                mTvImgList.setText(bean.getImgList().size() + "");
            } else {
                mTvImgList.setVisibility(View.GONE);
            }
            // 标签设置
            if (ObjectUtils.isNotEmpty(bean.getTag()) && bean.getTag().size() > 0) {
                for (int i = 0; i < bean.getTag().size(); i++) {
                    Object o = bean.getTag().get(i);
                    LinearLayout ll =
                            (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_tag_orange, null);
                    TextView tv = ll.findViewById(R.id.tv_tag);
                    tv.setText("" + o.toString());
                    //                    tv.setLayoutParams(params);
                    flowLayout.addView(ll);
                    flowLayout.invalidate();
                }
            }
            // 标签设置 ★★
            if (ObjectUtils.isNotEmpty(bean.getTag()) && bean.getTag().size() > 0) {
                mTagAdapter.setNewData(bean.getTag());
            }
            // 设置点赞收藏状态0未1已经
            if (bean.getVo().getThumb() == 0) {
                tvRecordStar.setSelected(false);
            } else {
                tvRecordStar.setSelected(true);
            }
            if (bean.getVo().getEnshrine() == 0) {
                tvChang.setSelected(false);
            } else {
                tvChang.setSelected(true);
            }
            currentLat = bean.getLatitude();
            currentLon = bean.getLongitude();
            currentAddress = bean.getAddress();
            submin = bean.getIntro();
            phone = bean.getPhone();
            name = bean.getName();
            panorama = bean.getPanorama();
            if (ObjectUtils.isEmpty(panorama)) {
                imgSceven.setVisibility(View.GONE);
            }
            mTvName.setText(bean.getName());
            tv_address.setText(bean.getAddress());
            if (ObjectUtils.isNotEmpty(bean.getPictureTwoToOne())) {
                sharePicture = bean.getPictureTwoToOne();
                picturePath = Environment.getExternalStorageDirectory() + "/zupubao/";
                Glide.with(this).asBitmap().load(bean.getPictureTwoToOne()).
                        into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource,
                                                        @Nullable Transition<? super Bitmap> transition) {
                                ImageUtils.save(resource, picturePath, Bitmap.CompressFormat.JPEG
                                        , false);
                            }
                        });
            }
            GlideApp.with(mContext).load(bean.getPictureTwoToOne()).placeholder(R.mipmap.common_ba_banner).error(R.mipmap.common_ba_banner).into(imgTop);
            String distence = MapUtils.calculateLineDistance(SPUtils.getInstance().getString(
                    "latitude"), SPUtils.getInstance().getString("longitude"), bean.getLatitude()
                    , bean.getLongitude());
            tv_distance.setText(distence);
            tv_phone.setText(bean.getPhone());
            ComUtils.setExpandTextview(extv, bean.getIntro(), bean.getContent());
            // 设置评论数据
            presenter.getCommonInfoData(bean.getId(), SourceType.RESOURCE_HOTEL);
            // 周边地图
            presenter.getAroundMap(bean.getLatitude(), bean.getLongitude(), "1,3,10,14", "10");

            // 便利设施
            mToolList.clear();
            mToolChildList.clear();

            if (bean.getNetworkCode().size() > 0)
                for (int i = 0; i < bean.getNetworkCode().size(); i++) {
                    for (int j = 0; j < mToolALLList.size(); j++) {
                        if (bean.getNetworkCode().get(i).getCode().equals(mToolALLList.get(j).getCode())) {
                            KeyValue keyValue = new KeyValue();
                            keyValue.setImg(mToolALLList.get(j).getImg());
                            keyValue.setName(bean.getNetworkCode().get(i).getName());
                            mToolList.add(keyValue);
                        }
                    }
                }

            if (bean.getServerIncludeCode().size() > 0)
                for (int i = 0; i < bean.getServerIncludeCode().size(); i++) {
                    for (int j = 0; j < mToolALLList.size(); j++) {
                        if (bean.getServerIncludeCode().get(i).getCode().equals(mToolALLList.get(j).getCode())) {
                            KeyValue keyValue = new KeyValue();
                            keyValue.setImg(mToolALLList.get(j).getImg());
                            keyValue.setName(bean.getServerIncludeCode().get(i).getName());
                            mToolList.add(keyValue);
                        }
                    }
                }

            if (bean.getDiningServerCode().size() > 0)
                for (int i = 0; i < bean.getDiningServerCode().size(); i++) {
                    for (int j = 0; j < mToolALLList.size(); j++) {
                        if (bean.getDiningServerCode().get(i).getCode().equals(mToolALLList.get(j).getCode())) {
                            KeyValue keyValue = new KeyValue();
                            keyValue.setImg(mToolALLList.get(j).getImg());
                            keyValue.setName(bean.getDiningServerCode().get(i).getName());
                            mToolList.add(keyValue);
                        }
                    }
                }
            if (bean.getReceptionServerCode().size() > 0)
                for (int i = 0; i < bean.getReceptionServerCode().size(); i++) {
                    for (int j = 0; j < mToolALLList.size(); j++) {
                        if (bean.getReceptionServerCode().get(i).getCode().equals(mToolALLList.get(j).getCode())) {
                            KeyValue keyValue = new KeyValue();
                            keyValue.setImg(mToolALLList.get(j).getImg());
                            keyValue.setName(bean.getReceptionServerCode().get(i).getName());
                            mToolList.add(keyValue);
                        }
                    }
                }
            if (bean.getTrafficServerCode().size() > 0)
                for (int i = 0; i < bean.getTrafficServerCode().size(); i++) {
                    for (int j = 0; j < mToolALLList.size(); j++) {
                        if (bean.getTrafficServerCode().get(i).getCode().equals(mToolALLList.get(j).getCode())) {
                            KeyValue keyValue = new KeyValue();
                            keyValue.setImg(mToolALLList.get(j).getImg());
                            keyValue.setName(bean.getTrafficServerCode().get(i).getName());
                            mToolList.add(keyValue);
                        }
                    }
                }
            if (bean.getRoomFacilitiesCode().size() > 0)
                for (int i = 0; i < bean.getRoomFacilitiesCode().size(); i++) {
                    for (int j = 0; j < mToolALLList.size(); j++) {
                        if (bean.getRoomFacilitiesCode().get(i).getCode().equals(mToolALLList.get(j).getCode())) {
                            KeyValue keyValue = new KeyValue();
                            keyValue.setImg(mToolALLList.get(j).getImg());
                            keyValue.setName(bean.getRoomFacilitiesCode().get(i).getName());
                            mToolList.add(keyValue);
                        }
                    }

                }

            mToolAdapter.notifyDataSetChanged();
            if (mToolList.size() > 6) {
                for (int i = 0; i < 6; i++) {
                    mToolChildList.add(mToolList.get(i));
                }
                mToolAdapter.setNewData(mToolChildList);
            } else {
                mToolAdapter.setNewData(mToolList);
                mToolAdapter.removeAllFooterView();
            }


        } else {
            mVa.setDisplayedChild(1);
        }
    }

    @Override
    public void addMapMarker(List<FoundNearEy> list) {
        if (ObjectUtils.isNotEmpty(list)) {
            for (int i = 0; i < aMap.getMapScreenMarkers().size(); i++) {
                aMap.getMapScreenMarkers().get(i).remove();
            }
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (int i = 0; i < list.size(); i++) {
                LatLng latLng = new LatLng(Double.parseDouble(list.get(i).getLat()),
                        Double.parseDouble(list.get(i).getLon()));

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.title(list.get(i).getTitle()).position(latLng).draggable(false);
                if (list.get(i).getType().equals("SCENERY")) {
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.detail_hotel_zbzy_point_hotel));
                } else if (list.get(i).getType().equals("DINING")) {
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.detail_jq_zbzy_point_restaurant));
                } else if (list.get(i).getType().equals("PARKINGLOT")) {
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.detail_jq_zbzy_point_parking));
                } else if (list.get(i).getType().equals("HIGHWAY")) {
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.detail_jq_zbzy_point_traffic));
                }
                Marker marker = aMap.addMarker(markerOptions);
                // 这行关键，标记Marker的类型xxx
                marker.setObject(list.get(i));
                builder.include(latLng);
            }
            // 设置所有maker显示在当前可视区域地图中
            LatLngBounds bounds = builder.build();
            aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));
        }
    }

    @Override
    public void setCommonInfoData(List<CommentBean> list, String allpeople) {
        if (ObjectUtils.isNotEmpty(list) && list.size() > 0) {
            mCommonInfoAdapter.removeAllHeaderView();
            mCommonInfoAdapter.removeAllFooterView();
            View head = getLayoutInflater().inflate(R.layout.item_commoninfo_head,
                    (ViewGroup) mCommonInfoRv.getParent(), false);
            TextView tv = (TextView) head.findViewById(R.id.tv_around);
            tv.setText("来自" + allpeople + "位游客点评");

            View footer = getLayoutInflater().inflate(R.layout.include_adapter_footer_more,
                    (ViewGroup) mCommonInfoRv.getParent(), false);
            TextView foot = (TextView) footer.findViewById(R.id.tv_footer_more);
            foot.setText("查看全部" + allpeople + "条评价信息");
            foot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ARouter.getInstance().build(Constant.ACTIVITY_COMMENTMORE).withString("id",
                            strategyId).withString("type", SourceType.RESOURCE_HOTEL).withString(
                                    "TYPE", "0").navigation();
                }
            });
            mCommonInfoAdapter.addHeaderView(head);
            mCommonInfoAdapter.addFooterView(footer);
            mCommonInfoAdapter.setNewData(list);
        }
    }

    @Override
    public void setTumbleStyle(boolean isSelected) {
        tvRecordStar.setSelected(isSelected);
    }

    @Override
    public void setChangStyle(boolean isSelected) {
        tvChang.setSelected(isSelected);
    }

    @Override
    public void goToLoginActivity() {
        ARouter.getInstance().build(Constant.ACTIVITY_LOGIN).navigation(this, 1);
    }

    @Override
    public void setHotelTip(String tip) {
        if (ObjectUtils.isNotEmpty(tip)) {
            ll_tip.setVisibility(View.VISIBLE);
            // 入住小贴士
            ComUtils.setExpandTextview(extvXiao, tip, tip);
        } else {
            ll_tip.setVisibility(View.GONE);
        }
    }

    /**
     * 在线预定地址
     */
    private String orDingPath = "";
    /**
     * 电商网页拼接参数
     */
    String URLPARAMS = "&" + "token=" + SPUtils.getInstance().getString(SPCommon.UC_TOKEN) +
            "&unid=" + SPUtils.getInstance().getString(SPCommon.UC_ID) + "&linkFrom=app";

    @OnClick({R.id.img_top, R.id.img_sceven, R.id.tv_around, R.id.img_phone, R.id.tv_record_star,
            R.id.tv_record_comment, R.id.tv_chang, R.id.rl_address, R.id.tv_img_show,
            R.id.tv_comment_write, R.id.ll_bottom_around, R.id.ll_scenic, R.id.ll_food,
            R.id.ll_car, R.id.ll_traffics})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_scenic:
                ComUtils.goToNearMapWitnType(currentLat, currentLon, name, currentAddress, this, 1);
                break;
            case R.id.ll_food:
                ComUtils.goToNearMapWitnType(currentLat, currentLon, name, currentAddress, this, 3);
                break;
            case R.id.ll_car:
                ComUtils.goToNearMapWitnType(currentLat, currentLon, name, currentAddress, this,
                        10);
                break;
            case R.id.ll_traffics:
                ComUtils.goToNearMapWitnType(currentLat, currentLon, name, currentAddress, this,
                        14);
                break;
            case R.id.tv_comment_write:
                if (ObjectUtils.isNotEmpty(orDingPath)) {
                    LoadingDialog.showDialogForLoading(this);
                    CommonRequest.checkedToken(new CommonRequest.OnStateCallBack() {
                        @Override
                        public void result(int value) {
                            LoadingDialog.cancelDialogForLoading();
                            // 0未失效
                            if (0 == value) {
                                if (ProjectConfig.CITY_NAME.equals("南宁")) {
                                    String phone = SPUtils.getInstance().getString(SPCommon.PHONE);
                                    boolean isService =
                                            SPUtils.getInstance().getBoolean(SPCommon.SERVICE,
                                                    false);
                                    try {
                                        String aesphone = AESEncryptUtil.Encrypt16(phone);
                                        String url = orDingPath + "&phone=" + aesphone;
                                        LogUtils.e("景区请求地址-->" + url);
                                        if (isService) {
                                            ARouter.getInstance().build(Constant.ACTIVITY_BASEWEB).withString("HTMLTITLE", "--").withString("HTMLURL", url).navigation();
                                        } else {
                                            ARouter.getInstance().build(Constant.ACTIVITY_USER_SERVICE).withString("HTMLURL", url).navigation();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    ARouter.getInstance().build(Constant.ACTIVITY_BASEWEB).withString("HTMLTITLE", name).withString("HTMLURL", orDingPath + URLPARAMS).navigation();
                                }
                            } else {
                                ARouter.getInstance().build(Constant.ACTIVITY_LOGIN).navigation(HotelDetailActivity.this, 1);
                            }
                        }
                    });
                }
                break;
            case R.id.tv_img_show:
                if (imgList.size() > 0) {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(this, PicturePreviewActivity.class);
                    bundle.putStringArrayList("imgList", imgList);
                    bundle.putInt("currentPosition", 0);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
            case R.id.tv_record_star:
                // 选择就删除点赞记录
                if (tvRecordStar.isSelected()) {
                    presenter.delThumb(strategyId);
                } else {
                    // 保存
                    presenter.saveThumb(submin, strategyId, name);
                }
                break;
            case R.id.tv_record_comment:
                LoadingDialog.showDialogForLoading(this);
                CommonRequest.checkedToken(new CommonRequest.OnStateCallBack() {
                    @Override
                    public void result(int value) {
                        LoadingDialog.cancelDialogForLoading();
                        // 0未失效
                        if (0 == value) {
                            ARouter.getInstance().build(Constant.ACTIVITY_WRITECOMMENT).withString("name", name).withString("id", strategyId).withString("type", SourceType.RESOURCE_HOTEL).withString("TYPE", "0").navigation(HotelDetailActivity.this, 100);
                        } else {
                            ToastUtils.showShort("请先登录");
                            ARouter.getInstance().build(Constant.ACTIVITY_LOGIN).navigation();
                        }
                    }
                });
                break;
            case R.id.tv_chang:
                // 选择就删除点赞记录
                if (tvChang.isSelected()) {
                    presenter.delCollection(strategyId);
                } else {
                    // 保存
                    presenter.saveCollection(strategyId, name, submin);
                }

                break;
            case R.id.img_phone:
                ComUtils.showQueryCancleDialogPhone(this, phone);
                break;
            case R.id.rl_address:
                if (!Utils.gethaveNet(this)) {
                    ShowToast.showText(this, "网络错误，无法进行导航操作");
                } else {
                    if (Utils.isnotNull(SPUtils.getInstance().getString(SPCommon.LATITUDE)) && Utils.isnotNull(SPUtils.getInstance().getString(SPCommon.LONGITUDE)) && Utils.isnotNull(currentLat) && Utils.isnotNull(currentLon)) {
                        com.daqsoft.commonnanning.helps_gdmap.MapNaviUtils.isMapNaviUtils(this,
                                SPUtils.getInstance().getString(SPCommon.LATITUDE),
                                SPUtils.getInstance().getString(SPCommon.LONGITUDE),
                                SPUtils.getInstance().getString(SPCommon.CITY_ADDRESS),
                                currentLat, currentLon, Utils.isnotNull(currentAddress) ?
                                        Utils.tr(currentAddress) : "目的地");
                    } else {
                        ShowToast.showText(this, "数据异常，无法进行导航操作");
                    }
                }
            case R.id.img_top:
                break;
            case R.id.img_sceven:
                if (ObjectUtils.isNotEmpty(panorama)) {
                    ARouter.getInstance().build(Constant.ACTIVITY_BASEWEB).withString("HTMLURL",
                            panorama).withString("HTMLTITLE", name).navigation();
                }
                break;
            case R.id.tv_around:
                // 周边好玩
                gotoAround();
                break;
            case R.id.ll_bottom_around:
                gotoAround();
                break;
            default:
                break;
        }
    }

    /**
     * 调整周边资源
     */
    private void gotoAround() {
        // 周边好玩
        Bundle bundle2 = new Bundle();
        bundle2.putString("lat", currentLat);
        bundle2.putString("lng", currentLon);
        bundle2.putString("scenicname", name);
        bundle2.putString("scenicadress", currentAddress);
        bundle2.putInt("type", 1);
        Intent intent2 = new Intent(this, FoundNearNewActivity.class);
        intent2.putExtras(bundle2);
        startActivity(intent2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 0) {
            presenter.getCommonInfoData(strategyId, SourceType.RESOURCE_HOTEL);
        } else if (requestCode == 1 && resultCode == 3) {
            getUserInfo();
        }
    }

    private void getUserInfo() {
        RetrofitHelper.getApiService().getUserInfo().subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BaseResponse<UserInfoEntity>>() {
            @Override
            public void accept(BaseResponse<UserInfoEntity> bean) throws Exception {
                if (bean.getCode() == 0) {
                    SPUtils.getInstance().put(SPCommon.PHONE, bean.getData().getPhone());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }

}
