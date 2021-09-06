package com.daqsoft.commonnanning.ui.mine.interact;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.request.RequestOptions;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.mine.interact.entity.CommentBean;
import com.daqsoft.commonnanning.utils.ShowDialog;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.glide.GlideApp;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.net.common.DefaultObserver;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.refresh.SmartRefreshLayout;
import com.example.tomasyb.baselib.refresh.api.RefreshLayout;
import com.example.tomasyb.baselib.refresh.listener.OnRefreshListener;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.agora.yview.photoview.PicturePreviewActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * 我的点评列表页面
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018.05.07
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_COMMENT)
public class CommentActivity extends BaseActivity {

    /**
     * 当前页码
     */
    int page = 1;
    /**
     * 每页条数
     */
    int limitPage = 10;
    @BindView(R.id.head_mine_comment)
    HeadView headMineComment;
    @BindView(R.id.tv_mine_comment_total)
    TextView tvMineCommentTotal;
    @BindView(R.id.iv_mine_comment_delete)
    ImageView ivMineCommentDelete;
    @BindView(R.id.pull_list_mine_comment)
    RecyclerView pullListMineComment;
    @BindView(R.id.ll_mine_comment)
    LinearLayout llMineComment;
    @BindView(R.id.no_date_img)
    ImageView noDateImg;
    @BindView(R.id.no_data_content)
    TextView noDataContent;
    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    /**
     * 我的评论的数据集合
     */
    private List<CommentBean> myCommentList = new ArrayList<>();
    /**
     * 我的点赞的适配器
     */
    private BaseQuickAdapter<CommentBean, BaseViewHolder> commonAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_comment;
    }

    /**
     * 初始化
     */
    @Override
    public void initView() {
        tvMineCommentTotal.setText("你还未有任何评论信息");
        headMineComment.setTitle("我的评论");
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getData();
            }
        });
        setCommonAdapter();
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    /**
     * 适配器设置
     */
    public void setCommonAdapter() {
        pullListMineComment.setLayoutManager(new LinearLayoutManager(this));
        commonAdapter = new BaseQuickAdapter<CommentBean, BaseViewHolder>
                (R.layout.item_my_comment_list, myCommentList) {
            @Override
            protected void convert(BaseViewHolder holder, final CommentBean resource) {
                GlideApp.with(CommentActivity.this)
                        .load(SPUtils.getInstance().getString(SPCommon.HEAD_IMG))
                        .apply(RequestOptions.bitmapTransform(new CropCircleTransformation()))
                        .error(R.mipmap.my_avatar_default)
                        .placeholder(R.mipmap.my_avatar_default)
                        .into((ImageView) holder.getView(R.id.iv_item_mine_comment_img));
                RatingBar ratingBar = holder.getView(R.id.ratingbar_item_mine_comment);
                ratingBar.setRating(resource.getStar());
                holder.setText(R.id.tv_item_mine_comment_time, resource.getTime());
                holder.setText(R.id.tv_item_mine_comment_content, resource.getComment());
                holder.setText(R.id.tv_item_mine_comment_title, resource.getTitle());
                RecyclerView recyclerView = holder.getView(R.id.recyclerView_item_mine_comment_img);
                LinearLayoutManager layoutManager = new LinearLayoutManager(CommentActivity.this);
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(layoutManager);
                BaseQuickAdapter<String, BaseViewHolder> imageAdapter =
                        new BaseQuickAdapter<String, BaseViewHolder>(R.layout
                                .item_resource_imgae, resource.getPicArr()) {
                            @Override
                            protected void convert(final BaseViewHolder helper, String item) {
                                GlideApp.with(CommentActivity.this)
                                        .load(item)
                                        .placeholder(R.mipmap.common_image_small)
                                        .error(R.mipmap.common_image_small)
                                        .into((ImageView) helper.getView(R.id
                                                .iv_item_resource_img));
                                // 图片预览功能
                                helper.setOnClickListener(R.id.iv_item_resource_img, new View
                                        .OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(CommentActivity.this,
                                                PicturePreviewActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("currentPosition", helper.getPosition());
                                        bundle.putStringArrayList("imgList", (ArrayList<String>)
                                                resource.getPicArr());
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }
                                });
                            }
                        };
                recyclerView.setAdapter(imageAdapter);
                holder.setOnClickListener(R.id.iv_item_mine_comment_cancel, new View
                        .OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cancelComment(resource);
                    }
                });
            }

        };
        pullListMineComment.setAdapter(commonAdapter);
    }

    /**
     * 获取数据
     */
    public void getData() {
        RetrofitHelper.getApiService()
                .getRecommendList(page + "", limitPage + "")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<CommentBean>() {
                    @Override
                    public void onSuccess(BaseResponse<CommentBean> response) {
                        if (ObjectUtils.isNotEmpty(response)
                                && response.getCode() == 0
                                && response.getDatas().size() > 0) {
                            llNoData.setVisibility(View.GONE);
                            llMineComment.setVisibility(View.VISIBLE);
                            if (page == 1) {
                                myCommentList.clear();
                            } else {
                                commonAdapter.loadMoreComplete();
                            }
                            if (ObjectUtils.isNotEmpty(response.getPage())
                                    && response.getPage().getCurrPage() < response.getPage()
                                    .getTotalPage()) {
                                commonAdapter.setEnableLoadMore(true);
                            } else {
                                commonAdapter.loadMoreEnd();
                            }
                            tvMineCommentTotal.setText("您累计评论" + response.getPage().getTotal() +
                                    "次");
                            myCommentList.addAll(response.getDatas());
                            commonAdapter.notifyDataSetChanged();
                        } else {
                            llNoData.setVisibility(View.VISIBLE);
                            llMineComment.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        refreshLayout.finishRefresh();
                    }
                });
    }


    /**
     * 取消点赞
     */
    public void cancelComment(final CommentBean commentBean) {
        ShowDialog.showDialog(this, "删除此条评论记录", "删除后数据将无法恢复",
                new ShowDialog.CompleteFuncData() {
                    @Override
                    public void success(String result) {
                        LoadingDialog.showDialogForLoading(CommentActivity.this, "", true);
                        RetrofitHelper.getApiService()
                                .deleteComment(commentBean.getId() + "")
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnSubscribe(new Consumer<Disposable>() {
                                    @Override
                                    public void accept(Disposable disposable) throws Exception {
                                        addDisposable(disposable);
                                    }
                                })
                                .subscribe(new DefaultObserver() {
                                    @Override
                                    public void onSuccess(BaseResponse response) {
                                        if (response.getCode() == 0) {
                                            ToastUtils.showShortCenter("删除评论成功");
                                            page = 1;
                                            getData();
                                        } else {
                                            ToastUtils.showShortCenter("删除评论失败,请稍后再试");
                                        }
                                    }

                                    @Override
                                    public void onFinish() {
                                        super.onFinish();
                                        LoadingDialog.cancelDialogForLoading();
                                    }
                                });
                    }
                });
    }


    @Override
    protected void onResume() {
        super.onResume();
        page = 1;
        refreshLayout.autoRefresh();
        commonAdapter.setEnableLoadMore(false);
        getData();
    }

    @OnClick({R.id.ll_no_data, R.id.iv_mine_comment_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_no_data:
                // 暂无数据
                page = 1;
                getData();
                break;
            case R.id.iv_mine_comment_delete:
                // 删除全部评论记录
                deleteAllComment();
                break;
            default:
                break;
        }
    }


    /**
     * 清空个人点赞记录
     */
    public void deleteAllComment() {
        ShowDialog.showDialog(CommentActivity.this, "删除全部评论记录", "删除后数据将无法恢复", new ShowDialog
                .CompleteFuncData() {
            @Override
            public void success(String result) {
                LoadingDialog.showDialogForLoading(CommentActivity.this, "", true);
                RetrofitHelper.getApiService()
                        .deleteAllComment()
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                addDisposable(disposable);
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DefaultObserver() {
                            @Override
                            public void onSuccess(BaseResponse response) {
                                if (0 == response.getCode()) {
                                    ToastUtils.showShortCenter("删除全部评论成功");
                                } else {
                                    ToastUtils.showShortCenter("删除全部评论失败，请稍后重试");
                                }
                            }

                            @Override
                            public void onFinish() {
                                super.onFinish();
                                LoadingDialog.cancelDialogForLoading();
                            }
                        });
            }
        });
    }

}
