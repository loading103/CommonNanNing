package com.daqsoft.commonnanning.utils;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.daqsoft.android.ProjectConfig;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.SocialUtil;
import com.example.tomasyb.baselib.util.LogUtils;

import java.util.ArrayList;

import io.agora.yshare.SocialHelper;
import io.agora.yshare.callback.SocialShareCallback;
import io.agora.yshare.entities.QQShareEntity;
import io.agora.yshare.entities.ShareEntity;
import io.agora.yshare.entities.WXShareEntity;
import io.agora.yview.dialog.BaseDialog;

/**
 * 分享工具
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2020/3/11 0011
 * @since JDK 1.8
 */
public class ShareUtils {
    /**
     * 三方登录
     */
    private static SocialHelper socialHelper;

    /**
     * 初始化弹框
     */
    public static BaseDialog ininShareDialog(Activity activity, String url, String name,
                                             String strategyId, String sharePicture,
                                             String submin) {
        socialHelper = SocialUtil.INSTANCE.socialHelper;
        BaseDialog mShareDialog = new BaseDialog(activity);
        mShareDialog.contentView(R.layout.dialog_bottom_share).layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)).gravity(Gravity.BOTTOM).animType(BaseDialog.AnimInType.BOTTOM).canceledOnTouchOutside(true);
        mShareDialog.findViewById(R.id.img_qq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShareDialog.dismiss();
                ShareEntity appInfo = QQShareEntity.createImageTextInfo(name, url + strategyId,
                        sharePicture, submin, ProjectConfig.CITY_NAME);
                LogUtils.e("你分享的地址---》" + ProjectConfig.SHARE_BASE_URL + strategyId);
                // QQ
                socialHelper.shareQQ(activity, appInfo, new SocialShareCallback() {
                    @Override
                    public void shareSuccess(int type) {

                    }

                    @Override
                    public void socialError(String msg) {

                    }
                });
            }
        });
        mShareDialog.findViewById(R.id.img_qqzone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShareDialog.dismiss();
                ArrayList<String> imglist = new ArrayList<>();
                imglist.add(sharePicture);
                ShareEntity appInfo = QQShareEntity.createImageTextInfoToQZone(name,
                        url + strategyId, imglist, submin, ProjectConfig.CITY_NAME);

                // QQ空间
                socialHelper.shareQQ(activity, appInfo, new SocialShareCallback() {
                    @Override
                    public void shareSuccess(int type) {

                    }

                    @Override
                    public void socialError(String msg) {

                    }
                });
            }
        });
        mShareDialog.findViewById(R.id.img_weix).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShareDialog.dismiss();
                ShareEntity webPageInfo = WXShareEntity.createWebPageInfo(false, url + strategyId
                        , sharePicture, name, submin);

                socialHelper.shareWX(activity, webPageInfo, new SocialShareCallback() {
                    @Override
                    public void shareSuccess(int type) {

                    }

                    @Override
                    public void socialError(String msg) {

                    }
                });
            }
        });
        mShareDialog.findViewById(R.id.img_weixzone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShareDialog.dismiss();
                ShareEntity webPageInfo = WXShareEntity.createWebPageInfo(true,
                        ProjectConfig.SHARE_BASE_URL + strategyId, sharePicture, name, submin);

                socialHelper.shareWX(activity, webPageInfo, new SocialShareCallback() {
                    @Override
                    public void shareSuccess(int type) {

                    }

                    @Override
                    public void socialError(String msg) {

                    }
                });
            }
        });
        return mShareDialog;
    }

}
