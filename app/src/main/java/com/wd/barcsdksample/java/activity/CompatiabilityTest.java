/*
 * *
 *  * Created by Mayank Paryani on 11/4/19 7:32 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/4/19 7:28 PM
 *
 */

package com.wd.barcsdksample.java.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.wd.barcsdksample.R;
import com.wd.barcsdksample.kotlin.TestSDKKotlinClass;

public class CompatiabilityTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compatiability_test);

        findViewById(R.id.JavaClass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CompatiabilityTest.this, new TestSDKJavaClass().testcall(), Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.KotlinClass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CompatiabilityTest.this, new TestSDKKotlinClass().testcall(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
