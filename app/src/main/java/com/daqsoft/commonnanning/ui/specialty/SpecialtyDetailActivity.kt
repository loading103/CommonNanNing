package com.daqsoft.commonnanning.ui.specialty

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import com.alibaba.android.arouter.launcher.ARouter
import com.daqsoft.commonnanning.R
import com.daqsoft.commonnanning.common.ComUtils
import com.daqsoft.commonnanning.common.Constant
import com.daqsoft.commonnanning.common.SourceType
import com.daqsoft.commonnanning.http.RetrofitHelper
import com.daqsoft.commonnanning.ui.entity.CommentEntity
import com.daqsoft.commonnanning.ui.entity.SpecialDetailBean
import com.daqsoft.commonnanning.ui.main.CommentMoreActivity
import com.daqsoft.commonnanning.ui.main.WriteCommentActivity
import com.daqsoft.commonnanning.utils.GlideUtils
import com.daqsoft.commonnanning.utils.NetWorkUtils
import com.daqsoft.utils.Utils
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter
import com.example.tomasyb.baselib.adapter.BaseViewHolder
import com.example.tomasyb.baselib.base.BaseActivity
import com.example.tomasyb.baselib.base.glide.GlideApp
import com.example.tomasyb.baselib.base.net.common.DefaultObserver
import com.example.tomasyb.baselib.base.net.entity.BaseResponse
import com.example.tomasyb.baselib.base.net.execute
import com.example.tomasyb.baselib.util.LogUtils
import com.example.tomasyb.baselib.util.SPUtils
import com.example.tomasyb.baselib.util.ToastUtils
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_specialty_detail.*
import java.util.*

/**
 * 特产详情
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-5-24
 * @since JDK 1.8.0_191
 */
class SpecialtyDetailActivity : BaseActivity(), View.OnClickListener {


