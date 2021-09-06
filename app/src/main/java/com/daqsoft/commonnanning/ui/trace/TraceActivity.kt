package com.daqsoft.commonnanning.ui.trace

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import com.daqsoft.android.ProjectConfig
import com.daqsoft.commonnanning.R
import com.daqsoft.commonnanning.http.RetrofitHelper
import com.daqsoft.commonnanning.ui.entity.FootPrintListBean
import com.daqsoft.commonnanning.ui.entity.StatisticsMapBean
import com.daqsoft.commonnanning.utils.GlideUtils
import com.daqsoft.commonnanning.utils.Utils
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter
import com.example.tomasyb.baselib.adapter.BaseViewHolder
import com.example.tomasyb.baselib.base.BaseActivity
import com.example.tomasyb.baselib.base.net.common.DefaultObserver
import com.example.tomasyb.baselib.base.net.entity.BaseResponse
import com.example.tomasyb.baselib.base.net.execute
import kotlinx.android.synthetic.main.activity_trace.*
import java.util.*

/**
 * 旅游足迹
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-9-11
 * @since JDK 1.8.0_191
 */
class TraceActivity : BaseActivity(), View.OnClickListener {


    override fun getLayoutId(): Int = R.layout.activity_trace

    override fun initView() {
        mHeadView.setTitle("旅游足迹")
        setAdapter()
        setOnClick()
    }

    private fun setAdapter() {
        mTraceRv.apply {
            layoutManager = LinearLayoutManager(this@TraceActivity)
            adapter = this@TraceActivity.adapter
        }
        adapter.setOnLoadMoreListener({
            page++
            getfootprintList()
        }, mTraceRv)
        adapter.setEnableLoadMore(false)
    }

    private fun setOnClick() {
        mCreateTv.setOnClickListener(this)
        mCreateTraceTv.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        getfootprintList()
    }
    override fun initData() {

    }

    private var page = 1
    private var pageSize = 10

    private val data = mutableListOf<FootPrintListBean>()
    private val adapter by lazy {
        object : BaseQuickAdapter<FootPrintListBean, BaseViewHolder>(R.layout.item_trace, data) {
            override fun convert(helper: BaseViewHolder?, item: FootPrintListBean?) {
                GlideUtils.loadImage(
                        this@TraceActivity,
                        helper?.getView(R.id.mCoverIv),
                        item?.logo,
                        R.mipmap.common_img_fail_h300
                )
                helper?.setText(R.id.mNameTv, item?.name)
                helper?.setText(R.id.mIntroTv, item?.summary)
                helper?.setText(R.id.mTimeTv, item?.gmtCreate)
                helper?.setText(R.id.tv_point, "${item?.num}个景区")
                helper?.getView<ImageView>(R.id.mCoverIv)?.setOnClickListener {
                    val intent = Intent(this@TraceActivity, TraceDetailActivity::class.java)
                    intent.putExtra("id", item?.id)
                    startActivity(intent)
                }
                helper?.itemView?.setOnClickListener {
                    val intent = Intent(this@TraceActivity, TraceDetailActivity::class.java)
                    intent.putExtra("id", item?.id)
                    startActivity(intent)
                }
            }
        }
    }

    /**
     * 足迹列表
     */
    private fun getfootprintList() {
        RetrofitHelper.getApiService()
                .getFootPrintList(page, pageSize)
                .execute(object : DefaultObserver<FootPrintListBean>() {
                    override fun onSuccess(response: BaseResponse<FootPrintListBean>?) {
                        if (response?.datas != null && response.datas?.isEmpty() == true) {
                            mViewAnimator.displayedChild = 0
                        } else {
                            mViewAnimator.displayedChild = 1
                            mTotalTv.text = "${response?.datas?.size}条${ProjectConfig.CITY_NAME}旅游足迹"
                            getStatisticsMap()
                            Utils.pageDeal(page, data as ArrayList<*>, response, adapter, null)
                            if (response?.datas != null) {
                                data.addAll(response.datas!!)
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                })
    }

    /**
     * 足迹统计信息
     */
    private fun getStatisticsMap() {
        RetrofitHelper.getApiService()
                .getStatisticsMap(null)
                .execute(object : DefaultObserver<StatisticsMapBean>() {
                    override fun onSuccess(response: BaseResponse<StatisticsMapBean>?) {
                        val sb = StringBuilder("一共去过${response?.data?.total}个景区")
                        for (i in 0 until (response?.data?.levelList?.size ?: 0)) {
                            sb.append(" · ")
                            sb.append("${response?.data?.levelList?.get(i)?.num}个" +
                                    "${response?.data?.levelList?.get(i)?.levelName}景区")
                        }
                        mCountTv.text = sb.toString()
                        GlideUtils.loadCircleImage(
                                this@TraceActivity,
                                mHeadIv,
                                response?.data?.head,
                                R.mipmap.common_img_fail_h158
                        )
                    }
                })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mCreateTv, R.id.mCreateTraceTv -> {
                startActivity(Intent(this, ScenicChooseActivity::class.java))
            }
        }
    }
}