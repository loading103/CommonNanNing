package com.daqsoft.commonnanning.http;


import com.daqsoft.android.ProjectConfig;
import com.daqsoft.commonnanning.common.URLConstant;
import com.daqsoft.commonnanning.hotel.HotelTip;
import com.daqsoft.commonnanning.ui.book.BookLibabry;
import com.daqsoft.commonnanning.ui.country.entity.CountryBean;
import com.daqsoft.commonnanning.ui.destination.DestinationInfoEntity;
import com.daqsoft.commonnanning.ui.destination.DestinationProduct;
import com.daqsoft.commonnanning.ui.destination.RegionEntity;
import com.daqsoft.commonnanning.ui.entity.ActivityDetailsEntity;
import com.daqsoft.commonnanning.ui.entity.AdvertEntity;
import com.daqsoft.commonnanning.ui.entity.AgreeMentEntity;
import com.daqsoft.commonnanning.ui.entity.BannerEntity;
import com.daqsoft.commonnanning.ui.entity.BindPhoneEntity;
import com.daqsoft.commonnanning.ui.entity.ChannelDetailsEntity;
import com.daqsoft.commonnanning.ui.entity.CommentEntity;
import com.daqsoft.commonnanning.ui.entity.ComplaintDetail;
import com.daqsoft.commonnanning.ui.entity.ComplaintListEntity;
import com.daqsoft.commonnanning.ui.entity.DiningEntity;
import com.daqsoft.commonnanning.ui.entity.EntertainmentDetailBean;
import com.daqsoft.commonnanning.ui.entity.EntertainmentListBean;
import com.daqsoft.commonnanning.ui.entity.FoodDetialEntity;
import com.daqsoft.commonnanning.ui.entity.FoodEntity;
import com.daqsoft.commonnanning.ui.entity.FootPrintListBean;
import com.daqsoft.commonnanning.ui.entity.FoundNearEy;
import com.daqsoft.commonnanning.ui.entity.GuideBean;
import com.daqsoft.commonnanning.ui.entity.GuideDetail;
import com.daqsoft.commonnanning.ui.entity.HotelDetail;
import com.daqsoft.commonnanning.ui.entity.HotelEntity;
import com.daqsoft.commonnanning.ui.entity.ImagesBean;
import com.daqsoft.commonnanning.ui.entity.IndexActivity;
import com.daqsoft.commonnanning.ui.entity.IndexNotify;
import com.daqsoft.commonnanning.ui.entity.IndexScenic;
import com.daqsoft.commonnanning.ui.entity.ListDictBean;
import com.daqsoft.commonnanning.ui.entity.MessageDetail;
import com.daqsoft.commonnanning.ui.entity.MessageEntity;
import com.daqsoft.commonnanning.ui.entity.MyStrategyListBean;
import com.daqsoft.commonnanning.ui.entity.NewsListEntity;
import com.daqsoft.commonnanning.ui.entity.PanoramaListBean;
import com.daqsoft.commonnanning.ui.entity.RouteEnty;
import com.daqsoft.commonnanning.ui.entity.ScenicChild;
import com.daqsoft.commonnanning.ui.entity.ScenicChildDetail;
import com.daqsoft.commonnanning.ui.entity.ScenicDetail;
import com.daqsoft.commonnanning.ui.entity.ScenicEntity;
import com.daqsoft.commonnanning.ui.entity.ScenicSeceledEnty;
import com.daqsoft.commonnanning.ui.entity.ScenicTies;
import com.daqsoft.commonnanning.ui.entity.ScenicVideo;
import com.daqsoft.commonnanning.ui.entity.SelectEntity;
import com.daqsoft.commonnanning.ui.entity.SpecialDetailBean;
import com.daqsoft.commonnanning.ui.entity.SpecialListBean;
import com.daqsoft.commonnanning.ui.entity.StatisticsMapBean;
import com.daqsoft.commonnanning.ui.entity.StrategyDetail;
import com.daqsoft.commonnanning.ui.entity.ToiletEntity;
import com.daqsoft.commonnanning.ui.entity.TouristDetailBean;
import com.daqsoft.commonnanning.ui.entity.TravelBean;
import com.daqsoft.commonnanning.ui.entity.TravelDetailsEntity;
import com.daqsoft.commonnanning.ui.entity.UpImgEntity;
import com.daqsoft.commonnanning.ui.entity.UserEntity;
import com.daqsoft.commonnanning.ui.entity.UserInfoEntity;
import com.daqsoft.commonnanning.ui.entity.VideoBean;
import com.daqsoft.commonnanning.ui.mine.message.MessageBean;
import com.daqsoft.commonnanning.ui.mine.interact.entity.CommentBean;
import com.daqsoft.commonnanning.ui.mine.interact.entity.EnshrineEntity;
import com.daqsoft.commonnanning.ui.mine.interact.entity.SceneryType;
import com.daqsoft.commonnanning.ui.mine.interact.entity.ThumbEntity;
import com.daqsoft.commonnanning.ui.service.bean.FlowListBean;
import com.daqsoft.commonnanning.ui.service.fun.bean.MonitorListBean;
import com.daqsoft.commonnanning.ui.service.guide.bean.GuideListBean;
import com.daqsoft.commonnanning.ui.service.guide.bean.GuideTypeEntity;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 网络请求所有接口的服务类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018/9/11 0011
 * @since JDK 1.8
 */
public interface HttpApiService {

    /**
     * 公共参数对象
     */
    public static HashMap<String, String> REQUESTMAP = new HashMap<>();


    /**
     * APP版本检测
     *
     * @param appId   app唯一标识
     * @param method  方法AppVersion
     * @param token   daqsoft
     * @param appType 1
     * @param version 版本号
     * @return
     */
    @GET(ProjectConfig.VERSION_URL)
    Call<ResponseBody> checkVersion(@Query("AppId") String appId, @Query("Method") String method,
                                    @Query("token") String token,
                                    @Query("AppType") String appType,
                                    @Query("VersionCode") String version);

