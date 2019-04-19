/*
 * *
 *  * Created by Mayank Paryani on 11/4/19 7:23 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/4/19 7:17 PM
 *
 */

package wd.com.barcksdk.crashlytics.utils;

import wd.com.barcksdk.crashlytics.utils.CrashUtil;

public class CrashReporterExceptionHandler implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler exceptionHandler;

    public CrashReporterExceptionHandler() {
        this.exceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {

        CrashUtil.saveCrashReport(throwable);

        exceptionHandler.uncaughtException(thread, throwable);
    }
}
