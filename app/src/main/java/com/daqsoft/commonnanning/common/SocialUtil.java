package com.daqsoft.commonnanning.common;


import com.daqsoft.android.ProjectConfig;

import io.agora.yshare.SocialHelper;

/**
 * 第三方登录对象初始化
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/3/20 0020
 * @since JDK 1.8
 */
public enum SocialUtil {
    INSTANCE();

    public SocialHelper socialHelper;

    SocialUtil() {
        socialHelper = new SocialHelper.Builder()
                .setQqAppId(ProjectConfig.QQ_APPID)
                .setWxAppId(ProjectConfig.WECHAT_APPID)
                .setWxAppSecret(ProjectConfig.WECHAT_SECRET)
                .setWbAppId("")
                .setWbRedirectUrl("")
                .build();
    }
}
