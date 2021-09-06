package com.daqsoft.commonnanning.ui.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.daqsoft.android.ProjectConfig;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.base.IApplication;
import com.daqsoft.commonnanning.common.AESEncryptUtil;
import com.daqsoft.commonnanning.common.ComUtils;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.common.SourceConstant;
import com.daqsoft.commonnanning.common.SourceType;
import com.daqsoft.commonnanning.common.URLConstant;
import com.daqsoft.commonnanning.helps_gdmap.MapNaviUtils;
import com.daqsoft.commonnanning.helps_gdmap.MapUtils;
import com.daqsoft.commonnanning.http.CommonRequest;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.FoundNearEy;
import com.daqsoft.commonnanning.ui.entity.GuideDetail;
import com.daqsoft.commonnanning.ui.entity.RouteEnty;
import com.daqsoft.commonnanning.ui.entity.ScenicChild;
import com.daqsoft.commonnanning.ui.entity.ScenicDetail;
import com.daqsoft.commonnanning.ui.entity.ScenicVideo;
import com.daqsoft.commonnanning.ui.entity.UserInfoEntity;
import com.daqsoft.commonnanning.ui.main.contract.ScenicDetailContact;
import com.daqsoft.commonnanning.ui.main.presenter.ScenicDetailPresenter;
import com.daqsoft.commonnanning.ui.mine.interact.entity.CommentBean;
import com.daqsoft.commonnanning.ui.scenic.AudioActivity;
import com.daqsoft.commonnanning.utils.GlideUtils;
import com.daqsoft.commonnanning.utils.ShareUtils;
import com.daqsoft.commonnanning.view.CenterDrawableTextView;
import com.daqsoft.commonnanning.view.ExpandTextView;
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
import com.example.tomasyb.baselib.util.LogUtils;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.util.ScreenUtils;
import com.example.tomasyb.baselib.util.SizeUtils;
import com.example.tomasyb.baselib.util.TimeUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.web.ProgressWebView;
import com.example.tomasyb.baselib.web.WebUtils;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.LoadingDialog;
import com.example.tomasyb.baselib.widget.LoadingTip;
import com.example.tomasyb.baselib.widget.img.RoundImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.agora.yview.dialog.BaseDialog;
import io.agora.yview.img.CircleImageView;
import io.agora.yview.photoview.PicturePreviewActivity;
import io.agora.yview.scrollview.JudgeNestedScrollView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * @version 1.0.1
 * @Description: 景区详情
 * @Author 严博
 * @CreateDate 2019-3-23 9:33
 * @since jdk1.8.0_201
 */
@Route(path = Constant.ACTIVITY_SCENIC_DETAIL)
public class ScenicDetailActivity extends BaseActivity<ScenicDetailContact.presenter> implements ScenicDetailContact.view {


