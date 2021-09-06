package com.daqsoft.commonnanning.ui.trace

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.daqsoft.commonnanning.R
import com.daqsoft.commonnanning.http.RetrofitHelper
import com.daqsoft.commonnanning.ui.entity.IndexScenic
import com.daqsoft.commonnanning.ui.mine.LoginActivity
import com.daqsoft.commonnanning.utils.GlideUtils
import com.example.tomasyb.baselib.base.BaseActivity
import com.example.tomasyb.baselib.base.net.common.DefaultObserver
import com.example.tomasyb.baselib.base.net.entity.BaseResponse
import com.example.tomasyb.baselib.base.net.execute
import com.zhouwei.mzbanner.MZBannerView
import com.zhouwei.mzbanner.holder.MZHolderCreator
import com.zhouwei.mzbanner.holder.MZViewHolder
import kotlinx.android.synthetic.main.activity_trace_intro.*

/**
 * 赐名并生产足迹
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-9-12
 * @since JDK 1.8.0_191
 */
class TraceIntroActivity : BaseActivity(), View.OnClickListener {


    override fun getLayoutId(): Int = R.layout.activity_trace_intro

    private val data by lazy { intent.getParcelableArrayListExtra<IndexScenic>("data") }
    private val id by lazy {
        val sb = StringBuilder()
        for (i in (0 until data.size)) {
            if (i == data.size - 1) {
                sb.append(data[i].id)
            } else {
                sb.append(data[i].id).append(",")
            }
        }
        sb
    }

    override fun initView() {
        mHeadView.setTitle("赐名并生产足迹")
        (mBanner as MZBannerView<IndexScenic>).setPages(data, MZHolderCreator<BannerViewHolder> {
            return@MZHolderCreator BannerViewHolder()
        })
        mCompleteTv.setOnClickListener(this)

        mIntroEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                mCountTv.text = "${s?.length}/500"
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

    override fun initData() {

    }

    companion object {
        class BannerViewHolder : MZViewHolder<IndexScenic> {
            var imageView: ImageView? = null
            var textView: TextView? = null
            override fun createView(p0: Context?): View {
                val view = LayoutInflater.from(p0).inflate(R.layout.banner_scenic, null)
                imageView = view?.findViewById(R.id.mCoverIv)
                textView = view.findViewById(R.id.mText)
                return view
            }

            override fun onBind(p0: Context?, p1: Int, p2: IndexScenic?) {
                GlideUtils.loadImage(
                        p0,
                        imageView,
                        p2?.picture,
                        R.mipmap.common_img_fail_h300
                )
                textView?.text = p2?.name
            }

        }
    }

    private fun save() {
        RetrofitHelper.getApiService()
                .touristSave(
                        mIntroEt.text.toString(),
                        mTitleEt.text.toString(),
                        id.toString(),
                        data.get(0).picture
                )
                .execute(object : DefaultObserver<Any>() {
                    override fun onSuccess(response: BaseResponse<Any>?) {
                        finish()
                    }

                    override fun onError(response: BaseResponse<Any>?) {
                        super.onError(response)
                        if (response?.code == 2) {
                            startActivity(Intent(this@TraceIntroActivity, LoginActivity::class.java))
                        }
                    }
                })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mCompleteTv -> {
                save()
            }
        }
    }
}