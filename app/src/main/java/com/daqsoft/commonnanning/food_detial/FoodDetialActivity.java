package com.daqsoft.commonnanning.food_detial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.location.AMapLocation;
import com.bumptech.glide.request.RequestOptions;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.base.IApplication;
import com.daqsoft.commonnanning.common.ComUtils;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.SourceType;
import com.daqsoft.commonnanning.helps_gdmap.CompleteFuncData;
import com.daqsoft.commonnanning.helps_gdmap.HelpMaps;
import com.daqsoft.commonnanning.helps_gdmap.MapNaviUtils;
import com.daqsoft.commonnanning.helps_gdmap.MapUtils;
import com.daqsoft.commonnanning.ui.entity.CommentEntity;
import com.daqsoft.commonnanning.ui.entity.FoodDetialEntity;
import com.daqsoft.commonnanning.utils.NetWorkUtils;
import com.daqsoft.utils.Utils;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.glide.GlideApp;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.refresh.SmartRefreshLayout;
import com.example.tomasyb.baselib.refresh.api.RefreshLayout;
import com.example.tomasyb.baselib.refresh.listener.OnRefreshListener;
import com.example.tomasyb.baselib.util.LogUtils;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.web.ProgressWebView;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.agora.yview.photoview.PicturePreviewActivity;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * -----------------------------------------------------------------
 *
 * @version 1.0.1
 * @Description:美食详情页面
 * @Author 董彧傑
 * @CreateDate 2019-3-29 15:56
 * @since jdk1.8.0_201
 * -----------------------------------------------------------------
 */
