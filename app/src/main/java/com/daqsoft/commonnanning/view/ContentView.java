package com.daqsoft.commonnanning.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.ComUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.web.ProgressWebView;


/**
 * 富文本查看跟多
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-5-8.10:35
 * @since JDK 1.8
 */

public class ContentView extends FrameLayout implements View.OnClickListener{
    private ProgressWebView pWebview;
    private ImageView mImgMore;
    private TextView tv_content;
    private ViewAnimator viewanimator;
    private boolean isOpen = false;

    public ContentView(Context context) {
        this(context,null);
    }

    public ContentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public ContentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化
     */
    private void init(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_content_expandview,null);
        pWebview = (ProgressWebView)view.findViewById(R.id.webView);
        mImgMore = (ImageView) view.findViewById(R.id.img_more);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        viewanimator = (ViewAnimator) view.findViewById(R.id.viewanimator);
        mImgMore.setOnClickListener(this);
        this.addView(view,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.WRAP_CONTENT));

    }


    /**
     * 设置数据
     * @return
     */
    public void setContent(String content,String subm){
        viewanimator.setDisplayedChild(0);
        pWebview.loadData(ComUtils.getNewContent(content),
                ComUtils.WEBVIEW_TYPE, null);
        tv_content.setText(subm);
    }


    @Override
    public void onClick(View view) {
        if (!isOpen){
            viewanimator.setDisplayedChild(1);
            isOpen = true;
            mImgMore.setImageResource(R.mipmap.complaint_details_arrow_up_normal);
        }else {
            viewanimator.setDisplayedChild(0);
            mImgMore.setImageResource(R.mipmap.complaint_details_arrow_down_normal);
            isOpen = false;
        }
    }

    /**
     * 添加监听
     */
    private void initListener() {

    }
}
