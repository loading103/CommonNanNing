package com.daqsoft.commonnanning.ui.service.complaint;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.ComplaintListEntity;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.net.common.DefaultObserver;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.refresh.SmartRefreshLayout;
import com.example.tomasyb.baselib.refresh.api.RefreshLayout;
import com.example.tomasyb.baselib.refresh.listener.OnRefreshListener;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.widget.HeadView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 投诉列表
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-3-27
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_COMPLAINT_LIST)
public class ComplaintListActivity extends BaseActivity {


    @BindView(R.id.headView)
    HeadView headView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.no_date_img)
    ImageView noDateImg;
    @BindView(R.id.no_data_content)
    TextView noDataContent;
    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;
    @BindView(R.id.iv_complaint_description)
    ImageView ivComplaintDescription;
    @BindView(R.id.iv_complaint_inquire)
    ImageView ivComplaintInquire;
    @BindView(R.id.btn_complaint)
    Button btnComplaint;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    /**
     * 当前页码
     */
    private int pageIndex = 1;
    /**
     * 每页条数
     */
    private final int PAGE_COUNT = 10;
    /**
     * 投诉列表适配器
     */
    BaseQuickAdapter<ComplaintListEntity, BaseViewHolder> adapter;
    /**
     * 投诉列表的数据集合
     */
    List<ComplaintListEntity> complaintList = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_complaintlist;
    }

    @Override
    public void initView() {
        headView.setTitle("投诉列表");
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                request(true);
            }
        });
        initAdapter();
    }

    /**
     * 初始化适配器
     */
    public void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BaseQuickAdapter<ComplaintListEntity, BaseViewHolder>(
                R.layout.adapter_complaintlist, complaintList) {
            @Override
            protected void convert(BaseViewHolder holder, final ComplaintListEntity item) {
                holder.setText(R.id.txt_title, item.getTitle());
                holder.setText(R.id.txt_comment, item.getReason());
                if (0 == item.getTsState()) {
                    // 未处理
                    holder.setText(R.id.item_complaint_status, "未处理");
                    holder.setBackgroundRes(R.id.item_complaint_status, R.drawable
                            .shape_orange_rectangle_2);
                } else {
                    holder.setText(R.id.item_complaint_status, "已处理");
                    holder.setBackgroundRes(R.id.item_complaint_status, R.drawable
                            .shape_main_rectangle_2);
                }
                if (ObjectUtils.isNotEmpty(item.getClResult())) {
                    holder.setVisible(R.id.ll_answer, true);
                    holder.setVisible(R.id.txt_time, false);
                    SpannableString ss = new SpannableString("处理结果：" + item.getClResult());
                    ss.setSpan(new TextAppearanceSpan(ComplaintListActivity.this, R.style
                                    .style_main_bold), 0, 5,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    holder.setText(R.id.txt_answer, ss);
                    holder.setText(R.id.txt_answer_time, item.getClTime());
                } else {
                    holder.setVisible(R.id.txt_time, true);
                    holder.setVisible(R.id.ll_answer, false);
                    holder.setText(R.id.txt_time, "投诉时间：" + item.getCreateTime());
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ARouter.getInstance().build(Constant.ACTIVITY_COMPLAINT_DETAILS)
                                .withString("id", item.getId()).navigation();
                    }
                });
            }
        };
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageIndex++;
                request(false);
            }
        }, recyclerView);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }


    /**
     * 请求数据
     *
     * @param isFresh
     */
    private void request(final boolean isFresh) {
        RetrofitHelper.getApiService()
                .getComplainList(PAGE_COUNT + "", pageIndex + "", "", "", "")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<ComplaintListEntity>() {
                    @Override
                    public void onSuccess(BaseResponse<ComplaintListEntity> response) {
                        if (ObjectUtils.isNotEmpty(response)) {
                            if (response.getCode() == 0
                                    && ObjectUtils.isNotEmpty(response.getDatas())) {
                                llNoData.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                if (isFresh) {
                                    complaintList.clear();
                                }
                                complaintList.addAll(response.getDatas());
                                if (1 != pageIndex) {
                                    adapter.loadMoreComplete();
                                }
                                if (ObjectUtils.isNotEmpty(response.getPage())
                                        && response.getPage().getCurrPage() < response.getPage()
                                        .getTotalPage()) {
                                    adapter.setEnableLoadMore(true);
                                } else {
                                    adapter.loadMoreEnd();
                                }
                                adapter.notifyDataSetChanged();
                            } else {
                                llNoData.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }
                        } else {
                            llNoData.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
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
     * 说明和搜索的按钮监听事件
     *
     * @param view
     */
    @OnClick({R.id.iv_complaint_description, R.id.iv_complaint_inquire, R.id.btn_complaint})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_complaint_description:
                // 说明
                ARouter.getInstance().build(Constant.ACTIVITY_COMPLAINT_DESC).navigation();
                break;
            case R.id.iv_complaint_inquire:
                // 搜索
                ARouter.getInstance().build(Constant.ACTIVITY_COMPLAINT_QUERY).navigation();
                break;
            case R.id.btn_complaint:
                // 我要投诉
                ARouter.getInstance().build(Constant.ACTIVITY_COMPLAINT).navigation();
                break;
            default:
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        pageIndex = 1;
        refreshLayout.autoRefresh();
    }

}
