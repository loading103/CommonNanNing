package com.daqsoft.commonnanning.ui.main

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.widget.ImageView
import com.daqsoft.android.ProjectConfig
import com.daqsoft.commonnanning.R
import com.daqsoft.commonnanning.http.RetrofitHelper
import com.daqsoft.commonnanning.scenic.VideoPlayActivity
import com.daqsoft.commonnanning.ui.entity.VideoBean
import com.daqsoft.commonnanning.utils.GlideUtils
import com.daqsoft.commonnanning.utils.Utils
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter
import com.example.tomasyb.baselib.adapter.BaseViewHolder
import com.example.tomasyb.baselib.base.BaseActivity
import com.example.tomasyb.baselib.base.net.common.DefaultObserver
import com.example.tomasyb.baselib.base.net.entity.BaseResponse
import com.example.tomasyb.baselib.base.net.execute
import kotlinx.android.synthetic.main.activity_scenic_video.*
import java.util.*

/**
 * 宣传视频
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-9-19
 * @since JDK 1.8.0_191
 */
class PromotionalVideoActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_scenic_video;
    }

    private val strategyId by lazy { intent.getStringExtra("mId") }

    override fun initView() {
        head.setTitle("景区视频")
        head.setBackListener { finish() }
        initAdapter()
        refreshlayout.autoRefresh()
        refreshlayout.setOnRefreshListener {
            getData(true)
            refreshlayout.finishRefresh()
        }

    }

    private var page = 1
    private var pageSize = 10
    private var data = mutableListOf<VideoBean>()
    private val adapter by lazy {
        object : BaseQuickAdapter<VideoBean, BaseViewHolder>(R.layout.item_fun_line, data) {
            override fun convert(helper: BaseViewHolder?, item: VideoBean?) {
                helper?.setVisible(R.id.img_paly, true)
                helper?.getView<ImageView>(R.id.img_index_scenic)?.setOnClickListener {
                    val intent = Intent(this@PromotionalVideoActivity, VideoPlayActivity::class.java)
                    intent.putExtra("content", item?.upload)
                    startActivity(intent)
                }
                GlideUtils.loadImage(
                        this@PromotionalVideoActivity,
                        helper?.getView(R.id.img_index_scenic),
                        item?.coverpicture,
                        R.mipmap.common_img_fail_h158
                )
                helper?.setText(R.id.tv_index_scenic, item?.sourceName)
            }
        }
    }

    private fun initAdapter() {
        adapter.setOnLoadMoreListener({
            getData(false)
        }, mpanlist_rv)
        mpanlist_rv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@PromotionalVideoActivity.adapter
        }
    }

    private fun getData(isRefresh: Boolean) {
        if (isRefresh) {
            page = 1
            // 这里的作用是防止下拉刷新的时候还可以上拉加载
            adapter.setEnableLoadMore(false)
        }
        RetrofitHelper.getApiService()
                .siteVideoList(
                        page,
                        pageSize,
                        "sourceType_1",
                        null,
                        null,
                        ProjectConfig.REGION,
                        null,
                        null,
                        null,
                        null,
                        null
                )
                .execute(object : DefaultObserver<VideoBean>() {
                    override fun onSuccess(response: BaseResponse<VideoBean>?) {
                        Utils.pageDeal(page, data as ArrayList<*>, response, adapter, pan_va)
                        if (response?.datas != null) {
                            data.addAll(response.datas)
                            adapter.notifyDataSetChanged()
                        }

                    }
                })
    }

    override fun initData() {

    }
}