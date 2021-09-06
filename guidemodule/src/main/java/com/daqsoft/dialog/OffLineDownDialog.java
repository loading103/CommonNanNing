package com.daqsoft.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daqsoft.guide.R;
import com.daqsoft.utils.FileUtil;
import com.daqsoft.utils.ZipUtils;
import com.daqsoft.view.TaskDownLoadView;

import java.io.File;

/**
 * 离线下载提示框
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-3-14
 * @since JDK 1.8
 */

public class OffLineDownDialog extends Dialog {

    protected OffLineDownDialog(Context context) {
        super(context);
    }


    protected OffLineDownDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public OffLineDownDialog(@NonNull Context context, boolean cancelable, @Nullable
            OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * 构造器
     */
    public static class Builder {
        /**
         * 上下文
         */
        private Context context;
        /**
         * 音频地址
         */
        private String audoUrl;
        /**
         * 地图地址
         */
        private String mapUrl;
        /**
         * 保存音频地址
         */
        private String saveAudioPath;
        /**
         * 保存地图地址
         */
        private String saveMapPath;
        /**
         * 保存压缩文件地址
         */
        private String unZipPath;


        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置音频地址
         *
         * @param audoUrl
         * @return
         */
        public Builder setAudoUrl(String audoUrl) {
            this.audoUrl = audoUrl;
            return this;
        }

        /**
         * 设置地图地址
         *
         * @param mapUrl
         * @return
         */
        public Builder setMapUrl(String mapUrl) {
            this.mapUrl = mapUrl;
            return this;
        }

        /**
         * 设置保存音频路径
         *
         * @param saveAudioPath
         * @return
         */
        public Builder setSaveAudioPath(String saveAudioPath) {
            this.saveAudioPath = saveAudioPath;
            return this;
        }

        /**
         * 设置保存地图路径
         *
         * @param saveMapPath
         * @return
         */
        public Builder setSaveMapPath(String saveMapPath) {
            this.saveMapPath = saveMapPath;
            return this;
        }

        /**
         * 设置zip解析保存地址
         *
         * @param unZipPath
         * @return
         */
        public Builder setUnzipParh(String unZipPath) {
            this.unZipPath = unZipPath;
            return this;
        }

        /**
         * 创建dialog
         *
         * @return
         */
        public OffLineDownDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
                    .LAYOUT_INFLATER_SERVICE);
            final OffLineDownDialog dialog = new OffLineDownDialog(context, R.style.MyDialog);
            View v = inflater.inflate(R.layout.guide_dialog_offline_down, null);
            dialog.addContentView(v, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams
                    .WRAP_CONTENT));
            final TextView txt_audioFinish = (TextView) v.findViewById(R.id.txt_audioFinish);
            final TextView txt_mapFinish = (TextView) v.findViewById(R.id.txt_mapFinish);
            final TaskDownLoadView vAudio = (TaskDownLoadView) v.findViewById(R.id.voiceDownLoad);
            LinearLayout voiceLL = (LinearLayout) v.findViewById(R.id.voice_down_ll);
            if (TextUtils.isEmpty(audoUrl)) {
                voiceLL.setVisibility(View.GONE);
            }
            final ImageView ivClose = (ImageView) v.findViewById(R.id
                    .iv_guide_dialog_offline_down_close);
            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            if (FileUtil.hasFile(saveAudioPath)) {
                vAudio.setVisibility(View.GONE);
                txt_audioFinish.setVisibility(View.VISIBLE);
                txt_audioFinish.setText("下载完成");
            }
            vAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(audoUrl)) {
                        Toast.makeText(context, "没有获得到文件下载路径", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (vAudio.isDownLoad()) {
                        vAudio.onPause();
                        return;
                    }
                    vAudio.onStart(audoUrl, saveAudioPath);
                }
            });
            vAudio.setListner(new TaskDownLoadView.StateProgressListner() {
                @Override
                public void onstart() {

                }

                @Override
                public void onPause() {

                }

                @Override
                public void onfinish() {
                    if (TextUtils.isEmpty(saveAudioPath)) {
                        return;
                    }
                    if (!saveAudioPath.endsWith("zip")) {
                        return;
                    }
                    File file = new File(saveAudioPath);
                    if (!file.exists()) {
                        return;
                    }
                    try {
                        ZipUtils.unzipFile(saveAudioPath, unZipPath);
                    } catch (Exception e) {
                        Toast.makeText(context, "下载的文件出错，请重新下载", Toast.LENGTH_SHORT).show();
                        File deleteFile = new File(saveAudioPath);
                        if (deleteFile.exists()) {
                            deleteFile.delete();
                        }
                    }
                    vAudio.setVisibility(View.GONE);
                    txt_audioFinish.setVisibility(View.VISIBLE);
                }

                @Override
                public void onError() {
                    Toast.makeText(context, "下载出错了", Toast.LENGTH_SHORT).show();
                    vAudio.setVisibility(View.VISIBLE);
                    vAudio.setProgress(0);
                    txt_audioFinish.setVisibility(View.GONE);
                }
            });
            final TaskDownLoadView vmap = (TaskDownLoadView) v.findViewById(R.id.customer_map);
            if (FileUtil.hasFile(saveMapPath)) {
                vmap.setVisibility(View.GONE);
                txt_mapFinish.setVisibility(View.VISIBLE);
                txt_mapFinish.setText("下载完成");
            }
            vmap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(mapUrl)) {
                        Toast.makeText(context, "没有获得到文件下载路径", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (vmap.isDownLoad()) {
                        vmap.onPause();
                        return;
                    }
                    vmap.onStart(mapUrl, saveMapPath);
                }
            });
            vmap.setListner(new TaskDownLoadView.StateProgressListner() {
                @Override
                public void onstart() {

                }

                @Override
                public void onPause() {

                }

                @Override
                public void onfinish() {
                    vmap.setVisibility(View.GONE);
                    txt_mapFinish.setVisibility(View.VISIBLE);
                }

                @Override
                public void onError() {
                    Toast.makeText(context, "下载出错了", Toast.LENGTH_SHORT).show();
                    vmap.setVisibility(View.VISIBLE);
                    vmap.setProgress(0);
                    txt_mapFinish.setVisibility(View.GONE);
                }
            });
            return dialog;
        }
    }
}
