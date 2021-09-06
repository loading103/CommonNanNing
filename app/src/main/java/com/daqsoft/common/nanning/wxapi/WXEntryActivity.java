package com.daqsoft.common.nanning.wxapi;


import com.daqsoft.commonnanning.common.SocialUtil;

import io.agora.yshare.SocialHelper;
import io.agora.yshare.WXHelperActivity;

/**
 * 微信登录回调类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/3/20 0020
 * @since JDK 1.8
 */
public class WXEntryActivity extends WXHelperActivity {

    @Override
    protected SocialHelper getSocialHelper() {
        return SocialUtil.INSTANCE.socialHelper;
    }
}
