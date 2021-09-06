package com.daqsoft.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.daqsoft.bean.MarketBean;
import com.daqsoft.bean.WalkDetailBean;
import com.daqsoft.guide.R;
import com.daqsoft.http.HttpCallBack;
import com.daqsoft.http.HttpResultBean;
import com.daqsoft.http.requestapi.RequestData;
import com.daqsoft.utils.Config;
import com.daqsoft.utils.SelfLatLng;
import com.daqsoft.utils.Utils;
import com.daqsoft.view.guide.MapInformationMenuView;
import com.daqsoft.view.mapview.MyMapView;
import com.daqsoft.view.mapview.bean.MapLocation;

import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 导游顶部recyler适配器
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-3-14
 * @since JDK 1.8
 */

public class MapInformationMenuListAdapter extends RecyclerBaseAdapter<MarketBean,
        MapInformationMenuListAdapter.MyHolder> {

    /**
     * 地图
     */

    private MyMapView mapView;
    /**
     * 资源Id
     */
    private int drawableID;
    /**
     * 列表图标的ID
     */
    private int drawableListID;
    /**
     * 设置路线监听
     */
    private WalkCallBack callBack;
    /**
     * 地图线路的对象
     */
    Polyline polyline;
    /**
     * 线路内容的RecyclerView
     */
    RecyclerView recyclerViewLineContent;
    /**
     * 当前列表
     */
    RecyclerView recyclerView;

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public RecyclerView getRecyclerViewLineContent() {
        return recyclerViewLineContent;
    }

    public void setRecyclerViewLineContent(RecyclerView recyclerViewLineContent) {
        this.recyclerViewLineContent = recyclerViewLineContent;
    }

    public int getDrawableListID() {
        return drawableListID;
    }

    public void setDrawableListID(int drawableListID) {
        this.drawableListID = drawableListID;
    }

    public void setDrawableID(int drawableID) {
        this.drawableID = drawableID;
    }

    public void setCallBack(WalkCallBack callBack) {
        this.callBack = callBack;
    }

    public MapInformationMenuListAdapter(Context context) {
        super(context);
    }

    public void setMapView(MyMapView mapView) {
        this.mapView = mapView;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = this.inflater.inflate(R.layout.guide_adapter_mapinformationmenu, parent, false);
        // 实例化viewholder
        MyHolder viewHolder = new MyHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final MarketBean bean = list.get(position);
        LogUtil.e(drawableListID + "----");
        if (Utils.isnotNull(bean.getAudioPath())) {
            holder.mTv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap
                    .guide_attractions_commentary, 0, 0, 0);
        } else {
            if (drawableListID > 0) {
                holder.mTv.setCompoundDrawablesWithIntrinsicBounds(drawableListID, 0, 0, 0);
            } else {
                holder.mTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        }
        holder.mTv.setText(bean.getName());
        holder.txt_distance.setText("距离" + SelfLatLng.CalculateLineDistance(bean.getLatitude(),
                bean.getLongitude()));
        // 浏览点
        if ("map_sourceType_line".equals(bean.getSourceType())) {
            holder.txt_distance.setVisibility(View.GONE);
            holder.llLineTotal.setVisibility(View.VISIBLE);
            holder.tvLineTotal.setText(bean.getLinePointNum() + "");
          /*  if(holder.getPosition()==0){
                requestWalkDetail(bean);
            }*/
        } else {
            holder.txt_distance.setVisibility(View.VISIBLE);
            holder.llLineTotal.setVisibility(View.GONE);
        }
        if (Config.CITY_NAME.equals("乌鲁木齐")) {
            holder.txt_distance.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("map_sourceType_line".equals(bean.getSourceType())) {
                    requestWalkDetail(bean, 1);
                    return;
                }
                if (mapView != null) {
                    Marker marker = mapView.getMarket(bean.getName());
                    if (marker != null) {
                        mapView.getMapManager().setLocation(new LatLng(Double.parseDouble(bean
                                .getLatitude()), Double.parseDouble(bean.getLongitude())));
                        mapView.getMapManager().setOldMarket(marker);
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

    /**
     * 获得路线数据
     * <p>
     * type =0 表示第一次进入默认选中，不展示下面具体的点位，横向布局
     *
     * @param bean
     */
    public void requestWalkDetail(MarketBean bean, final int type) {
        /**
         * 清除之前的画线
         */
        if (null != mapView.getPolyline()) {
            mapView.getPolyline().remove();
        }
        MapInformationMenuView.tvLineName.setText(bean.getName());
        MapInformationMenuView.tvLineTotal.setText(bean.getLinePointNum() + "");
        RequestData.requestGuideWalkBean(bean.getId() + "", new HttpCallBack<WalkDetailBean>
                (WalkDetailBean.class, context) {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void success(HttpResultBean<WalkDetailBean> bean) {
                if (bean.getDatas() != null) {
                    if (0 != type) {
                        setLineContent(bean.getDatas());
                    }
                    dataList.clear();
                    dataList.addAll(bean.getDatas());
                    setMapLineDetails(0);
                }
            }
        });
    }

    List<WalkDetailBean> dataList = new ArrayList<>();

    /**
     * 地图上面进行线路布局
     */
    public void setMapLineDetails(int position) {
        if (mapView != null) {
            mapView.clearMarket();
        }
        if (null != mapView.getPolyline()) {
            mapView.getPolyline().remove();
        }
        if (dataList != null) {
            // 线路地图上面画线
            List<LatLng> lineLatLngList = new ArrayList<LatLng>();
            for (int i = 0; i < dataList.size(); i++) {
                WalkDetailBean temp = dataList.get(i);
                lineLatLngList.add(new LatLng(Double.parseDouble(temp.getLatitude()), Double
                        .parseDouble(temp.getLongitude())));
                MarketBean b = new MarketBean();
                b.setName(temp.getName());
                b.setId(temp.getId());
                b.setSourceType("map_sourceType_line");
                b.setLatitude(temp.getLatitude());
                b.setLongitude(temp.getLongitude());
                b.setSummary(temp.getSummary());
                if (mapView != null) {
                    MapLocation<MarketBean> location = new MapLocation<MarketBean>(Double
                            .parseDouble(b.getLatitude()), Double.parseDouble(b.getLongitude()));
                    location.setTitle(b.getName());
                    location.setT(b);
                    MarketBean.Market market = b.getMarket();
                    location.setShowInforWindow(true);
                    if (market.getView() == null) {
                        View marketView = LayoutInflater.from(context).inflate(R.layout
                                .guide_view_map_guide_market, null);
                        ImageView img = (ImageView) marketView.findViewById(R.id.img_bitmap);
                        TextView txt = (TextView) marketView.findViewById(R.id.txt_name);
                        txt.setVisibility(View.GONE);
                        img.setVisibility(View.GONE);
                        TextView tvContent = (TextView) marketView.findViewById(R.id
                                .tv_line_number);
                        tvContent.setVisibility(View.VISIBLE);
                        tvContent.setText((i + 1) + "");
                        txt.setText(b.getName());
                        if (i == position) {
                            tvContent.setBackground(context.getResources().getDrawable(R.drawable
                                    .shape_circle_orange));
                            tvContent.setTextColor(context.getResources().getColor(R.color.white));
                        } else {
                            tvContent.setBackground(context.getResources().getDrawable(R.drawable
                                    .shape_circle_white));
                            tvContent.setTextColor(context.getResources().getColor(R.color
                                    .txt_orange));
                        }
                        img.setImageResource(drawableID);
                        market.setView(marketView);
                    }
                    mapView.addMarket(location, market.getView());
                }
            }
            /**
             * 地图上画线
             */
            if (null != mapView.getaMap()) {
                int color = context.getResources().getColor(R.color.orange);
                mapView.setPolyline(mapView.getaMap().addPolyline(new PolylineOptions().
                        addAll(lineLatLngList).width(10).color(Color.argb(255, Color.red(color),
                        Color.green(color), Color.blue(color)))));
            }
        }

    }


    /**
     * @param dataList
     */
    public void setLineContent(final List<WalkDetailBean> dataList) {
        if (Utils.isnotNull(recyclerViewLineContent)) {
            // 横向布局
            MapInformationMenuView.llLineContent.setVisibility(View.VISIBLE);
            MapInformationMenuView.llContent.setVisibility(View.GONE);
            LogUtil.e("显示-------");
            recyclerViewLineContent.setLayoutManager(new LinearLayoutManager(context,
                    LinearLayoutManager.HORIZONTAL, false));
            GuideLineAdapter guideLineAdapter = new GuideLineAdapter(context);
            guideLineAdapter.setData(dataList);
            guideLineAdapter.setListener(new GuideLineAdapter.Listener() {
                @Override
                public void setListener(int position) {
                    mapView.getMapManager().setLocation(Double.parseDouble(dataList.get(position)
                            .getLatitude()), Double.parseDouble(dataList.get(position)
                            .getLongitude()));

                    setMapLineDetails(position);
                    Marker marker = mapView.getMarket(dataList.get(position).getName());
                    if (marker != null) {
                        if (marker.isInfoWindowShown()) {
                            marker.hideInfoWindow();
                            return;
                        }
                        marker.showInfoWindow();
                    }
                }
            });
            recyclerViewLineContent.setAdapter(guideLineAdapter);
        }
    }


    /**
     * 适配器绑定类
     */
    public static class MyHolder extends RecyclerView.ViewHolder {
        TextView mTv;
        TextView txt_distance;
        TextView txt_level;
        LinearLayout llLineTotal;
        TextView tvLineTotal;

        public MyHolder(View itemView) {
            super(itemView);
            mTv = (TextView) itemView.findViewById(R.id.txt_content);
            txt_distance = (TextView) itemView.findViewById(R.id.tv_distance);
            txt_level = (TextView) itemView.findViewById(R.id.tv_scenic_level);
            llLineTotal = (LinearLayout) itemView.findViewById(R.id.ll_line_total);
            tvLineTotal = (TextView) itemView.findViewById(R.id.tv_line_total);
        }
    }

    /**
     * 路线点击返回数据回调
     */
    public interface WalkCallBack {
        void walkResult(int scource, List<MarketBean> listBean);
    }
}
