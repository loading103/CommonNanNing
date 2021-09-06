package com.daqsoft.commonnanning.ui.find

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daqsoft.commonnanning.R
import com.daqsoft.commonnanning.http.RetrofitHelper
import com.daqsoft.commonnanning.ui.entity.SpecialListBean
import com.daqsoft.commonnanning.ui.specialty.SpecialtyDetailActivity
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
 * 物产南宁
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-5-22
 * @since JDK 1.8.0_191
 */
class SpecialtyFragment : Fragment() {

    private var page = 1
    private var pageSize = 10
    private val data = mutableListOf<SpecialListBean>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_find_content, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }


    private val adapter = object : BaseQuickAdapter<SpecialListBean, BaseViewHolder>(R.layout.item_find_content_type4,
            data) {
        override fun convert(helper: BaseViewHolder?, item: SpecialListBean?) {
            GlideUtils.loadImage(
                    context,
                    helper?.getView(R.id.mCoverIv),
                    item?.coverFourToThree,
                    R.mipmap.common_img_fail_h158
            )
            helper?.setText(R.id.mTitleTv, item?.name)
            helper?.itemView?.setOnClickListener {
                val intent = Intent(context, SpecialtyDetailActivity::class.java)
                intent.putExtra("id", item?.id)
                startActivity(intent)
            }
        }
    }

    fun initView() {
        mRecyclerView.layoutManager = GridLayoutManager(context, 2) as RecyclerView.LayoutManager?
        mRecyclerView.adapter = adapter
        adapter.setOnLoadMoreListener({
            page++
            speciaList()
        }, mRecyclerView)
        adapter.setEnableLoadMore(false)
        mSmartRefreshLayout.setOnRefreshListener {
            data.clear()
            page = 1
            initData()
            mSmartRefreshLayout.finishRefresh(500)
        }
    }

    fun initData() {
        mSmartRefreshLayout.autoRefresh()
        speciaList()
    }

    fun speciaList() {
        RetrofitHelper.getApiService()
                .specialGoodsDetail(page, pageSize, "")
                .execute(object : DefaultObserver<SpecialListBean>() {
                    override fun onSuccess(response: BaseResponse<SpecialListBean>?) {
                        Utils.pageDeal(page, data as ArrayList<*>, response, adapter, mViewAnimator)
                        if (response?.code == 0) {
                            data.addAll(response?.datas!!)
                            adapter.notifyDataSetChanged()
                        }
                    }
                })
    }
}