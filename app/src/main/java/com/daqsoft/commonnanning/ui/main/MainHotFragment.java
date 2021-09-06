package com.daqsoft.commonnanning.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.IndexScenic;
import com.daqsoft.commonnanning.utils.GlideUtils;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.mvp.BaseFragment;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.widget.img.RoundImageView;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 热门推荐
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-4-3.9:11
 * @since JDK 1.8
 */

public class MainHotFragment extends BaseFragment{

    @BindView(R.id.index_scenic_rv)
    RecyclerView mRvScenic;
    @BindView(R.id.index_hot_va)
    ViewAnimator mVa;
    /**
     * 热门推荐标识0景区1美食2购买
     */
    private int mRecomondTag = 0;

    /**
     * 初始化
     * @param tag
     * @return
     */
    public static MainHotFragment getInstance(int tag){
        MainHotFragment mainHotFragment = new MainHotFragment();
        mainHotFragment.mRecomondTag = tag;
        return mainHotFragment;
    }

    /**
     * 景区的适配器
     */
    private BaseQuickAdapter<IndexScenic, BaseViewHolder> mScenicAdapter;
    @Override
    public void showLoadingDialog(String msg) {

    }

    @Override
    public void dismissLoadingDialog() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_hot;
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
        initScenicAdapter();
    }

    /**
     * 获取数据
     */
    public void getData(){
        if (mRecomondTag==0){
            getIndexScenic();
        }else if (mRecomondTag==1){
            getIndexFood();
        }else {
            getShopping();
        }
    }

    @Override
    protected void initData() {

    }
    /**
     * 初始化热门推荐适配器，包括玩什么，吃什么，哪里买
     */
    private void initScenicAdapter() {
        mRvScenic.setLayoutManager(new GridLayoutManager(getActivity(), 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mScenicAdapter = new BaseQuickAdapter<IndexScenic, BaseViewHolder>(R.layout
                .item_index_scenic, null) {
            @Override
            protected void convert(BaseViewHolder holder, final IndexScenic bean) {
                if (mRecomondTag == 0) {
                    GlideUtils.loadImage(getActivity(),(RoundImageView) holder.getView(R.id.img_index_scenic),bean.getPictureOneToOne(),R.mipmap.common_img_fail_h158);
                } else {
                    GlideUtils.loadImage(getActivity(),(RoundImageView) holder.getView(R.id.img_index_scenic),bean.getCoverOneToOne(),R.mipmap.common_img_fail_h158);
                }
                holder.setOnClickListener(R.id.img_index_scenic, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (mRecomondTag) {
                            // 景区
                            case 0:
                                ARouter.getInstance().build(Constant.ACTIVITY_SCENIC_DETAIL)
                                        .withString("mId", bean.getId()).navigation();
                                break;
                            // 美食
                            case 1:
                                ARouter.getInstance().build(Constant.ACTIVITY_FOOD_DETIAL)
                                        .withString("ID", bean.getId()).navigation();
                                break;
                            // 购物占时没有详情界面
                            case 2:
                                //
                                break;
                            default:
                                break;
                        }
                    }
                });
                holder.setText(R.id.tv_index_scenic, bean.getName());
            }
        };
        View view = getLayoutInflater().inflate(R.layout.include_adapter_footer_more, (ViewGroup)
                mRvScenic.getParent(), false);
        TextView foot = (TextView) view.findViewById(R.id.tv_footer_more);
        foot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 景区
                if (mRecomondTag == 0) {
                    ARouter.getInstance().build(Constant.ACTIVITY_SCENIC).navigation();
                    // 美食
                } else if (mRecomondTag == 1) {
                    ARouter.getInstance().build(Constant.ACTIVITY_FOOD).navigation();
                } else if (mRecomondTag == 2) {
                    // 购物
                    ARouter.getInstance().build(Constant.ACTIVITY_TRAVEL).
                            withInt("pagetype", 1).navigation();
                }
            }
        });
        mScenicAdapter.addFooterView(view);
        mRvScenic.setAdapter(mScenicAdapter);
    }

    /**
     * 获取推荐景区
     * recommendedPriority 推荐优先
     */
    public void getIndexScenic() {
        RetrofitHelper.getApiService().getIndexScenic("1", "3", "1","recommendedPriority")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .subscribe(new Consumer<BaseResponse<IndexScenic>>() {
                    @Override
                    public void accept(BaseResponse<IndexScenic> bean) throws Exception {
                        if (bean.getCode()==0&&bean.getDatas().size()>0){
                            mVa.setDisplayedChild(0);
                            mScenicAdapter.setNewData(bean.getDatas());
                        }else {
                            mVa.setDisplayedChild(1);
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mVa.setDisplayedChild(1);

                    }
                });
    }

    /**
     * 获取美食
     */
    public void getIndexFood() {
        RetrofitHelper.getApiService().getIndexFood("1", "3")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .subscribe(new Consumer<BaseResponse<IndexScenic>>() {
                    @Override
                    public void accept(BaseResponse<IndexScenic> bean) throws Exception {
                        if (bean.getCode()==0&&bean.getDatas().size()>0){
                            mVa.setDisplayedChild(0);
                            mScenicAdapter.setNewData(bean.getDatas());
                        }else {
                            mVa.setDisplayedChild(1);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mVa.setDisplayedChild(1);

                    }
                });
    }

    /**
     * 获取商店
     */
    public void getShopping() {
        RetrofitHelper.getApiService().getShopData("", "1", "3")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .subscribe(new Consumer<BaseResponse<IndexScenic>>() {
                    @Override
                    public void accept(BaseResponse<IndexScenic> bean) throws Exception {
                        if (bean.getCode()==0&&bean.getDatas().size()>0){
                            mVa.setDisplayedChild(0);
                            mScenicAdapter.setNewData(bean.getDatas());
                        }else {
                            mVa.setDisplayedChild(1);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mVa.setDisplayedChild(1);
                    }
                });
    }
}
