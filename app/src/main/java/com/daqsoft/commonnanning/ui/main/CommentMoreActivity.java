package com.daqsoft.commonnanning.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.ViewAnimator;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.URLConstant;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.mine.interact.entity.CommentBean;
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
import com.example.tomasyb.baselib.widget.img.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.agora.yview.img.CircleImageView;
import io.agora.yview.photoview.PicturePreviewActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 更多评论Activity
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-7-17
 * @since JDK 1.8.0_171
 */
@Route(path = Constant.ACTIVITY_COMMENTMORE)
public class CommentMoreActivity extends BaseActivity {
    @BindView(R.id.mpanlist_rv)
    RecyclerView mRv;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout mSmatRefresh;
    @BindView(R.id.pan_va)
    ViewAnimator mVa;
    @BindView(R.id.head)
    HeadView mHead;
    @Autowired(name = "id")
    String mRid;
    @Autowired(name = "type")
    String soucetype;
    @Autowired(name = "TYPE")
    String pageType;
    @Autowired(name = "name")
    String name;

    private int mPage = 1;
    private BaseQuickAdapter<CommentBean, BaseViewHolder> mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_comment_more;
    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);

        if (mRid == null || mRid.isEmpty()) {
            mRid = getIntent().getStringExtra("id");
        }
        if (soucetype == null || soucetype.isEmpty()) {
            soucetype = getIntent().getStringExtra("type");
        }
        if (pageType == null || pageType.isEmpty()) {
            pageType = getIntent().getStringExtra("TYPE");
        }
        if (name == null || name.isEmpty()) {
            name = getIntent().getStringExtra("name");
        }


        mHead.setTitle("全部评论");
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
        mAdapter = new BaseQuickAdapter<CommentBean, BaseViewHolder>(R.layout.item_comment, null) {
            @Override
            protected void convert(BaseViewHolder helper, final CommentBean bean) {
                GlideApp.with(mContext).load(bean.getHeadPath())
                        .placeholder(R.mipmap.my_avatar_default)
                        .error(R.mipmap.my_avatar_default)
                        .into((CircleImageView) helper.getView(R.id.iv_head_portrait));
                helper.setText(R.id.tv_comment_user_name, bean.getName());
                helper.setText(R.id.tv_comment, bean.getComment());
                helper.setText(R.id.tv_comment_time, bean.getTime());
                // 设置评论星级
                RatingBar ratingbar = helper.getView(R.id.ratingbar);
                ratingbar.setRating(bean.getStar());
                RecyclerView recyclerView = helper.getView(R.id.recyclerView_item_comment_img);
                recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                });
                BaseQuickAdapter<String, BaseViewHolder> imageAdapter =
                        new BaseQuickAdapter<String, BaseViewHolder>(R.layout
                                .item_resource_imgae, bean.getPicArr()) {
                            @Override
                            protected void convert(final BaseViewHolder helper, String item) {
                                GlideApp.with(mContext)
                                        .load(item)
                                        .placeholder(R.mipmap.common_image_small)
                                        .error(R.mipmap.common_image_small)
                                        .into((RoundImageView) helper.getView(R.id
                                                .iv_item_resource_img));
                                // 图片预览功能
                                helper.setOnClickListener(R.id.iv_item_resource_img, new View
                                        .OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(mContext,
                                                PicturePreviewActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("currentPosition", helper.getPosition());
                                        bundle.putStringArrayList("imgList", (ArrayList<String>)
                                                bean.getPicArr());
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }
                                });
                            }
                        };
                recyclerView.setAdapter(imageAdapter);
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
        RetrofitHelper.getApiService().getCommentInfo(mRid, soucetype, mPage + "", URLConstant
                .LIMITPAGE + "")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<CommentBean>>() {
                    @Override
                    public void accept(BaseResponse<CommentBean> line) throws Exception {
                        setData(isRefresh, line.getDatas());
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


    @OnClick(R.id.tv_wite_comment)
    public void onViewClicked() {
        ARouter.getInstance().build(Constant.ACTIVITY_WRITECOMMENT).withString("name", name)
                .withString("id", mRid)
                .withString("type", soucetype)
                .navigation();
    }
}
