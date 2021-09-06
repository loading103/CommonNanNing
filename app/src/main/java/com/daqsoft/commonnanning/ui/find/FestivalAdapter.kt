package com.daqsoft.commonnanning.ui.find

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daqsoft.commonnanning.R
import com.daqsoft.commonnanning.common.ParamsCommon
import com.daqsoft.commonnanning.ui.entity.IndexActivity
import com.daqsoft.commonnanning.ui.service.news.NewsWebActivity
import com.daqsoft.commonnanning.utils.GlideUtils
import io.agora.yview.photoview.PicturePreviewActivity
import kotlinx.android.synthetic.main.item_find_content_type1.view.*
import kotlinx.android.synthetic.main.item_find_content_type2.view.*

/**
 * 节庆活动的RecyclerViewAdapter
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-5-22
 * @since JDK 1.8.0_191
 */
class FestivalAdapter(private val ctx: Context?,
                      private val data: ArrayList<IndexActivity>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * 第一种布局类型，对应R.layout.item_find_content_type1，当获取的图片为单张时显示此布局
     */
    private val ITEM_TYPE_1 = 1
    /**
     * 第二种布局类型，对应R.layout.item_find_content_type2，当获取图片为多张时显示此布局
     */
    private val ITEM_TYPE_2 = 2

    private val imgs = arrayListOf<String>()
    /**
     * 创建ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ITEM_TYPE_1) {
            return Type1VH(LayoutInflater.from(ctx).inflate(R.layout.item_find_content_type1, parent, false))
        } else {
            return Type2VH(LayoutInflater.from(ctx).inflate(R.layout.item_find_content_type2, parent, false))
        }
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun getItemViewType(position: Int): Int {
        if (data?.get(position)?.imgsPics?.size ?: 0 > 1) {
            return ITEM_TYPE_2
        } else {
            return ITEM_TYPE_1
        }
    }

    /**
     * 第一种布局的ViewHolder
     */
    class Type1VH(itemView: View?) : RecyclerView.ViewHolder(itemView)

    /**
     * 第二种布局的ViewHolder
     */
    class Type2VH(itemView: View?) : RecyclerView.ViewHolder(itemView)

    /**
     * 绑定数据
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is Type1VH) {
            holder.itemView.mTitleTv.text = data?.get(position)?.title ?: ""
            holder.itemView.mIntroTv.text = data?.get(position)?.theme ?: ""
            holder.itemView.mTimeTv.text = data?.get(position)?.createTime ?: ""
            holder.itemView.mViewTv.text = "${data?.get(position)?.viewCount}"
            GlideUtils.loadRoundImage(
                    ctx,
                    holder.itemView.mCoverIv,
                    data?.get(position)?.coverOneToOne,
                    R.mipmap.common_img_fail_h300
            )
            holder.itemView.setOnClickListener {
                // 节会活动详情
                val intent = Intent(ctx, NewsWebActivity::class.java)
                intent.putExtra("id", data?.get(position)?.id.toString())
                intent.putExtra("title", data?.get(position)?.title ?: "")
                intent.putExtra("code", ParamsCommon.ACTIVITY_CHANELCODE)
                ctx?.startActivity(intent)
            }
        }
        if (holder is Type2VH) {
            holder.itemView.mType2TitleTv.text = data?.get(position)?.title ?: ""
            holder.itemView.mType2IntroTv.text = data?.get(position)?.title ?: ""
            if (data?.get(position)?.imgsPics?.size ?: 0 > 2) {
                GlideUtils.loadImage(
                        ctx,
                        holder.itemView.mCover1Iv,
                        data?.get(position)?.imgsPics?.get(0)?.imgPath,
                        R.mipmap.common_img_fail_h300
                )
                GlideUtils.loadImage(
                        ctx,
                        holder.itemView.mCover2Iv,
                        data?.get(position)?.imgsPics?.get(1)?.imgPath,
                        R.mipmap.common_img_fail_h300
                )
                GlideUtils.loadImage(
                        ctx,
                        holder.itemView.mCover3Iv,
                        data?.get(position)?.imgsPics?.get(2)?.imgPath,
                        R.mipmap.common_img_fail_h300
                )
            } else {
                GlideUtils.loadImage(
                        ctx,
                        holder.itemView.mCover1Iv,
                        data?.get(position)?.imgsPics?.get(0)?.imgPath,
                        R.mipmap.common_img_fail_h300
                )
                GlideUtils.loadImage(
                        ctx,
                        holder.itemView.mCover2Iv,
                        data?.get(position)?.imgsPics?.get(1)?.imgPath,
                        R.mipmap.common_img_fail_h300
                )
            }
            holder.itemView.setOnClickListener {
                // 节会活动详情
                val intent = Intent(ctx, NewsWebActivity::class.java)
                intent.putExtra("id", data?.get(position)?.id.toString())
                intent.putExtra("title", data?.get(position)?.title ?: "")
                intent.putExtra("code", ParamsCommon.NEWS)
                ctx?.startActivity(intent)
            }
            holder.itemView.mCover1Iv.setOnClickListener {
                val intent = Intent(ctx, PicturePreviewActivity::class.java)
                imgs.clear()
                data?.get(position)?.imgsPics?.forEach {
                    imgs.add(it.imgPath)
                }
                intent.putExtra("imgList", imgs)
                intent.putExtra("currentPosition", 0)
                ctx?.startActivity(intent)
            }
            holder.itemView.mCover2Iv.setOnClickListener {
                val intent = Intent(ctx, PicturePreviewActivity::class.java)
                imgs.clear()
                data?.get(position)?.imgsPics?.forEach {
                    imgs.add(it.imgPath)
                }
                intent.putExtra("imgList", imgs)
                intent.putExtra("currentPosition", 1)
                ctx?.startActivity(intent)
            }
            holder.itemView.mCover3Iv.setOnClickListener {
                val intent = Intent(ctx, PicturePreviewActivity::class.java)
                imgs.clear()
                data?.get(position)?.imgsPics?.forEach {
                    imgs.add(it.imgPath)
                }
                intent.putExtra("imgList", imgs)
                intent.putExtra("currentPosition", 2)
                ctx?.startActivity(intent)
            }
        }
        if (position == itemCount - 1) {
            loadMore?.invoke()
        }
    }

    /**
     * 加载更多回调
     */
    private var loadMore: (() -> Unit)? = null

    fun setLoadMoreListener(loadMoreListener: () -> Unit) {
        loadMore = loadMoreListener
    }

}