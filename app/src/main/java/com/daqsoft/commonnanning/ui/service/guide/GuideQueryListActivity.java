package com.daqsoft.commonnanning.ui.service.guide;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.base.IApplication;
import com.daqsoft.commonnanning.http.CommonRequest;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.service.guide.bean.GuideListBean;
import com.daqsoft.commonnanning.ui.service.guide.bean.GuideTypeEntity;
import com.daqsoft.commonnanning.utils.CommonWindow;
import com.daqsoft.commonnanning.view.CenterDrawableTextView;
import com.daqsoft.utils.Constant;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.glide.GlideApp;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.net.common.DefaultObserver;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.refresh.SmartRefreshLayout;
import com.example.tomasyb.baselib.refresh.api.RefreshLayout;
import com.example.tomasyb.baselib.refresh.listener.OnRefreshListener;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.widget.HeadView;

import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.agora.yview.view.CenterDrawableEdittext;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 查询列表(导游)
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-3-20
 * @since JDK 1.8
 */

public class GuideQueryListActivity extends BaseActivity {
    @BindView(R.id.headView)
    HeadView headView;
    @BindView(R.id.edt_search)
    CenterDrawableEdittext edtSearch;
    @BindView(R.id.guide_choose_region)
    CenterDrawableTextView guideChooseRegion;
    @BindView(R.id.guide_choose_level)
    CenterDrawableTextView guideChooseLevel;
    @BindView(R.id.ll_choose)
    LinearLayout llChoose;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.no_date_img)
    ImageView noDateImg;
    @BindView(R.id.no_data_content)
    TextView noDataContent;
    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;
    BaseQuickAdapter adapter;
    /**
     * 每页条数
     */
    private final int PAGE_COUNT = 10;
    @BindView(R.id.guide_animator)
    ViewAnimator guideAnimator;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    /**
     * 当前页码
     */
    private int pageIndex = 1;
    /**
     * 性别
     */
    private int gender = 0;
    /**
     * 性别
     */
    private String gender1 = "";
    /**
     * 地区编码
     */
    private String region = "";
    /**
     * 导游等级
     */
    private String level = "";
    /**
     * 导游级别类型数据集
     */
    List<GuideTypeEntity> guideTypeList = new ArrayList<>();
    /**
     * 导游数据集合
     */
    List<GuideListBean> guideList = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_guidequery;
    }

    @Override
    public void initView() {
        headView.setTitle("导游列表");
        getGuideTypeList();
        GuideTypeEntity guideTypeEntity = new GuideTypeEntity();
        guideTypeEntity.setName("全部");
        guideTypeEntity.setCode("");
        guideTypeList.add(guideTypeEntity);
        initAdapter();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                request();
            }
        });
        edtSearch.setText(getIntent().getStringExtra(Constant.IntentKey.CONTENT));
        gender = getIntent().getIntExtra("gender", 3);
        switch (gender) {
            case 0:
                gender1 = "gender_1";
                break;
            case 1:
                gender1 = "gender_2";
                break;
            case 3:
                gender1 = "";
                break;
            default:
                break;
        }
        refreshLayout.autoRefresh();
        request();
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    refreshLayout.autoRefresh();
                    request();
                    return false;
                }
                return false;

            }

        });
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    /**
     * 获取导游级别类型数据集
     */
    public void getGuideTypeList() {
        RetrofitHelper.getApiService().getGuideTypeList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).subscribe(new DefaultObserver<GuideTypeEntity>() {
            @Override
            public void onSuccess(BaseResponse<GuideTypeEntity> response) {
                if (response.getCode() == 0) {
                    guideTypeList.addAll(response.getDatas());
                }
            }
        });
    }


    @OnClick(R.id.edt_search)
    public void onclick_search(View v) {
        refreshLayout.autoRefresh();
        request();
    }

    /**
     * 请求数据
     */
    private void request() {
        String keyword = edtSearch.getText().toString();
        RetrofitHelper.getApiService().getGuideList(keyword, PAGE_COUNT, pageIndex, gender1,
                region, level).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).subscribe(new DefaultObserver<GuideListBean>() {
            @Override
            public void onSuccess(BaseResponse<GuideListBean> response) {
                if (ObjectUtils.isNotEmpty(response)) {
                    if (response.getCode() == 0 && ObjectUtils.isNotEmpty(response.getDatas())) {
                        guideAnimator.setDisplayedChild(0);
                        if (1 != pageIndex) {
                            adapter.loadMoreComplete();
                        } else {
                            guideList.clear();
                        }
                        guideList.addAll(response.getDatas());
                        if (ObjectUtils.isNotEmpty(response.getPage()) && response.getPage().getCurrPage() < response.getPage().getTotalPage()) {
                            adapter.setEnableLoadMore(true);
                        } else {
                            adapter.loadMoreEnd();
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        guideAnimator.setDisplayedChild(1);
                    }
                } else {
                    guideAnimator.setDisplayedChild(1);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                refreshLayout.finishRefresh();
            }
        });
    }

    @OnClick({R.id.guide_choose_region, R.id.guide_choose_level, R.id.ll_no_data})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.guide_choose_region:
                guideChooseRegion.setSelected(!guideChooseRegion.isSelected());
                if (guideChooseRegion.isSelected()) {
                    guideChooseLevel.setSelected(false);
                    if (IApplication.getInstance().regionList.size() < 1) {
                        CommonRequest.getSiteRegions();
                    }
                    if (ObjectUtils.isNotEmpty(IApplication.getInstance().regionList) && IApplication.getInstance().regionList.size() > 0) {
                        setWindow(llChoose, IApplication.getInstance().regionList, region, 1);
                    }
                }
                break;
            case R.id.guide_choose_level:
                guideChooseLevel.setSelected(!guideChooseLevel.isSelected());
                if (guideChooseLevel.isSelected()) {
                    guideChooseRegion.setSelected(false);
                    if (guideTypeList.size() < 1) {
                        getGuideTypeList();
                    }
                    setWindow(llChoose, guideTypeList, level, 2);
                }
                break;
            case R.id.ll_no_data:
                request();
                break;
            default:
                break;
        }
    }

    /**
     * 筛选弹框
     *
     * @param view       显示的父类布局
     * @param list       数据源
     * @param selected   选中的
     * @param filterType 类型，1 表示位置区域，2 景区类型 3 距离
     */
    public void setWindow(View view, List<?> list, String selected, final int filterType) {
        CommonWindow.CommomPopupWindow(this, view, list, selected, 1,
                new CommonWindow.WindowCallBack() {
            @Override
            public void windowCallBack(String name, String key) {
                LogUtil.e(name + key);
                pageIndex = 1;
                switch (filterType) {
                    case 1:
                        // 地区
                        region = key;
                        guideChooseRegion.setText(name);
                        guideChooseRegion.setSelected(false);
                        break;
                    case 2:
                        // 导游等级
                        level = key;
                        guideChooseLevel.setText(name);
                        guideChooseLevel.setSelected(false);
                        break;
                    default:
                        break;
                }
                request();
            }

            @Override
            public void windowDismiss() {
                guideChooseRegion.setSelected(false);
                guideChooseLevel.setSelected(false);
            }
        });
    }

    /**
     * 初始化适配器
     */
    public void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter =
                new BaseQuickAdapter<GuideListBean, BaseViewHolder>(R.layout.guide_adapter_guidequery, guideList) {
            @Override
            protected void convert(BaseViewHolder helper, GuideListBean item) {
                helper.setText(R.id.txt_code, item.getIdentification());
                helper.setText(R.id.txt_company, item.getCompany());
                helper.setText(R.id.txt_language_type, item.getLanguage());
                helper.setText(R.id.txt_name, item.getName());
                helper.setText(R.id.txt_level, item.getLevel());
                helper.setImageResource(R.id.img_tag, item.getSex().equals("男性") ?
                        R.mipmap.guide_list_male : item.getSex().equals("女性") ?
                        R.mipmap.guide_list_female : R.mipmap.guide_list_unknown);
                if (ObjectUtils.isNotEmpty(item.getPhoto())) {
                    helper.setVisible(R.id.view_pic, true);
                    GlideApp.with(GuideQueryListActivity.this).load(item.getPhoto()).placeholder(R.mipmap.my_avatar_default).error(R.mipmap.my_avatar_default).into((ImageView) helper.getView(R.id.view_pic));
                } else {
                    helper.setVisible(R.id.view_pic, false);
                }
            }
        };
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageIndex++;
                request();
            }
        }, recyclerView);
        recyclerView.setAdapter(adapter);
    }
}
