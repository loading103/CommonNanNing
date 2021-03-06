package com.daqsoft.commonnanning.ui.service.fun;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.location.AMapLocation;
import com.daqsoft.android.ProjectConfig;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.HtmlConstant;
import com.daqsoft.commonnanning.common.ParamsCommon;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.common.URLConstant;
import com.daqsoft.commonnanning.helps_gdmap.CompleteFuncData;
import com.daqsoft.commonnanning.helps_gdmap.HelpMaps;
import com.daqsoft.commonnanning.http.CommonRequest;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.scenic.VideoPlayActivity;
import com.daqsoft.commonnanning.ui.destination.DestinationProduct;
import com.daqsoft.commonnanning.ui.entity.AdvertEntity;
import com.daqsoft.commonnanning.ui.entity.IndexActivity;
import com.daqsoft.commonnanning.ui.entity.IndexBanner;
import com.daqsoft.commonnanning.ui.holder.NetWorkImageHolderView;
import com.daqsoft.commonnanning.ui.service.FoundNearNewActivity;
import com.daqsoft.commonnanning.ui.service.fun.bean.MonitorListBean;
import com.daqsoft.commonnanning.utils.GlideUtils;
import com.daqsoft.commonnanning.utils.Utils;
import com.daqsoft.commonnanning.view.webview.BaseWebFileActivity;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.BaseActivity;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.agora.yview.banner.ConvenientBanner;
import io.agora.yview.banner.holder.CBViewHolderCreator;
import io.agora.yview.banner.listener.OnItemClickListener;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * ???????????????
 *
 * @author ??????
 * @version 1.0.0
 * @date 2019/12/16.
 * @since JDK 1.8
 */
public class FunActivity extends BaseActivity {
    /**
     * banner
     */
    @BindView(R.id.banner_view)
    ConvenientBanner mBanner;
    @BindView(R.id.fun_recommend_list)
    RecyclerView funRecommendList;
    @BindView(R.id.ll_recommend)
    LinearLayout llRecommend;
    @BindView(R.id.fun_live_list)
    RecyclerView funLiveList;
    @BindView(R.id.ll_live)
    LinearLayout llLive;
    @BindView(R.id.head_title)
    HeadView headTitle;
    @BindView(R.id.fun_self_address)
    TextView funSelfAddress;
    @BindView(R.id.fun_scenic)
    TextView funScenic;
    @BindView(R.id.fun_hotel)
    TextView funHotel;
    @BindView(R.id.fun_food)
    TextView funFood;
    @BindView(R.id.fun_shopping)
    TextView funShopping;
    @BindView(R.id.fun_recreation)
    TextView funRecreation;
    @BindView(R.id.fun_group)
    TextView fun_group;
    /**
     * ?????????????????????
     */
    BaseQuickAdapter recommendAdapter;
    /**
     * ??????????????????
     */
    BaseQuickAdapter liveAdapter;
    /**
     * ??????????????????
     */
    private boolean canScroll = false;
    /**
     * ????????????
     */
    AMapLocation mapLocation = null;


    @Override
    public int getLayoutId() {
        return R.layout.activity_fun;
    }

