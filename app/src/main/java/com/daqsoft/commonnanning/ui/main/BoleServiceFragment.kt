package com.daqsoft.commonnanning.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.daqsoft.android.ProjectConfig
import com.daqsoft.busquery.BusQueryActivity
import com.daqsoft.commonnanning.R
import com.daqsoft.commonnanning.common.Constant
import com.daqsoft.commonnanning.common.SPCommon
import com.daqsoft.commonnanning.ui.destination.DestinationDetailsActivity
import com.daqsoft.commonnanning.ui.service.ContentWebActivity
import com.daqsoft.commonnanning.ui.service.FoundNearNewActivity
import com.daqsoft.commonnanning.ui.service.ToiletListActivity
import com.daqsoft.commonnanning.ui.service.TravelAgencyActivity
import com.daqsoft.commonnanning.ui.service.complaint.ComplaintListActivity
import com.daqsoft.commonnanning.ui.service.news.NewsListActivity
import com.daqsoft.commonnanning.ui.trace.TraceActivity
import com.daqsoft.guide.MapInformationActivity
import com.example.tomasyb.baselib.util.SPUtils
import kotlinx.android.synthetic.main.fragment_bole_server.*

/**
 * 博乐服务界面
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-9-11
 * @since JDK 1.8.0_191
 */
class BoleServiceFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bole_server, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        mHeadView.setTitle("服务")
        mHeadView.setBackHidden(false)
        mDestinationLl.setOnClickListener(this)
        mTraceLl.setOnClickListener(this)
        mComplaintLl.setOnClickListener(this)
        mNewsLl.setOnClickListener(this)
        mTrafficLl.setOnClickListener(this)
        mTravelAgencyLl.setOnClickListener(this)
        mToiletLl.setOnClickListener(this)
        mGuideLl.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mDestinationLl -> {
                // 目的地
                val intent = Intent(context, DestinationDetailsActivity::class.java)
                intent.putExtra("region", ProjectConfig.REGION)
                intent.putExtra("name", ProjectConfig.CITY_NAME)
                intent.putExtra("lat", SPUtils.getInstance().getString(SPCommon.LATITUDE))
                intent.putExtra("lng", SPUtils.getInstance().getString(SPCommon.LONGITUDE))
                startActivity(intent)
            }
            R.id.mTraceLl -> {
                // 走过的轨迹
                startActivity(Intent(context, TraceActivity::class.java))
            }
            R.id.mComplaintLl -> {
                // 在线投诉
                startActivity(Intent(context, ComplaintListActivity::class.java))
            }
            R.id.mNewsLl -> {
                // 旅游资讯
                startActivity(Intent(context, NewsListActivity::class.java))
            }
            R.id.mTrafficLl -> {
                // 交通指南
                val intent = Intent(context, ContentWebActivity::class.java)
                intent.putExtra("pageType",2)
                startActivity(intent)
            }
            R.id.mTravelAgencyLl -> {
                // 旅行社
                startActivity(Intent(context, TravelAgencyActivity::class.java))
            }
            R.id.mToiletLl -> {
                // 旅游厕所
                startActivity(Intent(context, ToiletListActivity::class.java))
            }
            R.id.mGuideLl -> {
                // 导游导览
                val intent = Intent(context, MapInformationActivity::class.java)
                // 网络请求根地址
                intent.putExtra("url", ProjectConfig.BASE_URL)
                // 语言编码
                intent.putExtra("lang", ProjectConfig.LANG)
                // 站点编码
                intent.putExtra("sitecode", ProjectConfig.SITECODE)
                // 地区编码
                intent.putExtra("region", ProjectConfig.REGION)
                // 微信的账号APPID
                intent.putExtra("appid", ProjectConfig.APPID)
                // 地区名称
                intent.putExtra("city", ProjectConfig.CITY_NAME)
                // 当前地区的经度
                intent.putExtra("lat", ProjectConfig.COMMON_LAT)
                // 当前地区的纬度
                intent.putExtra("lng", ProjectConfig.COMMON_LNG)
                intent.putExtra("ID", 88)
                // 当前地区的相关介绍信息
                intent.putExtra("about", "")
                startActivity(intent)
            }
        }

    }

    private fun initData() {

    }
}