package com.example.tomasyb.baselib.base.net

import com.example.tomasyb.baselib.base.net.common.DefaultObserver
import com.example.tomasyb.baselib.base.net.entity.BaseResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Kt的一些扩展
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-5-6
 * @since JDK 1.8.0_191
 */


fun <T> Observable<BaseResponse<T>>.execute(method: () -> Unit, defaultObserver: DefaultObserver<T>) {
    this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                method()
            }
            .subscribe(defaultObserver)
}


fun <T> Observable<BaseResponse<T>>.execute(defaultObserver: DefaultObserver<T>) {
    this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(defaultObserver)
}

