package com.daqsoft.http.requestapi;


import com.daqsoft.http.HttpCallBack;

/**
 * 请求的接口规范
 *  @author MouJunFeng
 * @time 2018-4-18.
 * @since JDK 1.8
 * @version 1.0.0
 */

public interface IRequestApi<T> {
    /**
     * 显示提示框
     */
    void showLoading();

    /**
     * 请求数据
     * @param url 请求地址
     */
    void request(String url, Object obj, HttpCallBack<T> callBack);

    /**
     * 关闭提示框
     */
    void closeLoading();
}