    /**
     * 获取首页公告
     *
     * @param chanelCode 栏目编码
     * @param page       当前页
     * @param limitPage  当前页数量
     * @return
     */
    @GET(URLConstant.INDEX_NOTICE)
    Observable<IndexNotify> getIndexNotify(@Query("chanelCode") String chanelCode,
                                           @Query("page") String page,
                                           @Query("limitPage") String limitPage);

    /**
     * 保存点赞
     */
    @GET(URLConstant.SAVE_THUMB)
    Observable<BaseResponse> saveThumb(@Query("reId") String reId,
                                       @Query("content") String content,
                                       @Query("target") String target,
                                       @Query("sourceType") String sourceTyp);

    /**
     * 添加评论
     */
    @GET(URLConstant.SAVE_COMMENT)
    Observable<BaseResponse> saveComment(@Query("titleApp") String title_app,
                                         @Query("sourceType") String sourceType,
                                         @Query("reId") String reId,
                                         @Query("comment") String comment,
                                         @Query("star") String star, @Query("pics") String pics,
                                         @Query("target") String target);

    /**
     * 检验token是否过期
     */
    @GET(URLConstant.TOKEN_INTERCEPT)
    Observable<BaseResponse> checkToken();

    /**
     * 删除点赞
     */
    @GET(URLConstant.DELE_THUMB)
    Observable<BaseResponse> saveThumb(@Query("id") String id);

    /**
     * 加入行程
     * id 行程ID
     */
    @GET(URLConstant.JOINJOURNEY)
    Observable<BaseResponse> joinJourny(@Query("id") String id,
                                        @Query("sourceId") String sourceId,
                                        @Query("date") String date);

    /**
     * 新增行程
     *
     * @return
     */
    @GET(URLConstant.CREATE_JOURNEY)
    Observable<BaseResponse<RouteEnty>> createJourny(@Query("name") String name);

    /**
     * 景区详情
     */
    @GET(URLConstant.SCENIC_DETAIL)
    Observable<BaseResponse<ScenicDetail>> getScenicDetail(@Query("sceneryId") String sceneryId);

    /**
     * 游记攻略取消收藏
     */
    @GET(URLConstant.DELETE_ENSHRINE2)
    Observable<BaseResponse> delCollection(@Query("reId") String reId,
                                           @Query("sourceType") String sourceTyp);

    /**
     * 游记攻略保存收藏
     */
    @GET(URLConstant.SAVE_ENSHRINE)
    Observable<BaseResponse> saveCollection(@Query("reId") String reId,
                                            @Query("sourceType") String sourceTyp, @Query(
                                                    "content") String content,
                                            @Query("target") String target);

    /**
     * 酒店详情
     *
     * @return
     */
    @GET(URLConstant.HOTEL_DETAIL)
    Observable<BaseResponse<HotelDetail>> getHotelDetail(@Query("id") String id);

    /**
     * 酒店小贴士
     *
     * @return
     */
    @GET(URLConstant.HOTEL_DETAIL_LISTTIP)
    Observable<BaseResponse<HotelTip>> getHotelListTip(@Query("resourceId") String id);

    /**
     * 游记攻略评论
     */
    @GET(URLConstant.GET_COMMENT_INFO)
    Observable<BaseResponse<CommentBean>> getCommentInfo(@Query("reId") String reId, @Query(
            "sourceType") String sourceTyp, @Query("page") String page,
                                                         @Query("limitPage") String limitPage);

    /**
     * 获取导游导览列表数据
     *
     * @param page
     * @param limitPage
     * @return
     */
    @GET(URLConstant.GUIDE_1_LIST)
    Observable<BaseResponse<GuideBean>> getMapGuideList(@Query("name") String name,
                                                        @Query("page") String page, @Query(
                                                                "limitPage") String limitPage);

    /**
     * 获取导游导览列表数据
     * 由近及远
     *
     * @param page
     * @param limitPage
     * @return
     */
    @GET(URLConstant.GUIDE_LIST_NEAR)
    Observable<BaseResponse<GuideBean>> getMapGuideListNear(@Query("latitude") String latitude,
                                                            @Query("longitude") String longitude,
                                                            @Query("name") String name, @Query(
                                                                    "page") String page, @Query(
                                                                            "limitPage") String limitPage);

    /**
     * 获取导游导览详情
     *
     * @return
     */
    @GET(URLConstant.GUIDE_DETAIL)
    Observable<BaseResponse<GuideDetail>> getMapGuideDetail(@Query("id") String id);

    /**
     * 获取子景点数据
     *
     * @return
     */
    @GET(URLConstant.SCENIC_CHILD_LIST)
    Observable<BaseResponse<ScenicChild>> getScenicChild(@Query("page") String page, @Query(
            "limitPage") String limitPage, @Query("sceneryId") String sceneryId);

    /**
     * 获取子景点数据详情
     *
     * @return
     */
    @GET(URLConstant.SCENIC_CHILD_DETAIL)
    Observable<BaseResponse<ScenicChildDetail>> getScenicChildDetail(@Query("childId") String childId);

    /**
     * 获取子景点视频
     *
     * @return
     */
    @GET(URLConstant.SCENIC_VIDEO)
    Observable<BaseResponse<ScenicVideo>> getScenicVideo(@Query("page") String page, @Query(
            "limitPage") String limitPage, @Query("sceneryId") String sceneryId);

