package com.daqsoft.commonnanning.ui.book;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewAnimator;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.ParamsCommon;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.common.URLConstant;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.AdvertEntity;
import com.daqsoft.commonnanning.ui.entity.IndexBanner;
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
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.widget.HeadView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.agora.yview.banner.ConvenientBanner;
import io.agora.yview.banner.holder.CBViewHolderCreator;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 图书馆
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/3/20 0020
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_LIBRARY)
public class LibraryActivity extends BaseActivity {
    @BindView(R.id.mpanlist_rv)
    RecyclerView mRv;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout mSmatRefresh;
    @BindView(R.id.pan_va)
    ViewAnimator mVa;
    @BindView(R.id.head)
    HeadView mHead;
    @Autowired(name = "pageType")
    int pageType;
    private int mPage = 1;
    private ConvenientBanner mBanner;
    private BaseQuickAdapter<BookLibabry, BaseViewHolder> mAdapter;
    /**
     * 头布局
     */
    private View mHeadView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_library;
    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        if (pageType == 0) {
            mHead.setTitle("博物馆");
        } else {
            mHead.setTitle("图书馆");
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
    }

    private void initAdapter() {
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BaseQuickAdapter<BookLibabry, BaseViewHolder>(R.layout.item_book_library,
                null) {
            @Override
            protected void convert(BaseViewHolder helper, final BookLibabry bean) {
                helper.setText(R.id.gonggao_tv_title, bean.getName());
                helper.setText(R.id.tv_bottom, bean.getAddress());
                helper.setText(R.id.tv_bottomdistence, "距您" + bean.getDistance() + "KM");
                GlideUtils.loadImage(mContext, (ImageView) helper.getView(R.id.item_scenic_img),
                        bean.getLogo(), R.mipmap.comimg_fail_240_180);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ARouter.getInstance().build(Constant.ACTIVITY_LIBRARY_DETAIL).withString
                                ("headimg", bean.getLogo()).withString("name", bean.getName())
                                .withString("address", bean.getAddress()).withString("intaoce",
                                bean.getIntroduce()).withString("lat", bean.getLatitude())
                                .withString("lon", bean.getLongitude()).withString("opentime",
                                bean.getOpenHours()).withString("phone", bean.getTel())
                                .navigation();
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
        mHeadView = LayoutInflater.from(this).inflate(R.layout.include_banner, null, false);
        mBanner = (ConvenientBanner) mHeadView.findViewById(R.id.include_banner);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mAdapter.addHeaderView(mHeadView);
        mRv.setAdapter(mAdapter);
        mHeadView.setVisibility(View.GONE);
        initBanner();
        getBannerData();
    }

    /**
     * 获取数据
     */
    private void getData(final boolean isRefresh) {
        if (isRefresh) {
            mPage = 1;
            // 这里的作用是防止下拉刷新的时候还可以上拉加载
            mAdapter.setEnableLoadMore(false);
        }
        // 0::博物馆
        if (pageType == 0) {
            RetrofitHelper.getApiService().getBoLibabryList(SPUtils.getInstance().getString
                    (SPCommon.LONGITUDE), SPUtils.getInstance().getString(SPCommon.LATITUDE),
                    mPage + "", URLConstant.LIMITPAGE + "").subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Consumer<Disposable>() {
                @Override
                public void accept(Disposable disposable) throws Exception {
                    addDisposable(disposable);
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BaseResponse<BookLibabry>>() {
                @Override
                public void accept(BaseResponse<BookLibabry> baseResponse) throws Exception {
                    setData(isRefresh, baseResponse.getDatas());
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
                        mHeadView.setVisibility(View.GONE);
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
            RetrofitHelper.getApiService().getLibabryList(SPUtils.getInstance().getString
                    (SPCommon.LONGITUDE), SPUtils.getInstance().getString(SPCommon.LATITUDE),
                    mPage + "", "15").subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() {
                @Override
                public void accept(Disposable disposable) throws Exception {
                    addDisposable(disposable);
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BaseResponse<BookLibabry>>() {
                @Override
                public void accept(BaseResponse<BookLibabry> baseResponse) throws Exception {
                    setData(isRefresh, baseResponse.getDatas());
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
                        mHeadView.setVisibility(View.GONE);
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
     * @param isRefresh
     * @param data
     */
    private void setData(boolean isRefresh, List data) {
        mPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            if (size > 0) {
                mHeadView.setVisibility(View.VISIBLE);
                mVa.setDisplayedChild(0);
                mAdapter.setNewData(data);
            } else {
                mHeadView.setVisibility(View.GONE);
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

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    private List<IndexBanner> bannerlist = new ArrayList<>();

    public void setBannerData(List<IndexBanner> list) {
        bannerlist.clear();
        bannerlist.addAll(list);
        mBanner.notifyDataSetChanged();
    }

    /**
     * 初始化banner
     */
    private void initBanner() {
        mBanner.setPages(new CBViewHolderCreator() {
            @Override
            public NetWorkImageHolderView createHolder(View itemView) {
                return new NetWorkImageHolderView(itemView, LibraryActivity.this);
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_banner_img;
            }
        }, bannerlist)
                // 设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.bga_banner_point_disabled, R.drawable
                        .bga_banner_point_enabled,}).setPageIndicatorAlign(ConvenientBanner
                .PageIndicatorAlign.CENTER_HORIZONTAL);
    }

    /**
     * 获取banner数据
     */
    public void getBannerData() {
        String sidecode = "";
        if (pageType == 0) {
            sidecode = ParamsCommon.MUSEUM_BANNER;
        } else {
            sidecode = ParamsCommon.LIBRARY_BANNER;
        }
        RetrofitHelper.getApiService().getIndexBannar(sidecode).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer<Disposable>
                () {
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
                    setBannerData(mlist);
                } else {
                    IndexBanner beans = new IndexBanner();
                    beans.setId("");
                    beans.setImg("");
                    mlist.add(beans);
                    setBannerData(mlist);
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
                setBannerData(mlist);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBanner != null) {
            // 开始自动翻页
            mBanner.startTurning();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mBanner != null) {
            // 停止翻页
            mBanner.stopTurning();
        }
    }
}
