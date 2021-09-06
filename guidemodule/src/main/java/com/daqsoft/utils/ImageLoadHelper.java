package com.daqsoft.utils;

import android.databinding.BindingAdapter;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 图片加载
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-5-3
 * @since JDK 1.8
 */

public class ImageLoadHelper {
    @BindingAdapter({"bind:imageUrl"})
    public final static void imageLoad(SimpleDraweeView view , String url){
        view.setImageURI(url);
    }
}