    @BindView(R.id.head)
    HeadView head;
    @BindView(R.id.img_top)
    ImageView imgTop;
    @BindView(R.id.img_weather)
    ImageView imgWeather;
    @BindView(R.id.img_sceven)
    ImageView imgSceven;
    @BindView(R.id.img_real)
    ImageView imgReal;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;
    @Autowired(name = "mId")
    String strategyId;
    @BindView(R.id.tv_weather)
    TextView mTvWeather;
    @BindView(R.id.tv_img_show)
    TextView mTvImgList;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_distance)
    TextView tv_distance;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.extv)
    ExpandTextView extv;
    @BindView(R.id.img_map)
    ImageView imgMap;
    @BindView(R.id.tv_map_tag)
    TextView tvMapTag;
    @BindView(R.id.ll_map)
    LinearLayout llMap;
    @BindView(R.id.tv_map_in)
    TextView tvMapIn;
    @BindView(R.id.rv_child)
    RecyclerView mChildRv;
    @BindView(R.id.rv_video)
    RecyclerView mVideoRv;
    @BindView(R.id.extv_adress)
    ExpandTextView extv_adress;
    @BindView(R.id.ll_adress)
    LinearLayout ll_adress;
    @BindView(R.id.extv_time)
    ExpandTextView extv_time;
    @BindView(R.id.ll_time)
    LinearLayout ll_time;
    @BindView(R.id.extv_traffic)
    ExpandTextView extv_traffic;
    @BindView(R.id.ll_traffic)
    LinearLayout ll_traffic;
    @BindView(R.id.ll_gongl)
    LinearLayout ll_gongl;
    @BindView(R.id.ll_travel)
    LinearLayout ll_travel;
    @BindView(R.id.extv_gongl)
    ExpandTextView extv_gongl;
    @BindView(R.id.extv_travel)
    ExpandTextView extv_travel;
    @BindView(R.id.tv_expand_tag)
    TextView tvExWidth;
    @BindView(R.id.commoninfo_rv)
    RecyclerView mCommonInfoRv;
    @BindView(R.id.tv_record_star)
    CenterDrawableTextView tvRecordStar;
    @BindView(R.id.tv_record_comment)
    CenterDrawableTextView tvRecordComment;
    @BindView(R.id.tv_chang)
    CenterDrawableTextView tvChang;
    @BindView(R.id.rv_tag)
    RecyclerView mTagRv;
    @BindView(R.id.tv_level)
    TextView mTvTag;
    @BindView(R.id.ll_address)
    LinearLayout mllAdress;
    @BindView(R.id.tv_comment_write)
    TextView mTvOrding;
    @BindView(R.id.tv_scenic_order)
    TextView tv_scenic_order;
    @BindView(R.id.ll_phone_tag)
    LinearLayout mllPhone;
    @BindView(R.id.tv_order)
    TextView tvOrder;
    @BindView(R.id.ll_must_go)
    LinearLayout ll_must_go;
    @BindView(R.id.ll_video)
    LinearLayout ll_video;
    @BindView(R.id.tv_scenic_route)
    TextView tv_scenic_route;
    @BindView(R.id.img_index_scenic)
    ImageView mImgScenic;
    @BindView(R.id.ll_common_sheshi)
    LinearLayout ll_common_sheshi;
    @BindView(R.id.img_voice)
    ImageView imgVoice;
    @BindView(R.id.img_phone)
    ImageView imgPhone;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.rl_map)
    RelativeLayout rlMap;
    @BindView(R.id.ll_map_bottom)
    LinearLayout llMapBottom;
    @BindView(R.id.card_map)
    CardView cardMap;
    @BindView(R.id.tv_must_go)
    TextView tvMustGo;
    @BindView(R.id.tv_video)
    TextView tvVideo;
    @BindView(R.id.tv_line)
    TextView tvLine;
    @BindView(R.id.tv_around)
    TextView tvAround;
    @BindView(R.id.map)
    MapView map;
    @BindView(R.id.ll_hotel)
    LinearLayout llHotel;
    @BindView(R.id.ll_food)
    LinearLayout llFood;
    @BindView(R.id.ll_car)
    LinearLayout llCar;
    @BindView(R.id.ll_traffics)
    LinearLayout llTraffics;
    @BindView(R.id.scrollview)
    JudgeNestedScrollView scrollview;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    /**
     * 在线预定地址
     */
    private String orDingPath = "";
    /**
     * 游记攻略的ID
     */
    private String mChildstrategyId = "";
    /**
     * 底部布局变化根据是否是网页跳转过来的
     */
    @BindView(R.id.scenic_detail_va)
    ViewAnimator mVaBottom;
    @Autowired(name = "type")
    String pageType = "0";
    @Autowired(name = "routId")
    String routId = "";
    @Autowired(name = "isJourney")
    String isJourney = "0";
    @BindView(R.id.extv_comment)
    ExpandTextView extv_comment;
    @BindView(R.id.tv_index_scenic)
    TextView mLineTitle;
    @BindView(R.id.tv_seven)
    TextView tv_seven;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_point)
    TextView tv_point;
    @BindView(R.id.tv_star)
    TextView tv_star;
    @BindView(R.id.tv_msg)
    TextView tv_msg;
    @BindView(R.id.ll_line)
    LinearLayout ll_line;
    @BindView(R.id.tv_heat)
    TextView mTvHeat;
    /**
     * 是否可以查看图片集合
     */
    private ArrayList<String> imgList = new ArrayList<>();
    /**
     * 720地址
     */
    private String panoramaPathApp = "";
    /**
     * 全景监控视频地址
     */
    private String videoPath = "";
    /**
     * 景区名字
     */
    private String name = "";
    /**
     * 景区简介
     */
    private String submin = "";
    /**
     * 电话
     */
    private String phone = "";
    /**
     * 导游导览id
     */
    private String mGuideId = "";
    /**
     * 当前经纬度
     */
    private String currentLat = "";
    private String currentLon = "";
    private String currentAddress = "";

    /**
     * 子景点适配器
     */
    private BaseQuickAdapter<ScenicChild, BaseViewHolder> mAdapter;
    /**
     * 子景点视频适配器
     */
    private BaseQuickAdapter<ScenicVideo, BaseViewHolder> mVideoAdapter;
    /**
     * 评论的适配器
     */
    private BaseQuickAdapter<CommentBean, BaseViewHolder> mCommonInfoAdapter;
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
     * 电商网页拼接参数
     */
    String URLPARAMS = "&" + "token=" + SPUtils.getInstance().getString(SPCommon.UC_TOKEN) +
            "&unid=" + SPUtils.getInstance().getString(SPCommon.UC_ID) + "&linkFrom=app";

    BaseDialog mShareDialog = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        initMap();
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
                GlideApp.with(mContext).load(bean.getHeadPath()).placeholder(R.mipmap.my_avatar_default).error(R.mipmap.my_avatar_default).apply(IApplication.requestOptions).into((CircleImageView) helper.getView(R.id.iv_head_portrait));
                helper.setText(R.id.tv_comment_user_name, bean.getName());
                helper.setText(R.id.tv_comment, bean.getComment().trim());
                helper.setText(R.id.tv_comment_time, bean.getTime());
                // 设置评论星级
                RatingBar ratingbar = helper.getView(R.id.ratingbar);
                ratingbar.setRating(bean.getStar());

                RecyclerView recyclerView = helper.getView(R.id.recyclerView_item_comment_img);
                recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                });
                BaseQuickAdapter<String, BaseViewHolder> imageAdapter =
                        new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_resource_imgae
                                , bean.getPicArr()) {
                    @Override
                    protected void convert(final BaseViewHolder helper, String item) {
                        GlideApp.with(mContext).load(item).placeholder(R.mipmap.comimg_fail_240_180).error(R.mipmap.comimg_fail_240_180).apply(IApplication.requestOptions).into((RoundImageView) helper.getView(R.id.iv_item_resource_img));
                        // 图片预览功能
                        helper.setOnClickListener(R.id.iv_item_resource_img,
                                new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(ScenicDetailActivity.this,
                                        PicturePreviewActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("currentPosition", helper.getPosition());
                                bundle.putStringArrayList("imgList",
                                        (ArrayList<String>) bean.getPicArr());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                    }
                };
                recyclerView.setAdapter(imageAdapter);
            }
        };
        mCommonInfoRv.setAdapter(mCommonInfoAdapter);
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
                    ComUtils.goToNearMap(currentLat, currentLon, name, currentAddress,
                            ScenicDetailActivity.this);
                }
            });
        }
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
        mapView.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_scenic_detail;
    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        initCanlender();
        head.setTitle("景区详情");
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
                if (mShareDialog != null) {
                    mShareDialog.show();
                } else {
                    ToastUtils.showShort("数据不全无法分享!");
                }
            }
        });
        // 0正常的景区详情跳转，1 网页行程跳转过来的
        if ("0".equals(pageType)) {
            mVaBottom.setDisplayedChild(0);
        } else if ("1".equals(pageType)) {
            mVaBottom.setDisplayedChild(1);
        }
        if ("0".equals(isJourney)) {
            tv_scenic_route.setBackgroundColor(getResources().getColor(R.color.main_default));
        } else if ("1".equals(isJourney)) {
            tv_scenic_route.setBackgroundColor(getResources().getColor(R.color.main_gray));
        }
        head.setBackListener(new HeadView.OnBackListener() {
            @Override
            public void onBack(View v) {
                finish();
            }
        });
        initTagAdapter();
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData();
            }
        });
        initChildRv();
        initCommonInfoAdapter();
        refreshlayout.autoRefresh();
    }

    /**
     * 刷新数据
     */
    private void getData() {
        presenter.getDetail(strategyId);
        presenter.getChildScenic(strategyId);
        presenter.getScenicVideo(strategyId);
    }

    /**
     * 初始化TAG适配器
     */
    private void initTagAdapter() {
        mTagRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));
        mTagAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_tag_orange, null) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_tag, item);
            }
        };
        mTagRv.setAdapter(mTagAdapter);
    }

    /**
     * 初始化子景点列表
     */
    private void initChildRv() {
        mChildRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));
        mAdapter = new BaseQuickAdapter<ScenicChild, BaseViewHolder>(R.layout.item_scenic_must_go
                , null) {

            @Override
            protected void convert(BaseViewHolder helper, ScenicChild bean) {
                GlideApp.with(mContext).load(bean.getCoverTwoToOne()).placeholder(R.mipmap.comimg_fail_240_180).error(R.mipmap.comimg_fail_240_180).apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(10, 0)).diskCacheStrategy(DiskCacheStrategy.RESOURCE).skipMemoryCache(true)).into((ImageView) helper.getView(R.id.img_index_scenic));

                helper.setText(R.id.tv_index_scenic, bean.getName());
            }
        };
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ScenicChild item = (ScenicChild) adapter.getItem(position);
                ARouter.getInstance().build(Constant.ACTIVITY_SCENIC_CHILD_DETAIL).withString(
                        "mId", item.getId()).withString("title", "景点详情").withInt("pageType", 0).navigation();
            }
        });
        mChildRv.setAdapter(mAdapter);

        // 视频
        mVideoRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));
        mVideoAdapter =
                new BaseQuickAdapter<ScenicVideo, BaseViewHolder>(R.layout.item_scenic_must_go,
                        null) {

            @Override
            protected void convert(BaseViewHolder helper, final ScenicVideo bean) {
                if (ObjectUtils.isNotEmpty(bean.getUpload())) {
                    helper.setVisible(R.id.img_play, true);
                    helper.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // 视频播放
                            ARouter.getInstance().build(Constant.ACTIVITY_VIDEO_PLAY).withString(
                                    "content", bean.getUpload()).navigation();
                        }
                    });
                } else {
                    helper.setVisible(R.id.img_play, false);
                }
                GlideApp.with(mContext).load(bean.getCoverpictureTwoToOne()).placeholder(R.mipmap.comimg_fail_240_180).error(R.mipmap.comimg_fail_240_180).apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(10, 0))).into((ImageView) helper.getView(R.id.img_index_scenic));

                helper.setText(R.id.tv_index_scenic, bean.getName());
            }
        };
        mVideoRv.setAdapter(mVideoAdapter);
    }

    @Override
    public ScenicDetailContact.presenter initPresenter() {
        return new ScenicDetailPresenter(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void setBaseData(ScenicDetail bean) {
        refreshlayout.finishRefresh();
        if (ObjectUtils.isNotEmpty(bean)) {
            if (bean != null) {
                mShareDialog = ShareUtils.ininShareDialog(ScenicDetailActivity.this,
                        ProjectConfig.SHARE_SCENIC_URL, bean.getName(), bean.getId() + "",
                        bean.getPicture(), Html.fromHtml(bean.getSummary(),
                                Html.FROM_HTML_MODE_COMPACT).toString());
            }

            if (ObjectUtils.isNotEmpty(bean.getHeat()) && !bean.getHeat().equals("0") && !bean.getHeat().equals("null")) {
                mTvHeat.setText("景区热度:" + bean.getHeat());
                mTvHeat.setVisibility(View.VISIBLE);
            } else {
                mTvHeat.setVisibility(View.GONE);
            }
            // 在线预定
            if (ObjectUtils.isNotEmpty(bean.getOnlineBooking())) {
                orDingPath = bean.getOnlineBooking();
                mTvOrding.setVisibility(View.VISIBLE);
                tv_scenic_order.setVisibility(View.VISIBLE);
                tvOrder.setVisibility(View.VISIBLE);
            } else {
                mTvOrding.setVisibility(View.GONE);
                tvOrder.setVisibility(View.GONE);
                tv_scenic_order.setVisibility(View.GONE);
            }
            // 标签设置
            if (ObjectUtils.isNotEmpty(bean.getTagNames()) && bean.getTagNames().size() > 0) {
                mTagAdapter.setNewData(bean.getTagNames());
            }
            if (ObjectUtils.isNotEmpty(bean.getServerFacilities())) {
                ll_common_sheshi.setVisibility(View.VISIBLE);
                setExpandTextview(extv_comment,
                        ComUtils.deleteHtmlImg(bean.getServerFacilities()),
                        bean.getServerFacilities());
            } else {
                ll_common_sheshi.setVisibility(View.GONE);
            }
            if (ObjectUtils.isNotEmpty(bean.getLevelName())) {
                mTvTag.setVisibility(View.VISIBLE);
                mTvTag.setText(bean.getLevelName());
            } else {
                mTvTag.setVisibility(View.GONE);
            }
            // 地址
            if (ObjectUtils.isNotEmpty(bean.getAddress()) ) {
                mllAdress.setVisibility(View.VISIBLE);
                currentLat = bean.getLatitude();
                currentLon = bean.getLongitude();
                currentAddress = bean.getAddress();
                String distence = MapUtils.calculateLineDistance(SPUtils.getInstance().getString(
                        "latitude"), SPUtils.getInstance().getString("longitude"),
                        bean.getLatitude(), bean.getLongitude());
                tv_distance.setText("距您" + distence);
                tv_address.setText(bean.getAddress());
            } else {
                mllAdress.setVisibility(View.GONE);
            }



            if (ObjectUtils.isNotEmpty(bean.getPhone())) {
                mllPhone.setVisibility(View.VISIBLE);
                tv_phone.setText(bean.getPhone());
            } else {
                mllPhone.setVisibility(View.GONE);
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
            name = bean.getName();
            submin = bean.getSummary();
            mGuideId = bean.getMapGuideId();
            phone = bean.getPhone();
            panoramaPathApp = bean.getPanoramaPathApp();
            // 判断是否有720全景
            if (ObjectUtils.isNotEmpty(panoramaPathApp)) {
                imgSceven.setVisibility(View.VISIBLE);
            } else {
                imgSceven.setVisibility(View.GONE);
            }
            videoPath = bean.getMonitorPath();
            // 判断是否有实时监控
            if (ObjectUtils.isNotEmpty(videoPath)) {
                imgReal.setVisibility(View.VISIBLE);
            } else {
                imgReal.setVisibility(View.GONE);
            }
            // 设置评论数据
            presenter.getCommonInfoData(bean.getId(), SourceType.RESOURCE_SCENIC);
            // 周边地图
            presenter.getAroundMap(bean.getLatitude(), bean.getLongitude(), "2,3,10,14", "10");
            mTvName.setText(name);
            if (ObjectUtils.isEmpty(mGuideId)) {
                llMap.setVisibility(View.GONE);
            } else {
                presenter.getMapGuideDetail(mGuideId);
            }
            try {
                // 设置游记攻略
                if (ObjectUtils.isNotEmpty(bean.getStreagy().getTitle())) {
                    ll_line.setVisibility(View.VISIBLE);
                    GlideApp.with(mContext).load(bean.getStreagy().getCover()).placeholder(R.mipmap.common_ba_banner).error(R.mipmap.common_ba_banner).into(mImgScenic);
                    mChildstrategyId = bean.getStreagy().getId() + "";
                    mLineTitle.setText(bean.getStreagy().getTitle());
                    tv_seven.setText(bean.getStreagy().getEnshrine() + "");
                    tv_time.setText(TimeUtils.millis2String(bean.getStreagy().getAddtime()) + "");
                    tv_point.setText(bean.getStreagy().getRecommend() + "");
                    tv_star.setText(bean.getStreagy().getEnshrine() + "");
                    tv_msg.setText(bean.getStreagy().getCommentNum() + "");
                } else {
                    ll_line.setVisibility(View.GONE);
                }

                if (ObjectUtils.isNotEmpty(bean.getBestShooting())) {
                    ll_adress.setVisibility(View.VISIBLE);
                    setExpandTextview(extv_adress, bean.getBestShooting(), bean.getBestShooting());
                } else {
                    ll_adress.setVisibility(View.GONE);
                }
                if (ObjectUtils.isNotEmpty(bean.getIntroduce())) {
                    extv.setVisibility(View.VISIBLE);
                    setExpandTextview(extv, ComUtils.deleteHtmlImg(bean.getIntroduce()),
                            bean.getIntroduce());
                } else {
                    extv.setVisibility(View.GONE);
                }
                if (ObjectUtils.isNotEmpty(bean.getTips().getOpentime())) {
                    ll_time.setVisibility(View.VISIBLE);
                    setExpandTextview2(extv_time, bean.getTips().getOpentime(),
                            bean.getTips().getOpentime());
                } else {
                    ll_time.setVisibility(View.GONE);
                }
                if (ObjectUtils.isNotEmpty(bean.getTips().getTrafficInformation())) {
                    ll_traffic.setVisibility(View.VISIBLE);
                    setExpandTextview2(extv_traffic, bean.getTips().getTrafficInformation(),
                            bean.getTips().getTrafficInformation());
                } else {
                    ll_traffic.setVisibility(View.GONE);
                }
                if (ObjectUtils.isNotEmpty(bean.getTips().getTicket())) {
                    ll_gongl.setVisibility(View.VISIBLE);
                    setExpandTextview2(extv_gongl, bean.getTips().getTicket(),
                            bean.getTips().getTicket());
                } else {
                    ll_gongl.setVisibility(View.GONE);
                }
                if (ObjectUtils.isNotEmpty(bean.getTips().getNotice().get(0).getContent())) {
                    ll_travel.setVisibility(View.VISIBLE);
                    setExpandTextview2(extv_travel,
                            ComUtils.deleteHtmlImg(bean.getTips().getNotice().get(0).getContent()), bean.getTips().getNotice().get(0).getContent());
                } else {
                    ll_travel.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            GlideUtils.loadImage(mContext, imgTop, bean.getPictureTwoToOne(),
                    R.mipmap.common_ba_banner);
            if (ComUtils.isDay()) {
                GlideUtils.loadImage(mContext, imgWeather, bean.getCurDateWeather().getPic_d(), 0);
                mTvWeather.setText(bean.getCurDateWeather().getTxt_d() + " " + bean.getCurDateWeather().getMin() + "℃-" + bean.getCurDateWeather().getMax() + "℃");
            } else {
                GlideUtils.loadImage(mContext, imgWeather, bean.getCurDateWeather().getPic_n(), 0);
                mTvWeather.setText(bean.getCurDateWeather().getTxt_n() + " " + bean.getCurDateWeather().getMin() + "℃-" + bean.getCurDateWeather().getMax() + "℃");
            }
            if (bean.getPics().size() > 0) {
                mTvImgList.setVisibility(View.VISIBLE);
                for (int i = 0; i < bean.getPics().size(); i++) {
                    imgList.add(bean.getPics().get(i).getImgPath());
                }
                mTvImgList.setText(bean.getPics().size() + "");
            } else {
                mTvImgList.setText("0");
                mTvImgList.setVisibility(View.GONE);
            }
            if (!bean.getAudioPath().isEmpty() && bean.getAudioPath() != null) {
                imgVoice.setVisibility(View.VISIBLE);
                voiceName = bean.getName();
                voiceUrl = bean.getAudioPath();
                cover = bean.getPicture();
            } else {
                imgVoice.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 封面
     */
    private String cover = "";
    /**
     * 语音名字
     */
    private String voiceName = "";
    /**
     * 语音url
     */
    private String voiceUrl = "";

    /**
     * 设置张开的数据
     *
     * @param tv
     * @param content
     */
    private void setExpandTextview(ExpandTextView tv, String content, String webContent) {
        // 设置TextView可展示的宽度 ( 父控件宽度 - 左右margin - 左右padding）
        int whidth = ScreenUtils.getScreenWidth() - SizeUtils.dp2px(16 * 2);
        tv.initWidth(whidth);
        tv.setMaxLines(3);
        tv.setCloseText(content);
        tv.setWebContent(webContent);
    }

    /**
     * 设置张开的数据
     *
     * @param tv
     * @param content
     */
    private void setExpandTextview2(ExpandTextView tv, String content, String webContent) {
        // 设置TextView可展示的宽度 ( 父控件宽度 - 左右margin - 左右padding）
        int whidth = ScreenUtils.getScreenWidth() - SizeUtils.dp2px(30 * 2) - tvExWidth.getWidth();
        tv.initWidth(whidth);
        tv.setMaxLines(2);
        tv.setCloseText(content);
        tv.setWebContent(webContent);
    }

    @Override
    public void setMapData(GuideDetail detail) {
        if (ObjectUtils.isNotEmpty(detail)) {
            try {
                if (ObjectUtils.isNotEmpty(detail.getCoverTwoToOne())) {
                    llMap.setVisibility(View.VISIBLE);
                    GlideApp.with(this).load(detail.getCoverTwoToOne()).placeholder(R.mipmap.common_ba_banner).error(R.mipmap.common_ba_banner).apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(10, 0)).diskCacheStrategy(DiskCacheStrategy.RESOURCE).skipMemoryCache(true)).into(imgMap);
                    tvMapTag.setText(detail.getName());
                }
            } catch (Exception e) {
                llMap.setVisibility(View.GONE);
                e.printStackTrace();
            }
        } else {
            llMap.setVisibility(View.GONE);
        }
    }

    @Override
    public void setChildScenic(List<ScenicChild> list) {
        try {
            if (list.size() > 0) {
                ll_must_go.setVisibility(View.VISIBLE);
                mAdapter.setNewData(list);
            } else {
                ll_must_go.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            ll_must_go.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }

    @Override
    public void setScenicVideo(List<ScenicVideo> list) {
        try {
            if (list.size() > 0) {
                ll_video.setVisibility(View.VISIBLE);
                mVideoAdapter.setNewData(list);
            } else {
                ll_video.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            ll_video.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }

    @Override
    public void addMapMarker(List<FoundNearEy> list) {
        if (ObjectUtils.isNotEmpty(list) && list.size() > 0) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (int i = 0; i < list.size(); i++) {
                LatLng latLng = new LatLng(Double.parseDouble(list.get(i).getLat()),
                        Double.parseDouble(list.get(i).getLon()));

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.title(list.get(i).getTitle()).position(latLng).draggable(false);
                if (list.get(i).getType().equals("HOTEL")) {
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.detail_jq_zbzy_point_hotel));
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
        } else {
            // 没有数据就到中心点
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            LatLng latLng = new LatLng(Double.parseDouble(ProjectConfig.COMMON_LAT),
                    Double.parseDouble(ProjectConfig.COMMON_LNG));
            builder.include(latLng);
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
            TextView line = (TextView) footer.findViewById(R.id.tv_line);
            line.setVisibility(View.GONE);
            foot.setText("查看全部" + allpeople + "条评价信息");
            foot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ARouter.getInstance().build(Constant.ACTIVITY_COMMENTMORE).withString("id",
                            strategyId).withString("type", SourceType.RESOURCE_SCENIC).withString("TYPE", "0").withString("name", name).navigation();
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

    @OnClick({R.id.img_sceven, R.id.img_real, R.id.tv_img_show, R.id.ll_phone, R.id.tv_map_in,
            R.id.tv_around, R.id.tv_video, R.id.tv_must_go, R.id.tv_record_star,
            R.id.tv_record_comment, R.id.tv_chang, R.id.tv_comment_write, R.id.tv_scenic_route,
            R.id.tv_scenic_order, R.id.ll_address, R.id.tv_line, R.id.img_index_scenic,
            R.id.ll_hotel, R.id.ll_food, R.id.ll_car, R.id.ll_traffics, R.id.img_voice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_hotel:
                ComUtils.goToNearMapWitnType(currentLat, currentLon, name, currentAddress, this, 2);
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
            // 身边游
            case R.id.img_index_scenic:
                if (ObjectUtils.isNotEmpty(mChildstrategyId)) {
                    ARouter.getInstance().build(Constant.ACTIVITY_LINEDETAIL).withString("mId",
                            mChildstrategyId).navigation();
                }
                break;
            case R.id.tv_line:
                ARouter.getInstance().build(Constant.ACTIVITY_LINE).withString("sourceId",
                        strategyId).navigation();
                break;
            case R.id.tv_scenic_route:
                if ("0".equals(isJourney)) {
                    LoadingDialog.showDialogForLoading(this);
                    CommonRequest.checkedToken(new CommonRequest.OnStateCallBack() {
                        @Override
                        public void result(int value) {
                            // 0未失效
                            if (0 == value) {
                                mCanlenderDialog.show();
                                loadingtip.setLoadingTip(LoadingTip.LoadStatus.loading);
                                mDiaWeb.addJavascriptInterface(new RequestHtmlData(),
                                        "appDataConfigDetail");
                                if (ObjectUtils.isNotEmpty(routId)) {
                                    WebUtils.setWebInfo3(mDiaWeb,
                                            URLConstant.TRIP_URL + routId + "/请选择出游日期", loadingtip);
                                } else {
                                    creatRoot();
                                }
                            } else {
                                LoadingDialog.cancelDialogForLoading();
                                ARouter.getInstance().build(Constant.ACTIVITY_LOGIN).navigation();
                            }
                        }
                    });
                }
                break;
            case R.id.tv_scenic_order:
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
                                        if (isService) {
                                            ARouter.getInstance().build(Constant.ACTIVITY_BASEWEB).withString("HTMLTITLE", "--").withString("HTMLURL", url).navigation();
                                        } else {
                                            ARouter.getInstance().build(Constant.ACTIVITY_USER_SERVICE).withString("HTMLURL", url).navigation();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    ARouter.getInstance().build(Constant.ACTIVITY_BASEWEB).withString("HTMLTITLE", name).withString("HTMLURL", orDingPath).navigation();
                                }
                            } else {
                                ARouter.getInstance().build(Constant.ACTIVITY_LOGIN).navigation(ScenicDetailActivity.this, 1);
                            }
                        }
                    });
                }
                break;
            // 在线预定
            case R.id.tv_comment_write:
                // 在线预定
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
                                    try {
                                        String aesphone = AESEncryptUtil.Encrypt16(phone);
                                        String url = orDingPath + "&phone=" + aesphone;
                                        boolean isService =
                                                SPUtils.getInstance().getBoolean(SPCommon.SERVICE
                                                        , false);
                                        if (isService) {
                                            ARouter.getInstance().build(Constant.ACTIVITY_BASEWEB).withString("HTMLTITLE", "--").withString("HTMLURL", url).navigation();
                                        } else {
                                            ARouter.getInstance().build(Constant.ACTIVITY_USER_SERVICE).withString("HTMLURL", url).navigation();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    ARouter.getInstance().build(Constant.ACTIVITY_BASEWEB).withString("HTMLTITLE", name).withString("HTMLURL", orDingPath).navigation();
                                }
                            } else {
                                ARouter.getInstance().build(Constant.ACTIVITY_LOGIN).navigation(ScenicDetailActivity.this, 1);
                            }
                        }
                    });
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
                            ARouter.getInstance().build(Constant.ACTIVITY_WRITECOMMENT).withString("name", name).withString("id", strategyId).withString("type", SourceType.RESOURCE_SCENIC).withString("TYPE", "0").navigation(ScenicDetailActivity.this, 100);
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
            // 必去景点
            case R.id.tv_must_go:
                ARouter.getInstance().build(Constant.ACTIVITY_SCENIC_CHILD).withString("mId",
                        strategyId).navigation();
                break;
            // 景区视频
            case R.id.tv_video:
                ARouter.getInstance().build(Constant.ACTIVITY_SCENIC_VIDEO).withString("mId",
                        strategyId).navigation();
                break;
            case R.id.tv_around:
                ComUtils.goToNearMap(currentLat, currentLon, name, currentAddress, this);
                break;
            case R.id.img_sceven:
                // 720
                if (ObjectUtils.isNotEmpty(panoramaPathApp)) {
                    ARouter.getInstance().build(Constant.ACTIVITY_BASEWEB).withString("HTMLURL",
                            panoramaPathApp).withString("HTMLTITLE", name).navigation();
                }
                break;
            case R.id.img_real:
                // 全景
                if (ObjectUtils.isNotEmpty(videoPath)) {
                    ARouter.getInstance().build(Constant.ACTIVITY_VIDEO_PLAY).withString("content"
                            , videoPath).navigation();
                }
                break;
            case R.id.tv_map_in:
                if (ObjectUtils.isNotEmpty(mGuideId)) {
                    ComUtils.goToGuide(Integer.parseInt(mGuideId), this, true);
                } else {
                    ToastUtils.showLong("暂无数据!");
                }
                break;
            case R.id.ll_phone:
                ComUtils.showQueryCancleDialogPhone(this, phone);
                break;
            case R.id.ll_address:
                if (!Utils.gethaveNet(this)) {
                    ShowToast.showText(this, "网络错误，无法进行导航操作");
                } else {
                    if (Utils.isnotNull(SPUtils.getInstance().getString(SPCommon.LATITUDE)) && Utils.isnotNull(SPUtils.getInstance().getString(SPCommon.LONGITUDE)) && Utils.isnotNull(currentLat) && Utils.isnotNull(currentLon)) {
                        MapNaviUtils.isMapNaviUtils(this,
                                SPUtils.getInstance().getString(SPCommon.LATITUDE),
                                SPUtils.getInstance().getString(SPCommon.LONGITUDE),
                                SPUtils.getInstance().getString(SPCommon.CITY_ADDRESS),
                                currentLat, currentLon, Utils.isnotNull(currentAddress) ?
                                        Utils.tr(currentAddress) : "目的地");
                    } else {
                        ShowToast.showText(this, "数据异常，无法进行导航操作");
                    }
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
            case R.id.img_voice:
                // 语音
                Intent intent = new Intent(this, AudioActivity.class);
                intent.putExtra("name", voiceName);
                intent.putExtra("audio", voiceUrl);
                intent.putExtra("img", cover);
                startActivity(intent);
                break;
            default:
                break;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 0) {
            presenter.getCommonInfoData(strategyId, SourceType.RESOURCE_SCENIC);
            // 登录
        } else if (requestCode == 1 && resultCode == 3) {
            getUserInfo();
        }
    }

    /**
     * 加载网页日历弹框
     */
    BaseDialog mCanlenderDialog;
    private LoadingTip loadingtip;
    /**
     * 日历
     */
    private ProgressWebView mDiaWeb;

    private void initCanlender() {
        mCanlenderDialog = new BaseDialog(this);
        mCanlenderDialog.contentView(R.layout.dialog_cander_selected).gravity(Gravity.BOTTOM).animType(BaseDialog.AnimInType.BOTTOM).layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)).canceledOnTouchOutside(true);
        mDiaWeb = (ProgressWebView) mCanlenderDialog.findViewById(R.id.dialog_web);
        loadingtip = (LoadingTip) mCanlenderDialog.findViewById(R.id.loadingtip);
    }

    /**
     * 网页交互的实体类
     *
     * @author 黄熙
     * @version 1.0.0
     * @date 2018/12/10 0010
     * @since JDK 1.8
     */
    public class RequestHtmlData {
        /**
         * 获取相关的基础数据
         *
         * @return
         */
        @JavascriptInterface
        public String getBaseInfo() {
            String result = "";
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("token", SPUtils.getInstance().getString(SPCommon.TOKEN));
                jsonObject.put("url", ProjectConfig.BASE_URL);
                jsonObject.put("siteCode", ProjectConfig.SITECODE);
                jsonObject.put("lat", ProjectConfig.COMMON_LAT);
                jsonObject.put("lng", ProjectConfig.COMMON_LNG);
                jsonObject.put("name", ProjectConfig.CITY_NAME);
                jsonObject.put("zoom", SourceConstant.ZOOM);
                jsonObject.put("region", ProjectConfig.REGION);
                jsonObject.put("theme", ProjectConfig.ROUTE_THEME_COLOR);
                result = jsonObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        /**
         * 隐藏弹框
         *
         * @return
         */
        @JavascriptInterface
        public void hidDialog() {
            if (ObjectUtils.isNotEmpty(mCanlenderDialog)) {
                mCanlenderDialog.dismiss();
            }
        }

        /**
         * 获取日历时间
         *
         * @param date
         */
        @JavascriptInterface
        public void getCalendarData(String date) {
            LogUtils.e("你的时间-->" + date);
            mCanlenderDialog.dismiss();
            LoadingDialog.showDialogForLoading(ScenicDetailActivity.this);
            joinJourny(routId, date);
        }

    }

    /**
     * 加入行程
     */
    private void joinJourny(String id, String date) {
        RetrofitHelper.getApiService().joinJourny(id, strategyId, date).subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BaseResponse>() {
            @Override
            public void accept(BaseResponse baseResponse) throws Exception {
                LoadingDialog.cancelDialogForLoading();
                if (baseResponse.getCode() == 0) {
                    ToastUtils.showShort("加入成功!");
                    Intent intent = new Intent();
                    intent.putExtra("mRouteId", id);
                    setResult(2, intent);
                    finish();
                } else {
                    ToastUtils.showShort(baseResponse.getMessage());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                LoadingDialog.cancelDialogForLoading();
                ToastUtils.showShort("请求错误,请稍后重试!");
            }
        });
    }

    /**
     * 创建行程
     */
    private void creatRoot() {
        RetrofitHelper.getApiService().createJourny("新增行程").subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BaseResponse<RouteEnty>>() {
            @Override
            public void accept(BaseResponse<RouteEnty> bean) throws Exception {
                LoadingDialog.cancelDialogForLoading();
                if (bean.getCode() == 0 && ObjectUtils.isNotEmpty(bean.getData().getId())) {
                    routId = bean.getData().getId();
                    WebUtils.setWebInfo3(mDiaWeb, URLConstant.TRIP_URL + routId + "/请选择出游日期",
                            loadingtip);
                } else {
                    LoadingDialog.cancelDialogForLoading();
                    ToastUtils.showShort("加入失败!");
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                LoadingDialog.cancelDialogForLoading();
                ToastUtils.showShort("请求失败!");
                LoadingDialog.cancelDialogForLoading();
            }
        });
    }
}