    /**
     * 宣传视频分页列表
     *
     * @param page      当前页
     * @param limitPage 每页条数
     * @param datatype  资源类型
     *                  sourceType_1景区、
     *                  sourceType_2酒店、
     *                  sourceType_3旅行社、
     *                  sourceType_4购物场所、
     *                  sourceType_5娱乐场所、
     *                  sourceType_6餐饮场所、
     *                  sourceType_7乡村旅游、
     *                  sourceType_8特色美食、
     *                  门票：ticket，
     *                  游记攻略：travels
     * @param order     升序/降序 asc升序、desc降 序
     * @param sort      排序字段
     * @param region    地区编码
     * @param typeCode  分类代码
     * @param dataid    资源id
     * @param orgId     组织编码
     * @param tag       标签
     * @param name      标题
     * @return
     */
    @GET(URLConstant.SITE_VIDEO_LIST)
    Observable<BaseResponse<VideoBean>> siteVideoList(@Query("page") Integer page, @Query(
            "limitPage") Integer limitPage, @Query("datatype") String datatype,
                                                      @Query("order") String order,
                                                      @Query("sort") String sort,
                                                      @Query("region") String region, @Query(
                                                              "typeCode") String typeCode,
                                                      @Query("dataid") String dataid, @Query(
                                                              "orgId") String orgId,
                                                      @Query("tag") String tag,
                                                      @Query("name") String name);

    /**
     * 获取子景点周边信息
     *
     * @return
     */
    @GET(URLConstant.NEAR_LIST)
    Observable<BaseResponse<FoundNearEy>> getAroundMap(@Query("page") String page, @Query(
            "limitPage") String limitPage, @Query("latitude") String latitude,
                                                       @Query("longitude") String longitude,
                                                       @Query("sourceType") String sourceType,
                                                       @Query("distance") String distance);

    /**
     * 获取子景点交通信息
     *
     * @return
     */
    @GET(URLConstant.SCENIC_TRAFFIC)
    Observable<BaseResponse<ScenicTies>> getScenicTraffic(@Query("sceneryId") String sceneryId);

    /**
     * 获取首页banner
     *
     * @param adCode 指定广告位的代码，如 index_top。需要通过后台配置广告位并添加广告
     * @return
     */
    @GET(URLConstant.ADVERTISING)
    Observable<BaseResponse<AdvertEntity>> getIndexBannar(@Query("adCode") String adCode);

    /**
     * 获取720
     *
     * @return
     */
    @GET(URLConstant.PANORAMA_LIST)
    Observable<BaseResponse<PanoramaListBean>> getPanoramaList(@Query("page") String page,
                                                               @Query("limitPage") String limitPage);

    /**
     * 获取线路列表
     *
     * @param page
     * @param limitPage
     * @return
     */
    @GET(URLConstant.MY_STRATEGY_LIST)
    Observable<BaseResponse<MyStrategyListBean>> getLineList(@Query("page") String page, @Query(
            "limitPage") String limitPage, @Query("status") String status,
                                                             @Query("sourceId") String sourceId);

    /**
     * 获取博物馆列表
     *
     * @param page
     * @return
     */
    @GET(URLConstant.LIBARY_BO_LIST)
    Observable<BaseResponse<BookLibabry>> getBoLibabryList(@Query("longitude") String longitude,
                                                           @Query("latitude") String latitude,
                                                           @Query("page") String page, @Query(
                                                                   "pageSize") String pageSize);

    /**
     * 获取图书馆列表
     *
     * @param page
     * @return
     */
    @GET(URLConstant.LIBARY_LIST)
    Observable<BaseResponse<BookLibabry>> getLibabryList(@Query("longitude") String longitude,
                                                         @Query("latitude") String latitude,
                                                         @Query("page") String page, @Query(
                                                                 "pageSize") String pageSize);

    /**
     * 获取线路列表
     *
     * @param id id
     * @return
     */
    @GET(URLConstant.MY_STRATEGY_DETAIL)
    Observable<BaseResponse<StrategyDetail>> getLineDetail(@Query("id") String id);


    /**
     * 景区列表
     *
     * @param page
     * @param limitPage
     * @param type
     * @return
     */
    @GET(URLConstant.SCENIC_LIST)
    Observable<BaseResponse<IndexScenic>> getIndexScenic(@Query("page") String page, @Query(
            "limitPage") String limitPage, @Query("type") String type,
                                                         @Query("orderType") String orderType);


    /**
     * 景区列表
     *
     * @param page
     * @param limitPage
     * @param type
     * @return
     */
    @GET(URLConstant.SCENIC_LIST)
    Observable<BaseResponse<IndexScenic>> getIndexScenic(@Query("page") String page, @Query(
            "limitPage") String limitPage, @Query("type") String type, @Query("lat") String lat,
                                                         @Query("lng") String lng, @Query(
                                                                 "distinct") String distinct,
                                                         @Query("orderType") String orderType);

    /**
     * 景区列表
     *
     * @param page
     * @param limitPage
     * @param type          类型 （必填 0 pc端 1 app端）
     * @param distinct      距离（KM）
     * @param resourceLevel 资源类型
     * @param orderType     排序方式
     * @return
     */
    @GET(URLConstant.SCENIC_LIST)
    Observable<BaseResponse<IndexScenic>> getIndexScenic(@Query("page") Integer page, @Query(
            "limitPage") Integer limitPage, @Query("type") Integer type,
                                                         @Query("distinct") Integer distinct,
                                                         @Query("resourceLevel") String resourceLevel, @Query("name") String name, @Query("orderType") String orderType,
                                                         @Query("longitude") String longitude,
                                                         @Query("latitude") String latitude);

    /**
     * banner接口
     *
     * @param adCode 编码
     * @return
     */
    @GET(URLConstant.ADVERTISING)
    Observable<BannerEntity> getBannerList(@Query("adCode") String adCode);


    /**
     * 景区完整列表(筛选数据)
     *
     * @return
     */
    @GET(URLConstant.SCENIC_TOTAL_LIST)
    Observable<ScenicEntity> getScenicList(@QueryMap HashMap<String, String> map);

