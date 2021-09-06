package com.daqsoft.busquery.http;

import com.daqsoft.busquery.bean.TrainCodeBean;
import com.daqsoft.busquery.bean.TrainDetailBean;
import com.daqsoft.busquery.bean.TrainListBean;
import com.daqsoft.busquery.buscommon.BusCommon;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 功能
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-4-10.18:35
 * @since JDK 1.8
 */

public interface BusApiService {
    /**
     * 公交列表
     */
    @GET(BusCommon.LIST_BUG)
    Call<ResponseBody> getBusList(
            @Query("city") String city,
            @Query("cityd") String cityd,
            @Query("origin") String origin,
            @Query("destination") String destination
    );

    /**
     * 附近公交
     */
    @GET(BusCommon.BUS_NEARBY)
    Call<ResponseBody> aroundBus(@Query("location") String location,
                                 @Query("radius") String radius);

    /**
     * 获取全国所有站点
     */
    @GET(BusCommon.STATION)
    Call<ResponseBody> getCodeStation(@Query("initial") String initial,
                                      @Query("name") String name);

    /**
     * 获取热门城市
     */
    @GET(BusCommon.HOT_CITY)
    Call<ResponseBody> getHotStation();

    /**
     * 获取历史城市
     */
    @GET(BusCommon.STATION_SEARCH)
    Call<ResponseBody> searchStation();

    /**
     * 火车时刻表
     */
    @GET(BusCommon.TRAIN_LIST)
    Observable<BaseResponse<TrainListBean>> getRrainLis(@Query("deptDate") String deptDate,
                                                        @Query("deptStationCode") String deptStationCode,
                                                        @Query("arrStationCode") String arrStationCode);

    /**
     * 火车时刻表保存搜索
     */
    @GET(BusCommon.SAVE_STATION)
    Call<ResponseBody> saveStation(@Query("name") String name);

    /**
     * 火车时刻表详情
     */
    @GET(BusCommon.TRAIN_DETAIL)
    Observable<TrainDetailBean> getTrainDetail(@Query("trainNo") String trainNo,
                                               @Query("deptStationCode") String deptStationCode,
                                               @Query("arrStationCode") String arrStationCode,
                                               @Query("deptDate") String deptDate);

    /**
     * 火车车次编码
     */
    @GET(BusCommon.TRAIN_CODE)
    Observable<BaseResponse<TrainCodeBean>> getTrainCode(@Query("deptDate") String deptDate,
                                                         @Query("deptStationCode") String deptStationCode,
                                                         @Query("arrStationCode") String arrStationCode);

}
