package com.daqsoft.commonnanning.ui.service.guide;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.utils.annotation.ViewToastInject;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.widget.HeadView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 导游页面
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-3-20
 * @since JDK 1.8
 */
public class GuideActivity extends BaseActivity {

    @BindView(R.id.edt_search)
    EditText edt_search;
    @BindView(R.id.tv_sex_search)
    TextView tvSexSearch;
    /**
     * 性别选择器
     */
    OptionsPickerView pvOptions;


    @BindView(R.id.edt_search_close)
    ImageView edtSearchClose;
    @BindView(R.id.btn_select)
    Button btnSelect;
    @BindView(R.id.title)
    HeadView title;
    /**
     * 性别的数据集
     */
    private List<String> sexList = Arrays.asList("男", "女", "未知");
    /**
     * 性别
     * 男性：gender_1，女性：gender_2，未知性别：gender_3
     */
    int gender = 3;


    @Override
    public int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public void initView() {
        title.setTitle("导游查询");
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    @OnClick(R.id.btn_select)
    public void onclick_select(View v) {
        if (!ViewToastInject.check(this)) {
            return;
        }
        String name = edt_search.getText().toString().trim();
        String sex = tvSexSearch.getText().toString().trim();
        Intent intent = new Intent(this, GuideQueryListActivity.class);
        intent.putExtra("CONTENT", edt_search.getText().toString());
        intent.putExtra("gender", gender);
        startActivity(intent);
    }

    @OnClick(R.id.edt_search_close)
    public void onclick_delete(View view) {
        edt_search.setText("");
    }

    @OnClick(R.id.tv_sex_search)
    public void onViewClicked() {
        // 条件选择器
        pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView
                .OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tvSexSearch.setText(sexList.get(options1));
                gender = options1;
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView tvSubmit = (TextView) v.findViewById(R.id
                                .choose_date_sure);
                        TextView tvCancel = (TextView) v.findViewById(R.id
                                .choose_date_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvOptions.returnData();
                                pvOptions.dismiss();
                            }
                        });

                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvOptions.dismiss();
                            }
                        });


                    }
                })
                .isDialog(false)
                .setSelectOptions(gender)
                .build();
        pvOptions.setPicker(sexList);
        pvOptions.show();
    }
}
