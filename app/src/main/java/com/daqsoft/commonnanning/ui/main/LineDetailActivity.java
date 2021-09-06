package com.daqsoft.commonnanning.ui.main;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.android.ProjectConfig;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.SourceType;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.adapter.MultipleTraveAdapter;
import com.daqsoft.commonnanning.ui.entity.StrategyDetail;
import com.daqsoft.commonnanning.ui.entity.StraveXqEy;
import com.daqsoft.commonnanning.ui.mine.LoginActivity;
import com.daqsoft.commonnanning.utils.GlideUtils;
import com.daqsoft.commonnanning.utils.ShareUtils;
import com.daqsoft.commonnanning.view.CenterDrawableTextView;
import com.daqsoft.utils.Utils;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.util.LogUtils;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.agora.yview.dialog.BaseDialog;
import io.agora.yview.img.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 推荐线路详情
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-6-4.15:50
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_LINEDETAIL)
public class LineDetailActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.head)
    HeadView mHead;
    @Autowired(name = "mId")
    String strategyId;
    ImageView strategyImg;
    TextView strategyTitle;
    TextView strategyTime;
    TextView strategyGiveNum;
    TextView strategyCollectNum;
    TextView strategyCommentNum;
    CircleImageView strategyIcon;
    TextView strategyUserName;
    TextView strategyAddress;
    @BindView(R.id.strategy_details_content)
    RecyclerView mRv;
    @BindView(R.id.strategy_details_navigation)
    NavigationView navigationView;
    @BindView(R.id.strategy_details_comment_tv)
    CenterDrawableTextView strategyComment;
    @BindView(R.id.strategy_details_give_tv)
    CenterDrawableTextView strategyGive;
    @BindView(R.id.strategy_details_collect_tv)
    CenterDrawableTextView strategyCollect;
    @BindView(R.id.strategy_details_drawer)
    DrawerLayout drawerLayout;
    @BindView(R.id.strategy_details_option)
    ImageView mImgMenu;
    /**
     * 标题段落的数量
     */
    private int indexNum;
    /**
     * 攻略详情的数据
     */
    private StrategyDetail strategyDetail;

    private MultipleTraveAdapter mAdapters;
    private List<StraveXqEy> mDatas;
    /**
     * 分享
     */
    private BaseDialog mShareDialog = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_line_detail;
    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        mHead.setTitle("攻略详情");
        mHead.setBackListener(new HeadView.OnBackListener() {
            @Override
            public void onBack(View v) {
                finish();
            }
        });
        navigationView.setNavigationItemSelectedListener(this);
        @SuppressLint("ResourceType") ColorStateList csl =
                getResources().getColorStateList(R.drawable.selector_txt_color_main_gray);
        navigationView.setItemTextColor(csl);
        initAdapter();
        getData();
        mHead.setMenuBackGround(R.mipmap.share);
        if (ObjectUtils.isNotEmpty(ProjectConfig.QQ_APPID) && ObjectUtils.isNotEmpty(ProjectConfig.WECHAT_APPID)) {
            mHead.setMenuHidden(true);
        } else {
            mHead.setMenuHidden(false);
        }
        if(ProjectConfig.CITY_NAME.equals("西藏")) {
            mHead.setMenuHidden(false);
        }
        mHead.setMenuListener(new HeadView.OnMenuListener() {
            @Override
            public void onClickMenu(View v) {
                if (mShareDialog != null) {
                    mShareDialog.show();
                } else {
                    ToastUtils.showShort("数据不全无法分享!");
                }
            }
        });
    }

    private void initAdapter() {
        mDatas = new ArrayList<>();
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapters = new MultipleTraveAdapter(mDatas);
        mRv.setAdapter(mAdapters);
    }

    private void getData() {
        LoadingDialog.showDialogForLoading(this);
        RetrofitHelper.getApiService().getLineDetail(strategyId).subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BaseResponse<StrategyDetail>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void accept(BaseResponse<StrategyDetail> bean) throws Exception {
                LoadingDialog.cancelDialogForLoading();
                if (bean.getData() != null) {
                    strategyDetail = bean.getData();
                    if (ObjectUtils.isNotEmpty(strategyDetail.getOprationStatus())) {
                        if (strategyDetail != null) {
                            mShareDialog = ShareUtils.ininShareDialog(LineDetailActivity.this,
                                    ProjectConfig.SHARE_STRATEGY_URL, strategyDetail.getTitle(),
                                    strategyDetail.getId() + "", strategyDetail.getCover(),
                                    Html.fromHtml(strategyDetail.getContent(),
                                            Html.FROM_HTML_MODE_COMPACT).toString());
                        }
                        StrategyDetail.OprationStatus oprationStatus =
                                strategyDetail.getOprationStatus();
                        // 点赞
                        if (1 == oprationStatus.getThumb()) {
                            strategyGive.setSelected(true);
                        } else {
                            strategyGive.setSelected(false);
                        }
                        // 收藏
                        if (1 == oprationStatus.getEnshrine()) {
                            strategyCollect.setSelected(true);
                        } else {
                            strategyCollect.setSelected(false);
                        }
                    }
                    // 用户
                    if (strategyDetail.getSource() == 2) {
                        mImgMenu.setVisibility(View.VISIBLE);
                        // 添加侧滑菜单数据
                        addMenu();
                        for (int i = 0; i < strategyDetail.getContents().size(); i++) {
                            StraveXqEy xq = new StraveXqEy();
                            StrategyDetail.Content content = strategyDetail.getContents().get(i);
                            if (content.getType() == 1) {
                                xq.setContent(content.getContent());
                                xq.setItemType(StraveXqEy.CONTENTS);
                            } else if (content.getType() == 2) {
                                xq.setContent(content.getContent());
                                xq.setItemType(StraveXqEy.IMG);
                            } else if (content.getType() == 3) {
                                xq.setContent(content.getContent());
                                xq.setItemType(StraveXqEy.CONTENTSTITLE);
                            } else if (content.getType() == 4) {
                                xq.setContent(content.getContent());
                                xq.setItemType(StraveXqEy.TRAVETITLE);
                            }
                            mDatas.add(xq);
                        }
                        mAdapters.notifyDataSetChanged();
                    } else {
                        // 后台
                        mImgMenu.setVisibility(View.GONE);
                        StraveXqEy xq = new StraveXqEy();
                        xq.setContent(strategyDetail.getContent());
                        xq.setItemType(StraveXqEy.WEBCONTENT);
                        mDatas.add(xq);
                        mAdapters.notifyDataSetChanged();
                    }
                    addHeader();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                LoadingDialog.cancelDialogForLoading();
            }
        });
    }

    /**
     * 添加头布局
     */
    private void addHeader() {
        View view = getLayoutInflater().inflate(R.layout.item_strategy_head,
                (ViewGroup) mRv.getParent(), false);
        strategyImg = (ImageView) view.findViewById(R.id.strategy_details_img);
        strategyIcon = (CircleImageView) view.findViewById(R.id.strategy_details_icon);
        strategyTitle = (TextView) view.findViewById(R.id.strategy_details_title);
        strategyTime = (TextView) view.findViewById(R.id.strategy_details_time);
        strategyGiveNum = (TextView) view.findViewById(R.id.strategy_details_give);
        strategyAddress = (TextView) view.findViewById(R.id.strategy_details_address);
        strategyCollectNum = (TextView) view.findViewById(R.id.strategy_details_collect);
        strategyCommentNum = (TextView) view.findViewById(R.id.strategy_details_comment);
        strategyUserName = (TextView) view.findViewById(R.id.strategy_details_username);

        strategyUserName.setVisibility(View.VISIBLE);
        strategyIcon.setVisibility(View.VISIBLE);
        strategyTitle.setText(strategyDetail.getTitle());
        strategyTime.setText(strategyDetail.getCreateTime());
        strategyGiveNum.setText(strategyDetail.getGivepoint() + "点赞");
        strategyCollectNum.setText(strategyDetail.getCollection() + "收藏");
        strategyCommentNum.setText(strategyDetail.getComment() + "评论");
        strategyComment.setText(strategyDetail.getComment() + "条评论");
        GlideUtils.loadImage(mContext, strategyImg, strategyDetail.getCover(),
                R.mipmap.common_ba_banner);
        GlideUtils.loadImage(mContext, strategyIcon, strategyDetail.getHead(),
                R.mipmap.common_ba_banner);

        strategyAddress.setText(ObjectUtils.isNotEmpty(strategyDetail.getRegionName()) ?
                strategyDetail.getRegionName() : "暂无地址");
        strategyUserName.setText(Utils.isnotNull(strategyDetail.getNickname()) ?
                strategyDetail.getNickname() : "暂无名称");
        mAdapters.addHeaderView(view);
    }

    /**
     * 添加菜单侧滑栏
     */
    public void addMenu() {
        List<StrategyDetail.Content> contents = strategyDetail.getContents();
        navigationView.getMenu().clear();
        indexNum = 0;
        if (ObjectUtils.isNotEmpty(contents) && contents.size() > 0) {
            for (int i = 0; i < contents.size(); i++) {
                if (contents.get(i).getType() == 3) {
                    indexNum++;
                    String index = String.valueOf(indexNum).length() == 1 ? "0" + (indexNum) +
                            "/" : (indexNum) + "/";
                    navigationView.getMenu().add(i, i, i,
                            index + "     " + contents.get(i).getContent());
                }
            }
        }
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // 滑动到position  boundPosition的item移出界面后停止
        for (int i = 0; i < navigationView.getMenu().size(); i++) {

            navigationView.getMenu().getItem(i).setChecked(false);
        }
        item.setChecked(true);
        try {
            if (item.getItemId() < navigationView.getMenu().size()) {
                mRv.scrollToPosition(item.getItemId() + 1);
            } else {
                mRv.scrollToPosition(item.getItemId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 关闭侧滑
        drawerLayout.closeDrawers();
        return true;
    }

    @OnClick({R.id.strategy_details_give_tv, R.id.strategy_details_collect_tv,
            R.id.strategy_details_comment_tv, R.id.strategy_details_option})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.strategy_details_give_tv:
                if (Utils.isnotNull(SPUtils.getInstance().getString("token"))) {
                    // 点赞
                    // true 为选中，则取消点赞
                    if (strategyGive.isSelected()) {
                        deleteThumb(0);
                    } else {
                        saveThumb(0);
                    }
                } else {
                    ToastUtils.showShortCenter("请先登录");
                    Utils.goToOtherClass(this, LoginActivity.class);
                }
                break;
            case R.id.strategy_details_collect_tv:
                if (Utils.isnotNull(SPUtils.getInstance().getString("token"))) {
                    // 收藏
                    if (strategyCollect.isSelected()) {
                        delCollection();
                    } else {
                        saveCollection();
                    }
                } else {
                    ToastUtils.showShortCenter("请先登录");
                    Utils.goToOtherClass(this, LoginActivity.class);
                }

                break;
            case R.id.strategy_details_comment_tv:
                if (Utils.isnotNull(SPUtils.getInstance().getString("token"))) {
                    ARouter.getInstance().build(Constant.ACTIVITY_COMMENTMORE).withString("name",
                            strategyDetail.getTitle()).withString("id", strategyId).withString(
                                    "type", SourceType.STRATEGY_TYPE).withString("TYPE", "0").navigation();
                } else {
                    ToastUtils.showShortCenter("请先登录");
                    Utils.goToOtherClass(this, LoginActivity.class);
                }

                // 评论
                break;
            case R.id.strategy_details_option:
                if (Utils.isnotNull(SPUtils.getInstance().getString("token"))) {
                    // 目录
                    // 打开侧滑菜单
                    drawerLayout.openDrawer(GravityCompat.START);
                } else {
                    ToastUtils.showShortCenter("请先登录");

                    Utils.goToOtherClass(this, LoginActivity.class);
                }
                break;
            default:
                break;
        }
        RadioButton button = new RadioButton(this);
        button.setChecked(true);
    }

    /**
     * 取消收藏或点赞
     */
    public void deleteThumb(final int type) {
        RetrofitHelper.getApiService().deleteThumb(strategyId).subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BaseResponse>() {
            @Override
            public void accept(BaseResponse bean) throws Exception {
                if (0 == bean.getCode()) {
                    // 点赞
                    if (type == 0) {
                        ToastUtils.showShort("成功取消点赞");
                        strategyGive.setSelected(false);
                    } else {
                        // 收藏
                        ToastUtils.showShort("成功取消收藏");
                        strategyCollect.setSelected(false);
                    }
                } else if (2 == bean.getCode()) {
                    ARouter.getInstance().build(Constant.ACTIVITY_LOGIN).navigation(LineDetailActivity.this, 1);
                    ToastUtils.showShort(bean.getMessage());
                } else {
                    ToastUtils.showShort(bean.getMessage());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.showShort("请求错误");
            }
        });
    }

    /**
     * 保存收藏或点赞的数据
     *
     * @param type 0 ,表示点赞，1：收藏
     */
    public void saveThumb(final int type) {
        String content = strategyDetail.getContent();
        if (Utils.isnotNull(content)) {
            if (content.length() > 50) {
                content = content.substring(0, 50) + "...";
            }
        }
        RetrofitHelper.getApiService().saveThumb(strategyDetail.getId() + "", content,
                strategyDetail.getTitle(), SourceType.STRATEGY_TYPE).subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BaseResponse>() {
            @Override
            public void accept(BaseResponse bean) throws Exception {
                LogUtils.e("成功");
                if (bean.getCode() == 0) {
                    if (type == 0) {
                        ToastUtils.showShort("点赞成功");
                        strategyGive.setSelected(true);
                    } else {
                        ToastUtils.showShort("收藏成功");
                        strategyCollect.setSelected(true);
                    }
                } else if (2 == bean.getCode()) {
                    ARouter.getInstance().build(Constant.ACTIVITY_LOGIN).navigation(LineDetailActivity.this, 1);
                    ToastUtils.showShort(bean.getMessage());
                } else {
                    ToastUtils.showShort(bean.getMessage());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.showShort("操作失败，请稍后重试");
            }
        });
    }

    /**
     * 取消收藏
     */
    public void delCollection() {
        RetrofitHelper.getApiService().delCollection(strategyId, SourceType.STRATEGY_TYPE).subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BaseResponse>() {
            @Override
            public void accept(BaseResponse bean) throws Exception {
                if (0 == bean.getCode()) {
                    ToastUtils.showShort("取消收藏成功");
                    strategyCollect.setSelected(false);
                } else if (2 == bean.getCode()) {
                    ARouter.getInstance().build(Constant.ACTIVITY_LOGIN).navigation(LineDetailActivity.this, 1);
                    ToastUtils.showShort(bean.getMessage());
                } else {
                    ToastUtils.showShort(bean.getMessage());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.showShort("操作失败，请稍后重试");
            }
        });
    }

    /**
     * 保存收藏
     */
    public void saveCollection() {
        RetrofitHelper.getApiService().saveCollection(strategyId, SourceType.STRATEGY_TYPE, null,
                strategyDetail.getTitle()).subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BaseResponse>() {
            @Override
            public void accept(BaseResponse bean) throws Exception {
                if (0 == bean.getCode()) {
                    ToastUtils.showShort("收藏成功");
                    strategyCollect.setSelected(true);
                } else if (2 == bean.getCode()) {
                    ARouter.getInstance().build(Constant.ACTIVITY_LOGIN).navigation(LineDetailActivity.this, 1);
                    ToastUtils.showShort(bean.getMessage());
                } else {
                    ToastUtils.showShort(bean.getMessage());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.showShort("操作失败，请稍后重试");
            }
        });
    }
}
