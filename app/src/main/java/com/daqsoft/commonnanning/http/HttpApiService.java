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
 * ????????????????????????????????????
 *
 * @author ??????
 * @version 1.0.0
 * @date 2018/9/11 0011
 * @since JDK 1.8
 */
public interface HttpApiService {

    /**
     * ??????????????????
     */
    public static HashMap<String, String> REQUESTMAP = new HashMap<>();


    /**
     * APP????????????
     *
     * @param appId   app????????????
     * @param method  ??????AppVersion
     * @param token   daqsoft
     * @param appType 1
     * @param version ?????????
     * @return
     */
    @GET(ProjectConfig.VERSION_URL)
    Call<ResponseBody> checkVersion(@Query("AppId") String appId, @Query("Method") String method,
                                    @Query("token") String token,
                                    @Query("AppType") String appType,
                                    @Query("VersionCode") String version);

    /**
     * ??????????????????
     *
     * @param chanelCode ????????????
     * @param page       ?????????
     * @param limitPage  ???????????????
     * @return
     */
    @GET(URLConstant.INDEX_NOTICE)
    Observable<IndexNotify> getIndexNotify(@Query("chanelCode") String chanelCode,
                                           @Query("page") String page,
                                           @Query("limitPage") String limitPage);

    /**
     * ????????????
     */
    @GET(URLConstant.SAVE_THUMB)
    Observable<BaseResponse> saveThumb(@Query("reId") String reId,
                                       @Query("content") String content,
                                       @Query("target") String target,
                                       @Query("sourceType") String sourceTyp);

    /**
     * ????????????
     */
    @GET(URLConstant.SAVE_COMMENT)
    Observable<BaseResponse> saveComment(@Query("titleApp") String title_app,
                                         @Query("sourceType") String sourceType,
                                         @Query("reId") String reId,
                                         @Query("comment") String comment,
                                         @Query("star") String star, @Query("pics") String pics,
                                         @Query("target") String target);

    /**
     * ??????token????????????
     */
    @GET(URLConstant.TOKEN_INTERCEPT)
    Observable<BaseResponse> checkToken();

    /**
     * ????????????
     */
    @GET(URLConstant.DELE_THUMB)
    Observable<BaseResponse> saveThumb(@Query("id") String id);

    /**
     * ????????????
     * id ??????ID
     */
    @GET(URLConstant.JOINJOURNEY)
    Observable<BaseResponse> joinJourny(@Query("id") String id,
                                        @Query("sourceId") String sourceId,
                                        @Query("date") String date);

    /**
     * ????????????
     *
     * @return
     */
    @GET(URLConstant.CREATE_JOURNEY)
    Observable<BaseResponse<RouteEnty>> createJourny(@Query("name") String name);

    /**
     * ????????????
     */
    @GET(URLConstant.SCENIC_DETAIL)
    Observable<BaseResponse<ScenicDetail>> getScenicDetail(@Query("sceneryId") String sceneryId);

    /**
     * ????????????????????????
     */
    @GET(URLConstant.DELETE_ENSHRINE2)
    Observable<BaseResponse> delCollection(@Query("reId") String reId,
                                           @Query("sourceType") String sourceTyp);

    /**
     * ????????????????????????
     */
    @GET(URLConstant.SAVE_ENSHRINE)
    Observable<BaseResponse> saveCollection(@Query("reId") String reId,
                                            @Query("sourceType") String sourceTyp, @Query(
                                                    "content") String content,
                                            @Query("target") String target);

    /**
     * ????????????
     *
     * @return
     */
    @GET(URLConstant.HOTEL_DETAIL)
    Observable<BaseResponse<HotelDetail>> getHotelDetail(@Query("id") String id);

    /**
     * ???????????????
     *
     * @return
     */
    @GET(URLConstant.HOTEL_DETAIL_LISTTIP)
    Observable<BaseResponse<HotelTip>> getHotelListTip(@Query("resourceId") String id);

    /**
     * ??????????????????
     */
    @GET(URLConstant.GET_COMMENT_INFO)
    Observable<BaseResponse<CommentBean>> getCommentInfo(@Query("reId") String reId, @Query(
            "sourceType") String sourceTyp, @Query("page") String page,
                                                         @Query("limitPage") String limitPage);

