package com.daqsoft.commonnanning.ui.main

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.daqsoft.busquery.BusQueryActivity
import com.daqsoft.commonnanning.R
import com.daqsoft.commonnanning.base.BaseWebActivity
import com.daqsoft.commonnanning.common.ParamsCommon
import com.daqsoft.commonnanning.common.SPCommon
import com.daqsoft.commonnanning.hotel.HotelActivity
import com.daqsoft.commonnanning.http.RetrofitHelper
import com.daqsoft.commonnanning.scenic.ScenicActivity
import com.daqsoft.commonnanning.ui.adapter.ViewpageAdapter
import com.daqsoft.commonnanning.ui.country.CountryDetailsActivity
import com.daqsoft.commonnanning.ui.country.CountryListActivity
import com.daqsoft.commonnanning.ui.country.entity.CountryBean
import com.daqsoft.commonnanning.ui.entity.*
import com.daqsoft.commonnanning.ui.holder.NetWorkIndexBannerRoundImageHolderView
import com.daqsoft.commonnanning.ui.holder.ScenicHolder
import com.daqsoft.commonnanning.ui.service.FoundNearNewActivity
import com.daqsoft.commonnanning.ui.service.ToiletListActivity
import com.daqsoft.commonnanning.ui.service.TravelAgencyActivity
import com.daqsoft.commonnanning.ui.service.complaint.ComplaintListActivity
import com.daqsoft.commonnanning.ui.service.news.NewsListActivity
import com.daqsoft.commonnanning.ui.service.news.NewsWebActivity
import com.daqsoft.commonnanning.utils.GlideUtils
import com.daqsoft.commonnanning.utils.Utils
import com.daqsoft.commonnanning.version.VersionCheck
import com.daqsoft.commonnanning.view.BottomRoundImageView
import com.daqsoft.http.LoadingDialog
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter
import com.example.tomasyb.baselib.adapter.BaseViewHolder
import com.example.tomasyb.baselib.base.net.common.DefaultObserver
import com.example.tomasyb.baselib.base.net.entity.BaseResponse
import com.example.tomasyb.baselib.base.net.execute
import com.example.tomasyb.baselib.util.SPUtils
import io.agora.yview.banner.ConvenientBanner
import io.agora.yview.banner.holder.CBViewHolderCreator
import io.agora.yview.banner.holder.Holder
import io.agora.yview.banner.listener.OnItemClickListener
import io.agora.yview.banner.listener.OnPageChangeListener
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.fragment_bole_main.*

/**
 * 博乐主页
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-9-9
 * @since JDK 1.8.0_191
 */
