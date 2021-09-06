package com.daqsoft.commonnanning.scenic;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
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
import com.daqsoft.commonnanning.helps_gdmap.CompleteFuncData;
import com.daqsoft.commonnanning.helps_gdmap.HelpMaps;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.IndexBanner;
import com.daqsoft.commonnanning.ui.entity.ScenicEntity;
import com.daqsoft.commonnanning.ui.entity.ScenicSeceledEnty;
import com.daqsoft.commonnanning.ui.entity.SelectEntity;
import com.daqsoft.commonnanning.ui.holder.NetWorkImageHolderView;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.glide.GlideApp;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.refresh.SmartRefreshLayout;
import com.example.tomasyb.baselib.refresh.api.RefreshLayout;
import com.example.tomasyb.baselib.refresh.listener.OnRefreshListener;
import com.example.tomasyb.baselib.util.KeyboardUtils;
import com.example.tomasyb.baselib.util.LogUtils;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.SizeUtils;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.LoadingDialog;
import com.example.tomasyb.baselib.widget.img.RoundImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

/**
 * -----------------------------------------------------------------
 *
 * @version 1.0.1
 * @Description: 景区界面
 * @Author 董彧傑
 * @CreateDate 2019-3-23 9:33
 * @since jdk1.8.0_201
 * -----------------------------------------------------------------
 */


