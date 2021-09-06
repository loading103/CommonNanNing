package com.daqsoft.commonnanning.version.receiver;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import com.daqsoft.android.ProjectConfig;
import com.example.tomasyb.baselib.base.AppManager;
import com.example.tomasyb.baselib.util.LogUtils;
import com.example.tomasyb.baselib.util.SPUtils;

import java.io.File;

/**
 * 广播实现自动安装的更新的APK
 * 应用版本更新服务
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018/9/4 0004
 * @since JDK 1.8
 */

public class UpdateBroadcastReceiver extends BroadcastReceiver {

    @SuppressLint("NewApi")
    public void onReceive(Context context, Intent intent) {
        long myDwonloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        long refernece = SPUtils.getInstance().getLong("refernece", 0);
        if (refernece != myDwonloadID) {
            return;
        }

        DownloadManager dManager = (DownloadManager) context.getSystemService(Context
                .DOWNLOAD_SERVICE);
        Uri downloadFileUri = dManager.getUriForDownloadedFile(myDwonloadID);
        installAPK(context, downloadFileUri);
    }

    private void installAPK(Context context, Uri apk) {
        if (Build.VERSION.SDK_INT < 23) {
            Intent intents = new Intent();
            intents.setAction("android.intent.action.VIEW");
            intents.addCategory("android.intent.category.DEFAULT");
            intents.setType("application/vnd.android.package-archive");
            intents.setData(apk);
            intents.setDataAndType(apk, "application/vnd.android.package-archive");
            intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intents);
        } else {
            File file = queryDownloadedApk(context);
            if (file.exists()) {
                openFile(file, context);
            }

        }
    }

    /**
     * 通过downLoadId查询下载的apk，解决6.0以后安装的问题
     *
     * @param context
     * @return
     */
    public static File queryDownloadedApk(Context context) {
        File targetApkFile = null;
        DownloadManager downloader = (DownloadManager) context.getSystemService(Context
                .DOWNLOAD_SERVICE);
        long downloadId = SPUtils.getInstance().getLong("refernece", -1);
        if (downloadId != -1) {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(downloadId);
            query.setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL);
            Cursor cur = downloader.query(query);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    String uriString = cur.getString(cur.getColumnIndex(DownloadManager
                            .COLUMN_LOCAL_URI));
                    if (!TextUtils.isEmpty(uriString)) {
                        targetApkFile = new File(Uri.parse(uriString).getPath());
                    }
                }
                cur.close();
            }
        }
        return targetApkFile;

    }

    @SuppressLint("WrongConstant")
    private void openFile(File file, Context context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startInstallO(context, file);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                startInstallN(context, file);
            } else {
                startInstall(context, file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * android1.x-6.x
     */
    private void startInstall(Context context, File file) throws Exception {
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(install);
    }

    /**
     * android7.x
     */
    private void startInstallN(Context context, File file) throws Exception {
        // 参数1 上下文, 参数2 在AndroidManifest中的android:authorities值, 参数3  共享的文件
        Uri uri = FileProvider.getUriForFile(context, ProjectConfig.AUTHORITIED, file);
        Intent install = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 添加这一句表示对目标应用临时授权该Uri所代表的文件
        install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        install.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(install);
    }

    /**
     * androidp_8.x
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallO(Context context, File file) {
        try {
            boolean isGranted = context.getPackageManager().canRequestPackageInstalls();
            if (isGranted) {
                // 安装应用的逻辑(写自己的就可以)
                startInstallN(context, file);
            } else {
                new AlertDialog.Builder(AppManager.getAppManager().currentActivity())
                        .setCancelable(false).setTitle("安装应用需要打开未知来源权限，请去设置中开启权限")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int w) {
                        Uri packageURI = Uri.parse("package:" + context.getPackageName());
                        // 注意这个是8.0新API
                        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                                packageURI);
                        context.startActivity(intent);
                    }
                }).show();
            }
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }

    }

    private String getMIMEType(File var0) {
        String var1 = "";
        String var2 = var0.getName();
        String var3 = var2.substring(var2.lastIndexOf(".") + 1, var2.length()).toLowerCase();
        var1 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var3);
        return var1;
    }


}
