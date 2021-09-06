package com.daqsoft.commonnanning.ui.find

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.daqsoft.commonnanning.http.RetrofitHelper
import com.daqsoft.commonnanning.ui.entity.ImagesBean
import com.daqsoft.commonnanning.utils.GlideUtils
import com.daqsoft.commonnanning.utils.Utils
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter
import com.example.tomasyb.baselib.adapter.BaseViewHolder
import com.example.tomasyb.baselib.base.net.common.DefaultObserver
import com.example.tomasyb.baselib.base.net.entity.BaseResponse
import com.example.tomasyb.baselib.base.net.execute
import io.agora.yview.photoview.PicturePreviewActivity
import kotlinx.android.synthetic.main.fragment_find_content.*
import java.util.*


/**
 * 最美南宁
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-5-22
 * @since JDK 1.8.0_191
 */
class AlbumFragment : Fragment() {
    private val data = mutableListOf<ImagesBean>()
    private val imgs = arrayListOf<String>()
    private var page = 1
    private var pageSize = 10
    private var windowWidth: Int? = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(com.daqsoft.commonnanning.R.layout.fragment_find_content, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        var wm = activity?.windowManager
        windowWidth = wm?.defaultDisplay?.width?.div(2)
    }

    private val adapter = object : BaseQuickAdapter<ImagesBean, BaseViewHolder>(com.daqsoft.commonnanning.R.layout.item_find_content_type3, data) {
        override fun convert(helper: BaseViewHolder?, item: ImagesBean?) {
            val view = helper?.getView(com.daqsoft.commonnanning.R.id.mCoverIv) as ImageView
            val layoutParams = view.layoutParams
            val rate: Float = item?.height?.toFloat()?.div(item.width.toFloat()) ?: 1.0f
            layoutParams.height = windowWidth?.times(rate ?: 1.0f)!!.toInt()
            layoutParams.width = windowWidth!!
            view.layoutParams = layoutParams

            helper?.setText(com.daqsoft.commonnanning.R.id.mTitleTv, item?.title)
            if (item?.type?.isEmpty() == true) {
                helper?.getView<TextView>(com.daqsoft.commonnanning.R.id.mTagTv)?.visibility = View.GONE
            } else {
                helper?.getView<TextView>(com.daqsoft.commonnanning.R.id.mTagTv)?.visibility = View.VISIBLE
                helper?.setText(com.daqsoft.commonnanning.R.id.mTagTv, item?.type)
            }
            GlideUtils.loadImage(
                    context,
                    helper?.getView(com.daqsoft.commonnanning.R.id.mCoverIv),
                    item?.url,
                    com.daqsoft.commonnanning.R.mipmap.common_img_fail_h300
            )
            helper?.itemView?.setOnClickListener {
                val intent = Intent(context, PicturePreviewActivity::class.java)
                imgs.clear()
                for (item in data) {
                    imgs.add(item.url)
                }
                intent.putExtra("imgList", imgs)
                intent.putExtra("currentPosition", helper.adapterPosition)
                startActivity(intent)
            }
        }
    }

    fun initView() {
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //刷新
                layoutManager.invalidateSpanAssignments();
            }
        })
        mRecyclerView.adapter = adapter
        adapter.setOnLoadMoreListener({
            page++
            sitePictureList()
        }, mRecyclerView)
        mSmartRefreshLayout.setOnRefreshListener {
            data.clear()
            page = 1
            sitePictureList()
            mSmartRefreshLayout.finishRefresh(500)
        }
        adapter.setEnableLoadMore(false)
    }

    fun initData() {
        mSmartRefreshLayout.autoRefresh()
        sitePictureList()

    }

    fun sitePictureList() {
        RetrofitHelper.getApiService()
                .sitePictureList(
                        page,
                        pageSize,
                        "sourceType_1"
                )
                .execute(object : DefaultObserver<ImagesBean>() {
                    override fun onSuccess(response: BaseResponse<ImagesBean>?) {
                        Utils.pageDeal(page, data as ArrayList<*>, response, adapter, mViewAnimator)
                        data.addAll(response?.datas!!)
                        adapter.notifyDataSetChanged()
                        imgs.clear()
                        response.datas?.forEach {
                            imgs.add(it.url)
                        }
                    }
                })
    }
}