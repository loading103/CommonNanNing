package com.example.tomasyb.baselib.base.net.common;

import android.app.Activity;
import android.support.annotation.NonNull;


import com.example.tomasyb.baselib.base.net.dialog.DialogUtils;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by zhpan on 2018/3/22.
 */

public class ProgressUtils {
    static DialogUtils dialogUtils = null;

    public static <T> ObservableTransformer<T, T> applyProgressBar(
            @NonNull final Activity activity, String msg, boolean isCancelProgess) {
        final WeakReference<Activity> activityWeakReference = new WeakReference<>(activity);
        dialogUtils = new DialogUtils();
        dialogUtils.showProgress(activityWeakReference.get());
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                }).doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        Activity context;
                        /*if ((context = activityWeakReference.get()) != null
                                && !context.isFinishing() && isCancelProgess) {
                            dialogUtils.dismissProgress();
                        }*/
                    }
                }).doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Activity context;
                       /* if ((context = activityWeakReference.get()) != null
                                && !context.isFinishing()) {
                            dialogUtils.dismissProgress();
                        }*/
                    }
                });
            }
        };
    }

    public static <T> ObservableTransformer<T, T> applyProgressBar(
            @NonNull final Activity activity, boolean isCancelProgress) {
        return applyProgressBar(activity, "", isCancelProgress);
    }

    /**
     * 隐藏加载框
     */
    public static void dismissProgress() {
        if (dialogUtils != null) {
            dialogUtils.dismissProgress();
        }
    }

    /**
     * 显示加载框
     */
    public static void showProgress(Activity activity) {
        //        if (dialogUtils == null) {
        dialogUtils = new DialogUtils();
        //        }
        dialogUtils.showProgress(activity);
    }
}
