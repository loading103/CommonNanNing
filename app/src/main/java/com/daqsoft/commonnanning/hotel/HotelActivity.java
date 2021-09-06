package com.daqsoft.commonnanning.hotel;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.location.AMapLocation;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.base.IApplication;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.common.URLConstant;
import com.daqsoft.commonnanning.helps_gdmap.CompleteFuncData;
import com.daqsoft.commonnanning.helps_gdmap.HelpMaps;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.HotelEntity;
import com.daqsoft.commonnanning.ui.entity.IndexBanner;
import com.daqsoft.commonnanning.ui.entity.SelectEntity;
import com.daqsoft.commonnanning.ui.holder.NetWorkImageHolderView;
import com.daqsoft.commonnanning.view.FlowLayout;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.glide.GlideApp;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.refresh.SmartRefreshLayout;
import com.example.tomasyb.baselib.refresh.api.RefreshLayout;
import com.example.tomasyb.baselib.refresh.listener.OnRefreshListener;
import com.example.tomasyb.baselib.util.GsonUtils;
import com.example.tomasyb.baselib.util.KeyboardUtils;
import com.example.tomasyb.baselib.util.LogUtils;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.util.SizeUtils;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.LoadingDialog;
import com.example.tomasyb.baselib.widget.img.RoundImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.agora.yview.banner.ConvenientBanner;
import io.agora.yview.banner.holder.CBViewHolderCreator;
import io.agora.yview.banner.listener.OnItemClickListener;
import io.agora.yview.view.CenterDrawableEdittext;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * -----------------------------------------------------------------
 *
 * @version 1.0.1
 * @Description 酒店页面
 * @Author 董彧傑
 * @CreateDate 2019-3-20 15:56
 * @since jdk1.8.0_201
 * -----------------------------------------------------------------
 */
@Route(path = Constant.ACTIVITY_HOTEL)
public class HotelActivity extends BaseActivity<HotelContact.presenter> implements HotelContact.view {


