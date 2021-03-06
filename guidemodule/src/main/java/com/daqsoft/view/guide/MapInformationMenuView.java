package com.daqsoft.view.guide;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MultiPointItem;
import com.amap.api.maps.model.MultiPointOverlay;
import com.amap.api.maps.model.MultiPointOverlayOptions;
import com.daqsoft.adapter.GuideConfigAdapter;
import com.daqsoft.adapter.MapInformationMenuListAdapter;
import com.daqsoft.bean.MapConfigBean;
import com.daqsoft.bean.MarketBean;
import com.daqsoft.bean.WalkBean;
import com.daqsoft.guide.MapInformationActivity;
import com.daqsoft.guide.R;
import com.daqsoft.http.HttpCallBack;
import com.daqsoft.http.HttpResultBean;
import com.daqsoft.http.requestapi.RequestApiProvider;
import com.daqsoft.http.requestapi.RequestData;
import com.daqsoft.http.requestapi.impl.GuideConfigApi;
import com.daqsoft.http.requestapi.impl.GuideMarketSourceApi;
import com.daqsoft.utils.Config;
import com.daqsoft.utils.Constant;
import com.daqsoft.utils.SoftinputUtils;
import com.daqsoft.utils.Utils;
import com.daqsoft.view.mapview.MyMapView;
import com.daqsoft.view.mapview.bean.MapLocation;

import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ??????????????????????????????View
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-3-14
 * @since JDK 1.8
 */

public class MapInformationMenuView extends FrameLayout {
    LinearLayout llSearch;
    EditText edt_search;
    Button btn_all;
    LinearLayout layout_content;
    RecyclerView recyclerView;
    /**
     * ??????????????????
     */
    RecyclerView recyclerView_config;
    /**
     * ???????????????
     */
    private GuideConfigAdapter guideConfigAdapter;
    TextView txtDhavenData;

    private MyMapView mapView;

    private RecyclerView.LayoutManager mLayoutManager;
    /**
     * ???????????????
     */
    private MapInformationMenuListAdapter adapter;

    private MyTextWatcher myTextWatcher;
    /**
     * ???????????????
     */
    public static LinearLayout llContent;
    /**
     * ???????????????????????????
     */
    public static LinearLayout llLineContent;
    /**
     * ???????????????
     */
    public static TextView tvLineName;
    /**
     * ??????????????????
     */
    public static TextView tvLineTotal;
    /**
     * ?????????????????????
     */
    private RecyclerView recyclerViewLineContent;
    /**
     * ????????????
     */
    private Map<String, List<MarketBean>> cache = new HashMap<String, List<MarketBean>>();
    /**
     * ??????market
     */
    private static Map<String, Integer> markMap = new HashMap<String, Integer>();

    static {
        markMap.put("sourceType_1", R.mipmap.guide_map_scenic_single);
        markMap.put("sourceType_16", R.mipmap.guide_map_park_single);
        markMap.put("sourceType_15", R.mipmap.guide_map_toilet_single);
        markMap.put("sourceType_2", R.mipmap.guide_map_hotel_single);
        markMap.put("sourceType_6", R.mipmap.guide_map_restaurant_single);
        markMap.put("sourceType_4", R.mipmap.guide_map_shop_single);
        markMap.put("map_sourceType_line", R.mipmap.guide_map_line_single);
        markMap.put("sourceType_11", R.mipmap.guide_map_gas_single);
        markMap.put("sourceType_12", R.mipmap.guide_map_passenger_single);
        markMap.put("sourceType_14", R.mipmap.guide_map_medical_single);
        markMap.put("map_sourceType_gate", R.mipmap.guide_map_entrance_and_exit);
        markMap.put("sourceType_20",R.mipmap.guide_gym);
        markMap.put("sourceType_21",R.mipmap.guide_broadcast);
        markMap.put("sourceType_19",R.mipmap.guide_movie);
    }

    /**
     * ???????????????market
     */
    private static Map<String, Integer> markList = new HashMap<String, Integer>();

