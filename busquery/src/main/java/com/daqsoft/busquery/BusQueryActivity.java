package com.daqsoft.busquery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.daqsoft.busquery.bean.BusSearchEty;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.widget.HeadView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * -----------------------------------------------------------------
 *
 * @version 1.0.1
 * @Description:公交查询
 * @Author 严博
 * @CreateDate 2019-4-8
 * @since jdk1.8.0_201
 * -----------------------------------------------------------------
 */
public class BusQueryActivity extends BaseActivity {
    private HeadView mHeadView;
    private AutoCompleteTextView mAtLast;
    private AutoCompleteTextView mAtMe;
    private RecyclerView mRv;
    private Button btnSearch;
    private ImageView img_change;
    private ImageView img_more;
    /**
     * 城市
     */
    private String city = "";
    private BaseQuickAdapter<BusSearchEty, BaseViewHolder> mBusAdapter;
    private List<BusSearchEty> mSearchList;
    // 终点
    private String mLastPostion = "";
    private String mLastPostion_ = "";
    // 起点
    private String mStartPostion_ = "";
    private String mStartPostion = "";
    /**
     * 我的位置
     */
    private String myLocation = "";
    private String myLocationName = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_bus_query;
    }

    @Override
    public void initView() {
        String latitude = getIntent().getStringExtra("latitude");
        String longitude = getIntent().getStringExtra("longitude");
        city = getIntent().getStringExtra("cityname");
        myLocationName = getIntent().getStringExtra("cityAddress");
        StringBuffer buffer = new StringBuffer();
        buffer.append(longitude);
        buffer.append(",");
        buffer.append(latitude);
        myLocation = buffer.toString();
        findId();
        if (ObjectUtils.isNotEmpty(myLocationName)) {
            mAtMe.setText("我的位置");
            mStartPostion_ = myLocation;
        }
    }

    /**
     * 找控件
     */
    private void findId() {
        mHeadView = (HeadView) findViewById(R.id.title);
        mHeadView.setTitle("公交查询");
        mHeadView.setBackListener(new HeadView.OnBackListener() {
            @Override
            public void onBack(View v) {
                finish();
            }
        });
        mAtLast = (AutoCompleteTextView) findViewById(R.id.keyword_other);
        mAtMe = (AutoCompleteTextView) findViewById(R.id.keyword_me);
        mRv = (RecyclerView) findViewById(R.id.rv_bus);
        img_change = (ImageView) findViewById(R.id.img_change);
        img_more = (ImageView) findViewById(R.id.img_more);
        img_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("meLocation", myLocation);
                Intent intent = new Intent(BusQueryActivity.this, NearbyBusActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btnSearch = (Button) findViewById(R.id.tv_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ObjectUtils.isNotEmpty(mAtMe.getText().toString().trim()) && ObjectUtils
                        .isNotEmpty(mAtLast.getText().toString().trim())) {
                    Bundle bundle = new Bundle();
                    bundle.putString("muLocation", mLastPostion_);
                    bundle.putString("meLocation", mStartPostion_);
                    bundle.putString("muLocationName", mAtLast.getText().toString().trim());
                    bundle.putString("meLocationName", mAtMe.getText().toString().trim());
                    bundle.putString("city", city);
                    Intent intent = new Intent(BusQueryActivity.this, BusQueryListActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (!ObjectUtils.isNotEmpty(mAtMe.getText().toString().trim())) {
                    ToastUtils.showShort("请输入起点");
                } else {
                    ToastUtils.showShort("请输入终点");
                }
            }
        });
        img_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mes = mAtMe.getText().toString().trim();
                String mu = mAtLast.getText().toString().trim();
                mAtMe.setText(mu);
                mAtLast.setText(mes);
                String postion = "";
                postion = mLastPostion_;
                mLastPostion_ = mStartPostion_;
                mStartPostion_ = postion;
            }
        });
        initRv();
        initEt();
    }

    private void initEt() {
        mAtMe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                String newText = s.toString().trim();
                if (ObjectUtils.isNotEmpty(newText)) {
                    InputtipsQuery inputquery = new InputtipsQuery(newText, "");
                    Inputtips inputTips = new Inputtips(BusQueryActivity.this, inputquery);
                    inputTips.setInputtipsListener(new Inputtips.InputtipsListener() {
                        @Override
                        public void onGetInputtips(List<Tip> tipList, int rCode) {
                            if (rCode == AMapException.CODE_AMAP_SUCCESS) {
                                if (tipList != null) {
                                    mSearchList.clear();
                                    int size = tipList.size();
                                    for (int i = 0; i < size; i++) {
                                        Tip tip = tipList.get(i);
                                        if (tip != null) {
                                            BusSearchEty bean = new BusSearchEty();
                                            bean.setName(tipList.get(i).getName());
                                            bean.setAdress(tipList.get(i).getDistrict());
                                            bean.setLocation(tip.getPoint().getLongitude() + ","
                                                    + tip.getPoint().getLatitude());
                                            bean.setType(0);
                                            mSearchList.add(bean);
                                        }
                                    }
                                    mBusAdapter.notifyDataSetChanged();
                                } else {
                                    mSearchList.clear();
                                    mBusAdapter.notifyDataSetChanged();
                                }
                            } else {
                                mSearchList.clear();
                                mBusAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                    inputTips.requestInputtipsAsyn();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mAtLast.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                String newText = city + s.toString().trim();
                if (ObjectUtils.isNotEmpty(newText)) {
                    InputtipsQuery inputquery = new InputtipsQuery(newText, "");
                    Inputtips inputTips = new Inputtips(BusQueryActivity.this, inputquery);
                    inputTips.setInputtipsListener(new Inputtips.InputtipsListener() {
                        @Override
                        public void onGetInputtips(List<Tip> tipList, int rCode) {
                            if (rCode == AMapException.CODE_AMAP_SUCCESS) {
                                if (tipList != null) {
                                    mSearchList.clear();
                                    int size = tipList.size();
                                    for (int i = 0; i < size; i++) {
                                        Tip tip = tipList.get(i);
                                        if (tip != null) {
                                            BusSearchEty bean = new BusSearchEty();
                                            bean.setName(tipList.get(i).getName());
                                            bean.setAdress(tipList.get(i).getDistrict());
                                            bean.setLocation(tip.getPoint().getLongitude() + ","
                                                    + tip.getPoint().getLatitude());
                                            bean.setType(1);
                                            mSearchList.add(bean);
                                        }
                                    }
                                    mBusAdapter.notifyDataSetChanged();
                                } else {
                                    mSearchList.clear();
                                    mBusAdapter.notifyDataSetChanged();
                                }
                            } else {
                                mSearchList.clear();
                                mBusAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                    inputTips.requestInputtipsAsyn();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initRv() {
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mSearchList = new ArrayList<>();
        mBusAdapter = new BaseQuickAdapter<BusSearchEty, BaseViewHolder>(R.layout.item_only_text,
                mSearchList) {
            @Override
            protected void convert(BaseViewHolder helper, final BusSearchEty item) {
                helper.setVisible(R.id.tv_adress, true);
                helper.setText(R.id.tv_adress, item.getAdress());
                helper.setText(R.id.tv_region, item.getName());
                helper.setOnClickListener(R.id.ll_bus_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (item.getType() == 0) {
                            mAtMe.setText(item.getName());
                            mStartPostion_ = item.getLocation();
                        } else {
                            mAtLast.setText(item.getName());
                            mLastPostion_ = item.getLocation();
                        }
                    }
                });
            }
        };
        mRv.setAdapter(mBusAdapter);
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }
}
