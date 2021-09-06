package com.daqsoft.busquery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.daqsoft.busquery.bean.BusDetialEntity;
import com.daqsoft.busquery.buscommon.BusCommon;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.widget.HeadView;

import java.util.ArrayList;
import java.util.List;

/**
 * 公交查询详情
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-6-4.15:50
 * @since JDK 1.8
 */
public class BusQueryDetailActivity extends BaseActivity {
    private HeadView mHeadView;
    /**
     * 选中的哪个公交路线
     */
    private int postion;
    /**
     * 传过来的json
     */
    private String json;
    /**
     * 起点
     */
    private String mStartLocation = "";
    /**
     * 数据
     */
    private List<String> mDtas;
    /**
     * 上面适配器
     */
    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;
    /**
     * 公交查询适配器
     */
    private BaseQuickAdapter<BusDetialEntity, BaseViewHolder> mBusAdapter;
    /**
     * 数据
     */
    private List<BusDetialEntity> mBusDatas;
    /**
     * 目的地名称
     */
    String stopName = "";
    /**
     * 起始地
     */
    String startName = "";
    /**
     * 是否展开
     */
    private boolean iskai = false;
    private RecyclerView mRv;
    private RecyclerView mRvBottom;
    private TextView tvStop;
    private TextView tvMe;
    TextView tvMinute;
    TextView mTvFoot;
    @Override
    public int getLayoutId() {
        return R.layout.activity_bus_query_detail;
    }

    @Override
    public void initView() {
        findId();
        initAdapter();
        initData();

    }

