package com.daqsoft.commonnanning.ui.trace

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.daqsoft.commonnanning.R
import com.daqsoft.commonnanning.common.SPCommon
import com.daqsoft.commonnanning.http.RetrofitHelper
import com.daqsoft.commonnanning.ui.entity.IndexScenic
import com.daqsoft.commonnanning.ui.entity.ScenicSeceledEnty
import com.daqsoft.commonnanning.utils.GlideUtils
import com.daqsoft.commonnanning.utils.Utils
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter
import com.example.tomasyb.baselib.adapter.BaseViewHolder
import com.example.tomasyb.baselib.base.BaseActivity
import com.example.tomasyb.baselib.base.net.common.DefaultObserver
import com.example.tomasyb.baselib.base.net.entity.BaseResponse
import com.example.tomasyb.baselib.base.net.execute
import com.example.tomasyb.baselib.util.KeyboardUtils
import com.example.tomasyb.baselib.util.SPUtils
import com.example.tomasyb.baselib.util.ToastUtils
import kotlinx.android.synthetic.main.activity_scenic_choose.*

/**
 * 选择景区
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-9-11
 * @since JDK 1.8.0_191
 */
class ScenicChooseActivity : BaseActivity(), View.OnClickListener {


    override fun getLayoutId(): Int = R.layout.activity_scenic_choose

