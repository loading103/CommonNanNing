package com.daqsoft.commonnanning.ui.mine.message;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ViewAnimator;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.URLConstant;
import com.daqsoft.commonnanning.http.RetrofitHelper;
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
import com.example.tomasyb.baselib.widget.HeadView;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @version 1.0.1
 * @Description: 消息提示
 * @Author 黄熙
 * @CreateDate 2019-12-17 9:33
 * @since jdk1.8.0_201
 */
@Route(path = Constant.ACTIVITY_MESSAGE)
public class MessageActivity extends BaseActivity {
    @BindView(R.id.mpanlist_rv)
    RecyclerView mRv;
    @BindView(R.id.head)
    HeadView head;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout mSmatRefresh;
    @BindView(R.id.pan_va)
    ViewAnimator mVa;
    private int mPage = 1;
    private BaseQuickAdapter<MessageBean, BaseViewHolder> mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    public void initView() {
        head.setTitle("消息提示");
        head.setBackListener(new HeadView.OnBackListener() {
            @Override
            public void onBack(View v) {
                finish();
            }
        });
        initAdapter();
        mSmatRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData(true);
            }
        });
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BaseQuickAdapter<MessageBean, BaseViewHolder>(R.layout.item_me_message,
                null) {
            @Override
            protected void convert(BaseViewHolder helper, final MessageBean bean) {
                helper.setText(R.id.tv_time, bean.getCreatetime());
                helper.setText(R.id.tv_title, bean.getTitle());
                helper.setText(R.id.tv_content, bean.getContent());
                if (bean.getStatus() == 0) {
                    helper.getView(R.id.iv_status).setVisibility(View.INVISIBLE);
                } else {
                    helper.getView(R.id.iv_status).setVisibility(View.VISIBLE);
                }
                helper.setOnClickListener(R.id.cl_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LogUtils.e("id-->" + bean.getId());
                        String id = bean.getId() + "";
                        Intent intent = new Intent(MessageActivity.this,
                                MessageDetailsActivity.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
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

        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mRv.setAdapter(mAdapter);
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
        RetrofitHelper.getApiService().getMeMessage(mPage + "", URLConstant.LIMITPAGE + "").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).subscribe(new Consumer<BaseResponse>() {
            @Override
            public void accept(BaseResponse baseResponse) throws Exception {
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

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }


    @Override
    protected void onResume() {
        super.onResume();
        getData(true);
    }
}
