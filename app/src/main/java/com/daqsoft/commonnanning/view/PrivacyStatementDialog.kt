package com.daqsoft.commonnanning.view

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.Window
import com.alibaba.android.arouter.launcher.ARouter
import com.daqsoft.android.ProjectConfig
import com.daqsoft.commonnanning.R
import com.daqsoft.commonnanning.base.BaseWebActivity
import com.daqsoft.commonnanning.common.Constant
import com.daqsoft.event.UndateFinishEvent
import kotlinx.android.synthetic.main.layout_privacy_statement_dialog.*
import org.greenrobot.eventbus.EventBus

/**
 * @Description
 * @ClassName   PrivacyStatementDialog
 * @Author      luoyi
 * @Time        2020/5/15 13:58
 */
class PrivacyStatementDialog : AlertDialog {
    var mContext: Context? = null


    constructor(context: Context?) : super(context, R.style.PrivacyStatementDialog) {
        mContext = context
        init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_privacy_statement_dialog)
        initView()
        val dialogWindow: Window = this.window
        dialogWindow.setWindowAnimations(R.style.dialog_zoom)
    }


    private fun initView() {


        tv_confirm.setOnClickListener {
            dismiss()
            EventBus.getDefault().post(UndateFinishEvent(true))
        }

        tv_confirm_no.setOnClickListener {
            dismiss()
            EventBus.getDefault().post(UndateFinishEvent(false))
        }
        var txtStatement = "您可以查看完整版"
        var txtUserMent = "用户协议"
        var txtPrivacy = "隐私政策"
        var spannaerStr = SpannableStringBuilder(txtStatement + txtUserMent + "及" + txtPrivacy)
        var start = txtStatement.length
        var end1 = txtStatement.length + txtUserMent.length
        spannaerStr.setSpan(
                ForegroundColorSpan(mContext!!.resources.getColor(R.color.colorPrimary)), start, end1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        // 用户协议
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View?) {
                // 用户注册协议
                if (ProjectConfig.CITY_NAME == "西藏") {
                    val intent = Intent(context, BaseWebActivity::class.java)
                    intent.putExtra("HTMLURL", ProjectConfig.USER_PATH)
                    context.startActivity(intent)
                }else{
                    ARouter.getInstance().build(Constant.ACTIVITY_CONTENT_WEB).withInt("pageType", 1).navigation()

                }
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }
        }
        spannaerStr.setSpan(
                clickableSpan, txtStatement.length, end1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        var start2 = end1 + 1
        spannaerStr.setSpan(
                ForegroundColorSpan(mContext!!.resources.getColor(R.color.colorPrimary)), start2, start2 + txtPrivacy.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        // 隐私协议
        val clickableSpan2: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View?) {
                // 隐私协议
                val intent = Intent(context, BaseWebActivity::class.java)
                intent.putExtra("HTMLURL", ProjectConfig.PRIVATER_PATH)
                context.startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }
        }
        spannaerStr.setSpan(
                clickableSpan2, start2, start2 + txtPrivacy.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tv_privacy_statement_info.text = spannaerStr
        tv_privacy_statement_info.movementMethod = LinkMovementMethod.getInstance();
    }

    private fun init() {
        setCancelable(false)
    }

    override fun show() {
        try {
            super.show()
        } catch (e: Exception) {
        }
    }


}