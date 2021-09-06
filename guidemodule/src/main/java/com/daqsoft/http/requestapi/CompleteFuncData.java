package com.daqsoft.http.requestapi;

/**
 * Created by huangx on 2016/7/13.
 */
public interface CompleteFuncData {
    /**
     * 请求完成后回调
     *
     * @param result
     *            返回的数据 返回2:表示没网,返回3:表示返回数据为""(空)
     */
    public void success(String result);
}