    /**
     * ??????????????????????????????
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
     * ??????????????????????????????
     * ????????????
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
     * ????????????????????????
     *
     * @return
     */
    @GET(URLConstant.GUIDE_DETAIL)
    Observable<BaseResponse<GuideDetail>> getMapGuideDetail(@Query("id") String id);

    /**
     * ?????????????????????
     *
     * @return
     */
    @GET(URLConstant.SCENIC_CHILD_LIST)
    Observable<BaseResponse<ScenicChild>> getScenicChild(@Query("page") String page, @Query(
            "limitPage") String limitPage, @Query("sceneryId") String sceneryId);

    /**
     * ???????????????????????????
     *
     * @return
     */
    @GET(URLConstant.SCENIC_CHILD_DETAIL)
    Observable<BaseResponse<ScenicChildDetail>> getScenicChildDetail(@Query("childId") String childId);

    /**
     * ?????????????????????
     *
     * @return
     */
    @GET(URLConstant.SCENIC_VIDEO)
    Observable<BaseResponse<ScenicVideo>> getScenicVideo(@Query("page") String page, @Query(
            "limitPage") String limitPage, @Query("sceneryId") String sceneryId);

    /**
     * ????????????????????????
     *
     * @param page      ?????????
     * @param limitPage ????????????
     * @param datatype  ????????????
     *                  sourceType_1?????????
     *                  sourceType_2?????????
     *                  sourceType_3????????????
     *                  sourceType_4???????????????
     *                  sourceType_5???????????????
     *                  sourceType_6???????????????
     *                  sourceType_7???????????????
     *                  sourceType_8???????????????
     *                  ?????????ticket???
     *                  ???????????????travels
     * @param order     ??????/?????? asc?????????desc??? ???
     * @param sort      ????????????
     * @param region    ????????????
     * @param typeCode  ????????????
     * @param dataid    ??????id
     * @param orgId     ????????????
     * @param tag       ??????
     * @param name      ??????
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
     * ???????????????????????????
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
     * ???????????????????????????
     *
     * @return
     */
    @GET(URLConstant.SCENIC_TRAFFIC)
    Observable<BaseResponse<ScenicTies>> getScenicTraffic(@Query("sceneryId") String sceneryId);

    /**
     * ????????????banner
     *
     * @param adCode ?????????????????????????????? index_top???????????????????????????????????????????????????
     * @return
     */
    @GET(URLConstant.ADVERTISING)
    Observable<BaseResponse<AdvertEntity>> getIndexBannar(@Query("adCode") String adCode);

    /**
     * ??????720
     *
     * @return
     */
    @GET(URLConstant.PANORAMA_LIST)
    Observable<BaseResponse<PanoramaListBean>> getPanoramaList(@Query("page") String page,
                                                               @Query("limitPage") String limitPage);

    /**
     * ??????????????????
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
     * ?????????????????????
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
     * ?????????????????????
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
     * ??????????????????
     *
     * @param id id
     * @return
     */
    @GET(URLConstant.MY_STRATEGY_DETAIL)
    Observable<BaseResponse<StrategyDetail>> getLineDetail(@Query("id") String id);


    /**
     * ????????????
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
     * ????????????
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
     * ????????????
     *
     * @param page
     * @param limitPage
     * @param type          ?????? ????????? 0 pc??? 1 app??????
     * @param distinct      ?????????KM???
     * @param resourceLevel ????????????
     * @param orderType     ????????????
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
     * banner??????
     *
     * @param adCode ??????
     * @return
     */
    @GET(URLConstant.ADVERTISING)
    Observable<BannerEntity> getBannerList(@Query("adCode") String adCode);


    /**
     * ??????????????????(????????????)
     *
     * @return
     */
    @GET(URLConstant.SCENIC_TOTAL_LIST)
    Observable<ScenicEntity> getScenicList(@QueryMap HashMap<String, String> map);

    /**
     * ??????????????????
     * resourceLevel scenicTheme  matterCrowd resourceType
     */
    @GET(URLConstant.SCENIC_TOTAL_LIST)
    Observable<ScenicEntity> searchScenicList(@QueryMap HashMap<String, String> map);


