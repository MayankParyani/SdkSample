/*
 * *
 *  * Created by Mayank Paryani on 11/4/19 7:19 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/4/19 7:17 PM
 *
 */

package wd.com.barcksdk.initializer;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import wd.com.barcksdk.crashlytics.CrashReporter;
import wd.com.barcksdk.exceptions.ApplicationNotInitializedException;
import wd.com.barcksdk.crashlytics.utils.Constants;
import wd.com.barcksdk.exceptions.JsonFileNotFoundException;
import wd.com.barcksdk.service.ServiceController;

public class CrashReporterInitProvider extends ContentProvider {

    public CrashReporterInitProvider() {
    }

    @Override
    public boolean onCreate() {
        if (getContext() == null) {
            try {
                throw new ApplicationNotInitializedException("Initialize Application Object: declare a class which extends Application Base class and declare the same in androidManifest");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        } else {
            try {
                if (checkJsonFileExistense(getContext())) {
                    Log.d("BarcSDK", "Provider Initialized");
                    CrashReporter.initialize(getContext());
                    new ServiceController().initialize(getContext());
                    return true;
                } else {
                    try {
                        throw new JsonFileNotFoundException("Sample Json Missing: Paste Sample.json file inside asset folder to initialize BARC Sdk");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    private boolean checkJsonFileExistense(Context context) throws IOException {
        AssetManager mg = context.getResources().getAssets();
        InputStream is = null;
        try {
            is = mg.open(Constants.CONFIGURATION_FILE);
            if (is != null) {
                //File exists so do something with it
                return true;
            } else {
                //file does not exist
                return false;
            }
        } catch (IOException ex) {
            //file does not exist
            return false;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public void attachInfo(Context context, ProviderInfo providerInfo) {
        if (providerInfo == null) {
            throw new NullPointerException("CrashReporterInitProvider ProviderInfo cannot be null.");
        }
        // So if the authorities equal the library internal ones, the developer forgot to set his applicationId
        if ("wd.com.barcksdk.initializer.CrashReporterInitProvider".equals(providerInfo.authority)) {
            throw new IllegalStateException("Incorrect provider authority in manifest. Most likely due to a "
                    + "missing applicationId variable in application\'s build.gradle.");
        }
        super.attachInfo(context, providerInfo);
    }
}