    /**
     * 筛选景区数据
     * resourceLevel scenicTheme  matterCrowd resourceType
     */
    @GET(URLConstant.SCENIC_TOTAL_LIST)
    Observable<ScenicEntity> searchScenicList(@QueryMap HashMap<String, String> map);


    /**
     * 筛选酒店数据
     * resourceLevel scenicTheme  matterCrowd resourceType
     */
    @GET(URLConstant.HOTEL_TOTAL_LIST)
    Observable<HotelEntity> searchHotelList(@QueryMap HashMap<String, String> map);

    /**
     * 酒店列表
     *
     * @param page
     * @param limitPage
     * @return
     */
    @GET(URLConstant.HOTEL_TOTAL_LIST)
    Observable<HotelEntity> getHotel(@Query("page") String page,
                                     @Query("limitPage") String limitPage,
                                     @Query("grade") String grade);

    /**
     * 食物详情
     *
     * @param moreSize 查询几条更多商品
     * @param id       美食id
     * @param token    登录后获取用户操作状态
     * @return
     */
    @GET(URLConstant.FOOD_DETAIL_LIST)
    Observable<FoodDetialEntity> getFoodDetial(@Query("moreSize") String moreSize,
                                               @Query("id") String id,
                                               @Query("token") String token);

    /**
     * @param reId       被评论记录ID
     * @param sourceType 资源类型 :sourceType_8 美食
     * @param limitPage
     * @param page
     * @return
     */
    @GET(URLConstant.GET_COMMENT_INFO)
    Observable<CommentEntity> getComment(@Query("reId") String reId,
                                         @Query("sourceType") String sourceType,
                                         @Query("page") String page,
                                         @Query("limitPage") String limitPage);

    /**
     * 酒店列表
     *
     * @return
     */
    @GET(URLConstant.HOTEL_TOTAL_LIST)
    Observable<HotelEntity> getHotelList(@QueryMap HashMap<String, String> map);

    /**
     * 食物列表
     *
     * @param page
     * @param limitPage
     * @return
     */
    @GET(URLConstant.FOOD_TOTAL_LIST)
    Observable<BaseResponse<FoodEntity>> getFoodList(@Query("page") String page, @Query(
            "limitPage") String limitPage, @Query("region") String region,
                                                     @Query("type") String type,
                                                     @Query("name") String name);

    /**
     * 餐馆列表
     *
     * @param page
     * @param limitPage
     * @return
     */
    @GET(URLConstant.DINING_TOTAL_LIST)
    Observable<DiningEntity> getDiningList(@Query("page") String page,
                                           @Query("limitPage") String limitPage,
                                           @Query("region") String region,
                                           @Query("type") String type);


    /**
     * 美食
     *
     * @param page
     * @param limitPage
     * @return
     */
    @GET(URLConstant.FOOD_LIST)
    Observable<BaseResponse<IndexScenic>> getIndexFood(@Query("page") String page, @Query(
            "limitPage") String limitPage);

    /**
     * @param page
     * @param limitPage
     * @return
     */
    @GET(URLConstant.SHOPPING_LIST)
    Observable<BaseResponse<IndexScenic>> getShopData(@Query("name") String name,
                                                      @Query("page") String page, @Query(
                                                              "limitPage") String limitPage);


    /**
     * 注册购买协议等接口
     *
     * @param version 查询接口的参数
     */
    @GET(URLConstant.SITE_AGREEMENT)
    Observable<BaseResponse<AgreeMentEntity>> getSiteAgreement(@Query("version") String version);

    /**
     * 登录
     *
     * @param account  账号
     * @param password 密码
     */
    @GET(URLConstant.LOGIN)
    Observable<BaseResponse<UserEntity>> login(@Query("ignoreCode") String ignoreCode, @Query(
            "account") String account, @Query("password") String password);

    /**
     * 判断三方账号绑定情况
     *
     * @param thirdAccount 三方账号
     * @param type         类型
     */
    @GET(URLConstant.BINDING_INFO)
    Observable<BaseResponse<BindPhoneEntity>> bindingInfo(@Query("thirdAccount") String thirdAccount, @Query("type") String type);

    /**
     * 微信登录后绑定账号
     *
     * @param password     密码
     * @param thirdAccount 三方账号，微信openid
     * @param phone        手机号
     * @param type         微信类型，微信 1,QQ 2
     */
    @GET(URLConstant.BIND_ACCOUNT)
    Observable<BaseResponse<UserEntity>> bindingAccount(@Query("password") String password,
                                                        @Query("thirdAccount") String thirdAccount, @Query("phone") String phone, @Query("type") String type);

    /**
     * 找回密码
     *
     * @param account        账号
     * @param password       密码
     * @param affirmPassword 确认密码
     * @param msgCode        短信验证码
     */
    @POST(URLConstant.FIND_PWD)
    Observable<BaseResponse> findPwd(@Query("account") String account,
                                     @Query("password") String password,
                                     @Query("affirmPassword") String affirmPassword, @Query(
                                             "msgCode") String msgCode);

    /**
     * 短信发送接口
     *
     * @param phone 电话号码
     * @param type  短信发送类型
     */
    @POST(URLConstant.SEND_MSG)
    Observable<BaseResponse> getSendMsg(@Query("phone") String phone, @Query("type") String type);

    /**
     * 短信验证验证码
     *
     * @param phone   手机号
     * @param type    短信类型
     * @param msgCode 验证码
     */
    @POST(URLConstant.CHECK_MSG)
    Observable<BaseResponse> checkMsg(@Query("phone") String phone, @Query("type") String type,
                                      @Query("msgCode") String msgCode);

    /**
     * 检查账号是否已存在或注册
     *
     * @param phone 手机号
     */
    @POST(URLConstant.ACCOUNT_EXIST)
    Observable<BaseResponse<BindPhoneEntity>> accountExist(@Query("account") String phone);