    /**
     * ??????????????????
     * resourceLevel scenicTheme  matterCrowd resourceType
     */
    @GET(URLConstant.HOTEL_TOTAL_LIST)
    Observable<HotelEntity> searchHotelList(@QueryMap HashMap<String, String> map);

    /**
     * ????????????
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
     * ????????????
     *
     * @param moreSize ????????????????????????
     * @param id       ??????id
     * @param token    ?????????????????????????????????
     * @return
     */
    @GET(URLConstant.FOOD_DETAIL_LIST)
    Observable<FoodDetialEntity> getFoodDetial(@Query("moreSize") String moreSize,
                                               @Query("id") String id,
                                               @Query("token") String token);

    /**
     * @param reId       ???????????????ID
     * @param sourceType ???????????? :sourceType_8 ??????
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
     * ????????????
     *
     * @return
     */
    @GET(URLConstant.HOTEL_TOTAL_LIST)
    Observable<HotelEntity> getHotelList(@QueryMap HashMap<String, String> map);

    /**
     * ????????????
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
     * ????????????
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
     * ??????
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
     * ???????????????????????????
     *
     * @param version ?????????????????????
     */
    @GET(URLConstant.SITE_AGREEMENT)
    Observable<BaseResponse<AgreeMentEntity>> getSiteAgreement(@Query("version") String version);

    /**
     * ??????
     *
     * @param account  ??????
     * @param password ??????
     */
    @GET(URLConstant.LOGIN)
    Observable<BaseResponse<UserEntity>> login(@Query("ignoreCode") String ignoreCode, @Query(
            "account") String account, @Query("password") String password);

    /**
     * ??????????????????????????????
     *
     * @param thirdAccount ????????????
     * @param type         ??????
     */
    @GET(URLConstant.BINDING_INFO)
    Observable<BaseResponse<BindPhoneEntity>> bindingInfo(@Query("thirdAccount") String thirdAccount, @Query("type") String type);

    /**
     * ???????????????????????????
     *
     * @param password     ??????
     * @param thirdAccount ?????????????????????openid
     * @param phone        ?????????
     * @param type         ????????????????????? 1,QQ 2
     */
    @GET(URLConstant.BIND_ACCOUNT)
    Observable<BaseResponse<UserEntity>> bindingAccount(@Query("password") String password,
                                                        @Query("thirdAccount") String thirdAccount, @Query("phone") String phone, @Query("type") String type);

    /**
     * ????????????
     *
     * @param account        ??????
     * @param password       ??????
     * @param affirmPassword ????????????
     * @param msgCode        ???????????????
     */
    @POST(URLConstant.FIND_PWD)
    Observable<BaseResponse> findPwd(@Query("account") String account,
                                     @Query("password") String password,
                                     @Query("affirmPassword") String affirmPassword, @Query(
                                             "msgCode") String msgCode);

    /**
     * ??????????????????
     *
     * @param phone ????????????
     * @param type  ??????????????????
     */
    @POST(URLConstant.SEND_MSG)
    Observable<BaseResponse> getSendMsg(@Query("phone") String phone, @Query("type") String type);

    /**
     * ?????????????????????
     *
     * @param phone   ?????????
     * @param type    ????????????
     * @param msgCode ?????????
     */
    @POST(URLConstant.CHECK_MSG)
    Observable<BaseResponse> checkMsg(@Query("phone") String phone, @Query("type") String type,
                                      @Query("msgCode") String msgCode);

    /**
     * ????????????????????????????????????
     *
     * @param phone ?????????
     */
    @POST(URLConstant.ACCOUNT_EXIST)
    Observable<BaseResponse<BindPhoneEntity>> accountExist(@Query("account") String phone);

    /**
     * ????????????
     *
     * @param page
     * @param limitPage
     */
    @GET(URLConstant.ACTIVITY_LIST)
    Observable<BaseResponse<IndexActivity>> getActivityList(@Query("page") String page, @Query(
            "limitPage") String limitPage, @Query("title") String title,
                                                            @Query("channelCode") String channelCode);

