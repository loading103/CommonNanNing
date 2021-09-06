package com.daqsoft.commonnanning.ui.country

import android.os.Build
import android.view.View
import com.daqsoft.commonnanning.R
import com.daqsoft.commonnanning.R.id.*
import com.daqsoft.commonnanning.common.ComUtils
import com.daqsoft.commonnanning.helps_gdmap.HelpMaps
import com.daqsoft.commonnanning.helps_gdmap.MapUtils
import com.daqsoft.commonnanning.http.RetrofitHelper
import com.daqsoft.commonnanning.ui.country.entity.CountryBean
import com.daqsoft.commonnanning.utils.GlideUtils
import com.daqsoft.utils.MapNaviUtils
import com.example.tomasyb.baselib.base.BaseActivity
import com.example.tomasyb.baselib.base.net.common.DefaultObserver
import com.example.tomasyb.baselib.base.net.entity.BaseResponse
import com.example.tomasyb.baselib.base.net.execute
import com.example.tomasyb.baselib.util.ObjectUtils
import com.example.tomasyb.baselib.util.ToastUtils
import kotlinx.android.synthetic.main.activity_country_details.*
import kotlinx.android.synthetic.main.activity_specialty_detail.*

/**
 * 乡村旅游详情Activity
 *
 * @author huangx
 * @version 1.0.0
 * @date 2019-5-18.15:50
 * @since JDK 1.8
 */
class CountryDetailsActivity : BaseActivity() {
    /**
     * 乡村旅游ID
     */
    var id: String = "";

    override fun initView() {
        head.setTitle(intent.getStringExtra("title") + "详情")
        id = intent.getStringExtra("id");
        webViews.settings.javaScriptEnabled = true
        webViews.settings.builtInZoomControls = true
        webViews.settings.displayZoomControls = false
        webViews.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webViews.settings.defaultTextEncodingName = "UTF-8"
        webViews.settings.blockNetworkImage = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //webView.settings.mixedContentMode = webView.settings.MIXED_CONTENT_ALWAYS_ALLOW  //注意安卓5.0以上的权限
        }
        refreshLayout.setOnRefreshListener {
            getDetailsData()
        }
    }

    override fun initData() {
        getDetailsData()
    }

    override fun getLayoutId(): Int = R.layout.activity_country_details

    /**
     * 获取相应的详情数据
     */
    fun getDetailsData() {
        refreshLayout.autoRefresh()
        RetrofitHelper.getApiService()
                .getCountryDetails(id)
                .execute(object : DefaultObserver<CountryBean>() {
                    override fun onSuccess(response: BaseResponse<CountryBean>?) {
                        if (ObjectUtils.isNotEmpty(response)) {
                            if (response?.code == 0
                                    && ObjectUtils.isNotEmpty(response.data)) {
                                val country = response.data;
                                GlideUtils.loadImage(this@CountryDetailsActivity,
                                        img_top, country.cover, R.mipmap.common_ba_banner)
                                tv_name.setText(country.name)
                                tv_address.setText(country.address)
                                if (ObjectUtils.isNotEmpty(country.phone)){
                                    ll_phone_tag.visibility = View.VISIBLE
                                    tv_phone.setText(country.phone)
                                }else{
                                    ll_phone_tag.visibility = View.GONE
                                }
                                webViews.loadDataWithBaseURL(null, ComUtils.getNewContent(country?.introduce),
                                        "text/html", "UTF-8", null)
                                /**
                                 * 获取用户当前位置
                                 */
                                HelpMaps.startLocation { location ->
                                    if (ObjectUtils.isNotEmpty(location)) {
                                        try {
                                            tv_distance.setText("距您直线" + MapUtils.calculateLineDistance(
                                                    location?.latitude.toString(), location?.longitude.toString(),
                                                    country?.latitude, country?.longitude))
                                            ll_address.setOnClickListener {
                                                if (ObjectUtils.isNotEmpty(country.latitude)
                                                        && ObjectUtils.isNotEmpty(country.longitude)
                                                        && ObjectUtils.isNotEmpty(country.address)) {
                                                    MapNaviUtils.isMapNaviUtils(this@CountryDetailsActivity, location.latitude,
                                                            location.longitude, "我的位置", country.latitude, country.longitude,
                                                            country.address)
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
                                ll_phone_tag.setOnClickListener {
                                    ComUtils.showQueryCancleDialogPhone(mContext, country.phone)
                                }

                            } else {
                                ToastUtils.showLongCenter(response?.message)
                                finish()
                            }
                        } else {
                            ToastUtils.showLongCenter("数据异常，请稍后再试")
                            finish()
                        }
                    }

                    override fun onFinish() {
                        super.onFinish()
                        refreshLayout.finishRefresh()
                    }

                })
    }
}
