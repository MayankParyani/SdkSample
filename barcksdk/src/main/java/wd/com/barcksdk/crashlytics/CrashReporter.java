/*
 * *
 *  * Created by Mayank Paryani on 11/4/19 7:19 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/4/19 7:17 PM
 *
 */

package wd.com.barcksdk.crashlytics;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import wd.com.barcksdk.crashlytics.ui.CrashReporterActivity;
import wd.com.barcksdk.crashlytics.utils.CrashReporterExceptionHandler;
import wd.com.barcksdk.crashlytics.exception.CrashReporterNotInitializedException;
import wd.com.barcksdk.crashlytics.utils.CrashUtil;

public class CrashReporter {

    private static Context applicationContext;

    private static String crashReportPath;

    private static boolean isNotificationEnabled = true;

    private CrashReporter() {
        // This class in not publicly instantiable
    }

    public static void initialize(Context context) {
        Log.d("BarcSDK", "Crash Reporter Initialized");
        applicationContext = context;
        setUpExceptionHandler();
    }

    public static void initialize(Context context, String crashReportSavePath) {
        applicationContext = context;
        crashReportPath = crashReportSavePath;
        setUpExceptionHandler();
    }

    private static void setUpExceptionHandler() {
        if (!(Thread.getDefaultUncaughtExceptionHandler() instanceof CrashReporterExceptionHandler)) {
            Thread.setDefaultUncaughtExceptionHandler(new CrashReporterExceptionHandler());
        }
    }

    public static Context getContext() {
        if (applicationContext == null) {
            try {
                throw new CrashReporterNotInitializedException("Initialize CrashReporter : call CrashReporter.initialize(context, crashReportPath)");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return applicationContext;
    }

    public static String getCrashReportPath() {
        return crashReportPath;
    }

    public static boolean isNotificationEnabled() {
        return isNotificationEnabled;
    }

    //LOG Exception APIs
    public static void logException(Exception exception) {
        CrashUtil.logException(exception);
    }

    public static Intent getLaunchIntent() {
        return new Intent(applicationContext, CrashReporterActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    public static void disableNotification() {
        isNotificationEnabled = false;
    }
}
