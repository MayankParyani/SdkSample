/*
 * *
 *  * Created by Mayank Paryani on 11/4/19 7:18 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/4/19 4:45 PM
 *
 */

package com.wd.barcsdksample.kotlin

import wd.com.barcksdk.samples.SampleJava

class TestSDKKotlinClass {

     fun testcall(): String {
        return SampleJava().greetings()
    }
}