    /**
     * 节会活动
     *
     * @param page
     * @param limitPage
     */
    @GET(URLConstant.ACTIVITY_LIST)
    Observable<BaseResponse<IndexActivity>> getActivityList(@Query("page") String page, @Query(
            "limitPage") String limitPage, @Query("title") String title,
                                                            @Query("channelCode") String channelCode);

    /**
     * 节会活动
     *
     * @param page
     * @param limitPage
     */
    @GET(URLConstant.ACTIVITY_LIST)
    Observable<BaseResponse<IndexActivity>> getActivityList(@Query("page") String page, @Query(
            "limitPage") String limitPage, @Query("title") String title,
                                                            @Query("channelCode") String channelCode, @Query("region") String region);

    /**
     * 获取线路列表
     *
     * @param page
     * @param limitPage
     * @param title     根据攻略标题模糊查询	string	选填
     * @param status    攻略状态	number	10：草稿，1：审核通过（发布成功），不传默认全部
     * @param region    地区编码
     * @return
     */
    @GET(URLConstant.MY_STRATEGY_LIST)
    Observable<BaseResponse<MyStrategyListBean>> getLineList(@Query("page") String page, @Query(
            "limitPage") String limitPage, @Query("title") String title,
                                                             @Query("status") String status,
                                                             @Query("region") String region);

    /**
     * 获取目的地列表页面数据(西安站点有数据)
     */
    @GET(URLConstant.DESTINATION_LIST)
    Observable<BaseResponse<DestinationInfoEntity>> getDestinationList(@Query("limitPage") String limitPage);

    /**
     * 获取站点下的地区数据
     *
     * @return
     */
    @GET(URLConstant.SITE_REGIONS)
    Observable<BaseResponse<RegionEntity>> getSiteRegions();

    /**
     * 活动列表
     *
     * @param page
     * @param limitPage
     */
    @GET(URLConstant.ACTIVITY_LIST2)
    Observable<BaseResponse<IndexActivity>> getActivityList2(@Query("page") String page, @Query(
            "limitPage") String limitPage, @Query("title") String title,
                                                             @Query("channelCode") String channelCode);

    /**
     * 旅行社列表
     *
     * @param page
     * @param limitPage
     * @param name
     * @param region
     * @return
     */
    @GET(URLConstant.TRAVEL_AGENCY_LIST)
    Observable<BaseResponse<TravelBean>> getTravelList(@Query("page") String page, @Query(
            "limitPage") String limitPage, @Query("name") String name,
                                                       @Query("region") String region);

    /** ---筛选接口------------------------------------------------------------------------*/
    /**
     * 景区等级
     *
     * @return
     */
    @GET(URLConstant.SCENERY_LEVEL)
    Observable<SelectEntity> getSceneryLevel();

    /**
     * 景区主题
     *
     * @return
     */
    @GET(URLConstant.SCENERY_THEME)
    Observable<SelectEntity> getSceneryTheme();

    /**
     * 景区人群
     *
     * @return
     */
    @GET(URLConstant.SCENERY_CROWD)
    Observable<SelectEntity> getSceneryCrowd();

    /**
     * 景区类型
     *
     * @return
     */
    @GET(URLConstant.SCENERY_TYPE)
    Observable<SelectEntity> getSceneryType();

    /**
     * 景区类型筛选
     *
     * @return
     */
    @GET(URLConstant.SCENERY_TYPE_NEW)
    Observable<BaseResponse<ScenicSeceledEnty>> getScenerySceletedType();

    /**
     * 娱乐场所类型
     *
     * @return
     */
    @GET(URLConstant.RECREATION_LISTDICT)
    Observable<BaseResponse<ListDictBean>> recreationListDict();

    /**
     * 娱乐详细
     *
     * @param id
     * @return
     */
    @GET(URLConstant.RECREATION_DETAIL)
    Observable<BaseResponse<EntertainmentDetailBean>> recreationDetai(@Query("id") long id);

    /**
     * 娱乐场所列表
     *
     * @param limitPage 每页条数
     * @param page      当前页
     * @param type      资源类型code
     * @param name      名称
     * @param tag       标签id（选填）
     * @param recommend 1表示推荐
     * @return
     */
    @GET(URLConstant.RECREATION_LIST)
    Observable<BaseResponse<EntertainmentListBean>> recreationList(@Query("limitPage") Integer limitPage, @Query("page") Integer page, @Query("type") String type, @Query("name") String name, @Query("tag") String tag, @Query("recommend") Integer recommend);

    /**
     * 酒店星级
     *
     * @return
     */
    @GET(URLConstant.HOTEL_LEVELLIST)
    Observable<SelectEntity> getHotelLevelList();

    /**
     * 酒店类型
     *
     * @return
     */
    @GET(URLConstant.HOTELT_YPELIST)
    Observable<SelectEntity> getHotelTypeList();

    /**
     * 其他服务
     *
     * @return
     */
    @GET(URLConstant.HOTEL_SERVICE)
    Call<ResponseBody> getHotelService();

    /**----------------------------------------------------------------------------------*/

    /**
     * 注册
     *
     * @param account  账号
     * @param password 密码
     * @param msgCode  短信验证码
     * @return
     */
    @POST(URLConstant.REGISTER)
    Observable<BaseResponse> register(@Query("account") String account,
                                      @Query("password") String password,
                                      @Query("msgCode") String msgCode,
                                      @Query("ignoreCode") String ignoreCode);

    /**
     * 修改密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return
     */
    @POST(URLConstant.UPDATE_PWD)
    Observable<BaseResponse> updatePwd(@Query("oldPassword") String oldPassword, @Query(
            "newPassword") String newPassword);

    /**
     * @param partList 图片上传到大数据中心
     * @return
     */
    @Multipart
    @POST("upload")
    Observable<UpImgEntity> upImg(@Part List<MultipartBody.Part> partList);

