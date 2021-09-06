package com.daqsoft.commonnanning.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.daqsoft.commonnanning.R
import com.daqsoft.commonnanning.common.SPCommon
import com.daqsoft.commonnanning.helps_gdmap.MapNaviUtils
import com.daqsoft.commonnanning.http.RetrofitHelper
import com.daqsoft.commonnanning.ui.entity.EntertainmentListBean
import com.daqsoft.commonnanning.ui.entity.ListDictBean
import com.daqsoft.commonnanning.utils.GlideUtils
import com.daqsoft.commonnanning.utils.Utils
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter
import com.example.tomasyb.baselib.adapter.BaseViewHolder
import com.example.tomasyb.baselib.base.mvp.BaseActivity
import com.example.tomasyb.baselib.base.mvp.IBasePresenter
import com.example.tomasyb.baselib.base.net.common.DefaultObserver
import com.example.tomasyb.baselib.base.net.entity.BaseResponse
import com.example.tomasyb.baselib.base.net.execute
import com.example.tomasyb.baselib.util.PhoneUtils
import com.example.tomasyb.baselib.util.SPUtils
import kotlinx.android.synthetic.main.activity_entertainment.*
import kotlinx.android.synthetic.main.include_search_center.*
import java.util.*

/**
 * 娱乐界面
 *
 * 需要参数 ：
 * type 传过来选择的类型 （0：娱乐推荐，1：娱乐场所）
 *
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-6-20
 * @since JDK 1.8.0_191
 */
class EntertainmentActivity : BaseActivity<IBasePresenter>(), View.OnClickListener {

    override fun getLayoutId(): Int = R.layout.activity_entertainment
    private val type by lazy { intent.getIntExtra("type", 1) }
    private var code: String? = null
    private var page = 1
    private var pageSize = 10
    private var selectedPostion = 0
    private val data = mutableListOf<EntertainmentListBean>()
    private val adapter = object : BaseQuickAdapter<EntertainmentListBean, BaseViewHolder>(R.layout.item_entertainment, data) {
        override fun convert(helper: BaseViewHolder?, item: EntertainmentListBean?) {
            GlideUtils.loadImage(
                    this@EntertainmentActivity,
                    helper?.getView(R.id.mCoverIv),
                    item?.cover,
                    R.mipmap.common_img_fail_h158
            )
            helper?.setText(R.id.mTitleTv, item?.name)
            if (!TextUtils.isEmpty(item?.phone)) {
                helper?.setText(R.id.mPhoneTv, "电话：${item?.phone}")
                helper?.getView<TextView>(R.id.mPhoneTv)?.visibility = View.VISIBLE
            } else {
                helper?.getView<TextView>(R.id.mPhoneTv)?.visibility = View.GONE
            }

            if (!TextUtils.isEmpty(item?.address)) {
                helper?.setText(R.id.mAddressTv, "地址：${item?.address}")
                helper?.getView<TextView>(R.id.mAddressTv)?.visibility = View.VISIBLE
            } else {
                helper?.getView<TextView>(R.id.mAddressTv)?.visibility = View.VISIBLE
            }


            helper?.getView<TextView>(R.id.mPhoneTv)?.setOnClickListener {
                if (ActivityCompat.checkSelfPermission(this@EntertainmentActivity,
                                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return@setOnClickListener
                }
                PhoneUtils.call(item?.phone)
            }
            helper?.getView<TextView>(R.id.mAddressTv)?.setOnClickListener {
                MapNaviUtils.isMapNaviUtils(
                        this@EntertainmentActivity,
                        SPUtils.getInstance().getString(SPCommon.LATITUDE),
                        SPUtils.getInstance().getString(SPCommon.LONGITUDE),
                        SPUtils.getInstance().getString(SPCommon.CITY_NAME),
                        item?.latitude,
                        item?.longitude,
                        item?.name
                )
            }
            helper?.itemView?.setOnClickListener {
                val intent = Intent(this@EntertainmentActivity, EntertainDetailActivity::class.java)
                intent.putExtra("id", item?.id)
                startActivity(intent)
            }
        }
    }
    private val listDict = mutableListOf<ListDictBean>()
    private val listAdapter = object : BaseQuickAdapter<ListDictBean, BaseViewHolder>(R.layout.item_popup_select,
            listDict) {
        override fun convert(helper: BaseViewHolder?, item: ListDictBean?) {
            helper?.setText(R.id.mNameTv, item?.name)
            helper?.getView<TextView>(R.id.mNameTv)?.isSelected = helper?.adapterPosition == selectedPostion
            helper?.itemView?.setOnClickListener {
                code = item?.skey
                selectedPostion = helper.adapterPosition
                popupWindow?.dismiss()
                notifyDataSetChanged()
                getEntertainment()
            }
        }
    }

    private var popupWindow: PopupWindow? = null
    override fun initView() {
        mHeadView.setTitle("娱乐")
        scenic_search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchKey = scenic_search.text.toString()
                getEntertainment()
                scenic_search.setText("")
            }

            return@setOnEditorActionListener false
        }