    /**
     * ????????????
     *
     * @param page
     * @param limitPage
     */
    @GET(URLConstant.ACTIVITY_LIST)
    Observable<BaseResponse<IndexActivity>> getActivityList(@Query("page") String page, @Query(
            "limitPage") String limitPage, @Query("title") String title,
                                                            @Query("channelCode") String channelCode, @Query("region") String region);

    /**
     * ??????????????????
     *
     * @param page
     * @param limitPage
     * @param title     ??????????????????????????????	string	??????
     * @param status    ????????????	number	10????????????1??????????????????????????????????????????????????????
     * @param region    ????????????
     * @return
     */
    @GET(URLConstant.MY_STRATEGY_LIST)
    Observable<BaseResponse<MyStrategyListBean>> getLineList(@Query("page") String page, @Query(
            "limitPage") String limitPage, @Query("title") String title,
                                                             @Query("status") String status,
                                                             @Query("region") String region);

    /**
     * ?????????????????????????????????(?????????????????????)
     */
    @GET(URLConstant.DESTINATION_LIST)
    Observable<BaseResponse<DestinationInfoEntity>> getDestinationList(@Query("limitPage") String limitPage);

    /**
     * ??????????????????????????????
     *
     * @return
     */
    @GET(URLConstant.SITE_REGIONS)
    Observable<BaseResponse<RegionEntity>> getSiteRegions();

    /**
     * ????????????
     *
     * @param page
     * @param limitPage
     */
    @GET(URLConstant.ACTIVITY_LIST2)
    Observable<BaseResponse<IndexActivity>> getActivityList2(@Query("page") String page, @Query(
            "limitPage") String limitPage, @Query("title") String title,
                                                             @Query("channelCode") String channelCode);

    /**
     * ???????????????
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

    /** ---????????????------------------------------------------------------------------------*/
    /**
     * ????????????
     *
     * @return
     */
    @GET(URLConstant.SCENERY_LEVEL)
    Observable<SelectEntity> getSceneryLevel();

    /**
     * ????????????
     *
     * @return
     */
    @GET(URLConstant.SCENERY_THEME)
    Observable<SelectEntity> getSceneryTheme();

    /**
     * ????????????
     *
     * @return
     */
    @GET(URLConstant.SCENERY_CROWD)
    Observable<SelectEntity> getSceneryCrowd();

    /**
     * ????????????
     *
     * @return
     */
    @GET(URLConstant.SCENERY_TYPE)
    Observable<SelectEntity> getSceneryType();

    /**
     * ??????????????????
     *
     * @return
     */
    @GET(URLConstant.SCENERY_TYPE_NEW)
    Observable<BaseResponse<ScenicSeceledEnty>> getScenerySceletedType();

    /**
     * ??????????????????
     *
     * @return
     */
    @GET(URLConstant.RECREATION_LISTDICT)
    Observable<BaseResponse<ListDictBean>> recreationListDict();

    /**
     * ????????????
     *
     * @param id
     * @return
     */
    @GET(URLConstant.RECREATION_DETAIL)
    Observable<BaseResponse<EntertainmentDetailBean>> recreationDetai(@Query("id") long id);

    /**
     * ??????????????????
     *
     * @param limitPage ????????????
     * @param page      ?????????
     * @param type      ????????????code
     * @param name      ??????
     * @param tag       ??????id????????????
     * @param recommend 1????????????
     * @return
     */
    @GET(URLConstant.RECREATION_LIST)
    Observable<BaseResponse<EntertainmentListBean>> recreationList(@Query("limitPage") Integer limitPage, @Query("page") Integer page, @Query("type") String type, @Query("name") String name, @Query("tag") String tag, @Query("recommend") Integer recommend);

    /**
     * ????????????
     *
     * @return
     */
    @GET(URLConstant.HOTEL_LEVELLIST)
    Observable<SelectEntity> getHotelLevelList();

    /**
     * ????????????
     *
     * @return
     */
    @GET(URLConstant.HOTELT_YPELIST)
    Observable<SelectEntity> getHotelTypeList();

    /**
     * ????????????
     *
     * @return
     */
    @GET(URLConstant.HOTEL_SERVICE)
    Call<ResponseBody> getHotelService();

    /**----------------------------------------------------------------------------------*/

