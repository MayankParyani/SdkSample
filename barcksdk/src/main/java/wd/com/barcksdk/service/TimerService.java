/*
 * *
 *  * Created by Mayank Paryani on 11/4/19 7:21 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/4/19 7:17 PM
 *
 */

package wd.com.barcksdk.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class TimerService extends Service {

    public static final int notify = 5000;  //interval between two services(Here Service run every 5 Minute)
    private Handler mHandler = new Handler();   //run on another Thread to avoid crash
    private Timer mTimer = null;    //timer handling
    private boolean isServiceActive;

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        if (mTimer != null) // Cancel if already existed
        {
            mTimer.cancel();
        } else {
            mTimer = new Timer();   //recreate new
        }
        mTimer.scheduleAtFixedRate(new TimeDisplay(), 0, notify);   //Schedule task
        isServiceActive = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();    //For Cancel Timer
        Toast.makeText(this, "Service is Destroyed", Toast.LENGTH_SHORT).show();
        isServiceActive = false;
    }

    //class TimeDisplay for handling task
    class TimeDisplay extends TimerTask {
        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    // display toast
                    //Toast.makeText(TimerService.this, "Service is running", Toast.LENGTH_SHORT).show();
/*                    if (!ServiceController.isAppIsInBackground()) {
                        // APP IS IN FOREGROUND!
                        Toast.makeText(TimerService.this, "Service is running", Toast.LENGTH_SHORT).show();
                        if (!isServiceActive) {
                            if (ServiceController.applicationContext != null) {
                                new ServiceController().initialize(ServiceController.applicationContext);
                            }
                        }
                    } else {
                        //ServiceController.stopService();
                    }*/
                }
            });
        }
    }
}