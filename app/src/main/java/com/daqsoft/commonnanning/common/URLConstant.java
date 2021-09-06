package com.daqsoft.commonnanning.common;

import com.daqsoft.android.ProjectConfig;

/**
 * 网络请求接口常量类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/3/19 0019
 * @since JDK 1.8
 */
public class URLConstant {

    /**
     * 网络投票
     */
    public static final String VOTE_HTML_URL = "http://project.daqsoft" + ".com/test/xlglm/#/vote"
            + "-list?token=";
    /**
     * 网络投票
     */
    public static final String RECRUIT_HTML_URL = "http://project.daqsoft" + ".com/test/xlglm" +
            "/#/recruit" + "-list?token=";
    /**
     * 本地网页地址
     */
    public static final String HTML_URL = "file:///android_asset/web/list.html";
    /**
     * 图书馆网页
     */
    public static final String BOOK_HTML_URL = HTML_URL + "?typeId=56&locationCode=book";
    /**
     * 博物馆网页
     */
    public static final String MUSEUM_HTML_URL = HTML_URL + "?typeId=55&locationCode=museum";
    /**
     * 每页条数
     */
    public static final int LIMITPAGE = 15;
    /**
     * 首页公告栏目编码
     */
    public static final String ACTION_TAG = "/gov/app";

    public static final String ROUTE_URL = ProjectConfig.ROUTE_HTML + "my-trip";
    /**
     * 日历选择地址
     */
    public static final String TRIP_URL = ProjectConfig.ROUTE_HTML + "calendar/";

