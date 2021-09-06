package com.daqsoft.commonnanning.ui.service

import android.view.View
import com.daqsoft.commonnanning.R
import com.daqsoft.commonnanning.base.IApplication
import com.daqsoft.commonnanning.common.ComUtils
import com.daqsoft.commonnanning.helps_gdmap.HelpMaps
import com.daqsoft.commonnanning.http.RetrofitHelper
import com.daqsoft.commonnanning.ui.entity.TravelDetailsEntity
import com.daqsoft.commonnanning.utils.GlideUtils
import com.daqsoft.utils.MapNaviUtils
import com.example.tomasyb.baselib.base.BaseActivity
import com.example.tomasyb.baselib.base.net.common.DefaultObserver
import com.example.tomasyb.baselib.base.net.entity.BaseResponse
import com.example.tomasyb.baselib.base.net.execute
import com.example.tomasyb.baselib.util.ObjectUtils
import com.example.tomasyb.baselib.util.ToastUtils
import kotlinx.android.synthetic.main.activity_travel_agency_details.*

/**
 * 旅行社详情页面
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018-7-3.14:32
 * @since JDK 1.8
 */
class TravelAgencyDetailsActivity : BaseActivity() {
    /**
     * 网络请求成功
     */
    val SUCCESS_CODE = 0
    /**
     *旅行社ID
     */
    var id = ""
    /**
     * 旅行社详情数据对象
     */
    lateinit var travelDetails: TravelDetailsEntity

    override fun getLayoutId(): Int = R.layout.activity_travel_agency_details

    override fun initView() {
        id = intent.getStringExtra("id")
        headView_details_title.setTitle("旅行社详情")
        refreshLayout.setOnRefreshListener {
            getData()
        }
    }

    override fun initData() {
        getData()

    }

    /**
     * 获取旅行社详情
     */
    fun getData() {
        refreshLayout.autoRefresh()
        RetrofitHelper.getApiService()
                .getTravelDetails(id)
                .execute(object : DefaultObserver<TravelDetailsEntity>() {
                    override fun onSuccess(response: BaseResponse<TravelDetailsEntity>?) {
                        if (response?.code == SUCCESS_CODE) {
                            var travelDetails = response.data
                            if (ObjectUtils.isNotEmpty(travelDetails.cover)) {
                                GlideUtils.loadImage(this@TravelAgencyDetailsActivity,
                                        iv_details_img, travelDetails.cover,
                                        R.mipmap.common_ba_banner)
                                iv_details_img.visibility = View.VISIBLE
                            } else {
                                iv_details_img.visibility = View.GONE
                            }
                            if (ObjectUtils.isNotEmpty(travelDetails.phone)) {
                                ll_details_phone.visibility = View.VISIBLE
                                tv_details_phone.setText(travelDetails.phone)
                            } else {
                                ll_details_phone.visibility = View.GONE
                            }
                            if (ObjectUtils.isNotEmpty(travelDetails.address)) {
                                ll_details_address.visibility = View.VISIBLE
                                tv_details_address.setText(travelDetails.address)
                            } else {
                                ll_details_address.visibility = View.GONE
                            }
                            tv_details_name.setText(travelDetails.name)
                            if(ObjectUtils.isNotEmpty(travelDetails.biztype)){
                                tv_details_content.loadData(ComUtils.getNewContent(travelDetails.biztype),"text/html; charset=UTF-8", null)
                            }else{
                                tv_details_content.loadData("暂无数据","text/html; charset=UTF-8", null)
                            }

                            iv_details_phone.setOnClickListener {
                                ComUtils.showQueryCancleDialogPhone(this@TravelAgencyDetailsActivity, travelDetails.phone)
                            }
                            if(!IApplication.getInstance().isAgreePrivate){
                                return
                            }
                            /**
                             * 获取用户当前位置
                             */
                            HelpMaps.startLocation { location ->
                                if (ObjectUtils.isNotEmpty(location)) {
                                    try {
                                        iv_details_address.setOnClickListener {
                                            if (ObjectUtils.isNotEmpty(travelDetails.latitude)
                                                    && ObjectUtils.isNotEmpty(travelDetails.longitude)
                                                    && ObjectUtils.isNotEmpty(travelDetails.address)) {
                                                MapNaviUtils.isMapNaviUtils(this@TravelAgencyDetailsActivity, location.latitude,
                                                        location.longitude, "我的位置", travelDetails.latitude, travelDetails.longitude,
                                                        travelDetails.address)
                                            } else {
                                                ToastUtils.showShortCenter(resources.getString(R.string.no_map_navi))
                                            }
                                        }
                                        HelpMaps.stopLocation()
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                } else {
                                }
                            }
                        }
                    }

                    override fun onFinish() {
                        super.onFinish()
                        refreshLayout.finishRefresh()
                    }
                })
    }

}
