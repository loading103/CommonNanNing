package com.daqsoft.guide;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.daqsoft.StatusBarUtil;
import com.daqsoft.bean.MapConfigureBean;
import com.daqsoft.bean.MarketBean;
import com.daqsoft.dialog.OffLineDownDialog;
import com.daqsoft.http.DefaultProgressCallBack;
import com.daqsoft.http.HttpCallBack;
import com.daqsoft.http.HttpResultBean;
import com.daqsoft.http.requestapi.RequestApiProvider;
import com.daqsoft.http.requestapi.impl.GuideAudioApi;
import com.daqsoft.http.requestapi.impl.MapGuideApi;
import com.daqsoft.utils.Config;
import com.daqsoft.utils.Constant;
import com.daqsoft.utils.EncryptUtils;
import com.daqsoft.utils.FileUtil;
import com.daqsoft.utils.MapNaviUtils;
import com.daqsoft.utils.SelfLatLng;
import com.daqsoft.utils.ShowToast;
import com.daqsoft.utils.Utils;
import com.daqsoft.utils.imageloader.MyImageLoader;
import com.daqsoft.view.AudioPlayView;
import com.daqsoft.view.HeadView;
import com.daqsoft.view.guide.MapGuideContentView;
import com.daqsoft.view.guide.MapInformationMenuView;
import com.daqsoft.view.mapview.MyMapView;
import com.daqsoft.view.mapview.bean.MapLocation;
import com.daqsoft.view.mapview.bean.RectLat;
import com.daqsoft.voice.IMAudioManager;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * ??????????????????
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-3-14
 * @since JDK 1.8
 */

