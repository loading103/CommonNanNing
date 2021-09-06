package com.daqsoft.voice;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author 严博
 * @version 1.0.0
 * @date 2019/3/19.
 * @since JDK 1.8
 * 异步语音
 */
public class AudioAsyncTask extends AsyncTask<String, Void, Void> {
    private VoiceFileUtils audioFile;

    public AudioAsyncTask(VoiceFileUtils audioFile) {
        this.audioFile = audioFile;
    }

    @Override
    protected Void doInBackground(String... params) {
        HttpURLConnection conn = null;
        try {
            // 构建URL
            URL url = new URL(params[0]);
            // 构造网络连接
            conn = (HttpURLConnection) url.openConnection();
            // 保存音频文件
            audioFile.exists(params[0]);
            audioFile.saveFile(conn.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert conn != null;
            // 断开网络连接
            conn.disconnect();
        }
        return null;
    }
}