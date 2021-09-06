package com.daqsoft.commonnanning.ui.find

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daqsoft.commonnanning.R
import com.daqsoft.commonnanning.common.ParamsCommon.ACTIVITY_CHANELCODE
import com.daqsoft.commonnanning.http.RetrofitHelper
import com.daqsoft.commonnanning.ui.entity.IndexActivity
import com.example.tomasyb.baselib.base.net.common.DefaultObserver
import com.example.tomasyb.baselib.base.net.entity.BaseResponse
import com.example.tomasyb.baselib.base.net.execute
import kotlinx.android.synthetic.main.fragment_find_content.*

/**
 * 节庆活动子Fragment
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-5-22
 * @since JDK 1.8.0_191
 */
class FestivalFragment : Fragment() {

    private var page = 1
    private var pageSize = 10
    private val data = arrayListOf<IndexActivity>()
    val adapter by lazy { FestivalAdapter(context, data) }
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
        adapter.setLoadMoreListener {
            page++
            activityList()
        }
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
                .getActivityList2(
                        page.toString(),
                        pageSize.toString(),
                        null,
                        ACTIVITY_CHANELCODE
                )
                .execute(object : DefaultObserver<IndexActivity>() {
                    override fun onSuccess(response: BaseResponse<IndexActivity>?) {
                        if (response?.page?.total ?: 0 > 0) {
                            if (mViewAnimator != null) {
                                mViewAnimator.displayedChild = 0
                            }
                            var totalPage: Int? = response?.page?.totalPage
                            var currPage: Int? = response?.page?.currPage
                            if (currPage !! < totalPage !!) {
                                adapter.setLoadMoreListener {
                                    page++
                                    activityList()
                                }
                            }else{
                                adapter.setLoadMoreListener {
                                    null
                                }
                            }
                            data.addAll(response?.datas!!)
                            adapter.notifyDataSetChanged()
                        } else {
                            if (mViewAnimator != null) {
                                mViewAnimator.displayedChild = 1
                            }
                        }
                    }
                })
    }
}