    @Override
    public void initView() {
        headTitle.setTitle("?????????");
        initRecommendAdapter();
        initLiveAdapter();
        if(ProjectConfig.CITY_NAME.equals("??????")){
            fun_group.setVisibility(View.GONE);
        }else {
            fun_group.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initData() {
        getBanner();
        getRecommendData();
        getLiveData();
        setLocation();
    }

    @OnClick({R.id.fun_live, R.id.fun_recommend, R.id.fun_map, R.id.fun_activity, R.id.fun_vote,
            R.id.fun_experience, R.id.fun_group, R.id.fun_scenic, R.id.fun_hotel, R.id.fun_food,
            R.id.fun_shopping, R.id.fun_recreation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fun_live:
                // ?????????
                Intent intent = new Intent(FunActivity.this, MonitorActivity.class);
                startActivity(intent);
                break;
            case R.id.fun_recommend:
                // ????????????
                ARouter.getInstance().build(Constant.ACTIVITY_FESTIVAL).withString("title",
                        "????????????").withString("code", ParamsCommon.RECOMMEND_CHANELCODE).navigation();
                break;
            case R.id.fun_map:
                // ?????????
                ARouter.getInstance().build(Constant.ACTIVITY_FOUNDNEAR).navigation();
                break;
            case R.id.fun_activity:
                // ????????????
                ARouter.getInstance().build(Constant.ACTIVITY_FESTIVAL).withString("title",
                        "????????????").withString("code", ParamsCommon.ACTIVITY_CHANELCODE).navigation();
                break;
            case R.id.fun_vote:
                // ????????????
            case R.id.fun_experience:
                // ????????????
            case R.id.fun_group:
                // ?????????
                jumpPage(view.getId());
                break;
            case R.id.fun_scenic:
                // ??????
                goFoundNearNewAc(1);
                break;
            case R.id.fun_hotel:
                // ??????
                goFoundNearNewAc(2);
                break;
            case R.id.fun_food:
                // ??????
                goFoundNearNewAc(8);
                break;
            case R.id.fun_shopping:
                // ??????
                goFoundNearNewAc(4);
                break;
            case R.id.fun_recreation:
                // ??????
                goFoundNearNewAc(5);
                break;
            default:
                break;
        }
    }


    /**
     * ?????????
     */
    private void goFoundNearNewAc(int position) {
        Intent intent = new Intent(this, FoundNearNewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("lat", mapLocation.getLatitude() + "");
        bundle.putString("lng", mapLocation.getLongitude() + "");
        bundle.putInt("type", -1);
        bundle.putInt("curentType", position);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * ????????????
     *
     * @param id ????????????????????????
     */
    public void jumpPage(int id) {
        CommonRequest.checkedToken(new CommonRequest.OnStateCallBack() {
            @Override
            public void result(int value) {
                LoadingDialog.cancelDialogForLoading();
                // 0?????????
                if (0 == value) {
                    switch (id) {
                        case R.id.fun_vote:
                            // ????????????
                            Intent intent = new Intent(FunActivity.this, BaseWebFileActivity.class);
                            intent.putExtra("urlKey",
                                    URLConstant.VOTE_HTML_URL + SPUtils.getInstance().getString(SPCommon.TOKEN));
                            intent.putExtra("title", "????????????");
                            startActivity(intent);
                            break;
                        case R.id.fun_experience:
                            // ????????????
                            intent = new Intent(FunActivity.this, BaseWebFileActivity.class);
                            intent.putExtra("urlKey",
                                    URLConstant.RECRUIT_HTML_URL + SPUtils.getInstance().getString(SPCommon.TOKEN));
                            intent.putExtra("title", "????????????");
                            startActivity(intent);
                            break;
                        case R.id.fun_group:
                            // ?????????
                            /*intent = new Intent(FunActivity.this, BaseWebFileActivity.class);
                            intent.putExtra("urlKey",
                                    HtmlConstant.GROUP + SPUtils.getInstance().getString(SPCommon.TOKEN));
                            intent.putExtra("title", "?????????");
                            startActivity(intent);*/
                            // ????????????
                            CommonRequest.goToShoppingHtml(FunActivity.this,
                                    HtmlConstant.GROUP , "?????????");
                            break;
                        default:
                            break;
                    }
                } else {
                    ARouter.getInstance().build(Constant.ACTIVITY_LOGIN).navigation();
                }

            }
        });
    }

    /**
     * ??????????????????????????????
     */
    private void initRecommendAdapter() {
        funRecommendList.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        recommendAdapter =
                new BaseQuickAdapter<IndexActivity, BaseViewHolder>(R.layout.item_fun_recommend,
                        null) {
            @Override
            protected void convert(BaseViewHolder helper, final IndexActivity bean) {
                GlideUtils.loadImage(mContext,
                        (ImageView) helper.getView(R.id.item_fun_recommend_img),
                        bean.getCoverTwoToOne(), R.mipmap.common_ba_banner);
                helper.setText(R.id.item_fun_recommend_title, bean.getTitle());
                helper.setText(R.id.item_fun_recommend_start_time, bean.getBeginTime());
                helper.setText(R.id.item_fun_recommend_end_time, bean.getEndTime());
                helper.setOnClickListener(R.id.item_fun_recommend_ll, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ARouter.getInstance().build(Constant.ACTIVITY_DETAILS_WEB).withString("id"
                                , bean.getId() + "").withString("code",
                                ParamsCommon.RECOMMEND_CHANELCODE).withString("title", "????????????").navigation();
                    }
                });
            }
        };
        recommendAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
            }
        });
        recommendAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        funRecommendList.setAdapter(recommendAdapter);
    }

    /**
     * ??????????????????????????????
     */
    private void initLiveAdapter() {
        funLiveList.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        liveAdapter =
                new BaseQuickAdapter<MonitorListBean, BaseViewHolder>(R.layout.item_fun_live,
                        null) {
            @Override
            protected void convert(BaseViewHolder helper, final MonitorListBean bean) {
                helper.setVisible(R.id.img_paly, true);
                helper.setText(R.id.tv_view_num, bean.getViewNum());
                helper.setOnClickListener(R.id.img_index_scenic, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(FunActivity.this, VideoPlayActivity.class);
                        intent.putExtra("content", bean.getMonitorPath());
                        startActivity(intent);
                    }
                });
                GlideUtils.loadImage(FunActivity.this, helper.getView(R.id.img_index_scenic),
                        bean.getCover(), R.mipmap.common_img_fail_h158);
                helper.setText(R.id.tv_index_scenic, bean.getName());
            }
        };
        liveAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
            }
        });
        liveAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        funLiveList.setAdapter(liveAdapter);
    }

    /**
     * ????????????
     */
    @SuppressLint("CheckResult")
    private void getRecommendData() {
        recommendAdapter.setEnableLoadMore(false);
        RetrofitHelper.getApiService().getActivityList(1 + "", "3", "",
                ParamsCommon.RECOMMEND_CHANELCODE).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).subscribe(new Consumer<BaseResponse<IndexActivity>>() {
            @Override
            public void accept(BaseResponse<IndexActivity> bean) throws Exception {
                if (bean != null && bean.getCode() == 0 && bean.getDatas().size() > 0) {
                    recommendAdapter.setNewData(bean.getDatas());
                    llRecommend.setVisibility(View.VISIBLE);
                    recommendAdapter.setEnableLoadMore(false);
                } else {
                    llRecommend.setVisibility(View.GONE);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
            }
        });
    }


    @SuppressLint("CheckResult")
    private void getLiveData() {
        // ??????????????????????????????????????????????????????????????????
        liveAdapter.setEnableLoadMore(false);
        RetrofitHelper.getApiService().getMonitorList("1", "1").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).subscribe(new Consumer<BaseResponse<MonitorListBean>>() {
            @Override
            public void accept(BaseResponse<MonitorListBean> bean) throws Exception {
                if (bean != null && bean.getCode() == 0 && bean.getDatas().size() > 0) {
                    liveAdapter.setNewData(bean.getDatas());
                    llLive.setVisibility(View.VISIBLE);
                    liveAdapter.setEnableLoadMore(false);
                } else {
                    llLive.setVisibility(View.GONE);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
            }
        });
    }

    /**
     * ????????????
     */
    private void setLocation() {
        HelpMaps.startLocation(new CompleteFuncData() {
            @Override
            public void success(AMapLocation location) {
                if (ObjectUtils.isNotEmpty(location)) {
                    mapLocation = location;
                    try {
                        funSelfAddress.setText(location.getAddress());
                        HelpMaps.stopLocation();
                        getDestinationProduct();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * ????????????????????????????????????????????????????????????
     */
    @SuppressLint("CheckResult")
    public void getDestinationProduct() {
        if (mapLocation != null) {
            RetrofitHelper.getApiService().getDestinationProduct(mapLocation.getLatitude() + "",
                    mapLocation.getLongitude() + "", "5").doOnSubscribe(disposable -> addDisposable(disposable)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(bean -> {
                if (bean.getCode() == 0 && ObjectUtils.isNotEmpty(bean.getData())) {
                    DestinationProduct product = bean.getData();
                    funScenic.setText(Utils.setTextColor("??????(" + product.getSCENERY() + ")",
                            getResources().getColor(R.color.main_black), 2,
                            3 + (product.getSCENERY() + "").length()));
                    funHotel.setText(Utils.setTextColor("??????(" + product.getHOTEL() + ")",
                            getResources().getColor(R.color.main_black), 2,
                            3 + (product.getHOTEL() + "").length()));
                    funShopping.setText(Utils.setTextColor("??????(" + product.getSHOPPING() + ")",
                            getResources().getColor(R.color.main_black), 2,
                            3 + (product.getSHOPPING() + "").length()));
                    funRecreation.setText(Utils.setTextColor("??????(" + product.getRECREATION() + ")"
                            + "", getResources().getColor(R.color.main_black), 2,
                            3 + (product.getRECREATION() + "").length()));
                    funFood.setText(Utils.setTextColor("??????(" + product.getDINING() + ")",
                            getResources().getColor(R.color.main_black), 3,
                            3 + (product.getTOILET() + "").length()));
                }
            });
        }
    }

    /**
     * ?????????banner???
     *
     * @param list
     */
    public void initBanner(List<IndexBanner> list) {
        mBanner.setPages(new CBViewHolderCreator() {
            @Override
            public NetWorkImageHolderView createHolder(View itemView) {
                return new NetWorkImageHolderView(itemView, FunActivity.this);
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_banner_img;
            }
        }, list)
                // ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????,????????????????????????????????????
                .setPageIndicator(new int[]{R.drawable.bga_banner_point_disabled,
                        R.drawable.bga_banner_point_enabled,}).setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        }).setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        //      .setOnPageChangeListener(this)
        // ??????????????????
        //        convenientBanner.setManualPageable(false);
        //  ????????????????????????
        if (list.size() == 1) {
            mBanner.setCanLoop(false);
            mBanner.setPointViewVisible(false);
            canScroll = false;
            mBanner.stopTurning();
        } else {
            canScroll = true;
            mBanner.startTurning();
        }
    }

    /**
     * ??????banner?????????
     */
    @SuppressLint("CheckResult")
    private void getBanner() {
        RetrofitHelper.getApiService().getIndexBannar(ParamsCommon.FUN_BANNER).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).subscribe(new Consumer<BaseResponse<AdvertEntity>>() {
            @Override
            public void accept(BaseResponse<AdvertEntity> bean) throws Exception {
                List<IndexBanner> mlist = new ArrayList<>();
                mlist.clear();
                if (bean.getCode() == 0 && bean.getDatas().size() > 0) {
                    for (int i = 0; i < bean.getDatas().size(); i++) {
                        IndexBanner beans = new IndexBanner();
                        beans.setId(bean.getDatas().get(i).getId());
                        beans.setImg(bean.getDatas().get(i).getPics().get(0));
                        mlist.add(beans);
                    }
                    initBanner(mlist);
                } else {
                    IndexBanner beans = new IndexBanner();
                    beans.setId("");
                    beans.setImg("");
                    mlist.add(beans);
                    initBanner(mlist);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                List<IndexBanner> mlist = new ArrayList<>();
                IndexBanner beans = new IndexBanner();
                beans.setId("");
                beans.setImg("");
                mlist.add(beans);
                initBanner(mlist);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        // ??????????????????
        if (canScroll) {
            mBanner.startTurning();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (canScroll) {
            // ????????????
            mBanner.stopTurning();
        }
    }

}