    /**
     * 编辑用户信息
     *
     * @param map
     * @return
     */
    @GET(URLConstant.UPDATE_USER_INFO)
    Observable<BaseResponse> updateUserInfo(@QueryMap Map<String, String> map);

    /**
     * 获取个人信息
     */
    @GET(URLConstant.USER_INFO)
    Observable<BaseResponse<UserInfoEntity>> getUserInfo();

    /**
     * 获取个人收藏过的信息（根据资源类型）
     *
     * @param page       页面
     * @param limitPage  每页条数
     * @param sourceType 资源类型
     */
    @GET(URLConstant.ENSHRINE_LIST)
    Observable<BaseResponse<EnshrineEntity>> getEnshrineList(@Query("page") String page, @Query(
            "limitPage") String limitPage, @Query("sourceType") String sourceType);

    /**
     * 取消收藏
     *
     * @param id id
     */
    @GET(URLConstant.DELETE_ENSHRINE)
    Observable<BaseResponse> deleteEnshrine(@Query("id") String id);

    /**
     * 取消收藏
     */
    @GET(URLConstant.DELETE_ALL_COLLECT)
    Observable<BaseResponse> deleteAllCollect();

    /**
     * *用户收藏类型列表
     */
    @GET(URLConstant.ENSHRINE_TYPE)
    Observable<BaseResponse<SceneryType>> getEnshrineType();

    /**
     * 获取我点评过的信息
     *
     * @param page      页面
     * @param limitPage 每页条数
     */
    @GET(URLConstant.RECOMMEND_LIST)
    Observable<BaseResponse<CommentBean>> getRecommendList(@Query("page") String page, @Query(
            "limitPage") String limitPage);

    /**
     * 取消评论
     *
     * @param id id
     * @return
     */
    @GET(URLConstant.DELETE_COMMENT)
    Observable<BaseResponse> deleteComment(@Query("id") String id);

    /**
     * 删除所有评论
     *
     * @return
     */
    @GET(URLConstant.DELETE_ALL_COMMENT)
    Observable<BaseResponse> deleteAllComment();

    /**
     * 获取个人点赞过的信息
     *
     * @param page      页面
     * @param limitPage 每页条数
     */
    @GET(URLConstant.THUMB_LIST)
    Observable<BaseResponse<ThumbEntity>> getMyLikeList(@Query("page") String page, @Query(
            "limitPage") String limitPage);


    /**
     * 清空个人点赞记录.清空个人收藏记录
     *
     * @return
     */
    @GET(URLConstant.DELETE_ALL_THUMB)
    Observable<BaseResponse> deleteAllThumb();

    /**
     * 根据ID取消 收藏或者点赞\评论的记录
     * 根据资源id删除点赞记录
     *
     * @param reId id
     * @return
     */
    @GET(URLConstant.DELETE_THUMB)
    Observable<BaseResponse> deleteThumb(@Query("reId") String reId);

    /**
     * 新增投诉
     *
     * @param map 传参
     * @return
     */
    @GET(URLConstant.ADD_COMPLAINT)
    Observable<BaseResponse> addComplaint(@QueryMap HashMap<String, String> map);


    /**
     * 投诉列表
     *
     * @param limitPage 每页条数
     * @param page      页码
     * @param code
     * @param phone     手机号码
     * @param handle    是否处理 0：未处理 1：已处理
     * @return
     */
    @GET(URLConstant.COMPLAINT_LIST)
    Observable<BaseResponse<ComplaintListEntity>> getComplainList(@Query("limitPage") String limitPage, @Query("page") String page, @Query("code") String code, @Query("phone") String phone, @Query("handle") String handle);

    /**
     * 投诉详情
     *
     * @param id id
     * @return
     */
    @GET(URLConstant.COMPLAINT_DETAIL)
    Observable<BaseResponse<ComplaintDetail>> getComplainDetails(@Query("id") String id);

    /**
     * 栏目详情
     *
     * @param channelCode 栏目代码
     * @return
     */
    @GET(URLConstant.CHANNEL_DETAILS)
    Observable<BaseResponse<ChannelDetailsEntity>> getChannelDetails(@Query("channelCode") String channelCode);


    /**
     * 厕所列表
     *
     * @param name      名称
     * @param region    【必填】地区编码
     * @param limitPage 每页条数
     * @param page      当前页数
     * @return
     */
    @GET(URLConstant.TOILET_LIST)
    Observable<BaseResponse<ToiletEntity>> getToiletList(@Query("name") String name, @Query(
            "region") String region, @Query("limitPage") String limitPage,
                                                         @Query("page") String page);

    /**
     * 旅游资讯列表
     *
     * @param channelCode 栏目代码
     * @param sortBy      更新时间（UPDATE_TIME），发布时间（PUBLISH_TIME） 大小写不敏感
     * @param sortType    升序（ASC），降序（DESC）
     * @param limitPage   每页条数
     * @param page        当前页数
     * @return
     */
    @GET(URLConstant.NEWS_LIST)
    Observable<BaseResponse<NewsListEntity>> getNewsList(@Query("channelCode") String channelCode
            , @Query("sortBy") String sortBy, @Query("sortType") String sortType, @Query(
                    "limitPage") String limitPage, @Query("page") String page);

    /**
     * 保存
     *
     * @param summary    简介
     * @param name       标题
     * @param resourceId 资源id
     * @param logo       封面图
     * @return
     */
    @GET(URLConstant.TOURIST_SAVE)
    Observable<BaseResponse> touristSave(@Query("summary") String summary,
                                         @Query("name") String name,
                                         @Query("resourceId") String resourceId,
                                         @Query("logo") String logo);