    override fun initView() {
        mHeadView.setTitle("选择景区")
        setAdapter()
        setOnClick()
        mSearchEt.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                getScenicList()
                KeyboardUtils.hideSoftInput(this@ScenicChooseActivity)
            }
            false
        }
    }

    private fun setOnClick() {
        mLevelChooseTv.setOnClickListener(this)
        mDistanceTv.setOnClickListener(this)
        mNextTv.setOnClickListener(this)
    }

    override fun initData() {
        getScenerySelectedType()
        getScenicList()
    }

    /**
     * 等级分类集合
     */
    private val orderTypeList = mutableListOf<ScenicSeceledEnty.ResourceLevelBean>()

    private var page = 1
    private var pageSize = 50
    /**
     * 排序方式
     */
    private var orderType = "recommendedPriority"
    /**
     * 等级选择的内容
     */
    private var orderedPostion = 0
    private val scenicSelected = mutableListOf<IndexScenic>()
    private var orderLevel: String? = null
    private var selectorAdapter: BaseQuickAdapter<ScenicSeceledEnty.ResourceLevelBean, BaseViewHolder>? = null
    private val scenicData = mutableListOf<IndexScenic>()
    private var selectCount = 0
    private val scenicAdapter by lazy {
        object : BaseQuickAdapter<IndexScenic, BaseViewHolder>(R.layout.item_scenic_choose, scenicData) {
            override fun convert(helper: BaseViewHolder?, item: IndexScenic?) {
                GlideUtils.loadImage(
                        this@ScenicChooseActivity,
                        helper?.getView(R.id.mCoverIv),
                        item?.picture,
                        R.mipmap.common_img_fail_h158
                )
                helper?.setText(R.id.mNameTv, item?.name)
                helper?.setText(R.id.mLevelTv, item?.levelName)
                helper?.itemView?.isSelected = item?.isSelected == true
                helper?.itemView?.setOnClickListener {
                    item?.isSelected = !item?.isSelected!!
                    if (item.isSelected) {
                        selectCount++
                        scenicSelected.add(item)
                    } else {
                        selectCount--
                        scenicSelected.remove(item)
                    }
                    setText()
                    notifyItemChanged(helper.adapterPosition)
                }
            }
        }
    }


    private fun setAdapter() {
        mScenicRv.apply {
            layoutManager = GridLayoutManager(this@ScenicChooseActivity, 2)
            adapter = scenicAdapter
        }
    }

    /**
     * 等级选择弹窗
     */
    private val levelSelectTypePop by lazy {
        // 创建新的pop
        val contentView = LayoutInflater.from(mContext)
                .inflate(R.layout.pop_order, null, false) as ConstraintLayout
        val popupWindow = PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true)
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        popupWindow.isOutsideTouchable = false
        popupWindow.setOnDismissListener { mLevelChooseTv.isSelected = false }
        val recyclerView = contentView.getViewById(R.id.recycler_view) as RecyclerView

        val lm = LinearLayoutManager(this)
        recyclerView.layoutManager = lm

        // 添加自定义分割线
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_line_divider)!!)
        recyclerView.addItemDecoration(divider)

        selectorAdapter = object : BaseQuickAdapter<ScenicSeceledEnty.ResourceLevelBean, BaseViewHolder>(R.layout.item_selector, orderTypeList) {
            override fun convert(holder: BaseViewHolder?, item: ScenicSeceledEnty.ResourceLevelBean) {
                val tv = holder?.getView<TextView>(R.id.select_text)
                tv?.text = item.skeyName
                if (orderedPostion == 0) {
                    orderLevel = item.skey
                }
                holder?.getView<TextView>(R.id.select_text)?.isSelected = orderedPostion == holder?.adapterPosition
                holder?.getView<TextView>(R.id.select_text)?.setOnClickListener {
                    orderedPostion = holder.adapterPosition ?: 0
                    orderLevel = item.skey
                    popupWindow.dismiss()
                    getScenicList()
                    mLevelChooseTv.text = item.skeyName
                    notifyDataSetChanged()
                    page = 1
                }
            }
        }
        recyclerView.adapter = selectorAdapter
        popupWindow
    }
    /**
     * 距离选择位置
     */
    private var distanceSelectedPosition = 0
    /**
     * 距离选择弹窗
     */
    private val distanceTypePop by lazy {
        // 创建新的pop
        val contentView = LayoutInflater.from(mContext)
                .inflate(R.layout.pop_order, null, false) as ConstraintLayout
        val popupWindow = PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true)
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        popupWindow.isOutsideTouchable = false
        popupWindow.setOnDismissListener { mLevelChooseTv.isSelected = false }
        val recyclerView = contentView.getViewById(R.id.recycler_view) as RecyclerView

        val lm = LinearLayoutManager(this)
        recyclerView.layoutManager = lm

        // 添加自定义分割线
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_line_divider)!!)
        recyclerView.addItemDecoration(divider)

        val list = mutableListOf(
                "不限",
                "1KM",
                "5KM",
                "10KM",
                "20KM",
                "30KM",
                "40KM",
                "50KM",
                "100KM",
                "1000KM"
        )
        val distance = mutableListOf(
                null,
                1,
                5,
                10,
                20,
                30,
                40,
                50,
                100,
                1000
        )
        val distanceAdapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_selector, list) {
            override fun convert(holder: BaseViewHolder, item: String) {
                val tv = holder.getView<TextView>(R.id.select_text)
                tv.text = item
                holder.getView<TextView>(R.id.select_text).isSelected =
                        distanceSelectedPosition == holder.adapterPosition
                holder.getView<TextView>(R.id.select_text).setOnClickListener {
                    distanceSelectedPosition = holder.adapterPosition
                    distinct = distance[holder.adapterPosition]
                    popupWindow.dismiss()
                    mDistanceTv.text = item
                    orderType = "distancePriority"
                    getScenicList()
                    notifyDataSetChanged()
                    page = 1
                }
            }
        }
        recyclerView.adapter = distanceAdapter
        popupWindow
    }

    private var distinct: Int? = null
    /**
     * 景区类型筛选
     */
    private fun getScenerySelectedType() {
        RetrofitHelper.getApiService()
                .getScenerySceletedType()
                .execute(object : DefaultObserver<ScenicSeceledEnty>() {
                    override fun onSuccess(response: BaseResponse<ScenicSeceledEnty>?) {
                        if (response?.data != null) {
                            orderTypeList.addAll(response.data.resourceLevel)
                            selectorAdapter?.notifyDataSetChanged()
                        }
                    }
                })
    }

    private fun setText() {
        if (selectCount == 0) {
            mNextTv.text = "已选 ,下一步"
        } else {
            mNextTv.text = "已选（${selectCount}）,下一步"
        }
    }

    private fun getScenicList() {
        RetrofitHelper.getApiService()
                .getIndexScenic(
                        page,
                        pageSize,
                        1,
                        distinct,
                        orderLevel,
                        mSearchEt.text.toString(),
                        orderType, SPUtils.getInstance().getString(SPCommon.LONGITUDE), SPUtils.getInstance().getString(SPCommon.LATITUDE)
                ).execute(object : DefaultObserver<IndexScenic>() {
                    override fun onSuccess(response: BaseResponse<IndexScenic>?) {
                        Utils.pageDeal(page, scenicData as ArrayList<*>, response, scenicAdapter, mViewAnimator)
                        if (response?.datas != null) {
                            scenicData.addAll(response.datas)
                            scenicAdapter.notifyDataSetChanged()
                        }
                        mSearchEt.setText("")
                    }
                })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mLevelChooseTv -> {
                levelSelectTypePop.showAsDropDown(mLevelChooseTv)
            }
            R.id.mDistanceTv -> {
                distanceTypePop.showAsDropDown(mDistanceTv)
            }
            R.id.mNextTv -> {
                if (scenicSelected.size == 0) {
                    ToastUtils.showShort("请选择景区")
                    return
                }
                val intent = Intent(this, ScenicConfirmActivity::class.java)
                intent.putExtra("data", scenicSelected as ArrayList)
                startActivity(intent)
            }
        }
    }

}