    /**
     * ??????
     *
     * @param account  ??????
     * @param password ??????
     * @param msgCode  ???????????????
     * @return
     */
    @POST(URLConstant.REGISTER)
    Observable<BaseResponse> register(@Query("account") String account,
                                      @Query("password") String password,
                                      @Query("msgCode") String msgCode,
                                      @Query("ignoreCode") String ignoreCode);

    /**
     * ????????????
     *
     * @param oldPassword ?????????
     * @param newPassword ?????????
     * @return
     */
    @POST(URLConstant.UPDATE_PWD)
    Observable<BaseResponse> updatePwd(@Query("oldPassword") String oldPassword, @Query(
            "newPassword") String newPassword);

    /**
     * @param partList ??????????????????????????????
     * @return
     */
    @Multipart
    @POST("upload")
    Observable<UpImgEntity> upImg(@Part List<MultipartBody.Part> partList);

    /**
     * ??????????????????
     *
     * @param map
     * @return
     */
    @GET(URLConstant.UPDATE_USER_INFO)
    Observable<BaseResponse> updateUserInfo(@QueryMap Map<String, String> map);

    /**
     * ??????????????????
     */
    @GET(URLConstant.USER_INFO)
    Observable<BaseResponse<UserInfoEntity>> getUserInfo();

    /**
     * ??????????????????????????????????????????????????????
     *
     * @param page       ??????
     * @param limitPage  ????????????
     * @param sourceType ????????????
     */
    @GET(URLConstant.ENSHRINE_LIST)
    Observable<BaseResponse<EnshrineEntity>> getEnshrineList(@Query("page") String page, @Query(
            "limitPage") String limitPage, @Query("sourceType") String sourceType);

    /**
     * ????????????
     *
     * @param id id
     */
    @GET(URLConstant.DELETE_ENSHRINE)
    Observable<BaseResponse> deleteEnshrine(@Query("id") String id);

    /**
     * ????????????
     */
    @GET(URLConstant.DELETE_ALL_COLLECT)
    Observable<BaseResponse> deleteAllCollect();

    /**
     * *????????????????????????
     */
    @GET(URLConstant.ENSHRINE_TYPE)
    Observable<BaseResponse<SceneryType>> getEnshrineType();

    /**
     * ???????????????????????????
     *
     * @param page      ??????
     * @param limitPage ????????????
     */
    @GET(URLConstant.RECOMMEND_LIST)
    Observable<BaseResponse<CommentBean>> getRecommendList(@Query("page") String page, @Query(
            "limitPage") String limitPage);

    /**
     * ????????????
     *
     * @param id id
     * @return
     */
    @GET(URLConstant.DELETE_COMMENT)
    Observable<BaseResponse> deleteComment(@Query("id") String id);

    /**
     * ??????????????????
     *
     * @return
     */
    @GET(URLConstant.DELETE_ALL_COMMENT)
    Observable<BaseResponse> deleteAllComment();

    /**
     * ??????????????????????????????
     *
     * @param page      ??????
     * @param limitPage ????????????
     */
    @GET(URLConstant.THUMB_LIST)
    Observable<BaseResponse<ThumbEntity>> getMyLikeList(@Query("page") String page, @Query(
            "limitPage") String limitPage);


    /**
     * ????????????????????????.????????????????????????
     *
     * @return
     */
    @GET(URLConstant.DELETE_ALL_THUMB)
    Observable<BaseResponse> deleteAllThumb();

    /**
     * ??????ID?????? ??????????????????\???????????????
     * ????????????id??????????????????
     *
     * @param reId id
     * @return
     */
    @GET(URLConstant.DELETE_THUMB)
    Observable<BaseResponse> deleteThumb(@Query("reId") String reId);

    /**
     * ????????????
     *
     * @param map ??????
     * @return
     */
    @GET(URLConstant.ADD_COMPLAINT)
    Observable<BaseResponse> addComplaint(@QueryMap HashMap<String, String> map);


