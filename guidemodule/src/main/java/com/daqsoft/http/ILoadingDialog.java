package com.daqsoft.http;

/**
 * 数据加载时需要显示的提示框
 * @author MouJunFeng
 * @time 2018-3-14
 * @since JDK 1.8
 * @version 1.0.0
 */

public interface ILoadingDialog {
    /**
     * 显示提示框
     */
    void showLoading();

    /**
     * 关闭提示框
     */
    void closeLoading();

    /**
     * 判断提示框是否显示
     */
    boolean isLoading();

    /**
     * 是否需要显示提示框
     */
    boolean isShowLoading();
}