    /**
     * 我的投诉查询界面
     */
    public static final String COMPLAINT_HTML_URL_ME =
            "http://111.19.162" + ".13:18090/complain" + "/app/code-query.html";
    /**
     * 文件上传
     */
    public static String FILE_UPLOAD = "http://file.geeker.com.cn";
    /**
     * 获取目的地推荐美食
     */
    public static final String DESTINATION_FOOD = "api" + ACTION_TAG + "/destination/foods";
    /**
     * 获取目的地推荐酒店
     */
    public static final String DESTINATION_HOTEL = "api" + ACTION_TAG + "/destination/hotels";
    /**
     * 获取目的地推荐景区
     */
    public static final String DESTINATION_SCENERY = "api" + ACTION_TAG + "/destination/scenerys";
    /**
     * 节庆活动列表接口
     */
    public static final String ACTIVITY_LIST = "api" + ACTION_TAG + "/activity/list";
    /**
     * 活动列表
     */
    public static final String ACTIVITY_LIST2 = "api/activity/list";
    /**
     * 节庆活动详情
     */
    public static final String ACTIVITY_DETAILS = "api/gov/app/activity/detail";
    /**
     * token拦截
     */
    public static final String TOKEN_INTERCEPT = "api/gov/app/member/checkToken";
    /**
     * 旅行社列表接口
     */
    public static final String TRAVEL_AGENCY_LIST = "api" + ACTION_TAG + "/travel/list";
    /**
     * 获取周边资源（身边游）
     */
    public static final String NEAR_LIST = "api" + ACTION_TAG + "/scenery/around";
    /**
     * 我的攻略列表
     */
    public static final String MY_STRATEGY_LIST = "api" + ACTION_TAG + "/travelStrategy/list";
    /**
     * 获取地区接口
     */
    public static final String SITE_REGIONS = "api" + ACTION_TAG + "/common/siteRegions";
    /**
     * 博物馆
     */
    public static final String LIBARY_BO_LIST = "api/gov/app/cultureMuseum/list";
    /**
     * 图书馆
     */
    public static final String LIBARY_LIST = "api/gov/app/cultureBooks/list";
    /**
     * 我的攻略详情
     */
    public static final String MY_STRATEGY_DETAIL = "api" + ACTION_TAG + "/travelStrategy" +
            "/strategyDetail";
    /**
     * 获取目的地列表页面
     */
    public static final String DESTINATION_LIST = "api" + ACTION_TAG + "/destination/list";
    /**
     * 保存评论项目
     */
    public static final String SAVE_COMMENT = "api" + ACTION_TAG + "/comment/saveComment";
    /**
     * 导游导览列表
     */
    public static final String GUIDE_1_LIST = "api" + ACTION_TAG + "/mapGuide/guideList";
    /**
     * 导游导览由近及远
     */
    public static final String GUIDE_LIST_NEAR = "api/gov/app/mapGuide/guideListByLonAndlat";
    /**
     * 导游导览详情
     */
    public static final String GUIDE_DETAIL = "api/mapGuide/getMapGuideSet";
    /**
     * 景区列表
     */
    public static final String SCENIC_LIST = "api" + ACTION_TAG + "/scenery/list";
    /**
     * 子景点列表
     */
    public static final String SCENIC_CHILD_LIST = "api/gov/app/scenery/children";
    /**
     * 子景点视频
     */
    public static final String SCENIC_VIDEO = "api/gov/app/scenery/getSceneryVideo";
    /**
     * 景区交通
     */
    public static final String SCENIC_TRAFFIC = "api/gov/app/scenery/listTraffic";
    /**
     * 宣传视频分页列表
     */
    public static final String SITE_VIDEO_LIST = "api/gov/app/sitevideo/list";
    /**
     * 保存点赞、
     */
    public static final String SAVE_THUMB = "api" + ACTION_TAG + "/thumb/saveThumb";
    /**
     * 删除点赞
     */
    public static final String DELE_THUMB = "api" + ACTION_TAG + "/thumb/deleteThumbById";
    /**
     * 景区想详情
     * sceneryId=879340527134412835&isRealpelple=1&token=
     */
    public static final String SCENIC_DETAIL = "api" + ACTION_TAG + "/scenery/detailById";
    /**
     * 子景点详情
     */
    public static final String SCENIC_CHILD_DETAIL = "api" + ACTION_TAG + "/scenery/findChildById";
    /**
     * 根据资源ID取消收藏
     */
    public static final String DELETE_ENSHRINE2 = "api" + ACTION_TAG + "/enshrine" +
            "/deleteEnshrineByReId";
    /**
     * 保存收藏信息
     */
    public static final String SAVE_ENSHRINE = "api" + ACTION_TAG + "/enshrine/saveEnshrine";
    /**
     * 评论数据
     */
    public static final String GET_COMMENT_INFO = "api" + ACTION_TAG + "/comment/getCommentInfo";
    /**
     * 首页banner图接口
     */
    public static final String INDEX_BANNER = "api" + ACTION_TAG + "/homepage/getHomePageBanner";
    /**
     * 广告图
     */
    public static final String ADVERTISING = "api" + ACTION_TAG + "/siteAd/list";
    /**
     * 720全景
     */
    public static final String PANORAMA_LIST = "api" + ACTION_TAG + "/sitepanorama/panoramaList";
    /**
     * 线路http://api.ptisp.daqsoft.com/govapi/api/gov/app/line/applist?lang=cn&siteCode=psxwwz
     * &type=1&page=1&limitPage
     * =5&name=&days=&lineType=&region=
     */
    public static final String LINE_LIST = "api" + ACTION_TAG + "/line/applist";
    /**
     * 酒店详情
     */
    public static final String HOTEL_DETAIL = "api/gov/app/hotel/detail";
    /**
     * 酒店小贴士
     */
    public static final String HOTEL_DETAIL_LISTTIP = "api/gov/app/hotel/listTip";
    /**
     * 美食列表
     */
    public static final String FOOD_LIST = "api" + ACTION_TAG + "/food/list";
    /**
     * 购物列表
     */
    public static final String SHOPPING_LIST = "api" + ACTION_TAG + "/shopping/list";
    /**
     * 首页公告
     */
    public static final String INDEX_NOTICE = "api" + ACTION_TAG + "/siteNotice/list";
    /**
     * 用户登录注册协议code
     */
    public static final String LOGIN_AGREEMENT = "v1.0";
    /**
     * 手机号码的验证
     */
    public static final String PHONE_MATCHER = "(1[0-9][0-9]|15[012356789]|18[0-9]|14[57])[0-9]{8}";
    /**
     * 短信发送的类型 注册时
     */
    public static String REGISTER_TYPE = "_register";
    /**
     * 短信发送的类型 找回密码时
     */
    public static String FIND_PASSWORD_TYPE = "_findPassword";
    /**
     * 第三方登录，绑定手机号
     */
    public static String BIND_ACCOUNT_TYPE = "_bindingAccount";