    /**
     * ????????????
     *
     * @param limitPage ????????????
     * @param page      ??????
     * @param code
     * @param phone     ????????????
     * @param handle    ???????????? 0???????????? 1????????????
     * @return
     */
    @GET(URLConstant.COMPLAINT_LIST)
    Observable<BaseResponse<ComplaintListEntity>> getComplainList(@Query("limitPage") String limitPage, @Query("page") String page, @Query("code") String code, @Query("phone") String phone, @Query("handle") String handle);

    /**
     * ????????????
     *
     * @param id id
     * @return
     */
    @GET(URLConstant.COMPLAINT_DETAIL)
    Observable<BaseResponse<ComplaintDetail>> getComplainDetails(@Query("id") String id);

    /**
     * ????????????
     *
     * @param channelCode ????????????
     * @return
     */
    @GET(URLConstant.CHANNEL_DETAILS)
    Observable<BaseResponse<ChannelDetailsEntity>> getChannelDetails(@Query("channelCode") String channelCode);


    /**
     * ????????????
     *
     * @param name      ??????
     * @param region    ????????????????????????
     * @param limitPage ????????????
     * @param page      ????????????
     * @return
     */
    @GET(URLConstant.TOILET_LIST)
    Observable<BaseResponse<ToiletEntity>> getToiletList(@Query("name") String name, @Query(
            "region") String region, @Query("limitPage") String limitPage,
                                                         @Query("page") String page);

    /**
     * ??????????????????
     *
     * @param channelCode ????????????
     * @param sortBy      ???????????????UPDATE_TIME?????????????????????PUBLISH_TIME??? ??????????????????
     * @param sortType    ?????????ASC???????????????DESC???
     * @param limitPage   ????????????
     * @param page        ????????????
     * @return
     */
    @GET(URLConstant.NEWS_LIST)
    Observable<BaseResponse<NewsListEntity>> getNewsList(@Query("channelCode") String channelCode
            , @Query("sortBy") String sortBy, @Query("sortType") String sortType, @Query(
                    "limitPage") String limitPage, @Query("page") String page);

    /**
     * ??????
     *
     * @param summary    ??????
     * @param name       ??????
     * @param resourceId ??????id
     * @param logo       ?????????
     * @return
     */
    @GET(URLConstant.TOURIST_SAVE)
    Observable<BaseResponse> touristSave(@Query("summary") String summary,
                                         @Query("name") String name,
                                         @Query("resourceId") String resourceId,
                                         @Query("logo") String logo);

    /**
     * ????????????????????????
     *
     * @param id          ID
     * @param channelCode ??????code
     * @return
     */
    @GET(URLConstant.NEWS_DETAILS)
    Observable<BaseResponse<NewsListEntity>> getNewsDetails(@Query("id") String id, @Query(
            "channelCode") String channelCode);

    /**
     * ??????????????????
     *
     * @param id ID
     * @return
     */
    @GET(URLConstant.ACTIVITY_DETAILS)
    Observable<BaseResponse<ActivityDetailsEntity>> getActivityDetails(@Query("id") String id);

    /**
     * ????????????
     *
     * @return api/elasticsearch/searchAll
     * http://ptisp.daqsoft.com/govapi/api/elasticsearch/searchAll?lang=cn&limitPage=20&params
     * =%E9%9D%92%E7%A7%80%E5%B1%B1&siteCode=nngjapp&token=ddf9c188-fef3-4905-95e9-e8f4940dadc0
     */
    @GET(URLConstant.SEARCH_ALL)
    Call<ResponseBody> searchAll(@Query("isLimit") String isLimit, @Query("params") String params
            , @Query("isSearchName") String isSearchName);

    /**
     * ??????????????????
     *
     * @return
     */
    @GET(URLConstant.SEARCH_SAVE)
    Call<ResponseBody> saveSearch(@Query("type") String type, @Query("content") String content);

    /**
     * ????????????
     *
     * @return
     */
    @GET(URLConstant.ROBOT_TYPE_LIST)
    Call<ResponseBody> findRobotTypeList();

    /**
     * ??????????????????????????????
     *
     * @param typeId ??????ID
     * @return
     */
    @GET(URLConstant.QUESTION_BY_TYPE)
    Call<ResponseBody> findQuestionByTypes(@Query("typeId") String typeId);

    /**
     * ??????????????????????????????
     *
     * @return
     */
    @GET(URLConstant.ROBOT_INFO)
    Call<ResponseBody> robotInfo();

