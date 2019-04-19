/*
 * *
 *  * Created by Mayank Paryani on 11/4/19 7:15 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 4/4/19 12:22 PM
 *
 */

package wd.com.barcksdk.crashlytics.utils;

import android.support.v4.view.ViewPager;

public abstract class SimplePageChangeListener implements ViewPager.OnPageChangeListener {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
    @Override
    public abstract void onPageSelected(int position);
    @Override
    public void onPageScrollStateChanged(int state) {}
}
