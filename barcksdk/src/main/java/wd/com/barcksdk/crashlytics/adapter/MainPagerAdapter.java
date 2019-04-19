/*
 * *
 *  * Created by Mayank Paryani on 11/4/19 7:14 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 4/4/19 12:24 PM
 *
 */

package wd.com.barcksdk.crashlytics.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import wd.com.barcksdk.crashlytics.ui.CrashLogFragment;
import wd.com.barcksdk.crashlytics.ui.ExceptionLogFragment;

/**
 * Created by
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    private CrashLogFragment crashLogFragment;
    private ExceptionLogFragment exceptionLogFragment;
    private String[] titles;

    public MainPagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return crashLogFragment = new CrashLogFragment();
        } else if (position == 1) {
            return exceptionLogFragment = new ExceptionLogFragment();
        } else {
            return new CrashLogFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    public void clearLogs() {
        crashLogFragment.clearLog();
        exceptionLogFragment.clearLog();
    }
}