    static {
        markList.put("sourceType_1", R.mipmap.guide_scenic);
        markList.put("sourceType_16", R.mipmap.guide_park);
        markList.put("sourceType_15", R.mipmap.guide_toilet);
        markList.put("sourceType_2", R.mipmap.guide_hotel);
        markList.put("sourceType_6", R.mipmap.guide_restaurant);
        markList.put("sourceType_4", R.mipmap.guide_shop);
        markList.put("map_sourceType_line", R.mipmap.guide_line);
        markList.put("sourceType_11", R.mipmap.guide_gas);
        markList.put("sourceType_12", R.mipmap.guide_passenger);
        markList.put("sourceType_14", R.mipmap.guide_medical);
        markList.put("map_sourceType_gate", R.mipmap.guide__entrance_and_exit);
        markList.put("sourceType_20",R.mipmap.guide_gym);
        markList.put("sourceType_21",R.mipmap.guide_broadcast);
        markList.put("sourceType_19",R.mipmap.guide_movie);
    }
  /*  static {
        markList.put("sourceType_1", R.mipmap.guide_expand_attractions);
        markList.put("sourceType_16", R.mipmap.guide_expand_park);
        markList.put("sourceType_15", R.mipmap.guide_expand_toilet);
        markList.put("sourceType_2", R.mipmap.guide_expand_hotel);
        markList.put("sourceType_6", R.mipmap.guide_expand_food);
        markList.put("sourceType_4", R.mipmap.guide_expand_shop);
        markList.put("map_sourceType_line", R.mipmap.guide_expand_line);
        markList.put("sourceType_11", R.mipmap.guide_expand_gas);
        markList.put("sourceType_12", R.mipmap.guide_expand_traffic);
        markList.put("sourceType_14", R.mipmap.guide_expand_medical);
    }*/


    public MapInformationMenuView(@NonNull Context context) {
        super(context);
        init();
    }

