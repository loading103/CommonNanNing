package com.daqsoft.commonnanning.http;

import com.daqsoft.commonnanning.base.IApplication;
import com.daqsoft.commonnanning.common.URLConstant;
import com.daqsoft.commonnanning.ui.entity.LocationEntity;
import com.example.tomasyb.baselib.util.LogUtils;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 获取全国地区接口服务
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/3/21 0021
 * @since JDK 1.8
 */
public class LocationService {

    /**
     * 获取地区数据
     */
    public static void getLocation() {
        Request params = null;
        params = new Request.Builder().url(URLConstant.LOCATION)
                .get()
                .build();
        Call call = new OkHttpClient().newCall(params);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.e(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = "";
                if (response.code() == 200) {
                    result = response.body().string();

                    // 用Gson 转成实体
                    ArrayList<LocationEntity> jsonBean = parseData(result);
                    getBeanData(jsonBean);
                }
            }
        });
    }

    /**
     * /**
     * 添加省份数据
     * <p>
     * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
     * PickerView会通过getPickerViewText方法获取字符串显示出来。
     *
     * @param jsonBean
     */
    public static void getBeanData(ArrayList<LocationEntity> jsonBean) {
        // 遍历省份
        for (int i = 0; i < jsonBean.size(); i++) {
            IApplication.options1Items.add(jsonBean.get(i).getName());
            // 该省的城市列表（第二级）
            ArrayList<String> CityList = new ArrayList<>();
            // 该省的所有地区列表（第三极）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();

            if (jsonBean.get(i).getSub().size() < 1) {
                // 添加城市
                CityList.add("无");
                Province_AreaList.add(CityList);
            }
            // 遍历该省份的所有城市
            for (int c = 0; c < jsonBean.get(i).getSub().size(); c++) {
                LocationEntity.SubBeanX subBeanX = jsonBean.get(i).getSub().get(c);
                if (ObjectUtils.isNotEmpty(subBeanX.getName())) {
                    // 添加城市
                    CityList.add(subBeanX.getName());
                }
                // 该城市的所有地区列表
                ArrayList<String> City_AreaList = new ArrayList<>();

                // 如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getSub().get(c).getSub() == null
                        || jsonBean.get(i).getSub().get(c).getSub().size() == 0) {
                    City_AreaList.add("无");
                } else {
                    // 该城市对应地区所有数据
                    for (int d = 0; d < jsonBean.get(i).getSub().get(c).getSub().size();
                         d++) {
                        LocationEntity.SubBeanX.SubBean AreaName = jsonBean.get(i).getSub
                                ().get(c).getSub().get(d);
                        if (ObjectUtils.isNotEmpty(AreaName.getName())) {
                            // 添加该城市所有地区数据
                            City_AreaList.add(AreaName.getName());
                        }

                    }
                }
                // 添加该省所有地区数据
                Province_AreaList.add(City_AreaList);
            }

            /**
             * 添加城市数据
             */
            if (!CityList.toString().contains("[]")) {
                IApplication.options2Items.add(CityList);
            } else {
            }

            /**
             * 添加地区数据
             */
            if (!Province_AreaList.toString().equals("[]")) {
                IApplication.options3Items.add(Province_AreaList);
            } else {
            }
        }
    }

    /**
     * 获取省份的数据
     *
     * @param result
     * @return
     */
    public static ArrayList<LocationEntity> parseData(String result) {
        // Gson 解析
        ArrayList<LocationEntity> detail = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getInt("code") == 0 &&
                    jsonObject.getJSONArray("datas").length() > 0) {
                JSONArray data = jsonObject.getJSONArray("datas");
                Gson gson = new Gson();
                for (int i = 0; i < data.length(); i++) {
                    LocationEntity location = gson.fromJson(data.optJSONObject(i).toString(),
                            LocationEntity.class);
                    detail.add(location);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        IApplication.locationList = detail;
        return detail;
    }
}
