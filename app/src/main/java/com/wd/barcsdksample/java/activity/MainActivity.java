/*
 * *
 *  * Created by Mayank Paryani on 11/4/19 7:32 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/4/19 7:31 PM
 *
 */

package com.wd.barcsdksample.java.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wd.barcsdksample.R;

import java.io.Console;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.crashlytics).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CrashlyticsTest.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.compatibility).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CompatiabilityTest.class);
                startActivity(intent);
            }
        });
    }
}