public class MapInformationActivity extends Activity implements MapGuideContentView
        .OnclickMenuListener, AMap.InfoWindowAdapter {
    LinearLayout layoutContent;
    HeadView headView;
    MyMapView mapView;
    MapGuideContentView guideContent;
    LinearLayout llPalyAudio;
    ImageView imgStopAudio;
    AudioPlayView audioPlayView;
    TextView txtPlayAudioContent;
    MapInformationMenuView menuView;
    TextView tvScenicMapList;
    /**
     * ??????map?????????
     */
    public static MapConfigureBean bean;

    /**
     * ????????????ZIP???
     */
    private static String SAVE_AUDIO_PATH;
    /**
     * ??????????????????????????????
     */
    private static String ZIP_AUDIO_PATH;
    /**
     * ???????????????????????????
     */
    private static String SAVE_MAP_PATH;
    /**
     * ??????ID
     */
    private int mapID;
    /**
     * ????????????
     */
    private MyImageLoader imageLoader;
    /**
     * ????????????????????????
     */
    private String mapUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int color = getResources().getColor(R.color.white);
        StatusBarUtil.setColor(this, color, 0);
        StatusBarUtil.setLightMode(this);
        initData();
        setContentView(R.layout.guide_activity_map_information);
        boolean isFirst = getIntent().getBooleanExtra("isFirst", false);
        if (isFirst) {
            ShowToast.showText(this, "????????????????????????????????????");
        }
        ZIP_AUDIO_PATH = getCacheDir().getAbsolutePath() + File.separator + "audio" + File
                .separator + "guideaudio" + File.separator;
        imageLoader = new MyImageLoader(this);
        layoutContent = (LinearLayout) findViewById(R.id.layout_content);
        headView = (HeadView) findViewById(R.id.view_headView);
        tvScenicMapList = (TextView) findViewById(R.id.tv_scenic_map_list);
        if (Config.CITY_NAME.equals("??????")) {
            tvScenicMapList.setVisibility(View.GONE);
            tvScenicMapList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String activityName = "com.daqsoft.commonnanning.ui.main.GuideListActivity";
                    Class clazz = null;
                    try {
                        Bundle bundle = new Bundle();
                        bundle.putInt("type", 1);
                        clazz = Class.forName(activityName);
                        Intent intent = new Intent(MapInformationActivity.this, clazz);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        LogUtil.e(e.toString());
                    }

                }
            });
        } else {
            tvScenicMapList.setVisibility(View.GONE);
        }
        headView.setBackListener(new HeadView.OnBackListener() {
            @Override
            public void onBack(View v) {
                if (MapInformationMenuView.llLineContent.getVisibility() == View.VISIBLE) {
                    MapInformationMenuView.llLineContent.setVisibility(View.GONE);
                    MapInformationMenuView.llContent.setVisibility(View.VISIBLE);
                    if (null != mapView) {
                        mapView.clearMarket();
                    }
                    if (null != mapView.getPolyline()) {
                        mapView.getPolyline().remove();
                    }
                } else {
                    finish();
                }
            }
        });
        mapView = (MyMapView) findViewById(R.id.mapView);
        guideContent = (MapGuideContentView) findViewById(R.id.guideContent);
        llPalyAudio = (LinearLayout) findViewById(R.id.ll_palyAudio);
        imgStopAudio = (ImageView) findViewById(R.id.img_stopAudio);
        imgStopAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick_stopAudio(v);
            }
        });
        audioPlayView = (AudioPlayView) findViewById(R.id.v_audioPlayView);
        txtPlayAudioContent = (TextView) findViewById(R.id.txt_play_audio_content);
        menuView = (MapInformationMenuView) findViewById(R.id.layout_menu);
        findViewById(R.id.img_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick_location(v);
            }
        });
        findViewById(R.id.img_offline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick_offLine(v);
            }
        });

        headView.setTitle("");
        mapView.create(savedInstanceState);
        menuView.setMapView(mapView);
        guideContent.setListener(this);
        mapView.setInfoWindowAdapter(this);
        mapID = getIntent().getIntExtra(Constant.IntentKey.ID, 0);
        requestMapConfig();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        MarketBean bean = (MarketBean) marker.getObject();
        View infoWindow = null;
        if (bean.getSourceType().equals("sourceType_1")) {
            infoWindow = getLayoutInflater().inflate(R.layout.item_guide_mapwindow, null);
            render(marker, infoWindow);
        } else {
            infoWindow = getLayoutInflater().inflate(R.layout.guide_dialog_scenic, null);
            render2(marker, infoWindow);
        }
        return infoWindow;
    }

    /**
     * ????????????
     *
     * @param marker
     * @param view
     */
    private void render(final Marker marker, View view) {
        final MarketBean bean = (MarketBean) marker.getObject();
        TextView txtTitle = (TextView) view.findViewById(R.id.txt_title);
        TextView txtContent = (TextView) view.findViewById(R.id.txt_content);
        SimpleDraweeView imgTag = (SimpleDraweeView) view.findViewById(R.id.img_tag);
        imgTag.setImageURI(bean.getCover());
        txtTitle.setText(bean.getName());
        txtContent.setText(bean.getSummary() != null ? bean.getSummary() : "");
        TextView txtPalyAudio = (TextView) view.findViewById(R.id.txt_palyAudio);
        TextView txt_information = (TextView) view.findViewById(R.id.txt_information);
        ImageView imgClose = (ImageView) view.findViewById(R.id.img_close);
        TextView txt_map = (TextView) view.findViewById(R.id.txt_scenic_map);
        TextView txt_daohang = (TextView) view.findViewById(R.id.txt_scenic_daohang);
        LinearLayout llScenic = (LinearLayout) view.findViewById(R.id.ll_guide_dialog_scenic);
        TextView tvScenicDistance = (TextView) view.findViewById(R.id.tv_dialog_distance);
        tvScenicDistance.getBackground().setAlpha(125);
        // ???????????????????????????????????????
        if (Utils.isnotNull(bean.getAudioPath())) {
            txtPalyAudio.setVisibility(View.VISIBLE);
        } else {
            txtPalyAudio.setVisibility(View.GONE);
        }
        // ???????????????
        if (bean.getSourceType().equals("sourceType_1")) {
            llScenic.setVisibility(View.VISIBLE);
            tvScenicDistance.setText(SelfLatLng.CalculateLineDistance(bean.getLatitude(), bean
                    .getLongitude()));
        } else {
            // ??????
            llScenic.setVisibility(View.GONE);
        }
        // ??????????????????????????????????????????
        if (Utils.isnotNull(bean.getLinkType())) {
            txt_map.setVisibility(View.VISIBLE);
        } else {
            txt_map.setVisibility(View.GONE);
        }
        // ??????????????????
        txtPalyAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(bean.getAudioPath())) {
                    return;
                }
                llPalyAudio.setVisibility(View.VISIBLE);
                try {
                    txtPlayAudioContent.setText("??????????????????\"" + bean.getName() + "\"????????????");
                    IMAudioManager.instance().release();
                    IMAudioManager.instance().playSound(bean.getAudioPath(), new MediaPlayer
                            .OnCompletionListener() {

                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            llPalyAudio.setVisibility(View.GONE);
                            audioPlayView.stop();
                        }
                    });
                    audioPlayView.start();
                } catch (Exception e) {

                }
            }
        });
        // ????????????
        txt_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseVoice();
                Intent intent = new Intent(MapInformationActivity.this, SpotDetailActivity.class);
                intent.putExtra(Constant.IntentKey.BEAN, bean);
                startActivity(intent);
            }
        });
        // ????????????
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (marker.isInfoWindowShown()) {
                    marker.hideInfoWindow();
                }
            }
        });
        // ??????
        txt_daohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Utils.gethaveNet(MapInformationActivity.this)) {
                    ShowToast.showText(MapInformationActivity.this, "???????????????????????????????????????");
                } else {
                    LatLng latLng = new LatLng(lat, lon);
                    if (Utils.isnotNull(bean.getLatitude()) && Utils.isnotNull(bean.getLongitude
                            ()) && Utils.isnotNull(latLng.latitude) && Utils.isnotNull(latLng
                            .longitude)) {

                        MapNaviUtils.isMapNaviUtils(MapInformationActivity.this, latLng.latitude,
                                latLng.longitude, "????????????", bean.getLatitude(), bean.getLongitude()
                                , Utils.isnotNull(bean.getAddress()) ? Utils.tr(bean.getAddress()
                                ) : "?????????");
                    } else {
                        ShowToast.showText(MapInformationActivity.this, "???????????????????????????????????????");
                    }
                }
            }
        });
        // ??????????????????
        txt_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                //  ??????1???????????????????????????????????????id
                if (Constant.LINKTYPE_SYSTEM.equals(bean.getLinkType()) && Utils.isnotNull(bean
                        .getLink())) {
                    intent = new Intent(MapInformationActivity.this, MapInformationActivity.class);
                    // ?????????????????????
                    intent.putExtra("url", Config.BASEURL);
                    // ?????????????????????
                    intent.putExtra("htmlurl", Config.HTMLURL);
                    // ????????????
                    intent.putExtra("lang", Config.LANG);
                    // ????????????
                    intent.putExtra("sitecode", Config.SITECODE);
                    // ????????????
                    intent.putExtra("region", Config.REGION);
                    // ???????????????APPID
                    intent.putExtra("appid", Config.WECHAT_APPID);
                    // ????????????
                    intent.putExtra("city", Config.CITY_NAME);
                    // ?????????????????????
                    intent.putExtra("lat", Config.COMMON_LAT);
                    // ?????????????????????
                    intent.putExtra("lng", Config.COMMON_LNG);
                    // ?????????????????????????????????
                    intent.putExtra("about", Config.about);
                    intent.putExtra(Constant.IntentKey.ID, Integer.parseInt(bean.getLink()));
                    startActivity(intent);
                } else if (Constant.LINKTYPE_OUTSIDE.equals(bean.getLinkType())) {
                    // ??????2??????????????????????????????????????????url,??????????????????
                    intent = new Intent();
                    intent.putExtra("htmlUrl", bean.getLink());
                    intent.putExtra("isShare", false);
                    intent.putExtra("tag", false);
                    intent.setClassName(MapInformationActivity.this, "com.daqsoft.android.base" +
                            ".WebActivity");
                    startActivity(intent);
                } else {
                    ShowToast.showText(MapInformationActivity.this, "?????????????????????????????????");
                }
            }
        });
        //        imgTag.setImageURI(bean.get);

    }

    /**
     * ????????????
     *
     * @param marker
     * @param view
     */
    private void render2(final Marker marker, View view) {
        final MarketBean bean = (MarketBean) marker.getObject();
        TextView txtTitleOther = (TextView) view.findViewById(R.id.txt_title_other);
        txtTitleOther.setText(bean.getName());
        TextView tvOtherDistance = (TextView) view.findViewById(R.id.tv_dialog_other_distance);
        // ?????????????????????????????????????????????
        if (bean.getSourceType().equals("map_sourceType_line")) {
            tvOtherDistance.setText(bean.getSummary());
        } else {
            tvOtherDistance.setText("??????" + SelfLatLng.CalculateLineDistance(bean.getLatitude(),
                    bean.getLongitude()) + "," + (bean.getAddress() != null ? bean.getAddress() :
                    ""));
        }
        TextView tv_guid = (TextView) view.findViewById(R.id.tv_guid);
        tv_guid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    LatLng latLng = SelfLatLng.getSelfLatLng();
                    if (Utils.isnotNull(bean.getLatitude()) && Utils.isnotNull(bean.getLongitude
                            ()) && Utils.isnotNull(latLng.latitude) && Utils.isnotNull(latLng
                            .longitude)) {

                        MapNaviUtils.isMapNaviUtils(MapInformationActivity.this, latLng.latitude,
                                latLng.longitude, "????????????", bean.getLatitude(), bean.getLongitude()
                                , Utils.isnotNull(bean.getAddress()) ? Utils.tr(bean.getAddress()
                                ) : "?????????");
                    } else {
                        ShowToast.showText(MapInformationActivity.this, "???????????????????????????????????????");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    /**
     * ??????????????????????????????????????????
     */
    public void initData() {
        Intent intent = getIntent();
        // ?????????????????????
        Config.BASEURL = intent.getStringExtra("url");
        // ?????????????????????
        Config.HTMLURL = intent.getStringExtra("htmlurl");
        // ????????????
        Config.LANG = intent.getStringExtra("lang");
        // ????????????
        Config.SITECODE = intent.getStringExtra("sitecode");
        // ????????????
        Config.REGION = intent.getStringExtra("region");
        // ???????????????APPID
        Config.WECHAT_APPID = intent.getStringExtra("appid");
        // ????????????
        Config.CITY_NAME = intent.getStringExtra("city");
        // ?????????????????????
        Config.COMMON_LAT = intent.getStringExtra("lat");
        // ?????????????????????
        Config.COMMON_LNG = intent.getStringExtra("lng");
        // ?????????????????????????????????
        Config.about = intent.getStringExtra("about");
    }

    /**
     * ??????????????????????????????
     *
     * @param v
     */
    public void onclick_offLine(View v) {
        GuideAudioApi guideAudioApi = (GuideAudioApi) RequestApiProvider.getAPI(Config.BASEURL +
                Constant.GUIDE_AUDIO);
        if (guideAudioApi == null) {
            guideAudioApi = (GuideAudioApi) RequestApiProvider.registerApiService(Config.BASEURL
                    + Constant.GUIDE_AUDIO, new GuideAudioApi(this));
        }
        int mapID = getIntent().getIntExtra(Constant.IntentKey.ID, 0);
        guideAudioApi.setId(mapID + "");
        guideAudioApi.request(Config.BASEURL + Constant.GUIDE_AUDIO, this, new
                HttpCallBack<String>(this) {
                    @Override
                    public void success(HttpResultBean<String> bean) {
                        String url = bean.getData();
                        if (!TextUtils.isEmpty(url)) {
                            // ??????????????????????????????
                            String fileName = url.substring(0, url.lastIndexOf("."));
                            // ?????????????????????
                            String suffix = url.substring(url.lastIndexOf(".") + 1);
                            String ciphertext = EncryptUtils.encryptMD5ToString(fileName) + "." + suffix;
                            SAVE_AUDIO_PATH = getCacheDir().getAbsolutePath() + File.separator + "audio"
                                    + File.separator + ciphertext;
                        }
                        OffLineDownDialog.Builder builder = new OffLineDownDialog.Builder
                                (MapInformationActivity.this);
                        OffLineDownDialog dialog = builder.setAudoUrl(url).setSaveAudioPath
                                (SAVE_AUDIO_PATH).setUnzipParh(ZIP_AUDIO_PATH).setSaveMapPath
                                (SAVE_MAP_PATH).setMapUrl(mapUrl).create();
                        dialog.show();

                    }
                });

    }

    /**
     * ????????????
     *
     * @param url  ????????????
     * @param path ????????????
     */
    /**
     * ????????????
     */
    private Callback.Cancelable cancelable;

    private void download(String url, String path) {
        RequestParams requestParams = new RequestParams(url);
        requestParams.setAutoResume(true);
        if (!TextUtils.isEmpty(path)) {
            requestParams.setSaveFilePath(path);
        }
        cancelable = x.http().get(requestParams, new DefaultProgressCallBack<File>() {
            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                //                Log.i("??????????????????--->" + total + "\t??????????????????--->" + current);
                // ??????????????????
                float percent = (float) current / total;
                setProgress((int) (percent * 360));
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onSuccess(File result) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    /**
     * ???????????? ??????????????????
     *
     * @param v
     */
    public void onclick_stopAudio(View v) {
        llPalyAudio.setVisibility(View.GONE);
        audioPlayView.stop();
        IMAudioManager.instance().release();
    }


    @Override
    public void onclick(View v) {
        if (v.getId() == R.id.img_play) {
            if (bean == null) {
                return;
            }
            if ("play".equals(v.getTag())) {
                IMAudioManager.instance().release();
                IMAudioManager.instance().playSound(bean.getSceneryAudioPath(), new MediaPlayer
                        .OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        llPalyAudio.setVisibility(View.GONE);
                        audioPlayView.stop();
                    }
                });
                audioPlayView.start();
            } else {
                IMAudioManager.instance().release();
            }
        }
    }

    /**
     * ????????????????????????
     */
    private void requestMapConfig() {
        MapGuideApi mapGuideApi = (MapGuideApi) RequestApiProvider.getAPI(Config.BASEURL +
                Constant.MAP_CONFIG);
        if (mapGuideApi == null) {
            mapGuideApi = (MapGuideApi) RequestApiProvider.registerApiService(Config.BASEURL +
                    Constant.MAP_CONFIG, new MapGuideApi(this));
        }
        mapGuideApi.setId(mapID + "");
        mapGuideApi.request(Config.BASEURL + Constant.MAP_CONFIG, this, new
                HttpCallBack<MapConfigureBean>(MapConfigureBean.class, this) {
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        super.onError(ex, isOnCallback);
                        Log.e("ta", ex.getMessage());
                    }

                    @Override
                    public void success(HttpResultBean<MapConfigureBean> result) {
                        bean = result.getData();
                        mapUrl = bean.getPath();
                        headView.setTitle(bean.getName());
                        // ????????????????????????????????????
                        if (!TextUtils.isEmpty(bean.getPath()) && bean.getPath().lastIndexOf("/") > 0) {
                            String name = bean.getPath().substring(bean.getPath().lastIndexOf("/") + 1);
                            SAVE_MAP_PATH = getCacheDir().getAbsolutePath() + File.separator + "map" +
                                    File.separator + name;
                        }
                        // ??????????????????
                        mapView.setData(bean);
                        // ?????????????????????????????????
                        mapView.setZoom(bean.getZoom());
                        // ??????????????????
                        mapView.location(Double.parseDouble(bean.getCenterLat()), Double.parseDouble(bean
                                .getCenterLon()));
                        requestMapBackground(bean.getPath());
                        download(mapUrl, SAVE_MAP_PATH);
                        guideContent.setInitData(bean);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 001:
                if (resultCode == RESULT_OK && data != null) {
                    mapID = data.getIntExtra("ID", 0);
                    requestMapConfig();
                }
        }
    }

    /**
     * ????????????????????????map????????????
     *
     * @param url
     */
    private void requestMapBackground(final String url) {

        imageLoader.loadImage(FileUtil.hasFile(SAVE_MAP_PATH) ? "file://" + SAVE_MAP_PATH : url,
                null, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                        RectLat lat = new RectLat();
                        lat.setLeft(Double.parseDouble(bean.getOntheLat()));
                        lat.setTop(Double.parseDouble(bean.getOntheLon()));
                        lat.setRight(Double.parseDouble(bean.getUndertheLat()));
                        lat.setBottom(Double.parseDouble(bean.getUndertheLon()));
                        mapView.setMapRectBitmap(lat, bitmap);
                    }
                });
    }

    private Double lat = 0.0;
    private Double lon = 0.0;

    /**
     * ????????????
     *
     * @param v
     */
    public void onclick_location(View v) {
        if (bean == null) {
            return;
        }
        LatLng latLng = new LatLng(lat, lon);
        mapView.location(latLng);
        MapLocation<MapConfigureBean> location = new MapLocation<MapConfigureBean>(lat, lon);
        location.setTitle(bean.getName());
        location.setT(bean);
        View marketView = LayoutInflater.from(this).inflate(R.layout.guide_view_map_guide_market,
                null);
        ImageView img = (ImageView) marketView.findViewById(R.id.img_bitmap);
        img.setImageResource(R.mipmap.guide_current_location);
        TextView txt = (TextView) marketView.findViewById(R.id.txt_name);
        txt.setText("????????????");
        mapView.addMarket(location, marketView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cancelable != null) {
            cancelable.cancel();
        }
        IMAudioManager.instance().release();
        audioPlayView.stop();
        mapView.getMapManager().getIMapLifeCycleManager().onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocation();
        mapView.getMapManager().getIMapLifeCycleManager().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //        IMAudioManager.instance().release();
        //        llPalyAudio.setVisibility(View.GONE);
        //        audioPlayView.stop();
        //        mapView.getMapManager().getIMapLifeCycleManager().onPause();
    }

    /**
     * ??????????????????
     */
    public void pauseVoice() {
        IMAudioManager.instance().release();
        llPalyAudio.setVisibility(View.GONE);
        audioPlayView.stop();
        mapView.getMapManager().getIMapLifeCycleManager().onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.getMapManager().getIMapLifeCycleManager().onSaveInstanceState(outState);
    }

    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption = null;

    /**
     * ????????????
     */
    public void startLocation() {
        mlocationClient = new AMapLocationClient(this);
        //?????????????????????
        mLocationOption = new AMapLocationClientOption();
        //??????????????????
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        lat = aMapLocation.getLatitude();
                        lon = aMapLocation.getLongitude();
                    }
                    mlocationClient.stopLocation();
                }
            }
        });
        // ???????????????????????????????????????Battery_Saving?????????????????????Device_Sensors??????????????????
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // ??????????????????,????????????,?????????2000ms
        mLocationOption.setInterval(2000);
        // ??????????????????
        mlocationClient.setLocationOption(mLocationOption);
        // ????????????????????????????????????????????????????????????????????????????????????????????????????????????
        // ??????????????????????????????????????????????????????????????????1000ms?????????????????????????????????stopLocation()???????????????????????????
        // ???????????????????????????????????????????????????onDestroy()??????
        // ?????????????????????????????????????????????????????????????????????stopLocation()???????????????????????????sdk???????????????
        // ????????????
        mlocationClient.startLocation();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent
                .ACTION_UP) {
            if (MapInformationMenuView.llLineContent.getVisibility() == View.VISIBLE) {
                MapInformationMenuView.llLineContent.setVisibility(View.GONE);
                MapInformationMenuView.llContent.setVisibility(View.VISIBLE);
                if (null != mapView) {
                    mapView.clearMarket();
                }
                if (null != mapView.getPolyline()) {
                    mapView.getPolyline().remove();
                }
            } else {
                finish();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
