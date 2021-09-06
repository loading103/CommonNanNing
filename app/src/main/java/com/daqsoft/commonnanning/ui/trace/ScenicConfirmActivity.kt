package com.daqsoft.commonnanning.ui.trace

import android.content.Intent
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.daqsoft.commonnanning.R
import com.daqsoft.commonnanning.ui.entity.IndexScenic
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter
import com.example.tomasyb.baselib.adapter.BaseViewHolder
import com.example.tomasyb.baselib.base.AppManager
import com.example.tomasyb.baselib.base.BaseActivity
import com.example.tomasyb.baselib.util.SizeUtils.dp2px
import com.example.tomasyb.baselib.util.ToastUtils
import com.library.flowlayout.FlowLayoutManager
import com.library.flowlayout.SpaceItemDecoration
import kotlinx.android.synthetic.main.activity_scenic_confirm.*
import kotlinx.android.synthetic.main.activity_scenic_confirm.mHeadView
import java.util.*

/**
 * 确认景区界面
 *
 * data:上个界面传过来的数据
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-9-12
 * @since JDK 1.8.0_191
 */
class ScenicConfirmActivity : BaseActivity(), View.OnClickListener {


    override fun getLayoutId(): Int = R.layout.activity_scenic_confirm

    private val datalist by lazy { intent.getParcelableArrayListExtra<IndexScenic>("data") }
    private val selectedList = mutableListOf<IndexScenic>()
    override fun initView() {
        mHeadView.setTitle("确认景区")
        mTotalTv.text = "您共选择了${datalist.size}个景区"
        selectedList.addAll(datalist)
        initAdapter()
        mNextTv.setOnClickListener(this)

    }

    private val adapter by lazy {
        object : BaseQuickAdapter<IndexScenic, BaseViewHolder>(R.layout.item_scenic_confirm_tag, datalist) {
            override fun convert(helper: BaseViewHolder?, item: IndexScenic?) {
                helper?.setText(R.id.mText, item?.name)
                if (helper?.adapterPosition == datalist.size - 1) {
                    helper.itemView.isSelected = true
                }
                helper?.itemView?.setOnClickListener {
                    if (helper.adapterPosition == datalist.size - 1) {
                        finish()
                        return@setOnClickListener
                    }
                    it.isSelected = !it.isSelected
                    if (it.isSelected) {
                        selectedList.remove(item)
                        mTotalTv.text = "您共选择了${selectedList.size}个景区"
                    } else {
                        selectedList.add(item!!)
                        mTotalTv.text = "您共选择了${selectedList.size}个景区"
                    }
                    notifyDataSetChanged()
                }
            }
        }
    }

    private fun initAdapter() {
        val index = IndexScenic()
        index.name = " + 添加"
        datalist.add(index)
        mRecyclerView.apply {
            layoutManager = FlowLayoutManager()
            addItemDecoration(SpaceItemDecoration(dp2px(5f)))
            adapter = this@ScenicConfirmActivity.adapter
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mNextTv -> {
                if(selectedList.size==0){
                    ToastUtils.showShort("请至少选择一个景区")
                    return
                }
                val intent = Intent(this, TraceIntroActivity::class.java)
                intent.putParcelableArrayListExtra("data", selectedList as ArrayList<out Parcelable>)
                startActivity(intent)
                AppManager.getAppManager().finishActivity(ScenicChooseActivity::class.java)
                finish()
            }
        }
    }

    override fun initData() {

    }
}