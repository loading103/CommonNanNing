package com.daqsoft.commonnanning.ui.destination;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.location.AMapLocation;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.base.IApplication;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.ParamsCommon;
import com.daqsoft.commonnanning.common.URLConstant;
import com.daqsoft.commonnanning.helps_gdmap.CompleteFuncData;
import com.daqsoft.commonnanning.helps_gdmap.HelpMaps;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.FoodEntity;
import com.daqsoft.commonnanning.ui.entity.HotelEntity;
import com.daqsoft.commonnanning.ui.entity.IndexActivity;
import com.daqsoft.commonnanning.ui.entity.MyStrategyListBean;
import com.daqsoft.commonnanning.ui.entity.ScenicEntity;
import com.daqsoft.commonnanning.ui.service.FoundNearNewActivity;
import com.daqsoft.commonnanning.utils.DateUtil;
import com.daqsoft.commonnanning.utils.Utils;
import com.example.tomasyb.baselib.base.glide.GlideApp;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.refresh.SmartRefreshLayout;
import com.example.tomasyb.baselib.refresh.api.RefreshLayout;
import com.example.tomasyb.baselib.refresh.listener.OnRefreshListener;
import com.example.tomasyb.baselib.rvadapter.CommonAdapter;
import com.example.tomasyb.baselib.rvadapter.base.ViewHolder;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.widget.HeadView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * ?????????????????????
 *
 * @author ??????
 * @version 1.0.0
 * @date 2018/7/23 0023
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_DESTINATION_DETAILS)
public class DestinationDetailsActivity extends BaseActivity {

    @BindView(R.id.head_destination)
    HeadView headView;
    @BindView(R.id.iv_destination_img)
    ImageView ivDestinationImg;
    @BindView(R.id.tv_destination_region_name)
    TextView tvDestinationRegionName;
    @BindView(R.id.tv_destination_address)
    TextView tvSelfAddress;
    @BindView(R.id.iv_destination_weather)
    ImageView ivDestinationWeather;
    @BindView(R.id.tv_destination_weather)
    TextView tvDestinationWeather;
    @BindView(R.id.tv_destination_weather_des)
    TextView tvDestinationWeatherDes;
    @BindView(R.id.tv_county_summary_more)
    TextView tvCountySummaryMore;
    @BindView(R.id.tv_destination_county_summary)
    TextView tvDestinationCountySummary;
    @BindView(R.id.ll_county_summary)
    LinearLayout llCountySummary;
    @BindView(R.id.tv_self_driving_more)
    TextView tvSelfDrivingMore;
    @BindView(R.id.tv_self_driving_address)
    TextView tvSelfDrivingAddress;
    @BindView(R.id.tv_destination_spot)
    TextView tvDestinationSpot;
    @BindView(R.id.tv_destination_hotel)
    TextView tvDestinationHotel;
    @BindView(R.id.tv_destination_food)
    TextView tvDestinationFood;
    @BindView(R.id.tv_destination_shopping)
    TextView tvDestinationShopping;
    @BindView(R.id.tv_destination_fun)
    TextView tvDestinationFun;
    @BindView(R.id.ll_resource_sum)
    LinearLayout llResourceSum;
    @BindView(R.id.ll_self_driving)
    LinearLayout llSelfDriving;
    @BindView(R.id.tv_spot_more)
    TextView tvSpotMore;
    @BindView(R.id.recyclerView_spot)
    RecyclerView recyclerViewSpot;
    @BindView(R.id.ll_destination_spot)
    LinearLayout llDestinationSpot;
    @BindView(R.id.tv_food_more)
    TextView tvFoodMore;
    @BindView(R.id.recyclerView_food)
    RecyclerView recyclerViewFood;
    @BindView(R.id.ll_destination_food)
    LinearLayout llDestinationFood;
    @BindView(R.id.tv_family_more)
    TextView tvFamilyMore;
    @BindView(R.id.recyclerView_family)
    RecyclerView recyclerViewFamily;
    @BindView(R.id.ll_destination_family)
    LinearLayout llDestinationFamily;
    @BindView(R.id.tv_hotel_more)
    TextView tvHotelMore;
    @BindView(R.id.recyclerView_hotel)
    RecyclerView recyclerViewHotel;
    @BindView(R.id.ll_destination_hotel)
    LinearLayout llDestinationHotel;
    @BindView(R.id.tv_activity_more)
    TextView tvActivityMore;
    @BindView(R.id.list_activity)
    RecyclerView listActivity;
    @BindView(R.id.ll_destination_activity)
    LinearLayout llDestinationActivity;
    @BindView(R.id.tv_strategy_more)
    TextView tvStrategyMore;
    @BindView(R.id.list_strategy)
    RecyclerView listStrategy;
    @BindView(R.id.ll_destination_strategy)
    LinearLayout llDestinationStrategy;
    @BindView(R.id.scroll_tab_destination)
    NestedScrollView scrollTabDestination;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    /**
     * ????????????
     */
    private String region;
    /**
     * ?????????????????????
     */
    private String lat;
    /**
     * ?????????????????????
     */
    private String lng;
    /**
     * ??????
     */
    private String distance = "5";
    /**
     * ????????????
     */
    private int page = 1;
    /**
     * ????????????????????????
     */
    private int limitPage = 3;
    /**
     * ??????????????????ID
     */
    private String destinationId = "";
    /**
     * ????????????
     */
    private String regionName = "";
    /**
     * ???????????????????????????
     */
    private List<ScenicEntity.DatasBean> scenicList = new ArrayList<>();
    /**
     * ???????????????????????????
     */
    private List<FoodEntity> foodList = new ArrayList<>();
    /**
     * ???????????????????????????
     */
    private List<HotelEntity.DatasBean> hotelList = new ArrayList<>();
    /**
     * ???????????????????????????
     */
    private List<IndexActivity> activityList = new ArrayList<>();
    /**
     * ???????????????????????????
     */
    private List<MyStrategyListBean> strategyList = new ArrayList<>();

