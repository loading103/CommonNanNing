package com.daqsoft.view.guide;

/**
 * 滚动监听
 * @author MouJunFeng
 * @time 2018-3-14
 * @since JDK 1.8
 * @version 1.0.0
 */

public interface ScrollViewListener {
    void onScrollChanged(MyHorizontalScrollView scrollView, int x, int y, int oldx, int oldy);
}
