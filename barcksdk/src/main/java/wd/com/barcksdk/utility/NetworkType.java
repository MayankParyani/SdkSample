/*
 * *
 *  * Created by Mayank Paryani on 18/4/19 5:56 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 1/10/18 12:23 PM
 *
 */

package wd.com.barcksdk.utility;

public enum NetworkType {

    MOBILE("Mobile", 0),
    MOBILE_2G("Mobile 2g", 1),
    MOBILE_3G("Mobile 3g", 2),
    MOBILE_4G("Mobile 4g", 3),
    WIFI("Wifi", 3),
    NOT_CONNECTED("Not connected to any network", 5);

    private String type;
    private int value;

    NetworkType(String type, int value) {
        this.type = type;
        this.value = value;
    }

    public String getNetworkType() {
        return type;
    }

    public int getNetworkValue() {
        return value;
    }
}
