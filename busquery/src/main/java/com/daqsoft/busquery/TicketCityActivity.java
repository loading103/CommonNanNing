package com.daqsoft.busquery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.daqsoft.busquery.bean.CitySortEntity;
import com.daqsoft.busquery.bean.MyCharEntity;
import com.daqsoft.busquery.http.BusRetrofitHelper;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.util.KeyboardUtils;
import com.example.tomasyb.baselib.util.LogUtils;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.widget.HeadView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 火车查询城市列表
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-6-4.15:50
 * @since JDK 1.8
 */
public class TicketCityActivity extends BaseActivity {
    private HeadView mHeadView;
    RecyclerView mHotRv;
    RecyclerView mCharRv;
    RecyclerView mRvList;
    RecyclerView mRvHistroy;
    TextView tvLocation;
    EditText mEtSearch;
    private List<CitySortEntity> mHotData;
    private List<CitySortEntity> mHistroyData;
    private List<MyCharEntity> mCharData;
    private List<CitySortEntity> mListData;
    private BaseQuickAdapter<CitySortEntity, BaseViewHolder> mHotAdapter;
    private BaseQuickAdapter<CitySortEntity, BaseViewHolder> mHistroyAdapter;
    private BaseQuickAdapter<MyCharEntity, BaseViewHolder> mCharAdapter;
    private BaseQuickAdapter<CitySortEntity, BaseViewHolder> mListAdapter;
    /**
     * 出发地页面返回标识
     */
    private final int RESULT_CODE = 101;
    /**
     * 目的地
     */
    private final int RESULT_CODE2 = 102;
    /**
     * 页面标识
     */
    private String type = "1";

    @Override
    public int getLayoutId() {
        return R.layout.activity_ticket_city;
    }

    @Override
    public void initView() {
        findId();
        initAdapter();
        getHotCity();
        getHistroy();
    }

    private void initAdapter() {
        type = getIntent().getStringExtra("Travel");
        mHeadView.setTitle(type.equals("1") ? "出发城市" : "到达城市");
        mEtSearch.setHint(type.equals("1") ? "出发地" : "到达地");
        tvLocation.setText(getIntent().getStringExtra("cityname"));
        tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mHotData = new ArrayList<>();
        mCharData = new ArrayList<>();
        mListData = new ArrayList<>();
        String[] chararr = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
                "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

        for (int i = 0; i < chararr.length; i++) {
            MyCharEntity bean = new MyCharEntity();
            bean.setName(chararr[i]);
            if (i == 0) {
                bean.setChecked(true);
            } else {
                bean.setChecked(false);
            }
            mCharData.add(bean);
        }

        mHotRv.setLayoutManager(new GridLayoutManager(this, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        mHotAdapter = new BaseQuickAdapter<CitySortEntity, BaseViewHolder>(R.layout.item_text,
                mHotData) {
            @Override
            protected void convert(BaseViewHolder helper, CitySortEntity item) {
                helper.setText(R.id.tv_location, item.getName());
            }
        };
        mHotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                finish(mHotData.get(position).getName(), mHotData.get(position).getCode());
            }
        });
        mHotRv.setAdapter(mHotAdapter);

        // 历史记录

