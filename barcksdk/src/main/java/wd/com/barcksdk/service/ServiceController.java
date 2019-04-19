/*
 * *
 *  * Created by Mayank Paryani on 11/4/19 7:21 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/4/19 7:17 PM
 *
 */

package wd.com.barcksdk.service;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.MobileAds;

import java.util.List;

import wd.com.barcksdk.utility.DeviceUtility;

public class ServiceController extends Activity implements Application.ActivityLifecycleCallbacks {

    public static Context applicationContext;
    private int activityReferences = 0;
    private boolean isActivityChangingConfigurations = false;

    public void initialize(Context context) {
        applicationContext = context;
        MobileAds.initialize(context, "ca-app-pub-3940256099942544~3347511713");
        registerActivityListener();
        startService();
    }

    private void registerActivityListener() {
        Application application = (Application) applicationContext.getApplicationContext();
        if (application != null) {
            application.registerActivityLifecycleCallbacks(this);
        }
    }

    public void unregisterActivityListener() {
        Application application = (Application) applicationContext.getApplicationContext();
        if (application != null) {
            application.unregisterActivityLifecycleCallbacks(this);
        }
    }

    private static void startService() {
        if (applicationContext != null) {
            Intent startServiceIntent = new Intent(applicationContext, TimerService.class);
            applicationContext.startService(startServiceIntent);
        }
    }

    public static void stopService() {
        if (applicationContext != null) {
            Intent startServiceIntent = new Intent(applicationContext, TimerService.class);
            applicationContext.stopService(startServiceIntent);
        }
    }

    private String getApplicationName() {
        ApplicationInfo applicationInfo = getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : getString(stringId);
    }

    public static boolean isAppIsInBackground() {
        if (applicationContext != null) {
            boolean isInBackground = true;
            ActivityManager am = (ActivityManager) applicationContext.getSystemService(Context.ACTIVITY_SERVICE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
                List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
                for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                    if (processInfo != null && processInfo.pkgList != null) {  //processInfo null pointer resolved 02-08-2018
                        if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                            for (String activeProcess : processInfo.pkgList) {
                                if (activeProcess.equals(applicationContext.getPackageName())) {
                                    isInBackground = false;
                                }
                            }
                        }
                    }
                }
            } else {
                List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
                ComponentName componentInfo = taskInfo.get(0).topActivity;
                if (componentInfo.getPackageName().equals(applicationContext.getPackageName())) {
                    isInBackground = false;
                }
            }

            return isInBackground;
        } else {
            return false;
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.d("Lifecycle", "created " + activity.getLocalClassName());
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (++activityReferences == 1 && !isActivityChangingConfigurations) {
            // App enters foreground
/*            Log.d("DeviceCheck", "App Name " + DeviceUtility.getAppName(applicationContext));
            Log.d("DeviceCheck", "Publisher App Package Name " + DeviceUtility.getPackageName(applicationContext));
            Log.d("DeviceCheck", "Publisher App Version " + DeviceUtility.getAppVersion(applicationContext));
            Log.d("DeviceCheck", "SDK Version " + DeviceUtility.getSDKVersion());
            Log.d("DeviceCheck", "Android ID " + DeviceUtility.getSecureID(applicationContext));
            Log.d("DeviceCheck", "Device OS " + DeviceUtility.getDeviceOS());
            Log.d("DeviceCheck", "Device Model " + DeviceUtility.getDeviceModel());
            Log.d("DeviceCheck", "User Agent " + DeviceUtility.getDefaultUserAgent(applicationContext));
            Log.d("DeviceCheck", "MCC " + DeviceUtility.getMCC(applicationContext));
            Log.d("DeviceCheck", "MNC " + DeviceUtility.getMNC(applicationContext));
            Log.d("DeviceCheck", "Carrier Name " + DeviceUtility.getCarrierName(applicationContext));
            Log.d("DeviceCheck", "Ip Address " + DeviceUtility.getIPAddress(applicationContext));
            Log.d("DeviceCheck", "Screen Resolution " + DeviceUtility.getScreenResolution(applicationContext));
            Log.d("DeviceCheck", "OS Name " + DeviceUtility.getOSName(applicationContext));
            Log.d("DeviceCheck", "Network Type " + DeviceUtility.getNetworkType(applicationContext));
            DeviceUtility.getAdIdS(applicationContext);*/
            Log.d("TrackStatus", DeviceUtility.getAppName(applicationContext) + "App Comes to Foreground");
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        isActivityChangingConfigurations = activity.isChangingConfigurations();
        if (--activityReferences == 0 && !isActivityChangingConfigurations) {
            // App enters background
            Log.d("TrackStatus", DeviceUtility.getAppName(applicationContext) + "App Goes to Background");
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.d("Lifecycle", "resumed " + activity.getLocalClassName());
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.d("Lifecycle", "paused " + activity.getLocalClassName());
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.d("Lifecycle", "destroyed " + activity.getLocalClassName());
    }
}
