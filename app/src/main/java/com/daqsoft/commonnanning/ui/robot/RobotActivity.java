package com.daqsoft.commonnanning.ui.robot;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.daqsoft.android.ProjectConfig;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.common.URLConstant;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.robot.adapter.RobotMultipleAdapter;
import com.daqsoft.commonnanning.ui.robot.entity.MsgEty;
import com.daqsoft.commonnanning.ui.robot.entity.RobotChangeEty;
import com.daqsoft.commonnanning.ui.robot.entity.RobotMultipleItem;
import com.daqsoft.commonnanning.ui.robot.entity.RobotTag;
import com.daqsoft.commonnanning.ui.robot.entity.RobotTypeTag;
import com.daqsoft.commonnanning.ui.robot.entity.XfVoiceBean;
import com.daqsoft.commonnanning.utils.ShowDialog;
import com.daqsoft.utils.Utils;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.adapter.BaseViewHolder;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.util.LogUtils;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.widget.HeadView;
import com.google.gson.Gson;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.library.flowlayout.FlowLayoutManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.agora.yview.dialog.BaseDialog;
import okhttp3.Call;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ?????????????????????
 *
 * @author ??????
 * @version 1.0.0
 * @date 2018/8/15
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_ROBOT)
public class RobotActivity extends BaseActivity {