    /**
     * 获取旅游资讯详情
     *
     * @param id          ID
     * @param channelCode 栏目code
     * @return
     */
    @GET(URLConstant.NEWS_DETAILS)
    Observable<BaseResponse<NewsListEntity>> getNewsDetails(@Query("id") String id, @Query(
            "channelCode") String channelCode);

    /**
     * 节庆活动详情
     *
     * @param id ID
     * @return
     */
    @GET(URLConstant.ACTIVITY_DETAILS)
    Observable<BaseResponse<ActivityDetailsEntity>> getActivityDetails(@Query("id") String id);

    /**
     * 全局搜索
     *
     * @return api/elasticsearch/searchAll
     * http://ptisp.daqsoft.com/govapi/api/elasticsearch/searchAll?lang=cn&limitPage=20&params
     * =%E9%9D%92%E7%A7%80%E5%B1%B1&siteCode=nngjapp&token=ddf9c188-fef3-4905-95e9-e8f4940dadc0
     */
    @GET(URLConstant.SEARCH_ALL)
    Call<ResponseBody> searchAll(@Query("isLimit") String isLimit, @Query("params") String params
            , @Query("isSearchName") String isSearchName);

    /**
     * 保存全局搜索
     *
     * @return
     */
    @GET(URLConstant.SEARCH_SAVE)
    Call<ResponseBody> saveSearch(@Query("type") String type, @Query("content") String content);

    /**
     * 分类列表
     *
     * @return
     */
    @GET(URLConstant.ROBOT_TYPE_LIST)
    Call<ResponseBody> findRobotTypeList();

    /**
     * 根据分类查询问答标签
     *
     * @param typeId 分类ID
     * @return
     */
    @GET(URLConstant.QUESTION_BY_TYPE)
    Call<ResponseBody> findQuestionByTypes(@Query("typeId") String typeId);

    /**
     * 获取机器人的相关信息
     *
     * @return
     */
    @GET(URLConstant.ROBOT_INFO)
    Call<ResponseBody> robotInfo();

    /**
     * 新增留言
     *
     * @param map 传参
     * @return
     */
    @GET(URLConstant.SAVE_MSG)
    Observable<BaseResponse> saveMsg(@QueryMap HashMap<String, String> map);


    /**
     * 在线留言列表页
     *
     * @param sort      排序字段
     * @param order     升序/降序 	ASC/DESC
     * @param page      页码
     * @param limitPage 每页条数
     * @return
     */
    @GET(URLConstant.MSG_LIST_SERVICE)
    Observable<BaseResponse<MessageEntity>> getMsgList(@Query("sort") String sort,
                                                       @Query("order") String order, @Query("page"
    ) String page, @Query("limitPage") String limitPage);


    /**
     * 留言详情
     *
     * @param id 留言ID
     * @return
     */
    @GET(URLConstant.MSG_DETAIL)
    Observable<BaseResponse<MessageDetail>> getMsgDetails(@Query("id") String id);


    /**
     * 意见采集
     *
     * @param phone       电话号
     * @param content     意见内容
     * @param opinionType 意见类型
     * @param images      图片集
     * @return
     */
    @GET(URLConstant.SAVE_OPINION_COLLECT)
    Observable<BaseResponse> addOpinion(@Query("phone") String phone,
                                        @Query("content") String content,
                                        @Query("opinionType") String opinionType,
                                        @Query("images") String images);

    /**
     * 获取消息列表
     */
    @GET(URLConstant.ME_MESSAGE_LIST)
    Observable<BaseResponse<MessageBean>> getMeMessage(@Query("page") String page, @Query(
            "limitPage") String limitPage);

    /**
     * 消息详情
     *
     * @param id 消息ID
     * @return
     */
    @GET(URLConstant.ME_MESSAGE_DETAIL)
    Observable<BaseResponse<MessageBean>> getMeMessageDetail(@Query("id") String id);

    /**
     * 消息标记为已读
     * @param id 消息ID
     * @return
     */
    @GET(URLConstant.READ_MESSAGE)
    Observable<BaseResponse> readMessage(@Query("id") String id);

    /**
     * 景区客流量数据
     */
    @GET(URLConstant.FLOW_LIST)
    Observable<BaseResponse<FlowListBean>> getFlowList(@Query("page") String page, @Query(
            "limitPage") String limitPage,@Query("name") String name);


    /**
     * 获取乡村旅游列表
     *
     * @param limitPage 每页条数
     * @param code      类型code
     * @param page      当前页
     * @param name      名称
     * @param tag       标签id 971 农家乐 ，972 乡村旅游
     * @param region    地区编码
     * @return
     */
    @GET(URLConstant.COUNTRY_LIST)
    Observable<BaseResponse<CountryBean>> getCountryList(@Query("limitPage") int limitPage,
                                                         @Query("type") String code, @Query("page"
    ) int page, @Query("name") String name, @Query("tag") Integer tag,
                                                         @Query("region") String region);

    /**
     * 获取身边游接口
     *
     * @param limitPage 每页条数
     * @return
     */
    @GET(URLConstant.NEAR_LIST)
    Observable<BaseResponse<ToiletEntity>> getAroundList(@Query("page") String page, @Query(
            "limitPage") String limitPage, @Query("latitude") String latitude,
                                                         @Query("longitude") String longitude,
                                                         @Query("sourceType") String sourceType,
                                                         @Query("name") String name, @Query(
                                                                 "distance") String distance);


    /**
     * 获取乡村旅游详情
     *
     * @param id 主键
     * @return
     */
    @GET(URLConstant.COUNTRY_DETAILS)
    Observable<BaseResponse<CountryBean>> getCountryDetails(@Query("id") String id);

