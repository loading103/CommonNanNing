package com.example.tomasyb.baselib.widget.coordinatortablayout;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.tomasyb.baselib.R;

/**
 *
 */

public class CoordinatorTabLayout extends CoordinatorLayout {
    private int[] mImageArray, mColorArray;

    private Context mContext;
    private Toolbar mToolbar;
    private ActionBar mActionbar;
    private TabLayout mTabLayout;
    private ImageView mImageView;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private LoadHeaderImagesListener mLoadHeaderImagesListener;
    private OnTabSelectedListener mOnTabSelectedListener;

    public CoordinatorTabLayout(Context context) {
        super(context);
        mContext = context;
    }

    public CoordinatorTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        if (!isInEditMode()) {
            initView(context);
            initWidget(context, attrs);
        }
    }

    public CoordinatorTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        if (!isInEditMode()) {
            initView(context);
            initWidget(context, attrs);
        }
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_coordinatortablayout, this, true);
        initToolbar();
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingtoolbarlayout);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mImageView = (ImageView) findViewById(R.id.imageview);
    }

    private void initWidget(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs
                , R.styleable.CoordinatorTabLayout);

        TypedValue typedValue = new TypedValue();
        mContext.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        int contentScrimColor = typedArray.getColor(
                R.styleable.CoordinatorTabLayout_contentScrim, typedValue.data);
        mCollapsingToolbarLayout.setContentScrimColor(contentScrimColor);

        int tabIndicatorColor = typedArray.getColor(R.styleable.CoordinatorTabLayout_tabIndicatorColor, Color.WHITE);
        mTabLayout.setSelectedTabIndicatorColor(tabIndicatorColor);

        int tabTextColor = typedArray.getColor(R.styleable.CoordinatorTabLayout_tabTextColor, Color.WHITE);
        mTabLayout.setTabTextColors(ColorStateList.valueOf(tabTextColor));
        typedArray.recycle();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        ((AppCompatActivity) mContext).setSupportActionBar(mToolbar);
        mActionbar = ((AppCompatActivity) mContext).getSupportActionBar();
    }

    /**
     * ??????Toolbar??????
     *
     * @param title ??????
     * @return CoordinatorTabLayout
     */
    public CoordinatorTabLayout setTitle(String title) {
        if (mActionbar != null) {
            mActionbar.setTitle(title);
        }
        return this;
    }

    /**
     * ??????Toolbar???????????????????????????
     *
     * @param canBack ????????????
     * @return CoordinatorTabLayout
     */
    public CoordinatorTabLayout setBackEnable(Boolean canBack) {
        if (canBack && mActionbar != null) {
            mActionbar.setDisplayHomeAsUpEnabled(true);
            mActionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
        }
        return this;
    }

    /**
     * ????????????tab?????????????????????
     *
     * @param imageArray ????????????
     * @return CoordinatorTabLayout
     */
    public CoordinatorTabLayout setImageArray(@NonNull int[] imageArray) {
        mImageArray = imageArray;
        return this;
    }

    /**
     * ????????????tab????????????????????????ContentScrimColor
     *
     * @param imageArray ????????????
     * @param colorArray ContentScrimColor??????
     * @return CoordinatorTabLayout
     */
    public CoordinatorTabLayout setImageArray(@NonNull int[] imageArray, @NonNull int[] colorArray) {
        mImageArray = imageArray;
        mColorArray = colorArray;
        return this;
    }

    /**
     * ????????????tab?????????ContentScrimColor
     *
     * @param colorArray ????????????
     * @return CoordinatorTabLayout
     */
    public CoordinatorTabLayout setContentScrimColorArray(@NonNull int[] colorArray) {
        mColorArray = colorArray;
        return this;
    }

    private void setupTabLayout() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mImageView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_dismiss));
                if (mLoadHeaderImagesListener == null) {
                    if (mImageArray != null) {
                        mImageView.setImageResource(mImageArray[tab.getPosition()]);
                    }
                } else {
                    mLoadHeaderImagesListener.loadHeaderImages(mImageView, tab);
                }
                if (mColorArray != null) {
                    mCollapsingToolbarLayout.setContentScrimColor(
                            ContextCompat.getColor(
                                    mContext, mColorArray[tab.getPosition()]));
                }
                mImageView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_show));

                if (mOnTabSelectedListener != null) {
                    mOnTabSelectedListener.onTabSelected(tab);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (mOnTabSelectedListener != null) {
                    mOnTabSelectedListener.onTabUnselected(tab);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (mOnTabSelectedListener != null) {
                    mOnTabSelectedListener.onTabReselected(tab);
                }
            }
        });
    }

    /**
     * ??????TabLayout TabMode
     *
     * @param mode
     * @return CoordinatorTabLayout
     */
    public CoordinatorTabLayout setTabMode(@TabLayout.Mode int mode) {
        mTabLayout.setTabMode(mode);
        return this;
    }

    /**
     * ???????????????????????????ViewPager
     *
     * @param viewPager ???TabLayout?????????ViewPager
     * @return CoordinatorTabLayout
     */
    public CoordinatorTabLayout setupWithViewPager(ViewPager viewPager) {
        setupTabLayout();
        mTabLayout.setupWithViewPager(viewPager);
        return this;
    }

    /**
     * ?????????????????????ActionBar
     */
    public ActionBar getActionBar() {
        return mActionbar;
    }

    /**
     * ?????????????????????TabLayout
     */
    public TabLayout getTabLayout() {
        return mTabLayout;
    }

    /**
     * ?????????????????????ImageView
     */
    public ImageView getImageView() {
        return mImageView;
    }

    /**
     * ??????LoadHeaderImagesListener
     *
     * @param loadHeaderImagesListener ??????LoadHeaderImagesListener
     * @return CoordinatorTabLayout
     */
    public CoordinatorTabLayout setLoadHeaderImagesListener(LoadHeaderImagesListener loadHeaderImagesListener) {
        mLoadHeaderImagesListener = loadHeaderImagesListener;
        return this;
    }

    /**
     * ??????onTabSelectedListener
     *
     * @param onTabSelectedListener ??????onTabSelectedListener
     * @return CoordinatorTabLayout
     */
    public CoordinatorTabLayout addOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        mOnTabSelectedListener = onTabSelectedListener;
        return this;
    }

    /**
     * ?????????????????????
     *
     * @param activity ???????????????activity
     * @return CoordinatorTabLayout
     */
    public CoordinatorTabLayout setTranslucentStatusBar(@NonNull Activity activity) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return this;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            activity.getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow()
                    .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (mToolbar != null) {
            MarginLayoutParams layoutParams = (MarginLayoutParams) mToolbar.getLayoutParams();
            layoutParams.setMargins(
                    layoutParams.leftMargin,
                    layoutParams.topMargin + SystemView.getStatusBarHeight(activity),
                    layoutParams.rightMargin,
                    layoutParams.bottomMargin);
        }

        return this;
    }

    /**
     * ???????????????
     *
     * @param activity ???????????????activity
     * @return CoordinatorTabLayout
     */
    public CoordinatorTabLayout setTranslucentNavigationBar(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return this;
        } else {
            mToolbar.setPadding(0, SystemView.getStatusBarHeight(activity) >> 1, 0, 0);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        return this;
    }

}