package com.daqsoft.commonnanning.ui.police;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.util.PhoneUtils;

import butterknife.OnClick;
import io.agora.yview.dialog.BaseDialog;

/**
 * 一键报警
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-6-4.15:50
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_ONEPOLICE)
public class OnePoliceActivity extends BaseActivity {
    /**
     * 弹框
     */
    private BaseDialog mPoliceDialog;
    @Override
    public int getLayoutId() {
        return R.layout.activity_one_police;
    }

    @Override
    public void initView() {
        mPoliceDialog = new BaseDialog(this);
        mPoliceDialog.contentView(R.layout.dialog_onepolice)
                .canceledOnTouchOutside(true);
        mPoliceDialog.findViewById(R.id.tv_gophone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPoliceDialog.dismiss();
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[]
                    // permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the
                    // documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                PhoneUtils.call("13778069524");
            }
        });
        mPoliceDialog.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPoliceDialog.dismiss();
            }
        });
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }


    @OnClick({R.id.img_back, R.id.img_police})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_police:
                mPoliceDialog.show();
                break;
        }
    }
}