    /**
     * 图片分页列表
     *
     * @param page
     * @param pageSize
     * @param datatype 资源类型【对应后台资源分类】
     *                 sourceType_1景区、
     *                 sourceType_2酒店、
     *                 sourceType_3旅行社、
     *                 sourceType_4购物场所、
     *                 sourceType_5娱乐场所、
     *                 sourceType_6餐饮场所、
     *                 sourceType_7乡村旅游、
     *                 sourceType_8特色美食、
     *                 门票：ticket，
     *                 游记攻略：travels
     * @return
     */
    @GET(URLConstant.SITE_PICTURE_LIST)
    Observable<BaseResponse<ImagesBean>> sitePictureList(@Query("page") Integer page, @Query(
            "limitPage") Integer pageSize, @Query("datatype") String datatype);

    @GET(URLConstant.SPECIAL_LIST)
    Observable<BaseResponse<SpecialListBean>> specialList(@Query("page") Integer page, @Query(
            "limitPage") Integer limitPage);

    @GET(URLConstant.SPECIAL_DETAIL)
    Observable<BaseResponse<SpecialDetailBean>> specialDetail(@Query("id") String id);

    /**
     * 旅游商品
     *
     * @param id
     * @return
     */
    @GET(URLConstant.SPECIAL_GOODS)
    Observable<BaseResponse<SpecialListBean>> specialGoodsDetail(@Query("page") Integer id,
                                                                 @Query("limitPage") Integer limitPage, @Query("name") String name);

    /**
     * 获取云宝宝三方登录信息
     *
     * @param code 云宝宝返回的数据
     * @return
     */
    @GET(URLConstant.YBB_URL_1)
    Observable<BaseResponse<UserEntity>> getYbbToken(@Query("code") String code);


    /**
     * 旅行社详情网络请求
     *
     * @param id 旅行社ID
     * @return
     */
    @GET(URLConstant.TRAVEL_DETAILS_URL)
    Observable<BaseResponse<TravelDetailsEntity>> getTravelDetails(@Query("id") String id);

    /**
     * 根据地区编码获取目的地基础数据
     *
     * @param region 地区编码
     * @return
     */
    @GET(URLConstant.DESTINATION_BASE_INFO)
    Observable<BaseResponse<DestinationInfoEntity>> getDestinationBaseInfo(@Query("region") String region);

    /**
     * 获取目的地推荐景区
     *
     * @param destinationId 目的地id
     * @param page
     * @param limitPage
     * @param lat           纬度
     * @param lng           经度
     * @return
     */
    @GET(URLConstant.DESTINATION_SCENERY)
    Observable<ScenicEntity> getDestinationScenery(@Query("destinationId") String destinationId,
                                                   @Query("page") int page,
                                                   @Query("limitPage") int limitPage, @Query(
                                                           "latitude") String lat, @Query(
                                                                   "longitude") String lng);

    /**
     * 获取目的地推荐美食列表
     *
     * @param destinationId 目的地id
     * @param page
     * @param limitPage
     * @return
     */
    @GET(URLConstant.DESTINATION_FOOD)
    Observable<BaseResponse<FoodEntity>> getDestinationFood(@Query("destinationId") String destinationId, @Query("page") int page, @Query("limitPage") int limitPage);

    /**
     * 获取目的地推荐酒店
     *
     * @param destinationId 目的地id
     * @param page
     * @param limitPage
     * @param lat           纬度
     * @param lng           经度
     * @return
     */
    @GET(URLConstant.DESTINATION_HOTEL)
    Observable<HotelEntity> getDestinationHotel(@Query("destinationId") String destinationId,
                                                @Query("page") int page,
                                                @Query("limitPage") int limitPage, @Query(
                                                        "latitude") String lat, @Query("longitude"
    ) String lng);

    /**
     * 根据经纬度获取一定距离范围内的相关产品数
     *
     * @param lat      纬度
     * @param lng      经度
     * @param distance 距离
     * @return
     */
    @GET(URLConstant.DESTINATION_PRODUCT)
    Observable<BaseResponse<DestinationProduct>> getDestinationProduct(@Query("lat") String lat,
                                                                       @Query("lng") String lng,
                                                                       @Query("distance") String distance);

    /**
     * 足迹列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return
     */
    @GET(URLConstant.FOOT_PRINT_LIST)
    Observable<BaseResponse<FootPrintListBean>> getFootPrintList(@Query("page") Integer page,
                                                                 @Query("limitPage") Integer pageSize);

    /**
     * 足迹统计信息
     *
     * @param id id
     * @return
     */
    @GET(URLConstant.GET_STATISTICS_MAP)
    Observable<BaseResponse<StatisticsMapBean>> getStatisticsMap(@Query("id") Integer id);

    /**
     * 足迹详情
     *
     * @param id 足迹id
     * @return
     */
    @GET(URLConstant.TOURIST_DETAIL)
    Observable<BaseResponse<TouristDetailBean>> touristDetail(@Query("id") String id);


    /**
     * 导游级别筛选列表
     *
     * @return
     */
    @GET(URLConstant.GUIDE_TYPE_LIST)
    Observable<BaseResponse<GuideTypeEntity>> getGuideTypeList();

    /**
     * 导游列表
     *
     * @param keyword
     * @param keyword   关键字/导游证号
     * @param limitPage 每页条数
     * @param page      页码
     * @param level     等级
     * @param gender    性别
     * @param region    地区
     * @return
     */
    @GET(URLConstant.GUIDE_BEAN_LIST)
    Observable<BaseResponse<GuideListBean>> getGuideList(@Query("keyword") String keyword,
                                                         @Query("limitPage") int limitPage,
                                                         @Query("page") int page,
                                                         @Query("gender") String gender, @Query(
                                                                 "region") String region, @Query(
                                                                         "level") String level);


    /**
     * 探风景列表
     *
     * @param limitPage 每页条数
     * @param page      页码
     * @return
     */
    @GET(URLConstant.MONITOR_LIST)
    Observable<BaseResponse<MonitorListBean>> getMonitorList(@Query("limitPage") String limitPage
            , @Query("page") String page);
}
