/*
 * *
 *  * Created by Mayank Paryani on 18/4/19 3:05 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 18/4/19 3:05 PM
 *
 */

package wd.com.barcksdk.utility;

import android.Manifest;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.concurrent.ExecutionException;

import wd.com.barcksdk.BuildConfig;

import static android.content.Context.WIFI_SERVICE;

public class DeviceUtility {

    public static String getSDKVersion() {
        try {
            return BuildConfig.VERSION_NAME;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getSecureID(Context context) {
        try {
            String androidId = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            return androidId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDeviceOS() {
        try {
            return Build.VERSION.RELEASE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDeviceModel() {
        try {
            return Build.MODEL;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDefaultUserAgent(Context context) {
        StringBuilder result = new StringBuilder(64);
        result.append("Dalvik/");
        result.append(System.getProperty("java.vm.version")); // such as 1.1.0
        result.append(" (Linux; U; Android ");

        String version = Build.VERSION.RELEASE; // "1.0" or "3.4b5"
        result.append(version.length() > 0 ? version : "1.0");

        // add the model for the release build
        if ("REL".equals(Build.VERSION.CODENAME)) {
            String model = Build.MODEL;
            if (model.length() > 0) {
                result.append("; ");
                result.append(model);
            }
        }
        String id = Build.ID; // "MASTER" or "M4-rc20"
        if (id.length() > 0) {
            result.append(" Build/");
            result.append(id);
        }
        result.append(")");
        return result.toString();
    }

    public String getDeviceIMEI(Context context) {
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            } else {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (telephonyManager != null) {
                    return telephonyManager.getDeviceId();
                } else {
                    return "";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getPackageName(Context context) {
        try {
            return context.getPackageName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getAppName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(context.getApplicationInfo().packageName, 0);
        } catch (final PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return (String) (applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo) : "Unknown");
    }

    public static String getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public static String getMCC(Context context) {
        try {
            TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String networkOperator = null;
            if (tel != null) {
                networkOperator = tel.getSimOperator();
            }
            if (!TextUtils.isEmpty(networkOperator)) {
                String mcc = networkOperator.substring(0, 3);
                return mcc;
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getMNC(Context context) {
        try {
            TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String networkOperator = null;
            if (tel != null) {
                networkOperator = tel.getSimOperator();
            }
            if (!TextUtils.isEmpty(networkOperator)) {
                String mnc = networkOperator.substring(3);
                return mnc;
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getCarrierName(Context context) {
        try {
            TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String carrierName = null;
            if (tel != null) {
                carrierName = tel.getSimOperatorName();
            }
            if (!TextUtils.isEmpty(carrierName)) {
                return carrierName;
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Get IP address from first non-localhost interface
     *
     * @param context must be application level context
     * @return address or empty string
     */
    public static String getIPAddress(Context context) {
        String ip;
        WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
        if (wm != null) {
            ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
            return ip;
        }
        return "";
    }

    private static boolean isNavigationBarAvailable(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d = null;
        if (wm != null) {
            d = wm.getDefaultDisplay();
        }
        DisplayMetrics dm = new DisplayMetrics();
        if (d != null) {
            d.getRealMetrics(dm);
        }
        int realHeight = dm.heightPixels;
        int realWidth = dm.widthPixels;
        if (d != null) {
            d.getMetrics(dm);
        }
        int displayHeight = dm.heightPixels;
        int displayWidth = dm.widthPixels;
        return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
    }

    private static int getNavigationBarHeight(Context context) {
        boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0 && !hasMenuKey) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static String getScreenResolution(Context context) {
        int ht;
        int wt;
        DisplayMetrics displaymetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(displaymetrics);
        }
        if (isNavigationBarAvailable(context)) {
            ht = displaymetrics.heightPixels + getNavigationBarHeight(context);
        } else {
            ht = displaymetrics.heightPixels;
        }
        wt = displaymetrics.widthPixels;
        return "{" + ht + "*" + wt + "}";
    }

    public static String getOSName(Context context) {
        StringBuilder builder = new StringBuilder();
        //builder.append("android : ").append(Build.VERSION.RELEASE);

        Field[] fields = Build.VERSION_CODES.class.getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            int fieldValue = -1;

            try {
                fieldValue = field.getInt(new Object());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            if (fieldValue == Build.VERSION.SDK_INT) {
                //builder.append(" : ").append(fieldName).append(" : ");
                builder.append(fieldName);
                //builder.append("sdk=").append(fieldValue);
            }
        }
        return builder.toString();
    }

    /**
     * Checks the type of data connection that is currently available on the device.
     *
     * @param context use application level context to avoid unnecessary leaks.
     * @return String of connected type i.e. Mobile or Wifi
     **/
    public static String getDataConnectionType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null && connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null) {
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()) {
                return NetworkType.MOBILE.getNetworkType();
            } else if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {
                return NetworkType.WIFI.getNetworkType();
            } else
                return NetworkType.NOT_CONNECTED.getNetworkType();
        } else
            return NetworkType.NOT_CONNECTED.getNetworkType();
    }

    /**
     * To get device consuming network type is 2g,3g,4g or wifi
     *
     * @param context must be application level context
     * @return "2g","3g","4g" or Wifi as a String based on the network type
     */
    public static String getNetworkType(Context context) {

        if (!TextUtils.isEmpty(getDataConnectionType(context)) &&
                getDataConnectionType(context).equalsIgnoreCase(NetworkType.WIFI.getNetworkType())) {
            return NetworkType.WIFI.getNetworkType();
        } else if (!TextUtils.isEmpty(getDataConnectionType(context)) &&
                getDataConnectionType(context).equalsIgnoreCase(NetworkType.MOBILE.getNetworkType())) {
            TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            int networkType = mTelephonyManager.getNetworkType();

            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return NetworkType.MOBILE_2G.getNetworkType();

                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    return NetworkType.MOBILE_3G.getNetworkType();

                case TelephonyManager.NETWORK_TYPE_LTE:
                    return NetworkType.MOBILE_4G.getNetworkType();

                default:
                    return NetworkType.NOT_CONNECTED.getNetworkType();
            }
        }
        return NetworkType.NOT_CONNECTED.getNetworkType();
    }

    public static void getAdIdS(final Context context) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                AdvertisingIdClient.Info idInfo = null;
                try {
                    idInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    String advertId = idInfo.getId();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }
}
