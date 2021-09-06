package com.daqsoft.commonnanning.ui.country

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import com.alibaba.android.arouter.facade.annotation.Route
import com.amap.api.location.AMapLocation
import com.daqsoft.commonnanning.R
import com.daqsoft.commonnanning.common.Constant
import com.daqsoft.commonnanning.common.ParamsCommon
import com.daqsoft.commonnanning.common.URLConstant
import com.daqsoft.commonnanning.helps_gdmap.HelpMaps
import com.daqsoft.commonnanning.helps_gdmap.MapUtils
import com.daqsoft.commonnanning.http.RetrofitHelper
import com.daqsoft.commonnanning.ui.country.entity.CountryBean
import com.daqsoft.commonnanning.ui.entity.IndexBanner
import com.daqsoft.commonnanning.utils.GlideUtils
import com.daqsoft.commonnanning.utils.Utils
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter
import com.example.tomasyb.baselib.adapter.BaseViewHolder
import com.example.tomasyb.baselib.base.BaseActivity
import com.example.tomasyb.baselib.base.net.common.DefaultObserver
import com.example.tomasyb.baselib.base.net.entity.BaseResponse
import com.example.tomasyb.baselib.base.net.execute
import com.example.tomasyb.baselib.util.KeyboardUtils
import com.example.tomasyb.baselib.util.ObjectUtils
import com.example.tomasyb.baselib.widget.img.RoundImageView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_country_list.*
import java.util.*

/**
 * 乡村旅游列表Activity
 *
 * @author huangx
 * @version 1.0.0
 * @date 2019-5-18.15:50
 * @since JDK 1.8
 */
@Route(path = Constant.COUNTRY_LIST_ACTIVITY)
class CountryListActivity : BaseActivity() {
    /**
     * 乡村旅游列表数据
     */
    private var countryList = mutableListOf<CountryBean>()
    /**
     * 当前页码
     */
    private var page = 1
    /**
     * 每页条数
     */
    private var pageSize = URLConstant.LIMITPAGE

    /**
     * 当前位置的location
     */
    private var mapLocation: AMapLocation? = null
    /**
     * 类型code
     */
    private var code: String? = null
    /**
     * tag 标签id 971 农家乐 ，972乡村旅游
     */
    private var tag: Int? = 971
    /**
     * 关键字搜索
     */
    private var name: String? = null
    /**
     * 是否需要轮播
     */
    private var canScroll = false
    /**
     * 标题
     */
    private var title: String? = null
    /**
     * banner图Code
     */
    private var bannerCode: String? = null

    /**
     * 布局回调
     */
    override fun getLayoutId(): Int = R.layout.activity_country_list

    /**
     * 布局初始化
     */
    override fun initView() {
        title = intent.getStringExtra("title")
        country_list_title.setTitle(title)
        tag = intent.getIntExtra("tag", 971)
        val tagNeed = intent.getBooleanExtra("tagNeed", true)

        if (!tagNeed) {
            tag = null
        }

        if (tag == 971) {
            bannerCode = ParamsCommon.FAMILY_BANNER
        } else {
            bannerCode = ParamsCommon.COUNTRY_BANNER
        }
        getLocation()
        setCountryAdapter()
        adapter.setEnableLoadMore(false)
        swipe_refresh.setOnRefreshListener {
            page = 1
            getCountryData()
        }
        country_location.setOnClickListener {
            getLocation()
        }
    }

    /**
     * 数据初始化
     */
    override fun initData() {
        swipe_refresh.autoRefresh()
        getBanner()
    }

