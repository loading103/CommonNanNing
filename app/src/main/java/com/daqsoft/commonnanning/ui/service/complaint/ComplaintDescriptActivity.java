package com.daqsoft.commonnanning.ui.service.complaint;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.widget.HeadView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 投诉流程说明页面
 *
 * @author 黄熙
 * @version 1.0.0
 * @time 2018-3-27.
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_COMPLAINT_DESC)
public class ComplaintDescriptActivity extends BaseActivity {
    @BindView(R.id.head_complaint_description)
    HeadView headView;


    @Override
    public int getLayoutId() {
        return R.layout.activity_complaint_descript;
    }

    @Override
    public void initView() {
        headView.setTitle("投诉流程说明");
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    @OnClick({R.id.tv_complaint_description})
    public void onClick(View view) {
        // 我要投诉
        ARouter.getInstance().build(Constant.ACTIVITY_COMPLAINT).navigation();
    }
}
