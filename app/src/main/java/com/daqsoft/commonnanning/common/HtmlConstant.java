package com.daqsoft.commonnanning.common;

import com.daqsoft.android.ProjectConfig;

/**
 * 调转小电商的网页链接统一管理类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/3/28 0028
 * @since JDK 1.8
 */
public class HtmlConstant {

    /**
     * 我的订单
     */
    public static final String HTML_MINE_ORDER = ProjectConfig.BASE_HTML_MALL + "order?";
    /**
     * 服务协议
     */
    public static final String HTML_SERVICE = "http://file.geeker.com" +
            ".cn/uploadfile/app/1569722301424/agreement.html";

    /**
     * 我的电子票
     */
    public static final String HTML_MINE_TICKET = ProjectConfig.BASE_HTML_MALL + "ucenter/ticket?";

    /**
     * 我的退款
     */
    public static final String HTML_MINE_REFUND_LIST = ProjectConfig.BASE_HTML_MALL + "refund" +
            "/list?";


    /**
     * 我的收货地址
     */
    public static final String HTML_MINE_ADDRESS = ProjectConfig.BASE_HTML_MALL + "ucenter" +
            "/address?";
    /**
     * 常用信息
     */
    public static final String HTML_MINE_CONTACTS = ProjectConfig.BASE_HTML_MALL + "user/contacts?";

    /**
     * 直通车
     * http://sub7974815.c.jkxds.net/goods/group?id=340&more=1
     */
    public static final String TRAIN = ProjectConfig.BASE_HTML_MALL + "goods/group?id=340&more=1";
    /**
     * 锡林郭勒盟的拼组团
     */
    public static final String GROUP = ProjectConfig.BASE_HTML_MALL + "goods/group?id=855&more=1";


}