    /**
     * 购买协议类接口名
     */
    public static final String SITE_AGREEMENT = "api/gov/app/siteAgreement/findByVersion";

    /**
     * 登录
     */
    public static final String LOGIN = "api/gov/app/member/login";


    /**
     * 三方账号绑定情况
     */
    public static final String BINDING_INFO = "api/gov/app/member/bindingInfo";

    /**
     * 微信登录后绑定账号
     */
    public static final String BIND_ACCOUNT = "api/gov/app/member/bindingAccount";
    /**
     * 添加景区
     */
    public static final String JOINJOURNEY = "api/gov/content/wisdomJourney/addScenicSource";
    /**
     * 新增行程
     */
    public static final String CREATE_JOURNEY = "api/gov/content/wisdomJourney/createJourney";

    /**
     * 找回密码
     */
    public static final String FIND_PWD = "api/gov/app/member/findPassword";

    /**
     * 短信发送接口
     * http://ptisp.daqsoft.com/govapi/api/gov/app/member/sendMsg?phone=13778069524&type=_register
     */
    public static final String SEND_MSG = "api/gov/app/member/sendMsg";

    /**
     * 短信验证码验证
     */
    public static final String CHECK_MSG = "api/gov/app/member/checkMsg";

    /**
     * 判断账号是否已经存在或注册
     */
    public static final String ACCOUNT_EXIST = "api/gov/app/member/accountExist";


    /**
     * 注册
     */
    public static final String REGISTER = "api/gov/app/member/register";

    /**
     * 修改密码
     */
    public static final String UPDATE_PWD = "api/gov/app/member/updatePassword";


    /**
     * 编辑用户信息
     */
    public static final String UPDATE_USER_INFO = "api/gov/app/userInfo/updateUserInfo";
    /**
     * 获取个人信息
     */
    public static final String USER_INFO = "api/gov/app/userInfo/getUserInfo";


    /**
     * 获取所有地区接口
     */
    public static final String LOCATION = "http://data.daqsoft.com/uedByName?region=100000";

    /**
     * 获取个人收藏过的信息（根据资源类型）
     */
    public static final String ENSHRINE_LIST = "api/gov/app/enshrine/getEnshrineByAccount";


    /**
     * 取消收藏
     */
    public static final String DELETE_ENSHRINE = "api/gov/app/enshrine/deleteEnshrineId";

    /**
     * 清空个人收藏记录
     */
    public static final String DELETE_ALL_COLLECT = "api/gov/app/enshrine/deleteByAccount";

    /**
     * 用户收藏类型列表
     */
    public static final String ENSHRINE_TYPE = "api/gov/app/enshrine/getEnshrineType";

    /**
     * 评论列表
     */
    public static final String RECOMMEND_LIST = "api/gov/app/comment/getCommentByAccount";
    /**
     * 取消评论
     */
    public static final String DELETE_COMMENT = "api/gov/app/comment/deleteCommentById";
    /**
     * 清空个人评论记录（清空审核已通过的）
     */
    public static final String DELETE_ALL_COMMENT = "api/gov/app/comment/deleteByAccount";

    /**
     * 用户点赞过的信息
     */
    public static final String THUMB_LIST = "api/gov/app/thumb/getThumbByAccount";

    /**
     * 取消点赞
     */
    public static final String DELETE_THUMB = "api/gov/app/thumb/deleteThumbByReId";

    /**
     * 清空个人点赞记录
     */
    public static final String DELETE_ALL_THUMB = "api/gov/app/thumb/deleteByAccount";

    /**
     * 景区banner接口
     */
    public static final String SITE_BANNER = "api/gov/app/siteAd/list";

    /**
     * 新增投诉
     */
    public static final String ADD_COMPLAINT = "api/gov/app/complain/save";
    /**
     * 投诉列表
     */
    public static final String COMPLAINT_LIST = "api/gov/app/complain/complainList";
    /**
     * 投诉详情
     */
    public static final String COMPLAINT_DETAIL = "api/gov/app/complain/detail";
    /**
     * 栏目详情
     */
    public static final String CHANNEL_DETAILS = "api/gov/app/siteChannel/detail";
    /**
     * 厕所列表页面
     */
    public static final String TOILET_LIST = "api/gov/app/tourismToilet/list";

