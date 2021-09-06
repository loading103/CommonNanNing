package com.daqsoft.commonnanning.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.request.RequestOptions;
import com.daqsoft.android.ProjectConfig;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.AESEncryptUtil;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.HtmlConstant;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.http.CommonRequest;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.ApplyModuleEntity;
import com.daqsoft.commonnanning.ui.entity.UserInfoEntity;
import com.daqsoft.commonnanning.ui.trace.TraceActivity;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.glide.GlideApp;
import com.example.tomasyb.baselib.base.mvp.BaseFragment;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.LoadingDialog;
//import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * 我的个人中心fragment
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-3-20.15:50
 * @since JDK 1.8
 */

public class MeFragment extends BaseFragment {


    @BindView(R.id.head_tab_mine)
    HeadView headTabMine;
    @BindView(R.id.iv_tab_mine_robot)
    ImageView ivTabMineRobot;
    @BindView(R.id.iv_tab_mine_img)
    ImageView ivTabMineImg;
    @BindView(R.id.tv_tab_mine_name)
    TextView tvTabMineName;
    @BindView(R.id.ll_tab_mine_login)
    LinearLayout llTabMineLogin;
    @BindView(R.id.tv_tab_mine_edit_info)
    TextView tvTabMineEditInfo;
    @BindView(R.id.tv_tab_mine_like)
    TextView tvTabMineLike;
    @BindView(R.id.tv_tab_mine_comment)
    TextView tvTabMineComment;
    @BindView(R.id.tv_tab_mine_collect)
    TextView tvTabMineCollect;
    @BindView(R.id.gv_tab_mine_apply)
    RecyclerView gvTabMineApply;
    @BindView(R.id.ll_tab_mine_apply)
    LinearLayout llTabMineApply;
    @BindView(R.id.tv_tab_mine_share)
    TextView mTvShare;




    /**
     * 常用功能的图标集合my_features_order
     */
    public static Integer[] applyIcons = new Integer[]{
            R.mipmap.my_features_order,
            R.mipmap.my_features_consumption,
            R.mipmap.my_features_refund,
            R.mipmap.my_features_address,
            R.mipmap.my_features_stroke,
            R.mipmap.my_features_complaint,
            R.mipmap.my_features_message,
            R.mipmap.my_features_opinion,
            R.mipmap.my_features_notification,
            R.mipmap.usercenter_icon_information_normal,
            R.mipmap.my_footprint
    };


    /**
     * 常用功能的图标集合my_features_order
     */
    public static Integer[] xzapplyIcons = new Integer[]{
            R.mipmap.my_features_stroke,
            R.mipmap.my_features_complaint,
            R.mipmap.my_features_message,
            R.mipmap.my_features_opinion,
            R.mipmap.my_features_notification,
            R.mipmap.my_footprint
    };
    /**
     * 个人中心常用功能模块
     */
    private List<ApplyModuleEntity> applyList = new ArrayList<>();

    /**
     * 个人中心常用功能模块的名字
     */
    private String[] applyName;
    /**
     * 个人中心常用功能模块的ID
     */
    private String[] applyID;

    @Override
    protected int getLayoutId() {
        return R.layout.fg_me;
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
        headTabMine.setTitle("我的");
        headTabMine.setBackHidden(false);
        mTvShare.setVisibility(View.GONE);
        tvTabMineComment.setVisibility(View.VISIBLE);
        if ("江西".equals(ProjectConfig.CITY_NAME)) {
            ivTabMineRobot.setVisibility(View.GONE);
        }

        if (ProjectConfig.APPID.equals("17867")) {
            //applyIcons = ProjectConfig.menuIcon;
        }

        setMineApplyData();
    }

    @Override
    protected void initData() {

    }


    /**
     * 设置个人信息中的功能模块页面
     */
    public void setMineApplyData() {
        applyList = new ArrayList<>();
        applyName = getResources().getStringArray(R.array.mine_apply_name);
        applyID = getResources().getStringArray(R.array.mine_apply_id);
        if(ProjectConfig.CITY_NAME.equals("西藏")){
            for (int i = 0; i < applyName.length; i++) {
                ApplyModuleEntity apply = new ApplyModuleEntity();
                apply.setName(applyName[i]);
                apply.setImg(xzapplyIcons[i]);
                apply.setId(applyID[i]);
                applyList.add(apply);
            }
        }else {
            for (int i = 0; i < applyName.length; i++) {
                ApplyModuleEntity apply = new ApplyModuleEntity();
                apply.setName(applyName[i]);
                apply.setImg(applyIcons[i]);
                apply.setId(applyID[i]);
                applyList.add(apply);
            }
        }
        initApplyAdapter();
    }

