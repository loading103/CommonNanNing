package com.daqsoft.commonnanning.http;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.URLConstant;
import com.daqsoft.commonnanning.ui.entity.UpImgEntity;
import com.daqsoft.commonnanning.utils.BitmapUtils;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 图片上传文件上传类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018/10/27 0027
 * @since JDK 1.8
 */
public class FileUpload {

    /**
     * 上传文件
     *
     * @param filePathList
     */
    public static void uploadFile(final Context context, final List<String> filePathList,
                                  final UploadFileBack back) {
        final Activity activity = (Activity) context;
        if (filePathList == null || filePathList.isEmpty()) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    back.result("");
                }
            });
            return;
        }
        final List<UpImgEntity> list = new ArrayList<UpImgEntity>();
        final List<String> resultList = new ArrayList<String>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    for (final String filePath : filePathList) {
                        if (!TextUtils.isEmpty(filePath)) {
                            File tempFile = new File(filePath);
                            if (!tempFile.exists()) {
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        back.result("-1");
                                    }
                                });
                                return;
                            }
                            OkHttpClient client = new OkHttpClient();
                            MultipartBody.Builder builder =
                                    new MultipartBody.Builder().setType(MultipartBody.FORM);
                            MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");
                            String newName =
                                    context.getCacheDir().getAbsolutePath() + File.separator + tempFile.getName();
                            String path = BitmapUtils.compressImage(filePath, newName, 480, 800,
                                    50);
                            if (TextUtils.isEmpty(path)) {
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        back.result("-1");
                                    }
                                });
                                return;
                            }
                            if (TextUtils.isEmpty(path)) {
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        back.result("-1");
                                    }
                                });
                                return;
                            }
                            File file = new File(path);
                            builder.addFormDataPart("path",context.getResources().getString(R.string.app_name));
                            builder.addFormDataPart("key", Constant.OSS_KEY);
                            builder.addFormDataPart("Filedata", file.getName(),
                                    RequestBody.create(MEDIA_TYPE_PNG, file));
                            RequestBody requestBody = builder.build();
                            Request.Builder RequestBuilder = new Request.Builder();
                            // 添加URL地址
                            RequestBuilder.url(URLConstant.FILE_UPLOAD + "/upload");
                            RequestBuilder.post(requestBody);
                            Request request = RequestBuilder.build();
                            String result = client.newCall(request).execute().body().string();
                            Gson gson = new Gson();
                            UpImgEntity bean = gson.fromJson(result, UpImgEntity.class);
                            list.add(bean);
                            resultList.add(bean.getFileUrl());
                            if (bean.getError() != 0) {
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        back.result("-1");
                                    }
                                });
                            }
                            // 判断是否是最后的一次
                            if (list.size() >= filePathList.size()) {
                                final StringBuffer sb = new StringBuffer();
                                for (UpImgEntity tempBean : list) {
                                    sb.append(tempBean.getFileUrl());
                                    sb.append(",");
                                }
                                sb.deleteCharAt(sb.length() - 1);
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        back.result(sb.toString());
                                        back.resultList(resultList);
                                    }
                                });
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            back.result("-1");
                            return;
                        }
                    });
                }
            }
        }).start();

    }

    /**
     * 文件上传回调
     */
    public interface UploadFileBack {
        /**
         * 网络回调字符串
         *
         * @param value
         */
        void result(String value);

        /**
         * 网络回调list
         *
         * @param value
         */
        void resultList(List<String> value);
    }


}
