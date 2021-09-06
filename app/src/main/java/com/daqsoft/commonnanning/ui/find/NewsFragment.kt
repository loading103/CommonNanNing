package com.daqsoft.commonnanning.ui.find

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.daqsoft.commonnanning.R
import com.daqsoft.commonnanning.common.Constant
import com.daqsoft.commonnanning.common.ParamsCommon
import com.daqsoft.commonnanning.http.RetrofitHelper
import com.daqsoft.commonnanning.ui.entity.IndexActivity
import com.daqsoft.commonnanning.ui.entity.NewsListEntity
import com.daqsoft.commonnanning.utils.GlideUtils
import com.daqsoft.commonnanning.utils.Utils
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter
import com.example.tomasyb.baselib.adapter.BaseViewHolder
import com.example.tomasyb.baselib.base.net.common.DefaultObserver
import com.example.tomasyb.baselib.base.net.entity.BaseResponse
import com.example.tomasyb.baselib.base.net.execute
import kotlinx.android.synthetic.main.fragment_find_content.*

/**
 * 资讯南宁
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-5-22
 * @since JDK 1.8.0_191
 */
class NewsFragment : Fragment() {
    private var page = 1
    private var pageSize = 10
    private val data = arrayListOf<NewsListEntity>()
    //val adapter by lazy { FestivalAdapter(context, data) }

    private val adapter = object :BaseQuickAdapter<NewsListEntity,BaseViewHolder>(R.layout.item_find_content_type1,data){
        override fun convert(helper: BaseViewHolder?, item: NewsListEntity?) {
            GlideUtils.loadRoundImage(
                    context,
                    helper?.getView(R.id.mCoverIv),
                    item?.cover,
                    R.mipmap.common_img_fail_h158
            )
            helper?.let {
                it.setText(R.id.mTitleTv,item?.title)
                it.setText(R.id.mIntroTv,item?.summary)
                it.setText(R.id.mTimeTv,item?.createTime)
                it.setText(R.id.mViewTv,item?.viewCount.toString())

            }
            helper?.itemView?.setOnClickListener {
                ARouter.getInstance().build(Constant.ACTIVITY_DETAILS_WEB)
                        .withString("id", item?.getId())
                        .withString("code", ParamsCommon.SERVICE_LYZX)
                        .withString("title", "资讯详情")
                        .navigation()
            }



        }

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_find_content, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    fun initView() {
        mRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.adapter = adapter
        adapter.setOnLoadMoreListener({
            page++
            activityList()
        },mRecyclerView)
        mSmartRefreshLayout.setOnRefreshListener {
            data.clear()
            page = 1
            activityList()
            mSmartRefreshLayout.finishRefresh(500)
        }
    }

    fun initData() {
        mSmartRefreshLayout.autoRefresh()
        activityList()
    }

    /**
     * 主题活动列表
     */
    fun activityList() {
        RetrofitHelper.getApiService()
                .getNewsList(
                        ParamsCommon.SERVICE_LYZX,
                        "","",
                        pageSize.toString(),
                        page.toString()
                )
                .execute(object : DefaultObserver<NewsListEntity>() {
                    override fun onSuccess(response: BaseResponse<NewsListEntity>?) {
                        Utils.pageDeal(page,data,response,adapter,mViewAnimator)
                        data.addAll(response?.datas!!)
                        adapter.notifyDataSetChanged()
                    }
                })
    }
}