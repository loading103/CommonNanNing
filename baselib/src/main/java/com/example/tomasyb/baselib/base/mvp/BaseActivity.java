package com.example.tomasyb.baselib.base.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.tomasyb.baselib.R;
import com.example.tomasyb.baselib.base.AppManager;
import com.example.tomasyb.baselib.util.StatusBarUtil;
//import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Activity基类
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-5-30.10:23
 * @since JDK 1.8
 */
public abstract class BaseActivity<P extends IBasePresenter> extends AppCompatActivity implements
        IBaseView {
    protected P presenter;
    public Context mContext;
    public Unbinder mUnbinder;//黄油刀
    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        doBeforeSetContentView();
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        presenter = initPresenter();
        this.initView();
    }

    //---------------------------------------------------------------------子类实现

    /**
     * 获取布局ID
     */
    public abstract int getLayoutId();

    /**
     * 初始化
     */
    public abstract void initView();

    /**
     * 初始化Presenter
     *
     * @return
     */
    public abstract P initPresenter();

    /**
     * 设置布局前设置
     */
    public void doBeforeSetContentView() {
        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
        int color = getResources().getColor(R.color.b_main_white);
        StatusBarUtil.setColor(this, color, 0);
        StatusBarUtil.setLightMode(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    /**
     * 销毁
     */
    @Override
    protected void onDestroy() {
        // 将当前activity移除管理栈
        AppManager.getAppManager().removeActivity(this);
        if (presenter != null) {
            // 在presenter中解绑释放view
            presenter.detach();
            presenter = null;
        }
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        unDisposable();
        super.onDestroy();
    }

    @Override
    public void showLoadingDialog(String msg) {

    }

    @Override
    public void dismissLoadingDialog() {

    }

    /**
     * 将Disposable添加
     *
     * @param subscription
     */
    public void addDisposable(Disposable subscription) {
        // csb 如果解绑了的话添加 sb 需要新的实例否则绑定时无效的
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    /**
     * 在界面退出等需要解绑观察者的情况下调用此方法统一解绑，防止Rx造成的内存泄漏
     */
    public void unDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }
}