    @BindView(R.id.robot_rv_seach)
    RecyclerView mRvSearch;
    @BindView(R.id.rv_chat)
    RecyclerView mRvChat;
    @BindView(R.id.et_robot_input)
    EditText mEtChat;
    @BindView(R.id.ll_routll)
    LinearLayout mRootview;
    @BindView(R.id.rv_bottom)
    RecyclerView mRvBottom;
    @BindView(R.id.ll_input)
    LinearLayout mLlInput;
    @BindView(R.id.robot_va)
    ViewAnimator mVa;
    @BindView(R.id.view_record)
    MarqueeView view_record;
    @BindView(R.id.tv_audiostart)
    TextView mTvStart;
    @BindView(R.id.tv_audioing)
    TextView tvAudioing;
    @BindView(R.id.robot_title)
    HeadView robotTitle;
    @BindView(R.id.iv_robot_voice)
    ImageView ivRobotVoice;
    private String nikname = "";
    /**
     * ??????????????????taglist
     */
    private List<RobotTag> mTagList;
    /**
     * ??????Tag
     */
    private List<RobotTypeTag> mTypeTagList = new ArrayList<>();
    /**
     * tag?????????
     */
    private BaseQuickAdapter<RobotTag, BaseViewHolder> mTagAdapter;
    /**
     * ???????????????
     */
    private RobotMultipleAdapter mChatAdapter;
    /**
     * ????????????
     */
    private List<RobotMultipleItem> mChatList;
    /**
     * ??????????????????
     */
    private boolean isAudioStart = false;
    /**
     * ???????????????
     */
    private BaseQuickAdapter<RobotTypeTag, BaseViewHolder> mTypeAdapter;
    /**
     * ??????????????????code
     */
    final int SUCCESS_CODE = 200;
    /**
     * ????????????????????????????????????
     */
    SpeechRecognizer mIat;
    /**
     * ????????????????????????????????????
     */
    SpeechSynthesizer speechSynthesizer;
    /**
     * ????????????
     */
    final int HEIGHT_MOVE = -200;
    /**
     * ????????????
     */
    boolean IS_SEND = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_robot2;
    }

    /**
     * ??????
     *
     * @param msg
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MsgEty msg) {
        try {
            addMeContent(msg.getContent());
            if (Utils.isnotNull(msg.getId()) && Utils.isnotNull(msg.getType())) {
                getAnswerById(msg.getId(), msg.getType(), msg.getLat(), msg.getLng(),
                        msg.getPhone(), msg.getAdress(), msg.getContent(), msg.getTitle());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ??????????????????
     */
    private void addMeContent(String content) {
        RobotMultipleItem bean = new RobotMultipleItem(1);
        bean.setContent(content);
        mChatList.add(bean);
        mChatAdapter.notifyDataSetChanged();
        mRvChat.scrollToPosition(mChatAdapter.getItemCount() - 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if(speechSynthesizer!=null){
            speechSynthesizer.destroy();
        }
        if(mIat!=null){
            mIat.destroy();
        }
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        robotTitle.setTitle("???????????????");
        inputTouch();
        initListener();
        initChatAdapter();
        getRobotInfo();
        initVoice();
        if(initIfly()){
            return;
        };
        initSpeechSynthesizer();
        initTagAdapter();
        getRobotTagList();
    }

    /**
     * ?????????tag????????????
     */
    public void initTagAdapter() {
        mTagList = new ArrayList<>();
        mRvSearch.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));
        mTagAdapter = new BaseQuickAdapter<RobotTag, BaseViewHolder>(R.layout.item_tag4, mTagList) {
            @Override
            protected void convert(BaseViewHolder helper, RobotTag item) {
                helper.setText(R.id.tag, item.getName());
                if (item.isSeclted()) {
                    helper.setBackgroundRes(R.id.tag, R.drawable.shape_search_main);
                    helper.setTextColor(R.id.tag, getResources().getColor(R.color.white));
                } else {
                    helper.setBackgroundRes(R.id.tag, R.drawable.shape_search);
                    helper.setTextColor(R.id.tag, getResources().getColor(R.color.text_black));
                }
            }
        };
        mTagAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < mTagList.size(); i++) {
                    if (position == i) {
                        if (mTagList.get(i).isSeclted()) {
                            mTagList.get(i).setSeclted(false);
                            mVa.setVisibility(View.GONE);
                        } else {
                            mVa.setVisibility(View.VISIBLE);
                            mTagList.get(i).setSeclted(true);
                            getTagByTypeid(mTagList.get(i).getId());
                            mVa.setDisplayedChild(0);
                        }
                    } else {
                        mTagList.get(i).setSeclted(false);
                    }
                    mTagAdapter.notifyDataSetChanged();
                }
            }
        });
        mRvSearch.setAdapter(mTagAdapter);
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }


    /**
     * ??????
     */
    private void initListener() {
        mEtChat.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    // do something
                    LogUtils.e("????????????");
                    String question = mEtChat.getText().toString().trim();
                    mEtChat.setText("");
                    RobotMultipleItem bean = new RobotMultipleItem(1);
                    bean.setContent(question);
                    mChatList.add(bean);
                    mChatAdapter.notifyDataSetChanged();
                    mRvChat.scrollToPosition(mChatAdapter.getItemCount() - 1);
                    getQuestion(question);
                    return true;
                }
                return false;
            }
        });

        mVa.setVisibility(View.GONE);
    }

    /**
     * ????????????????????????
     */
    private void initChatAdapter() {
        mRvChat.setLayoutManager(new LinearLayoutManager(this));
        mChatList = new ArrayList<>();
        mChatAdapter = new RobotMultipleAdapter(mChatList, this);
        mRvChat.setAdapter(mChatAdapter);
    }


    /**
     * ???????????????????????????
     */
    private void getRobotTagList() {
        RetrofitHelper.getApiService().findRobotTypeList().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                if (response.code() == SUCCESS_CODE) {
                    try {
                        String result = response.body().string();
                        LogUtils.e("????????????" + result);
                        try {
                            JSONObject object = JSONObject.parseObject(result);
                            JSONArray datasArray = object.getJSONArray("datas");
                            for (int i = 0; i < datasArray.size(); i++) {
                                RobotTag tag = new RobotTag();
                                JSONObject obj = datasArray.getJSONObject(i);
                                tag.setName(obj.getString("name"));
                                tag.setId(obj.getIntValue("id"));
                                tag.setSeclted(false);
                                mTagList.add(tag);
                            }
                            mTagAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * ??????????????????????????????
     */
    private void getTagByTypeid(int typeId) {
        RetrofitHelper.getApiService().findQuestionByTypes(typeId + "").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                if (response.code() == SUCCESS_CODE) {
                    try {
                        String result = response.body().string();
                        JSONObject object = JSONObject.parseObject(result.toString());
                        if (object.getIntValue("code") == 0) {
                            JSONArray datasArr = object.getJSONArray("datas");
                            mTypeTagList.clear();
                            for (int i = 0; i < datasArr.size(); i++) {
                                JSONObject obj = datasArr.getJSONObject(i);
                                RobotTypeTag bean = new RobotTypeTag();
                                bean.setAnswer(obj.getString("answer"));
                                bean.setContent(obj.getString("content"));
                                bean.setQuestion(obj.getString("question"));
                                mTypeTagList.add(bean);
                            }
                            initBottomAdapter();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    /**
     * ??????
     */
    private void initBottomAdapter() {
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        mRvBottom.setLayoutManager(flowLayoutManager);
        mTypeAdapter = null;
        mTypeAdapter = new BaseQuickAdapter<RobotTypeTag, BaseViewHolder>(R.layout.item_tag_robot
                , mTypeTagList) {
            @Override
            protected void convert(BaseViewHolder helper, RobotTypeTag item) {
                helper.setText(R.id.tag, item.getQuestion());
                if (item.isSected()) {
                    helper.setBackgroundRes(R.id.tag, R.drawable.shape_search_main);
                    helper.setTextColor(R.id.tag, getResources().getColor(R.color.white));
                } else {
                    helper.setBackgroundRes(R.id.tag, R.drawable.shape_search);
                    helper.setTextColor(R.id.tag, getResources().getColor(R.color.text_black));
                }

            }
        };
        mTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < mTypeTagList.size(); i++) {
                    if (position == i) {
                        mTypeTagList.get(i).setSected(true);
                        RobotMultipleItem bean = new RobotMultipleItem(1);
                        bean.setContent(mTypeTagList.get(i).getQuestion());
                        RobotMultipleItem bean2 = new RobotMultipleItem(2);
                        bean2.setContent(mTypeTagList.get(i).getContent());
                        bean2.setAnswerxq(mTypeTagList.get(i).getAnswer());
                        mChatList.add(bean);
                        mChatList.add(bean2);
                        //                        SpeechSynthesizer.shareInstance().start
                        // (mTypeTagList.get(i).getContent());
                        speechSynthesizer.startSpeaking(mTypeTagList.get(i).getContent(), listener);

                        mChatAdapter.notifyDataSetChanged();
                        mRvChat.scrollToPosition(mChatAdapter.getItemCount() - 1);
                        mVa.setDisplayedChild(0);
                    } else {
                        mTypeTagList.get(i).setSected(false);
                    }
                    mTypeAdapter.notifyDataSetChanged();
                    if (mTagList.size() > 0) {
                        for (int j = 0; j < mTagList.size(); j++) {
                            mTagList.get(j).setSeclted(false);
                        }
                        mTagAdapter.notifyDataSetChanged();
                    }
                    mVa.setVisibility(View.GONE);
                }
            }
        });
        mRvBottom.setAdapter(mTypeAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(RobotChangeEty msg) {
        try {
            changeScenic(msg.getQuestion(), msg.getmPage(), msg.getmPostion());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ?????????
     */
    private void changeScenic(final String question, final int page, final int postion) {
        OkHttpUtils.get().url(ProjectConfig.BASE_URL + URLConstant.FIND_QUESTION_NEW).addParams(
                "lang", ProjectConfig.LANG).addParams("siteCode", ProjectConfig.SITECODE).addParams("question", question).addParams("page", page + "").addParams("limitPage", mPageSize + "").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    LogUtils.e("???????????????-->" + response);
                    JSONObject object = JSONObject.parseObject(response);
                    JSONObject data = object.getJSONObject("data");
                    JSONArray answersArr = data.getJSONArray("robotAnswers");
                    JSONObject otherObj = data.getJSONObject("other");
                    JSONArray rowsArr = otherObj.getJSONArray("rows");
                    if (rowsArr.size() > 0) {
                        RobotMultipleItem bean = mChatList.get(postion);
                        bean.getmScenicList().clear();
                        List<RobotMultipleItem.ScenicType> mlist = new ArrayList<>();
                        for (int i = 0; i < rowsArr.size(); i++) {
                            JSONObject obj = rowsArr.getJSONObject(i);
                            RobotMultipleItem.ScenicType scenicType =
                                    new RobotMultipleItem.ScenicType();
                            scenicType.setContent(obj.getString("name"));
                            scenicType.setId(obj.getString("id"));
                            scenicType.setType(obj.getString("type"));
                            scenicType.setAdress(obj.getString("address"));
                            scenicType.setLat(obj.getString("latitude"));
                            scenicType.setLng(obj.getString("longitude"));
                            scenicType.setPhone(obj.getString("phone"));
                            mlist.add(scenicType);
                        }
                        bean.setCurentPage(page);
                        bean.setCurentQuestion(question);
                        bean.setmScenicList(mlist);
                        mChatAdapter.notifyItemChanged(postion);
                        mRvChat.scrollToPosition(postion);
                    } else {
                        ToastUtils.showShortCenter("????????????");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

    /**
     * ????????????
     *
     * @param question
     * question=%E4%B9%8C%E9%B2%81%E6%9C%A8%E9%BD%90&siteCode=wlmwwz&lang=cn&token=2cf58f45-e360
     * -4ddp_4-8fb8-3022df9356d9
     */
    private int mPage = 1;
    private int mPageSize = 3;

    private void getQuestion(final String question) {
        OkHttpUtils.get().url(ProjectConfig.BASE_URL + URLConstant.FIND_QUESTION_NEW).addParams(
                "lang", ProjectConfig.LANG).addParams("siteCode", ProjectConfig.SITECODE).addParams("question", question).addParams("page", mPage + "").addParams("limitPage", mPageSize + "").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    LogUtils.e("???????????????-->" + response);
                    JSONObject object = JSONObject.parseObject(response);
                    JSONObject data = object.getJSONObject("data");
                    JSONArray answersArr = data.getJSONArray("robotAnswers");
                    JSONObject otherObj = data.getJSONObject("other");
                    JSONArray rowsArr = otherObj.getJSONArray("rows");
                    // ??????????????????
                    if (answersArr.size() > 0 && Utils.isnotNull(answersArr.getJSONObject(0).getString("content"))) {
                        RobotMultipleItem bean = new RobotMultipleItem(2);
                        bean.setContent(answersArr.getJSONObject(0).getString("content"));
                        bean.setAnswerxq(answersArr.getJSONObject(0).getString("answer"));
                        bean.setContentType(0);
                        speechSynthesizer.startSpeaking(answersArr.getJSONObject(0).getString(
                                "content"), listener);
                        mChatList.add(bean);
                        mChatAdapter.notifyDataSetChanged();
                        mRvChat.scrollToPosition(mChatAdapter.getItemCount() - 1);
                    } else {
                        if (rowsArr.size() > 0) {
                            RobotMultipleItem bean = new RobotMultipleItem(2);
                            bean.setContentType(1);
                            List<RobotMultipleItem.ScenicType> mlist = new ArrayList<>();
                            boolean isResources = true;
                            for (int i = 0; i < rowsArr.size(); i++) {
                                JSONObject obj = rowsArr.getJSONObject(i);
                                RobotMultipleItem.ScenicType scenicType =
                                        new RobotMultipleItem.ScenicType();
                                if (i == 0) {
                                    try {
                                        isResources = obj.getBooleanValue("isResources");
                                    } catch (Exception e) {
                                        isResources = true;
                                        e.printStackTrace();
                                    }
                                }
                                scenicType.setContent(obj.getString("name"));
                                scenicType.setId(obj.getString("id"));
                                scenicType.setType(obj.getString("type"));
                                scenicType.setAdress(obj.getString("address"));
                                scenicType.setLat(obj.getString("latitude"));
                                scenicType.setLng(obj.getString("longitude"));
                                scenicType.setPhone(obj.getString("phone"));
                                scenicType.setQuestion(question);
                                mlist.add(scenicType);
                            }
                            bean.setResources(isResources);
                            bean.setNickname(nikname);
                            bean.setCurentPage(1);
                            bean.setCurentQuestion(question);
                            bean.setmScenicList(mlist);
                            mChatList.add(bean);
                            mChatAdapter.notifyDataSetChanged();
                            mRvChat.scrollToPosition(mChatAdapter.getItemCount() - 1);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

    /**
     * ?????????????????????????????????
     */
    InitListener initListener = new InitListener() {
        @Override
        public void onInit(int i) {
            Log.d("RobotActivity", "onInit: ???????????????");
        }
    };


    /**
     * ?????????????????????????????????
     */
    private Boolean initIfly() {
        try {
            // ??????????????????UI????????????
            // ??????SpeechRecognizer????????????????????????????????????????????????
            mIat = SpeechRecognizer.createRecognizer(RobotActivity.this, initListener);
            // ????????????ID??? SUBJECT ???????????????????????????????????????????????????????????????????????????????????????????????????????????? DEMO ????????????
            mIat.setParameter(SpeechConstant.CLOUD_GRAMMAR, null);
            mIat.setParameter(SpeechConstant.SUBJECT, null);
            // ???????????????????????????????????????json,xml??????plain ?????????????????????plain????????????????????????
            mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");
            // ??????engineType??????cloud???
            mIat.setParameter(SpeechConstant.ENGINE_TYPE, "cloud");
            // ???????????????????????????zh_cn???????????????
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // ????????????????????????
            mIat.setParameter(SpeechConstant.ACCENT, "zh_cn");
            // ?????????????????????:???????????????????????????ms??????????????????????????????????????????????????????
            // ????????????{1000???10000}
            mIat.setParameter(SpeechConstant.VAD_BOS, "4000");
            // ?????????????????????:????????????????????????????????????ms???????????????????????????????????????????????????????????????
            // ???????????????????????????{0~10000}
            mIat.setParameter(SpeechConstant.VAD_EOS, "1000");
            // ??????????????????,?????????"0"?????????????????????,?????????"1"?????????????????????
            mIat.setParameter(SpeechConstant.ASR_PTT, "1");
            return false;
        }catch (Exception e){
            Toast.makeText(mContext, "???????????????????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
            return true;
        }

    }

    /**
     * ?????????????????????
     */
    public void initSpeechSynthesizer() {
        speechSynthesizer = SpeechSynthesizer.createSynthesizer(this, initListener);
    }

    /**
     * ??????????????????????????????
     */
    SynthesizerListener listener = new SynthesizerListener() {
        @Override
        public void onSpeakBegin() {

        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }

        @Override
        public void onCompleted(SpeechError speechError) {

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };

    /**
     * ???????????????
     */
    private void initVoice() {
        for (int i = 0; i < 6; i++) {
            ImageView view = new ImageView(this);
            view.setImageResource(R.mipmap.icon_audio);
            view_record.addViewInQueue(view);
        }
        view_record.setScrollSpeed(8);
        view_record.setScrollDirection(MarqueeView.LEFT_TO_RIGHT);
        view_record.setViewMargin(15);
    }


    private StringBuffer voiceText = new StringBuffer();
    RecognizerListener recognizerListener = new RecognizerListener() {
        @Override
        public void onVolumeChanged(int i, byte[] bytes) {
        }

        @Override
        public void onBeginOfSpeech() {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            LogUtils.e("--------------");
            Gson gson = new Gson();
            XfVoiceBean xfVoiceBean = gson.fromJson(recognizerResult.getResultString(),
                    XfVoiceBean.class);
            for (int i = 0; i < xfVoiceBean.getWs().size(); i++) {
                for (int i1 = 0; i1 < xfVoiceBean.getWs().get(i).getCw().size(); i1++) {
                    voiceText.append(xfVoiceBean.getWs().get(i).getCw().get(i1).getW());
                }
            }

            if (b && IS_SEND) {
                LogUtils.e(voiceText.toString());
                getQuestion(voiceText.toString().trim());
                RobotMultipleItem bean = new RobotMultipleItem(1);
                bean.setContent(voiceText.toString().trim());
                mChatList.add(bean);
                mChatAdapter.notifyDataSetChanged();
                mRvChat.scrollToPosition(mChatAdapter.getItemCount() - 1);
                voiceText.setLength(0);
            }


        }

        @Override
        public void onError(SpeechError speechError) {
            LogUtils.e(speechError.toString());
            if (IS_SEND) {
                RobotMultipleItem bean = new RobotMultipleItem(2);
                bean.setContent("??????????????????,???????????????!");
                mChatList.add(bean);
                mChatAdapter.notifyDataSetChanged();
                mRvChat.scrollToPosition(mChatAdapter.getItemCount() - 1);
            }
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };

    /**
     * ??????id?????????????????????
     *
     * @param id
     * @param type
     */
    private void getAnswerById(final String id, final String type, final String lat,
                               final String lng, final String phone, final String adress,
                               final String content, final String title) {
        OkHttpUtils.get().url(ProjectConfig.BASE_URL + URLConstant.FIND_QUESTION_BY_ID).addParams("lang", ProjectConfig.LANG).addParams("siteCode", ProjectConfig.SITECODE).addParams("id", id).addParams("type", type).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(e.toString());
            }

            @Override
            public void onResponse(String response, int ids) {
                try {
                    RobotMultipleItem bean = new RobotMultipleItem(2);
                    bean.setContentType(2);
                    bean.setCurentQuestion(content);
                    List<RobotMultipleItem.ContentType> mList = new ArrayList<>();

                    JSONObject object = JSONObject.parseObject(response);
                    // ?????????????????????????????????????????????
                    if (object.getIntValue("code") == 0) {
                        JSONObject data = object.getJSONObject("data");

                        if (Utils.isnotNull(data.getString("mapGuideSet"))) {
                            RobotMultipleItem.ContentType contentType =
                                    new RobotMultipleItem.ContentType();
                            contentType.setName("????????????");
                            contentType.setType(0);
                            contentType.setmScenicType(type);
                            contentType.setMapGuideSet(data.getString("mapGuideSet"));
                            mList.add(contentType);

                        }
                        if (Utils.isnotNull(data.getString("fullAddress"))) {
                            RobotMultipleItem.ContentType contentType1 =
                                    new RobotMultipleItem.ContentType();
                            contentType1.setName("720");
                            contentType1.setType(1);
                            contentType1.setmScenicType(type);
                            contentType1.setFullAddress(data.getString("fullAddress"));
                            mList.add(contentType1);
                        }
                        if (Utils.isnotNull(data.getString("monitor"))) {
                            RobotMultipleItem.ContentType contentType2 =
                                    new RobotMultipleItem.ContentType();
                            contentType2.setName("????????????");
                            contentType2.setMonitor(data.getString("monitor"));
                            contentType2.setType(2);
                            contentType2.setmScenicType(type);
                            mList.add(contentType2);
                        }
                    }
                    // ????????????????????????????????????
                    if (Utils.isnotNull(lat) && Utils.isnotNull(lng) && Utils.isnotNull(adress)) {
                        RobotMultipleItem.ContentType contentType3 =
                                new RobotMultipleItem.ContentType();
                        contentType3.setName("?????????");
                        contentType3.setType(3);
                        contentType3.setmScenicType(type);
                        contentType3.setAdress(adress);
                        contentType3.setLat(lat);
                        contentType3.setLng(lng);
                        mList.add(contentType3);
                    }
                    if (Utils.isnotNull(phone)) {
                        RobotMultipleItem.ContentType contentType4 =
                                new RobotMultipleItem.ContentType();
                        contentType4.setType(4);
                        contentType4.setmScenicType(type);
                        contentType4.setName("?????????");
                        contentType4.setPhone(phone);
                        mList.add(contentType4);
                    }
                    if (Utils.isnotNull(lat) && Utils.isnotNull(lng)) {
                        RobotMultipleItem.ContentType contentType5 =
                                new RobotMultipleItem.ContentType();
                        contentType5.setType(5);
                        contentType5.setmScenicType(type);
                        contentType5.setLat(lat);
                        contentType5.setLng(lng);
                        contentType5.setAdress(adress);
                        contentType5.setContent(title);
                        contentType5.setName("?????????");
                        mList.add(contentType5);
                    }
                    if (type.equals("sourceType_1") || type.equals("sourceType_2") || type.equals("sourceType_9") || type.equals("sourceType_8")) {
                        RobotMultipleItem.ContentType contentType6 =
                                new RobotMultipleItem.ContentType();
                        contentType6.setType(6);
                        contentType6.setmScenicType(type);
                        contentType6.setId(id);
                        contentType6.setName("????????????");
                        mList.add(contentType6);
                    }
                    bean.setmContentTypeList(mList);
                    mChatList.add(bean);
                    mChatAdapter.notifyDataSetChanged();
                    mRvChat.scrollToPosition(mChatAdapter.getItemCount() - 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

    /**
     * ?????????????????????
     */
    private void getRobotInfo() {
        RetrofitHelper.getApiService().robotInfo().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                if (response.code() == SUCCESS_CODE) {
                    try {
                        String result = response.body().string();
                        LogUtils.e(result);
                        try {
                            JSONObject object = JSONObject.parseObject(result);
                            JSONObject data = object.getJSONObject("data");
                            RobotMultipleItem bean = new RobotMultipleItem(2);
                            bean.setContent(data.getString("greeting"));
                            nikname = data.getString("nickName");
                            SPUtils.getInstance().put(SPCommon.ROBOT_IMG, data.getString("logo"));
                            //                                    SpeechSynthesizer
                            // .shareInstance().start(data.getString
                            //                                            ("greeting"));
                            speechSynthesizer.startSpeaking(data.getString("greeting"), listener);
                            mChatList.add(bean);
                            mChatAdapter.notifyDataSetChanged();
                            mRvChat.scrollToPosition(mChatAdapter.getItemCount() - 1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private boolean isOpen = true;

    @OnClick({R.id.tv_audiostart, R.id.iv_robot_voice, R.id.rv_chat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_audiostart:
                if (isAudioStart) {
                    isAudioStart = false;
                    mIat.stopListening();
                    mTvStart.setText("??????");
                    tvAudioing.setText("??????????????????");
                    view_record.stopScroll();
                    mVa.setVisibility(View.GONE);
                } else {
                    view_record.startScroll();
                    isAudioStart = true;
                    tvAudioing.setText("?????????...");
                    mIat.startListening(recognizerListener);
                    mTvStart.setText("??????");
                }
                break;
            case R.id.iv_robot_voice:
                if (ivRobotVoice.isSelected()) {
                    ivRobotVoice.setSelected(false);
                    mEtChat.setText("");
                    mEtChat.setHint("??????????????????????????????");
                } else {
                    ivRobotVoice.setSelected(true);
                    mEtChat.setText("???????????????");
                }
                break;
            case R.id.rv_chat:
                mVa.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(speechSynthesizer!=null){
            speechSynthesizer.resumeSpeaking();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(speechSynthesizer!=null){
            speechSynthesizer.pauseSpeaking();
        }
    }

    /**
     * ????????????????????????????????????
     */
    public void inputTouch() {
        /**
         * ?????????????????????
         */
        BaseDialog dialog = ShowDialog.showVoiceDialog(this);
        mEtChat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (ivRobotVoice.isSelected()) {
                    // ???????????????
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        dialog.show();
                        mEtChat.setText("????????????");
                        view_record.startScroll();
                        isAudioStart = true;
                        mIat.startListening(recognizerListener);
                        return true;
                    } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        // ??????????????????
                        mEtChat.setText("???????????????");
                        // ??????????????????????????????
                        LogUtils.e(motionEvent.getY() + "-----------");
                        if (motionEvent.getY() > HEIGHT_MOVE) {
                            IS_SEND = true;
                        } else {
                            IS_SEND = false;
                        }
                        mIat.stopListening();
                        dialog.dismiss();
                        isAudioStart = false;
                        view_record.stopScroll();
                        mVa.setVisibility(View.GONE);
                        return true;
                    }
                }
                return false;
            }
        });
    }

}