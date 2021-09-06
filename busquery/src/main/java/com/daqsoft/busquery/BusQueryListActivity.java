package com.daqsoft.busquery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ViewAnimator;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.daqsoft.busquery.bean.BusListEntity;
import com.daqsoft.busquery.buscommon.BusCommon;
import com.daqsoft.busquery.http.BusRetrofitHelper;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.LoadingDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 公交查询列表页面
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-6-4.15:50
 * @since JDK 1.8
 */
public class BusQueryListActivity extends BaseActivity {
    private HeadView mHeadView;
    private ViewAnimator mVa;
    private RecyclerView mRv;
    /**
     * 城市
     */
    private String mCity = "";
    /**
     * 我的位置
     */
    private String myLocation = "";
    /**
     * 目的地位置
     */
    private String muLocation = "";
    /**
     * json数据
     */
    private String mJson = "";
    /**
     * 目的地
     */
    private String stopName = "";
    /**
     * 起始地
     */
    private String startName = "";
    /**
     * 适配器
     */
    private BaseQuickAdapter<BusListEntity, BaseViewHolder> mAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_bus_query_list;
    }

    @Override
    public void initView() {
        findId();
        initData();
    }

    private void initData() {
        try {
            mCity = getIntent().getStringExtra("city");
            muLocation = getIntent().getStringExtra("muLocation");
            myLocation = getIntent().getStringExtra("meLocation");
            startName = getIntent().getStringExtra("meLocationName");
            stopName = getIntent().getStringExtra("muLocationName");
            getLuData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据
     */
    private void getLuData() {
        LoadingDialog.showDialogForLoading(this);
        BusRetrofitHelper.getApiService().getBusList(mCity,"",myLocation,muLocation)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        LoadingDialog.cancelDialogForLoading();
                        if (response.code() == 200) {
                            String value = null;
                            try {
                                value = response.body().string();
                                mJson = value.toString();
                                JSONObject jsonObject = JSONObject.parseObject(value.toString());
                                if (jsonObject.getString("code").equals("0")) {
                                    JSONObject dataObj = jsonObject.getJSONObject("data");
                                    JSONArray transitsArray = dataObj.getJSONArray("transits");
                                    if (transitsArray.size() > 0) {
                                        mVa.setDisplayedChild(0);
                                        List<BusListEntity> mDatas = new ArrayList<>();
                                        for (int i = 0; i < transitsArray.size(); i++) {
                                            BusListEntity bean = new BusListEntity();
                                            JSONObject obj = transitsArray.getJSONObject(i);
                                            String duration = obj.getString("duration");
                                            bean.setMinute(BusCommon.minutetofen(duration));
                                            bean.setDicetence("步行" + obj.getString("walking_distance") + "m");
                                            List<BusListEntity.BusBean> mList = new ArrayList<>();
                                            JSONArray segmentsArray = obj.getJSONArray("segments");
                                            if (segmentsArray.size() > 0) {
                                                for (int j = 0; j < segmentsArray.size(); j++) {
                                                    BusListEntity.BusBean busBean = new BusListEntity
                                                            .BusBean();
                                                    JSONObject segmentsObj = segmentsArray.getJSONObject(j);
                                                    JSONObject busObj = segmentsObj.getJSONObject("bus");
                                                    JSONArray buslinesArray = busObj.getJSONArray("buslines");
                                                    if (buslinesArray.size() > 0) {
                                                        JSONObject needObj = buslinesArray
                                                                .getJSONObject(0);
                                                        String name = needObj.getString("name");
                                                        String[] split = name.split("\\(");
                                                        busBean.setBusnum(split[0]);
                                                        mList.add(busBean);
                                                    }

                                                }
                                            }
                                            bean.setmDatas(mList);
                                            mDatas.add(bean);
                                        }
                                        mAdapter.setNewData(mDatas);
                                    } else {
                                        mVa.setDisplayedChild(1);
                                    }
                                } else {
                                    mVa.setDisplayedChild(1);
                                }
                            } catch (IOException e) {
                                mVa.setDisplayedChild(1);
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        LoadingDialog.cancelDialogForLoading();
                        mVa.setDisplayedChild(1);
                    }
                });
    }

    private void findId() {
        mVa = findViewById(R.id.buslist_va);
        mRv = findViewById(R.id.bus_xq_rv);
        mHeadView = (HeadView)findViewById(R.id.title);
        mHeadView.setTitle("查询结果");
        mHeadView.setBackListener(new HeadView.OnBackListener() {
            @Override
            public void onBack(View v) {
                finish();
            }
        });
        initAdapter();
    }

    private void initAdapter() {
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BaseQuickAdapter<BusListEntity, BaseViewHolder>(R.layout.item_bussearch_list, null) {
            @Override
            protected void convert(BaseViewHolder helper, final BusListEntity item) {
                helper.setText(R.id.tv_minutes, item.getMinute());
                helper.setText(R.id.tv_distence, item.getDicetence());
                RecyclerView rv = (RecyclerView) helper.getView(R.id.rv_bus);
                rv.setLayoutManager(new GridLayoutManager(BusQueryListActivity.this, 3));
                rv.setAdapter(new BaseQuickAdapter<BusListEntity.BusBean, BaseViewHolder>(R.layout.item_txt_gray,
                        item.getmDatas()) {

                    @Override
                    protected void convert(BaseViewHolder helper, BusListEntity.BusBean item1) {
                        helper.setText(R.id.tv_busnum, item1.getBusnum());
                        if (helper.getPosition() == item.getmDatas().size() - 1) {
                            helper.setVisible(R.id.img_more, false);
                        }
                    }
                });
            }
        };
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("itempostion", position);
                bundle.putString("json", mJson);
                bundle.putString("meLocationName", startName);
                bundle.putString("muLocationName", stopName);
                if (ObjectUtils.isNotEmpty(mJson)) {
                    Intent intent = new Intent(BusQueryListActivity.this, BusQueryDetailActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        mRv.setAdapter(mAdapter);
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }
}