    /**
     * 初始化常用功能的适配器
     */
    public void initApplyAdapter() {

        // 		StaggeredGridLayoutManager的构造函数接收两个参数，
        // 第一个指定列数为3，第二个指定布局的排列方向
        StaggeredGridLayoutManager layout = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        gvTabMineApply.setNestedScrollingEnabled(false);
        gvTabMineApply.setLayoutManager(layout);
        BaseQuickAdapter adapter = new BaseQuickAdapter<ApplyModuleEntity, BaseViewHolder>(R.layout.index_gridview_adapter_item, applyList) {
            @Override
            protected void convert(BaseViewHolder helper, final ApplyModuleEntity item) {
                helper.setText(R.id.index_item_gd_name, item.getName());
                GlideApp.with(getActivity()).load(item.getImg()).into((ImageView) helper.getView(R.id.index_item_gd_img));
                helper.setOnClickListener(R.id.index_item_gd_ll, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (Integer.parseInt(item.getId())) {
                            case 1:
                                if (ProjectConfig.CITY_NAME.equals("南宁")) {
                                    checkToken();
                                } else {
                                    //  我的订单
                                    CommonRequest.goToShoppingHtml(getActivity(), HtmlConstant.HTML_MINE_ORDER, item.getName());
                                }
                                break;
                            case 2:
                                // 电子消费码
                                CommonRequest.goToShoppingHtml(getActivity(), HtmlConstant.HTML_MINE_TICKET, item.getName());
                                break;
                            case 3:
                                // 我的退款
                                CommonRequest.goToShoppingHtml(getActivity(), HtmlConstant.HTML_MINE_REFUND_LIST, item.getName());
                                break;
                            case 4:
                                // 收货地址
                                CommonRequest.goToShoppingHtml(getActivity(), HtmlConstant.HTML_MINE_ADDRESS, item.getName());
                                break;
                            case 5:
                                // 我的行程
                                if (ProjectConfig.APPID.equals("17867")) {
                                    startActivity(new Intent(getContext(), TraceActivity.class));
                                } else {
                                    checkedTokenGo(Constant.ACTIVITY_ROUTE);
                                }
                                break;
                            case 6:
                                // 投诉
                                if (ProjectConfig.CITY_NAME.equals("铜川") || "锡林郭勒盟".equals(ProjectConfig.CITY_NAME)) {
                                    // 跳转大行管投诉
                                    ARouter.getInstance().build(Constant.ACTIVITY_BASEWEB).withString("HTMLURL", ProjectConfig.MY_COMPLAINT_HTML_URL).withString("HTMLTITLE", "我的投诉").navigation();

                                } else if (ProjectConfig.CITY_NAME.equals("南宁")) {
                                    //  (跳转12301)
                                    ARouter.getInstance().build(Constant.ACTIVITY_BASEWEB).withString("HTMLURL", ProjectConfig.MY_COMPLAINT_HTML_URL).withString("HTMLTITLE", "我的投诉").navigation();

                                } else {
                                    checkedTokenGo(Constant.ACTIVITY_COMPLAINT_LIST);
                                }
                                break;
                            case 7:
                                // 在线留言
                                checkedTokenGo(Constant.ACTIVITY_ONLINE_MESSAGE_LIST);
                                break;
                            case 8:
                                // 意见反馈
                                checkedTokenGo(Constant.ACTIVITY_OPINION);
                                break;
                            case 9:
                                // 消息提示
                                checkedTokenGo(Constant.ACTIVITY_MESSAGE);
                                break;
                            case 10:
                                // 常用信息（跳转小电商）
                                CommonRequest.goToShoppingHtml(getActivity(),
                                        HtmlConstant.HTML_MINE_CONTACTS, "拼组团");
                                break;
                            default:
                                break;
                            case 11:
                                // 足迹
                                startActivity(new Intent(getContext(), TraceActivity.class));
                                break;
                        }
                    }
                });
            }
        };
        gvTabMineApply.setAdapter(adapter);
    }

    private void checkToken() {
        if (ObjectUtils.isNotEmpty(SPUtils.getInstance().getString(SPCommon.TOKEN))) {
            CommonRequest.checkedToken(new CommonRequest.OnStateCallBack() {
                @Override
                public void result(int value) {
                    // 0未失效
                    if (0 == value) {
                        String phone = SPUtils.getInstance().getString(SPCommon.PHONE);
                        boolean isService = SPUtils.getInstance().getBoolean(SPCommon.SERVICE,
                                false);
                        try {
                            String aesphone = AESEncryptUtil.Encrypt16(phone);
                            String url =
                                    ProjectConfig.BASE_HTML_TRAIN + aesphone + "&targetUrl" +
                                            "=/myOrder?status=all";
                            // 是否确定协议
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
            ToastUtils.showShortCenter("请先登录");
            ARouter.getInstance().build(Constant.ACTIVITY_LOGIN).navigation();
        }
    }

    /**
     * 检查token并调整对应的地址
     */
    private void checkedTokenGo(String pageUrl) {
        CommonRequest.checkedToken(new CommonRequest.OnStateCallBack() {
            @Override
            public void result(int value) {
                // 0未失效
                if (0 == value) {
                    ARouter.getInstance().build(pageUrl).navigation();
                } else {
                    ARouter.getInstance().build(Constant.ACTIVITY_LOGIN).navigation(getActivity(), 1);
                }
            }
        });
    }


    @OnClick({R.id.iv_tab_mine_img, R.id.tv_tab_mine_name, R.id.tv_tab_mine_edit_info,
            R.id.iv_tab_mine_robot, R.id.tv_tab_mine_like, R.id.tv_tab_mine_comment,
            R.id.tv_tab_mine_collect, R.id.tv_tab_mine_share})
    public void onClick(final View view) {
        CommonRequest.checkedToken(new CommonRequest.OnStateCallBack() {
            @Override
            public void result(int value) {
                LoadingDialog.cancelDialogForLoading();
                // 0未失效
                if (0 == value) {
                    switch (view.getId()) {
                        case R.id.tv_tab_mine_share:
                            // 分享
                            break;
                        case R.id.iv_tab_mine_img:
                            // 登录
                            ARouter.getInstance().build(Constant.ACTIVITY_MINE).navigation();
                            break;
                        case R.id.tv_tab_mine_name:
                            // 登录
                            ARouter.getInstance().build(Constant.ACTIVITY_MINE).navigation();
                            break;
                        case R.id.tv_tab_mine_edit_info:
                            // 查看并编辑个人资料
                            ARouter.getInstance().build(Constant.ACTIVITY_MINE).navigation();
                            break;
                        case R.id.iv_tab_mine_robot:
                            // 智能机器人
                            ARouter.getInstance().build(Constant.ACTIVITY_ROBOT).navigation();
                            break;
                        case R.id.tv_tab_mine_like:
                            // 点赞
                            ARouter.getInstance().build(Constant.ACTIVITY_THUMB).navigation();
                            break;
                        case R.id.tv_tab_mine_comment:
                            // 评论
                            ARouter.getInstance().build(Constant.ACTIVITY_COMMENT).navigation();
                            break;
                        case R.id.tv_tab_mine_collect:
                            // 收藏
                            ARouter.getInstance().build(Constant.ACTIVITY_COLLECT).navigation();
                            break;
                        default:
                            break;
                    }
                } else {
                    ARouter.getInstance().build(Constant.ACTIVITY_LOGIN).navigation(getActivity());
                }
            }
        });

    }

    @Override
    public void showLoadingDialog(String msg) {

    }

    @Override
    public void dismissLoadingDialog() {

    }


    @Override
    public void onResume() {
        super.onResume();
        initUserInfo();
    }

    /**
     * 初始化用户信息
     */
    public void initUserInfo() {
        if (ObjectUtils.isNotEmpty(SPUtils.getInstance().getString(SPCommon.TOKEN))) {
            getUserInfo();
        } else {
            tvTabMineName.setText("登录/注册");
            ivTabMineImg.setImageResource(R.mipmap.my_avatar_default);
            //登出
//            MobclickAgent.onProfileSignOff();
        }
    }

    private void getUserInfo() {
        RetrofitHelper.getApiService().getUserInfo().subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BaseResponse<UserInfoEntity>>() {
            @Override
            public void accept(BaseResponse<UserInfoEntity> bean) throws Exception {
                if (bean.getCode() == 0) {
                    tvTabMineName.setText(ObjectUtils.isNotEmpty(bean.getData().getNickname()) ?
                            bean.getData().getNickname() : bean.getData().getAccount());
                    GlideApp.with(getActivity()).load(bean.getData().getHead()).apply(RequestOptions.bitmapTransform(new CropCircleTransformation())).placeholder(R.mipmap.my_avatar_default).error(R.mipmap.my_avatar_default).into(ivTabMineImg);
                    SPUtils.getInstance().put(SPCommon.PHONE, bean.getData().getPhone());
                    SPUtils.getInstance().put(SPCommon.REAL_NAME, bean.getData().getName());
                    //当用户使用自有账号登录时，可以这样统计：
//                    MobclickAgent.onProfileSignIn(bean.getData().getAccount());
                } else {
                    tvTabMineName.setText("登录/注册");
                    ivTabMineImg.setImageResource(R.mipmap.my_avatar_default);
                    //登出
//                    MobclickAgent.onProfileSignOff();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                tvTabMineName.setText("登录/注册");
                ivTabMineImg.setImageResource(R.mipmap.my_avatar_default);
                //登出
//                MobclickAgent.onProfileSignOff();
            }
        });
    }
}
