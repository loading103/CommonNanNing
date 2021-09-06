package com.daqsoft.commonnanning.ui.main.shop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;

/**
 * 购物
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-6-4.15:50
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_SHOP)
public class ShopActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_shop;
    }

    @Override
    public void initView() {

    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }
}
