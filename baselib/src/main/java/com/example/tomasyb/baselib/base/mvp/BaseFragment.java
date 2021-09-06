package com.example.tomasyb.baselib.base.mvp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * des:基类fragment
 * Created by yanb
 * on 2016.07.12:38
 */
public abstract  class BaseFragment<P extends IBasePresenter> extends Fragment implements IBaseView {
    protected View rootView;
    public P mPresenter;
    public Unbinder mUnbinder;//黄油刀
    private Context context;
    public Activity mActivity;
    private CompositeDisposable mCompositeDisposable;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        mActivity = (Activity) context;

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null){
            rootView = inflater.inflate(getLayoutId(), container, false);
            mUnbinder = ButterKnife.bind(this,rootView);
            initView(rootView,savedInstanceState);
            initData();
        }
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = initPresenter();
    }


    //获取布局文件
    protected abstract int getLayoutId();
    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract P initPresenter();
    //初始化view
    protected abstract void initView(View view, @Nullable Bundle savedInstanceState);
    protected abstract void initData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder !=null){
            mUnbinder.unbind();
        }
        if (mPresenter != null){
            mPresenter.detach();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.rootView = null;
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
}
