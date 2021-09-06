package com.daqsoft.commonnanning.ui.main;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.request.RequestOptions;
import com.daqsoft.android.ProjectConfig;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.ComUtils;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.ParamsCommon;
import com.daqsoft.commonnanning.http.CommonRequest;
import com.daqsoft.commonnanning.ui.entity.AdvertEntity;
import com.daqsoft.commonnanning.ui.entity.GuideBean;
import com.daqsoft.commonnanning.ui.entity.IndexActivity;
import com.daqsoft.commonnanning.ui.entity.IndexBanner;
import com.daqsoft.commonnanning.ui.entity.IndexMenu;
import com.daqsoft.commonnanning.ui.entity.NewsListEntity;
import com.daqsoft.commonnanning.ui.holder.NetWorkImageHolderView;
import com.daqsoft.commonnanning.ui.holder.NetWorkIndexBannerImageHolderView;
import com.daqsoft.commonnanning.ui.main.contract.IndexContact;
import com.daqsoft.commonnanning.ui.main.presenter.IndexPresenter;
import com.daqsoft.commonnanning.utils.GlideUtils;
import com.daqsoft.commonnanning.utils.Utils;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.glide.GlideApp;
import com.example.tomasyb.baselib.base.mvp.BaseFragment;
import com.example.tomasyb.baselib.refresh.SmartRefreshLayout;
import com.example.tomasyb.baselib.refresh.api.RefreshHeader;
import com.example.tomasyb.baselib.refresh.api.RefreshLayout;
import com.example.tomasyb.baselib.refresh.listener.OnRefreshListener;
import com.example.tomasyb.baselib.refresh.listener.SimpleMultiPurposeListener;
import com.example.tomasyb.baselib.rvadapter.CommonAdapter;
import com.example.tomasyb.baselib.rvadapter.base.ViewHolder;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.widget.VerticalScrollTextView;
import com.example.tomasyb.baselib.widget.img.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.agora.yview.banner.ConvenientBanner;
import io.agora.yview.banner.holder.CBViewHolderCreator;
import io.agora.yview.banner.listener.OnItemClickListener;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * 首页fragment
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-6-4.15:50
 * @since JDK 1.8
 */

