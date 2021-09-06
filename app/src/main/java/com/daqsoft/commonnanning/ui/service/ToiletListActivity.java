package com.daqsoft.commonnanning.ui.service;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.daqsoft.android.ProjectConfig;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.helps_gdmap.CompleteFuncData;
import com.daqsoft.commonnanning.helps_gdmap.HelpMaps;
import com.daqsoft.commonnanning.helps_gdmap.MapNaviUtils;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.ToiletEntity;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.net.common.DefaultObserver;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.refresh.SmartRefreshLayout;
import com.example.tomasyb.baselib.refresh.api.RefreshLayout;
import com.example.tomasyb.baselib.refresh.listener.OnRefreshListener;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.agora.yview.dialog.BaseDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 厕所页面
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/3/19.
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_TOILET_LIST)
public class ToiletListActivity extends BaseActivity {


    @BindView(R.id.toilet_title)
    HeadView toiletTitle;
    //    @BindView(R.id.scenic_search)
    //    CenterDrawableEdittext scenicSearch;
    @BindView(R.id.toilet_list)
    RecyclerView recyclerView;
    @BindView(R.id.no_date_img)
    ImageView noDateImg;
    @BindView(R.id.no_data_content)
    TextView noDataContent;
    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;
    @BindView(R.id.toilet_animator)
    ViewAnimator toiletAnimator;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.toilet_address)
    TextView toiletAddress;

    /**
     * 判断用户是否在当前位置范围内，大于35则不在，反之
     */
    private double checkDistance;
    /**
     * 每页条数
     */
    int PAGE_SIZE = 10;
    /**
     * 页码
     */
    int page = 1;
    /**
     * 列表适配器
     */
    BaseQuickAdapter<ToiletEntity, BaseViewHolder> adapter;
    /**
     * 厕所数据集合
     */
    List<ToiletEntity> toiletList = new ArrayList<>();
    /**
     * 名称
     */
    String name = "";
    /**
     * 自己位置信息
     */
    AMapLocation mapLocation;

    @Override
    public int getLayoutId() {
        return R.layout.activity_toilet_list;
    }

    /**
     * 用户当前位置
     */
    private LatLng selfLatLng;
    /**
     * 地区
     */
    private LatLng commonLatLng;
    /**
     * 弹窗
     */
    private BaseDialog dialog;
    private String latitude = "";
    private String longitude = "";

    /**
     * 检查当前位置
     */
    public void checKLocation() {
        commonLatLng = new LatLng(Double.valueOf(ProjectConfig.COMMON_LAT), Double.valueOf
                (ProjectConfig.COMMON_LNG));
        selfLatLng = new LatLng(Double.valueOf(SPUtils.getInstance().getString("latitude")),
                Double.valueOf(SPUtils.getInstance().getString("longitude")));
        checkDistance = (AMapUtils.calculateLineDistance(selfLatLng, commonLatLng)) / 1000;
        if (checkDistance > 35) {
            dialog = new BaseDialog(this);
            dialog.contentView(R.layout.dialog_base_center).gravity(0).canceledOnTouchOutside
                    (true).show();
            dialog.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 确定
                    dialog.dismiss();
                    latitude = ProjectConfig.COMMON_LAT;
                    longitude = ProjectConfig.COMMON_LNG;
                    refreshLayout.autoRefresh();
                }
            });
            dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    latitude = SPUtils.getInstance().getString("latitude");
                    longitude = SPUtils.getInstance().getString("longitude");
                    refreshLayout.autoRefresh();
                }
            });
            TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
            title.setText("系统检测到你并未在" + ProjectConfig.CITY_NAME + "\n" + "是否切换到" + ProjectConfig
                    .CITY_NAME + "?");
        } else {
            latitude = SPUtils.getInstance().getString("latitude");
            longitude = SPUtils.getInstance().getString("longitude");
            refreshLayout.autoRefresh();
        }
    }

    @Override
    public void initView() {
        toiletTitle.setTitle("厕所");
        initAdapter();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getData();
            }
        });
        if (ObjectUtils.isNotEmpty(SPUtils.getInstance().getString("latitude")) && ObjectUtils
                .isNotEmpty(SPUtils.getInstance().getString("longitude"))) {
            checKLocation();
        } else {
            refreshLayout.autoRefresh();
        }

    }

    /**
     * 初始化适配器
     */
    public void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BaseQuickAdapter<ToiletEntity, BaseViewHolder>(R.layout.item_toilet_list,
                toiletList) {
            @Override
            protected void convert(BaseViewHolder helper, final ToiletEntity item) {
                helper.setText(R.id.item_toilet_name, item.getName());
                helper.setText(R.id.item_toilet_address, item.getAddress());
                helper.setText(R.id.item_toilet_distance, "距当前直线" + item.getDistance() + "km");

                if (ObjectUtils.isNotEmpty(mapLocation) && ObjectUtils.isNotEmpty(item
                        .getLatitude()) && ObjectUtils.isNotEmpty(item.getLongitude())) {
                    helper.setVisible(R.id.item_toilet_distance, true);
                    helper.setVisible(R.id.item_toilet_go, true);
                    helper.setOnClickListener(R.id.item_toilet_go, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MapNaviUtils.isMapNaviUtils(ToiletListActivity.this, mapLocation
                                    .getLatitude() + "", mapLocation.getLongitude() + "",
                                    mapLocation.getAddress(), item.getLatitude(), item
                                            .getLongitude(), item.getAddress());
                        }
                    });
                } else {
                    helper.setVisible(R.id.item_toilet_distance, false);
                    helper.setVisible(R.id.item_toilet_go, false);
                }
            }
        };
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                getData();
            }
        }, recyclerView);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 获取数据
     */
    public void getData() {
        RetrofitHelper.getApiService().getAroundList(page + "", "15", latitude, longitude, "12",
                name, "5").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).subscribe(new DefaultObserver<ToiletEntity>() {
            @Override
            public void onSuccess(BaseResponse<ToiletEntity> response) {
                if (ObjectUtils.isNotEmpty(response)) {
                    if (response.getCode() == 0 && ObjectUtils.isNotEmpty(response.getDatas())) {
                        toiletAnimator.setDisplayedChild(0);
                        if (1 != page) {
                            adapter.loadMoreComplete();
                        } else {
                            toiletList.clear();
                        }
                        toiletList.addAll(response.getDatas());
                        if (ObjectUtils.isNotEmpty(response.getPage()) && response.getPage()
                                .getCurrPage() < response.getPage().getTotalPage()) {
                            adapter.setEnableLoadMore(true);
                        } else {
                            adapter.loadMoreEnd();
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        toiletAnimator.setDisplayedChild(1);
                    }
                } else {
                    toiletAnimator.setDisplayedChild(1);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                refreshLayout.finishRefresh();
            }
        });
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }


    @OnClick(R.id.toilet_address)
    public void onViewClicked() {
        LoadingDialog.showDialogForLoading(this, "获取位置信息中~", true);
        initSelfAddress();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initSelfAddress();
    }

    /**
     * 获取自己当前位置
     */
    public void initSelfAddress() {
        // 获取位置信息
        HelpMaps.startLocation(new CompleteFuncData() {
            @Override
            public void success(AMapLocation location) {
                LoadingDialog.cancelDialogForLoading();
                if (ObjectUtils.isNotEmpty(location)) {
                    mapLocation = location;
                    toiletAddress.setText(location.getAddress() + "");
                }
            }
        });
    }

    /**
     * 监听搜索键，搜索功能
     *
     * @param event
     * @return
     */
    //    @Override
    //    public boolean dispatchKeyEvent(KeyEvent event) {
    //        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER
    //                && event.getAction() == KeyEvent.ACTION_UP) {
    //            name = scenicSearch.getText().toString().trim();
    //            page = 1;
    //            refreshLayout.autoRefresh();
    //            getData();
    //        }
    //        return super.dispatchKeyEvent(event);
    //    }
}
