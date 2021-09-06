package com.daqsoft.commonnanning.ui.mine.interact;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.SourceType;
import com.daqsoft.commonnanning.common.URLConstant;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.mine.interact.entity.EnshrineEntity;
import com.daqsoft.commonnanning.ui.mine.interact.entity.SceneryType;
import com.daqsoft.commonnanning.ui.specialty.SpecialtyDetailActivity;
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
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.LoadingDialog;
import com.example.tomasyb.baselib.widget.img.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 我的收藏页面
 *
 * @author Huangxi
 * @version 1.0.0
 * @time 2018-8-9
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_COLLECT)
public class CollectActivity extends BaseActivity {

    @BindView(R.id.head_mine_collect)
    HeadView headMineCollect;
    @BindView(R.id.rg_mine_collect)
    RadioGroup rgMineCollect;
    @BindView(R.id.tv_mine_collect_total)
    TextView tvMineCollectTotal;
    @BindView(R.id.iv_mine_collect_delete)
    ImageView ivMineCollectDelete;
    @BindView(R.id.pull_list_mine_collect)
    RecyclerView pullListMineCollect;
    @BindView(R.id.no_date_img)
    ImageView noDateImg;
    @BindView(R.id.no_data_content)
    TextView noDataContent;
    @BindView(R.id.collect_va)
    ViewAnimator mVa;
    /**
     * 当前页码
     */
    int page = 1;
    /**
     * 每页条数
     */
    int limitPage = 15;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;
    @BindView(R.id.ll_top)
    LinearLayout ll_top;

    /**
     * 我的点赞的数据集合
     */
    private List<EnshrineEntity> myCollectList = new ArrayList<>();
    /**
     * 我的点赞的适配器
     */
    private BaseQuickAdapter<EnshrineEntity, BaseViewHolder> commonAdapter;