public class MainFragment extends BaseFragment<IndexContact.presenter> implements IndexContact
        .view, OnItemClickListener {
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.index_banner)
    ConvenientBanner mBanner;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.index_rv)
    RecyclerView mRv;
    @BindView(R.id.index_vertextview)
    VerticalScrollTextView mVertextView;
    @BindView(R.id.img_map)
    ImageView mImgMap;
    @BindView(R.id.tv_map_tag)
    TextView mTvMapTitle;
    @BindView(R.id.tv_scenic)
    TextView tvScenic;
    @BindView(R.id.tv_food)
    TextView tvFood;
    @BindView(R.id.tv_buy)
    TextView tvBuy;
    @BindView(R.id.index_progressbar)
    ProgressBar mProbar;
    @BindView(R.id.index_rv_jieq)
    RecyclerView mRvJieQ;
    @BindView(R.id.index_play_title)
    TextView indexPlayTitle;
    @BindView(R.id.index_play_list)
    RecyclerView mRvPlay;
    @BindView(R.id.tv_map_in)
    TextView mTvMapIn;
    @BindView(R.id.ll_guide)
    LinearLayout ll_guide;
    @BindView(R.id.ll_play)
    LinearLayout ll_play;
    @BindView(R.id.ll_activity)
    LinearLayout ll_activity;
    @BindView(R.id.rl_location)
    RelativeLayout mLocation;
    /**
     * 导游导览的ID
     */
    private int GuideId = -11;

    /**
     * 菜单的适配器
     */
    private CommonAdapter<IndexMenu> mMenuAdapter;
    /**
     * 景区的适配器
     */
    private BaseQuickAdapter<AdvertEntity, BaseViewHolder> playAdapter;
    /**
     * 活动的适配器
     */
    private BaseQuickAdapter<IndexActivity, BaseViewHolder> mActicityAdapter;

    /**
     * banner是否可以滚动
     */
    private boolean isCanRun = false;
    private List<Fragment> fragments;

    @Override
    protected int getLayoutId() {
        return R.layout.fg_main;
    }

    @Override
    public IndexContact.presenter initPresenter() {
        return new IndexPresenter(this);
    }


    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
        setBannerNodata();
        initRefresh();
        initScenicFragment();
        switchFragment(0);
        initActivityAdapter();
        initPlayAdapter();
        mPresenter.setMenuData();
        mRefreshLayout.autoRefresh();
    }

    private void initScenicFragment() {
        fragments = new ArrayList<>();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                .beginTransaction();
        for (int i = 0; i < 3; i++) {
            fragments.add(MainHotFragment.getInstance(i));
        }
        for (int i = 0; i < fragments.size(); i++) {
            transaction.add(R.id.fl_container, fragments.get(i), "tag" + i);
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 选择碎片
     */
    private void switchFragment(int index) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                .beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            if (i == index) {
                transaction.show(fragment);
                continue;
            }
            transaction.hide(fragment);
        }
        transaction.commit();
    }

    /**
     * 刷新获取数据
     */
    private void getData() {
        mPresenter.getBannerData();
        mPresenter.getNowData();
        mPresenter.getMapGuideList();
        //mPresenter.getPlayList();
        mPresenter.getActivityList("");
        for (int i = 0; i < fragments.size(); i++) {
            ((MainHotFragment) fragments.get(i)).getData();
        }
    }

    /**
     * 初始化好玩数据
     */
    private void initPlayAdapter() {
        indexPlayTitle.setText("玩转" + ProjectConfig.CITY_NAME);
        mRvPlay.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        playAdapter = new BaseQuickAdapter<AdvertEntity, BaseViewHolder>(R.layout
                .item_index_play, null) {

            @Override
            protected void convert(BaseViewHolder helper, final AdvertEntity item) {
                if (ObjectUtils.isNotEmpty(item.getPics()) && item.getPics().size() > 0) {
                    GlideApp.with(getActivity()).load(item.getPics().get(0)).apply(RequestOptions
                            .bitmapTransform(new RoundedCornersTransformation(5, 0))).error(R
                            .mipmap.comimg_fail_240_180).placeholder(R.mipmap
                            .comimg_fail_240_180).into((ImageView) helper.getView(R.id
                            .item_index_play_img));
                    if (ObjectUtils.isNotEmpty(item.getUrl())) {
                        helper.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                CommonRequest.goToShoppingHtml(getActivity(), item.getUrl(), item
                                        .getTitle());
                            }
                        });
                    }
                }
            }
        };
        mRvPlay.setAdapter(playAdapter);
    }


    /**
     * 初始化节庆活动的适配器
     */
    private void initActivityAdapter() {
        // 节庆活动
        mRvJieQ.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mActicityAdapter = new BaseQuickAdapter<IndexActivity, BaseViewHolder>(R.layout
                .item_index_activity, null) {
            @Override
            protected void convert(BaseViewHolder holder, final IndexActivity bean) {
                GlideUtils.loadImage(mContext, (RoundImageView) holder.getView(R.id
                        .index_img_jie), bean.getCoverOneToOne(), R.mipmap.comimg_fail_240_180);
                holder.setText(R.id.tv_title, bean.getTitle());
                holder.setText(R.id.tv_content, bean.getTheme());
                holder.setText(R.id.tv_time, bean.getCreateTime());
                holder.setText(R.id.tv_star, bean.getViewCount() + "");
                holder.setText(R.id.tv_star, bean.getViewCount() + "");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ARouter.getInstance().build(Constant.ACTIVITY_DETAILS_WEB).withString
                                ("id", bean.getId() + "").withString("code", ParamsCommon
                                .ACTIVITY_CHANELCODE).withString("title", "活动详情").navigation();
                    }
                });
            }
        };
        View view2 = getLayoutInflater().inflate(R.layout.include_adapter_footer_more,
                (ViewGroup) mRvJieQ.getParent(), false);
        TextView foot2 = (TextView) view2.findViewById(R.id.tv_footer_more);
        foot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build(Constant.ACTIVITY_FESTIVAL)
                        .withString("title","节庆活动")
                        .withString("code",ParamsCommon.ACTIVITY_CHANELCODE).navigation();
            }
        });
        mActicityAdapter.addFooterView(view2);
        mRvJieQ.setAdapter(mActicityAdapter);
    }


    @Override
    protected void initData() {

    }


    @Override
    public void showLoadingDialog(String msg) {

    }

    @Override
    public void dismissLoadingDialog() {

    }

    @Override
    public void initRefresh() {
        mRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(2000);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(2000);
            }

            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent,
                                       int offset, int headerHeight, int maxDragHeight) {
                toolbar.setAlpha(1 - Math.min(percent, 1));
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData();
            }
        });

    }


    @Override
    public void initBanner(List<IndexBanner> list) {
        mBanner.setPages(new CBViewHolderCreator() {
            @Override
            public NetWorkIndexBannerImageHolderView createHolder(View itemView) {
                return new NetWorkIndexBannerImageHolderView(itemView, getActivity());
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_banner_img;
            }
        }, list)
                // 设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.bga_banner_point_disabled, R.drawable
                        .bga_banner_point_enabled,}).setOnItemClickListener(this)
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        //      .setOnPageChangeListener(this)
        // 设置翻页监听
        //        convenientBanner.setManualPageable(false);
        //  设置不能手动影响
        if (list.size() != 0 && list.size() == 1) {
            mBanner.setPointViewVisible(false);
            mBanner.setCanLoop(false);
            isCanRun = false;
            mBanner.stopTurning();
        } else {
            isCanRun = true;
        }
        mBanner.notifyDataSetChanged();
    }

    /**
     * 初始化banner
     */
    private void setBannerNodata() {
        List<IndexBanner> list = new ArrayList<>();
        IndexBanner beans = new IndexBanner();
        beans.setId("");
        beans.setImg("");
        list.add(beans);
        mBanner.setPages(new CBViewHolderCreator() {
            @Override
            public NetWorkImageHolderView createHolder(View itemView) {
                return new NetWorkImageHolderView(itemView, getActivity());
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_banner_img;
            }
        }, list)
                // 设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.bga_banner_point_disabled, R.drawable
                        .bga_banner_point_enabled,}).setOnItemClickListener(this)
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        //      .setOnPageChangeListener(this)
        // 设置翻页监听
        //        convenientBanner.setManualPageable(false);
        //  设置不能手动影响
        if (list.size() != 0 && list.size() == 1) {
            isCanRun = false;
            mBanner.stopTurning();
        } else {
            isCanRun = true;
        }
        mBanner.notifyDataSetChanged();
    }

    @Override
    public void initRv(List<IndexMenu> mDatas) {
        mRv.setLayoutManager(new GridLayoutManager(getActivity(), 6) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mMenuAdapter = new CommonAdapter<IndexMenu>(getActivity(), R.layout.item_index_menu,
                mDatas) {
            @Override
            protected void convert(ViewHolder holder, IndexMenu indexMenu, final int position) {
                holder.setText(R.id.tv_index_menu, indexMenu.getName());
                holder.setImageResource(R.id.img_index_menu, indexMenu.getmImg());
                holder.setOnClickListener(R.id.ll_index_top, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (position) {
                            case 0:
                                if (ProjectConfig.SITECODE.equals("cehmhyhwgw")) {
                                    ARouter.getInstance().build(Constant
                                            .SCENIC_OVERVIEW_ACTIVITY).navigation();
                                }
                                ARouter.getInstance().build(Constant.ACTIVITY_SCENIC).navigation();
                                break;
                            case 1:
                                ARouter.getInstance().build(Constant.ACTIVITY_HOTEL).withString
                                        ("grade", "").navigation();
                                break;
                            case 2:
                                ARouter.getInstance().build(Constant.ACTIVITY_FOOD).navigation();
                                break;
                            case 3:
                                ARouter.getInstance().build(Constant.ACTIVITY_TRAVEL).navigation();
                                break;
                            case 4:
                                if (!Utils.isFastClick()) {
                                    CommonRequest.checkedToken(new CommonRequest.OnStateCallBack() {
                                        @Override
                                        public void result(int value) {
                                            // 0未失效
                                            if (0 == value) {
                                                // 我的行程
                                                ARouter.getInstance().build(Constant
                                                        .ACTIVITY_ROUTE).navigation();
                                            } else {
                                                ARouter.getInstance().build(Constant
                                                        .ACTIVITY_LOGIN).navigation();
                                            }
                                        }
                                    });
                                }
                                break;
                            case 5:
                                if (ProjectConfig.CITY_NAME.equals("察尔汗")) {
                                    ARouter.getInstance().build(Constant.ACTIVITY_ONEPOLICE)
                                            .navigation();
                                } else {
                                    ARouter.getInstance().build(Constant.ACTIVITY_PANORAMALIST)
                                            .navigation();
                                }
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
        };
        mRv.setAdapter(mMenuAdapter);
    }

    @Override
    public void setNowData(final List<String> mDatas, final List<NewsListEntity> noticeList) {
        mVertextView.setDataSource(mDatas);
        mVertextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build(Constant.ACTIVITY_DETAILS_WEB).withString("id",
                        noticeList.get(mVertextView.getCurentItem()).getId() + "").withString
                        ("code", ParamsCommon.SERVICE_LYZX).withString("title", "资讯详情")
                        .navigation();
            }
        });
    }

    @Override
    public void setNoDataNotify() {
        List<String> mdatas = new ArrayList<>();
        mdatas.add("暂无公告");
        mVertextView.setDataSource(mdatas);
    }

    /**
     * 导游导览
     *
     * @param list
     */
    @Override
    public void setMapData(List<GuideBean> list) {
        try {
            if (ObjectUtils.isNotEmpty(list)) {
                if (list.size() > 0) {
                    ll_guide.setVisibility(View.VISIBLE);
                    mTvMapIn.setBackground(getResources().getDrawable(R.drawable.shap_bg_index_in));
                    GuideId = list.get(0).getId();
                    GlideUtils.loadImage(getActivity(), mImgMap, list.get(0).getCoverTwoToOne(),
                            R.mipmap.common_ba_banner);
                    mTvMapTitle.setText(list.get(0).getName());
                } else {
                    ll_guide.setVisibility(View.GONE);
                    mTvMapIn.setBackground(getResources().getDrawable(R.drawable
                            .shap_bg_index_in_no));
                }
            } else {
                ll_guide.setVisibility(View.GONE);
                mTvMapIn.setBackground(getResources().getDrawable(R.drawable.shap_bg_index_in_no));
            }
        } catch (Exception e) {
            ll_guide.setVisibility(View.GONE);
            mTvMapIn.setBackground(getResources().getDrawable(R.drawable.shap_bg_index_in_no));
            e.printStackTrace();
        }
    }

    @Override
    public void setPlayList(List<AdvertEntity> list) {
        try {
            ll_play.setVisibility(View.VISIBLE);
            if (ObjectUtils.isNotEmpty(list) && list.size() > 0) {
                playAdapter.setNewData(list);
            } else {
                ll_play.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            ll_play.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }


    @Override
    public void showProgress() {
        mProbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProbar.setVisibility(View.GONE);
    }

    @Override
    public void refreshActivity(List<IndexActivity> list) {
        try {
            if (ObjectUtils.isNotEmpty(list) && list.size() > 0) {
                ll_activity.setVisibility(View.VISIBLE);
                mActicityAdapter.setNewData(list);
            } else {
                ll_activity.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            ll_activity.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(int position) {
        // 这里是轮播图的点击事件
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isCanRun) {
            // 开始自动翻页
            mBanner.startTurning();
        }
        if (mVertextView != null) {
            mVertextView.startPlay();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isCanRun) {
            // 停止翻页
            mBanner.stopTurning();
        }
        if (mVertextView != null) {
            mVertextView.stopPlay();
        }
    }


    @OnClick({R.id.tv_map_in, R.id.tv_scenic, R.id.tv_food, R.id.tv_buy, R.id.rl_location, R.id
            .index_et_search, R.id.index_notice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.index_et_search:
                // 搜索
                ARouter.getInstance().build(Constant.ACTIVITY_GLOBALSEARCH).navigation();
                break;
            case R.id.tv_map_in:
                // 进入导游导览列表
                if (GuideId != -11) {
                    ComUtils.goToGuide(GuideId, getActivity(), true);
                }
                break;
            case R.id.tv_scenic:
                tvScenic.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tvFood.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvBuy.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvScenic.setTextSize(15);
                tvFood.setTextSize(13);
                tvBuy.setTextSize(13);
                switchFragment(0);
                break;
            case R.id.tv_food:
                tvScenic.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvFood.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tvBuy.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvScenic.setTextSize(13);
                tvFood.setTextSize(15);
                tvBuy.setTextSize(13);
                switchFragment(1);
                break;
            case R.id.tv_buy:
                tvScenic.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvFood.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvBuy.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tvScenic.setTextSize(13);
                tvFood.setTextSize(13);
                tvBuy.setTextSize(15);
                switchFragment(2);
                break;
            case R.id.rl_location:
                ARouter.getInstance().build(Constant.ACTIVITY_FOUNDNEAR).navigation();
                break;
            case R.id.index_notice:
                // 旅游资讯
                ARouter.getInstance().build(Constant.ACTIVITY_NEWS_LIST).navigation();
                break;
            default:
                break;
        }
    }
}
