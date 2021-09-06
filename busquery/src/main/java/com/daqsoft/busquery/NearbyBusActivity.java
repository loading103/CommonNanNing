package com.daqsoft.busquery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.daqsoft.busquery.bean.NearbyEntity;
import com.daqsoft.busquery.buscommon.NearbyAdapter;
import com.daqsoft.busquery.http.BusRetrofitHelper;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 公交查询附近公交
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-6-4.15:50
 * @since JDK 1.8
 */
public class NearbyBusActivity extends BaseActivity {
    RecyclerView mRv;
    private List<NearbyEntity> mNeedDatas;
    private NearbyAdapter mAdapter;
    private HeadView mHeadView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_nearby_bus;
    }

    @Override
    public void initView() {
        mHeadView = (HeadView)findViewById(R.id.title);
        mHeadView.setTitle("附近公交站");
        mHeadView.setBackListener(new HeadView.OnBackListener() {
            @Override
            public void onBack(View v) {
                finish();
            }
        });
        mRv = findViewById(R.id.rv_nearby);
        mNeedDatas = new ArrayList<>();
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NearbyAdapter(this, mNeedDatas);
        mRv.setAdapter(mAdapter);
        getData();
    }

    private void getData() {
        LoadingDialog.showDialogForLoading(this);
        String meLocation = getIntent().getStringExtra("meLocation");
        BusRetrofitHelper.getApiService().aroundBus(meLocation,"500")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody>
                            response) {
                        LoadingDialog.cancelDialogForLoading();
                        if (response.code() == 200) {
                            String value = null;
                            try {
                                value = response.body().string();
                                JSONObject object = JSONObject.parseObject(value.toString());
                                JSONArray datasArr = object.getJSONArray("datas");
                                if (object.getIntValue("code")==0&&datasArr.size()>0){
                                    for (int i = 0; i < datasArr.size(); i++) {
                                        JSONObject obj = datasArr.getJSONObject(i);
                                        NearbyEntity bean = new NearbyEntity();
                                        bean.setBusStation(obj.getString("name"));
                                        bean.setDestence(obj.getString("distance"));
                                        List<NearbyEntity.BusBean> mlist = new ArrayList<>();
                                        String[] split = obj.getString("address")
                                                .split(";");

                                        for (int j = 0; j < split.length; j++) {
                                            NearbyEntity.BusBean beans = new NearbyEntity.BusBean();
                                            beans.setBusName(split[j]);
                                            mlist.add(beans);
                                        }
                                        bean.setmBusbean(mlist);
                                        mNeedDatas.add(bean);
                                    }
                                    mAdapter.notifyDataSetChanged();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        LoadingDialog.cancelDialogForLoading();
                    }
                });
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }
}
