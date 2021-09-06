package com.daqsoft.commonnanning.ui.mine.online;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.MessageEntity;
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
import io.reactivex.schedulers.Schedulers;

/**
 * 我的留言列表页面
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018.05.07
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_ONLINE_MESSAGE_LIST)
public class OnLineMessageListActivity extends BaseActivity {
    @BindView(R.id.online_message_list_title)
    HeadView onlineMessageListTitle;
    @BindView(R.id.pull_message_list)
    RecyclerView pullMessageList;
    @BindView(R.id.no_date_img)
    ImageView noDateImg;
    @BindView(R.id.no_data_content)
    TextView noDataContent;
    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;
    @BindView(R.id.animator_message_list)
    ViewAnimator animatorMessageList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.message_add)
    TextView messageAdd;
    @BindView(R.id.activity_on_line_message_list)
    LinearLayout activityOnLineMessageList;
    /**
     * 页码
     */
    private int page = 1;
    /**
     * 每页条数
     */
    private int limitPage = 10;
    /**
     * 页面传递类型
     * 0：我的
     * 1：服务消息留言
     */
    private int mPageType = 0;
    /**
     * 留言列表适配器
     */
    BaseQuickAdapter<MessageEntity, BaseViewHolder> adapter;
    /**
     * 列表数据集合
     */
    private List<MessageEntity> messageList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_on_line_message_list;
    }

    /**
     * 初始化
     */
    @Override
    public void initView() {
        mPageType = getIntent().getIntExtra("type", 0);
        onlineMessageListTitle.setTitle("留言列表");
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getData();
            }
        });
        setMessageAdapter();
        getData();
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    /**
     * 列表适配器
     */
    public void setMessageAdapter() {
        pullMessageList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BaseQuickAdapter<MessageEntity, BaseViewHolder>(R.layout
                .item_on_line_message, messageList) {
            @Override
            protected void convert(BaseViewHolder holder, final MessageEntity item) {
                holder.setText(R.id.item_msg_title, item.getTitle());
                holder.setText(R.id.item_msg_time, item.getCreateTime());
                holder.setText(R.id.item_msg_content, item.getContent());
                // 跳转详情页
                holder.setOnClickListener(R.id.item_msg_detail, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ARouter.getInstance().build(Constant.ACTIVITY_ONLINE_MESSAGE_DETAILS)
                                .withString("id", item.getId())
                                .navigation();
                    }
                });
            }
        };
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                getData();
            }
        }, pullMessageList);
        pullMessageList.setAdapter(adapter);
    }

    /**
     * 获取数据
     */
    public void getData() {
        RetrofitHelper.getApiService()
                .getMsgList("", "", page + "", limitPage + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<MessageEntity>() {
                    @Override
                    public void onSuccess(BaseResponse<MessageEntity> response) {
                        if (ObjectUtils.isNotEmpty(response)) {
                            if (response.getCode() == 0
                                    && ObjectUtils.isNotEmpty(response.getDatas())) {
                                animatorMessageList.setDisplayedChild(0);
                                if (1 != page) {
                                    adapter.loadMoreComplete();
                                } else {
                                    messageList.clear();
                                }
                                messageList.addAll(response.getDatas());
                                if (ObjectUtils.isNotEmpty(response.getPage())
                                        && response.getPage().getCurrPage() < response.getPage()
                                        .getTotalPage()) {
                                    adapter.setEnableLoadMore(true);
                                } else {
                                    adapter.loadMoreEnd();
                                }
                                adapter.notifyDataSetChanged();
                            } else {
                                animatorMessageList.setDisplayedChild(1);
                            }
                        } else {
                            animatorMessageList.setDisplayedChild(1);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        refreshLayout.finishRefresh();
                    }
                });
    }

    @OnClick({R.id.message_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.message_add:
                ARouter.getInstance().build(Constant.ACTIVITY_ONLINE_MESSAGE)
                        .navigation();
                break;
            default:
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        page = 1;
        refreshLayout.autoRefresh();
        getData();
    }

}
