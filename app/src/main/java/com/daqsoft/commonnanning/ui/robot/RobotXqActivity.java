package com.daqsoft.commonnanning.ui.robot;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.daqsoft.commonnanning.R;
import com.example.tomasyb.baselib.base.glide.GlideApp;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.widget.HeadView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import butterknife.BindView;


/**
 * 机器人详情原生方法
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/3/19.
 * @since JDK 1.8
 */
public class RobotXqActivity extends BaseActivity {
    @BindView(R.id.robot_web)
    WebView robotWeb;
    @BindView(R.id.img_top)
    ImageView mImgTop;
    @BindView(R.id.robot_xq_title)
    HeadView robotXqTitle;
    /**
     * 类型
     */
    private int type = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_robot_xq;
    }

    @Override
    public void initView() {
        robotXqTitle.setTitle("详情");
        try {
            type = getIntent().getIntExtra("showtype", 1);
            String content = getIntent().getStringExtra("content");
            if (type == 0) {
                mImgTop.setVisibility(View.VISIBLE);
                String img = getIntent().getStringExtra("contentimg");
                GlideApp.with(this)
                        .load(img)
                        .placeholder(R.mipmap.robot_headimage)
                        .error(R.mipmap.robot_headimage)
                        .into(mImgTop);
            } else {
                mImgTop.setVisibility(View.GONE);
            }
            robotWeb.loadDataWithBaseURL(null, getNewContent(content),
                    "text/html", "UTF-8", null);
            WebSettings webSettings = robotWeb.getSettings();
            // 把html中的内容放大webview等宽的一列中
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }


    public String getNewContent(String htmltext) {
        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
        }
        return doc.toString();
    }

}