    /**
     * 旅游资讯列表
     */
    public static final String NEWS_LIST = "api/gov/app/news/list";

    /**
     * 旅游资讯详情
     */
    public static final String NEWS_DETAILS = "api/gov/app/news/detail";

    /**
     * 极客机器人
     */

    /**
     * 机器人信息
     */
    public static final String ROBOT_INFO = "api" + ACTION_TAG + "/robotInfo/findRobotInfo";

    /**
     * 业务分类列表
     */
    public static final String ROBOT_TYPE_LIST = "api" + ACTION_TAG + "/robotQuestion/findTypeList";

    /**
     * 根据分类查询问答标签
     */
    public static final String QUESTION_BY_TYPE = "api" + ACTION_TAG + "/robotQuestion" +
            "/findQuestionByType";


    /**
     * 问答回复
     */
    public static final String FIND_QUESTION_NEW = "api/robotQuestion/findAnswerByQuestion";

    /**
     * 根据id查条目全局搜索
     */
    public static final String FIND_QUESTION_BY_ID = "api/robotQuestion/findOtherSourceById";
    /**
     * 搜索全部
     */
    public static final String SEARCH_ALL = "api/elasticsearch/searchAll";
    /**
     * 保存
     */
    public static final String TOURIST_SAVE = "api/gov/app/tourist/save";
    /**
     * 保存搜索记录
     */
    public static final String SEARCH_SAVE = "api/gov/app/common/saveRecord";
    /**
     * 留言新增
     */
    public static final String SAVE_MSG = "api" + ACTION_TAG + "/leaveMsg/save";

    /**
     * 在线留言列表我的
     */
    public static final String MSG_LIST_SERVICE = "api" + ACTION_TAG + "/leaveMsg/getMyleaveList";

    /**
     * 留言详情页
     */
    public static final String MSG_DETAIL = "api" + ACTION_TAG + "/leaveMsg/getDetailBy";

    /**
     * 意见采集
     */
    public static final String SAVE_OPINION_COLLECT = "api" + ACTION_TAG + "/collectOpinion" +
            "/saveCollect";

    /**
     * 景区完整列表
     */
    public static final String SCENIC_TOTAL_LIST = "api" + ACTION_TAG + "/scenery/list";
    /**
     * 消息/api/userInfo/getAllMessage
     */
    public static final String ME_MESSAGE_LIST = "api/userInfo/getAllMessage";

    /**
     * 消息详情
     */
    public static final String ME_MESSAGE_DETAIL = "api/userInfo/getDetailMessage";
    /**
     * 消息标注已读
     */
    public static final String READ_MESSAGE = "api/userInfo/readMessage";

    /**
     * 根据地区代码+语言获取目的地基础信息
     */
    public static final String DESTINATION_BASE_INFO = "api" + ACTION_TAG + "/destination" +
            "/baseInfoByRegion";
    /**
     * 酒店完整列表
     */
    public static final String HOTEL_TOTAL_LIST = "api" + ACTION_TAG + "/hotel/list";
    /**
     * 根据经纬度获取一定距离范围内的相关产品数
     */
    public static final String DESTINATION_PRODUCT = "api" + ACTION_TAG + "/destination" +
            "/relatedProduct";
    /**
     * 餐馆完整列表
     */
    public static final String DINING_TOTAL_LIST = "api" + ACTION_TAG + "/dining/list";

    /**
     * 食物完整列表
     */
    public static final String FOOD_TOTAL_LIST = "api" + ACTION_TAG + "/food/list";
    /**
     * 食物详情
     */
    public static final String FOOD_DETAIL_LIST = "api/gov/app/food/detail";

    /**
     * 景区等级
     *
     * @return
     */
    public static final String SCENERY_LEVEL = "api/gov/app/common/getSceneryLevel";

    /**
     * 景区主题
     *
     * @return
     */
    public static final String SCENERY_THEME = "api/gov/app/common/getScenicTheme";

    /**
     * 景区人群
     *
     * @return
     */
    public static final String SCENERY_CROWD = "api/gov/app/common/getMatterCrowd";

