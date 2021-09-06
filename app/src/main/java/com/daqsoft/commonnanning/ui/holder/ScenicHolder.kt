package com.daqsoft.commonnanning.ui.holder

import android.content.Intent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.daqsoft.commonnanning.R
import com.daqsoft.commonnanning.common.SPCommon
import com.daqsoft.commonnanning.helps_gdmap.MapUtils
import com.daqsoft.commonnanning.ui.entity.IndexScenic
import com.daqsoft.commonnanning.ui.main.ScenicDetailActivity
import com.example.tomasyb.baselib.util.SPUtils
import io.agora.yview.banner.holder.Holder

/**
 * @author 周俊蒙
 * @version 1.0.0
 * @date 2019-5-27
 * @since JDK 1.8.0_191
 */
class ScenicHolder(itemView: View?) : Holder<IndexScenic>(itemView) {
    var mRecommendScenicTv: TextView? = null
    var mDistanceTv: TextView? = null
    var mRootLl: LinearLayout? = null
    override fun updateUI(data: IndexScenic?) {
        mRecommendScenicTv?.text = data?.name
        if (SPUtils.getInstance().getString(SPCommon.LATITUDE).isEmpty() || SPUtils.getInstance().getString(SPCommon
                        .LONGITUDE).isEmpty()) {
            return
        }
        val distance = MapUtils.calculateLineDistance(
                data?.latitude,
                data?.longitude,
                SPUtils.getInstance().getString(SPCommon.LATITUDE),
                SPUtils.getInstance().getString(SPCommon.LONGITUDE)
        )
        mDistanceTv?.text = "距您${distance}"
    }

    override fun initView(itemView: View?) {
        mRecommendScenicTv = itemView?.findViewById(R.id.mRecommendScenicTv)
        mDistanceTv = itemView?.findViewById(R.id.mDistanceTv)
        mRootLl = itemView?.findViewById(R.id.mRootLl)
    }

}