    private void initAdapter() {
        mDtas = new ArrayList<>();
        mBusDatas = new ArrayList<>();
        mRv.setLayoutManager(new GridLayoutManager(this, 3));
        mRvBottom.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_txt_gray, mDtas) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_busnum, item);
                if (helper.getPosition() == mDtas.size() - 1) {
                    helper.setVisible(R.id.img_more, false);
                }
            }
        };
        mRv.setAdapter(mAdapter);
        mBusAdapter = new BaseQuickAdapter<BusDetialEntity, BaseViewHolder>(R.layout.item_bus_detial, mBusDatas) {
            @Override
            protected void convert(BaseViewHolder helper, BusDetialEntity item) {
                helper.setText(R.id.tv_busstart, item.getBusStart());
                helper.setText(R.id.tv_busnum, item.getBusNum());
                helper.setText(R.id.tv_xiang, item.getBusXiang());
                helper.setText(R.id.tv_busstopnum, item.getBusTotla());
                helper.setText(R.id.tv_minutenum, item.getBusMinetue());
                helper.setText(R.id.tv_stop, item.getBusStop());
                helper.setText(R.id.textView15, item.getWalkDicetence());
                final RecyclerView mRv = (RecyclerView)helper.getView(R.id.rv_busstop);
                mRv.setLayoutManager(new LinearLayoutManager(mContext));
                mRv.setAdapter(new BaseQuickAdapter<BusDetialEntity.busStation,BaseViewHolder>(R.layout.item_only_text,item.getmBusList()) {

                    @Override
                    protected void convert(BaseViewHolder helper, BusDetialEntity.busStation item) {
                        helper.setText(R.id.tv_region,item.getBusName());
                    }
                });
                helper.setOnClickListener(R.id.ll_stop, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (iskai){
                            mRv.setVisibility(View.GONE);
                            iskai = false;
                        }else {
                            mRv.setVisibility(View.VISIBLE);
                            iskai = true;
                        }
                    }
                });
            }
        };
        mRvBottom.setAdapter(mBusAdapter);
    }

    /**
     * 找控件
     */
    private void findId() {
        mHeadView = (HeadView)findViewById(R.id.title);
        mHeadView.setTitle("结果详情");
        mHeadView.setBackListener(new HeadView.OnBackListener() {
            @Override
            public void onBack(View v) {
                finish();
            }
        });
        mRv = (RecyclerView)findViewById(R.id.rv_busitem);
        mRvBottom = (RecyclerView)findViewById(R.id.rv_bottom);
        tvStop = (TextView)findViewById(R.id.tv_stop);
        tvMe = (TextView)findViewById(R.id.tv_me);
        tvMinute = (TextView)findViewById(R.id.tv_minute);
        mTvFoot = (TextView)findViewById(R.id.textView7);
    }

    private void initData() {
        try {
            startName = getIntent().getStringExtra("meLocationName");
            stopName = getIntent().getStringExtra("muLocationName");
            json = getIntent().getStringExtra("json");
            postion = getIntent().getIntExtra("itempostion", 0);
            tvStop.setText(ObjectUtils.isNotEmpty(stopName) ? stopName : "目的地");
            tvMe.setText(ObjectUtils.isNotEmpty(startName) ? startName : "我的位置");
            matchData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 处理传过来的json数据
     */
    private void matchData() {
        try {
            JSONObject jsonObject = JSONObject.parseObject(json);
            if (jsonObject.getString("code").equals("0")) {
                JSONObject dataObj = jsonObject.getJSONObject("data");
                JSONArray transitsArray = dataObj.getJSONArray("transits");
                JSONObject itemObj = transitsArray.getJSONObject(postion);
                String duration = itemObj.getString("duration");
                tvMinute.setText(BusCommon.minutetofen(duration));
                mTvFoot.setText("步行" + itemObj.getString("walking_distance") + "m");
                JSONArray segmentsArray = itemObj.getJSONArray("segments");
                if (segmentsArray.size() > 0) {
                    for (int j = 0; j < segmentsArray.size(); j++) {
                        BusDetialEntity busBean = new BusDetialEntity();
                        JSONObject segmentsObj = segmentsArray.getJSONObject(j);
                        JSONObject busObj = segmentsObj.getJSONObject("bus");
                        boolean isSuccessWalk = true;
                        JSONObject walkingObj = null;
                        try {
                            isSuccessWalk = true;
                            walkingObj = segmentsObj.getJSONObject("walking");
                        }catch (Exception e){
                            isSuccessWalk = false;
                            e.printStackTrace();
                        }
                        JSONArray buslinesArray = busObj.getJSONArray("buslines");

                        if (buslinesArray.size() > 0) {
                            JSONObject needObj = buslinesArray
                                    .getJSONObject(0);
                            String name = needObj.getString("name");
                            String[] split = name.split("\\(");
                            mDtas.add(split[0]);
                            busBean.setBusStart(needObj.getJSONObject("departure_stop").getString("name"));
                            busBean.setBusStop(needObj.getJSONObject("arrival_stop").getString("name"));
                            busBean.setBusTotla(needObj.getJSONArray("via_stops").size() + "站");
                            JSONArray busStopArr = needObj.getJSONArray("via_stops");
                            if (busStopArr.size()>0){
                                List<BusDetialEntity.busStation> mlit = new ArrayList<>();
                                for (int i = 0; i < busStopArr.size(); i++) {
                                    JSONObject obj = busStopArr.getJSONObject(i);
                                    BusDetialEntity.busStation bean = new BusDetialEntity.busStation();
                                    bean.setBusName(obj.getString("name"));
                                    mlit.add(bean);
                                }
                                busBean.setmBusList(mlit);
                            }
                            busBean.setBusMinetue("（" + BusCommon.minutetofen(needObj.getString("duration")) + ")");
                            busBean.setBusNum(split[0]);
                            String[] split1 = split[1].split("--");
                            busBean.setBusXiang("开往" + split1[1].split("\\)")[0]);

                            if (isSuccessWalk){
                                busBean.setWalkDicetence("步行" + walkingObj.getString("distance") + "m (" + BusCommon
                                        .minutetofen(walkingObj.getString("duration")) + ")");
                            }else {
                                busBean.setWalkDicetence("步行0m");
                            }
                            mBusDatas.add(busBean);
                            // 不大于0就没有公交了
                        } else {
                            View view = getLayoutInflater().inflate(R.layout.item_bus_footer, (ViewGroup) mRvBottom
                                    .getParent(), false);
                            TextView footer = (TextView) view.findViewById(R.id.textView15);
                            footer.setText("步行" + walkingObj.getString("distance") + "m (" + BusCommon.minutetofen
                                    (walkingObj.getString("duration")) + ")");
                            mBusAdapter.addFooterView(view);
                        }

                    }
                    View head = getLayoutInflater().inflate(R.layout.item_bus_header, (ViewGroup) mRvBottom.getParent
                            (), false);
                    TextView headTxt = (TextView) head.findViewById(R.id.tv_title);
                    headTxt.setText("起点（我的位置）");
                    mBusAdapter.addHeaderView(head);
                    mBusAdapter.notifyDataSetChanged();
                    mAdapter.notifyDataSetChanged();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public IBasePresenter initPresenter() {
        return null;
    }
}