    /**
     * 乡村旅游列表适配器
     */
    val adapter = object : BaseQuickAdapter<CountryBean, BaseViewHolder>(R.layout.item_country_list, countryList) {
        override fun convert(helper: BaseViewHolder?, item: CountryBean?) {
            helper?.setText(R.id.item_country_name, item?.name)
            helper?.setText(R.id.item_country_address, item?.address)
            if (ObjectUtils.isNotEmpty(mapLocation)
                    && ObjectUtils.isNotEmpty(item?.latitude)
                    && ObjectUtils.isNotEmpty(item?.longitude)) {
                helper?.setText(R.id.item_country_distance, "距您直线" + MapUtils.calculateLineDistance(
                        mapLocation?.latitude.toString(), mapLocation?.longitude.toString(),
                        item?.latitude, item?.longitude))
            }
            helper?.itemView?.setOnClickListener {
                var intent = Intent()
                intent.setClass(this@CountryListActivity, CountryDetailsActivity::class.java)
                intent.putExtra("id", item?.id)
                intent.putExtra("title", title)
                startActivity(intent)
            }
            GlideUtils.loadImage(this@CountryListActivity,
                    helper?.getView<RoundImageView>(R.id.item_country_img),
                    item?.coverFourToThree,
                    R.mipmap.common_img_fail_h158)
        }
    }

    /**
     * 初始化适配器
     */
    private fun setCountryAdapter() {
        recyclerView.layoutManager = LinearLayoutManager(this);
        adapter.setOnLoadMoreListener({
            page++;
            getCountryData()
        }, recyclerView)
        recyclerView.adapter = adapter
    }

    /**
     *获取乡村旅游的数据
     */
    private fun getCountryData() {
        if (page == 1) {
            //swipe_refresh.autoRefresh()
        }
        RetrofitHelper.getApiService()
                .getCountryList(pageSize, code, page, name, tag, null)
                .execute(object : DefaultObserver<CountryBean>() {
                    override fun onSuccess(response: BaseResponse<CountryBean>?) {
                        Utils.pageDeal(page, countryList as ArrayList<*>, response, adapter, view_animator)
                        countryList.addAll(response?.datas!!)
                        adapter.notifyDataSetChanged()
                    }

                    override fun onFinish() {
                        super.onFinish()
                        swipe_refresh.finishRefresh()
                    }
                })
    }

    /**
     * 获取用户当前位置
     */
    private fun getLocation() {
        HelpMaps.startLocation { location ->
            if (ObjectUtils.isNotEmpty(location)) {
                mapLocation = location
                try {
                    country_location.setText(location.address)
                    HelpMaps.stopLocation()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                country_location.setText(resources.getString(R.string.no_address))
            }
        }
    }


    /**
     * 获取banner的数据
     */
    private fun getBanner() {
        RetrofitHelper.getApiService().getIndexBannar(bannerCode).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe { disposable -> addDisposable(disposable) }.subscribe({ bean ->
            val mlist = ArrayList<IndexBanner>()
            mlist.clear()
            if (bean.code == 0 && bean.datas.size > 0) {
                for (i in 0 until bean.datas.size) {
                    val beans = IndexBanner()
                    beans.id = bean.datas[i].id
                    beans.img = bean.datas[i].pics[0]
                    mlist.add(beans)
                }
                KotlinUtils.initBanner(mlist, banner_view, this, canScroll)
            } else {
                val beans = IndexBanner()
                beans.id = ""
                beans.img = ""
                mlist.add(beans)
                KotlinUtils.initBanner(mlist, banner_view, this, canScroll)
            }
        }, {
            val mlist = ArrayList<IndexBanner>()
            val beans = IndexBanner()
            beans.id = ""
            beans.img = ""
            mlist.add(beans)
            KotlinUtils.initBanner(mlist, banner_view, this, canScroll)
        })
    }


    override fun onResume() {
        super.onResume()
        // 开始自动翻页
        if (canScroll) {
            banner_view.startTurning()
        }
    }

    public override fun onPause() {
        super.onPause()
        if (canScroll) {
            // 停止翻页
            banner_view.stopTurning()
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if ((event?.keyCode == KeyEvent.KEYCODE_SEARCH || event?.keyCode == KeyEvent.KEYCODE_ENTER)
                && event?.action == KeyEvent.ACTION_UP) {
            name = country_search.text.toString().trim();
            page = 1
            KeyboardUtils.hideSoftInput(this)
            getCountryData()
            return true
        }
        return super.dispatchKeyEvent(event)
    }
}