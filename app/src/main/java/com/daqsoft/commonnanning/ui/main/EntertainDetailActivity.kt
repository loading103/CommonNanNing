package com.daqsoft.commonnanning.ui.main

import com.daqsoft.commonnanning.R
import com.daqsoft.commonnanning.common.ComUtils
import com.daqsoft.commonnanning.http.RetrofitHelper
import com.daqsoft.commonnanning.ui.entity.EntertainmentDetailBean
import com.daqsoft.commonnanning.utils.GlideUtils
import com.example.tomasyb.baselib.base.mvp.BaseActivity
import com.example.tomasyb.baselib.base.mvp.IBasePresenter
import com.example.tomasyb.baselib.base.net.common.DefaultObserver
import com.example.tomasyb.baselib.base.net.entity.BaseResponse
import com.example.tomasyb.baselib.base.net.execute
import kotlinx.android.synthetic.main.activity_entertain_detail.*

/**
 * 娱乐详情Activity
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-7-8
 * @since JDK 1.8.0_191
 */
class EntertainDetailActivity : BaseActivity<IBasePresenter>() {
    override fun getLayoutId(): Int = R.layout.activity_entertain_detail

    private val id by lazy { intent.getLongExtra("id", 0) }
    override fun initView() {
        enterTainDetail()
        mHeadView.setTitle("娱乐详情")
    }

    override fun initPresenter(): IBasePresenter? {
        return null
    }


    /**
     * 娱乐详情
     */
    fun enterTainDetail() {
        RetrofitHelper.getApiService()
                .recreationDetai(id)
                .execute(object : DefaultObserver<EntertainmentDetailBean>() {
                    override fun onSuccess(response: BaseResponse<EntertainmentDetailBean>?) {
                        GlideUtils.loadImage(
                                this@EntertainDetailActivity,
                                mCoverIv,
                                response?.data?.cover,
                                R.mipmap.common_img_fail_h300)
                        mTitleTv.text = response?.data?.name
                        if (!response?.data?.introduce.isNullOrEmpty()) {
                            mWebView.loadDataWithBaseURL(
                                    null,
                                    ComUtils.getNewContent(response?.data?.introduce),
                                    "text/html",
                                    "utf-8",
                                    null)
                        } else {
                            mWebView.loadDataWithBaseURL(
                                    null,
                                    "暂无数据",
                                    "text/html",
                                    "utf-8",
                                    null)
                        }


                    }
                })
    }
}