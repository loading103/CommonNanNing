package com.daqsoft.commonnanning.ui.trace

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Marker
import com.amap.api.maps.model.MarkerOptions
import com.daqsoft.android.ProjectConfig
import com.daqsoft.commonnanning.R
import com.daqsoft.commonnanning.http.RetrofitHelper
import com.daqsoft.commonnanning.ui.entity.StatisticsMapBean
import com.daqsoft.commonnanning.ui.entity.TouristDetailBean
import com.daqsoft.commonnanning.utils.GlideUtils
import com.example.tomasyb.baselib.base.BaseActivity
import com.example.tomasyb.baselib.base.net.common.DefaultObserver
import com.example.tomasyb.baselib.base.net.entity.BaseResponse
import com.example.tomasyb.baselib.base.net.execute
import kotlinx.android.synthetic.main.activity_trace_detail.*

/**
 * 足迹详情
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-9-11
 * @since JDK 1.8.0_191
 */
class TraceDetailActivity : BaseActivity(), AMap.InfoWindowAdapter {
    override fun getInfoContents(marker: Marker?): View? {
        val data = marker?.`object` as TouristDetailBean.Scenery
        val inflate = LayoutInflater.from(this).inflate(R.layout.pop_maker, null, false)
        val textView = inflate.findViewById<TextView>(R.id.mText)
        textView.text = data.resourceName
        return inflate
    }

    override fun getInfoWindow(marker: Marker?): View {
        val data = marker?.`object` as TouristDetailBean.Scenery
        val inflate = LayoutInflater.from(this).inflate(R.layout.pop_maker, null, false)
        val textView = inflate.findViewById<TextView>(R.id.mText)
        textView.text = data.resourceName
        return inflate
    }


    private val id by lazy { intent.getStringExtra("id") }

    override fun getLayoutId(): Int = R.layout.activity_trace_detail

    private val aMap by lazy { mMap.map }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMap.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mMap.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        mMap.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMap.onPause()
    }

    override fun initView() {
        mHeadView.setTitle("足迹详情")
        mCityNameTv.text = ProjectConfig.CITY_NAME
        mScenicTitleTv.text = "我去过的${ProjectConfig.CITY_NAME}景区"
        val settings = aMap.uiSettings
        settings.isZoomControlsEnabled = false
        val latLng = LatLng(ProjectConfig.COMMON_LAT.toDouble(), ProjectConfig.COMMON_LNG.toDouble())
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng))
        aMap.setInfoWindowAdapter(this)
    }

    override fun initData() {
        getStatisticsMap()
        touristDetail()
    }

    /**
     * 足迹统计信息
     */
    private fun getStatisticsMap() {
        RetrofitHelper.getApiService()
                .getStatisticsMap(id.toInt())
                .execute(object : DefaultObserver<StatisticsMapBean>() {
                    override fun onSuccess(response: BaseResponse<StatisticsMapBean>?) {
                        val sb = StringBuilder("一共去过${response?.data?.total}个景区")
                        for (i in 0 until (response?.data?.levelList?.size ?: 0)) {
                            sb.append(" · ")
                            sb.append("${response?.data?.levelList?.get(i)?.num}个" +
                                    "${response?.data?.levelList?.get(i)?.levelName}景区")
                        }
                        mCountTv.text = sb.toString()
                        mTitleTv.text = "${response?.data?.nickName}  的${ProjectConfig.CITY_NAME}旅行地图"
                        GlideUtils.loadCircleImage(
                                this@TraceDetailActivity,
                                mHeadIv,
                                response?.data?.head,
                                R.mipmap.common_img_fail_h158
                        )
                    }
                })
    }

    private fun touristDetail() {
        RetrofitHelper.getApiService()
                .touristDetail(id)
                .execute(object : DefaultObserver<TouristDetailBean>() {
                    override fun onSuccess(response: BaseResponse<TouristDetailBean>?) {

                        response?.data?.sceneryList?.forEach {
                            val markerOptions = MarkerOptions()
                            var latLng: LatLng? = null
                            if (it?.latitude?.isNotEmpty() == true || it?.longitude?.isNotEmpty() == true) {
                                latLng = LatLng(it.latitude?.toDouble() ?: 0.0, it.longitude?.toDouble() ?: 0.0)
                            }
                            markerOptions.position(latLng)
                            markerOptions
                                    .title(it?.resourceName)
                                    .icon(
                                            BitmapDescriptorFactory.fromBitmap(
                                                    BitmapFactory.decodeResource(
                                                            resources,
                                                            R.mipmap.zuji_dizhi_dianwei
                                                    )
                                            )
                                    )
                            val marker = aMap.addMarker(markerOptions)
                            marker.`object` = it
                            marker.isInfoWindowEnable = true
                            if (!marker.isInfoWindowShown) {
                                marker.showInfoWindow()
                            }
                        }

                        val sb = StringBuilder()

                        for (i in 0 until (response?.data?.nameList?.size ?: 0)) {
                            if (i == (response?.data?.nameList?.size ?: 0) - 1) {
                                sb.append(response?.data?.nameList?.get(i))
                            } else {
                                sb.append(response?.data?.nameList?.get(i)).append(" / ")
                            }
                        }

                        mScenicTv.text = sb.toString()
                    }
                })
    }

}