    /**
     * 收藏类型
     */
    private String resourceType = "";
    /**
     * 收藏类型集合
     */
    private List<SceneryType> resourceTypeList = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_collect;
    }

    /**
     * 初始化
     */
    @Override
    public void initView() {
        noDataContent.setText("你还未有任何收藏信息");
        headMineCollect.setTitle("我的收藏");
        getCollectType();
        setCommonAdapter();
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData(true);
            }
        });
        refreshlayout.autoRefresh();
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    /**
     * 适配器设置
     */
    public void setCommonAdapter() {
        pullListMineCollect.setLayoutManager(new LinearLayoutManager(this));
        commonAdapter = new BaseQuickAdapter<EnshrineEntity, BaseViewHolder>(R.layout
                .item_my_collect_list, myCollectList) {
            @Override
            protected void convert(BaseViewHolder holder, final EnshrineEntity resource) {
                GlideApp.with(CollectActivity.this)
                        .load(resource.getImage())
                        .placeholder(R.mipmap.comimg_fail_240_180)
                        .error(R.mipmap.comimg_fail_240_180)
                        .into((RoundImageView) holder.getView(R.id.iv_item_mine_collect_img));
                holder.setText(R.id.tv_item_mine_collect_name, resource.getTarget());
                if (!ObjectUtils.isNotEmpty(resource.getPrice())
                        || "0.00".equals(resource.getPrice())) {
                    LinearLayout view = (LinearLayout)holder.getView(R.id.ll_item_mine_collect_price);
                    view.setVisibility(View.INVISIBLE);
                } else {
                    holder.setVisible(R.id.ll_item_mine_collect_price, true);
                    holder.setText(R.id.tv_item_mine_collect_price, resource.getPrice());
                }
                holder.setText(R.id.tv_item_mine_collect_type, resource.getSourceTypeName());
                holder.setText(R.id.tv_item_mine_collect_time, "收藏于" + resource.getTime());
                holder.setOnClickListener(R.id.iv_item_mine_collect_delete, new View
                        .OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        cancelCollect(resource);
                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SourceType.RESOURCE_LINE.equals(resource.getSourceType())) {
                            // 线路详情
                            ARouter.getInstance().build(Constant.ACTIVITY_LINEDETAIL)
                                    .withString("mId", resource.getReId() + "")
                                    .navigation();
                        } else if (SourceType.RESOURCE_SCENIC.equals(resource.getSourceType())) {
                            // 景区详情
                            ARouter.getInstance().build(Constant.ACTIVITY_SCENIC_DETAIL)
                                    .withString("mId", resource.getReId() + "")
                                    .navigation();
                        } else if (SourceType.STRATEGY_TYPE.equals(resource.getSourceType())) {
                            // 游记攻略
                            ARouter.getInstance().build(Constant.ACTIVITY_LINEDETAIL)
                                    .withString("mId", resource.getReId() + "")
                                    .navigation();
                        } else if (SourceType.RESOURCE_HOTEL.equals(resource.getSourceType())) {
                            // 酒店
                            ARouter.getInstance().build(Constant.ACTIVITY_HOTEL_DETAIL)
                                    .withString("mId", resource.getReId() + "")
                                    .navigation();
                        } else if (SourceType.RESOURCE_FOOD.equals(resource.getSourceType())) {
                            // 美食
                            ARouter.getInstance().build(Constant.ACTIVITY_FOOD_DETIAL)
                                    .withString("ID",resource.getReId()+"")
                                    .navigation();
                        }else if (SourceType.RESOURCE_SPECIALTY.equals(resource.getSourceType())){
                            // 商品
                            Intent intent = new Intent(CollectActivity.this,SpecialtyDetailActivity.class);
                            intent.putExtra("id", resource.getReId()+"");
                            startActivity(intent);
                        }
                    }
                });
            }
        };
        commonAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getData(false);
            }
        }, pullListMineCollect);
        pullListMineCollect.setAdapter(commonAdapter);
    }

    /**
     * 获取数据
     */
    public void getData(final boolean isRefresh) {
        if (isRefresh) {
            page = 1;
            myCollectList.clear();
            // 这里的作用是防止下拉刷新的时候还可以上拉加载
            commonAdapter.setEnableLoadMore(false);
        }
        RetrofitHelper.getApiService()
                .getEnshrineList(page + "", limitPage + "", resourceType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .subscribe(new Consumer<BaseResponse<EnshrineEntity>>() {
                    @Override
                    public void accept(BaseResponse<EnshrineEntity> response)
                            throws Exception {
                        setData(isRefresh, response.getDatas());
                        tvMineCollectTotal.setText("您累计收藏" + response.getPage().getTotal() +
                                "个好物");
                        if (isRefresh) {
                            commonAdapter.setEnableLoadMore(true);
                            if (ObjectUtils.isNotEmpty(refreshlayout)) {
                                refreshlayout.finishRefresh();
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (isRefresh) {
                            mVa.setDisplayedChild(1);
                            commonAdapter.setEnableLoadMore(true);
                            if (ObjectUtils.isNotEmpty(refreshlayout)) {
                                refreshlayout.finishRefresh();
                            }
                        } else {
                            commonAdapter.loadMoreFail();
                        }
                    }
                });
    }
    /**
     * 设置数据
     *
     * @param isRefresh 刷新与否
     * @param data      数据
     */
    private void setData(boolean isRefresh, List data) {
        page++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            if (size > 0) {
                ll_top.setVisibility(View.VISIBLE);
                commonAdapter.setNewData(data);
                mVa.setDisplayedChild(0);
            } else {
                ll_top.setVisibility(View.GONE);
                mVa.setDisplayedChild(1);
            }
        } else {
            if (size > 0) {
                commonAdapter.addData(data);
            }
        }
        if (size < URLConstant.LIMITPAGE) {
            // 第一页如果不够一页就不显示没有更多数据布局
            commonAdapter.loadMoreEnd(isRefresh);
        } else {
            commonAdapter.loadMoreComplete();
        }
    }

    /**
     * 取消点赞
     */
    public void cancelCollect(final EnshrineEntity enshrineEntity) {
        ShowDialog.showDialog(CollectActivity.this, "删除收藏此条记录", "删除后数据将无法恢复",
                new ShowDialog.CompleteFuncData() {
                    @Override
                    public void success(String result) {
                        deleteCollect(enshrineEntity);
                    }

                });
    }

    /**
     * 取消收藏
     */
    public void deleteCollect(EnshrineEntity enshrine) {
        LoadingDialog.showDialogForLoading(this, "", true);
        RetrofitHelper.getApiService()
                .deleteEnshrine(enshrine.getId() + "")
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
                            ToastUtils.showShortCenter("取消收藏成功");
                            getData(true);
                        } else {
                            ToastUtils.showShortCenter("取消收藏失败,请稍后再试");
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        LoadingDialog.cancelDialogForLoading();
                    }
                });
    }


    @OnClick({R.id.iv_mine_collect_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_mine_collect_delete:
                // 删除全部收藏记录
                deleteAllCollect();
                break;
            default:
                break;
        }
    }


    /**
     * 清空个人点赞记录
     */
    public void deleteAllCollect() {
        ShowDialog.showDialog(CollectActivity.this, "删除全部点赞记录", "删除后数据将无法恢复", new
                ShowDialog.CompleteFuncData() {
                    @Override
                    public void success(String result) {
                        deleteAllCollectService();

                    }
                });
    }

    /**
     * 删除所有收藏
     */
    public void deleteAllCollectService() {
        LoadingDialog.showDialogForLoading(this, "", true);
        RetrofitHelper.getApiService()
                .deleteAllCollect()
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
                            ToastUtils.showShortCenter("删除全部记录成功");
                            getData(true);
                        } else {
                            ToastUtils.showShortCenter("删除全部记录失败，请稍后重试");
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        LoadingDialog.cancelDialogForLoading();
                    }
                });
    }

    /**
     * 获取收藏类型列表
     */
    public void getCollectType() {
        RetrofitHelper.getApiService()
                .getEnshrineType()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<SceneryType>() {
                    @Override
                    public void onSuccess(BaseResponse<SceneryType> response) {
                        if (ObjectUtils.isNotEmpty(response)
                                && 0 == response.getCode()
                                && ObjectUtils.isNotEmpty(response.getDatas())
                                && response.getDatas().size() > 0) {
                            resourceTypeList.clear();
                            SceneryType sceneryType = new SceneryType();
                            sceneryType.setName("全部");
                            sceneryType.setSkey("");
                            resourceTypeList.add(sceneryType);
                            resourceTypeList.addAll(response.getDatas());
                            rgMineCollect.setVisibility(View.VISIBLE);
                            setResourceType();
                        } else {
                            rgMineCollect.setVisibility(View.GONE);
                        }
                    }
                });
    }

    /**
     * 设置按照点赞的资源类型进行删选
     */
    public void setResourceType() {
        rgMineCollect.removeAllViews();
        for (int i = 0; i < resourceTypeList.size(); i++) {
            final RadioButton rbtn = (RadioButton) LayoutInflater.from(this)
                    .inflate(R.layout.include_collect_rbtn, null);
            rbtn.setText(resourceTypeList.get(i).getName());
            rbtn.setTag(resourceTypeList.get(i).getSkey());
            rbtn.setId(i);
            if (i == 0) {
                rbtn.setChecked(true);
            } else {
                rbtn.setChecked(false);
            }
            rbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        resourceType = (String) rbtn.getTag();
                        refreshlayout.autoRefresh();
                    }
                }
            });

            rgMineCollect.addView(rbtn);
        }
    }


}
