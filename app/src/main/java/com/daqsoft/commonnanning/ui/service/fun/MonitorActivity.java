package com.daqsoft.commonnanning.ui.service.fun;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ViewAnimator;

import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.URLConstant;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.scenic.VideoPlayActivity;
import com.daqsoft.commonnanning.ui.service.fun.bean.MonitorListBean;
import com.daqsoft.commonnanning.utils.GlideUtils;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.BaseActivity;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.refresh.SmartRefreshLayout;
import com.example.tomasyb.baselib.refresh.api.RefreshLayout;
import com.example.tomasyb.baselib.refresh.listener.OnRefreshListener;
import com.example.tomasyb.baselib.widget.HeadView;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * 探风景列表页面
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/12/16.
 * @since JDK 1.8
 */
public class MonitorActivity extends BaseActivity {

    @BindView(R.id.head_title)
    HeadView headView;
    @BindView(R.id.swipe_refresh)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.view_animator)
    ViewAnimator viewAnimator;

    /**
     * 探风景适配器
     */
    BaseQuickAdapter monitorAdapter;
    /**
     * 页码
     */
    int page = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_monitor;
    }

    @Override
    public void initView() {
        headView.setTitle("探风景");
        initMonitorAdapter();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getMonitorData();
            }
        });
    }

    @Override
    public void initData() {
        getMonitorData();
    }

    /**
     * 初始化好玩推荐适配器
     */
    private void initMonitorAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        monitorAdapter =
                new BaseQuickAdapter<MonitorListBean, BaseViewHolder>(R.layout.item_fun_live,
                        null) {
            @Override
            protected void convert(BaseViewHolder helper, final MonitorListBean bean) {
                helper.setVisible(R.id.img_paly, true);
                helper.setText(R.id.tv_view_num, bean.getViewNum());
                helper.setOnClickListener(R.id.img_index_scenic, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MonitorActivity.this, VideoPlayActivity.class);
                        intent.putExtra("content", bean.getMonitorPath());
                        startActivity(intent);
                    }
                });
                GlideUtils.loadImage(MonitorActivity.this, helper.getView(R.id.img_index_scenic),
                        bean.getCover(), R.mipmap.common_img_fail_h158);
                helper.setText(R.id.tv_index_scenic, bean.getName());
            }
        };
        monitorAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                getMonitorData();
            }
        });
        monitorAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        recyclerView.setAdapter(monitorAdapter);
    }


    @SuppressLint("CheckResult")
    private void getMonitorData() {
        if (page == 1) {
            // 这里的作用是防止下拉刷新的时候还可以上拉加载
            monitorAdapter.setEnableLoadMore(false);
        }
        RetrofitHelper.getApiService().getMonitorList(URLConstant.LIMITPAGE + "", page + "").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).subscribe(new Consumer<BaseResponse<MonitorListBean>>() {
            @Override
            public void accept(BaseResponse<MonitorListBean> bean) throws Exception {
                refreshLayout.finishRefresh();
                if (bean != null && bean.getCode() == 0 && bean.getDatas().size() > 0) {
                    if (page == 1) {
                        monitorAdapter.setNewData(bean.getDatas());
                    } else {
                        monitorAdapter.addData(bean.getDatas());
                    }
                    viewAnimator.setDisplayedChild(0);
                    if (bean.getPage().getCurrPage() < bean.getPage().getTotalPage()) {
                        monitorAdapter.loadMoreComplete();
                    } else {
                        monitorAdapter.setEnableLoadMore(false);
                    }
                } else {
                    viewAnimator.setDisplayedChild(1);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                refreshLayout.finishRefresh();
            }
        });
    }
}