        mRvHistroy.setLayoutManager(new GridLayoutManager(this, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mHistroyAdapter = new BaseQuickAdapter<CitySortEntity, BaseViewHolder>(R.layout
                .item_text, mHistroyData) {
            @Override
            protected void convert(BaseViewHolder helper, CitySortEntity item) {
                helper.setText(R.id.tv_location, item.getName());
            }
        };
        mHistroyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                finish(mHistroyData.get(position).getName(), mHistroyData.get(position).getCode());
            }
        });
        mRvHistroy.setAdapter(mHistroyAdapter);
        mCharRv.setLayoutManager(new GridLayoutManager(this, 6) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mCharAdapter = new BaseQuickAdapter<MyCharEntity, BaseViewHolder>(R.layout.item_text,
                mCharData) {
            @Override
            protected void convert(BaseViewHolder helper, MyCharEntity item) {
                helper.setText(R.id.tv_location, item.getName());
                if (item.isChecked()) {
                    helper.setTextColor(R.id.tv_location, getResources().getColor(R.color
                            .main_default));
                    helper.setBackgroundRes(R.id.tv_location, R.drawable
                            .shape_route_date_bg_selected);
                } else {
                    helper.setTextColor(R.id.tv_location, getResources().getColor(R.color
                            .b_text_gray));
                    helper.setBackgroundRes(R.id.tv_location, R.drawable.bg_gray_line);
                }
            }
        };
        mCharAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                filterData(mCharData.get(position).getName(), "");
                for (int i = 0; i < mCharData.size(); i++) {
                    if (i == position) {
                        mCharData.get(i).setChecked(true);
                    } else {
                        mCharData.get(i).setChecked(false);
                    }
                }
                mCharAdapter.notifyDataSetChanged();
            }
        });
        mCharRv.setAdapter(mCharAdapter);

        // 按字母进行搜索
        mRvList.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });


        mListAdapter = new BaseQuickAdapter<CitySortEntity, BaseViewHolder>(R.layout
                .item_text_line, mListData) {
            @Override
            protected void convert(BaseViewHolder helper, CitySortEntity item) {
                helper.setText(R.id.tv_item_city, item.getName());
            }
        };
        mListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                finish(mListData.get(position).getName(), mListData.get(position).getCode());
            }
        });
        mRvList.setAdapter(mListAdapter);
        mEtSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent
                        .ACTION_UP) {
                    // 隐藏软键盘
                    KeyboardUtils.hideSoftInput(TicketCityActivity.this);
                    String trim = mEtSearch.getText().toString().trim();
                    if (ObjectUtils.isNotEmpty(trim)) {
                        filterData("", trim);
                    } else {
                        ToastUtils.showShort("请输入搜索关键字");
                    }
                }
                return false;
            }
        });
        filterData("A", "");
    }

    private void findId() {
        mHeadView = (HeadView) findViewById(R.id.title);
        mHotRv = (RecyclerView) findViewById(R.id.rv_startcity);
        mCharRv = (RecyclerView) findViewById(R.id.rv_char);
        mRvList = (RecyclerView) findViewById(R.id.rv_list);
        mRvHistroy = (RecyclerView) findViewById(R.id.rv_histroy);
        tvLocation = findViewById(R.id.tv_location);
        mEtSearch = (EditText) findViewById(R.id.edt_toAdr1);
        mHeadView.setBackListener(new HeadView.OnBackListener() {
            @Override
            public void onBack(View v) {
                finish();
            }
        });
    }

    /**
     * 条件查找
     *
     * @param filterStr
     */
    private void filterData(String filterStr, String name) {
        mListData.clear();
        BusRetrofitHelper.getApiService().getCodeStation(filterStr, name).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    String value = null;
                    try {
                        value = response.body().string();
                        LogUtils.e(value.toString());
                        JSONObject object = JSONObject.parseObject(value.toString());
                        JSONArray datasArr = object.getJSONArray("datas");
                        if (object.getIntValue("code") == 0 && datasArr.size() > 0) {
                            for (int i = 0; i < datasArr.size(); i++) {
                                JSONObject obj = datasArr.getJSONObject(i);
                                CitySortEntity bean = new CitySortEntity();
                                bean.setName(obj.getString("name"));
                                bean.setCode(obj.getString("code"));
                                bean.setId(obj.getIntValue("id"));
                                mListData.add(bean);
                            }
                            mListAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtils.showShort("暂无结果");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void finish(String city, String id) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("mcearchCity", city);
        bundle.putString("mcearchCityId", id);
        intent.putExtras(bundle);
        if (type.equals("1")) {
            setResult(RESULT_CODE, intent);
        } else {
            setResult(RESULT_CODE2, intent);
        }
        finish();

    }

    /**
     * 获取热门城市
     */
    private void getHotCity() {
        BusRetrofitHelper.getApiService().getHotStation().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    String value = null;
                    try {
                        value = response.body().string();
                        JSONObject object = JSONObject.parseObject(value.toString());
                        JSONArray datasArr = object.getJSONArray("datas");
                        for (int i = 0; i < datasArr.size(); i++) {
                            JSONObject obj = datasArr.getJSONObject(i);
                            CitySortEntity bean = new CitySortEntity();
                            bean.setName(obj.getString("name"));
                            bean.setCode(obj.getString("code"));
                            mHotData.add(bean);
                        }
                        mHotAdapter.notifyDataSetChanged();
                        LogUtils.e(value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    private void getHistroy() {
        BusRetrofitHelper.getApiService().searchStation().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    String value = null;
                    try {
                        value = response.body().string();
                        LogUtils.e(value.toString());
                        JSONObject object = JSONObject.parseObject(value.toString());
                        JSONArray datasArr = object.getJSONArray("datas");
                        for (int i = 0; i < datasArr.size(); i++) {
                            JSONObject obj = datasArr.getJSONObject(i);
                            CitySortEntity bean = new CitySortEntity();
                            bean.setName(obj.getString("name"));
                            bean.setCode(obj.getString("code"));
                            mHistroyData.add(bean);
                        }
                        mHistroyAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }
}
