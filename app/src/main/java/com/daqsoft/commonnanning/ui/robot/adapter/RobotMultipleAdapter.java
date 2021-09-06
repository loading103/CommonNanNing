package com.daqsoft.commonnanning.ui.robot.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.location.AMapLocation;
import com.daqsoft.android.ProjectConfig;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.helps_gdmap.CompleteFuncData;
import com.daqsoft.commonnanning.helps_gdmap.HelpMaps;
import com.daqsoft.commonnanning.helps_gdmap.MapNaviUtils;
import com.daqsoft.commonnanning.ui.robot.entity.MsgEty;
import com.daqsoft.commonnanning.ui.robot.entity.RobotChangeEty;
import com.daqsoft.commonnanning.ui.robot.entity.RobotMultipleItem;
import com.daqsoft.commonnanning.ui.service.FoundNearNewActivity;
import com.daqsoft.commonnanning.utils.Utils;
import com.daqsoft.guide.MapInformationActivity;
import com.example.tomasyb.baselib.adapter.BaseMultiItemQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.glide.GlideApp;
import com.example.tomasyb.baselib.util.LogUtils;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.PhoneUtils;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * 聊天适配器
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-8-24.15:34
 * @since JDK 1.8
 */

public class RobotMultipleAdapter extends BaseMultiItemQuickAdapter<RobotMultipleItem,
        BaseViewHolder> {
    private Context mContext;

    public RobotMultipleAdapter(List<RobotMultipleItem> data, Context context) {
        super(data);
        this.mContext = context;
        addItemType(RobotMultipleItem.ME, R.layout.item_robot_me);
        addItemType(RobotMultipleItem.ROBOT, R.layout.item_robot_chat);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final RobotMultipleItem item) {
        switch (helper.getItemViewType()) {
            case RobotMultipleItem.ME:
                helper.setText(R.id.item_robot_chat_tv_question, item.getContent());
                GlideApp.with(mContext)
                        .load(SPUtils.getInstance().getString(SPCommon.HEAD_IMG))
                        .placeholder(R.mipmap.robot_headimage)
                        .error(R.mipmap.robot_headimage)
                        .into((ImageView) helper.getView(R.id.item_robot_chat_iv_me));
                break;
            case RobotMultipleItem.ROBOT:
                GlideApp.with(mContext)
                        .load(SPUtils.getInstance().getString(SPCommon.ROBOT_IMG))
                        .placeholder(R.mipmap.robot_headimage)
                        .error(R.mipmap.robot_headimage)
                        .into((ImageView) helper.getView(R.id.item_robot_chat_iv_robot));
                RecyclerView mRv = (RecyclerView) helper.getView(R.id.item_robot_rv);
                // 后台设置了问答的回复
                if (item.getContentType() == 0) {
                    helper.getView(R.id.robot_chat_tv_title).setVisibility(View.GONE);
                    mRv.setLayoutManager(new LinearLayoutManager(mContext) {
                        @Override
                        public boolean canScrollVertically() {
                            return false;
                        }
                    });
                    List<String> mlist = new ArrayList<>();
                    mlist.add(item.getContent());
                    BaseQuickAdapter<String, BaseViewHolder> mAdapter = new
                            BaseQuickAdapter<String, BaseViewHolder>(R.layout
                                    .item_robot_text_only, mlist) {

                                @Override
                                protected void convert(BaseViewHolder helper, String item_) {

                                    if(item_.contains("<section")|| item_.contains("style=")){
                                        helper.setText(R.id.tv_region, Html.fromHtml(item_).toString());
                                    }else {
                                        helper.setText(R.id.tv_region, item_);
                                    }

                                }
                            };
                    // 如果有详情页就出现进入详情页的条目
                    if (ObjectUtils.isNotEmpty(item.getAnswerxq())) {
                        View footer = LayoutInflater.from(mContext).inflate(R.layout
                                        .item_robot_text_xq,
                                (ViewGroup) mRv.getParent(), false);
                        TextView mTv = (TextView) footer.findViewById(R.id.tv_region);
                        mTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ARouter.getInstance().build(Constant.ACTIVITY_CONTENT_WEB).withInt("pageType", 3).withString("webContent",item.getAnswerxq()).withString("mTitle","详情")
                                        .navigation();
//                                Bundle bundle = new Bundle();
//                                bundle.putString("content", item.getAnswerxq());
//                                Intent intent = new Intent(mContext, RobotXqActivity.class);
//                                intent.putExtras(bundle);
//                                mContext.startActivity(intent);
                            }
                        });
                        mAdapter.addFooterView(footer);
                    }
                    mRv.setAdapter(mAdapter);

                } else if (item.getContentType() == 1) {
                    // 全文搜索的列表
                    if (ObjectUtils.isNotEmpty(item.getCurentQuestion())) {
                        helper.setVisible(R.id.robot_chat_tv_title, true);
                        String title = "";
                        if (item.isResources()) {
                            title = "为你查到关于" + "\"" + item.getCurentQuestion() + "\"" + "资源如下：";
                            helper.setText(R.id.robot_chat_tv_title,
                                    Utils.setTextColor(title,
                                            mContext.getResources().getColor(R.color
                                                    .main_default), 6,
                                            6 + item
                                                    .getCurentQuestion().length() + 2));
                        } else {
                            title = item.getNickname() + "还没学习到相关的知识！为您推荐其他相关的资源如下：";
                            helper.setText(R.id.robot_chat_tv_title, title);
                        }
                    }
                    mRv.setLayoutManager(new LinearLayoutManager(mContext) {
                        @Override
                        public boolean canScrollVertically() {
                            return false;
                        }
                    });
                    BaseQuickAdapter<RobotMultipleItem.ScenicType, BaseViewHolder> mAdapter = new
                            BaseQuickAdapter<RobotMultipleItem.ScenicType, BaseViewHolder>(R
                                    .layout.item_robot_text_more, item.getmScenicList()) {
                                @Override
                                protected void convert(BaseViewHolder helper, final
                                RobotMultipleItem
                                        .ScenicType item) {
                                    helper.setText(R.id.tv_region, item.getContent());
                                    helper.setOnClickListener(R.id.tv_region, new View
                                            .OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (ObjectUtils.isNotEmpty(item.getId()) &&
                                                    ObjectUtils.isNotEmpty
                                                            (item
                                                                    .getType())) {
                                                EventBus.getDefault().post(new MsgEty(item.getId
                                                        (), item.getType(),
                                                        item.getLat(), item.getLng(), item
                                                        .getAdress(), item.getPhone(),
                                                        item.getContent(), item.getContent()));
                                                LogUtils.e("你点击的是" + item.getContent());
                                            }
                                        }
                                    });
                                }
                            };
                    if (item.getmScenicList().size() > 0 && item.getmScenicList().size() == 3) {
                        View footer = LayoutInflater.from(mContext).inflate(R.layout
                                        .item_text_rudis_10,
                                (ViewGroup) mRv.getParent(), false);
                        TextView mTv = (TextView) footer.findViewById(R.id.tv_location);
                        mTv.setText("换一批,再看看");
                        mTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int mpage = item.getCurentPage();
                                mpage++;
                                EventBus.getDefault().post(new RobotChangeEty(item
                                        .getCurentQuestion(), mpage, helper.getPosition()));
                            }
                        });
                        mAdapter.addFooterView(footer);
                    }
                    mRv.setAdapter(mAdapter);
                } else if (item.getContentType() == 2) {
                    // 具体操作适配器
                    if (ObjectUtils.isNotEmpty(item.getCurentQuestion())) {
                        helper.setVisible(R.id.robot_chat_tv_title, true);
                        String title = "为你查到关于" + "\"" + item.getCurentQuestion() + "\"" +
                                "的资源分类如下：";
                        helper.setText(R.id.robot_chat_tv_title,
                                Utils.setTextColor(title, mContext.getResources()
                                        .getColor(R.color.main_default), 6, 6 + item
                                        .getCurentQuestion().length() + 2));
                    }
                    mRv.setLayoutManager(new GridLayoutManager(mContext, 2) {
                        @Override
                        public boolean canScrollVertically() {
                            return false;
                        }
                    });
                    mRv.setAdapter(new BaseQuickAdapter<RobotMultipleItem.ContentType,
                            BaseViewHolder>(R.layout.item_tag4, item.getmContentTypeList()) {
                        @Override
                        protected void convert(BaseViewHolder helper, final RobotMultipleItem
                                .ContentType item) {
                            helper.setText(R.id.tag, item.getName());
                            helper.setOnClickListener(R.id.tag, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    switch (item.getType()) {
                                        case 0:
                                            // 导游导览
                                            if (ObjectUtils.isNotEmpty(item.getMapGuideSet())) {
                                                Intent intent = new Intent(mContext,
                                                        MapInformationActivity.class);
                                                // 网络请求根地址
                                                intent.putExtra("url", ProjectConfig.BASE_URL);
                                                // 语言编码
                                                intent.putExtra("lang", ProjectConfig.LANG);
                                                // 站点编码
                                                intent.putExtra("sitecode", ProjectConfig.SITECODE);
                                                // 地区编码
                                                intent.putExtra("region", ProjectConfig.REGION);
                                                // 微信的账号APPID
                                                intent.putExtra("appid", ProjectConfig.APPID);
                                                // 地区名称
                                                intent.putExtra("city", ProjectConfig.CITY_NAME);
                                                // 当前地区的经度
                                                intent.putExtra("lat", ProjectConfig.COMMON_LAT);
                                                // 当前地区的纬度
                                                intent.putExtra("lng", ProjectConfig.COMMON_LNG);
                                                // 当前地区的相关介绍信息
                                                intent.putExtra("about", "");
                                                intent.putExtra("ID", Integer.valueOf(item
                                                        .getMapGuideSet()));
                                                mContext.startActivity(intent);
                                            } else {
                                                ToastUtils.showShortCenter("暂无导游导览");
                                            }
                                            break;
                                        case 1:
                                            // 720全景
                                            ARouter.getInstance().build(Constant.ACTIVITY_BASEWEB)
                                                    .withString("HTMLURL", item.getFullAddress())
                                                    .withString("HTMLTITLE", "720")
                                                    .navigation();
                                            break;
                                        case 2:
                                            // 实景展播
                                            ARouter.getInstance()
                                                    .build(Constant.ACTIVITY_VIDEO_PLAY)
                                                    .withString("content", item.getMonitor())
                                                    .navigation();
                                            break;
                                        case 3:
                                            // 导航
                                            HelpMaps.startLocation(new CompleteFuncData() {
                                                @Override
                                                public void success(AMapLocation location) {
                                                    if (ObjectUtils.isNotEmpty(location)) {
                                                        if (ObjectUtils.isNotEmpty(item.getLat())
                                                                && ObjectUtils.isNotEmpty(item
                                                                .getLng())) {
                                                            MapNaviUtils.isMapNaviUtils(
                                                                    (Activity) mContext,
                                                                    location.getLatitude() + "",
                                                                    location.getLongitude() + "",
                                                                    location.getAddress(),
                                                                    item.getLat(), item.getLng(),
                                                                    ObjectUtils.isNotEmpty(item
                                                                            .getAdress()) ?
                                                                            item.getAdress() :
                                                                            "目的地");
                                                        } else {
                                                            ToastUtils.showShortCenter
                                                                    ("数据异常，无法进行导航操作");
                                                        }
                                                    } else {
                                                        ToastUtils.showShortCenter("数据异常，无法进行导航操作");
                                                    }
                                                }
                                            });

                                            break;
                                        case 4:
                                            if (ActivityCompat.checkSelfPermission(mContext,
                                                    Manifest
                                                            .permission.CALL_PHONE) !=
                                                    PackageManager
                                                            .PERMISSION_GRANTED) {
                                                // TODO: Consider calling
                                                return;
                                            }
                                            PhoneUtils.call(item.getPhone());
                                            break;
                                        case 5:
                                            // 周边好玩
                                            Bundle bundle2 = new Bundle();
                                            bundle2.putString("lat", item.getLat());
                                            bundle2.putString("lng", item.getLng());
                                            bundle2.putString("scenicname", item.getContent());
                                            bundle2.putString("scenicadress", item.getAdress());
                                            bundle2.putInt("type", 1);
                                            Intent intent2 = new Intent(mContext,
                                                    FoundNearNewActivity.class);
                                            intent2.putExtras(bundle2);
                                            mContext.startActivity(intent2);
                                            break;
                                        case 6:
                                            // 了解更多
                                            LogUtils.e("你当前的类型是-->" + item.getmScenicType());
                                            if ("sourceType_1".equals(item.getmScenicType())) {
                                                // 景区
                                                ARouter.getInstance()
                                                        .build(Constant.ACTIVITY_SCENIC_DETAIL)
                                                        .withString("mId", item.getId())
                                                        .navigation(mContext);
                                            } else if ("sourceType_2".equals(item.getmScenicType
                                                    ())) {
                                                // 酒店
                                                ARouter.getInstance()
                                                        .build(Constant.ACTIVITY_HOTEL_DETAIL)
                                                        .withString("mId", item.getId())
                                                        .navigation();
                                            } else if ("sourceType_9".equals(item.getmScenicType
                                                    ())) {
                                                // 美食
                                                ARouter.getInstance()
                                                        .build(Constant.ACTIVITY_FOOD_DETIAL)
                                                        .withString("ID", item.getId())
                                                        .navigation();
                                            } else if ("sourceType_8".equals(item.getmScenicType
                                                    ())) {
                                                // 美食
                                                ARouter.getInstance()
                                                        .build(Constant.ACTIVITY_FOOD_DETIAL)
                                                        .withString("ID", item.getId())
                                                        .navigation();
                                            }
                                            break;
                                        default:
                                            break;

                                    }
                                }
                            });
                        }
                    });
                }
                break;
            default:

                break;
        }

    }
}