@Route(path = Constant.ACTIVITY_SCENIC)
public class ScenicActivity extends BaseActivity<ScenicContact.presenter> implements
        ScenicContact.view {
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

    /**
     * 当前加载的页面
     */
    int currentPage = 1;
    /**
     * 默认页面大小
     */
    int pageSize = 10;


    /**
     * 定位
     */
    AMapLocation mapLocation;
    /**
     * 填充器
     */
    LayoutInflater inflater;
    @BindView(R.id.swipe_refresh)
    SmartRefreshLayout swipeRefresh;
    /**
     * 景区列表数据
     */
    private List<ScenicEntity.DatasBean> mDataList = new ArrayList<>();
    /**
     * 景区列表adapter
     */
    private BaseQuickAdapter<ScenicEntity.DatasBean, BaseViewHolder> mAdapter;
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
     * 条件选择弹窗
     */
    PopupWindow selector;
    /**
     * 条件选择弹窗1级数据名
     */
    List selectorType = new ArrayList();

    /**
     * 条件选择弹窗1级数据对应类型
     */
    List selectorTypeValue = new ArrayList();

    /**
     * 条件选择弹窗2级数据
     */
    List<SelectEntity> secondLists = new ArrayList<>();
    /**
     * 条件选择弹窗2级数据
     */
    List<SelectEntity.DatasBean> secondList = new ArrayList<>();
    /**
     * 选中的条件
     */
    HashMap selectMap = new HashMap(Constant.MAP_SIZE);
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
    List<HashMap<Integer, View>> secondSelectedViews = new ArrayList<>();
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
     * 地区编码
     */
    @Autowired(name = "region")
    String region = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_hotel;
    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        routeHelpTitle.setTitle("景区");
        selectMap.put("page", currentPage + "");
        selectMap.put("limitPage", pageSize);
        if (ObjectUtils.isNotEmpty(region)) {
            selectMap.put("region", region);
        }
        getScenicTypeData();
        scrollManger();
        initRecycleView();
        initSearch();
        if(IApplication.getInstance().getIsAgreePrivate()){
            initLocation();
        }else {
            swipeRefresh.autoRefresh();
        }
        mAdapter.loadMoreComplete();
        presenter.getBannerData();
        swipeRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                currentPage = 1;
                selectMap.put("page", currentPage + "");
                if (ObjectUtils.isNotEmpty(mapLocation)) {
                    selectMap.put("lat", mapLocation.getLatitude() + "");
                    selectMap.put("lng", mapLocation.getLongitude() + "");
                }
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
                HelpMaps.stopLocation();
                swipeRefresh.autoRefresh();
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
                    KeyboardUtils.hideSoftInput(ScenicActivity.this);
                }
                return false;
            }
        });
    }

    /**
     * 列表适配器处理
     */
    public void initRecycleView() {
        inflater = LayoutInflater.from(mContext);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayout.VERTICAL);
        recyclerview.setLayoutManager(lm);
        // 添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration
                .VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_line_divider));


        mAdapter = new BaseQuickAdapter<ScenicEntity.DatasBean, BaseViewHolder>(R.layout
                .item_hotel, mDataList) {
            @Override
            protected void convert(BaseViewHolder holder, ScenicEntity.DatasBean datasBean) {
                // 距离
                int width;
                int height;
                if (null != mapLocation) {
                    // 定位成功
                    width = (int) getResources().getDimension(R.dimen.dp_120);
                    height = (int) getResources().getDimension(R.dimen.dp_90);
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
                try{
                    GlideApp.with(mContext).load(datasBean.getPictureOneToOne()).placeholder(R.mipmap
                            .common_img_fail_h158).error(R.mipmap.common_img_fail_h158).override
                            (width, height).apply(IApplication.requestOptions).into((RoundImageView)
                            holder.getView(R.id.image));
                }catch (Exception e){
                }

                holder.addOnClickListener(R.id.item);
                if (null == datasBean.getPics() || datasBean.getPics().size() == 0) {
                    holder.getView(R.id.pics).setVisibility(View.GONE);
                } else {
                    holder.getView(R.id.pics).setVisibility(View.VISIBLE);
                    ((TextView) holder.getView(R.id.pics)).setText(datasBean.getPics().size() + "");
                }

                holder.setText(R.id.name, datasBean.getName());
                holder.setText(R.id.address, datasBean.getAddress());
                ((LinearLayout) holder.getView(R.id.lable_container)).removeAllViews();
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout
                        .LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, SizeUtils.dp2px(2), SizeUtils.dp2px(8), SizeUtils.dp2px(2));
                if (ObjectUtils.isNotEmpty(datasBean.getLevelName())) {
                    TextView tv = (TextView) inflater.inflate(R.layout.item_lable_yellow, null);
                    tv.setText("" + datasBean.getLevelName());
                    tv.setLayoutParams(params);
                    ((LinearLayout) holder.getView(R.id.lable_container)).addView(tv);
                    ((LinearLayout) holder.getView(R.id.lable_container)).invalidate();
                }
                // 标签存在则显示
                if (null != datasBean.getTagNames()) {
                    for (String o : datasBean.getTagNames()) {
                        TextView tv = (TextView) inflater.inflate(R.layout.item_lable_yellow, null);
                        tv.setText("" + o.toString());
                        tv.setLayoutParams(params);
                        ((LinearLayout) holder.getView(R.id.lable_container)).addView(tv);
                        ((LinearLayout) holder.getView(R.id.lable_container)).invalidate();
                    }
                    // TODO: 2019-4-1

                } else {
                    // 标签为空时 保持占位
                    ((LinearLayout) holder.getView(R.id.lable_container)).removeAllViews();
                    holder.getView(R.id.lable_container).setVisibility(View.GONE);
                    holder.getView(R.id.scroll).setVisibility(View.GONE);
                }

                // 是否显示"荐"标签
                holder.getView(R.id.recommend).setVisibility(datasBean.getRecommend() == 1 ? View
                        .VISIBLE : View.GONE);
                try {
                    // 景区 "票" 标签
                    if (ObjectUtils.isNotEmpty(datasBean.getOnlineBooking())) {
                        holder.getView(R.id.book).setVisibility(View.VISIBLE);
                    } else {
                        holder.getView(R.id.book).setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    holder.getView(R.id.book).setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }
        };
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ScenicEntity.DatasBean bean = (ScenicEntity.DatasBean) adapter.getItem(position);
                ARouter.getInstance().build(Constant.ACTIVITY_SCENIC_DETAIL).withString("mId",
                        bean.getId()).navigation();
            }
        });
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                LogUtils.e("onLoadMoreRequested");
                currentPage++;
                selectMap.put("page", currentPage + "");
                presenter.getData(selectMap);
            }
        }, recyclerview);
        recyclerview.setAdapter(mAdapter);
    }


    public void scrollManger() {
        LogUtils.v("getTotalScrollRange: " + appBarLayout.getTotalScrollRange());
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                LogUtils.v("verticalOffset: " + verticalOffset);
                if (appBarLayout.getTotalScrollRange() - SizeUtils.dp2px(6) < Math.abs
                        (verticalOffset)) {
                    // TODO: 2019-3-20 滑动到顶部,操作定位视图
                    location.setVisibility(View.INVISIBLE);
                } else {
                    location.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    @Override
    public ScenicPresenter initPresenter() {
        return new ScenicPresenter(this);
    }

    @Override
    public void doBeforeSetContentView() {
        super.doBeforeSetContentView();
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
                .setPageIndicator(new int[]{R.drawable.bga_banner_point_disabled, R.drawable
                        .bga_banner_point_enabled,}).setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        }).setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        if (list.size() == 1) {
            bannerView.stopTurning();
        }
    }


    @Override
    public void onDataRefresh(ScenicEntity list) {
        swipeRefresh.finishRefresh();
        viewAnimator.setDisplayedChild(0);
        try {
            if (null != list && list.getCode() == 0 && ObjectUtils.isNotEmpty(list.getDatas()) &&
                    list.getDatas().size() > 0) {
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
                initLocation();
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


    void initOrder() {
        if (null == order) {
            // 创建新的pop
            ConstraintLayout contentView = (ConstraintLayout) LayoutInflater.from(mContext)
                    .inflate(R.layout.pop_order, null, false);
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
            RecyclerView recyclerView = (RecyclerView) contentView.getViewById(R.id.recycler_view);

            LinearLayoutManager lm = new LinearLayoutManager(this);
            lm.setOrientation(LinearLayout.VERTICAL);
            recyclerView.setLayoutManager(lm);
            // 添加自定义分割线
            DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration
                    .VERTICAL);
            divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_line_divider));
            recyclerView.addItemDecoration(divider);
            BaseQuickAdapter selectorAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R
                    .layout.item_selector, orderType) {
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
            selectorAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                if (orderedPostion != position) {
                    orderedPostion = position;
                    if (orderedView != null) {
                        orderedView.setSelected(false);
                    }
                    orderedView = view;
                    orderedView.setSelected(true);
                    // 按新的排序方式获取数据
                    selectMap.put("orderType", orderTypeValue.get(position));
                    order.dismiss();
                    currentPage = 1;
                    selectMap.put("page", currentPage + "");
                    swipeRefresh.autoRefresh();
                }
                LogUtils.e(position);
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


    /**
     * 获取景区列表接口
     */
    private void getScenicTypeData() {
        selectorType.clear();
        selectorTypeValue.clear();
        secondLists.clear();
        secondSelectedViews.clear();
        RetrofitHelper.getApiService().getScenerySceletedType().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer<Disposable>
                () {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).subscribe(new Consumer<BaseResponse<ScenicSeceledEnty>>() {
            @Override
            public void accept(BaseResponse<ScenicSeceledEnty> bean) throws Exception {
                if (bean.getCode() == 0) {
                    if (bean.getData().getMatterCrowd().size() > 0) {
                        selectorType.add("适合人群");
                        selectorTypeValue.add("matterCrowd");
                        SelectEntity selectEntity = new SelectEntity();
                        List<SelectEntity.DatasBean> list = new ArrayList<>();
                        SelectEntity.DatasBean beannull = new SelectEntity.DatasBean("不限", "");
                        list.add(beannull);
                        for (int i = 0; i < bean.getData().getMatterCrowd().size(); i++) {
                            SelectEntity.DatasBean bean1 = new SelectEntity.DatasBean(bean
                                    .getData().getMatterCrowd().get(i).getSkeyName(), bean
                                    .getData().getMatterCrowd().get(i).getSkey());
                            list.add(bean1);
                        }
                        selectEntity.setDatas(list);
                        secondLists.add(selectEntity);
                        secondSelectedViews.add(new HashMap<Integer, View>());
                    }
                    if (bean.getData().getResourceLevel().size() > 0) {
                        selectorType.add("景区等级");
                        selectorTypeValue.add("resourceLevel");
                        SelectEntity selectEntity = new SelectEntity();
                        List<SelectEntity.DatasBean> list = new ArrayList<>();
                        SelectEntity.DatasBean beannull = new SelectEntity.DatasBean("不限", "");
                        list.add(beannull);
                        for (int i = 0; i < bean.getData().getResourceLevel().size(); i++) {
                            SelectEntity.DatasBean bean1 = new SelectEntity.DatasBean(bean
                                    .getData().getResourceLevel().get(i).getSkeyName(), bean
                                    .getData().getResourceLevel().get(i).getSkey());
                            list.add(bean1);
                        }
                        selectEntity.setDatas(list);
                        secondLists.add(selectEntity);
                        secondSelectedViews.add(new HashMap<Integer, View>());
                    }
                    if (bean.getData().getScenicTheme().size() > 0) {
                        selectorType.add("景区主题");
                        selectorTypeValue.add("scenicTheme");
                        SelectEntity selectEntity = new SelectEntity();
                        List<SelectEntity.DatasBean> list = new ArrayList<>();
                        SelectEntity.DatasBean beannull = new SelectEntity.DatasBean("不限", "");
                        list.add(beannull);
                        for (int i = 0; i < bean.getData().getScenicTheme().size(); i++) {
                            SelectEntity.DatasBean bean1 = new SelectEntity.DatasBean(bean
                                    .getData().getScenicTheme().get(i).getSkeyName(), bean
                                    .getData().getScenicTheme().get(i).getSkey());
                            list.add(bean1);
                        }
                        selectEntity.setDatas(list);
                        secondLists.add(selectEntity);
                        secondSelectedViews.add(new HashMap<Integer, View>());
                    }
                    if (bean.getData().getType().size() > 0) {
                        selectorType.add("景区类型");
                        selectorTypeValue.add("resourceType");
                        SelectEntity selectEntity = new SelectEntity();
                        List<SelectEntity.DatasBean> list = new ArrayList<>();
                        SelectEntity.DatasBean beannull = new SelectEntity.DatasBean("不限", "");
                        list.add(beannull);
                        for (int i = 0; i < bean.getData().getType().size(); i++) {
                            SelectEntity.DatasBean bean1 = new SelectEntity.DatasBean(bean
                                    .getData().getType().get(i).getSkeyName(), bean.getData()
                                    .getType().get(i).getSkey());
                            list.add(bean1);
                        }
                        selectEntity.setDatas(list);
                        secondLists.add(selectEntity);
                        secondSelectedViews.add(new HashMap<Integer, View>());
                    }

                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }


    /**
     * 条件选择弹框
     */
    void initSelector() {
        // 若所需数据为空 则不弹窗
        if (secondLists.size() == 0) {
            return;
        }
        // 初次弹窗初始化, 之后用显示隐藏控制
        if (null == selector) {
            ConstraintLayout contentView = (ConstraintLayout) LayoutInflater.from(mContext)
                    .inflate(R.layout.pop_selector, null, false);
            selector = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, true);
            selector.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            selector.setOutsideTouchable(false);

            showSelector();
            typeChoose.setSelected(true);
            selector.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    typeChoose.setSelected(false);
                }
            });
            // 景区页面不需要多选,则隐藏
            ConstraintLayout group = (ConstraintLayout) contentView.getViewById(R.id.group);
            group.setVisibility(View.GONE);

            recyclerView1 = (RecyclerView) contentView.getViewById(R.id.recycler_view1);
            recyclerView2 = (RecyclerView) contentView.getViewById(R.id.recycler_view2);


            LinearLayoutManager lm1 = new LinearLayoutManager(this);
            lm1.setOrientation(LinearLayout.VERTICAL);
            LinearLayoutManager lm2 = new LinearLayoutManager(this);
            lm2.setOrientation(LinearLayout.VERTICAL);
            // 添加自定义分割线
            DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration
                    .VERTICAL);
            divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_line_divider));

            recyclerView1.setLayoutManager(lm1);
            recyclerView1.addItemDecoration(divider);
            recyclerView2.setLayoutManager(lm2);
            recyclerView2.addItemDecoration(divider);

            /**
             * 1级列表设置
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
            firstAdapter.setOnItemChildClickListener(new BaseQuickAdapter
                    .OnItemChildClickListener() {
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
                        if (secondLists.size() >= selectedPostion && secondLists.get
                                (selectedPostion) != null) {
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
             * 2级列表设置
             */

            secondList.clear();
            secondList.addAll(secondLists.get(selectedPostion).getDatas());
            secondAdapter = new BaseQuickAdapter<SelectEntity.DatasBean, BaseViewHolder>(R.layout
                    .item_selector, secondList) {
                @Override
                protected void convert(BaseViewHolder holder, SelectEntity.DatasBean item) {
                    TextView tv = holder.getView(R.id.select_text);
                    holder.addOnClickListener(R.id.select_text);
                    tv.setText(item.getName());
                    tv.setSelected(false);
                    // 初始化第1个被选中
                    if (secondSelectedViews.get(selectedPostion).size() == 0 && holder
                            .getPosition() == 0) {
                        tv.setSelected(true);
                        secondSelectedViews.get(selectedPostion).put(0, holder.getView(R.id
                                .select_text));
                        LogUtils.e("secondAdapter selectedPostion:" + selectedPostion);
                    }
                    // 重新加载被选中
                    if (secondSelectedViews.get(selectedPostion).get(holder.getAdapterPosition())
                            != null) {
                        secondSelectedViews.get(selectedPostion).put(holder.getPosition(), tv);
                        tv.setSelected(true);
                        LogUtils.e("重新加载被选中:" + holder.getPosition());
                    }
                }
            };
            secondAdapter.setOnItemChildClickListener(new BaseQuickAdapter
                    .OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    LogUtils.e("onItemChildClick position:" + position);

                    if (secondSelectedViews.get(selectedPostion).get(position) == null) {
                        for (Map.Entry<Integer, View> entry : secondSelectedViews.get
                                (selectedPostion).entrySet()) {
                            // 单选则移除 多选则不移除
                            LogUtils.e("isSelected before " + selectedPostion + " " + ((TextView)
                                    entry.getValue()).getText() + entry.getValue().isSelected());
                            ((TextView) entry.getValue()).setSelected(false);
                            LogUtils.e("isSelected after  " + selectedPostion + " " + ((TextView)
                                    entry.getValue()).getText() + entry.getValue().isSelected());
                            secondSelectedViews.get(selectedPostion).clear();
                        }
                        view.setSelected(true);
                        LogUtils.e("put" + selectedPostion + " " + position + ((TextView) view)
                                .getText());

                        secondSelectedViews.get(selectedPostion).put(position, view);
                        // 单选就直接访问
                        selectMap.put(firstRule, secondLists.get(selectedPostion).getDatas().get
                                (position).getSkey());
                        currentPage = 1;
                        selectMap.put("page", currentPage + "");

                        swipeRefresh.autoRefresh();
                        selector.dismiss();
                    }
                    LogUtils.e(position);
                }
            });
            recyclerView2.setAdapter(secondAdapter);

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

    public void showSelector() {
        appBarLayout.setExpanded(false, false);
        Rect rect1 = new Rect();
        routeHelpTitle.getGlobalVisibleRect(rect1);
        int y = (int) (rect1.bottom + getResources().getDimension(R.dimen.dp_92));
        int x = 0;
        int h = (int) (routeHelpTitle.getResources().getDisplayMetrics().heightPixels - y -
                getResources().getDimension(R.dimen.dp_100));
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

