package com.daqsoft.commonnanning.ui.service.complaint;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;

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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 投诉查询结果
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-3-22
 * @since JDK 1.8
 */

public class ComplaintQueryResultActivity extends BaseActivity {

    @BindView(R.id.headView)
    HeadView headView;
    @BindView(R.id.txt_type)
    TextView txtType;
    @BindView(R.id.txt_phone)
    TextView txtPhone;
    @BindView(R.id.txt_count)
    TextView txtCount;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.no_date_img)
    ImageView noDateImg;
    @BindView(R.id.no_data_content)
    TextView noDataContent;
    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;
    @BindView(R.id.animator)
    ViewAnimator animator;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;
    /**
     * 输入内容
     */
    private String content;
    /**
     * 是手机还是编码
     */
    private String code;
    /**
     * 每页条数
     */
    private final int PAGE_COUNT = 10;
    /**
     * 页码
     */
    private int pageIndex = 1;
    /**
     * 适配器
     */
    BaseQuickAdapter adapter;
    /**
     * 投诉列表数据集合
     */
    List<ComplaintListEntity> complaintList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_complaintqueryresult;
    }

    @Override
    public void initView() {
        content = getIntent().getStringExtra("content");
        code = getIntent().getStringExtra("type");
        txtType.setText(!TextUtils.isEmpty(code) && "phone".equals(code) ? "查询手机号:" : "查询编码:");
        txtPhone.setText(content);
        headView.setTitle("查询结果");
        initAdapter();
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                request(true);
            }
        });
        refreshlayout.autoRefresh();
        request(true);
    }

    /**
     * 初始化适配器
     */
    public void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BaseQuickAdapter<ComplaintListEntity, BaseViewHolder>(
                R.layout.adapter_complaintqueryresult, complaintList) {
            @Override
            protected void convert(BaseViewHolder holder, final ComplaintListEntity item) {
                holder.setText(R.id.txt_title, item.getTitle());
                holder.setText(R.id.txt_comment, item.getReason());
                holder.setText(R.id.txt_code, item.getCodes());
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
                holder.setText(R.id.txt_time, "投诉时间：" + item.getCreateTime());
                if (ObjectUtils.isNotEmpty(item.getClResult())) {
                    holder.setVisible(R.id.ll_answer, true);
                    SpannableString ss = new SpannableString("处理结果：" + item.getClResult());
                    ss.setSpan(new TextAppearanceSpan(ComplaintQueryResultActivity.this, R.style
                                    .style_main_bold), 0, 5,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    holder.setText(R.id.txt_answer, ss);
                    holder.setText(R.id.txt_answer_time, item.getClTime());
                } else {
                    holder.setVisible(R.id.ll_answer, false);
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
        String phone = !TextUtils.isEmpty(content) && !TextUtils.isEmpty(this.code) && "phone"
                .equals(this.code) ? content : "";
        String code = !TextUtils.isEmpty(content) && !TextUtils.isEmpty(this.code) && "code"
                .equals(this.code) ? content : "";
        RetrofitHelper.getApiService()
                .getComplainList(PAGE_COUNT + "", pageIndex + "", code, phone, "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<ComplaintListEntity>() {
                    @Override
                    public void onSuccess(BaseResponse<ComplaintListEntity> response) {
                        if (ObjectUtils.isNotEmpty(response)
                                && response.getCode() == 0
                                && ObjectUtils.isNotEmpty(response.getDatas())) {
                            animator.setDisplayedChild(0);
                            if (isFresh) {
                                complaintList.clear();
                            }
                            complaintList.addAll(response.getDatas());
                            txtCount.setText(response.getPage().getTotal() + "");
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
                            animator.setDisplayedChild(1);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        refreshlayout.finishRefresh();
                    }
                });
    }
}