    public MapInformationMenuView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * ?????????
     */
    private void init() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout
                .guide_view_mapinformationmenu, null);
        this.addView(v, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.WRAP_CONTENT));
        recyclerViewLineContent = (RecyclerView) v.findViewById(R.id.recyclerView_line);
        tvLineName = (TextView) v.findViewById(R.id.tv_line_name);
        tvLineTotal = (TextView) v.findViewById(R.id.tv_line_total);
        llLineContent = (LinearLayout) v.findViewById(R.id.ll_line_content);
        llContent = (LinearLayout) v.findViewById(R.id.ll_content);
        recyclerView_config = (RecyclerView) v.findViewById(R.id.recyclerView_config);
        llSearch = (LinearLayout) v.findViewById(R.id.ll_search);
        edt_search = (EditText) v.findViewById(R.id.edt_search);
        btn_all = (Button) v.findViewById(R.id.btn_all);
        layout_content = (LinearLayout) v.findViewById(R.id.layout_content);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        txtDhavenData = (TextView) v.findViewById(R.id.txt_haven_data);
        myTextWatcher = new MyTextWatcher();
        edt_search.addTextChangedListener(myTextWatcher);
        btn_all.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick_btnall();
            }
        });
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new MapInformationMenuListAdapter(getContext());

        recyclerView.setLayoutManager(mLayoutManager);
        // ???????????????
        //        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
        //                DividerItemDecoration.VERTICAL));
        // ??????adapter
        recyclerView.setAdapter(adapter);
        txtDhavenData.setVisibility(View.GONE);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView_config.setLayoutManager(manager);
        guideConfigAdapter = new GuideConfigAdapter(getContext());
        recyclerView_config.setAdapter(guideConfigAdapter);
        guideConfigAdapter.setListener(new GuideConfigAdapter.OnTypeclickListener() {
            @Override
            public void onCLick(MapConfigBean bean) {
                mapView.clearMarket();
                adapter.clear();
                mapView.clearAnimation();
                // ?????????????????????????????????
                mapView.setZoom(MapInformationActivity.bean.getZoom());
                // ??????????????????
                mapView.location(Double.parseDouble(MapInformationActivity.bean.getCenterLat()),
                        Double.parseDouble(MapInformationActivity.bean.getCenterLon()));
                if (layout_content.getVisibility() == View.GONE) {
                    llSearch.setVisibility(View.GONE);
                } else {
                    if (guideConfigAdapter.getSelect() != null) {
                        if (guideConfigAdapter.getSelect().getName().equals("??????") ||
                                guideConfigAdapter.getSelect().getName().equals("??????")) {
                            llSearch.setVisibility(View.VISIBLE);
                        } else {
                            llSearch.setVisibility(View.GONE);
                        }

                    }
                }
                if (guideConfigAdapter.getSelect().getName().equals("??????")) {
                    // ????????????????????????????????????
                    onclick_btnall();
                    requqestWalk(bean.getSkey());
                    return;
                } else {
                    // ??????????????????
                    if (layout_content.getVisibility() == VISIBLE) {
                        onclick_btnall();
                    }
                }
                requestTypeMarkets(bean.getSkey());
            }
        });
        rquestConfig();
    }

    /**
     * ?????????????????????
     */
    public class MyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String value = edt_search.getText().toString();
            if (cache.get("sourceType_1") != null) {
                adapter.setData(cache.get("sourceType_1"));
            }
            if (TextUtils.isEmpty(edt_search.getText().toString())) {
                List<MarketBean> list = cache.get("sourceType_1");
                adapter.setData(list);
            } else {
                List<MarketBean> list = new ArrayList<MarketBean>();
                for (int i = 0; i < adapter.getList().size(); i++) {
                    MarketBean bean = adapter.getList().get(i);
                    if (bean.getName().contains(value)) {
                        list.add(bean);
                    }
                }
                adapter.setData(list);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void onclick_btnall() {
        Activity activity = (Activity) getContext();
        if (layout_content.getVisibility() == View.GONE) {
            layout_content.setVisibility(View.VISIBLE);
            btn_all.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.mipmap.guide_arrow_fold,
                    0, 0);
            if (guideConfigAdapter.getSelect() != null) {
                if (guideConfigAdapter.getSelect().getName().equals("??????") || guideConfigAdapter
                        .getSelect().getName().equals("??????")) {
                    llSearch.setVisibility(View.VISIBLE);
                } else {
                    llSearch.setVisibility(View.GONE);
                }
            }
            if (!SoftinputUtils.isSoftInputShow(activity)) {
                SoftinputUtils.openKeybord(edt_search, getContext());
            }
        } else {
            llSearch.setVisibility(View.GONE);
            layout_content.setVisibility(View.GONE);
            btn_all.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.mipmap.guide_arrow_open,
                    0, 0);
            if (SoftinputUtils.isSoftInputShow(activity)) {
                SoftinputUtils.closeKeybord(edt_search, getContext());
            }
        }
    }

    /**
     * ?????????????????????menu
     */
    private void rquestConfig() {
        GuideConfigApi api = (GuideConfigApi) RequestApiProvider.getAPI(Config.BASEURL + Constant
                .GUIDE_CONFIG);
        if (api == null) {
            api = (GuideConfigApi) RequestApiProvider.registerApiService(Config.BASEURL +
                    Constant.GUIDE_CONFIG, new GuideConfigApi(getContext()));
        }
        Activity activity = (Activity) getContext();
        api.setMapGuideSetId(activity.getIntent().getIntExtra(Constant.IntentKey.ID, 0) + "");
        api.request(Config.BASEURL + Constant.GUIDE_CONFIG, this, new HttpCallBack<MapConfigBean>
                (MapConfigBean.class, getContext()) {

            @Override
            public void success(HttpResultBean<MapConfigBean> result) {
                LogUtil.e(result.toString());
                if (result.getDatas() != null) {
                    guideConfigAdapter.setSelectData(result.getDatas().get(0));
                    result.getDatas().get(0).setIscheck(true);
                    guideConfigAdapter.addAll(result.getDatas());
                    if (result.getDatas().get(0).getSkey().equals("map_sourceType_line")) {
                        requqestWalk(result.getDatas().get(0).getSkey());
                    } else {
                        requestTypeMarkets(result.getDatas().get(0).getSkey());
                    }
                }
            }
        });
    }

    /**
     * ??????markets??????
     *
     * @param type
     */

    private void requestTypeMarkets(final String type) {
        GuideMarketSourceApi api = (GuideMarketSourceApi) RequestApiProvider.getAPI(Config
                .BASEURL + Constant.GUIDE_MARKET_SOURCE);
        if (api == null) {
            api = (GuideMarketSourceApi) RequestApiProvider.registerApiService(Config.BASEURL +
                    Constant.GUIDE_MARKET_SOURCE, new GuideMarketSourceApi(getContext()));
        }
        Activity activity = (Activity) getContext();
        api.setMapGuideid(activity.getIntent().getIntExtra(Constant.IntentKey.ID, 0) + "");
        api.setSourceType(type);
        api.request(Config.BASEURL + Constant.GUIDE_MARKET_SOURCE, this, new
                HttpCallBack<MarketBean>(MarketBean.class, getContext()) {

            @Override
            public boolean onCache(String result) {
                List<MarketBean> listBean = cache.get(type);
                if (listBean != null && !listBean.isEmpty()) {
                    LogUtil.e("marker??????------" + listBean.size());
                    if (markMap.containsKey(type)) {
                        setDataToAdapter(markMap.get(type), markList.get(type), listBean);
                    } else {
                        setDataToAdapter(-1, -1, listBean);
                    }
                }
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                LatLng mCurrentlat;
                for (int i = 0; i < listBean.size(); i++) {
                    mCurrentlat = new LatLng(Double.parseDouble(listBean.get(i).getLatitude()),
                            Double.parseDouble(listBean.get(i).getLongitude()));
                    builder.include(mCurrentlat);
                }
                LatLngBounds bounds = builder.build();
                return !listBean.isEmpty();

            }

            @Override
            public void success(HttpResultBean<MarketBean> result) {
                LogUtil.e("marker??????------" + result.getDatas().size());
                List<MarketBean> listBean = new ArrayList<>();
                if (result.getDatas().size() > 50) {
                    listBean.clear();
                    for (int i = 0; i < 50; i++) {
                        listBean.add(result.getDatas().get(i));
                    }
                } else {
                    listBean = result.getDatas();
                }
                LogUtil.e("marker??????------" + listBean.size());
                cache.put(type, listBean);
                if (markMap.containsKey(type)) {
                    setDataToAdapter(markMap.get(type), markList.get(type), listBean);
                } else {
                    setDataToAdapter(-1, -1, listBean);
                }
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                LatLng mCurrentlat;
                for (int i = 0; i < listBean.size(); i++) {
                    mCurrentlat = new LatLng(Double.parseDouble(listBean.get(i).getLatitude()),
                            Double.parseDouble(listBean.get(i).getLongitude()));
                    builder.include(mCurrentlat);
                }
                LatLngBounds bounds = builder.build();
                //mapView.centenerMap2(bounds.northeast);
            }
        });
    }

    /**
     * ?????????????????????
     */
    private void requqestWalk(final String type) {
        Activity activity = (Activity) getContext();
        String guideID = activity.getIntent().getIntExtra(Constant.IntentKey.ID, 0) + "";
        RequestData.requestGuideWalk(guideID, new HttpCallBack<WalkBean>(WalkBean.class,
                getContext()) {
            @Override
            public void success(HttpResultBean<WalkBean> bean) {
                if (bean.getDatas() != null) {
                    List<MarketBean> listBean = new ArrayList<>();
                    for (WalkBean temp : bean.getDatas()) {
                        MarketBean en = new MarketBean();
                        en.setId(temp.getId());
                        en.setMapGuideSetId(temp.getMapGuideSetId());
                        en.setName(temp.getName());
                        en.setSourceType(temp.getSourceType());
                        en.setLinePointNum(temp.getLinePointNum());
                        listBean.add(en);
                    }
                    if (markMap.containsKey(type)) {
                        setDataToAdapter(R.mipmap.guide_line, markMap.get(type), listBean);
                        adapter.setDrawableID(markMap.get(type));
                        adapter.setDrawableListID(markList.get(type));
                        adapter.setData(listBean);
                        adapter.setRecyclerViewLineContent(recyclerViewLineContent);
                        adapter.setRecyclerView(recyclerView);
                        adapter.setCallBack(new MapInformationMenuListAdapter.WalkCallBack() {

                            @Override
                            public void walkResult(int scource, List<MarketBean> listBean) {
                                setDataToAdapter(scource, markList.get(type), listBean);
                            }
                        });
                    }
                    adapter.requestWalkDetail(listBean.get(0), 0);
                }
            }
        });
    }

    /**
     * ???recyler????????????
     * ??????????????????????????????
     *
     * @param listBean
     */
    private void setDataToAdapter(int scource, int listScource, List<MarketBean> listBean) {
        mapView.clearMarket();
        if (null != mapView.getPolyline()) {
            mapView.getPolyline().remove();
        }
        adapter.clear();
        if (listBean != null) {
            if (listBean.isEmpty()) {
                txtDhavenData.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                return;
            }
            txtDhavenData.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            for (MarketBean bean : listBean) {
                if (mapView != null) {
                    MarketBean.Market market = bean.getMarket();
                    if (TextUtils.isEmpty(bean.getLatitude()) || TextUtils.isEmpty(bean
                            .getLatitude())) {
                        continue;
                    }
                    MapLocation<MarketBean> location = new MapLocation<MarketBean>(Double
                            .parseDouble(bean.getLatitude()), Double.parseDouble(bean
                            .getLongitude()));
                    location.setTitle(bean.getName());
                    location.setT(bean);
                   /* // ??????????????????????????????????????????
                    location.setShowInforWindow(scource == R.mipmap.guide_scenic_spot_normal);*/
                    location.setShowInforWindow(true);
                    // ???????????????
                    if (market.getView() == null) {
                        View marketView = LayoutInflater.from(getContext()).inflate(R.layout
                                .guide_view_map_guide_market, null);
                        ImageView img = (ImageView) marketView.findViewById(R.id.img_bitmap);
                        TextView txt = (TextView) marketView.findViewById(R.id.txt_name);
                        txt.setText(TextUtils.isEmpty(bean.getName()) ? "" : bean.getName());
                        if (scource > 0) {
                            if (bean.getSourceType().equals("sourceType_1")) {
                                if (bean.getAudioPath() != null && !bean.getAudioPath().equals("")) {
                                    // ?????????
                                    img.setImageResource(R.mipmap.guide_commentary_selected);
                                } else {
                                    img.setImageResource(scource);
                                }
                            } else {
                                img.setImageResource(scource);
                            }
                        }
                        market.setView(marketView);
                    }
                    mapView.addMarket(location, market.getView());
                }
            }
            adapter.setDrawableID(scource);
            adapter.setDrawableListID(listScource);
            adapter.addAll(listBean);

        }
    }


    Marker marker;

    public void setMultiPointOverlay(List<MarketBean> listBean, final int scource) {
        mapView.getaMap().addMultiPointOverlay(null);
        MultiPointOverlayOptions overlayOptions = new MultiPointOverlayOptions();
        // ????????????
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap
                .guide_map_scenic_single);
        overlayOptions.icon(bitmapDescriptor);
        // ????????????
        overlayOptions.anchor(0.5f, 0.5f);
        MultiPointOverlay multiPointOverlay = mapView.getaMap().addMultiPointOverlay
                (overlayOptions);
        mapView.getaMap().setOnMultiPointClickListener(new AMap.OnMultiPointClickListener() {
            @Override
            public boolean onPointClick(MultiPointItem pointItem) {
                android.util.Log.i("amap ", "onPointClick");
                if (marker.isRemoved()) {
                    if (Utils.isnotNull(pointItem.getObject())) {
                        MarketBean marketBean = (MarketBean) pointItem.getObject();
                        // ??????amap clear???????????????marker?????????????????????
                        View marketView = LayoutInflater.from(getContext()).inflate(R.layout
                                .guide_view_map_guide_market, null);
                        ImageView img = (ImageView) marketView.findViewById(R.id.img_bitmap);
                        TextView txt = (TextView) marketView.findViewById(R.id.txt_name);
                        txt.setText(TextUtils.isEmpty(marketBean.getName()) ? "" : marketBean
                                .getName());
                        if (scource > 0) {
                            img.setImageResource(scource);
                        }
                 /*       marker = mapView.getaMap().addMarker(new MarkerOptions().icon
                 (BitmapDescriptorFactory
                                .defaultMarker

                                        (BitmapDescriptorFactory.HUE_RED)));*/

                        marker = mapView.getaMap().addMarker(new MarkerOptions().title(marketBean
                                .getName()).icon(BitmapDescriptorFactory.fromView(marketView)));
                    }
                }
                // ????????????Marker?????????????????????????????????
                marker.setPosition(pointItem.getLatLng());
                marker.setToTop();
                return false;
            }
        });

        List<MultiPointItem> list = new ArrayList<MultiPointItem>();
        for (MarketBean marketBean : listBean) {
            // ??????MultiPointItem?????????????????????????????????????????????????????????
            if (Utils.isnotNull(marketBean.getLatitude()) && Utils.isnotNull(marketBean
                    .getLongitude())) {
                LatLng latLng = new LatLng(Double.parseDouble(marketBean.getLatitude()), Double
                        .parseDouble(marketBean.getLongitude()));
                MultiPointItem multiPointItem = new MultiPointItem(latLng);
                multiPointItem.setTitle(marketBean.getName());
                multiPointItem.setObject(marketBean);
                list.add(multiPointItem);
            }
        }
        // ???????????????????????????????????????????????????????????????????????????????????????????????????
        multiPointOverlay.setItems(list);
    }

    /**
     * ??????????????????market????????????????????????
     *
     * @param mapView
     */
    public void setMapView(MyMapView mapView) {
        this.mapView = mapView;
        adapter.setMapView(mapView);

    }
}
