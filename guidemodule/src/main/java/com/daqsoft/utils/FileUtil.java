package com.daqsoft.utils;

import android.text.TextUtils;

import java.io.File;
import java.text.DecimalFormat;

/**
 * 文件工具类
 * @author MouJunFeng
 * @time 2018-3-14
 * @since JDK 1.8
 * @version 1.0.0
 */

public class FileUtil {
    /**
     * 判断SD卡是否存在
     *
     * @return boolean
     */
    public static boolean checkSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否有文件
     * @param path
     * @return
     */
    public static boolean hasFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        File file = new File(path);
        return file.exists();
    }

    /**
     * 获取文件的大小
     *
     * @param fileSize 文件的大小
     * @return
     */
    public static String FormetFileSize(int fileSize) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileSize < 1024) {
            fileSizeString = df.format((double) fileSize) + "B";
        } else if (fileSize < 1048576) {
            fileSizeString = df.format((double) fileSize / 1024) + "K";
        } else if (fileSize < 1073741824) {
            fileSizeString = df.format((double) fileSize / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileSize / 1073741824) + "G";
        }
        return fileSizeString;
    }
    //获得文件名称
    public static String getFileName(String filePath){
        File file = new File(filePath);
        if(file.exists()){
            return file.getName();
        }
        return "";
    }
}
