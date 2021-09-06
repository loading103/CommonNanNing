package com.daqsoft.commonnanning.ui.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ViewAnimator;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.ComUtils;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.adapter.IndexSearchAdapter;
import com.daqsoft.commonnanning.ui.entity.SearchListEntity;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 全局搜索
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-5-2
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_GLOBALSEARCH)
public class GlobalSearchActivity extends BaseActivity implements View.OnKeyListener, TextWatcher {
    @BindView(R.id.recyclerView)
    RecyclerView mRv;
    @BindView(R.id.index_et_search)
    EditText mEtSearch;
    @BindView(R.id.va_)
    ViewAnimator mVa;


    private List<SearchListEntity> mDatas;
    private String strSearch = "";


    @Override
    public int getLayoutId() {
        return R.layout.activity_global_search;
    }

    @Override
    public void initView() {
        mEtSearch.setOnKeyListener(this);
        mEtSearch.addTextChangedListener(this);
        mDatas = new ArrayList<>();
        initAdapter();
    }

    /**
     * 搜索内容
     */
    private void searchAll() {
        LoadingDialog.showDialogForLoading(this, "", true);
        RetrofitHelper.getApiService().searchAll("1", strSearch, "true").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                LoadingDialog.cancelDialogForLoading();
                if (response.code() == 200) {
                    try {
                        String result = response.body().string();
                        JSONObject object = JSONObject.parseObject(result.toString());
                        JSONObject data = object.getJSONObject("data");
                        JSONArray type_guide = data.getJSONArray("type_guide");
                        JSONArray activity = data.getJSONArray("activity");
                        JSONArray destination = data.getJSONArray("destination");
                        JSONArray type_news = data.getJSONArray("type_news");
                        JSONArray video = data.getJSONArray("video");
                        JSONArray picture = data.getJSONArray("picture");
                        JSONArray sourceType_8 = data.getJSONArray("sourceType_8");
                        JSONArray sourceType_5 = data.getJSONArray("sourceType_5");
                        JSONArray sourceType_4 = data.getJSONArray("sourceType_4");
                        JSONArray sourceType_3 = data.getJSONArray("sourceType_3");
                        JSONArray sourceType_2 = data.getJSONArray("sourceType_2");
                        JSONArray sourceType_1 = data.getJSONArray("sourceType_1");
                        JSONArray travel_strategy = data.getJSONArray("travel_strategy");
                        int code = object.getIntValue("code");
                        if (type_guide.size() > 0 || activity.size() > 0 || destination.size() >
                                0 || type_news.size() > 0 || video.size() > 0 || picture.size() >
                                0 || sourceType_8.size() > 0 || sourceType_5.size() > 0 ||
                                sourceType_4.size() > 0 || sourceType_3.size() > 0 ||
                                sourceType_2.size() > 0 || sourceType_1.size() > 0 ||
                                travel_strategy.size() > 0) {
                            if (code == 0) {
                                mVa.setDisplayedChild(0);
                                mDatas.clear();
                                if (sourceType_1.size() > 0) {
                                    SearchListEntity bean = new SearchListEntity();
                                    List<SearchListEntity.SearchItem> mlist = new ArrayList<>();
                                    bean.setTitle("景区");
                                    bean.setType("sourceType_1");
                                    for (int j = 0; j < sourceType_1.size(); j++) {
                                        JSONObject obj = sourceType_1.getJSONObject(j);
                                        SearchListEntity.SearchItem item = new SearchListEntity
                                                .SearchItem();
                                        item.setName(obj.getString("name"));
                                        item.setId(obj.getString("id"));
                                        item.setResourcecode(obj.getString("resourcecode"));
                                        item.setLeveName(obj.getString("levelName"));
                                        item.setType("sourceType_1");
                                        mlist.add(item);
                                    }
                                    bean.setmItemList(mlist);
                                    mDatas.add(bean);
                                }
                                if (sourceType_2.size() > 0) {
                                    SearchListEntity bean = new SearchListEntity();
                                    bean.setTitle("酒店");
                                    bean.setType("sourceType_2");
                                    List<SearchListEntity.SearchItem> mlist2 = new ArrayList<>();
                                    for (int j = 0; j < sourceType_2.size(); j++) {
                                        JSONObject obj = sourceType_2.getJSONObject(j);
                                        SearchListEntity.SearchItem item = new SearchListEntity
                                                .SearchItem();
                                        item.setName(obj.getString("name"));
                                        item.setId(obj.getString("id"));
                                        item.setResourcecode(obj.getString("resourcecode"));
                                        item.setType("sourceType_2");
                                        mlist2.add(item);
                                    }
                                    bean.setmItemList(mlist2);
                                    mDatas.add(bean);
                                }
                                if (travel_strategy.size() > 0) {
                                    SearchListEntity bean = new SearchListEntity();
                                    bean.setTitle("游记攻略");
                                    bean.setType("travel_strategy");
                                    List<SearchListEntity.SearchItem> mliststrategy = new
                                            ArrayList<>();
                                    for (int j = 0; j < travel_strategy.size(); j++) {
                                        JSONObject obj = travel_strategy.getJSONObject(j);
                                        SearchListEntity.SearchItem item = new SearchListEntity
                                                .SearchItem();
                                        item.setName(obj.getString("title"));
                                        item.setId(obj.getString("id"));
                                        item.setType("travel_strategy");
                                        mliststrategy.add(item);
                                    }
                                    bean.setmItemList(mliststrategy);
                                    mDatas.add(bean);
                                }
                                // 美食
                                if (sourceType_8.size() > 0) {
                                    SearchListEntity bean = new SearchListEntity();
                                    bean.setTitle("美食");
                                    bean.setType("sourceType_8");
                                    List<SearchListEntity.SearchItem> mlist1 = new ArrayList<>();
                                    for (int j = 0; j < sourceType_8.size(); j++) {
                                        JSONObject obj = sourceType_8.getJSONObject(j);
                                        SearchListEntity.SearchItem item = new SearchListEntity
                                                .SearchItem();
                                        item.setName(obj.getString("name"));
                                        item.setId(obj.getString("id"));
                                        item.setType("sourceType_8");
                                        mlist1.add(item);
                                    }
                                    bean.setmItemList(mlist1);
                                    mDatas.add(bean);
                                }
                                // 图片
                                if (picture.size() > 0) {
                                    SearchListEntity bean = new SearchListEntity();
                                    bean.setTitle("图片");
                                    bean.setType("picture");
                                    List<SearchListEntity.SearchItem> mListImage = new
                                            ArrayList<>();
                                    for (int j = 0; j < picture.size(); j++) {
                                        JSONObject obj = picture.getJSONObject(j);
                                        SearchListEntity.SearchItem item = new SearchListEntity
                                                .SearchItem();
                                        item.setName(obj.getString("title"));
                                        item.setImgUrl(obj.getString("url"));
                                        item.setType("picture");
                                        mListImage.add(item);
                                    }
                                    bean.setmItemList(mListImage);
                                    mDatas.add(bean);
                                }
                                // 视频
                                if (video.size() > 0) {
                                    SearchListEntity bean = new SearchListEntity();
                                    bean.setTitle("视频");
                                    bean.setType("video");
                                    List<SearchListEntity.SearchItem> mlistvideo = new
                                            ArrayList<>();
                                    for (int j = 0; j < video.size(); j++) {
                                        JSONObject obj = video.getJSONObject(j);
                                        SearchListEntity.SearchItem item = new SearchListEntity
                                                .SearchItem();
                                        item.setName(obj.getString("title"));
                                        item.setVideoUrl(obj.getString("upload"));
                                        item.setType("video");
                                        mlistvideo.add(item);
                                    }
                                    bean.setmItemList(mlistvideo);
                                    mDatas.add(bean);
                                }
                                // 旅行社
                                if (sourceType_3.size() > 0) {
                                    SearchListEntity bean = new SearchListEntity();
                                    bean.setTitle("旅行社");
                                    bean.setType("sourceType_3");
                                    List<SearchListEntity.SearchItem> mlistTravel = new
                                            ArrayList<>();
                                    for (int j = 0; j < sourceType_3.size(); j++) {
                                        JSONObject obj = sourceType_3.getJSONObject(j);
                                        SearchListEntity.SearchItem item = new SearchListEntity
                                                .SearchItem();
                                        item.setName(obj.getString("name"));
                                        item.setId(obj.getString("id"));
                                        item.setType("sourceType_3");
                                        mlistTravel.add(item);
                                    }
                                    bean.setmItemList(mlistTravel);
                                    mDatas.add(bean);
                                }
                                if (mDatas.size() > 0) {
                                    mAdapter.upData(mDatas);
                                } else {
                                    mVa.setDisplayedChild(1);
                                }
                            } else {
                                // 报错
                                mVa.setDisplayedChild(1);
                            }
                        } else {
                            mVa.setDisplayedChild(1);
                        }

                    } catch (Exception e) {
                        mVa.setDisplayedChild(1);
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

    private IndexSearchAdapter mAdapter;

    /**
     * 适配器
     */
    private void initAdapter() {
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new IndexSearchAdapter(this);
        mRv.setAdapter(mAdapter);
    }


    @OnClick({R.id.index_et_search, R.id.tv_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.index_et_search:
                break;
            case R.id.tv_cancle:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
            ComUtils.hideInputWindow(this);
            String search = mEtSearch.getText().toString().trim();
            if (TextUtils.isEmpty(search)) {
                ToastUtils.showShort("请输入搜索内容");
            } else {
                strSearch = search;
                searchAll();
            }
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        strSearch = mEtSearch.getText().toString().trim();
        searchAll();
    }
}
