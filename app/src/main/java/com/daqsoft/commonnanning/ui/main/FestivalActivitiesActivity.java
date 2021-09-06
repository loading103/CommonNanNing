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
import com.daqsoft.commonnanning.common.ParamsCommon;
import com.daqsoft.commonnanning.common.URLConstant;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.IndexActivity;
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
import com.example.tomasyb.baselib.widget.HeadView;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 节庆活动
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-6-4.15:50
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_FESTIVAL)
public class FestivalActivitiesActivity extends BaseActivity {
    @BindView(R.id.mpanlist_rv)
    RecyclerView mRv;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout mSmatRefresh;
    @BindView(R.id.pan_va)
    ViewAnimator mVa;
    @BindView(R.id.head)
    HeadView mHead;
    private int mPage = 1;
    private BaseQuickAdapter<IndexActivity, BaseViewHolder> mAdapter;
    /**
     * 标题
     */
    @Autowired(name = "title")
    String title = "";
    /**
     * code
     */
    @Autowired(name = "code")
    String code = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_fistavel;
    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        mHead.setTitle(title == "" ? "节庆活动" : title);
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
        mAdapter = new BaseQuickAdapter<IndexActivity, BaseViewHolder>(R.layout.item_fun_line,
                null) {
            @Override
            protected void convert(BaseViewHolder helper, final IndexActivity bean) {
                helper.setVisible(R.id.ll_bottom, true);
                helper.setVisible(R.id.tv_point, false);
                helper.setVisible(R.id.tv_star, false);
                helper.setVisible(R.id.tv_msg, false);
                GlideUtils.loadImage(mContext, (ImageView) helper.getView(R.id.img_index_scenic),
                        bean.getCoverTwoToOne(), R.mipmap.common_ba_banner);
                helper.setText(R.id.tv_index_scenic, bean.getTitle());
                if (ObjectUtils.isNotEmpty(bean.getRegionName())) {
                    helper.setText(R.id.tv_seven, bean.getRegionName());
                    helper.setVisible(R.id.tv_seven, true);
                } else {
                    helper.setVisible(R.id.tv_seven, false);
                }
                helper.setText(R.id.tv_time, bean.getCreateTime());
                helper.setOnClickListener(R.id.img_index_scenic, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ARouter.getInstance().build(Constant.ACTIVITY_DETAILS_WEB).withString("id"
                                , bean.getId() + "").withString("code",
                                code).withString("title", "活动详情").navigation();
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
    private void getData(final boolean isRefresh) {
        if (isRefresh) {
            mPage = 1;
            // 这里的作用是防止下拉刷新的时候还可以上拉加载
            mAdapter.setEnableLoadMore(false);
        }
        RetrofitHelper.getApiService().getActivityList(mPage + "", URLConstant.LIMITPAGE + "", ""
                , code == "" ? ParamsCommon.ACTIVITY_CHANELCODE : code).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).subscribe(new Consumer<BaseResponse<IndexActivity>>() {
            @Override
            public void accept(BaseResponse<IndexActivity> bean) throws Exception {
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
