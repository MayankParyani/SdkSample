/*
 * *
 *  * Created by Mayank Paryani on 11/4/19 7:32 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/4/19 7:28 PM
 *
 */

package com.wd.barcsdksample.java.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wd.barcsdksample.R;

import wd.com.barcksdk.crashlytics.CrashReporter;

public class CrashlyticsTest extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crashlytics_test);

        findViewById(R.id.nullPointer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = null;
                context.getResources(); //This will nullify the object and crash the application
            }
        });

        findViewById(R.id.exception).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ArithmeticException arithmeticException = new ArithmeticException();
                    throw arithmeticException;
                } catch (Exception e) {
                    CrashReporter.logException(e);
                }
            }
        });
    }
}
