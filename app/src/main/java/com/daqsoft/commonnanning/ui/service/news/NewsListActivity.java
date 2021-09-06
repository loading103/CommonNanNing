package com.daqsoft.commonnanning.ui.service.news;

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
import com.daqsoft.commonnanning.common.ParamsCommon;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.NewsListEntity;
import com.daqsoft.commonnanning.utils.GlideUtils;
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
import com.example.tomasyb.baselib.widget.img.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 旅游资讯页面
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/3/19.
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_NEWS_LIST)
public class NewsListActivity extends BaseActivity {


    @BindView(R.id.news_list_title)
    HeadView newsListTitle;
    @BindView(R.id.news_list)
    RecyclerView recyclerView;
    @BindView(R.id.no_date_img)
    ImageView noDateImg;
    @BindView(R.id.no_data_content)
    TextView noDataContent;
    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;
    @BindView(R.id.news_list_animator)
    ViewAnimator newsListAnimator;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    /**
     * 旅游资讯列表适配器
     */
    BaseQuickAdapter<NewsListEntity, BaseViewHolder> adapter;

    /**
     * 旅游资讯数据集合
     */
    List<NewsListEntity> newsList = new ArrayList<>();
    /**
     * 每页条数
     */
    final int PAGE_SIZE = 10;
    /**
     * 页码
     */
    int pageSize = 1;
    /**
     * 按照发布时间排序
     */
    final String PUBLISH_TIME = "PUBLISH_TIME";

    /**
     * 降序
     */
    final String DESC = "DESC";

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_list;
    }

    @Override
    public void initView() {
        newsListTitle.setTitle("旅游资讯");
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageSize = 1;
                getData();
            }
        });
        initAdapter();
    }

    /**
     * 适配器初始化
     */
    public void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BaseQuickAdapter<NewsListEntity, BaseViewHolder>(
                R.layout.item_news_list, newsList) {
            @Override
            protected void convert(BaseViewHolder helper, final NewsListEntity item) {
                helper.setText(R.id.item_news_name, item.getTitle());
                helper.setText(R.id.item_news_summary, item.getSummary());
                helper.setText(R.id.item_news_time, item.getPublishtime());
                helper.setText(R.id.item_news_view, item.getViewCount() + "");
                GlideUtils.loadImage(mContext,(RoundImageView) helper.getView(R.id.item_news_cover),item.getCoverTwoToOne(),R.mipmap.common_img_fail_h158);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ARouter.getInstance().build(Constant.ACTIVITY_DETAILS_WEB)
                                .withString("id", item.getId())
                                .withString("code", ParamsCommon.SERVICE_LYZX)
                                .withString("title", "资讯详情")
                                .navigation();
                    }
                });
            }
        };
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageSize++;
                getData();
            }
        }, recyclerView);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 数据获取
     */
    public void getData() {
        RetrofitHelper.getApiService()
                .getNewsList(ParamsCommon.SERVICE_LYZX,
                        PUBLISH_TIME, DESC, PAGE_SIZE + "", pageSize + "")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<NewsListEntity>() {
                    @Override
                    public void onSuccess(BaseResponse<NewsListEntity> response) {
                        if (ObjectUtils.isNotEmpty(response)) {
                            if (response.getCode() == 0
                                    && ObjectUtils.isNotEmpty(response.getDatas())) {
                                newsListAnimator.setDisplayedChild(0);
                                if (1 != pageSize) {
                                    adapter.loadMoreComplete();
                                } else {
                                    newsList.clear();
                                }
                                newsList.addAll(response.getDatas());
                                if (ObjectUtils.isNotEmpty(response.getPage())
                                        && response.getPage().getCurrPage() < response.getPage()
                                        .getTotalPage()) {
                                    adapter.setEnableLoadMore(true);
                                } else {
                                    adapter.loadMoreEnd();
                                }
                                adapter.notifyDataSetChanged();
                            } else {
                                newsListAnimator.setDisplayedChild(1);
                            }
                        } else {
                            newsListAnimator.setDisplayedChild(1);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        refreshLayout.finishRefresh();
                    }
                });
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        pageSize = 1;
        refreshLayout.autoRefresh();
        getData();
    }
}
