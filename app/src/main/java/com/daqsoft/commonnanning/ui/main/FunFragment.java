package com.daqsoft.commonnanning.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.android.ProjectConfig;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.ParamsCommon;
import com.daqsoft.commonnanning.ui.entity.IndexActivity;
import com.daqsoft.commonnanning.ui.entity.IndexBanner;
import com.daqsoft.commonnanning.ui.entity.IndexScenic;
import com.daqsoft.commonnanning.ui.entity.MyStrategyListBean;
import com.daqsoft.commonnanning.ui.entity.PanoramaListBean;
import com.daqsoft.commonnanning.ui.holder.NetWorkImageHolderView;
import com.daqsoft.commonnanning.ui.main.contract.FunContact;
import com.daqsoft.commonnanning.ui.main.presenter.FunPresenter;
import com.daqsoft.commonnanning.utils.GlideUtils;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.mvp.BaseFragment;
import com.example.tomasyb.baselib.refresh.SmartRefreshLayout;
import com.example.tomasyb.baselib.refresh.api.RefreshLayout;
import com.example.tomasyb.baselib.refresh.listener.OnRefreshListener;
import com.example.tomasyb.baselib.util.ActivityUtils;
import com.example.tomasyb.baselib.util.LogUtils;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.img.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.agora.yview.banner.ConvenientBanner;
import io.agora.yview.banner.holder.CBViewHolderCreator;
import io.agora.yview.banner.listener.OnItemClickListener;

/**
 * 好玩fragment
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-6-4.15:50
 * @since JDK 1.8
 */

