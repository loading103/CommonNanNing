package com.daqsoft.commonnanning.ui.destination;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.view.MyGridview;
import com.example.tomasyb.baselib.base.glide.GlideApp;
import com.example.tomasyb.baselib.base.mvp.BaseFragment;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.util.LogUtils;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.widget.HeadView;
import com.zhouwei.mzbanner.MZBannerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * -----------------------------------------------------------------
 *
 * @version 1.0.1
 * @Description:目的地页面
 * @Author 董彧傑
 * @CreateDate 2019-4-9
 * @since jdk1.8.0_201
 * -----------------------------------------------------------------
 */
public class DestinationFragment extends BaseFragment {


    @BindView(R.id.head_destination_title)
    HeadView title;
    @BindView(R.id.iv_destination_banner)
    ImageView ivBanner;
    @BindView(R.id.iv_destination_list)
    ImageView ivList;
    @BindView(R.id.ll_destination_banner)
    LinearLayout llBanner;
    @BindView(R.id.ll_destination_list)
    LinearLayout llList;
    @BindView(R.id.banner_destination)
    MZBannerView banner;
    @BindView(R.id.gv_destination_list)
    MyGridview gridview;
    Unbinder unbinder;
    View view;
    /**
     * 目的地首页列表数据集合
     */
    private List<DestinationInfoEntity> destinationListEntityInfo = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_destination;
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
        title.setTitle("目的地");
        title.setBackHidden(false);
        getData();

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


    @OnClick({R.id.iv_destination_banner, R.id.iv_destination_list})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_destination_banner:
                // banner翻页
                llBanner.setVisibility(View.GONE);
                llList.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_destination_list:
                // gridview列表页
                llBanner.setVisibility(View.VISIBLE);
                llList.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    /**
     * 获取数据
     */
    @SuppressLint("CheckResult")
    public void getData() {
        RetrofitHelper.getApiService()
                .getDestinationList("1000")
                .doOnSubscribe(disposable -> addDisposable(disposable))
                .subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe(bean -> {
            if (ObjectUtils.isNotEmpty(bean)) {
                destinationListEntityInfo.clear();
                destinationListEntityInfo = bean.getDatas();
                /**
                 * banner页面数据嵌套
                 */
                banner.setIndicatorVisible(false);
                banner.setVisibility(View.VISIBLE);
                banner.setPages(destinationListEntityInfo, () -> new BannerDestinationViewHolder());
                /**
                 * gridView页面数据嵌套
                 */
                setCommonAdapter();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                LogUtils.e("" + throwable);
            }
        });

        /***/
    }

    /**
     * 设置数据GridView
     */
    public void setCommonAdapter() {
        GridAdapter<DestinationInfoEntity> commonAdapter = new GridAdapter<DestinationInfoEntity>
                (getActivity(), destinationListEntityInfo, R.layout.item_destination_list) {

            @Override
            public void convert(GridViewHolder holder, final DestinationInfoEntity resource) {
                holder.setText(R.id.tv_item_destination_name, resource.getRegionName());
                holder.setText(R.id.tv_item_destination_sum, resource.getViewCount() + "人浏览");
                GlideApp.with(mContext).load(resource.getCoverOneToOne()).placeholder(R.mipmap
                        .common_img_fail_h158).error(R.mipmap.common_img_fail_h158).into(
                        (ImageView) holder.getView(R.id.iv_item_destination_banner));
                holder.setOnClickListener(R.id.ll_item_destination_list, view -> {
                    ARouter.getInstance().build(Constant.ACTIVITY_DESTINATION_DETAILS)
                            .withString("name", resource.getRegionName())
                            .withString("region", resource.getRegion())
                            .withString("lat", resource.getLatitude())
                            .withString("lng", resource.getLongitude())
                            .navigation();
                });
            }
        };


        gridview.setAdapter(commonAdapter);
    }


    /**
     * 进度加载框
     */
    private AlertDialog alertDialog = null;


    /**
     * 隐藏加载框
     */
    public void dismiss() {
        alertDialog.dismiss();
    }
}
