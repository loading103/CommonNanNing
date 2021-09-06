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
import com.bumptech.glide.request.RequestOptions;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.URLConstant;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.ScenicChild;
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
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * 景区详情子景点列表
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-6-4.15:50
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_SCENIC_CHILD)
public class ScenicChildListActivity extends BaseActivity {
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
    private BaseQuickAdapter<ScenicChild,BaseViewHolder> mAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_scenic_child;
    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        mHead.setTitle("景点");
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

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BaseQuickAdapter<ScenicChild, BaseViewHolder>(R.layout.item_scenic_child,null) {
            @Override
            protected void convert(BaseViewHolder helper, final ScenicChild bean) {

                GlideApp.with(mContext).load(bean.getCoverOneToOne())
                        .placeholder(R.mipmap.common_img_fail_h158)
                        .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(10,0)))
                        .error(R.mipmap.common_img_fail_h158)
                        .into((ImageView) helper.getView(R.id.index_img_jie));

                helper.setText(R.id.tv_title, bean.getName());
                helper.setText(R.id.tv_content, bean.getIntroduce());


            }
        };
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ScenicChild item = (ScenicChild)adapter.getItem(position);
                ARouter.getInstance().build(Constant.ACTIVITY_SCENIC_CHILD_DETAIL).withString("mId",item.getId()).navigation();
            }
        });
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
        if (isRefresh){
            mPage=1;
            // 这里的作用是防止下拉刷新的时候还可以上拉加载
            mAdapter.setEnableLoadMore(false);
        }

        RetrofitHelper.getApiService().getScenicChild(mPage+"", URLConstant.LIMITPAGE+"", strategyId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<ScenicChild>>() {
                    @Override
                    public void accept(BaseResponse<ScenicChild> bean) throws Exception {
                        setData(isRefresh, bean.getDatas());
                        if (isRefresh){
                            mAdapter.setEnableLoadMore(true);
                            if (ObjectUtils.isNotEmpty(mSmatRefresh)){
                                mSmatRefresh.finishRefresh();
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (isRefresh){
                            mVa.setDisplayedChild(1);
                            mAdapter.setEnableLoadMore(true);
                            if (ObjectUtils.isNotEmpty(mSmatRefresh)){
                                mSmatRefresh.finishRefresh();
                            }
                        }else {
                            mAdapter.loadMoreFail();
                        }
                    }
                });
    }

    /**
     * 设置数据
     * @param isRefresh
     * @param data
     */
    private void setData(boolean isRefresh, List data) {
        mPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            if (size>0){
                mVa.setDisplayedChild(0);
                mAdapter.setNewData(data);
            }else {
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