public class FunFragment extends BaseFragment<FunContact.presenter> implements FunContact.view,
        OnItemClickListener {
    @BindView(R.id.fun_head)
    HeadView mHeadView;
    @BindView(R.id.fun_rv)
    RecyclerView mAllRv;
    @BindView(R.id.index_banner)
    ConvenientBanner mBanner;
    @BindView(R.id.fun_rv_line)
    RecyclerView mAllRvLine;
    @BindView(R.id.fun_rv_activity)
    RecyclerView mAllRvActivity;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout mSmartRefresh;
    @BindView(R.id.fun_panorama_ll)
    LinearLayout fun_panorama_ll;
    @BindView(R.id.fun_line_ll)
    LinearLayout fun_line_ll;
    @BindView(R.id.fun_activity_ll)
    LinearLayout fun_activity_ll;
    @BindView(R.id.fun_rv_best_scenic)
    RecyclerView mRv_BeastScenic;
    @BindView(R.id.fun_best_scenic)
    LinearLayout fun_best_scenic;
    @BindView(R.id.fun_rv_gongl)
    RecyclerView mRvGongl;
    @BindView(R.id.fun_ll_gongl)
    LinearLayout fun_ll_gongl;
    /**
     * 全景适配器
     */
    private BaseQuickAdapter<PanoramaListBean, BaseViewHolder> mPanAdapter;
    /**
     * 线路适配器
     */
    private BaseQuickAdapter<MyStrategyListBean, BaseViewHolder> mLineAdapter;
    /**
     * 节庆活动想相关
     */
    private BaseQuickAdapter<IndexActivity, BaseViewHolder> mActicityAdapter;
    /**
     * 游记攻略
     */
    private BaseQuickAdapter<MyStrategyListBean, BaseViewHolder> mGonglAdapter;
    /**
     * 推荐景区
     */
    private BaseQuickAdapter<IndexScenic, BaseViewHolder> mBestScenicAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fg_fun;
    }

    /**
     * banner是否可以滚动
     */
    private boolean isCanRun = false;

    @Override
    public FunContact.presenter initPresenter() {
        return new FunPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isCanRun) {
            // 开始自动翻页
            mBanner.startTurning();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isCanRun) {
            // 停止翻页
            mBanner.stopTurning();
        }
    }

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
        mHeadView.setTitle("好玩"+ ProjectConfig.CITY_NAME);
        mHeadView.setBackHidden(false);
        initScenicAdapter();
        initRefresh();
        if (ProjectConfig.CITY_NAME.equals("察尔汗")){
            initGongl();
            initBestAdapter();
        }else {
            initLineAdapter();
            initActivityAdapter();
        }
        setBannerNodata();
    }

    /**
     * 初始化攻略适配器，查尔汗用
     */
    private void initGongl() {
        mRvGongl.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mGonglAdapter = new BaseQuickAdapter<MyStrategyListBean, BaseViewHolder>(R.layout
                .item_index_activity, null) {
            @Override
            protected void convert(BaseViewHolder holder, final MyStrategyListBean bean) {
                GlideUtils.loadImage(mContext,
                        (RoundImageView) holder.getView(R.id.index_img_jie),bean.getCoverOneToOne(),R.mipmap.comimg_fail_240_180);
                holder.setText(R.id.tv_title, bean.getTitle());
                holder.setText(R.id.tv_content, bean.getDigest());
                holder.setText(R.id.tv_time, bean.getCreateTime());
                holder.setText(R.id.tv_star, bean.getViewCount() + "");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LogUtils.e("id-->" + bean.getId());
                        String id = bean.getId() + "";
                        ARouter.getInstance().build(Constant.ACTIVITY_LINEDETAIL).withString
                                ("mId", id)
                                .navigation();
                    }
                });
            }
        };
        View view2 = getLayoutInflater().inflate(R.layout.include_adapter_footer_more,
                (ViewGroup) mAllRvActivity
                        .getParent(), false);
        TextView foot2 = (TextView) view2.findViewById(R.id.tv_footer_more);
        foot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.startActivity(LineActivity.class);
            }
        });
        mGonglAdapter.addFooterView(view2);
        mRvGongl.setAdapter(mGonglAdapter);
    }

    /**
     * 初始化推荐景区适配器
     */
    private void initBestAdapter() {
        mRv_BeastScenic.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        mBestScenicAdapter = new BaseQuickAdapter<IndexScenic, BaseViewHolder>(R.layout.item_best_scenic,null) {
            @Override
            protected void convert(BaseViewHolder helper, IndexScenic item) {
                helper.setText(R.id.tv_name,item.getName());
                helper.setText(R.id.tv_num,"NO."+(helper.getAdapterPosition()+1));
                helper.setText(R.id.tv_content,item.getSummary().trim());
                GlideUtils.loadImage(mContext,(RoundImageView)helper.getView(R.id.img_round),item.getPictureTwoToOne(),R.mipmap.comimg_fail_240_180);
            }
        };
        mRv_BeastScenic.setAdapter(mBestScenicAdapter);
    }

    /**
     * 刷新数据
     */
    private void initRefresh() {
        mSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData();
            }
        });
        getData();
    }

    /**
     * 刷新数据
     */
    private void getData() {
        mPresenter.getPanoramaList();
        mPresenter.getBannerData();
        mPresenter.getLineData();
        if (ProjectConfig.CITY_NAME.equals("察尔汗")){
            mPresenter.getBestScenic();
        }else {
            mPresenter.getActivityList("");
        }
    }


    /**
     * 初始化活动适配器
     */
    private void initActivityAdapter() {
        // 节庆活动
        mAllRvActivity.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mActicityAdapter = new BaseQuickAdapter<IndexActivity, BaseViewHolder>(R.layout
                .item_index_activity, null) {
            @Override
            protected void convert(BaseViewHolder holder, final IndexActivity bean) {
                GlideUtils.loadImage(mContext,
                        (RoundImageView) holder.getView(R.id.index_img_jie),bean.getCoverOneToOne(),R.mipmap.comimg_fail_240_180);
                holder.setText(R.id.tv_title, bean.getTitle());
                holder.setText(R.id.tv_content, bean.getTheme());
                holder.setText(R.id.tv_time, bean.getCreateTime());
                holder.setText(R.id.tv_star, bean.getViewCount() + "");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ARouter.getInstance().build(Constant.ACTIVITY_DETAILS_WEB)
                                .withString("id", bean.getId() + "")
                                .withString("code", ParamsCommon.ACTIVITY_CHANELCODE)
                                .withString("title", "活动详情")
                                .navigation();
                    }
                });
            }
        };
        View view2 = getLayoutInflater().inflate(R.layout.include_adapter_footer_more,
                (ViewGroup) mAllRvActivity
                .getParent(), false);
        TextView foot2 = (TextView) view2.findViewById(R.id.tv_footer_more);
        foot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build(Constant.ACTIVITY_FESTIVAL)
                        .withString("title","节庆活动")
                        .withString("code",ParamsCommon.ACTIVITY_CHANELCODE)
                        .navigation();
            }
        });
        mActicityAdapter.addFooterView(view2);
        mAllRvActivity.setAdapter(mActicityAdapter);
    }

    /**
     * 初始化线路适配器
     */
    private void initLineAdapter() {
        /**
         * 线路
         */
        mAllRvLine.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mLineAdapter = new BaseQuickAdapter<MyStrategyListBean, BaseViewHolder>(R.layout
                .item_fun_line, null) {
            @Override
            protected void convert(BaseViewHolder holder, final MyStrategyListBean bean) {
                GlideUtils.loadImage(mContext,(ImageView) holder.getView(R.id.img_index_scenic),bean.getCoverTwoToOne(),R.mipmap.common_ba_banner);
                holder.setText(R.id.tv_index_scenic, bean.getTitle());
                holder.setOnClickListener(R.id.img_index_scenic, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LogUtils.e("id-->" + bean.getId());
                        String id = bean.getId() + "";
                        ARouter.getInstance().build(Constant.ACTIVITY_LINEDETAIL).withString
                                ("mId", id)
                                .navigation();
                    }
                });
            }
        };
        View view1 = getLayoutInflater().inflate(R.layout.include_adapter_footer_more,
                (ViewGroup) mAllRvLine
                .getParent(), false);
        TextView foot1 = (TextView) view1.findViewById(R.id.tv_footer_more);
        foot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.startActivity(LineActivity.class);
            }
        });
        mLineAdapter.addFooterView(view1);
        mAllRvLine.setAdapter(mLineAdapter);
    }

    /**
     * 初始化推荐全景适配器
     */
    private void initScenicAdapter() {
        mAllRv.setLayoutManager(new GridLayoutManager(getActivity(), 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mPanAdapter = new BaseQuickAdapter<PanoramaListBean, BaseViewHolder>(
                R.layout.item_fun_panorama, null) {
            @Override
            protected void convert(BaseViewHolder holder, final PanoramaListBean bean) {
                GlideUtils.loadImage(mContext,
                        (RoundImageView) holder.getView(R.id.img_index_scenic),bean.getCoverpictureOneToOne(),R.mipmap.comimg_fail_240_180);
                holder.setText(R.id.tv_index_scenic, bean.getName());
                holder.setOnClickListener(R.id.img_index_scenic, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ARouter.getInstance().build(Constant.ACTIVITY_BASEWEB).withString
                                ("HTMLURL", bean.getUrl())
                                .withString("HTMLTITLE", bean.getName())
                                .navigation();
                    }
                });
            }
        };
        View view = getLayoutInflater().inflate(R.layout.include_adapter_footer_more, (ViewGroup)
                        mAllRv.getParent(),
                false);
        TextView foots = (TextView) view.findViewById(R.id.tv_footer_more);
        foots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build(Constant.ACTIVITY_PANORAMALIST).navigation();
            }
        });
        mPanAdapter.addFooterView(view);
        mAllRv.setAdapter(mPanAdapter);
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
    public void setPanorama(List<PanoramaListBean> list) {
        try {
            if (ObjectUtils.isNotEmpty(list) && list.size() > 0) {
                fun_panorama_ll.setVisibility(View.VISIBLE);
                mPanAdapter.setNewData(list);
            } else {
                fun_panorama_ll.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            fun_panorama_ll.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }

    @Override
    public void initBanner(List<IndexBanner> list) {
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
                        .bga_banner_point_enabled,})
                .setOnItemClickListener(this)
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        //  设置不能手动影响
        if (list.size() != 0 && list.size() == 1) {
            mBanner.setCanLoop(false);
            mBanner.setPointViewVisible(false);
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
                        .bga_banner_point_enabled,})
                .setOnItemClickListener(this)
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
    public void setLineData(List<MyStrategyListBean> list) {
        try {
            mSmartRefresh.finishRefresh();
            if (ObjectUtils.isNotEmpty(list) && list.size() > 0) {
                if (ProjectConfig.CITY_NAME.equals("察尔汗")){
                    fun_ll_gongl.setVisibility(View.VISIBLE);
                    mGonglAdapter.setNewData(list);
                }else {
                    fun_line_ll.setVisibility(View.VISIBLE);
                    mLineAdapter.setNewData(list);
                }
            } else {
                fun_ll_gongl.setVisibility(View.GONE);
                fun_line_ll.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            fun_ll_gongl.setVisibility(View.GONE);
            fun_line_ll.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }

    @Override
    public void refreshActivity(List<IndexActivity> list) {
        try {
            if (ObjectUtils.isNotEmpty(list) && list.size() > 0) {
                fun_activity_ll.setVisibility(View.VISIBLE);
                mActicityAdapter.setNewData(list);
            } else {
                fun_activity_ll.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            fun_activity_ll.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }

    @Override
    public void setBestScenicData(List<IndexScenic> mlist) {
        try {
            if (mlist.size()>0){
                fun_best_scenic.setVisibility(View.VISIBLE);
                mBestScenicAdapter.setNewData(mlist);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(int position) {

    }
}
