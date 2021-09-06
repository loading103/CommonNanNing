package com.daqsoft.android;


import com.daqsoft.commonnanning.R;

import cloudbae.loginlibrary.bean.Environment;

/**
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/3/19.
 * @since JDK 1.8
 * 锡林郭勒盟的所有公共配置信息
 */

public class ProjectConfig {
    /**
     * 版本更新地址
     */
    public static final String VERSION_URL = "http://app.daqsoft.com/appserives/Services.aspx";
    /**
     * 小电商商城地址
     * (测试)
     * http://testc.c.xds.daqsoft.com/
     * test3c.c.xds.daqsoft.com
     * http://sub1040832.c.jkxds.net/
     */
    public static final String BASE_HTML_MALL = "http://sub1040832.c.jkxds.net/";
    /**
     * 行程的地址
     * http://scrs.test.daqsoft.com/GISControlYBY/dist/index.html#/
     * http://scrs.daqsoft.com/dist/dist_hqg/dist_vue/index.html#/my-trip
     */
    public static final String ROUTE_HTML = "http://scrs.daqsoft" + "" + "" + "" + "" + ".com" +
            "/dist/dist_hqg/dist_vue/index" + ".html#/";
    /**
     * 行程主题色设置
     */
    public static final String ROUTE_THEME_COLOR = "xl";
    public static final String BASE_HTML_TRAIN = "https://lynnm.wpiao.cn/login/daQi?phone=";

    /**
     * 基础数据请求接口
     * 测试：http://ptisp.test.daqsoft.com/govapi/
     * 正式：http://ptisp.daqsoft.com/govapi/
     * 发哥本地：http://lifm.test.daqsoft.com/
     * 灰度正式：http://b.ptisp.daqsoft.com/govapi/
     * https://nnapp.cloudbae.cn:38080
     */
    public static final String BASE_URL = "http://ptisp.daqsoft.com/govapi/";
    /**
     * 文件上传
     */
    public static final String FILE_UPLOAD = "http://file.geeker.com.cn";

    /**
     * 返回数据的语言格式
     */
    public static final String LANG = "cn";
    /**
     * 锡林郭勒盟登录类型
     * 无
     */
    public static final Environment ANANING_TYPE = null;


    public static String PRIVATER_PATH="https://lyzxpx.xizang.gov.cn/yszc/tibetTourism/";
    /**
     * 站点编码
     * 锡林郭勒盟：xlglmwgw
     */
    public static String SITECODE = "xlglmwgw";
    /**
     * 分享的跟地址
     * http://project.daqsoft.com/h5/hotel/hotel-detail
     * .html?siteCode=ycyjywgw&id=1101046421029885778
     */
    public static final String SHARE_BASE_URL = "http://project.daqsoft" + ".com/h5/gov/";
    /**
     * 酒店分享
     */
    public static final String SHARE_HOTEL_URL =
            SHARE_BASE_URL + "hotel-detail.html?siteCode=" + SITECODE + "&id=";
    /**
     * 美食分享
     */
    public static final String SHARE_FOOD_URL =
            SHARE_BASE_URL + "food-detail.html?siteCode=" + SITECODE + "&id=";
    /**
     * 资讯分享
     */
    public static final String SHARE_NEWS_URL =
            SHARE_BASE_URL + "news-detail.html?siteCode=" + SITECODE + "&id=";
    /**
     * 攻略分享
     */
    public static final String SHARE_STRATEGY_URL = SHARE_BASE_URL + "strategy-detail" +
            ".html?siteCode=" + SITECODE + "&id=";
    /**
     * 景区分享
     */
    public static final String SHARE_SCENIC_URL =
            SHARE_BASE_URL + "scenic-detail.html?siteCode=" + SITECODE + "&id=";
    /**
     * 地区编码
     */
    public static String REGION = "152500";
    /**
     * 微信相关的账号APPID 和secret
     */
    public static String WECHAT_APPID = "wx6e1c323fd18e4f47";
    public static String WECHAT_SECRET = "38f1e08e767d5bdb166d279ae130090a";
    /**
     * QQ相关申请的appID 和secret 应用在微信上申请的ID
     */
    public static String QQ_APPID = "101856280";
    /**
     * 地区名字
     */
    public static String CITY_NAME = "锡林郭勒盟";
    /**
     * 当前地区的经纬度 lat lng
     * 22.787357
     */
    public static String COMMON_LAT = "43.9332000000";
    public static String COMMON_LNG = "116.0477500000";
    /**
     * APPID 版本升级用
     */
    public static final String APPID = "56259";
    /**
     * 科大讯飞的APPID
     */
    public static final String XF_APPID = "5c99d8ec";

    /**
     * 首页底部图片
     */
    public static int[] mIconUnselectIds = {R.mipmap.home_tab_home_normal,
            R.mipmap.home_tab_mdd_normal, R.mipmap.home_tab_find_normal,
            R.mipmap.home_tab_service_normal, R.mipmap.home_tab_mine_normal};
    public static int[] mIconSelectIds = {R.mipmap.home_tab_home_selected,
            R.mipmap.home_tab_mdd_selected, R.mipmap.home_tab_find_selected,
            R.mipmap.home_tab_service_selected, R.mipmap.home_tab_mine_selected};

    /**
     * 7.0之后文件共享
     */
    public static final String AUTHORITIED = "com.daqsoft.common.xlglm.fileprovider";
    /**
     * 在线投诉页面
     */
    public static final String COMPLAINT_HTML_URL = "http://60.31.138" + ".150:18087/complain/app"
            + "/complaints-suggestions.html";
    /**
     * 我的投诉页面
     */
    public static final String MY_COMPLAINT_HTML_URL =
            "http://60.31.138" + ".150:18087/complain" + "/app/code-query.html";
    /**
     * 首页菜单图标集合
     */
    public static final Integer[] INDEX_MENU_ICON = {R.mipmap.home_scenic_spot,
            R.mipmap.home_hotel, R.mipmap.home_museum, R.mipmap.home_library,
            R.mipmap.home_product, R.mipmap.home_parking, R.mipmap.home_toilet,
            R.mipmap.home_travel_agency, R.mipmap.home_weather, R.mipmap.home_shepherd_shouse};
}