    @BindView(R.id.route_help_title)
    HeadView routeHelpTitle;
    @BindView(R.id.banner_view)
    ConvenientBanner bannerView;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.type_choose)
    TextView typeChoose;
    @BindView(R.id.smart_choose)
    TextView smartChoose;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.scenic_search)
    CenterDrawableEdittext scenicSearch;

    @BindView(R.id.view_animator)
    ViewAnimator viewAnimator;
    @BindView(R.id.swipe_refresh)
    SmartRefreshLayout swipeRefresh;
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;

    /**
     * 酒店列表适配器
     */
    private BaseQuickAdapter<HotelEntity.DatasBean, BaseViewHolder> mAdapter;
    /**
     * 酒店列表数据
     */
    private List<HotelEntity.DatasBean> mDataList = new ArrayList<>();

    /**
     * 当前加载的页面
     */
    int currentPage = 1;
    /**
     * 默认页面大小
     */
    int pageSize = 10;
    /**
     * 多选条件列表数据
     */
    List<SelectEntity.DataBean> thirdList = new ArrayList<>();

    /**
     * 定位
     */
    AMapLocation mapLocation;
    /**
     * 填充器
     */
    LayoutInflater inflater;
    /**
     * 排序弹窗
     */
    PopupWindow order;
    /**
     * 排序列表类型名称
     */
    List orderType = Arrays.asList("智能排序", "推荐优先", "热度优先", "好评优先", "距离优先");
    /**
     * 排序列表对应类型
     */
    List orderTypeValue = Arrays.asList("", "recommendedPriority", "heatPriority",
            "praisePriority", "distancePriority");
    /**
     * 排序类型postion
     */
    int orderedPostion = 0;
    /**
     * 排序列表view
     */
    View orderedView;
    /**
     * 弹窗布局
     */
    ConstraintLayout contentView;
    /**
     * 1级列表
     */
    RecyclerView recyclerView;
    /**
     * 条件选择弹窗adapter
     */
    BaseQuickAdapter selectorAdapter;
    /**
     * 公共LinearLayoutManager
     */
    LinearLayoutManager lm;
    /**
     * 条件选择弹窗
     */
    PopupWindow selector;
    /**
     * 条件选择弹窗1级数据名
     */
    List selectorType = new ArrayList();
    //    List selectorType = Arrays.asList("酒店星级", "酒店类型", "其他服务");
    /**
     * 条件选择弹窗1级数据对应类型
     */
    List selectorTypeValue = new ArrayList();
    //    List selectorTypeValue = Arrays.asList("resourceLevel", "grade", "specialService");
    /**
     * 条件选择弹窗2级数据
     */
    List<SelectEntity> secondLists = new ArrayList<>(4);
    /**
     * 条件选择弹窗2级数据
     */
    List<SelectEntity.DatasBean> secondList = new ArrayList<>();
    /**
     * 选中的条件
     */
    HashMap<String, String> selectMap = new HashMap(Constant.MAP_SIZE);
    /**
     * 选中的条件和对应的view
     */
    HashMap<String, View> serviceMap = new HashMap(Constant.MAP_SIZE);
    /**
     * 存储所有选中services
     */
    String services = "";
    /**
     * 条件选择弹窗1级选中条件key
     */
    String firstRule = "";
    /**
     * 被选中的view
     */
    View selectedView;
    /**
     * 选择类型postion
     */
    int selectedPostion = 0;
    /**
     * 条件选择item位置和view
     */
    List<HashMap<Integer, View>> secondSelectedViews = new ArrayList<>(4);
    /**
     * 条件选择1级recycleview
     */
    RecyclerView recyclerView1;
    /**
     * 条件选择2级recycleview
     */
    RecyclerView recyclerView2;
    /**
     * 条件选择1级adapter
     */
    BaseQuickAdapter firstAdapter;
    /**
     * 条件选择2级adapter
     */
    BaseQuickAdapter secondAdapter;
    /**
     * 条件选择多选adapter
     */
    BaseQuickAdapter thirdAdapter;
    /**
     * 地区编码
     */
    @Autowired(name = "region")
    String region = "";
    @Autowired(name = "grade")
    String grade = "";


    @Override
    public int getLayoutId() {
        return R.layout.activity_hotel;
    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        routeHelpTitle.setTitle("酒店列表");
        initSelectData();
        scrollManger();
        initRecycleView();
        initSearch();
        if(IApplication.getInstance().getIsAgreePrivate()){
            initLocation();
        }
        selectMap.put("page", currentPage + "");
        selectMap.put("grade", grade);
        selectMap.put("limitPage", URLConstant.LIMITPAGE + "");

        if (ObjectUtils.isNotEmpty(region)) {
            selectMap.put("region", region);
        }
        mAdapter.loadMoreComplete();
        presenter.getBannerData();
        swipeRefresh.autoRefresh();
        swipeRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                currentPage = 1;
                selectMap.put("page", currentPage + "");
                selectMap.put("longitude", SPUtils.getInstance().getString(SPCommon.LONGITUDE));
                selectMap.put("latitude", SPUtils.getInstance().getString(SPCommon.LATITUDE));
                presenter.getData(selectMap);
            }
        });
    }


    /**
     * 初始化定位
     */
    void initLocation() {
        HelpMaps.startLocation(new CompleteFuncData() {
            @Override
            public void success(AMapLocation loc) {
                if (ObjectUtils.isNotEmpty(loc)) {
                    mapLocation = loc;
                    location.setText(mapLocation.getAddress());
                }
            }
        });

        location.setText("未能获取当前位置,请开启定位");
    }

    /**
     * 按关键字搜索,搜索字段 name
     */
    void initSearch() {
        scenicSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    selectMap.put("name", textView.getText().toString());
                    currentPage = 1;
                    selectMap.put("page", currentPage + "");
                    swipeRefresh.autoRefresh();
                    KeyboardUtils.hideSoftInput(HotelActivity.this);
                }
                return false;
            }
        });
    }

    /**
     * 初始化Recyclerview
     */
    public void initRecycleView() {
        inflater = LayoutInflater.from(mContext);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayout.VERTICAL);
        recyclerview.setLayoutManager(lm);
        // 添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_line_divider));
        recyclerview.addItemDecoration(divider);

        mAdapter =
                new BaseQuickAdapter<HotelEntity.DatasBean, BaseViewHolder>(R.layout.item_hotel,
                        mDataList) {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    protected void convert(BaseViewHolder holder, HotelEntity.DatasBean datasBean) {
                        int width;
                        int height;
                        if (null != mapLocation) {
                            // 定位成功
                            width = (int) getResources().getDimension(R.dimen.dp_120);
                            height = (int) getResources().getDimension(R.dimen.dp_90);
//                            holder.setText(R.id.range, "距您直线" + datasBean.getDistance() + "KM");
                            if(TextUtils.isEmpty( datasBean.getDistance())){
                                holder.getView(R.id.range).setVisibility(View.GONE);
                            }else {
                                holder.getView(R.id.range).setVisibility(View.VISIBLE);
                                holder.setText(R.id.range, "距您直线" + datasBean.getDistance() + "KM");
                            }
                        } else {
                            // 定位失败
                            width = (int) getResources().getDimension(R.dimen.dp_94);
                            height = (int) getResources().getDimension(R.dimen.dp_70);
                            holder.getView(R.id.range).setVisibility(View.GONE);
                        }
                        holder.addOnClickListener(R.id.item);
                        try{
                            GlideApp.with(mContext).load(datasBean.getPictureOneToOne()).placeholder(R.mipmap.common_img_fail_h158).error(R.mipmap.common_img_fail_h158).override(width, height).apply(IApplication.requestOptions).into((RoundImageView) holder.getView(R.id.image));
                        }catch (Exception e){
                        }
                        if (null == datasBean.getPics() || datasBean.getPics().size() == 0) {
                            holder.getView(R.id.pics).setVisibility(View.GONE);
                        } else {
                            holder.getView(R.id.pics).setVisibility(View.VISIBLE);
                            ((TextView) holder.getView(R.id.pics)).setText(datasBean.getPics().size() + "");
                        }
                        holder.setText(R.id.name, datasBean.getName());
                        holder.setText(R.id.address, datasBean.getAddress());
                        Object level = hotelLevelFormat(datasBean.getLevel());
                        ((LinearLayout) holder.getView(R.id.lable_container)).removeAllViews();
                        LinearLayout.LayoutParams params =
                                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0, 0, SizeUtils.dp2px(4), 0);
                        if (ObjectUtils.isNotEmpty(level)) {
                            TextView tv = (TextView) inflater.inflate(R.layout.item_lable_yellow, null);
                            tv.setText("" + level.toString());
                            tv.setLayoutParams(params);
                            ((LinearLayout) holder.getView(R.id.lable_container)).addView(tv);
                            ((LinearLayout) holder.getView(R.id.lable_container)).invalidate();
                        }
                        // 标签存在则显示
                        if (null != datasBean.getTagNames() && datasBean.getTagNames().size() > 0) {
                            int sum = datasBean.getTagNames().size();
                            for (int i = 0; i < sum; i++) {
                                Object o = datasBean.getTagNames().get(i);
                                TextView tv = (TextView) inflater.inflate(R.layout.item_lable_yellow, null);
                                tv.setText("" + o.toString());
                                tv.setLayoutParams(params);
                                ((LinearLayout) holder.getView(R.id.lable_container)).addView(tv);
                                ((LinearLayout) holder.getView(R.id.lable_container)).invalidate();
                            }

                        }

                        if (ObjectUtils.isEmpty(level) && datasBean.getTagNames().size() == 0) {
                            // 标签为空时 保持占位
                            ((LinearLayout) holder.getView(R.id.lable_container)).removeAllViews();
                            holder.getView(R.id.lable_container).setVisibility(View.INVISIBLE);
                            holder.getView(R.id.scroll).setVisibility(View.INVISIBLE);
                        }


                        // 是否显示"荐"标签
                        holder.getView(R.id.recommend).

                                setVisibility(datasBean.getRecommend() == 1 ? View.VISIBLE : View.GONE);

                        // 2019-3-21 根据价格判断 是否显示"订"标签
                        if (ObjectUtils.isNotEmpty(datasBean.getCheap())) {
                            holder.getView(R.id.book).setVisibility(View.VISIBLE);
                        } else {
                            holder.getView(R.id.book).setVisibility(View.GONE);
                        }
                    }
                };
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                HotelEntity.DatasBean bean = (HotelEntity.DatasBean) adapter.getItem(position);
                ((ConstraintLayout) view).getViewById(R.id.image).getMeasuredWidth();
                ((ConstraintLayout) view).getViewById(R.id.image).getMeasuredHeight();
                ARouter.getInstance().build(Constant.ACTIVITY_HOTEL_DETAIL).withString("mId",
                        bean.getId()).navigation();
            }
        });
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                LogUtils.e("onLoadMoreRequested");
                currentPage++;
                selectMap.put("page", currentPage + "");
                selectMap.put("longitude", SPUtils.getInstance().getString(SPCommon.LONGITUDE));
                selectMap.put("latitude", SPUtils.getInstance().getString(SPCommon.LATITUDE));
                presenter.getData(selectMap);
            }
        }, recyclerView);
        recyclerview.setAdapter(mAdapter);
    }


    public void scrollManger() {
        LogUtils.v("getTotalScrollRange: " + appBarLayout.getTotalScrollRange());
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                LogUtils.v("verticalOffset: " + verticalOffset);
                if (appBarLayout.getTotalScrollRange() - SizeUtils.dp2px(6) < Math.abs(verticalOffset)) {
                    // TODO: 2019-3-20 滑动到顶部,操作定位视图
                    location.setVisibility(View.INVISIBLE);
                } else {
                    location.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    @Override
    public HotelPresenter initPresenter() {
        return new HotelPresenter(this);
    }

    @Override
    public void doBeforeSetContentView() {
        super.doBeforeSetContentView();
        //        StatusBarUtil.set(HotelActivity.this);
    }


    @Override
    public void showProgress() {
        LogUtils.e("showProgress");
        LoadingDialog.showDialogForLoading((Activity) mContext);
    }

    @Override
    public void hideProgress() {
        LogUtils.e("hideProgress");
        LoadingDialog.cancelDialogForLoading();
    }

    @Override
    public void initBanner(List<IndexBanner> list) {
        bannerView.setPages(new CBViewHolderCreator() {
            @Override
            public NetWorkImageHolderView createHolder(View itemView) {
                return new NetWorkImageHolderView(itemView, (Activity) mContext);
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_banner_img;
            }
        }, list)
                // 设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.bga_banner_point_disabled,
                        R.drawable.bga_banner_point_enabled,}).setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        }).setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        //      .setOnPageChangeListener(this)

        // 设置翻页监听
        //        convenientBanner.setManualPageable(false);

        // 设置不能手动影响
        if (list.size() == 1) {
            bannerView.stopTurning();
        }
    }

    @Override
    public void onDataRefresh(HotelEntity list) {
        swipeRefresh.finishRefresh();
        viewAnimator.setDisplayedChild(0);
        try {
            if (null != list && list.getCode() == 0 && ObjectUtils.isNotEmpty(list.getDatas()) && list.getDatas().size() > 0) {
                if (currentPage == 1) {
                    mAdapter.setNewData(list.getDatas());
                } else {
                    mAdapter.loadMoreComplete();
                    mAdapter.addData(list.getDatas());
                }
                if (list.getPage().getCurrPage() < list.getPage().getTotalPage()) {
                    mAdapter.setEnableLoadMore(true);
                } else {
                    mAdapter.loadMoreEnd();
                }
            } else {
                viewAnimator.setDisplayedChild(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            viewAnimator.setDisplayedChild(1);
        }
    }


    @OnClick({R.id.location, R.id.type_choose, R.id.smart_choose, R.id.banner_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.location:
                currentPage = 1;
                selectMap.put("page", currentPage + "");
                swipeRefresh.autoRefresh();
                break;
            case R.id.type_choose:
                initSelector();
                break;
            case R.id.smart_choose:
                initOrder();
                break;
            default:
        }
    }


    /**
     * 选择排序方式弹窗
     */
    void initOrder() {
        if (null == order) {
            // 创建新的pop
            contentView =
                    (ConstraintLayout) LayoutInflater.from(mContext).inflate(R.layout.pop_order,
                            null, false);
            order = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, true);
            order.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            order.setOutsideTouchable(false);
            showOrder();
            smartChoose.setSelected(true);
            order.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    smartChoose.setSelected(false);
                }
            });
            recyclerView = (RecyclerView) contentView.getViewById(R.id.recycler_view);

            lm = new LinearLayoutManager(this);
            lm.setOrientation(LinearLayout.VERTICAL);
            recyclerView.setLayoutManager(lm);
            // 添加自定义分割线
            DividerItemDecoration divider = new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL);
            divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_line_divider));
            recyclerView.addItemDecoration(divider);
            selectorAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_selector
                    , orderType) {
                @Override
                protected void convert(BaseViewHolder holder, String item) {
                    TextView tv = holder.getView(R.id.select_text);
                    tv.setText(item);
                    if (holder.getPosition() == 0) {
                        orderedView = holder.getView(R.id.select_text);
                        orderedView.setSelected(true);
                        orderedPostion = 0;
                    }
                    holder.addOnClickListener(R.id.select_text);
                }
            };
            recyclerView.setAdapter(selectorAdapter);
            selectorAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    if (orderedPostion != position) {
                        orderedPostion = position;
                        if (orderedView != null) {
                            orderedView.setSelected(false);
                        }
                        orderedView = view;
                        orderedView.setSelected(true);
                        // 按新的排序方式获取数据
                        selectMap.put("orderType", orderTypeValue.get(position) + "");
                        currentPage = 1;
                        selectMap.put("page", currentPage + "");
                        order.dismiss();
                        swipeRefresh.autoRefresh();
                    }
                }
            });

        } else if (order.isShowing()) {
            // 正在显示则消失
            smartChoose.setSelected(false);
            order.dismiss();
        } else {
            // 消失则显示
            smartChoose.setSelected(true);
            showOrder();
        }
    }


    private void initSelectData() {
        selectorType = Arrays.asList(getResources().getStringArray(R.array.hotel_selector_type));
        selectorTypeValue =
                Arrays.asList(getResources().getStringArray(R.array.hotel_selector_type_value));

        secondLists.add(0, new SelectEntity());
        secondLists.add(1, new SelectEntity());
        secondSelectedViews.add(0, new HashMap<Integer, View>());
        secondSelectedViews.add(1, new HashMap<Integer, View>());
        secondSelectedViews.add(2, new HashMap<Integer, View>());

        //        // 使用sp暂存的本地数据
        //        if (SPUtils.getInstance().getString(SPCommon.HOTEL_LEVEL, "").length() > 0 &&
        // SPUtils
        //                .getInstance().getString(SPCommon.HOTEL_TYPE, "").length() > 0 && SPUtils
        //                .getInstance().getString(SPCommon.HOTEL_SERVICE, "").length() > 0) {
        //            secondLists.set(0, GsonUtils.fromJson(SPUtils.getInstance().getString(SPCommon
        //                    .HOTEL_LEVEL, ""), SelectEntity.class));
        //            secondLists.set(1, GsonUtils.fromJson(SPUtils.getInstance().getString(SPCommon
        //                    .HOTEL_TYPE, ""), SelectEntity.class));
        //            initServiceData(SPUtils.getInstance().getString(SPCommon.HOTEL_SERVICE, ""));
        //        }

        RetrofitHelper.getApiService()
                .getHotelLevelList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .subscribe(new Consumer<SelectEntity>() {
                    @Override
                    public void accept(SelectEntity bean) throws Exception {
                        bean.getDatas().add(0, new SelectEntity.DatasBean("不限", ""));
                        secondLists.set(0, bean);
                        SPUtils.getInstance().put(SPCommon.HOTEL_LEVEL, GsonUtils.toJson(bean));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
        RetrofitHelper.getApiService()
                .getHotelTypeList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .subscribe(new Consumer<SelectEntity>() {
                    @Override
                    public void accept(SelectEntity bean) throws Exception {
                        bean.getDatas().add(0, new SelectEntity.DatasBean("不限", ""));
                        secondLists.set(1, bean);
                        SPUtils.getInstance().put(SPCommon.HOTEL_TYPE, GsonUtils.toJson(bean));

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
        RetrofitHelper.getApiService().getHotelService().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    String value = null;
                    try {
                        value = response.body().string();
                        SPUtils.getInstance().put(SPCommon.HOTEL_SERVICE, value);
                        initServiceData(value);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }

    /**
     * 处理 "其他服务" 数据
     *
     * @param response
     */
    void initServiceData(String response) {
        if (response == null || response.length() == 0) {
            return;
        }
        try {
            JSONObject obj = new JSONObject(response);
            JSONObject data = obj.getJSONObject("data");
            Iterator<String> datakeys = data.keys();
            while (datakeys.hasNext()) {
                String key = datakeys.next();
                JSONArray array = (JSONArray) data.get(key);
                List<SelectEntity.DatasBean> list = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    list.add(GsonUtils.fromJson(array.get(i).toString(),
                            SelectEntity.DatasBean.class));
                }
                SelectEntity.DataBean bean = new SelectEntity.DataBean();
                bean.setName(key);
                bean.setList(list);
                thirdList.add(bean);
            }
            LogUtils.e(thirdList.size() + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 条件选择弹框
     */
    void initSelector() {
        // 若所需数据为空 则不弹窗
        Iterator<SelectEntity> iterator = secondLists.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getDatas() == null || iterator.next().getDatas().size() == 0) {
                return;
            }
        }
        if (thirdList.size() == 0) {
            return;
        }
        // 初次弹窗初始化, 之后用显示隐藏控制
        if (null == selector) {
            ConstraintLayout contentView =
                    (ConstraintLayout) LayoutInflater.from(mContext).inflate(R.layout.pop_selector, null, false);
            selector = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, true);
            selector.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            selector.setOutsideTouchable(false);

            typeChoose.setSelected(true);
            selector.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    typeChoose.setSelected(false);
                }
            });
            showSelector();

            // 酒店页面 设置重置和完成点击
            ConstraintLayout group = (ConstraintLayout) contentView.getViewById(R.id.group);
            TextView reset = (TextView) group.getViewById(R.id.reset);
            TextView complete = (TextView) group.getViewById(R.id.complete);
            reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    services = "";
                    // TODO: 2019-3-27 取消选中状态
                    for (Map.Entry<String, View> entry : serviceMap.entrySet()) {
                        entry.getValue().setSelected(false);
                    }
                    serviceMap.clear();
                    for (SelectEntity.DataBean dataBean : thirdList) {
                        for (SelectEntity.DatasBean datasBean : dataBean.getList()) {
                            datasBean.setSelected(false);
                        }
                    }
                }
            });
            complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectMap.put("specialService", services);
                    currentPage = 1;
                    selectMap.put("page", currentPage + "");
                    selector.dismiss();
                    swipeRefresh.autoRefresh();
                }
            });
            recyclerView1 = (RecyclerView) contentView.getViewById(R.id.recycler_view1);
            recyclerView2 = (RecyclerView) contentView.getViewById(R.id.recycler_view2);

            LinearLayoutManager lm1 = new LinearLayoutManager(this);
            lm1.setOrientation(LinearLayout.VERTICAL);
            LinearLayoutManager lm2 = new LinearLayoutManager(this);
            lm2.setOrientation(LinearLayout.VERTICAL);
            LinearLayoutManager lm3 = new LinearLayoutManager(this);
            lm3.setOrientation(LinearLayout.VERTICAL);
            // 添加自定义分割线
            DividerItemDecoration divider = new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL);
            divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_line_divider));

            recyclerView1.setLayoutManager(lm1);
            recyclerView1.addItemDecoration(divider);
            recyclerView2.setLayoutManager(lm2);
            recyclerView2.addItemDecoration(divider);

            /**
             * 一级列表设置
             */

            firstAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_selector,
                    selectorType) {
                @Override
                protected void convert(BaseViewHolder holder, String item) {
                    TextView tv = holder.getView(R.id.select_text);
                    tv.setText(item);
                    if (holder.getPosition() == 0) {
                        selectedView = holder.getView(R.id.select_text);
                        selectedView.setSelected(true);
                        selectedPostion = 0;
                        firstRule = (String) selectorTypeValue.get(0);
                    }
                    holder.addOnClickListener(R.id.select_text);
                }
            };
            firstAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                    if (selectedPostion != position) {
                        selectedPostion = position;
                        if (selectedView != null) {
                            selectedView.setSelected(false);
                        }
                        selectedView = view;
                        selectedView.setSelected(true);
                        firstRule = (String) selectorTypeValue.get(position);
                        // 选到其他服务
                        if (position == 2) {
                            recyclerView2.setAdapter(thirdAdapter);
                        } else if (secondLists.size() >= selectedPostion && secondLists.get(selectedPostion) != null) {
                            recyclerView2.setAdapter(secondAdapter);
                            secondList.clear();
                            secondList.addAll(secondLists.get(selectedPostion).getDatas());
                            secondAdapter.notifyDataSetChanged();
                        }
                    }

                    LogUtils.e(position);
                    //                    ToastUtils.showShortCenter(((TextView) view).getText()
                    // + "" + position);
                }
            });
            recyclerView1.setAdapter(firstAdapter);
            /**
             * 二级列表设置
             */

            secondList.clear();
            secondList.addAll(secondLists.get(selectedPostion).getDatas());
            secondAdapter =
                    new BaseQuickAdapter<SelectEntity.DatasBean, BaseViewHolder>(R.layout.item_selector, secondList) {
                        @Override
                        protected void convert(BaseViewHolder holder, SelectEntity.DatasBean item) {
                            TextView tv = holder.getView(R.id.select_text);
                            holder.addOnClickListener(R.id.select_text);
                            tv.setText(item.getName());
                            tv.setSelected(false);
                            // 初始化第一个被选中
                            if (secondSelectedViews.get(selectedPostion).size() == 0 && holder.getPosition() == 0) {
                                tv.setSelected(true);
                                secondSelectedViews.get(selectedPostion).put(0,
                                        holder.getView(R.id.select_text));
                                LogUtils.e("secondAdapter selectedPostion:" + selectedPostion);
                            }
                            // 重新加载被选中
                            if (secondSelectedViews.get(selectedPostion).get(holder.getAdapterPosition()) != null) {
                                secondSelectedViews.get(selectedPostion).put(holder.getPosition(), tv);
                                tv.setSelected(true);
                                //                        LogUtils.e("重新加载被选中:" + holder.getPosition());
                            }
                        }
                    };
            secondAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    LogUtils.e("onItemChildClick position:" + position);

                    if (secondSelectedViews.get(selectedPostion).get(position) == null) {
                        for (Map.Entry<Integer, View> entry :
                                secondSelectedViews.get(selectedPostion).entrySet()) {
                            // 单选则移除 多选则不移除
                            LogUtils.e("isSelected before " + selectedPostion + " " + ((TextView) entry.getValue()).getText() + entry.getValue().isSelected());
                            entry.getValue().setSelected(false);
                            LogUtils.e("isSelected after  " + selectedPostion + " " + ((TextView) entry.getValue()).getText() + entry.getValue().isSelected());
                            secondSelectedViews.get(selectedPostion).clear();
                        }
                        view.setSelected(true);
                        LogUtils.e("put" + selectedPostion + " " + position + ((TextView) view).getText());

                        secondSelectedViews.get(selectedPostion).put(position, view);
                        // 单选就直接访问
                        selectMap.put(firstRule,
                                secondLists.get(selectedPostion).getDatas().get(position).getSkey());
                        currentPage = 1;
                        selectMap.put("page", currentPage + "");
                        swipeRefresh.autoRefresh();
                        selector.dismiss();
                    }
                }
            });
            recyclerView2.setAdapter(secondAdapter);
            /**
             * 服务列表设置
             */
            thirdAdapter =
                    new BaseQuickAdapter<SelectEntity.DataBean, BaseViewHolder>(R.layout.item_service, thirdList) {
                        @Override
                        protected void convert(BaseViewHolder holder, SelectEntity.DataBean item) {
                            TextView tv = holder.getView(R.id.name);
                            if (tv.getText().length() == 0) {
                                if (null != item.getList()) {
                                    LinearLayout.LayoutParams params =
                                            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, SizeUtils.dp2px(30));
                                    // 单位px,需求8px.
                                    params.setMargins(0, SizeUtils.dp2px(6), SizeUtils.dp2px(12),
                                            SizeUtils.dp2px(6));
                                    for (SelectEntity.DatasBean bean : item.getList()) {
                                        LinearLayout ll =
                                                (LinearLayout) inflater.inflate(R.layout.item_lable_grey,
                                                        null);
                                        TextView textView = ll.findViewById(R.id.lable);
                                        textView.setText("" + bean.getName());
                                        ll.setId(View.generateViewId());
                                        ll.setLayoutParams(params);
                                        if (bean.isSelected()) {
                                            ll.setSelected(bean.isSelected());
                                            serviceMap.put(bean.getSkey(), ll);
                                        }
                                        ((FlowLayout) holder.getView(R.id.lable_container)).addView(ll);
                                        holder.addOnClickListener(ll.getId());
                                    }
                                } else {
                                    // 标签为空时 保持占位
                                    holder.getView(R.id.lable_container).setVisibility(View.INVISIBLE);
                                }
                            }
                            tv.setText(item.getName());
                        }
                    };
            thirdAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    TextView tv = view.findViewById(R.id.lable);
                    String content = (String) tv.getText();
                    List<SelectEntity.DatasBean> list = thirdList.get(position).getList();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getName().equals(content)) {
                            list.get(i).getSkey();
                            LogUtils.e("onItemChildClick position:" + position);
                            serviceMap.put(list.get(i).getSkey(), view);
                            thirdList.get(position).getList().get(i).setSelected(true);
                            view.setSelected(true);
                            if (services.length() == 0) {
                                services += list.get(i).getSkey();
                            } else {
                                services += "," + list.get(i).getSkey();
                            }
                            return;
                        }
                    }
                }
            });

        } else if (selector.isShowing()) {
            // 正在显示则消失
            typeChoose.setSelected(false);
            selector.dismiss();
        } else {
            // 消失则显示
            typeChoose.setSelected(true);
            showSelector();
        }
    }

    public static String hotelLevelFormat(String level) {
        switch (level) {
            case "hotelStarLevel_5":
                return "★★★★★";
            case "hotelStarLevel_4":
                return "★★★★";
            case "hotelStarLevel_3":
                return "★★★";
            case "hotelStarLevel_2":
                return "★★";
            case "hotelStarLevel_1":
                return "★";
            case "hotelStarLevel_00":
                return "";
            default:
                return "";
        }
    }

    public void showSelector() {
        appBarLayout.setExpanded(false, false);
        Rect rect1 = new Rect();
        routeHelpTitle.getGlobalVisibleRect(rect1);
        int y = (int) (rect1.bottom + getResources().getDimension(R.dimen.dp_92));
        int x = 0;
        int h = (int) (routeHelpTitle.getResources().getDisplayMetrics().heightPixels - y - getResources().getDimension(R.dimen.dp_92));
        selector.setHeight(h);
        selector.showAtLocation(routeHelpTitle.getRootView(), Gravity.NO_GRAVITY, x, y);
    }

    public void showOrder() {
        appBarLayout.setExpanded(false, false);
        Rect rect1 = new Rect();
        routeHelpTitle.getGlobalVisibleRect(rect1);
        int y = (int) (rect1.bottom + getResources().getDimension(R.dimen.dp_92));
        int x = 0;
        order.showAtLocation(routeHelpTitle.getRootView(), Gravity.NO_GRAVITY, x, y);
    }
}

