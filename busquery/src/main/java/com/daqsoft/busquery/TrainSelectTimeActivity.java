package com.daqsoft.busquery;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.daqsoft.busquery.bean.TrainListBean;
import com.daqsoft.busquery.buscommon.BusCommon;
import com.daqsoft.busquery.http.BusRetrofitHelper;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.LoadingDialog;

import java.util.Calendar;
import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 火车查询列表
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-6-4.15:50
 * @since JDK 1.8
 */
public class TrainSelectTimeActivity extends BaseActivity {
    private HeadView mHeadView;
    private RecyclerView recyclerView;
    TextView txt_time;
    private String dateValue;
    private String fromStrID = "";
    private String ArraveStrId = "";
    private ViewAnimator ticket_va;
    private TextView txt_next;
    private TextView txt_before;
    /**
     * 火车列表适配器
     */
    private BaseQuickAdapter<TrainListBean,BaseViewHolder> mAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_train_select_time;
    }

    @Override
    public void initView() {
        findId();
        initAdapter();
        getData();
    }

    /**
     * 获取数据
     */
    private void getData() {
        LoadingDialog.showDialogForLoading(this);
        BusRetrofitHelper.getApiService().getRrainLis(dateValue,fromStrID,ArraveStrId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<TrainListBean>>() {
                    @Override
                    public void accept(BaseResponse<TrainListBean> bean) throws Exception {
                        LoadingDialog.cancelDialogForLoading();
                        if (bean.getCode()==0){
                            ticket_va.setDisplayedChild(0);
                            mAdapter.setNewData(bean.getData().getTrainInfos());
                        }else {
                            ticket_va.setDisplayedChild(1);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ticket_va.setDisplayedChild(1);
                        LoadingDialog.cancelDialogForLoading();
                    }
                });
    }

    private void initAdapter() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BaseQuickAdapter<TrainListBean, BaseViewHolder>(R.layout.adapter_trainselecttime,null) {
            @Override
            protected void convert(BaseViewHolder helper, TrainListBean item) {
                helper.setText(R.id.txt_startTime,item.getDeptTime());
                helper.setText(R.id.txt_startAdr,item.getDeptStationName());
                helper.setText(R.id.txt_code,item.getTrainCode());
                helper.setText(R.id.txt_time,item.getRunTime());
                helper.setText(R.id.txt_endTime,item.getArrTime());
                helper.setText(R.id.txt_endAdr,item.getArrStationName());
                helper.setText(R.id.txt_price,"¥"+item.getMinPrice());
                RecyclerView mrv = (RecyclerView)helper.getView(R.id.recyclerView);
                mrv.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
                mrv.setAdapter(new BaseQuickAdapter<TrainListBean.Seat,BaseViewHolder>(R.layout.adapter_train_seat,item.getSeatList()) {
                    @Override
                    protected void convert(BaseViewHolder helper, TrainListBean.Seat item) {
                        helper.setText(R.id.txt_seat_type,item.getSeatName());
                        helper.setText(R.id.txt_seat_num,item.getSeatNum());
                    }
                });
            }
        };
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TrainListBean item = (TrainListBean)adapter.getItem(position);
                Intent intent = new Intent(mContext, TrainDetailActivity.class);
                intent.putExtra("BEAN", item);
                intent.putExtra("fromStrID", fromStrID);
                intent.putExtra("ArraveStrId", ArraveStrId);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    private void findId() {
        txt_time = (TextView)findViewById(R.id.txt_time);
        txt_next = (TextView)findViewById(R.id.txt_next);
        txt_before = (TextView)findViewById(R.id.txt_before);
        txt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = (Calendar) txt_time.getTag();
                if (cal == null) {
                    cal = Calendar.getInstance();
                }
                cal.add(Calendar.DATE, +1);
                int week = cal.get(Calendar.DAY_OF_WEEK);
                txt_time.setText((cal.get(Calendar.MONTH)+1) + "月" + cal.get(Calendar.DATE) + "日" + BusCommon.weekMap.get(week));
                txt_time.setTag(cal);
                Calendar cal2 = (Calendar) txt_time.getTag();
                dateValue = BusCommon.format(cal.getTime(), BusCommon.DEFAULT1);
                getData();
            }
        });
        txt_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = (Calendar) txt_time.getTag();
                if (cal == null) {
                    cal = Calendar.getInstance();
                }
                cal.add(Calendar.DATE, -1);
                int week = cal.get(Calendar.DAY_OF_WEEK);
                txt_time.setText((cal.get(Calendar.MONTH)+1) + "月" + cal.get(Calendar.DATE) + "日" + BusCommon.weekMap.get(week));
                txt_time.setTag(cal);
                Calendar cal2 = (Calendar) txt_time.getTag();
                dateValue = BusCommon.format(cal.getTime(), BusCommon.DEFAULT1);
                getData();
            }
        });
        mHeadView = (HeadView)findViewById(R.id.headView);
        ticket_va = (ViewAnimator)findViewById(R.id.ticket_va);
        String title = getIntent().getStringExtra("TITLE");
        mHeadView.setTitle(title);
        mHeadView.setBackListener(new HeadView.OnBackListener() {
            @Override
            public void onBack(View v) {
                finish();
            }
        });
        Date date = (Date) getIntent().getSerializableExtra("tag");
        if (date != null) {
            setDate(date);
        }
        dateValue = getIntent().getStringExtra("dateValue");
        fromStrID = getIntent().getStringExtra("fromStrID");
        ArraveStrId = getIntent().getStringExtra("ArraveStrId");
    }
    /**
     * 设置显示时间
     *
     * @param date
     */
    private void setDate(Date date) {
        if (date == null) {
            return;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.DAY_OF_WEEK);
        txt_time.setText((cal.get(Calendar.MONTH)+1) + "月" + cal.get(Calendar.DATE) + "日" + BusCommon.weekMap.get(week));
        txt_time.setTag(cal);
    }
    @Override
    public IBasePresenter initPresenter() {
        return null;
    }
}