    /**
     * ????????????
     *
     * @param map ??????
     * @return
     */
    @GET(URLConstant.SAVE_MSG)
    Observable<BaseResponse> saveMsg(@QueryMap HashMap<String, String> map);


    /**
     * ?????????????????????
     *
     * @param sort      ????????????
     * @param order     ??????/?????? 	ASC/DESC
     * @param page      ??????
     * @param limitPage ????????????
     * @return
     */
    @GET(URLConstant.MSG_LIST_SERVICE)
    Observable<BaseResponse<MessageEntity>> getMsgList(@Query("sort") String sort,
                                                       @Query("order") String order, @Query("page"
    ) String page, @Query("limitPage") String limitPage);


    /**
     * ????????????
     *
     * @param id ??????ID
     * @return
     */
    @GET(URLConstant.MSG_DETAIL)
    Observable<BaseResponse<MessageDetail>> getMsgDetails(@Query("id") String id);


    /**
     * ????????????
     *
     * @param phone       ?????????
     * @param content     ????????????
     * @param opinionType ????????????
     * @param images      ?????????
     * @return
     */
    @GET(URLConstant.SAVE_OPINION_COLLECT)
    Observable<BaseResponse> addOpinion(@Query("phone") String phone,
                                        @Query("content") String content,
                                        @Query("opinionType") String opinionType,
                                        @Query("images") String images);

    /**
     * ??????????????????
     */
    @GET(URLConstant.ME_MESSAGE_LIST)
    Observable<BaseResponse<MessageBean>> getMeMessage(@Query("page") String page, @Query(
            "limitPage") String limitPage);

    /**
     * ????????????
     *
     * @param id ??????ID
     * @return
     */
    @GET(URLConstant.ME_MESSAGE_DETAIL)
    Observable<BaseResponse<MessageBean>> getMeMessageDetail(@Query("id") String id);

    /**
     * ?????????????????????
     * @param id ??????ID
     * @return
     */
    @GET(URLConstant.READ_MESSAGE)
    Observable<BaseResponse> readMessage(@Query("id") String id);

    /**
     * ?????????????????????
     */
    @GET(URLConstant.FLOW_LIST)
    Observable<BaseResponse<FlowListBean>> getFlowList(@Query("page") String page, @Query(
            "limitPage") String limitPage,@Query("name") String name);


    /**
     * ????????????????????????
     *
     * @param limitPage ????????????
     * @param code      ??????code
     * @param page      ?????????
     * @param name      ??????
     * @param tag       ??????id 971 ????????? ???972 ????????????
     * @param region    ????????????
     * @return
     */
    @GET(URLConstant.COUNTRY_LIST)
    Observable<BaseResponse<CountryBean>> getCountryList(@Query("limitPage") int limitPage,
                                                         @Query("type") String code, @Query("page"
    ) int page, @Query("name") String name, @Query("tag") Integer tag,
                                                         @Query("region") String region);

    /**
     * ?????????????????????
     *
     * @param limitPage ????????????
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
     * ????????????????????????
     *
     * @param id ??????
     * @return
     */
    @GET(URLConstant.COUNTRY_DETAILS)
    Observable<BaseResponse<CountryBean>> getCountryDetails(@Query("id") String id);

    /**
     * ??????????????????
     *
     * @param page
     * @param pageSize
     * @param datatype ??????????????????????????????????????????
     *                 sourceType_1?????????
     *                 sourceType_2?????????
     *                 sourceType_3????????????
     *                 sourceType_4???????????????
     *                 sourceType_5???????????????
     *                 sourceType_6???????????????
     *                 sourceType_7???????????????
     *                 sourceType_8???????????????
     *                 ?????????ticket???
     *                 ???????????????travels
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
     * ????????????
     *
     * @param id
     * @return
     */
    @GET(URLConstant.SPECIAL_GOODS)
    Observable<BaseResponse<SpecialListBean>> specialGoodsDetail(@Query("page") Integer id,
                                                                 @Query("limitPage") Integer limitPage, @Query("name") String name);