@Route(path = Constant.ACTIVITY_FOOD_DETIAL)
public class FoodDetialActivity extends BaseActivity<FoodDetialContact.presenter> implements FoodDetialContact.view {
    @BindView(R.id.route_help_title)
    HeadView routeHelpTitle;
    @BindView(R.id.recycler_view1)
    RecyclerView recyclerView1;
    @BindView(R.id.recycler_view2)
    RecyclerView recyclerView2;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.more_restaurant)
    TextView moreRestaurant;
    @BindView(R.id.more_comment)
    TextView moreComment;
    @BindView(R.id.like)
    TextView like;
    @BindView(R.id.comment)
    TextView comment;
    @BindView(R.id.collection)
    TextView collection;
    @BindView(R.id.restaurant_title)
    TextView restaurantTitle;
    @BindView(R.id.restaurant_line)
    View restaurantLine;
    /**
     * 默认展示餐馆和评论条数
     */
    int defaultSize = 3;
    @BindView(R.id.comment_title)
    TextView commentTitle;
    @BindView(R.id.no_comment)
    ConstraintLayout noComment;
    @BindView(R.id.pics)
    TextView pics;
    @BindView(R.id.mSmartRefreshLayout)
    SmartRefreshLayout mSmartRefreshlayout;


    /**
     * 食物详情数据
     */
    private FoodDetialEntity.DataBean detial;
    /**
     * 定位
     */
    AMapLocation mapLocation;
    /**
     * 食物资源id
     */
    String id = "";
    /**
     * 餐馆list的adapter
     */
    BaseQuickAdapter restAdapter;
    /**
     * 评论list的adapter
     */
    BaseQuickAdapter commAdapter;
    /**
     * 餐馆list的完整数据
     */
    List<FoodDetialEntity.DataBean.RelationDiningBean> restaurantList = new ArrayList<>();
    /**
     * 餐馆list的展示数据
     */
    List<FoodDetialEntity.DataBean.RelationDiningBean> showRestaurantList = new ArrayList<>();
    /**
     * 评论list的完整数据
     */
    List<CommentEntity.DatasBean> commentList = new ArrayList<>();
    /**
     * 评论list的展示数据
     */
    List<CommentEntity.DatasBean> showCommentList = new ArrayList<>();
    @BindView(R.id.webView)
    ProgressWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        if(IApplication.getInstance().getIsAgreePrivate()){
            initLocation();
        }
        id = getIntent().getStringExtra("ID");
        initData();
    }

    @Override
    public void initView() {
        routeHelpTitle.setTitle("美食详情");
        mSmartRefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                restaurantList.clear();
                showRestaurantList.clear();
                commentList.clear();
                showCommentList.clear();
                initData();
                mSmartRefreshlayout.finishRefresh(500);
            }
        });
    }


    void initData() {
        presenter.getData(id);
    }

    public void initDetial() {
        if (null != detial) {
            GlideApp.with(mContext).load(detial.getCover())
                    .placeholder(R.mipmap.common_ba_banner)
                    .error(R.mipmap.common_ba_banner)
                    .centerCrop()
                    .into(image);
            name.setText(detial.getName());
            webView.loadData(ComUtils.getNewContent(detial.getContent()),
                    ComUtils.WEBVIEW_TYPE, null);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2019-4-2 传图片列表
//                    detial.getPics()
                }
            });
            if (detial.getPics().size() == 0) {
                pics.setVisibility(View.GONE);
            } else {
                pics.setVisibility(View.VISIBLE);
                pics.setText(detial.getPics().size() + "张");
            }
            // 设置收藏选中状态
            if (detial.getVo().getEnshrine() == 1) {
                collection.setSelected(true);
            } else if (detial.getVo().getEnshrine() == 0) {
                collection.setSelected(false);
            }
            // 设置点赞选中状态
            if (detial.getVo().getThumb() == 1) {
                like.setSelected(true);
            } else if (detial.getVo().getThumb() == 0) {
                like.setSelected(false);
            }
            if (!commentTitle.getText().equals("暂无评价")) {
                commentTitle.setText("来自" + detial.getCommentNum() + "位游客点评");
            }
            moreComment.setText("查看全部" + detial.getCommentNum() + "条评价信息");
        }
    }


    public void initRestRecycleView() {
        if (restaurantList.size() == 0) {
            restaurantLine.setVisibility(View.GONE);
            restaurantTitle.setVisibility(View.GONE);
            moreRestaurant.setVisibility(View.GONE);
        } else if (restaurantList.size() <= defaultSize) {
            restaurantLine.setVisibility(View.VISIBLE);
            restaurantTitle.setVisibility(View.VISIBLE);
            moreRestaurant.setVisibility(View.VISIBLE);
            showRestaurantList.addAll(restaurantList);
        } else {
            restaurantLine.setVisibility(View.VISIBLE);
            restaurantTitle.setVisibility(View.VISIBLE);
            moreRestaurant.setVisibility(View.VISIBLE);
            for (int i = 0; i < defaultSize; i++) {
                FoodDetialEntity.DataBean.RelationDiningBean bean = restaurantList.get(i);
                showRestaurantList.add(bean);
            }
        }
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayout.VERTICAL);
        recyclerView1.setLayoutManager(lm);
        // 添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_line_divider));
        recyclerView1.addItemDecoration(divider);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setNestedScrollingEnabled(false);
        restAdapter = new BaseQuickAdapter<FoodDetialEntity.DataBean.RelationDiningBean, BaseViewHolder>(R.layout.item_restaurant, showRestaurantList) {
            @Override
            protected void convert(BaseViewHolder holder, FoodDetialEntity.DataBean.RelationDiningBean datasBean) {

                GlideApp.with(mContext).load(datasBean.getLogo())
                        .placeholder(R.mipmap.common_img_fail_h158)
                        .error(R.mipmap.common_img_fail_h158)
                        .centerCrop()
                        .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(10, 0)))
                        .into((ImageView) holder.getView(R.id.image));
                holder.setText(R.id.name, datasBean.getName());
                holder.setText(R.id.address, datasBean.getAddress());
                if (null != mapLocation) {
                    // 定位成功
                    String distance = MapUtils.calculateLineDistance(datasBean.getLatitude(), datasBean.getLongitude(), "" + mapLocation.getLatitude(), "" + mapLocation.getLongitude());
                    holder.setText(R.id.range, "距您直线" + distance);
                } else {
                    // 定位失败
                    holder.setText(R.id.range, "定位失败");
                }
                holder.addOnClickListener(R.id.phone);
                holder.addOnClickListener(R.id.guide);

            }
        };
        restAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (((TextView) view).getText().equals("电话")) {
                    String phone = detial.getRelationDining().get(position).getPhone();
                    ComUtils.showQueryCancleDialogPhone(mContext, phone);
//                    ToastUtils.showShortCenter(phone);
                } else if (((TextView) view).getText().equals("导航")) {
                    // 导航
                    if (ObjectUtils.isNotEmpty(mapLocation)
                            && ObjectUtils.isNotEmpty(detial.getRelationDining().get(position).getLatitude())
                            && ObjectUtils.isNotEmpty(detial.getRelationDining().get(position).getLongitude())) {
                        MapNaviUtils.isMapNaviUtils((Activity) mContext,
                                mapLocation.getLatitude() + "",
                                mapLocation.getLongitude() + "",
                                mapLocation.getAddress(),
                                detial.getRelationDining().get(position).getLatitude(),
                                detial.getRelationDining().get(position).getLongitude(),
                                detial.getRelationDining().get(position).getAddress());
                    } else {
                        ToastUtils.showShortCenter(getResources().getString(R.string.no_map_navi));
                    }
                    MapNaviUtils.isMapNaviUtils((Activity) mContext,
                            mapLocation.getLatitude() + "",
                            mapLocation.getLongitude() + "",
                            mapLocation.getAddress(),
                            detial.getRelationDining().get(position).getLatitude(),
                            detial.getRelationDining().get(position).getLongitude(),
                            detial.getRelationDining().get(position).getAddress());

                }
            }
        });

        recyclerView1.setAdapter(restAdapter);
    }


    public void initCommentRecycleView() {
        if (commentList.size() == 0) {
            commentTitle.setText("暂无评价");
            moreComment.setVisibility(View.GONE);
            noComment.setVisibility(View.VISIBLE);
        } else if (commentList.size() > defaultSize) {
            moreComment.setVisibility(View.VISIBLE);
            for (int i = 0; i < defaultSize; i++) {
                CommentEntity.DatasBean bean = commentList.get(i);
                showCommentList.add(bean);
            }
        } else {
            showCommentList.addAll(commentList);
            moreComment.setVisibility(View.VISIBLE);
            // moreComment.setVisibility(View.GONE);
        }

        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayout.VERTICAL);
        recyclerView2.setLayoutManager(lm);
        recyclerView2.setNestedScrollingEnabled(false);

        commAdapter = new BaseQuickAdapter<CommentEntity.DatasBean, BaseViewHolder>(R.layout.item_comment, showCommentList) {
            @Override
            protected void convert(BaseViewHolder holder, CommentEntity.DatasBean datasBean) {

                GlideApp.with(mContext).load(datasBean.getHeadPath())
                        .placeholder(R.mipmap.common_img_fail_h158)
                        .error(R.mipmap.common_img_fail_h158)
                        .centerCrop()
                        .into((ImageView) holder.getView(R.id.iv_head_portrait));
                // 评论item载入数据
                holder.setText(R.id.tv_comment_user_name, datasBean.getName());
                RatingBar ratingbar = holder.getView(R.id.ratingbar);
                ratingbar.setRating(datasBean.getStar());
                holder.setText(R.id.tv_comment, datasBean.getComment());
                holder.setText(R.id.tv_comment_time, datasBean.getTime());
                final ArrayList<String> strings = new ArrayList<>();
                if (Utils.isnotNull(datasBean.getPics())) {
                    String[] split = datasBean.getPics().split("[,]");
                    if (!split[0].equals("")) {
                        strings.addAll(Arrays.asList(split));
                    }
                }
                if (strings.size() > 0) {
                    LogUtils.e("datasBean.getPics(): " + strings.size());
                    BaseQuickAdapter<String, BaseViewHolder> imageAdapter =
                            new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_resource_imgae, strings) {
                                @Override
                                protected void convert(final BaseViewHolder helper, String item) {
                                    GlideApp.with(mContext).load(item)
                                            .placeholder(R.mipmap.common_img_fail_h158)
                                            .error(R.mipmap.common_img_fail_h158)
                                            .centerCrop()
                                            .into((ImageView) helper.getView(R.id.iv_item_resource_img));
                                }
                            };
                    RecyclerView recyclerView = holder.getView(R.id.recyclerView_item_comment_img);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(imageAdapter);
                }
            }
        };
        commAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });

        recyclerView2.setAdapter(commAdapter);
    }


    @Override
    public void onDataRefresh(FoodDetialEntity.DataBean bean) {
        try {
            if (null != bean) {
                LogUtils.e("onDataRefresh: " + bean.getName());
                detial = bean;
                initDetial();
                restaurantList = detial.getRelationDining();
                initRestRecycleView();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadCommentSuccess(List<CommentEntity.DatasBean> bean) {
        if (null != bean) {
            commentList = bean;
            showCommentList.clear();
            initCommentRecycleView();
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_food_detial;
    }


    @Override
    public FoodDetialPresenter initPresenter() {
        return new FoodDetialPresenter(this);
    }

    /**
     * 初始化定位
     */
    void initLocation() {
        HelpMaps.startLocation(new CompleteFuncData() {
            @Override
            public void success(AMapLocation loc) {
                LogUtils.e("Location hideProgress");
                LoadingDialog.cancelDialogForLoading();
                if (ObjectUtils.isNotEmpty(loc)) {
                    mapLocation = loc;
                }
            }
        });

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
    public void goToLoginActivity() {
        ToastUtils.showShortCenter("请先登录");
        ARouter.getInstance().build(Constant.ACTIVITY_LOGIN).navigation(this, 1);
    }

    @OnClick({R.id.pics, R.id.image, R.id.more_restaurant, R.id.more_comment, R.id.like, R.id.comment, R.id.collection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image:
                break;
            case R.id.more_restaurant:
                if (null == detial) {
                    return;
                }
                moreRestaurant.setVisibility(View.GONE);
                showRestaurantList.clear();
                showRestaurantList.addAll(restaurantList);
                restAdapter.notifyDataSetChanged();
                break;
            case R.id.more_comment:
                if (null == detial) {
                    return;
                }
                if (Utils.isnotNull(SPUtils.getInstance().getString("token"))) {
                    ARouter.getInstance().build(Constant.ACTIVITY_COMMENTMORE)
                            .withString("name", detial.getName())
                            .withString("id", "" + id)
                            .withString("type", SourceType.RESOURCE_FOOD)
                            .withString("TYPE", "0")
                            .navigation();
                } else {
                    goToLoginActivity();
                }
                break;
            case R.id.like:
                if (Utils.isnotNull(SPUtils.getInstance().getString("token"))) {
                    // 点赞
                    if (like.isSelected()) {
                        NetWorkUtils.deleteThumb(id + "", like, (Activity) mContext);
                    } else {
                        NetWorkUtils.saveThumb(id + "", SourceType.FOOD_SOURCE_TYPE, detial.getName(), "", like, (Activity) mContext);
                    }
                } else {
                    goToLoginActivity();
                }
                break;
            case R.id.collection:
                if (Utils.isnotNull(SPUtils.getInstance().getString("token"))) {
                    // 收藏
                    if (collection.isSelected()) {
                        NetWorkUtils.delCollection(id + "", collection, (Activity) mContext);
                    } else {
                        NetWorkUtils.saveCollection(id + "", SourceType.FOOD_SOURCE_TYPE, detial.getName(), detial.getSummary(), collection, (Activity) mContext);
                    }
                } else {
                    goToLoginActivity();
                }
                break;
            case R.id.comment:
                // 评论
                if (Utils.isnotNull(SPUtils.getInstance().getString("token"))) {
                    ARouter.getInstance().build(Constant.ACTIVITY_WRITECOMMENT)
                            .withString("name", detial.getName())
                            .withString("id", "" + id)
                            .withString("type", SourceType.RESOURCE_FOOD)
                            .withString("TYPE", "0")
                            .navigation(this, 100);
                } else {
                    goToLoginActivity();
                }
                break;
            case R.id.pics:
                // 查看图片
                Intent intent = new Intent(this, PicturePreviewActivity.class);
                ArrayList<String> img = new ArrayList<String>();
                for (FoodDetialEntity.DataBean.PicBean pic : detial.getPics()) {
                    img.add(pic.getImgPath());
                }
                intent.putExtra("imgList", img);
                intent.putExtra("currentPosition", 0);
                startActivity(intent);
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 0) {
            presenter.getComment(id);
        }
    }
}
