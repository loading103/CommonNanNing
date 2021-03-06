package com.daqsoft.commonnanning.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daqsoft.android.ProjectConfig;
import com.daqsoft.busquery.BusQueryActivity;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.ar.UnityPlayerActivity;
import com.daqsoft.commonnanning.common.AESEncryptUtil;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.HtmlConstant;
import com.daqsoft.commonnanning.common.LabelCommon;
import com.daqsoft.commonnanning.common.ParamsCommon;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.common.URLConstant;
import com.daqsoft.commonnanning.http.CommonRequest;
import com.daqsoft.commonnanning.ui.adapter.ViewpageAdapter;
import com.daqsoft.commonnanning.ui.country.CountryDetailsActivity;
import com.daqsoft.commonnanning.ui.country.CountryListActivity;
import com.daqsoft.commonnanning.ui.country.entity.CountryBean;
import com.daqsoft.commonnanning.ui.entity.GuideBean;
import com.daqsoft.commonnanning.ui.entity.IndexActivity;
import com.daqsoft.commonnanning.ui.entity.IndexBanner;
import com.daqsoft.commonnanning.ui.entity.IndexMenu;
import com.daqsoft.commonnanning.ui.entity.MyStrategyListBean;
import com.daqsoft.commonnanning.ui.entity.PanoramaListBean;
import com.daqsoft.commonnanning.ui.holder.NetWorkIndexBannerRoundImageHolderView;
import com.daqsoft.commonnanning.ui.main.contract.IndexNewContact;
import com.daqsoft.commonnanning.ui.main.presenter.IndexNewPresenter;
import com.daqsoft.commonnanning.ui.service.FoundNearNewActivity;
import com.daqsoft.commonnanning.ui.service.news.NewsWebActivity;
import com.daqsoft.commonnanning.utils.GlideUtils;
import com.daqsoft.commonnanning.utils.Utils;
import com.daqsoft.commonnanning.view.BottomRoundImageView;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.mvp.BaseFragment;
import com.example.tomasyb.baselib.refresh.SmartRefreshLayout;
import com.example.tomasyb.baselib.refresh.api.RefreshHeader;
import com.example.tomasyb.baselib.refresh.api.RefreshLayout;
import com.example.tomasyb.baselib.refresh.listener.OnRefreshListener;
import com.example.tomasyb.baselib.refresh.listener.SimpleMultiPurposeListener;
import com.example.tomasyb.baselib.rvadapter.CommonAdapter;
import com.example.tomasyb.baselib.rvadapter.base.ViewHolder;
import com.example.tomasyb.baselib.util.ActivityUtils;
import com.example.tomasyb.baselib.util.LogUtils;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.widget.img.RoundImageView;
import com.example.tomasyb.baselib.widget.viewpager.ComPagerWithTitleAdapter;
import com.example.tomasyb.baselib.widget.viewpager.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.agora.yview.banner.ConvenientBanner;
import io.agora.yview.banner.holder.CBViewHolderCreator;
import io.agora.yview.banner.listener.OnItemClickListener;
import io.agora.yview.banner.listener.OnPageChangeListener;
import io.agora.yview.tablayout.SlidingTabLayout;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * ??????fragment???(????????????)
 *
 * @author ??????
 * @version 1.0.0
 * @date 2018-6-4.15:50
 * @since JDK 1.8
 */

