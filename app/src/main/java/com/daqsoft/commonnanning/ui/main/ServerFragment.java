package com.daqsoft.commonnanning.ui.main;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.android.ProjectConfig;
import com.daqsoft.busquery.BusQueryActivity;
import com.daqsoft.busquery.TrainticketsActivity;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.ui.entity.ServerEntity;
import com.daqsoft.commonnanning.ui.service.FoundNearNewActivity;
import com.daqsoft.commonnanning.ui.service.PassengerFlowActivity;
import com.daqsoft.commonnanning.ui.service.fun.FunActivity;
import com.daqsoft.commonnanning.ui.service.guide.GuideActivity;
import com.daqsoft.commonnanning.view.webview.BaseWebFileActivity;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.mvp.BaseFragment;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.widget.HeadView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 服务fragment
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-6-4.15:50
 * @since JDK 1.8
 */

public class ServerFragment extends BaseFragment {


    @BindView(R.id.service_title)
    HeadView serviceTitle;
    @BindView(R.id.recycler_server)
    RecyclerView recyclerView;

    /**
     * 适配器
     */
    BaseQuickAdapter<ServerEntity, BaseViewHolder> adapter;
    /**
     * 服务列表
     */
    List<ServerEntity> serverList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fg_server;
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
        serviceTitle.setTitle("服务");
        serviceTitle.setBackHidden(false);
    }

    @Override
    protected void initData() {
        initAdapter();
        String[] titles = getResources().getStringArray(R.array.server_menu_title);
        String[] infos = getResources().getStringArray(R.array.server_menu_info);
        String[] contents = getResources().getStringArray(R.array.server_menu_content);
        int[] titleColors = getResources().getIntArray(R.array.server_title_color);
        int[] infoColors = getResources().getIntArray(R.array.server_info_color);
        TypedArray bgImgs = getResources().obtainTypedArray(R.array.server_bg_img);
        TypedArray icons = getResources().obtainTypedArray(R.array.server_icon);
        String[] types = getResources().getStringArray(R.array.server_type);
        for (int i = 0; i < titles.length; i++) {
            ServerEntity server = new ServerEntity();
            server.setTitle(titles[i]);
            server.setContent(contents[i]);
            server.setInfo(infos[i]);
            server.setTitleColor(titleColors[i]);
            server.setInfoColor(infoColors[i]);
            server.setBgImg(bgImgs.getResourceId(i, 0));
            server.setIcon(icons.getResourceId(i, 0));
            server.setType(types[i]);
            serverList.add(server);
        }
        adapter.notifyDataSetChanged();

    }

    /**
     * 列表适配器加点击事件
     */
    public void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BaseQuickAdapter<ServerEntity, BaseViewHolder>(R.layout.item_server,
                serverList) {
            @Override
            protected void convert(BaseViewHolder helper, ServerEntity item) {
                helper.setText(R.id.tv_title, item.getTitle());
                helper.setText(R.id.tv_info, item.getInfo());
                helper.setText(R.id.tv_content, item.getContent());
                helper.setTextColor(R.id.tv_title, item.getTitleColor());
                helper.setTextColor(R.id.tv_info, item.getInfoColor());
                helper.setBackgroundRes(R.id.ll_item, item.getBgImg());
                helper.setImageResource(R.id.iv_icon, item.getIcon());
            }
        };
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String type = serverList.get(position).getType();
                switch (type) {
                    case "robot":
                        // 智能机器人
                        ARouter.getInstance().build(Constant.ACTIVITY_ROBOT).navigation();
                        break;
                    case "guide":
                        // 导游导览
                        Bundle bundle = new Bundle();
                        bundle.putInt("PageType", 2);
                        Intent intent = new Intent(getActivity(), GuideListActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case "stroke":
                        // 智能行程
                        if (ObjectUtils.isNotEmpty(SPUtils.getInstance().getString(SPCommon.TOKEN))) {
                            ARouter.getInstance().build(Constant.ACTIVITY_ROUTE).navigation();
                        } else {
                            ARouter.getInstance().build(Constant.ACTIVITY_LOGIN).navigation();
                        }
                        break;
                    case "complaint":
                        // 在线投诉
                        if (ObjectUtils.isNotEmpty(ProjectConfig.COMPLAINT_HTML_URL)) {
                            Intent intent4 = new Intent(getActivity(), BaseWebFileActivity.class);
                            Bundle bundle4 = new Bundle();
                            bundle4.putString("urlKey", ProjectConfig.COMPLAINT_HTML_URL);
                            bundle4.putString("title", "在线投诉");
                            intent4.putExtras(bundle4);
                            startActivity(intent4);
                        } else {
                            ARouter.getInstance().build(Constant.ACTIVITY_COMPLAINT_LIST).navigation();
                        }
                        break;
                    case "news":
                        // 旅游资讯
                        ARouter.getInstance().build(Constant.ACTIVITY_NEWS_LIST).navigation();
                        break;
                    case "traffic":
                        // 交通指南
                        ARouter.getInstance().build(Constant.ACTIVITY_CONTENT_WEB).withInt(
                                "pageType", 2).navigation();
                        break;
                    case "travelAgency":
                        // 旅行社
                        ARouter.getInstance().build(Constant.ACTIVITY_TRAVEL).navigation();
                        break;
                    case "toilet":
                        // 厕所
                        bundle = new Bundle();
                        bundle.putInt("type", 0);
                        bundle.putInt("curentType", 12);
                        intent = new Intent(getActivity(), FoundNearNewActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case "bus":
                        // 公交车查询
                        bundle = new Bundle();
                        bundle.putString("latitude",
                                SPUtils.getInstance().getString(SPCommon.LATITUDE));
                        bundle.putString("longitude",
                                SPUtils.getInstance().getString(SPCommon.LONGITUDE));
                        bundle.putString("cityname",
                                SPUtils.getInstance().getString(SPCommon.CITY_NAME));
                        bundle.putString("cityAddress",
                                SPUtils.getInstance().getString(SPCommon.CITY_ADDRESS));
                        intent = new Intent(getActivity(), BusQueryActivity.class);
                        intent.putExtras(bundle);
                        getActivity().startActivity(intent);
                        break;
                    case "gas":
                        // 加油站查询
                        bundle = new Bundle();
                        bundle.putInt("type", 0);
                        bundle.putInt("curentType", 11);
                        intent = new Intent(getActivity(), FoundNearNewActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case "shopping":
                        // 购物
                        bundle = new Bundle();
                        bundle.putInt("type", 0);
                        bundle.putInt("curentType", 4);
                        intent = new Intent(getActivity(), FoundNearNewActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case "fun":
                        // 超好玩
                        intent = new Intent(getActivity(), FunActivity.class);
                        startActivity(intent);
                        break;
                    case "train":
                        // 火车票查询
                        bundle = new Bundle();
                        bundle.putString("cityname", SPUtils.getInstance().getString("cityname"));
                        intent = new Intent(getActivity(), TrainticketsActivity.class);
                        intent.putExtras(bundle);
                        getActivity().startActivity(intent);
                        break;
                    case "passengerFlow":
                        // 景区客流量
                        intent = new Intent(getActivity(), PassengerFlowActivity.class);
                        startActivity(intent);
                        break;
                    case "guideQuery":
                        // 导游查询
                        intent = new Intent(getActivity(), GuideActivity.class);
                        getActivity().startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void showLoadingDialog(String msg) {

    }

    @Override
    public void dismissLoadingDialog() {

    }

}
