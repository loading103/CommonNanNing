package com.daqsoft.commonnanning.ui.main;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.http.FileUpload;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.adapter.CommentWriteAdapter;
import com.daqsoft.commonnanning.view.FullyGridLayoutManager;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.util.LogUtils;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * 写评论Activity
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-7-17
 * @since JDK 1.8.0_171
 */
@Route(path = Constant.ACTIVITY_WRITECOMMENT)
public class WriteCommentActivity extends BaseActivity implements TextWatcher {
    @BindView(R.id.headView)
    HeadView headView;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.tv_limit)
    TextView tvLimit;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.iv_star_one)
    ImageView ivStarOne;
    @BindView(R.id.iv_star_two)
    ImageView ivStarTwo;
    @BindView(R.id.iv_star_three)
    ImageView ivStarThree;
    @BindView(R.id.iv_star_four)
    ImageView ivStarFour;
    @BindView(R.id.iv_star_five)
    ImageView ivStarFive;
    @BindView(R.id.tv_score_des)
    TextView tvScoreDes;
    @Autowired(name = "name")
    String name;
    @Autowired(name = "id")
    String id;
    @Autowired(name = "type")
    String type;

    /**
     * 最大的字数限制
     */
    public static final int MAX_COUNT = 200;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.recyclerView_write_comment)
    RecyclerView recyclerViewWriteComment;

    /**
     * 星星集合
     */
    private ImageView[] stars;
    /**
     * 星级描述
     */
    private String[] describe = {
            "1分，非常不满意，各方面都很差",
            "2分，不满意，比较差",
            "3分，一般，还需改善",
            "4分，比较满意，仍可改善",
            "5分，非常满意，无可挑剔",
    };
    /**
     * 星级数
     */
    private int count = 5;
    /**
     * 图片选择的适配器
     */
    CommentWriteAdapter mAdapter;
    /**
     * 图片选择数据集合
     */
    ArrayList<String> mImgList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_write_comment;
    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        if (name == null || name.isEmpty()) {
            name = getIntent().getStringExtra("name");
        }
        if (id == null || name.isEmpty()) {
            id = getIntent().getStringExtra("id");
        }
        if (type == null || type.isEmpty()) {
            type = getIntent().getStringExtra("type");
        }


        tvName.setText(name);
        headView.setTitle("写评论");
        etComment.addTextChangedListener(this);
        stars = new ImageView[]{ivStarOne, ivStarTwo, ivStarThree, ivStarFour, ivStarFive,};
        setImageAdapter();
    }


    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    public void setImageAdapter() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 4, GridLayoutManager
                .VERTICAL, false);
        recyclerViewWriteComment.setLayoutManager(manager);
        mAdapter = new CommentWriteAdapter(this, new CommentWriteAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                choicePicture(5 - mImgList.size(), 1);
            }
        });
        mAdapter.setList(mImgList);
        recyclerViewWriteComment.setAdapter(mAdapter);
    }

    private void choicePicture(int num, int model) {
        Intent intent = new Intent(this, MultiImageSelectorActivity.class);
        // 是否显示调用相机拍照
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, false);
        // 最大图片选择数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, num);
        // 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者 多选/MultiImageSelectorActivity
        // .MODE_MULTI)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, model);
        // 默认选择
      /*  if (imgList != null && imgList.size() > 0) {
            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, imgList);
        }*/
        startActivityForResult(intent, 200);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> imgList = data.getStringArrayListExtra
                        (MultiImageSelectorActivity.EXTRA_RESULT);
                mImgList.addAll(imgList);
                mAdapter.setList(mImgList);
                mAdapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
        tvLimit.setText(start + "/" + MAX_COUNT);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        tvLimit.setText(start + "/" + MAX_COUNT);
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length() > MAX_COUNT) {
            editable.delete(MAX_COUNT, editable.length());
        }
        tvLimit.setText(editable.length() + "/" + MAX_COUNT);
    }


    @OnClick({R.id.iv_star_one, R.id.iv_star_two, R.id.iv_star_three, R.id.iv_star_four, R.id
            .iv_star_five, R.id.tv_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_star_one:
                SetStar(1);
                break;
            case R.id.iv_star_two:
                SetStar(2);
                break;
            case R.id.iv_star_three:
                SetStar(3);
                break;
            case R.id.iv_star_four:
                SetStar(4);
                break;
            case R.id.iv_star_five:
                SetStar(5);
                break;
            case R.id.tv_commit:
                // 确认提交
                Commit();
        }
    }

    /**
     * 确认提交
     */
    private void Commit() {
        final String comment = etComment.getText().toString();
        if (ObjectUtils.isNotEmpty(comment)) {
            LoadingDialog.showDialogForLoading(this);
            if (mImgList.size() > 0) {
                FileUpload.uploadFile(this, mImgList, new FileUpload.UploadFileBack() {
                    @Override
                    public void result(String value) {
                        if (!getString(R.string.value_1).equals(value)) {
                            LogUtils.e("请求-->" + value);
                            commit(value, comment);
                        } else {
                            ToastUtils.showShortCenter("图片上传失败");
                        }
                    }

                    @Override
                    public void resultList(List<String> value) {
                    }
                });
            } else {
                commit("", comment);
            }
        } else {
            ToastUtils.showLong("请输入评论内容");
        }
    }

    /**
     * 提交
     */
    private void commit(String img, String comment_) {
        RetrofitHelper.getApiService().saveComment(name, type, id, comment_, String.valueOf(count), img, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse baseResponse) throws Exception {
                        LoadingDialog.cancelDialogForLoading();
                        if (baseResponse.getCode() == 0) {
                            ToastUtils.showLong("提交成功，等待后台审核");
                            setResult(0);
                            finish();
                        } else if (baseResponse.getCode() == 2) {
                            ARouter.getInstance().build(Constant.ACTIVITY_LOGIN).navigation
                                    (WriteCommentActivity.this, 1);
                            ToastUtils.showLong("请先登录");
                        } else {
                            ToastUtils.showLong(baseResponse.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LoadingDialog.cancelDialogForLoading();
                        ToastUtils.showLong("请求失败!");
                    }
                });
    }


    /**
     * 设置星星
     *
     * @param count 第几颗星
     */
    private void SetStar(int count) {
        this.count = count;
        for (int i = 0; i < stars.length; i++) {
            if (i < count) {
                stars[i].setImageDrawable(getResources().getDrawable(R.mipmap.travel_comment_write_star_normal));
            } else {
                stars[i].setImageDrawable(getResources().getDrawable(R.mipmap.travel_comment_write_star_disabled));
            }
        }
        tvScoreDes.setText(describe[count - 1]);
    }
}
