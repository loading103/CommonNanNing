package com.daqsoft.busquery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.daqsoft.busquery.bean.TrainCodeBean;
import com.daqsoft.busquery.bean.TrainDetail;
import com.daqsoft.busquery.bean.TrainDetailBean;
import com.daqsoft.busquery.bean.TrainListBean;
import com.daqsoft.busquery.http.BusRetrofitHelper;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.widget.HeadView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 火车查询列表详情
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-6-4.15:50
 * @since JDK 1.8
 */
public class TrainDetailActivity extends BaseActivity {
    private HeadView mHeadView;
    TextView txt_startTime;
    TextView txt_startAdr;
    TextView txt_code;
    TextView txt_time;
    TextView txt_endAdr;
    TextView txt_endTime;
    RecyclerView recyclerView_price;
    RecyclerView recyclerView;
    private BaseQuickAdapter<TrainDetailBean.DataBeanX.DataBean,BaseViewHolder> adapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_train_detail;
    }

    @Override
    public void initView() {
        mHeadView = (HeadView)findViewById(R.id.headView);
        txt_startTime = (TextView)findViewById(R.id.txt_startTime);
        txt_startAdr = (TextView)findViewById(R.id.txt_startAdr);
        txt_code = (TextView)findViewById(R.id.txt_code);
        txt_time = (TextView)findViewById(R.id.txt_time);
        txt_endAdr = (TextView)findViewById(R.id.txt_endAdr);
        txt_endTime = (TextView)findViewById(R.id.txt_endTime);
        recyclerView_price = (RecyclerView)findViewById(R.id.recyclerView_price);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mHeadView.setTitle("车次详情");
        mHeadView.setBackListener(new HeadView.OnBackListener() {
            @Override
            public void onBack(View v) {
                finish();
            }
        });
        TrainListBean bean = (TrainListBean) getIntent().getSerializableExtra("BEAN");
        if (bean != null) {
            txt_startTime.setText(bean.getDeptTime());
            txt_startAdr.setText(bean.getDeptStationName());
            txt_code.setText(bean.getTrainCode());
            txt_time.setText(bean.getRunTime());
            txt_endTime.setText(bean.getArrTime());
            txt_endAdr.setText(bean.getArrStationName());
        }
        initAdapter();
        getData(bean.getTrainCode(), bean.getDeptDate());
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        adapter = new BaseQuickAdapter<TrainDetailBean.DataBeanX.DataBean, BaseViewHolder>(R.layout.adapter_traindetail,null) {
            @Override
            protected void convert(BaseViewHolder helper, TrainDetailBean.DataBeanX.DataBean bean) {
                helper.setText(R.id.txt_no,(helper.getAdapterPosition()+1)+"");
                helper.setText(R.id.txt_from,bean.getStation_name());
                helper.setText(R.id.txt_Time1,bean.getArrive_time());
                helper.setText(R.id.txt_Time2,bean.getStart_time());
                helper.setText(R.id.txt_Time3,bean.getStopover_time());
            }
        };
        recyclerView.setAdapter(adapter);

    }

    private void getData(final String traiNum, final String date) {
        final String fromCode = getIntent().getStringExtra("fromStrID");
        final String endCode = getIntent().getStringExtra("ArraveStrId");
        BusRetrofitHelper.getApiService().getTrainCode(date,fromCode,endCode)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<TrainCodeBean>>() {
                    @Override
                    public void accept(BaseResponse<TrainCodeBean> bean) throws Exception {
                        if (bean.getDatas() != null) {
                            for (TrainCodeBean temp : bean.getDatas()) {
                                if (temp.getTrainNum().equals(traiNum)) {
                                    getDetail(temp.getTrainNo(),fromCode,endCode,date);
                                    return;
                                }
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    private void getDetail(String no,String deptStationCode,String arrStationCode,String date){
        BusRetrofitHelper.getApiService().getTrainDetail(no,deptStationCode,arrStationCode,date)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TrainDetailBean>() {
                    @Override
                    public void accept(TrainDetailBean trainDetailBean) throws Exception {
                        if (trainDetailBean.getCode()==0){
                            adapter.setNewData(trainDetailBean.getData().getData());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }
}
