package com.daqsoft.commonnanning.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewAnimator;

import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.ComUtils;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.common.URLConstant;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.GuideBean;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.glide.GlideApp;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.refresh.SmartRefreshLayout;
import com.example.tomasyb.baselib.refresh.api.RefreshLayout;
import com.example.tomasyb.baselib.refresh.listener.OnRefreshListener;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.widget.HeadView;

import java.util.List;

import butterknife.BindView;
import io.agora.yview.view.CenterDrawableEdittext;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 导游导览列表
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-6-4.15:50
 * @since JDK 1.8
 */
public class GuideListActivity extends BaseActivity {
    @BindView(R.id.mpanlist_rv)
    RecyclerView mRv;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout mSmatRefresh;
    @BindView(R.id.pan_va)
    ViewAnimator mVa;
    @BindView(R.id.head)
    HeadView mHead;
    /**
     * 搜索框
     */
    @BindView(R.id.travel_list_search)
    CenterDrawableEdittext travelListSearch;
    private int mPage = 1;
    private int mPageType = 0;
    private BaseQuickAdapter<GuideBean, BaseViewHolder> mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide_list;
    }

    @Override
    public void initView() {
        mPageType = getIntent().getIntExtra("PageType", 0);
        mHead.setTitle("导游导览");
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
        mAdapter = new BaseQuickAdapter<GuideBean, BaseViewHolder>(R.layout.item_fun_line, null) {
            @Override
            protected void convert(BaseViewHolder helper, final GuideBean bean) {
                helper.setVisible(R.id.tv_seven, true);
                helper.setText(R.id.tv_seven, bean.getRegionName());
                GlideApp.with(mContext).load(bean.getCoverTwoToOne()).placeholder(R.mipmap
                        .common_ba_banner).error(R.mipmap.common_ba_banner).into((ImageView)
                        helper.getView(R.id.img_index_scenic));
                helper.setText(R.id.tv_index_scenic, bean.getName());
                helper.setOnClickListener(R.id.img_index_scenic, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ObjectUtils.isNotEmpty(bean.getId())) {
                            boolean isFirst = false;
                            if (helper.getPosition() == 0) {
                                isFirst = true;
                            } else {
                                isFirst = false;
                            }
                            ComUtils.goToGuide(bean.getId(), mContext, isFirst);
                        } else {
                            ToastUtils.showShort("暂无数据!");
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
        mRv.setAdapter(mAdapter);
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
        RetrofitHelper.getApiService().getMapGuideListNear(SPUtils.getInstance().getString
                (SPCommon.LATITUDE), SPUtils.getInstance().getString(SPCommon.LONGITUDE), name,
                mPage + "", URLConstant.LIMITPAGE + "").subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BaseResponse<GuideBean>>() {
            @Override
            public void accept(BaseResponse<GuideBean> bean) throws Exception {
                setData(isRefresh, bean.getDatas());
                if (isRefresh) {
                    mAdapter.setEnableLoadMore(true);
                    mSmatRefresh.finishRefresh();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (isRefresh) {
                    mVa.setDisplayedChild(1);
                    mAdapter.setEnableLoadMore(true);
                    mSmatRefresh.finishRefresh();
                } else {
                    mAdapter.loadMoreFail();
                }
            }
        });
    }

    private void setData(boolean isRefresh, List data) {
        mPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            if (size > 0) {
                mVa.setDisplayedChild(0);
                mAdapter.setNewData(data);
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
     * 搜索关键字
     */
    private String name = "";

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent
                .ACTION_UP) {
            name = travelListSearch.getText().toString().trim();
            getData(true);
            return true;
        }
        return super.dispatchKeyEvent(event);

    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }
}
