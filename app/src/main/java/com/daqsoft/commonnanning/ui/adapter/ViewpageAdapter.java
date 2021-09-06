package com.daqsoft.commonnanning.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * viewpager
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-5-18.17:08
 * @since JDK 1.8
 */

public class ViewpageAdapter extends PagerAdapter {
    private List<View> list_view;
    public ViewpageAdapter(List<View> list_view) {
        this.list_view = list_view;
    }
    @Override
    public int getCount() {
        return list_view.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list_view.get(position));
        return list_view.get(position );
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list_view.get(position));
    }
}
