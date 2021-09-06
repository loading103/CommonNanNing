package com.daqsoft.commonnanning.ui.find

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daqsoft.commonnanning.R
import com.daqsoft.commonnanning.common.ParamsCommon
import com.daqsoft.commonnanning.http.RetrofitHelper
import com.daqsoft.commonnanning.ui.country.KotlinUtils
import com.daqsoft.commonnanning.ui.entity.AdvertEntity
import com.daqsoft.commonnanning.ui.entity.IndexBanner
import com.example.tomasyb.baselib.base.net.common.DefaultObserver
import com.example.tomasyb.baselib.base.net.entity.BaseResponse
import com.example.tomasyb.baselib.base.net.execute
import com.example.tomasyb.baselib.widget.viewpager.ComPagerWithTitleAdapter
import kotlinx.android.synthetic.main.fragment_find.*

/**
 * 发现界面
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-5-21
 * @since JDK 1.8.0_191
 */
class FindFragment : Fragment() {
    lateinit var titles: Array<String>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_find, container, false)

    private val bannerImgs = arrayListOf<IndexBanner>()

    companion object {
        val instance: FindFragment by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            FindFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()

    }

    private var mAdapter: ComPagerWithTitleAdapter? = null
    fun initView() {
        titles = activity!!.resources!!.getStringArray(R.array.find_titles)
        val fragments = arrayListOf<Fragment>(
                FestivalFragment(),
                NewsFragment(),
                AlbumFragment(),
                FoodFragment(),
                SpecialtyFragment()
        )
        mViewPager.adapter = ComPagerWithTitleAdapter(fragmentManager, fragments, titles)
        mTabLayout.setViewPager(mViewPager, titles, activity, fragments)
        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> getBanner(ParamsCommon.FESTIVAL_TOP)
                    1 -> getBanner(ParamsCommon.NEWS_TOP)
                    2 -> getBanner(ParamsCommon.ALBUM_TOP)
                    3 -> getBanner(ParamsCommon.FOOD_TOP)
                    4 -> getBanner(ParamsCommon.SPECIALTY_TOP)
                }
            }

        })

        KotlinUtils.initBanner(bannerImgs, mBanner, activity, true)
        getBanner(ParamsCommon.FESTIVAL_TOP)
    }

    /**
     * 获取Banner图片
     */
    fun getBanner(code: String) {
        bannerImgs.clear()
        RetrofitHelper.getApiService()
                .getIndexBannar(code)
                .execute(object : DefaultObserver<AdvertEntity>() {
                    override fun onSuccess(response: BaseResponse<AdvertEntity>?) {
                        if (response?.datas != null) {
                            response.datas?.forEach {
                                val indexBanner = IndexBanner()
                                indexBanner.img = it.pics[0]
                                indexBanner.id = it.id
                                bannerImgs.add(indexBanner)
                            }
                            mBanner.notifyDataSetChanged()
                        }
                    }
                })
    }
}