class MainBoleFragment : Fragment(), View.OnClickListener, OnItemClickListener {
    override fun onItemClick(position: Int) {
        if (mTopBgDataList.get(position).url == null || mTopBgDataList.get(position).url.equals("")) {
            return
        }
        val intent = Intent(context, BaseWebActivity::class.java)
        intent.putExtra("HTMLURL", mTopBgDataList.get(position).url)
        intent.putExtra("HTMLTITLE", mTopBgDataList.get(position).name)
        startActivity(intent)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bole_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {

        setOnClickListener()
        initTopBannerBg()
        initTopBanner()
        setScenicBanner()
        setAdapter()
        mSwipeRefreshLayout.setOnRefreshListener {
            page = 1
            mTopBgDataList.clear()
            mTopBgImgList.clear()
            scenicData.clear()
            rangeScenicData.clear()
            strategyData.clear()
            newsData.clear()
            mTopBgVp.adapter?.notifyDataSetChanged()
            initData()
            mSwipeRefreshLayout.isRefreshing = false
        }
        mAddressTv.text = SPUtils.getInstance().getString(SPCommon.CITY_NAME)
    }

    private fun setScenicBanner() {
        mRecommendCB.setPages(object : CBViewHolderCreator {
            override fun createHolder(itemView: View?): Holder<*> = ScenicHolder(itemView)
            override fun getLayoutId(): Int = R.layout.bole_home_recommend_scenic
        }, rangeScenicData as List<Nothing>)
                // 设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(intArrayOf(R.drawable.bga_banner_point_disabled, R.drawable.bga_banner_point_enabled))
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                .setOnItemClickListener(this)
    }

    fun setOnClickListener() {
        mSearchTv.setOnClickListener(this)
        mScenicTv.setOnClickListener(this)
        mHotelTv.setOnClickListener(this)
        mDestinationTv.setOnClickListener(this)
        mVideoTv.setOnClickListener(this)
        mStrategyTv.setOnClickListener(this)
        mParkTv.setOnClickListener(this)
        mToiletTv.setOnClickListener(this)
        mTravelAgencyTv.setOnClickListener(this)
        mWeatherTv.setOnClickListener(this)
        mTrafficTv.setOnClickListener(this)
        mAroundCl.setOnClickListener(this)
        mComplaintCl.setOnClickListener(this)
        mMapCl.setOnClickListener(this)
        mSeasonMoreIv.setOnClickListener(this)
        mHotRecommendMoreIv.setOnClickListener(this)
        mScenicTagLl.setOnClickListener(this)
        mHotelTagLl.setOnClickListener(this)
        mNewsMoreIv.setOnClickListener(this)
        mStrategyMoreIv.setOnClickListener(this)
        mSearchTv.setOnClickListener(this)
        mFarmCl.setOnClickListener(this)
    }


    /**
     * 初始化顶部Banner图
     */
    private fun initTopBanner() {
        mTopBanner.setPages(
                object : CBViewHolderCreator {
                    override fun getLayoutId(): Int = R.layout.item_banner_round_img

                    override fun createHolder(itemView: View?): NetWorkIndexBannerRoundImageHolderView =
                            NetWorkIndexBannerRoundImageHolderView(itemView, activity);
                }, mTopBgDataList as List<Nothing>
        ).setPageIndicator(
                intArrayOf(
                        R.drawable.bga_banner_point_disabled,
                        R.drawable.bga_banner_point_enabled
                )
        )
                .setOnItemClickListener(this)
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnPageChangeListener(object : OnPageChangeListener {
                    override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                    }

                    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    }

                    override fun onPageSelected(index: Int) {
                        if (mTopBgDataList.isNullOrEmpty()) {
                            return
                        }
                        mTopBgVp.setCurrentItem(index, false)
                    }

                })
                .startTurning()
    }

    private val mTopBgImgList = mutableListOf<View>()
    private val mTopBgDataList = mutableListOf<IndexBanner>()
    /**
     * 初始化顶部Banner图背景
     */
    private fun initTopBannerBg() {
        if (mTopBgDataList.size > 0) {
            for (i in 0 until mTopBgDataList.size) {
                val view = LayoutInflater.from(activity).inflate(R.layout.item_index_bg_imgview, null)
                val imgBg = view.findViewById<BottomRoundImageView>(R.id.img_index_bg)
                Glide.with(this)
                        .load(mTopBgDataList[i].img)
                        .apply(
                                RequestOptions
                                        .bitmapTransform(
                                                BlurTransformation(25)
                                        )
                        )
                        .into(imgBg)
                mTopBgImgList.add(view)
            }
        }
        mTopBgVp.adapter = ViewpageAdapter(mTopBgImgList)
    }

    private fun initData() {
        getTopBanner()
        getScenicRecommend()
        getRecommend()
        getStrategy()
        getNews()
        getCountryList()
    }


    private var pageSize = 10
    /**
     * 农家乐信息
     */
    private fun getCountryList() {
        RetrofitHelper.getApiService()
                .getCountryList(
                        pageSize, "", page, "", null, ""
                )
                .execute(object : DefaultObserver<CountryBean>() {
                    override fun onSuccess(response: BaseResponse<CountryBean>?) {
                        if (response?.datas != null && response.datas.size > 0) {
                            mFarmCl.visibility = View.VISIBLE
                            mDivideLine6.visibility = View.VISIBLE
                            farmData.addAll(response.datas!!)
                            farmAdapter.notifyDataSetChanged()
                        } else {
                            mFarmCl.visibility = View.GONE
                            mDivideLine6.visibility = View.GONE
                        }
                    }
                })
    }

    private fun getTopBanner() {
        RetrofitHelper.getApiService()
                .getIndexBannar(ParamsCommon.INDEXTOP_BANNER)
                .execute(object : DefaultObserver<AdvertEntity>() {
                    override fun onSuccess(response: BaseResponse<AdvertEntity>?) {
                        for (i in 0 until (response?.datas?.size ?: 0)) {
                            val indexBanner = IndexBanner()
                            indexBanner.id = response?.datas?.get(i)?.id
                            indexBanner.img = response?.datas?.get(i)?.pics?.get(0)
                            indexBanner.url = response?.datas?.get(i)?.url
                            indexBanner.name = response?.datas?.get(i)?.title
                            mTopBgDataList.add(indexBanner)
                        }
                        initTopBannerBg()
//                        mTopBgVp.adapter?.notifyDataSetChanged()
                        mTopBanner.notifyDataSetChanged()
                    }
                })
    }

    private val scenicData = mutableListOf<IndexScenic>()

    /**
     * 附近推荐景点数据
     */
    private val rangeScenicData = mutableListOf<IndexScenic>()

    /**
     * 获取推荐
     */
    private fun getRecommend() {
        if (SPUtils.getInstance().getString(SPCommon.LATITUDE).isNullOrEmpty() ||
                SPUtils.getInstance().getString(SPCommon.LONGITUDE).isNullOrEmpty()) {
            return
        }
        RetrofitHelper.getApiService()
                .getIndexScenic(
                        "1",
                        "10",
                        "1",
                        SPUtils.getInstance().getString(SPCommon.LATITUDE),
                        SPUtils.getInstance().getString(SPCommon.LONGITUDE),
                        "2",
                        "recommendedPriority"
                )
                .execute(object : DefaultObserver<IndexScenic>() {
                    override fun onSuccess(response: BaseResponse<IndexScenic>?) {
                        if (response?.datas != null && response.datas.size > 0) {
                            rangeScenicData.clear()
                            rangeScenicData.addAll(response.datas!!)
                            mRecommendCB.visibility = View.VISIBLE
                            mDivideLine3.visibility = View.VISIBLE
                            mRecommendCB.notifyDataSetChanged()
                        } else {
                            mRecommendCB.visibility = View.GONE
                            mDivideLine3.visibility = View.GONE
                        }
                    }
                })
    }


    /**
     * 每页条数
     */
    val PAGE_SIZE = 10
    /**
     * 页码
     */
    var page = 1
    /**
     * 按照发布时间排序
     */
    val PUBLISH_TIME = "PUBLISH_TIME"

    /**
     * 降序
     */
    val DESC = "DESC"

    /**
     * 获取旅游资讯
     */
    private fun getNews() {
        RetrofitHelper.getApiService()
                .getNewsList(
                        ParamsCommon.SERVICE_LYZX,
                        PUBLISH_TIME,
                        DESC,
                        "$PAGE_SIZE",
                        "$page"
                )
                .execute(object : DefaultObserver<NewsListEntity>() {
                    override fun onSuccess(response: BaseResponse<NewsListEntity>?) {
                        if (response?.datas != null && response.datas.size > 0) {
                            mNewsCl.visibility = View.VISIBLE
                            newsData.addAll(response.datas!!)
                            newsAdapter.notifyDataSetChanged()
                        } else {
                            mNewsCl.visibility = View.GONE
                        }
                    }
                })
    }

    /**
     * 获取推荐景区
     */
    private fun getScenicRecommend() {

        RetrofitHelper.getApiService()
                .getIndexScenic(
                        "1",
                        "10",
                        "1",
                        "recommendedPriority"
                )
                .execute(object : DefaultObserver<IndexScenic>() {
                    override fun onSuccess(response: BaseResponse<IndexScenic>?) {
                        if (response?.datas != null) {
                            scenicData.clear()
                            scenicData.addAll(response.datas!!)
                            scenicAdapter.notifyDataSetChanged()
                        }
                        LoadingDialog.cancelDialogForLoading()
                    }
                })
    }

    /**
     * 城市攻略
     */
    private fun getStrategy() {
        RetrofitHelper.getApiService()
                .getLineList(
                        "1",
                        "15",
                        "1",
                        ""
                )
                .execute(object : DefaultObserver<MyStrategyListBean>() {
                    override fun onSuccess(response: BaseResponse<MyStrategyListBean>?) {
                        if (response?.datas != null && response.datas.size > 0) {
                            mStrategyCl.visibility = View.VISIBLE
                            mDivideLine5.visibility = View.VISIBLE
                            strategyData.addAll(response.datas)
                            strategyAdapter.notifyDataSetChanged()
                        } else {
                            mStrategyCl.visibility = View.GONE
                            mDivideLine5.visibility = View.GONE
                        }
                    }
                })
    }

    /**
     * 推荐酒店
     */
    private fun getIndexHotel() {

        RetrofitHelper.getApiService()
                .getHotel(
                        "1",
                        "10",
                        ""
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HotelEntity> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: HotelEntity) {
                        if (t.code == 0) {
                            scenicData.clear()
                            val datas = t.datas
                            val list = mutableListOf<IndexScenic>()
                            for (i in 0 until datas.size) {
                                val beans = IndexScenic()
                                beans.coverOneToOne = datas[i].picture
                                beans.name = datas.get(i).name
                                beans.id = datas.get(i).id
                                list.add(beans)
                            }
                            scenicData.addAll(list)
                            hotelAdapter.notifyDataSetChanged()
                        }
                        LoadingDialog.cancelDialogForLoading()
                    }

                    override fun onError(e: Throwable) {
                    }

                })
    }

    /**
     * 景区适配器
     */
    private val scenicAdapter by lazy {
        object : BaseQuickAdapter<IndexScenic, BaseViewHolder>(R.layout.item_index_scenic_new, scenicData) {
            override fun convert(helper: BaseViewHolder?, item: IndexScenic?) {
                GlideUtils.loadImage(
                        activity,
                        helper?.getView(R.id.img_index_scenic),
                        item?.pictureOneToOne,
                        R.mipmap.common_img_fail_h158
                )
                helper?.setText(R.id.tv_index_scenic, item?.name)
                helper?.itemView?.setOnClickListener {

                    val intent = Intent(context, ScenicDetailActivity::class.java)
                    intent.putExtra("mId", item?.id)
                    startActivity(intent)

                }

            }

        }
    }

    /**
     * 酒店适配器
     */
    private val hotelAdapter by lazy {
        object : BaseQuickAdapter<IndexScenic, BaseViewHolder>(R.layout.item_index_scenic_new, scenicData) {
            override fun convert(helper: BaseViewHolder?, item: IndexScenic?) {
                GlideUtils.loadImage(
                        activity,
                        helper?.getView(R.id.img_index_scenic),
                        item?.coverOneToOne,
                        R.mipmap.common_img_fail_h158
                )
                helper?.setText(R.id.tv_index_scenic, item?.name)
                helper?.itemView?.setOnClickListener {
                    val intent = Intent(context, HotelDetailActivity::class.java)
                    intent.putExtra("mId", item?.id)
                    startActivity(intent)
                }

            }
        }
    }


    private val strategyData = mutableListOf<MyStrategyListBean>()
    /**
     * 城市攻略适配器
     */
    private val strategyAdapter by lazy {
        object : BaseQuickAdapter<MyStrategyListBean, BaseViewHolder>(R.layout.item_index_line, strategyData) {
            override fun convert(helper: BaseViewHolder?, item: MyStrategyListBean?) {
                GlideUtils.loadImage(
                        context,
                        helper?.getView(R.id.img_index_scenic),
                        item?.coverTwoToOne,
                        R.mipmap.common_ba_banner
                )
                helper?.setText(R.id.tv_travel_name, item?.title)
                helper?.setOnClickListener(R.id.img_index_scenic) {
                    val id = "${item?.id}"
                    val intent = Intent(context, LineDetailActivity::class.java)
                    intent.putExtra("mId", id)
                    startActivity(intent)
                }
            }
        }
    }
    /**
     * 景区动态数据
     */
    private val newsData = mutableListOf<NewsListEntity>()
    /**
     * 旅游资讯
     */
    private val newsAdapter by lazy {
        object : BaseQuickAdapter<NewsListEntity, BaseViewHolder>(R.layout.item_bole_home_news, newsData) {
            override fun convert(helper: BaseViewHolder?, item: NewsListEntity?) {
                GlideUtils.loadImage(
                        context,
                        helper?.getView(R.id.mCoverIv),
                        item?.cover,
                        R.mipmap.common_img_fail_h158
                )
                helper?.setText(R.id.mTitleTv, item?.title)
                helper?.setText(R.id.mIntroTv, item?.summary)
                helper?.setText(R.id.mTimeTv, item?.publishtime)
                helper?.setText(R.id.mViewTv, "${item?.viewCount}")
                helper?.itemView?.setOnClickListener {
                    val intent = Intent(context, NewsWebActivity::class.java)
                    intent.putExtra("title", item?.title)
                    intent.putExtra("id", item?.id.toString())
                    intent.putExtra("code", ParamsCommon.SERVICE_LYZX)
                    startActivity(intent)
                }
            }
        }
    }

    private val farmData = mutableListOf<CountryBean>()
    private val farmAdapter by lazy {
        object : BaseQuickAdapter<CountryBean, BaseViewHolder>(R.layout.item_index_rural, farmData) {
            override fun convert(helper: BaseViewHolder?, item: CountryBean?) {
                helper?.setText(R.id.tv_travel_name, item?.name)
                GlideUtils.loadImage(
                        context,
                        helper?.getView(R.id.img_index_scenic),
                        item?.cover,
                        R.mipmap.comimg_fail_240_180
                )
                helper?.itemView?.setOnClickListener {
                    val id = item?.id + ""
                    val intent = Intent()
                    intent.setClass(activity!!, CountryDetailsActivity::class.java)
                    intent.putExtra("id", id)
                    intent.putExtra("title", "农家乐")
                    startActivity(intent)
                }
            }
        }
    }

    private fun setAdapter() {
        mHotRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = scenicAdapter
        }

        mStrategyRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = strategyAdapter
        }

        mNewsRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }

        ingdex_famer_rv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = farmAdapter
        }

    }

    private var hotSelected = 0
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mScenicTv -> {
                // 景区
                startActivity(Intent(context, ScenicActivity::class.java))
            }
            R.id.mHotelTv -> {
                // 酒店
                val intent = Intent(context, HotelActivity::class.java)
                intent.putExtra("grade", "")
                startActivity(intent)
            }
            R.id.mDestinationTv -> {
                // 娱乐场所
//                val intent = Intent(context, DestinationDetailsActivity::class.java)
//                intent.putExtra("region", ProjectConfig.REGION)
//                intent.putExtra("name", ProjectConfig.CITY_NAME)
//                intent.putExtra("lat", SPUtils.getInstance().getString(SPCommon.LATITUDE))
//                intent.putExtra("lng", SPUtils.getInstance().getString(SPCommon.LONGITUDE))

                val intent = Intent(context, EntertainmentActivity::class.java)
                intent.putExtra("type", 0)
                startActivity(intent)

            }
            R.id.mVideoTv -> {
                // 宣传视频
                val intent = Intent(context, PromotionalVideoActivity::class.java)
                startActivity(intent)
            }
            R.id.mStrategyTv -> {
                // 城市攻略
                startActivity(Intent(context, LineActivity::class.java))
            }
            R.id.mScenicTagLl -> {
                // 景区
                hotSelected = 0
                mScenicIndictor.visibility = View.VISIBLE
                mHotelIndictor.visibility = View.INVISIBLE
                LoadingDialog.showDialogForLoading(context)
                getScenicRecommend()
                mHotRv.adapter = scenicAdapter
            }
            R.id.mHotelTagLl -> {
                // 城市攻略
                hotSelected = 1
                mScenicIndictor.visibility = View.INVISIBLE
                mHotelIndictor.visibility = View.VISIBLE
                LoadingDialog.showDialogForLoading(context)
                getIndexHotel()
                mHotRv.adapter = hotelAdapter
            }
            R.id.mParkTv -> {
                // 停车场
                val intent = Intent(context, FoundNearNewActivity::class.java)
                intent.putExtra("type", 0)
                intent.putExtra("curentType", 10)
                startActivity(intent)
            }
            R.id.mToiletTv -> {
                // 厕所
                startActivity(Intent(context, ToiletListActivity::class.java))
            }
            R.id.mTravelAgencyTv -> {
                // 旅行社
                startActivity(Intent(context, TravelAgencyActivity::class.java))
            }
            //启动商城
            R.id.mWeatherTv -> {
                if(Utils.isInstallPackage("com.yzl.xjshop")){
                    var intent: Intent? = getAppOpenIntentByPackageName(context!!, "com.yzl.xjshop")
                    startActivity(intent)
                } else {
                    val intent = Intent(context, BaseWebActivity::class.java)
                    intent.putExtra("HTMLURL", "http://xinjiangwap.ypxlbz.com/download.html")
                    startActivity(intent)
                }
            }
            R.id.mTrafficTv -> {
                // 交通信息
                val intent = Intent(context, BusQueryActivity::class.java)
                intent.putExtra("latitude", SPUtils.getInstance().getString(SPCommon.LATITUDE))
                intent.putExtra("longitude", SPUtils.getInstance().getString(SPCommon.LONGITUDE))
                intent.putExtra("cityname", SPUtils.getInstance().getString("cityname"))
                intent.putExtra("cityAddress", SPUtils.getInstance().getString("cityAddress"))
                startActivity(intent)
            }
            R.id.mAroundCl -> {
                // 周边信息
                val intent = Intent(context, FoundNearNewActivity::class.java)
                intent.putExtra("latitude", SPUtils.getInstance().getString(SPCommon.LATITUDE))
                intent.putExtra("longitude", SPUtils.getInstance().getString(SPCommon.LONGITUDE))
                intent.putExtra("curentType", 1)
                startActivity(intent)
            }
            R.id.mComplaintCl -> {
                // 一键投诉
                startActivity(Intent(context, ComplaintListActivity::class.java))
            }
            R.id.mMapCl -> {
                // 景区地图
                startActivity(Intent(context, GuideListActivity::class.java))
            }
            R.id.mHotRecommendMoreIv -> {
                // 热门推荐更多
                if (hotSelected == 0) {
                    startActivity(Intent(context, ScenicActivity::class.java))
                } else if (hotSelected == 1) {
                    val intent = Intent(context, HotelActivity::class.java)
                    intent.putExtra("grade", "")
                    startActivity(intent)
                }
            }
            R.id.mStrategyMoreIv -> {
                // 攻略推荐更多
                startActivity(Intent(context, LineActivity::class.java))
            }
            R.id.mNewsMoreIv -> {
                // 旅游资讯更多
                startActivity(Intent(context, NewsListActivity::class.java))
            }
            R.id.mSearchTv -> {
                // 全局搜索
                startActivity(Intent(context, GlobalSearchActivity::class.java))
            }
            R.id.mFarmCl -> {
                // 农家乐
                val intent = Intent(activity, CountryListActivity::class.java)
                intent.putExtra("title", "农家乐")
                intent.putExtra("tagNeed", false)

//                intent.putExtra("tag", 971)
                startActivity(intent)
            }
        }
    }

    private fun getAppOpenIntentByPackageName(context: Context, packageName: String?): Intent? {
        var mainAct: String? = null
        val pkgMag: PackageManager = context.packageManager
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.flags = Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED or Intent.FLAG_ACTIVITY_NEW_TASK
        val list: List<ResolveInfo> = pkgMag.queryIntentActivities(intent, PackageManager.GET_ACTIVITIES)
        for (element in list) {
            val info: ResolveInfo = element
            if (info.activityInfo.packageName == packageName) {
                mainAct = info.activityInfo.name
                break
            }
        }
        intent.component = ComponentName(packageName, mainAct)
        return intent
    }

}