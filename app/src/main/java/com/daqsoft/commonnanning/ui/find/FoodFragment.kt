package com.daqsoft.commonnanning.ui.find

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daqsoft.commonnanning.R
import com.daqsoft.commonnanning.food_detial.FoodDetialActivity
import com.daqsoft.commonnanning.http.RetrofitHelper
import com.daqsoft.commonnanning.ui.entity.IndexScenic
import com.daqsoft.commonnanning.utils.GlideUtils
import com.daqsoft.commonnanning.utils.Utils
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter
import com.example.tomasyb.baselib.adapter.BaseViewHolder
import com.example.tomasyb.baselib.base.net.common.DefaultObserver
import com.example.tomasyb.baselib.base.net.entity.BaseResponse
import com.example.tomasyb.baselib.base.net.execute
import kotlinx.android.synthetic.main.fragment_find_content.*
import java.util.*

/**
 * 风味南宁
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-5-22
 * @since JDK 1.8.0_191
 */
class FoodFragment : Fragment() {
    private val data = mutableListOf<IndexScenic>()
    private var page = 1
    private var pageSize = 10
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_find_content, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }


    private val adapter = object : BaseQuickAdapter<IndexScenic, BaseViewHolder>(R.layout.item_find_content_type4, data) {
        override fun convert(helper: BaseViewHolder?, item: IndexScenic?) {
            GlideUtils.loadImage(
                    context,
                    helper?.getView(R.id.mCoverIv),
                    item?.coverFourToThree,
                    R.mipmap.common_img_fail_h158
            )
            helper?.setText(R.id.mTitleTv, item?.name)
            helper?.itemView?.setOnClickListener {
                val intent = Intent(context, FoodDetialActivity::class.java)
                intent.putExtra("ID", item?.id?.toString())
                startActivity(intent)
            }
        }
    }

    fun initView() {
        mRecyclerView.layoutManager = GridLayoutManager(context, 2)
        mRecyclerView.adapter = adapter
        adapter.setOnLoadMoreListener({
            page++
            getIndexFood()
        }, mRecyclerView)
        mSmartRefreshLayout.setOnRefreshListener {
            data.clear()
            page = 1
            getIndexFood()
            mSmartRefreshLayout.finishRefresh(500)
        }
        adapter.setEnableLoadMore(false)
    }

    fun initData() {
        mSmartRefreshLayout.autoRefresh()
        getIndexFood()

    }

    fun getIndexFood() {
        RetrofitHelper.getApiService()
                .getIndexFood(
                        page.toString(),
                        pageSize.toString()
                )
                .execute(object : DefaultObserver<IndexScenic>() {
                    override fun onSuccess(response: BaseResponse<IndexScenic>?) {
                        Utils.pageDeal(page, data as ArrayList<*>, response, adapter, mViewAnimator)
                        if (response?.code == 0) {
                            data.addAll(response?.datas!!)
                            adapter.notifyDataSetChanged()
                        }
                    }
                })
    }
}