public class MainXlgLNewFragment extends BaseFragment<IndexNewContact.presenter> implements IndexNewContact.view, OnItemClickListener {
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.index_banner)
    ConvenientBanner mBanner;
    @BindView(R.id.index_rv)
    RecyclerView mRv;
    @BindView(R.id.slid_tab)
    SlidingTabLayout mSlidTabLayout;
    @BindView(R.id.index_viewpager)
    NoScrollViewPager mViewHotPager;

    @BindView(R.id.index_rv_seven)
    RecyclerView mRvSceven;
    @BindView(R.id.container_sceven)
    ConstraintLayout mConSceven;
    @BindView(R.id.ingdex_travel_rv)
    RecyclerView mRvTravelGuide;
    @BindView(R.id.container_gong)
    ConstraintLayout mTravel;
    @BindView(R.id.ingdex_rural_rv)
    RecyclerView mRvRural;
    @BindView(R.id.ingdex_famer_rv)
    RecyclerView mRvFamer;
    @BindView(R.id.container_xiang)
    ConstraintLayout mRuRalCol;
    @BindView(R.id.container_famer)
    ConstraintLayout mFamerCol;
    @BindView(R.id.container_activity)
    ConstraintLayout mActivityCol;
    @BindView(R.id.ingdex_activity_rv)
    RecyclerView mRvActivity;
    @BindView(R.id.index_top_viewpager)
    ViewPager mViewTopPager;
    @BindView(R.id.index_iv_ar)
    ImageView mIvAr;
    @BindView(R.id.index_iv_icon)
    ImageView mIvIcon;
    @BindView(R.id.index_iv_line)
    View mLine;
    private ComPagerWithTitleAdapter mAdapter;
    /**
     * ???????????????
     */
    private BaseQuickAdapter<CountryBean, BaseViewHolder> mRualAdapter;
    /**
     * ???????????????
     */
    private BaseQuickAdapter<CountryBean, BaseViewHolder> mFamerAdapter;
    /**
     * ?????????????????????
     */
    private BaseQuickAdapter<IndexActivity, BaseViewHolder> mActivityAdapter;
    /**
     * ????????????
     */
    private BaseQuickAdapter<MyStrategyListBean, BaseViewHolder> mLineAdapter;
    /**
     * ???????????????ID
     */
    private int GuideId = -11;

    /**
     * ??????????????????
     */
    private CommonAdapter<IndexMenu> mMenuAdapter;

    /**
     * ???????????????
     */
    private BaseQuickAdapter<PanoramaListBean, BaseViewHolder> mPanAdapter;
    /**
     * ??????????????????????????????
     */
    private String[] mTitles;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private List<View> mTopBaImgList = new ArrayList<>();
    /**
     * ??????viewpager
     */
    private ViewpageAdapter mTopViewPagerAdapter;

    @Override
    public void setLineData(List<MyStrategyListBean> list) {
        try {
            if (ObjectUtils.isNotEmpty(list) && list.size() > 0) {
                mTravel.setVisibility(View.VISIBLE);
                mLineAdapter.setNewData(list);
            } else {
                mTravel.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            mTravel.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }

    @Override
    public void setRuRalData(List<CountryBean> list) {
        try {
            if (list != null && list.size() > 0) {
                mRuRalCol.setVisibility(View.VISIBLE);
                mRualAdapter.setNewData(list);
            } else {
                mRuRalCol.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            mRuRalCol.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }

    @Override
    public void setFamerData(List<CountryBean> list) {
        try {
            if (list != null && list.size() > 0) {
                mFamerCol.setVisibility(View.VISIBLE);
                mFamerAdapter.setNewData(list);
            } else {
                mFamerCol.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            mFamerCol.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }

    @Override
    public void setActivityData(List<IndexActivity> activityData) {
        try {
            if (activityData != null && activityData.size() > 0) {
                mActivityCol.setVisibility(View.VISIBLE);
                mActivityAdapter.setNewData(activityData);
            } else {
                mActivityCol.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            mActivityCol.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }

    /**
     * ???????????????????????????
     */
    private void initFamerAdapter() {
        mRvFamer.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false) {

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mFamerAdapter =
                new BaseQuickAdapter<CountryBean, BaseViewHolder>(R.layout.item_index_famer, null) {
            @Override
            protected void convert(BaseViewHolder holder, final CountryBean bean) {
                GlideUtils.loadImage(mContext, (ImageView) holder.getView(R.id.img_index_scenic),
                        bean.getCoverTwoToOne(), R.mipmap.common_ba_banner);
                holder.setText(R.id.tv_travel_name, bean.getName());
                holder.setOnClickListener(R.id.img_index_scenic, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LogUtils.e("id-->" + bean.getId());
                        String id = bean.getId() + "";
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), CountryDetailsActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("title", "?????????");
                        startActivity(intent);
                    }
                });
            }
        };
        mRvFamer.setAdapter(mFamerAdapter);
    }

    /**
     * ??????????????????????????????
     */
    private void initRuralAdapter() {
        mRvRural.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false) {

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRualAdapter =
                new BaseQuickAdapter<CountryBean, BaseViewHolder>(R.layout.item_index_rural, null) {
            @Override
            protected void convert(BaseViewHolder holder, final CountryBean bean) {
                GlideUtils.loadImage(mContext, (ImageView) holder.getView(R.id.img_index_scenic),
                        bean.getCoverFourToThree(), R.mipmap.common_ba_banner);
                holder.setText(R.id.tv_travel_name, bean.getName());
                holder.setOnClickListener(R.id.img_index_scenic, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LogUtils.e("id-->" + bean.getId());
                        String id = bean.getId() + "";
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), CountryDetailsActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("title", "????????????");
                        startActivity(intent);
                    }
                });
            }
        };
        mRvRural.setAdapter(mRualAdapter);
    }

    /**
     * ????????????????????????
     */
    private void initLineAdapter() {
        /**
         * ??????
         */
        mRvTravelGuide.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false) {

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mLineAdapter =
                new BaseQuickAdapter<MyStrategyListBean, BaseViewHolder>(R.layout.item_index_line
                        , null) {
            @Override
            protected void convert(BaseViewHolder holder, final MyStrategyListBean bean) {
                GlideUtils.loadImage(mContext, (ImageView) holder.getView(R.id.img_index_scenic),
                        bean.getCoverTwoToOne(), R.mipmap.common_ba_banner);
                holder.setText(R.id.tv_travel_name, bean.getTitle());
                holder.setOnClickListener(R.id.img_index_scenic, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LogUtils.e("id-->" + bean.getId());
                        String id = bean.getId() + "";
                        ARouter.getInstance().build(Constant.ACTIVITY_LINEDETAIL).withString("mId"
                                , id).navigation();
                    }
                });
            }
        };
        mRvTravelGuide.setAdapter(mLineAdapter);
    }

    /**
     * ??????????????????????????????
     */
    private void initActivityAdapter() {
        /**
         * ????????????
         */
        mRvActivity.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false) {

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mActivityAdapter =
                new BaseQuickAdapter<IndexActivity, BaseViewHolder>(R.layout.item_index_activity,
                        null) {
            @Override
            protected void convert(BaseViewHolder holder, final IndexActivity bean) {
                GlideUtils.loadImage(mContext,
                        (RoundImageView) holder.getView(R.id.index_img_jie),
                        bean.getCoverTwoToOne(), R.mipmap.comimg_fail_240_180);
                holder.setText(R.id.tv_title, bean.getTitle());
                holder.setText(R.id.tv_content, bean.getTheme());
                holder.setText(R.id.tv_time, bean.getCreateTime());
                holder.setText(R.id.tv_star, bean.getViewCount() + "");
                holder.setOnClickListener(R.id.ll_activity, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), NewsWebActivity.class);
                        intent.putExtra("id", bean.getId() + "");
                        intent.putExtra("title", bean.getTitle());
                        intent.putExtra("code", ParamsCommon.ACTIVITY_CHANELCODE);
                        startActivity(intent);
                    }
                });
            }
        };
        mRvActivity.setAdapter(mActivityAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fg_main_new_xlgl;
    }

    @Override
    public IndexNewContact.presenter initPresenter() {
        return new IndexNewPresenter(this);
    }

    @Override
    public void setPanorama(List<PanoramaListBean> list) {
        try {
            if (ObjectUtils.isNotEmpty(list) && list.size() > 0) {
                mConSceven.setVisibility(View.VISIBLE);
                mPanAdapter.setNewData(list);
            } else {
                mConSceven.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            mConSceven.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }

    /**
     * ??????????????????????????????
     */
    private void initScenicAdapter() {
        mRvSceven.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false) {

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mPanAdapter =
                new BaseQuickAdapter<PanoramaListBean, BaseViewHolder>(R.layout.item_index_panorama, null) {
            @Override
            protected void convert(BaseViewHolder holder, final PanoramaListBean bean) {
                GlideUtils.loadImage(mContext,
                        (RoundImageView) holder.getView(R.id.img_index_scenic),
                        bean.getCoverpictureOneToOne(), R.mipmap.comimg_fail_240_180);
                holder.setText(R.id.tv_index_scenic, bean.getName());
                holder.setOnClickListener(R.id.img_index_scenic, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ARouter.getInstance().build(Constant.ACTIVITY_BASEWEB).withString(
                                "HTMLURL", bean.getUrl()).withString("HTMLTITLE", bean.getName()).navigation();
                    }
                });
            }
        };
        mRvSceven.setAdapter(mPanAdapter);
    }

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
        mTitles = getActivity().getResources().getStringArray(R.array.index_title);
        // ??????
        if ("???????????????".equals(ProjectConfig.CITY_NAME) || "??????".equals(ProjectConfig.CITY_NAME)) {
            if ("???????????????".equals(ProjectConfig.CITY_NAME)) {
                mIvAr.setVisibility(View.VISIBLE);
            }
            mIvAr.getBackground().setAlpha(50);
            mIvIcon.setVisibility(View.GONE);
            mLine.setVisibility(View.GONE);
        }
        setBannerNodata();
        initRefresh();
        initFamerAdapter();
        initScenicAdapter();
        initHotViewPager();
        initLineAdapter();
        initRuralAdapter();
        initActivityAdapter();
        mPresenter.setMenuData(getActivity());
        mRefreshLayout.autoRefresh();
    }

    /**
     * ?????????????????????
     */
    private void initTopBgView(List<IndexBanner> list) {
        if (list != null) {
            mTopBaImgList.clear();
            if (list.size() > 1) {
                for (int i = 0; i < list.size(); i++) {
                    View view =
                            LayoutInflater.from(getActivity()).inflate(R.layout.item_index_bg_imgview, null);
                    BottomRoundImageView imgbg =
                            (BottomRoundImageView) view.findViewById(R.id.img_index_bg);
                    Glide.with(this).load(list.get(i).getImg()).apply(RequestOptions.bitmapTransform(new BlurTransformation(25))).into(imgbg);
                    mTopBaImgList.add(view);
                }
                mTopViewPagerAdapter = new ViewpageAdapter(mTopBaImgList);
                mViewTopPager.setAdapter(mTopViewPagerAdapter);
            } else if (list.size() == 1) {
                if (ObjectUtils.isNotEmpty(list.get(0).getImg())) {
                    View view =
                            LayoutInflater.from(getActivity()).inflate(R.layout.item_index_bg_imgview, null);
                    BottomRoundImageView imgbg =
                            (BottomRoundImageView) view.findViewById(R.id.img_index_bg);
                    Glide.with(this).load(list.get(0).getImg()).apply(RequestOptions.bitmapTransform(new BlurTransformation(25))).into(imgbg);
                    mTopBaImgList.add(view);
                    mTopViewPagerAdapter = new ViewpageAdapter(mTopBaImgList);
                    mViewTopPager.setAdapter(mTopViewPagerAdapter);
                }
            }
        }
    }

    /**
     * ??????????????????????????????
     */
    private void initHotViewPager() {
        for (int i = 0; i < mTitles.length; i++) {
            mFragments.add(MainHotNewFragment.getInstance(i));
        }
        mAdapter = new ComPagerWithTitleAdapter(getActivity().getSupportFragmentManager(),
                mFragments, mTitles);
        mViewHotPager.setAdapter(mAdapter);
        mViewHotPager.setOffscreenPageLimit(3);
        mSlidTabLayout.setViewPager(mViewHotPager, mTitles, getActivity(), mFragments);
    }


    /**
     * ??????????????????
     */
    private void getData() {
        mPresenter.getPanoramaList();
        mPresenter.getBannerData();
        mPresenter.getLineData();
        if ("??????".equals(ProjectConfig.CITY_NAME)) {
            mPresenter.getRuralData();
            mPresenter.getFamerData();
        } else if ("???????????????".equals(ProjectConfig.CITY_NAME) || "??????".equals(ProjectConfig.CITY_NAME)) {
            mPresenter.getActivityData();
        }
        for (int i = 0; i < mFragments.size(); i++) {
            ((MainHotNewFragment) mFragments.get(i)).getData();
        }
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
        bannerlist.clear();
        bannerlist.addAll(list);
        initTopBgView(bannerlist);
        mBanner.notifyDataSetChanged();
    }

    private List<IndexBanner> bannerlist = new ArrayList<>();

    /**
     * ?????????banner
     */
    private void setBannerNodata() {
        mBanner.setPages(new CBViewHolderCreator() {
            @Override
            public NetWorkIndexBannerRoundImageHolderView createHolder(View itemView) {
                return new NetWorkIndexBannerRoundImageHolderView(itemView, getActivity());
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_banner_round_img;
            }
        }, bannerlist)
                // ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????,????????????????????????????????????
                .setPageIndicator(new int[]{R.drawable.bga_banner_point_disabled,
                        R.drawable.bga_banner_point_enabled}).setOnItemClickListener(this).setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL).setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //                LogUtils.e("--------------------->" + newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }

            @Override
            public void onPageSelected(int index) {
                mViewTopPager.setCurrentItem(index, false);
            }
        });

        mBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (!TextUtils.isEmpty(bannerlist.get(position).getUrl())) {
                    //                    Intent intent =new Intent(getContext(), BaseWebActivity
                    //                    .class);
                    //                    intent.putExtra("HTMLURL", bannerlist.get(position)
                    //                    .getUrl());
                    //                    intent.putExtra("HTMLTITLE", bannerlist.get(position)
                    //                    .getName());
                    //                    startActivity(intent);

                    checkToken(bannerlist.get(position).getUrl());
                }
            }
        });
    }

    @Override
    public void initRv(List<IndexMenu> mDatas) {
        mRv.setLayoutManager(new GridLayoutManager(getActivity(), 5) {
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
                        switch (indexMenu.getType()) {
                            case LabelCommon.PARKING:
                                // ?????????
                                // ???????????????
                                Bundle bundle = new Bundle();
                                bundle.putInt("type", 0);
                                bundle.putInt("curentType", 10);
                                Intent intent = new Intent(getActivity(),
                                        FoundNearNewActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                break;
                            case LabelCommon.TOILET:
                                // ??????
                                if ("??????".equals(ProjectConfig.CITY_NAME)) {
                                    ARouter.getInstance().build(Constant.ACTIVITY_TOILET_LIST).navigation();
                                } else {
                                    bundle = new Bundle();
                                    bundle.putInt("type", 0);
                                    bundle.putInt("curentType", 12);
                                    intent = new Intent(getActivity(), FoundNearNewActivity.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                                break;
                            case LabelCommon.WEATHER:
                                // ??????
                                if (ProjectConfig.CITY_NAME.equals("??????")) {
                                    String url =
                                            URLConstant.WEATHER_HTML_NANNING_LAT + "lng=" + SPUtils.getInstance().getString(SPCommon.LONGITUDE) + "&lat=" + SPUtils.getInstance().getString(SPCommon.LATITUDE);
                                    ARouter.getInstance().build(Constant.ACTIVITY_BASEWEB).withString("HTMLURL", url).withString("HTMLTITLE", "??????").navigation();
                                } else {
                                    ARouter.getInstance().build(Constant.ACTIVITY_BASEWEB).withString("HTMLURL", URLConstant.WEATHER_HTML).withString("HTMLTITLE", "????????????").withBoolean("isNeedTitle", true).navigation();

                                }
                                break;
                            case LabelCommon.MUSEUM:
                                // ?????????
                                ARouter.getInstance().build(Constant.ACTIVITY_LIBRARY).withInt(
                                        "pageType", 0).navigation();
                                break;
                            case LabelCommon.LIBRARY:
                                // ?????????
                                ARouter.getInstance().build(Constant.ACTIVITY_LIBRARY).withInt(
                                        "pageType", 1).navigation();
                                break;
                            case LabelCommon.SCENIC:
                                // ??????
                                ARouter.getInstance().build(Constant.ACTIVITY_SCENIC).navigation();
                                break;
                            case LabelCommon.HOTEL:
                                // ??????
                                ARouter.getInstance().build(Constant.ACTIVITY_HOTEL).withString(
                                        "grade", "").navigation();
                                break;
                            case LabelCommon.TRAVEL:
                                // ?????????
                                ARouter.getInstance().build(Constant.ACTIVITY_TRAVEL).navigation();
                                break;
                            case LabelCommon.TRAIN:
                                // ????????????????????????????????????????????????
                                if (ProjectConfig.CITY_NAME.equals("??????")) {
                                    checkToken();
                                } else {
                                    CommonRequest.goToShoppingHtml(getActivity(),
                                            HtmlConstant.TRAIN, "?????????");
                                }
                                break;
                            case LabelCommon.SHEPHERD:
                                ARouter.getInstance().build(Constant.ACTIVITY_BASEWEB).withString("HTMLURL", URLConstant.SHEPHERD_HOME_URL).withString("HTMLTITLE", "????????????").navigation();
                                // ????????????
                                break;
                            case LabelCommon.PRODUCT:
                                // ????????????
                                CommonRequest.goToShoppingHtml(getActivity(),
                                        ProjectConfig.BASE_HTML_MALL, "????????????");
                                break;
                            case LabelCommon.SHOPPING:
                                // ??????
                                bundle = new Bundle();
                                bundle.putInt("type", 0);
                                bundle.putInt("curentType", 4);
                                intent = new Intent(getActivity(), FoundNearNewActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                break;
                            case LabelCommon.GASSTATION:
                                // ?????????
                                bundle = new Bundle();
                                bundle.putInt("type", 0);
                                bundle.putInt("curentType", 11);
                                intent = new Intent(getActivity(), FoundNearNewActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                break;
                            case LabelCommon.PANORAMA:
                                // 720??????
                                ARouter.getInstance().build(Constant.ACTIVITY_PANORAMALIST).navigation();
                                break;
                            case LabelCommon.SPECIALTY:
                                // ??????
                                ARouter.getInstance().build(Constant.ACTIVITY_SPECIALTY_LIST).navigation();
                                break;
                            case LabelCommon.FYML:
                                // ????????????
                                ARouter.getInstance().build(Constant.ACTIVITY_CONTENT_WEB).withInt("pageType", 4).navigation();
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

    /**
     * ???????????????banner??????????????????
     */
    private void checkToken(String url) {
        if (ObjectUtils.isNotEmpty(SPUtils.getInstance().getString(SPCommon.TOKEN))) {
            CommonRequest.checkedToken(new CommonRequest.OnStateCallBack() {
                @Override
                public void result(int value) {
                    // 0?????????
                    if (0 == value) {
                        String phone = SPUtils.getInstance().getString(SPCommon.PHONE);
                        boolean isService = SPUtils.getInstance().getBoolean(SPCommon.SERVICE,
                                false);
                        try {
                            String aesphone = AESEncryptUtil.Encrypt16(phone);
                            String u = url;
                            if (url.contains("?")) {
                                u = url + "&phone=" + aesphone;
                            } else {
                                u = url + "?phone=" + aesphone;
                            }
                            // ??????????????????
                            if (isService) {
                                ARouter.getInstance().build(Constant.ACTIVITY_BASEWEB).withString("HTMLTITLE", "--").withString("HTMLURL", u).navigation();
                            } else {
                                ARouter.getInstance().build(Constant.ACTIVITY_USER_SERVICE).withString("HTMLURL", u).navigation();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        ARouter.getInstance().build(Constant.ACTIVITY_LOGIN).navigation();
                    }
                }
            });
        } else {
            ToastUtils.showShortCenter("????????????");
            ARouter.getInstance().build(Constant.ACTIVITY_LOGIN).navigation();
        }
    }


    private void checkToken() {
        if (ObjectUtils.isNotEmpty(SPUtils.getInstance().getString(SPCommon.TOKEN))) {
            CommonRequest.checkedToken(new CommonRequest.OnStateCallBack() {
                @Override
                public void result(int value) {
                    // 0?????????
                    if (0 == value) {
                        String phone = SPUtils.getInstance().getString(SPCommon.PHONE);
                        boolean isService = SPUtils.getInstance().getBoolean(SPCommon.SERVICE,
                                false);
                        try {
                            String aesphone = AESEncryptUtil.Encrypt16(phone);
                            String url =
                                    ProjectConfig.BASE_HTML_TRAIN + aesphone + "&targetUrl" +
                                            "=/throughTrain";
                            // ??????????????????
                            if (isService) {
                                ARouter.getInstance().build(Constant.ACTIVITY_BASEWEB).withString("HTMLTITLE", "--").withString("HTMLURL", url).navigation();
                            } else {
                                ARouter.getInstance().build(Constant.ACTIVITY_USER_SERVICE).withString("HTMLURL", url).navigation();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        ARouter.getInstance().build(Constant.ACTIVITY_LOGIN).navigation();
                    }
                }
            });
        } else {
            ToastUtils.showShortCenter("????????????");
            ARouter.getInstance().build(Constant.ACTIVITY_LOGIN).navigation();
        }
    }

    /**
     * ????????????
     *
     * @param list
     */
    @Override
    public void setMapData(List<GuideBean> list) {
        try {
            if (ObjectUtils.isNotEmpty(list)) {
                if (list.size() > 0) {
                    GuideId = list.get(0).getId();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onItemClick(int position) {
        // ?????????????????????????????????
    }

    @Override
    public void onResume() {
        super.onResume();
        // ??????????????????
        mBanner.startTurning();

    }

    @Override
    public void onPause() {
        super.onPause();
        // ????????????
        mBanner.stopTurning();
    }

    @OnClick({R.id.img_guide, R.id.index_et_search, R.id.img_more_scenen, R.id.img_more_hot,
            R.id.img_more_gong, R.id.img_more_rural, R.id.img_more_famer, R.id.img_trave,
            R.id.img_robot, R.id.index_iv_ar, R.id.img_more_activity,R.id.img_yuyue})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_robot:
                if ("??????".equals(ProjectConfig.CITY_NAME)) {
                    // ????????????
                    ARouter.getInstance().build(Constant.ACTIVITY_ONLINE_MESSAGE).navigation();
                } else {
                    // ?????????
                    ARouter.getInstance().build(Constant.ACTIVITY_ROBOT).navigation();
                }
                break;
            // ??????
            case R.id.img_trave:
                if ("??????".equals(ProjectConfig.CITY_NAME)) {
                    // ????????????
                    Bundle bundle = new Bundle();
                    bundle.putString("latitude", SPUtils.getInstance().getString("latitude"));
                    bundle.putString("longitude", SPUtils.getInstance().getString("longitude"));
                    bundle.putString("cityname", SPUtils.getInstance().getString("cityname"));
                    bundle.putString("cityAddress", SPUtils.getInstance().getString("cityAddress"));
                    Intent intent = new Intent(getActivity(), BusQueryActivity.class);
                    intent.putExtras(bundle);
                    getActivity().startActivity(intent);
                } else {
                    if (!Utils.isFastClick()) {
                        CommonRequest.checkedToken(new CommonRequest.OnStateCallBack() {
                            @Override
                            public void result(int value) {
                                // 0?????????
                                if (0 == value) {
                                    if (ProjectConfig.CITY_NAME.equals("??????")) {
                                        boolean isService =
                                                SPUtils.getInstance().getBoolean(SPCommon.SERVICE
                                                        , false);
                                        // ????????????
                                        if (isService) {
                                            ARouter.getInstance().build(Constant.ACTIVITY_ROUTE).navigation();
                                        } else {
                                            ARouter.getInstance().build(Constant.ACTIVITY_USER_SERVICE).withInt("mPageType", 1).navigation();
                                        }
                                    } else {
                                        ARouter.getInstance().build(Constant.ACTIVITY_ROUTE).navigation();
                                    }
                                } else {
                                    ARouter.getInstance().build(Constant.ACTIVITY_LOGIN).navigation();
                                }
                            }
                        });
                    }
                }
                break;
            case R.id.img_more_famer:
                Intent intent2 = new Intent(getActivity(), CountryListActivity.class);
                intent2.putExtra("title", "???????????????");
                intent2.putExtra("tag", 971);
                startActivity(intent2);
                break;
            case R.id.img_more_rural:
                Intent intent = new Intent(getActivity(), CountryListActivity.class);
                intent.putExtra("title", "????????????");
                intent.putExtra("tag", 972);
                startActivity(intent);
                break;
            case R.id.img_more_gong:
                ActivityUtils.startActivity(LineActivity.class);
                break;
            case R.id.img_more_hot:
                switch (mSlidTabLayout.getCurrentTab()) {
                    case 0:
                        // ??????
                        ARouter.getInstance().build(Constant.ACTIVITY_SCENIC).navigation();
                        break;
                    case 1:
                        // ??????
                        ARouter.getInstance().build(Constant.ACTIVITY_HOTEL).withString("grade",
                                "").navigation();
                        break;
                    case 2:
                        // ??????
                        ARouter.getInstance().build(Constant.ACTIVITY_HOTEL).withString("grade",
                                "hotelType_4").navigation();
                        break;
                    default:
                }
                break;
            case R.id.img_more_scenen:
                ARouter.getInstance().build(Constant.ACTIVITY_PANORAMALIST).navigation();
                break;
            case R.id.index_et_search:
                // ??????
                ARouter.getInstance().build(Constant.ACTIVITY_GLOBALSEARCH).navigation();
                break;
            case R.id.img_guide:
                if ("??????".equals(ProjectConfig.CITY_NAME)) {
                    // ??????
                    ARouter.getInstance().build(Constant.ACTIVITY_COMPLAINT).navigation();
                } else {
                    // ????????????????????????
                    intent = new Intent(getActivity(), GuideListActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.index_iv_ar:
                // AR
                intent = new Intent(getActivity(), UnityPlayerActivity.class);
                startActivity(intent);
                break;
            case R.id.img_more_activity:
                // ????????????
                ARouter.getInstance().build(Constant.ACTIVITY_FESTIVAL).withString("title",
                        "????????????").withString("code", ParamsCommon.ACTIVITY_CHANELCODE).navigation();
                break;
            case R.id.img_yuyue:
                ARouter.getInstance().build(Constant.ACTIVITY_BASEWEB).withString("HTMLURL", URLConstant.SHEPHERD_YU_YUE_URL).withString("HTMLTITLE", "????????????").navigation();
                // ????????????
                break;
            default:
                break;
        }
    }
}
