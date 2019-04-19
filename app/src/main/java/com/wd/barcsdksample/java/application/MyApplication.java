/*
 * *
 *  * Created by Mayank Paryani on 11/4/19 7:32 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/4/19 7:24 PM
 *
 */

package com.wd.barcsdksample.java.application;

import android.app.Application;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import wd.com.barcksdk.crashlytics.utils.CrashUtil;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        if (CrashUtil.getDefaultPath() != null) {
            Log.d("Path", CrashUtil.getDefaultPath());
        }
    }
}
