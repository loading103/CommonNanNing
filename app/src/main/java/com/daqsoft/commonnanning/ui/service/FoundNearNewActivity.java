package com.daqsoft.commonnanning.ui.service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.bumptech.glide.request.RequestOptions;
import com.daqsoft.android.ProjectConfig;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.base.IApplication;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.common.URLConstant;
import com.daqsoft.commonnanning.helps_gdmap.CompleteFuncData;
import com.daqsoft.commonnanning.helps_gdmap.HelpMaps;
import com.daqsoft.commonnanning.helps_gdmap.MapNaviUtils;
import com.daqsoft.commonnanning.ui.entity.FoundNearEy;
import com.daqsoft.commonnanning.ui.entity.FoundNearTagEty;
import com.daqsoft.utils.Utils;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.glide.GlideApp;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.rx.permission.RxPermissions;
import com.example.tomasyb.baselib.util.LogUtils;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.LoadingDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.agora.yview.dialog.BaseDialog;
import io.reactivex.functions.Consumer;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Call;
import okhttp3.Request;

/**
 * ????????????????????????????????????
 *
 * @author ??????
 * @version 1.0.0
 * @date 2018.05.07
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_FOUNDNEAR)
public class FoundNearNewActivity extends BaseActivity implements AMap.OnMarkerClickListener,
        AMap.InfoWindowAdapter {
    @BindView(R.id.rv_tag)
    RecyclerView mRvTag;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.viewanimator)
    ViewAnimator mVa;
    @BindView(R.id.img_more)
    ImageView mImgHide;
    @BindView(R.id.ll_tag)
    LinearLayout llTag;
    @BindView(R.id.head)
    HeadView mHead;
    @BindView(R.id.no_date_img)
    ImageView noDateImg;
    @BindView(R.id.no_data_content)
    TextView noDataContent;
    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;
    /**
     * ??????????????????????????????????????????????????????????????????????????????
     * 0 ????????????
     * 1 ???????????????????????????????????????????????????
     */
    private int type = 0;
    private TextureMapView mMapView;

    /**
     * ????????????
     */
    private AMap aMap;
    /**
     * ????????????
     */
    private UiSettings mUiSettings;
    /**
     * ??????
     */
    private LatLng commonLatLng;

    /**
     * ?????????
     */
    private BaseQuickAdapter<FoundNearEy, BaseViewHolder> mAdapters;
    /**
     * ??????
     */
    private List<FoundNearEy> mDatas;
    /**
     * ????????????
     */
    private List<FoundNearTagEty> mTagDatas;
    /**
     * ??????????????????marker
     */
    private List<Marker> markerList = new ArrayList<>();

    /**
     * ???????????????????????????????????????????????????35??????????????????
     */
    private double checkDistance;
    /**
     * ??????
     */
    private int mPage = 1;

    /**
     * ????????????
     *
     * @param sType ??????
     */

    /**
     * ??????
     */
    private String distance = "500";
    /**
     * ??????
     */
    private String latitude = "";
    /**
     * ????????????
     */
    private String address = "";
    /**
     * ??????
     */
    private String longitude = "";
    /**
     * ????????????
     */
    private int limitPage = 15;
    /**
     * ?????????????????????
     */
    private int mCurentType = 1;
    /**
     * ??????
     */
    private String title = "?????????";
    /**
     * ???????????????????????????
     */
    private int iconMarker = R.mipmap.found_travel_around_map_attractions;

    /**
     * ????????????
     */
    private String region = "";
    /**
     * ???????????????
     */
    private int selected = 0;
    /**
     * ??????
     */
    private BaseDialog dialog;
    /**
     * ?????????????????????
     */
    private List<Integer> tagImage = Arrays.asList(R.drawable.select_near_scenic, R.drawable
            .select_near_scenic2, R.drawable.select_near_scenic3, R.drawable.select_near_scenic4,
            R.drawable.select_near_scenic5, R.drawable.select_near_scenic8, R.drawable
                    .select_near_scenic10, R.drawable.select_near_scenic11, R.drawable
                    .select_near_scenic12, R.drawable.select_near_scenic14, R.drawable
                    .select_near_scenic15);

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView = (TextureMapView) findViewById(R.id.map);
        RxPermissions rxPermission = new RxPermissions(this);
        rxPermission.request(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                // ??????
                if (aBoolean) {
                    LogUtils.e("??????????????????");
                    mMapView.onCreate(savedInstanceState);
                    // ??????
                    if (aMap == null) {
                        aMap = mMapView.getMap();
                        mUiSettings = aMap.getUiSettings();
                        mUiSettings.setZoomControlsEnabled(false);
                        // ????????????marker???????????????
                        aMap.setOnMarkerClickListener(FoundNearNewActivity.this);
                        aMap.setInfoWindowAdapter(FoundNearNewActivity.this);
                    }
                    initFirst();
                } else {
                    Toast.makeText(mContext, "????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * ???????????????
     *
     * @param latLng
     */
    public void setCenterMap(LatLng latLng) {
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_found_near_new;
    }

    /**
     * ?????????????????????
     */
    private LatLng mCenterLat;

    /**
     * ???????????????
     */
    void initLocation() {
        HelpMaps.startLocation(new CompleteFuncData() {
            @Override
            public void success(AMapLocation loc) {
                if (ObjectUtils.isNotEmpty(loc)) {
                    double lat = loc.getLatitude();
                    double lng = loc.getLongitude();
                    mCenterLat = new LatLng(lat, lng);
                    Marker marker = aMap.addMarker(new MarkerOptions().icon
                            (BitmapDescriptorFactory.fromResource(R.mipmap
                                    .guide_current_location)).position(mCenterLat).draggable
                            (false));
                    HelpMaps.stopLocation();
                }
            }
        });

    }

    /**
     * ??????????????????
     */
    private LatLng selfLatLng;

    private BaseQuickAdapter<FoundNearTagEty, BaseViewHolder> mTagAdapter;

    @Override
    public void initView() {
    }


    private void initFirst() {
        initAll();
        latitude = SPUtils.getInstance().getString("latitude");
        longitude = SPUtils.getInstance().getString("longitude");
        address = SPUtils.getInstance().getString(SPCommon.CITY_ADDRESS);
        if (ObjectUtils.isNotEmpty(latitude) && ObjectUtils.isNotEmpty(longitude)) {
//            selfLatLng = new LatLng(Double.valueOf(SPUtils.getInstance().getString("latitude")),
//                    Double.valueOf(SPUtils.getInstance().getString("longitude")));
            selfLatLng = new LatLng(Double.valueOf(latitude),
                    Double.valueOf(longitude));
            aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(selfLatLng, 15));
            commonLatLng = new LatLng(Double.valueOf(ProjectConfig.COMMON_LAT), Double.valueOf
                    (ProjectConfig.COMMON_LNG));
            if (type == 0) {
                mCurentType = getIntent().getIntExtra("curentType", 1);
                checKLocation();
            } else {
                mCurentType = getIntent().getIntExtra("curentType", 1);
                latitude = getIntent().getStringExtra("lat");
                longitude = getIntent().getStringExtra("lng");
                getDatas(true, mCurentType);
            }
            if (mCurentType >= 1) {
                selected = mCurentType;
            }
            checkTagData();
        } else {
            latitude="30.509214";
            longitude="104.084845";
            address="???????????????????????????????????????170?????????AI????????????";
            selfLatLng = new LatLng(Double.valueOf(latitude),
                    Double.valueOf(longitude));
            aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(selfLatLng, 15));
            commonLatLng = new LatLng(Double.valueOf(ProjectConfig.COMMON_LAT), Double.valueOf
                    (ProjectConfig.COMMON_LNG));
            if (type == 0) {
                mCurentType = getIntent().getIntExtra("curentType", 1);
                checKLocation();
            } else {
                mCurentType = getIntent().getIntExtra("curentType", 1);
                latitude = getIntent().getStringExtra("lat");
                longitude = getIntent().getStringExtra("lng");
                getDatas(true, mCurentType);
            }
            if (mCurentType >= 1) {
                selected = mCurentType;
            }
            checkTagData();


        }
    }

    /**
     * ?????????????????????
     */
    private void initAll() {
        mHead.setTitle("?????????");
        mHead.setBackListener(new HeadView.OnBackListener() {
            @Override
            public void onBack(View v) {
                finish();
            }
        });
        type = getIntent().getIntExtra("type", 0);
        mDatas = new ArrayList<>();
        mTagDatas = new ArrayList<>();
        initTagAdapter();
        initAdapter();
    }

    /**
     * ??????Tag????????????
     */
    public void checkTagData() {
        String[] tagArr = getResources().getStringArray(R.array.found_near_tag);
        for (int i = 0; i < tagArr.length; i++) {
            FoundNearTagEty bean = new FoundNearTagEty();
            bean.setName(tagArr[i]);
            int curenttype = 0;
            if (i == 5) {
                curenttype = 8;
                bean.setmType(8);
            } else if (i == 6) {
                curenttype = 10;
                bean.setmType(10);
            } else if (i == 7) {
                curenttype = 11;
                bean.setmType(11);
            }
//            else if (i == 8) {
//                curenttype = 12;
//                bean.setmType(12);
//            }
            else if (i == 9) {
                curenttype = 14;
                bean.setmType(14);
            } else if (i == 10) {
                curenttype = 15;
                bean.setmType(15);
            } else {
                curenttype = i + 1;
                bean.setmType(i + 1);
            }
            if (i < tagImage.size()) {
                bean.setImage(tagImage.get(i));
            }
            if (curenttype == selected) {
                LogUtils.e("????????????" + bean.getmType());
                bean.setSceted(true);
            } else {
                bean.setSceted(false);
            }
            mTagDatas.add(bean);
        }
        mTagAdapter.notifyDataSetChanged();
        for (int i = 0; i < mTagDatas.size(); i++) {
            if (mTagDatas.get(i).isSceted()) {
                mRvTag.scrollToPosition(i);
            }
        }
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    /**
     * ?????????tag?????????
     */
    public void initTagAdapter() {
        mRvTag.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));
        mTagAdapter = new BaseQuickAdapter<FoundNearTagEty, BaseViewHolder>(R.layout
                .item_left_img_righttv, mTagDatas) {
            @Override
            protected void convert(final BaseViewHolder helper, final FoundNearTagEty item) {
                TextView mTv = (TextView) helper.getView(R.id.tv_region);
                helper.setText(R.id.tv_region, item.getName());
                Drawable drawableLeft = null;
                drawableLeft = getResources().getDrawable(item.getImage());

                mTv.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null);
                mTv.setCompoundDrawablePadding(5);
                if (item.isSceted()) {
                    mTv.setSelected(true);
                } else {
                    mTv.setSelected(false);
                }
                helper.setOnClickListener(R.id.tv_region, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (FoundNearTagEty bean : mTagDatas) {
                            bean.setSceted(false);
                        }
                        item.setSceted(true);
                        mTagAdapter.notifyDataSetChanged();
                        mCurentType = item.getmType();
                        getDatas(true, item.getmType());
                    }
                });
            }
        };
        mRvTag.setAdapter(mTagAdapter);
    }

    /**
     * ??????????????????
     */
    public void initAdapter() {
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapters = new BaseQuickAdapter<FoundNearEy, BaseViewHolder>(R.layout
                .item_around_scenic, mDatas) {
            @Override
            protected void convert(BaseViewHolder helper, final FoundNearEy item) {
                if (item.getType().equals("12") || item.getType().equals("10") || item.getType()
                        .equals("11") || item.getType().equals("15")||item.getType().equals("9") ||item.getType().equals("4")||item.getType().equals("14")) {
                    // ?????????????????????
                    helper.getView(R.id.item_scenic_img).setVisibility(View.GONE);
                } else {
                    GlideApp.with(mContext).load(item.getImg()).placeholder(R.mipmap
                            .common_image_small).error(R.mipmap.common_image_small).apply
                            (RequestOptions.bitmapTransform(new RoundedCornersTransformation(10,
                                    0))).into((ImageView) helper.getView(R.id.item_scenic_img));
                    helper.setVisible(R.id.item_scenic_img, true);
                }
                helper.setText(R.id.gonggao_tv_title, item.getTitle());
                helper.setOnClickListener(R.id.tv_go, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Utils.isnotNull(item.getLat()) && Utils.isnotNull(item.getLon())) {
                            MapNaviUtils.isMapNaviUtils(FoundNearNewActivity.this, SPUtils
                                    .getInstance().getString(SPCommon.LATITUDE), SPUtils
                                    .getInstance().getString(SPCommon.LONGITUDE), SPUtils
                                    .getInstance().getString(SPCommon.CITY_ADDRESS), item.getLat
                                    (), item.getLon(), Utils.isnotNull(item.getAdress()) ? Utils
                                    .tr(item.getAdress()) : "?????????");
                        } else {
                            ToastUtils.showShort("???????????????????????????????????????");
                        }
                    }
                });
                helper.setText(R.id.tv_bottom, "?????????" + item.getDistance() + "Km");
                if (item.getType().equals("1")) {
                    helper.setVisible(R.id.gonggao_tv_content, true);
                    helper.setVisible(R.id.tv_yuding, true);
                    helper.setVisible(R.id.around_tv_score, true);
                    helper.setText(R.id.around_tv_score, item.getCommentLevel().equals("0") ?
                            "4.0???" : item.getCommentLevel() + "???");
                    helper.setText(R.id.gonggao_tv_content, item.getLevelname());
                    helper.setOnClickListener(R.id.tv_yuding, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // ??????????????????
                            ARouter.getInstance().build(Constant.ACTIVITY_SCENIC_DETAIL)
                                    .withString("mId", item.getId()).navigation();
                        }
                    });
                } else if (item.getType().equals("2")) {
                    helper.setVisible(R.id.gonggao_tv_content, true);
                    helper.setVisible(R.id.tv_yuding, true);
                    helper.setVisible(R.id.around_tv_score, true);
                    helper.setText(R.id.around_tv_score, item.getCommentLevel().equals("0") ?
                            "4.0???" : item.getCommentLevel() + "???");
                    helper.setText(R.id.gonggao_tv_content, item.getLevelname());
                    helper.setOnClickListener(R.id.tv_yuding, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            /**
                             * ??????????????????
                             */
                            ARouter.getInstance().build(Constant.ACTIVITY_HOTEL_DETAIL)
                                    .withString("mId", item.getId()).navigation();
                        }
                    });
                } else {
                    helper.setVisible(R.id.tv_yuding, false);
                    helper.setVisible(R.id.around_tv_score, false);
                    helper.setVisible(R.id.gonggao_tv_content, false);
                }
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mMapView != null) {
                            Marker marker = getMarket(item.getTitle());
                            if (marker != null) {
                                aMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double
                                        .parseDouble(item.getLat()), Double.parseDouble(item
                                        .getLon()))));
                                if (marker.isInfoWindowShown()) {
                                    marker.hideInfoWindow();
                                    return;
                                }
                                marker.showInfoWindow();

                            }
                        }
                    }
                });

            }
        };
        mAdapters.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener()

        {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
        mRv.setAdapter(mAdapters);
    }


    /**
     * ??????????????????????????????????????????????????????
     */
    public void checKLocation() {
        // ??????
        checkDistance = (AMapUtils.calculateLineDistance(selfLatLng, commonLatLng)) / 1000;
        if (checkDistance > 35) {
            LogUtils.e("??????" + ProjectConfig.CITY_NAME + "???");
            if (!IApplication.getInstance().isCheckLocationMap) {
                dialog = new BaseDialog(this);
                dialog.contentView(R.layout.dialog_base_center).gravity(0).canceledOnTouchOutside
                        (true).show();
                dialog.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // ??????
                        dialog.dismiss();
                        IApplication.getInstance().isCheckLocationMap = true;
                        if (!Utils.isnotNull(ProjectConfig.COMMON_LAT)) {
                            ToastUtils.showShort("??????????????????????????????");
                            return;
                        }
                        latitude = ProjectConfig.COMMON_LAT;
                        longitude = ProjectConfig.COMMON_LNG;
                        aMap.moveCamera(CameraUpdateFactory.changeLatLng(commonLatLng));
                        getDatas(true, mCurentType);
                    }
                });
                dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        getDatas(true, mCurentType);
                    }
                });
                TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
                title.setText("???????????????????????????" + ProjectConfig.CITY_NAME + "\n" + "???????????????" +
                        ProjectConfig.CITY_NAME + "?");
            } else {
                latitude = ProjectConfig.COMMON_LAT;
                longitude = ProjectConfig.COMMON_LNG;
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(commonLatLng));
                getDatas(true, mCurentType);
            }
        } else {
            getDatas(true, mCurentType);
        }
    }


    /**
     * ????????????
     */
    private void loadMore() {
        getDatas(false, mCurentType);

    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        // ???????????????????????????marker???show????????????
        marker.showInfoWindow();
        return false;
    }


    /**
     * ????????????????????????
     */
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }


    /**
     * ????????????
     *
     * @param isRefresh
     * @param list
     */
    private void setData(boolean isRefresh, List<FoundNearEy> list) {
        mPage++;
        final int size = list == null ? 0 : list.size();
        if (isRefresh) {
            if (size == 0) {
                mVa.setDisplayedChild(1);
            }
            markerList.clear();
            mDatas.clear();
            aMap.clear();
            mDatas.addAll(list);
            mAdapters.notifyDataSetChanged();
            for (int i = 0; i < list.size(); i++) {
                // ??????????????????
                double lat = Double.valueOf(list.get(i).getLat());
                double lng = Double.valueOf(list.get(i).getLon());
                LatLng mCurrentlat = new LatLng(lat, lng);
                matchMarkIcon(list.get(i).getType());
                Marker marker = aMap.addMarker(new MarkerOptions().title("").icon
                        (BitmapDescriptorFactory.fromResource(iconMarker)).position(mCurrentlat)
                        .draggable(false));
                FoundNearEy bean = new FoundNearEy();
                bean.setTitle(list.get(i).getTitle());
                bean.setLat(list.get(i).getLat());
                bean.setLon(list.get(i).getLon());
                bean.setAdress(list.get(i).getAdress());
                marker.setTitle(list.get(i).getTitle());
                marker.setObject(bean);
                markerList.add(marker);
            }

            if (type == 1) {
                // ???????????????????????????
                setCenterMap(new LatLng(Double.parseDouble(latitude), Double.parseDouble
                        (longitude)));
                LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble
                        (longitude));
                Marker marker = aMap.addMarker(new MarkerOptions().title("").icon
                        (BitmapDescriptorFactory.fromResource(R.mipmap.radio_select)).position
                        (latLng).draggable(false));
                FoundNearEy bean = new FoundNearEy();
                bean.setTitle(getIntent().getStringExtra("scenicname"));
                bean.setLat(latitude);
                bean.setLon(longitude);
                bean.setAdress(getIntent().getStringExtra("scenicadress"));
                marker.setTitle(getIntent().getStringExtra("scenicname"));
                marker.setObject(bean);
                marker.showInfoWindow();
                markerList.add(marker);
            }
            Utils.maoToCenter(aMap, markerList, null);

        } else {
            if (size > 0) {
                mDatas.addAll(list);
                mAdapters.notifyDataSetChanged();
                for (int i = 0; i < list.size(); i++) {
                    // ??????????????????
                    double lat = Double.valueOf(list.get(i).getLat());
                    double lng = Double.valueOf(list.get(i).getLon());
                    LatLng mCurrentlat = new LatLng(lat, lng);
                    matchMarkIcon(list.get(i).getType());
                    Marker marker = aMap.addMarker(new MarkerOptions().title("").icon
                            (BitmapDescriptorFactory.fromResource(iconMarker)).position
                            (mCurrentlat).draggable(false));
                    FoundNearEy bean = new FoundNearEy();
                    bean.setTitle(list.get(i).getTitle());
                    bean.setLat(list.get(i).getLat());
                    bean.setLon(list.get(i).getLon());
                    bean.setAdress(list.get(i).getAdress());
                    marker.setTitle(list.get(i).getTitle());
                    marker.setObject(bean);
                    markerList.add(marker);
                }
                Utils.maoToCenter(aMap, markerList, null);
            }
        }
        if (size < URLConstant.LIMITPAGE) {
            // ???????????????????????????????????????????????????????????????
            mAdapters.loadMoreEnd(isRefresh);
        } else {
            mAdapters.loadMoreComplete();
        }
    }


    /**
     * ????????????
     */
    public void getDatas(final boolean ifrefresh, final int type) {
        LogUtils.e("????????????--???" + type);
        if (ifrefresh) {
            mPage = 1;
            // ??????????????????????????????????????????????????????????????????
            mAdapters.setEnableLoadMore(false);

        }
        int  types=type;
        if(types==9){
            types=12;
        }
        LogUtils.e(longitude + "," + latitude);
        OkHttpUtils.get().url(ProjectConfig.BASE_URL + URLConstant.NEAR_LIST).addParams("lang",
                ProjectConfig.LANG).addParams("siteCode", ProjectConfig.SITECODE).addParams
                ("type", "1").addParams("sourceType", types + "").addParams("distance", distance +
                "").addParams("longitude", longitude + "").addParams("latitude", latitude + "")
                .addParams("page", mPage + "").addParams("region", region).addParams("limitPage",
                limitPage + "").build().execute(new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                LoadingDialog.showDialogForLoading(FoundNearNewActivity.this);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                LoadingDialog.cancelDialogForLoading();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                mVa.setDisplayedChild(1);
                mAdapters.setEnableLoadMore(true);
            }

            @Override
            public void onResponse(String response, int id) {
                LogUtils.e(response);
                try {
                    initLocation();
                    JSONObject object = JSONObject.parseObject(response);
                    JSONArray datasArr = object.getJSONArray("datas");
                    if (object.getIntValue("code") == 0) {
                        List<FoundNearEy> mlist = new ArrayList<>();
                        for (int i = 0; i < datasArr.size(); i++) {
                            JSONObject obj = datasArr.getJSONObject(i);
                            FoundNearEy bean = new FoundNearEy();
                            bean.setImg(obj.getString("logo"));
                            bean.setTitle(obj.getString("name"));
                            bean.setDistance(obj.getString("distance"));
                            bean.setAdress(Utils.isnotNull(obj.getString("address")) ? obj
                                    .getString("address") : "????????????");
                            bean.setLat(obj.getString("lat"));
                            bean.setLon(obj.getString("lon"));
                            bean.setId(obj.getString("id"));
                            bean.setCommentLevel(Utils.isnotNull(obj.getString("commentLevel")) ?
                                    obj.getString("commentLevel") : "0");
                            bean.setResid(obj.getString("rescode"));
                            bean.setLevelname(obj.getString("levelName"));
                            if (type == 1 || type == 2) {
                                bean.setIsshowxq(true);
                            } else {
                                bean.setIsshowxq(false);
                            }
                            bean.setType(type + "");
                            mlist.add(bean);
                        }
                        mVa.setDisplayedChild(0);
                        setData(ifrefresh, mlist);
                        mAdapters.setEnableLoadMore(true);
                    } else {
                        mVa.setDisplayedChild(1);
                    }
                } catch (Exception e) {
                    mVa.setDisplayedChild(1);
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View infoWindow = getLayoutInflater().inflate(R.layout.item_map_window, null);
        render(marker, infoWindow);
        return infoWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    /**
     * ?????????infowinfow??????
     */
    public void render(Marker marker, View view) {
        final FoundNearEy bean = (FoundNearEy) marker.getObject();
        TextView mapTitle = (TextView) view.findViewById(R.id.map_title);
        TextView mapContent = (TextView) view.findViewById(R.id.map_content);
        TextView mTvGo = (TextView) view.findViewById(R.id.map_go);
        mapTitle.setText(bean.getTitle());
        mapContent.setText(bean.getAdress());
        mTvGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isnotNull(bean.getLat()) && Utils.isnotNull(bean.getLon())) {
                    MapNaviUtils.isMapNaviUtils(FoundNearNewActivity.this, SPUtils.getInstance()
                            .getString(SPCommon.LATITUDE), SPUtils.getInstance().getString
                            (SPCommon.LONGITUDE), SPUtils.getInstance().getString(SPCommon
                            .CITY_ADDRESS), bean.getLat(), bean.getLon(), Utils.isnotNull(bean
                            .getTitle()) ? Utils.tr(bean.getTitle()) : "?????????");
                } else {
                    ToastUtils.showShort("???????????????????????????????????????");
                }
            }
        });
    }

    private boolean isKai = false;


    @OnClick({R.id.img_more, R.id.img_location})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_more:
                if (isKai) {
                    mVa.setVisibility(View.VISIBLE);
                    isKai = false;
                    mImgHide.setImageResource(R.mipmap.complaint_details_arrow_up_normal);
                } else {
                    mImgHide.setImageResource(R.mipmap.complaint_details_arrow_down_normal);
                    isKai = true;
                    mVa.setVisibility(View.GONE);
                }
                break;
            case R.id.img_location:
                if (ObjectUtils.isNotEmpty(mCenterLat)) {
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(mCenterLat));
                }
                break;
            default:
                break;
        }
    }


    private void matchMarkIcon(String type) {
        int mType = Integer.parseInt(type);
        switch (mType) {
            // ??????
            case 1:
                iconMarker = R.mipmap.near_icon_attractions_small;
                break;
            // ??????
            case 2:
                iconMarker = R.mipmap.near_icon_hotel_small;
                break;
            // ??????
            case 3:
                iconMarker = R.mipmap.near_icon_restaurant_small;
                break;
            // ??????
            case 4:
                iconMarker = R.mipmap.near_icon_shop_small;
                break;
            // ??????
            case 5:
                iconMarker = R.mipmap.near_icon_entertainment_small;
                break;
            // ?????????
            case 6:
                iconMarker = R.mipmap.near_icon_travelagency_small;
                break;
            //near_icon_place_small
            case 7:
                iconMarker = R.mipmap.near_icon_place_small;
                break;
            // ????????????
            case 8:
                iconMarker = R.mipmap.near_icon_food_small;
                break;
            case 9:
                iconMarker = R.mipmap.near_icon_country_small;
                break;
            case 10:
                iconMarker = R.mipmap.near_icon_parkinglot_small;
                break;
            case 11:
                iconMarker = R.mipmap.near_icon_gas_small;
                break;
            case 12:
                iconMarker = R.mipmap.near_icon_toilet_small;
                break;
            case 13:
                iconMarker = R.mipmap.near_icon_airport_small;
                break;
            case 14:
                iconMarker = R.mipmap.near_icon_station_small;
                break;
            case 15:
                iconMarker = R.mipmap.near_icon_hospital_small;
                break;
            default:
                break;

        }
    }


    /**
     * ??????????????????market
     *
     * @param title ??????
     * @return
     */
    public Marker getMarket(String title) {
        List<Marker> mapScreenMarkers = aMap.getMapScreenMarkers();
        for (int i = 0; i < mapScreenMarkers.size(); i++) {
            Marker marker = mapScreenMarkers.get(i);
            if (marker.getTitle() == null) {
                return null;
            }
            if (marker.getTitle().equals(title)) {
                return marker;
            }
        }
        return null;
    }

}
