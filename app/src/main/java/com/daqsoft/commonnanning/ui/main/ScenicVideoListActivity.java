package com.daqsoft.commonnanning.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewAnimator;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.URLConstant;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.ScenicVideo;
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
import com.example.tomasyb.baselib.widget.HeadView;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 景区视频
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-6-4.15:50
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_SCENIC_VIDEO)
public class ScenicVideoListActivity extends BaseActivity {
    @BindView(R.id.mpanlist_rv)
    RecyclerView mRv;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout mSmatRefresh;
    @BindView(R.id.pan_va)
    ViewAnimator mVa;
    @BindView(R.id.head)
    HeadView mHead;
    @Autowired(name = "mId")
    String strategyId;
    private int mPage = 1;
    private BaseQuickAdapter<ScenicVideo, BaseViewHolder> mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_scenic_video;
    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        mHead.setTitle("景区视频");
        if (strategyId == null) {
            strategyId = getIntent().getStringExtra("mId");
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
        mAdapter = new BaseQuickAdapter<ScenicVideo, BaseViewHolder>(R.layout.item_fun_line, null) {
            @Override
            protected void convert(BaseViewHolder helper, final ScenicVideo bean) {
                if (ObjectUtils.isNotEmpty(bean.getUpload())) {
                    helper.setVisible(R.id.img_paly, true);
                    helper.setOnClickListener(R.id.img_paly, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // 视频播放
                            ARouter.getInstance().build(Constant.ACTIVITY_VIDEO_PLAY).withString
                                    ("content", bean.getUpload()).navigation();
                        }
                    });
                } else {
                    helper.setVisible(R.id.img_paly, false);
                }
                GlideApp.with(mContext).load(bean.getCoverpictureTwoToOne())
                        .placeholder(R.mipmap.common_img_fail_h158)
                        .error(R.mipmap.common_img_fail_h158)
                        .into((ImageView) helper.getView(R.id.img_index_scenic));

                helper.setText(R.id.tv_index_scenic, bean.getName());
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
    private void getData(final boolean isRefresh) {
        if (isRefresh) {
            mPage = 1;
            // 这里的作用是防止下拉刷新的时候还可以上拉加载
            mAdapter.setEnableLoadMore(false);
        }
        RetrofitHelper.getApiService().getScenicVideo(mPage + "", URLConstant.LIMITPAGE + "", strategyId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<ScenicVideo>>() {
                    @Override
                    public void accept(BaseResponse<ScenicVideo> bean) throws Exception {
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

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }
}
