package com.daqsoft.commonnanning.ui.service;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.location.AMapLocation;
import com.daqsoft.android.ProjectConfig;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.base.IApplication;
import com.daqsoft.commonnanning.common.ComUtils;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.ParamsCommon;
import com.daqsoft.commonnanning.common.URLConstant;
import com.daqsoft.commonnanning.helps_gdmap.CompleteFuncData;
import com.daqsoft.commonnanning.helps_gdmap.HelpMaps;
import com.daqsoft.commonnanning.helps_gdmap.MapNaviUtils;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.AdvertEntity;
import com.daqsoft.commonnanning.ui.entity.IndexBanner;
import com.daqsoft.commonnanning.ui.entity.IndexScenic;
import com.daqsoft.commonnanning.ui.entity.TravelBean;
import com.daqsoft.commonnanning.ui.holder.NetWorkImageHolderView;
import com.daqsoft.commonnanning.utils.GlideUtils;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.refresh.SmartRefreshLayout;
import com.example.tomasyb.baselib.refresh.api.RefreshLayout;
import com.example.tomasyb.baselib.refresh.listener.OnRefreshListener;
import com.example.tomasyb.baselib.util.LogUtils;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.SizeUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.widget.HeadView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.agora.yview.banner.ConvenientBanner;
import io.agora.yview.banner.holder.CBViewHolderCreator;
import io.agora.yview.banner.listener.OnItemClickListener;
import io.agora.yview.view.CenterDrawableEdittext;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 旅行社
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-6-4.15:50
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_TRAVEL)
public class TravelAgencyActivity extends BaseActivity {
    @BindView(R.id.mpanlist_rv)
    RecyclerView mRv;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout mSmatRefresh;
    @BindView(R.id.pan_va)
    ViewAnimator mVa;
    @BindView(R.id.head)
    HeadView mHead;
    @Autowired(name = "pagetype")
    int pageType;
    /**
     * 搜索关键字
     */
    private String name = "";
    /**
     * 定位
     */
    @BindView(R.id.va_location)
    ViewAnimator mLocationVa;
    /**
     * 定位的Bar
     */
    @BindView(R.id.img_location)
    ImageView mImgBar;
    /**
     * 定位的字
     */
    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    /**
     * 搜索框
     */
    @BindView(R.id.travel_list_search)
    CenterDrawableEdittext travelListSearch;
    /**
     * 当前页码
     */
    private int mPage = 1;
    /**
     * banner
     */
    @BindView(R.id.index_banner)
    ConvenientBanner mBanner;
    /**
     * 是否需要轮播
     */
    private boolean canScroll = false;
    /**
     * 适配器
     */
    private BaseQuickAdapter<TravelBean, BaseViewHolder> mAdapter;
    /**
     * 当前位置的location
     */
    AMapLocation mapLocation = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_travel;
    }

    public void scrollManger() {
        LogUtils.v("getTotalScrollRange: " + appBarLayout.getTotalScrollRange());
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (appBarLayout.getTotalScrollRange() - SizeUtils.dp2px(6) < Math.abs(verticalOffset)) {
                    // TODO: 2019-3-20 滑动到顶部,操作定位视图
                    tv_location.setVisibility(View.INVISIBLE);
                    mImgBar.setVisibility(View.INVISIBLE);
                } else {
                    tv_location.setVisibility(View.VISIBLE);
                    mImgBar.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void initView() {
        scrollManger();
        ARouter.getInstance().inject(this);
        // 购物
        if (pageType == 1) {
            mHead.setTitle("购物");
        } else {
            mHead.setTitle("旅行社");
        }
        mHead.setBackListener(new HeadView.OnBackListener() {
            @Override
            public void onBack(View v) {
                finish();
            }
        });
        initAdapter();
        mSmatRefresh.autoRefresh();
        mSmatRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData(true);
            }
        });
        getBanner();
        if(IApplication.getInstance().getIsAgreePrivate()) {
            setLocation();
        }
    }

    /**
     * 设置定位
     */
    private void setLocation() {
        mLocationVa.setDisplayedChild(0);
        HelpMaps.startLocation(new CompleteFuncData() {
            @Override
            public void success(AMapLocation location) {
                if (ObjectUtils.isNotEmpty(location)) {
                    mapLocation = location;
                    try {
                        tv_location.setText(location.getAddress());
                        mLocationVa.setDisplayedChild(1);
                        HelpMaps.stopLocation();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BaseQuickAdapter<TravelBean, BaseViewHolder>(R.layout.item_travel, null) {
            @Override
            protected void convert(BaseViewHolder helper, final TravelBean bean) {
                helper.setText(R.id.tv_title, bean.getName());
                helper.setText(R.id.tv_phone, "电话:" + bean.getPhone());
                helper.setText(R.id.tv_address, "地址:" + bean.getAddress());
                LogUtils.e("当前经纬度-->" + bean.getLatitude());
                if (ObjectUtils.isNotEmpty(bean.getLatitude()) && ObjectUtils.isNotEmpty(bean.getLongitude())) {
                    helper.setVisible(R.id.img_location, true);
                    helper.setOnClickListener(R.id.ll_address, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (ObjectUtils.isNotEmpty(mapLocation) && ObjectUtils.isNotEmpty(bean.getLatitude()) && ObjectUtils.isNotEmpty(bean.getLongitude())) {
                                MapNaviUtils.isMapNaviUtils(TravelAgencyActivity.this,
                                        mapLocation.getLatitude() + "",
                                        mapLocation.getLongitude() + "", mapLocation.getAddress()
                                        , bean.getLatitude(), bean.getLongitude(),
                                        bean.getAddress());
                            } else {
                                ToastUtils.showShortCenter(getResources().getString(R.string.no_map_navi));
                            }
                        }
                    });
                } else {
                    helper.setVisible(R.id.img_location, false);
                }
                if (ObjectUtils.isNotEmpty(bean.getLogoOneToOne())) {
                    helper.getView(R.id.img_head).setVisibility(View.VISIBLE);
                    GlideUtils.loadImage(mContext, (ImageView) helper.getView(R.id.img_head),
                            bean.getLogoOneToOne(), R.mipmap.comimg_fail_240_180);
                } else {
                    helper.getView(R.id.img_head).setVisibility(View.GONE);
                }
                helper.setOnClickListener(R.id.tv_phone, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ComUtils.showQueryCancleDialogPhone(mContext, bean.getPhone());
                    }
                });
                helper.setOnClickListener(R.id.ll_travel, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if ("南宁".equals(ProjectConfig.CITY_NAME)) {
                            Intent intent = new Intent(TravelAgencyActivity.this,
                                    TravelAgencyDetailsActivity.class);
                            intent.putExtra("id", bean.getId());
                            startActivity(intent);
                        }
                    }
                });
            }
        };
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getData(false);
            }
        });
        mImgBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocation();
            }
        });
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mRv.setAdapter(mAdapter);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
            name = travelListSearch.getText().toString().trim();
            getData(true);
            return true;
        }
        return super.dispatchKeyEvent(event);

    }

    /**
     * @param list
     */
    public void initBanner(List<IndexBanner> list) {
        mBanner.setPages(new CBViewHolderCreator() {
            @Override
            public NetWorkImageHolderView createHolder(View itemView) {
                return new NetWorkImageHolderView(itemView, TravelAgencyActivity.this);
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_banner_img;
            }
        }, list)
                // 设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.bga_banner_point_disabled,
                        R.drawable.bga_banner_point_enabled,}).setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        }).setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        //      .setOnPageChangeListener(this)
        // 设置翻页监听
        //        convenientBanner.setManualPageable(false);
        //  设置不能手动影响
        if (list.size() == 1) {
            mBanner.setCanLoop(false);
            mBanner.setPointViewVisible(false);
            canScroll = false;
            mBanner.stopTurning();
        } else {
            canScroll = true;
            mBanner.startTurning();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 开始自动翻页
        if (canScroll) {
            mBanner.startTurning();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (canScroll) {
            // 停止翻页
            mBanner.stopTurning();
        }
    }

    /**
     * 获取数据
     */
    @SuppressLint("CheckResult")
    private void getData(final boolean isRefresh) {
        if (isRefresh) {
            mPage = 1;
            // 这里的作用是防止下拉刷新的时候还可以上拉加载
            mAdapter.setEnableLoadMore(false);
        }
        if (pageType == 0) {
            RetrofitHelper.getApiService().getTravelList(mPage + "", URLConstant.LIMITPAGE + "",
                    name, "").subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() {
                @Override
                public void accept(Disposable disposable) throws Exception {
                    addDisposable(disposable);
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BaseResponse<TravelBean>>() {
                @Override
                public void accept(BaseResponse<TravelBean> bean) throws Exception {
                    setData(isRefresh, bean.getDatas());
                    if (isRefresh) {
                        mAdapter.setEnableLoadMore(true);
                        if (ObjectUtils.isNotEmpty(mSmatRefresh)) {
                            mSmatRefresh.finishRefresh();
                        }
                    }

                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    if (isRefresh) {
                        mVa.setDisplayedChild(1);
                        mAdapter.setEnableLoadMore(true);
                        if (ObjectUtils.isNotEmpty(mSmatRefresh)) {
                            mSmatRefresh.finishRefresh();
                        }
                    } else {
                        mAdapter.loadMoreFail();
                    }
                }
            });
        } else {
            RetrofitHelper.getApiService().getShopData(name, mPage + "", URLConstant.LIMITPAGE +
                    "").subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() {
                @Override
                public void accept(Disposable disposable) throws Exception {
                    addDisposable(disposable);
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BaseResponse<IndexScenic>>() {
                @Override
                public void accept(BaseResponse<IndexScenic> bean) throws Exception {
                    List<TravelBean> list = new ArrayList<>();
                    for (int i = 0; i < bean.getDatas().size(); i++) {
                        TravelBean bea = new TravelBean();
                        bea.setAddress(bean.getDatas().get(i).getAddress());
                        bea.setPhone(bean.getDatas().get(i).getPhone());
                        bea.setName(bean.getDatas().get(i).getName());
                        bea.setLatitude(bean.getDatas().get(i).getLatitude());
                        bea.setLongitude(bean.getDatas().get(i).getLongitude());
                        bea.setLogoOneToOne(bean.getDatas().get(i).getCoverOneToOne());
                        list.add(bea);
                    }
                    setData(isRefresh, list);
                    if (isRefresh) {
                        mAdapter.setEnableLoadMore(true);
                        if (ObjectUtils.isNotEmpty(mSmatRefresh)) {
                            mSmatRefresh.finishRefresh();
                        }
                    }

                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    if (isRefresh) {
                        mVa.setDisplayedChild(1);
                        mAdapter.setEnableLoadMore(true);
                        if (ObjectUtils.isNotEmpty(mSmatRefresh)) {
                            mSmatRefresh.finishRefresh();
                        }
                    } else {
                        mAdapter.loadMoreFail();
                    }
                }
            });
        }
    }

    /**
     * 设置数据
     *
     * @param isRefresh 刷新与否
     * @param data      数据
     */
    private void setData(boolean isRefresh, List data) {
        mPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            if (size > 0) {
                mAdapter.setNewData(data);
                mVa.setDisplayedChild(0);
            } else {
                mVa.setDisplayedChild(1);
            }
        } else {
            if (size > 0) {
                mAdapter.addData(data);
            }
        }
        if (size < URLConstant.LIMITPAGE) {
            // 第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isRefresh);
        } else {
            mAdapter.loadMoreComplete();
        }
    }

    /**
     * 获取banner的数据
     */
    @SuppressLint("CheckResult")
    private void getBanner() {
        RetrofitHelper.getApiService().getIndexBannar(ParamsCommon.TravelAgencyActivity_BANNER).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).subscribe(new Consumer<BaseResponse<AdvertEntity>>() {
            @Override
            public void accept(BaseResponse<AdvertEntity> bean) throws Exception {
                List<IndexBanner> mlist = new ArrayList<>();
                mlist.clear();
                if (bean.getCode() == 0 && bean.getDatas().size() > 0) {
                    for (int i = 0; i < bean.getDatas().size(); i++) {
                        IndexBanner beans = new IndexBanner();
                        beans.setId(bean.getDatas().get(i).getId());
                        beans.setImg(bean.getDatas().get(i).getPics().get(0));
                        mlist.add(beans);
                    }
                    initBanner(mlist);
                } else {
                    IndexBanner beans = new IndexBanner();
                    beans.setId("");
                    beans.setImg("");
                    mlist.add(beans);
                    initBanner(mlist);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                List<IndexBanner> mlist = new ArrayList<>();
                IndexBanner beans = new IndexBanner();
                beans.setId("");
                beans.setImg("");
                mlist.add(beans);
                initBanner(mlist);
            }
        });
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }
}
