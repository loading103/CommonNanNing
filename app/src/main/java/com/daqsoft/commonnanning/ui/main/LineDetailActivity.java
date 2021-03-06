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
 * ??????????????????
 *
 * @author ??????
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
     * ?????????????????????
     */
    private int indexNum;
    /**
     * ?????????????????????
     */
    private StrategyDetail strategyDetail;

    private MultipleTraveAdapter mAdapters;
    private List<StraveXqEy> mDatas;
    /**
     * ??????
     */
    private BaseDialog mShareDialog = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_line_detail;
    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        mHead.setTitle("????????????");
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
        if(ProjectConfig.CITY_NAME.equals("??????")) {
            mHead.setMenuHidden(false);
        }
        mHead.setMenuListener(new HeadView.OnMenuListener() {
            @Override
            public void onClickMenu(View v) {
                if (mShareDialog != null) {
                    mShareDialog.show();
                } else {
                    ToastUtils.showShort("????????????????????????!");
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
                        // ??????
                        if (1 == oprationStatus.getThumb()) {
                            strategyGive.setSelected(true);
                        } else {
                            strategyGive.setSelected(false);
                        }
                        // ??????
                        if (1 == oprationStatus.getEnshrine()) {
                            strategyCollect.setSelected(true);
                        } else {
                            strategyCollect.setSelected(false);
                        }
                    }
                    // ??????
                    if (strategyDetail.getSource() == 2) {
                        mImgMenu.setVisibility(View.VISIBLE);
                        // ????????????????????????
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
                        // ??????
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
     * ???????????????
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
        strategyGiveNum.setText(strategyDetail.getGivepoint() + "??????");
        strategyCollectNum.setText(strategyDetail.getCollection() + "??????");
        strategyCommentNum.setText(strategyDetail.getComment() + "??????");
        strategyComment.setText(strategyDetail.getComment() + "?????????");
        GlideUtils.loadImage(mContext, strategyImg, strategyDetail.getCover(),
                R.mipmap.common_ba_banner);
        GlideUtils.loadImage(mContext, strategyIcon, strategyDetail.getHead(),
                R.mipmap.common_ba_banner);

        strategyAddress.setText(ObjectUtils.isNotEmpty(strategyDetail.getRegionName()) ?
                strategyDetail.getRegionName() : "????????????");
        strategyUserName.setText(Utils.isnotNull(strategyDetail.getNickname()) ?
                strategyDetail.getNickname() : "????????????");
        mAdapters.addHeaderView(view);
    }

    /**
     * ?????????????????????
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
        // ?????????position  boundPosition???item?????????????????????
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
        // ????????????
        drawerLayout.closeDrawers();
        return true;
    }

    @OnClick({R.id.strategy_details_give_tv, R.id.strategy_details_collect_tv,
            R.id.strategy_details_comment_tv, R.id.strategy_details_option})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.strategy_details_give_tv:
                if (Utils.isnotNull(SPUtils.getInstance().getString("token"))) {
                    // ??????
                    // true ???????????????????????????
                    if (strategyGive.isSelected()) {
                        deleteThumb(0);
                    } else {
                        saveThumb(0);
                    }
                } else {
                    ToastUtils.showShortCenter("????????????");
                    Utils.goToOtherClass(this, LoginActivity.class);
                }
                break;
            case R.id.strategy_details_collect_tv:
                if (Utils.isnotNull(SPUtils.getInstance().getString("token"))) {
                    // ??????
                    if (strategyCollect.isSelected()) {
                        delCollection();
                    } else {
                        saveCollection();
                    }
                } else {
                    ToastUtils.showShortCenter("????????????");
                    Utils.goToOtherClass(this, LoginActivity.class);
                }

                break;
            case R.id.strategy_details_comment_tv:
                if (Utils.isnotNull(SPUtils.getInstance().getString("token"))) {
                    ARouter.getInstance().build(Constant.ACTIVITY_COMMENTMORE).withString("name",
                            strategyDetail.getTitle()).withString("id", strategyId).withString(
                                    "type", SourceType.STRATEGY_TYPE).withString("TYPE", "0").navigation();
                } else {
                    ToastUtils.showShortCenter("????????????");
                    Utils.goToOtherClass(this, LoginActivity.class);
                }

                // ??????
                break;
            case R.id.strategy_details_option:
                if (Utils.isnotNull(SPUtils.getInstance().getString("token"))) {
                    // ??????
                    // ??????????????????
                    drawerLayout.openDrawer(GravityCompat.START);
                } else {
                    ToastUtils.showShortCenter("????????????");

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
     * ?????????????????????
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
                    // ??????
                    if (type == 0) {
                        ToastUtils.showShort("??????????????????");
                        strategyGive.setSelected(false);
                    } else {
                        // ??????
                        ToastUtils.showShort("??????????????????");
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
                ToastUtils.showShort("????????????");
            }
        });
    }

    /**
     * ??????????????????????????????
     *
     * @param type 0 ,???????????????1?????????
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
                LogUtils.e("??????");
                if (bean.getCode() == 0) {
                    if (type == 0) {
                        ToastUtils.showShort("????????????");
                        strategyGive.setSelected(true);
                    } else {
                        ToastUtils.showShort("????????????");
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
                ToastUtils.showShort("??????????????????????????????");
            }
        });
    }

    /**
     * ????????????
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
                    ToastUtils.showShort("??????????????????");
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
                ToastUtils.showShort("??????????????????????????????");
            }
        });
    }

    /**
     * ????????????
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
                    ToastUtils.showShort("????????????");
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
                ToastUtils.showShort("??????????????????????????????");
            }
        });
    }
}