        initPopupWindow()
        setTabSelect()
        setAdapter()
        getEntertainmentType()
        getEntertainment()
    }

    override fun initPresenter(): IBasePresenter? {
        return null
    }

    /**
     * 初始化条件选择需要用的PopupWindow
     */
    fun initPopupWindow() {
        val inflate = LayoutInflater.from(this).inflate(R.layout.popup_list, null, false)
        popupWindow = PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        val mRecyclerView = inflate.findViewById<RecyclerView>(R.id.mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.adapter = listAdapter

        popupWindow?.isFocusable = true
        popupWindow?.isOutsideTouchable = true
        popupWindow?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        popupWindow?.setOnDismissListener {
            val lp = window.attributes
            lp.alpha = 1f
            window.attributes = lp
        }
    }

    /**
     * 设置Tab的初始化选择状态
     */
    fun setTabSelect() {
        mRecommendTv.setOnClickListener(this)
        mPlaceTv.setOnClickListener(this)
        if (type == 1) {
            mPlaceTv.isSelected = true
            mIndictor0.visibility = View.INVISIBLE
            mIndictor1.visibility = View.VISIBLE
        } else {
            mRecommendTv.isSelected = true
            mIndictor0.visibility = View.VISIBLE
            mIndictor1.visibility = View.INVISIBLE
        }
        mTypeSelectTv.setOnClickListener(this)
    }

    /**
     * 设置适配器
     */
    fun setAdapter() {
        mRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.adapter = adapter
    }

    /**
     * 获取娱乐场所类型
     */
    fun getEntertainmentType() {
        RetrofitHelper.getApiService()
                .recreationListDict()
                .execute(object : DefaultObserver<ListDictBean>() {
                    override fun onSuccess(response: BaseResponse<ListDictBean>?) {
                        if (response?.datas != null && response.datas.size > 0) {
                            listDict.addAll(response.datas)
                            listAdapter.notifyDataSetChanged()
                        }
                    }
                })
    }


    private var searchKey = ""

    private var recommend = 0
    /**
     * 获取娱乐场所数据
     */
    fun getEntertainment() {
        RetrofitHelper.getApiService()
                .recreationList(
                        pageSize,
                        page,
                        code,
                        searchKey,
                        null,
                        recommend
                )
                .execute(object : DefaultObserver<EntertainmentListBean>() {
                    override fun onSuccess(response: BaseResponse<EntertainmentListBean>?) {
                        Utils.pageDeal(page, data as ArrayList<*>, response, adapter, mViewAnimator)
                        if (response?.datas != null) {
                            data.addAll(response.datas!!)
                            adapter.notifyDataSetChanged()
                        }
                    }
                })

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mRecommendTv -> {
                mRecommendTv.isSelected = true
                mPlaceTv.isSelected = false
                mIndictor0.visibility = View.VISIBLE
                mIndictor1.visibility = View.INVISIBLE

                recommend = 1
                getEntertainment()
            }
            R.id.mPlaceTv -> {
                mRecommendTv.isSelected = false
                mPlaceTv.isSelected = true
                mIndictor0.visibility = View.INVISIBLE
                mIndictor1.visibility = View.VISIBLE

                recommend = 0
                getEntertainment()
            }
            R.id.mTypeSelectTv -> {
                val lp = window.attributes
                lp.alpha = 0.8f
                window.attributes = lp
                popupWindow?.showAsDropDown(mTypeSelectTv)
            }
        }
    }

}