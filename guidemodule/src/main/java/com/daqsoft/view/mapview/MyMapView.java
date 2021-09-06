package com.daqsoft.view.mapview;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.Polyline;
import com.daqsoft.bean.MapConfigureBean;
import com.daqsoft.guide.R;
import com.daqsoft.utils.ShowToast;
import com.daqsoft.view.mapview.bean.MapLocation;
import com.daqsoft.view.mapview.bean.RectLat;
import com.daqsoft.view.mapview.impl.GaoDeMapManager;

import org.xutils.common.util.LogUtil;

import java.util.List;


/**
 * 我的地图自定义控件
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-3-14.
 * @since JDK 1.8
 */

public class MyMapView extends FrameLayout
        implements AMap.OnMarkerClickListener, AMap.OnMapClickListener {
    MapView mapView;
    private GaoDeMapManager mapManager;
    /**
     * 是否显示弹出框
     */
    private boolean isShowInforWindow = false;

    private AMap aMap;

    private MapLocation mapLocation;
    /**
     * 地图线路的对象
     */
    Polyline polyline;

    /**
     * 地图配置的数据信息
     */
    MapConfigureBean mapBean;

    public Polyline getPolyline() {
        return polyline;
    }

    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
    }

    public MyMapView(@NonNull Context context) {
        super(context);
        init();
    }

    public MyMapView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyMapView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setShowInforWindow(boolean showInforWindow) {
        isShowInforWindow = showInforWindow;
    }

    /**
     * 初始化
     */
    private void init() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.guide_view_mymap, null);
        MapView mapView = (MapView) v.findViewById(R.id.view_map);
        aMap = mapView.getMap();
        this.addView(v, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mapManager = new GaoDeMapManager(mapView);
        mapManager.setOnMarkerClickListener(this);
        mapManager.setOnMapClickListener(this);
        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                if (aMap!=null&&mapManager.getOldMarket()!=null){
                    if (mapManager.getOldMarket().isInfoWindowShown()){
                        mapManager.getOldMarket().hideInfoWindow();
                    }
                }
            }
        });
    }

    public AMap getaMap() {
        return aMap;
    }

    /**
     * 设置market弹出窗
     *
     * @param infoWindowAdapter
     */
    public void setInfoWindowAdapter(AMap.InfoWindowAdapter infoWindowAdapter) {
        mapManager.getAMap().setInfoWindowAdapter(infoWindowAdapter);
    }

    /**
     * 通过经纬度定位
     */
    public void location(double latitude, double Longitude) {
        mapManager.setLocation(latitude, Longitude);
    }

    /**
     * 通过经纬度LatLng实体类定位
     */
    public void location(LatLng latLng) {
        mapManager.setLocation(latLng);
    }

    /**
     * 设置地图的配置
     */
    public void setData(MapConfigureBean mapData) {
        this.mapBean = mapData;
    }

    /**
     * 设置inforwindow偏移
     *
     * @param marketX
     * @param marketY
     */
    public void setoffset(int marketX, int marketY) {
        mapManager.setOffset(marketX, marketY);
    }

    /**
     * 通过标题获得marker
     *
     * @param title
     * @return
     */
    public Marker getMarket(String title) {
        return mapManager.getMarket(title);
    }

    /**
     * 添加market
     *
     * @param bean
     * @param bitmap
     */
    public void addMarket(MapLocation bean, int bitmap) {
        mapLocation = bean;
        mapManager.addMarket(bean, bitmap);
    }

    public void centenerMap(LatLngBounds bounds){
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }
    public void centenerMap2(LatLng latLng){
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }
    /**
     * 添加market
     *
     * @param bean
     */
    public void addMarket(MapLocation bean, View v) {
        mapLocation = bean;
        mapManager.addMarket(bean, v);
    }

    /**
     * 清除所有的market
     */
    public void clearMarket() {
        mapManager.clearMarket();
    }

    /**
     * 在某个区域类覆盖自定义图片
     *
     * @param rectF
     * @param bitmap
     */
    public void setMapRectBitmap(RectLat rectF, Bitmap bitmap) {
        mapManager.setMapRectBitmap(rectF, bitmap);
    }

    public void create(Bundle bundle) {
        mapManager.getIMapLifeCycleManager().oncreate(bundle);
    }

    public GaoDeMapManager getMapManager() {
        return mapManager;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        LogUtil.e(mapLocation.toString());
        mapManager.setOldMarket(marker);
        if (mapLocation.isShowInforWindow()) {
            if (marker.isInfoWindowShown()) {
                marker.hideInfoWindow();
            } else {
                marker.showInfoWindow();
            }
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void onMapClick(LatLng latLng) {
//        if (mapManager.getOldMarket() != null) {
//            if (mapManager.getOldMarket().isInfoWindowShown()) {
//                mapManager.getOldMarket().hideInfoWindow();
//            }
//        }
    }

    /**
     * 设置地图的缩放比例
     *
     * @param zoom 当前地图缩放比例
     */
    public void setZoom(String zoom) {
        aMap.moveCamera(CameraUpdateFactory.zoomTo(Float.parseFloat(zoom)));
        LogUtil.e("设置缩放比例");
    }
}
