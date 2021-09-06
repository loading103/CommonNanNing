package com.daqsoft.commonnanning.ui.mine.interact;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.request.RequestOptions;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.mine.interact.entity.ThumbEntity;
import com.daqsoft.commonnanning.utils.ShowDialog;
import com.daqsoft.commonnanning.utils.Utils;
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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 我的点赞页面
 *
 * @author Huangxi
 * @version 1.0.0
 * @time 2018-8-9
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_THUMB)
public class ThumbActivity extends BaseActivity {

    /**
     * 当前页码
     */
    int page = 1;
    /**
     * 每页条数
     */
    int limitPage = 10;
    @BindView(R.id.head_mine_like)
    HeadView headMineLike;
    @BindView(R.id.tv_mine_like_total)
    TextView tvMineLikeTotal;
    @BindView(R.id.iv_mine_like_delete)
    ImageView ivMineLikeDelete;
    @BindView(R.id.pull_list_mine_like)
    RecyclerView pullListMineLike;
    @BindView(R.id.ll_mine_like)
    LinearLayout llMineLike;
    @BindView(R.id.no_date_img)
    ImageView noDateImg;
    @BindView(R.id.no_data_content)
    TextView noDataContent;
    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    /**
     * 我的点赞的数据集合
     */
    private List<ThumbEntity> myLikeList = new ArrayList<>();
    /**
     * 我的点赞的适配器
     */
    private BaseQuickAdapter<ThumbEntity, BaseViewHolder> commonAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_like;
    }

    /**
     * 初始化
     */
    @Override
    public void initView() {
        tvMineLikeTotal.setText("你还未有任何点赞信息");
        headMineLike.setTitle("我的点赞");
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
        pullListMineLike.setLayoutManager(new LinearLayoutManager(this));
        commonAdapter = new BaseQuickAdapter<ThumbEntity, BaseViewHolder>(R.layout
                .item_my_like_list, myLikeList) {

            @Override
            protected void convert(BaseViewHolder holder, final ThumbEntity myLikeEntity) {
                GlideApp.with(ThumbActivity.this)
                        .load(SPUtils.getInstance().getString(SPCommon.HEAD_IMG))
                        .apply(new RequestOptions().circleCrop())
                        .placeholder(R.mipmap.my_avatar_default)
                        .error(R.mipmap.my_avatar_default)
                        .into((ImageView) holder.getView(R.id.iv_item_mine_like_img));
                holder.setText(R.id.tv_item_mine_like_time, myLikeEntity.getTime());
                String title = "";
                if (ObjectUtils.isNotEmpty(myLikeEntity.getTitle())) {
                    title = "#" + myLikeEntity.getTitle() + "#";
                    String content = "赞了" + title + Utils.deleteHtmlImg(myLikeEntity.getContent());
                    holder.setText(R.id.tv_item_mine_like_content, Utils.setTextColor(content,
                            ThumbActivity.this.getResources().getColor(R.color.main_default), 2,
                            2 + title.length()));
                } else {
                    holder.setText(R.id.tv_item_mine_like_content, "未知");
                }

                holder.setOnClickListener(R.id.iv_item_mine_like_cancel, new View.OnClickListener
                        () {
                    @Override
                    public void onClick(View view) {
                        cancelMyLike(myLikeEntity);
                    }
                });
            }
        };
        pullListMineLike.setAdapter(commonAdapter);
    }

    /**
     * 获取数据
     */
    public void getData() {
        RetrofitHelper.getApiService()
                .getMyLikeList(page + "", limitPage + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .subscribe(new DefaultObserver<ThumbEntity>() {
                    @Override
                    public void onSuccess(BaseResponse<ThumbEntity> response) {
                        if (ObjectUtils.isNotEmpty(response)
                                && response.getCode() == 0
                                && response.getDatas().size() > 0) {
                            llNoData.setVisibility(View.GONE);
                            llMineLike.setVisibility(View.VISIBLE);
                            if (page == 1) {
                                myLikeList.clear();
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
                            tvMineLikeTotal.setText("您累计点赞" + response.getPage().getTotal() + "次");
                            myLikeList.addAll(response.getDatas());
                            commonAdapter.notifyDataSetChanged();
                        } else {
                            llNoData.setVisibility(View.VISIBLE);
                            llMineLike.setVisibility(View.GONE);
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
     *
     * @param myLikeEntity
     */
    public void cancelMyLike(final ThumbEntity myLikeEntity) {
        ShowDialog.showDialog(ThumbActivity.this, "删除此条点赞记录", "删除后数据将无法恢复", new ShowDialog
                .CompleteFuncData() {
            @Override
            public void success(String result) {
                LoadingDialog.showDialogForLoading(ThumbActivity.this, "", true);
                RetrofitHelper.getApiService()
                        .deleteThumb(myLikeEntity.getReId() + "")
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
                                if (response.getCode() == 0) {
                                    ToastUtils.showShortCenter("取消点赞成功");
                                    page = 1;
                                    getData();
                                } else {
                                    ToastUtils.showShortCenter("取消点赞失败,请稍后再试");
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

    @OnClick({R.id.ll_no_data, R.id.iv_mine_like_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_no_data:
                // 暂无数据
                page = 1;
                getData();
                break;
            case R.id.iv_mine_like_delete:
                // 删除全部点赞记录
                deleteAllThumb();
                break;
        }
    }


    /**
     * 清空个人点赞记录
     */
    public void deleteAllThumb() {
        ShowDialog.showDialog(ThumbActivity.this, "删除全部点赞记录", "删除后数据将无法恢复", new ShowDialog
                .CompleteFuncData() {
            @Override
            public void success(String result) {
                LoadingDialog.showDialogForLoading(ThumbActivity.this, "", true);
                RetrofitHelper.getApiService()
                        .deleteAllThumb()
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
                                if (0 == response.getCode()) {
                                    ToastUtils.showShortCenter("删除全部点赞成功");
                                } else {
                                    ToastUtils.showShortCenter("删除全部点赞失败，请稍后重试");
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