    /**
     * ?????????????????????????????????
     *
     * @param code ????????????????????????
     * @return
     */
    @GET(URLConstant.YBB_URL_1)
    Observable<BaseResponse<UserEntity>> getYbbToken(@Query("code") String code);


    /**
     * ???????????????????????????
     *
     * @param id ?????????ID
     * @return
     */
    @GET(URLConstant.TRAVEL_DETAILS_URL)
    Observable<BaseResponse<TravelDetailsEntity>> getTravelDetails(@Query("id") String id);

    /**
     * ?????????????????????????????????????????????
     *
     * @param region ????????????
     * @return
     */
    @GET(URLConstant.DESTINATION_BASE_INFO)
    Observable<BaseResponse<DestinationInfoEntity>> getDestinationBaseInfo(@Query("region") String region);

    /**
     * ???????????????????????????
     *
     * @param destinationId ?????????id
     * @param page
     * @param limitPage
     * @param lat           ??????
     * @param lng           ??????
     * @return
     */
    @GET(URLConstant.DESTINATION_SCENERY)
    Observable<ScenicEntity> getDestinationScenery(@Query("destinationId") String destinationId,
                                                   @Query("page") int page,
                                                   @Query("limitPage") int limitPage, @Query(
                                                           "latitude") String lat, @Query(
                                                                   "longitude") String lng);

    /**
     * ?????????????????????????????????
     *
     * @param destinationId ?????????id
     * @param page
     * @param limitPage
     * @return
     */
    @GET(URLConstant.DESTINATION_FOOD)
    Observable<BaseResponse<FoodEntity>> getDestinationFood(@Query("destinationId") String destinationId, @Query("page") int page, @Query("limitPage") int limitPage);

    /**
     * ???????????????????????????
     *
     * @param destinationId ?????????id
     * @param page
     * @param limitPage
     * @param lat           ??????
     * @param lng           ??????
     * @return
     */
    @GET(URLConstant.DESTINATION_HOTEL)
    Observable<HotelEntity> getDestinationHotel(@Query("destinationId") String destinationId,
                                                @Query("page") int page,
                                                @Query("limitPage") int limitPage, @Query(
                                                        "latitude") String lat, @Query("longitude"
    ) String lng);

    /**
     * ????????????????????????????????????????????????????????????
     *
     * @param lat      ??????
     * @param lng      ??????
     * @param distance ??????
     * @return
     */
    @GET(URLConstant.DESTINATION_PRODUCT)
    Observable<BaseResponse<DestinationProduct>> getDestinationProduct(@Query("lat") String lat,
                                                                       @Query("lng") String lng,
                                                                       @Query("distance") String distance);

    /**
     * ????????????
     *
     * @param page     ??????
     * @param pageSize ????????????
     * @return
     */
    @GET(URLConstant.FOOT_PRINT_LIST)
    Observable<BaseResponse<FootPrintListBean>> getFootPrintList(@Query("page") Integer page,
                                                                 @Query("limitPage") Integer pageSize);

    /**
     * ??????????????????
     *
     * @param id id
     * @return
     */
    @GET(URLConstant.GET_STATISTICS_MAP)
    Observable<BaseResponse<StatisticsMapBean>> getStatisticsMap(@Query("id") Integer id);

    /**
     * ????????????
     *
     * @param id ??????id
     * @return
     */
    @GET(URLConstant.TOURIST_DETAIL)
    Observable<BaseResponse<TouristDetailBean>> touristDetail(@Query("id") String id);


    /**
     * ????????????????????????
     *
     * @return
     */
    @GET(URLConstant.GUIDE_TYPE_LIST)
    Observable<BaseResponse<GuideTypeEntity>> getGuideTypeList();

    /**
     * ????????????
     *
     * @param keyword
     * @param keyword   ?????????/????????????
     * @param limitPage ????????????
     * @param page      ??????
     * @param level     ??????
     * @param gender    ??????
     * @param region    ??????
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
     * ???????????????
     *
     * @param limitPage ????????????
     * @param page      ??????
     * @return
     */
    @GET(URLConstant.MONITOR_LIST)
    Observable<BaseResponse<MonitorListBean>> getMonitorList(@Query("limitPage") String limitPage
            , @Query("page") String page);
}
