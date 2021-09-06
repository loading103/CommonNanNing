package com.daqsoft.commonnanning.ui.mine.online;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.MessageDetail;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.glide.GlideApp;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.agora.yview.photoview.PicturePreviewActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 留言详情页面
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018.05.07
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_ONLINE_MESSAGE_DETAILS)
public class MessageDetailActivity extends BaseActivity implements BaseQuickAdapter
        .OnItemClickListener {

    /**
     * 留言ID
     */
    @Autowired(name = "id")
    String id;
    @BindView(R.id.message_details_title)
    HeadView messageDetailsTitle;
    @BindView(R.id.msg_detail_title)
    TextView msgDetailTitle;
    @BindView(R.id.msg_detail_time)
    TextView msgDetailTime;
    @BindView(R.id.msg_detail_content)
    TextView msgDetailContent;
    @BindView(R.id.msg_detail_answer)
    TextView msgDetailAnswer;
    @BindView(R.id.msg_detail_img)
    RecyclerView msgDetailImg;
    @BindView(R.id.activity_message_detail)
    LinearLayout activityMessageDetail;
    /**
     * 留言详情对象
     */
    private MessageDetail messageDetail = null;


    @Override
    public int getLayoutId() {
        return R.layout.activity_message_detail;
    }

    /**
     * 初始化
     */
    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        messageDetailsTitle.setTitle("留言详情");
        getData();
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    /**
     * 根据ID获取留言详情
     */
    public void getData() {
        LoadingDialog.showDialogForLoading(this, "", true);
        RetrofitHelper.getApiService().getMsgDetails(id).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BaseResponse<MessageDetail>>() {
            @Override
            public void accept(BaseResponse<MessageDetail> response) throws Exception {
                LoadingDialog.cancelDialogForLoading();
                if (ObjectUtils.isNotEmpty(response)) {
                    if (response.getCode() == 0 && ObjectUtils.isNotEmpty(response.getData())) {
                        messageDetail = response.getData();
                        msgDetailTitle.setText(messageDetail.getTitle());
                        msgDetailTime.setText(messageDetail.getCreateTime());
                        msgDetailContent.setText(messageDetail.getContent());
                        msgDetailAnswer.setText(messageDetail.getClResult());
                        initImagesAdapter(messageDetail.getCredentials());

                    } else {
                        ToastUtils.showShortCenter(response.getMessage());
                    }
                } else {
                    ToastUtils.showShortCenter("操作异常");
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                LoadingDialog.cancelDialogForLoading();
                ToastUtils.showShortCenter("请求错误!");
            }
        });
    }


    /**
     * 图片适配器
     */
    public void initImagesAdapter(List<String> imgList) {
        msgDetailImg.setLayoutManager(new GridLayoutManager(this, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        BaseQuickAdapter adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout
                .rounded_imageview_layout, imgList) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                GlideApp.with(MessageDetailActivity.this).load(item).into((ImageView) helper
                        .getView(R.id.rounded_item_image));
                helper.setVisible(R.id.item_imgage_delete, false);
            }
        };
        adapter.setOnItemClickListener(this);
        msgDetailImg.setAdapter(adapter);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("currentPosition", position);
        bundle.putStringArrayList("imgList", (ArrayList<String>) messageDetail.getCredentials());
        Intent intent = new Intent(this, PicturePreviewActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
