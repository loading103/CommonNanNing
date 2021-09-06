package com.daqsoft.busquery;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daqsoft.busquery.bean.TrainListBean;
import com.daqsoft.busquery.buscommon.BusCommon;
import com.daqsoft.busquery.http.BusRetrofitHelper;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.util.annotation.ViewToastInject;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.LoadingDialog;
import com.example.tomasyb.baselib.widget.date.TimeSelectDialog;

import java.util.Calendar;
import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 火车查询
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-6-4.15:50
 * @since JDK 1.8
 */
public class TrainticketsActivity extends BaseActivity {
    private HeadView mHeadView;
    private TextView edt_fromAdr;
    private TextView edt_toAdr;
    TextView txt_date;
    TextView txt_week;
    private ImageView img_change;
    /**
     * 出发地页面返回标识
     */
    private final int REQUEST_CODE = 1;
    private String mcityName = "";
    private String mStrefromAdr = "";
    private String mStretoAdr = "";
    private String fromStrID = "";
    private String ArraveStrId = "";
    private LinearLayout ll_date;
    private Button btn_submit;
    @Override
    public int getLayoutId() {
        return R.layout.activity_traintickets_activity;
    }

    @Override
    public void initView() {
        findId();
        initData();
    }

    private void initData() {
        Calendar cal = Calendar.getInstance();
        txt_date.setText(cal.get(Calendar.YEAR) + "年" + (cal.get(Calendar.MONTH) + 1) + "月" + cal
                .get(Calendar.DATE)
                + "日");
        txt_date.setTag(cal.getTime());
        int week = cal.get(Calendar.DAY_OF_WEEK);
        int tempDays = BusCommon.getDaydifference(cal.getTime());
        String daysValue = BusCommon.todyMap.get(tempDays);
        String result = "";
        if (!TextUtils.isEmpty(daysValue)) {
            result = "(" + daysValue + ")";
        }
        txt_week.setText(BusCommon.weekMap.get(week) + result);
        edt_fromAdr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(TrainticketsActivity.this, TicketCityActivity
                        .class);
                Bundle bundle2 = new Bundle();
                bundle2.putString("Travel", "1");
                bundle2.putString("cityname", mcityName);
                intent2.putExtras(bundle2);
                startActivityForResult(intent2, REQUEST_CODE);
            }
        });
        edt_toAdr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrainticketsActivity.this, TicketCityActivity
                        .class);
                Bundle bundle = new Bundle();
                bundle.putString("Travel", "2");
                bundle.putString("cityname", mcityName);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    private void findId() {
        mcityName = getIntent().getStringExtra("cityname");
        mHeadView = (HeadView)findViewById(R.id.title);
        edt_fromAdr = (TextView)findViewById(R.id.edt_fromAdr);
        edt_toAdr = (TextView)findViewById(R.id.edt_toAdr);
        txt_date = (TextView)findViewById(R.id.txt_date);
        txt_week = (TextView)findViewById(R.id.txt_week);
        img_change = (ImageView) findViewById(R.id.img_change);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request();
            }
        });
        ll_date = (LinearLayout) findViewById(R.id.ll_date);
        ll_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeSelectDialog.Builder builder = new TimeSelectDialog.Builder(TrainticketsActivity.this);
                builder.setListener(new TimeSelectDialog.OnTimeSelectListener() {
                    @Override
                    public void select(Date date) {
                        if (date != null) {
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(date);
                            txt_date.setText(cal.get(Calendar.YEAR) + "年" + (cal.get(Calendar.MONTH) + 1)
                                    + "月" + cal.get
                                    (Calendar.DATE) + "日");
                            txt_date.setTag(date);
                            int week = cal.get(Calendar.DAY_OF_WEEK);
                            int tempDays = BusCommon.getDaydifference(date);
                            String daysValue = BusCommon.todyMap.get(tempDays);
                            String result = "";
                            if (!TextUtils.isEmpty(daysValue)) {
                                result = "(" + daysValue + ")";
                            }
                            txt_week.setText(BusCommon.weekMap.get(week) + result);
                        }
                    }
                });
                TimeSelectDialog dialog = builder.create();
                dialog.show();
            }
        });
        img_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ObjectUtils.isNotEmpty(mStrefromAdr) && ObjectUtils.isNotEmpty(mStretoAdr)) {
                    String fromStr = mStrefromAdr;
                    String toStr = mStretoAdr;
                    String fromId = fromStrID;
                    String toId = ArraveStrId;
                    edt_fromAdr.setText(toStr);
                    edt_toAdr.setText(fromStr);
                    mStrefromAdr = toStr;
                    mStretoAdr = fromStr;
                    fromStrID = toId;
                    ArraveStrId = fromId;
                } else {
                    ToastUtils.showShort("请选择目的地");
                }
            }
        });
        mHeadView.setTitle("火车查询");
        mHeadView.setBackListener(new HeadView.OnBackListener() {
            @Override
            public void onBack(View v) {
                finish();
            }
        });
    }

    private void request() {
        if (!ViewToastInject.check(this)) {
            return;
        }
        if (txt_date.getTag() == null) {
            ToastUtils.showShort("请选择日期");
            return;
        }
        final Date date = (Date) txt_date.getTag();
        String dateValue = BusCommon.format(date, BusCommon.DEFAULT1);
        if (TextUtils.isEmpty(fromStrID)) {
            ToastUtils.showShort("没有查询到出发站点简写");
            return;
        }
        if (TextUtils.isEmpty(ArraveStrId)) {
            ToastUtils.showShort("没有查询到到达站点简写");
            return;
        }
        saveStation(dateValue,date);
    }
    /**
     * 页面返回标识
     */
    private final int RESULT_CODE = 101;
    /**
     * 到达
     */
    private final int RESULT_CODE2 = 102;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            // 出发
            if (resultCode == RESULT_CODE) {
                mStrefromAdr = data.getExtras().getString("mcearchCity");
                fromStrID = data.getExtras().getString("mcearchCityId");
                edt_fromAdr.setText(mStrefromAdr);
            } else if (resultCode == RESULT_CODE2) {
                mStretoAdr = data.getExtras().getString("mcearchCity");
                ArraveStrId = data.getExtras().getString("mcearchCityId");
                edt_toAdr.setText(mStretoAdr);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 保存搜索
     */
    private void saveStation(final String dateValue, final Date date) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(mStrefromAdr);
        buffer.append(",");
        buffer.append(mStretoAdr);
        BusRetrofitHelper.getApiService().saveStation(buffer.toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody>
                            response) {
                        Intent intent = new Intent(TrainticketsActivity.this,
                                TrainSelectTimeActivity
                                        .class);
                        intent.putExtra("dateValue", dateValue);
                        intent.putExtra("fromStrID", fromStrID);
                        intent.putExtra("ArraveStrId", ArraveStrId);
                        intent.putExtra("TITLE", edt_fromAdr.getText()
                                .toString() +
                                "-" + edt_toAdr
                                .getText().toString());
                        intent.putExtra("tag", date);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        ToastUtils.showShort("查询失败!");
                    }
                });
    }
    @Override
    public IBasePresenter initPresenter() {
        return null;
    }
}