    /**
     * 景区类型
     *
     * @return
     */
    public static final String SCENERY_TYPE = "api/gov/app/common/getSceneryType";
    /**
     * 景区筛选新
     */
    public static final String SCENERY_TYPE_NEW = "api/scenery/getTypeAndLevel";
    /**
     * 娱乐场所类型
     */
    public static final String RECREATION_LISTDICT = "api" + ACTION_TAG + "/recreation/listDict";
    /**
     * 娱乐详细
     */
    public static final String RECREATION_DETAIL = "api" + ACTION_TAG + "/recreation/detail";
    /**
     * 娱乐场所列表
     */
    public static final String RECREATION_LIST = "api" + ACTION_TAG + "/recreation/list";
    /**
     * 酒店星级
     *
     * @return
     */
    public static final String HOTEL_LEVELLIST = "api/gov/app/common/getHotelLevelList";

    /**
     * 酒店类型
     *
     * @return
     */
    public static final String HOTELT_YPELIST = "api/gov/app/common/getHotelTypeList";

    /**
     * 其他服务
     *
     * @return
     */
    public static final String HOTEL_SERVICE = "api/gov/app/common/getHotelService";

    /**
     * 乡村旅游
     *
     * @return
     */
    public static final String COUNTRY_LIST = "api/gov/app/ruraltourism/list";


    /**
     * 乡村旅游类型
     *
     * @return
     */
    public static final String COUNTRY_TYPE_LIST = "api/gov/app/ruraltourism/list";
    /**
     * 分页列表
     */
    public static final String SITE_PICTURE_LIST = "api/gov/app/sitepicture/list";


    /**
     * 乡村旅游类型
     *
     * @return
     */
    public static final String COUNTRY_DETAILS = "api/gov/app/ruraltourism/detail";
    /**
     * 特产详情
     * /api/gov/app/goods/detail
     */
    public static final String SPECIAL_DETAIL = "api/gov/app/goods/detail";
    /**
     * 旅游商品
     */
    public static final String SPECIAL_GOODS = "api/gov/app/goods/list";
    /**
     * 云宝宝
     */
    public static final String YBB_URL = "https://nnapptest.cloudbae.cn:38081/";
    public static final String YBB_URL_1 = "api/gov/app/member/getTokenAndLogin";
    /**
     * 特产列表
     */
    public static final String SPECIAL_LIST = "api/gov/app/special/list";
    /**
     * 天气助手链接
     */
    public static String WEATHER_HTML = "http://s.ued.daqsoft.com/geekUI/mobile/demo" +
            "/#/weather?region=" + ProjectConfig.REGION;
    /**
     * 南宁天气助手定制化
     */
    public static String WEATHER_HTML_NANNING = "http://project.daqsoft" + "" + ".com/weather" +
            "/#/weather?region=450103&area=nanning";

    /**
     * 南宁天气助手定制化
     */
    public static String WEATHER_HTML_NANNING_LAT = "http://project.daqsoft.com/weather/#/weather?";


    /**
     * 旅行社详情页面
     */
    public static final String TRAVEL_DETAILS_URL = "api/gov/app/travel/detail";
    /**
     * 足迹列表
     */
    public static final String FOOT_PRINT_LIST = "api/gov/app/tourist/list";
    /**
     * 足迹统计信息
     */
    public static final String GET_STATISTICS_MAP = "api/gov/app/tourist/getStatisticsMap";
    /**
     * 足迹详情
     */
    public static final String TOURIST_DETAIL = "api/gov/app/tourist/detail";
    /**
     * 牧人之家地址
     * Shepherd's Home
     */
    public static final String SHEPHERD_HOME_URL =
            "https://dev.yueyat.net/app/index" + ".php?i" + "=49&c=entry&eid=1472";

    public static final String SHEPHERD_YU_YUE_URL ="https://mfsyy.yueyat.net/";

    /**
     * 导游级别类型
     */
    public static final String GUIDE_TYPE_LIST = "api" + ACTION_TAG + "/guide/type/list";

    /**
     * 导游列表
     */
    public static final String GUIDE_BEAN_LIST = "api" + ACTION_TAG + "/guide/list";
    /**
     * 探风景列表
     */
    public static final String MONITOR_LIST = "api" + ACTION_TAG + "/scenery/sceneryMonitorList";
    /**
     * 景区客流量
     */
    public static final String FLOW_LIST = "api" + ACTION_TAG + "/scenicTouristFlow/list";


}
