package com.daqsoft.android;


import com.daqsoft.commonnanning.R;

import cloudbae.loginlibrary.bean.Environment;

/**
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/3/19.
 * @since JDK 1.8
 * 南宁的所有公共配置信息
 */

public class ProjectConfig {
    /**
     * 小电商商城地址
     * (测试)
     * http://testc.c.xds.daqsoft.com/
     * test3c.c.xds.daqsoft.com
     * http://192.168.7.170:1111/(本地)
     * http://sub7974815.c.jkxds.net/（南宁正式）
     * http://sub7974815.c.jkxds.net/
     */
    public static final String BASE_HTML_MALL = "http://sub7974815.c.jkxds.net/";
    /**
     * 直通车新的网页地址
     * https://lynnm.wpiao.cn/login/daQi?phone=871cc44eebfcf628cc15dc88b4e382b9&targetUrl=/throughTrain
     */
    //public static final String BASE_HTML_TRAIN = "http://lynn1.wpiao.cn/throughTrain";
    /**
     * 三方电商
     * 测试 https://lynnm.wpiao.cn/login/daQi?phone=
     * lynn1.wpiao.cn
     * http://scrs.test.daqsoft.com/GISControlYBY/dist/index.html#/
     */
    public static final String BASE_HTML_TRAIN = "https://lynn1.wpiao.cn/login/daQi?phone=";
    /**
     * 行程的地址
     * http://scrs.test.daqsoft.com/GISControlYBY/dist/index.html#/
     * http://scrs.daqsoft.com/dist/dist_hqg/dist_vue/index.html#/my-trip
     */
    public static final String ROUTE_HTML = "http://scrs.daqsoft.com/dist/dist_hqg/dist_vue/index.html#/";
    /**
     * 行程主题色设置
     */
    public static final String ROUTE_THEME_COLOR = "nn";

    /**
     * 基础数据请求接口
     * 测试：http://ptisp.test.daqsoft.com/govapi/
     * 正式：http://ptisp.daqsoft.com/govapi/
     * 发哥本地：http://lifm.test.daqsoft.com/
     * 灰度正式：http://b.ptisp.daqsoft.com/govapi/
     * https://nnapp.cloudbae.cn:38080
     * 202
     * http://202.103.199.213:81/govapi/
     * 本地
     * http://app3.nnlyxxw.com/govapi/
     */
//    public static final String BASE_URL = "http://ptisp.daqsoft.com/govapi/";
    //public static final String BASE_URL = "http://192.168.2.118:8013/";
    //public static final String BASE_URL = "http://192.168.2.84:8013/";
    //public static final String BASE_URL = "http://app3.nnlyxxw.com/govapi/";
    //public static final String BASE_URL = "http://ptisp.test.daqsoft.com/govapi/";
    //public static final String BASE_URL = "http://ptisp.test.daqsoft.com/121new/govapi/";
    //public static final String BASE_URL = "http://b.ptisp.daqsoft.com/govapi/";
    // 唐红梅
    //public static final String BASE_URL = "http://10.252.251.35:8013/";
    public static final String BASE_URL = "https://app2.nnlyxxw.com/govapi/";


    /**
     * 文件上传
     */
    public static final String FILE_UPLOAD = "http://file.geeker.com.cn";

    /**
     * 返回数据的语言格式
     */
    public static final String LANG = "cn";
    /**
     * 爱南宁登录类型
     * Environment.TEST 测试
     * Environment.RELEASE 正式
     */
    public static final Environment ANANING_TYPE = Environment.RELEASE;
    /**
     * 站点编码
     * 南宁：nngjapp
     * 西安：xazxwApp
     * 句容：jsjrwwz
     * 乌鲁木齐：wlmwwz
     * 察尔汗：cehmhyhwgw
     */
    public static String SITECODE = "nngjapp";
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
    public static final String SHARE_NEWS_URL = "";
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
     * 句容 321183
     * 乌鲁木齐 650100
     * 南宁450100
     */
    public static String REGION = "450100";
    /**
     * 微信相关的账号APPID 和secret
     */
    public static String WECHAT_APPID = "wx68acf21b7aaac37f";
    public static String WECHAT_SECRET = "13333f23bf28c65b38dc40783da577e8";
    /**
     * QQ相关申请的appID 和secret 应用在微信上申请的ID
     */
    public static String QQ_APPID = "101558913";
    /**
     * 地区名字
     */
    public static String CITY_NAME = "南宁";
    /**
     * 当前地区的经纬度 lat lng
     * 22.787357
     */
    public static String COMMON_LAT = "22.81771";
    public static String COMMON_LNG = "108.366397";
    /**
     * APPID 版本升级用 南宁本地用
     */
    public static final String APPID = "25928";
    /**
     * 版本更新地址
     * 本地更新
     */
    public static final String VERSION_URL = "http://nnlyxxw.com/appserives/Services.aspx";

    /**
     * 公司内部更新
     */
    //public static final String APPID = "64128";
    /**
     * 版本更新地址
     */
    //public static final String VERSION_URL = "http://app.daqsoft.com/appserives/Services.aspx";



    /**
     * 科大讯飞的APPID
     */
    public static final String XF_APPID = "5c99d8ec";

    /**
     * 首页底部图片
     */
    public static int[] mIconUnselectIds = {R.mipmap.home_tab_home_normal, R.mipmap
            .home_tab_find_normal, R.mipmap.home_tab_service_normal, R.mipmap.home_tab_mine_normal};
    public static int[] mIconSelectIds = {R.mipmap.home_tab_home_selected, R.mipmap
            .home_tab_find_selected, R.mipmap.home_tab_service_selected, R.mipmap
            .home_tab_mine_selected};

    /**
     * 7.0之后文件共享
     */
    public static final String AUTHORITIED = "com.daqsoft.common.nanning.fileprovider";
    /**
     * 在线投诉页面
     */
    public static final String COMPLAINT_HTML_URL =
            "https://mucomplain.12301.cn/view/complaintweixin";
    /**
     * 我的投诉页面
     */
    public static final String MY_COMPLAINT_HTML_URL =
            "https://mucomplain.12301.cn/view/complaintmobile?type=weixin#/valid";

    /**
     * 首页菜单图标集合
     */
    public static final Integer[] INDEX_MENU_ICON = {R.mipmap.home_parking, R.mipmap.home_home, R
            .mipmap.home_weather, R.mipmap.home_museum, R.mipmap.home_library, R.mipmap
            .home_legacy_list,R.mipmap
            .home_scenic_spot, R.mipmap.home_hotel, R.mipmap.home_travel_agency, R.mipmap
            .home_through_train2};
}