    override fun getLayoutId(): Int = R.layout.activity_specialty_detail
    private val id by lazy { intent.getStringExtra("id") }
    private val data = arrayListOf<CommentEntity.DatasBean>()
    private var page = 1
    private var pageSize = 10
    override fun initView() {
        route_help_title.setTitle("特产详情")
        webView.settings.javaScriptEnabled = true
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webView.settings.defaultTextEncodingName = "UTF-8"
        webView.settings.blockNetworkImage = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //webView.settings.mixedContentMode = webView.settings.MIXED_CONTENT_ALWAYS_ALLOW  //注意安卓5.0以上的权限
        }
        mSmartRefreshLayout.setOnRefreshListener {
            initData()
            mSmartRefreshLayout.finishRefresh(500)
        }
        recycler_view2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_view2.adapter = adapter
        more_comment.setOnClickListener(this)
        like.setOnClickListener(this)
        comment.setOnClickListener(this)
        collection.setOnClickListener(this)
    }

    private val adapter = object : BaseQuickAdapter<CommentEntity.DatasBean, BaseViewHolder>(
            R.layout.item_comment, data) {
        override fun convert(helper: BaseViewHolder?, item: CommentEntity.DatasBean?) {
            GlideApp.with(mContext)
                    .load(item?.getHeadPath())
                    .placeholder(R.mipmap.common_img_fail_h158)
                    .error(R.mipmap.common_img_fail_h158)
                    .centerCrop()
                    .into(helper?.getView(R.id.iv_head_portrait) as ImageView)
            // 评论item载入数据
            helper.setText(R.id.tv_comment_user_name, item?.name)
            val ratingbar = helper.getView<RatingBar>(R.id.ratingbar)
            ratingbar.rating = item?.getStar()?.toFloat() ?: 0f
            helper.setText(R.id.tv_comment, item?.comment)
            helper.setText(R.id.tv_comment_time, item?.time)
            val strings = ArrayList<String>()
            if (Utils.isnotNull(item?.pics)) {
                val split = item?.pics?.split(",")
                if (split?.get(0) != "") {
                    strings.addAll(split!!)
                }
            }
            if (strings.size > 0) {
                LogUtils.e("datasBean.getPics(): " + strings.size)
                val imageAdapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_resource_imgae, strings) {
                    override fun convert(helper: BaseViewHolder, item: String) {
                        GlideApp.with(mContext).load(item)
                                .placeholder(R.mipmap.common_img_fail_h158)
                                .error(R.mipmap.common_img_fail_h158)
                                .centerCrop()
                                .into(helper.getView<View>(R.id.iv_item_resource_img) as ImageView)
                    }
                }
                val recyclerView = helper.getView<RecyclerView>(R.id.recyclerView_item_comment_img)
                val layoutManager = LinearLayoutManager(mContext)
                layoutManager.orientation = LinearLayoutManager.HORIZONTAL
                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = imageAdapter
            }
        }
    }

    override fun initData() {
        specialtyDetail()
        getComment()
    }

    private var detail: SpecialDetailBean? = null
    private var mSumContent : String ? = null
    fun specialtyDetail() {
        RetrofitHelper.getApiService()
                .specialDetail(id)
                .execute(object : DefaultObserver<SpecialDetailBean>() {
                    override fun onSuccess(response: BaseResponse<SpecialDetailBean>?) {
                        detail = response?.data
                        if (response?.data?.vo?.thumb==1){
                            like.isSelected = true
                        }else{
                            like.isSelected = false
                        }
                        if (response?.data?.vo?.enshrine==1){
                            collection.isSelected = true
                        }else{
                            collection.isSelected = false
                        }
                        GlideUtils.loadImage(
                                this@SpecialtyDetailActivity,
                                image,
                                response?.data?.coverTwoToOne,
                                R.mipmap.common_img_fail_h300
                        )
                        mSumContent = response?.data?.introduce
                        name.text = response?.data?.name ?: ""
                        webView.loadDataWithBaseURL(null, ComUtils.getNewContent(response?.data?.introduce),
                                "text/html", "UTF-8", null)
                    }
                })
    }

    fun getComment() {
        RetrofitHelper.getApiService()
                .getComment(
                        id,
                        "sourceType_9",
                        page.toString(),
                        pageSize.toString()
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : Observer<CommentEntity> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: CommentEntity) {
                        data.addAll(t.datas)
                        if (data.size == 0) {
                            mCommentLl.visibility = View.GONE
                        } else {
                            mCommentLl.visibility = View.VISIBLE
                            comment_title.text = "来自${data.size}位游客点评"
                            adapter.notifyDataSetChanged()
                        }

                    }

                    override fun onError(e: Throwable) {
                    }

                })

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.more_comment -> {
                // 更多评论
                if (detail == null) {
                    return
                }
                if (Utils.isnotNull(SPUtils.getInstance().getString("token"))) {
                    val intent = Intent(this, CommentMoreActivity::class.java)
                    intent.putExtra("name", detail?.name)
                    intent.putExtra("id", id)
                    intent.putExtra("type", SourceType.RESOURCE_SPECIALTY)
                    intent.putExtra("TYPE", "0")
                    startActivity(intent)
                } else {
                    goToLoginActivity()
                }
            }
            R.id.like -> {
                // 点赞
                if (Utils.isnotNull(SPUtils.getInstance().getString("token"))) {
                    // 点赞
                    if (like.isSelected) {
                        NetWorkUtils.deleteThumb(id, like, mContext as Activity)
                    } else {
                        NetWorkUtils.saveThumb(id, SourceType.RESOURCE_SPECIALTY, detail?.name, "", like,
                                mContext as Activity)
                    }
                } else {
                    goToLoginActivity()
                }
            }
            R.id.comment -> {
                // 评论
                if (Utils.isnotNull(SPUtils.getInstance().getString("token"))) {
                    val intent = Intent(this, WriteCommentActivity::class.java)
                    intent.putExtra("name", detail?.name)
                    intent.putExtra("id", id)
                    intent.putExtra("type", SourceType.RESOURCE_SPECIALTY)
                    intent.putExtra("TYPE", "0")
                    startActivityForResult(intent, 100)
                } else {
                    goToLoginActivity()
                }
            }
            R.id.collection -> {
                // 收藏
                if (Utils.isnotNull(SPUtils.getInstance().getString("token"))) {
                    // 收藏
                    if (collection.isSelected) {
                        NetWorkUtils.delCollection(id, collection, mContext as Activity)
                    } else {
                        val subSequence = mSumContent?.subSequence(0, 10)
                        NetWorkUtils.saveCollection(id, SourceType.RESOURCE_SPECIALTY, detail?.name, subSequence as String?, collection, mContext as Activity)
                    }
                } else {
                    goToLoginActivity()
                }
            }
        }
    }

    fun goToLoginActivity() {
        ToastUtils.showShortCenter("请先登录")
        ARouter.getInstance().build(Constant.ACTIVITY_LOGIN).navigation(this, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == 0) {
            getComment()
        }
    }
}