package com.daqsoft.commonnanning.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.ComUtils;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.ScenicChildDetail;
import com.example.tomasyb.baselib.base.glide.GlideApp;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.web.ProgressWebView;
import com.example.tomasyb.baselib.widget.HeadView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 子景点详情
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-6-4.15:50
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_SCENIC_CHILD_DETAIL)
public class ScenicChildDetailActivity extends BaseActivity {
    @Autowired(name = "mId")
    String strategyId;
    @BindView(R.id.head)
    HeadView mHead;
    @BindView(R.id.img_top)
    ImageView imgTop;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.va)
    ViewAnimator va;
    @BindView(R.id.webView)
    ProgressWebView webView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_scenic_child_detail;
    }

    @Override
    public void initView() {
        mHead.setTitle("景点详情");
        mHead.setBackListener(new HeadView.OnBackListener() {
            @Override
            public void onBack(View v) {
                finish();
            }
        });
        ARouter.getInstance().inject(this);
        getData();
    }

    /**
     * 获取数据
     */
    private void getData() {
        RetrofitHelper.getApiService().getScenicChildDetail(strategyId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<ScenicChildDetail>>() {
                    @Override
                    public void accept(BaseResponse<ScenicChildDetail>
                                               bean) throws Exception {
                        if (bean.getCode()==0){
                            va.setDisplayedChild(0);
                            GlideApp.with(mContext).load(bean.getData().getCoverTwoToOne())
                                    .placeholder(R.mipmap.common_img_fail_h158)
                                    .error(R.mipmap.common_img_fail_h158)
                                    .into(imgTop);
                            tvTitle.setText(bean.getData().getName());
                            webView.loadData(ComUtils.getNewContent(bean.getData().getDescribe()),
                                    ComUtils.WEBVIEW_TYPE, null);
                        }else {
                            va.setDisplayedChild(1);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        va.setDisplayedChild(1);
                    }
                });
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
