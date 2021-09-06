package com.daqsoft.commonnanning.ui.specialty;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.URLConstant;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.IndexBanner;
import com.daqsoft.commonnanning.ui.entity.SpecialListBean;
import com.daqsoft.commonnanning.ui.holder.NetWorkImageHolderView;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.glide.GlideApp;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.refresh.SmartRefreshLayout;
import com.example.tomasyb.baselib.refresh.api.RefreshLayout;
import com.example.tomasyb.baselib.refresh.listener.OnRefreshListener;
import com.example.tomasyb.baselib.util.KeyboardUtils;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.LoadingDialog;
import com.example.tomasyb.baselib.widget.img.RoundImageView;

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
 * -----------------------------------------------------------------
 *
 * @version 1.0.1
 * @Description:特产页面
 * @Author 黄熙
 * @CreateDate 2019-3-20 15:56
 * @since jdk1.8.0_201
 * -----------------------------------------------------------------
 */
@Route(path = Constant.ACTIVITY_SPECIALTY_LIST)
public class SpecialtyActivity extends BaseActivity<SpecialtyContact.presenter> implements SpecialtyContact.view {
    @BindView(R.id.route_help_title)
    HeadView routeHelpTitle;
    @BindView(R.id.banner_view)
    ConvenientBanner bannerView;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.scenic_search)
    CenterDrawableEdittext scenicSearch;

    @BindView(R.id.view_animator)
    ViewAnimator viewAnimator;
    @BindView(R.id.swipe_refresh)
    SmartRefreshLayout swipeRefresh;

    /**
     * 食物列表adapter
     */
    private BaseQuickAdapter<SpecialListBean, BaseViewHolder> mAdapter;

    /**
     * 当前加载的页面
     */
    int currentPage = 1;
    /**
     * 搜索关键字
     */
    String name = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_food;
    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        routeHelpTitle.setTitle("特产");
        scrollManger();
        initRecycleView();
        initSearch();
        presenter.getBannerData();
        swipeRefresh.autoRefresh();
        swipeRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData(true);
            }
        });
    }


    public void initRecycleView() {
        LinearLayoutManager lm = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(lm);
        mAdapter =
                new BaseQuickAdapter<SpecialListBean, BaseViewHolder>(R.layout.item_specialty_list, null) {
            @Override
            protected void convert(BaseViewHolder holder, final SpecialListBean datasBean) {
                GlideApp.with(mContext).load(datasBean.getCoverTwoToOne()).placeholder(R.mipmap.common_img_fail_h158).error(R.mipmap.common_img_fail_h158).into((RoundImageView) holder.getView(R.id.image));
                holder.setText(R.id.tv_title, datasBean.getName());
                holder.setText(R.id.tv_content, datasBean.getSummary());
            }
        };
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(SpecialtyActivity.this, SpecialtyDetailActivity.class);
                intent.putExtra("id", ((SpecialListBean) adapter.getItem(position)).getId());
                startActivity(intent);
            }
        }); mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getData(false);
            }
        });
        recyclerview.setAdapter(mAdapter);
    }

    /**
     * 页面滚动操作
     */
    public void scrollManger() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (appBarLayout.getTotalScrollRange() == Math.abs(verticalOffset)) {
                    //  TODO: 2019-3-20 滑动到顶部,操作定位视图
                    bannerView.setVisibility(View.INVISIBLE);
                } else {
                    bannerView.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    @Override
    public SpecialtyContact.presenter initPresenter() {
        return new SpecialtyPresenter(this);
    }


    @Override
    public void showProgress() {
        LoadingDialog.showDialogForLoading((Activity) mContext);
    }

    @Override
    public void hideProgress() {
        LoadingDialog.cancelDialogForLoading();
    }

    @Override
    public void initBanner(List<IndexBanner> list) {
        bannerView.setPages(new CBViewHolderCreator() {
            @Override
            public NetWorkImageHolderView createHolder(View itemView) {
                return new NetWorkImageHolderView(itemView, (Activity) mContext);
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

        // 设置不能手动影响
        if (list.size() == 1) {
            bannerView.stopTurning();
        }
    }


    /**
     * 刷新
     *
     * @param isRefresh
     */
    @SuppressLint("CheckResult")
    private void getData(boolean isRefresh) {
        if (isRefresh) {
            currentPage = 1;
            // 这里的作用是防止下拉刷新的时候还可以上拉加载
            mAdapter.setEnableLoadMore(false);
        }
        RetrofitHelper.getApiService().specialGoodsDetail(currentPage, URLConstant.LIMITPAGE,
                name).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).subscribe(new Consumer<BaseResponse<SpecialListBean>>() {
            @Override
            public void accept(BaseResponse<SpecialListBean> bean) throws Exception {
                setData(isRefresh, bean.getDatas());
                if (isRefresh) {
                    mAdapter.setEnableLoadMore(true);
                    if (ObjectUtils.isNotEmpty(swipeRefresh)) {
                        swipeRefresh.finishRefresh();
                    }
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (isRefresh) {
                    viewAnimator.setDisplayedChild(1);
                    mAdapter.setEnableLoadMore(true);
                    if (ObjectUtils.isNotEmpty(swipeRefresh)) {
                        swipeRefresh.finishRefresh();
                    }
                } else {
                    mAdapter.loadMoreFail();
                }
            }
        });
    }

    private void setData(boolean isRefresh, List data) {
        currentPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            if (size > 0) {
                viewAnimator.setDisplayedChild(0);
                mAdapter.setNewData(data);
            } else {
                viewAnimator.setDisplayedChild(1);
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
     * 按关键字搜索,搜索字段 name
     */
    void initSearch() {
        scenicSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    name = scenicSearch.getText().toString().trim();
                    swipeRefresh.autoRefresh();
                    KeyboardUtils.hideSoftInput(SpecialtyActivity.this);
                }
                return false;
            }
        });
    }
}
