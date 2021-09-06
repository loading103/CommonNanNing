package com.daqsoft.utils.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * 自定义文件下载类
 * @author MouJunFeng
 * @time 2018-4-25
 * @since JDK 1.8
 * @version 1.0.0
 */

public class MyImageLoader {
    /**
     * 文件下载
     */
    private ImageLoader imageLoader;
    /**
     * 下载参数配置
     */
    private DisplayImageOptions options;

    public MyImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                //// default = device screen dimensions 内存缓存文件的最大长宽
                .memoryCacheExtraOptions(480, 800)
                // 本地缓存的详细信息(缓存的最大长宽)，最好不要设置这个
                .diskCacheExtraOptions(480, 800, null)
                // default 设置当前线程的优先级
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .denyCacheImageMultipleSizesInMemory()
                //可以通过自己的内存缓存实现
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                // 内存缓存的最大值
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13)
                // 50 Mb sd卡(本地)缓存的最大值
                .diskCacheSize(50 * 1024 * 1024)
                // 可以缓存的文件数量
                .diskCacheFileCount(100)
                // default为使用HASHCODE对UIL进行加密命名， 还可以用MD5(new Mdp_5FileNameGenerator())加密
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .imageDownloader(new BaseImageDownloader(context))
                .imageDecoder(new BaseImageDecoder(false))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .writeDebugLogs() // 打印debug log
                .build(); //开始构建
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();
    }

    /**
     * 加载图片
     * @param uri 地址
     * @param imageView 控件
     */
    public void loadImage(String uri, ImageView imageView) {
        imageLoader.displayImage(uri, imageView);
    }

    /**
     * 加载图片
     * @param uri 地址
     * @param options 配置
     * @param listener 回调
     */
    public void loadImage(String uri, DisplayImageOptions options, ImageLoadingListener listener) {
        if (options == null) {
            DisplayImageOptions displayImageOptions = getDisplayImageOptions();
            imageLoader.loadImage(uri, displayImageOptions, listener);
        } else {
            imageLoader.loadImage(uri, options, listener);
        }

    }

    /**
     * 设置默认的配置
     * @return
     */
    public DisplayImageOptions getDisplayImageOptions() {
        if (options != null) {
            return options;
        }
        options = new DisplayImageOptions.Builder()
                // default 设置图片在加载前是否重置、复位
                .resetViewBeforeLoading(false)
                // default  设置下载的图片是否缓存在内存中
                .cacheInMemory(false)
                // default  设置下载的图片是否缓存在SD卡中
                .cacheOnDisk(false)
                // default 设置图片以如何的编码方式显示
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                // default 设置图片的解码类型
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                // default  还可以设置圆角图片new RoundedBitmapDisplayer(20)
                .displayer(new SimpleBitmapDisplayer())
                .imageScaleType(ImageScaleType.NONE)
                .build();
        return options;
    }
}
