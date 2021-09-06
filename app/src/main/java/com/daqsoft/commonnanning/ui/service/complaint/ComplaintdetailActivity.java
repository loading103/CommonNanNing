package com.daqsoft.commonnanning.ui.service.complaint;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.daqsoft.commonnanning.ui.entity.ComplaintDetail;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.glide.GlideApp;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.net.common.DefaultObserver;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.PhoneUtils;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.agora.yview.photoview.PicturePreviewActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 投诉详情
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-3-22.
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_COMPLAINT_DETAILS)
public class ComplaintdetailActivity extends BaseActivity implements BaseQuickAdapter
        .OnItemClickListener {
    @BindView(R.id.headView)
    HeadView headView;
    @BindView(R.id.txt_name)
    TextView txt_name;
    @BindView(R.id.txt_sex)
    TextView txt_sex;
    @BindView(R.id.txt_phone)
    TextView txt_phone;
    @BindView(R.id.txt_to_complain)
    TextView txt_to_complain;
    @BindView(R.id.txt_complaint_content)
    TextView txt_complaint_content;
    @BindView(R.id.txt_complaint_time)
    TextView txt_complaint_time;
    @BindView(R.id.ll_complaint_down_up)
    LinearLayout ll_complaint_down_up;
    @BindView(R.id.txt_open)
    TextView txt_open;
    @BindView(R.id.txt_code)
    TextView txt_code;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_details_down_up)
    ImageView iv_details_down_up;
    @BindView(R.id.ll_complaint_result)
    LinearLayout ll_complaint_result;
    @BindView(R.id.tv_result_time)
    TextView tv_result_time;
    @BindView(R.id.txt_result)
    TextView txt_result;
    @BindView(R.id.ll_complaint_consult_phone)
    LinearLayout ll_consult_phone;
    @BindView(R.id.tv_consult_phone)
    TextView txt_consult_phone;
    @BindView(R.id.tv_complaint_status)
    TextView tv_status;
    @BindView(R.id.complaint_code)
    TextView complaintCode;
    @BindView(R.id.iv_consult_call_phone)
    ImageView ivConsultCallPhone;

    /**
     * 投诉事件的图片的适配器
     */
    private BaseQuickAdapter adapter;
    /**
     * 投诉事件详情数据对象
     */
    private ComplaintDetail bean;
    /**
     * 投诉ID
     */
    @Autowired(name = "id")
    String complaintId = "";
    /**
     * 图片集合
     */
    ArrayList<String> imgList = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_complaintdetail;
    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        headView.setTitle("投诉详情");
        initRecycler();
        request(complaintId);

    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    /**
     * 进入图片的初始化
     */
    public void initRecycler() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout
                .rounded_imageview_layout, imgList) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                GlideApp.with(ComplaintdetailActivity.this)
                        .load(item)
                        .into((ImageView) helper.getView(R.id.rounded_item_image));
                helper.setVisible(R.id.item_imgage_delete, false);
            }
        };
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }


    @OnClick({R.id.iv_details_down_up, R.id.iv_consult_call_phone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_details_down_up:
                // 已经是显示，点击则隐藏
                if (ll_complaint_down_up.getVisibility() == View.VISIBLE) {
                    iv_details_down_up.setImageResource(R.mipmap
                            .complaint_details_arrow_down_normal);
                    ll_complaint_down_up.setVisibility(View.GONE);
                } else {
                    iv_details_down_up.setImageResource(R.mipmap.complaint_details_arrow_up_normal);
                    ll_complaint_down_up.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.iv_consult_call_phone:
                // 拨打电话
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) !=
                        PackageManager
                                .PERMISSION_GRANTED) {
                    return;
                }
                PhoneUtils.call("");
                break;
            default:

                break;
        }

    }

    /**
     * 数据请求投诉详情
     *
     * @param id 投诉的事件ID
     */
    private void request(String id) {
        LoadingDialog.showDialogForLoading(this, "", true);
        RetrofitHelper.getApiService()
                .getComplainDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<ComplaintDetail>() {
                    @Override
                    public void onSuccess(BaseResponse<ComplaintDetail> response) {
                        if (ObjectUtils.isNotEmpty(response)
                                && response.getCode() == 0) {
                            bean = response.getData();
                            txt_name.setText(bean.getName());
                            txt_sex.setText(bean.getSex() == 0 ? "男" : bean.getSex() == 1 ? "女" :
                                    "保密");
                            txt_to_complain.setText(bean.getUnit());
                            txt_complaint_content.setText(bean.getReason());
                            txt_complaint_time.setText(bean.getClTime());
                            txt_open.setText(bean.isOpen() == 1 ? "是" : "否");
                            txt_code.setText(bean.getCodes());
                            complaintCode.setText(bean.getCodes());
                            if (!TextUtils.isEmpty(bean.getCredentials())) {
                                String[] values = bean.getCredentials().split(",");
                                if (ObjectUtils.isNotEmpty(values)
                                        && values.length > 0) {
                                    imgList.addAll(Arrays.asList(values));
                                    adapter.notifyDataSetChanged();
                                }
                            }
                            txt_result.setText(bean.getClResult());
                            tv_status.setText(bean.getTsState() == 0 ? "未处理" : "已处理");
                            if (0 == bean.getTsState()) {
                                ll_complaint_result.setVisibility(View.GONE);
                            } else {
                                ll_complaint_result.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        LoadingDialog.cancelDialogForLoading();
                    }
                });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("currentPosition", position);
        bundle.putStringArrayList("imgList", imgList);
        Intent intent = new Intent(this, PicturePreviewActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
