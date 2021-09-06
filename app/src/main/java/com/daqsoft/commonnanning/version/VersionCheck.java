package com.daqsoft.commonnanning.version;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.daqsoft.android.ProjectConfig;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.version.service.UpdateService;
import com.example.tomasyb.baselib.base.AppManager;
import com.example.tomasyb.baselib.util.LogUtils;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.widget.dialog.EnsureDialog;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 版本检测服务
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018/10/17 0017
 * @since JDK 1.8
 */
public class VersionCheck {

    /**
     * 检查应用版本
     */
    private static String localVersion = "";

    public static void checkUpdate(final Activity context) {
        try {
            PackageInfo packageInfo = context.getApplicationContext().getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        RetrofitHelper.getApiService().checkVersion(ProjectConfig.APPID, "AppVersion", "daqsoft",
                "1", localVersion).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    if (!ObjectUtils.isNotEmpty(result) || "3".equals(result) || "2".equals
                            (result)) {
                        ToastUtils.showShortCenter("新版本检测失败!");
                    } else if ("0".equals(result)) {
                    } else {
                        JSONObject jnewdata = new JSONObject(result);
                        if (jnewdata.get("IsUpdate").toString().equals("1")) {
                            alertUserDown2(context, jnewdata.get("AppUpdateInfo") + "", jnewdata
                                    .get("DownPath").toString().trim(), jnewdata.getString
                                    ("VersionCode") + "");
                        } else {
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShortCenter("新版本检测失败!");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                ToastUtils.showShortCenter("新版本检测失败!");
            }
        });
    }

    /**
     * 弹出确认框
     *
     * @param context
     * @param text
     * @param url
     */
    public static void alertUserDown2(final Activity context, String text, final String url,
                                      String version) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.include_version_window);
        TextView tvContent = (TextView) window.findViewById(R.id.version_content);
        tvContent.setText("有新版本" + version + "，建议更新");
        TextView btnCancel = (TextView) window.findViewById(R.id.version_cancel);
        TextView btnSure = (TextView) window.findViewById(R.id.version_sure);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 开启更新服务UpdateService
                // 这里为了把update更好模块化，可以传一些updateService依赖的值
                // 如布局ID，资源ID，动态获取的标题,这里以app_name为例
                Intent updateIntent = new Intent(context, UpdateService.class);
                updateIntent.putExtra("app_name", context.getResources().getString(R.string
                        .app_name));
                updateIntent.putExtra("updatepath", url);
                context.startService(updateIntent);
                LogUtils.e("下载中...");
                dialog.dismiss();
            }
        });
    }

    /**
     * 弹出确认框
     * @param context
     * @param url
     */
    public static void ApkDown(final Activity context, String name,final String url) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.include_version_window);
        TextView tvContent = (TextView) window.findViewById(R.id.version_content);
        tvContent.setText("是否安装" + name + "？");
        TextView btnCancel = (TextView) window.findViewById(R.id.version_cancel);
        TextView btnSure = (TextView) window.findViewById(R.id.version_sure);
        TextView title = window.findViewById(R.id.version_title);
        title.setText("温馨提示！");
        btnSure.setText("安装");
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 开启更新服务UpdateService
                // 这里为了把update更好模块化，可以传一些updateService依赖的值
                // 如布局ID，资源ID，动态获取的标题,这里以app_name为例
                Intent updateIntent = new Intent(context, UpdateService.class);
                updateIntent.putExtra("app_name", context.getResources().getString(R.string.app_name));
                updateIntent.putExtra("updatepath", url);
                context.startService(updateIntent);
                LogUtils.e("下载中...");
                dialog.dismiss();
            }
        });
    }

    /**
     * 检查版本更新的权限
     *
     * @param activity
     */
    public static void checkUpdatePermission(Activity activity) {
        boolean isGranted = false;
        try{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                isGranted = activity.getPackageManager().canRequestPackageInstalls();
            } else {
                isGranted = true;
            }
            if (isGranted) {
                // 版本检测
                VersionCheck.checkUpdate(activity);
            } else {
                EnsureDialog ensureDialog = new EnsureDialog(AppManager.getAppManager().currentActivity());
                ensureDialog.builder().setTitle("安装应用需要打开未知来源权限，请去设置中开启权限").setPositiveButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri packageURI = Uri.parse("package:" + activity.getPackageName());
                        // 注意这个是8.0新API
                        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                                packageURI);
                        activity.startActivityForResult(intent, 1001);
                        ensureDialog.dismiss();
                    }
                }).setNegativeButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ensureDialog.dismiss();
                    }
                }).show();
            }
        }catch (Exception e){

        }

//            new AlertDialog.Builder(AppManager.getAppManager().currentActivity()).setCancelable
//                    (false).setTitle("安装应用需要打开未知来源权限，请去设置中开启权限").setPositiveButton("确定", new
//                    DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface d, int w) {
//                            Uri packageURI = Uri.parse("package:" + activity.getPackageName());
//                            // 注意这个是8.0新API
//                            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
//                                    packageURI);
//                            activity.startActivityForResult(intent, 1001);
//                        }
//                    }).show();
    }
}

