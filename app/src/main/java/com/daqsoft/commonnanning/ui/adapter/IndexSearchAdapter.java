package com.daqsoft.commonnanning.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSONObject;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.SearchListEntity;
import com.daqsoft.commonnanning.ui.service.TravelAgencyDetailsActivity;
import com.daqsoft.utils.Utils;
import com.library.flowlayout.FlowLayoutManager;

import java.util.ArrayList;
import java.util.List;

import io.agora.yview.photoview.PicturePreviewActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 功能
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-8-30.8:56
 * @since JDK 1.8
 */

public class IndexSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<SearchListEntity> mList;

    public IndexSearchAdapter(Context context) {
        this.context = context;
        mList = new ArrayList<>();
    }

    public void upData(List<SearchListEntity> mList_) {
        if (Utils.isnotNull(mList)) {
            mList.clear();
            for (int i = 0; i < mList_.size(); i++) {
                mList.add(mList_.get(i));
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductHolder(View.inflate(context, R.layout.item_search_content, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ProductHolder productHolder = (ProductHolder) holder;
        SearchListEntity bean = mList.get(position);
        productHolder.title.setText(bean.getTitle());

        final FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        //productHolder.des.addItemDecoration(new SpaceItemDecoration(dp2px(5)));
        productHolder.des.setLayoutManager(flowLayoutManager);
        productHolder.des.setAdapter(new FlowAdapter(bean.getmItemList(), position));
        productHolder.mImgDele.setImageResource(R.mipmap.global_search_arrow_normal);
        productHolder.mImgTag.setImageResource(macthImg(bean.getType()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ProductHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private RecyclerView des;
        private ImageView mImgTag;
        private ImageView mImgDele;

        public ProductHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_search_title);
            des = (RecyclerView) itemView.findViewById(R.id.search_item_rv);
            mImgTag = (ImageView) itemView.findViewById(R.id.img_tag_search);
            mImgDele = (ImageView) itemView.findViewById(R.id.delete_img);
        }
    }

    private int dp2px(float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context
                .getResources().getDisplayMetrics());
    }


    class FlowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<SearchListEntity.SearchItem> list;
        private SearchListEntity.SearchItem selectDes;
        private int parentPostion;

        public FlowAdapter(List<SearchListEntity.SearchItem> list, int parentPostion_) {
            this.list = list;
            this.parentPostion = parentPostion_;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(View.inflate(context, R.layout.flow_item, null));
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            TextView textView = ((MyHolder) holder).text;

            final SearchListEntity.SearchItem des = list.get(position);
            if (des.isSelect()) {
                textView.setBackground(context.getResources().getDrawable(R.drawable
                        .bg_common_solid_main));
                textView.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                textView.setBackground(context.getResources().getDrawable(R.drawable
                        .bg_common_solid_gray));
                textView.setTextColor(context.getResources().getColor(R.color.text_black));
            }
            textView.setText(des.getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (des != selectDes) {
                        if (selectDes != null) {
                            selectDes.setSelect(false);
                        }
                    }
                    //                    des.setSelect(true);
                    if (des.getType().equals("picture") && list.size() > 0) {
                        ArrayList<String> pictureList = new ArrayList<>();
                        for (SearchListEntity.SearchItem item : list) {
                            pictureList.add(item.getImgUrl());
                        }
                        // 图片
                        Intent intent = new Intent(context, PicturePreviewActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("currentPosition", position);
                        bundle.putStringArrayList("imgList", (ArrayList<String>) pictureList);
                        intent.putExtras(bundle);
                        context.startActivity(intent);

                    }
                    selectDes = des;
                    notifyDataSetChanged();
                    saveSearch(des.getType(), des.getName(), des.getId(), Utils.isnotNull(des
                            .getResourcecode()) ? des.getResourcecode() : "", Utils.isnotNull(des
                            .getLeveName()) ? des.getLeveName() : "", Utils.isnotNull(des
                            .getVideoUrl()) ? des.getVideoUrl() : "");

                }
            });
        }

        @Override
        public int getItemCount() {
            return Utils.isnotNull(list) ? list.size() : 0;
        }

        class MyHolder extends RecyclerView.ViewHolder {
            private TextView text;

            public MyHolder(View itemView) {
                super(itemView);
                text = (TextView) itemView.findViewById(R.id.flow_text);
            }
        }
    }

    /**
     * 根据位置设置图片
     */
    private int macthImg(String type) {
        int ResId = 0;
        if (type.equals("sourceType_1")) {
            ResId = R.mipmap.global_search_scenic_spot;
        } else if (type.equals("sourceType_2")) {
            ResId = R.mipmap.global_search_hotel;
        } else if (type.equals("travel_strategy")) {
            ResId = R.mipmap.global_search_raiders;
        } else if (type.equals("sourceType_8")) {
            ResId = R.mipmap.global_search_food;
        } else if (type.equals("destination")) {
            ResId = R.mipmap.global_search_destination;
        } else if (type.equals("picture")) {
            ResId = R.mipmap.global_search_pic;
        } else if (type.equals("video")) {
            ResId = R.mipmap.global_search_video;
        } else if (type.equals("type_news")) {
            ResId = R.mipmap.global_search_news;
        } else if (type.equals("sourceType_3")) {
            ResId = R.mipmap.global_search_travel_agency;
        }
        return ResId;
    }

    /**
     * 根据位置设置图片
     */
    private String macthType(int postion) {
        String type = "";
        switch (postion) {
            case 0:
                // 景区
                type = "sourceType_1";

                break;
            case 1:
                // 酒店
                type = "sourceType_2";
                break;
            case 2:
                /**
                 * 游记攻略
                 */
                type = "travel_strategy";
                break;
            case 3:
                // 美食
                type = "sourceType_8";
                break;
            case 4:
                // 目的地
                type = "destination";
                break;
            case 5:
                // 图片
                type = "picture";
                break;
            case 6:
                // 视频
                type = "video";
                break;
            case 7:
                // 新闻
                type = "type_news";
                break;
            case 8:
                // 旅行社
                type = "sourceType_3";
                break;
        }
        return type;
    }

    /**
     * 保存搜索
     *
     * @param type
     * @param content
     */
    private void saveSearch(final String type, final String content, final String id, final
    String resourcecode, final String levelName, final String videoUrl) {
        RetrofitHelper.getApiService().saveSearch(type, content).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        String result = response.body().string();
                        JSONObject object = JSONObject.parseObject(result.toString());
                        if (object.getIntValue("code") == 0) {
                            if (type.equals("sourceType_1")) {
                                // 景区详情
                                ARouter.getInstance().build(Constant.ACTIVITY_SCENIC_DETAIL)
                                        .withString("mId", id).navigation();
                            } else if (type.equals("sourceType_2")) {
                                // 酒店详情
                                ARouter.getInstance().build(Constant.ACTIVITY_HOTEL_DETAIL)
                                        .withString("mId", id).navigation();
                            } else if (type.equals("travel_strategy")) {
                                // 游记攻略详情
                                ARouter.getInstance().build(Constant.ACTIVITY_LINEDETAIL)
                                        .withString("mId", id).navigation();
                            } else if (type.equals("sourceType_8")) {
                                // 美食详情
                                ARouter.getInstance().build(Constant.ACTIVITY_FOOD_DETIAL)
                                        .withString("ID", id).navigation();
                            } else if (type.equals("destination")) {

                            } else if (type.equals("picture")) {
                                // 图片

                            } else if (type.equals("video")) {
                                // 视频
                                ARouter.getInstance().build(Constant.ACTIVITY_VIDEO_PLAY)
                                        .withString("content", videoUrl).navigation();
                            } else if (type.equals("type_news")) {

                            } else if (type.equals("sourceType_3")) {
                                // 旅行社
                                Intent intent = new Intent(context, TravelAgencyDetailsActivity
                                        .class);
                                intent.putExtra("id", id);
                                context.startActivity(intent);

                            }
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

}