    /**
     * ????????????
     */
    private List<RegionEntity> regionList;
    /**
     * ???????????????????????????
     */
    private DestinationInfoEntity info = null;
    /**
     * ?????????????????????????????????
     */
    private Bundle bundle = null;

    /**
     * ?????????????????????
     */
    LinearLayoutManager manager;

    String destinationLat = "";

    String destinationLng = "";


    @Override
    public int getLayoutId() {
        return R.layout.activity_destination_details;
    }

    /**
     * ?????????
     */
    @Override
    public void initView() {
        if (IApplication.getInstance().regionList.size() > 0) {
            regionList = new ArrayList<>();
            regionList.addAll(IApplication.getInstance().regionList);
            regionList.remove(0);
        }
        region = getIntent().getStringExtra("region");
        regionName = getIntent().getStringExtra("name");
        destinationLat = getIntent().getStringExtra("lat");
        destinationLng = getIntent().getStringExtra("lng");
        headView.setTitle(regionName);
        // ?????????????????????????????????????????????
        initSelfAddress();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData();
            }
        });
        refreshLayout.autoRefresh();
    }

    /**
     * ????????????????????????
     */
    public void initSelfAddress() {
        // ??????????????????
        HelpMaps.startLocation(new CompleteFuncData() {
            @Override
            public void success(AMapLocation location) {
                if (ObjectUtils.isNotEmpty(location)) {
                    lat = location.getLatitude() + "";
                    lng = location.getLongitude() + "";
                    tvSelfDrivingAddress.setText(location.getAddress() + "");
                    getDestinationProduct();
                }
            }
        });
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    /**
     * ????????????
     */
    public void getData() {
        getBaseInfoByRegion(region);
        getActivityData();
        getStrategy();
    }

    @OnClick({R.id.tv_destination_region_name, R.id.ll_county_summary, R.id.tv_self_driving_more,
            R.id.tv_activity_more, R.id.tv_destination_spot, R.id.tv_destination_hotel, R.id
            .tv_destination_food, R.id.tv_destination_shopping, R.id.tv_destination_fun, R.id
            .tv_spot_more, R.id.tv_food_more, R.id.tv_hotel_more, R.id.ll_destination_activity, R
            .id.tv_strategy_more, R.id.tv_family_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_destination_region_name:
                // ????????????
//                setChooseDistance();
                break;
            case R.id.ll_county_summary:
                // ????????????
                Intent intent = new Intent(this, CountySummaryActivity.class);
                bundle = new Bundle();
                bundle.putString("region", region);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.tv_self_driving_more:
                // ???????????????
                goFoundNearNewAc(1);
                break;
            case R.id.tv_spot_more:
                // ????????????
                ARouter.getInstance().build(Constant.ACTIVITY_SCENIC).withString("region",
                        region).navigation();
                break;
            case R.id.tv_hotel_more:
                ARouter.getInstance()
                        .build(Constant.ACTIVITY_HOTEL)
                        .withString("region", region)
                        .withString("grade", "")
                        .navigation();
                break;
            case R.id.tv_food_more:
                // ????????????
                ARouter.getInstance().build(Constant.ACTIVITY_FOOD).withString("region", region)
                        .navigation();
                break;
            case R.id.tv_family_more:
                // ?????????
                break;
            case R.id.tv_activity_more:
                // ????????????
                ARouter.getInstance().build(Constant.ACTIVITY_FESTIVAL).withString("region",
                        region).navigation();
                break;
            case R.id.tv_strategy_more:
                // ????????????
                ARouter.getInstance().build(Constant.ACTIVITY_LINE).withString("region", region)
                        .navigation();

                break;
            case R.id.tv_destination_spot:
                // ??????
                goFoundNearNewAc(1);
                break;
            case R.id.tv_destination_hotel:
                // ??????
                goFoundNearNewAc(2);
                break;
            case R.id.tv_destination_food:
                // ??????
                goFoundNearNewAc(9);
                break;
            case R.id.tv_destination_shopping:
                // ??????
                goFoundNearNewAc(4);
                break;
            case R.id.tv_destination_fun:
                // ??????
                goFoundNearNewAc(5);
                break;
            default:

        }
    }

    /**
     * ?????????
     */
    private void goFoundNearNewAc(int position) {
        Intent intent = new Intent(this, FoundNearNewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("region", region);
        if (info == null) {
            return;
        }
        bundle.putString("lat", info.getLatitude());
        bundle.putString("lng", info.getLongitude());
        bundle.putInt("type", -1);
        bundle.putInt("curentType", position);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * ?????????????????????????????????????????????
     *
     * @param region
     */
    @SuppressLint("CheckResult")
    public void getBaseInfoByRegion(String region) {
        RetrofitHelper.getApiService()
                .getDestinationBaseInfo(region)
                .doOnSubscribe(disposable -> addDisposable(disposable))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    info = bean.getData();
                    if (ObjectUtils.isNotEmpty(info)) {
                        GlideApp.with(DestinationDetailsActivity.this).load(info.getCoverTwoToOne())
                                .placeholder(R.mipmap.common_ba_banner).error(R.mipmap.common_ba_banner)
                                .into(ivDestinationImg);
                        tvDestinationRegionName.setText(info.getRegionName());
                        tvSelfAddress.setText("");
                        try {
                            if (ObjectUtils.isNotEmpty(info.getWeather())) {
                                DestinationInfoEntity.WeatherBean weatherBean = info.getWeather();
                                DestinationInfoEntity.WeatherBean.TmpBean tmpBean = weatherBean.getTmp();
                                DestinationInfoEntity.WeatherBean.CondBean condBean = weatherBean.getCond();
                                tvDestinationWeather.setText(tmpBean.getMin() + "???-" + tmpBean.getMax() +
                                        "???");
                                // ?????????????????????
                                if (DateUtil.isDay()) {
                                    Glide.with(DestinationDetailsActivity.this).load(condBean.getPic_d())
                                            .into(ivDestinationWeather);
                                    tvDestinationWeatherDes.setText(condBean.getTxt_d());
                                } else {
                                    Glide.with(DestinationDetailsActivity.this).load(condBean.getPic_n())
                                            .into(ivDestinationWeather);
                                    tvDestinationWeatherDes.setText(condBean.getTxt_n());
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        tvDestinationCountySummary.setText(ObjectUtils.isNotEmpty(Utils.deleteHtmlImg
                                (info.getContent())) ? Utils.deleteHtmlImg(info.getContent()) : "????????????");
                        // ??????????????????ID????????????????????????????????????
                        destinationId = info.getId() + "";

                        lat = info.getLatitude();
                        lng = info.getLongitude();
                        getScenery();
                        getFood();
                        getHotel();
                        refreshLayout.finishRefresh();
                    }
                });
    }

    /**
     * ????????????????????????????????????????????????????????????
     */
    @SuppressLint("CheckResult")
    public void getDestinationProduct() {
        RetrofitHelper.getApiService()
                .getDestinationProduct(info.getLatitude(), info.getLongitude(), distance)
                .doOnSubscribe(disposable ->
                        addDisposable(disposable)
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getCode() == 0 && ObjectUtils.isNotEmpty(bean.getData())) {
                        DestinationProduct product = bean.getData();
                        tvDestinationSpot.setText(Utils.setTextColor("??????(" + product.getSCENERY() + ")",
                                getResources().getColor(R.color.main_black), 2, 3 + (product.getSCENERY()
                                        + "").length()));
                        tvDestinationHotel.setText(Utils.setTextColor("??????(" + product.getHOTEL() + ")",
                                getResources().getColor(R.color.main_black), 2, 3 + (product.getHOTEL() +
                                        "").length()));
                        tvDestinationShopping.setText(Utils.setTextColor("??????(" + product.getSHOPPING() +
                                ")", getResources().getColor(R.color.main_black), 2, 3 + (product
                                .getSHOPPING() + "").length()));
                        tvDestinationFun.setText(Utils.setTextColor("??????(" + product.getRECREATION() + ")"
                                + "", getResources().getColor(R.color.main_black), 2, 3 + (product
                                .getRECREATION() + "").length()));
                        tvDestinationFood.setText(Utils.setTextColor("??????(" + product.getTOILET() + ")",
                                getResources().getColor(R.color.main_black), 3, 3 + (product.getTOILET()
                                        + "").length()));
                    }
                });

    }

    /**
     * ??????????????????
     */
    @SuppressLint("CheckResult")
    public void getFood() {
        RetrofitHelper.getApiService()
                .getDestinationFood(
                        destinationId,
                        page,
                        URLConstant.LIMITPAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    addDisposable(disposable);
                })
                .subscribe(bean -> {
                    if (ObjectUtils.isNotEmpty(bean) && bean.getCode() == 0 && ObjectUtils.isNotEmpty
                            (bean.getDatas()) && bean.getDatas().size() > 0) {
                        llDestinationFood.setVisibility(View.VISIBLE);
                        foodList.clear();
                        foodList.addAll(bean.getDatas());
                        FoodRecyclerAdapter adapter = new FoodRecyclerAdapter(DestinationDetailsActivity
                                .this, foodList);
                        manager = new LinearLayoutManager(DestinationDetailsActivity.this);
                        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        recyclerViewFood.setLayoutManager(manager);
                        recyclerViewFood.setAdapter(adapter);
                    } else {
                        llDestinationFood.setVisibility(View.GONE);
                    }
                });
    }

    /**
     * ??????????????????
     */
    @SuppressLint("CheckResult")
    public void getScenery() {
        RetrofitHelper.getApiService()
                .getDestinationScenery(
                        destinationId,
                        page,
                        URLConstant.LIMITPAGE,
                        lat,
                        lng
                )
                .doOnSubscribe(disposable -> {
                    addDisposable(disposable);
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (ObjectUtils.isNotEmpty(bean) && bean.getCode() == 0 && ObjectUtils.isNotEmpty
                            (bean.getDatas()) && bean.getDatas().size() > 0) {
                        llDestinationSpot.setVisibility(View.VISIBLE);
                        scenicList.clear();
                        scenicList.addAll(bean.getDatas());
                        SpotRecyclerAdapter adapter = new SpotRecyclerAdapter(DestinationDetailsActivity
                                .this, scenicList);
                        manager = new LinearLayoutManager(DestinationDetailsActivity.this);
                        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        recyclerViewSpot.setLayoutManager(manager);
                        recyclerViewSpot.setAdapter(adapter);
                    } else {
                        llDestinationSpot.setVisibility(View.GONE);
                    }
                });
    }

    /**
     * ?????????????????? ????????????
     */
    @SuppressLint("CheckResult")
    public void getHotel() {
        RetrofitHelper.getApiService()
                .getDestinationHotel(
                        destinationId,
                        page,
                        URLConstant.LIMITPAGE,
                        lat,
                        lng
                ).doOnSubscribe(disposable -> {
            addDisposable(disposable);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(bean -> {
            if (ObjectUtils.isNotEmpty(bean) && bean.getCode() == 0 && ObjectUtils.isNotEmpty
                    (bean.getDatas()) && bean.getDatas().size() > 0) {
                llDestinationHotel.setVisibility(View.VISIBLE);
                hotelList.clear();
                hotelList.addAll(bean.getDatas());
                HotelRecyclerAdapter adapter = new HotelRecyclerAdapter
                        (DestinationDetailsActivity.this, hotelList);
                LinearLayoutManager manager = new LinearLayoutManager(DestinationDetailsActivity
                        .this, LinearLayoutManager.HORIZONTAL, false) {

                    @Override
                    public boolean canScrollHorizontally() {
                        return false;
                    }
                };
                recyclerViewHotel.setLayoutManager(manager);
                recyclerViewHotel.setAdapter(adapter);
            } else {
                llDestinationHotel.setVisibility(View.GONE);
            }
        });
    }


    /**
     * ????????????
     */
    @SuppressLint("CheckResult")
    public void getActivityData() {
        RetrofitHelper.getApiService()
                .getActivityList(
                        page + "",
                        limitPage + "",
                        "",
                        ParamsCommon.ACTIVITY_CHANELCODE,
                        region).subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    addDisposable(disposable);
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getCode() == 0 && ObjectUtils.isNotEmpty(bean.getDatas()) && bean.getDatas()
                            .size() > 0) {
                        activityList.clear();
                        activityList.addAll(bean.getDatas());
                        llDestinationActivity.setVisibility(View.VISIBLE);
                        listActivity.setLayoutManager(new LinearLayoutManager(this));
                        // ????????????
                        listActivity.setNestedScrollingEnabled(false);
                        CommonAdapter<IndexActivity> commonAdapter = new CommonAdapter<IndexActivity>
                                (DestinationDetailsActivity
                                        .this, R.layout.item_activity_destination, activityList) {
                            @Override
                            public void convert(final ViewHolder holder, IndexActivity resource, int
                                    position) {
                                holder.setOnClickListener(R.id.ll_jqhd, new View.OnClickListener() {

                                    @Override
                                    public void onClick(View view) {
                                        ARouter.getInstance().build(Constant.ACTIVITY_DETAILS_WEB)
                                                .withString("id", resource.getId() + "").withString
                                                ("code", ParamsCommon.ACTIVITY_CHANELCODE).withString
                                                ("title", "????????????").navigation();
                                    }
                                });
                                holder.setText(R.id.tv_item_activity_name, resource.getTitle());
                                GlideApp.with(DestinationDetailsActivity.this).load(resource.getCover())
                                        .placeholder(R.mipmap.common_ba_banner).error(R.mipmap
                                        .common_ba_banner).into((ImageView) holder.getView(R.id
                                        .iv_item_activity_img));
                                if (!ObjectUtils.isNotEmpty(resource.getBeginTime()) || !ObjectUtils
                                        .isNotEmpty(resource.getEndTime())) {
                                    holder.setVisible(R.id.ll_item_activity_time, false);
                                } else {
                                    holder.setVisible(R.id.ll_item_activity_time, true);
                                    holder.setText(R.id.tv_item_activity_start_time, resource
                                            .getBeginTime());
                                    holder.setText(R.id.tv_item_activity_end_time, resource.getEndTime());
                                }
                            }
                        };
                        listActivity.setAdapter(commonAdapter);
                    } else {
                        llDestinationActivity.setVisibility(View.GONE);
                    }
                });
    }

    /**
     * ???????????????????????????
     * <p>
     * ????????????
     */
    @SuppressLint("CheckResult")
    public void getStrategy() {
        RetrofitHelper.getApiService()
                .getLineList(page + "", limitPage + "", "", "1", region)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    addDisposable(disposable);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (ObjectUtils.isNotEmpty(bean) && ObjectUtils.isNotEmpty(bean.getDatas()) && bean
                            .getDatas().size() > 0) {
                        llDestinationStrategy.setVisibility(View.VISIBLE);
                        strategyList.clear();
                        strategyList.addAll(bean.getDatas());
                        listStrategy.setLayoutManager(new LinearLayoutManager(this));
                        listStrategy.setNestedScrollingEnabled(false);
                        StrategyAdapter adapter = new StrategyAdapter(
                                DestinationDetailsActivity.this,
                                R.layout.item_strategy_index, strategyList
                        );
                        listStrategy.setAdapter(adapter);
                    } else {
                        llDestinationStrategy.setVisibility(View.GONE);
                    }
                });
    }

    /**
     * ????????????
     */
    public void setChooseDistance() {
        // ???????????????
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView
                .OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                if (ObjectUtils.isNotEmpty(regionList) && regionList.size() > 0) {
                    regionName = regionList.get(options1).getName();
                    tvDestinationRegionName.setText(regionList.get(options1).getName());
                    headView.setTitle(regionList.get(options1).getName());
                    tvDestinationRegionName.setTag(regionList.get(options1).getRegion());
                    region = regionList.get(options1).getRegion();
                    getData();
                }
            }
        }).build();
        List<String> adrList = new ArrayList<>();
        adrList.addAll(IApplication.getInstance().siteRegionList);
        if (IApplication.getInstance().siteRegionList.size() > 0) {
            adrList.remove(0);
        }
        pvOptions.setPicker(adrList);
        pvOptions.show();
    }

    public void onRefresh() {
        getData();
    }


}
