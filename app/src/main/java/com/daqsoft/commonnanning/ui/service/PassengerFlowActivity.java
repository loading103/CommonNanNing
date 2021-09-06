package com.daqsoft.commonnanning.ui.service;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.URLConstant;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.service.bean.FlowListBean;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.BaseActivity;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.refresh.SmartRefreshLayout;
import com.example.tomasyb.baselib.refresh.api.RefreshLayout;
import com.example.tomasyb.baselib.refresh.listener.OnRefreshListener;
import com.example.tomasyb.baselib.util.KeyboardUtils;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.Utils;
import com.example.tomasyb.baselib.widget.HeadView;

import java.util.List;

import butterknife.BindView;
import io.agora.yview.view.CenterDrawableEdittext;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 景区客流量页面
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/12/14.
 * @since JDK 1.8
 */
public class PassengerFlowActivity extends BaseActivity {
    @BindView(R.id.headView)
    HeadView headView;
    @BindView(R.id.edt_search)
    CenterDrawableEdittext edtSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.no_date_img)
    ImageView noDateImg;
    @BindView(R.id.no_data_content)
    TextView noDataContent;
    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;
    BaseQuickAdapter adapter;
    /**
     * 每页条数
     */
    @BindView(R.id.viewAnimator)
    ViewAnimator viewAnimator;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    /**
     * 当前页码
     */
    private int pageIndex = 1;
    /**
     * 关键字搜索
     */
    private String name = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_passenger_flow;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        headView.setTitle("景区客流量");
        KeyboardUtils.hideSoftInput(this);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                name = edtSearch.getText().toString().trim();
                getData(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        headView.setBackListener(new HeadView.OnBackListener() {
            @Override
            public void onBack(View v) {
                finish();
            }
        });
        initAdapter();
        refreshLayout.autoRefresh();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter =
                new BaseQuickAdapter<FlowListBean.ListBean, BaseViewHolder>(R.layout.item_passenger_flow, null) {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            protected void convert(BaseViewHolder helper, final FlowListBean.ListBean bean) {
                helper.setText(R.id.tv_name, bean.getName());
                String maxPeople = "0人";
                if (bean.getMaxLoad() < 10000) {
                    maxPeople = bean.getMaxLoad() + "人";
                } else {
                    maxPeople = ((double) bean.getMaxLoad() / 10000) + "万人";
                }
                helper.setText(R.id.tv_time, "景区最大承载量" + maxPeople);
                ProgressBar progressBar = helper.getView(R.id.bar_count);
                if (bean.getRealTimePeopleTotal() <= 0) {
                    helper.setVisible(R.id.ll_people, false);
                    progressBar.setVisibility(View.GONE);
                } else {
                    helper.setVisible(R.id.ll_people, true);
                    progressBar.setVisibility(View.VISIBLE);
                }
                progressBar.setMax(bean.getMaxLoad());
                progressBar.setProgress(bean.getRealTimePeopleTotal());
                if (!ObjectUtils.isEmpty(bean.getLevel()) && !bean.equals("非A")) {
                    helper.setText(R.id.tv_level, bean.getLevel());
                    helper.setVisible(R.id.tv_level, true);
                } else {
                    helper.setVisible(R.id.tv_level, false);
                }
                String comfortName = bean.getComfortLevel() == 5 ? "非常拥挤" :
                        bean.getComfortLevel() == 4 ? "拥挤" : bean.getComfortLevel() == 3 ? "一般" :
                                bean.getComfortLevel() == 2 ? "舒适" : bean.getComfortLevel() == 1
                                        ? "非常舒适" : "";
                helper.setText(R.id.tv_grade, bean.getComfortLevel() + "");
                helper.setText(R.id.tv_people, bean.getRealTimePeopleTotal() + "");
                helper.setText(R.id.tv_comfort, comfortName);
                if (bean.getComfortLevel() == 5) {
                    progressBar.setProgressDrawable(PassengerFlowActivity.this.getDrawable(R.drawable.passenger_flow_style_red));
                    helper.getView(R.id.tv_level).setBackground(PassengerFlowActivity.this.getDrawable(R.drawable.shape_flow_red_circle));
                    helper.setTextColor(R.id.tv_grade,
                            PassengerFlowActivity.this.getResources().getColor(R.color.flow_red));
                    helper.setTextColor(R.id.tv_people,
                            PassengerFlowActivity.this.getResources().getColor(R.color.flow_red));
                    helper.setTextColor(R.id.tv_comfort,
                            PassengerFlowActivity.this.getResources().getColor(R.color.flow_red));
                } else if (bean.getComfortLevel() == 4) {
                    progressBar.setProgressDrawable(PassengerFlowActivity.this.getDrawable(R.drawable.passenger_flow_style_orange));
                    helper.getView(R.id.tv_level).setBackground(PassengerFlowActivity.this.getDrawable(R.drawable.shape_orange_circle));
                    helper.setTextColor(R.id.tv_grade,
                            PassengerFlowActivity.this.getResources().getColor(R.color.flow_orange));
                    helper.setTextColor(R.id.tv_people,
                            PassengerFlowActivity.this.getResources().getColor(R.color.flow_orange));
                    helper.setTextColor(R.id.tv_comfort,
                            PassengerFlowActivity.this.getResources().getColor(R.color.flow_orange));
                } else if (bean.getComfortLevel() == 3) {
                    progressBar.setProgressDrawable(PassengerFlowActivity.this.getDrawable(R.drawable.passenger_flow_style_yellow));
                    helper.getView(R.id.tv_level).setBackground(PassengerFlowActivity.this.getDrawable(R.drawable.shape_yellow_circle));
                    helper.setTextColor(R.id.tv_grade,
                            PassengerFlowActivity.this.getResources().getColor(R.color.flow_yellow));
                    helper.setTextColor(R.id.tv_people,
                            PassengerFlowActivity.this.getResources().getColor(R.color.flow_yellow));
                    helper.setTextColor(R.id.tv_comfort,
                            PassengerFlowActivity.this.getResources().getColor(R.color.flow_yellow));
                } else if (bean.getComfortLevel() == 2) {
                    progressBar.setProgressDrawable(PassengerFlowActivity.this.getDrawable(R.drawable.passenger_flow_style_blue));
                    helper.getView(R.id.tv_level).setBackground(PassengerFlowActivity.this.getDrawable(R.drawable.shape_blue_circle));
                    helper.setTextColor(R.id.tv_grade,
                            PassengerFlowActivity.this.getResources().getColor(R.color.flow_green));
                    helper.setTextColor(R.id.tv_people,
                            PassengerFlowActivity.this.getResources().getColor(R.color.flow_green));
                    helper.setTextColor(R.id.tv_comfort,
                            PassengerFlowActivity.this.getResources().getColor(R.color.flow_green));
                } else if (bean.getComfortLevel() == 1) {
                    progressBar.setProgressDrawable(PassengerFlowActivity.this.getDrawable(R.drawable.passenger_flow_style_green));
                    helper.getView(R.id.tv_level).setBackground(PassengerFlowActivity.this.getDrawable(R.drawable.shape_green_circle));
                    helper.setTextColor(R.id.tv_grade,
                            PassengerFlowActivity.this.getResources().getColor(R.color.flow_blue));
                    helper.setTextColor(R.id.tv_people,
                            PassengerFlowActivity.this.getResources().getColor(R.color.flow_blue));
                    helper.setTextColor(R.id.tv_comfort,
                            PassengerFlowActivity.this.getResources().getColor(R.color.flow_blue));
                }
            }
        };
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getData(false);
            }
        });

        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 获取数据
     */
    @SuppressLint("CheckResult")
    private void getData(final boolean isRefresh) {
        if (isRefresh) {
            pageIndex = 1;
            // 这里的作用是防止下拉刷新的时候还可以上拉加载
            adapter.setEnableLoadMore(false);
        }
        RetrofitHelper.getApiService().getFlowList(pageIndex + "", URLConstant.LIMITPAGE + "",
                name).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).subscribe(new Consumer<BaseResponse<FlowListBean>>() {
            @Override
            public void accept(BaseResponse<FlowListBean> baseResponse) throws Exception {
                setData(isRefresh, baseResponse.getData().getList());
                if (isRefresh) {
                    adapter.setEnableLoadMore(true);
                    if (ObjectUtils.isNotEmpty(refreshLayout)) {
                        refreshLayout.finishRefresh();
                    }
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (isRefresh) {
                    viewAnimator.setDisplayedChild(1);
                    adapter.setEnableLoadMore(true);
                    if (ObjectUtils.isNotEmpty(refreshLayout)) {
                        refreshLayout.finishRefresh();
                    }
                } else {
                    adapter.loadMoreFail();
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
        pageIndex++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            if (size > 0) {
                viewAnimator.setDisplayedChild(0);
                adapter.setNewData(data);
            } else {
                viewAnimator.setDisplayedChild(1);
            }
        } else {
            if (size > 0) {
                adapter.addData(data);
            }
        }
        if (size < URLConstant.LIMITPAGE) {
            // 第一页如果不够一页就不显示没有更多数据布局
            adapter.loadMoreEnd(isRefresh);
        } else {
            adapter.loadMoreComplete();
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
            name = edtSearch.getText().toString().trim();
            getData(true);
            return true;
        }
        return super.dispatchKeyEvent(event);

    }
}
